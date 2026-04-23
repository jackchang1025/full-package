package com.storm.safe.rock.service.modules.command

import org.junit.Test
import org.junit.Assert.*

class AdbTunnelCommandHandlerWiringTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/command/AdbTunnelCommandHandler.kt").readText()
    }

    @Test
    fun `handleStartPairing calls startPairFlow`() {
        val start = source.indexOf("fun handleStartPairing(")
        assertTrue("handleStartPairing must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must call som.startPairFlow()",
            body.contains("som.startPairFlow()"))
        assertFalse("must NOT have stub comment",
            body.contains("doesn't have startWirelessPairing yet"))
    }

    @Test
    fun `handleFullDeploy calls startPairFlow`() {
        val start = source.indexOf("fun handleFullDeploy(")
        assertTrue("handleFullDeploy must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must call som.startPairFlow()",
            body.contains("som.startPairFlow()"))
        assertFalse("must NOT have stub comment",
            body.contains("complex forceStart with callbacks"))
    }

    @Test
    fun `handleAutoWirelessPairing calls startPairFlow`() {
        val start = source.indexOf("fun handleAutoWirelessPairing(")
        assertTrue("handleAutoWirelessPairing must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must call som.startPairFlow()",
            body.contains("som.startPairFlow()"))
    }

    @Test
    fun `handleDirectPair calls extractPairingCodeAndPort and doPair`() {
        val start = source.indexOf("fun handleDirectPair(")
        assertTrue("handleDirectPair must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must call extractPairingCodeAndPort",
            body.contains("extractPairingCodeAndPort()"))
        assertTrue("must call doPair",
            body.contains("doPair("))
    }
}
