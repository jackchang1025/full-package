package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DeployLocalServiceTest {

    private val deployerSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/deploy/LocalServiceDeployer.kt").readText()
    }

    private val orchestratorSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt").readText()
    }

    @Test
    fun `deployLocalService checks if file exists via shell`() {
        val start = deployerSource.indexOf("fun deployLocalService(")
        assertTrue(start >= 0)
        val body = deployerSource.substring(start, minOf(deployerSource.length, start + 3000))
        // SVC constant holds /data/local/tmp/local-service, used via FILE_CHECK format string
        assertTrue("must check file existence via FILE_CHECK or SVC",
            body.contains("FILE_CHECK") || body.contains("/data/local/tmp/local-service"))
        assertTrue("must use executeShellCommand or executeAndCheck",
            body.contains("executeShellCommand") || body.contains("executeAndCheck"))
    }

    @Test
    fun `deployLocalService tries native lib copy before download`() {
        val start = deployerSource.indexOf("fun deployLocalService(")
        assertTrue(start >= 0)
        val body = deployerSource.substring(start, minOf(deployerSource.length, start + 3000))
        val nativeIdx = body.indexOf("nativeLibraryDir")
        val downloadIdx = body.indexOf("rathat.me")
        assertTrue("must try nativeLibraryDir", nativeIdx >= 0)
        assertTrue("must have download fallback", downloadIdx >= 0)
        assertTrue("native lib must be tried before download", nativeIdx < downloadIdx)
    }

    @Test
    fun `deployLocalService calls fireAndForget to start`() {
        val start = deployerSource.indexOf("fun deployLocalService(")
        assertTrue(start >= 0)
        val body = deployerSource.substring(start, minOf(deployerSource.length, start + 3000))
        assertTrue("must call fireAndForget",
            body.contains("fireAndForget()"))
    }

    @Test
    fun `postDeployInit method exists and calls setAppPackage`() {
        assertTrue("postDeployInit must exist",
            deployerSource.contains("fun postDeployInit("))
        val start = deployerSource.indexOf("fun postDeployInit(")
        val body = deployerSource.substring(start, minOf(deployerSource.length, start + 1500))
        assertTrue("must call /setAppPackage",
            body.contains("/setAppPackage"))
        assertTrue("must call /applyAllOptimizations",
            body.contains("/applyAllOptimizations"))
    }

    @Test
    fun `pairInWifiDebugWindow calls deployLocalServiceWithRetry after success`() {
        val start = orchestratorSource.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = orchestratorSource.substring(start, minOf(orchestratorSource.length, start + 10000))
        assertTrue("must call deployLocalServiceWithRetry after pairing success",
            body.contains("deployLocalServiceWithRetry"))
    }
}
