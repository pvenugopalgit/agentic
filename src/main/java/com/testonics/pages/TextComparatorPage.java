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
        this.originalTextArea  = innerFrame.locator("#left");
        this.revisedTextArea   = innerFrame.locator("#right");
        this.compareButton     = innerFrame.locator("button.compare");
        this.clearButton       = innerFrame.locator("button.clear");
        this.resultSection     = innerFrame.locator("#summary");
        this.diffHighlights    = innerFrame.locator("span.red, span.green");
        this.addedLines        = innerFrame.locator("span.green");
        this.removedLines      = innerFrame.locator("span.red");
        this.pageTitle         = innerFrame.locator("h1");
    }

    public String getPageTitle() {
        return page.title();
    }

    public String getHeadingText() {
        return pageTitle.first().textContent().trim();
    }

    public void waitForReady() {
        originalTextArea.waitFor(new Locator.WaitForOptions()
            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
            .setTimeout(60000));
    }

    public void enterOriginalText(String text) {
        logger.info("Entering original text");
        waitForReady();
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
        return originalTextArea.innerText();
    }

    public String getRevisedTextValue() {
        return revisedTextArea.innerText();
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
