package com.tnd.pw.development.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.constants.Methods;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.UnableUpdateParkingLotException;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.service.DevHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@HandlerServiceClass
public class DevHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevHandler.class);

    @Autowired
    private DevHandlerService devHandlerService;

    @HandlerService(path = "/", protocol = "GET")
    public BaseResponse<CsDevRepresentation> check(BaseRequest request) {
        return new BaseResponse<>(null);
    }

    @HandlerService(method = Methods.STATISTIC_DEV)
    public BaseResponse<CsDevRepresentation> statisticDev(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticDev() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = devHandlerService.statisticDev(request);
        LOGGER.info("[DevHandler] statisticDev() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(method = Methods.CALCULATE_DEV)
    public BaseResponse<CsDevRepresentation> calculateDev(DevRequest request) throws DBServiceException, ReleaseNotFoundException, FeatureNotFoundException, ActionServiceFailedException, UnableUpdateParkingLotException {
        LOGGER.info("[DevHandler] calculateDev() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = devHandlerService.calculateFeature(request);
        LOGGER.info("[DevHandler] calculateDev() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/statistic/goal", protocol = "GET")
    public BaseResponse<CsDevRepresentation> statisticDevByGoal(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticDevByGoal() - request: {}", GsonUtils.convertToString(request));
        request.setGoals(Arrays.asList(request.getId()));
        request.setId(null);
        CsDevRepresentation response = devHandlerService.statisticDev(request);
        LOGGER.info("[DevHandler] statisticDevByGoal() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/statistic/release/goal", protocol = "GET")
    public BaseResponse<CsDevRepresentation> statisticReleaseByGoal(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticReleaseByGoal() - request: {}", GsonUtils.convertToString(request));
        request.setGoals(Arrays.asList(request.getId()));
        request.setId(null);
        CsDevRepresentation response = devHandlerService.statisticRelease(request);
        LOGGER.info("[DevHandler] statisticReleaseByGoal() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/statistic/feature/goal", protocol = "GET")
    public BaseResponse<CsDevRepresentation> statisticFeatureByGoal(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticFeatureByGoal() - request: {}", GsonUtils.convertToString(request));
        request.setGoals(Arrays.asList(request.getId()));
        request.setId(null);
        CsDevRepresentation response = devHandlerService.statisticFeature(request);
        LOGGER.info("[DevHandler] statisticFeatureByGoal() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/statistic/initiative", protocol = "GET")
    public BaseResponse<CsDevRepresentation> statisticDevByInitiative(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticDevByInitiative() - request: {}", GsonUtils.convertToString(request));
        request.setInitiatives(Arrays.asList(request.getId()));
        request.setId(null);
        CsDevRepresentation response = devHandlerService.statisticDev(request);
        LOGGER.info("[DevHandler] statisticDevByInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/statistic/release/initiative", protocol = "GET")
    public BaseResponse<CsDevRepresentation> statisticReleaseByInitiative(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticReleaseByInitiative() - request: {}", GsonUtils.convertToString(request));
        request.setInitiatives(Arrays.asList(request.getId()));
        request.setId(null);
        CsDevRepresentation response = devHandlerService.statisticRelease(request);
        LOGGER.info("[DevHandler] statisticReleaseByInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/statistic/feature/initiative", protocol = "GET")
    public BaseResponse<CsDevRepresentation> statisticFeatureByInitiative(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticFeatureByInitiative() - request: {}", GsonUtils.convertToString(request));
        request.setInitiativeId(request.getId());
        request.setId(null);
        CsDevRepresentation response = devHandlerService.statisticFeature(request);
        LOGGER.info("[DevHandler] statisticFeatureByInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/statistic/product", protocol = "GET")
    public BaseResponse<CsDevRepresentation> statisticDevByProduct(DevRequest request) throws DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[DevHandler] statisticDevByProduct() - request: {}", GsonUtils.convertToString(request));
        request.setProductId(request.getId());
        request.setId(null);
        CsDevRepresentation response = devHandlerService.statisticDev(request);
        LOGGER.info("[DevHandler] statisticDevByProduct() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
