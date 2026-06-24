Feature: Text Comparison Tool Part 3
  As a user
  I want advanced text comparison capabilities
  So that I can identify differences between text documents efficiently

  Scenario: Compare two text blocks and highlight differences
    Given I have two text blocks to compare
    When I initiate the comparison
    Then the tool displays the differences clearly
    And matching sections are highlighted in one color
    And differing sections are highlighted in another color

  Scenario: Handle large text files for comparison
    Given I have two large text documents
    When I compare them
    Then the comparison completes without performance degradation
    And the results are accurate

  Scenario: Export comparison results
    Given a comparison has been completed
    When I request to export the results
    Then the tool generates a report in the desired format
    And the export includes all identified differences
