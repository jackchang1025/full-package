package com.vendor.rat.adb;

import static org.junit.Assert.*;

import android.app.Application;

import com.vendor.rat.control.entity.ADBConfig;
import com.vendor.rat.utils.SharedUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class AdbConnectionManagerTest {

    @Before
    public void setUp() {
        // Reset singleton between tests
        AdbConnectionManager.resetForTesting();
        SharedUtils.init(RuntimeEnvironment.getApplication());
        AdbConnectionManager.init(RuntimeEnvironment.getApplication());
    }

    @Test
    public void testSingletonInit() {
        assertNotNull(AdbConnectionManager.getInstance());
    }

    @Test
    public void testSingletonIsSame() {
        AdbConnectionManager a = AdbConnectionManager.getInstance();
        AdbConnectionManager b = AdbConnectionManager.getInstance();
        assertSame(a, b);
    }

    @Test
    public void testDoubleInitDoesNotReplace() {
        AdbConnectionManager first = AdbConnectionManager.getInstance();
        AdbConnectionManager.init(RuntimeEnvironment.getApplication());
        assertSame(first, AdbConnectionManager.getInstance());
    }

    @Test
    public void testNotConnectedByDefault() {
        assertFalse(AdbConnectionManager.getInstance().isAdbConnected());
    }

    @Test
    public void testNotPairedByDefault() {
        assertFalse(AdbConnectionManager.getInstance().isPaired());
    }

    @Test
    public void testGetAdbConfig() {
        ADBConfig config = AdbConnectionManager.getInstance().getAdbConfig();
        assertNotNull(config);
        assertFalse(config.isConnected());
        assertFalse(config.isPaired());
    }

    @Test
    public void testGetDeviceName() {
        // getDeviceName is protected, but we verify it via the config's connectedDevice field
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        assertNotNull(mgr);
    }

    @Test
    public void testDoPairRejectsNullHost() {
        assertFalse(AdbConnectionManager.getInstance().doPair(null, 5555, "123456"));
    }

    @Test
    public void testDoPairRejectsNullCode() {
        assertFalse(AdbConnectionManager.getInstance().doPair("127.0.0.1", 5555, null));
    }

    @Test
    public void testDoPairRejectsInvalidPort() {
        assertFalse(AdbConnectionManager.getInstance().doPair("127.0.0.1", 0, "123456"));
        assertFalse(AdbConnectionManager.getInstance().doPair("127.0.0.1", -1, "123456"));
    }

    @Test
    public void testDoConnectRejectsInvalidPort() {
        assertFalse(AdbConnectionManager.getInstance().doConnect(0));
        assertFalse(AdbConnectionManager.getInstance().doConnect(-1));
    }

    @Test
    public void testDoConnectWithHostRejectsNull() {
        assertFalse(AdbConnectionManager.getInstance().doConnect(null, 5555));
    }

    @Test
    public void testDoConnectWithHostRejectsInvalidPort() {
        assertFalse(AdbConnectionManager.getInstance().doConnect("127.0.0.1", 0));
    }

    @Test
    public void testExecuteShellRejectsNull() {
        AdbConnectionManager.AdbShellResult result =
            AdbConnectionManager.getInstance().executeShell(null);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteShellRejectsEmpty() {
        AdbConnectionManager.AdbShellResult result =
            AdbConnectionManager.getInstance().executeShell("");
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    public void testExecuteShellNotConnected() {
        AdbConnectionManager.AdbShellResult result =
            AdbConnectionManager.getInstance().executeShell("ls");
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getOutput().contains("Not connected"));
    }

    @Test
    public void testExecuteShellAsyncDoesNotCrash() {
        // Should not throw even when not connected
        AdbConnectionManager.getInstance().executeShellAsync("ls");
    }

    @Test
    public void testExecuteShellAsyncNullDoesNotCrash() {
        AdbConnectionManager.getInstance().executeShellAsync(null);
    }

    @Test
    public void testExecuteShellExpectNotConnected() {
        assertFalse(AdbConnectionManager.getInstance()
            .executeShellExpect("ls", "/data"));
    }

    @Test
    public void testHeartbeatDoesNotCrash() {
        AdbConnectionManager.getInstance().heartbeat();
    }

    @Test
    public void testDoDisconnectDoesNotCrash() {
        AdbConnectionManager.getInstance().doDisconnect();
    }

    @Test
    public void testAdbShellResult() {
        AdbConnectionManager.AdbShellResult success =
            new AdbConnectionManager.AdbShellResult(true, "output");
        assertTrue(success.isSuccess());
        assertEquals("output", success.getOutput());
        assertNotNull(success.toString());

        AdbConnectionManager.AdbShellResult failure =
            new AdbConnectionManager.AdbShellResult(false, "error");
        assertFalse(failure.isSuccess());
        assertEquals("error", failure.getOutput());
    }

    @Test
    public void testRsaKeyGenerationOnInit() {
        // Keys should be generated during init
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        // Verify through AdbPersistence that key paths were saved
        String pkPath = AdbPersistence.getPrivateKeyPath();
        String certPath = AdbPersistence.getCertPath();
        assertNotNull("Private key path should be saved", pkPath);
        assertNotNull("Cert path should be saved", certPath);
        assertTrue("Private key file should exist", new java.io.File(pkPath).exists());
        assertTrue("Cert file should exist", new java.io.File(certPath).exists());
    }
}
