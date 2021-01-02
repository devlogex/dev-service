package com.tnd.pw.development.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class CsDevRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_release")
    private List<ReleaseRep> releaseReps;

    @SerializedName("parking_lot_feature")
    private ReleaseRep parkingLotFeature;

    @SerializedName("parking_lot_epic")
    private ReleaseRep parkingLotEpic;

    @SerializedName("list_feature_rep")
    private List<FeatureRep> featureReps;

    @SerializedName("list_release_phase")
    private List<ReleasePhaseRep> releasePhaseReps;

    @SerializedName("list_epic")
    private HashMap<String, List<EpicRep>> mapEpicReps;

    @SerializedName("list_feature")
    private HashMap<String, List<FeatureRep>> mapFeatureReps;

    @SerializedName("feature")
    private FeatureRep featureRep;

    @SerializedName("list_requirement")
    private List<RequirementRep> requirementReps;

    @SerializedName("list_idea")
    private List<IdeaRep> ideaReps;

    @SerializedName("user_story")
    private UserStoryRep userStoryRep;

    @SerializedName("list_user_story")
    private List<UserStoryRep> userStoryReps;
}
