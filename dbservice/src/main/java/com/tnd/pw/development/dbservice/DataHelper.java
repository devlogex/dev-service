package com.tnd.pw.development.dbservice;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.dbservice.common.representation.DBServiceRequest;
import com.tnd.dbservice.common.representation.DBServiceResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DataHelper {
    private String queryUrl;
    private String executeUrl;

    private DBServiceApiClient dbServiceApiClient;

    public DataHelper(String domain, DBServiceApiClient dbServiceApiClient) {
        this.queryUrl = domain + "/select";
        this.executeUrl = domain + "/execute";
        this.dbServiceApiClient = dbServiceApiClient;
    }

    public <T extends Serializable> List<T> querySQL(String query, Class<T> responseType) throws IOException, DBServiceException {
        DBServiceRequest request = new DBServiceRequest(query);
        BaseResponse<DBServiceResponse.QueryResult> response = dbServiceApiClient.selectSQL(queryUrl, request);
        if(!response.getData().getSuccess()) {
            throw new DBServiceException();
        }
        return GsonUtils.toListObject(GsonUtils.convertToString(response.getData().getData()), responseType);
    }

    public void executeSQL(String query) throws IOException, DBServiceException {
        DBServiceRequest request = new DBServiceRequest(query);
        BaseResponse<Boolean> response = dbServiceApiClient.executeSQL(executeUrl, request);
        try {
            if (!response.getData()) {
                throw new DBServiceException();
            }
        } catch (Exception e) {
            throw new DBServiceException();
        }
    }
}
