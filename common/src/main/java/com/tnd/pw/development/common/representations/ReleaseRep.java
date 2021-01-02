package com.tnd.pw.development.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseRep implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private String state;
    @SerializedName("owner")
    private Long owner;
    @SerializedName("initiatives")
    private List<Long> initiatives;
    @SerializedName("goals")
    private List<Long> goals;
    @SerializedName("days_to_release")
    private Integer daysToRelease;
    @SerializedName("release_date")
    private Long releaseDate;
    @SerializedName("start_on")
    private Long startOn;
    @SerializedName("end_on")
    private Long endOn;
    @SerializedName("develop_start_on")
    private Long developStartOn;
    @SerializedName("theme")
    private String theme;
    @SerializedName("files")
    private String files;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;

    @SerializedName("process")
    private Integer process;
    @SerializedName("feature_pending")
    private Integer featurePending;
    @SerializedName("feature_complete")
    private Integer featureComplete;

    @SerializedName("list_features")
    private List<FeatureRep> featureReps;

    @SerializedName("list_epics")
    private List<EpicRep> epicReps;

    @SerializedName("release_phases")
    private List<ReleasePhaseRep> releasePhaseReps;

    @SerializedName("list_todo")
    private List<TodoRepresentation> todoReps;
    @SerializedName("list_comment")
    private List<CommentRepresentation> commentReps;
}
