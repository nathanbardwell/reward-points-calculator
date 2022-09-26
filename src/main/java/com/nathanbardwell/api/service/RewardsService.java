package com.nathanbardwell.api.service;

import com.nathanbardwell.api.dto.CustomerRewardPoints;
import com.nathanbardwell.api.dto.RewardPointsResponse;
import com.nathanbardwell.api.dto.TransactionRecord;
import com.nathanbardwell.api.dto.TransactionRecordsRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardsService {

	public RewardPointsResponse calculateRewardsPoints(TransactionRecordsRequest request, LocalDate dateRangeStart, LocalDate dateRangeEnd) {
		List<TransactionRecord> transactions = filterTransactionsByDateRange(request.getTransactions(), dateRangeStart, dateRangeEnd);

		Map<String, List<TransactionRecord>> customerTransactions = organizeTransactionsByCustomerId(transactions);

		List<CustomerRewardPoints> customerRewardPoints = customerTransactions.entrySet().stream()
				.map(entry -> calculateCustomerRewardPoints(entry.getKey(), entry.getValue()))
				.toList();

		return new RewardPointsResponse(customerRewardPoints);
	}

	protected List<TransactionRecord> filterTransactionsByDateRange(List<TransactionRecord> transactions, LocalDate start, LocalDate end) {
		return transactions.stream()
				.filter(txn -> !txn.getPurchaseDate().isBefore(start))
				.filter(txn -> !txn.getPurchaseDate().isAfter(end))
				.toList();
	}

	protected Map<String, List<TransactionRecord>> organizeTransactionsByCustomerId(List<TransactionRecord> transactions) {
		HashMap<String, List<TransactionRecord>> customerTransactions = new HashMap<>();

		for (TransactionRecord transaction : transactions) {
			String customerId = transaction.getCustomerId();

			if (customerTransactions.containsKey(customerId)) {
				customerTransactions.get(customerId).add(transaction);
			} else {
				List<TransactionRecord> transactionRecords = new ArrayList<>();
				transactionRecords.add(transaction);
				customerTransactions.put(customerId, transactionRecords);
			}
		}

		return customerTransactions;
	}

	protected CustomerRewardPoints calculateCustomerRewardPoints(String customerId, List<TransactionRecord> transactions) {
		Map<String, Integer> monthlyRewardPoints = calculateMonthlyRewardPoints(transactions);
		int totalPoints = monthlyRewardPoints.values().stream().mapToInt(i -> i).sum();

		return new CustomerRewardPoints(customerId, totalPoints, monthlyRewardPoints);
	}

	protected Map<String, Integer> calculateMonthlyRewardPoints(List<TransactionRecord> transactions) {
		Map<String, Integer> monthlyRewardPoints = new HashMap<>();

		for (TransactionRecord transaction : transactions) {
			String transactionMonthYear = transaction.getPurchaseDate().format(DateTimeFormatter.ofPattern("LLL yyyy"));
			int transactionPointsEarned = calculatePointsForPurchaseAmount(transaction.getPurchaseAmount());

			if (monthlyRewardPoints.containsKey(transactionMonthYear)) {
				int newPointsTotal = monthlyRewardPoints.get(transactionMonthYear) + transactionPointsEarned;
				monthlyRewardPoints.put(transactionMonthYear, newPointsTotal);
			} else {
				monthlyRewardPoints.put(transactionMonthYear, transactionPointsEarned);
			}
		}

		return monthlyRewardPoints;
	}

	protected int calculatePointsForPurchaseAmount(BigDecimal purchaseAmount) {
		int purchaseDollarAmount = purchaseAmount.intValue();
		int totalPoints = 0;

		if (purchaseDollarAmount > 50) {
			int dollarsOver50 = purchaseDollarAmount - 50;
			totalPoints += dollarsOver50;
		}
		if (purchaseDollarAmount > 100) {
			int dollarsOver100 = purchaseDollarAmount - 100;
			totalPoints += dollarsOver100;
		}

		return totalPoints;
	}
}
