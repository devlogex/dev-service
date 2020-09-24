package com.tnd.pw.development.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReleasePhaseRep implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("release_id")
    private Long releaseId;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("color")
    private String color;
    @SerializedName("date")
    private String date;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;

    @SerializedName("list_todo")
    private List<TodoRepresentation> todoReps;
    @SerializedName("list_comment")
    private List<CommentRepresentation> commentReps;
}
