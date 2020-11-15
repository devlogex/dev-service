package com.tnd.pw.development.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.WorkspaceTokenRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceRequest extends WorkspaceTokenRequest {
    @SerializedName("state")
    private String state;
}
