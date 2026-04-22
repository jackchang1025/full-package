package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class StartPairFlowAlignmentTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `startPairFlow has three-way dispatch including isInWifiDebugWindow`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue("startPairFlow must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must call isInDevOptionsWindow",
            body.contains("isInDevOptionsWindow()"))
        assertTrue("must call isInWifiDebugWindow",
            body.contains("isInWifiDebugWindow()"))
        assertTrue("must dispatch pairInWifiDebugWindow",
            body.contains("pairInWifiDebugWindow"))
    }

    @Test
    fun `startPairFlow schedules 120s timeout via timeoutHandler`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must schedule timeoutHandler",
            body.contains("timeoutHandler"))
    }

    @Test
    fun `openDevOptionsRetryV2 has three-way detection`() {
        val start = source.indexOf("fun openDevOptionsRetryV2()")
        assertTrue("openDevOptionsRetryV2 must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must call isInDevOptionsWindow",
            body.contains("isInDevOptionsWindow()"))
        assertTrue("must call isInWifiDebugWindow",
            body.contains("isInWifiDebugWindow()"))
    }

    @Test
    fun `openDevOptionsRetryV2 dispatches pairInWifiDebugWindow when on wifi debug page`() {
        val start = source.indexOf("fun openDevOptionsRetryV2()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must add pairInWifiDebugWindow to queue",
            body.contains("\"pairInWifiDebugWindow\""))
    }
}
