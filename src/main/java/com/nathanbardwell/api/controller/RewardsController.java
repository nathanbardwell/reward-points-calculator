package com.nathanbardwell.api.controller;

import com.nathanbardwell.api.dto.RewardPointsResponse;
import com.nathanbardwell.api.dto.TransactionRecordsRequest;
import com.nathanbardwell.api.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1.0/rewards")
public class RewardsController {

	private final RewardsService rewardsService;

	@Autowired
	public RewardsController(RewardsService rewardsService) {
		this.rewardsService = rewardsService;
	}

	@GetMapping("/calculate-points")
	public ResponseEntity<RewardPointsResponse> calculatePoints(@RequestBody TransactionRecordsRequest request,
	                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	                                                            @RequestParam LocalDate dateRangeStart,
	                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	                                                            @RequestParam LocalDate dateRangeEnd) {
		RewardPointsResponse response = rewardsService.calculateRewardsPoints(request, dateRangeStart, dateRangeEnd);

		return ResponseEntity.ok(response);
	}

}
