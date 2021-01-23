package com.tnd.pw.development.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.release.exception.*;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.EpicRep;
import com.tnd.pw.development.common.representations.ReleasePhaseRep;
import com.tnd.pw.development.common.representations.ReleaseRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.runner.exception.InvalidDataException;


public interface ReleaseHandlerService {
    CsDevRepresentation addRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException;
    CsDevRepresentation addParkingLot(DevRequest request) throws DBServiceException;
    CsDevRepresentation getRelease(DevRequest request) throws DBServiceException;
    ReleaseRep getReleaseInfo(DevRequest request) throws DBServiceException, ReleaseNotFoundException, ActionServiceFailedException;
    ReleaseRep updateRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException, ActionServiceFailedException, UnableUpdateParkingLotException;
    CsDevRepresentation removeRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException;

    CsDevRepresentation addReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException;

    ReleasePhaseRep getReleasePhaseInfo(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException, ActionServiceFailedException;

    ReleasePhaseRep updateReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException, ActionServiceFailedException;

    CsDevRepresentation removeReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException;

    CsDevRepresentation addEpic(DevRequest request) throws DBServiceException, EpicNotFoundException;

    EpicRep getEpicInfo(DevRequest request) throws DBServiceException, EpicNotFoundException, ActionServiceFailedException;

    CsDevRepresentation updateEpic(DevRequest request) throws DBServiceException, EpicNotFoundException, ActionServiceFailedException, ReleaseLayoutNotFoundException;

    CsDevRepresentation removeEpic(DevRequest request) throws DBServiceException, EpicNotFoundException;

    CsDevRepresentation getEpic(DevRequest request) throws DBServiceException;

    CsDevRepresentation getReleasePhase(DevRequest request) throws DBServiceException, ReleasePhaseNotFoundException;

    CsDevRepresentation updateEpicLayout(DevRequest request) throws DBServiceException, ReleaseLayoutNotFoundException, InvalidDataException;

    CsDevRepresentation updateEpicRelease(DevRequest request) throws DBServiceException, EpicNotFoundException, ReleaseLayoutNotFoundException, InvalidDataException;

    CsDevRepresentation getReleaseByProductId(DevRequest request) throws DBServiceException;
}
