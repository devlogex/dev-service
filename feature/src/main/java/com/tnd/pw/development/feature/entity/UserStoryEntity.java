package com.tnd.pw.development.feature.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserStoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private Integer state;
    @SerializedName("personas")
    private String personas;
    @SerializedName("steps")
    private String steps;
    @SerializedName("epics")
    private String epics;
    @SerializedName("releases")
    private String releases;
    @SerializedName("length")
    private Integer length;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
}