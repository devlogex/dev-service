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
            "INSERT INTO release(id, product_id, name, state, owner, theme, " +
                    "created_at, created_by, start_on, end_on, release_date, type) " +
                    "values(%d, %d, '%s', %d, %d, '%s', %d, %d, %d, %d, %d, '%s')";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM release WHERE id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_LIST_ID =
            "SELECT * FROM release WHERE id IN (%s) ORDER BY created_at";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM release WHERE product_id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_PRODUCT_ID_AND_TYPE =
            "SELECT * FROM release WHERE product_id = %d AND type = '%s' ORDER BY created_at";
    private static final String SQL_SELECT_BY_GOAL_ID =
            "SELECT * FROM release WHERE goals like '%%%s%%' ORDER BY created_at";
    private static final String SQL_SELECT_BY_GOAL_ID_AND_TYPE =
            "SELECT * FROM release WHERE goals like '%%%s%%' AND type = '%s' ORDER BY created_at";
    private static final String SQL_SELECT_BY_INITIATIVE_ID =
            "SELECT * FROM release WHERE initiatives like '%%%s%%' ORDER BY created_at";
    private static final String SQL_SELECT_BY_INITIATIVE_ID_AND_TYPE =
            "SELECT * FROM release WHERE initiatives like '%%%s%%' AND type = '%s' ORDER BY created_at";
    private static final String SQL_SELECT_BY_DURATION =
            "SELECT * FROM release WHERE ( %d BETWEEN start_on AND end_on ) AND ( %d BETWEEN start_on AND end_on ) ORDER BY created_at";
    private static final String SQL_UPDATE =
            "UPDATE release SET name = '%s', state = %d, owner = %d, initiatives = '%s', goals = '%s', " +
                    "start_on = %d, end_on = %d, days_to_release = %d, release_date = %d, develop_start_on = %d, " +
                    "theme = '%s', files = '%s', process = %d, pending_features = %d, completed_features = %d " +
                    "WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM release WHERE id = %d";


    @Override
    public void create(ReleaseEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(),
                entity.getName(), entity.getState(), entity.getOwner(), entity.getTheme(),
                entity.getCreatedAt(), entity.getCreatedBy(), entity.getStartOn(),
                entity.getEndOn(), entity.getReleaseDate(), entity.getType());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<ReleaseEntity> get(ReleaseEntity entity) throws DBServiceException, ReleaseNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getProductId() != null) {
            if(entity.getType() == null) {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
            } else {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_AND_TYPE, entity.getProductId(), entity.getType());
            }
        }
        else if(entity.getGoals() != null) {
            if(entity.getType() == null) {
                query = String.format(SQL_SELECT_BY_GOAL_ID, entity.getGoals());
            } else {
                query = String.format(SQL_SELECT_BY_GOAL_ID_AND_TYPE, entity.getGoals(), entity.getType());
            }
        }
        else if(entity.getInitiatives() != null) {
            if(entity.getType() == null) {
                query = String.format(SQL_SELECT_BY_INITIATIVE_ID, entity.getInitiatives());
            } else {
                query = String.format(SQL_SELECT_BY_INITIATIVE_ID_AND_TYPE, entity.getInitiatives(), entity.getType());
            }
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
    public List<ReleaseEntity> get(List<Long> ids) throws DBServiceException, ReleaseNotFoundException {
        String listId = "";
        for (int i=0;i<ids.size() - 1; i++) {
            listId += ids.get(i) + ",";
        }
        listId += ids.get(ids.size()-1);
        String query = String.format(SQL_SELECT_BY_LIST_ID, listId);
        List<ReleaseEntity> entities = dataHelper.querySQL(query, ReleaseEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new ReleaseNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(ReleaseEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getState(),entity.getOwner(),
                entity.getInitiatives(),entity.getGoals(), entity.getStartOn(), entity.getEndOn(),
                entity.getDaysToRelease(), entity.getReleaseDate(), entity.getDevelopStartOn(),
                entity.getTheme(), entity.getFiles(), entity.getProcess(),
                entity.getPendingFeatures(), entity.getCompletedFeatures(),
                entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(ReleaseEntity entity) throws DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
