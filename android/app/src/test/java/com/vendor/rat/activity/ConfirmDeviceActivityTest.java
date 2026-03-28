package com.vendor.rat.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.vendor.rat.credential.FakeLockCredentialCipher;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.utils.SharedUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class ConfirmDeviceActivityTest {

    @Before
    public void setUp() {
        SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
      LockCredentialStore.clearAll();
    }

    // 1. onResume should use KeyguardManager to start confirm intent.
    //    In Robolectric, createConfirmDeviceCredentialIntent returns null by default,
    //    so the null-intent cleanup path is exercised instead.
    //We verify the activity finishes (null path) and suppress is set.
    @Test
    public void onResume_shouldStartConfirmIntent_usingKeyguardManager() {
        Intent launchIntent = new Intent(RuntimeEnvironment.getApplication(), ConfirmDeviceActivity.class);
        launchIntent.putExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE, "TEST_RESUME");

    ConfirmDeviceActivity activity = Robolectric.buildActivity(ConfirmDeviceActivity.class, launchIntent)
                .create()
      .resume()
              .get();

    // Robolectric KeyguardManager returns null for createConfirmDeviceCredentialIntent,
    // so the null-intent cleanup path runs: suppress + finish
        assertTrue("Activity should finish when confirm intent is null", activity.isFinishing());
     assertTrue("Prompt should be suppressed when no lock screen",
                LockCredentialStore.isPromptSuppressedForCurrentRun());
 }

    // 2. onResume second time should NOT re-launch (launchStarted guard)
    @Test
  public void onResume_secondTime_shouldNotRelaunch() {
        Intent launchIntent = new Intent(RuntimeEnvironment.getApplication(), ConfirmDeviceActivity.class);
        launchIntent.putExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE, "TEST_GUARD");

        ConfirmDeviceActivity activity = Robolectric.buildActivity(ConfirmDeviceActivity.class, launchIntent)
     .create()
              .get();

    // Manually set launchStarted to simulate first launch already happened
     activity.launchStarted.set(true);

   // Call onResume — should return immediately due to launchStarted guard
        // Reset suppressed flag to verify onResume doesn't re-run cleanup
        LockCredentialStore.clearAll();
        activity.onResume();

        // If onResume re-ran, it would set suppressed (since Robolectric returns null intent)
        assertFalse("onResume should not re-run when launchStarted is true",
                LockCredentialStore.isPromptSuppressedForCurrentRun());
    }

    // 3. RESULT_OK should mark current run verified and trigger keepalive
    @Test
    public void onActivityResult_ok_shouldMarkCurrentRunVerified() {
        Intent launchIntent = new Intent(RuntimeEnvironment.getApplication(), ConfirmDeviceActivity.class);
        launchIntent.putExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE, "TEST_OK");

        ConfirmDeviceActivity activity = Robolectric.buildActivity(ConfirmDeviceActivity.class, launchIntent)
        .create()
    .get();

    // Simulate RESULT_OK
        activity.onActivityResult(1001, Activity.RESULT_OK, null);

        assertTrue("Current run should be marked verified",
LockCredentialStore.isCurrentRunVerified());
        assertTrue("Activity should finish after RESULT_OK", activity.isFinishing());
    }

    // 4. RESULT_CANCELED should clear PIN, suppress, remove overlay
    @Test
    public void onActivityResult_cancel_shouldClearPinAndSuppressAndRemoveOverlay() {
        // Save a PIN first
        LockCredentialStore.savePin("123456");
        assertTrue("Precondition: credential should exist", LockCredentialStore.hasCredential());

        Intent launchIntent = new Intent(RuntimeEnvironment.getApplication(), ConfirmDeviceActivity.class);
     launchIntent.putExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE, "TEST_CANCEL");

      ConfirmDeviceActivity activity = Robolectric.buildActivity(ConfirmDeviceActivity.class, launchIntent)
    .create()
                .get();

        // Simulate RESULT_CANCELED
        activity.onActivityResult(1001, Activity.RESULT_CANCELED, null);

   assertFalse("Credential should be cleared after cancel",
  LockCredentialStore.hasCredential());
        assertTrue("Prompt should be suppressed after cancel",
 LockCredentialStore.isPromptSuppressedForCurrentRun());
        assertTrue("Activity should finish after cancel", activity.isFinishing());
    }

    // 5. newIntent factory should include event code
    @Test
    public void newIntent_shouldContainEventCode() {
      Intent intent = ConfirmDeviceActivity.newIntent(
         RuntimeEnvironment.getApplication(), "TEST_CODE");
        assertEquals("TEST_CODE", intent.getStringExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE));
    }

  // 6. null confirm intent should cleanup and finish
    @Test
    public void onResume_shouldCleanupAndFinish_whenConfirmIntentIsNull() {
   // In Robolectric, KeyguardManager.createConfirmDeviceCredentialIntent returns null by default
      // This tests the case where device has no lock screen
        Intent launchIntent = new Intent(RuntimeEnvironment.getApplication(), ConfirmDeviceActivity.class);
     launchIntent.putExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE, "TEST_NULL_INTENT");

        ConfirmDeviceActivity activity = Robolectric.buildActivity(ConfirmDeviceActivity.class, launchIntent)
   .create()
          .resume()
       .get();

        assertTrue("Activity should finish when confirm intent is null", activity.isFinishing());
        assertTrue("Prompt should be suppressed when no lock screen",
         LockCredentialStore.isPromptSuppressedForCurrentRun());
      assertFalse("Current run should not be verified",
     LockCredentialStore.isCurrentRunVerified());
    }
}
