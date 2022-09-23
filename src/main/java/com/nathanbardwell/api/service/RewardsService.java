package com.nathanbardwell.api.service;

import com.nathanbardwell.api.dto.RewardPointsResponse;
import com.nathanbardwell.api.dto.TransactionRecordsRequest;
import org.springframework.stereotype.Service;

@Service
public class RewardsService {

	public RewardPointsResponse calculateRewardsPoints(TransactionRecordsRequest request) {
		return new RewardPointsResponse();
	}
}
