package com.vendor.rat.helper;

import android.widget.LinearLayout;

import com.vendor.rat.service.MyAccessibilityService;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * BlockOverlayInner API 签名 + 结构测试 (纯 JUnit)
 */
public class BlockOverlayInnerTest {

    @Test
    public void extendsLinearLayout() {
        assertTrue("Should extend LinearLayout",
            LinearLayout.class.isAssignableFrom(BlockOverlayInner.class));
    }

    @Test
    public void hasDefaultConstructor() throws Exception {
        BlockOverlayInner.class.getConstructor(
            MyAccessibilityService.class, String.class);
    }

    @Test
    public void hasBlockingModeConstructor() throws Exception {
        BlockOverlayInner.class.getConstructor(
            MyAccessibilityService.class, String.class, boolean.class);
    }

    @Test
    public void f313a_fieldIsPublic() throws Exception {
        java.lang.reflect.Field field = BlockOverlayInner.class.getDeclaredField("f313a");
        assertTrue("f313a should be public",
            java.lang.reflect.Modifier.isPublic(field.getModifiers()));
    }

    @Test
    public void hasCustomOnLayout() throws Exception {
        java.lang.reflect.Method method = BlockOverlayInner.class.getDeclaredMethod(
            "onLayout", boolean.class, int.class, int.class, int.class, int.class);
        assertNotNull(method);
        assertTrue("onLayout should be final",
            java.lang.reflect.Modifier.isFinal(method.getModifiers()));
    }
}
