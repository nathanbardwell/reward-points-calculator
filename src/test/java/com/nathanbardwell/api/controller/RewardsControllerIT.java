package com.nathanbardwell.api.controller;


import com.nathanbardwell.api.RewardPointsCalculatorApplication;
import com.nathanbardwell.api.dto.CalculateRewardPointsRequest;
import com.nathanbardwell.api.dto.CalculateRewardPointsResponse;
import com.nathanbardwell.api.dto.TransactionRecord;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RewardPointsCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardsControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    void testCalculatePoints() {
        TransactionRecord transaction1 = new TransactionRecord();
        transaction1.setCustomerId("12345");
        transaction1.setTransactionAmount(BigDecimal.valueOf(55.00));
        transaction1.setTransactionDate(LocalDate.of(2022, 10, 1));

        TransactionRecord transaction2 = new TransactionRecord();
        transaction2.setCustomerId("54321");
        transaction2.setTransactionAmount(BigDecimal.valueOf(75.00));
        transaction2.setTransactionDate(LocalDate.of(2022, 11, 1));

        CalculateRewardPointsRequest request = new CalculateRewardPointsRequest();
        request.setTransactions(List.of(transaction1, transaction2));

        HttpEntity<CalculateRewardPointsRequest> requestEntity = new HttpEntity<>(request, headers);

        String url = createURLWithPort("/api/v1/rewards/calculate-points");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class);

        String expected = "{\"customerRewardPoints\":[{\"customerId\":\"54321\",\"pointsTotal\":25,\"pointsByMonth\":{\"Nov 2022\":25}},{\"customerId\":\"12345\",\"pointsTotal\":5,\"pointsByMonth\":{\"Oct 2022\":5}}]}";

        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(expected);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
