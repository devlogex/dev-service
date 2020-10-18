package com.tnd.pw.development.release.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ReleaseService {
    ReleaseEntity createRelease(ReleaseEntity entity) throws DBServiceException;
    List<ReleaseEntity> getRelease(ReleaseEntity entity) throws DBServiceException, ReleaseNotFoundException;
    void updateRelease(ReleaseEntity entity) throws DBServiceException;
    void removeRelease(ReleaseEntity entity) throws DBServiceException;

    ReleasePhaseEntity createReleasePhase(ReleasePhaseEntity entity) throws DBServiceException;
    List<ReleasePhaseEntity> getReleasePhase(ReleasePhaseEntity entity) throws DBServiceException, ReleasePhaseNotFoundException;
    void updateReleasePhase(ReleasePhaseEntity entity) throws DBServiceException;
    void removeReleasePhase(ReleasePhaseEntity entity) throws DBServiceException;

    EpicEntity createEpic(EpicEntity entity) throws DBServiceException;
    List<EpicEntity> getEpic(EpicEntity entity) throws DBServiceException, EpicNotFoundException;
    void updateEpic(EpicEntity entity) throws DBServiceException;
    void removeEpic(EpicEntity entity) throws DBServiceException;
}
