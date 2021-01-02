package com.tnd.pw.development.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.development.feature.entity.UTStep;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserStoryRep implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private String state;
    @SerializedName("personas")
    private List<Long> personas;
    @SerializedName("steps")
    private List<UTStepRep> steps;
    @SerializedName("epics")
    private List<UTEpicRep> epics;
    @SerializedName("releases")
    private List<UTReleaseRep> releases;
    @SerializedName("length")
    private Integer length;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
}