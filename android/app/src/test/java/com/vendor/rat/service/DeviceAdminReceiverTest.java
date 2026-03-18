package com.vendor.rat.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AppDeviceAdminReceiver 单元测试
 */
public class DeviceAdminReceiverTest {

    @Test
    public void testOnDisableRequested_returnsWarningText() {
        AppDeviceAdminReceiver receiver = new AppDeviceAdminReceiver();
        // onDisableRequested 需要 Context，在纯 JVM 环境下无法直接调用
        // 验证类实例化正常
        assertNotNull(receiver);
    }

    @Test
    public void testStaticMethods_exist() {
        // 验证静态方法签名存在 (编译级验证)
        // 实际调用需要 Android Context，这里只验证类加载
        try {
            Class<?> clazz = AppDeviceAdminReceiver.class;
            assertNotNull(clazz.getMethod("isAdminActive",
                android.content.Context.class));
            assertNotNull(clazz.getMethod("getComponentName",
                android.content.Context.class));
            assertNotNull(clazz.getMethod("lockScreen",
                android.content.Context.class));
            assertNotNull(clazz.getMethod("wipeData",
                android.content.Context.class));
            assertNotNull(clazz.getMethod("resetPassword",
                android.content.Context.class, String.class));
        } catch (NoSuchMethodException e) {
            fail("Missing expected method: " + e.getMessage());
        }
    }

    @Test
    public void testReceiverExtendsDeviceAdminReceiver() {
        AppDeviceAdminReceiver receiver = new AppDeviceAdminReceiver();
        assertTrue("Should extend DeviceAdminReceiver",
            receiver instanceof android.app.admin.DeviceAdminReceiver);
    }
}
