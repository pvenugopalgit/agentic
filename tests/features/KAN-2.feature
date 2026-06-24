Feature: AI-Based XPath Generator Agent
  As a QA automation engineer
  I want an AI agent that generates robust XPath locators for web elements
  So that I can create stable, maintainable Playwright test automation scripts

  Scenario: Generate XPath with multiple strategies
    Given the user provides an HTML snippet
    When the AI agent generates an XPath
    Then it should provide at least three strategies:
      | Strategy      |
      | Absolute XPath |
      | Relative XPath |
      | Attribute-based XPath |

  Scenario: Generate XPath avoiding dynamic attributes
    Given the user provides an HTML snippet with elements containing dynamic attributes
    When the AI agent analyzes the element for dynamic patterns
    Then it should avoid using dynamic attributes in the XPath
    And it should generate a stable, maintainable XPath using alternative attributes or DOM hierarchy

  Scenario: Validate XPath before returning
    Given the AI agent generates an XPath
    When the XPath is executed against the provided DOM
    Then the agent should verify that the XPath returns at least one element
    And if the XPath is invalid, the agent should regenerate a corrected version

  Scenario: Explain the XPath logic
    Given the AI agent generates an XPath
    When the user requests an explanation
    Then the agent should provide clear reasoning for the XPath construction
    And it should explain why specific strategies were chosen or rejected
    And it should document the expected element matching behavior

  Scenario: Return structured output for tool integration
    Given the AI agent generates XPath locators
    When the output is formatted for Playwright
    Then it should return a JSON structure with:
      | Field      | Content                          |
      | locator    | The generated XPath string       |
      | type       | The type of XPath (absolute/relative/attribute-based) |
      | reasoning  | Explanation for the XPath choice |
      | alternatives | List of alternative XPath options |
