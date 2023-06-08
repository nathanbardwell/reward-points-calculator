package com.nathanbardwell.api.service;

import com.nathanbardwell.api.dto.CalculateRewardPointsRequest;
import com.nathanbardwell.api.dto.CalculateRewardPointsResponse;
import com.nathanbardwell.api.dto.CustomerRewardPoints;
import com.nathanbardwell.api.dto.TransactionRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculateRewardPointsService {

    public CalculateRewardPointsResponse calculateRewardPoints(CalculateRewardPointsRequest rewardPointsRequest) {
        Map<String, List<TransactionRecord>> customerTransactions = organizeTransactionsByCustomerId(rewardPointsRequest.getTransactions());

        List<CustomerRewardPoints> customerRewardPoints = customerTransactions.entrySet().stream()
                .map(entry -> calculateCustomerRewardPoints(entry.getKey(), entry.getValue()))
                .toList();

        return new CalculateRewardPointsResponse(customerRewardPoints);
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
        Map<String, Integer> monthlyRewardPoints = calculateMonthlyCustomerRewardPoints(transactions);
        int totalPoints = monthlyRewardPoints.values().stream().mapToInt(i -> i).sum();

        return new CustomerRewardPoints(customerId, totalPoints, monthlyRewardPoints);
    }

    protected Map<String, Integer> calculateMonthlyCustomerRewardPoints(List<TransactionRecord> transactions) {
        Map<String, Integer> monthlyRewardPoints = new HashMap<>();

        for (TransactionRecord transaction : transactions) {
            String transactionMonthYear = transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("LLL yyyy"));
            int transactionPointsEarned = calculatePointsForTransactionAmount(transaction.getTransactionAmount());

            if (monthlyRewardPoints.containsKey(transactionMonthYear)) {
                int newPointsTotal = monthlyRewardPoints.get(transactionMonthYear) + transactionPointsEarned;
                monthlyRewardPoints.put(transactionMonthYear, newPointsTotal);
            } else {
                monthlyRewardPoints.put(transactionMonthYear, transactionPointsEarned);
            }
        }

        return monthlyRewardPoints;
    }

    protected int calculatePointsForTransactionAmount(BigDecimal transactionAmount) {
        int transactionDollarAmount = transactionAmount.intValue();
        int totalPoints = 0;

        if (transactionDollarAmount > 50) {
            int dollarsOver50 = transactionDollarAmount - 50;
            totalPoints += dollarsOver50;
        }
        if (transactionDollarAmount > 100) {
            int dollarsOver100 = transactionDollarAmount - 100;
            totalPoints += dollarsOver100;
        }

        return totalPoints;
    }

}
