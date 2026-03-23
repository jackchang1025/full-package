package com.vendor.rat.service;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * MyAccessibilityService 遮罩防护逻辑测试 (纯 JUnit)
 */
public class MyAccessibilityServiceOverlayGuardTest {

    // ============ overlayGuardFired 防抖 ============

    @Test
    public void overlayGuardFired_isAtomicBoolean() throws Exception {
        Field field = MyAccessibilityService.class.getDeclaredField("overlayGuardFired");
        assertEquals(AtomicBoolean.class, field.getType());
    }

    @Test
    public void overlayGuardFired_initiallyFalse() throws Exception {
        MyAccessibilityService service = new MyAccessibilityService();
        Field field = MyAccessibilityService.class.getDeclaredField("overlayGuardFired");
        field.setAccessible(true);
        AtomicBoolean guard = (AtomicBoolean) field.get(service);
        assertFalse(guard.get());
    }

    @Test
    public void overlayGuardFired_compareAndSetPreventsDouble() throws Exception {
        MyAccessibilityService service = new MyAccessibilityService();
        Field field = MyAccessibilityService.class.getDeclaredField("overlayGuardFired");
        field.setAccessible(true);
        AtomicBoolean guard = (AtomicBoolean) field.get(service);

        assertTrue("First CAS should succeed", guard.compareAndSet(false, true));
        assertFalse("Second CAS should fail", guard.compareAndSet(false, true));
    }

    @Test
    public void resetOverlayGuard_resetsToFalse() throws Exception {
        MyAccessibilityService service = new MyAccessibilityService();
        Field field = MyAccessibilityService.class.getDeclaredField("overlayGuardFired");
        field.setAccessible(true);
        AtomicBoolean guard = (AtomicBoolean) field.get(service);

        guard.set(true);
        service.resetOverlayGuard();
        assertFalse(guard.get());
    }

    @Test
    public void resetOverlayGuard_isPublic() throws Exception {
        Method method = MyAccessibilityService.class.getDeclaredMethod("resetOverlayGuard");
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    // ============ isLauncherPackage 缓存 ============

    @Test
    public void cachedLauncherPackage_isVolatile() throws Exception {
        Field field = MyAccessibilityService.class.getDeclaredField("cachedLauncherPackage");
        field.setAccessible(true);
        assertTrue("Should be volatile",
            java.lang.reflect.Modifier.isVolatile(field.getModifiers()));
    }

    @Test
    public void cachedLauncherPackage_initiallyNull() throws Exception {
        MyAccessibilityService service = new MyAccessibilityService();
        Field field = MyAccessibilityService.class.getDeclaredField("cachedLauncherPackage");
        field.setAccessible(true);
        assertNull(field.get(service));
    }

    @Test
    public void isLauncherPackage_returnsFalseForNull() throws Exception {
        MyAccessibilityService service = new MyAccessibilityService();
        Method method = MyAccessibilityService.class.getDeclaredMethod("isLauncherPackage", String.class);
        method.setAccessible(true);
        assertFalse((boolean) method.invoke(service, (Object) null));
    }

    // ============ restoreSettingsTask ============

    @Test
    public void restoreSettingsTask_isPrivate() throws Exception {
        Method method = MyAccessibilityService.class.getDeclaredMethod("restoreSettingsTask");
        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
    }
}
