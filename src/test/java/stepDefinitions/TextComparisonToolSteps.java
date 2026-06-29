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
        String testDataA = "The quick brown fox";
        comparisonPage.fillFirstTextBox(testDataA);
        page.waitForLoadState();
    }

    @And("the user has entered text B into the second text box")
    public void userEnteredTextB() {
        String testDataB = "The slow brown fox";
        comparisonPage.fillSecondTextBox(testDataB);
        page.waitForLoadState();
    }

    @When("the user clicks the \"Compare\" button")
    public void userClicksCompare() {
        comparisonPage.clickCompare();
        page.waitForLoadState();
    }

    @Then("the tool indicates whether the texts are identical")
    public void toolIndicatesEquality() {
        String result = comparisonPage.getResultText();
        Assert.assertNotNull("Comparison result should be displayed", result);
        Assert.assertFalse("Result should contain comparison information", result.trim().isEmpty());
    }

    @And("if not identical, highlights the exact differences")
    public void highlightsDifferences() {
        boolean hasHighlights = comparisonPage.hasHighlightedDifferences();
        Assert.assertTrue("Differences should be highlighted when texts differ", hasHighlights);
    }

    /**
     * User enters text A into the first text box (KAN-4)
     */
    @Given("the user enters text A into the first text box")
    public void userEntersTextAIntoFirstTextBox() {
        String textA = "The quick brown fox jumps over the lazy dog";
        comparisonPage.fillFirstTextBox(textA);
        page.waitForLoadState();
    }

    /**
     * User enters the same text A into the second text box (identical comparison scenario)
     */
    @And("the user enters the same text A into the second text box")
    public void userEntersSameTextAIntoSecondTextBox() {
        String identicalTextA = "The quick brown fox jumps over the lazy dog";
        comparisonPage.fillSecondTextBox(identicalTextA);
        page.waitForLoadState();
    }

    /**
     * User enters text B with slight variations into the second text box (difference scenario)
     */
    @And("the user enters text B with slight variations into the second text box")
    public void userEntersTextBWithSlightVariationsIntoSecondTextBox() {
        String textBWithVariations = "The quick brown fox jumps over the sleeping dog";
        comparisonPage.fillSecondTextBox(textBWithVariations);
        page.waitForLoadState();
    }

    /**
     * Tool displays a result indicating both texts are identical
     */
    @Then("the tool displays a result indicating both texts are identical")
    public void toolDisplaysIdenticalResult() {
        String result = comparisonPage.getResultText();
        Assert.assertNotNull("Result should be displayed", result);
        Assert.assertFalse("Result should contain comparison information", result.trim().isEmpty());
        Assert.assertTrue("Result should indicate texts are identical", 
            result.toLowerCase().contains("identical") || 
            result.toLowerCase().contains("match") ||
            result.toLowerCase().contains("same"));
    }

    /**
     * Verifies no differences are highlighted when texts are identical
     */
    @And("no differences are highlighted")
    public void noDifferencesAreHighlighted() {
        boolean hasHighlights = comparisonPage.hasHighlightedDifferences();
        Assert.assertFalse("No differences should be highlighted for identical texts", hasHighlights);
    }

    /**
     * Tool highlights the exact words or characters that differ
     */
    @Then("the tool highlights the exact words or characters that differ")
    public void toolHighlightsExactDifferences() {
        boolean hasHighlights = comparisonPage.hasHighlightedDifferences();
        Assert.assertTrue("Tool should highlight differing words or characters", hasHighlights);
    }

    /**
     * Result clearly shows additions, deletions, or modifications
     */
    @And("the result clearly shows additions, deletions, or modifications")
    public void resultShowsAdditionsDeletionsOrModifications() {
        String result = comparisonPage.getResultText();
        Assert.assertNotNull("Result should show diff details", result);
        Assert.assertFalse("Result should contain diff information", result.trim().isEmpty());
    }
}
