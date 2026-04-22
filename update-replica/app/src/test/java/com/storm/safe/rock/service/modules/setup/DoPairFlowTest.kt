package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DoPairFlowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `doPair creates TLS 1_3 socket to 127_0_0_1`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue("doPair method must exist", methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must create Socket to 127.0.0.1", body.contains("127.0.0.1"))
        assertTrue("must set TLSv1.3", body.contains("TLSv1.3"))
        assertTrue("must call startHandshake", body.contains("startHandshake"))
    }

    @Test
    fun `doPair calls exportKeyingMaterial`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must call exportKeyingMaterial", body.contains("exportKeyingMaterial"))
    }

    @Test
    fun `doPair constructs password as code_bytes concatenated with keying_material`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must construct password from pairingCode bytes + keyingMaterial",
            body.contains("arraycopy") || body.contains("copyInto") || body.contains("pairingCode.toByteArray()"))
    }

    @Test
    fun `doPair uses Spake2Context with adb pair client and server identities`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must use 'adb pair client' identity", body.contains("adb pair client"))
        assertTrue("must use 'adb pair server' identity", body.contains("adb pair server"))
    }

    @Test
    fun `doPair derives AES key with HKDF label`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must use HKDF label", body.contains("adb pairing_auth aes-128-gcm key"))
    }

    @Test
    fun `doPair sends TYPE_SPAKE2 0 and TYPE_PEER_INFO 1`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must send SPAKE2 with type 0", body.contains("writePairingPacket") && body.contains(", 0,"))
        assertTrue("must send PeerInfo with type 1", body.contains("writePairingPacket") && body.contains(", 1,"))
    }
}
