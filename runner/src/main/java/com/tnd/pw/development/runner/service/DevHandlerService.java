package com.tnd.pw.development.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.UnableUpdateParkingLotException;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;

public interface DevHandlerService {
    CsDevRepresentation statisticDev(DevRequest request) throws DBServiceException, ReleaseNotFoundException;
    CsDevRepresentation calculateFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException, ReleaseNotFoundException, ActionServiceFailedException, UnableUpdateParkingLotException;
    CsDevRepresentation statisticRelease(DevRequest request) throws DBServiceException;
    CsDevRepresentation statisticFeature(DevRequest request) throws DBServiceException;


}
