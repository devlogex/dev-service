package com.tnd.pw.development.release.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;

import java.io.IOException;
import java.util.List;

public interface EpicDao {
    void create(EpicEntity entity) throws DBServiceException;
    List<EpicEntity> get(EpicEntity entity) throws DBServiceException, EpicNotFoundException;
    List<EpicEntity> get(List<Long> ids) throws DBServiceException, EpicNotFoundException;
    void update(EpicEntity entity) throws DBServiceException;
    void remove(EpicEntity entity) throws DBServiceException;
}
