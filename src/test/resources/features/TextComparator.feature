Feature: Text Comparator Functionality
  As a user of the Testonics Text Comparator
  I want to compare two pieces of text
  So that I can identify differences between them

  Background:
    Given I navigate to the Text Comparator page

  @smoke @TC001
  Scenario: Page loads successfully
    Then the page title should not be empty
    And the page heading should be visible

  # @smoke @TC002
  # Scenario: Text areas are displayed on the page
  #   Then the original text area should be visible
  #   And the revised text area should be visible

  # @smoke @TC003
  # Scenario: Compare button is visible on the page
  #   Then the Compare button should be visible

  # @regression @TC004
  # Scenario: User can enter text in the original text area
  #   When I enter "This is the original text for testing." in the original text area
  #   Then the original text area should contain "This is the original text for testing."

  # @regression @TC005
  # Scenario: User can enter text in the revised text area
  #   When I enter "This is the revised text for testing." in the revised text area
  #   Then the revised text area should contain "This is the revised text for testing."

  # @regression @TC006
  # Scenario: Comparing identical texts shows no differences
  #   When I enter "Hello World. This is identical text." in the original text area
  #   And I enter "Hello World. This is identical text." in the revised text area
  #   And I click the Compare button
  #   Then no differences should be highlighted

  # @regression @TC007
  # Scenario: Comparing different texts highlights differences
  #   When I enter "The quick brown fox jumps over the lazy dog." in the original text area
  #   And I enter "The quick red fox jumps over the lazy cat." in the revised text area
  #   And I click the Compare button
  #   Then differences should be highlighted

  # @regression @TC008
  # Scenario: Added lines are highlighted when revised text has extra content
  #   When I enter the following in the original text area
  #     """
  #     Line 1
  #     Line 2
  #     """
  #   And I enter the following in the revised text area
  #     """
  #     Line 1
  #     Line 2
  #     Line 3 - newly added
  #     """
  #   And I click the Compare button
  #   Then added lines should be highlighted

  # @regression @TC009
  # Scenario: Removed lines are highlighted when original text has extra content
  #   When I enter the following in the original text area
  #     """
  #     Line 1
  #     Line 2
  #     Line 3 - to be removed
  #     """
  #   And I enter the following in the revised text area
  #     """
  #     Line 1
  #     Line 2
  #     """
  #   And I click the Compare button
  #   Then removed lines should be highlighted

  # @regression @TC010
  # Scenario: Clear button resets both text areas
  #   When I enter "Some original content" in the original text area
  #   And I enter "Some revised content" in the revised text area
  #   And I click the Clear button
  #   Then the original text area should be empty
  #   And the revised text area should be empty

  # @edge-case @TC011
  # Scenario: Comparing empty texts shows no differences
  #   When I click the Compare button
  #   Then no differences should be highlighted

  # @edge-case @TC012
  # Scenario: Comparing texts with special characters detects differences
  #   When I enter "Hello @World! #Test 100% done & ready." in the original text area
  #   And I enter "Hello @World! #Test 90% done & pending." in the revised text area
  #   And I click the Compare button
  #   Then differences should be highlighted

  # @edge-case @TC013
  # Scenario: Comparing multiline text blocks detects inline changes
  #   When I enter the following in the original text area
  #     """
  #     First line of content.
  #     Second line unchanged.
  #     Third line original.
  #     """
  #   And I enter the following in the revised text area
  #     """
  #     First line of content.
  #     Second line unchanged.
  #     Third line modified.
  #     """
  #   And I click the Compare button
  #   Then differences should be highlighted

  # @regression @TC014
  # Scenario Outline: Comparing various text pairs detects expected differences
  #   When I enter "<original>" in the original text area
  #   And I enter "<revised>" in the revised text area
  #   And I click the Compare button
  #   Then the comparison result should be "<result>"

  #   Examples:
  #     | original           | revised            | result      |
  #     | Hello World        | Hello World        | no-diff     |
  #     | Hello World        | Hello Java         | has-diff    |
  #     | foo bar baz        | foo bar qux        | has-diff    |
  #     | same text here     | same text here     | no-diff     |
