package com.tnd.pw.action.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.runner.service.ReleaseHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.EpicRep;
import com.tnd.pw.development.common.representations.ReleasePhaseRep;
import com.tnd.pw.development.common.representations.ReleaseRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.release.exception.EpicNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.ReleasePhaseNotFoundException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class ReleaseHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseHandler.class);

    @Autowired
    private ReleaseHandlerService releaseHandlerService;

    @HandlerService(path = "/development/release/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addRelease(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[ReleaseHandler] addRelease() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.addRelease(request);
        LOGGER.info("[ReleaseHandler] addRelease() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getRelease(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[ReleaseHandler] getRelease() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.getRelease(request);
        LOGGER.info("[ReleaseHandler] getRelease() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/info", protocol = "GET")
    public BaseResponse<ReleaseRep> getReleaseInfo(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException, CallApiFailException {
        LOGGER.info("[ReleaseHandler] getReleaseInfo() - request: {}", GsonUtils.convertToString(request));
        ReleaseRep response = releaseHandlerService.getReleaseInfo(request);
        LOGGER.info("[ReleaseHandler] getReleaseInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/update", protocol = "POST")
    public BaseResponse<ReleaseRep> updateRelease(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException, CallApiFailException {
        LOGGER.info("[ReleaseHandler] updateRelease() - request: {}", GsonUtils.convertToString(request));
        ReleaseRep response = releaseHandlerService.updateRelease(request);
        LOGGER.info("[ReleaseHandler] updateRelease() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeRelease(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException {
        LOGGER.info("[ReleaseHandler] removeRelease() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.removeRelease(request);
        LOGGER.info("[ReleaseHandler] removeRelease() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/phase/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addReleasePhase(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException {
        LOGGER.info("[ReleaseHandler] addReleasePhase() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.addReleasePhase(request);
        LOGGER.info("[ReleaseHandler] addReleasePhase() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/phase/info", protocol = "GET")
    public BaseResponse<ReleasePhaseRep> getReleasePhaseInfo(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException, CallApiFailException {
        LOGGER.info("[ReleaseHandler] getReleasePhaseInfo() - request: {}", GsonUtils.convertToString(request));
        ReleasePhaseRep response = releaseHandlerService.getReleasePhaseInfo(request);
        LOGGER.info("[ReleaseHandler] getReleasePhaseInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/phase", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getReleasePhase(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException, CallApiFailException {
        LOGGER.info("[ReleaseHandler] getReleasePhase() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.getReleasePhase(request);
        LOGGER.info("[ReleaseHandler] getReleasePhase() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/phase/update", protocol = "POST")
    public BaseResponse<ReleasePhaseRep> updateReleasePhase(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException, CallApiFailException {
        LOGGER.info("[ReleaseHandler] updateReleasePhase() - request: {}", GsonUtils.convertToString(request));
        ReleasePhaseRep response = releaseHandlerService.updateReleasePhase(request);
        LOGGER.info("[ReleaseHandler] updateReleasePhase() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/phase/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeReleasePhase(DevRequest request) throws DBServiceException, IOException, ReleasePhaseNotFoundException {
        LOGGER.info("[ReleaseHandler] removeReleasePhase() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.removeReleasePhase(request);
        LOGGER.info("[ReleaseHandler] removeReleasePhase() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/epic/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addEpic(DevRequest request) throws DBServiceException, IOException, EpicNotFoundException {
        LOGGER.info("[ReleaseHandler] addEpic() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.addEpic(request);
        LOGGER.info("[ReleaseHandler] addEpic() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/epic", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getEpic(DevRequest request) throws IOException, DBServiceException {
        LOGGER.info("[ReleaseHandler] getEpic() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.getEpic(request);
        LOGGER.info("[ReleaseHandler] getEpic() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/epic/info", protocol = "GET")
    public BaseResponse<EpicRep> geEpicInfo(DevRequest request) throws DBServiceException, IOException, CallApiFailException, EpicNotFoundException {
        LOGGER.info("[ReleaseHandler] geEpicInfo() - request: {}", GsonUtils.convertToString(request));
        EpicRep response = releaseHandlerService.getEpicInfo(request);
        LOGGER.info("[ReleaseHandler] geEpicInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/epic/update", protocol = "POST")
    public BaseResponse<EpicRep> updateEpic(DevRequest request) throws DBServiceException, IOException, CallApiFailException, EpicNotFoundException {
        LOGGER.info("[ReleaseHandler] updateEpic() - request: {}", GsonUtils.convertToString(request));
        EpicRep response = releaseHandlerService.updateEpic(request);
        LOGGER.info("[ReleaseHandler] updateEpic() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/release/epic/remove", protocol = "POST")
    public BaseResponse<CsDevRepresentation> removeEpic(DevRequest request) throws DBServiceException, IOException, EpicNotFoundException {
        LOGGER.info("[ReleaseHandler] removeEpic() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = releaseHandlerService.removeEpic(request);
        LOGGER.info("[ReleaseHandler] removeEpic() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
