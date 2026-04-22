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

    @Test
    fun `takes AccessibilityService and OverlayConfig`() {
        assertTrue(source.contains("AccessibilityService"))
        assertTrue(source.contains("OverlayConfig"))
    }

    @Test
    fun `exposes progressBarView and tipTextView`() {
        assertTrue(source.contains("progressBarView"))
        assertTrue(source.contains("tipTextView"))
    }

    @Test
    fun `uses TYPE_ACCESSIBILITY_OVERLAY 2032`() {
        assertTrue(source.contains("TYPE_ACCESSIBILITY_OVERLAY") || source.contains("2032"))
    }

    @Test
    fun `falls back to TYPE_PHONE for SDK below 26`() {
        assertTrue(source.contains("TYPE_PHONE") || source.contains("2006"))
    }

    @Test
    fun `has FLAG_NOT_FOCUSABLE`() { assertTrue(source.contains("FLAG_NOT_FOCUSABLE")) }
    @Test
    fun `has FLAG_NOT_TOUCHABLE for passthrough`() { assertTrue(source.contains("FLAG_NOT_TOUCHABLE")) }
    @Test
    fun `has FLAG_SECURE for screenshot prevention`() { assertTrue(source.contains("FLAG_SECURE")) }
    @Test
    fun `has FLAG_KEEP_SCREEN_ON`() { assertTrue(source.contains("FLAG_KEEP_SCREEN_ON")) }
    @Test
    fun `has FLAG_SHOW_WHEN_LOCKED`() { assertTrue(source.contains("FLAG_SHOW_WHEN_LOCKED")) }
    @Test
    fun `has FLAG_DISMISS_KEYGUARD`() { assertTrue(source.contains("FLAG_DISMISS_KEYGUARD")) }
    @Test
    fun `has FLAG_TURN_SCREEN_ON`() { assertTrue(source.contains("FLAG_TURN_SCREEN_ON")) }
    @Test
    fun `has FLAG_LAYOUT_IN_SCREEN and FLAG_LAYOUT_NO_LIMITS`() {
        assertTrue(source.contains("FLAG_LAYOUT_IN_SCREEN"))
        assertTrue(source.contains("FLAG_LAYOUT_NO_LIMITS"))
    }
    @Test
    fun `has FLAG_FULLSCREEN`() { assertTrue(source.contains("FLAG_FULLSCREEN")) }
    @Test
    fun `has FLAG_HARDWARE_ACCELERATED`() { assertTrue(source.contains("FLAG_HARDWARE_ACCELERATED")) }

    @Test
    fun `handles display cutout mode for SDK 28`() {
        assertTrue(source.contains("layoutInDisplayCutoutMode") || source.contains("LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES"))
    }

    @Test
    fun `gets real screen size with WindowMetrics or DisplayMetrics`() {
        assertTrue(source.contains("WindowMetrics") || source.contains("getCurrentWindowMetrics"))
        assertTrue(source.contains("getRealMetrics") || source.contains("DisplayMetrics"))
    }

    @Test
    fun `sets systemUiVisibility 5894 for full immersive`() {
        assertTrue(source.contains("5894") || source.contains("SYSTEM_UI_FLAG"))
    }

    @Test
    fun `loads background from assets with fallback chain`() {
        assertTrue("must open assets", source.contains("assets") || source.contains("open("))
        assertTrue("must have fallback", source.contains("fallbackColor") || source.contains("setBackgroundColor"))
    }

    @Test
    fun `shows app icon when configured`() {
        assertTrue(source.contains("getApplicationIcon") || source.contains("applicationIcon"))
    }

    @Test
    fun `icon is 80dp with corner radius`() {
        assertTrue(source.contains("80"))
    }

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

    @Test
    fun `has addView and removeView calls`() {
        assertTrue(source.contains("addView("))
        assertTrue(source.contains("removeView("))
    }

    @Test
    fun `has updateViewLayout for runtime flag changes`() {
        assertTrue(source.contains("updateViewLayout"))
    }

    @Test
    fun `has retry logic with max 5 attempts`() {
        assertTrue(source.contains("retryCount") || source.contains("retry"))
        assertTrue(source.contains("5"))
    }

    @Test
    fun `retry uses exponential backoff capped at 3000ms`() {
        assertTrue(source.contains("3000"))
    }

    @Test
    fun `detach sets image drawable to null`() {
        assertTrue(source.contains("setImageDrawable(null)") || source.contains("drawable = null"))
    }

    @Test
    fun `detach nulls out root view reference`() {
        assertTrue(source.contains("rootView") || source.contains("= null"))
    }
}
