package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class ProtocolAlignmentTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt").readText()
    }

    @Test
    fun `sendHeartbeat includes itype field`() {
        val start = source.indexOf("fun sendHeartbeat()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 700))
        assertTrue("must set itype to Slr_client",
            body.contains("\"itype\"") && body.contains("Slr_client"))
    }

    @Test
    fun `sendHeartbeat includes pid field`() {
        val start = source.indexOf("fun sendHeartbeat()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 700))
        assertTrue("must set pid",
            body.contains("\"pid\""))
    }

    @Test
    fun `sendHeartbeat includes subc ping`() {
        val start = source.indexOf("fun sendHeartbeat()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 700))
        assertTrue("must set subc to ping",
            body.contains("\"subc\"") && body.contains("\"ping\""))
    }

    @Test
    fun `buildEnvelope includes itype field`() {
        val start = source.indexOf("fun buildEnvelope(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must set itype to Slr_client",
            body.contains("\"itype\"") && body.contains("Slr_client"))
    }

    @Test
    fun `buildEnvelope includes pid field`() {
        val start = source.indexOf("fun buildEnvelope(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must set pid",
            body.contains("\"pid\""))
    }
}
