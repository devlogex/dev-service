package com.tnd.pw.action.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.EpicRep;
import com.tnd.pw.development.common.representations.ReleasePhaseRep;
import com.tnd.pw.development.common.representations.ReleaseRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;

import java.io.IOException;

public interface ReleaseHandlerService {
    CsDevRepresentation addRelease(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException;
    CsDevRepresentation getRelease(DevRequest request) throws IOException, DBServiceException;
    ReleaseRep getReleaseInfo(DevRequest request) throws DBServiceException, ReleaseNotFoundException, IOException, CallApiFailException;
    ReleaseRep updateRelease(DevRequest request) throws DBServiceException, ReleaseNotFoundException, IOException, CallApiFailException;
    CsDevRepresentation removeRelease(DevRequest request) throws IOException, DBServiceException;

    CsDevRepresentation addReleasePhase(DevRequest request) throws IOException, DBServiceException, ReleasePhaseNotFoundException;

    ReleasePhaseRep getReleasePhaseInfo(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException, CallApiFailException;

    ReleasePhaseRep updateReleasePhase(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException, CallApiFailException;

    ReleasePhaseRep removeReleasePhase(DevRequest request) throws DBServiceException, IOException;

    CsDevRepresentation addEpic(DevRequest request) throws IOException, DBServiceException, EpicNotFoundException;

    EpicRep getEpicInfo(DevRequest request) throws DBServiceException, EpicNotFoundException, IOException, CallApiFailException;

    EpicRep updateEpic(DevRequest request) throws DBServiceException, EpicNotFoundException, IOException, CallApiFailException;

    CsDevRepresentation removeEpic(DevRequest request) throws IOException, DBServiceException;

    CsDevRepresentation getEpic(DevRequest request) throws IOException, DBServiceException;
}
