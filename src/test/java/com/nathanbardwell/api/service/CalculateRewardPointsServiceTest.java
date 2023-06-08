package com.nathanbardwell.api.service;

import com.nathanbardwell.api.dto.CalculateRewardPointsRequest;
import com.nathanbardwell.api.dto.CalculateRewardPointsResponse;
import com.nathanbardwell.api.dto.CustomerRewardPoints;
import com.nathanbardwell.api.dto.TransactionRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculateRewardPointsServiceTest {

    CalculateRewardPointsService calculateRewardPointsService;

    @BeforeEach
    void setup() {
        calculateRewardPointsService = new CalculateRewardPointsService();
    }

    @Test
    void testCalculateRewardPoints() {
        TransactionRecord transaction1 = new TransactionRecord();
        transaction1.setCustomerId("12345");
        transaction1.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction1.setTransactionDate(LocalDate.of(2022, 10, 1));

        TransactionRecord transaction2 = new TransactionRecord();
        transaction2.setCustomerId("54321");
        transaction2.setTransactionAmount(BigDecimal.valueOf(75.00));
        transaction2.setTransactionDate(LocalDate.of(2022, 11, 1));

        List<TransactionRecord> transactions = List.of(transaction1, transaction2);
        CalculateRewardPointsRequest request = new CalculateRewardPointsRequest();
        request.setTransactions(transactions);

        CalculateRewardPointsResponse actual = calculateRewardPointsService.calculateRewardPoints(request);

        assertThat(actual.getCustomerRewardPoints()).hasSize(2)
                .contains(new CustomerRewardPoints("12345", 5, Map.of("Oct 2022", 5)))
                .contains(new CustomerRewardPoints("54321", 25, Map.of("Nov 2022", 25)));
    }

    @Test
    void testOrganizeTransactionsByCustomer() {
        TransactionRecord transaction1 = new TransactionRecord();
        transaction1.setCustomerId("12345");
        transaction1.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction1.setTransactionDate(LocalDate.of(2022, 10, 1));

        TransactionRecord transaction2 = new TransactionRecord();
        transaction2.setCustomerId("54321");
        transaction2.setTransactionAmount(BigDecimal.valueOf(75.00));
        transaction2.setTransactionDate(LocalDate.of(2022, 11, 1));

        List<TransactionRecord> transactions = List.of(transaction1, transaction2);

        Map<String, List<TransactionRecord>> actual = calculateRewardPointsService.organizeTransactionsByCustomerId(transactions);

        assertThat(actual).hasSize(2)
                .containsEntry("12345", List.of(transaction1))
                .containsEntry("54321", List.of(transaction2));
    }

    @Test
    void testCalculateCustomerRewardPoints() {
        TransactionRecord transaction1 = new TransactionRecord();
        transaction1.setCustomerId("12345");
        transaction1.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction1.setTransactionDate(LocalDate.of(2022, 10, 1));

        TransactionRecord transaction2 = new TransactionRecord();
        transaction2.setCustomerId("12345");
        transaction2.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction2.setTransactionDate(LocalDate.of(2022, 10, 1));

        TransactionRecord transaction3 = new TransactionRecord();
        transaction3.setCustomerId("12345");
        transaction3.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction3.setTransactionDate(LocalDate.of(2022, 9, 1));

        TransactionRecord transaction4 = new TransactionRecord();
        transaction4.setCustomerId("12345");
        transaction4.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction4.setTransactionDate(LocalDate.of(2022, 8, 1));

        List<TransactionRecord> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        CustomerRewardPoints actual = calculateRewardPointsService.calculateCustomerRewardPoints("12345", transactions);

        assertThat(actual.getCustomerId()).isEqualTo("12345");
        assertThat(actual.getPointsByMonth()).hasSize(3);
        assertThat(actual.getPointsTotal()).isEqualTo(20);
    }

    @Test
    void testCalculateMonthlyRewardPoints() {
        TransactionRecord transaction1 = new TransactionRecord();
        transaction1.setCustomerId("12345");
        transaction1.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction1.setTransactionDate(LocalDate.of(2022, 10, 1));

        TransactionRecord transaction2 = new TransactionRecord();
        transaction2.setCustomerId("12345");
        transaction2.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction2.setTransactionDate(LocalDate.of(2022, 10, 1));

        TransactionRecord transaction3 = new TransactionRecord();
        transaction3.setCustomerId("12345");
        transaction3.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction3.setTransactionDate(LocalDate.of(2022, 9, 1));

        TransactionRecord transaction4 = new TransactionRecord();
        transaction4.setCustomerId("12345");
        transaction4.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction4.setTransactionDate(LocalDate.of(2022, 8, 1));

        List<TransactionRecord> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        Map<String, Integer> expected = Map.of(
                "Oct 2022", 10,
                "Sep 2022", 5,
                "Aug 2022", 5);

        Map<String, Integer> actual = calculateRewardPointsService.calculateMonthlyCustomerRewardPoints(transactions);

        assertThat(actual).hasSize(3)
                .containsAllEntriesOf(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "25.00, 0",
            "50.00, 0",
            "75.00, 25",
            "100.00, 50",
            "120.00, 90"
    })
    void testCalculatePointsForTransactionAmount(BigDecimal TransactionAmount, int expected) {
        int actual = calculateRewardPointsService.calculatePointsForTransactionAmount(TransactionAmount);
        assertThat(expected).isEqualTo(actual);
    }

}
