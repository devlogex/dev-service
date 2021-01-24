package com.tnd.pw.development.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.feature.constants.UserStoryState;
import com.tnd.pw.development.feature.entity.*;
import com.tnd.pw.development.feature.exception.UserStoryNotFoundException;
import com.tnd.pw.development.release.constants.ReleaseLayoutType;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.exception.InvalidDataException;
import com.tnd.pw.development.runner.service.CalculateService;
import com.tnd.pw.development.runner.service.FeatureHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.FeatureRep;
import com.tnd.pw.development.common.representations.RequirementRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.feature.constants.FeatureState;
import com.tnd.pw.development.feature.constants.FeatureType;
import com.tnd.pw.development.feature.constants.RequirementState;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleaseLayoutEntity;
import com.tnd.pw.development.release.exception.ReleaseLayoutNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.report.common.constants.ReportAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FeatureHandlerServiceImpl implements FeatureHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureHandlerServiceImpl.class);

    @Autowired
    private FeatureService featureService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private ReleaseService releaseService;
    @Autowired
    private CalculateService calculateService;

    @Override
    public CsDevRepresentation addFeature(DevRequest request) throws DBServiceException {
        Long productId = GenUID.getProductId(request.getId());
        FeatureEntity feature = featureService.createFeature(
                FeatureEntity.builder()
                        .name(request.getName())
                        .releaseId(request.getId())
                        .productId(productId)
                        .type(FeatureType.valueOf(request.getType()).ordinal())
                        .startOn(request.getStartOn())
                        .endOn(request.getEndOn())
                        .description(request.getDescription())
                        .files(request.getFiles())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );

        List<Long> layout = null;
        try {
            ReleaseLayoutEntity releaseLayoutEntity = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(feature.getReleaseId())
                            .type(ReleaseLayoutType.FEATURE)
                            .build()
            ).get(0);
            layout = GsonUtils.getGson().fromJson(releaseLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
            layout.add(feature.getId());
            releaseLayoutEntity.setLayout(GsonUtils.convertToString(layout));
            releaseService.updateReleaseLayout(releaseLayoutEntity);
        } catch (ReleaseLayoutNotFoundException e) {
            layout = new ArrayList<>();
            layout.add(feature.getId());
            releaseService.createReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(feature.getReleaseId())
                            .productId(feature.getProductId())
                            .type(ReleaseLayoutType.FEATURE)
                            .layout(GsonUtils.convertToString(layout))
                            .build()
            );
        }

        sdkService.createHistory(request.getPayload().getUserId(), feature.getId(), ReportAction.CREATED, GsonUtils.convertToString(feature));
        calculateService.calculateDevHide(feature);
        return getFeatures(productId);
    }

    @Override
    public CsDevRepresentation updateFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException, ReleaseLayoutNotFoundException {
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        String oldRelease = GsonUtils.convertToString(featureEntity);

        if(request.getName() != null) {
            featureEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            featureEntity.setState(FeatureState.valueOf(request.getState()).ordinal());
        }
        if(request.getType() != null) {
            featureEntity.setType(FeatureType.valueOf(request.getType()).ordinal());
        }
        if(request.getAssignTo() != null) {
            featureEntity.setAssignTo(request.getAssignTo());
        }
        if(request.getInitiativeId() != null) {
            featureEntity.setInitiativeId(request.getInitiativeId());
        }
        if(request.getGoals() != null) {
            featureEntity.setGoals(GsonUtils.convertToString(request.getGoals()));
        }
        if(request.getEpicId() != null) {
            featureEntity.setEpicId(request.getEpicId());
        }
        if(request.getDescription() != null) {
            featureEntity.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            featureEntity.setFiles(request.getFiles());
        }
        if(request.getStartOn() != null) {
            featureEntity.setStartOn(request.getStartOn());
        }
        if(request.getEndOn() != null) {
            featureEntity.setEndOn(request.getEndOn());
        }

        if(request.getReleaseId() != null
                && request.getReleaseId().compareTo(featureEntity.getReleaseId()) != 0) {

            FeatureEntity oldFeature = featureEntity;
            ReleaseLayoutEntity oldLayoutEntity = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(featureEntity.getReleaseId())
                            .type(ReleaseLayoutType.FEATURE)
                            .build()
            ).get(0);
            ReleaseLayoutEntity newLayoutEntity = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .releaseId(request.getReleaseId())
                            .type(ReleaseLayoutType.FEATURE)
                            .build()
            ).get(0);

            List<Long> oldLayout = GsonUtils.getGson().fromJson(oldLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
            List<Long> newLayout = GsonUtils.getGson().fromJson(newLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
            oldLayout.remove(featureEntity.getId());
            newLayout.add(featureEntity.getId());
            oldLayoutEntity.setLayout(GsonUtils.convertToString(oldLayout));
            newLayoutEntity.setLayout(GsonUtils.convertToString(newLayout));
            releaseService.updateReleaseLayout(oldLayoutEntity);
            releaseService.updateReleaseLayout(newLayoutEntity);

            featureEntity.setReleaseId(request.getReleaseId());
            calculateService.calculateDevHide(oldFeature);
            calculateService.calculateDevHide(featureEntity);
        }

        featureService.updateFeature(featureEntity);

        sdkService.createHistory(request.getPayload().getUserId(), featureEntity.getId(), ReportAction.UPDATED, oldRelease + "|" + GsonUtils.convertToString(featureEntity));

        return getFeatures(featureEntity.getProductId());
    }

    @Override
    public CsDevRepresentation updateFeatureLayout(DevRequest request) throws DBServiceException, ReleaseLayoutNotFoundException, InvalidDataException {
        ReleaseLayoutEntity releaseLayoutEntity = releaseService.getReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(request.getId())
                        .type(ReleaseLayoutType.FEATURE)
                        .build()
        ).get(0);
        List<Long> layout = GsonUtils.getGson().fromJson(releaseLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
        
        checkInputData(request.getLayout(), layout);

        releaseLayoutEntity.setLayout(GsonUtils.convertToString(request.getLayout()));
        releaseService.updateReleaseLayout(releaseLayoutEntity);
        return getFeatures(releaseLayoutEntity.getProductId());
    }

    @Override
    public CsDevRepresentation updateFeatureRelease(DevRequest request) throws DBServiceException, FeatureNotFoundException, InvalidDataException, ReleaseLayoutNotFoundException {
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        FeatureEntity oldFeature = featureEntity;
        ReleaseLayoutEntity oldLayoutEntityFeature = releaseService.getReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(featureEntity.getReleaseId())
                        .type(ReleaseLayoutType.FEATURE)
                        .build()
        ).get(0);
        ReleaseLayoutEntity oldLayoutEntity = releaseService.getReleaseLayout(
                ReleaseLayoutEntity.builder()
                        .releaseId(request.getReleaseId())
                        .type(ReleaseLayoutType.FEATURE)
                        .build()
        ).get(0);
        List<Long> oldLayoutFeature = GsonUtils.getGson().fromJson(oldLayoutEntityFeature.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
        List<Long> oldLayout = GsonUtils.getGson().fromJson(oldLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
        List<Long> newLayout = request.getLayout();
        checkInputData(oldLayout, newLayout, featureEntity.getId());

        oldLayoutFeature.remove(featureEntity.getId());
        oldLayoutEntityFeature.setLayout(GsonUtils.convertToString(oldLayoutFeature));
        oldLayoutEntity.setLayout(GsonUtils.convertToString(newLayout));

        releaseService.updateReleaseLayout(oldLayoutEntity);
        releaseService.updateReleaseLayout(oldLayoutEntityFeature);
        featureEntity.setReleaseId(request.getReleaseId());
        featureService.updateFeature(featureEntity);

        calculateService.calculateDevHide(oldFeature);
        calculateService.calculateDevHide(featureEntity);
        return getFeatures(featureEntity.getProductId());
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

    private void checkInputData(List<Long> layout1, List<Long> layout2) throws InvalidDataException {
        HashSet<Long> set = new HashSet<>();
        set.addAll(layout1);
        set.addAll(layout2);
        if(set.size() != layout1.size() || set.size() != layout2.size()) {
            throw new InvalidDataException("Layout Input Error !");
        }
    }

    @Override
    public CsDevRepresentation getFeature(DevRequest request) throws DBServiceException {
        return getFeatures(request.getId());
    }

    @Override
    public FeatureRep getFeatureInfo(DevRequest request) throws DBServiceException, FeatureNotFoundException, ActionServiceFailedException {
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(featureEntity.getId());
        List<RequirementEntity> requirements = null;
        try {
            requirements = featureService.getRequirement(
                    RequirementEntity.builder()
                            .featureId(featureEntity.getId())
                            .build()
            );
        } catch (RequirementNotFoundException e) {
        }
        sdkService.createWatcher(request.getPayload().getUserId(), featureEntity.getId());
        return RepresentationBuilder.buildFeatureRep(featureEntity, actionRep, requirements);
    }

    @Override
    public CsDevRepresentation removeFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException {
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        featureService.removeFeature(featureEntity);
        calculateService.calculateDevHide(featureEntity);
        calculateService.updateUserStoryAfterRemoveFeature(featureEntity.getId());
        return getFeatures(featureEntity.getProductId());
    }

    @Override
    public CsDevRepresentation addRequirement(DevRequest request) throws DBServiceException {
        RequirementEntity requirement = featureService.createRequirement(
                RequirementEntity.builder()
                        .featureId(request.getId())
                        .name(request.getName())
                        .description(request.getDescription())
                        .files(request.getFiles())
                        .build()
        );
        return getRequirement(requirement.getFeatureId());
    }

    @Override
    public CsDevRepresentation getRequirement(DevRequest request) throws DBServiceException {
        return getRequirement(request.getId());
    }

    @Override
    public RequirementRep getRequirementInfo(DevRequest request) throws DBServiceException, RequirementNotFoundException, ActionServiceFailedException {
        RequirementEntity requirementEntity = featureService.getRequirement(
                RequirementEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(requirementEntity.getId());
        return RepresentationBuilder.buildRequirementRep(requirementEntity, actionRep);
    }

    @Override
    public CsDevRepresentation updateRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException {
        RequirementEntity requirementEntity = featureService.getRequirement(
                RequirementEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        if(request.getName() != null) {
            requirementEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            requirementEntity.setState(RequirementState.valueOf(request.getState()).ordinal());
        }
        if(request.getAssignTo() != null) {
            requirementEntity.setAssignTo(request.getAssignTo());
        }
        if(request.getDescription() != null) {
            requirementEntity.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            requirementEntity.setFiles(request.getFiles());
        }
        featureService.updateRequirement(requirementEntity);
        return getRequirement(requirementEntity.getFeatureId());
    }

    @Override
    public CsDevRepresentation removeRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException {
        RequirementEntity requirementEntity = featureService.getRequirement(
                RequirementEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        featureService.removeRequirement(requirementEntity);
        return getRequirement(requirementEntity.getFeatureId());
    }

    @Override
    public CsDevRepresentation addUserStory(DevRequest request) throws DBServiceException {
        UserStoryEntity userStory = featureService.createUserStory(
                UserStoryEntity.builder()
                        .productId(request.getId())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );
        return getListUserStory(userStory.getProductId(), null);
    }

    @Override
    public CsDevRepresentation getUserStory(DevRequest request) throws DBServiceException {
        Long productId = request.getProductId();
        UserStoryState state = request.getState() != null ? UserStoryState.valueOf(request.getState()) : null;
        return getListUserStory(productId, state);
    }

    @Override
    public CsDevRepresentation getUserStoryInfo(DevRequest request) throws DBServiceException {
        try {
            UserStoryEntity userStoryEntity = featureService.getUserStory(
                    UserStoryEntity.builder()
                            .id(request.getId())
                            .build()
            ).get(0);
            return getUserStoryInfo(userStoryEntity);
        } catch (UserStoryNotFoundException e) {
            return null;
        }
    }

    @Override
    public CsDevRepresentation updateUserStory(DevRequest request) throws DBServiceException, UserStoryNotFoundException, InvalidDataException {
        UserStoryEntity userStoryEntity = featureService.getUserStory(
                UserStoryEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        if(request.getName() != null) {
            userStoryEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            userStoryEntity.setState(UserStoryState.valueOf(request.getState()).ordinal());
        }
        if(request.getSteps() != null) {
            userStoryEntity.setSteps(GsonUtils.convertToString(request.getSteps()));
        }
        if(request.getEpics() != null) {
//            updateUTEpic(userStoryEntity, request);
            userStoryEntity.setEpics(GsonUtils.convertToString(request.getEpics()));
        }
        if(request.getReleases() != null) {
            userStoryEntity.setReleases(GsonUtils.convertToString(request.getReleases()));
        }
        if(request.getLength() != null) {
            userStoryEntity.setLength(request.getLength());
        }
        if(request.getPersonas() != null) {
            userStoryEntity.setPersonas(GsonUtils.convertToString(request.getPersonas()));
        }

        featureService.updateUserStory(userStoryEntity);
        return getUserStoryInfo(userStoryEntity);
    }

    private void updateUTEpic(UserStoryEntity userStoryEntity, DevRequest request) throws InvalidDataException {
        List<UTEpic> utEpics = GsonUtils.getGson().fromJson(
                userStoryEntity.getEpics(),
                new TypeToken<ArrayList<UTEpic>>(){}.getType()
        );
        List<UTEpic> newUTEpic = request.getEpics();
        if(utEpics.size() > newUTEpic.size()) {
            for(int i = 0; i < newUTEpic.size();i++) {
                utEpics.remove(newUTEpic.get(i));
            }
            if(utEpics.size() != 1) {
                throw new InvalidDataException("Data UTEpic Invalid !");
            }
        } else if(utEpics.size() < newUTEpic.size()) {
            List<UTEpic> temp = new ArrayList<>();
            temp.addAll(newUTEpic);
            for(int i = 0; i < utEpics.size();i++) {
                temp.remove(utEpics.get(i));
            }
            if(temp.size() != 1) {
                throw new InvalidDataException("Data UTEpic Invalid !");
            }
        } else {
            HashSet<UTEpic> set = new HashSet<>();
            set.addAll(utEpics);
            set.addAll(newUTEpic);
            int countChange = utEpics.size() - set.size();
            if(countChange != 2 || countChange == 0) {
                throw new InvalidDataException("Data UTEpic Invalid !");
            }
        }

        userStoryEntity.setEpics(GsonUtils.convertToString(request.getEpics()));
    }

    @Override
    public CsDevRepresentation removeUserStory(DevRequest request) throws DBServiceException, UserStoryNotFoundException {
        UserStoryEntity userStoryEntity = featureService.getUserStory(
                UserStoryEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        featureService.removeUserStory(userStoryEntity);
        return getListUserStory(userStoryEntity.getProductId(), null);
    }

    @Override
    public CsDevRepresentation getFeatureByInitiativeIds(DevRequest request) throws DBServiceException {
        List<FeatureEntity> featureEntities = new ArrayList<>();
        try {
            featureEntities = featureService.getFeatureByInitiativeIds(request.getInitiatives());
        } catch (FeatureNotFoundException e) {
        }
        return RepresentationBuilder.buildListFeatureReps(featureEntities);
    }

    private CsDevRepresentation getUserStoryInfo(UserStoryEntity userStoryEntity) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = new ArrayList<>();
        List<EpicEntity> epicEntities = new ArrayList<>();
        List<FeatureEntity> featureEntities = new ArrayList<>();
        List<Long> epicIds = new ArrayList<>();
        try {
            List<Long> releaseIds = new ArrayList<>();
            List<UTEpic> utEpics = GsonUtils.getGson().fromJson(userStoryEntity.getEpics(), new TypeToken<ArrayList<UTEpic>>(){}.getType());
            List<UTRelease> utReleases = GsonUtils.getGson().fromJson(userStoryEntity.getReleases(), new TypeToken<ArrayList<UTRelease>>(){}.getType());

            epicIds.addAll(utEpics.stream().map(epic -> epic.getId()).collect(Collectors.toList()));
            releaseIds.addAll(utReleases.stream().map(release -> release.getId()).collect(Collectors.toList()));

            if(!CollectionUtils.isEmpty(releaseIds)) {
                releaseEntities = releaseService.getRelease(releaseIds);
                featureEntities = featureService.getFeature(releaseIds);
            }
        } catch (ReleaseNotFoundException | FeatureNotFoundException e) {
        }
        if(!CollectionUtils.isEmpty(epicIds)) {
            try {
                epicEntities = releaseService.getEpic(epicIds);
            } catch (EpicNotFoundException e) {
            }
        }
        return RepresentationBuilder.buildUserStoryRep(userStoryEntity, releaseEntities, epicEntities, featureEntities);
    }

    private CsDevRepresentation getListUserStory(Long productId, UserStoryState state) throws DBServiceException {
        List<UserStoryEntity> userStoryEntities = new ArrayList<>();
        try {
            userStoryEntities = featureService.getUserStory(
                    UserStoryEntity.builder()
                            .productId(productId)
                            .state(state != null ? state.ordinal() : null )
                            .build()
            );
        } catch (UserStoryNotFoundException e) {
        }
        return RepresentationBuilder.buildUserStoryRep(userStoryEntities);
    }

    public CsDevRepresentation getRequirement(Long featureId) throws DBServiceException {
        List<RequirementEntity> requirementEntities = null;
        try {
            requirementEntities = featureService.getRequirement(
                    RequirementEntity.builder()
                            .featureId(featureId)
                            .build()
            );
        } catch (RequirementNotFoundException e) {
        }
        return RepresentationBuilder.buildListRequirement(requirementEntities);
    }

    private CsDevRepresentation getFeatures(Long productId) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = new ArrayList<>();
        List<FeatureEntity> featureEntities = new ArrayList<>();
        List<ReleaseLayoutEntity> releaseLayouts = new ArrayList<>();
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .productId(productId)
                            .build()
            );
            List<Long> releaseIds = releaseEntities.stream().map(release -> release.getId()).collect(Collectors.toList());
            featureEntities = featureService.getFeature(releaseIds);
            releaseLayouts = releaseService.getReleaseLayout(
                    ReleaseLayoutEntity.builder()
                            .productId(productId)
                            .type(ReleaseLayoutType.FEATURE)
                            .build()
            );

        }catch (ReleaseNotFoundException | FeatureNotFoundException | ReleaseLayoutNotFoundException e) {
        }
        return RepresentationBuilder.buildListFeatureRep(featureEntities, releaseEntities, releaseLayouts);
    }
}
