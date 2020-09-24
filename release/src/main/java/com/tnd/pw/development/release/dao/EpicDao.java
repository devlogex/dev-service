package com.tnd.pw.development.release.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.exception.EpicNotFoundException;

import java.io.IOException;
import java.util.List;

public interface EpicDao {
    void create(EpicEntity entity) throws IOException, DBServiceException;
    List<EpicEntity> get(EpicEntity entity) throws IOException, DBServiceException, EpicNotFoundException;
    void update(EpicEntity entity) throws IOException, DBServiceException;
    void remove(EpicEntity entity) throws IOException, DBServiceException;
}
