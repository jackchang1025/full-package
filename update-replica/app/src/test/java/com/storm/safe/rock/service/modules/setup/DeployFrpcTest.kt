package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DeployFrpcTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `deployFrpcBinary method exists`() {
        assertTrue("deployFrpcBinary must exist",
            source.contains("fun deployFrpcBinary()"))
    }

    @Test
    fun `deployFrpcBinary copies from nativeLibraryDir first`() {
        val start = source.indexOf("fun deployFrpcBinary()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("must check nativeLibraryDir",
            body.contains("nativeLibraryDir"))
        assertTrue("must reference libfrpc.so",
            body.contains("libfrpc.so"))
    }

    @Test
    fun `deployFrpcBinary has XOR decrypt fallback`() {
        val start = source.indexOf("fun deployFrpcBinary()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("must have XOR key",
            body.contains("K9qZ-XlN7Q"))
    }

    @Test
    fun `postDeployInit calls deployFrpcBinary`() {
        val start = source.indexOf("fun postDeployInit()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must call deployFrpcBinary",
            body.contains("deployFrpcBinary()"))
    }

    @Test
    fun `notifyLocalServiceConfig uses getServerAddr not hardcoded empty`() {
        val start = source.indexOf("fun notifyLocalServiceConfig()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must call getServerAddr",
            body.contains("getServerAddr()"))
        assertFalse("must NOT hardcode empty serverAddr",
            body.contains("\"serverAddr\":\"\""))
    }

    @Test
    fun `getServerAddr checks debug flag`() {
        assertTrue("getServerAddr must exist",
            source.contains("fun getServerAddr()"))
        val start = source.indexOf("fun getServerAddr()")
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must check debug_server_addr",
            body.contains("debug_server_addr"))
    }
}
