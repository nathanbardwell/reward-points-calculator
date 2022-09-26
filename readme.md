# Requirements

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

* Solve using Spring Boot
* Create a RESTful endpoint
* Make up a data set to best demonstrate your solution
* Check solution into GitHub
___

# API Doc

## Calculate Reward Points

Given a list of transactions, will calculate reward points earned per customer.

`GET /api/v1.0/rewards/calculate-points`

### Headers
None

### Path Parameters
None

### Query Parameters
`dateRangeStart` <sub>date (Required)</sub>
* format: `yyyy-mm-dd`
* Earliest date in desired date range (inclusive).

`dateRangeEnd` <sub>date (Required)</sub>
* format: `yyyy-mm-dd`
* Latest date in desired date range (inclusive).

### Request Schema
<sub>application/json</sub>

#### TransactionRecordsRequest
| Field          | Description |
|----------------|----|
| `transactions` | Array of objects (TransactionRecord) |

#### TransactionRecord
|   |         |
|---|---------|
|`customerId` | String  |
| `purchaseDate` | Date    |
|`purchaseAmount` | Decimal |

#### Example request
```json
{
  "transactions": [
    {
      "customerId": "customer1",
      "purchaseDate": "2022-08-01",
      "purchaseAmount": "50.00"
    },
    {
      "customerId": "customer1",
      "purchaseDate": "2022-09-01",
      "purchaseAmount": "75.00"
    },
    {
      "customerId": "customer1",
      "purchaseDate": "2022-10-01",
      "purchaseAmount": "120.00"
    },
    {
      "customerId": "customer2",
      "purchaseDate": "2022-08-01",
      "purchaseAmount": "50.00"
    },
    {
      "customerId": "customer2",
      "purchaseDate": "2022-09-01",
      "purchaseAmount": "75.00"
    },
    {
      "customerId": "customer2",
      "purchaseDate": "2022-10-01",
      "purchaseAmount": "120.00"
    },
    {
      "customerId": "customer3",
      "purchaseDate": "2022-08-01",
      "purchaseAmount": "50.00"
    },
    {
      "customerId": "customer3",
      "purchaseDate": "2022-09-01",
      "purchaseAmount": "75.00"
    },
    {
      "customerId": "customer3",
      "purchaseDate": "2022-10-01",
      "purchaseAmount": "120.00"
    }
  ]
}
```

### Response Schema
<sub>application/json</br>

#### RewardPointsResponse
| Field                  | Description                               |
|------------------------|-------------------------------------------|
| `cusomterRewardPoints` | Array of objects (CustomerRewardsPoints)  |

#### CustomerRewardPoints
|                       |         |
|-----------------------|---------|
| `customerId`          | String  |
| `totalRewardPoints`   | Integer |
| `monthlyRewardPoints` | Object  |

#### Example response
```json
{
  "customerRewardPoints": [
    {
      "customerId": "customer1",
      "totalRewardPoints": 115,
      "monthlyRewardPoints": {
        "Oct 2022": 90,
        "Sep 2022": 25,
        "Aug 2022": 0
      }
    },
    {
      "customerId": "customer2",
      "totalRewardPoints": 115,
      "monthlyRewardPoints": {
        "Oct 2022": 90,
        "Sep 2022": 25,
        "Aug 2022": 0
      }
    },
    {
      "customerId": "customer3",
      "totalRewardPoints": 115,
      "monthlyRewardPoints": {
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
