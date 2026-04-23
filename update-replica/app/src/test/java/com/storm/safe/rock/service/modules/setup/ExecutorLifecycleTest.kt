package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ExecutorLifecycleTest {

    private val orchestratorSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
    }

    @Test
    fun `executor is declared as var not val`() {
        assertTrue("executor must be var for rebuild",
            orchestratorSource.contains("var executor: ScheduledExecutorService"))
        assertFalse("executor must NOT be val",
            orchestratorSource.contains("val executor: ScheduledExecutorService"))
    }

    @Test
    fun `startPairFlow rebuilds executor when shutdown`() {
        val start = orchestratorSource.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 1500))
        assertTrue("must check isShutdown",
            body.contains("executor.isShutdown"))
        assertTrue("must create new executor",
            body.contains("Executors.newSingleThreadScheduledExecutor()"))
    }

    @Test
    fun `startPairFlow does not just log warning on shutdown`() {
        val start = orchestratorSource.indexOf("fun startPairFlow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 1500))
        assertFalse("must NOT just warn about shutdown",
            body.contains("部分任务可能无法调度"))
    }
}
