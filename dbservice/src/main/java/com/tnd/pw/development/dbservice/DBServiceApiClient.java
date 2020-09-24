package com.tnd.pw.development.dbservice;

import com.google.gson.Gson;
import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.common.constants.Protocol;
import com.tnd.dbservice.common.representation.DBServiceRequest;
import com.tnd.dbservice.common.representation.DBServiceResponse;

import java.io.IOException;

public class DBServiceApiClient extends AbstractService {
    private Gson gson = new Gson();
    public BaseResponse<DBServiceResponse.QueryResult> selectSQL(String url, DBServiceRequest request) throws IOException {
        BaseResponse<DBServiceResponse.QueryResult> response = client.sendRequest(url, Protocol.GET.name(), request);
        response.setData(gson.fromJson(gson.toJson(response.getData()), DBServiceResponse.QueryResult.class));
        return response;
    }

    public BaseResponse<Boolean> executeSQL(String url, DBServiceRequest request) throws IOException {
        return client.sendRequest(url, Protocol.POST.name(), request);
    }
}
