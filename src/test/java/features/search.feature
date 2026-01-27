Feature: Search Functionality

  Background:
    Given user is on the job search website

  @happy
  Scenario: Basic Search - Only title and location
    When user enters title and location in search
    Then user gets a list of jobs for preferred parameters
    And  user can sort by date

  @happy
  Scenario: Advanced Search - with job reference and or employer details
    When advanced user enters preferences in search
    Then user gets a list of jobs for preferred parameters
    And  user can sort by date

  @unhappy
  Scenario: Advanced Search- with invalid job-ref
    When advanced user enters invalid job-ref preferences in search
    Then user gets a message no job found

  @unhappy
  Scenario: No results for given title
    When no jobs match my preferences
    Then user gets a message no job found

  @happy
  Scenario: Clicking on filter should clear all the fields/default values if any should be visible
    And I type only title and location
    When I click on clear filter button
    Then All fields should be cleared or default values should be visible

  @ui
  Scenario: Distance field is disabled initially and enabled after entering Location
    Given the Distance field is disabled
    When I enter location "London"
    Then the Distance field should be enabled

  @ui
  Scenario: Verify all options in Distance dropdown
    Then the Distance dropdown should contain the following options:
      | +5 Miles   |
      | +10 Miles  |
      | +20 Miles  |
      | +30 Miles  |
      | +50 Miles  |
      | +100 Miles |