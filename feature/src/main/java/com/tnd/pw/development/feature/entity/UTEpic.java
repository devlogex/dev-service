package com.tnd.pw.development.feature.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UTEpic implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("index")
    private Integer index;

    @Override
    public int hashCode() {
        return (int)(id & 4294967295L) | (index << 24);
    }

    @Override
    public boolean equals(Object o) {
        UTEpic obj = (UTEpic) o;
        return this.getId().compareTo(((UTEpic) o).getId()) == 0
                && this.getIndex() == obj.getIndex();
    }
}