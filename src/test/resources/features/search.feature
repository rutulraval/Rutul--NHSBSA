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

  @ui
  Scenario: Verify placeholder text and default dropdown values after expanding more search options
    When I click on the More search options link
    Then the What field should display hint text "For example, job title or skills"
    And the Distance dropdown should be disabled with "All locations" selected by default
    And the Pay Range dropdown should have "Please select" option selected by default

  @happy
  Scenario Outline: Fill out NHS job search form and verify field selections
    When I enter job title "<jobTitle>"
    And I enter location "<location>"
    And I select "<distance>" in the Distance dropdown
    And I click on the More search options link
    And I enter job reference "<jobReference>"
    And I enter employer "<employer>"
    And I select "<payRange>" from the Pay Range dropdown
    And I click on the Search button
    Then I wait for search results to be displayed
    And the sort by dropdown should have "Best Match" selected by default
    When I select "Date Posted (newest)" from the sort by dropdown
    Then the sort by dropdown should have "Date Posted (newest)" selected
    Then the Job Title field should contain "<jobTitle>"
    And the Location field should contain "<location>"
    And the Job Reference field should contain "<jobReference>"
    And the Employer field should contain "<employer>"

    Examples:
      | jobTitle | location   | distance  | jobReference      | employer  | payRange           |
      | Nurse    | London     | +10 Miles | 364-A-9725        | NHS Trust | £20,000 to £30,000 |
      | Doctor   | Manchester | +20 Miles |                   |           | £30,000 to £40,000 |

  @unhappy
  Scenario Outline: Search with invalid inputs and verify error messages
    When I enter job title "<jobTitle>"
    And I enter location "<location>"
    And I select "<distance>" in the Distance dropdown
    And I click on the More search options link
    And I enter job reference "<jobReference>"
    And I enter employer "<employer>"
    And I select "<payRange>" from the Pay Range dropdown
    And I click on the Search button
    Then I should see error message "<expectedMessage>"

    Examples:
      | jobTitle      | location   | distance   | jobReference      | employer      | payRange            | expectedMessage    |
      | Nurse         | Londn      | +10 Miles  | 200-7220253-GO-LP | NHS Trust     | £20,000 to £30,000  | Location not found |
      | Test Manager  | Bristol    | +50 Miles  | FAKE-REF-999      | NHS Trust     | £70,000 to £80,000  | No result found    |
      | Nurse         | London     | +10 Miles  | 200-7220253-123   | Fake Employer | £20,000 to £30,000  | No result found    |
      | Nurse         | -*&()      | +10 Miles  | 200-7220253-GO-LP | NHS Trust     | £90,000 to £100,000 | Location not found |
      | RandomFakeJob | Bath       | +100 Miles | 1234              |               | £100,000 plus       | No result found    |

  @happy
  Scenario: Search with all fields empty
    When I enter job title ""
    And the Distance dropdown should be disabled with "All locations" selected by default
    And I click on the More search options link
    And I enter job reference ""
    And I enter employer ""
    And I select "Please select" from the Pay Range dropdown
    And I click on the Search button
    Then I wait for search results to be displayed
    And I should see all available job results

  @unhappy
  Scenario: Unhappy path - Invalid input returns no suggestions
    When I enter job title "=-!¬"
    And I enter "XYZ1234567" into the location field
    Then I should see no suggestions