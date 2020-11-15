package com.tnd.pw.development.idea.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class IdeaEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("name")
    private String name;
    @SerializedName("state")
    private Integer state;
    @SerializedName("content")
    private String content;
    @SerializedName("files")
    private String files;
    @SerializedName("vote")
    private String vote;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
}