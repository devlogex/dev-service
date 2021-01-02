package com.tnd.pw.development.release.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.release.dao.ReleaseLayoutDao;
import com.tnd.pw.development.release.entity.ReleaseLayoutEntity;
import com.tnd.pw.development.release.exception.ReleaseLayoutNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ReleaseLayoutDaoImpl implements ReleaseLayoutDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO release_layout(id, release_id, product_id, type, layout) " +
                    "values(%d, %d, %d, '%s', '%s')";
    private static final String SQL_SELECT_BY_RELEASE_ID_AND_TYPE =
            "SELECT * FROM release_layout WHERE release_id = %d AND type = '%s'";
    private static final String SQL_SELECT_BY_PRODUCT_ID_AND_TYPE =
            "SELECT * FROM release_layout WHERE product_id = %d AND type = '%s'";
    private static final String SQL_UPDATE =
            "UPDATE release_layout SET layout = '%s' " +
                    "WHERE id = %d";


    @Override
    public void create(ReleaseLayoutEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getReleaseId(),
                entity.getProductId(), entity.getType(), entity.getLayout());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<ReleaseLayoutEntity> get(ReleaseLayoutEntity entity) throws DBServiceException, ReleaseLayoutNotFoundException {
        String query = "";
        if(entity.getType() != null) {
            if (entity.getReleaseId() != null) {
                query = String.format(SQL_SELECT_BY_RELEASE_ID_AND_TYPE, entity.getReleaseId(), entity.getType());
            } else if (entity.getProductId() != null) {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_AND_TYPE, entity.getProductId(), entity.getType());
            }
        }

        List<ReleaseLayoutEntity> entities = dataHelper.querySQL(query, ReleaseLayoutEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new ReleaseLayoutNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(ReleaseLayoutEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getLayout(),
                entity.getId());
        dataHelper.executeSQL(query);
    }
}
