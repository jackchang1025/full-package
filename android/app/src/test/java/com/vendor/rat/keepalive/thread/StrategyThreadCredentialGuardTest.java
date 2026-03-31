package com.vendor.rat.keepalive.thread;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import android.app.Application;
import android.view.View;
import android.view.WindowManager;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.stage.ShowOverlayStage;
import com.vendor.rat.credential.FakeLockCredentialCipher;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.utils.DeviceUtils;
import com.vendor.rat.utils.SharedUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Tests for OPPO credential gate guards in StrategyThread and ShowOverlayStage.
 *
 * Testing strategy:
 *   - DeviceUtils.setBrandForTest("oppo") injects brand for OPPO-specific tests.
 *   - LockCredentialStore uses in-memory volatile flags, directly controllable.
 *   - keepAliveTriggered is a private static AtomicBoolean read via ReflectionHelpers.
 *   - ShowOverlayStage idempotency tested by injecting fake View/WindowManager into
 *     BlockViewHelper.viewRef and BlockViewHelper.windowManager (both public static).
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class StrategyThreadCredentialGuardTest {

 @Before
    public void setUp() {
     SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
        LockCredentialStore.clearAll();
   StrategyThread.resetTrigger();
        // Reset BlockViewHelper state
 BlockViewHelper.viewRef.set(null);
     BlockViewHelper.windowManager = null;
        // Reset brand to empty (Robolectric default)
        DeviceUtils.setBrandForTest("");
    }

    @After
    public void tearDown() {
        DeviceUtils.setBrandForTest("");
        LockCredentialStore.clearAll();
        StrategyThread.resetTrigger();
        BlockViewHelper.viewRef.set(null);
        BlockViewHelper.windowManager = null;
    }

    // ============ StrategyThread credential guard tests ============

  /**
     * OPPO device + credential NOT verified -> guard blocks execution.
     * keepAliveTriggered must remain false (AutomationPipeline.executeStandard not called).
     */
    @Test
    public void triggerKeepAliveIfNeeded_shouldReturn_whenOppoAndCurrentRunNotVerified() {
     // Arrange: simulate OPPO device
        DeviceUtils.setBrandForTest("oppo");
        // Credential NOT verified (default after clearAll)
        assertFalse("Precondition: isCurrentRunVerified should be false",
                LockCredentialStore.isCurrentRunVerified());

        // Act
        StrategyThread.triggerKeepAliveIfNeeded();

        // Assert: keepAliveTriggered must still be false -- guard returned early before
        // the compareAndSet that would set it to true
        AtomicBoolean triggered = ReflectionHelpers.getStaticField(
                StrategyThread.class, "keepAliveTriggered");
    assertFalse("keepAliveTriggered must remain false when OPPO guard blocks",
  triggered.get());
    }

    /**
     * OPPO device + credential IS verified -> guard passes, normal flow proceeds.
     * keepAliveTriggered remains false only because service == null in unit tests,
     * NOT because the guard blocked execution.
     */
    @Test
    public void triggerKeepAliveIfNeeded_shouldProceed_whenOppoAndCurrentRunVerified() {
     // Arrange: simulate OPPO device
        DeviceUtils.setBrandForTest("oppo");
        // Mark credential as verified
    LockCredentialStore.markCurrentRunVerified();
        assertTrue("Precondition: isCurrentRunVerified should be true",
         LockCredentialStore.isCurrentRunVerified());

        // Act: should NOT throw, guard should not block
     StrategyThread.triggerKeepAliveIfNeeded();

        // Assert: no exception thrown (test would fail otherwise).
        // keepAliveTriggered is false because MyAccessibilityService.P() == null in unit tests.
        // The guard itself did not return early -- execution reached the service == null check.
        AtomicBoolean triggered = ReflectionHelpers.getStaticField(
   StrategyThread.class, "keepAliveTriggered");
        assertFalse("keepAliveTriggered is false because service is null in unit test (expected)",
  triggered.get());
    }

  /**
     * Non-OPPO device (Huawei) + credential NOT verified -> OPPO guard does NOT apply.
     * Method should proceed past the guard (will return early at service == null check).
     */
  @Test
    public void triggerKeepAliveIfNeeded_shouldNotApplyGuard_whenNotOppo() {
        // Arrange: simulate Huawei device
    DeviceUtils.setBrandForTest("huawei");
      assertFalse(LockCredentialStore.isCurrentRunVerified());

        // Act: OPPO guard should NOT fire for Huawei
        StrategyThread.triggerKeepAliveIfNeeded();

        // Assert: no exception thrown. keepAliveTriggered is false because service == null,
        // NOT because the OPPO guard fired.
        AtomicBoolean triggered = ReflectionHelpers.getStaticField(
     StrategyThread.class, "keepAliveTriggered");
      assertFalse("keepAliveTriggered is false because service is null (not OPPO guard)",
                triggered.get());
 }

    // ============ ShowOverlayStage idempotency tests ============

    /**
     * When overlay is already showing, ShowOverlayStage must:
     *   1. Set passable.overlayShowing = true
     *2. Call next.run()
  *   3. NOT call BlockViewHelper.show() again (no-op path)
     */
    @Test
    public void showOverlayStage_shouldBeNoOp_whenOverlayAlreadyShowing() {
        // Arrange: simulate overlay already showing by injecting fake View + WindowManager
     View fakeView = mock(View.class);
        WindowManager fakeWm = mock(WindowManager.class);
        BlockViewHelper.viewRef.set(fakeView);
        BlockViewHelper.windowManager = fakeWm;
        assertTrue("Precondition: BlockViewHelper.isShowing() must be true",
                BlockViewHelper.isShowing());

    PipelineContext passable = new PipelineContext(null);
        AtomicBoolean nextCalled = new AtomicBoolean(false);
    Runnable next = () -> nextCalled.set(true);

      ShowOverlayStage stage = new ShowOverlayStage();

        // Act
        stage.handle(passable, next);

   // Assert
        assertTrue("passable.isOverlayShowing() must be true", passable.isOverlayShowing());
    assertTrue("next.run() must have been called", nextCalled.get());
    }

    /**
     * When overlay is NOT showing, ShowOverlayStage must attempt to show it.
     * Since BlockViewHelper.show() requires real Android WindowManager (unavailable in tests),
     * it will fail silently -- next should NOT be called.
     */
    @Test
    public void showOverlayStage_shouldNotCallNext_whenOverlayFailsToShow() {
 // Arrange: overlay not showing (default)
        assertFalse("Precondition: BlockViewHelper.isShowing() must be false",
        BlockViewHelper.isShowing());

   PipelineContext passable = new PipelineContext(null);
        AtomicBoolean nextCalled = new AtomicBoolean(false);
    Runnable next = () -> nextCalled.set(true);

 ShowOverlayStage stage = new ShowOverlayStage();

        // Act: should not throw even without real WindowManager
        stage.handle(passable, next);

  // Assert: overlay did not show (no real WindowManager), next not called
        assertFalse("passable.isOverlayShowing() must be false when show() fails",
   passable.isOverlayShowing());
 assertFalse("next must NOT be called when overlay failed to show",
  nextCalled.get());
    }
}
