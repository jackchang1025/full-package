package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DebugPortSyncTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `pairInWifiDebugWindow saves debug port after pairing success`() {
        val start = source.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        val successIdx = body.indexOf("PAIR_DEPT_PAIR_SUCCESS")
        assertTrue("must set PAIR_SUCCESS", successIdx >= 0)
        val afterSuccess = body.substring(successIdx)
        assertTrue("must call getWirelessDebugPort after success",
            afterSuccess.contains("getWirelessDebugPort()"))
        assertTrue("must call saveDebugPortAndSync",
            afterSuccess.contains("saveDebugPortAndSync"))
    }

    @Test
    fun `getWirelessDebugPort method exists`() {
        assertTrue("getWirelessDebugPort must exist",
            source.contains("fun getWirelessDebugPort()"))
    }

    @Test
    fun `saveDebugPortAndSync method exists`() {
        assertTrue("saveDebugPortAndSync must exist",
            source.contains("fun saveDebugPortAndSync("))
    }
}
