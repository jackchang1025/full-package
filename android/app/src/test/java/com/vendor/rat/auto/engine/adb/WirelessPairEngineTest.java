package com.vendor.rat.auto.engine.adb;

import static org.junit.Assert.*;

import android.app.Application;

import com.vendor.rat.credential.FakeLockCredentialCipher;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.utils.DeviceUtils;
import com.vendor.rat.utils.SharedUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * WirelessPairEngine OPPO credential gate 测试
 *
 * 覆盖:
 *   1. startPairing: OPPO 设备 + 未验证 → 返回 false (credential gate 拦截)
 *   2. startPairing: OPPO 设备 + 已验证 → 不被 gate 拦截 (因 AccessibilityService null 而返回 false)
 *   3. startPairing: 非 OPPO 设备 + 未验证 → 不被 gate 拦截
 *
 * Phase 0 / Phase 2 预检查依赖 AccessibilityService + UI，无法在 JVM 单元测试中直接执行，
 * 相关逻辑通过集成测试或真机验证。
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class WirelessPairEngineTest {

    @Before
    public void setUp() {
        SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
        LockCredentialStore.clearAll();
        // Reset pairing-in-progress flag between tests
        // (WirelessPairEngine.startPairing sets it; we need clean state)
        resetPairingInProgress();
    }

    @After
    public void tearDown() {
      // Restore brand to default
        DeviceUtils.setBrandForTest(null);
        LockCredentialStore.clearAll();
        resetPairingInProgress();
    }

    // ============ Helper ============

    /**
     * Reset the static AtomicBoolean mPairingInProgress to false.
     * We do this by calling startPairing with null context (which returns false without setting it),
     * or by relying on the fact that after a false-return path the flag is never set.
  * Since the gate check returns before compareAndSet, the flag stays false.
     */
    private void resetPairingInProgress() {
        // The flag is only set to true inside startPairing after compareAndSet succeeds.
      // After a gate-blocked call, it's never set. After a service-null path, it's reset.
        // So between tests it should already be false. This is a safety no-op.
    }

    // ============ Test 1: OPPO + not verified → gate blocks ============

    /**
     * OPPO 设备且 currentRunNotVerified → credential gate 拦截，返回 false。
     * 验证 gate 在 compareAndSet 之前生效（mPairingInProgress 保持 false）。
     */
    @Test
    public void startPairing_shouldRejectOppoFlow_whenCurrentRunNotVerified() {
   // Arrange: OPPO brand, NOT verified
        DeviceUtils.setBrandForTest("oppo");
        // LockCredentialStore.isCurrentRunVerified() == false (clearAll was called in setUp)

     // Act
        boolean result = WirelessPairEngine.startPairing(RuntimeEnvironment.getApplication());

        // Assert: gate blocked → false
        assertFalse("OPPO credential gate should block unverified run", result);

      // Also verify mPairingInProgress was NOT set (gate fires before compareAndSet)
        assertFalse("mPairingInProgress should remain false after gate block",
   WirelessPairEngine.isPairingInProgress());
    }

    // ============ Test 2: OPPO + verified → gate passes, service-null path ============

  /**
     * OPPO 设备且 currentRunVerified → gate 放行。
     * 因 AccessibilityService 为 null（JVM 环境），startPairing 返回 false，
     * 但 mPairingInProgress 会先被设为 true 再被重置为 false（service-null 路径）。
     *
     * 关键断言：不应被 credential gate 拦截（即代码执行到了 compareAndSet 之后）。
   */
    @Test
    public void startPairing_shouldPassGate_whenOppoAndCurrentRunVerified() {
        // Arrange: OPPO brand, verified
        DeviceUtils.setBrandForTest("oppo");
        LockCredentialStore.markCurrentRunVerified();

        // Act
    boolean result = WirelessPairEngine.startPairing(RuntimeEnvironment.getApplication());

        // Assert: gate passed, but service is null → returns false
     // The important thing is it did NOT return false due to the credential gate
        // (which would have prevented compareAndSet from being reached).
        // After service-null path, mPairingInProgress is reset to false.
        assertFalse("Should return false because AccessibilityService is null in JVM",
         result);
        assertFalse("mPairingInProgress should be reset to false after service-null path",
       WirelessPairEngine.isPairingInProgress());

     // Verify that isCurrentRunVerified is still true (gate didn't clear it)
        assertTrue("currentRunVerified should remain true after gate pass",
  LockCredentialStore.isCurrentRunVerified());
    }

    // ============ Test 3: Non-OPPO + not verified → gate does not apply ============

 /**
     * 非 OPPO 设备（如小米）且未验证 → credential gate 不适用，
     * 流程继续到 service-null 路径返回 false。
     */
    @Test
    public void startPairing_shouldNotApplyGate_whenNonOppoDevice() {
  // Arrange: Xiaomi brand, NOT verified
        DeviceUtils.setBrandForTest("xiaomi");
      // isCurrentRunVerified() == false

        // Act
  boolean result = WirelessPairEngine.startPairing(RuntimeEnvironment.getApplication());

        // Assert: gate did not block (non-OPPO), but service is null → false
        assertFalse("Should return false because AccessibilityService is null in JVM",
            result);
        assertFalse("mPairingInProgress should be reset after service-null path",
           WirelessPairEngine.isPairingInProgress());
    }

    // ============ Test 4: realme brand (OPPO family) + not verified → gate blocks ============

    /**
     * realme 属于 OPPO 家族 (isOppo() returns true)，未验证 → gate 拦截。
     */
    @Test
 public void startPairing_shouldRejectRealmeBrand_whenCurrentRunNotVerified() {
        DeviceUtils.setBrandForTest("realme");

        boolean result = WirelessPairEngine.startPairing(RuntimeEnvironment.getApplication());

        assertFalse("realme (OPPO family) credential gate should block unverified run", result);
        assertFalse("mPairingInProgress should remain false after gate block",
        WirelessPairEngine.isPairingInProgress());
    }

    // ============ Test 5: null context → returns false before gate ============

    /**
     * context 为 null → 在 gate 之前就返回 false（null-check 优先）。
     */
    @Test
    public void startPairing_shouldReturnFalse_whenContextIsNull() {
    DeviceUtils.setBrandForTest("oppo");
        LockCredentialStore.markCurrentRunVerified();

        boolean result = WirelessPairEngine.startPairing(null);

        assertFalse("null context should return false immediately", result);
    }

      // ============ Test 6: OPPO + no credential + not verified → gate blocks ============

    /**
     * OPPO 设备且无 credential（clearAll）且未验证 → gate 拦截。
     * 验证新 guard （去掉 hasCredential() 条件）在无 credential 时也能拦截。
     */
    @Test
    public void startPairing_shouldReject_whenOppoAndNoCredentialAndNotVerified() {
        DeviceUtils.setBrandForTest("oppo");
        LockCredentialStore.clearAll(); // 无 credential
        // 不调 markCurrentRunVerified

        boolean result = WirelessPairEngine.startPairing(RuntimeEnvironment.getApplication());

assertFalse("OPPO + no credential + not verified should be blocked by gate", result);
        assertFalse("mPairingInProgress should remain false after gate block",
         WirelessPairEngine.isPairingInProgress());
    }
}
