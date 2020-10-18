package com.tnd.pw.action.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.feature.exception.FeatureNotFoundException;
import com.tnd.pw.development.release.exception.ReleaseNotFoundException;

@HandlerServiceClass
public class DevHandler implements BaseHandler {

    @HandlerService(path = "/", protocol = "GET")
    public BaseResponse<CsDevRepresentation> addFeature(BaseRequest request) throws FeatureNotFoundException, DBServiceException, ReleaseNotFoundException {
        return new BaseResponse<>(null);
    }
}
