package com.tnd.pw.development.common.utils;

import com.google.common.reflect.TypeToken;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.todos.constants.TodoState;
import com.tnd.pw.development.common.representations.*;
import com.tnd.pw.development.feature.constants.FeatureIsComplete;
import com.tnd.pw.development.feature.constants.FeatureState;
import com.tnd.pw.development.feature.constants.FeatureType;
import com.tnd.pw.development.feature.constants.RequirementState;
import com.tnd.pw.development.feature.entity.FeatureEntity;
import com.tnd.pw.development.feature.entity.RequirementEntity;
import com.tnd.pw.development.idea.constants.IdeaState;
import com.tnd.pw.development.idea.entity.IdeaEntity;
import com.tnd.pw.development.release.constants.EpicState;
import com.tnd.pw.development.release.constants.PhaseType;
import com.tnd.pw.development.release.constants.ReleaseState;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleaseLayoutEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class RepresentationBuilder {

    public static CsDevRepresentation buildListReleaseRep(List<ReleaseEntity> releaseEntities, List<FeatureEntity> featureEntities) {
        List<ReleaseRep> releaseReps = new ArrayList<>();
        if(releaseEntities == null)
            releaseEntities = new ArrayList<>();
        if(featureEntities == null) {
            featureEntities = new ArrayList<>();
        }
        for (ReleaseEntity releaseEntity : releaseEntities) {
            List<FeatureEntity> featureOfRelease = featureEntities.stream().filter(feature -> feature.getReleaseId().compareTo(releaseEntity.getId())==0).collect(Collectors.toList());
            ReleaseRep releaseRep = buildReleaseRep(releaseEntity, null, null);
            releaseRep.setFeatureReps(buildListFeatureRep(featureOfRelease));
            releaseReps.add(releaseRep);
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setReleaseReps(releaseReps);
        return representation;
    }

    private static List<FeatureRep> buildListFeatureRep(List<FeatureEntity> featureEntities) {
        List<FeatureRep> featureReps = new ArrayList<>();
        for(FeatureEntity featureEntity: featureEntities) {
            featureReps.add(buildFeatureRep(featureEntity, null,null));
        }
        return featureReps;
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
            List<Long> initiatives = GsonUtils.toListObject(releaseEntity.getInitiatives(), Long.class);
            List<Long> goals = GsonUtils.toListObject(releaseEntity.getGoals(), Long.class);
            releaseRep.setInitiatives(initiatives);
            releaseRep.setGoals(goals);

            releaseRep.setProcess(releaseEntity.getProcess());
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
            epicRep.setDescription(epicEntity.getDescription());
            epicRep.setFiles(epicEntity.getFiles());
            epicRep.setCreatedAt(epicEntity.getCreatedAt());
            epicRep.setCreatedBy(epicEntity.getCreatedBy());

            List<Long> initiatives = GsonUtils.toListObject(epicEntity.getInitiatives(), Long.class);
            List<Long> goals = GsonUtils.toListObject(epicEntity.getGoals(), Long.class);
            epicRep.setInitiatives(initiatives);
            epicRep.setGoals(goals);
        }
        return epicRep;
    }

    public static CsDevRepresentation buildListFeatureRep(List<FeatureEntity> featureEntities, List<ReleaseEntity> releaseEntities, List<ReleaseLayoutEntity> releaseLayouts) {
        HashMap<String, List<FeatureRep>> featureReps = new HashMap<>();
        if(releaseEntities == null) {
            releaseEntities = new ArrayList<>();
        }
        if(releaseLayouts == null) {
            releaseLayouts = new ArrayList<>();
        }
        if(featureEntities == null) {
            featureEntities = new ArrayList<>();
        }
        for(ReleaseEntity releaseEntity: releaseEntities) {
            ReleaseRep releaseRep = new ReleaseRep();
            releaseRep.setId(releaseEntity.getId());
            releaseRep.setName(releaseEntity.getName());
            releaseRep.setCreatedAt(releaseEntity.getCreatedAt());
            List<FeatureRep> featureRepList = new ArrayList<>();
            List<ReleaseLayoutEntity> layoutEntities = releaseLayouts.stream().filter(layout -> layout.getReleaseId().compareTo(releaseEntity.getId()) == 0).collect(Collectors.toList());
            if(layoutEntities.size() > 0) {
                ReleaseLayoutEntity releaseLayoutEntity = layoutEntities.get(0);
                List<Long> layout = GsonUtils.getGson().fromJson(releaseLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>() {
                }.getType());

                for (Long featureId : layout) {
                    List<FeatureEntity> featureList = featureEntities.stream().filter(feature -> feature.getId().compareTo(featureId) == 0).collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(featureList)) {
                        featureRepList.add(buildFeatureRep(featureList.get(0), null, null));
                    }
                }
            }

            featureReps.put(GsonUtils.convertToString(releaseRep), featureRepList);
        }

        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setMapFeatureReps(featureReps);
        return representation;
    }

    public static FeatureRep buildFeatureRep(FeatureEntity feature, CsActionRepresentation actionRep, List<RequirementEntity> requirementEntities) {
        FeatureRep featureRep = new FeatureRep();

        featureRep.setId(feature.getId());
        featureRep.setName(feature.getName());
        featureRep.setProductId(feature.getProductId());
        featureRep.setState(FeatureState.values()[feature.getState()].name());
        featureRep.setType(FeatureType.values()[feature.getType()].name());
        featureRep.setStartOn(feature.getStartOn());
        featureRep.setEndOn(feature.getEndOn());

        if(actionRep != null) {
            featureRep.setTodoReps(actionRep.getTodoReps());
            featureRep.setCommentReps(actionRep.getCommentReps());

            featureRep.setReleaseId(feature.getReleaseId());
            featureRep.setAssignTo(feature.getAssignTo());
            featureRep.setEpicId(feature.getEpicId());
            featureRep.setDescription(feature.getDescription());
            featureRep.setFiles(feature.getFiles());
            featureRep.setRequirements(feature.getRequirements());
            featureRep.setCreatedAt(feature.getCreatedAt());
            featureRep.setCreatedBy(feature.getCreatedBy());

            List<Long> initiatives = GsonUtils.toListObject(feature.getInitiatives(), Long.class);
            List<Long> goals = GsonUtils.toListObject(feature.getGoals(), Long.class);
            featureRep.setInitiatives(initiatives);
            featureRep.setGoals(goals);

            if(!CollectionUtils.isEmpty(actionRep.getTodoReps())) {
                long countComplete = actionRep.getTodoReps().stream().filter(todo->todo.getState().equals(TodoState.COMPLETE.toString())).count();
                int process = (int) (countComplete/(actionRep.getTodoReps().size())) * 100;
                featureRep.setProcess(process);
            } else {
                featureRep.setProcess(100);
            }
        }
        if(requirementEntities != null) {
            featureRep.setRequirementReps(buildListRequirement(requirementEntities).getRequirementReps());
        }
        return featureRep;
    }

    public static CsDevRepresentation buildListRequirement(List<RequirementEntity> requirementEntities) {
        List<RequirementRep> requirementReps = new ArrayList<>();
        if(requirementEntities != null) {
            for (RequirementEntity entity : requirementEntities) {
                requirementReps.add(buildRequirementRep(entity, null));
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setRequirementReps(requirementReps);
        return representation;
    }

    public static RequirementRep buildRequirementRep(RequirementEntity entity, CsActionRepresentation actionRep) {
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

        if(actionRep != null) {
            requirementRep.setTodoReps(actionRep.getTodoReps());
            requirementRep.setCommentReps(actionRep.getCommentReps());
        }
        return requirementRep;
    }

    public static CsDevRepresentation buildListIdeaRep(List<IdeaEntity> ideaEntities, Long userId) {
        if(ideaEntities == null) {
            ideaEntities = new ArrayList<>();
        }
        List<IdeaRep> ideaReps = new ArrayList<>();
        for(IdeaEntity ideaEntity: ideaEntities) {
            ideaReps.add(buildIdeaRep(ideaEntity, userId, null));
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setIdeaReps(ideaReps);
        return representation;
    }

    public static IdeaRep buildIdeaRep(IdeaEntity ideaEntity, Long userId, CsActionRepresentation actionRep) {
        IdeaRep ideaRep = new IdeaRep();
        ideaRep.setId(ideaEntity.getId());
        ideaRep.setName(ideaEntity.getName());
        ideaRep.setState(IdeaState.values()[ideaEntity.getState()].name());
        ideaRep.setCreatedAt(ideaEntity.getCreatedAt());
        ideaRep.setCreatedBy(ideaEntity.getCreatedBy());

        HashSet<Long> set = GsonUtils.getGson().fromJson(ideaEntity.getVote(), new TypeToken<HashSet<Long>>(){}.getType());
        ideaRep.setVoteNumber(set.size());
        ideaRep.setVoteState(set.contains(userId));

        if(actionRep != null) {
            ideaRep.setTodoReps(actionRep.getTodoReps());
            ideaRep.setCommentReps(actionRep.getCommentReps());

            ideaRep.setContent(ideaEntity.getContent());
            ideaRep.setFiles(ideaEntity.getFiles());
        }
        return ideaRep;
    }

    public static CsDevRepresentation statisticDev(List<ReleaseEntity> releaseEntities, List<FeatureEntity> featureEntities) {
        CsDevRepresentation representation = new CsDevRepresentation();
        List<ReleaseRep> releaseReps = new ArrayList<>();
        List<FeatureRep> featureReps = new ArrayList<>();
        for(ReleaseEntity releaseEntity: releaseEntities) {
            ReleaseRep releaseRep = new ReleaseRep();
            releaseRep.setId(releaseEntity.getId());
            releaseRep.setName(releaseEntity.getName());
            releaseRep.setProcess(releaseEntity.getProcess());
            releaseRep.setFeaturePending(releaseEntity.getPendingFeatures());
            releaseRep.setFeatureComplete(releaseEntity.getCompletedFeatures());
            releaseRep.setState(ReleaseState.values()[releaseEntity.getState()].name());
            releaseReps.add(releaseRep);
        }
        for(FeatureEntity featureEntity: featureEntities) {
            FeatureRep featureRep = new FeatureRep();
            featureRep.setId(featureEntity.getId());
            featureRep.setName(featureEntity.getName());
            featureRep.setProcess(featureEntity.getProcess());
            featureRep.setIsComplete(featureEntity.getIsComplete()== FeatureIsComplete.TRUE);
            featureRep.setState(ReleaseState.values()[featureEntity.getState()].name());
            featureReps.add(featureRep);
        }

        representation.setFeatureReps(featureReps);
        representation.setReleaseReps(releaseReps);
        return representation;
    }
}
