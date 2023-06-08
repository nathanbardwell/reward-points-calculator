package com.nathanbardwell.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CustomerRewardPoints {

	private String customerId;
	private int pointsTotal;
	private Map<String, Integer> pointsByMonth;

}
