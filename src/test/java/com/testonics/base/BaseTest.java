package com.testonics.base;

/**
 * BaseTest is retained as a placeholder.
 * Browser lifecycle is managed by {@link com.testonics.hooks.Hooks} via Cucumber
 * @Before / @After / @BeforeAll / @AfterAll hooks.
 * Shared state is passed between hooks and step definitions through
 * {@link com.testonics.context.TestContext} using PicoContainer DI.
 */
public class BaseTest {
    // Intentionally empty — see Hooks.java and TestContext.java
}
