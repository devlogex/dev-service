package com.tnd.pw.development.feature.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.feature.dao.RequirementDao;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class RequirementDaoImpl implements RequirementDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO feature(id, feature_id, name, assign_to, description, files, created_at, created_by) " +
                    "values(%d, %d, '%s', %d, '%s','%s', %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM feature WHERE id = %d";
    private static final String SQL_SELECT_BY_FEATURE_ID =
            "SELECT * FROM feature WHERE feature_id = %d";
    private static final String SQL_UPDATE =
            "UPDATE feature SET name = '%s', assign_to = %d, description = '%s', files = '%s' " +
                    "WHERE id = %d";
    private static final String SQL_DELETE_BY_ID =
            "DELETE FROM feature WHERE id = %d";
    private static final String SQL_DELETE_BY_FEATURE_ID =
            "DELETE FROM feature WHERE feature_id = %d";


    @Override
    public void create(RequirementEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getFeatureId(), entity.getName(),
                entity.getAssignTo(), entity.getDescription(),entity.getFiles(), entity.getCreatedAt(),
                entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<RequirementEntity> get(RequirementEntity entity) throws IOException, DBServiceException, RequirementNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getFeatureId() != null) {
            query = String.format(SQL_SELECT_BY_FEATURE_ID, entity.getFeatureId());
        }
        List<RequirementEntity> entities = dataHelper.querySQL(query, RequirementEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new RequirementNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(RequirementEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getAssignTo(),
                entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(RequirementEntity entity) throws IOException, DBServiceException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_DELETE_BY_ID, entity.getId());
        } else if(entity.getFeatureId() != null) {
            query = String.format(SQL_DELETE_BY_FEATURE_ID, entity.getFeatureId());
        }
        dataHelper.executeSQL(query);
    }
}
