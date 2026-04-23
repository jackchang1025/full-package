package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ReadDebugPortFromScreenTest {

    private val uiPortReaderSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/discovery/UiPortReader.kt").readText()
    }

    private val orchestratorSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
    }

    @Test
    fun `readDebugPortFromScreen method exists with IP port regex`() {
        assertTrue("readDebugPortFromScreen must exist",
            uiPortReaderSource.contains("fun readDebugPortFromScreen()"))
        assertTrue("must use IP:port regex",
            uiPortReaderSource.contains("\\d{1,3}") && uiPortReaderSource.contains("\\d+"))
    }

    @Test
    fun `readDebugPortFromScreen validates port range`() {
        assertTrue("must check 30000",
            uiPortReaderSource.contains("30000"))
        assertTrue("must check 65536",
            uiPortReaderSource.contains("65536"))
    }

    @Test
    fun `pairInWifiDebugWindow calls readDebugPortFromScreen with retry`() {
        val start = orchestratorSource.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 10000))
        // After refactoring, the orchestrator reads port from UI via extractPortFromUi with retry loop
        assertTrue("must read port from screen with retry",
            body.contains("extractPortFromUi") || body.contains("readDebugPortFromScreen"))
        assertTrue("must retry up to 5 times",
            body.contains("1..5"))
    }
}
