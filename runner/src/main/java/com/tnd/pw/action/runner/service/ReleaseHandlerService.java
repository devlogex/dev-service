package com.tnd.pw.action.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.EpicRep;
import com.tnd.pw.development.common.representations.ReleasePhaseRep;
import com.tnd.pw.development.common.representations.ReleaseRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;


public interface ReleaseHandlerService {
    CsDevRepresentation addRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException;
    CsDevRepresentation getRelease(DevRequest request) throws DBServiceException;
    ReleaseRep getReleaseInfo(DevRequest request) throws DBServiceException, ReleaseNotFoundException, ActionServiceFailedException;
    ReleaseRep updateRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException, ActionServiceFailedException;
    CsDevRepresentation removeRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException;

    CsDevRepresentation addReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException;

    ReleasePhaseRep getReleasePhaseInfo(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException, ActionServiceFailedException;

    ReleasePhaseRep updateReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException, ActionServiceFailedException;

    CsDevRepresentation removeReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException;

    CsDevRepresentation addEpic(DevRequest request) throws DBServiceException, EpicNotFoundException;

    EpicRep getEpicInfo(DevRequest request) throws DBServiceException, EpicNotFoundException, ActionServiceFailedException;

    EpicRep updateEpic(DevRequest request) throws DBServiceException, EpicNotFoundException, ActionServiceFailedException;

    CsDevRepresentation removeEpic(DevRequest request) throws DBServiceException, EpicNotFoundException;

    CsDevRepresentation getEpic(DevRequest request) throws DBServiceException;

    CsDevRepresentation getReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException;
}
