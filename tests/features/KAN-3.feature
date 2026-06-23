Feature: Text Comparison Tool
  As a user
  I want to compare two text inputs
  So that I can identify differences, similarities, and mismatches accurately and quickly

  Scenario: Compare two texts to determine equality
    Given the user has entered text A into the first text box
    And the user has entered text B into the second text box
    When the user clicks the "Compare" button
    Then the tool indicates whether the texts are identical
    And if not identical, highlights the exact differences
