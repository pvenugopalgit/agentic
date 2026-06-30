package com.testonics.runner;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue",              value = "com.testonics.hooks,com.testonics.stepdefs")
@ConfigurationParameter(key = "cucumber.plugin",            value = "pretty,html:target/cucumber-reports/report.html,json:target/cucumber-reports/report.json,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = "cucumber.filter.tags",       value = "not @Ignore")
@ConfigurationParameter(key = "cucumber.publish.quiet",     value = "true")
public class TestRunner {
    // Entry point — Cucumber scenarios are discovered automatically.
    // Run with: mvn test
    // Run by tag: mvn test -Dcucumber.filter.tags="@smoke"
}
