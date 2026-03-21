package com.vendor.rat.auto.engine;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.model.req.ListenWindow;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * AutoEngine 电池优化对话框方法测试
 * 对应 vendor o/c.java J(), I(), N(), u() 中的电池优化检测
 */
public class AutoEngineBatteryDialogTest {

    private static class TestableEngine extends AutoEngine {
        TestableEngine() {
            super(new ArrayList<WindowMatcher>(), "com.android.settings");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    android.view.accessibility.AccessibilityEvent event) {}
        @Override
        public void execute() {}

        public void setCurrentWindow(String pkg, String cls) {
            currentPackage = pkg;
            currentClassName = cls;
        }

        public java.util.concurrent.ConcurrentLinkedQueue<String> getStateQueue() {
            return stateQueue;
        }

        public void doCheckBatteryOptimizationDialog() {
            checkBatteryOptimizationDialog();
        }
    }

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    // ============ buildBatteryDialogWindow (vendor c.java J() :68-72) ============

    @Test
    public void testBuildBatteryDialogWindow_packageIsSettings() {
        ListenWindow lw = AutoEngine.buildBatteryDialogWindow();
        assertEquals("com.android.settings", lw.getPackageName());
        assertEquals("android.app.Dialog", lw.getClassName());
    }

    @Test
    public void testBuildBatteryDialogWindow_eventTypes() {
        ListenWindow lw = AutoEngine.buildBatteryDialogWindow();
        HashSet<Integer> types = lw.getEventTypes();
        assertNotNull(types);
        assertTrue(types.contains(32));    // WINDOW_CONTENT_CHANGED
        assertTrue(types.contains(16384)); // VIEW_SCROLLED
    }

    // ============ buildBatteryAllowButtonFilters (vendor c.java I() :55-66) ============

    @Test
    public void testBuildBatteryAllowButtonFilters_twoFilters() {
        NodeFilter[] filters = AutoEngine.buildBatteryAllowButtonFilters();
        assertNotNull(filters);
        assertEquals(2, filters.length);
    }

    // ============ buildCancelButtonFilter (vendor c.java N() :119-123) ============

    @Test
    public void testBuildCancelButtonFilter_notNull() {
        CombineFilter filter = AutoEngine.buildCancelButtonFilter();
        assertNotNull(filter);
    }

    // ============ checkBatteryOptimizationDialog (vendor c.java u() :762-801) ============

    @Test
    public void testCheckBatteryDialog_notInDialog_noAction() {
        engine.setCurrentWindow("com.huawei.systemmanager", "SomeActivity");
        engine.doCheckBatteryOptimizationDialog();
        assertFalse(engine.getStateQueue().contains("keepInBatteryUnRestricted"));
    }

    @Test
    public void testCheckBatteryDialog_inDialog_addsState() {
        // 模拟当前在 com.android.settings/android.app.Dialog
        engine.setCurrentWindow("com.android.settings", "android.app.Dialog");
        engine.doCheckBatteryOptimizationDialog();
        assertTrue(engine.getStateQueue().contains("keepInBatteryUnRestricted"));
    }

    @Test
    public void testCheckBatteryDialog_alreadyInState_noDoubleAdd() {
        engine.setCurrentWindow("com.android.settings", "android.app.Dialog");
        engine.getStateQueue().add("keepInBatteryUnRestricted");
        int sizeBefore = engine.getStateQueue().size();
        engine.doCheckBatteryOptimizationDialog();
        assertEquals(sizeBefore, engine.getStateQueue().size());
    }
}
