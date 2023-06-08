package com.nathanbardwell.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class CalculateRewardPointsRequest {

	private List<TransactionRecord> transactions;

}
