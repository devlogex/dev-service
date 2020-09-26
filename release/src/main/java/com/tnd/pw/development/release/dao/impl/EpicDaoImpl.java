package com.tnd.pw.development.release.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.release.dao.EpicDao;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class EpicDaoImpl implements EpicDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO epic(id, product_id, name, state, release_id, " +
                    "description, files, created_at, created_by) " +
                    "values(%d, %d, '%s', %d, %d, '%s', '%s', %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM epic WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM epic WHERE product_id = %d";
    private static final String SQL_SELECT_BY_RELEASE_ID =
            "SELECT * FROM epic WHERE release_id = %d";
    private static final String SQL_UPDATE =
            "UPDATE epic SET name = '%s', state = %d, release_id = %d, initiative_id = %d, goals = '%s', " +
                    "assign_to = %d, description = '%s', files = '%s' " +
                    "WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM epic WHERE id = %d";

    @Override
    public void create(EpicEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(), entity.getName(),
                entity.getState(), entity.getReleaseId(), entity.getDescription(),
                entity.getFiles(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<EpicEntity> get(EpicEntity entity) throws IOException, DBServiceException, EpicNotFoundException {
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
        List<EpicEntity> entities = dataHelper.querySQL(query, EpicEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new EpicNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(EpicEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getState(),entity.getReleaseId(),
                entity.getInitiativeId(),entity.getGoals(), entity.getAssignTo(), entity.getDescription(),
                entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(EpicEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
