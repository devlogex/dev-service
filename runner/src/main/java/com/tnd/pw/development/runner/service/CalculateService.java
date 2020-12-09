package com.tnd.pw.development.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.feature.constants.FeatureIsComplete;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.development.runner.config.DevelopmentConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CalculateService {
    @Autowired
    private ReleaseService releaseService;
    @Autowired
    private FeatureService featureService;

    public void calculateRelease(FeatureEntity featureEntity) throws DBServiceException, FeatureNotFoundException, ReleaseNotFoundException {
        ReleaseEntity releaseEntity = releaseService.getRelease(
                ReleaseEntity.builder()
                        .id(featureEntity.getReleaseId())
                        .build()
        ).get(0);
        Long productId = featureEntity.getProductId();
        List<FeatureEntity> featureEntities = featureService.getFeature(
                FeatureEntity.builder()
                        .productId(productId)
                        .build()
        );
        Integer processRelease = 100;
        Integer pendingFeatures = 0;
        Integer completeFeatures = 0;
        if(featureEntities != null) {
            long countCompletedFeature = featureEntities.stream().filter(feature -> feature.getIsComplete()== FeatureIsComplete.TRUE).count();
            processRelease = (int)countCompletedFeature/featureEntities.size() * 100;
            pendingFeatures = (int)(featureEntities.size() - countCompletedFeature);
            completeFeatures = (int)countCompletedFeature;
        }
        releaseEntity.setProcess(processRelease);
        releaseEntity.setPendingFeatures(pendingFeatures);
        releaseEntity.setCompletedFeatures(completeFeatures);
        releaseService.updateRelease(releaseEntity);
    }

    public void calculateDevHide(FeatureEntity featureEntity) {
        DevelopmentConfig.executor.execute(() -> {
            try {
                calculateRelease(featureEntity);
            } catch (DBServiceException | FeatureNotFoundException | ReleaseNotFoundException e) {
            }
        });
    }
}
