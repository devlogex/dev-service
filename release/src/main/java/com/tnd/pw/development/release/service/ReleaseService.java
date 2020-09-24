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
    ReleaseEntity createRelease(ReleaseEntity entity) throws IOException, DBServiceException;
    List<ReleaseEntity> getRelease(ReleaseEntity entity) throws IOException, DBServiceException, ReleaseNotFoundException;
    void updateRelease(ReleaseEntity entity) throws IOException, DBServiceException;
    void removeRelease(ReleaseEntity entity) throws IOException, DBServiceException;

    ReleasePhaseEntity createReleasePhase(ReleasePhaseEntity entity) throws IOException, DBServiceException;
    List<ReleasePhaseEntity> getReleasePhase(ReleasePhaseEntity entity) throws IOException, DBServiceException, ReleasePhaseNotFoundException;
    void updateReleasePhase(ReleasePhaseEntity entity) throws IOException, DBServiceException;
    void removeReleasePhase(ReleasePhaseEntity entity) throws IOException, DBServiceException;

    EpicEntity createEpic(EpicEntity entity) throws IOException, DBServiceException;
    List<EpicEntity> getEpic(EpicEntity entity) throws IOException, DBServiceException, EpicNotFoundException;
    void updateEpic(EpicEntity entity) throws IOException, DBServiceException;
    void removeEpic(EpicEntity entity) throws IOException, DBServiceException;
}
