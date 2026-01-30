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

  @ui
  Scenario: Verify all options in Pay Range dropdown
    Then the Pay Range dropdown should contain the following options:
      | Please select      |
      | £0 to £10,000      |
      | £10,000 to £20,000 |
      | £20,000 to £30,000 |
      | £30,000 to £40,000 |
      | £40,000 to £50,000 |
      | £50,000 to £60,000 |
      | £60,000 to £70,000 |
      | £70,000 to £80,000 |
      | £80,000 to £90,000 |
      | £90,000 to £100,000|
      | £100,000 plus      |

  @ui
  Scenario: Verify all options in Sort By dropdown
    When I enter location "London"
    And I click on More search options
    Then I wait for search results to be displayed
    Then the sort by dropdown should contain the following options:
      | Best Match                |
      | Closing Date             |
      | Date Posted (newest)     |
      | Salary lowest to highest |
      | Salary highest to lowest |

  @happy
  Scenario: Happy path - Valid input returns suggestions
    When I enter "Lon" into the location field
    Then I should see suggestions containing "Lon"
    And I select "London" from the suggestions