Feature: Text Comparison Tool Part 1
  As a user of the Testonics Text Comparator
  I want to compare two pieces of text
  So that I can identify whether the texts are identical or spot exact differences

  Background:
    Given I navigate to the Text Comparator page

  @smoke @KAN-4 @TC-KAN4-01
  Scenario: Compare two identical text inputs
    When I enter "Hello World. This is identical text." in the original text area
    And I enter "Hello World. This is identical text." in the revised text area
    And I click the Compare button
    Then no differences should be highlighted

  @regression @KAN-4 @TC-KAN4-02
  Scenario: Compare two texts with minor differences highlights changes
    When I enter "The quick brown fox jumps over the lazy dog." in the original text area
    And I enter "The quick red fox jumps over the lazy cat." in the revised text area
    And I click the Compare button
    Then differences should be highlighted
