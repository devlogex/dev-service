package com.tnd.pw.development.feature.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;

import java.io.IOException;
import java.util.List;

public interface FeatureDao {
    void create(FeatureEntity entity) throws IOException, DBServiceException;
    List<FeatureEntity> get(FeatureEntity entity) throws IOException, DBServiceException, FeatureNotFoundException;
    void update(FeatureEntity entity) throws IOException, DBServiceException;
    void remove(FeatureEntity entity) throws IOException, DBServiceException;
}
