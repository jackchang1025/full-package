package com.vendor.rat.auto.engine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * PermissionAutoGrantEngine.matchWindow() 重写逻辑测试
 *
 * 纯 JVM 测试，无需 Android 框架。
 * 注意: 构造函数创建 ScheduledExecutorService，@After 中调用 destroy() 清理。
 */
public class PermissionAutoGrantEngineMatchWindowTest {

    private PermissionAutoGrantEngine engine;

    @Before
    public void setUp() {
        engine = new PermissionAutoGrantEngine();
    }

    @After
    public void tearDown() {
        engine.destroy();
    }

    // ============ 权限控制器包名匹配 ============

    @Test
    public void matchWindow_androidPermissionController_returnsTrue() {
        assertTrue(engine.matchWindow("com.android.permissioncontroller", "AnyClass", 0));
    }

    @Test
    public void matchWindow_googlePermissionController_returnsTrue() {
        assertTrue(engine.matchWindow("com.google.android.permissioncontroller", "AnyClass", 0));
    }

    @Test
    public void matchWindow_packageInstaller_returnsTrue() {
        assertTrue(engine.matchWindow("com.android.packageinstaller", "AnyClass", 0));
    }

    // ============ 华为权限弹窗 ============

    @Test
    public void matchWindow_huaweiWithPermissionClass_returnsTrue() {
        assertTrue(engine.matchWindow("com.huawei.systemmanager",
                "com.huawei.systemmanager.permission.PermissionActivity", 0));
    }

    @Test
    public void matchWindow_huaweiWithoutPermissionClass_returnsFalse() {
        assertFalse(engine.matchWindow("com.huawei.systemmanager",
                "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity", 0));
    }

    // ============ 通用 GrantPermissions 类名匹配 ============

    @Test
    public void matchWindow_anyPackageWithGrantPermissionsClass_returnsTrue() {
        assertTrue(engine.matchWindow("com.random.package",
                "com.random.package.GrantPermissionsActivity", 0));
    }

    @Test
    public void matchWindow_anyPackageWithoutGrantPermissions_returnsFalse() {
        assertFalse(engine.matchWindow("com.random.package",
                "com.random.package.SomeOtherActivity", 0));
    }

    // ============ null 输入 ============

    @Test
    public void matchWindow_nullPackage_returnsFalse() {
        assertFalse(engine.matchWindow(null, "AnyClass", 0));
    }

    @Test
    public void matchWindow_permissionControllerNullClass_returnsTrue() {
        // 权限控制器包名匹配不检查 className
        assertTrue(engine.matchWindow("com.android.permissioncontroller", null, 0));
    }

    @Test
    public void matchWindow_huaweiNullClass_returnsFalse() {
        // 华为需要 className 包含 "Permission"，null className → false
        assertFalse(engine.matchWindow("com.huawei.systemmanager", null, 0));
    }

    // ============ 不匹配的包名 ============

    @Test
    public void matchWindow_randomPackageRandomClass_returnsFalse() {
        assertFalse(engine.matchWindow("com.foo.bar", "com.foo.bar.MainActivity", 0));
    }

    @Test
    public void matchWindow_androidSettings_returnsFalse() {
        assertFalse(engine.matchWindow("com.android.settings",
                "com.android.settings.HWSettings", 0));
    }
}
