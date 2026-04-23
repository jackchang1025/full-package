package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class StartPairFlowAlignmentTest {

    private val orchestratorSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
    }

    @Test
    fun `startPairFlow has three-way dispatch including isInWifiDebugWindow`() {
        val start = orchestratorSource.indexOf("fun startPairFlow()")
        assertTrue("startPairFlow must exist", start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 2000))
        assertTrue("must call isInDevOptionsWindow",
            body.contains("isInDevOptionsWindow()"))
        assertTrue("must call isInWifiDebugWindow",
            body.contains("isInWifiDebugWindow()"))
        assertTrue("must dispatch pairInWifiDebugWindow",
            body.contains("pairInWifiDebugWindow"))
    }

    @Test
    fun `startPairFlow schedules 120s timeout via timeoutHandler`() {
        val start = orchestratorSource.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 2000))
        assertTrue("must schedule timeoutHandler",
            body.contains("timeoutHandler"))
    }

    @Test
    fun `startPairFlow dispatches pairInDevOption when on dev options page`() {
        val start = orchestratorSource.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 2000))
        assertTrue("must dispatch pairInDevOption",
            body.contains("pairInDevOption"))
    }

    @Test
    fun `startPairFlow enables wireless debugging via settings as fallback`() {
        val start = orchestratorSource.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 3000))
        assertTrue("must call enableWirelessDebuggingViaSettings",
            body.contains("enableWirelessDebuggingViaSettings"))
    }
}
