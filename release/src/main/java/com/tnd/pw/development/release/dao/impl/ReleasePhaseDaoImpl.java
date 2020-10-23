package com.tnd.pw.development.release.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.release.dao.ReleaseDao;
import com.tnd.pw.development.release.dao.ReleasePhaseDao;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class ReleasePhaseDaoImpl implements ReleasePhaseDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO release_phase(id, release_id, name, type, color, date) " +
                    "values(%d, %d, '%s', %d, '%s', '%s')";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM release_phase WHERE id = %d";
    private static final String SQL_SELECT_BY_RELEASE_ID =
            "SELECT * FROM release_phase WHERE release_id = %d";
    private static final String SQL_UPDATE =
            "UPDATE release_phase SET name = '%s', type = %d, date = '%S', color = '%s', description = '%s', " +
                    "files = '%s' WHERE id = %d";
    private static final String SQL_DELETE_BY_ID =
            "DELETE FROM release_phase WHERE id = %d";
    private static final String SQL_DELETE_BY_RELEASE_ID =
            "DELETE FROM release_phase WHERE release_id = %d";

    @Override
    public void create(ReleasePhaseEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getReleaseId(), entity.getName(),
                entity.getType(), entity.getColor(), entity.getDate());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<ReleasePhaseEntity> get(ReleasePhaseEntity entity) throws DBServiceException, ReleasePhaseNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getReleaseId()!= null) {
            query = String.format(SQL_SELECT_BY_RELEASE_ID, entity.getReleaseId());
        }
        List<ReleasePhaseEntity> entities = dataHelper.querySQL(query, ReleasePhaseEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new ReleasePhaseNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(ReleasePhaseEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getType(),entity.getDate(),
                entity.getColor(),entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(ReleasePhaseEntity entity) throws DBServiceException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_DELETE_BY_ID, entity.getId());
        }
        else if(entity.getReleaseId()!= null) {
            query = String.format(SQL_DELETE_BY_RELEASE_ID, entity.getReleaseId());
        }
        dataHelper.executeSQL(query);
    }
}
