Feature: App performance

  Scenario: Add task response time
    When I add a task
    Then the task should appear within 200 milliseconds

Feature: App usability

  Scenario: Mobile screen compatibility
    Given I use the app on a phone with 360x640 resolution
    Then all UI elements should be visible and usable without scrolling horizontally

Feature: Data privacy

  Scenario: Tasks are user-specific
    Given user A adds a task "Confidential"
    And user B logs in
    Then user B should not see task "Confidential"
