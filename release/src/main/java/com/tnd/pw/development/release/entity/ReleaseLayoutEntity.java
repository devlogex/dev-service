package com.tnd.pw.development.release.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReleaseLayoutEntity implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("release_id")
    private Long releaseId;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("type")
    private String type;
    @SerializedName("layout")
    private String layout;
}
