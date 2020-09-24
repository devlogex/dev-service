package com.tnd.pw.action.runner.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.runner.service.FeatureHandlerService;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.common.utils.RepresentationBuilder;
import com.tnd.pw.development.feature.constants.FeatureState;
import com.tnd.pw.development.feature.constants.FeatureType;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.strategy.call.api.CallActionApi;
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
    private CallActionApi callActionApi;
    @Autowired
    private ReleaseService releaseService;

    @Override
    public CsDevRepresentation addFeature(DevRequest request) throws IOException, DBServiceException, ReleaseNotFoundException, FeatureNotFoundException {
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
        List<ReleaseEntity> releaseEntities = releaseService.getRelease(
                ReleaseEntity.builder()
                        .productId(productId)
                        .build()
        );

        List<FeatureEntity> featureEntities = featureService.getFeature(
                FeatureEntity.builder()
                        .productId(productId)
                        .build()
        );
        return RepresentationBuilder.buildListFeatureRep(featureEntities, releaseEntities);
    }

    @Override
    public CsDevRepresentation updateFeature(DevRequest request) {
        if(request.getReleaseId() != null) {

            return null;
        }
        if(request.getName() != null) {

        }
    }
}
