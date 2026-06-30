package com.testonics.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private final Properties properties = new Properties();

    private ConfigManager() {
        loadProperties();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.warn("config.properties not found, using defaults");
                return;
            }
            properties.load(input);
            logger.info("Configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url", "https://www.testonics.in/text-compartor");
    }

    public String getBrowser() {
        return System.getProperty("browser", properties.getProperty("browser", "chromium"));
    }

    public boolean isHeadless() {
        String headless = System.getProperty("headless", properties.getProperty("headless", "false"));
        return Boolean.parseBoolean(headless);
    }

    public int getDefaultTimeout() {
        return Integer.parseInt(properties.getProperty("default.timeout", "30000"));
    }

    public int getSlowMo() {
        return Integer.parseInt(properties.getProperty("slow.mo", "0"));
    }

    public String getScreenshotDir() {
        return properties.getProperty("screenshot.dir", "target/screenshots");
    }

    public String getVideoDir() {
        return properties.getProperty("video.dir", "target/videos");
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
