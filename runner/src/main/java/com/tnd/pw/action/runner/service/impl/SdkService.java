package com.tnd.pw.action.runner.service.impl;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.runner.exception.ActionServiceFailedException;
import com.tnd.pw.action.sdk.ActionServiceSdkClient;
import org.springframework.beans.factory.annotation.Autowired;

public class SdkService {
    @Autowired
    private ActionServiceSdkClient actionServiceSdkClient;

    public CsActionRepresentation getTodoComment(Long belongId) throws ActionServiceFailedException {
        BaseResponse<CsActionRepresentation> response = actionServiceSdkClient.getTodoComments(belongId);
        if(response.getResponseCode() < 1 ) {
            throw new ActionServiceFailedException();
        }
        return response.getData();
    }
}
