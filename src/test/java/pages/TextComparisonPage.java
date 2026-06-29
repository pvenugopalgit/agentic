package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * Page Object for the Text Comparison Tool page.
 */
public class TextComparisonPage {
    private final Page page;

    // Locators for Text Comparison Tool UI elements
    public final Locator firstTextBox;
    public final Locator secondTextBox;
    public final Locator compareButton;
    public final Locator resultText;
    public final Locator highlightedDifferences;

    public TextComparisonPage(Page page) {
        this.page = page;

        // Locators for the Text Comparison Tool UI elements
        // These locators are based on common patterns; adjust if necessary based on actual HTML
        this.firstTextBox = page.locator("#text-input-a, textarea[id*='first'], input[id*='first']");
        this.secondTextBox = page.locator("#text-input-b, textarea[id*='second'], input[id*='second']");
        this.compareButton = page.locator("button[data-action='compare'], button:has-text('Compare'), button:has-text('compare')");
        this.resultText = page.locator("#comparison-result, .result, [class*='result']");
        this.highlightedDifferences = page.locator(".diff-highlight, .highlight, [class*='highlight']");
    }

    /**
     * Fills the first text box with the given text.
     *
     * @param text the text to enter in the first text box
     */
    public void fillFirstTextBox(String text) {
        firstTextBox.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        firstTextBox.fill(text);
    }

    /**
     * Fills the second text box with the given text.
     *
     * @param text the text to enter in the second text box
     */
    public void fillSecondTextBox(String text) {
        secondTextBox.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        secondTextBox.fill(text);
    }

    /**
     * Clicks the Compare button to trigger comparison.
     */
    public void clickCompare() {
        compareButton.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Returns the result text displayed after comparison.
     *
     * @return result message string
     */
    public String getResultText() {
        resultText.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        return resultText.textContent();
    }

    /**
     * Returns whether highlighted differences are visible on the page.
     *
     * @return true if differences are highlighted, false otherwise
     */
    public boolean hasHighlightedDifferences() {
        try {
            page.waitForTimeout(500);
            return highlightedDifferences.count() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
