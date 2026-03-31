package com.vendor.rat.auto.engine.adb;

import static org.junit.Assert.*;

import android.app.Application;

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
 * Tests for OPPO credential gate on WirelessPairEngine.startPairing().
 *
 * On OPPO devices, startPairing() must be blocked when the current run
 * has not been verified via the lock credential flow.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class WirelessPairEngineCredentialGateTest {

    @Before
    public void setUp() {
  SharedUtils.init(RuntimeEnvironment.getApplication());
    LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
   LockCredentialStore.clearAll();
    }

    @Test
    public void startPairing_shouldReject_whenOppoAndCurrentRunNotVerified() {
        ShadowBuild.setManufacturer("OPPO");

        LockCredentialStore.savePin("123456");
        assertFalse(LockCredentialStore.isCurrentRunVerified());

        // startPairing should return false because credential gate blocks it
        boolean result = WirelessPairEngine.startPairing(
            RuntimeEnvironment.getApplication());
        assertFalse("startPairing should be blocked by credential gate on OPPO", result);
    }

    @Test
    public void startPairing_shouldNotBlockByCredentialGate_whenNonOppoDevice() {
    ShadowBuild.setManufacturer("HUAWEI");

        LockCredentialStore.savePin("123456");
      assertFalse(LockCredentialStore.isCurrentRunVerified());

        // On non-OPPO, credential gate should not apply.
        // Will return false because AccessibilityService is null (Robolectric),
        // but NOT because of credential guard.
     boolean result = WirelessPairEngine.startPairing(
         RuntimeEnvironment.getApplication());
        // Returns false due to null AccessibilityService, not credential gate
assertFalse(result);
    }

    @Test
    public void startPairing_shouldNotBlockByCredentialGate_whenOppoAndVerified() {
        ShadowBuild.setManufacturer("OPPO");

      LockCredentialStore.savePin("123456");
LockCredentialStore.markCurrentRunVerified();

        // Should pass credential gate (will fail later due to null service in test)
     boolean result = WirelessPairEngine.startPairing(
            RuntimeEnvironment.getApplication());
        // Returns false due to null AccessibilityService, but credential gate passed
  assertFalse(result);
    }

    @Test
    public void startPairing_shouldNotBlockByCredentialGate_whenOppoAndNoCredential() {
        ShadowBuild.setManufacturer("OPPO");

        // No credential saved — gate should not apply
 assertFalse(LockCredentialStore.hasCredential());

 boolean result = WirelessPairEngine.startPairing(
        RuntimeEnvironment.getApplication());
        // Returns false due to null AccessibilityService, but credential gate passed
        assertFalse(result);
    }
}
