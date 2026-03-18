package com.vendor.rat;

import com.vendor.rat.config.AppConfig;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MODULE_08 启动流程 — JVM 单元测试
 */
public class StartupModuleTest {

    @Test
    public void testAppConfigDefault_hasValidServerHost() {
        AppConfig config = AppConfig.getDefault();
        assertNotNull(config.getServerHost());
        assertTrue(config.getServerHost().startsWith("http"));
    }

    @Test
    public void testAppConfigDefault_hasValidWebSocketUrl() {
        AppConfig config = AppConfig.getDefault();
        assertNotNull(config.getWebSocketUrl());
        assertTrue(config.getWebSocketUrl().startsWith("ws"));
    }

    @Test
    public void testAppConfigDefault_hasValidDownloadHost() {
        AppConfig config = AppConfig.getDefault();
        assertNotNull(config.getDownloadRatHatHost());
        assertTrue(config.getDownloadRatHatHost().startsWith("http"));
    }

    @Test
    public void testAppConfigDefault_screenOffDuration_positive() {
        AppConfig config = AppConfig.getDefault();
        assertTrue(config.getPerScreenOffDuration() > 0);
    }

    @Test
    public void testAppConfigDefault_idleDuration_positive() {
        AppConfig config = AppConfig.getDefault();
        assertTrue(config.getPerIdleDuration() > 0);
    }

    @Test
    public void testAppConfigDefault_idleDuration_greaterThanScreenOff() {
        AppConfig config = AppConfig.getDefault();
        assertTrue(config.getPerIdleDuration() >= config.getPerScreenOffDuration());
    }

    @Test
    public void testAppConfigSetters() {
        AppConfig config = new AppConfig();
        config.setServerHost("https://test.example.com");
        config.setWebSocketUrl("wss://test.example.com/ws");
        config.setDownloadRatHatHost("https://dl.test.com");
        config.setPerScreenOffDuration(3);
        config.setPerIdleDuration(10);

        assertEquals("https://test.example.com", config.getServerHost());
        assertEquals("wss://test.example.com/ws", config.getWebSocketUrl());
        assertEquals("https://dl.test.com", config.getDownloadRatHatHost());
        assertEquals(Integer.valueOf(3), config.getPerScreenOffDuration());
        assertEquals(Integer.valueOf(10), config.getPerIdleDuration());
    }

    @Test
    public void testAppConfigDefault_hasGuideFields() {
        AppConfig config = AppConfig.getDefault();
        assertNotNull(config.getGuideAccessibilityHost());
        assertNotNull(config.getMainUrl());
        assertNotNull(config.getAlertTitle());
        assertNotNull(config.getAlertMsg());
        assertNotNull(config.getOkText());
        assertNotNull(config.getExitConfirm());
    }

    @Test
    public void testMainApplication_getInstance_beforeInit_noException() {
        try {
            MainApplication instance = MainApplication.getInstance();
        } catch (Exception e) {
            fail("getInstance() should not throw: " + e.getMessage());
        }
    }
}
