package com.vendor.rat.helper;

import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.vendor.rat.service.MyAccessibilityService;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * BlockOverlayView API 签名 + 结构测试 (纯 JUnit)
 */
public class BlockOverlayViewTest {

    @Test
    public void extendsFrameLayout() {
        assertTrue("Should extend FrameLayout",
            FrameLayout.class.isAssignableFrom(BlockOverlayView.class));
    }

    @Test
    public void productionFactory_methodSignature() throws Exception {
        java.lang.reflect.Method method = BlockOverlayView.class.getMethod(
            "production",
            MyAccessibilityService.class, String.class, int.class, String.class);
        assertNotNull("production() factory method should exist", method);
        assertTrue("production() should be static",
            java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        assertTrue("production() should be public",
            java.lang.reflect.Modifier.isPublic(method.getModifiers()));
        assertEquals("production() should return BlockOverlayView",
            BlockOverlayView.class, method.getReturnType());
    }

    @Test
    public void blockingMode_fieldIsFinalBoolean() throws Exception {
        java.lang.reflect.Field field = BlockOverlayView.class.getDeclaredField("blockingMode");
        assertTrue("blockingMode should be private",
            java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
        assertTrue("blockingMode should be final",
            java.lang.reflect.Modifier.isFinal(field.getModifiers()));
        assertEquals("blockingMode should be boolean", boolean.class, field.getType());
    }

    @Test
    public void f311a_fieldIsPublic() throws Exception {
        java.lang.reflect.Field field = BlockOverlayView.class.getDeclaredField("f311a");
        assertTrue("f311a should be public",
            java.lang.reflect.Modifier.isPublic(field.getModifiers()));
    }

    @Test
    public void hasOnTouchEventOverride() throws Exception {
        java.lang.reflect.Method method = BlockOverlayView.class.getDeclaredMethod(
            "onTouchEvent", MotionEvent.class);
        assertNotNull(method);
    }

    @Test
    public void hasOnInterceptTouchEventOverride() throws Exception {
        java.lang.reflect.Method method = BlockOverlayView.class.getDeclaredMethod(
            "onInterceptTouchEvent", MotionEvent.class);
        assertNotNull(method);
    }

    @Test
    public void hasDebugConstructor_withDrawable() throws Exception {
        BlockOverlayView.class.getConstructor(
            MyAccessibilityService.class, String.class, android.graphics.drawable.Drawable.class);
    }

    @Test
    public void hasDebugConstructor_hintOnly() throws Exception {
        BlockOverlayView.class.getConstructor(
            MyAccessibilityService.class, String.class);
    }

    @Test
    public void noPublicBooleanConstructor() {
        // 生产模式构造器应是 private，通过 production() 调用
        // 不应有 public (service, hint, boolean) 构造器（避免歧义）
        try {
            BlockOverlayView.class.getConstructor(
                MyAccessibilityService.class, String.class, boolean.class);
            fail("Should not have public (service, hint, boolean) constructor");
        } catch (NoSuchMethodException expected) {
            // 正确: boolean 构造器不存在或是 private
        }
    }
}
