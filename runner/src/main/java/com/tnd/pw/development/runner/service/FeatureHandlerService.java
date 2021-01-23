package com.tnd.pw.development.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.exception.UserStoryNotFoundException;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.exception.InvalidDataException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.FeatureRep;
import com.tnd.pw.development.common.representations.RequirementRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseLayoutNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;

public interface FeatureHandlerService {
    CsDevRepresentation addFeature(DevRequest request) throws DBServiceException, ReleaseNotFoundException, FeatureNotFoundException;

    CsDevRepresentation updateFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException, ReleaseLayoutNotFoundException;

    CsDevRepresentation updateFeatureLayout(DevRequest request) throws DBServiceException, FeatureNotFoundException, ReleaseLayoutNotFoundException, InvalidDataException;

    CsDevRepresentation updateFeatureRelease(DevRequest request) throws DBServiceException, FeatureNotFoundException, InvalidDataException, ReleaseLayoutNotFoundException;

    CsDevRepresentation getFeature(DevRequest request) throws DBServiceException;

    FeatureRep getFeatureInfo(DevRequest request) throws DBServiceException, FeatureNotFoundException, ActionServiceFailedException;

    CsDevRepresentation removeFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException;

    CsDevRepresentation addRequirement(DevRequest request) throws DBServiceException;

    CsDevRepresentation getRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException;

    RequirementRep getRequirementInfo(DevRequest request) throws DBServiceException, RequirementNotFoundException, ActionServiceFailedException;

    CsDevRepresentation updateRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException;

    CsDevRepresentation removeRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException;

    CsDevRepresentation addUserStory(DevRequest request) throws DBServiceException;

    CsDevRepresentation getUserStory(DevRequest request) throws DBServiceException;

    CsDevRepresentation getUserStoryInfo(DevRequest request) throws DBServiceException;

    CsDevRepresentation updateUserStory(DevRequest request) throws DBServiceException, UserStoryNotFoundException, InvalidDataException;

    CsDevRepresentation removeUserStory(DevRequest request) throws DBServiceException, UserStoryNotFoundException;

    CsDevRepresentation getFeatureByInitiativeIds(DevRequest request) throws DBServiceException;
}
