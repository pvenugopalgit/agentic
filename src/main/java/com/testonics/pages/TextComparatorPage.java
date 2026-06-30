package com.testonics.pages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextComparatorPage {

    private static final Logger logger = LoggerFactory.getLogger(TextComparatorPage.class);

    private final Page page;
    private final FrameLocator innerFrame;

    // Locators
    private final Locator originalTextArea;
    private final Locator revisedTextArea;
    private final Locator compareButton;
    private final Locator clearButton;
    private final Locator resultSection;
    private final Locator diffHighlights;
    private final Locator addedLines;
    private final Locator removedLines;
    private final Locator pageTitle;

    public TextComparatorPage(Page page) {
        this.page = page;

        // The page is hosted on Google Sites — actual content is 3 iframes deep
        this.innerFrame = page.frameLocator("iframe").frameLocator("iframe").frameLocator("iframe");

        // Initialize locators inside the innermost iframe
        this.originalTextArea  = innerFrame.locator("textarea").nth(0);
        this.revisedTextArea   = innerFrame.locator("textarea").nth(1);
        this.compareButton     = innerFrame.locator("button:has-text('Compare')");
        this.clearButton       = innerFrame.locator("button:has-text('Clear')");
        this.resultSection     = innerFrame.locator(".result, #result, .diff-result, [class*='result']");
        this.diffHighlights    = innerFrame.locator(".diff, .highlight, [class*='diff']");
        this.addedLines        = innerFrame.locator(".added, .ins, [class*='added'], ins");
        this.removedLines      = innerFrame.locator(".removed, .del, [class*='removed'], del");
        this.pageTitle         = innerFrame.locator("h1, h2, h3");
    }

    public String getPageTitle() {
        return page.title();
    }

    public String getHeadingText() {
        return pageTitle.first().textContent().trim();
    }

    public void enterOriginalText(String text) {
        logger.info("Entering original text");
        originalTextArea.clear();
        originalTextArea.fill(text);
    }

    public void enterRevisedText(String text) {
        logger.info("Entering revised text");
        revisedTextArea.clear();
        revisedTextArea.fill(text);
    }

    public void clickCompare() {
        logger.info("Clicking Compare button");
        compareButton.click();
        page.waitForLoadState();
    }

    public void clickClear() {
        logger.info("Clicking Clear button");
        clearButton.click();
    }

    public boolean isResultDisplayed() {
        return resultSection.isVisible();
    }

    public boolean hasDifferences() {
        return diffHighlights.count() > 0;
    }

    public int getAddedLinesCount() {
        return addedLines.count();
    }

    public int getRemovedLinesCount() {
        return removedLines.count();
    }

    public String getOriginalTextValue() {
        return originalTextArea.inputValue();
    }

    public String getRevisedTextValue() {
        return revisedTextArea.inputValue();
    }

    public boolean isOriginalTextAreaEmpty() {
        return getOriginalTextValue().isEmpty();
    }

    public boolean isRevisedTextAreaEmpty() {
        return getRevisedTextValue().isEmpty();
    }

    public boolean isOriginalTextAreaVisible() {
        return originalTextArea.isVisible();
    }

    public boolean isRevisedTextAreaVisible() {
        return revisedTextArea.isVisible();
    }

    public boolean isCompareButtonVisible() {
        return compareButton.isVisible();
    }

    public void compareTexts(String original, String revised) {
        enterOriginalText(original);
        enterRevisedText(revised);
        clickCompare();
    }
}
