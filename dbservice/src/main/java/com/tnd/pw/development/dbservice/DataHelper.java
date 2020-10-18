package com.tnd.pw.development.dbservice;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.dbservice.common.representation.DBServiceRequest;
import com.tnd.dbservice.common.representation.DBServiceResponse;
import com.tnd.dbservice.sdk.api.DBServiceSdkClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DataHelper {
    private String queryUrl;
    private String executeUrl;
    private DBServiceApiClient dbServiceApiClient;

    private DBServiceSdkClient dbServiceSdkClient;

    @Deprecated
    public DataHelper(String domain, DBServiceApiClient dbServiceApiClient) {
        this.queryUrl = domain + "/select";
        this.executeUrl = domain + "/execute";
        this.dbServiceApiClient = dbServiceApiClient;
    }

    public DataHelper(DBServiceSdkClient dbServiceSdkClient) {
        this.dbServiceSdkClient = dbServiceSdkClient;
    }

    public <T extends Serializable> List<T> querySQL(String query, Class<T> responseType) throws DBServiceException {
        BaseResponse<DBServiceResponse.QueryResult> response = dbServiceSdkClient.selectSQL(query);
        if(response.getResponseCode() < 1) {
            throw new DBServiceException();
        }
        return GsonUtils.toListObject(GsonUtils.convertToString(response.getData().getData()), responseType);
    }

    public void executeSQL(String query) throws DBServiceException {
        BaseResponse<Boolean> response = dbServiceSdkClient.executeSQL(query);
        if(response.getResponseCode() < 1) {
            throw new DBServiceException();
        }
    }
}