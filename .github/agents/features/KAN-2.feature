Feature: AI-Based XPath Generator Agent
  As a user
  I want an AI agent that generates stable XPaths and CSS selectors
  So that I can reliably identify elements in an HTML snippet and understand how they were constructed

  Scenario: Generate XPath for a unique element
    Given the user provides an HTML snippet containing a unique element
    When the AI agent analyzes the DOM structure
    Then it generates a valid XPath that uniquely identifies the element
    And the XPath returns exactly one matching node when executed
    And the agent displays the generated XPath to the user

  Scenario: Provide multiple XPath strategies
    Given the user provides an HTML snippet
    When the AI agent generates XPaths
    Then it provides at least three strategies: absolute XPath, relative XPath, and attribute-based XPath
    And each XPath is syntactically correct and valid

  Scenario: Handle dynamic attributes
    Given the HTML contains dynamic attributes such as auto-generated IDs
    When the AI agent detects unstable attributes
    Then it avoids using dynamic attributes in the XPath
    And it generates a stable, maintainable XPath using alternative attributes or DOM hierarchy

  Scenario: Validate XPath before returning
    Given the AI agent generates an XPath
    When the XPath is executed against the provided DOM
    Then the agent verifies that the XPath returns at least one element
    And if the XPath is invalid the agent regenerates a corrected version

  Scenario: Explain the XPath logic
    Given the user requests an explanation
    When the AI agent generates an XPath
    Then it provides a human-readable explanation referencing attributes, hierarchy, or patterns used

  Scenario: Error handling for invalid HTML
    Given the user provides malformed or incomplete HTML
    When the AI agent attempts to parse the input
    Then it notifies the user that the HTML is invalid
    And it provides suggestions for correcting the input

  Scenario: Generate XPath for a selected element among similar elements
    Given the HTML contains multiple similar elements and the user selects one
    When the AI agent generates an XPath for the selection
    Then the XPath uniquely identifies the selected element
    And it does not match unintended sibling elements

  Scenario: Support CSS selector output
    Given the user requests a CSS selector instead of an XPath
    When the AI agent analyzes the DOM
    Then it generates a valid CSS selector that uniquely identifies the target element

  Scenario: Performance requirement
    Given the user submits an HTML snippet
    When the AI agent processes the input
    Then it returns XPath results within 2 seconds for DOMs under 500 nodes

  Scenario: Logging and traceability
    Given the AI agent generates an XPath
    When the process completes
    Then the agent logs the input (excluding sensitive data), the generated XPath, and validation results
