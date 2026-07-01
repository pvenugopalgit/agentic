package com.testonics.hooks;

import com.microsoft.playwright.*;
import com.testonics.config.ConfigManager;
import com.testonics.context.TestContext;
import com.testonics.pages.TextComparatorPage;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Arrays;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    private static Playwright playwright;
    private static Browser browser;

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @BeforeAll
    public static void launchBrowser() {
        playwright = Playwright.create();
        String browserType = config.getBrowser().toLowerCase();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(config.isHeadless())
                .setSlowMo(config.getSlowMo())
                .setArgs(Arrays.asList(
                        "--start-maximized",
                        "--disable-web-security",
                        "--disable-site-isolation-trials",
                        "--disable-features=IsolateOrigins,site-per-process"
                ));

        browser = switch (browserType) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit"  -> playwright.webkit().launch(launchOptions);
            default        -> playwright.chromium().launch(launchOptions);
        };

        logger.info("Browser launched: {}", browserType);
    }

    @Before
    public void createContextAndPage(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(null)
                .setRecordVideoDir(Paths.get(config.getVideoDir()));

                // Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                // .setViewportSize(1920, 1080)
                // .setRecordVideoDir(Paths.get(config.getVideoDir()));

        BrowserContext context = browser.newContext(contextOptions);
        context.setDefaultTimeout(config.getDefaultTimeout());

        Page page = context.newPage();

        TextComparatorPage textComparatorPage = new TextComparatorPage(page);

        testContext.setPlaywright(playwright);
        testContext.setBrowser(browser);
        testContext.setBrowserContext(context);
        testContext.setPage(page);
        testContext.setTextComparatorPage(textComparatorPage);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.warn("Scenario FAILED: {}", scenario.getName());
            try {
                byte[] screenshot = testContext.getPage().screenshot();
                scenario.attach(screenshot, "image/png", scenario.getName() + "_failure");
                logger.info("Failure screenshot attached to Allure report");
            } catch (Exception e) {
                logger.error("Failed to capture screenshot on failure", e);
            }
        }

        if (testContext.getPage() != null)          testContext.getPage().close();
        if (testContext.getBrowserContext() != null) testContext.getBrowserContext().close();
        logger.info("Scenario completed: {}", scenario.getName());
    }

    @AfterAll
    public static void closeBrowser() {
        if (browser != null)     browser.close();
        if (playwright != null)  playwright.close();
        logger.info("Browser closed after all scenarios");
    }
}
