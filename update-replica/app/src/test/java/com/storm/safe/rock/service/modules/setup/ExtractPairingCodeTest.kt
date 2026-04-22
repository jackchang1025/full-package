package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ExtractPairingCodeTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `collectAllNodes method exists and does not filter by className`() {
        assertTrue("collectAllNodes must exist", source.contains("fun collectAllNodes("))
        val start = source.indexOf("fun collectAllNodes(")
        // 只检查方法签名后 200 字符内是否有 className 过滤（实际过滤逻辑）
        val body = source.substring(start, minOf(source.length, start + 200))
        assertFalse(
            "collectAllNodes must NOT filter by className (vendor m212007f2 collects ALL nodes)",
            body.contains("className")
        )
    }

    @Test
    fun `extractPairingCodeAndPort uses split with limit 6`() {
        val start = source.indexOf("fun extractPairingCodeAndPort()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("split must use limit 6 for IPv6 safety",
            body.contains("limit = 6") || body.contains("limit=6"))
    }

    @Test
    fun `extractPairingCodeAndPort calls collectAllNodes`() {
        val start = source.indexOf("fun extractPairingCodeAndPort()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("must call collectAllNodes", body.contains("collectAllNodes("))
    }
}
