Feature: Google Search

  As a web user
  I want to search for information using Google
  So that I can find relevant results

  Scenario: Search for OpenAI
    Given I am on the Google homepage
    When I search for "OpenAI"
    Then the page title should contain "OpenAI"
