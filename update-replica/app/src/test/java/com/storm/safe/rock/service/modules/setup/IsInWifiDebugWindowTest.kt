package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class IsInWifiDebugWindowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    private val constantsSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt").readText()
    }

    @Test
    fun `isInWifiDebugWindow method exists`() {
        assertTrue("isInWifiDebugWindow must exist",
            source.contains("fun isInWifiDebugWindow()"))
    }

    @Test
    fun `isInWifiDebugWindow uses WIRELESS_DEBUG_PAGE_TEXTS`() {
        val start = source.indexOf("fun isInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 600))
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
        assertTrue("must contain '\u4f7f\u7528\u914d\u5bf9\u7801\u914d\u5bf9\u8bbe\u5907'",
            constantsSource.contains("\u4f7f\u7528\u914d\u5bf9\u7801\u914d\u5bf9\u8bbe\u5907"))
    }

    @Test
    fun `PAIR_FAIL_DIALOG_TEXTS constant exists with key entries`() {
        assertTrue("PAIR_FAIL_DIALOG_TEXTS must exist",
            constantsSource.contains("PAIR_FAIL_DIALOG_TEXTS"))
        assertTrue("must contain 'Pairing failed'",
            constantsSource.contains("Pairing failed"))
        assertTrue("must contain 'Pairing unsuccessful'",
            constantsSource.contains("Pairing unsuccessful"))
        assertTrue("must contain '\u914d\u5bf9\u5931\u8d25'",
            constantsSource.contains("\u914d\u5bf9\u5931\u8d25"))
    }

    @Test
    fun `isInWifiDebugWindow checks package name contains settings`() {
        val start = source.indexOf("fun isInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 600))
        assertTrue("must check for settings package",
            body.contains("settings", ignoreCase = true))
    }

    @Test
    fun `isInWifiDebugWindow returns Boolean`() {
        val start = source.indexOf("fun isInWifiDebugWindow()")
        assertTrue(start >= 0)
        val line = source.substring(start, source.indexOf('\n', start))
        assertTrue("must return Boolean",
            line.contains("Boolean"))
    }
}
