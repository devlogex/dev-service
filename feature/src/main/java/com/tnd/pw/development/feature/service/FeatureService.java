package com.tnd.pw.development.feature.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;

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
}
