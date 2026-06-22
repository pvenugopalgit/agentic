Feature: Text Comparison Tool Part1
  As a user
  I want a tool that compares two pieces of text
  So that I can see whether they are identical or where they differ

  Scenario: Compare two identical text inputs
    Given the user enters text A into the first text box
    And the user enters the same text A into the second text box
    When the user clicks the "Compare" button
    Then the tool displays a result indicating that both texts are identical
    And no differences are highlighted

  Scenario: Compare two texts with minor differences
    Given the user enters text A into the first text box
    And the user enters text B with slight variations into the second text box
    When the user clicks the "Compare" button
    Then the tool highlights the exact words or characters that differ
    And the result clearly shows additions, deletions, or modifications
