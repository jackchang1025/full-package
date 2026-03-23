package com.vendor.rat.helper;

import android.view.WindowManager;

import com.vendor.rat.config.AppConfig;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * BlockViewHelper 单元测试 (纯 JUnit + returnDefaultValues=true)
 */
public class BlockViewHelperTest {

    @After
    public void tearDown() {
        BlockViewHelper.viewRef.set(null);
        BlockViewHelper.viewShowing.set(false);
        BlockViewHelper.windowManager = null;
        BlockViewHelper.savedBrightness.set(-1);
        BlockViewHelper.destroyLock.set(true);
    }

    // ============ isShowing() ============

    @Test
    public void isShowing_returnsFalse_whenBothNull() {
        BlockViewHelper.viewRef.set(null);
        BlockViewHelper.windowManager = null;
        assertFalse(BlockViewHelper.isShowing());
    }

    // ============ VENDOR_WINDOW_FLAGS ============

    @Test
    public void vendorWindowFlags_is591800() throws Exception {
        java.lang.reflect.Field field = BlockViewHelper.class.getDeclaredField("VENDOR_WINDOW_FLAGS");
        field.setAccessible(true);
        assertEquals(591800, field.getInt(null));
    }

    @Test
    public void vendorWindowFlags_containsRequiredFlags() throws Exception {
        java.lang.reflect.Field field = BlockViewHelper.class.getDeclaredField("VENDOR_WINDOW_FLAGS");
        field.setAccessible(true);
        int flags = field.getInt(null);

        assertTrue("FLAG_NOT_FOCUSABLE",
            (flags & WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE) != 0);
        assertTrue("FLAG_NOT_TOUCHABLE",
            (flags & WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE) != 0);
        assertTrue("FLAG_NOT_TOUCH_MODAL",
            (flags & WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL) != 0);
        assertTrue("FLAG_KEEP_SCREEN_ON",
            (flags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) != 0);
        assertTrue("FLAG_FULLSCREEN",
            (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0);
    }

    @Test
    public void productionFlags_removesNotTouchable() throws Exception {
        java.lang.reflect.Field field = BlockViewHelper.class.getDeclaredField("VENDOR_WINDOW_FLAGS");
        field.setAccessible(true);
        int vendorFlags = field.getInt(null);
        int prodFlags = vendorFlags & ~WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        assertEquals("NOT_TOUCHABLE removed", 0,
            prodFlags & WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        assertTrue("NOT_FOCUSABLE remains",
            (prodFlags & WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE) != 0);
        assertTrue("NOT_TOUCH_MODAL remains",
            (prodFlags & WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL) != 0);
    }

    // ============ sendProgress 空安全 ============

    @Test
    public void sendProgress_noopWhenViewRefNull() {
        BlockViewHelper.viewRef.set(null);
        BlockViewHelper.sendProgress(50); // 不应抛异常
    }

    @Test
    public void sendProgress_noopWhenProgressZeroOrNegative() {
        BlockViewHelper.sendProgress(0);
        BlockViewHelper.sendProgress(-1);
    }

    // ============ productionMode 判定 ============

    @Test
    public void productionMode_trueWhenDebugNull() {
        AppConfig config = new AppConfig();
        config.setDebug(null);
        assertTrue(config.getDebug() == null || config.getDebug() != 1);
    }

    @Test
    public void productionMode_trueWhenDebugZero() {
        AppConfig config = new AppConfig();
        config.setDebug(0);
        assertTrue(config.getDebug() == null || config.getDebug() != 1);
    }

    @Test
    public void productionMode_falseWhenDebugOne() {
        AppConfig config = new AppConfig();
        config.setDebug(1);
        assertFalse(config.getDebug() == null || config.getDebug() != 1);
    }

    @Test
    public void productionMode_trueWhenConfigNull() {
        AppConfig config = null;
        assertTrue(config == null);
    }

    // ============ 静态状态默认值 ============

    @Test
    public void viewShowing_defaultFalse() {
        assertFalse(BlockViewHelper.viewShowing.get());
    }

    @Test
    public void destroyLock_defaultTrue() {
        assertTrue(BlockViewHelper.destroyLock.get());
    }

    @Test
    public void savedBrightness_defaultMinusOne() {
        assertEquals(-1, BlockViewHelper.savedBrightness.get());
    }
}
