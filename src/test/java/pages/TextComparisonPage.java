package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for the Text Comparison Tool page.
 */
public class TextComparisonPage {
    private final Page page;

    // TODO: Replace placeholder locators with actual selectors
    public final Locator firstTextBox;
    public final Locator secondTextBox;
    public final Locator compareButton;
    public final Locator resultText;
    public final Locator highlightedDifferences;

    public TextComparisonPage(Page page) {
        this.page = page;

        // TODO: Update locators to match actual element selectors
        this.firstTextBox = page.locator("#text-input-a"); // TODO: replace with actual locator
        this.secondTextBox = page.locator("#text-input-b"); // TODO: replace with actual locator
        this.compareButton = page.locator("button[data-action='compare']"); // TODO: replace with actual locator
        this.resultText = page.locator("#comparison-result"); // TODO: replace with actual locator
        this.highlightedDifferences = page.locator(".diff-highlight"); // TODO: replace with actual locator
    }

    /**
     * Fills the first text box with the given text.
     *
     * @param text the text to enter in the first text box
     */
    public void fillFirstTextBox(String text) {
        // TODO: Navigate to page if not already there
        firstTextBox.fill(text);
    }

    /**
     * Fills the second text box with the given text.
     *
     * @param text the text to enter in the second text box
     */
    public void fillSecondTextBox(String text) {
        secondTextBox.fill(text);
    }

    /**
     * Clicks the Compare button to trigger comparison.
     */
    public void clickCompare() {
        compareButton.click();
    }

    /**
     * Returns the result text displayed after comparison.
     *
     * @return result message string
     */
    public String getResultText() {
        // TODO: Wait for result to appear if needed
        return resultText.textContent();
    }

    /**
     * Returns whether highlighted differences are visible on the page.
     *
     * @return true if differences are highlighted, false otherwise
     */
    public boolean hasHighlightedDifferences() {
        // TODO: Implement actual check for highlighted differences
        return highlightedDifferences.count() > 0;
    }
}
