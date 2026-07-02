Feature: Text Comparison Tool Part 3 - Clear Functionality
  As a user of the Testonics Text Comparator
  I want to clear the text inputs after a comparison
  So that I can start a new comparison without residual text

  Background:
    Given I navigate to the Text Comparator page

  @smoke @KAN-6 @TC-KAN6-01
  Scenario: Clear button removes text after comparing identical texts
    When I enter "Hello World. This is identical text." in the original text area
    And I enter "Hello World. This is identical text." in the revised text area
    And I click the Compare button
    Then no differences should be highlighted
    When I click the Clear button
    Then the original text area should be empty
    And the revised text area should be empty

  @regression @KAN-6 @TC-KAN6-02
  Scenario: Clear button removes text after comparing different texts
    When I enter "The quick brown fox jumps over the lazy dog." in the original text area
    And I enter "The quick red fox jumps over the lazy cat." in the revised text area
    And I click the Compare button
    Then differences should be highlighted
    When I click the Clear button
    Then the original text area should be empty
    And the revised text area should be empty

  @regression @KAN-6 @TC-KAN6-03
  Scenario: Clear button removes text without clicking Compare
    When I enter "Some text entered by the user." in the original text area
    And I enter "Different text entered by the user." in the revised text area
    When I click the Clear button
    Then the original text area should be empty
    And the revised text area should be empty
