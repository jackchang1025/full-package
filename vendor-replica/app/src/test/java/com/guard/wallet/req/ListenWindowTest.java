package com.guard.wallet.req;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * ListenWindow.equals() 单元测试。
 *
 * 覆盖场景:
 *   1-2.  正常匹配 / 不匹配 (pkg+cls)
 *   3-7.  HyperOS 3 泛化 className 降级 (5 tests)
 *   8.    FrameLayout 不是泛化值
 *   9-11. 原有行为回归 (空 className / 空 packageName / SoftInputWindow)
 *  12-14. equals 基本契约 (自反性 / null / 类型)
 *  15.    对称性: a.equals(b) == b.equals(a) 对泛化 className
 */
public class ListenWindowTest {

    @Test
    public void exactMatch_samePackageAndClass() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    public void noMatch_samePackageDifferentClass() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
        assertFalse(a.equals(b));
    }

    @Test
    public void hyperOs3_actualIsGenericView_fallbackToPackageOnly() {
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow actual = new ListenWindow("com.miui.securitycenter",
                "android.view.View");
        assertTrue("HyperOS 3 泛化 className 应降级为仅比 packageName",
                expected.equals(actual));
    }

    @Test
    public void hyperOs3_expectedIsGenericView_fallbackToPackageOnly() {
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "android.view.View");
        ListenWindow actual = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
        assertTrue(expected.equals(actual));
    }

    @Test
    public void hyperOs3_differentPackage_noFalsePositive() {
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow actual = new ListenWindow("com.android.settings",
                "android.view.View");
        assertFalse("不同 packageName 即使 className 泛化也不应匹配",
                expected.equals(actual));
    }

    @Test
    public void hyperOs3_bothGenericView_samePackage() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter", "android.view.View");
        ListenWindow b = new ListenWindow("com.miui.securitycenter", "android.view.View");
        assertTrue(a.equals(b));
    }

    @Test
    public void hyperOs3_bothGenericView_differentPackage() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter", "android.view.View");
        ListenWindow b = new ListenWindow("com.android.settings", "android.view.View");
        assertFalse(a.equals(b));
    }

    @Test
    public void frameLayout_isNotGeneric_requiresExactMatch() {
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "android.widget.FrameLayout");
        ListenWindow actual = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertFalse("FrameLayout 不是泛化值，不应降级匹配",
                expected.equals(actual));
    }

    @Test
    public void emptyClassName_fallbackToPackageOnly() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter", null);
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertTrue("空 className 应降级为仅比 packageName", a.equals(b));
    }

    @Test
    public void emptyPackageName_fallbackToClassOnly() {
        ListenWindow a = new ListenWindow(null,
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertTrue("空 packageName 应降级为仅比 className", a.equals(b));
    }

    @Test
    public void softInputWindow_fallbackToPackageOnly() {
        ListenWindow a = new ListenWindow("com.example.ime",
                "android.inputmethodservice.SoftInputWindow");
        ListenWindow b = new ListenWindow("com.example.ime",
                "com.example.ime.SomeActivity");
        assertTrue("SoftInputWindow 应降级为仅比 packageName", a.equals(b));
    }

    @Test
    public void reflexive() {
        ListenWindow a = new ListenWindow("pkg", "cls");
        assertTrue(a.equals(a));
    }

    @Test
    public void nullSafe() {
        ListenWindow a = new ListenWindow("pkg", "cls");
        assertFalse(a.equals(null));
    }

    @Test
    public void differentType() {
        ListenWindow a = new ListenWindow("pkg", "cls");
        assertFalse(a.equals("not a ListenWindow"));
    }

    @Test
    public void symmetric_genericViewFallback() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "android.view.View");
        assertEquals("equals 必须对称: a.equals(b) == b.equals(a)",
                a.equals(b), b.equals(a));
    }
}
