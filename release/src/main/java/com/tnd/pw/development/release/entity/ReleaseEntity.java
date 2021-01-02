package com.tnd.pw.development.release.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReleaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private Integer state;
    @SerializedName("owner")
    private Long owner;
    @SerializedName("initiatives")
    private String initiatives;
    @SerializedName("goals")
    private String goals;
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
    @SerializedName("process")
    private Integer process;
    @SerializedName("pending_features")
    private Integer pendingFeatures;
    @SerializedName("completed_features")
    private Integer completedFeatures;
    @SerializedName("theme")
    private String theme;
    @SerializedName("files")
    private String files;
    @SerializedName("type")
    private String type;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
}
