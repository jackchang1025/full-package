package com.vendor.rat.helper;

import android.app.Activity;
import android.widget.ImageView;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * GuideDialogHelper 单元测试 (纯 JUnit)
 */
public class GuideDialogHelperTest {

    @Test
    public void colorBg_value() {
        assertEquals(android.graphics.Color.parseColor("#303133"),
            GuideDialogHelper.COLOR_BG);
    }

    @Test
    public void isShowing_returnsFalse_whenNotShown() {
        assertFalse(GuideDialogHelper.isShowing());
    }

    @Test
    public void loadImage_signatureCompatibleWithBlockImageLoader() throws Exception {
        // GuideDialogHelper.loadImage 参数 Activity extends Context
        java.lang.reflect.Method guideMethod = GuideDialogHelper.class.getDeclaredMethod(
            "loadImage", Activity.class, String.class, String.class, ImageView.class);
        assertNotNull(guideMethod);

        java.lang.reflect.Method blockMethod = BlockImageLoader.class.getDeclaredMethod(
            "loadImage", android.content.Context.class, String.class, String.class, ImageView.class);
        assertNotNull(blockMethod);

        assertTrue(android.content.Context.class.isAssignableFrom(Activity.class));
    }
}
