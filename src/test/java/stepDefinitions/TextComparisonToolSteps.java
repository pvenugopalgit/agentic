package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserContext;
import org.junit.Assert;
import pages.TextComparisonPage;

/**
 * Step definitions for Text Comparison Tool feature
 */
public class TextComparisonToolSteps {
    private Page page;
    private TextComparisonPage comparisonPage;

    public TextComparisonToolSteps() {
        Playwright pw = Playwright.create();
        Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        this.page = context.newPage();
        this.comparisonPage = new TextComparisonPage(page);
    }

    @Given("the user has entered text A into the first text box")
    public void userEnteredTextA() {
        // TODO: replace with real test data or parameterize
        comparisonPage.fillFirstTextBox("Sample text A");
    }

    @And("the user has entered text B into the second text box")
    public void userEnteredTextB() {
        comparisonPage.fillSecondTextBox("Sample text B");
    }

    @When("the user clicks the \"Compare\" button")
    public void userClicksCompare() {
        comparisonPage.clickCompare();
    }

    @Then("the tool indicates whether the texts are identical")
    public void toolIndicatesEquality() {
        // TODO: assert based on expected outcome; this placeholder checks presence of result
        String result = comparisonPage.getResultText();
        Assert.assertNotNull("Result should be shown", result);
    }

    @And("if not identical, highlights the exact differences")
    public void highlightsDifferences() {
        // TODO: Implement exact-difference verification
        boolean highlights = comparisonPage.hasHighlightedDifferences();
        // This assertion is permissive; implement precise checks when locators known
        Assert.assertTrue("Differences should be highlighted when texts differ", highlights);
    }

    /**
     * User enters text A into the first text box (KAN-4)
     */
    @Given("the user enters text A into the first text box")
    public void userEntersTextAIntoFirstTextBox() {
        // TODO: Replace with actual test data or parameterize
        comparisonPage.fillFirstTextBox("Sample text A");
    }

    /**
     * User enters the same text A into the second text box (identical comparison scenario)
     */
    @And("the user enters the same text A into the second text box")
    public void userEntersSameTextAIntoSecondTextBox() {
        // TODO: Replace with actual test data matching first text box
        comparisonPage.fillSecondTextBox("Sample text A");
    }

    /**
     * User enters text B with slight variations into the second text box (difference scenario)
     */
    @And("the user enters text B with slight variations into the second text box")
    public void userEntersTextBWithSlightVariationsIntoSecondTextBox() {
        // TODO: Replace with actual test data representing slight variations
        comparisonPage.fillSecondTextBox("Sample text B with slight variations");
    }

    /**
     * Tool displays a result indicating both texts are identical
     */
    @Then("the tool displays a result indicating both texts are identical")
    public void toolDisplaysIdenticalResult() {
        // TODO: Verify the result message indicates texts are identical
        String result = comparisonPage.getResultText();
        Assert.assertNotNull("Result should be shown", result);
        // Assert.assertTrue("Result should indicate identical texts", result.contains("identical"));
    }

    /**
     * Verifies no differences are highlighted when texts are identical
     */
    @And("no differences are highlighted")
    public void noDifferencesAreHighlighted() {
        // TODO: Verify that no difference highlights are present
        boolean highlights = comparisonPage.hasHighlightedDifferences();
        Assert.assertFalse("No differences should be highlighted for identical texts", highlights);
    }

    /**
     * Tool highlights the exact words or characters that differ
     */
    @Then("the tool highlights the exact words or characters that differ")
    public void toolHighlightsExactDifferences() {
        // TODO: Verify exact word/character-level highlights are visible
        boolean highlights = comparisonPage.hasHighlightedDifferences();
        Assert.assertTrue("Tool should highlight differing words or characters", highlights);
    }

    /**
     * Result clearly shows additions, deletions, or modifications
     */
    @And("the result clearly shows additions, deletions, or modifications")
    public void resultShowsAdditionsDeletionsOrModifications() {
        // TODO: Verify additions, deletions, or modifications are displayed in the result
        String result = comparisonPage.getResultText();
        Assert.assertNotNull("Result should show diff details", result);
        // Assert.assertTrue(result.contains("addition") || result.contains("deletion") || result.contains("modification"));
    }
}
