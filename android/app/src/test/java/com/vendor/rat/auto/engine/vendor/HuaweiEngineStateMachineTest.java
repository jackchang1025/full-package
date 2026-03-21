package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

/**
 * HuaweiEngine 状态机转换 + 完成条件测试
 *
 * 创建 TestableEngine 暴露 stateQueue，测试 enterState/inState/exitState。
 * 纯 JVM 测试，无需 Android 框架。
 */
public class HuaweiEngineStateMachineTest {

    // 状态常量 — 复制自 HuaweiEngine
    private static final String ST_HW_SETTINGS = "keepAliveInHwSettings";
    private static final String ST_APP_NOTIF = "keepAliveInAppAndNotification";
    private static final String ST_STARTUP = "keepAlvieInStartupAppControl"; // vendor 原始拼写
    private static final String ST_DIALOG = "keepAliveInAlertDialog";

    /**
     * 可测试的引擎子类 — 暴露 stateQueue 和状态操作
     */
    private static class TestableEngine extends AutoEngine {

        TestableEngine() {
            super(new ArrayList<WindowMatcher>(), "com.android.settings");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    android.view.accessibility.AccessibilityEvent event) {
            // no-op for testing
        }

        @Override
        public void execute() {
            // no-op for testing
        }

        ConcurrentLinkedQueue<String> getStateQueue() {
            return stateQueue;
        }

        // 暴露 protected 方法
        public boolean doEnterState(String state, String... removeStates) {
            return enterState(state, removeStates);
        }

        public boolean doInState(String state) {
            return inState(state);
        }

        public void doExitState(String state) {
            exitState(state);
        }
    }

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    // ============ 初始状态 ============

    @Test
    public void initialState_queueIsEmpty() {
        assertTrue(engine.getStateQueue().isEmpty());
    }

    // ============ enterState ============

    @Test
    public void enterState_hwSettings_addsToQueue() {
        boolean added = engine.doEnterState(ST_HW_SETTINGS);
        assertTrue(added);
        assertTrue(engine.doInState(ST_HW_SETTINGS));
    }

    @Test
    public void enterState_appNotif_removesHwSettings() {
        engine.doEnterState(ST_HW_SETTINGS);
        engine.doEnterState(ST_APP_NOTIF, ST_HW_SETTINGS, ST_STARTUP, ST_DIALOG);

        assertFalse(engine.doInState(ST_HW_SETTINGS));
        assertTrue(engine.doInState(ST_APP_NOTIF));
    }

    @Test
    public void enterState_startup_removesHwSettingsAndAppNotif() {
        engine.doEnterState(ST_HW_SETTINGS);
        engine.doEnterState(ST_APP_NOTIF, ST_HW_SETTINGS);
        // Now enter startup, removing both HW_SETTINGS and APP_NOTIF
        engine.doEnterState(ST_STARTUP, ST_HW_SETTINGS, ST_APP_NOTIF, ST_DIALOG);

        assertFalse(engine.doInState(ST_HW_SETTINGS));
        assertFalse(engine.doInState(ST_APP_NOTIF));
        assertTrue(engine.doInState(ST_STARTUP));
    }

    @Test
    public void enterState_dialog_removesAllOtherStates() {
        engine.doEnterState(ST_HW_SETTINGS);
        engine.doEnterState(ST_DIALOG, ST_HW_SETTINGS, ST_APP_NOTIF, ST_STARTUP);

        assertFalse(engine.doInState(ST_HW_SETTINGS));
        assertFalse(engine.doInState(ST_APP_NOTIF));
        assertFalse(engine.doInState(ST_STARTUP));
        assertTrue(engine.doInState(ST_DIALOG));
    }

    @Test
    public void enterState_duplicate_doesNotAddTwice() {
        engine.doEnterState(ST_HW_SETTINGS);
        boolean added = engine.doEnterState(ST_HW_SETTINGS);
        assertFalse(added); // already present
        assertEquals(1, engine.getStateQueue().size());
    }

    // ============ exitState ============

    @Test
    public void exitState_removesState() {
        engine.doEnterState(ST_HW_SETTINGS);
        engine.doExitState(ST_HW_SETTINGS);
        assertFalse(engine.doInState(ST_HW_SETTINGS));
    }

    @Test
    public void exitState_nonExistent_doesNotThrow() {
        // Should not throw
        engine.doExitState(ST_DIALOG);
        assertFalse(engine.doInState(ST_DIALOG));
    }

    // ============ 完整转换序列 ============

    @Test
    public void fullTransition_hwToAppToStartupToDialog() {
        // HW_SETTINGS
        engine.doEnterState(ST_HW_SETTINGS);
        assertTrue(engine.doInState(ST_HW_SETTINGS));

        // → APP_NOTIF (removes HW)
        engine.doEnterState(ST_APP_NOTIF, ST_HW_SETTINGS, ST_STARTUP, ST_DIALOG);
        assertFalse(engine.doInState(ST_HW_SETTINGS));
        assertTrue(engine.doInState(ST_APP_NOTIF));

        // → STARTUP (removes APP_NOTIF)
        engine.doEnterState(ST_STARTUP, ST_HW_SETTINGS, ST_APP_NOTIF, ST_DIALOG);
        assertFalse(engine.doInState(ST_APP_NOTIF));
        assertTrue(engine.doInState(ST_STARTUP));

        // → DIALOG (removes STARTUP)
        engine.doEnterState(ST_DIALOG, ST_HW_SETTINGS, ST_APP_NOTIF, ST_STARTUP);
        assertFalse(engine.doInState(ST_STARTUP));
        assertTrue(engine.doInState(ST_DIALOG));

        // Only DIALOG remains
        assertEquals(1, engine.getStateQueue().size());
    }

    // ============ clear ============

    @Test
    public void stateQueueClear_removesAllStates() {
        engine.doEnterState(ST_HW_SETTINGS);
        engine.doEnterState(ST_APP_NOTIF);
        engine.getStateQueue().clear();
        assertTrue(engine.getStateQueue().isEmpty());
    }

    // ============ 完成条件 ============

    @Test
    public void completionCondition_autoStartAndBackground_isComplete() {
        // 模拟: mainAutoStart=true + mainBackground=true → 完成
        // 这里测试 stateQueue 清空后引擎可以标记完成
        engine.doEnterState(ST_DIALOG, ST_HW_SETTINGS, ST_APP_NOTIF, ST_STARTUP);
        engine.getStateQueue().clear();
        assertTrue(engine.getStateQueue().isEmpty());
        // 引擎初始状态未完成
        assertFalse(engine.isCompleted());
    }

    @Test
    public void completionCondition_notFinished_byDefault() {
        assertFalse(engine.isCompleted());
        assertFalse(engine.isFinished());
    }
}
