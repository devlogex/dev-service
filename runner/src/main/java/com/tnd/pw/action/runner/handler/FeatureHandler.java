package com.tnd.pw.action.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.runner.service.FeatureHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
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
}
