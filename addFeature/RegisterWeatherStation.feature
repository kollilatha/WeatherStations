@registerStation
Feature: Add weather station 

Scenario: Add weather station with valid key
Given Adding new station
When  we give request  with invalid detials
And   we give request  with detials
Then the station shoukd be created

