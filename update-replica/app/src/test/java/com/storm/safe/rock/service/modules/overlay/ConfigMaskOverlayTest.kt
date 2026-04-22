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
            java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/ConfigMaskOverlay.kt").exists()
        )
    }

    @Test
    fun `MyAccessibilityService uses overlayManager not ConfigMaskOverlay`() {
        val source = java.io.File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt").readText()
        assertFalse("must not reference ConfigMaskOverlay", source.contains("ConfigMaskOverlay"))
        assertTrue("must have overlayManager field", source.contains("overlayManager"))
    }

    @Test
    fun `BlackScreenCommandHandler uses overlayManager`() {
        val source = java.io.File("src/main/java/com/storm/safe/rock/service/modules/command/BlackScreenCommandHandler.kt").readText()
        assertFalse("must not reference ConfigMaskOverlay", source.contains("ConfigMaskOverlay"))
        assertTrue("must use OverlayConfig.blackScreen or overlayManager", source.contains("OverlayConfig.blackScreen") || source.contains("overlayManager"))
    }

    @Test
    fun `PairFlowOrchestrator calls overlayManager hide`() {
        val source = java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
        assertTrue("handleComplete must call overlayManager.hide()", source.contains("overlayManager") && source.contains(".hide()"))
    }

    @Test
    fun `OpenDevelopmentDelegate uses AudioStealthManager`() {
        val source = java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/OpenDevelopmentDelegate.kt").readText()
        assertFalse("must not have inline savedAudioVolumes", source.contains("val savedAudioVolumes"))
        assertFalse("must not have inline audioStreamTypes", source.contains("val audioStreamTypes"))
        assertTrue("must reference AudioStealthManager", source.contains("AudioStealthManager") || source.contains("audioStealth"))
    }

    @Test
    fun `AdbTunnelCommandHandler uses AudioStealthManager`() {
        val source = java.io.File("src/main/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandler.kt").readText()
        assertTrue("must reference AudioStealthManager or forceRestoreDefaults", source.contains("AudioStealthManager") || source.contains("forceRestoreDefaults"))
    }
}
