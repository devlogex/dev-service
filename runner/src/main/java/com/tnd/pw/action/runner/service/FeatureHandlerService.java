package com.tnd.pw.action.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.FeatureRep;
import com.tnd.pw.development.common.representations.RequirementRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;

import java.io.IOException;

public interface FeatureHandlerService {
    CsDevRepresentation addFeature(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException, FeatureNotFoundException;

    CsDevRepresentation updateFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException, IOException;

    CsDevRepresentation getFeature(DevRequest request) throws IOException, DBServiceException;

    FeatureRep getFeatureInfo(DevRequest request) throws DBServiceException, FeatureNotFoundException, IOException, CallApiFailException;

    CsDevRepresentation removeFeature(DevRequest request) throws IOException, DBServiceException, FeatureNotFoundException;

    CsDevRepresentation addRequirement(DevRequest request) throws IOException, DBServiceException;

    CsDevRepresentation getRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException, IOException;

    RequirementRep getRequirementInfo(DevRequest request) throws DBServiceException, RequirementNotFoundException, IOException;

    CsDevRepresentation updateRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException, IOException;

    CsDevRepresentation removeRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException, IOException;
}
