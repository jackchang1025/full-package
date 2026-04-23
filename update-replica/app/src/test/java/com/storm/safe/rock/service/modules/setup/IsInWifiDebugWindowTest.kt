package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class IsInWifiDebugWindowTest {

    private val wirelessDebugNavSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/WirelessDebugNavigator.kt").readText()
    }

    private val constantsSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt").readText()
    }

    @Test
    fun `isInWifiDebugWindow method exists`() {
        assertTrue("isInWifiDebugWindow must exist in WirelessDebugNavigator",
            wirelessDebugNavSource.contains("fun isInWifiDebugWindow()"))
    }

    @Test
    fun `isInWifiDebugWindow uses WIRELESS_DEBUG_PAGE_TEXTS`() {
        val start = wirelessDebugNavSource.indexOf("fun isInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = wirelessDebugNavSource.substring(start, minOf(wirelessDebugNavSource.length, start + 1200))
        assertTrue("must reference WIRELESS_DEBUG_PAGE_TEXTS",
            body.contains("WIRELESS_DEBUG_PAGE_TEXTS"))
    }

    @Test
    fun `WIRELESS_DEBUG_PAGE_TEXTS constant exists with key entries`() {
        assertTrue("WIRELESS_DEBUG_PAGE_TEXTS must exist",
            constantsSource.contains("WIRELESS_DEBUG_PAGE_TEXTS"))
        assertTrue("must contain 'IP address & port'",
            constantsSource.contains("IP address & port"))
        assertTrue("must contain 'Pair device with pairing code'",
            constantsSource.contains("Pair device with pairing code"))
        assertTrue("must contain '使用配对码配对设备'",
            constantsSource.contains("使用配对码配对设备"))
    }

    @Test
    fun `PAIR_FAIL_DIALOG_TEXTS constant exists with key entries`() {
        assertTrue("PAIR_FAIL_DIALOG_TEXTS must exist",
            constantsSource.contains("PAIR_FAIL_DIALOG_TEXTS"))
        assertTrue("must contain 'Pairing failed'",
            constantsSource.contains("Pairing failed"))
        assertTrue("must contain 'Pairing unsuccessful'",
            constantsSource.contains("Pairing unsuccessful"))
        assertTrue("must contain '配对失败'",
            constantsSource.contains("配对失败"))
    }

    @Test
    fun `isInWifiDebugWindow checks package name contains settings`() {
        val start = wirelessDebugNavSource.indexOf("fun isInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = wirelessDebugNavSource.substring(start, minOf(wirelessDebugNavSource.length, start + 600))
        assertTrue("must check for settings package",
            body.contains("settings", ignoreCase = true))
    }

    @Test
    fun `isInWifiDebugWindow returns Boolean`() {
        val start = wirelessDebugNavSource.indexOf("fun isInWifiDebugWindow()")
        assertTrue(start >= 0)
        val line = wirelessDebugNavSource.substring(start, wirelessDebugNavSource.indexOf('\n', start))
        assertTrue("must return Boolean",
            line.contains("Boolean"))
    }
}
