package com.nathanbardwell.api.controller;

import com.nathanbardwell.api.dto.CalculateRewardPointsRequest;
import com.nathanbardwell.api.dto.CalculateRewardPointsResponse;
import com.nathanbardwell.api.service.CalculateRewardPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardsController {

    private final CalculateRewardPointsService calculateRewardPointsService;

    @Autowired
    public RewardsController(CalculateRewardPointsService calculateRewardPointsService) {
        this.calculateRewardPointsService = calculateRewardPointsService;
    }

    @PostMapping("/calculate-points")
    public ResponseEntity<CalculateRewardPointsResponse> calculatePoints(@RequestBody CalculateRewardPointsRequest request) {
        CalculateRewardPointsResponse response = calculateRewardPointsService.calculateRewardPoints(request);

        return ResponseEntity.ok(response);
    }


}
