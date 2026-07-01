package com.testonics.stepdefs;

import com.microsoft.playwright.options.LoadState;
import com.testonics.config.ConfigManager;
import com.testonics.context.TestContext;
import com.testonics.pages.TextComparatorPage;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TextComparatorSteps {

    private static final Logger logger = LoggerFactory.getLogger(TextComparatorSteps.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    private final TestContext testContext;
    private TextComparatorPage page() { return testContext.getTextComparatorPage(); }

    public TextComparatorSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    // ─── Navigation ────────────────────────────────────────────────────────────

    @Given("I navigate to the Text Comparator page")
    public void navigateToTextComparatorPage() {
        testContext.getPage().navigate(config.getBaseUrl());
        testContext.getPage().waitForLoadState(LoadState.NETWORKIDLE);
        logger.info("Navigated to: {}", config.getBaseUrl());
    }

    // ─── Assertions: Page Load ──────────────────────────────────────────────────

    @Then("the page title should not be empty")
    public void pageTitleShouldNotBeEmpty() {
        String title = page().getPageTitle();
        assertNotNull(title, "Page title should not be null");
        assertFalse(title.isEmpty(), "Page title should not be empty");
    }

    @Then("the page heading should be visible")
    public void pageHeadingShouldBeVisible() {
        assertFalse(page().getHeadingText().isEmpty(), "Page heading should be visible and non-empty");
    }

    // ─── Assertions: UI Elements ────────────────────────────────────────────────

    @Then("the original text area should be visible")
    public void originalTextAreaShouldBeVisible() {
        assertTrue(page().isOriginalTextAreaVisible(), "Original text area should be visible");
    }

    @Then("the revised text area should be visible")
    public void revisedTextAreaShouldBeVisible() {
        assertTrue(page().isRevisedTextAreaVisible(), "Revised text area should be visible");
    }

    @Then("the Compare button should be visible")
    public void compareButtonShouldBeVisible() {
        assertTrue(page().isCompareButtonVisible(), "Compare button should be visible");
    }

    // ─── Actions: Text Input (inline) ───────────────────────────────────────────

    @When("I enter {string} in the original text area")
    public void enterInOriginalTextArea(String text) {
        page().enterOriginalText(text);
    }

    @When("I enter {string} in the revised text area")
    public void enterInRevisedTextArea(String text) {
        page().enterRevisedText(text);
    }

    // ─── Actions: Text Input (docstring) ────────────────────────────────────────

    @When("I enter the following in the original text area")
    public void enterMultilineInOriginalTextArea(String text) {
        page().enterOriginalText(text);
    }

    @When("I enter the following in the revised text area")
    public void enterMultilineInRevisedTextArea(String text) {
        page().enterRevisedText(text);
    }

    // ─── Actions: Buttons ───────────────────────────────────────────────────────

    @When("I click the Compare button")
    public void clickCompareButton() {
        page().clickCompare();
    }

    @When("I click the Clear button")
    public void clickClearButton() {
        page().clickClear();
    }

    // ─── Assertions: Text Areas ─────────────────────────────────────────────────

    @Then("the original text area should contain {string}")
    public void originalTextAreaShouldContain(String expectedText) {
        assertEquals(expectedText, page().getOriginalTextValue(),
                "Original text area content mismatch");
    }

    @Then("the revised text area should contain {string}")
    public void revisedTextAreaShouldContain(String expectedText) {
        assertEquals(expectedText, page().getRevisedTextValue(),
                "Revised text area content mismatch");
    }

    @Then("the original text area should be empty")
    public void originalTextAreaShouldBeEmpty() {
        assertTrue(page().isOriginalTextAreaEmpty(), "Original text area should be empty after clear");
    }

    @Then("the revised text area should be empty")
    public void revisedTextAreaShouldBeEmpty() {
        assertTrue(page().isRevisedTextAreaEmpty(), "Revised text area should be empty after clear");
    }

    // ─── Assertions: Comparison Results ─────────────────────────────────────────

    @Then("no differences should be highlighted")
    public void noDifferencesShouldBeHighlighted() {
        assertFalse(page().hasDifferences(), "No differences should be highlighted for identical texts");
    }

    @Then("differences should be highlighted")
    public void differencesShouldBeHighlighted() {
        assertTrue(page().hasDifferences(), "Differences should be highlighted");
    }

    @Then("added lines should be highlighted")
    public void addedLinesShouldBeHighlighted() {
        assertTrue(page().getAddedLinesCount() > 0, "Added lines should be highlighted");
    }

    @Then("removed lines should be highlighted")
    public void removedLinesShouldBeHighlighted() {
        assertTrue(page().getRemovedLinesCount() > 0, "Removed lines should be highlighted");
    }

    // ─── Scenario Outline ───────────────────────────────────────────────────────

    @Then("the comparison result should be {string}")
    public void comparisonResultShouldBe(String expectedResult) {
        boolean hasDiff = page().hasDifferences();
        if ("has-diff".equalsIgnoreCase(expectedResult)) {
            assertTrue(hasDiff, "Expected differences to be highlighted");
        } else if ("no-diff".equalsIgnoreCase(expectedResult)) {
            assertFalse(hasDiff, "Expected no differences to be highlighted");
        } else {
            fail("Unknown expected result: " + expectedResult);
        }
    }
}
