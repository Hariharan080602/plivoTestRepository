Feature: Open weather automation to predict the coldest and humidest cities

  Scenario: Get top three cold and humid cities
    Given the user gives the city name 'Bangalore,Chennai,Mumbai,Kolkata,Delhi' to find the latitude and longitude
    Then the user finds the top three humid cities from by comparing the humid values from excel
    Then the user finds the top three cold cities from by comparing the humid values from excel