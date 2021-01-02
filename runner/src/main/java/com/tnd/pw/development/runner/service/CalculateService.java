package com.tnd.pw.development.runner.service;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.feature.constants.FeatureIsComplete;
import com.tnd.pw.development.feature.entity.*;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.UserStoryNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.UnableUpdateParkingLotException;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.development.runner.config.DevelopmentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateService {
    @Autowired
    private ReleaseService releaseService;
    @Autowired
    private FeatureService featureService;

    public void calculateRelease(FeatureEntity featureEntity) throws DBServiceException, FeatureNotFoundException, ReleaseNotFoundException, UnableUpdateParkingLotException {
        ReleaseEntity releaseEntity = releaseService.getRelease(
                ReleaseEntity.builder()
                        .id(featureEntity.getReleaseId())
                        .build()
        ).get(0);
        List<FeatureEntity> featureEntities = featureService.getFeature(
                FeatureEntity.builder()
                        .releaseId(releaseEntity.getId())
                        .build()
        );
        Integer processRelease = 0;
        Integer pendingFeatures = 0;
        Integer completeFeatures = 0;
        if(featureEntities != null) {
            long countCompletedFeature = featureEntities.stream().filter(feature -> feature.getIsComplete()== FeatureIsComplete.TRUE).count();
            processRelease = (int)countCompletedFeature * 100 /featureEntities.size();
            pendingFeatures = (int)(featureEntities.size() - countCompletedFeature);
            completeFeatures = (int)countCompletedFeature;
        }
        releaseEntity.setProcess(processRelease);
        releaseEntity.setPendingFeatures(pendingFeatures);
        releaseEntity.setCompletedFeatures(completeFeatures);
        releaseService.updateRelease(releaseEntity);
    }

    public void calculateDevHide(FeatureEntity featureEntity) {
        DevelopmentConfig.executor.execute(() -> {
            try {
                calculateRelease(featureEntity);
            } catch (DBServiceException | FeatureNotFoundException | ReleaseNotFoundException | UnableUpdateParkingLotException e) {
            }
        });
    }

    public void updateUserStoryAfterRemoveRelease(Long releaseId) throws DBServiceException {
        try {
            UserStoryEntity userStoryEntity = featureService.getUserStory(
                    UserStoryEntity.builder()
                            .releases(String.valueOf(releaseId))
                            .build()
            ).get(0);
            List<UTRelease> utReleases = GsonUtils.getGson().fromJson(
                    userStoryEntity.getReleases(),
                    new TypeToken<ArrayList<UTRelease>>() {
                    }.getType()
            );
            if(!CollectionUtils.isEmpty(utReleases)) {
                List<UTRelease> utReleasesFilter = utReleases.stream().filter(release -> release.getId().compareTo(releaseId) != 0).collect(Collectors.toList());
                if(utReleases.size() != utReleasesFilter.size()) {
                    userStoryEntity.setReleases(GsonUtils.convertToString(utReleasesFilter));
                    featureService.updateUserStory(userStoryEntity);
                }
            }
        } catch (UserStoryNotFoundException e) {
        }
    }

    public void updateUserStoryAfterRemoveEpic(Long epicId) throws DBServiceException {
        try {
            UserStoryEntity userStoryEntity = featureService.getUserStory(
                    UserStoryEntity.builder()
                            .epics(String.valueOf(epicId))
                            .build()
            ).get(0);
            List<UTEpic> utEpics = GsonUtils.getGson().fromJson(
                    userStoryEntity.getReleases(),
                    new TypeToken<ArrayList<UTEpic>>() {
                    }.getType()
            );
            if(!CollectionUtils.isEmpty(utEpics)) {
                List<UTEpic> utEpicsFilter = utEpics.stream().filter(epic -> epic.getId().compareTo(epicId) != 0).collect(Collectors.toList());
                if(utEpics.size() != utEpicsFilter.size()) {
                    userStoryEntity.setEpics(GsonUtils.convertToString(utEpicsFilter));
                    featureService.updateUserStory(userStoryEntity);
                }
            }
        } catch (UserStoryNotFoundException e) {
        }
    }

    public void updateUserStoryAfterRemoveFeature(Long featureId) throws DBServiceException {
        try {
            UserStoryEntity userStoryEntity = featureService.getUserStory(
                    UserStoryEntity.builder()
                            .releases(String.valueOf(featureId))
                            .build()
            ).get(0);
            List<UTRelease> utReleases = GsonUtils.getGson().fromJson(
                    userStoryEntity.getReleases(),
                    new TypeToken<ArrayList<UTRelease>>() {
                    }.getType()
            );
            if(!CollectionUtils.isEmpty(utReleases)) {
                OUTLOOK: for(int i = 0; i < utReleases.size(); i++) {
                    List<UTFeature> features = utReleases.get(i).getFeatures();
                    for(int j = 0; j < features.size(); j++ ) {
                        if(features.get(j).getId().compareTo(featureId) == 0) {
                            features.remove(j);
                            break OUTLOOK;
                        }
                    }
                }
                userStoryEntity.setReleases(GsonUtils.convertToString(utReleases));
                featureService.updateUserStory(userStoryEntity);
            }
        } catch (UserStoryNotFoundException e) {
        }
    }
}
