package com.tnd.pw.development.idea.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.idea.entity.IdeaEntity;
import com.tnd.pw.development.idea.exception.IdeaNotFoundException;

import java.util.List;

public interface IdeaDao {
    void create(IdeaEntity entity) throws DBServiceException;
    List<IdeaEntity> get(IdeaEntity entity) throws DBServiceException, IdeaNotFoundException;
    void update(IdeaEntity entity) throws DBServiceException;
    void remove(IdeaEntity entity) throws DBServiceException;
}
