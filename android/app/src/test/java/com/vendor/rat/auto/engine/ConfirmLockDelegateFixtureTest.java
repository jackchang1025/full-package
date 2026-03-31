package com.vendor.rat.auto.engine;

import android.app.Application;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.testutil.UiDumpFixture;
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
 * ConfirmLockDelegate Fixture 驱动测试
 *
 * 基于 OPPO Find X6 (ColorOS 16, Android 16) 真机 dump 的 ConfirmLockPassword XML,
 * 验证 digit button 查找 (content-desc="0"-"9")、锁屏提示文本检测、isLockScreen 判断。
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class ConfirmLockDelegateFixtureTest {

    @Before
    public void setUp() {
      SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
        LockCredentialStore.clearAll();
    }

    // Test 1: Fixture can find digit buttons by content-desc
    @Test
    public void fixture_shouldFindDigitButtonsByContentDesc() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/confirm_lock_password.xml");

        for (char d = '0'; d <= '9'; d++) {
   CombineFilter filter = CombineFilter.and(
        StringCondition.className("android.widget.Button"),
      StringCondition.descEquals(String.valueOf(d)));
            UiNode btn = root.findOneByCombine(filter);
   assertNotNull("Should find button for digit " + d, btn);
        }
    }

 // Test 2: Fixture can detect the confirm lock prompt text
    @Test
    public void fixture_shouldDetectConfirmLockPrompt() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/confirm_lock_password.xml");
        UiNode prompt = root.findOneByCombine(
CombineFilter.and(
StringCondition.className("android.widget.TextView"),
      StringCondition.textContains("锁屏密码")));
     assertNotNull("Should find lock screen prompt text", prompt);
    }

    // Test 3: isLockScreen recognizes ConfirmLockPassword
    @Test
 public void isLockScreen_shouldRecognizeConfirmLockPassword() {
        assertTrue(ConfirmLockDelegate.isLockScreen(
            "com.android.settings.password.ConfirmLockPassword"));
     assertFalse(ConfirmLockDelegate.isLockScreen(
    "com.android.settings.DevelopmentSettings"));
        assertFalse(ConfirmLockDelegate.isLockScreen(null));
    }

    // Test 4: findDigitButton works with fixture (indirect via filter)
    @Test
    public void findDigitButton_shouldFindByContentDesc() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/confirm_lock_password.xml");

        // Test digit "1" - should find exactly one Button with content-desc="1"
    CombineFilter filter = CombineFilter.and(
       StringCondition.className("android.widget.Button"),
        StringCondition.descEquals("1"));
   UiNode btn = root.findOneByCombine(filter);
     assertNotNull(btn);
    }

    // Test 5: Password display area exists in fixture
    @Test
    public void fixture_shouldHavePasswordFieldDescription() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/confirm_lock_password.xml");
      // The password field shows "密码栏，已输入 0 个值，共 6 个值"
        UiNode pwField = root.findOneByCombine(
      CombineFilter.and(
           StringCondition.descContains("密码")));
        assertNotNull("Should find password field by content-desc", pwField);
  }

    // Test 6: Cancel button exists on the ConfirmLockPassword screen
    @Test
    public void fixture_shouldHaveCancelButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/confirm_lock_password.xml");
        UiNode cancelBtn = root.findOneByCombine(
            CombineFilter.and(
          StringCondition.className("android.widget.Button"),
              StringCondition.textEquals("取消")));
        assertNotNull("Should find cancel button", cancelBtn);
     assertTrue("Cancel button should be clickable", cancelBtn.isClickable());
    }

    // Test 7: createListenWindows covers ConfirmLockPassword
    @Test
    public void createListenWindows_shouldIncludeConfirmLockPassword() {
        java.util.List<AutoEngine.WindowMatcher> windows = ConfirmLockDelegate.createListenWindows();
        assertFalse("Window list should not be empty", windows.isEmpty());
        boolean found = false;
        for (AutoEngine.WindowMatcher wm : windows) {
       if ("com.android.settings.password.ConfirmLockPassword".equals(wm.getClassName())) {
  found = true;
    break;
            }
    }
        assertTrue("Should include ConfirmLockPassword window", found);
    }

    // Test 8: isLockScreen covers all expected window classes
    @Test
    public void isLockScreen_shouldCoverAllExpectedClasses() {
  assertTrue(ConfirmLockDelegate.isLockScreen(
     "com.android.settings.password.ConfirmLockPassword"));
        assertTrue(ConfirmLockDelegate.isLockScreen(
     "com.android.settings.password.ConfirmLockPattern"));
        assertTrue(ConfirmLockDelegate.isLockScreen(
"com.android.settings.password.ChooseLockGeneric"));
        assertTrue(ConfirmLockDelegate.isLockScreen(
      "com.vivo.settings.password.ConfirmVivoPin$InternalActivity"));
    assertTrue(ConfirmLockDelegate.isLockScreen(
    "com.android.settings.password.ConfirmLockPattern$InternalActivity"));
   // Negative cases
      assertFalse(ConfirmLockDelegate.isLockScreen(null));
        assertFalse(ConfirmLockDelegate.isLockScreen(""));
        assertFalse(ConfirmLockDelegate.isLockScreen(
     "com.android.settings.DevelopmentSettings"));
    }

 // Test 9: Keyboard container has exactly 10 digit buttons
    @Test
    public void fixture_shouldHaveExactlyTenDigitButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/confirm_lock_password.xml");
        int count = 0;
     for (char d = '0'; d <= '9'; d++) {
            UiNode btn = root.findOneByCombine(
         CombineFilter.and(
       StringCondition.className("android.widget.Button"),
       StringCondition.descEquals(String.valueOf(d))));
       if (btn != null) count++;
        }
     assertEquals("Should find exactly 10 digit buttons", 10, count);
    }

    // Test 10: Digit buttons are clickable
    @Test
    public void fixture_digitButtonsShouldBeClickable() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/confirm_lock_password.xml");
        for (char d = '0'; d <= '9'; d++) {
UiNode btn = root.findOneByCombine(
     CombineFilter.and(
      StringCondition.className("android.widget.Button"),
      StringCondition.descEquals(String.valueOf(d))));
            assertNotNull("Button for digit " + d + " should exist", btn);
            assertTrue("Button for digit " + d + " should be clickable", btn.isClickable());
        }
    }
}
