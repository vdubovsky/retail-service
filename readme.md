# Simple drools based bonus points calculation service

## Getting started

- git clone https://github.com/vdubovsky/retail-service
- mvn clean package
- java -jar ./target/retail-rules-0.0.1-SNAPSHOT.jar

# Curl example
```
curl --location --request POST 'http://localhost:8080/api/v1/transactions/bonuses' \
--header 'Authorization: Basic a2llc2VydmVyOmtpZXNlcnZlcjEh' \
--header 'Content-Type: application/json' \
--data-raw '[
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "928725ef-6dfe-4c00-90d8-275866777632",
"amount": 51.03,
"executedAt": "2023-01-12T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "928725ef-6dfe-4c00-90d8-275866777632",
"amount": 99.99,
"executedAt": "2023-01-11T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "928725ef-6dfe-4c00-90d8-275866777632",
"amount": 48,
"executedAt": "2022-12-30T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "432325ef-6dfe-4c00-90d8-275866777632",
"amount": 21.03,
"executedAt": "2023-01-12T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "432325ef-6dfe-4c00-90d8-275866777632",
"amount": 39.99,
"executedAt": "2023-01-11T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "928725ef-6dfe-4c00-90d8-275866777632",
"amount": 101.01,
"executedAt": "2022-11-30T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "112325ef-6dfe-4c00-90d8-275866777632",
"amount": 121.03,
"executedAt": "2023-01-12T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "112325ef-6dfe-4c00-90d8-275866777632",
"amount": 252.99,
"executedAt": "2023-01-11T12:00:00"
},
{
"id": "654725ef-6dfe-4c00-90d8-275866777632",
"userId": "928725ef-6dfe-4c00-90d8-275866777632",
"amount": 100.00,
"executedAt": "2022-10-30T12:00:00"
}
]'
```