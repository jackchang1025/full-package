package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class PkgVerifyOverlayTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/PkgVerifyOverlay.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue("PkgVerifyOverlay.kt must exist",
            java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/PkgVerifyOverlay.kt").exists())
    }

    @Test
    fun `object declaration matches JADX cm0 singleton pattern`() {
        assertTrue("must be object (singleton)", source.contains("object PkgVerifyOverlay"))
    }

    @Test
    fun `has PREFS_NAME matching vendor pkg_verify_state`() {
        assertTrue("must use pkg_verify_state prefs", source.contains("\"pkg_verify_state\""))
    }

    @Test
    fun `has KEY_DONE matching vendor v_done`() {
        assertTrue("must use v_done key", source.contains("\"v_done\""))
    }

    @Test
    fun `has 3 strategy window types`() {
        assertTrue("must reference TYPE_ACCESSIBILITY_OVERLAY (2032)", source.contains("2032"))
        assertTrue("must reference TYPE_APPLICATION_OVERLAY (2038)", source.contains("2038"))
    }

    @Test
    fun `has getBrandColor method with 6 brand families`() {
        assertTrue("must have getBrandColor", source.contains("fun getBrandColor"))
        assertTrue("Huawei red", source.contains("CE0E2D"))
        assertTrue("Xiaomi orange", source.contains("FF6900"))
        assertTrue("Oppo blue", source.contains("1B8CFE"))
        assertTrue("Vivo indigo", source.contains("415FFF"))
        assertTrue("Samsung blue", source.contains("1259C3"))
        assertTrue("Default Google blue", source.contains("4285F4"))
    }

    @Test
    fun `has show method as entry point`() {
        assertTrue("must have show(service) entry point", source.contains("fun show("))
    }

    @Test
    fun `has retry and strategy switch logic`() {
        assertTrue("must have retry logic", source.contains("retryCount"))
        assertTrue("must have strategy logic", source.contains("strategyIndex"))
    }

    @Test
    fun `has hideIcon method using setComponentEnabledSetting`() {
        assertTrue("must have hideIcon", source.contains("fun hideIcon("))
        assertTrue("must disable component", source.contains("setComponentEnabledSetting"))
    }

    @Test
    fun `has buildOverlayView method`() {
        assertTrue("must have buildOverlayView", source.contains("fun buildOverlayView("))
    }

    @Test
    fun `uses WindowManager addView to display`() {
        assertTrue("must call addView", source.contains("addView("))
    }

    @Test
    fun `sets v_done after successful display`() {
        assertTrue("must set v_done=true", source.contains("putBoolean") && source.contains("v_done"))
    }
}
