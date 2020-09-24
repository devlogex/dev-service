package com.tnd.pw.development.feature.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;

import java.io.IOException;
import java.util.List;

public interface FeatureService {
    FeatureEntity createFeature(FeatureEntity entity) throws IOException, DBServiceException;
    List<FeatureEntity> getFeature(FeatureEntity entity) throws IOException, DBServiceException, FeatureNotFoundException;
    void updateFeature(FeatureEntity entity) throws IOException, DBServiceException;
    void removeFeature(FeatureEntity entity) throws IOException, DBServiceException;

    RequirementEntity createRequirement(RequirementEntity entity) throws IOException, DBServiceException;
    List<RequirementEntity> getRequirement(RequirementEntity entity) throws IOException, DBServiceException, RequirementNotFoundException;
    void updateRequirement(RequirementEntity entity) throws IOException, DBServiceException;
    void removeRequirement(RequirementEntity entity) throws IOException, DBServiceException;
}
