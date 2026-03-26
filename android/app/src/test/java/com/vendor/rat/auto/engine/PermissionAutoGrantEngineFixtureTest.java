package com.vendor.rat.auto.engine;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.testutil.UiDumpFixture;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * PermissionAutoGrantEngine Fixture 驱动测试
 *
 * 基于 OPPO Find X6 (ColorOS 16, Android 16) 真机 dump 的权限弹窗 XML,
 * 验证 PermissionAutoGrantEngine 能正确找到各类权限弹窗的"允许"按钮。
 *
 * 3 种弹窗布局:
 *   1. 相机/麦克风: 3 按钮 (使用时允许 / 仅本次使用时允许 / 不允许)
 *   2. 位置: 3 按钮 + RadioButton (确切/大致位置)
 *   3. 短信/通话/通讯录: 2 按钮 (允许 / 不允许)
 */
public class PermissionAutoGrantEngineFixtureTest {

    // ============ 相机权限弹窗 (3 按钮布局) ============

    @Test
    public void camera_findAllowForegroundButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_camera.xml");
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));
        assertNotNull("相机弹窗应有 foreground_only 按钮", btn);
        assertEquals("使用时允许", btn.getText());
        assertTrue(btn.isClickable());
    }

    @Test
    public void camera_findOneTimeButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_camera.xml");
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_one_time_button"));
        assertNotNull("相机弹窗应有 one_time 按钮", btn);
        assertEquals("仅本次使用时允许", btn.getText());
    }

    @Test
    public void camera_findDenyButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_camera.xml");
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_deny_button"));
        assertNotNull("相机弹窗应有 deny 按钮", btn);
        assertEquals("不允许", btn.getText());
    }

    @Test
    public void camera_findByText_useWhileApp() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_camera.xml");
        // PermissionAutoGrantEngine ALLOW_BUTTON_TEXTS 优先级匹配
        UiNode btn = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.textContains("使用时允许")
            ));
        assertNotNull("应能通过文本'使用时允许'找到按钮", btn);
    }

    @Test
    public void camera_noAllowAllTimeButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_camera.xml");
        // 相机弹窗没有"始终允许"
        UiNode btn = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.textContains("始终允许")
            ));
        assertNull("相机弹窗不应有'始终允许'按钮", btn);
    }

    @Test
    public void camera_hasPermissionMessage() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_camera.xml");
        UiNode msg = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_message"));
        assertNotNull(msg);
        assertTrue("消息应包含'摄像头'", msg.getText().contains("摄像头"));
    }

    // ============ 位置权限弹窗 (3 按钮 + RadioButton) ============

    @Test
    public void location_findAllowForegroundButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_location.xml");
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));
        assertNotNull("位置弹窗应有 foreground_only 按钮", btn);
        assertEquals("使用时允许", btn.getText());
    }

    @Test
    public void location_hasPrecisionRadioButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_location.xml");
        // 位置弹窗特有: 确切/大致位置 RadioButton
        UiNode fineRadio = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_location_accuracy_radio_fine"));
        UiNode coarseRadio = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_location_accuracy_radio_coarse"));
        assertNotNull("位置弹窗应有'确切位置' RadioButton", fineRadio);
        assertNotNull("位置弹窗应有'大致位置' RadioButton", coarseRadio);
        assertEquals("确切位置", fineRadio.getText());
        assertEquals("大致位置", coarseRadio.getText());
    }

    @Test
    public void location_hasPermissionMessage() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_location.xml");
        UiNode msg = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_message"));
        assertNotNull(msg);
        assertTrue("消息应包含'位置信息'", msg.getText().contains("位置信息"));
    }

    // ============ 短信权限弹窗 (2 按钮布局) ============

    @Test
    public void sms_findAllowButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sms.xml");
        // 短信弹窗: permission_allow_button (不是 foreground_only)
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_button"));
        assertNotNull("短信弹窗应有 allow 按钮", btn);
        assertEquals("允许", btn.getText());
        assertTrue(btn.isClickable());
    }

    @Test
    public void sms_noForegroundOnlyButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sms.xml");
        // 短信弹窗没有"使用时允许"
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));
        assertNull("短信弹窗不应有 foreground_only 按钮", btn);
    }

    @Test
    public void sms_findByText_allow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sms.xml");
        // PermissionAutoGrantEngine fallback: 通过文本"允许"匹配
        UiNode btn = root.findOneByCombine(
            CombineFilter.button("允许"));
        assertNotNull("应能通过文本'允许'找到按钮", btn);
    }

    @Test
    public void sms_hasPermissionMessage() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sms.xml");
        UiNode msg = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_message"));
        assertNotNull(msg);
        assertTrue("消息应包含'短信'", msg.getText().contains("短信"));
    }

    // ============ 通话记录权限弹窗 (2 按钮布局) ============

    @Test
    public void callLog_findAllowButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_call_log.xml");
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_button"));
        assertNotNull("通话记录弹窗应有 allow 按钮", btn);
        assertEquals("允许", btn.getText());
    }

    @Test
    public void callLog_hasPermissionMessage() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_call_log.xml");
        UiNode msg = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_message"));
        assertNotNull(msg);
        assertTrue("消息应包含'通话记录'", msg.getText().contains("通话记录"));
    }

    // ============ PermissionAutoGrantEngine 优先级匹配验证 ============

    @Test
    public void priority_camera_prefersForegroundOnly() {
        // 相机弹窗: 有"使用时允许", 应优先于"允许"
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_camera.xml");

        // 按 PermissionAutoGrantEngine 优先级: 先找"使用时允许"
        UiNode foreground = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.Button"),
            StringCondition.textContains("使用时允许")));
        // 不应有通用"允许"按钮
        UiNode allow = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_button"));
        assertNotNull("应找到'使用时允许'", foreground);
        assertNull("相机弹窗不应有通用 allow 按钮", allow);
    }

    @Test
    public void priority_sms_fallsBackToAllow() {
        // 短信弹窗: 没有"使用时允许", 应 fallback 到"允许"
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sms.xml");

        UiNode foreground = root.findOneByCombine(CombineFilter.and(
            StringCondition.className("android.widget.Button"),
            StringCondition.textContains("使用时允许")));
        UiNode allow = root.findOneByCombine(CombineFilter.button("允许"));
        assertNull("短信弹窗不应有'使用时允许'", foreground);
        assertNotNull("短信弹窗应 fallback 到'允许'", allow);
    }
}
