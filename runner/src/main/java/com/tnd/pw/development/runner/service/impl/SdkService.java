package com.tnd.pw.development.runner.service.impl;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.runner.config.DevelopmentConfig;
import com.tnd.pw.development.runner.exception.ActionServiceFailedException;
import com.tnd.pw.action.sdk.ActionServiceSdkClient;
import com.tnd.pw.report.sdk.ReportSdkClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SdkService {
    @Autowired
    private ActionServiceSdkClient actionServiceSdkClient;
    @Autowired
    private ReportSdkClient reportSdkClient;

    public void createHistory(Long userId, Long objectId, String action, String content) {
        DevelopmentConfig.executor.execute(() -> reportSdkClient.createHistory(userId, objectId, action, content));
    }

    public void createWatcher(Long userId, Long objectId) {
        DevelopmentConfig.executor.execute(() -> reportSdkClient.createWatcher(userId, objectId));
    }

    public CsActionRepresentation getTodoComment(Long belongId) throws ActionServiceFailedException {
        BaseResponse<CsActionRepresentation> response = actionServiceSdkClient.getTodoComments(belongId);
        if(response.getResponseCode() < 1 ) {
            throw new ActionServiceFailedException();
        }
        return response.getData();
    }

    public CsActionRepresentation getTodo(List<Long> belongIds) throws ActionServiceFailedException {
        BaseResponse<CsActionRepresentation> response = actionServiceSdkClient.getTodos(belongIds);
        if(response.getResponseCode() < 1 ) {
            throw new ActionServiceFailedException();
        }
        return response.getData();
    }
}
