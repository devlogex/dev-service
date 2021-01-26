package com.tnd.pw.development.common.utils;

import com.google.common.reflect.TypeToken;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.common.representations.*;
import com.tnd.pw.development.feature.constants.*;
import com.tnd.pw.development.feature.entity.*;
import com.tnd.pw.development.idea.constants.IdeaState;
import com.tnd.pw.development.idea.entity.IdeaEntity;
import com.tnd.pw.development.release.constants.EpicState;
import com.tnd.pw.development.release.constants.PhaseType;
import com.tnd.pw.development.release.constants.ReleaseState;
import com.tnd.pw.development.release.constants.ReleaseType;
import com.tnd.pw.development.release.entity.EpicEntity;
import com.tnd.pw.development.release.entity.ReleaseEntity;
import com.tnd.pw.development.release.entity.ReleaseLayoutEntity;
import com.tnd.pw.development.release.entity.ReleasePhaseEntity;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class RepresentationBuilder {

    public static CsDevRepresentation buildListReleaseRep(List<ReleaseEntity> releaseEntities, List<FeatureEntity> featureEntities) {
        List<ReleaseRep> releaseReps = new ArrayList<>();
        ReleaseRep parkingLotFeature = new ReleaseRep();
        ReleaseRep parkingLotEpic = new ReleaseRep();

        for (ReleaseEntity releaseEntity : releaseEntities) {
            if(ReleaseType.NORMAL.equals(releaseEntity.getType())) {
                List<FeatureEntity> featureOfRelease = featureEntities.stream().filter(feature -> feature.getReleaseId().compareTo(releaseEntity.getId()) == 0).collect(Collectors.toList());
                ReleaseRep releaseRep = buildReleaseRep(releaseEntity, null, null);
                releaseRep.setFeatureReps(buildListFeatureRep(featureOfRelease));
                releaseReps.add(releaseRep);
            } else if (ReleaseType.PARKING_LOT_FEATURE.equals(releaseEntity.getType())) {
                parkingLotFeature.setId(releaseEntity.getId());
                parkingLotFeature.setName(releaseEntity.getName());
                parkingLotFeature.setProductId(releaseEntity.getProductId());
            } else if (ReleaseType.PARKING_LOT_EPIC.equals(releaseEntity.getType())) {
                parkingLotEpic.setId(releaseEntity.getId());
                parkingLotEpic.setName(releaseEntity.getName());
                parkingLotEpic.setProductId(releaseEntity.getProductId());
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setReleaseReps(releaseReps);
        representation.setParkingLotFeature(parkingLotFeature);
        representation.setParkingLotEpic(parkingLotEpic);
        return representation;
    }

    public static CsDevRepresentation buildListReleaseRep(List<ReleaseEntity> releaseEntities) {
        List<ReleaseRep> releaseReps = new ArrayList<>();

        for (ReleaseEntity releaseEntity : releaseEntities) {
            if(ReleaseType.NORMAL.equals(releaseEntity.getType())) {
                ReleaseRep releaseRep = buildReleaseRep(releaseEntity, null, null);
                releaseReps.add(releaseRep);
            }
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setReleaseReps(releaseReps);
        return representation;
    }

    public static List<FeatureRep> buildListFeatureRep(List<FeatureEntity> featureEntities) {
        List<FeatureRep> featureReps = new ArrayList<>();
        for(FeatureEntity featureEntity: featureEntities) {
            featureReps.add(buildFeatureRep(featureEntity, null,null));
        }
        return featureReps;
    }

    public static CsDevRepresentation buildListFeatureReps(List<FeatureEntity> featureEntities) {
        List<FeatureRep> featureReps = new ArrayList<>();
        for(FeatureEntity featureEntity: featureEntities) {
            featureReps.add(buildFeatureRep(featureEntity, null,null));
        }
        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setFeatureReps(featureReps);
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
        releaseRep.setProcess(releaseEntity.getProcess());
        releaseRep.setState(ReleaseState.values()[releaseEntity.getState()].name());

        if(actionRep != null) {
            releaseRep.setTodoReps(actionRep.getTodoReps());
            releaseRep.setCommentReps(actionRep.getCommentReps());

            releaseRep.setTheme(releaseEntity.getTheme());
            releaseRep.setFiles(releaseEntity.getFiles());
            releaseRep.setCreatedAt(releaseEntity.getCreatedAt());
            releaseRep.setCreatedBy(releaseEntity.getCreatedBy());
            releaseRep.setOwner(releaseEntity.getOwner());
            List<Long> initiatives = GsonUtils.toListObject(releaseEntity.getInitiatives(), Long.class);
            List<Long> goals = GsonUtils.toListObject(releaseEntity.getGoals(), Long.class);
            releaseRep.setInitiatives(initiatives);
            releaseRep.setGoals(goals);

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

    public static CsDevRepresentation buildListEpicRep(List<EpicEntity> epicEntities, List<ReleaseEntity> releaseEntities, List<ReleaseLayoutEntity> releaseLayouts) {
        LinkedHashMap<String, List<EpicRep>> epicReps = new LinkedHashMap<>();
        ReleaseRep parkingLotEpic = new ReleaseRep();

        for(ReleaseEntity releaseEntity: releaseEntities) {
            if(ReleaseType.NORMAL.equals(releaseEntity.getType())) {
                ReleaseRep releaseRep = new ReleaseRep();
                releaseRep.setId(releaseEntity.getId());
                releaseRep.setName(releaseEntity.getName());
                releaseRep.setCreatedAt(releaseEntity.getCreatedAt());
                List<EpicRep> epicRepList = buildListEpicOfRelease(releaseEntity, epicEntities, releaseLayouts);

                epicReps.put(GsonUtils.convertToString(releaseRep), epicRepList);
            }
            else if(ReleaseType.PARKING_LOT_EPIC.equals(releaseEntity.getType())) {
                parkingLotEpic = buildParkingLotRep(releaseEntity, null, epicEntities, releaseLayouts);
            }
        }

        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setMapEpicReps(epicReps);
        representation.setParkingLotEpic(parkingLotEpic);
        return representation;
    }

    private static List<EpicRep> buildListEpicOfRelease(ReleaseEntity releaseEntity, List<EpicEntity> epicEntities, List<ReleaseLayoutEntity> releaseLayouts) {
        List<EpicRep> epicReps = new ArrayList<>();
        if(CollectionUtils.isEmpty(epicEntities)) {
            return epicReps;
        }
        List<ReleaseLayoutEntity> layoutEntities = releaseLayouts.stream().filter(layout -> layout.getReleaseId().compareTo(releaseEntity.getId()) == 0).collect(Collectors.toList());
        if (layoutEntities.size() > 0) {
            ReleaseLayoutEntity releaseLayoutEntity = layoutEntities.get(0);
            List<Long> layout = GsonUtils.getGson().fromJson(releaseLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>() {
            }.getType());

            for (Long epicId : layout) {
                List<EpicEntity> epicList = epicEntities.stream().filter(epic -> epic.getId().compareTo(epicId) == 0).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(epicList)) {
                    epicReps.add(buildEpicRep(epicList.get(0), null));
                }
            }
        }
        return epicReps;
    }

    public static EpicRep buildEpicRep(EpicEntity epicEntity, CsActionRepresentation actionRep) {
        EpicRep epicRep = new EpicRep();
        epicRep.setId(epicEntity.getId());
        epicRep.setName(epicEntity.getName());
        epicRep.setProductId(epicEntity.getProductId());
        epicRep.setStartOn(epicEntity.getStartOn());
        epicRep.setEndOn(epicEntity.getEndOn());
        epicRep.setState(EpicState.values()[epicEntity.getState()].name());

        if(actionRep != null) {
            epicRep.setTodoReps(actionRep.getTodoReps());
            epicRep.setCommentReps(actionRep.getCommentReps());

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
        LinkedHashMap<String, List<FeatureRep>> featureReps = new LinkedHashMap<>();
        ReleaseRep parkingLotFeature = new ReleaseRep();

        for(ReleaseEntity releaseEntity: releaseEntities) {
            if(ReleaseType.NORMAL.equals(releaseEntity.getType())) {
                ReleaseRep releaseRep = new ReleaseRep();
                releaseRep.setId(releaseEntity.getId());
                releaseRep.setName(releaseEntity.getName());
                releaseRep.setCreatedAt(releaseEntity.getCreatedAt());
                List<FeatureRep> featureRepList = buildListFeatureOfRelease(releaseEntity, featureEntities, releaseLayouts);

                featureReps.put(GsonUtils.convertToString(releaseRep), featureRepList);
            }
            else if(ReleaseType.PARKING_LOT_FEATURE.equals(releaseEntity.getType())) {
                parkingLotFeature = buildParkingLotRep(releaseEntity, featureEntities, null, releaseLayouts);
            }
        }

        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setMapFeatureReps(featureReps);
        representation.setParkingLotFeature(parkingLotFeature);
        return representation;
    }

    private static ReleaseRep buildParkingLotRep(ReleaseEntity releaseEntity, List<FeatureEntity> featureEntities, List<EpicEntity> epicEntities, List<ReleaseLayoutEntity> releaseLayouts) {
        ReleaseRep parkingLot = new ReleaseRep();
        parkingLot.setId(releaseEntity.getId());
        parkingLot.setName(releaseEntity.getName());
        List<FeatureRep> featureReps = buildListFeatureOfRelease(releaseEntity, featureEntities,releaseLayouts);
        parkingLot.setFeatureReps(featureReps);
        List<EpicRep> epicReps = buildListEpicOfRelease(releaseEntity, epicEntities,releaseLayouts);
        parkingLot.setEpicReps(epicReps);
        return parkingLot;
    }

    private static List<FeatureRep> buildListFeatureOfRelease(ReleaseEntity releaseEntity, List<FeatureEntity> featureEntities, List<ReleaseLayoutEntity> releaseLayouts) {
        List<FeatureRep> featureReps = new ArrayList<>();
        if(CollectionUtils.isEmpty(featureEntities)) {
            return featureReps;
        }
        List<ReleaseLayoutEntity> layoutEntities = releaseLayouts.stream().filter(layout -> layout.getReleaseId().compareTo(releaseEntity.getId()) == 0).collect(Collectors.toList());
        if (layoutEntities.size() > 0) {
            ReleaseLayoutEntity releaseLayoutEntity = layoutEntities.get(0);
            List<Long> layout = GsonUtils.getGson().fromJson(releaseLayoutEntity.getLayout(), new TypeToken<ArrayList<Long>>() {
            }.getType());

            for (Long featureId : layout) {
                List<FeatureEntity> featureList = featureEntities.stream().filter(feature -> feature.getId().compareTo(featureId) == 0).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(featureList)) {
                    featureReps.add(buildFeatureRep(featureList.get(0), null, null));
                }
            }
        }
        return featureReps;
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
        featureRep.setInitiativeId(feature.getInitiativeId());
        featureRep.setProcess(feature.getProcess());

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
            featureRep.setIsComplete(feature.getIsComplete() == FeatureIsComplete.TRUE);
            List<Long> goals = GsonUtils.toListObject(feature.getGoals(), Long.class);
            featureRep.setGoals(goals);
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
            featureRep.setState(FeatureState.values()[featureEntity.getState()].name());
            featureReps.add(featureRep);
        }

        representation.setFeatureReps(featureReps);
        representation.setReleaseReps(releaseReps);
        return representation;
    }

    public static CsDevRepresentation buildUserStoryRep(UserStoryEntity userStoryEntity, List<ReleaseEntity> releaseEntities, List<EpicEntity> epicEntities, List<FeatureEntity> featureEntities) {
        UserStoryRep userStoryRep = buildUserStoryRep(userStoryEntity);

        List<UTStep> utSteps = GsonUtils.getGson().fromJson(userStoryEntity.getSteps(), new TypeToken<ArrayList<UTStep>>(){}.getType());
        List<UTEpic> utEpics = GsonUtils.getGson().fromJson(userStoryEntity.getEpics(), new TypeToken<ArrayList<UTEpic>>(){}.getType());
        List<UTRelease> utReleases = GsonUtils.getGson().fromJson(userStoryEntity.getReleases(), new TypeToken<ArrayList<UTRelease>>(){}.getType());
        userStoryRep.setSteps(buildListUTStepRep(utSteps));
        userStoryRep.setEpics(buildListUTEpicRep(utEpics, epicEntities));
        userStoryRep.setReleases(buildListUTReleaseRep(utReleases, releaseEntities, featureEntities));
        userStoryRep.setPersonas(
                GsonUtils.getGson().fromJson(
                        userStoryEntity.getPersonas(),
                        new TypeToken<ArrayList<Long>>(){}.getType()
                )
        );

        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setUserStoryRep(userStoryRep);
        return representation;
    }

    private static List<UTReleaseRep> buildListUTReleaseRep(List<UTRelease> utReleases, List<ReleaseEntity> releaseEntities, List<FeatureEntity> featureEntities) {
        List<UTReleaseRep> utReleaseReps = new ArrayList<>();
        for(UTRelease utRelease: utReleases) {
            UTReleaseRep utReleaseRep = new UTReleaseRep();
            utReleaseRep.setId(utRelease.getId());
            utReleaseRep.setHeight(utRelease.getHeight());
            ReleaseEntity releaseEntity = releaseEntities.stream().filter(epic -> epic.getId().compareTo(utRelease.getId()) == 0).findFirst().get();
            utReleaseRep.setName(releaseEntity.getName());

            List<UTFeatureRep> utFeatureReps = new ArrayList<>();
            for(UTFeature utFeature: utRelease.getFeatures()) {
                UTFeatureRep utFeatureRep = new UTFeatureRep();
                utFeatureRep.setId(utFeature.getId());
                utFeatureRep.setPositionX(utFeature.getPositionX());
                utFeatureRep.setPositionY(utFeature.getPositionY());
                FeatureEntity featureEntity = featureEntities.stream()
                        .filter(feature -> feature.getId().compareTo(utFeature.getId()) == 0)
                        .findFirst().get();
                utFeatureRep.setName(featureEntity.getName());

                utFeatureReps.add(utFeatureRep);
            }

            utReleaseRep.setFeatures(utFeatureReps);

            utReleaseReps.add(utReleaseRep);
        }
        return utReleaseReps;
    }

    private static List<UTEpicRep> buildListUTEpicRep(List<UTEpic> utEpics, List<EpicEntity> epicEntities) {
        List<UTEpicRep> utEpicReps = new ArrayList<>();
        for(UTEpic utEpic: utEpics) {
            UTEpicRep utEpicRep = new UTEpicRep();
            utEpicRep.setId(utEpic.getId());
            utEpicRep.setIndex(utEpic.getIndex());
            EpicEntity epicEntity = epicEntities.stream().filter(epic -> epic.getId().compareTo(utEpic.getId()) == 0).findFirst().get();
            utEpicRep.setName(epicEntity.getName());

            utEpicReps.add(utEpicRep);
        }
        return utEpicReps;
    }

    public static List<UTStepRep> buildListUTStepRep(List<UTStep> utSteps) {
        List<UTStepRep> utStepReps = new ArrayList<>();
        for(UTStep utStep: utSteps) {
            UTStepRep utStepRep = new UTStepRep();
            utStepRep.setId(utStep.getId());
            utStepRep.setName(utStep.getName());
            utStepRep.setIndex(utStep.getIndex());
            utStepRep.setLength(utStep.getLength());

            utStepReps.add(utStepRep);
        }
        return utStepReps;
    }

    public static CsDevRepresentation buildUserStoryRep(List<UserStoryEntity> userStoryEntities) {
        List<UserStoryRep> userStoryReps = new ArrayList<>();

        for(UserStoryEntity userStoryEntity: userStoryEntities) {
            userStoryReps.add(buildUserStoryRep(userStoryEntity));
        }

        CsDevRepresentation representation = new CsDevRepresentation();
        representation.setUserStoryReps(userStoryReps);
        return representation;
    }

    private static UserStoryRep buildUserStoryRep(UserStoryEntity userStoryEntity) {
        UserStoryRep userStoryRep = new UserStoryRep();
        userStoryRep.setId(userStoryEntity.getId());
        userStoryRep.setName(userStoryEntity.getName());
        userStoryRep.setState(UserStoryState.values()[userStoryEntity.getState()].name());
        userStoryRep.setProductId(userStoryEntity.getProductId());
        userStoryRep.setCreatedAt(userStoryEntity.getCreatedAt());
        userStoryRep.setCreatedBy(userStoryEntity.getCreatedBy());
        userStoryRep.setLength(userStoryEntity.getLength());
        userStoryRep.setPersonas(
                GsonUtils.getGson().fromJson(
                        userStoryEntity.getPersonas(),
                        new TypeToken<ArrayList<Long>>(){}.getType()
                )
        );
        return userStoryRep;
    }
}
