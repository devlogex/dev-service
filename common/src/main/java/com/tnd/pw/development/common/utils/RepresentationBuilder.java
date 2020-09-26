package com.tnd.pw.development.common.utils;

import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.common.representations.*;
import com.tnd.pw.development.feature.constants.FeatureState;
import com.tnd.pw.development.feature.constants.FeatureType;
import com.tnd.pw.development.feature.constants.RequirementState;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.release.constants.EpicState;
import com.tnd.pw.development.release.constants.PhaseType;
import com.tnd.pw.development.release.constants.ReleaseState;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RepresentationBuilder {

    public static CsDevRepresentation buildListReleaseRep(List<ReleaseEntity> releaseEntities) {
        List<ReleaseRep> releaseReps = new ArrayList<>();
        if(releaseEntities != null) {
            for (ReleaseEntity releaseEntity : releaseEntities) {
                releaseReps.add(buildReleaseRep(releaseEntity,null,null));
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setReleaseReps(releaseReps);
        return representation;
    }

    public static ReleaseRep buildReleaseRep(ReleaseEntity releaseEntity, CsActionRepresentation actionRep, List<ReleasePhaseEntity> releasePhaseEntities) {
        ReleaseRep releaseRep = new ReleaseRep();
        releaseRep.setId(releaseEntity.getId());
        releaseRep.setName(releaseEntity.getName());
        releaseRep.setProductId(releaseEntity.getProductId());
        releaseRep.setDaysToRelease(releaseEntity.getDaysToRelease());
        releaseRep.setReleaseDate(releaseEntity.getReleaseDate());
        releaseRep.setStartOn(releaseEntity.getStartOn());
        releaseRep.setEndOn(releaseEntity.getEndOn());
        releaseRep.setDevelopStartOn(releaseEntity.getDevelopStartOn());

        if(actionRep != null) {
            releaseRep.setTodoReps(actionRep.getTodoReps());
            releaseRep.setCommentReps(actionRep.getCommentReps());

            releaseRep.setTheme(releaseEntity.getTheme());
            releaseRep.setFiles(releaseEntity.getFiles());
            releaseRep.setCreatedAt(releaseEntity.getCreatedAt());
            releaseRep.setCreatedBy(releaseEntity.getCreatedBy());
            releaseRep.setState(ReleaseState.values()[releaseEntity.getState()].name());
            releaseRep.setOwner(releaseEntity.getOwner());
            releaseRep.setInitiativeId(releaseEntity.getInitiativeId());
            releaseRep.setGoals(releaseEntity.getGoals());
        }
        List<ReleasePhaseRep> releasePhaseReps = new ArrayList<>();
        if(releasePhaseEntities != null) {
            for(ReleasePhaseEntity releasePhaseEntity: releasePhaseEntities) {
                releasePhaseReps.add(buildReleasePhaseRep(releasePhaseEntity, null));
            }
        }
        releaseRep.setReleasePhaseReps(releasePhaseReps);
        return releaseRep;
    }

    public static ReleasePhaseRep buildReleasePhaseRep(ReleasePhaseEntity releasePhaseEntity, CsActionRepresentation actionRep) {
        ReleasePhaseRep releasePhaseRep = new ReleasePhaseRep();
        releasePhaseRep.setId(releasePhaseEntity.getId());
        releasePhaseRep.setName(releasePhaseEntity.getName());
        releasePhaseRep.setColor(releasePhaseEntity.getColor());
        releasePhaseRep.setDate(releasePhaseEntity.getDate());
        releasePhaseRep.setReleaseId(releasePhaseEntity.getReleaseId());
        if(actionRep != null) {
            releasePhaseRep.setTodoReps(actionRep.getTodoReps());
            releasePhaseRep.setCommentReps(actionRep.getCommentReps());

            releasePhaseRep.setDescription(releasePhaseEntity.getDescription());
            releasePhaseRep.setFiles(releasePhaseEntity.getFiles());
            releasePhaseRep.setType(PhaseType.values()[releasePhaseEntity.getType()].name());
        }
        return releasePhaseRep;
    }


    public static CsDevRepresentation buildListReleasePhaseRep(List<ReleasePhaseEntity> releasePhases) {
        List<ReleasePhaseRep> releasePhaseReps = new ArrayList<>();
        if(releasePhases != null) {
            for (ReleasePhaseEntity releasePhaseEntity : releasePhases) {
                releasePhaseReps.add(buildReleasePhaseRep(releasePhaseEntity, null));
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setReleasePhaseReps(releasePhaseReps);
        return representation;
    }

    public static CsDevRepresentation buildListEpicRep(List<EpicEntity> epicEntities) {
        List<EpicRep> epicReps = new ArrayList<>();
        if(epicEntities != null) {
            for (EpicEntity epicEntity : epicEntities) {
                epicReps.add(buildEpicRep(epicEntity, null));
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setEpicReps(epicReps);
        return representation;
    }

    public static EpicRep buildEpicRep(EpicEntity epicEntity, CsActionRepresentation actionRep) {
        EpicRep epicRep = new EpicRep();
        epicRep.setId(epicEntity.getId());
        epicRep.setName(epicEntity.getName());
        epicRep.setProductId(epicEntity.getProductId());

        if(actionRep != null) {
            epicRep.setTodoReps(actionRep.getTodoReps());
            epicRep.setCommentReps(actionRep.getCommentReps());

            epicRep.setState(EpicState.values()[epicEntity.getState()].name());
            epicRep.setReleaseId(epicEntity.getReleaseId());
            epicRep.setInitiativeId(epicEntity.getInitiativeId());
            epicRep.setGoals(epicEntity.getGoals());
            epicRep.setDescription(epicEntity.getDescription());
            epicRep.setFiles(epicEntity.getFiles());
            epicRep.setCreatedAt(epicEntity.getCreatedAt());
            epicRep.setCreatedBy(epicEntity.getCreatedBy());
        }
        return epicRep;
    }

    public static CsDevRepresentation buildListFeatureRep(List<FeatureEntity> featureEntities, List<ReleaseEntity> releaseEntities) {
        HashMap<String, List<FeatureRep>> featureReps = new HashMap<>();
        if(releaseEntities != null) {
            for(ReleaseEntity releaseEntity: releaseEntities) {
                ReleaseRep releaseRep = new ReleaseRep();
                releaseRep.setId(releaseEntity.getId());
                releaseRep.setName(releaseEntity.getName());

                List<FeatureRep> featureRepList = new ArrayList<>();
                if(featureEntities != null) {
                    featureRepList = featureEntities.stream().filter(feature -> feature.getReleaseId().compareTo(releaseEntity.getId()) == 0)
                            .map(feature -> buildFeatureRep(feature, null)).collect(Collectors.toList());
                }
                featureReps.put(GsonUtils.convertToString(releaseRep), featureRepList);
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setFeatureReps(featureReps);
        return representation;
    }

    public static FeatureRep buildFeatureRep(FeatureEntity feature, CsActionRepresentation actionRep) {
        FeatureRep featureRep = new FeatureRep();

        featureRep.setId(feature.getId());
        featureRep.setName(feature.getName());
        featureRep.setProductId(feature.getProductId());

        if(actionRep != null) {
            featureRep.setTodoReps(actionRep.getTodoReps());
            featureRep.setCommentReps(actionRep.getCommentReps());

            featureRep.setState(FeatureState.values()[feature.getState()].name());
            featureRep.setType(FeatureType.values()[feature.getType()].name());
            featureRep.setReleaseId(feature.getReleaseId());
            featureRep.setAssignTo(feature.getAssignTo());
            featureRep.setInitiativeId(feature.getInitiativeId());
            featureRep.setGoals(feature.getGoals());
            featureRep.setEpicId(feature.getEpicId());
            featureRep.setDescription(feature.getDescription());
            featureRep.setFiles(feature.getFiles());
            featureRep.setRequirements(feature.getRequirements());
            featureRep.setCreatedAt(feature.getCreatedAt());
            featureRep.setCreatedBy(feature.getCreatedBy());
        }
        return featureRep;
    }

    public static CsDevRepresentation buildListRequirement(List<RequirementEntity> requirementEntities) {
        List<RequirementRep> requirementReps = new ArrayList<>();
        if(requirementEntities != null) {
            for (RequirementEntity entity : requirementEntities) {
                requirementReps.add(buildRequirementRep(entity));
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setRequirementReps(requirementReps);
        return representation;
    }

    public static RequirementRep buildRequirementRep(RequirementEntity entity) {
        RequirementRep requirementRep = new RequirementRep();
        requirementRep.setId(entity.getId());
        requirementRep.setName(entity.getName());
        requirementRep.setState(RequirementState.values()[entity.getState()].name());
        requirementRep.setAssignTo(entity.getAssignTo());
        requirementRep.setFeatureId(entity.getFeatureId());
        requirementRep.setDescription(entity.getDescription());
        requirementRep.setFiles(entity.getFiles());
        requirementRep.setCreatedAt(entity.getCreatedAt());
        requirementRep.setCreatedBy(entity.getCreatedBy());
        return requirementRep;
    }
}
