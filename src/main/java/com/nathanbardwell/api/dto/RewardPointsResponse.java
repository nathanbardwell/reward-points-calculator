package com.nathanbardwell.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RewardPointsResponse {

	private List<CustomerRewardPoints> customerRewardPoints;

}
