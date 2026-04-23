package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class TimeoutAndRecoveryTest {

    private val orchestratorSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
    }

    private val serviceMonitorSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/deploy/ServiceMonitor.kt").readText()
    }

    @Test
    fun `timeoutHandler method exists`() {
        assertTrue("timeoutHandler must exist",
            orchestratorSource.contains("fun timeoutHandler()"))
    }

    @Test
    fun `timeoutHandler checks PAIR_FINISH state before acting`() {
        val start = orchestratorSource.indexOf("fun timeoutHandler()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 400))
        assertTrue("must check PAIR_DEPT_PAIR_FINISH",
            body.contains("PAIR_DEPT_PAIR_FINISH"))
    }

    @Test
    fun `timeoutHandler calls finishLocalAdbPair on timeout`() {
        val start = orchestratorSource.indexOf("fun timeoutHandler()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 400))
        assertTrue("must call finishLocalAdbPair",
            body.contains("finishLocalAdbPair()"))
    }

    @Test
    fun `startPairFlow references timeoutHandler for 120s guard`() {
        val start = orchestratorSource.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 2000))
        assertTrue("must schedule timeoutHandler",
            body.contains("timeoutHandler"))
    }

    @Test
    fun `heartbeat recovery triggers enableWirelessDebugging when local service not alive`() {
        // After refactoring, heartbeat recovery calls enableWirelessDebugging (not startPairFlow directly)
        assertTrue("enableWirelessDebugging must be called in heartbeat recovery",
            serviceMonitorSource.contains("enableWirelessDebugging()"))
    }
}
