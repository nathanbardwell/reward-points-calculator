package com.nathanbardwell.api.controller;

import com.nathanbardwell.api.dto.RewardPointsResponse;
import com.nathanbardwell.api.dto.TransactionRecordsRequest;
import com.nathanbardwell.api.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/rewards")
public class RewardsController {

	private final RewardsService rewardsService;

	@Autowired
	public RewardsController(RewardsService rewardsService) {
		this.rewardsService = rewardsService;
	}

	@GetMapping("/calculate-points")
	public ResponseEntity<RewardPointsResponse> calculatePoints(TransactionRecordsRequest request) {
		RewardPointsResponse response = rewardsService.calculateRewardsPoints(request);

		return ResponseEntity.ok(response);
	}

}
