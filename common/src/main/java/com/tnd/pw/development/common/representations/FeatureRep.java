package com.tnd.pw.development.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class FeatureRep  implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private String state;
    @SerializedName("release_id")
    private Long releaseId;
    @SerializedName("type")
    private String type;
    @SerializedName("assign_to")
    private Long assignTo;
    @SerializedName("initiatives")
    private List<Long> initiatives;
    @SerializedName("goals")
    private List<Long> goals;
    @SerializedName("start_on")
    private Long startOn;
    @SerializedName("end_on")
    private Long endOn;
    @SerializedName("epic_id")
    private Long epicId;
    @SerializedName("requirements")
    private String requirements;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
    @SerializedName("process")
    private Integer process;
    @SerializedName("is_complete")
    private Boolean isComplete;

    @SerializedName("list_todo")
    private List<TodoRepresentation> todoReps;
    @SerializedName("list_comment")
    private List<CommentRepresentation> commentReps;

    @SerializedName("list_requirement")
    private List<RequirementRep> requirementReps;
}
