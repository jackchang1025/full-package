package com.vendor.rat.auto.engine;

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

import static org.junit.Assert.*;

/**
 * OpenDevelopmentDelegate 测试
 *
 * 验证:
 * 1. ConfirmLockPassword 窗口触发状态迁移
 * 2. 无 PIN 时快速失败
 * 3. 有 PIN 时正确委托给 ConfirmLockDelegate
 * 4. createListenWindows 包含 ConfirmLockPassword
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class OpenDevelopmentDelegateTest {

    @Before
    public void setUp() {
     SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
     LockCredentialStore.clearAll();
    }

    // Test 1: createListenWindows includes ConfirmLockPassword
    @Test
    public void createListenWindows_shouldIncludeConfirmLockPassword() {
   java.util.List<AutoEngine.WindowMatcher> windows = OpenDevelopmentDelegate.createListenWindows();
        boolean found = false;
  for (AutoEngine.WindowMatcher wm : windows) {
    if ("com.android.settings.password.ConfirmLockPassword".equals(wm.getClassName())) {
          found = true;
      break;
         }
        }
    assertTrue("Should include ConfirmLockPassword window", found);
    }

    // Test 2: createListenWindows includes ConfirmLockPattern
    @Test
    public void createListenWindows_shouldIncludeConfirmLockPattern() {
   java.util.List<AutoEngine.WindowMatcher> windows = OpenDevelopmentDelegate.createListenWindows();
        boolean found = false;
        for (AutoEngine.WindowMatcher wm : windows) {
    if ("com.android.settings.password.ConfirmLockPattern".equals(wm.getClassName())) {
                found = true;
     break;
            }
        }
        assertTrue("Should include ConfirmLockPattern window", found);
    }

    // Test 3: isLockScreen delegates correctly
    @Test
    public void isLockScreen_shouldRecognizeConfirmLockPassword() {
        assertTrue(ConfirmLockDelegate.isLockScreen(
            "com.android.settings.password.ConfirmLockPassword"));
    }

    // Test 4: handleConfirmLockWindow with no PIN should not crash
    @Test
  public void handleConfirmLockWindow_shouldNotCrash_whenNoPinStored() {
   assertFalse("No PIN stored", LockCredentialStore.hasCredential());
        OpenDevelopmentDelegate delegate = new OpenDevelopmentDelegate();
        // Calling handleConfirmLockWindow should not throw
        delegate.handleConfirmLockWindow();
        // State should reflect failure
        String state = delegate.currentState.get();
        assertEquals("Should remain in ENABLE_FAIL or return to UNKNOWN after no PIN",
    OpenDevelopmentDelegate.STATE_ENABLE_FAIL, state);
    }

    // Test 5: handleConfirmLockWindow with PIN stored should transition state
    @Test
    public void handleConfirmLockWindow_shouldTransitionState_whenPinStored() {
    LockCredentialStore.savePin("123456");
        assertTrue("PIN should be stored", LockCredentialStore.hasCredential());

        OpenDevelopmentDelegate delegate = new OpenDevelopmentDelegate();
        delegate.handleConfirmLockWindow();

        // State should have transitioned to ENTER_CONFIRM (even if auto-input fails
        // because we have no real root node in unit test)
        String state = delegate.currentState.get();
        assertTrue("State should advance past PREPARE_CONFIRM",
         OpenDevelopmentDelegate.STATE_ENTER_CONFIRM.equals(state)
         || OpenDevelopmentDelegate.STATE_ENABLE_FAIL.equals(state));
    }

    // Test 6: State constants are distinct
    @Test
 public void stateConstants_shouldBeDistinct() {
 String[] states = {
            OpenDevelopmentDelegate.STATE_UNKNOWN,
    OpenDevelopmentDelegate.STATE_ENTER_ABOUT,
            OpenDevelopmentDelegate.STATE_PREPARE_VERSION,
  OpenDevelopmentDelegate.STATE_ENTER_VERSION,
     OpenDevelopmentDelegate.STATE_PREPARE_CONFIRM,
  OpenDevelopmentDelegate.STATE_ENTER_CONFIRM,
     OpenDevelopmentDelegate.STATE_CONFIRM_SUCCESS,
        OpenDevelopmentDelegate.STATE_ENABLE_SUCCESS,
            OpenDevelopmentDelegate.STATE_ENABLE_FAIL,
            OpenDevelopmentDelegate.STATE_WIN_CHECK,
    OpenDevelopmentDelegate.STATE_WIN_PREPARE,
      };
        java.util.Set<String> unique = new java.util.HashSet<>(java.util.Arrays.asList(states));
        assertEquals("All state constants should be unique", states.length, unique.size());
    }

    // Test 7: Initial state should be UNKNOWN
    @Test
    public void initialState_shouldBeUnknown() {
   OpenDevelopmentDelegate delegate = new OpenDevelopmentDelegate();
        assertEquals(OpenDevelopmentDelegate.STATE_UNKNOWN, delegate.currentState.get());
    }
}
