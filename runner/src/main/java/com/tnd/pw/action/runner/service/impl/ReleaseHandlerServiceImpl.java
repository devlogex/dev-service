package com.tnd.pw.action.runner.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.runner.service.ReleaseHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.EpicRep;
import com.tnd.pw.development.common.representations.ReleasePhaseRep;
import com.tnd.pw.development.common.representations.ReleaseRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.release.constants.EpicState;
import com.tnd.pw.development.release.constants.PhaseType;
import com.tnd.pw.development.release.constants.ReleaseState;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.strategy.call.api.CallActionApi;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReleaseHandlerServiceImpl implements ReleaseHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseHandlerServiceImpl.class);

    @Autowired
    private ReleaseService releaseService;
    @Autowired
    private CallActionApi callActionApi;

    @Override
    public CsDevRepresentation addRelease(DevRequest request) throws IOException, DBServiceException {
        ReleaseEntity release = releaseService.createRelease(
                ReleaseEntity.builder()
                        .name(request.getName())
                        .productId(request.getId())
                        .owner(request.getOwner())
                        .theme(request.getTheme())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );
        if(request.isGeneratePhases()) {
            createDefaultPhases(release);
        }

        List<ReleaseEntity> releaseEntities = null;
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .productId(release.getProductId())
                            .build()
            );
        } catch (ReleaseNotFoundException e) {}
        return RepresentationBuilder.buildListReleaseRep(releaseEntities);
    }

    @Override
    public CsDevRepresentation getRelease(DevRequest request) throws IOException, DBServiceException {
        return getRelease(request.getId());
    }

    private CsDevRepresentation getRelease(Long productId) throws IOException, DBServiceException {
        List<ReleaseEntity> releaseEntities = null;
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .productId(productId)
                            .build()
            );
        } catch (ReleaseNotFoundException e) {}
        return RepresentationBuilder.buildListReleaseRep(releaseEntities);
    }

    @Override
    public ReleaseRep getReleaseInfo(DevRequest request) throws DBServiceException, ReleaseNotFoundException, IOException, CallApiFailException {
        ReleaseEntity releaseEntity = releaseService.getRelease(
                ReleaseEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        List<ReleasePhaseEntity> releasePhase = null;
        try {
            releasePhase = releaseService.getReleasePhase(
                    ReleasePhaseEntity.builder()
                            .releaseId(releaseEntity.getId())
                            .build()
            );
        } catch (ReleasePhaseNotFoundException e) {
        }
        CsActionRepresentation actionRep = callActionApi.call(releaseEntity.getId(), request);
        return RepresentationBuilder.buildReleaseRep(releaseEntity, actionRep, releasePhase);
    }

    @Override
    public ReleaseRep updateRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException, IOException, CallApiFailException {
        ReleaseEntity releaseEntity = releaseService.getRelease(ReleaseEntity.builder().id(request.getId()).build()).get(0);
        if(request.getName() != null) {
            releaseEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            releaseEntity.setState(ReleaseState.valueOf(request.getState()).ordinal());
        }
        if(request.getOwner() != null) {
            releaseEntity.setOwner(request.getOwner());
        }
        if(request.getInitiativeId() != null) {
            releaseEntity.setInitiativeId(request.getInitiativeId());
        }
        if(request.getGoals() != null) {
            releaseEntity.setGoals(request.getGoals());
        }
        if(request.getReleaseDate() != null) {
            releaseEntity.setReleaseDate(request.getReleaseDate());
            long duration = TimeUnit.DAYS.convert(request.getReleaseDate() - releaseEntity.getCreatedAt(), TimeUnit.MILLISECONDS);
            releaseEntity.setDaysToRelease((int)duration);
        }
        if(request.getStartOn() != null) {
            releaseEntity.setStartOn(request.getStartOn());
        }
        if(request.getEndOn() != null) {
            releaseEntity.setEndOn(request.getEndOn());
        }
        if(request.getDevelopStartOn() != null) {
            releaseEntity.setDevelopStartOn(request.getDevelopStartOn());
        }
        if(request.getTheme() != null) {
            releaseEntity.setTheme(request.getTheme());
        }
        if(request.getFiles() != null) {
            releaseEntity.setFiles(request.getFiles());
        }
        releaseService.updateRelease(releaseEntity);

        List<ReleasePhaseEntity> releasePhase = null;
        try {
            releasePhase = releaseService.getReleasePhase(ReleasePhaseEntity.builder().releaseId(releaseEntity.getId()).build());
        } catch (ReleasePhaseNotFoundException e) {}
        CsActionRepresentation actionRep = callActionApi.call(releaseEntity.getId(), request);
        return RepresentationBuilder.buildReleaseRep(releaseEntity, actionRep, releasePhase);
    }

    @Override
    public CsDevRepresentation removeRelease(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException {
        ReleaseEntity releaseEntity = releaseService.getRelease(
                ReleaseEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        releaseService.removeRelease(releaseEntity);
        return getRelease(releaseEntity.getProductId());
    }

    @Override
    public CsDevRepresentation addReleasePhase(DevRequest request) throws IOException, DBServiceException, ReleasePhaseNotFoundException {
        ReleasePhaseEntity releasePhase = releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .type(PhaseType.valueOf(request.getType()).ordinal())
                        .name(String.format("new %s", PhaseType.valueOf(request.getType()).name()))
                        .releaseId(request.getId())
                        .build()
        );
        List<ReleasePhaseEntity> releasePhases = releaseService.getReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(releasePhase.getReleaseId())
                        .build()
        );
        return RepresentationBuilder.buildListReleasePhaseRep(releasePhases);
    }

    @Override
    public ReleasePhaseRep getReleasePhaseInfo(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException, CallApiFailException {
        ReleasePhaseEntity releasePhaseEntity = releaseService.getReleasePhase(
                ReleasePhaseEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = callActionApi.call(releasePhaseEntity.getId(), request);
        return RepresentationBuilder.buildReleasePhaseRep(releasePhaseEntity, actionRep);
    }

    @Override
    public ReleasePhaseRep updateReleasePhase(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException, CallApiFailException {
        ReleasePhaseEntity releasePhaseEntity = releaseService.getReleasePhase(
                ReleasePhaseEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        if(request.getName() != null) {
            releasePhaseEntity.setName(request.getName());
        }
        if(request.getColor() != null) {
            releasePhaseEntity.setColor(request.getColor());
        }
        if(request.getDate() != null) {
            releasePhaseEntity.setDate(request.getDate());
        }
        if(request.getDescription() != null) {
            releasePhaseEntity.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            releasePhaseEntity.setFiles(request.getFiles());
        }
        if(request.getType() != null) {
            if(request.getType().equals(PhaseType.PHASE.name()) && releasePhaseEntity.getType() == PhaseType.MILESTONE.ordinal()) {
                releasePhaseEntity.setDate(String.format("%d-%d",releasePhaseEntity.getDate()));
            } else if(request.getType().equals(PhaseType.MILESTONE.name()) && releasePhaseEntity.getType() == PhaseType.PHASE.ordinal()) {
                releasePhaseEntity.setDate(releasePhaseEntity.getDate().split("-")[0]);
            }
            releasePhaseEntity.setType(PhaseType.valueOf(request.getType()).ordinal());
        }
        releaseService.updateReleasePhase(releasePhaseEntity);
        CsActionRepresentation actionRep = callActionApi.call(releasePhaseEntity.getId(), request);
        return RepresentationBuilder.buildReleasePhaseRep(releasePhaseEntity, actionRep);
    }

    @Override
    public CsDevRepresentation removeReleasePhase(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException {
        ReleasePhaseEntity releasePhaseEntity = releaseService.getReleasePhase(
                ReleasePhaseEntity.builder()
                        .id(request.getId()).build()
        ).get(0);

        releaseService.removeReleasePhase(ReleasePhaseEntity.builder().id(request.getId()).build());
        return getReleasePhase(releasePhaseEntity.getReleaseId());
    }

    private CsDevRepresentation getReleasePhase(Long releaseId) throws IOException, DBServiceException {
        List<ReleasePhaseEntity> releasePhases = null;
        try {
            releasePhases = releaseService.getReleasePhase(
                    ReleasePhaseEntity.builder()
                            .releaseId(releaseId)
                            .build()
            );
        } catch (ReleasePhaseNotFoundException e) {
        }
        return RepresentationBuilder.buildListReleasePhaseRep(releasePhases);
    }

    @Override
    public CsDevRepresentation addEpic(DevRequest request) throws IOException, DBServiceException, EpicNotFoundException {
        Long productId = GenUID.getProductId(request.getId());
        EpicEntity epic = releaseService.createEpic(
                EpicEntity.builder()
                        .releaseId(request.getId())
                        .productId(productId)
                        .name(request.getName())
                        .description(request.getDescription())
                        .files(request.getFiles())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );
        List<EpicEntity> epicEntities = releaseService.getEpic(
                EpicEntity.builder()
                        .productId(epic.getProductId())
                        .build()
        );
        return RepresentationBuilder.buildListEpicRep(epicEntities);
    }

    @Override
    public EpicRep getEpicInfo(DevRequest request) throws DBServiceException, EpicNotFoundException, IOException, CallApiFailException {
        EpicEntity epicEntity = releaseService.getEpic(
                EpicEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);

        CsActionRepresentation actionRep = callActionApi.call(epicEntity.getId(), request);
        return RepresentationBuilder.buildEpicRep(epicEntity, actionRep);
    }

    @Override
    public EpicRep updateEpic(DevRequest request) throws DBServiceException, EpicNotFoundException, IOException, CallApiFailException {
        EpicEntity epicEntity = releaseService.getEpic(
                EpicEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        if(request.getName() != null) {
            epicEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            epicEntity.setState(EpicState.valueOf(request.getState()).ordinal());
        }
        if(request.getAssignTo() != null) {
            epicEntity.setAssignTo(request.getAssignTo());
        }
        if(request.getInitiativeId() != null) {
            epicEntity.setInitiativeId(request.getInitiativeId());
        }
        if(request.getGoals() != null) {
            epicEntity.setGoals(request.getGoals());
        }
        if(request.getDescription() != null) {
            epicEntity.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            epicEntity.setFiles(request.getFiles());
        }
        if(request.getReleaseId() != null) {
            epicEntity.setReleaseId(request.getReleaseId());
        }

        releaseService.updateEpic(epicEntity);
        CsActionRepresentation actionRep = callActionApi.call(epicEntity.getId(), request);
        return RepresentationBuilder.buildEpicRep(epicEntity, actionRep);
    }

    @Override
    public CsDevRepresentation removeEpic(DevRequest request) throws IOException, DBServiceException, EpicNotFoundException {
        EpicEntity epicEntity = releaseService.getEpic(
                EpicEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        releaseService.removeEpic(epicEntity);
        return getEpic(epicEntity.getProductId());
    }

    private CsDevRepresentation getEpic(Long productId) throws IOException, DBServiceException {
        List<EpicEntity> epicEntities = null;
        try {
            epicEntities = releaseService.getEpic(
                    EpicEntity.builder()
                            .productId(productId)
                            .build()
            );
        } catch (EpicNotFoundException e) {
        }
        return RepresentationBuilder.buildListEpicRep(epicEntities);
    }

    @Override
    public CsDevRepresentation getEpic(DevRequest request) throws IOException, DBServiceException {
        return getEpic(request.getId());
    }

    @Override
    public CsDevRepresentation getReleasePhase(DevRequest request) throws DBServiceException, IOException {
        return getReleasePhase(request.getId());
    }


    private void createDefaultPhases(ReleaseEntity release) throws IOException, DBServiceException {
        Calendar calendar = Calendar.getInstance();

        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Review product strategy")
                        .type(PhaseType.PHASE.ordinal())
                        .color("#A5A5A5")
                        .date(String.format("%d-%d", calendar.getTimeInMillis(), calendar.getTimeInMillis()))
                        .build()
        );
        calendar.add(Calendar.DATE, 1);
        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Prioritize customer ideas")
                        .type(PhaseType.PHASE.ordinal())
                        .color("#E1CC8A")
                        .date(String.format("%d-%d", calendar.getTimeInMillis(), calendar.getTimeInMillis()))
                        .build()
        );
        calendar.add(Calendar.DATE, 1);
        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Add engineering requests")
                        .type(PhaseType.PHASE.ordinal())
                        .color("#9AC7D8")
                        .date(String.format("%d-%d", calendar.getTimeInMillis(), calendar.getTimeInMillis()))
                        .build()
        );
        calendar.add(Calendar.DATE, 1);
        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Define included features")
                        .type(PhaseType.PHASE.ordinal())
                        .color("#92CD53")
                        .date(String.format("%d-%d", calendar.getTimeInMillis(), calendar.getTimeInMillis()))
                        .build()
        );
        calendar.add(Calendar.DATE, 1);
        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Review release with product team")
                        .type(PhaseType.MILESTONE.ordinal())
                        .color("#FAB35E")
                        .date(String.format("%d", calendar.getTimeInMillis()))
                        .build()
        );
        calendar.add(Calendar.DATE, 1);
        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Development")
                        .type(PhaseType.PHASE.ordinal())
                        .color("#EA8282")
                        .date(String.format("%d-%d", calendar.getTimeInMillis(), calendar.getTimeInMillis()))
                        .build()
        );
        calendar.add(Calendar.DATE, 1);
        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Launch planning")
                        .type(PhaseType.PHASE.ordinal())
                        .color("#94819E")
                        .date(String.format("%d-%d", calendar.getTimeInMillis(), calendar.getTimeInMillis()))
                        .build()
        );
        calendar.add(Calendar.DATE, 1);
        releaseService.createReleasePhase(
                ReleasePhaseEntity.builder()
                        .releaseId(release.getId())
                        .name("Sales and Support training")
                        .type(PhaseType.PHASE.ordinal())
                        .color("#94819E")
                        .date(String.format("%d", calendar.getTimeInMillis()))
                        .build()
        );
    }
}
