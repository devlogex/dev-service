package com.tnd.pw.development.release.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.entity.ReleaseLayoutEntity;
import com.tnd.pw.development.release.exception.ReleaseLayoutNotFoundException;

import java.util.List;

public interface ReleaseLayoutDao {
    void create(ReleaseLayoutEntity entity) throws DBServiceException;
    List<ReleaseLayoutEntity> get(ReleaseLayoutEntity entity) throws DBServiceException, ReleaseLayoutNotFoundException;
    void update(ReleaseLayoutEntity entity) throws DBServiceException;
}
