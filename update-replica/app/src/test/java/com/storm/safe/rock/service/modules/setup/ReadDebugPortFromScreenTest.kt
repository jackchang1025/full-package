package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ReadDebugPortFromScreenTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `readDebugPortFromScreen method exists with IP port regex`() {
        assertTrue("readDebugPortFromScreen must exist",
            source.contains("fun readDebugPortFromScreen()"))
        val start = source.indexOf("fun readDebugPortFromScreen()")
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must use IP:port regex",
            body.contains("\\d{1,3}") && body.contains("\\d+"))
    }

    @Test
    fun `readDebugPortFromScreen validates port range`() {
        val start = source.indexOf("fun readDebugPortFromScreen()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must check 30000",
            body.contains("30000"))
        assertTrue("must check 65536",
            body.contains("65536"))
    }

    @Test
    fun `pairInWifiDebugWindow calls readDebugPortFromScreen with retry`() {
        val start = source.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must call readDebugPortFromScreen",
            body.contains("readDebugPortFromScreen()"))
        assertTrue("must retry 5 times",
            body.contains("1..5"))
    }
}
