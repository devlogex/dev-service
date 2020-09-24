package com.tnd.pw.development.release.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReleasePhaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("release_id")
    private Long releaseId;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private Integer type;
    @SerializedName("color")
    private String color;
    @SerializedName("date")
    private String date;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;
}
