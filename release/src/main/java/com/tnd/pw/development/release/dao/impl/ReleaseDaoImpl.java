package com.tnd.pw.development.release.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.release.dao.ReleaseDao;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class ReleaseDaoImpl implements ReleaseDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO release(id, product_id, name, state, owner, initiative_id, goals, " +
                    "days_to_release, release_day, start_on, end_on, develop_start_on, " +
                    "theme, files, created_at, created_by) " +
                    "values(%d, %d, '%s', %d, %d, %d, %d, %d, %d, %d, %d, %d, '%s', '%s', %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM release WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM release WHERE product_id = %d";
    private static final String SQL_SELECT_BY_DURATION =
            "SELECT * FROM release WHERE ( %d BETWEEN start_on AND end_on ) AND ( %d BETWEEN start_on AND end_on )";
    private static final String SQL_UPDATE =
            "UPDATE release SET name = '%s', state = %d, owner = %d, initiative_id = %d, goals = '%s', " +
                    "start_on = %d, end_on = %d, days_to_release = %d, release_day = %d, develop_start_on = %d, " +
                    "theme = '%s', files = '%s' " +
                    "WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM release WHERE id = %d";


    @Override
    public void create(ReleaseEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(), entity.getName(),
                entity.getState(), entity.getOwner(), entity.getInitiativeId(),entity.getGoals(),
                entity.getDaysToRelease(), entity.getReleaseDate(), entity.getStartOn(), entity.getEndOn(),
                entity.getDevelopStartOn(), entity.getTheme(), entity.getFiles(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<ReleaseEntity> get(ReleaseEntity entity) throws IOException, DBServiceException, ReleaseNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getProductId() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
        }
        else if(entity.getStartOn() != null && entity.getEndOn() != null){
            query = String.format(SQL_SELECT_BY_DURATION, entity.getStartOn(), entity.getEndOn());
        }
        List<ReleaseEntity> entities = dataHelper.querySQL(query, ReleaseEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new ReleaseNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(ReleaseEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getState(),entity.getOwner(),
                entity.getInitiativeId(),entity.getGoals(), entity.getStartOn(), entity.getEndOn(),
                entity.getDaysToRelease(), entity.getReleaseDate(), entity.getDevelopStartOn(),
                entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(ReleaseEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
