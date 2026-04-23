package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class RemoteConfigThreadPoolTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt").readText()
    }

    @Test
    fun `start uses fixed thread pool of 8`() {
        val start = source.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must use newFixedThreadPool",
            body.contains("newFixedThreadPool(8)"))
    }

    @Test
    fun `accept loop submits to thread pool not synchronous`() {
        val start = source.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must submit to executor",
            body.contains("executor?.submit"))
    }

    @Test
    fun `start tries multiple ports and skips 7912`() {
        val start = source.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must skip LOCAL_SERVICE_PORT",
            body.contains("LOCAL_SERVICE_PORT"))
        assertTrue("must try port range",
            body.contains("port + 8"))
    }
}
