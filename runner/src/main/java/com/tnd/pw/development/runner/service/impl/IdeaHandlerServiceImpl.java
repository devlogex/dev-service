package com.tnd.pw.development.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.IdeaRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.requests.WorkspaceRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.idea.constants.IdeaState;
import com.tnd.pw.development.idea.entity.IdeaEntity;
import com.tnd.pw.development.idea.exception.IdeaNotFoundException;
import com.tnd.pw.development.idea.service.IdeaService;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.service.IdeaHandlerService;
import com.tnd.pw.report.common.constants.ReportAction;
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
        HashSet<Long> set = new HashSet<>();
        set.add(request.getPayload().getUserId());

        IdeaEntity ideaEntity = ideaService.create(
                IdeaEntity.builder()
                        .name(request.getName())
                        .productId(request.getId())
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .content(request.getContent())
                        .createdBy(request.getPayload().getUserId())
                        .vote(GsonUtils.convertToString(set))
                        .build()
        );
        sdkService.createHistory(request.getPayload().getUserId(), ideaEntity.getId(), ReportAction.CREATED, GsonUtils.convertToString(ideaEntity));

        return getIdeas(request.getPayload().getUserId(), request.getPayload().getWorkspaceId(), null);
    }

    @Override
    public CsDevRepresentation updateIdea(DevRequest request) throws DBServiceException, IdeaNotFoundException {
        IdeaEntity ideaEntity = ideaService.get(
                IdeaEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        String oldIdea = GsonUtils.convertToString(ideaEntity);

        if(request.getName() != null) {
            ideaEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            ideaEntity.setState(IdeaState.valueOf(request.getState()).ordinal());
        }
        if(request.getContent() != null) {
            ideaEntity.setContent(request.getContent());
        }
        if(request.getFiles() != null) {
            ideaEntity.setFiles(request.getFiles());
        }
        ideaService.update(ideaEntity);
        sdkService.createHistory(request.getPayload().getUserId(), ideaEntity.getId(), ReportAction.UPDATED, oldIdea + "|" + GsonUtils.convertToString(ideaEntity));
        return getIdeas(request.getPayload().getUserId(), request.getPayload().getWorkspaceId(), null);
    }

    @Override
    public CsDevRepresentation getIdea(WorkspaceRequest request) throws DBServiceException {
        Long userId = request.getPayload().getUserId();
        Long workspaceId = request.getPayload().getWorkspaceId();
        Integer state = request.getState() != null ?
                IdeaState.valueOf(request.getState()).ordinal()
                :
                null;
        return getIdeas(userId, workspaceId, state);
    }

    @Override
    public IdeaRep getIdeaInfo(DevRequest request) throws DBServiceException, IdeaNotFoundException, ActionServiceFailedException {
        IdeaEntity ideaEntity = ideaService.get(
                IdeaEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(ideaEntity.getId());
        sdkService.createWatcher(request.getPayload().getUserId(), ideaEntity.getId());
        return RepresentationBuilder.buildIdeaRep(ideaEntity, request.getPayload().getUserId(), actionRep);
    }

    @Override
    public IdeaRep voteIdea(DevRequest request) throws DBServiceException, IdeaNotFoundException, ActionServiceFailedException {
        IdeaEntity ideaEntity = ideaService.get(
                IdeaEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        HashSet<Long> set = GsonUtils.getGson().fromJson(ideaEntity.getVote(), new TypeToken<HashSet<Long>>(){}.getType());
        Long userId = request.getPayload().getUserId();
        if(set.contains(userId)) {
            set.remove(userId);
        } else {
            set.add(userId);
        }
        ideaEntity.setVote(GsonUtils.convertToString(set));
        ideaService.update(ideaEntity);

        CsActionRepresentation actionRep = sdkService.getTodoComment(ideaEntity.getId());
        return RepresentationBuilder.buildIdeaRep(ideaEntity, request.getPayload().getUserId(), actionRep);
    }

    private CsDevRepresentation getIdeas(Long userId, Long workspaceId, Integer state) throws DBServiceException {
        List<IdeaEntity> ideaEntities = null;
        try {
            ideaEntities = ideaService.get(
                    IdeaEntity.builder()
                            .workspaceId(workspaceId)
                            .state(state)
                            .build()
            );
        } catch (IdeaNotFoundException e) {
        }
        return RepresentationBuilder.buildListIdeaRep(ideaEntities, userId);
    }
}
