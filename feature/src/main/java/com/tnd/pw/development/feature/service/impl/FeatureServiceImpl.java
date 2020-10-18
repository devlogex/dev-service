package com.tnd.pw.development.feature.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.constants.FeatureState;
import com.tnd.pw.development.feature.constants.RequirementState;
import com.tnd.pw.development.feature.dao.FeatureDao;
import com.tnd.pw.development.feature.dao.RequirementDao;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class FeatureServiceImpl implements FeatureService {
    @Autowired
    private FeatureDao featureDao;
    @Autowired
    private RequirementDao requirementDao;

    @Override
    public FeatureEntity createFeature(FeatureEntity entity) throws DBServiceException {
        entity.setId(GenUID.genIdByProduct(entity.getProductId()));
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(FeatureState.UNDER_CONSIDERATION.ordinal());
        entity.setDescription(entity.getDescription() != null ? entity.getDescription() : "");
        entity.setFiles(entity.getFiles() != null ? entity.getFiles() : "");
        featureDao.create(entity);
        return entity;
    }

    @Override
    public List<FeatureEntity> getFeature(FeatureEntity entity) throws DBServiceException, FeatureNotFoundException {
        return featureDao.get(entity);
    }

    @Override
    public List<FeatureEntity> getFeature(List<Long> releaseIds) throws DBServiceException, FeatureNotFoundException {
        return featureDao.get(releaseIds);
    }

    @Override
    public void updateFeature(FeatureEntity entity) throws DBServiceException {
        featureDao.update(entity);
    }

    @Override
    public void removeFeature(FeatureEntity entity) throws DBServiceException {
        featureDao.remove(entity);
    }

    @Override
    public RequirementEntity createRequirement(RequirementEntity entity) throws DBServiceException {
        entity.setId(GenUID.genIdByParent(entity.getFeatureId()));
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(RequirementState.UNDER_CONSIDERATION.ordinal());
        requirementDao.create(entity);
        return entity;
    }

    @Override
    public List<RequirementEntity> getRequirement(RequirementEntity entity) throws DBServiceException, RequirementNotFoundException {
        return requirementDao.get(entity);
    }

    @Override
    public void updateRequirement(RequirementEntity entity) throws DBServiceException {
        requirementDao.update(entity);
    }

    @Override
    public void removeRequirement(RequirementEntity entity) throws DBServiceException {
        requirementDao.remove(entity);
    }
}
