package com.vendor.rat.auto.engine;

import android.graphics.Rect;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.entity.CheckedResult;
import com.vendor.rat.auto.entity.Point;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AutoEngine Switch/CheckBox 操作方法测试
 *
 * 测试 vendor o/c.java 的 P/O/R/S 方法在 replica 中的实现。
 * 纯 JVM + Mockito，无需 Robolectric。
 */
@RunWith(MockitoJUnitRunner.class)
public class AutoEngineSwitchOperationTest {

    /**
     * 可测试的引擎子类 — 暴露 protected 方法
     */
    private static class TestableEngine extends AutoEngine {
        TestableEngine() {
            super(new ArrayList<WindowMatcher>(), "com.android.settings");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    android.view.accessibility.AccessibilityEvent event) {}

        @Override
        public void execute() {}

        // 暴露 P/O/R/S 方法供测试调用
        public CheckedResult doP(UiNode target) { return P(target); }
        public CheckedResult doO(UiNode target) { return O(target); }
        public CheckedResult doR(UiNode target, int retries) { return R(target, retries); }
        public CheckedResult doS(UiNode target) { return S(target); }
    }

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    // ============ P() — CompoundButton 操作 (vendor c.java:223-309) ============

    @Test
    public void testP_compoundButtonInDirectChild_alreadyChecked_noClick() {
        UiNode target = mock(UiNode.class);
        UiNode compoundButton = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(compoundButton);
        when(compoundButton.checked()).thenReturn(true);

        CheckedResult result = engine.doP(target);

        assertTrue(result.isChecked());
        assertFalse(result.isClicked());
        verify(compoundButton, never()).click();
    }

    @Test
    public void testP_compoundButtonInDirectChild_unchecked_clickSucceeds() {
        UiNode target = mock(UiNode.class);
        UiNode compoundButton = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(compoundButton);
        when(compoundButton.checked()).thenReturn(false, true); // false first, true after click
        when(compoundButton.click()).thenReturn(true);
        when(compoundButton.refresh()).thenReturn(true);

        CheckedResult result = engine.doP(target);

        assertTrue(result.isChecked());
        assertTrue(result.isClicked());
    }

    @Test
    public void testP_compoundButtonInParent_depth1() {
        UiNode target = mock(UiNode.class);
        UiNode parent1 = mock(UiNode.class);
        UiNode compoundButton = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(null);
        when(target.parent()).thenReturn(parent1);
        when(parent1.findOneByCombine(any(NodeFilter.class))).thenReturn(compoundButton);
        when(compoundButton.checked()).thenReturn(true);

        CheckedResult result = engine.doP(target);

        assertTrue(result.isChecked());
    }

    @Test
    public void testP_compoundButtonInParent_depth2() {
        UiNode target = mock(UiNode.class);
        UiNode parent1 = mock(UiNode.class);
        UiNode parent2 = mock(UiNode.class);
        UiNode compoundButton = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(null);
        when(target.parent()).thenReturn(parent1);
        when(parent1.findOneByCombine(any(NodeFilter.class))).thenReturn(null);
        when(parent1.parent()).thenReturn(parent2);
        when(parent2.findOneByCombine(any(NodeFilter.class))).thenReturn(compoundButton);
        when(compoundButton.checked()).thenReturn(true);

        CheckedResult result = engine.doP(target);

        assertTrue(result.isChecked());
    }

    @Test
    public void testP_notFound_returnsUnchecked() {
        UiNode target = mock(UiNode.class);
        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(null);
        when(target.parent()).thenReturn(null);

        CheckedResult result = engine.doP(target);

        assertFalse(result.isChecked());
        assertFalse(result.isClicked());
    }

    @Test
    public void testP_clickFails_tryClickableParent() {
        UiNode target = mock(UiNode.class);
        UiNode compoundButton = mock(UiNode.class);
        UiNode clickableParent = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(compoundButton);
        when(compoundButton.checked()).thenReturn(false);
        when(compoundButton.click()).thenReturn(false);
        when(compoundButton.refresh()).thenReturn(true);
        // vendor c.java:280: findParentUtilCombine(L()) — 查找 clickable 父节点
        when(compoundButton.findParentUtilCombine(any(NodeFilter.class))).thenReturn(clickableParent);
        when(clickableParent.click()).thenReturn(true);

        CheckedResult result = engine.doP(target);

        assertTrue(result.isClicked());
        verify(clickableParent).click();
    }

    @Test
    public void testP_retryLoop_maxRetries5() {
        UiNode target = mock(UiNode.class);
        UiNode compoundButton = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(compoundButton);
        // always unchecked — 验证重试 5 次后放弃
        when(compoundButton.checked()).thenReturn(false);
        when(compoundButton.click()).thenReturn(true);
        when(compoundButton.refresh()).thenReturn(true);
        when(compoundButton.findParentUtilCombine(any(NodeFilter.class))).thenReturn(null);

        CheckedResult result = engine.doP(target);

        assertFalse(result.isChecked());
        assertTrue(result.isClicked());
    }

    // ============ O() — Switch/CheckBox OR 操作 (vendor c.java:488-559) ============

    @Test
    public void testO_findsSwitch_alreadyChecked() {
        UiNode target = mock(UiNode.class);
        UiNode switchNode = mock(UiNode.class);

        when(target.findOneByOperateOr(any(NodeFilter.class), any(NodeFilter.class)))
                .thenReturn(switchNode);
        when(switchNode.checked()).thenReturn(true);

        CheckedResult result = engine.doO(target);

        assertTrue(result.isChecked());
        assertFalse(result.isClicked());
    }

    @Test
    public void testO_findsCheckBox_unchecked_clickAndVerify() {
        UiNode target = mock(UiNode.class);
        UiNode checkBox = mock(UiNode.class);

        when(target.findOneByOperateOr(any(NodeFilter.class), any(NodeFilter.class)))
                .thenReturn(checkBox);
        when(checkBox.checked()).thenReturn(false, true); // unchecked → click → checked
        when(checkBox.click()).thenReturn(true);
        when(checkBox.refresh()).thenReturn(true);

        CheckedResult result = engine.doO(target);

        assertTrue(result.isChecked());
        assertTrue(result.isClicked());
    }

    @Test
    public void testO_notFound_returnsUnchecked() {
        UiNode target = mock(UiNode.class);
        when(target.findOneByOperateOr(any(NodeFilter.class), any(NodeFilter.class)))
                .thenReturn(null);
        when(target.parent()).thenReturn(null);

        CheckedResult result = engine.doO(target);

        assertFalse(result.isChecked());
        assertFalse(result.isClicked());
    }

    @Test
    public void testO_retryLoop_clickUntilChecked() {
        UiNode target = mock(UiNode.class);
        UiNode switchNode = mock(UiNode.class);

        when(target.findOneByOperateOr(any(NodeFilter.class), any(NodeFilter.class)))
                .thenReturn(switchNode);
        // vendor c.java:541-551: 最多 5 次 click+T0(5)+refresh
        AtomicInteger callCount = new AtomicInteger(0);
        when(switchNode.checked()).thenAnswer((InvocationOnMock inv) -> {
            return callCount.incrementAndGet() >= 4; // 第 4 次返回 true
        });
        when(switchNode.click()).thenReturn(true);
        when(switchNode.refresh()).thenReturn(true);

        CheckedResult result = engine.doO(target);

        assertTrue(result.isChecked());
        assertTrue(result.isClicked());
    }

    // ============ R() — Switch 坐标点击 (vendor c.java:654-731) ============
    // 注意: R() 依赖 MiscUtils.tapAtCoordinate，在 JVM 测试中无法真正执行
    // 这里测试逻辑流程，tapAtCoordinate 通过 static mock 或跳过

    @Test
    public void testR_findSwitch_notFound_returnsUnchecked() {
        UiNode target = mock(UiNode.class);
        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(null);
        when(target.parent()).thenReturn(null);

        CheckedResult result = engine.doR(target, 5);

        assertFalse(result.isChecked());
        assertFalse(result.isClicked());
    }

    @Test
    public void testR_findSwitch_alreadyChecked_noClick() {
        UiNode target = mock(UiNode.class);
        UiNode switchNode = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(switchNode);
        when(switchNode.checked()).thenReturn(true);
        when(switchNode.boundsInScreen()).thenReturn(new Rect(0, 0, 500, 100));
        when(switchNode.centerInScreen()).thenReturn(new Point(250f, 50f));

        CheckedResult result = engine.doR(target, 5);

        assertTrue(result.isChecked());
        assertFalse(result.isClicked());
    }

    // ============ S() — Switch 坐标点击变体 (vendor c.java:334-382) ============

    @Test
    public void testS_notFound_returnsDefault() {
        UiNode target = mock(UiNode.class);
        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(null);
        when(target.parent()).thenReturn(null);

        CheckedResult result = engine.doS(target);

        assertFalse(result.isChecked());
        assertFalse(result.isClicked());
    }

    @Test
    public void testS_alreadyChecked_noClick() {
        UiNode target = mock(UiNode.class);
        UiNode switchNode = mock(UiNode.class);

        when(target.findOneByCombine(any(NodeFilter.class))).thenReturn(switchNode);
        when(switchNode.checked()).thenReturn(true);
        when(switchNode.boundsInScreen()).thenReturn(new Rect(0, 0, 500, 100));
        when(switchNode.centerInScreen()).thenReturn(new Point(250f, 50f));

        CheckedResult result = engine.doS(target);

        assertTrue(result.isChecked());
        assertFalse(result.isClicked());
    }
}
