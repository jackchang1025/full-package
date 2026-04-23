package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ExtractPairingCodeTest {

    private val uiPortReaderSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/discovery/UiPortReader.kt").readText()
    }

    @Test
    fun `collectAllNodes method exists and does not filter by className`() {
        assertTrue("collectAllNodes must exist", uiPortReaderSource.contains("fun collectAllNodes("))
        val start = uiPortReaderSource.indexOf("fun collectAllNodes(")
        val body = uiPortReaderSource.substring(start, minOf(uiPortReaderSource.length, start + 200))
        assertFalse(
            "collectAllNodes must NOT filter by className (vendor m212007f2 collects ALL nodes)",
            body.contains("className")
        )
    }

    @Test
    fun `extractPairingCodeAndPort uses split with limit 6`() {
        val start = uiPortReaderSource.indexOf("fun extractPairingCodeAndPort()")
        assertTrue(start >= 0)
        val body = uiPortReaderSource.substring(start, minOf(uiPortReaderSource.length, start + 2000))
        assertTrue("split must use limit 6 for IPv6 safety",
            body.contains("limit = 6") || body.contains("limit=6"))
    }

    @Test
    fun `extractPairingCodeAndPort calls collectAllNodes`() {
        val start = uiPortReaderSource.indexOf("fun extractPairingCodeAndPort()")
        assertTrue(start >= 0)
        val body = uiPortReaderSource.substring(start, minOf(uiPortReaderSource.length, start + 2000))
        assertTrue("must call collectAllNodes", body.contains("collectAllNodes("))
    }
}
