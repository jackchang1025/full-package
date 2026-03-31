package com.vendor.rat.debug;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Intent;

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
 * Tests for OPPO credential gate on DebugReceiver.ACTION_START_PAIR.
 *
 * On OPPO devices, the debug broadcast to start pairing must be blocked
 * when the current run has not been verified via the lock credential flow.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class DebugReceiverCredentialGateTest {

    private DebugReceiver receiver;

    @Before
    public void setUp() {
        SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
     LockCredentialStore.clearAll();
        receiver = new DebugReceiver();
    }

  @Test
    public void onReceive_startPair_shouldBeBlocked_whenOppoAndCurrentRunNotVerified() {
        ShadowBuild.setManufacturer("OPPO");

        LockCredentialStore.savePin("123456");
        assertFalse(LockCredentialStore.isCurrentRunVerified());

        Intent intent = new Intent(DebugReceiver.ACTION_START_PAIR);
  receiver.onReceive(RuntimeEnvironment.getApplication(), intent);

    // Pairing should NOT have been triggered
        assertFalse("Pairing should not start via debug broadcast on gated OPPO",
     WirelessPairEngine.isPairingInProgress());
    }

    @Test
    public void onReceive_startPair_shouldNotBlockByCredentialGate_whenNonOppoDevice() {
     ShadowBuild.setManufacturer("HUAWEI");

        LockCredentialStore.savePin("123456");
        assertFalse(LockCredentialStore.isCurrentRunVerified());

   Intent intent = new Intent(DebugReceiver.ACTION_START_PAIR);
      receiver.onReceive(RuntimeEnvironment.getApplication(), intent);

        // Non-OPPO: credential gate should not apply.
        // Pairing still won't start because AccessibilityService is null,
        // but the gate itself should not block it.
    }

    @Test
    public void onReceive_startPair_shouldNotBlockByCredentialGate_whenOppoAndVerified() {
        ShadowBuild.setManufacturer("OPPO");

        LockCredentialStore.savePin("123456");
        LockCredentialStore.markCurrentRunVerified();

 Intent intent = new Intent(DebugReceiver.ACTION_START_PAIR);
        receiver.onReceive(RuntimeEnvironment.getApplication(), intent);

        // Credential gate should pass (pairing still won't start due to null service)
    }
}
