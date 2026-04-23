package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DebugPortSyncTest {

    private val orchestratorSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
    }

    private val portScannerSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/discovery/PortScanner.kt").readText()
    }

    @Test
    fun `pairInWifiDebugWindow saves debug port after pairing success`() {
        val start = orchestratorSource.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 10000))
        assertTrue("must set PAIR_SUCCESS",
            body.contains("PAIR_DEPT_PAIR_SUCCESS"))
        assertTrue("must call saveDebugPortAndSync",
            body.contains("saveDebugPortAndSync"))
    }

    @Test
    fun `getWirelessDebugPort method exists in PortScanner`() {
        assertTrue("getWirelessDebugPort must exist",
            portScannerSource.contains("fun getWirelessDebugPort()"))
    }

    @Test
    fun `saveDebugPortAndSync method exists in PortScanner`() {
        assertTrue("saveDebugPortAndSync must exist",
            portScannerSource.contains("fun saveDebugPortAndSync("))
    }
}
