package com.nathanbardwell.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateRewardPointsResponse {

	private List<CustomerRewardPoints> customerRewardPoints;

}
