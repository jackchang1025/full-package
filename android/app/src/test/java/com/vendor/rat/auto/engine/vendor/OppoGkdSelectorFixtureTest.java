package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.testutil.UiDumpFixture;
import com.vendor.rat.auto.util.GkdSelectorHelper;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * GKD Selector Fixture 驱动测试
 *
 * 使用与 OppoEngineFixtureTest / OppoPermissionMgmtFixtureTest 相同的真机 XML fixture,
 * 验证 GkdSelectorHelper.findOne/findAll + GKD 选择器语法在 mock 节点树上正确工作。
 *
 * 这些测试验证 CRITICAL 修复后的完整链路:
 *   GkdTransform.getAttr (QueryContext unwrap + Boolean types)
 *   → Transform.querySelector (tree traversal)
 *   → Selector.parse + match (CSS-like selectors)
 *
 * GKD Connect 操作符说明:
 *   A > B   — B 的直接父节点匹配 A (仅 offset=0)
 *   A >2 B  — B 的第 2 级祖先匹配 A (offset=1)
 *   A >n B  — B 的任意祖先匹配 A (等价 CSS descendant 语义)
 */
public class OppoGkdSelectorFixtureTest {

    // ============ 应用详情页 (app_detail.xml) ============

    @Test
    public void appDetail_gkd_findBatteryManagement() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");
        assertNotNull("fixture 加载失败", root);

        // GKD selector: className + text contains
        UiNode target = GkdSelectorHelper.findOne(root, "TextView[text*=\"耗电管理\"]");
        assertNotNull("GKD 应找到'耗电管理'文本", target);
        assertEquals("耗电管理", target.getText());
    }

    @Test
    public void appDetail_gkd_findAppName() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");

        UiNode appName = GkdSelectorHelper.findOne(root, "TextView[text*=\"System Service\"]");
        assertNotNull("GKD 应找到 app 名称 'System Service'", appName);
        assertTrue(appName.getText().contains("System Service"));
    }

    @Test
    public void appDetail_gkd_findScrollableNode() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");

        // GKD selector: boolean attribute
        UiNode scrollable = GkdSelectorHelper.findOne(root, "[scrollable=true]");
        assertNotNull("GKD 应找到 scrollable 节点", scrollable);
        assertTrue("节点应 scrollable", scrollable.isScrollable());
    }

    // ============ 耗电管理页 (power_control.xml) ============

    @Test
    public void powerControl_gkd_findFullBackgroundText() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");
        assertNotNull("fixture 加载失败", root);

        UiNode textNode = GkdSelectorHelper.findOne(root, "TextView[text*=\"完全允许后台行为\"]");
        assertNotNull("GKD 应找到'完全允许后台行为'", textNode);
    }

    @Test
    public void powerControl_gkd_findAnyAncestorClickable() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // GKD >n = 任意祖先匹配 (类似 CSS descendant 选择器)
        // 真机 XML: TextView 的 grandparent (LinearLayout) 是 clickable
        UiNode childText = GkdSelectorHelper.findOne(root,
                "[clickable=true] >n TextView[text*=\"完全允许后台行为\"]");
        assertNotNull("GKD >n 应找到有 clickable 祖先的'完全允许后台行为'", childText);
        assertTrue(childText.getText().contains("完全允许后台行为"));
    }

    @Test
    public void powerControl_gkd_directParent_isRelativeLayout() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // GKD > (直接父节点) — 真机 XML: TextView 直接父节点是 RelativeLayout
        UiNode childText = GkdSelectorHelper.findOne(root,
                "RelativeLayout > TextView[text*=\"完全允许后台行为\"]");
        assertNotNull("GKD > 直接父节点应是 RelativeLayout", childText);
    }

    @Test
    public void powerControl_gkd_findRadioButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // GKD className-only selector
        UiNode radio = GkdSelectorHelper.findOne(root, "RadioButton");
        assertNotNull("GKD 应找到 RadioButton", radio);
    }

    @Test
    public void powerControl_gkd_noSwitchWidget() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_control.xml");

        // GKD: Switch should not exist
        UiNode switchNode = GkdSelectorHelper.findOne(root, "Switch");
        assertNull("GKD 耗电管理页不应有 Switch (ColorOS 16 用 RadioButton)", switchNode);
    }

    // ============ 确认对话框 (power_dialog.xml) ============

    @Test
    public void powerDialog_gkd_findAllowButton_byIdSuffix() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");
        assertNotNull("fixture 加载失败", root);

        // GKD: id ends with
        UiNode allowBtn = GkdSelectorHelper.findOne(root, "[id$=\"button1\"]");
        assertNotNull("GKD 应找到 id$='button1' 的允许按钮", allowBtn);
        assertEquals("允许", allowBtn.getText());
        assertTrue("按钮应 clickable", allowBtn.isClickable());
    }

    @Test
    public void powerDialog_gkd_findAllowButton_byText() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        // GKD: className + text exact match
        UiNode btn = GkdSelectorHelper.findOne(root, "Button[text=\"允许\"]");
        assertNotNull("GKD 应找到'允许'按钮", btn);
    }

    @Test
    public void powerDialog_gkd_findCancelButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        UiNode cancelBtn = GkdSelectorHelper.findOne(root, "[id$=\"button2\"]");
        assertNotNull("GKD 应找到 id$='button2' 的取消按钮", cancelBtn);
        assertEquals("取消", cancelBtn.getText());
    }

    // ============ 权限弹窗 (permission_popup.xml) ============

    @Test
    public void permissionPopup_gkd_findAllowForegroundButton() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/permission_popup.xml");
        assertNotNull("fixture 加载失败", root);

        UiNode btn = GkdSelectorHelper.findOne(root, "Button[text*=\"使用时允许\"]");
        assertNotNull("GKD 应找到'使用时允许'按钮", btn);
        assertTrue("按钮应 clickable", btn.isClickable());
    }

    @Test
    public void permissionPopup_gkd_findByResourceId() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/permission_popup.xml");

        UiNode btn = GkdSelectorHelper.findOne(root,
                "[id*=\"permission_allow_foreground_only_button\"]");
        assertNotNull("GKD 应通过 resource-id 找到权限按钮", btn);
    }

    // ============ 权限管理页 (perm_mgmt_top.xml, perm_mgmt_denied.xml) ============

    @Test
    public void permMgmt_gkd_findAllDeniedTexts() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_top.xml");
        assertNotNull(root);

        // GKD findAll: 精确文本匹配
        List<UiNode> deniedTexts = GkdSelectorHelper.findAll(root, "TextView[text=\"不允许\"]");
        assertNotNull("findAll 应返回非 null 列表", deniedTexts);
        assertFalse("应找到至少一个'不允许'文本", deniedTexts.isEmpty());
    }

    @Test
    public void permMgmt_denied_gkd_findCamera() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_denied.xml");
        assertNotNull(root);

        UiNode cam = GkdSelectorHelper.findOne(root, "TextView[text*=\"摄像头\"]");
        assertNotNull("GKD 滚动后应找到'摄像头'权限", cam);
    }

    // ============ 权限子页面 — 自动授权逻辑 ============

    @Test
    public void subLocation_gkd_findAlwaysAllow_anyAncestor() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");
        assertNotNull(root);

        // GKD >n = 任意深度祖先
        // 位置权限: TextView["始终允许"] 的 grandparent (LinearLayout) 是 clickable
        UiNode alwaysAllow = GkdSelectorHelper.findOne(root,
                "[clickable=true] >n TextView[text=\"始终允许\"]");
        assertNotNull("GKD >n 位置权限应找到有 clickable 祖先的'始终允许'", alwaysAllow);
        assertEquals("始终允许", alwaysAllow.getText());
    }

    @Test
    public void subLocation_gkd_findAlwaysAllow_textOnly() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");

        // 纯文本选择器 (不含 connect)
        UiNode alwaysAllow = GkdSelectorHelper.findOne(root, "TextView[text=\"始终允许\"]");
        assertNotNull("GKD 位置权限应有'始终允许'文本", alwaysAllow);
        assertEquals("始终允许", alwaysAllow.getText());
    }

    @Test
    public void subCamera_gkd_prefersUseTimeAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");
        assertNotNull(root);

        // 摄像头: 3 选项, 无 "始终允许", 优先 "使用时允许"
        UiNode alwaysAllow = GkdSelectorHelper.findOne(root, "TextView[text=\"始终允许\"]");
        assertNull("摄像头权限不应有'始终允许'", alwaysAllow);

        UiNode useTimeAllow = GkdSelectorHelper.findOne(root, "TextView[text=\"使用时允许\"]");
        assertNotNull("摄像头权限应有'使用时允许'", useTimeAllow);
    }

    @Test
    public void subPhone_gkd_fallsBackToAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");
        assertNotNull(root);

        // 电话: 2 选项, 只有 "允许" / "不允许"
        UiNode alwaysAllow = GkdSelectorHelper.findOne(root, "TextView[text=\"始终允许\"]");
        assertNull("电话权限不应有'始终允许'", alwaysAllow);

        UiNode useTimeAllow = GkdSelectorHelper.findOne(root, "TextView[text=\"使用时允许\"]");
        assertNull("电话权限不应有'使用时允许'", useTimeAllow);

        UiNode allow = GkdSelectorHelper.findOne(root, "TextView[text=\"允许\"]");
        assertNotNull("电话权限应 fallback 到'允许'", allow);
        assertEquals("允许", allow.getText());
    }

    @Test
    public void autoGrant_gkd_location_priorityLogic() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");

        // 模拟 OppoEngine 的自动授权优先级: 始终允许 > 使用时允许 > 允许
        String[] priority = {"始终允许", "使用时允许", "允许"};
        UiNode found = null;
        for (String text : priority) {
            found = GkdSelectorHelper.findOne(root, "TextView[text=\"" + text + "\"]");
            if (found != null) break;
        }
        assertNotNull(found);
        assertEquals("位置权限应优先选'始终允许'", "始终允许", found.getText());
    }

    @Test
    public void autoGrant_gkd_camera_priorityLogic() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");

        String[] priority = {"始终允许", "使用时允许", "允许"};
        UiNode found = null;
        for (String text : priority) {
            found = GkdSelectorHelper.findOne(root, "TextView[text=\"" + text + "\"]");
            if (found != null) break;
        }
        assertNotNull(found);
        assertEquals("摄像头权限应优先选'使用时允许'", "使用时允许", found.getText());
    }

    @Test
    public void autoGrant_gkd_phone_priorityLogic() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");

        String[] priority = {"始终允许", "使用时允许", "允许"};
        UiNode found = null;
        for (String text : priority) {
            found = GkdSelectorHelper.findOne(root, "TextView[text=\"" + text + "\"]");
            if (found != null) break;
        }
        assertNotNull(found);
        assertEquals("电话权限应 fallback 到'允许'", "允许", found.getText());
    }

    // ============ RadioButton 计数 (findAll) ============

    @Test
    public void subLocation_gkd_has4RadioButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");

        List<UiNode> radios = GkdSelectorHelper.findAll(root, "RadioButton");
        assertEquals("位置权限应有 4 个 RadioButton", 4, radios.size());
    }

    @Test
    public void subCamera_gkd_has3RadioButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_camera.xml");

        List<UiNode> radios = GkdSelectorHelper.findAll(root, "RadioButton");
        assertEquals("摄像头权限应有 3 个 RadioButton", 3, radios.size());
    }

    @Test
    public void subPhone_gkd_has2RadioButtons() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");

        List<UiNode> radios = GkdSelectorHelper.findAll(root, "RadioButton");
        assertEquals("电话权限应有 2 个 RadioButton", 2, radios.size());
    }

    @Test
    public void subContacts_gkd_findAllowOption() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_contacts.xml");
        assertNotNull(root);

        UiNode allow = GkdSelectorHelper.findOne(root, "TextView[text=\"允许\"]");
        assertNotNull("通讯录权限应有'允许'选项", allow);
    }

    // ============ GKD Selector 特有功能测试 ============

    @Test
    public void escapeForSelector_normalText() {
        assertEquals("普通文本", GkdSelectorHelper.escapeForSelector("普通文本"));
    }

    @Test
    public void escapeForSelector_withDoubleQuotes() {
        // 双引号应被转义
        assertEquals("say \\\"hello\\\"", GkdSelectorHelper.escapeForSelector("say \"hello\""));
    }

    @Test
    public void escapeForSelector_withBackslash() {
        // 反斜杠应被转义
        assertEquals("path\\\\to\\\\file", GkdSelectorHelper.escapeForSelector("path\\to\\file"));
    }

    @Test
    public void escapeForSelector_null() {
        assertEquals("", GkdSelectorHelper.escapeForSelector(null));
    }

    @Test
    public void escapeForSelector_empty() {
        assertEquals("", GkdSelectorHelper.escapeForSelector(""));
    }

    @Test
    public void gkd_booleanAttribute_clickableTrue() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        // [clickable=true] 应匹配 boolean 属性 (不是字符串 "true")
        UiNode clickable = GkdSelectorHelper.findOne(root, "[clickable=true]");
        assertNotNull("GKD 应找到 clickable=true 的节点", clickable);
        assertTrue("节点应确实是 clickable", clickable.isClickable());
    }

    @Test
    public void gkd_booleanAttribute_checkedFalse() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");

        // [checked=false] — 第一个 RadioButton (允许) 未选中
        UiNode unchecked = GkdSelectorHelper.findOne(root, "RadioButton[checked=false]");
        assertNotNull("GKD 应找到 checked=false 的 RadioButton", unchecked);
        assertFalse("RadioButton 应确实未选中", unchecked.isChecked());
    }

    @Test
    public void gkd_booleanAttribute_checkedTrue() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");

        // 电话权限: "不允许" 的 RadioButton 当前选中 (checked=true)
        UiNode checked = GkdSelectorHelper.findOne(root, "RadioButton[checked=true]");
        assertNotNull("GKD 应找到 checked=true 的 RadioButton", checked);
        assertTrue("RadioButton 应确实选中", checked.isChecked());
    }

    @Test
    public void gkd_findAll_returnsEmptyList_whenNoMatch() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        // 对话框中不应有 RadioButton
        List<UiNode> results = GkdSelectorHelper.findAll(root, "RadioButton");
        assertNotNull("findAll 应返回非 null 列表", results);
        assertTrue("对话框中不应有 RadioButton, 列表应为空", results.isEmpty());
    }

    @Test
    public void gkd_findAll_returnsEmptyList_whenNoMatchText() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");

        List<UiNode> results = GkdSelectorHelper.findAll(root, "TextView[text=\"不存在的文本XYZ\"]");
        assertNotNull("findAll 不应返回 null", results);
        assertTrue("不存在的文本应返回空列表", results.isEmpty());
    }

    @Test
    public void gkd_findOne_returnsNull_whenNoMatch() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/power_dialog.xml");

        UiNode result = GkdSelectorHelper.findOne(root, "CheckBox[text=\"不存在\"]");
        assertNull("findOne 不匹配时应返回 null", result);
    }

    // ============ GKD Connect 操作符语义验证 ============

    @Test
    public void gkd_directParent_vs_anyAncestor() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");

        // > (直接父节点) — "始终允许" 的直接父是 RelativeLayout (非 clickable)
        UiNode directParent = GkdSelectorHelper.findOne(root,
                "[clickable=true] > TextView[text=\"始终允许\"]");
        assertNull("GKD > 直接父节点不是 clickable, 应返回 null", directParent);

        // >n (任意祖先) — grandparent LinearLayout 是 clickable
        UiNode anyAncestor = GkdSelectorHelper.findOne(root,
                "[clickable=true] >n TextView[text=\"始终允许\"]");
        assertNotNull("GKD >n 任意祖先中有 clickable, 应找到", anyAncestor);
    }

    // ============ 跨 fixture 一致性: GKD 与 CombineFilter 结果应一致 ============

    @Test
    public void consistency_permMgmtDenied_findPhone() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_denied.xml");

        UiNode gkdResult = GkdSelectorHelper.findOne(root, "TextView[text=\"电话\"]");
        assertNotNull("GKD 应在 perm_mgmt_denied 中找到'电话'", gkdResult);
        assertEquals("电话", gkdResult.getText());
    }

    @Test
    public void consistency_shortcut_findUseTimeAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_shortcut.xml");
        assertNotNull(root);

        UiNode btn = GkdSelectorHelper.findOne(root, "TextView[text=\"使用时允许\"]");
        assertNotNull("GKD 桌面快捷方式应有'使用时允许'选项", btn);
    }

    @Test
    public void consistency_smsMgmt_findAllow() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_sms_mgmt.xml");
        assertNotNull(root);

        UiNode allow = GkdSelectorHelper.findOne(root, "TextView[text=\"允许\"]");
        assertNotNull("GKD 短信权限应有'允许'选项", allow);
    }

    // ============ findAll 多结果计数 ============

    @Test
    public void findAll_gkd_multipleTextViews() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_location.xml");

        // 位置权限页面应有多个 TextView
        List<UiNode> textViews = GkdSelectorHelper.findAll(root, "TextView");
        assertNotNull(textViews);
        assertTrue("位置权限页应有多个 TextView", textViews.size() > 3);
    }

    @Test
    public void findAll_gkd_deniedCountInPermMgmt() {
        UiNode root = UiDumpFixture.load("fixtures/oppo/perm_mgmt_denied.xml");

        List<UiNode> deniedTexts = GkdSelectorHelper.findAll(root, "TextView[text=\"不允许\"]");
        assertNotNull(deniedTexts);
        assertTrue("perm_mgmt_denied 中应有至少一个'不允许'", deniedTexts.size() >= 1);
    }
}
