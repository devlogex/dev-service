package com.tnd.pw.development.feature.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RequirementEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("feature_id")
    private Long featureId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private Integer state;
    @SerializedName("assign_to")
    private Long assignTo;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
}
