package com.tnd.pw.development.feature.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.feature.dao.FeatureDao;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class FeatureDaoImpl implements FeatureDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO feature(id, product_id, name, type, state, release_id, " +
                    "description, files, created_at, created_by, start_on, end_on) " +
                    "values(%d, %d, '%s',  %d, %d, %d, '%s', '%s', %d, %d, %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM feature WHERE id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_LIST_RELEASE_ID =
            "SELECT * FROM feature WHERE release_id IN (%s) ORDER BY created_at";
    private static final String SQL_SELECT_BY_LIST_INITIATIVE_ID =
            "SELECT * FROM feature WHERE initiative_id IN (%s) ORDER BY created_at";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM feature WHERE product_id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_GOAL_ID =
            "SELECT * FROM feature WHERE goals LIKE '%%%s%%' ORDER BY created_at";
    private static final String SQL_SELECT_BY_INITIATIVE_ID =
            "SELECT * FROM feature WHERE initiative_id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_RELEASE_ID =
            "SELECT * FROM feature WHERE release_id = %d ORDER BY created_at";
    private static final String SQL_UPDATE =
                "UPDATE feature SET name = '%s', state = %d, release_id = %d, initiative_id = %d, goals = '%s', " +
                        "assign_to = %d, epic_id = %d, requirements = '%s', description = '%s', files = '%s', start_on = %d, " +
                        "end_on = %d, process = %d, is_complete = %d " +
                        "WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM feature WHERE id = %d";

    @Override
    public void create(FeatureEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(), entity.getName(),
                entity.getType(), entity.getState(), entity.getReleaseId(), entity.getDescription(),
                entity.getFiles(), entity.getCreatedAt(), entity.getCreatedBy(), entity.getStartOn(), entity.getEndOn());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<FeatureEntity> get(FeatureEntity entity) throws DBServiceException, FeatureNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getProductId() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
        }
        else if(entity.getGoals() != null) {
            query = String.format(SQL_SELECT_BY_GOAL_ID, entity.getGoals());
        }
        else if(entity.getInitiativeId() != null) {
            query = String.format(SQL_SELECT_BY_INITIATIVE_ID, entity.getInitiativeId());
        }
        else if(entity.getReleaseId() != null){
            query = String.format(SQL_SELECT_BY_RELEASE_ID, entity.getReleaseId());
        }
        List<FeatureEntity> entities = dataHelper.querySQL(query, FeatureEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new FeatureNotFoundException();
        }
        return entities;
    }

    @Override
    public List<FeatureEntity> get(List<Long> releaseIds) throws DBServiceException, FeatureNotFoundException {
        String listId = "";
        for (int i=0;i<releaseIds.size() - 1; i++) {
            listId += releaseIds.get(i) + ",";
        }
        listId += releaseIds.get(releaseIds.size()-1);
        String query = String.format(SQL_SELECT_BY_LIST_RELEASE_ID, listId);
        List<FeatureEntity> entities = dataHelper.querySQL(query, FeatureEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new FeatureNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(FeatureEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getState(),entity.getReleaseId(),
                entity.getInitiativeId(),entity.getGoals(), entity.getAssignTo(), entity.getEpicId(),
                entity.getRequirements(), entity.getDescription(), entity.getFiles(), entity.getStartOn(),
                entity.getEndOn(), entity.getProcess(), entity.getIsComplete(),
                entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(FeatureEntity entity) throws DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<FeatureEntity> getByInitiativeIds(List<Long> initiativeIds) throws DBServiceException, FeatureNotFoundException {
        String listId = "";
        for (int i=0;i<initiativeIds.size() - 1; i++) {
            listId += initiativeIds.get(i) + ",";
        }
        listId += initiativeIds.get(initiativeIds.size()-1);
        String query = String.format(SQL_SELECT_BY_LIST_INITIATIVE_ID, listId);
        List<FeatureEntity> entities = dataHelper.querySQL(query, FeatureEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new FeatureNotFoundException();
        }
        return entities;
    }
}
