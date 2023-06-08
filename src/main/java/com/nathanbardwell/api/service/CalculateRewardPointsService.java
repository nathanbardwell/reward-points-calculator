package com.nathanbardwell.api.service;

import com.nathanbardwell.api.dto.CalculateRewardPointsRequest;
import com.nathanbardwell.api.dto.CalculateRewardPointsResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculateRewardPointsService {

    public CalculateRewardPointsResponse calculateRewardPoints(CalculateRewardPointsRequest rewardPointsRequest) {
        return new CalculateRewardPointsResponse();
    }

}
