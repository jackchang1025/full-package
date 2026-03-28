package com.vendor.rat.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Application;

import com.vendor.rat.credential.FakeLockCredentialCipher;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.utils.SharedUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class LockCredentialPromptActivityTest {

    private LockCredentialPromptActivity activity;

    @Before
    public void setUp() {
      SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
        LockCredentialStore.clearAll();
        activity = Robolectric.buildActivity(LockCredentialPromptActivity.class)
       .create()
                .resume()
        .get();
    }

    @Test
    public void submitPin_shouldSaveCredentialAndFinishWithOk() {
        enterPin("123456");
        activity.onSubmitClicked();

        assertEquals("123456", LockCredentialStore.getPin());
      assertTrue(activity.isFinishing());
        ShadowActivity shadow = Shadows.shadowOf(activity);
        assertEquals(Activity.RESULT_OK, shadow.getResultCode());
    }

@Test
    public void submitShortPin_shouldShowErrorAndStay() {
        enterPin("123");
      activity.onSubmitClicked();

        assertFalse(activity.isFinishing());
  String latestToast = ShadowToast.getTextOfLatestToast();
   assertNotNull("Toast should be shown for short PIN", latestToast);
        assertTrue("Toast should mention 6-digit requirement",
      latestToast.contains("6"));
    }

    @Test
    public void deleteDigit_shouldRemoveLastDigit() {
enterPin("123");
        activity.onDeleteClicked();

     assertEquals("12", activity.getCurrentPin());
    }

@Test
    public void digitClicked_shouldNotExceedSixDigits() {
        enterPin("1234567");

        assertEquals(6, activity.getCurrentPin().length());
        assertEquals("123456", activity.getCurrentPin());
    }

    @Test
    public void backPress_shouldFinishWithCanceled() {
    activity.onBackPressed();

        assertTrue(activity.isFinishing());
        ShadowActivity shadow = Shadows.shadowOf(activity);
        assertEquals(Activity.RESULT_CANCELED, shadow.getResultCode());
    }

    @Test
    public void submitPin_shouldResetCurrentRunFlags() {
        assertFalse("Precondition: should not be verified before marking",
       LockCredentialStore.isCurrentRunVerified());

        LockCredentialStore.markCurrentRunVerified();
   assertTrue("Precondition: should be verified after marking",
        LockCredentialStore.isCurrentRunVerified());

        enterPin("123456");
  activity.onSubmitClicked();

        assertFalse("savePin should reset currentRunVerified",
    LockCredentialStore.isCurrentRunVerified());
    }

    @Test
    public void deleteOnEmpty_shouldNotCrash() {
        activity.onDeleteClicked();
    assertEquals("", activity.getCurrentPin());
    }

    @Test
    public void pinDisplay_shouldMaskDigits() {
        enterPin("123");
        assertEquals("123", activity.getCurrentPin());
      String displayText = activity.getPinDisplayText();
     assertEquals("\u25CF\u25CF\u25CF", displayText);
    }

    @Test
    public void initialState_shouldHaveEmptyPin() {
        assertEquals("", activity.getCurrentPin());
    }

    @Test
    public void submitPin_shouldClearPinBufferAfterSave() {
        enterPin("123456");
        activity.onSubmitClicked();

  // After submit, the internal buffer should be cleared (security hygiene)
   assertEquals("", activity.getCurrentPin());
    }

    /**
     * Helper: enter each character of the given string as a digit click.
     */
    private void enterPin(String digits) {
        for (char c : digits.toCharArray()) {
  activity.onDigitClicked(c);
        }
    }
}
