package com.vendor.rat.auto.engine.adb;

/**
 * Constants for wireless ADB self-pairing engine.
 *
 * Covers GKD Selector expressions, UI text matchers, timeout values,
 * and package names used across all 7 phases of the pairing flow.
 *
 * Target device: OPPO PGFM10 (Android 16, ColorOS)
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

    /** GKD Selector: clickable row containing "系统与更新" */
    public static final String SEL_SYSTEM_UPDATE =
            "[clickable=true] >n TextView[text*=\"系统与更新\"]";

    /** GKD Selector: clickable row containing "系统管理" (fallback) */
    public static final String SEL_SYSTEM_MANAGEMENT =
            "[clickable=true] >n TextView[text*=\"系统管理\"]";

    /** GKD Selector: clickable row containing "开发者选项" */
    public static final String SEL_DEV_OPTIONS =
            "[clickable=true] >n TextView[text*=\"开发者选项\"]";

    // ── Phase 2: Enable Wireless Debug ──────────────────────────────────

    /** "无线调试" toggle text */
    public static final String TEXT_WIRELESS_DEBUG = "无线调试";

    /** English fallback: "Wireless debugging" */
    public static final String TEXT_WIRELESS_DEBUG_EN = "Wireless debugging";

    /** GKD Selector: clickable row containing "无线调试" */
    public static final String SEL_WIRELESS_DEBUG_ROW =
            "[clickable=true] >n TextView[text*=\"无线调试\"]";

    /** GKD Selector: Switch widget (toggle control) */
    public static final String SEL_SWITCH = "Switch";

    /** GKD Selector: RadioButton widget */
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

    /** "使用配对码配对" full text */
    public static final String TEXT_PAIR_WITH_CODE = "使用配对码配对";

    /** "使用配对码" short text for partial matching */
    public static final String TEXT_PAIR_WITH_CODE_SHORT = "使用配对码";

    /** English fallback: "Pair device with pairing code" */
    public static final String TEXT_PAIR_WITH_CODE_EN = "Pair device with pairing code";

    /** GKD Selector: clickable row containing "使用配对码" */
    public static final String SEL_PAIR_WITH_CODE =
            "[clickable=true] >n TextView[text*=\"使用配对码\"]";

    // ── Phase 4: Read Pair Code + Port ──────────────────────────────────

    /** Regex pattern matching a 6-digit pairing code */
    public static final String REGEX_PAIR_CODE = "\\d{6}";

    /** Minimum port number for wireless pairing */
    public static final int PAIR_PORT_MIN = 30000;

    /** Maximum port number for wireless pairing */
    public static final int PAIR_PORT_MAX = 49999;

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

    /** Android Settings app package */
    public static final String PKG_SETTINGS = "com.android.settings";

    /** SystemUI package */
    public static final String PKG_SYSTEM_UI = "com.android.systemui";
}
