package com.tnd.pw.development.release.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleaseLayoutEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import com.tnd.pw.development.release.exception.*;

import java.io.IOException;
import java.util.List;

public interface ReleaseService {
    ReleaseEntity createRelease(ReleaseEntity entity) throws DBServiceException;
    ReleaseEntity createParkingLotFeature(ReleaseEntity entity) throws DBServiceException;
    ReleaseEntity createParkingLotEpic(ReleaseEntity entity) throws DBServiceException;
    List<ReleaseEntity> getRelease(ReleaseEntity entity) throws DBServiceException, ReleaseNotFoundException;
    List<ReleaseEntity> getRelease(List<Long> releaseIds) throws DBServiceException, ReleaseNotFoundException;
    void updateRelease(ReleaseEntity entity) throws DBServiceException, UnableUpdateParkingLotException;
    void removeRelease(ReleaseEntity entity) throws DBServiceException;

    ReleasePhaseEntity createReleasePhase(ReleasePhaseEntity entity) throws DBServiceException;
    List<ReleasePhaseEntity> getReleasePhase(ReleasePhaseEntity entity) throws DBServiceException, ReleasePhaseNotFoundException;
    void updateReleasePhase(ReleasePhaseEntity entity) throws DBServiceException;
    void removeReleasePhase(ReleasePhaseEntity entity) throws DBServiceException;

    EpicEntity createEpic(EpicEntity entity) throws DBServiceException;
    List<EpicEntity> getEpic(EpicEntity entity) throws DBServiceException, EpicNotFoundException;
    List<EpicEntity> getEpic(List<Long> epicIds) throws DBServiceException, EpicNotFoundException;
    void updateEpic(EpicEntity entity) throws DBServiceException;
    void removeEpic(EpicEntity entity) throws DBServiceException;

    void createReleaseLayout(ReleaseLayoutEntity entity) throws DBServiceException;
    List<ReleaseLayoutEntity> getReleaseLayout(ReleaseLayoutEntity entity) throws DBServiceException, ReleaseLayoutNotFoundException;
    void updateReleaseLayout(ReleaseLayoutEntity entity) throws DBServiceException;
}
