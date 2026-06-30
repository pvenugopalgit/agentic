package com.testonics.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaywrightUtils {

    private static final Logger logger = LoggerFactory.getLogger(PlaywrightUtils.class);

    private PlaywrightUtils() {
        // Utility class
    }

    public static String takeTimestampedScreenshot(Page page, String name, String dir) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = name + "_" + timestamp + ".png";
        String fullPath = dir + "/" + fileName;
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fullPath)).setFullPage(true));
        logger.info("Screenshot saved: {}", fullPath);
        return fullPath;
    }

    public static void waitForElement(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
    }

    public static void scrollToElement(Page page, Locator locator) {
        locator.scrollIntoViewIfNeeded();
        logger.info("Scrolled element into view");
    }

    public static void highlightElement(Page page, Locator locator) {
        page.evaluate("el => el.style.border = '3px solid red'", locator.elementHandle());
    }

    public static boolean isElementVisible(Locator locator) {
        try {
            return locator.isVisible();
        } catch (Exception e) {
            logger.warn("Element not found: {}", e.getMessage());
            return false;
        }
    }

    public static void clearAndType(Locator locator, String text) {
        locator.clear();
        locator.fill(text);
        logger.info("Cleared and typed text into element");
    }

    public static String getElementText(Locator locator) {
        String text = locator.textContent();
        return text != null ? text.trim() : "";
    }
}
