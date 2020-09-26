package com.tnd.pw.action.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.runner.service.FeatureHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.FeatureRep;
import com.tnd.pw.development.common.representations.RequirementRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class FeatureHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseHandler.class);

    @Autowired
    private FeatureHandlerService featureHandlerService;

    @HandlerService(path = "/development/feature/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addFeature(DevRequest request) throws FeatureNotFoundException, DBServiceException, ReleaseNotFoundException, IOException {
        LOGGER.info("[FeatureHandler] addFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.addFeature(request);
        LOGGER.info("[FeatureHandler] addFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateFeature(DevRequest request) throws FeatureNotFoundException, DBServiceException, ReleaseNotFoundException, IOException {
        LOGGER.info("[FeatureHandler] updateFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.updateFeature(request);
        LOGGER.info("[FeatureHandler] updateFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getFeature(DevRequest request) throws DBServiceException, IOException {
        LOGGER.info("[FeatureHandler] getFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.getFeature(request);
        LOGGER.info("[FeatureHandler] getFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/info", protocol = "GET")
    public BaseResponse<FeatureRep> getFeatureInfo(DevRequest request) throws DBServiceException, IOException, FeatureNotFoundException, CallApiFailException {
        LOGGER.info("[FeatureHandler] getFeatureInfo() - request: {}", GsonUtils.convertToString(request));
        FeatureRep response = featureHandlerService.getFeatureInfo(request);
        LOGGER.info("[FeatureHandler] getFeatureInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeFeature(DevRequest request) throws DBServiceException, IOException, FeatureNotFoundException {
        LOGGER.info("[FeatureHandler] removeFeature() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.removeFeature(request);
        LOGGER.info("[FeatureHandler] removeFeature() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addRequirement(DevRequest request) throws DBServiceException, IOException, FeatureNotFoundException {
        LOGGER.info("[FeatureHandler] addRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.addRequirement(request);
        LOGGER.info("[FeatureHandler] addRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getRequirement(DevRequest request) throws DBServiceException, IOException, FeatureNotFoundException, RequirementNotFoundException {
        LOGGER.info("[FeatureHandler] getRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.getRequirement(request);
        LOGGER.info("[FeatureHandler] getRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/info", protocol = "GET")
    public BaseResponse<RequirementRep> getRequirementInfo(DevRequest request) throws DBServiceException, IOException, RequirementNotFoundException {
        LOGGER.info("[FeatureHandler] getRequirementInfo() - request: {}", GsonUtils.convertToString(request));
        RequirementRep response = featureHandlerService.getRequirementInfo(request);
        LOGGER.info("[FeatureHandler] getRequirementInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateRequirement(DevRequest request) throws DBServiceException, IOException, RequirementNotFoundException {
        LOGGER.info("[FeatureHandler] updateRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.updateRequirement(request);
        LOGGER.info("[FeatureHandler] updateRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/feature/requirement/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeRequirement(DevRequest request) throws DBServiceException, IOException, RequirementNotFoundException {
        LOGGER.info("[FeatureHandler] removeRequirement() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = featureHandlerService.removeRequirement(request);
        LOGGER.info("[FeatureHandler] removeRequirement() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
