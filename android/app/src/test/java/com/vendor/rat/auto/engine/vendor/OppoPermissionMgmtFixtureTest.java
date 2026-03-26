package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.testutil.UiDumpFixture;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * OPPO 权限管理页面 Fixture 测试
 *
 * 基于 OPPO Find X6 (ColorOS 16, Android 16) 全新安装后真机 dump。
 * 包名: com.oplus.securitypermission (普通 OPPO 应用, 无 accessibilityDataSensitive 限制)
 *
 * 4 种权限子页面布局:
 *   A. 4 选项: 始终允许 / 使用时允许 / 每次询问 / 不允许 (位置)
 *   B. 3 选项: 使用时允许 / 每次询问 / 不允许 (摄像头, 桌面快捷方式)
 *   C. 2 选项: 允许 / 不允许 (短信, 电话)
 *   C+: 2 选项 + 隐私替身 Switch (通讯录)
 */
public class OppoPermissionMgmtFixtureTest {

    // ============ 权限列表页 — 顶部视图 ============

    @Test
    public void permList_top_findAllowedSection() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_top.xml");
        assertNotNull(root);
        UiNode section = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("允许")));
        assertNotNull("顶部应有'允许'分组标题", section);
    }

    @Test
    public void permList_top_findDeniedSection() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_top.xml");
        UiNode section = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("不允许")));
        assertNotNull("顶部应有'不允许'分组标题", section);
    }

    @Test
    public void permList_top_findDeniedPermission_shortcut() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_top.xml");
        UiNode perm = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textContains("创建桌面快捷方式")));
        assertNotNull("应找到'创建桌面快捷方式'权限", perm);
    }

    // ============ 权限列表页 — 滚动后不允许分组 ============

    @Test
    public void permList_denied_findCamera() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_denied.xml");
        assertNotNull(root);
        UiNode cam = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textContains("摄像头")));
        assertNotNull("滚动后应找到'摄像头'权限", cam);
    }

    @Test
    public void permList_denied_findPhone() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_denied.xml");
        UiNode phone = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("电话")));
        assertNotNull("滚动后应找到'电话'权限", phone);
    }

    @Test
    public void permList_denied_findOtherPermissions() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_denied.xml");
        UiNode other = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("其他权限")));
        assertNotNull("应有'其他权限'分组", other);
    }

    // ============ 布局 A: 位置信息 (4 选项 — 始终允许) ============

    @Test
    public void subLocation_findAlwaysAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");
        assertNotNull(root);
        UiNode alwaysAllow = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("始终允许")));
        assertNotNull("位置权限应有'始终允许'选项", alwaysAllow);
    }

    @Test
    public void subLocation_findUseTimeAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");
        UiNode btn = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("使用时允许")));
        assertNotNull("位置权限应有'使用时允许'选项", btn);
    }

    @Test
    public void subLocation_currentlyDenied() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");
        // "不允许" RadioButton 当前选中
        UiNode denied = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("不允许")));
        assertNotNull(denied);
    }

    @Test
    public void subLocation_has4RadioButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");
        java.util.List<UiNode> radios = root.findAllByCombine(
            StringCondition.className("android.widget.RadioButton"));
        assertEquals("位置权限应有 4 个 RadioButton", 4, radios.size());
    }

    // ============ 布局 B: 摄像头 (3 选项 — 使用时允许) ============

    @Test
    public void subCamera_findUseTimeAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");
        assertNotNull(root);
        UiNode btn = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("使用时允许")));
        assertNotNull("摄像头权限应有'使用时允许'选项", btn);
    }

    @Test
    public void subCamera_noAlwaysAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");
        UiNode alwaysAllow = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("始终允许")));
        assertNull("摄像头权限不应有'始终允许'选项", alwaysAllow);
    }

    @Test
    public void subCamera_has3RadioButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");
        java.util.List<UiNode> radios = root.findAllByCombine(
            StringCondition.className("android.widget.RadioButton"));
        assertEquals("摄像头权限应有 3 个 RadioButton", 3, radios.size());
    }

    // ============ 布局 C: 电话/短信 (2 选项 — 允许) ============

    @Test
    public void subPhone_findAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");
        assertNotNull(root);
        UiNode allow = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("允许")));
        assertNotNull("电话权限应有'允许'选项", allow);
    }

    @Test
    public void subPhone_has2RadioButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");
        java.util.List<UiNode> radios = root.findAllByCombine(
            StringCondition.className("android.widget.RadioButton"));
        assertEquals("电话权限应有 2 个 RadioButton", 2, radios.size());
    }

    @Test
    public void subSms_findAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_sms_mgmt.xml");
        assertNotNull(root);
        UiNode allow = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("允许")));
        assertNotNull("短信权限应有'允许'选项", allow);
    }

    // ============ 布局 C+: 通讯录 (2 选项 + 隐私替身) ============

    @Test
    public void subContacts_findAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_contacts.xml");
        assertNotNull(root);
        UiNode allow = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("允许")));
        assertNotNull("通讯录权限应有'允许'选项", allow);
    }

    @Test
    public void subContacts_hasPrivacySwitch() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_contacts.xml");
        UiNode privacyLabel = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textContains("隐私替身")));
        assertNotNull("通讯录权限应有'隐私替身'开关", privacyLabel);
    }

    // ============ 桌面快捷方式 (3 选项) ============

    @Test
    public void subShortcut_findUseTimeAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_shortcut.xml");
        assertNotNull(root);
        UiNode btn = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("使用时允许")));
        assertNotNull("桌面快捷方式应有'使用时允许'选项", btn);
    }

    // ============ 自动化核心: 找到最高优先级允许选项 ============

    @Test
    public void autoGrant_location_prefersAlwaysAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");
        // 优先级: 始终允许 > 使用时允许 > 允许
        String[] priority = {"始终允许", "使用时允许", "允许"};
        UiNode found = null;
        for (String text : priority) {
            found = root.findOneByCombine(CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textEquals(text)));
            if (found != null) break;
        }
        assertNotNull(found);
        assertEquals("位置权限应优先选'始终允许'", "始终允许", found.getText());
    }

    @Test
    public void autoGrant_camera_prefersUseTimeAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");
        String[] priority = {"始终允许", "使用时允许", "允许"};
        UiNode found = null;
        for (String text : priority) {
            found = root.findOneByCombine(CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textEquals(text)));
            if (found != null) break;
        }
        assertNotNull(found);
        assertEquals("摄像头权限应优先选'使用时允许'", "使用时允许", found.getText());
    }

    @Test
    public void autoGrant_phone_fallsBackToAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");
        String[] priority = {"始终允许", "使用时允许", "允许"};
        UiNode found = null;
        for (String text : priority) {
            found = root.findOneByCombine(CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textEquals(text)));
            if (found != null) break;
        }
        assertNotNull(found);
        assertEquals("电话权限应 fallback 到'允许'", "允许", found.getText());
    }

    // ============ 行点击: 找到 clickable 行包含允许文本 ============

    @Test
    public void subCamera_findClickableRowWithAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");
        UiNode row = root.findOneByCombineWithChild(
            CombineFilter.clickable(),
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textEquals("使用时允许")));
        assertNotNull("应找到包含'使用时允许'的 clickable 行", row);
        assertTrue(row.isClickable());
    }

    @Test
    public void subPhone_findClickableRowWithAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");
        UiNode row = root.findOneByCombineWithChild(
            CombineFilter.clickable(),
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textEquals("允许")));
        assertNotNull("应找到包含'允许'的 clickable 行", row);
    }
}
