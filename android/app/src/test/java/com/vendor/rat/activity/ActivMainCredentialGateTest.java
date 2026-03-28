package com.vendor.rat.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.vendor.rat.credential.FakeLockCredentialCipher;
import com.vendor.rat.credential.LockCredentialStore;
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
 * Tests for the OPPO credential gate logic in ActivMain.
 *
 * The credential gate intercepts onResume on OPPO devices to enforce
 * lock-screen PIN collection and device credential verification before
 * allowing the main flow to proceed.
 *
 * Three branches:
 *   1. No stored PIN → launch LockCredentialPromptActivity
 *   2. PIN stored but not verified this run → launch ConfirmDeviceActivity
 *   3. Already verified this run → continue normally
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class ActivMainCredentialGateTest {

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

    // ============ Three-branch tests ============

    /**
     * Branch 1: No stored PIN + not suppressed → launches LockCredentialPromptActivity.
 */
    @Test
    public void onResume_shouldLaunchPrompt_whenNoStoredPin() {
        assertFalse("Precondition: no credential stored", LockCredentialStore.hasCredential());

   controller.resume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
   assertNotNull("Should launch an activity for result", launched);
        assertEquals(LockCredentialPromptActivity.class.getName(),
       launched.intent.getComponent().getClassName());
        assertEquals(ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT, launched.requestCode);
    }

 /**
     * Branch 2: PIN saved + not verified this run → launches ConfirmDeviceActivity.
     */
    @Test
    public void onResume_shouldStartSystemVerification_whenPinExistsButRunNotVerified() {
        LockCredentialStore.savePin("123456");
        assertTrue("Precondition: credential stored", LockCredentialStore.hasCredential());
        assertFalse("Precondition: not verified", LockCredentialStore.isCurrentRunVerified());

controller.resume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNotNull("Should launch an activity for result", launched);
        assertEquals(ConfirmDeviceActivity.class.getName(),
      launched.intent.getComponent().getClassName());
        assertEquals(ActivMain.REQUEST_CONFIRM_DEVICE, launched.requestCode);
    }

    /**
     * Branch 3: Already verified this run → no credential activity launched.
*/
    @Test
    public void onResume_shouldContinueNormally_whenCurrentRunAlreadyVerified() {
        LockCredentialStore.savePin("123456");
        LockCredentialStore.markCurrentRunVerified();

        controller.resume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNull("No credential activity should be launched when already verified", launched);
 }

    // ============ Suppression tests ============

    /**
 * Suppressed prompt → no prompt launched on resume.
     */
    @Test
    public void onResume_shouldNotLaunchPrompt_whenPromptSuppressed() {
     // No credential stored, but prompt is suppressed
        assertFalse(LockCredentialStore.hasCredential());
  LockCredentialStore.markPromptSuppressedForCurrentRun();

        controller.resume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNull("No activity should be launched when prompt is suppressed", launched);
    }

    /**
     * Prompt cancelled → markPromptSuppressedForCurrentRun is called.
 */
    @Test
    public void onActivityResult_promptCanceled_shouldSuppressPrompt() {
        assertFalse("Precondition: not suppressed",
LockCredentialStore.isPromptSuppressedForCurrentRun());

        activity.onActivityResult(
                ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT,
   Activity.RESULT_CANCELED,
    null);

        assertTrue("Prompt should be suppressed after cancel",
              LockCredentialStore.isPromptSuppressedForCurrentRun());
    }

    // ============ onActivityResult tests ============

    /**
     * Prompt OK → starts ConfirmDeviceActivity (credential verification flow).
     */
    @Test
    public void onActivityResult_promptOk_shouldStartVerification() {
        activity.onActivityResult(
         ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT,
        Activity.RESULT_OK,
                null);

     ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
        assertNotNull("Should launch ConfirmDeviceActivity after prompt OK", launched);
        assertEquals(ConfirmDeviceActivity.class.getName(),
     launched.intent.getComponent().getClassName());
 assertEquals(ActivMain.REQUEST_CONFIRM_DEVICE, launched.requestCode);
    }

    /**
     * Prompt OK → resetCurrentRunFlags is called (clears any stale verified/suppressed state).
     */
    @Test
 public void onActivityResult_promptOk_shouldResetCurrentRunFlags() {
        // Set up stale state
     LockCredentialStore.markCurrentRunVerified();
        LockCredentialStore.markPromptSuppressedForCurrentRun();

        activity.onActivityResult(
       ActivMain.REQUEST_LOCK_CREDENTIAL_PROMPT,
            Activity.RESULT_OK,
     null);

        assertFalse("currentRunVerified should be reset",
                LockCredentialStore.isCurrentRunVerified());
assertFalse("promptSuppressed should be reset",
 LockCredentialStore.isPromptSuppressedForCurrentRun());
    }

    // ============ Non-OPPO test ============

    /**
     * Samsung device → credential gate does not activate.
     */
    @Test
    public void onResume_shouldNotUseGate_whenNotOppo() {
        DeviceUtils.setBrandForTest("samsung");

        controller.resume();

        ShadowActivity.IntentForResult launched = shadow.getNextStartedActivityForResult();
 assertNull("No credential gate activity on Samsung", launched);
    }

    // ============ Anti-debounce test ============

    /**
     * Second onResume while first launch is in-flight → no second launch.
   */
    @Test
    public void onResume_shouldNotDoubleLaunch_whenAlreadyLaunching() {
        assertFalse(LockCredentialStore.hasCredential());

        // First resume triggers the prompt launch
    controller.resume();
        ShadowActivity.IntentForResult first = shadow.getNextStartedActivityForResult();
        assertNotNull("First resume should launch prompt", first);

// Second resume while credentialGateLaunching is still true
     // (onActivityResult hasn't been called yet to reset it)
        activity.onResume();
        ShadowActivity.IntentForResult second = shadow.getNextStartedActivityForResult();
        assertNull("Second resume should NOT launch another activity (anti-debounce)", second);
    }
}
