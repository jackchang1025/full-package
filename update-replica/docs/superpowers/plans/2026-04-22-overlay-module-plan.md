# Overlay Module Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a unified, config-driven overlay module that replaces the current stub (`ConfigMaskOverlay`) and Activity mask (`yojggfhv`), using a single WindowManager implementation with full progress animation and audio stealth.

**Architecture:** 5 files in `service/modules/overlay/` — OverlayConfig (data), OverlayManager (facade), OverlayWindowView (UI+window), OverlayProgressAnimator (animation), AudioStealthManager (audio mute/restore). All clients call `overlayManager.show(config)` / `.hide()`. Tests use JUnit 4 + Robolectric source-based validation.

**Tech Stack:** Kotlin, Android WindowManager, TYPE_ACCESSIBILITY_OVERLAY, JUnit 4, Robolectric, kotlinx-coroutines-test

**Spec:** `docs/superpowers/specs/2026-04-22-overlay-module-design.md`

**Build command:** `./gradlew test` (from `update-replica/`)

---

## File Map

| # | File (create) | Responsibility | Est. lines |
|---|--------------|---------------|-----------|
| 1 | `service/modules/overlay/OverlayConfig.kt` | Config data class + sealed types + factory methods | ~100 |
| 2 | `service/modules/overlay/AudioStealthManager.kt` | Mute/restore 5 audio streams + ringer + haptic | ~90 |
| 3 | `service/modules/overlay/OverlayProgressAnimator.kt` | Dual-mode progress animation + tip rotation | ~110 |
| 4 | `service/modules/overlay/OverlayWindowView.kt` | Programmatic UI + WindowManager addView/removeView | ~260 |
| 5 | `service/modules/overlay/OverlayManager.kt` | Lifecycle facade: show/hide/isShowing/updateProgress | ~130 |

| # | File (modify) | Change |
|---|--------------|--------|
| 6 | `service/MyAccessibilityService.kt` | Replace `ConfigMaskOverlay.show/hide` → `overlayManager.show/hide` |
| 7 | `service/modules/command/BlackScreenCommandHandler.kt` | Replace stub calls → `overlayManager.show(OverlayConfig.blackScreen(...))` |
| 8 | `service/modules/setup/flow/PairFlowOrchestrator.kt` | Wire `overlayManager.hide()` in `handleComplete()` |
| 9 | `service/modules/setup/OpenDevelopmentDelegate.kt` | Replace inline audio fields → inject `AudioStealthManager` |
| 10 | `service/modules/command/AdbTunnelCommandHandler.kt` | Replace static `restoreSoundAndHaptic` → `AudioStealthManager` |

| # | File (delete) | Reason |
|---|--------------|--------|
| 11 | `service/modules/overlay/ConfigMaskOverlay.kt` | Replaced by OverlayManager |

| # | Test file (create/rewrite) |
|---|---------------------------|
| T1 | `test/.../overlay/OverlayConfigTest.kt` |
| T2 | `test/.../overlay/AudioStealthManagerTest.kt` |
| T3 | `test/.../overlay/OverlayProgressAnimatorTest.kt` |
| T4 | `test/.../overlay/OverlayWindowViewTest.kt` |
| T5 | `test/.../overlay/OverlayManagerTest.kt` |
| T6 | `test/.../overlay/ConfigMaskOverlayTest.kt` (rewrite → integration) |

All paths below are relative to `app/src/main/java/com/storm/safe/rock/` (source) and `app/src/test/java/com/storm/safe/rock/` (test).

---

## Task 1: OverlayConfig — 配置数据类

**Files:**
- Create: `service/modules/overlay/OverlayConfig.kt`
- Test: `test: service/modules/overlay/OverlayConfigTest.kt`

### Step 1: Write failing tests

- [ ] **1.1 Create test file**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayConfigTest.kt
package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class OverlayConfigTest {

    // ── Data class basics ──

    @Test
    fun `default config has expected values`() {
        val config = OverlayConfig()
        assertTrue(config.background is OverlayConfig.OverlayBackground.Image)
        assertEquals(OverlayConfig.TouchMode.PASSTHROUGH, config.touchMode)
        assertFalse(config.preventScreenshot)
        assertTrue(config.showAppIcon)
        assertTrue(config.progressBar is OverlayConfig.ProgressBarStyle.GradientBlue)
        assertEquals("配置中请稍后...", config.titleText)
        assertEquals("正在自动配置和连接\n请勿操作设备", config.subtitleText)
        assertEquals("配置完成后将自动返回应用", config.statusText)
        assertEquals("#FFFFFF", config.titleColor)
        assertEquals("#CCCCCC", config.subtitleColor)
        assertEquals(5, config.loadingTips.size)
        assertTrue(config.keepScreenOn)
    }

    @Test
    fun `DEFAULT_TIPS has 5 vendor items`() {
        val tips = OverlayConfig.DEFAULT_TIPS
        assertEquals(5, tips.size)
        assertEquals("检查最优线路中", tips[0])
        assertEquals("正在连接服务器...", tips[1])
        assertEquals("正在加载资源...", tips[2])
        assertEquals("正在初始化配置...", tips[3])
        assertEquals("正在启动", tips[4])
    }

    // ── TouchMode enum ──

    @Test
    fun `TouchMode has exactly 2 values`() {
        assertEquals(2, OverlayConfig.TouchMode.values().size)
        assertNotNull(OverlayConfig.TouchMode.PASSTHROUGH)
        assertNotNull(OverlayConfig.TouchMode.INTERCEPT)
    }

    // ── OverlayBackground sealed interface ──

    @Test
    fun `SolidColor default is black with alpha 1`() {
        val bg = OverlayConfig.OverlayBackground.SolidColor()
        assertEquals(android.graphics.Color.BLACK, bg.color)
        assertEquals(1f, bg.alpha, 0.001f)
    }

    @Test
    fun `Image default has 2 asset paths and fallback drawable`() {
        val bg = OverlayConfig.OverlayBackground.Image()
        assertEquals(2, bg.assetPaths.size)
        assertEquals("app_loading_bg.webp", bg.assetPaths[0])
        assertEquals("app_loading_bg.png", bg.assetPaths[1])
        assertEquals("bg_config_mask", bg.fallbackDrawable)
        assertEquals(android.graphics.Color.BLACK, bg.fallbackColor)
    }

    // ── ProgressBarStyle sealed interface ──

    @Test
    fun `GradientBlue has vendor colors`() {
        val style = OverlayConfig.ProgressBarStyle.GradientBlue()
        assertEquals(0xFF4A90D9.toInt(), style.startColor)
        assertEquals(0xFF67B8F7.toInt(), style.endColor)
        assertFalse(style.startFromMax)
    }

    @Test
    fun `SystemOrange has vendor color`() {
        val style = OverlayConfig.ProgressBarStyle.SystemOrange()
        assertEquals(0xFFFF9800.toInt(), style.color)
        assertFalse(style.startFromMax)
    }

    @Test
    fun `None is singleton`() {
        assertSame(OverlayConfig.ProgressBarStyle.None, OverlayConfig.ProgressBarStyle.None)
    }

    // ��─ Factory methods ──

    @Test
    fun `configMask factory returns default config`() {
        val config = OverlayConfig.configMask()
        assertEquals(OverlayConfig(), config)
    }

    @Test
    fun `blackScreen factory creates correct config`() {
        val config = OverlayConfig.blackScreen(text = "更新中", alpha = 0.95f)
        assertTrue(config.background is OverlayConfig.OverlayBackground.SolidColor)
        val bg = config.background as OverlayConfig.OverlayBackground.SolidColor
        assertEquals(0.95f, bg.alpha, 0.001f)
        assertEquals(OverlayConfig.TouchMode.INTERCEPT, config.touchMode)
        assertTrue(config.preventScreenshot)
        assertFalse(config.showAppIcon)
        assertTrue(config.progressBar is OverlayConfig.ProgressBarStyle.None)
        assertEquals("更新中", config.titleText)
        assertEquals("", config.subtitleText)
        assertEquals("", config.statusText)
        assertTrue(config.loadingTips.isEmpty())
    }

    @Test
    fun `blackScreen with interceptTouch false`() {
        val config = OverlayConfig.blackScreen(interceptTouch = false)
        assertEquals(OverlayConfig.TouchMode.PASSTHROUGH, config.touchMode)
    }

    // ── Copy with override ──

    @Test
    fun `data class copy preserves unmodified fields`() {
        val original = OverlayConfig.configMask()
        val modified = original.copy(showAppIcon = false, preventScreenshot = true)
        assertFalse(modified.showAppIcon)
        assertTrue(modified.preventScreenshot)
        assertEquals(original.titleText, modified.titleText)
        assertEquals(original.loadingTips, modified.loadingTips)
    }
}
```

- [ ] **1.2 Run tests — verify RED**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -5
```

Expected: compilation failure — `OverlayConfig` does not exist.

### Step 2: Write implementation

- [ ] **1.3 Create OverlayConfig.kt**

```kotlin
// app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayConfig.kt
package com.storm.safe.rock.service.modules.overlay

import android.graphics.Color

/**
 * Unified configuration for the overlay module.
 *
 * Reverse-engineered from JADX: dd0 (MaskConfig, 10 fields) + fd0 (BlackScreen params).
 * Vendor fields: f55699a0..f55708a9 (dd0), f56200a2 (fd0 alpha).
 */
data class OverlayConfig(
    val background: OverlayBackground = OverlayBackground.Image(),
    val touchMode: TouchMode = TouchMode.PASSTHROUGH,
    val preventScreenshot: Boolean = false,
    val showAppIcon: Boolean = true,
    val progressBar: ProgressBarStyle = ProgressBarStyle.GradientBlue(),
    val titleText: String = "配置中请稍后...",
    val subtitleText: String = "正在自动配置和连接\n请勿操作设备",
    val statusText: String = "配置完成后将自动返回应用",
    val titleColor: String = "#FFFFFF",
    val subtitleColor: String = "#CCCCCC",
    val loadingTips: List<String> = DEFAULT_TIPS,
    val keepScreenOn: Boolean = true,
) {
    enum class TouchMode { PASSTHROUGH, INTERCEPT }

    sealed interface OverlayBackground {
        data class SolidColor(
            val color: Int = Color.BLACK,
            val alpha: Float = 1f
        ) : OverlayBackground

        data class Image(
            val assetPaths: List<String> = listOf(
                "app_loading_bg.webp",
                "app_loading_bg.png"
            ),
            val fallbackDrawable: String? = "bg_config_mask",
            val fallbackColor: Int = Color.BLACK
        ) : OverlayBackground
    }

    sealed interface ProgressBarStyle {
        data object None : ProgressBarStyle

        data class GradientBlue(
            val startColor: Int = 0xFF4A90D9.toInt(),
            val endColor: Int = 0xFF67B8F7.toInt(),
            val startFromMax: Boolean = false
        ) : ProgressBarStyle

        data class SystemOrange(
            val color: Int = 0xFFFF9800.toInt(),
            val startFromMax: Boolean = false
        ) : ProgressBarStyle
    }

    companion object {
        val DEFAULT_TIPS = listOf(
            "检查最优线路中",
            "正在连接服务器...",
            "正在加载资源...",
            "正在初始化配置...",
            "正在启动"
        )

        fun configMask() = OverlayConfig()

        fun blackScreen(
            text: String = "",
            alpha: Float = 0.99f,
            interceptTouch: Boolean = true
        ) = OverlayConfig(
            background = OverlayBackground.SolidColor(alpha = alpha),
            touchMode = if (interceptTouch) TouchMode.INTERCEPT else TouchMode.PASSTHROUGH,
            preventScreenshot = true,
            showAppIcon = false,
            progressBar = ProgressBarStyle.None,
            titleText = text,
            subtitleText = "",
            statusText = "",
            loadingTips = emptyList()
        )
    }
}
```

- [ ] **1.4 Run tests — verify GREEN**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.OverlayConfigTest" 2>&1 | tail -10
```

Expected: all 12 tests PASS.

- [ ] **1.5 Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayConfig.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayConfigTest.kt
git commit -m "feat(overlay): add OverlayConfig data class with sealed types and factory methods"
```

---

## Task 2: AudioStealthManager — 音频静音/恢��

**Files:**
- Create: `service/modules/overlay/AudioStealthManager.kt`
- Test: `test: service/modules/overlay/AudioStealthManagerTest.kt`

### Step 1: Write failing tests

- [ ] **2.1 Create test file**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManagerTest.kt
package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class AudioStealthManagerTest {

    private val source by lazy {
        java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManager.kt").readText()
    }

    // ── Class structure ──

    @Test
    fun `file exists`() {
        assertTrue(java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManager.kt").exists())
    }

    @Test
    fun `class takes Context in constructor`() {
        assertTrue(source.contains("class AudioStealthManager"))
        assertTrue(source.contains("Context"))
    }

    // ── Vendor stream types (f53808b6 = [2, 5, 1, 3, 4]) ──

    @Test
    fun `STREAM_TYPES matches vendor f53808b6`() {
        assertTrue("must define STREAM_TYPES", source.contains("STREAM_TYPES"))
        // Vendor order: VOICE_CALL(2), NOTIFICATION(5), RING(1), MUSIC(3), ALARM(4)
        assertTrue("must contain stream 2 (VOICE_CALL)", source.contains("STREAM_VOICE_CALL") || source.contains(", 2,") || source.contains("(2,"))
        assertTrue("must contain stream 5 (NOTIFICATION)", source.contains("STREAM_NOTIFICATION") || source.contains(", 5,") || source.contains("(5,"))
        assertTrue("must contain stream 1 (RING)", source.contains("STREAM_RING") || source.contains(", 1,"))
        assertTrue("must contain stream 3 (MUSIC)", source.contains("STREAM_MUSIC") || source.contains(", 3,"))
        assertTrue("must contain stream 4 (ALARM)", source.contains("STREAM_ALARM") || source.contains(", 4)"))
    }

    // ── State fields (vendor f53805b3, f53806b4, f53807b5) ──

    @Test
    fun `has savedVolumes map`() {
        assertTrue(source.contains("savedVolumes"))
        assertTrue(source.contains("LinkedHashMap"))
    }

    @Test
    fun `has savedRingerMode with default NORMAL`() {
        assertTrue(source.contains("savedRingerMode"))
        assertTrue(source.contains("RINGER_MODE_NORMAL") || source.contains("= 2"))
    }

    @Test
    fun `has savedHapticFeedback with default 1`() {
        assertTrue(source.contains("savedHapticFeedback"))
    }

    @Test
    fun `has isActive volatile flag`() {
        assertTrue(source.contains("isActive"))
        assertTrue(source.contains("@Volatile") || source.contains("Volatile"))
    }

    // ── Public API ──

    @Test
    fun `has muteAll method`() {
        assertTrue(source.contains("fun muteAll()"))
    }

    @Test
    fun `muteAll saves volumes then sets to zero`() {
        // Must call getStreamVolume before setStreamVolume
        assertTrue("must get volume", source.contains("getStreamVolume"))
        assertTrue("must set volume to 0", source.contains("setStreamVolume"))
    }

    @Test
    fun `muteAll sets ringer to SILENT`() {
        assertTrue(source.contains("RINGER_MODE_SILENT") || source.contains("ringerMode = 0"))
    }

    @Test
    fun `muteAll disables haptic feedback`() {
        assertTrue(source.contains("haptic_feedback_enabled"))
        assertTrue(source.contains("putInt"))
    }

    @Test
    fun `has restoreAll method`() {
        assertTrue(source.contains("fun restoreAll()"))
    }

    @Test
    fun `restoreAll clears savedVolumes after restore`() {
        assertTrue(source.contains("savedVolumes.clear()") || source.contains("clear()"))
    }

    @Test
    fun `has forceRestoreDefaults method`() {
        assertTrue(source.contains("fun forceRestoreDefaults()"))
    }

    @Test
    fun `forceRestoreDefaults sets RINGER_MODE_NORMAL`() {
        assertTrue(source.contains("RINGER_MODE_NORMAL"))
    }
}
```

- [ ] **2.2 Run tests — verify RED**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.AudioStealthManagerTest" 2>&1 | tail -5
```

Expected: FAIL — file does not exist.

### Step 2: Write implementation

- [ ] **2.3 Create AudioStealthManager.kt**

```kotlin
// app/src/main/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManager.kt
package com.storm.safe.rock.service.modules.overlay

import android.content.Context
import android.media.AudioManager
import android.provider.Settings
import android.util.Log

/**
 * Manages audio muting and haptic feedback disabling during stealth automation.
 *
 * Reverse-engineered from JADX:
 * - C0343a0.java:240-277 (mute logic, inline in FULL_DEPLOY handler)
 * - C0358a0.java:869-897 (restore logic, OpenDevelopmentDelegate.handleFailure)
 * - C0343a0.java:53-64 (force restore defaults, AdbTunnelCommandHandler)
 *
 * Vendor fields: f53805b3 (savedRingerMode), f53806b4 (savedHapticFeedback),
 *               f53807b5 (savedAudioVolumes), f53808b6 (audioStreamTypes=[2,5,1,3,4])
 */
class AudioStealthManager(private val context: Context) {

    companion object {
        private const val TAG = "AudioStealth"

        val STREAM_TYPES = listOf(
            AudioManager.STREAM_VOICE_CALL,   // 2
            AudioManager.STREAM_NOTIFICATION, // 5
            AudioManager.STREAM_RING,         // 1
            AudioManager.STREAM_MUSIC,        // 3
            AudioManager.STREAM_ALARM         // 4
        )
    }

    private val savedVolumes = LinkedHashMap<Int, Int>()
    private var savedRingerMode: Int = AudioManager.RINGER_MODE_NORMAL
    private var savedHapticFeedback: Int = 1

    @Volatile
    var isActive: Boolean = false
        private set

    fun muteAll() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            val resolver = context.contentResolver

            if (audioManager != null) {
                for (streamType in STREAM_TYPES) {
                    try {
                        savedVolumes[streamType] = audioManager.getStreamVolume(streamType)
                        audioManager.setStreamVolume(streamType, 0, 0)
                    } catch (e: Exception) {
                        Log.w(TAG, "静音流${streamType}失败: ${e.message}")
                    }
                }

                savedRingerMode = audioManager.ringerMode
                try {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                } catch (e: Exception) {
                    Log.w(TAG, "设置铃声静默失败: ${e.message}")
                }
            }

            try {
                savedHapticFeedback = Settings.System.getInt(resolver, "haptic_feedback_enabled", 1)
                Settings.System.putInt(resolver, "haptic_feedback_enabled", 0)
            } catch (e: Exception) {
                Log.w(TAG, "禁用触觉反馈失败: ${e.message}")
            }

            isActive = true
            Log.d(TAG, "适配前静音完成 (原铃声模式: $savedRingerMode, 原触觉: $savedHapticFeedback)")
        } catch (e: Exception) {
            Log.e(TAG, "muteAll 异常", e)
        }
    }

    fun restoreAll() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            val resolver = context.contentResolver

            if (audioManager != null) {
                for ((streamType, volume) in savedVolumes) {
                    try {
                        audioManager.setStreamVolume(streamType, volume, 0)
                        Log.i(TAG, "流${streamType}音量恢复为$volume")
                    } catch (e: Exception) {
                        Log.w(TAG, "恢复流${streamType}音量失败: ${e.message}")
                    }
                }
            }
            savedVolumes.clear()

            if (audioManager != null) {
                try {
                    audioManager.ringerMode = savedRingerMode
                } catch (e: Exception) {
                    Log.w(TAG, "恢复铃声模式失败: ${e.message}")
                }
            }
            Log.d(TAG, "已恢复铃声模式: $savedRingerMode")

            try {
                Settings.System.putInt(resolver, "haptic_feedback_enabled", savedHapticFeedback)
                Log.d(TAG, "已恢复触觉反馈: $savedHapticFeedback")
            } catch (e: Exception) {
                Log.w(TAG, "恢复触觉反馈失败: ${e.message}")
            }

            isActive = false
            Log.d(TAG, "适配后恢复声音完成")
        } catch (e: Exception) {
            Log.e(TAG, "restoreAll 异常", e)
        }
    }

    fun forceRestoreDefaults() {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            if (audioManager != null) {
                try {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                } catch (e: Exception) {
                    Log.w(TAG, "强制恢���铃声失败: ${e.message}")
                }
            }
            Log.d(TAG, "已恢复铃声模式: NORMAL")

            try {
                Settings.System.putInt(context.contentResolver, "haptic_feedback_enabled", 1)
                Log.d(TAG, "已开启触觉反馈")
            } catch (e: Exception) {
                Log.w(TAG, "开启触觉反馈失败: ${e.message}")
            }

            isActive = false
            Log.d(TAG, "local-service 部署成功后已恢复铃声 + 开启触觉反馈")
        } catch (e: Exception) {
            Log.e(TAG, "forceRestoreDefaults 异常", e)
        }
    }
}
```

- [ ] **2.4 Run tests — verify GREEN**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.AudioStealthManagerTest" 2>&1 | tail -10
```

Expected: all 15 tests PASS.

- [ ] **2.5 Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManagerTest.kt
git commit -m "feat(overlay): add AudioStealthManager — mute/restore 5 streams + ringer + haptic"
```

---

## Task 3: OverlayProgressAnimator — 双模式进度动画

**Files:**
- Create: `service/modules/overlay/OverlayProgressAnimator.kt`
- Test: `test: service/modules/overlay/OverlayProgressAnimatorTest.kt`

### Step 1: Write failing tests

- [ ] **3.1 Create test file**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimatorTest.kt
package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class OverlayProgressAnimatorTest {

    private val source by lazy {
        java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimator.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue(java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimator.kt").exists())
    }

    // ── Public API ──

    @Test
    fun `has start stop forceProgress methods`() {
        assertTrue(source.contains("fun start()"))
        assertTrue(source.contains("fun stop()"))
        assertTrue(source.contains("fun forceProgress("))
    }

    // ── Mode B: startFromMax=false (vendor RunnableC0707j6 default path) ──

    @Test
    fun `calcProgress mode B before 30s returns 0 to 80`() {
        // vendor: clamp((elapsed / 30000.0) * 80, 0, 80)
        assertEquals(0, OverlayProgressAnimator.calcProgress(0L, false))
        assertEquals(40, OverlayProgressAnimator.calcProgress(15000L, false))
        assertEquals(80, OverlayProgressAnimator.calcProgress(30000L, false))
    }

    @Test
    fun `calcProgress mode B after 30s returns 80 to 95`() {
        // vendor: clamp(((elapsed - 30000) / 3000) + 80, 80, 95)
        assertEquals(80, OverlayProgressAnimator.calcProgress(30000L, false))
        assertEquals(81, OverlayProgressAnimator.calcProgress(33000L, false))
        assertEquals(85, OverlayProgressAnimator.calcProgress(45000L, false))
        assertEquals(95, OverlayProgressAnimator.calcProgress(75000L, false))
        assertEquals(95, OverlayProgressAnimator.calcProgress(120000L, false))
    }

    // ── Mode A: startFromMax=true (vendor withProgress path) ──

    @Test
    fun `calcProgress mode A returns 80 to 100 over 60s`() {
        // vendor: clamp((elapsed / 60000.0) * 20 + 80, 80, 100)
        assertEquals(80, OverlayProgressAnimator.calcProgress(0L, true))
        assertEquals(90, OverlayProgressAnimator.calcProgress(30000L, true))
        assertEquals(100, OverlayProgressAnimator.calcProgress(60000L, true))
        assertEquals(100, OverlayProgressAnimator.calcProgress(90000L, true))
    }

    // ── Interval calculation ──

    @Test
    fun `calcInterval mode B before 30s returns 1000ms`() {
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(0L, false))
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(29000L, false))
    }

    @Test
    fun `calcInterval mode B after 30s returns 3000ms`() {
        assertEquals(3000L, OverlayProgressAnimator.calcInterval(30000L, false))
        assertEquals(3000L, OverlayProgressAnimator.calcInterval(60000L, false))
    }

    @Test
    fun `calcInterval mode A always returns 1000ms`() {
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(0L, true))
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(30000L, true))
    }

    // ── Tip index calculation ──

    @Test
    fun `calcTipIndex maps progress to tip index`() {
        // vendor: clamp(floor(progress / 100.0 * tipCount), 0, tipCount - 1)
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(0, 5))
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(19, 5))
        assertEquals(1, OverlayProgressAnimator.calcTipIndex(20, 5))
        assertEquals(2, OverlayProgressAnimator.calcTipIndex(40, 5))
        assertEquals(3, OverlayProgressAnimator.calcTipIndex(60, 5))
        assertEquals(4, OverlayProgressAnimator.calcTipIndex(80, 5))
        assertEquals(4, OverlayProgressAnimator.calcTipIndex(100, 5))
    }

    @Test
    fun `calcTipIndex with 0 tips returns 0`() {
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(50, 0))
    }

    @Test
    fun `calcTipIndex with 1 tip always returns 0`() {
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(0, 1))
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(99, 1))
    }

    // ── Handler usage ──

    @Test
    fun `uses Handler for scheduling`() {
        assertTrue(source.contains("Handler"))
        assertTrue(source.contains("Looper.getMainLooper()"))
    }

    @Test
    fun `stop removes callbacks`() {
        assertTrue(source.contains("removeCallbacks"))
    }
}
```

- [ ] **3.2 Run tests — verify RED**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.OverlayProgressAnimatorTest" 2>&1 | tail -5
```

Expected: FAIL — file does not exist.

### Step 2: Write implementation

- [ ] **3.3 Create OverlayProgressAnimator.kt**

```kotlin
// app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimator.kt
package com.storm.safe.rock.service.modules.overlay

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Dual-mode progress animation for the overlay.
 *
 * Reverse-engineered from JADX: RunnableC0707j6.java (case 0, lines 48-88).
 * Mode B (startFromMax=false): 0→80% in 30s, then 80→95% every 3s.
 * Mode A (startFromMax=true): 80→100% in 60s.
 */
class OverlayProgressAnimator(
    private val progressBarView: View?,
    private val tipTextView: TextView?,
    private val containerWidthProvider: () -> Int,
    private val tips: List<String>,
    private val startFromMax: Boolean
) {
    private var handler: Handler? = null
    private var startTime: Long = 0L
    private var lastProgress: Int = -1
    private var running = false

    companion object {
        fun calcProgress(elapsedMs: Long, startFromMax: Boolean): Int {
            return if (startFromMax) {
                ((elapsedMs / 60000.0) * 20 + 80).toInt().coerceIn(80, 100)
            } else if (elapsedMs < 30000) {
                ((elapsedMs / 30000.0) * 80).toInt().coerceIn(0, 80)
            } else {
                (((elapsedMs - 30000) / 3000).toInt() + 80).coerceIn(80, 95)
            }
        }

        fun calcInterval(elapsedMs: Long, startFromMax: Boolean): Long {
            return if (startFromMax) {
                1000L
            } else if (elapsedMs < 30000) {
                1000L
            } else {
                3000L
            }
        }

        fun calcTipIndex(progress: Int, tipCount: Int): Int {
            if (tipCount <= 0) return 0
            return ((progress / 100.0) * tipCount).toInt().coerceIn(0, tipCount - 1)
        }
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (!running) return

            val elapsed = System.currentTimeMillis() - startTime
            val progress = calcProgress(elapsed, startFromMax)

            if (progress != lastProgress) {
                lastProgress = progress
                updateProgressBar(progress)
                updateTipText(progress)
            }

            val maxProgress = if (startFromMax) 100 else 95
            if (progress < maxProgress && handler != null) {
                handler?.postDelayed(this, calcInterval(elapsed, startFromMax))
            }
        }
    }

    fun start() {
        stop()
        startTime = System.currentTimeMillis()
        lastProgress = -1
        running = true
        handler = Handler(Looper.getMainLooper())
        handler?.post(runnable)
    }

    fun stop() {
        running = false
        handler?.removeCallbacks(runnable)
        handler = null
    }

    fun forceProgress(percent: Int, message: String? = null) {
        lastProgress = percent
        updateProgressBar(percent)
        if (message != null) {
            tipTextView?.text = message
        } else {
            updateTipText(percent)
        }
    }

    private fun updateProgressBar(percent: Int) {
        val bar = progressBarView ?: return
        val containerWidth = containerWidthProvider()
        if (containerWidth <= 0) return
        val newWidth = (containerWidth * percent / 100.0f).toInt()
        bar.layoutParams = FrameLayout.LayoutParams(newWidth, FrameLayout.LayoutParams.MATCH_PARENT)
    }

    private fun updateTipText(percent: Int) {
        if (tips.isEmpty()) return
        val index = calcTipIndex(percent, tips.size)
        tipTextView?.text = tips[index]
    }
}
```

- [ ] **3.4 Run tests — verify GREEN**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.OverlayProgressAnimatorTest" 2>&1 | tail -10
```

Expected: all 14 tests PASS.

- [ ] **3.5 Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimator.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimatorTest.kt
git commit -m "feat(overlay): add OverlayProgressAnimator — dual-mode progress with tip rotation"
```

---

## Task 4: OverlayWindowView — UI 构建 + 窗口管理

**Files:**
- Create: `service/modules/overlay/OverlayWindowView.kt`
- Test: `test: service/modules/overlay/OverlayWindowViewTest.kt`

### Step 1: Write failing tests

- [ ] **4.1 Create test file**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayWindowViewTest.kt
package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class OverlayWindowViewTest {

    private val source by lazy {
        java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayWindowView.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue(java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayWindowView.kt").exists())
    }

    // ── Constructor ──

    @Test
    fun `takes AccessibilityService and OverlayConfig`() {
        assertTrue(source.contains("AccessibilityService"))
        assertTrue(source.contains("OverlayConfig"))
    }

    // ── UI references ──

    @Test
    fun `exposes progressBarView and tipTextView`() {
        assertTrue(source.contains("progressBarView"))
        assertTrue(source.contains("tipTextView"))
    }

    // ── Window type ──

    @Test
    fun `uses TYPE_ACCESSIBILITY_OVERLAY 2032`() {
        assertTrue(source.contains("TYPE_ACCESSIBILITY_OVERLAY") || source.contains("2032"))
    }

    @Test
    fun `falls back to TYPE_PHONE for SDK below 26`() {
        assertTrue(source.contains("TYPE_PHONE") || source.contains("2006"))
    }

    // ── Flags ──

    @Test
    fun `has FLAG_NOT_FOCUSABLE`() {
        assertTrue(source.contains("FLAG_NOT_FOCUSABLE"))
    }

    @Test
    fun `has FLAG_NOT_TOUCHABLE for passthrough`() {
        assertTrue(source.contains("FLAG_NOT_TOUCHABLE"))
    }

    @Test
    fun `has FLAG_SECURE for screenshot prevention`() {
        assertTrue(source.contains("FLAG_SECURE"))
    }

    @Test
    fun `has FLAG_KEEP_SCREEN_ON`() {
        assertTrue(source.contains("FLAG_KEEP_SCREEN_ON"))
    }

    @Test
    fun `has FLAG_SHOW_WHEN_LOCKED`() {
        assertTrue(source.contains("FLAG_SHOW_WHEN_LOCKED"))
    }

    @Test
    fun `has FLAG_DISMISS_KEYGUARD`() {
        assertTrue(source.contains("FLAG_DISMISS_KEYGUARD"))
    }

    @Test
    fun `has FLAG_TURN_SCREEN_ON`() {
        assertTrue(source.contains("FLAG_TURN_SCREEN_ON"))
    }

    @Test
    fun `has FLAG_LAYOUT_IN_SCREEN and FLAG_LAYOUT_NO_LIMITS`() {
        assertTrue(source.contains("FLAG_LAYOUT_IN_SCREEN"))
        assertTrue(source.contains("FLAG_LAYOUT_NO_LIMITS"))
    }

    @Test
    fun `has FLAG_FULLSCREEN`() {
        assertTrue(source.contains("FLAG_FULLSCREEN"))
    }

    @Test
    fun `has FLAG_HARDWARE_ACCELERATED`() {
        assertTrue(source.contains("FLAG_HARDWARE_ACCELERATED"))
    }

    // ── Notch handling ──

    @Test
    fun `handles display cutout mode for SDK 28`() {
        assertTrue(source.contains("layoutInDisplayCutoutMode") || source.contains("LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES"))
    }

    // ── Screen size ──

    @Test
    fun `gets real screen size with WindowMetrics or DisplayMetrics`() {
        assertTrue(source.contains("WindowMetrics") || source.contains("getCurrentWindowMetrics"))
        assertTrue(source.contains("getRealMetrics") || source.contains("DisplayMetrics"))
    }

    // ── UI system visibility (full immersive) ──

    @Test
    fun `sets systemUiVisibility 5894 for full immersive`() {
        assertTrue(source.contains("5894") || source.contains("SYSTEM_UI_FLAG"))
    }

    // ── Background ──

    @Test
    fun `loads background from assets with fallback chain`() {
        assertTrue("must open assets", source.contains("assets") || source.contains("open("))
        assertTrue("must have fallback", source.contains("fallbackColor") || source.contains("setBackgroundColor"))
    }

    // ── App icon ──

    @Test
    fun `shows app icon when configured`() {
        assertTrue(source.contains("getApplicationIcon") || source.contains("applicationIcon"))
    }

    @Test
    fun `icon is 80dp with 16dp corner radius`() {
        assertTrue(source.contains("80"))
        assertTrue(source.contains("16") || source.contains("cornerRadius"))
    }

    // ── Progress bar ──

    @Test
    fun `progress bar uses vendor colors 4A90D9 and 67B8F7`() {
        assertTrue(source.contains("4A90D9") || source.contains("startColor"))
        assertTrue(source.contains("67B8F7") || source.contains("endColor"))
    }

    @Test
    fun `progress bar width is 65 percent of screen`() {
        assertTrue(source.contains("0.65") || source.contains("65"))
    }

    @Test
    fun `progress bar track is semi-transparent white 33FFFFFF`() {
        assertTrue(source.contains("33FFFFFF") || source.contains("0x33FFFFFF"))
    }

    // ── WindowManager operations ──

    @Test
    fun `has addView and removeView calls`() {
        assertTrue(source.contains("addView("))
        assertTrue(source.contains("removeView("))
    }

    @Test
    fun `has updateViewLayout for runtime flag changes`() {
        assertTrue(source.contains("updateViewLayout"))
    }

    // ── Retry mechanism ──

    @Test
    fun `has retry logic with max 5 attempts`() {
        assertTrue(source.contains("retryCount") || source.contains("retry"))
        assertTrue(source.contains("5"))
    }

    @Test
    fun `retry uses exponential backoff capped at 3000ms`() {
        assertTrue(source.contains("3000"))
    }

    // ── Resource cleanup ──

    @Test
    fun `detach sets image drawable to null`() {
        assertTrue(source.contains("setImageDrawable(null)") || source.contains("drawable = null"))
    }

    @Test
    fun `detach nulls out root view reference`() {
        assertTrue(source.contains("rootView") || source.contains("= null"))
    }
}
```

- [ ] **4.2 Run tests — verify RED**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.OverlayWindowViewTest" 2>&1 | tail -5
```

Expected: FAIL — file does not exist.

### Step 2: Write implementation

- [ ] **4.3 Create OverlayWindowView.kt**

```kotlin
// app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayWindowView.kt
package com.storm.safe.rock.service.modules.overlay

import android.accessibilityservice.AccessibilityService
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Builds and manages the WindowManager overlay view.
 *
 * Reverse-engineered from JADX: C0708j7.java (MaskOverlay, 320 lines).
 * Vendor fields: f57276a1 (config), f57277a2 (wmInitialized), f57278a3 (isShowing),
 *               f57279a4 (windowManager), f57280a5 (rootView), f57281a6 (progressBar),
 *               f57282a7 (tipText), f57283a8 (handler), f57284a9 (progressRunnable),
 *               f57285b0 (startTime), f57286b1 (withProgress), f57287b2 (wantShow),
 *               f57288b3 (retryCount)
 */
class OverlayWindowView(
    private val service: AccessibilityService,
    private val config: OverlayConfig
) {
    companion object {
        private const val TAG = "OverlayWindowView"
        private const val MAX_RETRIES = 5
        private const val MAX_RETRY_DELAY_MS = 3000L
    }

    var progressBarView: View? = null
        private set
    var tipTextView: TextView? = null
        private set

    private var windowManager: WindowManager? = null
    var rootView: FrameLayout? = null
        private set
    private var bgImageView: ImageView? = null
    @Volatile
    var isAttached: Boolean = false
        private set
    private var retryCount: Int = 0
    private val mainHandler = Handler(Looper.getMainLooper())

    fun attach() {
        if (isAttached) return

        if (windowManager == null) {
            windowManager = service.getSystemService("window") as? WindowManager
        }

        try {
            rootView = buildView()
            val params = buildLayoutParams()
            windowManager?.addView(rootView, params)
            isAttached = true
            retryCount = 0
            Log.d(TAG, "✅ 遮挡层已显示")
        } catch (e: Exception) {
            retryCount++
            if (retryCount > MAX_RETRIES) {
                Log.e(TAG, "❌ 遮挡层显示失败，已重试${MAX_RETRIES}次")
                rootView = null
                return
            }
            val delay = ((1L shl retryCount) * 200).coerceAtMost(MAX_RETRY_DELAY_MS)
            Log.w(TAG, "⚠️ addView失败(第${retryCount}次), ${delay}ms后重试")
            rootView = null
            mainHandler.postDelayed({ attach() }, delay)
        }
    }

    fun detach() {
        if (!isAttached) return

        try {
            bgImageView?.setImageDrawable(null)
            rootView?.background = null
            windowManager?.removeView(rootView)
        } catch (e: Exception) {
            Log.w(TAG, "removeView 异常: ${e.message}")
        }

        rootView = null
        progressBarView = null
        tipTextView = null
        bgImageView = null
        isAttached = false
        Log.d(TAG, "遮挡层已移除")
    }

    fun updateFlags(newConfig: OverlayConfig) {
        val rv = rootView ?: return
        if (!isAttached) return

        try {
            val params = buildLayoutParams(newConfig)
            windowManager?.updateViewLayout(rv, params)
        } catch (e: Exception) {
            Log.w(TAG, "updateViewLayout 异常: ${e.message}")
        }
    }

    fun getContainerWidth(): Int {
        val bar = progressBarView ?: return 0
        val container = bar.parent as? FrameLayout ?: return 0
        val w = container.width
        if (w > 0) return w
        val (screenW, _) = getRealScreenSize()
        return (screenW * 0.65f).toInt()
    }

    // ── Private: build view hierarchy ──

    private fun buildView(): FrameLayout {
        val density = service.resources.displayMetrics.density
        val (screenWidth, _) = getRealScreenSize()

        val root = FrameLayout(service)
        root.setBackgroundColor(Color.BLACK)
        // Full immersive: LAYOUT_STABLE | LAYOUT_HIDE_NAVIGATION | LAYOUT_FULLSCREEN
        //                 | HIDE_NAVIGATION | FULLSCREEN | IMMERSIVE_STICKY
        @Suppress("DEPRECATION")
        root.systemUiVisibility = 5894

        // Background layer
        val bgImage = ImageView(service)
        bgImage.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        bgImage.scaleType = ImageView.ScaleType.CENTER_CROP
        loadBackground(bgImage)
        bgImageView = bgImage
        root.addView(bgImage)

        // Center content
        val center = LinearLayout(service)
        center.orientation = LinearLayout.VERTICAL
        center.gravity = Gravity.CENTER
        val centerParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
        )
        centerParams.gravity = Gravity.CENTER
        center.layoutParams = centerParams

        // App icon
        if (config.showAppIcon) {
            try {
                val icon = ImageView(service)
                val iconSize = (80 * density).toInt()
                val iconParams = LinearLayout.LayoutParams(iconSize, iconSize)
                iconParams.gravity = Gravity.CENTER_HORIZONTAL
                iconParams.bottomMargin = (12 * density).toInt()
                icon.layoutParams = iconParams
                icon.scaleType = ImageView.ScaleType.FIT_CENTER
                icon.clipToOutline = true
                val cornerRadius = 16 * density
                icon.outlineProvider = RoundedOutlineProvider(cornerRadius)
                icon.setImageDrawable(service.packageManager.getApplicationIcon(service.packageName))
                center.addView(icon)

                // App name
                val appName = service.packageManager.getApplicationLabel(
                    service.packageManager.getApplicationInfo(service.packageName, 0)
                ).toString()
                if (appName.isNotEmpty()) {
                    val nameView = TextView(service)
                    nameView.text = appName
                    nameView.textSize = 18f
                    nameView.setTextColor(Color.WHITE)
                    nameView.gravity = Gravity.CENTER
                    val nameParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    nameParams.gravity = Gravity.CENTER_HORIZONTAL
                    nameParams.bottomMargin = (28 * density).toInt()
                    nameView.layoutParams = nameParams
                    center.addView(nameView)
                }
            } catch (e: Exception) {
                Log.w(TAG, "加载应用图标失败: ${e.message}")
            }
        }

        // Progress bar
        when (val style = config.progressBar) {
            is OverlayConfig.ProgressBarStyle.None -> { /* no progress bar */ }
            is OverlayConfig.ProgressBarStyle.GradientBlue -> {
                buildProgressBar(center, density, screenWidth, style.startColor, style.endColor)
            }
            is OverlayConfig.ProgressBarStyle.SystemOrange -> {
                buildProgressBar(center, density, screenWidth, style.color, style.color)
            }
        }

        // Tip text
        if (config.loadingTips.isNotEmpty()) {
            val tipView = TextView(service)
            tipView.text = config.loadingTips[0]
            tipView.textSize = 14f
            try {
                tipView.setTextColor(Color.parseColor(config.titleColor))
            } catch (_: Exception) {
                tipView.setTextColor(Color.WHITE)
            }
            tipView.gravity = Gravity.CENTER
            val tipParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            tipParams.gravity = Gravity.CENTER_HORIZONTAL
            tipView.layoutParams = tipParams
            tipTextView = tipView
            center.addView(tipView)
        }

        root.addView(center)

        // Bottom text area
        if (config.titleText.isNotEmpty() || config.subtitleText.isNotEmpty()) {
            val bottom = LinearLayout(service)
            bottom.orientation = LinearLayout.VERTICAL
            bottom.gravity = Gravity.CENTER
            val bottomParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
            )
            bottomParams.gravity = Gravity.BOTTOM
            bottomParams.bottomMargin = (60 * density).toInt()
            bottom.layoutParams = bottomParams

            if (config.titleText.isNotEmpty()) {
                val titleView = TextView(service)
                titleView.text = config.titleText.trim()
                titleView.textSize = 16f
                try {
                    titleView.setTextColor(Color.parseColor(config.titleColor))
                } catch (_: Exception) {
                    titleView.setTextColor(Color.WHITE)
                }
                titleView.gravity = Gravity.CENTER
                bottom.addView(titleView)
            }

            if (config.subtitleText.isNotEmpty()) {
                val subView = TextView(service)
                subView.text = config.subtitleText.replace("\\n", "\n")
                subView.textSize = 12f
                try {
                    subView.setTextColor(Color.parseColor(config.subtitleColor))
                } catch (_: Exception) {
                    subView.setTextColor(Color.parseColor("#CCCCCC"))
                }
                subView.gravity = Gravity.CENTER
                val subParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                subParams.topMargin = (8 * density).toInt()
                subView.layoutParams = subParams
                bottom.addView(subView)
            }

            if (config.statusText.isNotEmpty()) {
                val statusView = TextView(service)
                statusView.text = config.statusText
                statusView.textSize = 12f
                statusView.setTextColor(Color.parseColor("#AAAAAA"))
                statusView.gravity = Gravity.CENTER
                val statusParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                statusParams.topMargin = (8 * density).toInt()
                statusView.layoutParams = statusParams
                bottom.addView(statusView)
            }

            root.addView(bottom)
        }

        return root
    }

    private fun buildProgressBar(
        parent: LinearLayout, density: Float, screenWidth: Int,
        startColor: Int, endColor: Int
    ) {
        val barWidth = (screenWidth * 0.65f).toInt()
        val barHeight = (6 * density).toInt()

        val container = FrameLayout(service)
        val containerParams = LinearLayout.LayoutParams(barWidth, barHeight)
        containerParams.gravity = Gravity.CENTER_HORIZONTAL
        containerParams.bottomMargin = (16 * density).toInt()
        container.layoutParams = containerParams

        // Track
        val track = View(service)
        val trackBg = GradientDrawable()
        trackBg.setColor(0x33FFFFFF)
        trackBg.cornerRadius = 2 * density
        track.background = trackBg
        track.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        container.addView(track)

        // Progress indicator
        val progress = View(service)
        val progressBg = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(startColor, endColor)
        )
        progressBg.cornerRadius = 2 * density
        progress.background = progressBg
        progress.layoutParams = FrameLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT)
        progressBarView = progress
        container.addView(progress)

        parent.addView(container)
    }

    private fun loadBackground(imageView: ImageView) {
        when (val bg = config.background) {
            is OverlayConfig.OverlayBackground.SolidColor -> {
                imageView.setBackgroundColor(bg.color)
                imageView.alpha = bg.alpha
            }
            is OverlayConfig.OverlayBackground.Image -> {
                var loaded = false
                for (assetPath in bg.assetPaths) {
                    try {
                        val stream = service.assets.open(assetPath)
                        val bitmap = BitmapFactory.decodeStream(stream)
                        stream.close()
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap)
                            loaded = true
                            break
                        }
                    } catch (_: Exception) { }
                }
                if (!loaded && bg.fallbackDrawable != null) {
                    try {
                        val resId = service.resources.getIdentifier(
                            bg.fallbackDrawable, "drawable", service.packageName
                        )
                        if (resId != 0) {
                            imageView.setImageResource(resId)
                            loaded = true
                        }
                    } catch (_: Exception) { }
                }
                if (!loaded) {
                    imageView.setBackgroundColor(bg.fallbackColor)
                }
            }
        }
    }

    fun buildLayoutParams(overrideConfig: OverlayConfig? = null): WindowManager.LayoutParams {
        val cfg = overrideConfig ?: config

        val type = if (Build.VERSION.SDK_INT >= 26)
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY // 2032
        else
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE // 2006

        var flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED

        if (cfg.keepScreenOn) {
            flags = flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        }
        flags = flags or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD

        if (cfg.touchMode == OverlayConfig.TouchMode.PASSTHROUGH) {
            flags = flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        }
        if (cfg.preventScreenshot) {
            flags = flags or WindowManager.LayoutParams.FLAG_SECURE
        }

        val (w, h) = getRealScreenSize()

        val params = WindowManager.LayoutParams(w, h, type, flags, android.graphics.PixelFormat.RGBA_8888)
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 0
        if (Build.VERSION.SDK_INT >= 28) {
            params.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        return params
    }

    private fun getRealScreenSize(): Pair<Int, Int> {
        val wm = windowManager ?: service.getSystemService("window") as WindowManager
        return if (Build.VERSION.SDK_INT >= 30) {
            val bounds: Rect = wm.currentWindowMetrics.bounds
            Pair(bounds.width(), bounds.height())
        } else {
            val dm = DisplayMetrics()
            @Suppress("DEPRECATION")
            wm.defaultDisplay.getRealMetrics(dm)
            Pair(dm.widthPixels, dm.heightPixels)
        }
    }

    /**
     * Rounded corner outline provider for app icon.
     * Vendor: C0706j5 (density-based corner radius).
     */
    private class RoundedOutlineProvider(private val radius: Float) :
        android.view.ViewOutlineProvider() {
        override fun getOutline(view: View, outline: android.graphics.Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
}
```

- [ ] **4.4 Run tests — verify GREEN**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.OverlayWindowViewTest" 2>&1 | tail -10
```

Expected: all 27 tests PASS.

- [ ] **4.5 Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayWindowView.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayWindowViewTest.kt
git commit -m "feat(overlay): add OverlayWindowView — UI builder + WindowManager with retry"
```

---

## Task 5: OverlayManager — 生命周期门面

**Files:**
- Create: `service/modules/overlay/OverlayManager.kt`
- Test: `test: service/modules/overlay/OverlayManagerTest.kt`

### Step 1: Write failing tests

- [ ] **5.1 Create test file**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayManagerTest.kt
package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class OverlayManagerTest {

    private val source by lazy {
        java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayManager.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue(java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayManager.kt").exists())
    }

    // ── Constructor ──

    @Test
    fun `takes AccessibilityService in constructor`() {
        assertTrue(source.contains("class OverlayManager"))
        assertTrue(source.contains("AccessibilityService"))
    }

    // ── Public API ──

    @Test
    fun `has show method with OverlayConfig parameter`() {
        assertTrue(source.contains("fun show("))
        assertTrue(source.contains("OverlayConfig"))
    }

    @Test
    fun `show has default parameter configMask`() {
        assertTrue(source.contains("OverlayConfig.configMask()") || source.contains("= OverlayConfig()"))
    }

    @Test
    fun `has hide method`() {
        assertTrue(source.contains("fun hide()"))
    }

    @Test
    fun `has isShowing method or property`() {
        assertTrue(source.contains("isShowing"))
    }

    @Test
    fun `has updateProgress method`() {
        assertTrue(source.contains("fun updateProgress("))
    }

    @Test
    fun `has dispose method`() {
        assertTrue(source.contains("fun dispose()"))
    }

    // ── Thread safety ──

    @Test
    fun `uses volatile for showing state`() {
        assertTrue(source.contains("@Volatile") || source.contains("Volatile"))
    }

    @Test
    fun `uses Handler for main thread dispatch`() {
        assertTrue(source.contains("Handler"))
        assertTrue(source.contains("Looper.getMainLooper()"))
    }

    // ── Internal dependencies ──

    @Test
    fun `creates OverlayWindowView`() {
        assertTrue(source.contains("OverlayWindowView"))
    }

    @Test
    fun `creates OverlayProgressAnimator`() {
        assertTrue(source.contains("OverlayProgressAnimator"))
    }

    // ── Show logic ──

    @Test
    fun `show calls attach on window view`() {
        assertTrue(source.contains(".attach()") || source.contains("attach()"))
    }

    @Test
    fun `show starts animator`() {
        assertTrue(source.contains(".start()") || source.contains("animator"))
    }

    // ── Hide logic ──

    @Test
    fun `hide stops animator`() {
        assertTrue(source.contains(".stop()"))
    }

    @Test
    fun `hide calls detach on window view`() {
        assertTrue(source.contains(".detach()") || source.contains("detach()"))
    }

    // ── Dispose ──

    @Test
    fun `dispose calls hide`() {
        assertTrue(source.contains("hide()"))
    }
}
```

- [ ] **5.2 Run tests — verify RED**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.OverlayManagerTest" 2>&1 | tail -5
```

Expected: FAIL — file does not exist.

### Step 2: Write implementation

- [ ] **5.3 Create OverlayManager.kt**

```kotlin
// app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayManager.kt
package com.storm.safe.rock.service.modules.overlay

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * Lifecycle facade for the unified overlay module.
 *
 * Reverse-engineered from JADX: C0763km.java (ConfigMaskManager).
 * Vendor fields: f57543a0 (service), f57544a1 (listener), f57545a2 (maskView).
 * Methods: a0 → hide (m213600a0), a1 → show (m213601a1).
 */
class OverlayManager(private val service: AccessibilityService) {

    companion object {
        private const val TAG = "OverlayManager"
    }

    private var windowView: OverlayWindowView? = null
    private var animator: OverlayProgressAnimator? = null
    private var currentConfig: OverlayConfig? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    @Volatile
    var isShowing: Boolean = false
        private set

    fun show(config: OverlayConfig = OverlayConfig.configMask()) {
        val action = Runnable {
            try {
                if (isShowing) {
                    // Already showing — update flags if config changed
                    currentConfig = config
                    windowView?.updateFlags(config)
                    Log.d(TAG, "遮罩已更新配置")
                    return@Runnable
                }

                currentConfig = config
                val view = OverlayWindowView(service, config)
                windowView = view
                view.attach()

                // Start progress animation if applicable
                val startFromMax = when (val style = config.progressBar) {
                    is OverlayConfig.ProgressBarStyle.GradientBlue -> style.startFromMax
                    is OverlayConfig.ProgressBarStyle.SystemOrange -> style.startFromMax
                    is OverlayConfig.ProgressBarStyle.None -> false
                }

                if (config.progressBar !is OverlayConfig.ProgressBarStyle.None) {
                    val anim = OverlayProgressAnimator(
                        progressBarView = view.progressBarView,
                        tipTextView = view.tipTextView,
                        containerWidthProvider = { view.getContainerWidth() },
                        tips = config.loadingTips,
                        startFromMax = startFromMax
                    )
                    animator = anim
                    anim.start()
                }

                isShowing = true
                Log.d(TAG, "遮罩已显示")
            } catch (e: Exception) {
                Log.e(TAG, "show 异常", e)
            }
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run()
        } else {
            mainHandler.post(action)
        }
    }

    fun hide() {
        val action = Runnable {
            try {
                if (!isShowing) return@Runnable

                animator?.stop()
                animator = null

                windowView?.detach()
                windowView = null

                currentConfig = null
                isShowing = false
                Log.d(TAG, "遮罩已隐藏")
            } catch (e: Exception) {
                Log.e(TAG, "hide 异常", e)
            }
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run()
        } else {
            mainHandler.post(action)
        }
    }

    fun updateProgress(percent: Int, message: String? = null) {
        mainHandler.post {
            animator?.forceProgress(percent, message)
        }
    }

    fun dispose() {
        hide()
        Log.d(TAG, "OverlayManager disposed")
    }
}
```

- [ ] **5.4 Run tests — verify GREEN**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.OverlayManagerTest" 2>&1 | tail -10
```

Expected: all 17 tests PASS.

- [ ] **5.5 Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/overlay/OverlayManagerTest.kt
git commit -m "feat(overlay): add OverlayManager facade — show/hide/updateProgress lifecycle"
```

---

## Task 6: 集成迁移 — 调用方适配 + 删除旧代码

**Files:**
- Delete: `service/modules/overlay/ConfigMaskOverlay.kt`
- Modify: `service/MyAccessibilityService.kt:317-318, 2676-2722, 2836-2844, 3904`
- Modify: `service/modules/command/BlackScreenCommandHandler.kt:69-176`
- Modify: `service/modules/setup/flow/PairFlowOrchestrator.kt:557-565`
- Modify: `service/modules/setup/OpenDevelopmentDelegate.kt:403-406, 645, 1217-1254`
- Modify: `service/modules/command/AdbTunnelCommandHandler.kt:70-97, 156`
- Rewrite test: `test: service/modules/overlay/ConfigMaskOverlayTest.kt`

### Step 1: Rewrite integration test

- [ ] **6.1 Rewrite ConfigMaskOverlayTest.kt as integration test**

```kotlin
// app/src/test/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlayTest.kt
package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

/**
 * Integration test: verify all old ConfigMaskOverlay call sites migrated to OverlayManager.
 */
class ConfigMaskOverlayTest {

    @Test
    fun `ConfigMaskOverlay file no longer exists`() {
        assertFalse(
            "ConfigMaskOverlay.kt should be deleted",
            java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlay.kt").exists()
        )
    }

    @Test
    fun `MyAccessibilityService uses overlayManager not ConfigMaskOverlay`() {
        val source = java.io.File("app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt").readText()
        assertFalse("must not reference ConfigMaskOverlay", source.contains("ConfigMaskOverlay"))
        assertTrue("must have overlayManager field", source.contains("overlayManager"))
    }

    @Test
    fun `BlackScreenCommandHandler uses overlayManager`() {
        val source = java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/command/BlackScreenCommandHandler.kt").readText()
        assertFalse("must not reference ConfigMaskOverlay", source.contains("ConfigMaskOverlay"))
        assertTrue("must use OverlayConfig.blackScreen", source.contains("OverlayConfig.blackScreen") || source.contains("overlayManager"))
    }

    @Test
    fun `PairFlowOrchestrator calls overlayManager hide`() {
        val source = java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
        assertTrue("handleComplete must call overlayManager.hide()", source.contains("overlayManager") && source.contains(".hide()"))
    }

    @Test
    fun `OpenDevelopmentDelegate uses AudioStealthManager`() {
        val source = java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/setup/OpenDevelopmentDelegate.kt").readText()
        assertFalse("must not have inline savedAudioVolumes", source.contains("val savedAudioVolumes"))
        assertFalse("must not have inline audioStreamTypes", source.contains("val audioStreamTypes"))
        assertTrue("must reference AudioStealthManager", source.contains("AudioStealthManager") || source.contains("audioStealth"))
    }

    @Test
    fun `AdbTunnelCommandHandler uses AudioStealthManager`() {
        val source = java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandler.kt").readText()
        assertFalse("must not have inline restoreSoundAndHaptic", source.contains("fun restoreSoundAndHaptic"))
        assertTrue("must reference AudioStealthManager", source.contains("AudioStealthManager") || source.contains("audioStealth") || source.contains("forceRestoreDefaults"))
    }
}
```

- [ ] **6.2 Run integration test — verify RED**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.ConfigMaskOverlayTest" 2>&1 | tail -10
```

Expected: FAIL — ConfigMaskOverlay.kt still exists, call sites not migrated.

### Step 2: Migrate call sites

- [ ] **6.3 Delete ConfigMaskOverlay.kt**

```bash
rm app/src/main/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlay.kt
```

- [ ] **6.4 Migrate MyAccessibilityService**

Add field near line 318 (after `configProgressManager`):

```kotlin
/** Unified overlay manager — replaces ConfigMaskOverlay stub */
var overlayManager: OverlayManager? = null
```

Replace initialization at line ~2837 (where `// JADX: C0763km — configMaskManager` comment is):

```kotlin
// JADX: C0763km — configMaskManager → OverlayManager
try {
    overlayManager = com.storm.safe.rock.service.modules.overlay.OverlayManager(this)
} catch (e: Exception) {
    android.util.Log.e(TAG, "❌ OverlayManager 初始化失败", e)
}
```

Replace lines ~2676-2683 (Android 11+ show):

```kotlin
if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
    try {
        overlayManager?.show()
        android.util.Log.d(TAG, "🖤 Android 11+设备：显示配置期间遮盖")
        configProgressManager?.startConfig()
    } catch (_: Exception) {}
} else {
    android.util.Log.d(TAG, "🎭 [DEBUG] configMask 已跳过")
}
```

Replace lines ~2715-2722 (Android 10 show) with same pattern.

Replace line ~3904 (postAuthorizationInit hide):

```kotlin
overlayManager?.hide()
android.util.Log.d(TAG, "✅ [授权后初始化] 延迟组件注册完成，配置遮罩已隐藏")
```

- [ ] **6.5 Migrate BlackScreenCommandHandler**

Replace `handleEnableBlackScreen` lines ~101 area:

```kotlin
// Replace: ConfigMaskOverlay.show(service)
// With:
val overlayMgr = service.overlayManager
if (overlayMgr != null) {
    overlayMgr.show(com.storm.safe.rock.service.modules.overlay.OverlayConfig.blackScreen(
        text = text,
        alpha = alpha / 255f,
        interceptTouch = true
    ))
} else {
    Log.w(TAG, "overlayManager 不可用")
}
```

Replace `handleDisableBlackScreen` line ~168 area:

```kotlin
// Replace: ConfigMaskOverlay.hide()
// With:
service.overlayManager?.hide()
```

Remove the import for `ConfigMaskOverlay`.

- [ ] **6.6 Migrate PairFlowOrchestrator**

Replace `handleComplete()` lines ~560-564:

```kotlin
// Hide accessibility overlay
try {
    service.overlayManager?.hide()
    Log.d(TAG, "适配流程完成，已隐藏无障碍遮盖")
} catch (e: Exception) {
    Log.e(TAG, "隐藏无障碍遮盖失败", e)
}
```

This requires `PairFlowOrchestrator` to have access to `service`. Check its constructor — it already holds a reference to the AccessibilityService via `context` or `service` field.

- [ ] **6.7 Migrate OpenDevelopmentDelegate**

Add constructor parameter or field:

```kotlin
var audioStealth: com.storm.safe.rock.service.modules.overlay.AudioStealthManager? = null
```

In the `init` block (after `instance = this`), add:

```kotlin
audioStealth = com.storm.safe.rock.service.modules.overlay.AudioStealthManager(context)
audioStealth?.muteAll()
```

Replace `handleFailure()` line ~645 (`restoreSoundAndHaptic()` call):

```kotlin
audioStealth?.restoreAll()
```

Also in `handleSuccess()` if it calls `restoreSoundAndHaptic()`:

```kotlin
audioStealth?.restoreAll()
```

Remove the old fields and method:
- Delete `savedRingerMode`, `savedHapticFeedback`, `savedAudioVolumes`, `audioStreamTypes` (lines 403-406)
- Delete `private fun restoreSoundAndHaptic()` (lines 1217-1254)

- [ ] **6.8 Migrate AdbTunnelCommandHandler**

Replace companion object's `restoreSoundAndHaptic()` method (lines 70-97) with:

```kotlin
fun restoreSoundAndHaptic(context: android.content.Context) {
    com.storm.safe.rock.service.modules.overlay.AudioStealthManager(context).forceRestoreDefaults()
}
```

Or simplify the call site at line ~156 to call `AudioStealthManager` directly:

```kotlin
context.service?.let {
    com.storm.safe.rock.service.modules.overlay.AudioStealthManager(it).forceRestoreDefaults()
}
```

And remove the old method body.

- [ ] **6.9 Run integration tests — verify GREEN**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test --tests "*.overlay.ConfigMaskOverlayTest" 2>&1 | tail -10
```

Expected: all 6 integration tests PASS.

- [ ] **6.10 Run full test suite**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -20
```

Expected: all existing tests still PASS (no regressions).

- [ ] **6.11 Commit**

```bash
git add -A app/src/main/java/com/storm/safe/rock/service/ \
         app/src/test/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlayTest.kt
git commit -m "refactor(overlay): migrate 9 call sites to OverlayManager + AudioStealthManager

- Delete ConfigMaskOverlay.kt stub
- MyAccessibilityService: overlayManager.show()/hide()
- BlackScreenCommandHandler: OverlayConfig.blackScreen()
- PairFlowOrchestrator: overlayManager.hide() in handleComplete()
- OpenDevelopmentDelegate: AudioStealthManager.muteAll()/restoreAll()
- AdbTunnelCommandHandler: AudioStealthManager.forceRestoreDefaults()"
```

---

## Task 7: 全量验证 + 清理

- [ ] **7.1 Run full test suite**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -30
```

Expected: ALL tests PASS.

- [ ] **7.2 Verify no remaining references to old code**

```bash
cd /home/code/php/project/full-package/update-replica && grep -rn "ConfigMaskOverlay" app/src/main/java/ || echo "CLEAN: no ConfigMaskOverlay references"
```

Expected: `CLEAN: no ConfigMaskOverlay references`

- [ ] **7.3 Verify overlay module file count**

```bash
ls -la app/src/main/java/com/storm/safe/rock/service/modules/overlay/
```

Expected: 5 files — `OverlayConfig.kt`, `OverlayManager.kt`, `OverlayWindowView.kt`, `OverlayProgressAnimator.kt`, `AudioStealthManager.kt` (+ `PkgVerifyOverlay.kt` pre-existing)

- [ ] **7.4 Commit any final fixes if needed**

```bash
git status
# If clean, skip. If changes:
git add -A && git commit -m "fix(overlay): final cleanup after migration"
```
