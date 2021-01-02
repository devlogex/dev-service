package com.tnd.pw.development.sdk.impl;

import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.development.common.constants.Methods;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.common.requests.DevRequest;
import com.tnd.pw.development.sdk.DevServiceSdkClient;

import java.util.Arrays;

public class DevServiceSdkClientImpl extends AbstractService implements DevServiceSdkClient {

    public DevServiceSdkClientImpl(String host, int port, int appId) {
        super(host, port, appId);
    }

    @Override
    public BaseResponse<CsDevRepresentation> statisticDevByGoal(Long goalId) {
        DevRequest request = new DevRequest();
        request.setGoals(Arrays.asList(goalId));
        return client.sendRequest(Methods.STATISTIC_DEV, request);
    }

    @Override
    public BaseResponse<CsDevRepresentation> statisticDevByInitiative(Long initiativeId) {
        DevRequest request = new DevRequest();
        request.setInitiativeId(initiativeId);
        return client.sendRequest(Methods.STATISTIC_DEV, request);
    }

    @Override
    public BaseResponse<CsDevRepresentation> statisticDevByProduct(Long productId) {
        DevRequest request = new DevRequest();
        request.setProductId(productId);
        return client.sendRequest(Methods.STATISTIC_DEV, request);
    }

    @Override
    public BaseResponse<CsDevRepresentation> calculateDev(Long featureId) {
        DevRequest request = new DevRequest();
        request.setFeatureId(featureId);
        return client.sendRequest(Methods.CALCULATE_DEV, request);
    }

    @Override
    public BaseResponse<CsDevRepresentation> generateParkingLot(Long productId) {
        DevRequest request = new DevRequest();
        request.setProductId(productId);
        return client.sendRequest(Methods.GENERATE_PARKING_LOT, request);
    }
}
