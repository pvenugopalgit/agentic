# Playwright Java Test Automation Framework

Automated test suite for **[Testonics Text Comparator](https://www.testonics.in/text-compartor)** using Playwright Java with JUnit 5, Page Object Model, and Allure reporting.

---

## Project Structure

```
playwright-java-automation/
├── src/
│   ├── main/
│   │   ├── java/com/testonics/
│   │   │   ├── config/
│   │   │   │   └── ConfigManager.java       # Centralized config reader
│   │   │   ├── pages/
│   │   │   │   └── TextComparatorPage.java  # Page Object for Text Comparator
│   │   │   └── utils/
│   │   │       └── PlaywrightUtils.java     # Reusable browser utilities
│   │   └── resources/
│   │       ├── config.properties            # App & browser configuration
│   │       └── logback.xml                  # Logging configuration
│   └── test/
│       └── java/com/testonics/
│           ├── base/
│           │   └── BaseTest.java            # JUnit lifecycle + browser setup
│           └── tests/
│               └── TextComparatorTest.java  # 13 test cases
├── .vscode/
│   ├── mcp.json                             # Playwright MCP server config
│   └── settings.java                        # VS Code Java settings
└── pom.xml                                  # Maven dependencies
```

---

## Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+ (for MCP server)

---

## Setup

### 1. Install Playwright browsers
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### 2. Install MCP server dependencies
```bash
npx @playwright/mcp@latest
```

---

## Running Tests

### Run all tests
```bash
mvn test
```

### Run with specific browser
```bash
mvn test -Dbrowser=firefox
```

### Run headless
```bash
mvn test -Dheadless=true
```

### Run a specific test class
```bash
mvn test -Dtest=TextComparatorTest
```

### Run a single test method
```bash
mvn test -Dtest=TextComparatorTest#testCompareIdenticalTexts
```

---

## Generate Allure Report

```bash
mvn allure:serve
```

---

## Test Cases

| ID     | Test Name                              | Severity |
|--------|----------------------------------------|----------|
| TC001  | Verify page loads successfully         | Blocker  |
| TC002  | Verify text areas are visible          | Critical |
| TC003  | Verify Compare button is visible       | Critical |
| TC004  | Enter text in original area            | Normal   |
| TC005  | Enter text in revised area             | Normal   |
| TC006  | Compare identical texts                | Critical |
| TC007  | Compare different texts                | Critical |
| TC008  | Compare with added lines               | Normal   |
| TC009  | Compare with removed lines             | Normal   |
| TC010  | Clear button resets both areas         | Normal   |
| TC011  | Compare with empty texts               | Minor    |
| TC012  | Compare with special characters        | Minor    |
| TC013  | Compare multiline text blocks          | Minor    |

---

## MCP Server (GitHub Copilot Integration)

The `.vscode/mcp.json` registers the **Playwright MCP server**, enabling GitHub Copilot to interact with the browser directly during development and debugging.

To use, open the **MCP** panel in VS Code and start the `playwright` server.

---

## Configuration (`config.properties`)

| Key               | Default                                          | Description             |
|-------------------|--------------------------------------------------|-------------------------|
| `base.url`        | `https://www.testonics.in/text-compartor`        | Application URL         |
| `browser`         | `chromium`                                       | Browser to use          |
| `headless`        | `false`                                          | Run headless            |
| `default.timeout` | `30000`                                          | Element timeout (ms)    |
| `screenshot.dir`  | `target/screenshots`                             | Screenshot output path  |
| `video.dir`       | `target/videos`                                  | Video recording path    |
