package com.tnd.pw.development.feature.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.entity.UserStoryEntity;
import com.tnd.pw.development.feature.exception.UserStoryNotFoundException;

import java.util.List;

public interface UserStoryDao {
    void create(UserStoryEntity entity) throws DBServiceException;
    List<UserStoryEntity> get(UserStoryEntity entity) throws DBServiceException, UserStoryNotFoundException;
    void update(UserStoryEntity entity) throws DBServiceException;
    void remove(UserStoryEntity entity) throws DBServiceException;
}
