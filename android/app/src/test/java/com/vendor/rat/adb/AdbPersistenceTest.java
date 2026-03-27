package com.vendor.rat.adb;

import static org.junit.Assert.*;

import com.vendor.rat.control.entity.ADBConfig;
import com.vendor.rat.utils.SharedUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class AdbPersistenceTest {

    @Before
    public void setUp() {
        SharedUtils.init(RuntimeEnvironment.getApplication());
    }

    @Test
    public void testSaveAndLoadConfig() {
        ADBConfig config = new ADBConfig();
        config.setPaired(true);
        config.setDebugPort(40523);
        config.setConnectedDevice("localhost");

        AdbPersistence.saveConfig(config);
        ADBConfig loaded = AdbPersistence.loadConfig();

        assertNotNull(loaded);
        assertTrue(loaded.isPaired());
        assertEquals(Integer.valueOf(40523), loaded.getDebugPort());
        assertEquals("localhost", loaded.getConnectedDevice());
    }

    @Test
    public void testLoadConfigReturnsDefaultWhenEmpty() {
        ADBConfig config = AdbPersistence.loadConfig();
        assertNotNull(config);
        assertFalse(config.isPaired());
    }

    @Test
    public void testIsPaired() {
        assertFalse(AdbPersistence.isPaired());

        ADBConfig config = new ADBConfig();
        config.setPaired(true);
        AdbPersistence.saveConfig(config);

        assertTrue(AdbPersistence.isPaired());
    }

    @Test
    public void testSaveAndLoadKeyPaths() {
        AdbPersistence.saveKeyPaths("/data/private.key", "/data/cert.pem");
        assertEquals("/data/private.key", AdbPersistence.getPrivateKeyPath());
        assertEquals("/data/cert.pem", AdbPersistence.getCertPath());
    }

    @Test
    public void testKeyPathsDefaultNull() {
        assertNull(AdbPersistence.getPrivateKeyPath());
        assertNull(AdbPersistence.getCertPath());
    }

    @Test
    public void testSaveConfigSetsUpdateTime() {
        ADBConfig config = new ADBConfig();
        config.setPaired(true);
        AdbPersistence.saveConfig(config);

        ADBConfig loaded = AdbPersistence.loadConfig();
        assertTrue(loaded.getUpdateTime() > 0);
    }

    @Test
    public void testSaveNullDoesNotCrash() {
        AdbPersistence.saveConfig(null);
        // Should not throw
        ADBConfig loaded = AdbPersistence.loadConfig();
        assertNotNull(loaded);
    }
}
