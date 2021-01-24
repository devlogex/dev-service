package com.tnd.pw.development.idea.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.idea.dao.IdeaDao;
import com.tnd.pw.development.idea.entity.IdeaEntity;
import com.tnd.pw.development.idea.exception.IdeaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class IdeaDaoImpl implements IdeaDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO idea(id, product_id, workspace_id, name, state, content, files, " +
                    "vote, created_at, created_by) " +
                    "values(%d, %d, %d, '%s',  %d, '%s', '%s', '%s', %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM idea WHERE id = %d ORDER BY created_at DESC";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM idea WHERE product_id = %d ORDER BY created_at DESC";
    private static final String SQL_SELECT_BY_PRODUCT_ID_AND_STATE =
            "SELECT * FROM idea WHERE product_id = %d AND state = %d ORDER BY created_at DESC";
    private static final String SQL_SELECT_BY_WORKSPACE_ID =
            "SELECT * FROM idea WHERE workspace_id = %d ORDER BY created_at DESC";
    private static final String SQL_SELECT_BY_WORKSPACE_ID_AND_STATE =
            "SELECT * FROM idea WHERE workspace_id = %d AND state = %d ORDER BY created_at DESC";
    private static final String SQL_UPDATE =
            "UPDATE idea SET name = '%s', state = %d, content = '%s', " +
                    " files = '%s', vote = '%s' " +
                    "WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM idea WHERE id = %d";

    @Override
    public void create(IdeaEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(),
                entity.getWorkspaceId(), entity.getName(), entity.getState(), entity.getContent(),
                entity.getFiles(), entity.getVote(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<IdeaEntity> get(IdeaEntity entity) throws DBServiceException, IdeaNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getProductId() != null && entity.getState() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID_AND_STATE, entity.getProductId(), entity.getState());
        }
        else if(entity.getProductId() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
        }
        else if(entity.getState() != null && entity.getWorkspaceId() != null){
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID_AND_STATE, entity.getWorkspaceId(), entity.getState());
        }
        else {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID, entity.getWorkspaceId());
        }
        List<IdeaEntity> entities = dataHelper.querySQL(query, IdeaEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new IdeaNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(IdeaEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getState(),entity.getContent(),
                entity.getFiles(), entity.getVote(),entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(IdeaEntity entity) throws DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
