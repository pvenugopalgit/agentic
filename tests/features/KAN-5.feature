Feature: Detect differences between two text inputs
  As a user
  I want the tool to detect when two inputs are not identical
  So that I know the differences exist

  Scenario: System identifies two different inputs
    Given the user has provided two text inputs
    When the inputs are not identical
    Then the system identifies both inputs are not identical

  Scenario: Differences are highlighted in the output
    Given the user has provided two different text inputs
    When the comparison is performed
    Then differences are highlighted

  Scenario: Confirmation message for non-matching texts
    Given the user has provided two text inputs
    When the inputs do not match exactly
    Then a message confirms the texts do not match exactly
