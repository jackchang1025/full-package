package com.vendor.rat.auto.engine;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.model.req.ListenWindow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AutoEngine ListenWindow 匹配方法测试
 * 对应 vendor o/e.java p() + q()
 */
@RunWith(MockitoJUnitRunner.class)
public class AutoEngineListenWindowMatchTest {

    private static class TestableEngine extends AutoEngine {
        TestableEngine() {
            super(new ArrayList<WindowMatcher>(), "com.android.settings");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    android.view.accessibility.AccessibilityEvent event) {}
        @Override
        public void execute() {}

        // 暴露 protected 方法
        public boolean doMatchListenWindow(ListenWindow lw, UiNode root) {
            return matchListenWindow(lw, root);
        }
        public boolean doMatchListenWindows(List<ListenWindow> windows) {
            return matchListenWindows(windows);
        }
        public void setCurrentWindow(String pkg, String cls) {
            currentPackage = pkg;
            currentClassName = cls;
        }
    }

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    // ============ matchListenWindow (vendor e.java p()) ============

    @Test
    public void testMatchListenWindow_noMatchsNoDismiss_returnsTrue() {
        ListenWindow lw = new ListenWindow("com.android.settings", "android.app.Dialog");
        UiNode root = mock(UiNode.class);

        assertTrue(engine.doMatchListenWindow(lw, root));
    }

    @Test
    public void testMatchListenWindow_matchsAllPass_returnsTrue() {
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        CombineFilter filter1 = mock(CombineFilter.class);
        CombineFilter filter2 = mock(CombineFilter.class);
        lw.setMatchs(Arrays.asList(filter1, filter2));

        UiNode root = mock(UiNode.class);
        UiNode found = mock(UiNode.class);
        when(root.findOneByCombine(any(NodeFilter.class))).thenReturn(found);

        assertTrue(engine.doMatchListenWindow(lw, root));
    }

    @Test
    public void testMatchListenWindow_matchsOneFails_returnsFalse() {
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        CombineFilter filter1 = mock(CombineFilter.class);
        CombineFilter filter2 = mock(CombineFilter.class);
        lw.setMatchs(Arrays.asList(filter1, filter2));

        UiNode root = mock(UiNode.class);
        UiNode found = mock(UiNode.class);
        // filter1 找到, filter2 找不到
        when(root.findOneByCombine(filter1)).thenReturn(found);
        when(root.findOneByCombine(filter2)).thenReturn(null);

        assertFalse(engine.doMatchListenWindow(lw, root));
    }

    @Test
    public void testMatchListenWindow_dismissMatch_returnsFalse() {
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        CombineFilter dismissFilter = mock(CombineFilter.class);
        lw.setDismiss(Collections.singletonList(dismissFilter));

        UiNode root = mock(UiNode.class);
        UiNode found = mock(UiNode.class);
        when(root.findOneByCombine(dismissFilter)).thenReturn(found);

        assertFalse(engine.doMatchListenWindow(lw, root));
    }

    @Test
    public void testMatchListenWindow_dismissNoMatch_returnsTrue() {
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        CombineFilter dismissFilter = mock(CombineFilter.class);
        lw.setDismiss(Collections.singletonList(dismissFilter));

        UiNode root = mock(UiNode.class);
        when(root.findOneByCombine(dismissFilter)).thenReturn(null);

        assertTrue(engine.doMatchListenWindow(lw, root));
    }

    @Test
    public void testMatchListenWindow_nullRoot_returnsTrue() {
        ListenWindow lw = new ListenWindow("com.android.settings", null);
        // matchs 为空, dismiss 为空 → true
        assertTrue(engine.doMatchListenWindow(lw, null));
    }

    // ============ matchListenWindows (vendor e.java q()) ============

    @Test
    public void testMatchListenWindows_emptyList_returnsFalse() {
        assertFalse(engine.doMatchListenWindows(Collections.emptyList()));
    }

    @Test
    public void testMatchListenWindows_nullList_returnsFalse() {
        assertFalse(engine.doMatchListenWindows(null));
    }

    @Test
    public void testMatchListenWindows_packageMatch_noMatchs_returnsTrue() {
        engine.setCurrentWindow("com.android.settings", "android.app.Dialog");
        ListenWindow lw = new ListenWindow("com.android.settings", "android.app.Dialog");

        assertTrue(engine.doMatchListenWindows(Collections.singletonList(lw)));
    }

    @Test
    public void testMatchListenWindows_packageMismatch_returnsFalse() {
        engine.setCurrentWindow("com.android.settings", "android.app.Dialog");
        ListenWindow lw = new ListenWindow("com.huawei.systemmanager", null);

        assertFalse(engine.doMatchListenWindows(Collections.singletonList(lw)));
    }

    @Test
    public void testMatchListenWindows_classNameNull_matchesAnyClass() {
        engine.setCurrentWindow("com.android.settings", "com.android.settings.SubSettings");
        ListenWindow lw = new ListenWindow("com.android.settings", null);

        assertTrue(engine.doMatchListenWindows(Collections.singletonList(lw)));
    }

    @Test
    public void testMatchListenWindows_classNameMismatch_returnsFalse() {
        engine.setCurrentWindow("com.android.settings", "com.android.settings.SubSettings");
        ListenWindow lw = new ListenWindow("com.android.settings", "android.app.Dialog");

        assertFalse(engine.doMatchListenWindows(Collections.singletonList(lw)));
    }

    @Test
    public void testMatchListenWindows_multipleWindows_firstMatch_returnsTrue() {
        engine.setCurrentWindow("com.android.settings", "android.app.Dialog");
        ListenWindow lw1 = new ListenWindow("com.huawei.systemmanager", null);
        ListenWindow lw2 = new ListenWindow("com.android.settings", "android.app.Dialog");

        assertTrue(engine.doMatchListenWindows(Arrays.asList(lw1, lw2)));
    }
}
