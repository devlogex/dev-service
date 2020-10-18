package com.tnd.pw.development.release.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.constants.EpicState;
import com.tnd.pw.development.release.constants.ReleaseState;
import com.tnd.pw.development.release.dao.EpicDao;
import com.tnd.pw.development.release.dao.ReleaseDao;
import com.tnd.pw.development.release.dao.ReleasePhaseDao;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;
import com.tnd.pw.development.release.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class ReleaseServiceImpl implements ReleaseService {
    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private ReleasePhaseDao releasePhaseDao;
    @Autowired
    private EpicDao epicDao;

    @Override
    public ReleaseEntity createRelease(ReleaseEntity entity) throws DBServiceException {
        entity.setId(GenUID.genIdByProduct(entity.getProductId()));
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(ReleaseState.UNDER_CONSIDERATION.ordinal());
        entity.setTheme(entity.getTheme() != null ? entity.getTheme() : "");
        releaseDao.create(entity);
        return entity;
    }

    @Override
    public List<ReleaseEntity> getRelease(ReleaseEntity entity) throws DBServiceException, ReleaseNotFoundException {
        return releaseDao.get(entity);
    }

    @Override
    public void updateRelease(ReleaseEntity entity) throws DBServiceException {
        releaseDao.update(entity);
    }

    @Override
    public void removeRelease(ReleaseEntity entity) throws DBServiceException {
        releaseDao.remove(entity);
    }

    @Override
    public ReleasePhaseEntity createReleasePhase(ReleasePhaseEntity entity) throws DBServiceException {
        entity.setId(GenUID.genIdByParent(entity.getReleaseId()));
        entity.setColor("#E24D4D");
        entity.setDate(entity.getDate() != null ? entity.getDate() : "");
        releasePhaseDao.create(entity);
        return entity;
    }

    @Override
    public List<ReleasePhaseEntity> getReleasePhase(ReleasePhaseEntity entity) throws DBServiceException, ReleasePhaseNotFoundException {
        return releasePhaseDao.get(entity);
    }

    @Override
    public void updateReleasePhase(ReleasePhaseEntity entity) throws DBServiceException {
        releasePhaseDao.update(entity);
    }

    @Override
    public void removeReleasePhase(ReleasePhaseEntity entity) throws DBServiceException {
        releasePhaseDao.remove(entity);
    }

    @Override
    public EpicEntity createEpic(EpicEntity entity) throws DBServiceException {
        entity.setId(GenUID.genIdByProduct(entity.getProductId()));
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(EpicState.UNDER_CONSIDERATION.ordinal());
        entity.setDescription(entity.getDescription() != null ? entity.getDescription() : "");
        entity.setFiles(entity.getFiles() != null ? entity.getFiles() : "");
        epicDao.create(entity);
        return entity;
    }

    @Override
    public List<EpicEntity> getEpic(EpicEntity entity) throws DBServiceException, EpicNotFoundException {
        return epicDao.get(entity);
    }

    @Override
    public void updateEpic(EpicEntity entity) throws DBServiceException {
        epicDao.update(entity);
    }

    @Override
    public void removeEpic(EpicEntity entity) throws DBServiceException {
        epicDao.remove(entity);
    }
}
