package com.tnd.pw.development.sdk;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.development.common.representations.CsDevRepresentation;

import java.util.List;

public interface DevServiceSdkClient {
    BaseResponse<CsDevRepresentation> statisticDevByGoal(Long goalId);
    BaseResponse<CsDevRepresentation> statisticDevByInitiative(Long initiativeId);
    BaseResponse<CsDevRepresentation> statisticDevByProduct(Long productId);
    BaseResponse<CsDevRepresentation> calculateDev(Long featureId);

    BaseResponse<CsDevRepresentation> generateParkingLot(Long productId);

    BaseResponse<CsDevRepresentation> getFeatureByInitiativeIds(List<Long> initiativeIds);
    BaseResponse<CsDevRepresentation> getReleaseByProductId(Long productId);
}
