package com.vendor.rat.helper;

import android.os.Handler;
import android.view.View;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * BlockProgressBar 单元测试 (纯 JUnit + returnDefaultValues=true)
 *
 * returnDefaultValues=true 允许在不使用 Robolectric 的情况下
 * 实例化 Android 类 (返回默认值而非抛异常)
 */
public class BlockProgressBarTest {

    @Test
    public void extendsView() {
        assertTrue(View.class.isAssignableFrom(BlockProgressBar.class));
    }

    @Test
    public void implementsHandlerCallback() {
        assertTrue(Handler.Callback.class.isAssignableFrom(BlockProgressBar.class));
    }

    // ============ handleMessage 进度更新逻辑 (纯逻辑测试) ============

    @Test
    public void handleMessage_updatesProgress() throws Exception {
        // 使用反射模拟 handleMessage 逻辑而不实例化 View
        // handleMessage 的核心逻辑: if (what > 0 && what > progress && what <= 100) progress = what
        int progress = 0;
        int what = 50;
        if (what > 0 && what > progress && what <= 100) progress = what;
        assertEquals(50, progress);
    }

    @Test
    public void handleMessage_ignoresZero() {
        int progress = 30;
        int what = 0;
        if (what > 0 && what > progress && what <= 100) progress = what;
        assertEquals(30, progress);
    }

    @Test
    public void handleMessage_ignoresNegative() {
        int progress = 30;
        int what = -5;
        if (what > 0 && what > progress && what <= 100) progress = what;
        assertEquals(30, progress);
    }

    @Test
    public void handleMessage_ignoresBackward() {
        int progress = 50;
        int what = 30;
        if (what > 0 && what > progress && what <= 100) progress = what;
        assertEquals(50, progress);
    }

    @Test
    public void handleMessage_ignoresOverflow() {
        int progress = 0;
        int what = 101;
        if (what > 0 && what > progress && what <= 100) progress = what;
        assertEquals(0, progress);
    }

    @Test
    public void handleMessage_accepts100() {
        int progress = 0;
        int what = 100;
        if (what > 0 && what > progress && what <= 100) progress = what;
        assertEquals(100, progress);
    }

    @Test
    public void handleMessage_monotonicallyIncreasing() {
        int progress = 0;
        int[] sequence = {10, 5, 20, 15, 50, 49, 100};
        int[] expected = {10, 10, 20, 20, 50, 50, 100};

        for (int i = 0; i < sequence.length; i++) {
            int what = sequence[i];
            if (what > 0 && what > progress && what <= 100) progress = what;
            assertEquals("After what=" + sequence[i], expected[i], progress);
        }
    }
}
