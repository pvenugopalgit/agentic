Feature: Text Comparison Tool Part2
  As a user
  I want advanced comparison options and robust handling of edge cases
  So that I can compare large, varied, or messy texts reliably

  Scenario: Compare two very large text inputs for performance
    Given the user has entered a very large text A into the first text box
    And the user has entered a very large text B into the second text box
    When the user clicks the "Compare" button
    Then the tool returns a comparison result within an acceptable time
    And the result correctly identifies differences between the texts

  Scenario: Case sensitivity option respects user preference
    Given the user has entered text A into the first text box
    And the user has entered text B with different casing into the second text box
    When the user enables the "ignore case" option and clicks "Compare"
    Then the tool treats differently-cased characters as equal
    When the user disables the "ignore case" option and clicks "Compare"
    Then the tool highlights case differences

  Scenario: Ignore whitespace differences
    Given the user has entered text A with extra whitespace into the first text box
    And the user has entered text B without the extra whitespace into the second text box
    When the user enables the "ignore whitespace" option and clicks "Compare"
    Then the tool reports the texts as equivalent

  Scenario: Handle Unicode and multilingual text correctly
    Given the user has entered text A in one language into the first text box
    And the user has entered text B in another language or using combining characters into the second text box
    When the user clicks the "Compare" button
    Then the tool correctly handles Unicode normalization and highlights real differences only

  Scenario: Provide a concise summary of differences
    Given the user has entered text A into the first text box
    And the user has entered text B into the second text box
    When the user clicks the "Compare" button
    Then the tool outputs a summary showing added, removed, and changed counts
