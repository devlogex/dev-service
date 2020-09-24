package com.tnd.pw.action.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;

import java.io.IOException;

public interface FeatureHandlerService {
    CsDevRepresentation addFeature(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException, FeatureNotFoundException;

    CsDevRepresentation updateFeature(DevRequest request);
}
