package com.tnd.pw.development.release.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ReleasePhaseDao {
    void create(ReleasePhaseEntity entity) throws DBServiceException;
    List<ReleasePhaseEntity> get(ReleasePhaseEntity entity) throws DBServiceException, ReleasePhaseNotFoundException;
    void update(ReleasePhaseEntity entity) throws DBServiceException;
    void remove(ReleasePhaseEntity entity) throws DBServiceException;
}
