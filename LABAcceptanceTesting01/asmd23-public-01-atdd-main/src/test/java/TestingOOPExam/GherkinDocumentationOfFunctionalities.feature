Feature: Task management in the To-Do List App

  As a user
  I want to manage my tasks
  So that I can stay organized and productive

  Background:
    Given the application is running
    And I am on the task list screen

  Scenario: Add a new task
    When I enter "Buy groceries" into the new task field
    And I click "Add Task"
    Then the task "Buy groceries" should appear in the task list

  Scenario: Mark a task as completed
    Given the task "Buy groceries" exists in the list
    When I click the checkbox next to "Buy groceries"
    Then the task "Buy groceries" should be marked as completed

  Scenario: Delete a task
    Given the task "Buy groceries" exists in the list
    When I click the delete icon next to "Buy groceries"
    Then the task "Buy groceries" should be removed from the list

  Scenario: View only completed tasks
    Given tasks "Buy groceries" and "Call mom" exist in the list
    And "Buy groceries" is marked as completed
    When I filter the list by "Completed"
    Then only "Buy groceries" should be visible

  Scenario: Prevent adding empty tasks
    When I click "Add Task" without typing anything
    Then the app should show an error "Task cannot be empty"

  Scenario: Persist tasks between sessions
    Given I have added the task "Study for exam"
    When I close and reopen the app
    Then the task "Study for exam" should still be in the task list
