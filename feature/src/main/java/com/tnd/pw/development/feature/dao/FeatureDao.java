package com.tnd.pw.development.feature.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;

import java.io.IOException;
import java.util.List;

public interface FeatureDao {
    void create(FeatureEntity entity) throws DBServiceException;
    List<FeatureEntity> get(FeatureEntity entity) throws DBServiceException, FeatureNotFoundException;
    List<FeatureEntity> get(List<Long> releaseIds) throws DBServiceException, FeatureNotFoundException;
    void update(FeatureEntity entity) throws DBServiceException;
    void remove(FeatureEntity entity) throws DBServiceException;
}
