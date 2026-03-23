package com.vendor.rat.helper;

import android.content.Context;
import android.widget.ImageView;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * BlockImageLoader API 签名测试 (纯 JUnit，不加载 native 库)
 */
public class BlockImageLoaderTest {

    @Test
    public void classIsFinal() {
        assertTrue("BlockImageLoader should be final",
            java.lang.reflect.Modifier.isFinal(BlockImageLoader.class.getModifiers()));
    }

    @Test
    public void constructorIsPrivate() throws Exception {
        java.lang.reflect.Constructor<?> ctor = BlockImageLoader.class.getDeclaredConstructor();
        assertTrue("Constructor should be private",
            java.lang.reflect.Modifier.isPrivate(ctor.getModifiers()));
    }

    @Test
    public void loadImage_methodSignature() throws Exception {
        java.lang.reflect.Method method = BlockImageLoader.class.getMethod(
            "loadImage", Context.class, String.class, String.class, ImageView.class);
        assertNotNull(method);
        assertTrue("loadImage should be static",
            java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        assertTrue("loadImage should be public",
            java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void loadImageFromAssets_methodSignature() throws Exception {
        java.lang.reflect.Method method = BlockImageLoader.class.getMethod(
            "loadImageFromAssets", Context.class, String.class, ImageView.class);
        assertNotNull(method);
        assertTrue("loadImageFromAssets should be static",
            java.lang.reflect.Modifier.isStatic(method.getModifiers()));
    }
}
