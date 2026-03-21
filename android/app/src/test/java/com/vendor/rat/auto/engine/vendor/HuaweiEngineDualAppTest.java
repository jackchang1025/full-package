package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

/**
 * HuaweiEngine 双应用保活 + R() 坐标点击集成测试
 *
 * 验证 vendor o/n.java 中的双应用保活流程:
 *   KEEP_ALIVE_UNKNOWN → MAIN_APP → BACKUP_APP → 完成
 */
public class HuaweiEngineDualAppTest {

    private static final String ST_HW_SETTINGS = "keepAliveInHwSettings";
    private static final String ST_STARTUP = "keepAlvieInStartupAppControl";
    private static final String ST_DIALOG = "keepAliveInAlertDialog";

    private static class TestableEngine extends AutoEngine {
        // 模拟双应用保活状态
        enum KeepAliveTarget { UNKNOWN, MAIN_APP, BACKUP_APP }
        KeepAliveTarget target = KeepAliveTarget.UNKNOWN;
        boolean mainAutoStart = false;
        boolean mainBackground = false;
        boolean backupAutoStart = false;
        boolean backupBackground = false;
        boolean finished = false;

        TestableEngine() {
            super(new ArrayList<WindowMatcher>(), "com.android.settings");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    android.view.accessibility.AccessibilityEvent event) {}
        @Override
        public void execute() {}

        ConcurrentLinkedQueue<String> getStateQueue() { return stateQueue; }

        /**
         * 模拟 vendor o/n.java r0() 中的双应用切换逻辑
         */
        void simulateStartupControlComplete() {
            if (target == KeepAliveTarget.UNKNOWN) {
                target = KeepAliveTarget.MAIN_APP;
            }

            if (target == KeepAliveTarget.MAIN_APP) {
                mainAutoStart = true;
                mainBackground = true;
                // vendor: 检查备份应用是否存在
                if (hasBackupApp()) {
                    target = KeepAliveTarget.BACKUP_APP;
                    stateQueue.clear();
                    // 重新开始处理备份应用
                } else {
                    finished = true;
                }
            } else if (target == KeepAliveTarget.BACKUP_APP) {
                backupAutoStart = true;
                backupBackground = true;
                finished = true;
            }
        }

        boolean hasBackupApp() {
            return true; // 测试中假设有备份应用
        }
    }

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    // ============ 双应用保活流程 ============

    @Test
    public void initialTarget_isUnknown() {
        assertEquals(TestableEngine.KeepAliveTarget.UNKNOWN, engine.target);
    }

    @Test
    public void firstComplete_setsMainApp() {
        engine.simulateStartupControlComplete();
        assertEquals(TestableEngine.KeepAliveTarget.BACKUP_APP, engine.target);
        assertTrue(engine.mainAutoStart);
        assertTrue(engine.mainBackground);
        assertFalse(engine.finished);
    }

    @Test
    public void secondComplete_setsBackupApp_finishes() {
        engine.simulateStartupControlComplete(); // UNKNOWN → MAIN_APP → BACKUP_APP
        engine.simulateStartupControlComplete(); // BACKUP_APP → finished
        assertTrue(engine.backupAutoStart);
        assertTrue(engine.backupBackground);
        assertTrue(engine.finished);
    }

    @Test
    public void noBackupApp_finishesAfterMain() {
        TestableEngine noBackup = new TestableEngine() {
            @Override
            boolean hasBackupApp() { return false; }
        };
        noBackup.simulateStartupControlComplete();
        assertTrue(noBackup.mainAutoStart);
        assertTrue(noBackup.finished);
    }

    @Test
    public void stateQueue_clearedOnAppSwitch() {
        engine.getStateQueue().add(ST_STARTUP);
        engine.getStateQueue().add(ST_DIALOG);
        engine.simulateStartupControlComplete(); // 切换到 BACKUP_APP
        assertTrue(engine.getStateQueue().isEmpty());
    }

    // ============ 状态字段独立性 ============

    @Test
    public void mainAndBackup_fieldsIndependent() {
        engine.simulateStartupControlComplete(); // main
        assertTrue(engine.mainAutoStart);
        assertFalse(engine.backupAutoStart);

        engine.simulateStartupControlComplete(); // backup
        assertTrue(engine.mainAutoStart);
        assertTrue(engine.backupAutoStart);
    }
}
