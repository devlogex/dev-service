package com.tnd.pw.action.runner.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.runner.exception.ActionServiceFailedException;
import com.tnd.pw.action.runner.service.FeatureHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.representations.FeatureRep;
import com.tnd.pw.development.common.representations.RequirementRep;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.GsonUtils;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.feature.constants.FeatureState;
import com.tnd.pw.development.feature.constants.FeatureType;
import com.tnd.pw.development.feature.constants.RequirementState;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.exception.RequirementNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.service.ReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class FeatureHandlerServiceImpl implements FeatureHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureHandlerServiceImpl.class);

    @Autowired
    private FeatureService featureService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private ReleaseService releaseService;

    @Override
    public CsDevRepresentation addFeature(DevRequest request) throws DBServiceException, ReleaseNotFoundException, FeatureNotFoundException {
        Long productId = GenUID.getProductId(request.getId());
        featureService.createFeature(
                FeatureEntity.builder()
                        .name(request.getName())
                        .releaseId(request.getId())
                        .productId(productId)
                        .type(FeatureType.valueOf(request.getType()).ordinal())
                        .description(request.getDescription())
                        .files(request.getFiles())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );

        return getFeatures(productId);
    }

    @Override
    public CsDevRepresentation updateFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException {
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        if(request.getReleaseId() != null) {
            featureEntity.setReleaseId(request.getReleaseId());
        }
        if(request.getName() != null) {
            featureEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            featureEntity.setState(FeatureState.valueOf(request.getState()).ordinal());
        }
        if(request.getType() != null) {
            featureEntity.setType(FeatureType.valueOf(request.getType()).ordinal());
        }
        if(request.getAssignTo() != null) {
            featureEntity.setAssignTo(request.getAssignTo());
        }
        if(request.getInitiativeId() != null) {
            featureEntity.setInitiativeId(request.getInitiativeId());
        }
        if(request.getGoals() != null) {
            featureEntity.setGoals(GsonUtils.convertToString(request.getGoals()));
        }
        if(request.getEpicId() != null) {
            featureEntity.setEpicId(request.getEpicId());
        }
        if(request.getDescription() != null) {
            featureEntity.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            featureEntity.setFiles(request.getFiles());
        }
        featureService.updateFeature(featureEntity);
        return getFeatures(featureEntity.getProductId());
    }

    @Override
    public CsDevRepresentation getFeature(DevRequest request) throws DBServiceException {
        return getFeatures(request.getId());
    }

    @Override
    public FeatureRep getFeatureInfo(DevRequest request) throws DBServiceException, FeatureNotFoundException, ActionServiceFailedException {
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(featureEntity.getId());
        List<RequirementEntity> requirements = null;
        try {
            requirements = featureService.getRequirement(
                    RequirementEntity.builder()
                            .featureId(featureEntity.getId())
                            .build()
            );
        } catch (RequirementNotFoundException e) {
        }
        return RepresentationBuilder.buildFeatureRep(featureEntity, actionRep, requirements);
    }

    @Override
    public CsDevRepresentation removeFeature(DevRequest request) throws DBServiceException, FeatureNotFoundException {
        FeatureEntity featureEntity = featureService.getFeature(
                FeatureEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        featureService.removeFeature(featureEntity);
        return getFeatures(featureEntity.getProductId());
    }

    @Override
    public CsDevRepresentation addRequirement(DevRequest request) throws DBServiceException {
        RequirementEntity requirement = featureService.createRequirement(
                RequirementEntity.builder()
                        .featureId(request.getId())
                        .name(request.getName())
                        .description(request.getDescription())
                        .files(request.getFiles())
                        .build()
        );
        return getRequirement(requirement.getFeatureId());
    }

    @Override
    public CsDevRepresentation getRequirement(DevRequest request) throws DBServiceException {
        return getRequirement(request.getId());
    }

    @Override
    public RequirementRep getRequirementInfo(DevRequest request) throws DBServiceException, RequirementNotFoundException, ActionServiceFailedException {
        RequirementEntity requirementEntity = featureService.getRequirement(
                RequirementEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(requirementEntity.getId());
        return RepresentationBuilder.buildRequirementRep(requirementEntity, actionRep);
    }

    @Override
    public CsDevRepresentation updateRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException {
        RequirementEntity requirementEntity = featureService.getRequirement(
                RequirementEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        if(request.getName() != null) {
            requirementEntity.setName(request.getName());
        }
        if(request.getState() != null) {
            requirementEntity.setState(RequirementState.valueOf(request.getState()).ordinal());
        }
        if(request.getAssignTo() != null) {
            requirementEntity.setAssignTo(request.getAssignTo());
        }
        if(request.getDescription() != null) {
            requirementEntity.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            requirementEntity.setFiles(request.getFiles());
        }
        featureService.updateRequirement(requirementEntity);
        return getRequirement(requirementEntity.getFeatureId());
    }

    @Override
    public CsDevRepresentation removeRequirement(DevRequest request) throws DBServiceException, RequirementNotFoundException {
        RequirementEntity requirementEntity = featureService.getRequirement(
                RequirementEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        featureService.removeRequirement(requirementEntity);
        return getRequirement(requirementEntity.getFeatureId());
    }

    public CsDevRepresentation getRequirement(Long featureId) throws DBServiceException {
        List<RequirementEntity> requirementEntities = null;
        try {
            requirementEntities = featureService.getRequirement(
                    RequirementEntity.builder()
                            .featureId(featureId)
                            .build()
            );
        } catch (RequirementNotFoundException e) {
        }
        return RepresentationBuilder.buildListRequirement(requirementEntities);
    }

    private CsDevRepresentation getFeatures(Long productId) throws DBServiceException {
        List<ReleaseEntity> releaseEntities = null;
        List<FeatureEntity> featureEntities = null;
        try {
            releaseEntities = releaseService.getRelease(
                    ReleaseEntity.builder()
                            .productId(productId)
                            .build()
            );
            featureEntities = featureService.getFeature(
                    FeatureEntity.builder()
                            .productId(productId)
                            .build()
            );
        }catch (ReleaseNotFoundException | FeatureNotFoundException e) {
        }
        return RepresentationBuilder.buildListFeatureRep(featureEntities, releaseEntities);
    }
}
