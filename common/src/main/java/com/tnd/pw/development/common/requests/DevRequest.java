package com.tnd.pw.development.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.ProductTokenRequest;
import com.tnd.pw.development.feature.entity.UTEpic;
import com.tnd.pw.development.feature.entity.UTRelease;
import com.tnd.pw.development.feature.entity.UTStep;
import com.tnd.pw.development.release.entity.EpicEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DevRequest extends ProductTokenRequest {

    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private String state;
    @SerializedName("owner")
    private Long owner;
    @SerializedName("initiatives")
    private List<Long> initiatives;
    @SerializedName("initiative_id")
    private Long initiativeId;
    @SerializedName("release_id")
    private Long releaseId;
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
    @SerializedName("description")
    private String description;
    @SerializedName("theme")
    private String theme;
    @SerializedName("files")
    private String files;
    @SerializedName("type")
    private String type;
    @SerializedName("color")
    private String color;
    @SerializedName("date")
    private String date;
    @SerializedName("assign_to")
    private Long assignTo;
    @SerializedName("epic_id")
    private Long epicId;
    @SerializedName("feature_id")
    private Long featureId;
    @SerializedName("is_generate_phases")
    private boolean isGeneratePhases;

    @SerializedName("template")
    private Boolean template;

    @SerializedName("layout")
    private List<Long> layout;

    @SerializedName("vote")
    private String vote;
    @SerializedName("content")
    private String content;

    @SerializedName("steps")
    private List<UTStep> steps;
    @SerializedName("epics")
    private List<UTEpic> epics;
    @SerializedName("releases")
    private List<UTRelease> releases;
    @SerializedName("length")
    private Integer length;
    @SerializedName("personas")
    private List<Long> personas;
}
