# RESTful API to calculate real time statistics from the last 60 seconds
         
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

Building a RESTful API to calculate real time statistics from the last 60 seconds.
There will be two APIs, one of them is called every time a transaction is made. It is also the sole input of this RESTful API.
The other one returns the statistic based of the transactions of the last 60 seconds.
 
**POST /transactions**

For every transaction made, the api called and the following JSON Body is posted as request body:

{
"amount": 12.3,
"timestamp": 1478192204000
}

Where:
* amount - transaction amount - in double
* timestamp - transaction time in epoch in millis in UTC time zone (this is not current timestamp) - in long

Returns: 
Empty body with either 201 or 204.
* 201 - in case of success
* 204 - if transaction is older than 60 seconds

**GET /statistics**

This endpoint returns the statistics based on the transactions for the last 60 seconds.

Returns:
JSON Body
{
"sum": 1000,
"avg": 100,
"max": 200,
"min": 50,
"count": 10
}

Where:
* sum is a double specifying the total sum of transaction value in the last 60 seconds
* avg is a double specifying the average amount of transaction value in the last 60 seconds
* max is a double specifying single highest transaction value in the last 60 seconds
* min is a double specifying single lowest transaction value in the last 60 seconds
* count is a long specifying the total number of transactions happened in the last 60 seconds

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

### How to build and run

Execute the following command to use maven to run the JUnits and build the application and run it.

**mvn clean install spring-boot:run**

### How to access the api endpoints
Following are the curl statements to call the APIs.

**1. To create a transaction:**

**_curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{"amount": 20,"timestamp": 1478192204000 }' 'http://localhost:8080/transactions'_**

**2. To retrive statistics:**

**_curl -X GET --header 'Accept: text/plain' 'http://localhost:8080/statistics'_**

OR

You may use the Swagger UI available [here](http://localhost:8080/swagger-ui.html) to perform the operations as well.

OR

You may use any rest client to make the rest api calls.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------


Code submitted by: **Animesh Chakraborty, animeshcse@gmail.com**