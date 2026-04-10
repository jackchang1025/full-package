package com.guard.wallet.entity;

import com.guard.wallet.core.AppUtils;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.ActionValueCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.gkd.GkdNodeFinder;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.uisearch.MultiScrollCondition;
import com.guard.wallet.uisearch.SingleScrollCondition;
import com.guard.wallet.utils.SystemHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * UI 节点对象 — AccessibilityNodeInfoCompat 的封装。
 * 参考 Android UIAutomator UiObject2 + vendor CFR 源码 (8456行) 翻译。
 *
 * 核心结构:
 * - source: AtomicReference<AccessibilityNodeInfoCompat> — 包装的无障碍节点
 * - depth/indexInParent: 树遍历深度和索引
 * - cacheProperties: 缓存的属性（text/id/desc）
 * - 属性访问器: 委托给 source.get() 的对应方法
 * - findBy/findOneBy/findLastBy: BFS 遍历子树 + 条件过滤
 * - performAction: 委托给 source.get().performAction()
 */
public class UiObject implements Serializable {
    private static final String TAG = "UiObject";
    private final HashMap<String, String> cacheProperties;
    private final int depth;
    private final int indexInParent;
    private boolean rootRecycle = false;
    private final AtomicReference<AccessibilityNodeInfoCompat> source;
    private String uniqueId;

    // ═══════ 构造器 ═══════

    /** No-arg constructor — backward compat stub for service/delegate code */
    public UiObject() {
        this.cacheProperties = new LinkedHashMap<>();
        this.source = new AtomicReference<>(null);
        this.depth = 0;
        this.indexInParent = 0;
    }

    public UiObject(AccessibilityNodeInfoCompat node, int depth, int indexInParent) {
        this.cacheProperties = new LinkedHashMap<>();
        this.source = new AtomicReference<>(node);
        this.depth = depth;
        this.indexInParent = indexInParent;
    }

    public UiObject(AccessibilityNodeInfo node, int depth, int indexInParent) {
        this.cacheProperties = new LinkedHashMap<>();
        this.source = new AtomicReference<>(AccessibilityNodeInfoCompat.wrap(node));
        this.depth = depth;
        this.indexInParent = indexInParent;
    }

    public UiObject(AccessibilityNodeInfo node, int depth, int indexInParent, boolean cacheProps) {
        this.cacheProperties = new LinkedHashMap<>();
        this.source = new AtomicReference<>(AccessibilityNodeInfoCompat.wrap(node));
        this.depth = depth;
        this.indexInParent = indexInParent;
        if (cacheProps) {
            String t = text();
            if (!AppUtils.B(t)) cacheProperties.put("text", t);
            String i = id();
            if (!AppUtils.B(i)) {
                Log.d(TAG, "cache node id:" + i);
                cacheProperties.put("id", i);
            }
            String d = desc();
            if (!AppUtils.B(d)) cacheProperties.put("desc", d);
        }
    }

    // ═══════ 工厂方法 ═══════

    public static UiObject createRoot(AccessibilityNodeInfo node) {
        if (node == null) return null;
        try {
            return new UiObject(node, 0, -1);
        } catch (Exception e) {
            AppUtils.s("UiObject-createRoot:", e);
            return null;
        }
    }

    public static UiObject createRoot(AccessibilityNodeInfo node, boolean cacheProps) {
        if (node == null) {
            Log.d(TAG, "createRoot source is null");
            return null;
        }
        try {
            return new UiObject(node, 0, -1, cacheProps);
        } catch (Exception e) {
            AppUtils.s("UiObject-createRoot:", e);
            return null;
        }
    }

    // ═══════ 内部辅助 ═══════

    /** 安全获取 source 节点 */
    private AccessibilityNodeInfoCompat getSource() {
        AtomicReference<AccessibilityNodeInfoCompat> ref = this.source;
        if (ref == null) return null;
        return ref.get();
    }

    /** 安全获取 boolean 属性的通用模板 */
    private boolean getBoolProp(String name, BoolPropGetter getter) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return false;
            return getter.get(s);
        } catch (Exception e) {
            AppUtils.s("UiObject-" + name + ":", e);
            return false;
        }
    }

    /** 安全获取 String 属性的通用模板 */
    private String getStringProp(String name, StringPropGetter getter) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return null;
            CharSequence cs = getter.get(s);
            return cs != null ? cs.toString() : null;
        } catch (Exception e) {
            AppUtils.s("UiObject-" + name + ":", e);
            return null;
        }
    }

    /** 安全获取 int 属性的通用模板 */
    private int getIntProp(String name, int defaultVal, IntPropGetter getter) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return defaultVal;
            return getter.get(s);
        } catch (Exception e) {
            AppUtils.s("UiObject-" + name + ":", e);
            return defaultVal;
        }
    }

    @FunctionalInterface
    private interface BoolPropGetter { boolean get(AccessibilityNodeInfoCompat s); }
    @FunctionalInterface
    private interface StringPropGetter { CharSequence get(AccessibilityNodeInfoCompat s); }
    @FunctionalInterface
    private interface IntPropGetter { int get(AccessibilityNodeInfoCompat s); }

    // ═══════ 基础属性访问器 ═══════

    public String text() {
        // 优先使用缓存
        String cached = cacheProperties.get("text");
        if (cached != null) return cached;
        return getStringProp("text", AccessibilityNodeInfoCompat::getText);
    }

    public CharSequence getText() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            return s != null ? s.getText() : null;
        } catch (Exception e) {
            AppUtils.s("UiObject-getText:", e);
            return null;
        }
    }

    public String id() {
        String cached = cacheProperties.get("id");
        if (cached != null) return cached;
        return getStringProp("id", s -> s.getViewIdResourceName());
    }

    public String desc() {
        String cached = cacheProperties.get("desc");
        if (cached != null) return cached;
        return getStringProp("desc", AccessibilityNodeInfoCompat::getContentDescription);
    }

    public String className() {
        return getStringProp("className", AccessibilityNodeInfoCompat::getClassName);
    }

    public String packageName() {
        return getStringProp("packageName", AccessibilityNodeInfoCompat::getPackageName);
    }

    public String hintText() {
        return getStringProp("hintText", AccessibilityNodeInfoCompat::getHintText);
    }

    public String paneTitle() {
        return getStringProp("paneTitle", AccessibilityNodeInfoCompat::getPaneTitle);
    }

    public String roleDesc() {
        return getStringProp("roleDesc", AccessibilityNodeInfoCompat::getRoleDescription);
    }

    public String stateDesc() {
        return getStringProp("stateDesc", AccessibilityNodeInfoCompat::getStateDescription);
    }

    public String tooltipText() {
        return getStringProp("tooltipText", AccessibilityNodeInfoCompat::getTooltipText);
    }

    public String uniqueId() { return this.uniqueId; }
    public void setUniqueId(String id) { this.uniqueId = id; }
    public int getDepth() { return depth; }
    public int getIndexInParent() { return indexInParent; }
    public boolean isRootRecycle() { return rootRecycle; }

    // ═══════ Boolean 属性 (24个, BooleanFilter 需要) ═══════

    public boolean clickable()     { return getBoolProp("clickable", AccessibilityNodeInfoCompat::isClickable); }
    public boolean longClickable() { return getBoolProp("longClickable", AccessibilityNodeInfoCompat::isLongClickable); }
    public boolean checkable()     { return getBoolProp("checkable", AccessibilityNodeInfoCompat::isCheckable); }
    public boolean checked()       { return getBoolProp("checked", AccessibilityNodeInfoCompat::isChecked); }
    public boolean focusable()     { return getBoolProp("focusable", AccessibilityNodeInfoCompat::isFocusable); }
    public boolean focused()       { return getBoolProp("focused", AccessibilityNodeInfoCompat::isFocused); }
    public boolean enabled()       { return getBoolProp("enabled", AccessibilityNodeInfoCompat::isEnabled); }
    public boolean selected()      { return getBoolProp("selected", AccessibilityNodeInfoCompat::isSelected); }
    public boolean scrollable()    { return getBoolProp("scrollable", AccessibilityNodeInfoCompat::isScrollable); }
    public boolean password()      { return getBoolProp("password", AccessibilityNodeInfoCompat::isPassword); }
    public boolean editable()      { return getBoolProp("editable", AccessibilityNodeInfoCompat::isEditable); }
    public boolean visibleToUser() { return getBoolProp("visibleToUser", AccessibilityNodeInfoCompat::isVisibleToUser); }
    public boolean dismissable()   { return getBoolProp("dismissable", AccessibilityNodeInfoCompat::isDismissable); }
    public boolean contentInvalid(){ return getBoolProp("contentInvalid", AccessibilityNodeInfoCompat::isContentInvalid); }
    public boolean contextClickable() { return getBoolProp("contextClickable", AccessibilityNodeInfoCompat::isContextClickable); }
    public boolean multiLine()     { return getBoolProp("multiLine", AccessibilityNodeInfoCompat::isMultiLine); }
    public boolean importantForAccessibility() { return getBoolProp("importantForAccessibility", AccessibilityNodeInfoCompat::isImportantForAccessibility); }
    public boolean screenReaderFocusable() { return getBoolProp("screenReaderFocusable", AccessibilityNodeInfoCompat::isScreenReaderFocusable); }
    public boolean heading()       { return getBoolProp("heading", AccessibilityNodeInfoCompat::isHeading); }
    public boolean canOpenPopup()  { return getBoolProp("canOpenPopup", s -> s.canOpenPopup()); }
    public boolean showingHintText() { return getBoolProp("showingHintText", AccessibilityNodeInfoCompat::isShowingHintText); }
    public boolean textSelectable(){ return getBoolProp("textSelectable", AccessibilityNodeInfoCompat::isTextSelectable); }
    public boolean textEntryKey()  { return getBoolProp("textEntryKey", AccessibilityNodeInfoCompat::isTextEntryKey); }
    public boolean accessibilityFocused() { return getBoolProp("accessibilityFocused", AccessibilityNodeInfoCompat::isAccessibilityFocused); }

    // BooleanFilter 兼容别名 (isXxx 风格)
    public boolean isClickable()     { return clickable(); }
    public boolean isLongClickable() { return longClickable(); }
    public boolean isCheckable()     { return checkable(); }
    public boolean isChecked()       { return checked(); }
    public boolean isFocusable()     { return focusable(); }
    public boolean isFocused()       { return focused(); }
    public boolean isEnabled()       { return enabled(); }
    public boolean isSelected()      { return selected(); }
    public boolean isScrollable()    { return scrollable(); }
    public boolean isPassword()      { return password(); }
    public boolean isEditable()      { return editable(); }
    public boolean isVisibleToUser() { return visibleToUser(); }
    public boolean isDismissable()   { return dismissable(); }
    public boolean isContentInvalid() { return contentInvalid(); }
    public boolean isContextClickable() { return contextClickable(); }
    public boolean isMultiLine()     { return multiLine(); }
    public boolean isImportantForAccessibility() { return importantForAccessibility(); }
    public boolean isScreenReaderFocusable() { return screenReaderFocusable(); }
    public boolean isHeading()       { return heading(); }
    public boolean isShowingHintText() { return showingHintText(); }
    public boolean isTextSelectable() { return textSelectable(); }
    public boolean isTextEntryKey()  { return textEntryKey(); }
    public boolean isAccessibilityFocused() { return accessibilityFocused(); }

    // ═══════ Rect / Point 属性 ═══════

    public Rect boundsInScreen() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return null;
            Rect r = new Rect();
            s.getBoundsInScreen(r);
            return r;
        } catch (Exception e) {
            AppUtils.s("UiObject-boundsInScreen:", e);
            return null;
        }
    }

    public Rect boundsInParent() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return null;
            Rect r = new Rect();
            s.getBoundsInParent(r);
            return r;
        } catch (Exception e) {
            AppUtils.s("UiObject-boundsInParent:", e);
            return null;
        }
    }

    public Rect boundsInWindow() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return null;
            // API 34+
            Rect r = new Rect();
            s.getBoundsInScreen(r); // fallback to screen bounds
            return r;
        } catch (Exception e) {
            AppUtils.s("UiObject-boundsInWindow:", e);
            return null;
        }
    }

    public Point centerInScreen() {
        try {
            Rect r = boundsInScreen();
            if (r == null) return null;
            return new Point(r.centerX(), r.centerY());
        } catch (Exception e) {
            return null;
        }
    }

    public Point centerInParent() {
        try {
            Rect r = boundsInParent();
            if (r == null) return null;
            return new Point(r.centerX(), r.centerY());
        } catch (Exception e) {
            return null;
        }
    }

    // ═══════ Int 属性 ═══════

    public int childCount() { return getIntProp("childCount", 0, AccessibilityNodeInfoCompat::getChildCount); }
    public int drawingOrder() { return getIntProp("drawingOrder", 0, AccessibilityNodeInfoCompat::getDrawingOrder); }

    public int column() {
        return getIntProp("column", -1, s -> {
            AccessibilityNodeInfoCompat.CollectionItemInfoCompat ci = s.getCollectionItemInfo();
            return ci != null ? ci.getColumnIndex() : -1;
        });
    }

    public int columnCount() {
        return getIntProp("columnCount", -1, s -> {
            AccessibilityNodeInfoCompat.CollectionInfoCompat ci = s.getCollectionInfo();
            return ci != null ? ci.getColumnCount() : -1;
        });
    }

    public int columnSpan() {
        return getIntProp("columnSpan", -1, s -> {
            AccessibilityNodeInfoCompat.CollectionItemInfoCompat ci = s.getCollectionItemInfo();
            return ci != null ? ci.getColumnSpan() : -1;
        });
    }

    public int row() {
        return getIntProp("row", -1, s -> {
            AccessibilityNodeInfoCompat.CollectionItemInfoCompat ci = s.getCollectionItemInfo();
            return ci != null ? ci.getRowIndex() : -1;
        });
    }

    public int rowCount() {
        return getIntProp("rowCount", -1, s -> {
            AccessibilityNodeInfoCompat.CollectionInfoCompat ci = s.getCollectionInfo();
            return ci != null ? ci.getRowCount() : -1;
        });
    }

    public int rowSpan() {
        return getIntProp("rowSpan", -1, s -> {
            AccessibilityNodeInfoCompat.CollectionItemInfoCompat ci = s.getCollectionItemInfo();
            return ci != null ? ci.getRowSpan() : -1;
        });
    }

    public int regionCount() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return 0;
            AccessibilityNodeInfo unwrapped = s.unwrap();
            if (unwrapped == null) return 0;
            // touchDelegateInfo available API 29+
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                AccessibilityNodeInfo.TouchDelegateInfo info = unwrapped.getTouchDelegateInfo();
                return info != null ? info.getRegionCount() : 0;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    // ═══════ 树遍历 ═══════

    public UiObject child(int index) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null || index < 0 || index >= childCount()) return null;
            AccessibilityNodeInfoCompat child = s.getChild(index);
            if (child == null) return null;
            return new UiObject(child, this.depth + 1, index);
        } catch (Exception e) {
            AppUtils.s("UiObject-child:", e);
            return null;
        }
    }

    public UiObject parent() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return null;
            AccessibilityNodeInfoCompat p = s.getParent();
            if (p == null) return null;
            return new UiObject(p, Math.max(0, this.depth - 1), -1);
        } catch (Exception e) {
            AppUtils.s("UiObject-parent:", e);
            return null;
        }
    }

    public AccessibilityNodeInfoCompat getParent() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            return s != null ? s.getParent() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public UiObject currentFocusedNode() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return null;
            AccessibilityNodeInfoCompat focused = s.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
            if (focused == null) return null;
            return new UiObject(focused, 0, -1);
        } catch (Exception e) {
            AppUtils.s("UiObject-currentFocusedNode:", e);
            return null;
        }
    }

    // ═══════ 动作执行 ═══════

    public boolean performAction(int action) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return false;
            return s.performAction(action);
        } catch (IllegalStateException e) {
            AppUtils.s("UiObject-performAction:", e);
            return false;
        }
    }

    public boolean performAction(int action, Bundle args) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return false;
            return s.performAction(action, args);
        } catch (IllegalStateException e) {
            AppUtils.s("UiObject-performAction:", e);
            return false;
        }
    }

    public boolean click() {
        try {
            if (clickable() && performAction(AccessibilityNodeInfoCompat.ACTION_CLICK)) return true;
            Point center = centerInScreen();
            if (center != null) {
                return SystemHelper.s((int) center.getX(), (int) center.getY());
            }
            return false;
        } catch (Exception e) {
            AppUtils.s("UiObject-click:", e);
            return false;
        }
    }

    public boolean clickPosition(float xRatio, float yRatio) {
        if (xRatio > 1.0f || xRatio <= 0.0f) xRatio = 0.5f;
        if (yRatio > 1.0f || yRatio <= 0.0f) yRatio = 0.5f;
        try {
            Rect b = boundsInScreen();
            if (b == null) return false;
            int x = b.left + (int) (b.width() * xRatio);
            int y = b.top + (int) (b.height() * yRatio);
            return SystemHelper.s(x, y);
        } catch (Exception e) {
            AppUtils.s("UiObject-clickPosition:", e);
            return false;
        }
    }

    public boolean longClick() { return performAction(AccessibilityNodeInfoCompat.ACTION_LONG_CLICK); }
    public boolean focus() { return performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS); }
    public boolean clearFocus() { return performAction(AccessibilityNodeInfoCompat.ACTION_CLEAR_FOCUS); }
    public boolean accessibilityFocus() { return performAction(AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS); }
    public boolean clearAccessibilityFocus() { return performAction(AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS); }
    public boolean select() { return performAction(AccessibilityNodeInfoCompat.ACTION_SELECT); }
    public boolean copy() { return performAction(AccessibilityNodeInfoCompat.ACTION_COPY); }
    public boolean cut() { return performAction(AccessibilityNodeInfoCompat.ACTION_CUT); }
    public boolean paste() { return performAction(AccessibilityNodeInfoCompat.ACTION_PASTE); }
    public boolean dismiss() { return performAction(AccessibilityNodeInfoCompat.ACTION_DISMISS); }
    public boolean collapse() { return performAction(524288); } // ACTION_COLLAPSE
    public boolean expand() { return performAction(262144); }   // ACTION_EXPAND
    public boolean show() { return performAction(16908354); }   // ACTION_SHOW_ON_SCREEN
    public boolean contextClick() { return performAction(16908348); } // ACTION_CONTEXT_CLICK
    public boolean enter() { return click(); }

    public boolean setText(String text) {
        try {
            Bundle args = new Bundle();
            args.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            return performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, args);
        } catch (Exception e) {
            AppUtils.s("UiObject-setText:", e);
            return false;
        }
    }

    // ═══════ 滚动能力检测 ═══════

    public boolean canScrollForward() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return false;
            List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = s.getActionList();
            for (AccessibilityNodeInfoCompat.AccessibilityActionCompat a : actions) {
                if (a.getId() == AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canScrollBackward() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return false;
            List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = s.getActionList();
            for (AccessibilityNodeInfoCompat.AccessibilityActionCompat a : actions) {
                if (a.getId() == AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canScrollDown() { return canScrollForward(); }
    public boolean canScrollUp() { return canScrollBackward(); }
    public boolean canScrollRight() { return canScrollForward(); }
    public boolean canScrollLeft() { return canScrollBackward(); }

    // ═══════ 滚动操作 ═══════

    public boolean scrollForward() {
        try {
            if (!canScrollForward()) return false;
            if (!performAction(AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD)) return false;
            refresh();
            return true;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollForward:", e);
            return false;
        }
    }

    public boolean scrollBackward() {
        try {
            if (!canScrollBackward()) return false;
            if (!performAction(AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD)) return false;
            refresh();
            return true;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollBackward:", e);
            return false;
        }
    }

    public boolean scrollDown() { return scrollForward(); }
    public boolean scrollUp() { return scrollBackward(); }
    public boolean scrollRight() { return scrollForward(); }
    public boolean scrollLeft() { return scrollBackward(); }

    public boolean scrollForwardByGesture() {
        try {
            if (!canScrollForward()) return false;
            if (!simulationScrollForward()) return false;
            refresh();
            return true;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollForwardByGesture:", e);
            return false;
        }
    }

    public boolean scrollBackwardByGesture() {
        try {
            if (!canScrollBackward()) return false;
            if (!simulationScrollBackward()) return false;
            refresh();
            return true;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollBackwardByGesture:", e);
            return false;
        }
    }

    public void scrollForwardEnd() {
        try {
            while (scrollForward()) {
                SystemHelper.T0(1); // sleep 200ms
            }
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollForwardEnd:", e);
        }
    }

    public void scrollBackwardEnd() {
        try {
            while (scrollBackward()) {
                SystemHelper.T0(1);
            }
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollBackwardEnd:", e);
        }
    }

    /** 模拟手势向下滚动 (用于无 ACTION_SCROLL_FORWARD 的场景) */
    public boolean simulationScrollForward() {
        try {
            Rect b = boundsInScreen();
            if (b == null) return false;
            int cx = b.centerX();
            int fromY = b.bottom - b.height() / 4;
            int toY = b.top + b.height() / 4;
            return SystemHelper.s(cx, fromY, cx, toY);
        } catch (Exception e) {
            return false;
        }
    }

    /** 模拟手势向上滚动 */
    public boolean simulationScrollBackward() {
        try {
            Rect b = boundsInScreen();
            if (b == null) return false;
            int cx = b.centerX();
            int fromY = b.top + b.height() / 4;
            int toY = b.bottom - b.height() / 4;
            return SystemHelper.s(cx, fromY, cx, toY);
        } catch (Exception e) {
            return false;
        }
    }

    // ═══════ 节点生命周期 ═══════

    public void recycle() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return;
            if (!MyAccessibilityService.Z(s.unwrap())) {
                s.recycle();
            }
        } catch (Exception e) {
            AppUtils.s("UiObject-recycle:", e);
        }
    }

    public boolean refresh() {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s == null) return false;
            return s.refresh();
        } catch (Exception e) {
            return false;
        }
    }

    // ═══════ BFS 搜索引擎 (参考 k/a.java 的 r/s/t 方法 + Android UIAutomator) ═══════

    /**
     * BFS 遍历子树，按条件过滤，返回所有匹配节点集合。
     * vendor UiGlobalSelector.s() -- 核心搜索实现。
     */
    private UiObjectCollection bfsSearch(NodeMatcher matcher, int maxCount) {
        ArrayList<UiObject> results = new ArrayList<>();
        if (matcher == null) return UiObjectCollection.of(results);
        ConcurrentLinkedQueue<UiObject> queue = new ConcurrentLinkedQueue<>();
        queue.offer(this);
        try {
            while (!queue.isEmpty()) {
                UiObject node = queue.poll();
                if (node == null) continue;

                boolean matched = matcher.matches(node);
                if (matched) {
                    results.add(node);
                    if (results.size() >= maxCount) {
                        return UiObjectCollection.of(results);
                    }
                }

                // 遍历所有子节点，加入队列
                for (int i = 0; i < node.childCount(); i++) {
                    UiObject child = node.child(i);
                    if (child != null) {
                        queue.offer(child);
                    }
                }

                // 不匹配且非 root → 回收
                if (!matched && !node.equals(this)) {
                    node.recycle();
                }
            }
        } catch (Exception e) {
            AppUtils.s("BFS", e);
        }
        return UiObjectCollection.of(results);
    }

    /** BFS 搜索第一个匹配节点 */
    private UiObject bfsSearchOne(NodeMatcher matcher) {
        UiObjectCollection result = bfsSearch(matcher, 1);
        return (result != null && result.size() > 0) ? result.get(0) : null;
    }

    /** BFS 搜索最后一个匹配节点 */
    private UiObject bfsSearchLast(NodeMatcher matcher) {
        UiObjectCollection result = bfsSearch(matcher, Integer.MAX_VALUE);
        return (result != null && result.size() > 0) ? result.get(result.size() - 1) : null;
    }

    @FunctionalInterface
    private interface NodeMatcher {
        boolean matches(UiObject node);
    }

    // ═══════ String 匹配辅助 ═══════

    private static boolean strEquals(String a, String b) { return a != null && a.equals(b); }
    private static boolean strContains(String a, String b) { return a != null && b != null && a.contains(b); }
    private static boolean strStartsWith(String a, String b) { return a != null && b != null && a.startsWith(b); }
    private static boolean strEndsWith(String a, String b) { return a != null && b != null && a.endsWith(b); }
    private static boolean strMatches(String a, String regex) {
        try { return a != null && regex != null && a.matches(regex); }
        catch (Exception e) { return false; }
    }

    // ═══════ findBy* (返回 UiObjectCollection) ═══════

    // --- Text ---
    public UiObjectCollection findByText(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEquals(n.text(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findByText:", e); return null; }
    }
    public UiObjectCollection findByTextContains(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strContains(n.text(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findByTextContains:", e); return null; }
    }
    public UiObjectCollection findByTextStartsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strStartsWith(n.text(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findByTextStartsWith:", e); return null; }
    }
    public UiObjectCollection findByTextEndsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEndsWith(n.text(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findByTextEndsWith:", e); return null; }
    }
    public UiObjectCollection findByTextMatches(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strMatches(n.text(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findByTextMatches:", e); return null; }
    }

    // --- Desc ---
    public UiObjectCollection findByDesc(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEquals(n.desc(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByDescContains(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strContains(n.desc(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByDescStartsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strStartsWith(n.desc(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByDescEndsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEndsWith(n.desc(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByDescMatches(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strMatches(n.desc(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }

    // --- Id ---
    public UiObjectCollection findById(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEquals(n.id(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findById:", e); return null; }
    }
    public UiObjectCollection findByIdContains(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strContains(n.id(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByIdStartsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strStartsWith(n.id(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByIdEndsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEndsWith(n.id(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByIdMatches(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strMatches(n.id(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }

    // --- ClassName ---
    public UiObjectCollection findByClassName(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEquals(n.className(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByClassNameContains(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strContains(n.className(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByClassNameStartsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strStartsWith(n.className(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByClassNameEndsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEndsWith(n.className(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findByClassNameEndsWith:", e); return null; }
    }
    public UiObjectCollection findByClassNameMatches(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strMatches(n.className(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject-findByClassNameMatches:", e); return null; }
    }

    // --- PackageName ---
    public UiObjectCollection findByPackageName(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEquals(n.packageName(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByPackageNameContains(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strContains(n.packageName(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByPackageNameStartsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strStartsWith(n.packageName(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByPackageNameEndsWith(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strEndsWith(n.packageName(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }
    public UiObjectCollection findByPackageNameMatches(String v) {
        if (AppUtils.B(v)) return null;
        try { return bfsSearch(n -> strMatches(n.packageName(), v), Integer.MAX_VALUE); }
        catch (Exception e) { AppUtils.s("UiObject", e); return null; }
    }

    // --- Bounds ---
    public UiObjectCollection findByBounds(int l, int t, int r, int b) {
        return bfsSearch(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left == l && rect.top == t && rect.right == r && rect.bottom == b; }, Integer.MAX_VALUE);
    }
    public UiObjectCollection findByBoundsContains(int l, int t, int r, int b) {
        return bfsSearch(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left <= l && rect.top <= t && rect.right >= r && rect.bottom >= b; }, Integer.MAX_VALUE);
    }
    public UiObjectCollection findByBoundsInside(int l, int t, int r, int b) {
        return bfsSearch(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left >= l && rect.top >= t && rect.right <= r && rect.bottom <= b; }, Integer.MAX_VALUE);
    }

    // ═══════ findOneBy* (返回单个 UiObject) ═══════

    // --- Text ---
    public UiObject findOneByText(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEquals(n.text(), v)); }
    public UiObject findOneByTextContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strContains(n.text(), v)); }
    public UiObject findOneByTextStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strStartsWith(n.text(), v)); }
    public UiObject findOneByTextEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEndsWith(n.text(), v)); }
    public UiObject findOneByTextMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strMatches(n.text(), v)); }

    // --- Desc ---
    public UiObject findOneByDesc(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEquals(n.desc(), v)); }
    public UiObject findOneByDescContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strContains(n.desc(), v)); }
    public UiObject findOneByDescStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strStartsWith(n.desc(), v)); }
    public UiObject findOneByDescEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEndsWith(n.desc(), v)); }
    public UiObject findOneByDescMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strMatches(n.desc(), v)); }

    // --- Id ---
    public UiObject findOneById(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEquals(n.id(), v)); }
    public UiObject findOneByIdContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strContains(n.id(), v)); }
    public UiObject findOneByIdStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strStartsWith(n.id(), v)); }
    public UiObject findOneByIdEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEndsWith(n.id(), v)); }
    public UiObject findOneByIdMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strMatches(n.id(), v)); }

    // --- ClassName ---
    public UiObject findOneByClassName(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEquals(n.className(), v)); }
    public UiObject findOneByClassNameContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strContains(n.className(), v)); }
    public UiObject findOneByClassNameStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strStartsWith(n.className(), v)); }
    public UiObject findOneByClassNameEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEndsWith(n.className(), v)); }
    public UiObject findOneByClassNameMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strMatches(n.className(), v)); }

    // --- PackageName ---
    public UiObject findOneByPackageName(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEquals(n.packageName(), v)); }
    public UiObject findOneByPackageNameContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strContains(n.packageName(), v)); }
    public UiObject findOneByPackageNameStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strStartsWith(n.packageName(), v)); }
    public UiObject findOneByPackageNameEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strEndsWith(n.packageName(), v)); }
    public UiObject findOneByPackageNameMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchOne(n -> strMatches(n.packageName(), v)); }

    // --- Bounds/Point ---
    public UiObject findOneByBounds(int l, int t, int r, int b) { return bfsSearchOne(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left == l && rect.top == t && rect.right == r && rect.bottom == b; }); }
    public UiObject findOneByBoundsContains(int l, int t, int r, int b) { return bfsSearchOne(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left <= l && rect.top <= t && rect.right >= r && rect.bottom >= b; }); }
    public UiObject findOneByBoundsInside(int l, int t, int r, int b) { return bfsSearchOne(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left >= l && rect.top >= t && rect.right <= r && rect.bottom <= b; }); }
    public UiObject findOneByPointContains(float x, float y) { return bfsSearchOne(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.contains((int) x, (int) y); }); }
    public UiObject findOneByPointContains(int x, int y) { return bfsSearchOne(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.contains(x, y); }); }

    // ═══════ findLastBy* (返回最后匹配 UiObject) ═══════

    // --- Text ---
    public UiObject findLastByText(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEquals(n.text(), v)); }
    public UiObject findLastByTextContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strContains(n.text(), v)); }
    public UiObject findLastByTextStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strStartsWith(n.text(), v)); }
    public UiObject findLastByTextEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEndsWith(n.text(), v)); }
    public UiObject findLastByTextMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strMatches(n.text(), v)); }

    // --- Desc ---
    public UiObject findLastByDesc(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEquals(n.desc(), v)); }
    public UiObject findLastByDescContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strContains(n.desc(), v)); }
    public UiObject findLastByDescStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strStartsWith(n.desc(), v)); }
    public UiObject findLastByDescEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEndsWith(n.desc(), v)); }
    public UiObject findLastByDescMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strMatches(n.desc(), v)); }

    // --- Id ---
    public UiObject findLastById(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEquals(n.id(), v)); }
    public UiObject findLastByIdContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strContains(n.id(), v)); }
    public UiObject findLastByIdStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strStartsWith(n.id(), v)); }
    public UiObject findLastByIdEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEndsWith(n.id(), v)); }
    public UiObject findLastByIdMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strMatches(n.id(), v)); }

    // --- ClassName ---
    public UiObject findLastByClassName(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEquals(n.className(), v)); }
    public UiObject findLastByClassNameContains(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strContains(n.className(), v)); }
    public UiObject findLastByClassNameStartsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strStartsWith(n.className(), v)); }
    public UiObject findLastByClassNameEndsWith(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strEndsWith(n.className(), v)); }
    public UiObject findLastByClassNameMatches(String v) { if (AppUtils.B(v)) return null; return bfsSearchLast(n -> strMatches(n.className(), v)); }

    // --- Bounds ---
    public UiObject findLastByBounds(int l, int t, int r, int b) { return bfsSearchLast(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left == l && rect.top == t && rect.right == r && rect.bottom == b; }); }
    public UiObject findLastByBoundsContains(int l, int t, int r, int b) { return bfsSearchLast(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left <= l && rect.top <= t && rect.right >= r && rect.bottom >= b; }); }
    public UiObject findLastByBoundsInside(int l, int t, int r, int b) { return bfsSearchLast(n -> { Rect rect = n.boundsInScreen(); return rect != null && rect.left >= l && rect.top >= t && rect.right <= r && rect.bottom <= b; }); }

    // ═══════ CombineFilter 查找 (复合条件) — 委托给 GkdNodeFinder ═══════

    public UiObjectCollection findByCombine(CombineFilter filter) {
        if (filter == null) return null;
        try {
            List<UiObject> results = GkdNodeFinder.findAllByCombine(this, filter);
            return UiObjectCollection.of(new ArrayList<>(results));
        } catch (Exception e) {
            AppUtils.s("UiObject-findByCombine:", e);
            return null;
        }
    }

    public UiObject findOneByCombine(CombineFilter filter) {
        if (filter == null) return null;
        try {
            return GkdNodeFinder.findOneByCombine(this, filter);
        } catch (Exception e) {
            AppUtils.s("UiObject-findOneByCombine:", e);
            return null;
        }
    }

    public UiObject findOneByCombineLoop(CombineFilter filter) {
        // vendor 实现: 循环尝试刷新直到找到
        return findOneByCombine(filter);
    }

    public UiObject findLastByCombine(CombineFilter filter) {
        if (filter == null) return null;
        try {
            List<UiObject> results = GkdNodeFinder.findAllByCombine(this, filter);
            return results.isEmpty() ? null : results.get(results.size() - 1);
        } catch (Exception e) {
            AppUtils.s("UiObject-findLastByCombine:", e);
            return null;
        }
    }

    public UiObjectCollection findByCombineWithChild(CombineFilterWithChild filter) {
        if (filter == null) return null;
        try {
            List<UiObject> results = GkdNodeFinder.findAllByCombineWithChild(this, filter);
            return UiObjectCollection.of(new ArrayList<>(results));
        } catch (Exception e) {
            AppUtils.s("UiObject-findByCombineWithChild:", e);
            return null;
        }
    }

    public UiObject findOneByCombineWithChild(CombineFilterWithChild filter) {
        if (filter == null) return null;
        try {
            return GkdNodeFinder.findOneByCombineWithChild(this, filter);
        } catch (Exception e) {
            AppUtils.s("UiObject-findOneByCombineWithChild:", e);
            return null;
        }
    }

    public UiObjectCollection findByCombineWithoutChild(CombineFilterWithChild filter) {
        if (filter == null) return null;
        try {
            List<UiObject> parents = GkdNodeFinder.findAllByCombine(this, filter.getParentFilter());
            ArrayList<UiObject> matched = new ArrayList<>();
            for (UiObject p : parents) {
                if (p != null && GkdNodeFinder.findOneByCombine(p, filter.getChildFilter()) == null) {
                    matched.add(p);
                }
            }
            return UiObjectCollection.of(matched);
        } catch (Exception e) {
            AppUtils.s("UiObject-findByCombineWithoutChild:", e);
            return null;
        }
    }

    public UiObject findOneByCombineWithoutChild(CombineFilterWithChild filter) {
        if (filter == null) return null;
        try {
            List<UiObject> parents = GkdNodeFinder.findAllByCombine(this, filter.getParentFilter());
            for (UiObject p : parents) {
                if (p != null && GkdNodeFinder.findOneByCombine(p, filter.getChildFilter()) == null) {
                    return p;
                }
            }
            return null;
        } catch (Exception e) {
            AppUtils.s("UiObject-findOneByCombineWithoutChild:", e);
            return null;
        }
    }

    public UiObject findOneByCombineWithParent(CombineFilterWithChild filter) {
        if (filter == null) return null;
        try {
            List<UiObject> parents = GkdNodeFinder.findAllByCombine(this, filter.getParentFilter());
            for (UiObject p : parents) {
                if (p != null) {
                    UiObject child = GkdNodeFinder.findOneByCombine(p, filter.getChildFilter());
                    if (child != null) return child;
                }
            }
            return null;
        } catch (Exception e) {
            AppUtils.s("UiObject-findOneByCombineWithParent:", e);
            return null;
        }
    }

    // ═══════ OperateOr 查找 (OR 复合条件) — 委托给 GkdNodeFinder ═══════

    public UiObjectCollection findByOperateOr(CombineFiltersWithOr filter) {
        if (filter == null || filter.getFilterList() == null) return null;
        try {
            List<UiObject> results = GkdNodeFinder.findAllByOperateOr(this, filter);
            return UiObjectCollection.of(new ArrayList<>(results));
        } catch (Exception e) {
            AppUtils.s("UiObject-findByOperateOr:", e);
            return null;
        }
    }

    public UiObject findOneByOperateOr(CombineFiltersWithOr filter) {
        if (filter == null || filter.getFilterList() == null) return null;
        try {
            return GkdNodeFinder.findOneByOperateOr(this, filter);
        } catch (Exception e) {
            AppUtils.s("UiObject-findOneByOperateOr:", e);
            return null;
        }
    }

    public UiObject findOneByOperateOrLoop(CombineFiltersWithOr filter) {
        return findOneByOperateOr(filter);
    }

    public UiObject findLastByOperateOr(CombineFiltersWithOr filter) {
        if (filter == null || filter.getFilterList() == null) return null;
        try {
            List<UiObject> results = GkdNodeFinder.findAllByOperateOr(this, filter);
            return results.isEmpty() ? null : results.get(results.size() - 1);
        } catch (Exception e) {
            AppUtils.s("UiObject-findLastByOperateOr:", e);
            return null;
        }
    }

    // ═══════ 父节点查找 ═══════

    public UiObject findParentByCombine(CombineFilter filter, Integer maxUp) {
        if (filter == null) return null;
        try {
            UiObject current = this.parent();
            int count = 0;
            int max = (maxUp != null && maxUp > 0) ? maxUp : 50;
            while (current != null && count < max) {
                // Use GkdNodeFinder to check if the current parent matches the filter.
                // findOneByCombine on itself (depth=0) acts as a match check.
                if (GkdNodeFinder.findOneByCombine(current, filter) != null) return current;
                current = current.parent();
                count++;
            }
            return null;
        } catch (Exception e) {
            AppUtils.s("UiObject-findParentByCombine:", e);
            return null;
        }
    }

    public UiObject findParentUtilCombine(CombineFilter filter) {
        return findParentByCombine(filter, 50);
    }

    public UiObject findChildUtilUpLevel(CombineFilter filter, Integer upLevel) {
        if (filter == null) return null;
        try {
            UiObject p = findParentByCombine(filter, upLevel);
            if (p == null) return null;
            return p.findOneByCombine(filter);
        } catch (Exception e) {
            AppUtils.s("UiObject-findChildUtilUpLevel:", e);
            return null;
        }
    }

    // ═══════ scrollForwardUtil / scrollBackwardUtil ═══════

    public UiObject scrollForwardUtil(SingleScrollCondition condition) {
        if (condition == null) return null;
        try {
            UiObject found = condition.evaluateSingle(this);
            if (found != null && found.visibleToUser()) return found;
            int count = 0;
            while (scrollForward() && count <= condition.scrollCount()) {
                found = utilRefresh(condition);
                if (found != null && found.visibleToUser()) return found;
                count++;
            }
            return null;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollForwardUtil:", e);
            return null;
        }
    }

    public UiObject scrollBackwardUtil(SingleScrollCondition condition) {
        if (condition == null) return null;
        try {
            UiObject found = condition.evaluateSingle(this);
            if (found != null && found.visibleToUser()) return found;
            int count = 0;
            while (scrollBackward() && count <= condition.scrollCount()) {
                found = utilRefresh(condition);
                if (found != null && found.visibleToUser()) return found;
                count++;
            }
            return null;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollBackwardUtil:", e);
            return null;
        }
    }

    public UiObjectCollection scrollForwardUtilMultiple(MultiScrollCondition condition) {
        if (condition == null) return null;
        try {
            UiObjectCollection found = condition.evaluateMultiple(this);
            if (found != null && found.size() > 0) return found;
            int count = 0;
            while (scrollForward() && count <= condition.scrollCount()) {
                found = condition.evaluateMultiple(this);
                if (found != null && found.size() > 0) return found;
                count++;
            }
            return null;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollForwardUtilMultiple:", e);
            return null;
        }
    }

    public UiObjectCollection scrollBackwardUtilMultiple(MultiScrollCondition condition) {
        if (condition == null) return null;
        try {
            UiObjectCollection found = condition.evaluateMultiple(this);
            if (found != null && found.size() > 0) return found;
            int count = 0;
            while (scrollBackward() && count <= condition.scrollCount()) {
                found = condition.evaluateMultiple(this);
                if (found != null && found.size() > 0) return found;
                count++;
            }
            return null;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollBackwardUtilMultiple:", e);
            return null;
        }
    }

    /** 滚动后刷新并重新搜索 */
    private UiObject utilRefresh(SingleScrollCondition condition) {
        try {
            refresh();
            SystemHelper.T0(1); // 等待 200ms
            return condition.evaluateSingle(this);
        } catch (Exception e) {
            return null;
        }
    }

    // ═══════ actionByName — 按动作名执行 (vendor 巨型 switch-case) ═══════

    public boolean actionByName(TargetActionCondition condition) {
        if (condition == null || AppUtils.B(condition.getActionName())) return false;
        try {
            String action = condition.getActionName();
            switch (action) {
                case "click": return click();
                case "longClick": return longClick();
                case "focus": return focus();
                case "clearFocus": return clearFocus();
                case "select": return select();
                case "copy": return copy();
                case "cut": return cut();
                case "paste": return paste();
                case "dismiss": return dismiss();
                case "collapse": return collapse();
                case "expand": return expand();
                case "show": return show();
                case "scrollForward": return scrollForward();
                case "scrollBackward": return scrollBackward();
                case "scrollDown": return scrollDown();
                case "scrollUp": return scrollUp();
                case "scrollLeft": return scrollLeft();
                case "scrollRight": return scrollRight();
                case "scrollForwardEnd": scrollForwardEnd(); return true;
                case "scrollBackwardEnd": scrollBackwardEnd(); return true;
                case "scrollForwardByGesture": return scrollForwardByGesture();
                case "scrollBackwardByGesture": return scrollBackwardByGesture();
                case "accessibilityFocus": return accessibilityFocus();
                case "clearAccessibilityFocus": return clearAccessibilityFocus();
                case "contextClick": return contextClick();
                case "enter": return enter();
                case "setText":
                    ActionValueCondition avc = condition.getActionValueCondition();
                    return avc != null && setText(avc.getValue());
                case "clickPosition":
                    ActionValueCondition posAvc = condition.getActionValueCondition();
                    if (posAvc != null) {
                        return clickPosition(posAvc.getXRatio(), posAvc.getYRatio());
                    }
                    return false;
                default:
                    Log.w(TAG, "Unknown action: " + action);
                    return false;
            }
        } catch (Exception e) {
            AppUtils.s("UiObject-actionByName:", e);
            return false;
        }
    }

    // ═══════ toString / hashCode / equals ═══════

    @Override
    public String toString() {
        try {
            return "UiObject{id=" + id() + ", text=" + text() + ", desc=" + desc()
                    + ", class=" + className() + ", depth=" + depth + "}";
        } catch (Exception e) {
            return "UiObject{}";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, depth, indexInParent);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UiObject)) return false;
        UiObject other = (UiObject) obj;
        return depth == other.depth && indexInParent == other.indexInParent
                && Objects.equals(uniqueId, other.uniqueId);
    }

    // ═══════ 补全缺失方法 (vendor 有但 replica 缺少的 15 个) ═══════

    public int depth() { return this.depth; }
    public int indexInParent() { return this.indexInParent; }
    public AtomicReference<AccessibilityNodeInfoCompat> source() { return this.source; }
    public void setRootRecycle(boolean v) { this.rootRecycle = v; }

    public AccessibilityNodeInfoCompat getChild(int index) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            return s != null ? s.getChild(index) : null;
        } catch (Exception e) { return null; }
    }

    public String getProperty(String name) {
        if (name == null) return null;
        switch (name) {
            case "text": return text();
            case "id": return id();
            case "desc": return desc();
            case "className": return className();
            case "packageName": return packageName();
            default: return cacheProperties.get(name);
        }
    }

    /** 重复点击 n 次，间隔 delayMs */
    public boolean repeatClick(int count, long delayMs) {
        try {
            for (int i = 0; i < count; i++) {
                if (!click()) return false;
                if (delayMs > 0 && i < count - 1) Thread.sleep(delayMs);
            }
            return true;
        } catch (Exception e) {
            AppUtils.s("UiObject-repeatClick:", e);
            return false;
        }
    }

    /** 滚动到指定文本可见 */
    public boolean scrollTo(String text) {
        if (AppUtils.B(text)) return false;
        try {
            if (findOneByText(text) != null) return true;
            int maxScroll = 30;
            for (int i = 0; i < maxScroll; i++) {
                if (!scrollForward()) return false;
                if (findOneByText(text) != null) return true;
            }
            return false;
        } catch (Exception e) {
            AppUtils.s("UiObject-scrollTo:", e);
            return false;
        }
    }

    public void setBoundsInScreen(Rect bounds) {
        try {
            AccessibilityNodeInfoCompat s = getSource();
            if (s != null) s.setBoundsInScreen(bounds);
        } catch (Exception e) {
            AppUtils.s("UiObject-setBoundsInScreen:", e);
        }
    }

    public boolean setProgress(float progress) {
        try {
            Bundle args = new Bundle();
            args.putFloat(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE, progress);
            return performAction(android.R.id.accessibilityActionSetProgress, args);
        } catch (Exception e) {
            AppUtils.s("UiObject-setProgress:", e);
            return false;
        }
    }

    public boolean setSelection(int start, int end) {
        try {
            Bundle args = new Bundle();
            args.putInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_START_INT, start);
            args.putInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_END_INT, end);
            return performAction(AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, args);
        } catch (Exception e) {
            AppUtils.s("UiObject-setSelection:", e);
            return false;
        }
    }

    public Rect getRegionAt(int index) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                AccessibilityNodeInfoCompat s = getSource();
                if (s == null) return null;
                AccessibilityNodeInfo.TouchDelegateInfo info = s.unwrap().getTouchDelegateInfo();
                if (info == null || index < 0 || index >= info.getRegionCount()) return null;
                android.graphics.Region region = info.getRegionAt(index);
                return region != null ? region.getBounds() : null;
            }
            return null;
        } catch (Exception e) { return null; }
    }

    public UiObject getTargetForRegion(Rect bounds) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                AccessibilityNodeInfoCompat s = getSource();
                if (s == null || bounds == null) return null;
                AccessibilityNodeInfo.TouchDelegateInfo info = s.unwrap().getTouchDelegateInfo();
                if (info == null) return null;
                android.graphics.Region region = new android.graphics.Region(bounds);
                AccessibilityNodeInfo target = info.getTargetForRegion(region);
                if (target != null) return new UiObject(target, depth + 1, -1);
            }
            return null;
        } catch (Exception e) { return null; }
    }

    /** scrollForwardUtil/scrollBackwardUtil 内部用的刷新重搜 (已在上面定义，此处为 package 可见性覆盖) */
    private UiObjectCollection utilMultipleRefresh(MultiScrollCondition condition) {
        try {
            refresh();
            SystemHelper.T0(1);
            return condition.evaluateMultiple(this);
        } catch (Exception e) { return null; }
    }
}
