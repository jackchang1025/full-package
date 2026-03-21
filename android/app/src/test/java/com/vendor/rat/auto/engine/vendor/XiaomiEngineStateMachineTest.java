package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

/**
 * XiaomiEngine 状态机测试
 *
 * 测试 stateQueue 的 enterState / inState / exitState 行为，
 * 以及完成条件、keepAliveType 等初始状态。
 *
 * 纯 JVM 测试，无需 Android 框架。
 *
 * 基于逆向: o/q.java (498 行)
 * 状态常量对应: ConcurrentLinkedQueue "keepAliveIn*" 字符串
 */
public class XiaomiEngineStateMachineTest {

    // ====== 状态常量 — 复制自 XiaomiEngine ======
    private static final String ST_APP_DETAIL   = "keepAliveInAppDetail";
    private static final String ST_AUTO_START   = "keepAliveInAutoStartManage";
    private static final String ST_APP_PERMS    = "keepAliveInAppPermissions";
    private static final String ST_OTHER_PERMS  = "keepAliveInOtherPermissions";
    private static final String ST_PERM_MODIFY  = "keepAliveInPermissionModify";

    /**
     * 可测试的最小子类，暴露 protected 状态机方法。
     * 模仿 HuaweiEngineStateMachineTest 的 TestableEngine 模式。
     */
    static class TestableEngine extends AutoEngine {

        TestableEngine() {
            super("com.miui.securitycenter");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    android.view.accessibility.AccessibilityEvent event) {
        }

        @Override
        public void execute() {
        }

        public ConcurrentLinkedQueue<String> getStateQueue() {
            return stateQueue;
        }

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

    @Test
    public void initialState_notCompleted() {
        assertFalse(engine.isCompleted());
    }

    @Test
    public void initialState_notFinished() {
        assertFalse(engine.isFinished());
    }

    // ============ enterState ============

    @Test
    public void enterState_appDetail_addsToQueue() {
        boolean added = engine.doEnterState(ST_APP_DETAIL);
        assertTrue(added);
        assertTrue(engine.doInState(ST_APP_DETAIL));
    }

    @Test
    public void enterState_autoStart_addsToQueue() {
        boolean added = engine.doEnterState(ST_AUTO_START);
        assertTrue(added);
        assertTrue(engine.doInState(ST_AUTO_START));
    }

    @Test
    public void enterState_duplicate_returnsFalse() {
        engine.doEnterState(ST_APP_DETAIL);
        boolean added = engine.doEnterState(ST_APP_DETAIL);
        assertFalse(added);
        assertEquals(1, engine.getStateQueue().size());
    }

    @Test
    public void enterState_duplicate_doesNotDuplicate() {
        engine.doEnterState(ST_AUTO_START);
        engine.doEnterState(ST_AUTO_START);
        long count = engine.getStateQueue().stream()
                .filter(s -> s.equals(ST_AUTO_START)).count();
        assertEquals(1, count);
    }

    // ============ 逆向对齐: f0() 分支 — 进入 App 详情 ============
    //
    // 对应 o/q.java u() 中:
    //   concurrentLinkedQueue.remove(ST_AUTO_START)
    //   concurrentLinkedQueue.remove(ST_APP_PERMS)
    //   concurrentLinkedQueue.remove(ST_OTHER_PERMS)
    //   concurrentLinkedQueue.remove(ST_PERM_MODIFY)
    //   if (!contains(ST_APP_DETAIL)) add(ST_APP_DETAIL)

    @Test
    public void enterAppDetail_removesAutoStart() {
        engine.doEnterState(ST_AUTO_START);
        engine.doEnterState(ST_APP_DETAIL,
                ST_AUTO_START, ST_APP_PERMS, ST_OTHER_PERMS, ST_PERM_MODIFY);

        assertFalse(engine.doInState(ST_AUTO_START));
        assertTrue(engine.doInState(ST_APP_DETAIL));
    }

    @Test
    public void enterAppDetail_removesAllFourSiblingStates() {
        engine.doEnterState(ST_AUTO_START);
        engine.doEnterState(ST_APP_PERMS);
        engine.doEnterState(ST_OTHER_PERMS);
        engine.doEnterState(ST_PERM_MODIFY);

        engine.doEnterState(ST_APP_DETAIL,
                ST_AUTO_START, ST_APP_PERMS, ST_OTHER_PERMS, ST_PERM_MODIFY);

        assertFalse(engine.doInState(ST_AUTO_START));
        assertFalse(engine.doInState(ST_APP_PERMS));
        assertFalse(engine.doInState(ST_OTHER_PERMS));
        assertFalse(engine.doInState(ST_PERM_MODIFY));
        assertTrue(engine.doInState(ST_APP_DETAIL));
        assertEquals(1, engine.getStateQueue().size());
    }

    // ============ 逆向对齐: h0() 分支 — 进入自启动管理 ============
    //
    // 对应 o/q.java u() 中:
    //   concurrentLinkedQueue.remove(ST_APP_DETAIL)
    //   concurrentLinkedQueue.remove(ST_APP_PERMS)
    //   concurrentLinkedQueue.remove(ST_OTHER_PERMS)
    //   concurrentLinkedQueue.remove(ST_PERM_MODIFY)
    //   if (!contains(ST_AUTO_START)) add(ST_AUTO_START)

    @Test
    public void enterAutoStart_removesAppDetail() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_AUTO_START,
                ST_APP_DETAIL, ST_APP_PERMS, ST_OTHER_PERMS, ST_PERM_MODIFY);

        assertFalse(engine.doInState(ST_APP_DETAIL));
        assertTrue(engine.doInState(ST_AUTO_START));
    }

    @Test
    public void enterAutoStart_removesAllFourSiblingStates() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_APP_PERMS);
        engine.doEnterState(ST_OTHER_PERMS);
        engine.doEnterState(ST_PERM_MODIFY);

        engine.doEnterState(ST_AUTO_START,
                ST_APP_DETAIL, ST_APP_PERMS, ST_OTHER_PERMS, ST_PERM_MODIFY);

        assertFalse(engine.doInState(ST_APP_DETAIL));
        assertFalse(engine.doInState(ST_APP_PERMS));
        assertFalse(engine.doInState(ST_OTHER_PERMS));
        assertFalse(engine.doInState(ST_PERM_MODIFY));
        assertTrue(engine.doInState(ST_AUTO_START));
        assertEquals(1, engine.getStateQueue().size());
    }

    // ============ exitState ============

    @Test
    public void exitState_removesExistingState() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doExitState(ST_APP_DETAIL);
        assertFalse(engine.doInState(ST_APP_DETAIL));
    }

    @Test
    public void exitState_nonExistent_noError() {
        // Should not throw
        engine.doExitState(ST_APP_DETAIL);
        assertFalse(engine.doInState(ST_APP_DETAIL));
    }

    // ============ 多状态共存 ============

    @Test
    public void multipleStates_independentlyTracked() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_AUTO_START);

        assertTrue(engine.doInState(ST_APP_DETAIL));
        assertTrue(engine.doInState(ST_AUTO_START));
        assertEquals(2, engine.getStateQueue().size());
    }

    @Test
    public void multipleStates_exitOneLeaveOther() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_AUTO_START);
        engine.doExitState(ST_APP_DETAIL);

        assertFalse(engine.doInState(ST_APP_DETAIL));
        assertTrue(engine.doInState(ST_AUTO_START));
        assertEquals(1, engine.getStateQueue().size());
    }

    // ============ 状态机状态转换序列 ============

    @Test
    public void stateTransition_appDetailThenAutoStart_correctSequence() {
        // App 详情窗口出现
        engine.doEnterState(ST_APP_DETAIL);
        assertTrue(engine.doInState(ST_APP_DETAIL));

        // 切换到自启动管理 (移除 APP_DETAIL)
        engine.doEnterState(ST_AUTO_START,
                ST_APP_DETAIL, ST_APP_PERMS, ST_OTHER_PERMS, ST_PERM_MODIFY);
        assertFalse(engine.doInState(ST_APP_DETAIL));
        assertTrue(engine.doInState(ST_AUTO_START));

        // 只剩 AUTO_START
        assertEquals(1, engine.getStateQueue().size());
    }

    @Test
    public void stateTransition_autoStartThenAppDetail_correctSequence() {
        // 先进自启动管理
        engine.doEnterState(ST_AUTO_START);
        assertTrue(engine.doInState(ST_AUTO_START));

        // 切换到 App 详情 (移除 AUTO_START)
        engine.doEnterState(ST_APP_DETAIL,
                ST_AUTO_START, ST_APP_PERMS, ST_OTHER_PERMS, ST_PERM_MODIFY);
        assertFalse(engine.doInState(ST_AUTO_START));
        assertTrue(engine.doInState(ST_APP_DETAIL));

        assertEquals(1, engine.getStateQueue().size());
    }

    // ============ clear ============

    @Test
    public void stateQueueClear_removesAllStates() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_AUTO_START);
        engine.getStateQueue().clear();
        assertTrue(engine.getStateQueue().isEmpty());
    }

    // ============ 完成条件 ============

    @Test
    public void completionCondition_notFinished_byDefault() {
        assertFalse(engine.isCompleted());
        assertFalse(engine.isFinished());
    }

    @Test
    public void completionCondition_afterQueueClear_stillNotCompleted() {
        // stateQueue 清空不等于 finished — finish() 才设置 finished flag
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_AUTO_START);
        engine.getStateQueue().clear();
        assertTrue(engine.getStateQueue().isEmpty());
        assertFalse(engine.isCompleted());
    }

    // ============ 所有五个状态常量验证 ============

    @Test
    public void allFiveStateConstants_canBeEntered() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_AUTO_START);
        engine.doEnterState(ST_APP_PERMS);
        engine.doEnterState(ST_OTHER_PERMS);
        engine.doEnterState(ST_PERM_MODIFY);

        assertTrue(engine.doInState(ST_APP_DETAIL));
        assertTrue(engine.doInState(ST_AUTO_START));
        assertTrue(engine.doInState(ST_APP_PERMS));
        assertTrue(engine.doInState(ST_OTHER_PERMS));
        assertTrue(engine.doInState(ST_PERM_MODIFY));
        assertEquals(5, engine.getStateQueue().size());
    }

    @Test
    public void allFiveStateConstants_canBeCleared() {
        engine.doEnterState(ST_APP_DETAIL);
        engine.doEnterState(ST_AUTO_START);
        engine.doEnterState(ST_APP_PERMS);
        engine.doEnterState(ST_OTHER_PERMS);
        engine.doEnterState(ST_PERM_MODIFY);

        engine.doExitState(ST_APP_DETAIL);
        engine.doExitState(ST_AUTO_START);
        engine.doExitState(ST_APP_PERMS);
        engine.doExitState(ST_OTHER_PERMS);
        engine.doExitState(ST_PERM_MODIFY);

        assertTrue(engine.getStateQueue().isEmpty());
    }
}
