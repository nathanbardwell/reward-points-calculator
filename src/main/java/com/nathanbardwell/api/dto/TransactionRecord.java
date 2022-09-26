package com.nathanbardwell.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRecord {

	private String customerId;
	private String transactionId;
	private LocalDate purchaseDate;
	private BigDecimal purchaseAmount;

}
