package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class OverlayConfigTest {

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

    @Test
    fun `TouchMode has exactly 2 values`() {
        assertEquals(2, OverlayConfig.TouchMode.values().size)
        assertNotNull(OverlayConfig.TouchMode.PASSTHROUGH)
        assertNotNull(OverlayConfig.TouchMode.INTERCEPT)
    }

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
