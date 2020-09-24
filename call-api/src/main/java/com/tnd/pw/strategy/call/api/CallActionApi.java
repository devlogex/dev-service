package com.tnd.pw.strategy.call.api;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.utils.GsonUtils;
import com.tnd.pw.action.sdk.ActionSdkApi;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class CallActionApi {
    @Autowired
    private ActionSdkApi actionSdkApiClient;

    public CsActionRepresentation call(Long id, DevRequest request) throws IOException, CallApiFailException {
        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setId(id);
        actionRequest.setToken(request.getToken());
        BaseResponse<CsActionRepresentation> response = actionSdkApiClient.getCsActionRep(actionRequest);
        if(response.getStatusCode() != 200) {
            throw new CallApiFailException("Call action-service to get todo_comment failed !");
        }
        CsActionRepresentation actionRep = GsonUtils.getGson().fromJson(com.tnd.pw.action.common.utils.GsonUtils.convertToString(response.getData()), CsActionRepresentation.class);
        return actionRep;
    }
}
