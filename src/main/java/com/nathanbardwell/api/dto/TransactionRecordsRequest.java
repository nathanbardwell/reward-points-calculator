package com.nathanbardwell.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionRecordsRequest {

	private List<TransactionRecord> transactions;

}
