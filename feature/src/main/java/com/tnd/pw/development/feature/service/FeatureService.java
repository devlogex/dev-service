package com.tnd.pw.development.feature.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.feature.entity.UserStoryEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.feature.exception.UserStoryNotFoundException;

import java.io.IOException;
import java.util.List;

public interface FeatureService {
    FeatureEntity createFeature(FeatureEntity entity) throws DBServiceException;
    List<FeatureEntity> getFeature(FeatureEntity entity) throws DBServiceException, FeatureNotFoundException;
    List<FeatureEntity> getFeature(List<Long> releaseIds) throws DBServiceException, FeatureNotFoundException;
    void updateFeature(FeatureEntity entity) throws DBServiceException;
    void removeFeature(FeatureEntity entity) throws DBServiceException;

    RequirementEntity createRequirement(RequirementEntity entity) throws DBServiceException;
    List<RequirementEntity> getRequirement(RequirementEntity entity) throws DBServiceException, RequirementNotFoundException;
    void updateRequirement(RequirementEntity entity) throws DBServiceException;
    void removeRequirement(RequirementEntity entity) throws DBServiceException;

    UserStoryEntity createUserStory(UserStoryEntity entity) throws DBServiceException;
    List<UserStoryEntity> getUserStory(UserStoryEntity entity) throws DBServiceException, UserStoryNotFoundException;
    void updateUserStory(UserStoryEntity entity) throws DBServiceException;
    void removeUserStory(UserStoryEntity entity) throws DBServiceException;
}
