package com.tnd.pw.development.idea.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.idea.constants.IdeaState;
import com.tnd.pw.development.idea.dao.IdeaDao;
import com.tnd.pw.development.idea.entity.IdeaEntity;
import com.tnd.pw.development.idea.exception.IdeaNotFoundException;
import com.tnd.pw.development.idea.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IdeaServiceImpl implements IdeaService {
    @Autowired
    private IdeaDao ideaDao;

    @Override
    public IdeaEntity create(IdeaEntity entity) throws DBServiceException {
        entity.setId(GenUID.genIdByProduct(entity.getProductId()));
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(IdeaState.NEEDS_REVIEW.ordinal());
        ideaDao.create(entity);
        return entity;
    }

    @Override
    public List<IdeaEntity> get(IdeaEntity entity) throws DBServiceException, IdeaNotFoundException {
        return ideaDao.get(entity);
    }

    @Override
    public void update(IdeaEntity entity) throws DBServiceException {
        ideaDao.update(entity);
    }
}
