package com.tnd.pw.development.release.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ReleaseDao {
    void create(ReleaseEntity entity) throws DBServiceException;
    List<ReleaseEntity> get(ReleaseEntity entity) throws DBServiceException, ReleaseNotFoundException;
    void update(ReleaseEntity entity) throws DBServiceException;
    void remove(ReleaseEntity entity) throws DBServiceException;
}
