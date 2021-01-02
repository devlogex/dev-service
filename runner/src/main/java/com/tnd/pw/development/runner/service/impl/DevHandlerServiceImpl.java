package com.tnd.pw.development.runner.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.todos.constants.TodoState;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.feature.constants.FeatureIsComplete;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.release.constants.ReleaseType;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.exception.UnableUpdateParkingLotException;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.development.runner.service.CalculateService;
import com.tnd.pw.development.runner.service.DevHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DevHandlerServiceImpl implements DevHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevHandlerServiceImpl.class);

    @Autowired
    private ReleaseService releaseService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private CalculateService calculateService;

    @Override
    public CsDevRepresentation statisticDev(DevRequest request) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = new ArrayList<>();
        List<FeatureEntity> featureEntities = new ArrayList<>();
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .goals(request.getGoals() != null ? String.valueOf(request.getGoals().get(0)) : null)
                            .initiatives(request.getInitiatives() != null ? String.valueOf(request.getInitiatives().get(0)) : null)
                            .productId(request.getProductId())
                            .type(ReleaseType.NORMAL)
                            .build()
            );
            featureEntities = featureService.getFeature(
                    FeatureEntity.builder()
                            .goals(request.getGoals() != null ? String.valueOf(request.getGoals().get(0)) : null)
                            .initiativeId(request.getInitiativeId())
                            .productId(request.getProductId())
                            .build()
            );
        } catch (ReleaseNotFoundException | FeatureNotFoundException e) {
        }

        return RepresentationBuilder.statisticDev(releaseEntities, featureEntities);
    }

    @Override
    public CsDevRepresentation calculateFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException, ReleaseNotFoundException, ActionServiceFailedException, UnableUpdateParkingLotException {
        Long featureId = request.getFeatureId();
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(featureId)
                        .build()
        ).get(0);
        List<TodoRepresentation> todoReps = sdkService.getTodo(Arrays.asList(featureId)).getTodoReps();
        Integer process = 0;
        if(todoReps != null) {
            List<TodoRepresentation> completedTodos = todoReps.stream()
                    .filter(todo -> todo.getState().equals(TodoState.COMPLETE.name()))
                    .collect(Collectors.toList());
            process = completedTodos.size() * 100 / todoReps.size();
        }
        featureEntity.setIsComplete(process == 100 ? FeatureIsComplete.TRUE : FeatureIsComplete.FALSE);
        featureEntity.setProcess(process);
        featureService.updateFeature(featureEntity);

        calculateService.calculateRelease(featureEntity);
        return null;
    }

    @Override
    public CsDevRepresentation statisticRelease(DevRequest request) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = new ArrayList<>();
        List<FeatureEntity> featureEntities = new ArrayList<>();
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .goals(request.getGoals() != null ? String.valueOf(request.getGoals().get(0)) : null)
                            .initiatives(request.getInitiatives() != null ? String.valueOf(request.getInitiatives().get(0)) : null)
                            .productId(request.getProductId())
                            .type(ReleaseType.NORMAL)
                            .build()
            );
        } catch (ReleaseNotFoundException e) {
        }

        return RepresentationBuilder.statisticDev(releaseEntities, featureEntities);
    }

    @Override
    public CsDevRepresentation statisticFeature(DevRequest request) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = new ArrayList<>();
        List<FeatureEntity> featureEntities = new ArrayList<>();
        try {
            featureEntities = featureService.getFeature(
                    FeatureEntity.builder()
                            .goals(request.getGoals() != null ? String.valueOf(request.getGoals().get(0)) : null)
                            .initiativeId(request.getInitiativeId())
                            .productId(request.getProductId())
                            .build()
            );
        } catch (FeatureNotFoundException e) {
        }

        return RepresentationBuilder.statisticDev(releaseEntities, featureEntities);
    }
}
