package com.nathanbardwell.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardPoints {

	private String customerId;
	private int pointsTotal;
	private Map<String, Integer> pointsByMonth;

}
