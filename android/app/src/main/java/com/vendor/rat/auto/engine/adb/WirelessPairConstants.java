package com.vendor.rat.auto.engine.adb;

/**
 * Constants for wireless ADB self-pairing engine.
 *
 * Covers GKD Selector expressions, UI text matchers, timeout values,
 * and package names used across all 7 phases of the pairing flow.
 *
 * Target device: OPPO PGFM10 (Android 16, ColorOS)
 *
 * Calibrated from real device XML dumps (2026-03-27):
 *   - settings_home_scrolled.xml  → Phase 1 selectors
 *   - dev_options_wireless.xml    → Phase 2 selectors (split-preference layout)
 *   - wireless_debug.xml          → Phase 3 selectors
 *   - pair_code_dialog.xml        → Phase 4 selectors (resource-id based)
 */
public final class WirelessPairConstants {

    private WirelessPairConstants() {
        // Utility class — no instantiation
    }

    // ── Phase 1: Navigate to Developer Options ──────────────────────────

    /** "系统与更新" menu entry text */
    public static final String TEXT_SYSTEM_UPDATE = "系统与更新";

    /** "系统管理" fallback menu entry text */
    public static final String TEXT_SYSTEM_MANAGEMENT = "系统管理";

    /** "开发者选项" menu entry text */
    public static final String TEXT_DEVELOPER_OPTIONS = "开发者选项";

    /** "关于本机" menu entry text (used for build-number tapping to enable dev options) */
    public static final String TEXT_ABOUT_PHONE = "关于本机";

    /**
     * GKD Selector: clickable row containing "系统与更新".
     *
     * Dump structure: LinearLayout[clickable=true] > TextView[resource-id="android:id/title", text="系统与更新"]
     */
    public static final String SEL_SYSTEM_UPDATE =
            "[clickable=true] >n TextView[text*=\"系统与更新\"]";

    /** GKD Selector: clickable row containing "系统管理" (fallback) */
    public static final String SEL_SYSTEM_MANAGEMENT =
            "[clickable=true] >n TextView[text*=\"系统管理\"]";

    /** GKD Selector: clickable row containing "开发者选项" */
    public static final String SEL_DEV_OPTIONS =
            "[clickable=true] >n TextView[text*=\"开发者选项\"]";

    /** GKD Selector: clickable row containing "关于本机" */
    public static final String SEL_ABOUT_PHONE =
            "[clickable=true] >n TextView[text*=\"关于本机\"]";

    // ── Phase 2: Enable Wireless Debug ──────────────────────────────────

    /** "无线调试" toggle text */
    public static final String TEXT_WIRELESS_DEBUG = "无线调试";

    /** English fallback: "Wireless debugging" */
    public static final String TEXT_WIRELESS_DEBUG_EN = "Wireless debugging";

    /**
     * GKD Selector: clickable area containing "无线调试" text (enters the sub-page).
     *
     * Dump structure (split-preference layout):
     *   LinearLayout[clickable=true]            ← outer row
     *     ├── main_layout[clickable=true]       ← click → enters wireless debug page
     *     │     └── RelativeLayout
     *     │           └── TextView[text="无线调试"]
     *     └── switch_layout[clickable=true]     ← click → toggles switch
     *           └── Switch[id="switch_widget"]
     *
     * This selector matches the TEXT area; clicking it navigates to the sub-page.
     */
    public static final String SEL_WIRELESS_DEBUG_ROW =
            "[clickable=true] >n TextView[text*=\"无线调试\"]";

    /**
     * GKD Selector: the Switch toggle for wireless debug (within switch_layout).
     *
     * On OPPO ColorOS, the wireless debug row uses a split layout where the Switch
     * itself is clickable=true inside a separate switch_layout container.
     * Widget class: android.widget.Switch (resource-id: android:id/switch_widget)
     */
    public static final String SEL_SWITCH = "Switch";

    /** Resource ID of the switch widget used in Settings preferences */
    public static final String RES_ID_SWITCH_WIDGET = "android:id/switch_widget";

    /**
     * Resource ID of the main_layout (text area) in a split-preference row.
     * Clicking this enters the sub-page rather than toggling the switch.
     */
    public static final String RES_ID_MAIN_LAYOUT = "com.android.settings:id/main_layout";

    /**
     * Resource ID of the switch_layout in a split-preference row.
     * Clicking this toggles the switch on/off.
     */
    public static final String RES_ID_SWITCH_LAYOUT = "com.android.settings:id/switch_layout";

    /** GKD Selector: RadioButton widget (fallback for non-OPPO devices) */
    public static final String SEL_RADIO_BUTTON = "RadioButton";

    /** Confirm dialog — match by resource ID (android:id/button1) */
    public static final String SEL_CONFIRM_BUTTON_ID = "[id$=\"button1\"]";

    /** Confirm dialog — "确认" button text */
    public static final String SEL_CONFIRM_BUTTON_TEXT = "Button[text*=\"确认\"]";

    /** Confirm dialog — "允许" button text */
    public static final String SEL_ALLOW_BUTTON_TEXT = "Button[text*=\"允许\"]";

    /** Confirm dialog — "确定" button text */
    public static final String SEL_OK_BUTTON_TEXT = "Button[text*=\"确定\"]";

    // ── Phase 3: Click Pair Code ────────────────────────────────────────

    /**
     * "使用配对码配对设备" — full text as seen on OPPO wireless debug page.
     *
     * Dump shows exact text: "使用配对码配对设备" (resource-id: android:id/title)
     * with summary: "使用六位数的配对码配对新设备"
     */
    public static final String TEXT_PAIR_WITH_CODE = "使用配对码配对设备";

    /** "使用配对码" short text for partial matching */
    public static final String TEXT_PAIR_WITH_CODE_SHORT = "使用配对码";

    /** English fallback: "Pair device with pairing code" */
    public static final String TEXT_PAIR_WITH_CODE_EN = "Pair device with pairing code";

    /**
     * GKD Selector: clickable row containing "使用配对码".
     *
     * Dump structure: LinearLayout[clickable=true] > RelativeLayout > TextView[text="使用配对码配对设备"]
     * Using partial match "使用配对码" to handle minor text variations across OEMs.
     */
    public static final String SEL_PAIR_WITH_CODE =
            "[clickable=true] >n TextView[text*=\"使用配对码\"]";

    // ── Phase 4: Read Pair Code + Port ──────────────────────────────────

    /**
     * Dialog title text: "与设备配对" (resource-id: com.android.settings:id/alertTitle).
     *
     * This is the AlertDialog title, not to be confused with the "WLAN 配对码" label
     * which is a section header inside the dialog body.
     */
    public static final String TEXT_PAIR_DIALOG_TITLE = "与设备配对";

    /** "WLAN 配对码" label inside the pair code dialog body (section header) */
    public static final String TEXT_WLAN_PAIR_CODE_LABEL = "WLAN 配对码";

    /** "IP 地址和端口" label inside the pair code dialog body */
    public static final String TEXT_IP_PORT_LABEL = "IP 地址和端口";

    /**
     * Resource ID of the pairing code TextView in the pair dialog.
     * Contains the 6-digit code (e.g. "123671").
     */
    public static final String RES_ID_PAIRING_CODE = "com.android.settings:id/pairing_code";

    /**
     * Resource ID of the IP address + port TextView in the pair dialog.
     * Contains the IP:port for pairing (e.g. "192.168.31.249:46549").
     */
    public static final String RES_ID_IP_ADDR = "com.android.settings:id/ip_addr";

    /**
     * Resource ID of the dialog title.
     */
    public static final String RES_ID_ALERT_TITLE = "com.android.settings:id/alertTitle";

    /**
     * GKD Selector: pair code by resource ID.
     * This is the most reliable way to find the 6-digit code.
     */
    public static final String SEL_PAIRING_CODE =
            "TextView[id=\"com.android.settings:id/pairing_code\"]";

    /**
     * GKD Selector: IP address + port by resource ID.
     * This is the most reliable way to find the pairing IP:port.
     */
    public static final String SEL_IP_ADDR =
            "TextView[id=\"com.android.settings:id/ip_addr\"]";

    /** Regex pattern matching a 6-digit pairing code */
    public static final String REGEX_PAIR_CODE = "\\d{6}";

    /**
     * Regex pattern matching an IP:port string in the pair dialog.
     * Format: x.x.x.x:port (port range 30000-49999 for pairing).
     */
    public static final String REGEX_IP_PORT = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{4,5}";

    /** Minimum port number for wireless pairing */
    public static final int PAIR_PORT_MIN = 30000;

    /** Maximum port number for wireless pairing */
    public static final int PAIR_PORT_MAX = 49999;

    /**
     * Cancel button in pair dialog (resource-id: android:id/button2, text: "取消").
     *
     * Note: The OPPO pair code dialog only has a Cancel button (button2);
     * there is no confirm/OK button (button1). Pairing happens automatically
     * when the code is entered on the connecting device.
     */
    public static final String TEXT_CANCEL_BUTTON = "取消";

    /** Resource ID of the cancel button in the pair dialog */
    public static final String RES_ID_CANCEL_BUTTON = "android:id/button2";

    // ── Timeouts ────────────────────────────────────────────────────────

    /** Per-phase timeout in milliseconds */
    public static final long PHASE_TIMEOUT_MS = 15_000;

    /** Overall engine timeout in milliseconds */
    public static final long OVERALL_TIMEOUT_MS = 120_000;

    /** Maximum scroll attempts when searching for a UI element */
    public static final int MAX_SCROLL_ATTEMPTS = 10;

    /** Maximum retries per phase before declaring failure */
    public static final int MAX_PHASE_RETRIES = 3;

    /** Pair code validity window in milliseconds (~55s before system expires it) */
    public static final long PAIR_CODE_EXPIRY_MS = 55_000;

    /** Delay between auto-connect retries in milliseconds */
    public static final long AUTO_CONNECT_RETRY_DELAY_MS = 3_000;

    /** Maximum auto-connect retry attempts */
    public static final int AUTO_CONNECT_MAX_RETRIES = 3;

    // ── Package Names ───────────────────────────────────────────────────

    /**
     * Android Settings app package.
     *
     * Confirmed from all 4 dumps: the pair code dialog is rendered by
     * com.android.settings (NOT com.android.systemui).
     */
    public static final String PKG_SETTINGS = "com.android.settings";

    /** SystemUI package (not used for pair dialog on OPPO, kept for other OEMs) */
    public static final String PKG_SYSTEM_UI = "com.android.systemui";
}
