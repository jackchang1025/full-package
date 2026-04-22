package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DeployLocalServiceTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `deployLocalService checks if file exists via shell`() {
        val start = source.indexOf("fun deployLocalService()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must check file existence",
            body.contains("/data/local/tmp/local-service"))
        assertTrue("must use executeShellCommand or executeAndCheck",
            body.contains("executeShellCommand") || body.contains("executeAndCheck"))
    }

    @Test
    fun `deployLocalService tries native lib copy before download`() {
        val start = source.indexOf("fun deployLocalService()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        val nativeIdx = body.indexOf("nativeLibraryDir")
        val downloadIdx = body.indexOf("rathat.me")
        assertTrue("must try nativeLibraryDir", nativeIdx >= 0)
        assertTrue("must have download fallback", downloadIdx >= 0)
        assertTrue("native lib must be tried before download", nativeIdx < downloadIdx)
    }

    @Test
    fun `deployLocalService calls fireAndForget to start`() {
        val start = source.indexOf("fun deployLocalService()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must call fireAndForget",
            body.contains("fireAndForget()"))
    }

    @Test
    fun `postDeployInit method exists and calls setAppPackage`() {
        assertTrue("postDeployInit must exist",
            source.contains("fun postDeployInit()"))
        val start = source.indexOf("fun postDeployInit()")
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must call /setAppPackage",
            body.contains("/setAppPackage"))
        assertTrue("must call /applyAllOptimizations",
            body.contains("/applyAllOptimizations"))
    }

    @Test
    fun `pairInWifiDebugWindow calls deployLocalService after success`() {
        val start = source.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 4000))
        assertTrue("must call deployLocalService after pairing success",
            body.contains("deployLocalService()"))
    }
}
