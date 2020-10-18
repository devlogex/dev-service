package com.tnd.pw.development.feature.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;

import java.io.IOException;
import java.util.List;

public interface RequirementDao {
    void create(RequirementEntity entity) throws DBServiceException;
    List<RequirementEntity> get(RequirementEntity entity) throws DBServiceException, RequirementNotFoundException;
    void update(RequirementEntity entity) throws DBServiceException;
    void remove(RequirementEntity entity) throws DBServiceException;
}
