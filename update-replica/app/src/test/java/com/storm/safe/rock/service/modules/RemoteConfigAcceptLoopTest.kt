package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class RemoteConfigAcceptLoopTest {

    private val rcmSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt").readText()
    }

    private val masSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt").readText()
    }

    @Test
    fun `start method has ServerSocket accept loop`() {
        val start = rcmSource.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = rcmSource.substring(start, minOf(rcmSource.length, start + 2000))
        assertTrue("must create ServerSocket",
            body.contains("ServerSocket("))
        assertTrue("must call accept",
            body.contains(".accept()"))
        assertTrue("must call handleClient",
            body.contains("handleClient("))
    }

    @Test
    fun `handleClient method exists with HTTP parsing`() {
        assertTrue("handleClient must exist",
            rcmSource.contains("fun handleClient("))
        val start = rcmSource.indexOf("fun handleClient(")
        val body = rcmSource.substring(start, minOf(rcmSource.length, start + 3500))
        assertTrue("must call routeRequest",
            body.contains("routeRequest("))
        assertTrue("must write HTTP response",
            body.contains("HTTP/1.1 200 OK"))
    }

    @Test
    fun `MyAccessibilityService calls rcm start`() {
        val idx = masSource.indexOf("RemoteConfigManager(applicationContext)")
        assertTrue("must create RemoteConfigManager", idx >= 0)
        val surrounding = masSource.substring(idx, minOf(masSource.length, idx + 200))
        assertTrue("must call .start() after creation",
            surrounding.contains(".start()"))
    }
}
