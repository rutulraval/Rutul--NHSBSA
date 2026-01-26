Feature: Search Functionality

  Background:
    Given user is on the job search website

  Scenario: Basic Search - Only title and location

    When user enters title and location in search
    Then user gets a list of jobs for preferred parameters
    And  user can sort by date


  Scenario: Advanced Search - with job reference and or employer details

    When advanced user enters preferences in search
    Then user gets a list of jobs for preferred parameters
    And  user can sort by date

  Scenario: Advanced Search- with invalid job-ref
    When advanced user enters invalid job-ref preferences in search
    Then user gets a message no job found

  Scenario: No results for given title
    When no jobs match my preferences
    Then I should see a message indicating no results were found