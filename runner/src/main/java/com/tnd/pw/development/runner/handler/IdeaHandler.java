package com.tnd.pw.development.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.IdeaRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.idea.exception.IdeaNotFoundException;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.service.IdeaHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@HandlerServiceClass
public class IdeaHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdeaHandler.class);

    @Autowired
    private IdeaHandlerService ideaHandlerService;

    @HandlerService(path = "/development/idea/add", protocol = "POST")
    public BaseResponse<CsDevRepresentation> addIdea(DevRequest request) throws DBServiceException {
        LOGGER.info("[IdeaHandler] addIdea() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = ideaHandlerService.addIdea(request);
        LOGGER.info("[IdeaHandler] addIdea() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/idea/update", protocol = "POST")
    public BaseResponse<CsDevRepresentation> updateIdea(DevRequest request) throws DBServiceException, IdeaNotFoundException {
        LOGGER.info("[IdeaHandler] updateIdea() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = ideaHandlerService.updateIdea(request);
        LOGGER.info("[IdeaHandler] updateIdea() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/idea", protocol = "GET")
    public BaseResponse<CsDevRepresentation> getIdea(DevRequest request) throws DBServiceException {
        LOGGER.info("[IdeaHandler] getIdea() - request: {}", GsonUtils.convertToString(request));
        CsDevRepresentation response = ideaHandlerService.getIdea(request);
        LOGGER.info("[IdeaHandler] getIdea() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/idea/info", protocol = "GET")
    public BaseResponse<IdeaRep> getIdeaInfo(DevRequest request) throws DBServiceException, ActionServiceFailedException, IdeaNotFoundException {
        LOGGER.info("[IdeaHandler] getIdeaInfo() - request: {}", GsonUtils.convertToString(request));
        IdeaRep response = ideaHandlerService.getIdeaInfo(request);
        LOGGER.info("[IdeaHandler] getIdeaInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/development/idea/vote", protocol = "POST")
    public BaseResponse<IdeaRep> voteIdea(DevRequest request) throws DBServiceException, ActionServiceFailedException, IdeaNotFoundException {
        LOGGER.info("[IdeaHandler] voteIdea() - request: {}", GsonUtils.convertToString(request));
        IdeaRep response = ideaHandlerService.voteIdea(request);
        LOGGER.info("[IdeaHandler] voteIdea() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
