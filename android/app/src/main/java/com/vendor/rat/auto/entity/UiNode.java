package com.vendor.rat.auto.entity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.filter.NodeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * UI 节点封装 (模块 04 核心)
 *
 * 封装 AccessibilityNodeInfo，提供简洁的查询和操作 API
 *
 * 基于逆向分析: com/guard/wallet/entity/UiObject.java (~500 行)
 * 关键能力:
 *   - findOneByCombine: 递归 DFS 查找第一个匹配节点
 *   - findAllByCombine: 递归查找所有匹配节点
 *   - scrollForwardUntil/scrollBackwardUntil: 滚动查找
 *   - scrollForwardEnd: 滚动到底部
 *   - findParentUntil: 向上遍历父节点查找
 *   - findClickableParent: 查找可点击父节点
 *   - click(): 增强版，节点不可点击时向上查找可点击父节点
 */
public class UiNode {

    private static final String TAG = "UiNode";
    private static final long SCROLL_DELAY = 100L;  // 滚动间隔 ms
    private static final int MAX_SCROLL_ATTEMPTS = 30; // 最大滚动次数

    private final AccessibilityNodeInfo nodeInfo;

    public UiNode(AccessibilityNodeInfo nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    // ============ 属性访问 ============

    public String getText() {
        return nodeInfo.getText() != null ? nodeInfo.getText().toString() : "";
    }

    public String getClassName() {
        return nodeInfo.getClassName() != null ? nodeInfo.getClassName().toString() : "";
    }

    public String getViewIdResourceName() {
        return nodeInfo.getViewIdResourceName() != null
            ? nodeInfo.getViewIdResourceName() : "";
    }

    public String getContentDescription() {
        return nodeInfo.getContentDescription() != null
            ? nodeInfo.getContentDescription().toString() : "";
    }

    public boolean isClickable() { return nodeInfo.isClickable(); }
    public boolean isEnabled() { return nodeInfo.isEnabled(); }
    public boolean isChecked() { return nodeInfo.isChecked(); }
    public boolean isScrollable() { return nodeInfo.isScrollable(); }
    public boolean isCheckable() { return nodeInfo.isCheckable(); }
    public boolean isFocusable() { return nodeInfo.isFocusable(); }
    public boolean isSelected() { return nodeInfo.isSelected(); }

    public String getPackageName() {
        return nodeInfo.getPackageName() != null ? nodeInfo.getPackageName().toString() : "";
    }

    public String getHintText() {
        // ADAPT: API 26+, vendor UiObject 有此属性
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            CharSequence hint = nodeInfo.getHintText();
            return hint != null ? hint.toString() : "";
        }
        return "";
    }

    public String getPanelTitle() {
        // ADAPT: API 28+, vendor UiObject.paneTitle
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            CharSequence pane = nodeInfo.getPaneTitle();
            return pane != null ? pane.toString() : "";
        }
        return "";
    }

    public String getRoleDescription() {
        // ADAPT: API 26+
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            // TODO: VENDOR_VERIFY - roleDescription 需要 AccessibilityNodeInfoCompat
            return "";
        }
        return "";
    }

    public String getStateDescription() {
        // ADAPT: API 30+
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            CharSequence state = nodeInfo.getStateDescription();
            return state != null ? state.toString() : "";
        }
        return "";
    }

    public String getTooltipText() {
        // ADAPT: API 28+
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            CharSequence tooltip = nodeInfo.getTooltipText();
            return tooltip != null ? tooltip.toString() : "";
        }
        return "";
    }

    public String getUniqueId() {
        // ADAPT: API 33+
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            String uid = nodeInfo.getUniqueId();
            return uid != null ? uid : "";
        }
        return "";
    }

    /**
     * 是否密码输入框
     * 基于逆向: o/h.java 锁屏密码监控
     */
    public boolean isPassword() {
        return nodeInfo.isPassword();
    }

    // Vendor: UiObject.longClickable()
    public boolean isLongClickable() { return nodeInfo.isLongClickable(); }
    // Vendor: UiObject.focused()
    public boolean isFocused() { return nodeInfo.isFocused(); }
    // Vendor: UiObject.visibleToUser()
    public boolean isVisibleToUser() { return nodeInfo.isVisibleToUser(); }
    // Vendor: UiObject.editable()
    public boolean isEditable() { return nodeInfo.isEditable(); }
    // Vendor: UiObject.multiLine()
    public boolean isMultiLine() { return nodeInfo.isMultiLine(); }
    // Vendor: UiObject.dismissable()
    public boolean isDismissable() { return nodeInfo.isDismissable(); }
    // Vendor: UiObject.contentInvalid()
    public boolean isContentInvalid() { return nodeInfo.isContentInvalid(); }
    // Vendor: UiObject.contextClickable()
    public boolean isContextClickable() { return nodeInfo.isContextClickable(); }

    // Vendor: UiObject.boundsInParent()
    public Rect boundsInParent() {
        Rect rect = new Rect();
        nodeInfo.getBoundsInParent(rect);
        return rect;
    }

    /**
     * 获取屏幕坐标边界
     * // ADAPT: vendor UiObject.getBoundsInScreen()
     */
    public Rect boundsInScreen() {
        Rect rect = new Rect();
        nodeInfo.getBoundsInScreen(rect);
        return rect;
    }

    // Vendor: UiObject.centerInScreen()
    public Point centerInScreen() {
        Rect bounds = boundsInScreen();
        return new Point(bounds.exactCenterX(), bounds.exactCenterY());
    }

    // Vendor: UiObject.centerInParent()
    public Point centerInParent() {
        Rect bounds = boundsInParent();
        return new Point(bounds.centerX(), bounds.centerY());
    }

    // ============ 基础操作 ============

    /**
     * 点击操作 (增强版)
     * 如果当前节点不可点击，向上遍历查找可点击的父节点
     * 基于逆向: UiObject.click() 带 parent traversal
     */
    public boolean click() {
        if (nodeInfo.isClickable()) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        // 向上查找可点击的父节点
        UiNode clickableParent = findClickableParent();
        if (clickableParent != null) {
            return clickableParent.getNodeInfo()
                .performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        // 最后尝试直接点击
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    /**
     * 强制直接点击（不查找父节点）
     */
    public boolean clickDirect() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    public boolean longClick() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }

    public boolean setText(String text) {
        Bundle args = new Bundle();
        args.putCharSequence(
            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
    }

    public boolean focus() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
    }

    public boolean clearFocus() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS);
    }

    public boolean select() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SELECT);
    }

    // Vendor: UiObject.accessibilityFocus()
    public boolean accessibilityFocus() {
        return performAction(64);
    }

    // Vendor: UiObject.clearAccessibilityFocus()
    public boolean clearAccessibilityFocus() {
        return performAction(128);
    }

    // Vendor: UiObject.copy()
    public boolean copy() { return performAction(16384); }

    // Vendor: UiObject.paste()
    public boolean paste() { return performAction(32768); }

    // Vendor: UiObject.cut()
    public boolean cut() { return performAction(65536); }

    // Vendor: UiObject.collapse()
    public boolean collapse() { return performAction(524288); }

    // Vendor: UiObject.expand()
    public boolean expand() { return performAction(262144); }

    // Vendor: UiObject.dismiss()
    public boolean dismiss() { return performAction(1048576); }

    // Vendor: UiObject.show()
    public boolean show() {
        return performAction(16908354); // ACTION_SHOW_ON_SCREEN
    }

    // Vendor: UiObject.contextClick()
    public boolean contextClick() {
        return performAction(16908348); // ACTION_CONTEXT_CLICK
    }

    // Vendor: UiObject.scrollLeft/Right/Up/Down
    public boolean scrollLeft() { return performAction(16908345); }
    public boolean scrollRight() { return performAction(16908347); }
    public boolean scrollUp() { return performAction(16908344); }
    public boolean scrollDown() { return performAction(16908346); }

    // Vendor: UiObject.setSelection(int, int)
    public boolean setSelection(int start, int end) {
        Bundle args = new Bundle();
        args.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, start);
        args.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, end);
        return performAction(131072, args);
    }

    // Vendor: UiObject.setProgress(float)
    public boolean setProgress(float value) {
        Bundle args = new Bundle();
        args.putFloat(AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE, value);
        return performAction(16908349, args); // ACTION_SET_PROGRESS
    }

    // Vendor: UiObject.scrollTo(int, int)
    public boolean scrollTo(int row, int column) {
        Bundle args = new Bundle();
        args.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, row);
        args.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_COLUMN_INT, column);
        return performAction(16908343, args); // ACTION_SCROLL_TO_POSITION
    }

    /**
     * 通用 performAction (供 UiNodeCollection 调用)
     * Vendor: UiObject.performAction(int)
     */
    public boolean performAction(int action) {
        return nodeInfo.performAction(action);
    }

    /**
     * 带 Bundle 参数的 performAction
     * Vendor: UiObject.performAction(int, Bundle)
     */
    public boolean performAction(int action, Bundle arguments) {
        return nodeInfo.performAction(action, arguments);
    }

    /**
     * 获取父节点 (别名，vendor 中 UiObject.parent() 调用)
     */
    public UiNode parent() {
        return getParent();
    }

    /**
     * 是否可以向前滚动
     * Vendor: UiObject.canScrollForward()
     */
    public boolean canScrollForward() {
        return nodeInfo.isScrollable();
    }

    /**
     * 是否可以向后滚动
     * Vendor: UiObject.canScrollBackward()
     */
    public boolean canScrollBackward() {
        return nodeInfo.isScrollable();
    }

    // ============ 滚动操作 ============

    public boolean scrollForward() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }

    public boolean scrollBackward() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }

    /**
     * 滚动到底部
     * 基于逆向: UiObject.scrollForwardEnd()
     * 持续向下滚动直到无法再滚
     */
    public boolean scrollForwardEnd() {
        boolean scrolled = false;
        int attempts = 0;
        while (attempts < MAX_SCROLL_ATTEMPTS) {
            if (!nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                break;
            }
            scrolled = true;
            attempts++;
            sleep(SCROLL_DELAY);
        }
        return scrolled;
    }

    /**
     * 滚动到顶部
     */
    public boolean scrollBackwardEnd() {
        boolean scrolled = false;
        int attempts = 0;
        while (attempts < MAX_SCROLL_ATTEMPTS) {
            if (!nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) {
                break;
            }
            scrolled = true;
            attempts++;
            sleep(SCROLL_DELAY);
        }
        return scrolled;
    }

    /**
     * 向下滚动查找
     * 基于逆向: UiObject.scrollForwardUtil(filter)
     * 每次滚动后在当前可见内容中查找匹配节点
     */
    public UiNode scrollForwardUntil(NodeFilter filter) {
        int attempts = 0;
        while (attempts < MAX_SCROLL_ATTEMPTS) {
            // 先在当前可见范围查找
            UiNode found = findOneByCombine(filter);
            if (found != null) {
                return found;
            }
            // 向下滚动
            if (!nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                break;
            }
            attempts++;
            sleep(SCROLL_DELAY);
            // 刷新节点
            refresh();
        }
        // 最后再尝试查找一次
        return findOneByCombine(filter);
    }

    /**
     * 向上滚动查找
     * 基于逆向: UiObject.scrollBackwardUtil(filter)
     */
    public UiNode scrollBackwardUntil(NodeFilter filter) {
        int attempts = 0;
        while (attempts < MAX_SCROLL_ATTEMPTS) {
            // 先在当前可见范围查找
            UiNode found = findOneByCombine(filter);
            if (found != null) {
                return found;
            }
            // 向上滚动
            if (!nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) {
                break;
            }
            attempts++;
            sleep(SCROLL_DELAY);
            // 刷新节点
            refresh();
        }
        // 最后再尝试查找一次
        return findOneByCombine(filter);
    }

    // ============ 子节点访问 ============

    public int getChildCount() { return nodeInfo.getChildCount(); }

    public UiNode getChild(int index) {
        AccessibilityNodeInfo child = nodeInfo.getChild(index);
        return child != null ? new UiNode(child) : null;
    }

    public List<UiNode> getChildren() {
        List<UiNode> children = new ArrayList<>();
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo child = nodeInfo.getChild(i);
            if (child != null) {
                children.add(new UiNode(child));
            }
        }
        return children;
    }

    // ============ 父节点访问 ============

    public UiNode getParent() {
        AccessibilityNodeInfo parent = nodeInfo.getParent();
        return parent != null ? new UiNode(parent) : null;
    }

    /**
     * 向上遍历查找符合条件的父节点
     * 基于逆向: UiObject.findParentUtilCombine(filter)
     */
    public UiNode findParentUntil(NodeFilter filter) {
        AccessibilityNodeInfo current = nodeInfo.getParent();
        int depth = 0;
        while (current != null && depth < 20) { // 防止无限循环
            UiNode parentNode = new UiNode(current);
            if (filter.accept(parentNode)) {
                return parentNode;
            }
            current = current.getParent();
            depth++;
        }
        return null;
    }

    /**
     * 查找可点击的父节点
     * 基于逆向: findParentUtilCombine(c.L()) 其中 c.L() 是 clickable=true 过滤器
     */
    public UiNode findClickableParent() {
        return findParentUntil(new NodeFilter() {
            @Override
            public boolean accept(UiNode node) {
                return node.isClickable();
            }
        });
    }

    // ============ 查找方法 (递归 DFS) ============

    /**
     * 递归查找第一个匹配的节点
     * 基于逆向: UiObject.findOneByCombine(CombineFilter)
     */
    public UiNode findOneByCombine(NodeFilter filter) {
        return findNodeRecursive(nodeInfo, filter);
    }

    /**
     * 查找匹配 parentFilter 且包含匹配 childFilter 子节点的第一个节点
     * 对应逆向: UiObject.findOneByCombineWithChild(CombineFilterWithChild)
     * vendor 模式: k().findOneByCombineWithChild(new CombineFilterWithChild(K(), textFilter))
     *   K() = clickable row filter
     *   textFilter = 目标文本 filter (e0/c0/f0 等)
     *
     * @param parentFilter 父节点过滤条件
     * @param childFilter 子节点过滤条件
     * @return 匹配的父节点, 或 null
     */
    public UiNode findOneByCombineWithChild(NodeFilter parentFilter, NodeFilter childFilter) {
        return findWithChildRecursive(nodeInfo, parentFilter, childFilter);
    }

    private UiNode findWithChildRecursive(AccessibilityNodeInfo node,
                                           NodeFilter parentFilter, NodeFilter childFilter) {
        if (node == null) return null;
        UiNode wrapped = new UiNode(node);
        // 先检查当前节点是否匹配 parentFilter
        if (parentFilter.accept(wrapped)) {
            // 再检查子节点是否有匹配 childFilter 的
            UiNode childMatch = findNodeRecursive(node, childFilter);
            if (childMatch != null) {
                return wrapped;
            }
        }
        // 递归子节点
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                UiNode result = findWithChildRecursive(child, parentFilter, childFilter);
                if (result != null) return result;
            }
        }
        return null;
    }

    /**
     * 递归查找所有匹配的节点
     */
    public List<UiNode> findAllByCombine(NodeFilter filter) {
        List<UiNode> results = new ArrayList<>();
        findAllNodesRecursive(nodeInfo, filter, results);
        return results;
    }

    // ============ 递归查找实现 ============

    private UiNode findNodeRecursive(AccessibilityNodeInfo node, NodeFilter filter) {
        if (node == null) return null;

        // 检查当前节点
        UiNode uiNode = new UiNode(node);
        if (filter.accept(uiNode)) {
            return uiNode;
        }

        // 递归检查子节点 (DFS)
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                UiNode found = findNodeRecursive(child, filter);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    private void findAllNodesRecursive(AccessibilityNodeInfo node, NodeFilter filter,
                                        List<UiNode> results) {
        if (node == null) return;

        // 检查当前节点
        UiNode uiNode = new UiNode(node);
        if (filter.accept(uiNode)) {
            results.add(uiNode);
        }

        // 递归检查子节点
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                findAllNodesRecursive(child, filter, results);
            }
        }
    }

    // ============ 节点管理 ============

    public AccessibilityNodeInfo getNodeInfo() { return nodeInfo; }

    /**
     * Vendor: UiObject.source()
     */
    public AccessibilityNodeInfo source() { return nodeInfo; }

    /**
     * Vendor: UiObject.createRoot(AccessibilityNodeInfo)
     */
    public static UiNode createRoot(AccessibilityNodeInfo info) {
        if (info == null) return null;
        try {
            return new UiNode(info);
        } catch (Exception e) {
            Log.e(TAG, "createRoot error", e);
            return null;
        }
    }

    // ============ Vendor 风格属性访问 (别名) ============

    /** Vendor: UiObject.text() */
    public String text() { return getText(); }
    /** Vendor: UiObject.className() */
    public String className() { return getClassName(); }
    /** Vendor: UiObject.id() */
    public String id() { return getViewIdResourceName(); }
    /** Vendor: UiObject.desc() */
    public String desc() { return getContentDescription(); }
    /** Vendor: UiObject.depth() */
    public int depth() { return 0; }
    /** Vendor: UiObject.indexInParent() */
    public int indexInParent() { return 0; }
    /** Vendor: UiObject.childCount() */
    public int childCount() { return getChildCount(); }
    /** Vendor: UiObject.child(int) */
    public UiNode child(int index) { return getChild(index); }
    /** Vendor: UiObject.clickable() */
    public boolean clickable() { return isClickable(); }
    /** Vendor: UiObject.checkable() */
    public boolean checkable() { return isCheckable(); }
    /** Vendor: UiObject.checked() */
    public boolean checked() { return isChecked(); }
    /** Vendor: UiObject.enabled() */
    public boolean enabled() { return isEnabled(); }
    /** Vendor: UiObject.focused() */
    public boolean focused() { return isFocused(); }
    /** Vendor: UiObject.focusable() */
    public boolean focusable() { return isFocusable(); }
    /** Vendor: UiObject.scrollable() */
    public boolean scrollable() { return isScrollable(); }
    /** Vendor: UiObject.selected() */
    public boolean selected() { return isSelected(); }
    /** Vendor: UiObject.password() */
    public boolean password() { return isPassword(); }
    /** Vendor: UiObject.longClickable() */
    public boolean longClickable() { return isLongClickable(); }
    /** Vendor: UiObject.editable() */
    public boolean editable() { return isEditable(); }
    /** Vendor: UiObject.visibleToUser() */
    public boolean visibleToUser() { return isVisibleToUser(); }
    /** Vendor: UiObject.dismissable() */
    public boolean dismissable() { return isDismissable(); }
    /** Vendor: UiObject.multiLine() */
    public boolean multiLine() { return isMultiLine(); }
    /** Vendor: UiObject.contentInvalid() */
    public boolean contentInvalid() { return isContentInvalid(); }
    /** Vendor: UiObject.contextClickable() */
    public boolean contextClickable() { return isContextClickable(); }
    /** Vendor: UiObject.uniqueId() */
    public String uniqueId() { return getUniqueId(); }
    /** Vendor: UiObject.packageName() */
    public String packageName() { return getPackageName(); }
    /** Vendor: UiObject.hintText() */
    public String hintText() { return getHintText(); }
    /** Vendor: UiObject.paneTitle() */
    public String paneTitle() { return getPanelTitle(); }
    /** Vendor: UiObject.roleDesc() */
    public String roleDesc() { return getRoleDescription(); }
    /** Vendor: UiObject.stateDesc() */
    public String stateDesc() { return getStateDescription(); }
    /** Vendor: UiObject.tooltipText() */
    public String tooltipText() { return getTooltipText(); }
    /** Vendor: UiObject.accessibilityFocused() */
    public boolean accessibilityFocused() {
        return nodeInfo.isAccessibilityFocused();
    }
    /** Vendor: UiObject.canOpenPopup() */
    public boolean canOpenPopup() {
        return nodeInfo.canOpenPopup();
    }
    /** Vendor: UiObject.heading() */
    public boolean heading() {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            return nodeInfo.isHeading();
        }
        return false;
    }
    /** Vendor: UiObject.importantForAccessibility() */
    public boolean importantForAccessibility() {
        return nodeInfo.isImportantForAccessibility();
    }
    /** Vendor: UiObject.showingHintText() */
    public boolean showingHintText() {
        return nodeInfo.isShowingHintText();
    }
    /** Vendor: UiObject.screenReaderFocusable() */
    public boolean screenReaderFocusable() {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            return nodeInfo.isScreenReaderFocusable();
        }
        return false;
    }
    /** Vendor: UiObject.textSelectable() */
    public boolean textSelectable() {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            return nodeInfo.isTextSelectable();
        }
        return false;
    }
    /** Vendor: UiObject.textEntryKey() */
    public boolean textEntryKey() {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            return nodeInfo.isTextEntryKey();
        }
        return false;
    }
    /** Vendor: UiObject.canScrollDown() */
    public boolean canScrollDown() { return canScrollForward(); }
    /** Vendor: UiObject.canScrollUp() */
    public boolean canScrollUp() { return canScrollBackward(); }
    /** Vendor: UiObject.canScrollLeft() */
    public boolean canScrollLeft() { return canScrollBackward(); }
    /** Vendor: UiObject.canScrollRight() */
    public boolean canScrollRight() { return canScrollForward(); }
    /** Vendor: UiObject.drawingOrder() */
    public int drawingOrder() { return nodeInfo.getDrawingOrder(); }
    /** Vendor: UiObject.row() */
    public int row() {
        AccessibilityNodeInfo.CollectionItemInfo item = nodeInfo.getCollectionItemInfo();
        return item != null ? item.getRowIndex() : 0;
    }
    /** Vendor: UiObject.column() */
    public int column() {
        AccessibilityNodeInfo.CollectionItemInfo item = nodeInfo.getCollectionItemInfo();
        return item != null ? item.getColumnIndex() : 0;
    }
    /** Vendor: UiObject.rowCount() */
    public int rowCount() {
        AccessibilityNodeInfo.CollectionInfo info = nodeInfo.getCollectionInfo();
        return info != null ? info.getRowCount() : 0;
    }
    /** Vendor: UiObject.columnCount() */
    public int columnCount() {
        AccessibilityNodeInfo.CollectionInfo info = nodeInfo.getCollectionInfo();
        return info != null ? info.getColumnCount() : 0;
    }
    /** Vendor: UiObject.rowSpan() */
    public int rowSpan() {
        AccessibilityNodeInfo.CollectionItemInfo item = nodeInfo.getCollectionItemInfo();
        return item != null ? item.getRowSpan() : 0;
    }
    /** Vendor: UiObject.columnSpan() */
    public int columnSpan() {
        AccessibilityNodeInfo.CollectionItemInfo item = nodeInfo.getCollectionItemInfo();
        return item != null ? item.getColumnSpan() : 0;
    }

    // ============ Vendor findBy* 系列 (返回 UiNodeCollection) ============

    // --- Text ---
    public UiNodeCollection findByText(String s) { return findAll(n -> s.equals(n.text())); }
    public UiNodeCollection findByTextContains(String s) { return findAll(n -> n.text() != null && n.text().contains(s)); }
    public UiNodeCollection findByTextEndsWith(String s) { return findAll(n -> n.text() != null && n.text().endsWith(s)); }
    public UiNodeCollection findByTextMatches(String s) { return findAll(n -> n.text() != null && n.text().matches(s)); }
    public UiNodeCollection findByTextStartsWith(String s) { return findAll(n -> n.text() != null && n.text().startsWith(s)); }

    // --- Desc ---
    public UiNodeCollection findByDesc(String s) { return findAll(n -> s.equals(n.desc())); }
    public UiNodeCollection findByDescContains(String s) { return findAll(n -> n.desc() != null && n.desc().contains(s)); }
    public UiNodeCollection findByDescEndsWith(String s) { return findAll(n -> n.desc() != null && n.desc().endsWith(s)); }
    public UiNodeCollection findByDescMatches(String s) { return findAll(n -> n.desc() != null && n.desc().matches(s)); }
    public UiNodeCollection findByDescStartsWith(String s) { return findAll(n -> n.desc() != null && n.desc().startsWith(s)); }

    // --- Id ---
    public UiNodeCollection findById(String s) { return findAll(n -> s.equals(n.id())); }
    public UiNodeCollection findByIdContains(String s) { return findAll(n -> n.id() != null && n.id().contains(s)); }
    public UiNodeCollection findByIdEndsWith(String s) { return findAll(n -> n.id() != null && n.id().endsWith(s)); }
    public UiNodeCollection findByIdMatches(String s) { return findAll(n -> n.id() != null && n.id().matches(s)); }
    public UiNodeCollection findByIdStartsWith(String s) { return findAll(n -> n.id() != null && n.id().startsWith(s)); }

    // --- ClassName ---
    public UiNodeCollection findByClassName(String s) { return findAll(n -> s.equals(n.className())); }
    public UiNodeCollection findByClassNameContains(String s) { return findAll(n -> n.className() != null && n.className().contains(s)); }
    public UiNodeCollection findByClassNameEndsWith(String s) { return findAll(n -> n.className() != null && n.className().endsWith(s)); }
    public UiNodeCollection findByClassNameMatches(String s) { return findAll(n -> n.className() != null && n.className().matches(s)); }
    public UiNodeCollection findByClassNameStartsWith(String s) { return findAll(n -> n.className() != null && n.className().startsWith(s)); }

    // --- PackageName ---
    public UiNodeCollection findByPackageName(String s) { return findAll(n -> s.equals(n.getPackageName())); }
    public UiNodeCollection findByPackageNameContains(String s) { return findAll(n -> n.getPackageName() != null && n.getPackageName().contains(s)); }
    public UiNodeCollection findByPackageNameEndsWith(String s) { return findAll(n -> n.getPackageName() != null && n.getPackageName().endsWith(s)); }
    public UiNodeCollection findByPackageNameMatches(String s) { return findAll(n -> n.getPackageName() != null && n.getPackageName().matches(s)); }
    public UiNodeCollection findByPackageNameStartsWith(String s) { return findAll(n -> n.getPackageName() != null && n.getPackageName().startsWith(s)); }

    // --- CombineFilter ---
    public UiNodeCollection findByCombine(CombineFilter f) { return findAll(f); }

    // --- Bounds ---
    public UiNodeCollection findByBounds(int l, int t, int r, int b) {
        return findAll(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left == l && rc.top == t && rc.right == r && rc.bottom == b; });
    }
    public UiNodeCollection findByBoundsContains(int l, int t, int r, int b) {
        return findAll(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left <= l && rc.top <= t && rc.right >= r && rc.bottom >= b; });
    }
    public UiNodeCollection findByBoundsInside(int l, int t, int r, int b) {
        return findAll(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left >= l && rc.top >= t && rc.right <= r && rc.bottom <= b; });
    }

    // ============ Vendor findOneBy* 系列 (返回第一个匹配) ============

    // --- Text ---
    public UiNode findOneByText(String s) { return findOneByCombine(n -> s.equals(n.text())); }
    public UiNode findOneByTextContains(String s) { return findOneByCombine(n -> n.text() != null && n.text().contains(s)); }
    public UiNode findOneByTextEndsWith(String s) { return findOneByCombine(n -> n.text() != null && n.text().endsWith(s)); }
    public UiNode findOneByTextMatches(String s) { return findOneByCombine(n -> n.text() != null && n.text().matches(s)); }
    public UiNode findOneByTextStartsWith(String s) { return findOneByCombine(n -> n.text() != null && n.text().startsWith(s)); }

    // --- Desc ---
    public UiNode findOneByDesc(String s) { return findOneByCombine(n -> s.equals(n.desc())); }
    public UiNode findOneByDescContains(String s) { return findOneByCombine(n -> n.desc() != null && n.desc().contains(s)); }
    public UiNode findOneByDescEndsWith(String s) { return findOneByCombine(n -> n.desc() != null && n.desc().endsWith(s)); }
    public UiNode findOneByDescMatches(String s) { return findOneByCombine(n -> n.desc() != null && n.desc().matches(s)); }
    public UiNode findOneByDescStartsWith(String s) { return findOneByCombine(n -> n.desc() != null && n.desc().startsWith(s)); }

    // --- Id ---
    public UiNode findOneById(String s) { return findOneByCombine(n -> s.equals(n.id())); }
    public UiNode findOneByIdContains(String s) { return findOneByCombine(n -> n.id() != null && n.id().contains(s)); }
    public UiNode findOneByIdEndsWith(String s) { return findOneByCombine(n -> n.id() != null && n.id().endsWith(s)); }
    public UiNode findOneByIdMatches(String s) { return findOneByCombine(n -> n.id() != null && n.id().matches(s)); }
    public UiNode findOneByIdStartsWith(String s) { return findOneByCombine(n -> n.id() != null && n.id().startsWith(s)); }

    // --- ClassName ---
    public UiNode findOneByClassName(String s) { return findOneByCombine(n -> s.equals(n.className())); }
    public UiNode findOneByClassNameContains(String s) { return findOneByCombine(n -> n.className() != null && n.className().contains(s)); }
    public UiNode findOneByClassNameEndsWith(String s) { return findOneByCombine(n -> n.className() != null && n.className().endsWith(s)); }
    public UiNode findOneByClassNameMatches(String s) { return findOneByCombine(n -> n.className() != null && n.className().matches(s)); }
    public UiNode findOneByClassNameStartsWith(String s) { return findOneByCombine(n -> n.className() != null && n.className().startsWith(s)); }

    // --- PackageName ---
    public UiNode findOneByPackageName(String s) { return findOneByCombine(n -> s.equals(n.getPackageName())); }
    public UiNode findOneByPackageNameContains(String s) { return findOneByCombine(n -> n.getPackageName() != null && n.getPackageName().contains(s)); }
    public UiNode findOneByPackageNameEndsWith(String s) { return findOneByCombine(n -> n.getPackageName() != null && n.getPackageName().endsWith(s)); }
    public UiNode findOneByPackageNameMatches(String s) { return findOneByCombine(n -> n.getPackageName() != null && n.getPackageName().matches(s)); }
    public UiNode findOneByPackageNameStartsWith(String s) { return findOneByCombine(n -> n.getPackageName() != null && n.getPackageName().startsWith(s)); }

    // --- Bounds ---
    public UiNode findOneByBounds(int l, int t, int r, int b) {
        return findOneByCombine(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left == l && rc.top == t && rc.right == r && rc.bottom == b; });
    }
    public UiNode findOneByBoundsContains(int l, int t, int r, int b) {
        return findOneByCombine(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left <= l && rc.top <= t && rc.right >= r && rc.bottom >= b; });
    }
    public UiNode findOneByBoundsInside(int l, int t, int r, int b) {
        return findOneByCombine(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left >= l && rc.top >= t && rc.right <= r && rc.bottom <= b; });
    }
    public UiNode findOneByPointContains(float x, float y) {
        return findOneByCombine(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.contains((int) x, (int) y); });
    }

    // ============ Vendor findLastBy* 系列 (返回最后一个匹配) ============

    private UiNode findLast(NodeFilter filter) {
        List<UiNode> all = findAllByCombine(filter);
        return all.isEmpty() ? null : all.get(all.size() - 1);
    }

    // --- Text ---
    public UiNode findLastByText(String s) { return findLast(n -> s.equals(n.text())); }
    public UiNode findLastByTextContains(String s) { return findLast(n -> n.text() != null && n.text().contains(s)); }
    public UiNode findLastByTextEndsWith(String s) { return findLast(n -> n.text() != null && n.text().endsWith(s)); }
    public UiNode findLastByTextMatches(String s) { return findLast(n -> n.text() != null && n.text().matches(s)); }
    public UiNode findLastByTextStartsWith(String s) { return findLast(n -> n.text() != null && n.text().startsWith(s)); }

    // --- Desc ---
    public UiNode findLastByDesc(String s) { return findLast(n -> s.equals(n.desc())); }
    public UiNode findLastByDescContains(String s) { return findLast(n -> n.desc() != null && n.desc().contains(s)); }
    public UiNode findLastByDescEndsWith(String s) { return findLast(n -> n.desc() != null && n.desc().endsWith(s)); }
    public UiNode findLastByDescMatches(String s) { return findLast(n -> n.desc() != null && n.desc().matches(s)); }
    public UiNode findLastByDescStartsWith(String s) { return findLast(n -> n.desc() != null && n.desc().startsWith(s)); }

    // --- Id ---
    public UiNode findLastById(String s) { return findLast(n -> s.equals(n.id())); }
    public UiNode findLastByIdContains(String s) { return findLast(n -> n.id() != null && n.id().contains(s)); }
    public UiNode findLastByIdEndsWith(String s) { return findLast(n -> n.id() != null && n.id().endsWith(s)); }
    public UiNode findLastByIdMatches(String s) { return findLast(n -> n.id() != null && n.id().matches(s)); }
    public UiNode findLastByIdStartsWith(String s) { return findLast(n -> n.id() != null && n.id().startsWith(s)); }

    // --- ClassName ---
    public UiNode findLastByClassName(String s) { return findLast(n -> s.equals(n.className())); }
    public UiNode findLastByClassNameContains(String s) { return findLast(n -> n.className() != null && n.className().contains(s)); }
    public UiNode findLastByClassNameEndsWith(String s) { return findLast(n -> n.className() != null && n.className().endsWith(s)); }
    public UiNode findLastByClassNameMatches(String s) { return findLast(n -> n.className() != null && n.className().matches(s)); }
    public UiNode findLastByClassNameStartsWith(String s) { return findLast(n -> n.className() != null && n.className().startsWith(s)); }

    // --- CombineFilter ---
    public UiNode findLastByCombine(CombineFilter f) { return findLast(f); }

    // --- Bounds ---
    public UiNode findLastByBounds(int l, int t, int r, int b) {
        return findLast(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left == l && rc.top == t && rc.right == r && rc.bottom == b; });
    }
    public UiNode findLastByBoundsContains(int l, int t, int r, int b) {
        return findLast(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left <= l && rc.top <= t && rc.right >= r && rc.bottom >= b; });
    }
    public UiNode findLastByBoundsInside(int l, int t, int r, int b) {
        return findLast(n -> { android.graphics.Rect rc = n.boundsInScreen(); return rc.left >= l && rc.top >= t && rc.right <= r && rc.bottom <= b; });
    }

    // ============ Vendor findBy* 辅助: findAll → UiNodeCollection ============

    private UiNodeCollection findAll(NodeFilter filter) {
        return new UiNodeCollection(findAllByCombine(filter));
    }

    // 向后兼容: 返回 List<UiNode> 的版本 (引擎类使用)
    public List<UiNode> findAllByClassName(String className) {
        return findAllByCombine(n -> className.equals(n.className()));
    }

    // ============ Vendor 查找方法 (CombineFilter 数据类版本) ============

    /**
     * Vendor: UiObject.findOneByCombineLoop(CombineFilter)
     * 带重试的查找，最多等待 10 次
     */
    public UiNode findOneByCombineLoop(NodeFilter filter) {
        for (int i = 0; i < 10; i++) {
            UiNode found = findOneByCombine(filter);
            if (found != null) return found;
            sleep(SCROLL_DELAY);
            refresh();
        }
        return null;
    }

    /**
     * Vendor: UiObject.findParentUtilCombine(CombineFilter)
     * 别名，与 findParentUntil 相同
     */
    public UiNode findParentUtilCombine(NodeFilter filter) {
        return findParentUntil(filter);
    }

    /**
     * Vendor: UiObject.findParentByCombine(CombineFilter, Integer)
     * 在子节点中查找匹配项，然后向上 upLevel 层找父节点
     */
    public UiNode findParentByCombine(NodeFilter childFilter, Integer upLevel) {
        UiNode child = findOneByCombine(childFilter);
        if (child == null || upLevel == null) return child;
        UiNode current = child;
        for (int i = 0; i < upLevel; i++) {
            UiNode p = current.parent();
            if (p == null) break;
            current = p;
        }
        return current;
    }

    /**
     * Vendor: UiObject.findOneByOperateOr(CombineFiltersWithOr)
     * OR 组合查找 - 任一子过滤器匹配即返回
     */
    public UiNode findOneByOperateOr(NodeFilter... filters) {
        for (NodeFilter filter : filters) {
            UiNode found = findOneByCombine(filter);
            if (found != null) return found;
        }
        return null;
    }

    /**
     * Vendor: UiObject.findOneByOperateOrLoop(CombineFiltersWithOr)
     * 带重试的 OR 查找
     */
    public UiNode findOneByOperateOrLoop(NodeFilter... filters) {
        for (int i = 0; i < 10; i++) {
            for (NodeFilter filter : filters) {
                UiNode found = findOneByCombine(filter);
                if (found != null) return found;
            }
            sleep(SCROLL_DELAY);
            refresh();
        }
        return null;
    }

    /**
     * Vendor: UiObject.scrollForwardUtil(z.a)
     * 向下滚动查找 (NodeFilter 版本别名)
     */
    public UiNode scrollForwardUtil(NodeFilter filter) {
        return scrollForwardUntil(filter);
    }

    /**
     * Vendor: UiObject.scrollBackwardUtil(z.a)
     */
    public UiNode scrollBackwardUtil(NodeFilter filter) {
        return scrollBackwardUntil(filter);
    }

    /**
     * 刷新节点信息
     * 基于逆向: UiObject.refresh()
     */
    public boolean refresh() {
        if (nodeInfo != null) {
            return nodeInfo.refresh();
        }
        return false;
    }

    public void recycle() {
        nodeInfo.recycle();
    }

    // ============ 工具方法 ============

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return "UiNode{" +
            "text='" + getText() + '\'' +
            ", class='" + getClassName() + '\'' +
            ", id='" + getViewIdResourceName() + '\'' +
            ", clickable=" + isClickable() +
            ", checked=" + isChecked() +
            ", scrollable=" + isScrollable() +
            '}';
    }
}
