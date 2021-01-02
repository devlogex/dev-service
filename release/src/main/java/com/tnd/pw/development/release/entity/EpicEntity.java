package com.tnd.pw.development.release.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EpicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private Integer state;
    @SerializedName("start_on")
    private Long startOn;
    @SerializedName("end_on")
    private Long endOn;
    @SerializedName("release_id")
    private Long releaseId;
    @SerializedName("assign_to")
    private Long assignTo;
    @SerializedName("initiatives")
    private String initiatives;
    @SerializedName("goals")
    private String goals;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
}
