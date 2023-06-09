## Requirements

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three-month period, calculate the reward points earned for each customer per month and total.

* Solve using Spring Boot
* Create a RESTful endpoint
* Make up a data set to best demonstrate your solution
* Check solution into GitHub

# API Doc

## Calculate Reward Points

Given a list of transactions, will calculate reward points earned per customer (total and by month).

`POST /api/v1/rewards/calculate-points`

### Headers
None

### Path Parameters
None

### Request Schema
<sub>application/json</sub>

#### TransactionRecordsRequest
| Field          | Description                          |
|----------------|--------------------------------------|
| `transactions` | Array of objects (TransactionRecord) |

#### TransactionRecord
|                     |                   |
|---------------------|-------------------|
| `customerId`        | String            |
| `transactionDate`   | Date (YYYY-mm-DD) |
| `transactionAmount` | Decimal           |

#### Example request
```json
{
  "transactions": [
    {
      "customerId": "customer1",
      "transactionDate": "2022-08-01",
      "transactionAmount": "50.00"
    },
    {
      "customerId": "customer1",
      "transactionDate": "2022-09-01",
      "transactionAmount": "75.00"
    },
    {
      "customerId": "customer1",
      "transactionDate": "2022-10-01",
      "transactionAmount": "120.00"
    },
    {
      "customerId": "customer2",
      "transactionDate": "2022-08-01",
      "transactionAmount": "50.00"
    },
    {
      "customerId": "customer2",
      "transactionDate": "2022-09-01",
      "transactionAmount": "75.00"
    },
    {
      "customerId": "customer2",
      "transactionDate": "2022-10-01",
      "transactionAmount": "120.00"
    },
    {
      "customerId": "customer3",
      "transactionDate": "2022-08-01",
      "transactionAmount": "50.00"
    },
    {
      "customerId": "customer3",
      "transactionDate": "2022-09-01",
      "transactionAmount": "75.00"
    },
    {
      "customerId": "customer3",
      "transactionDate": "2022-10-01",
      "transactionAmount": "120.00"
    }
  ]
}
```

### Response Schema
<sub>application/json</br>

#### RewardPointsResponse
| Field                  | Description                              |
|------------------------|------------------------------------------|
| `customerRewardPoints` | Array of objects (CustomerRewardsPoints) |

#### CustomerRewardPoints
|                 |         |
|-----------------|---------|
| `customerId`    | String  |
| `pointsTotal`   | Integer |
| `pointsByMonth` | Object  |

#### Example response
```json
{
  "customerRewardPoints": [
    {
      "customerId": "customer1",
      "pointsTotal": 115,
      "pointsByMonth": {
        "Oct 2022": 90,
        "Sep 2022": 25,
        "Aug 2022": 0
      }
    },
    {
      "customerId": "customer2",
      "pointsTotal": 115,
      "pointsByMonth": {
        "Oct 2022": 90,
        "Sep 2022": 25,
        "Aug 2022": 0
      }
    },
    {
      "customerId": "customer3",
      "pointsTotal": 115,
      "pointsByMonth": {
        "Oct 2022": 90,
        "Sep 2022": 25,
        "Aug 2022": 0
      }
    }
  ]
}
```


### Response Status Codes
| Status code | Description |
|-------------|-------------|
| `200`       | OK          |
