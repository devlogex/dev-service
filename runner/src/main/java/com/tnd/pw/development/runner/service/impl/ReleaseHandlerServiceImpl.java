package com.tnd.pw.development.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.release.constants.ReleaseLayoutType;
import com.tnd.pw.development.release.exception.*;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.exception.InvalidDataException;
import com.tnd.pw.development.runner.service.CalculateService;
import com.tnd.pw.development.runner.service.ReleaseHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.EpicRep;
import com.tnd.pw.development.common.representations.ReleasePhaseRep;
import com.tnd.pw.development.common.representations.ReleaseRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.release.constants.EpicState;
import com.tnd.pw.development.release.constants.PhaseType;
import com.tnd.pw.development.release.constants.ReleaseState;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleaseLayoutEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.report.common.constants.ReportAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ReleaseHandlerServiceImpl implements ReleaseHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseHandlerServiceImpl.class);

    @Autowired
    private ReleaseService releaseService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private CalculateService calculateService;

    @Override
    public CsDevRepresentation addRelease(DevRequest request) throws DBServiceException {
        ReleaseEntity release = releaseService.createRelease(
                ReleaseEntity.builder()
                        .name(request.getName())
                        .productId(request.getId())
                        .owner(request.getOwner())
                        .theme(request.getTheme())
                        .startOn(request.getStartOn())
                        .endOn(request.getEndOn())
                        .releaseDate(request.getReleaseDate())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );
        if(request.isGeneratePhases()) {
            createDefaultPhases(release);
        }

        sdkService.createHistory(request.getPayload().getUserId(), release.getId(), ReportAction.CREATED, GsonUtils.convertToString(release));

        return getRelease(release.getProductId());
    }

    @Override
    public CsDevRepresentation addParkingLot(DevRequest request) throws DBServiceException {
        createParkingLotFeature(request);
        createParkingLotEpic(request);
        return getRelease(request.getProductId());
    }

    private void createParkingLotEpic(DevRequest request) throws DBServiceException {
        ReleaseEntity release = releaseService.createParkingLotEpic(
                ReleaseEntity.builder()
                        .name("Parking Lot")
                        .productId(request.getProductId())
                        .build()
        );


        releaseService.createReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(release.getId())
                        .productId(release.getProductId())
                        .type(ReleaseLayoutType.EPIC)
                        .layout(GsonUtils.convertToString(new ArrayList()))
                        .build());
    }

    private void createParkingLotFeature(DevRequest request) throws DBServiceException {
        ReleaseEntity release = releaseService.createParkingLotFeature(
                ReleaseEntity.builder()
                        .name("Parking Lot")
                        .productId(request.getProductId())
                        .build()
        );


        releaseService.createReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(release.getId())
                        .productId(release.getProductId())
                        .type(ReleaseLayoutType.FEATURE)
                        .layout(GsonUtils.convertToString(new ArrayList()))
                        .build());
    }

    @Override
    public CsDevRepresentation getRelease(DevRequest request) throws DBServiceException {
        return getRelease(request.getId());
    }

    private CsDevRepresentation getRelease(Long productId) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = new ArrayList<>();
        List<FeatureEntity> features = new ArrayList<>();
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .productId(productId)
                            .build()
            );
            List<Long> releaseIds = releaseEntities.stream().map(release->release.getId()).collect(Collectors.toList());
            features = featureService.getFeature(releaseIds);
        } catch (ReleaseNotFoundException | FeatureNotFoundException e) {}
        return RepresentationBuilder.buildListReleaseRep(releaseEntities, features);
    }

    @Override
    public ReleaseRep getReleaseInfo(DevRequest request) throws DBServiceException, ReleaseNotFoundException, ActionServiceFailedException {
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
        CsActionRepresentation actionRep = sdkService.getTodoComment(releaseEntity.getId());

        sdkService.createWatcher(request.getPayload().getUserId(), releaseEntity.getId());
        return RepresentationBuilder.buildReleaseRep(releaseEntity, actionRep, releasePhase);
    }

    @Override
    public ReleaseRep updateRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException, ActionServiceFailedException, UnableUpdateParkingLotException {
        ReleaseEntity releaseEntity = releaseService.getRelease(ReleaseEntity.builder().id(request.getId()).build()).get(0);
        String oldRelease = GsonUtils.convertToString(releaseEntity);

        if(request.getName() != null) {
            releaseEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            releaseEntity.setState(ReleaseState.valueOf(request.getState()).ordinal());
        }
        if(request.getOwner() != null) {
            releaseEntity.setOwner(request.getOwner());
        }
        if(request.getInitiatives() != null) {
            releaseEntity.setInitiatives(GsonUtils.convertToString(request.getInitiatives()));
        }
        if(request.getGoals() != null) {
            releaseEntity.setGoals(GsonUtils.convertToString(request.getGoals()));
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
        CsActionRepresentation actionRep = sdkService.getTodoComment(releaseEntity.getId());

        sdkService.createHistory(request.getPayload().getUserId(), releaseEntity.getId(), ReportAction.UPDATED, oldRelease + "|" + GsonUtils.convertToString(releaseEntity));

        return RepresentationBuilder.buildReleaseRep(releaseEntity, actionRep, releasePhase);
    }

    @Override
    public CsDevRepresentation removeRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        ReleaseEntity releaseEntity = releaseService.getRelease(
                ReleaseEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        releaseService.removeRelease(releaseEntity);
        calculateService.updateUserStoryAfterRemoveRelease(releaseEntity.getId());
        return getRelease(releaseEntity.getProductId());
    }

    @Override
    public CsDevRepresentation addReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException {
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
    public ReleasePhaseRep getReleasePhaseInfo(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException, ActionServiceFailedException {
        ReleasePhaseEntity releasePhaseEntity = releaseService.getReleasePhase(
                ReleasePhaseEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(releasePhaseEntity.getId());
        return RepresentationBuilder.buildReleasePhaseRep(releasePhaseEntity, actionRep);
    }

    @Override
    public ReleasePhaseRep updateReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException, ActionServiceFailedException {
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
        CsActionRepresentation actionRep = sdkService.getTodoComment(releasePhaseEntity.getId());
        return RepresentationBuilder.buildReleasePhaseRep(releasePhaseEntity, actionRep);
    }

    @Override
    public CsDevRepresentation removeReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException {
        ReleasePhaseEntity releasePhaseEntity = releaseService.getReleasePhase(
                ReleasePhaseEntity.builder()
                        .id(request.getId()).build()
        ).get(0);

        releaseService.removeReleasePhase(ReleasePhaseEntity.builder().id(request.getId()).build());
        return getReleasePhase(releasePhaseEntity.getReleaseId());
    }

    private CsDevRepresentation getReleasePhase(Long releaseId) throws DBServiceException {
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
    public CsDevRepresentation addEpic(DevRequest request) throws DBServiceException, EpicNotFoundException {
        Long productId = GenUID.getProductId(request.getId());
        EpicEntity epic = releaseService.createEpic(
                EpicEntity.builder()
                        .releaseId(request.getId())
                        .productId(productId)
                        .name(request.getName())
                        .description(request.getDescription())
                        .files(request.getFiles())
                        .createdBy(request.getPayload().getUserId())
                        .startOn(request.getStartOn())
                        .endOn(request.getEndOn())
                        .build()
        );

        List<Long> layout = null;
        try {
            ReleaseLayoutEntity releaseLayoutEntity = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(epic.getReleaseId())
                            .type(ReleaseLayoutType.EPIC)
                            .build()
            ).get(0);
            layout = GsonUtils.getGson().fromJson(releaseLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
            layout.add(epic.getId());
            releaseLayoutEntity.setLayout(GsonUtils.convertToString(layout));
            releaseService.updateReleaseLayout(releaseLayoutEntity);
        } catch (ReleaseLayoutNotFoundException e) {
            layout = new ArrayList<>();
            layout.add(epic.getId());
            releaseService.createReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(epic.getReleaseId())
                            .productId(epic.getProductId())
                            .type(ReleaseLayoutType.EPIC)
                            .layout(GsonUtils.convertToString(layout))
                            .build()
            );
        }

        sdkService.createHistory(request.getPayload().getUserId(), epic.getId(), ReportAction.CREATED, GsonUtils.convertToString(epic));
        return getEpic(productId);
    }

    @Override
    public EpicRep getEpicInfo(DevRequest request) throws DBServiceException, EpicNotFoundException, ActionServiceFailedException {
        EpicEntity epicEntity = releaseService.getEpic(
                EpicEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);

        CsActionRepresentation actionRep = sdkService.getTodoComment(epicEntity.getId());
        sdkService.createWatcher(request.getPayload().getUserId(), epicEntity.getId());
        return RepresentationBuilder.buildEpicRep(epicEntity, actionRep);
    }

    @Override
    public CsDevRepresentation updateEpic(DevRequest request) throws DBServiceException, EpicNotFoundException, ActionServiceFailedException, ReleaseLayoutNotFoundException {
        EpicEntity epicEntity = releaseService.getEpic(
                EpicEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        EpicEntity oldEpic = epicEntity;
        if(request.getName() != null) {
            epicEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            epicEntity.setState(EpicState.valueOf(request.getState()).ordinal());
        }
        if(request.getAssignTo() != null) {
            epicEntity.setAssignTo(request.getAssignTo());
        }
        if(request.getInitiatives() != null) {
            epicEntity.setInitiatives(GsonUtils.convertToString(request.getInitiatives()));
        }
        if(request.getGoals() != null) {
            epicEntity.setGoals(GsonUtils.convertToString(request.getGoals()));
        }
        if(request.getDescription() != null) {
            epicEntity.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            epicEntity.setFiles(request.getFiles());
        }
        if(request.getReleaseId() != null
                && request.getReleaseId().compareTo(epicEntity.getReleaseId()) != 0) {
            ReleaseLayoutEntity oldLayoutEntity = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(epicEntity.getReleaseId())
                            .type(ReleaseLayoutType.EPIC)
                            .build()
            ).get(0);
            ReleaseLayoutEntity newLayoutEntity = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(request.getReleaseId())
                            .type(ReleaseLayoutType.EPIC)
                            .build()
            ).get(0);

            List<Long> oldLayout = GsonUtils.getGson().fromJson(oldLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
            List<Long> newLayout = GsonUtils.getGson().fromJson(newLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
            oldLayout.remove(epicEntity.getId());
            newLayout.add(epicEntity.getId());
            oldLayoutEntity.setLayout(GsonUtils.convertToString(oldLayout));
            newLayoutEntity.setLayout(GsonUtils.convertToString(newLayout));
            releaseService.updateReleaseLayout(oldLayoutEntity);
            releaseService.updateReleaseLayout(newLayoutEntity);

            epicEntity.setReleaseId(request.getReleaseId());
        }
        if(request.getStartOn() != null) {
            epicEntity.setStartOn(request.getStartOn());
        }
        if(request.getEndOn() != null) {
            epicEntity.setEndOn(request.getEndOn());
        }

        releaseService.updateEpic(epicEntity);
        sdkService.createHistory(request.getPayload().getUserId(), epicEntity.getId(), ReportAction.UPDATED, oldEpic + "|" + GsonUtils.convertToString(epicEntity));
        return getEpic(epicEntity.getProductId());
    }

    @Override
    public CsDevRepresentation removeEpic(DevRequest request) throws DBServiceException, EpicNotFoundException {
        EpicEntity epicEntity = releaseService.getEpic(
                EpicEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        releaseService.removeEpic(epicEntity);
        calculateService.updateUserStoryAfterRemoveEpic(epicEntity.getId());
        return getEpic(epicEntity.getProductId());
    }

    private CsDevRepresentation getEpic(Long productId) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = new ArrayList<>();
        List<EpicEntity> epicEntities = new ArrayList<>();
        List<ReleaseLayoutEntity> releaseLayouts = new ArrayList<>();
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .productId(productId)
                            .build()
            );
            epicEntities = releaseService.getEpic(
                    EpicEntity.builder()
                            .productId(productId)
                            .build()
            );
            releaseLayouts = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .productId(productId)
                            .type(ReleaseLayoutType.EPIC)
                            .build()
            );

        }catch (ReleaseNotFoundException | ReleaseLayoutNotFoundException | EpicNotFoundException e) {
        }
        return RepresentationBuilder.buildListEpicRep(epicEntities, releaseEntities, releaseLayouts);
    }

    @Override
    public CsDevRepresentation getEpic(DevRequest request) throws DBServiceException {
        return getEpic(request.getId());
    }

    @Override
    public CsDevRepresentation getReleasePhase(DevRequest request) throws DBServiceException {
        return getReleasePhase(request.getId());
    }

    @Override
    public CsDevRepresentation updateEpicLayout(DevRequest request) throws DBServiceException, ReleaseLayoutNotFoundException, InvalidDataException {
        ReleaseLayoutEntity releaseLayoutEntity = releaseService.getReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(request.getId())
                        .type(ReleaseLayoutType.EPIC)
                        .build()
        ).get(0);
        List<Long> layout = GsonUtils.getGson().fromJson(releaseLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());

        checkInputData(request.getLayout(), layout);

        releaseLayoutEntity.setLayout(GsonUtils.convertToString(request.getLayout()));
        releaseService.updateReleaseLayout(releaseLayoutEntity);
        return getEpic(releaseLayoutEntity.getProductId());
    }

    @Override
    public CsDevRepresentation updateEpicRelease(DevRequest request) throws DBServiceException, EpicNotFoundException, ReleaseLayoutNotFoundException, InvalidDataException {
        EpicEntity epicEntity = releaseService.getEpic(
                EpicEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        ReleaseLayoutEntity oldLayoutEntityEpic = releaseService.getReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(epicEntity.getReleaseId())
                        .type(ReleaseLayoutType.EPIC)
                        .build()
        ).get(0);
        ReleaseLayoutEntity oldLayoutEntity = releaseService.getReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(request.getReleaseId())
                        .type(ReleaseLayoutType.EPIC)
                        .build()
        ).get(0);
        List<Long> oldLayoutFeature = GsonUtils.getGson().fromJson(oldLayoutEntityEpic.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
        List<Long> oldLayout = GsonUtils.getGson().fromJson(oldLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
        List<Long> newLayout = request.getLayout();
        checkInputData(oldLayout, newLayout, epicEntity.getId());

        oldLayoutFeature.remove(epicEntity.getId());
        oldLayoutEntityEpic.setLayout(GsonUtils.convertToString(oldLayoutFeature));
        oldLayoutEntity.setLayout(GsonUtils.convertToString(newLayout));

        releaseService.updateReleaseLayout(oldLayoutEntity);
        releaseService.updateReleaseLayout(oldLayoutEntityEpic);
        epicEntity.setReleaseId(request.getReleaseId());
        releaseService.updateEpic(epicEntity);

        return getEpic(epicEntity.getProductId());
    }


    private void checkInputData(List<Long> layout1, List<Long> layout2) throws InvalidDataException {
        HashSet<Long> set = new HashSet<>();
        set.addAll(layout1);
        set.addAll(layout2);
        if(set.size() != layout1.size() || set.size() != layout2.size()) {
            throw new InvalidDataException("Layout Input Error !");
        }
    }

    private void checkInputData(List<Long> oldLayout, List<Long> newLayout, Long id) throws InvalidDataException {
        HashSet<Long> set = new HashSet<>();
        oldLayout.add(id);
        set.addAll(oldLayout);
        set.addAll(newLayout);
        if(set.size() != oldLayout.size() || set.size() != newLayout.size()) {
            throw new InvalidDataException("Layout Input Error !");
        }
    }

    private void createDefaultPhases(ReleaseEntity release) throws DBServiceException {
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
