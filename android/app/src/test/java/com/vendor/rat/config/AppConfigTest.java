package com.vendor.rat.config;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AppConfig 单元测试
 */
public class AppConfigTest {

    @Test
    public void testDefaultConfig() {
        AppConfig config = AppConfig.getDefault();

        assertNotNull(config.getServerHost());
        assertNotNull(config.getWebSocketUrl());
        assertEquals(2, config.getPerScreenOffDuration());
        assertEquals(5, config.getPerIdleDuration());
    }

    @Test
    public void testSettersGetters() {
        AppConfig config = new AppConfig();
        config.setServerHost("https://test.example.com");
        config.setWebSocketUrl("wss://test.example.com/ws");
        config.setPerScreenOffDuration(3);
        config.setPerIdleDuration(10);

        assertEquals("https://test.example.com", config.getServerHost());
        assertEquals("wss://test.example.com/ws", config.getWebSocketUrl());
        assertEquals(3, config.getPerScreenOffDuration());
        assertEquals(10, config.getPerIdleDuration());
    }
}
