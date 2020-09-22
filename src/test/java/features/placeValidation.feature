Feature: Validating Place API

@AddPlace @Regression
Scenario Outline: Verify if Place is bening Successfully added
Given Add Place Playload with "<name>" "<language>" "<address>"
When user calls "addPlaceAPI" with "Post" HTTP Request
Then The API Call got success with Status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And Verify place ID created maps to "<name>" using "getPlaceAPI"


Examples:
	|name 			|language	|address				|
	|myName			|English	|My Home Address		|
#	|mySecondName	|French		|My French Home Address	|

@DeletePlace
Scenario: Verify if delete place functionality is working
Given DeletePlace payload
When user calls "deletePlaceAPI" with "Post" HTTP Request
Then The API Call got success with Status code 200
And "status" in response body is "OK"
