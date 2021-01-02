package com.tnd.pw.development.feature.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class UTRelease implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("height")
    private Integer height;
    @SerializedName("features")
    private List<UTFeature> features;

    public UTRelease() {
        features = new ArrayList<>();
    }
}