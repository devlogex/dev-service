package com.tnd.pw.development.feature.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.feature.dao.UserStoryDao;
import com.tnd.pw.development.feature.entity.UserStoryEntity;
import com.tnd.pw.development.feature.exception.UserStoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class UserStoryDaoImpl implements UserStoryDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO user_story(id, product_id, name, state, steps, " +
                    "epics, releases, created_at, created_by) " +
                    "values(%d, %d, '%s', %d, '%s', '%s', '%s', %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM user_story WHERE id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM user_story WHERE product_id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_PRODUCT_ID_AND_STATE =
            "SELECT * FROM user_story WHERE product_id = %d AND state = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_RELEASES =
            "SELECT * FROM user_story WHERE releases LIKE '%%%s%%' ORDER BY created_at";
    private static final String SQL_SELECT_BY_EPICS =
            "SELECT * FROM user_story WHERE epics LIKE '%%%s%%' ORDER BY created_at";
    private static final String SQL_UPDATE =
            "UPDATE user_story SET name = '%s', state = %d, steps = '%s', epics = '%s', " +
                    "releases = '%s', length = %d, personas = '%s' WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM user_story WHERE id = %d";

    @Override
    public void create(UserStoryEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(), entity.getName(),
                entity.getState(), entity.getSteps(), entity.getEpics(),
                entity.getReleases(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<UserStoryEntity> get(UserStoryEntity entity) throws DBServiceException, UserStoryNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getState() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID_AND_STATE, entity.getProductId(), entity.getState());
        }
        else if(entity.getProductId() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
        }
        else if(entity.getReleases() != null) {
            query = String.format(SQL_SELECT_BY_RELEASES, entity.getReleases());
        }
        else if(entity.getEpics() != null) {
            query = String.format(SQL_SELECT_BY_EPICS, entity.getEpics());
        }
        List<UserStoryEntity> entities = dataHelper.querySQL(query, UserStoryEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new UserStoryNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(UserStoryEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getState(),entity.getSteps(),
                entity.getEpics(),entity.getReleases(), entity.getLength(), entity.getPersonas(),
                entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(UserStoryEntity entity) throws DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
