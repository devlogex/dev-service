package com.tnd.pw.development.runner.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.IdeaRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.idea.constants.IdeaState;
import com.tnd.pw.development.idea.entity.IdeaEntity;
import com.tnd.pw.development.idea.exception.IdeaNotFoundException;
import com.tnd.pw.development.idea.service.IdeaService;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.handler.IdeaHandler;
import com.tnd.pw.development.runner.service.IdeaHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public class IdeaHandlerServiceImpl implements IdeaHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdeaHandlerServiceImpl.class);
    @Autowired
    private IdeaService ideaService;
    @Autowired
    private SdkService sdkService;

    @Override
    public CsDevRepresentation addIdea(DevRequest request) throws DBServiceException {
        ideaService.create(
                IdeaEntity.builder()
                        .name(request.getName())
                        .productId(request.getId())
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .content(request.getContent())
                        .vote(GsonUtils.convertToString(new HashSet<Long>()))
                        .build()
        );
        return getIdeas(request);
    }

    @Override
    public CsDevRepresentation updateIdea(DevRequest request) throws DBServiceException, IdeaNotFoundException {
        IdeaEntity ideaEntity = ideaService.get(
                IdeaEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);

        if(request.getName() != null) {
            ideaEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            ideaEntity.setState(IdeaState.valueOf(request.getState()).ordinal());
        }
        if(request.getContent() != null) {
            ideaEntity.setContent(request.getContent());
        }
        ideaService.update(ideaEntity);
        
        return getIdeas(request);
    }

    @Override
    public CsDevRepresentation getIdea(DevRequest request) throws DBServiceException {
        return getIdeas(request);
    }

    @Override
    public IdeaRep getIdeaInfo(DevRequest request) throws DBServiceException, IdeaNotFoundException, ActionServiceFailedException {
        IdeaEntity ideaEntity = ideaService.get(
                IdeaEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(ideaEntity.getId());
        return RepresentationBuilder.buildIdeaRep(ideaEntity, request.getPayload().getUserId(), actionRep);
    }

    private CsDevRepresentation getIdeas(DevRequest request) throws DBServiceException {
        List<IdeaEntity> ideaEntities = null;
        try {
            ideaEntities = ideaService.get(IdeaEntity.builder().workspaceId(request.getPayload().getWorkspaceId()).build());
        } catch (IdeaNotFoundException e) {
        }
        return RepresentationBuilder.buildListIdeaRep(ideaEntities, request.getPayload().getUserId());
    }
}
