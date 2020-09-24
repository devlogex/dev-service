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
            "INSERT INTO feature(id, product_id, name, state, release_id, assign_to, initiative_id, goals, " +
                    "epic_id, requirements, description, files, created_at, created_by) " +
                    "values(%d, %d, '%s', %d, %d, %d, %d, '%s', %d, '%s', '%s', '%s', %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM feature WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM feature WHERE product_id = %d";
    private static final String SQL_SELECT_BY_RELEASE_ID =
            "SELECT * FROM feature WHERE release_id = %d";
    private static final String SQL_UPDATE =
            "UPDATE feature SET name = '%s', state = %d, release_id = %d, initiative_id = %d, goals = '%s', " +
                    "assign_to = %d, epic_id = %d, requirements = '%s', description = '%s', files = '%s' " +
                    "WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM feature WHERE id = %d";

    @Override
    public void create(FeatureEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(), entity.getName(),
                entity.getState(), entity.getReleaseId(), entity.getAssignTo(),entity.getInitiativeId(),
                entity.getGoals(), entity.getEpicId(), entity.getRequirements(), entity.getDescription(),
                entity.getFiles(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<FeatureEntity> get(FeatureEntity entity) throws IOException, DBServiceException, FeatureNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getProductId() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
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
    public void update(FeatureEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getState(),entity.getReleaseId(),
                entity.getInitiativeId(),entity.getGoals(), entity.getAssignTo(), entity.getEpicId(),
                entity.getRequirements(), entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(FeatureEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
