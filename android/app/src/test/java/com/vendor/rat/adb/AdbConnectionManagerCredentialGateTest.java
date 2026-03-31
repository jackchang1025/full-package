package com.vendor.rat.adb;

import static org.junit.Assert.*;

import android.app.Application;

import com.vendor.rat.auto.engine.adb.WirelessPairEngine;
import com.vendor.rat.credential.FakeLockCredentialCipher;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.utils.SharedUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowBuild;

/**
 * Tests for OPPO credential gate on AdbConnectionManager.triggerPairingIfNeeded().
 *
 * triggerPairingIfNeeded() is private, so we test it indirectly via heartbeat().
 * On OPPO devices with credential not verified, heartbeat() must not trigger pairing.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class AdbConnectionManagerCredentialGateTest {

    @Before
    public void setUp() {
        AdbConnectionManager.resetForTesting();
        SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
        LockCredentialStore.clearAll();
        AdbConnectionManager.init(RuntimeEnvironment.getApplication());
    }

    @Test
    public void heartbeat_shouldNotTriggerPairing_whenOppoAndCurrentRunNotVerified() {
        ShadowBuild.setManufacturer("OPPO");

      LockCredentialStore.savePin("123456");
    assertFalse(LockCredentialStore.isCurrentRunVerified());

        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
     assertNotNull(mgr);

  // heartbeat should not crash and should not trigger pairing
        mgr.heartbeat();

      // Pairing should NOT have been triggered
        assertFalse("Pairing should not be in progress after heartbeat on gated OPPO",
     WirelessPairEngine.isPairingInProgress());
    }

    @Test
    public void heartbeat_shouldNotCrash_whenOppoAndVerified() {
        ShadowBuild.setManufacturer("OPPO");

        LockCredentialStore.savePin("123456");
        LockCredentialStore.markCurrentRunVerified();

        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        assertNotNull(mgr);

      // Should pass credential gate (may still not pair due to other conditions)
        mgr.heartbeat();
    }

    @Test
 public void heartbeat_shouldNotBlockByCredentialGate_whenNonOppoDevice() {
        ShadowBuild.setManufacturer("HUAWEI");

        LockCredentialStore.savePin("123456");
        assertFalse(LockCredentialStore.isCurrentRunVerified());

        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        assertNotNull(mgr);

      // Non-OPPO: credential gate should not apply
  mgr.heartbeat();
  }
}
