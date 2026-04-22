package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class TimeoutAndRecoveryTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `timeoutHandler method exists`() {
        assertTrue("timeoutHandler must exist",
            source.contains("fun timeoutHandler()"))
    }

    @Test
    fun `timeoutHandler checks PAIR_FINISH state before acting`() {
        val start = source.indexOf("fun timeoutHandler()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must check PAIR_DEPT_PAIR_FINISH",
            body.contains("PAIR_DEPT_PAIR_FINISH"))
    }

    @Test
    fun `timeoutHandler calls finishLocalAdbPair on timeout`() {
        val start = source.indexOf("fun timeoutHandler()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must call finishLocalAdbPair",
            body.contains("finishLocalAdbPair()"))
    }

    @Test
    fun `startPairFlow references timeoutHandler for 120s guard`() {
        val start = source.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must schedule timeoutHandler",
            body.contains("timeoutHandler"))
    }

    @Test
    fun `heartbeat recovery triggers startPairFlow after enableWirelessDebugging`() {
        val idx = source.indexOf("enableWirelessDebuggingViaSettings()")
        assertTrue("enableWirelessDebuggingViaSettings must exist", idx >= 0)
        val surrounding = source.substring(
            maxOf(0, idx - 200),
            minOf(source.length, idx + 500)
        )
        assertTrue("heartbeat recovery must trigger startPairFlow after re-enabling wireless debug",
            surrounding.contains("startPairFlow()"))
    }
}
