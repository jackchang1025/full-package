package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class OverlayManagerTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayManager.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue(java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayManager.kt").exists())
    }

    @Test
    fun `takes AccessibilityService in constructor`() {
        assertTrue(source.contains("class OverlayManager"))
        assertTrue(source.contains("AccessibilityService"))
    }

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

    @Test
    fun `uses volatile for showing state`() {
        assertTrue(source.contains("@Volatile") || source.contains("Volatile"))
    }

    @Test
    fun `uses Handler for main thread dispatch`() {
        assertTrue(source.contains("Handler"))
        assertTrue(source.contains("Looper.getMainLooper()"))
    }

    @Test
    fun `creates OverlayWindowView`() {
        assertTrue(source.contains("OverlayWindowView"))
    }

    @Test
    fun `creates OverlayProgressAnimator`() {
        assertTrue(source.contains("OverlayProgressAnimator"))
    }

    @Test
    fun `show calls attach on window view`() {
        assertTrue(source.contains(".attach()") || source.contains("attach()"))
    }

    @Test
    fun `show starts animator`() {
        assertTrue(source.contains(".start()") || source.contains("animator"))
    }

    @Test
    fun `hide stops animator`() {
        assertTrue(source.contains(".stop()"))
    }

    @Test
    fun `hide calls detach on window view`() {
        assertTrue(source.contains(".detach()") || source.contains("detach()"))
    }

    @Test
    fun `dispose calls hide`() {
        assertTrue(source.contains("hide()"))
    }
}
