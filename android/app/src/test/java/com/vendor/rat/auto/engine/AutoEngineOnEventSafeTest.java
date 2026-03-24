package com.vendor.rat.auto.engine;

import android.view.accessibility.AccessibilityEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * AutoEngine.onAccessibilityEvent 模板方法 + onEventSafe 委托测试
 *
 * 纯 JVM 测试，用 Mockito mock AccessibilityEvent (避免 native 依赖)
 */
public class AutoEngineOnEventSafeTest {

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    @After
    public void tearDown() {
        engine.destroy();
    }

    // ============ 正常流程 ============

    @Test
    public void onAccessibilityEvent_setsCurrentPackage() {
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        engine.onAccessibilityEvent(event, "com.target.app", "com.target.app.Main");
        assertEquals("com.target.app", engine.currentPackage);
    }

    @Test
    public void onAccessibilityEvent_setsCurrentClassName() {
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        engine.onAccessibilityEvent(event, "com.target.app", "com.target.app.Main");
        assertEquals("com.target.app.Main", engine.currentClassName);
    }

    @Test
    public void onAccessibilityEvent_callsOnEventSafe() {
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        engine.onAccessibilityEvent(event, "com.test", "com.test.Activity");
        assertTrue(engine.onEventSafeCalled);
        assertEquals("com.test", engine.lastPackage);
        assertEquals("com.test.Activity", engine.lastClassName);
    }

    // ============ isCompleted 跳过 ============

    @Test
    public void onAccessibilityEvent_completed_skipsOnEventSafe() {
        engine.markCompleted();
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        engine.onAccessibilityEvent(event, "com.test", "com.test.Activity");
        assertFalse(engine.onEventSafeCalled);
    }

    @Test
    public void onAccessibilityEvent_completed_doesNotUpdateCurrentPackage() {
        engine.currentPackage = "original";
        engine.currentClassName = "original.Class";
        engine.markCompleted();
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        engine.onAccessibilityEvent(event, "com.new.pkg", "com.new.Activity");
        assertEquals("original", engine.currentPackage);
        assertEquals("original.Class", engine.currentClassName);
    }

    // ============ 异常兜底 ============

    @Test
    public void onAccessibilityEvent_onEventSafeThrows_doesNotCrash() {
        engine.onEventSafeException = new RuntimeException("event handler boom");
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        // 不应抛出异常
        engine.onAccessibilityEvent(event, "com.test", "com.test.Activity");
    }

    @Test
    public void onAccessibilityEvent_onEventSafeThrows_currentPackageStillSet() {
        engine.onEventSafeException = new RuntimeException("boom");
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        engine.onAccessibilityEvent(event, "com.pkg", "com.pkg.Cls");
        // currentPackage 在 onEventSafe 之前设置，所以异常不影响
        assertEquals("com.pkg", engine.currentPackage);
        assertEquals("com.pkg.Cls", engine.currentClassName);
    }

    // ============ 默认 onEventSafe 委托 ============

    @Test
    public void onEventSafe_default_callsOnWindowMatched() {
        DefaultEngine defaultEngine = new DefaultEngine();
        AccessibilityEvent event = mock(AccessibilityEvent.class);
        defaultEngine.onAccessibilityEvent(event, "com.test", "com.test.Act");
        assertTrue(defaultEngine.onWindowMatchedCalled);
        assertEquals("com.test", defaultEngine.matchedPackage);
        assertEquals("com.test.Act", defaultEngine.matchedClassName);
        defaultEngine.destroy();
    }

    // ============ TestableEngine — 覆写 onEventSafe 记录调用 ============

    private static class TestableEngine extends AutoEngine {

        boolean onEventSafeCalled = false;
        String lastPackage;
        String lastClassName;
        RuntimeException onEventSafeException;

        TestableEngine() {
            super(new ArrayList<>(), "com.test.pkg");
        }

        void markCompleted() {
            try {
                java.lang.reflect.Field f = AutoEngine.class.getDeclaredField("finished");
                f.setAccessible(true);
                ((java.util.concurrent.atomic.AtomicBoolean) f.get(this)).set(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onEventSafe(AccessibilityEvent event, String packageName,
                                    String className) {
            onEventSafeCalled = true;
            lastPackage = packageName;
            lastClassName = className;
            if (onEventSafeException != null) throw onEventSafeException;
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    AccessibilityEvent event) {
            // no-op
        }

        @Override
        public void execute() {
            // no-op
        }
    }

    // ============ DefaultEngine — 不覆写 onEventSafe，验证默认实现 ============

    private static class DefaultEngine extends AutoEngine {

        boolean onWindowMatchedCalled = false;
        String matchedPackage;
        String matchedClassName;

        DefaultEngine() {
            super(new ArrayList<>(), "com.test.pkg");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    AccessibilityEvent event) {
            onWindowMatchedCalled = true;
            matchedPackage = packageName;
            matchedClassName = className;
        }

        @Override
        public void execute() {
            // no-op
        }
    }
}
