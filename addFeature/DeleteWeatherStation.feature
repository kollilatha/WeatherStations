
Feature: Delete Operation
@deletestation
Scenario: Deleting Weather Station
Given User is with endpoint and id
When User sends the request
Then Delete that Weather Station 
@getNoContent
Scenario: Deleting already deleted station
Given User is with endpoint and id
Then  we should get no content found
