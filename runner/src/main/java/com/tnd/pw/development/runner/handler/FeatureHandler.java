package com.tnd.pw.development.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.constants.Methods;
import com.tnd.pw.development.feature.exception.UserStoryNotFoundException;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.exception.InvalidDataException;
import com.tnd.pw.development.runner.service.FeatureHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.FeatureRep;
import com.tnd.pw.development.common.representations.RequirementRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseLayoutNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@HandlerServiceClass
public class FeatureHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseHandler.class);

    @Autowired
    private FeatureHandlerService featureHandlerService;

    @HandlerService(path = "/development/feature/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addFeature(DevRequest request) throws FeatureNotFoundException, DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[FeatureHandler] addFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.addFeature(request);
        LOGGER.info("[FeatureHandler] addFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateFeature(DevRequest request) throws FeatureNotFoundException, DBServiceException, ReleaseLayoutNotFoundException {
        LOGGER.info("[FeatureHandler] updateFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.updateFeature(request);
        LOGGER.info("[FeatureHandler] updateFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/layout/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateFeatureLayout(DevRequest request) throws FeatureNotFoundException, DBServiceException, ReleaseLayoutNotFoundException, InvalidDataException {
        LOGGER.info("[FeatureHandler] updateFeatureLayout() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.updateFeatureLayout(request);
        LOGGER.info("[FeatureHandler] updateFeatureLayout() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/release/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateFeatureRelease(DevRequest request) throws FeatureNotFoundException, InvalidDataException, ReleaseLayoutNotFoundException, DBServiceException {
        LOGGER.info("[FeatureHandler] updateFeatureRelease() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.updateFeatureRelease(request);
        LOGGER.info("[FeatureHandler] updateFeatureRelease() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getFeature(DevRequest request) throws DBServiceException {
        LOGGER.info("[FeatureHandler] getFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.getFeature(request);
        LOGGER.info("[FeatureHandler] getFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(method = Methods.GET_FEATURES_BY_INITIATIVE_IDS)
    public BaseResponse<CsDevRepresentation> getFeatureByInitiativeIds(DevRequest request) throws DBServiceException {
        LOGGER.info("[FeatureHandler] getFeatureByInitiativeIds() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.getFeatureByInitiativeIds(request);
        LOGGER.info("[FeatureHandler] getFeatureByInitiativeIds() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/info", protocol = "GET")
    public BaseResponse<FeatureRep> getFeatureInfo(DevRequest request) throws DBServiceException, FeatureNotFoundException, ActionServiceFailedException {
        LOGGER.info("[FeatureHandler] getFeatureInfo() - request: {}", GsonUtils.convertToString(request));
        FeatureRep response = featureHandlerService.getFeatureInfo(request);
        LOGGER.info("[FeatureHandler] getFeatureInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException {
        LOGGER.info("[FeatureHandler] removeFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.removeFeature(request);
        LOGGER.info("[FeatureHandler] removeFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addRequirement(DevRequest request) throws DBServiceException {
        LOGGER.info("[FeatureHandler] addRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.addRequirement(request);
        LOGGER.info("[FeatureHandler] addRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException {
        LOGGER.info("[FeatureHandler] getRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.getRequirement(request);
        LOGGER.info("[FeatureHandler] getRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/info", protocol = "GET")
    public BaseResponse<RequirementRep> getRequirementInfo(DevRequest request) throws DBServiceException, RequirementNotFoundException, ActionServiceFailedException {
        LOGGER.info("[FeatureHandler] getRequirementInfo() - request: {}", GsonUtils.convertToString(request));
        RequirementRep response = featureHandlerService.getRequirementInfo(request);
        LOGGER.info("[FeatureHandler] getRequirementInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException {
        LOGGER.info("[FeatureHandler] updateRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.updateRequirement(request);
        LOGGER.info("[FeatureHandler] updateRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException {
        LOGGER.info("[FeatureHandler] removeRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.removeRequirement(request);
        LOGGER.info("[FeatureHandler] removeRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/user_story/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addUserStory(DevRequest request) throws DBServiceException {
        LOGGER.info("[FeatureHandler] addUserStory() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.addUserStory(request);
        LOGGER.info("[FeatureHandler] addUserStory() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/user_story", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getUserStory(DevRequest request) throws DBServiceException {
        LOGGER.info("[FeatureHandler] getUserStory() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.getUserStory(request);
        LOGGER.info("[FeatureHandler] getUserStory() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/user_story/info", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getUserStoryInfo(DevRequest request) throws DBServiceException {
        LOGGER.info("[FeatureHandler] getUserStoryInfo() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.getUserStoryInfo(request);
        LOGGER.info("[FeatureHandler] getUserStoryInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/user_story/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateUserStory(DevRequest request) throws DBServiceException, UserStoryNotFoundException, InvalidDataException {
        LOGGER.info("[FeatureHandler] updateUserStory() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.updateUserStory(request);
        LOGGER.info("[FeatureHandler] updateUserStory() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/user_story/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeUserStory(DevRequest request) throws UserStoryNotFoundException, DBServiceException {
        LOGGER.info("[FeatureHandler] removeUserStory() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.removeUserStory(request);
        LOGGER.info("[FeatureHandler] removeUserStory() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

}
