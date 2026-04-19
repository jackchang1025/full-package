package com.storm.safe.rock.p000

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class WebViewManagerTest {
    private val sourceFile = File("src/main/java/com/storm/safe/rock/p000/WebViewManager.kt")

    @Test
    fun `source file exists`() {
        assertTrue(sourceFile.exists())
    }

    @Test
    fun `has activity field referencing iuzxujjtqev`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("iuzxujjtqev"))
    }

    @Test
    fun `has webView field`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("var webView"))
    }

    @Test
    fun `has initialize method`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("fun initialize("))
    }

    @Test
    fun `enables JavaScript`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("javaScriptEnabled = true"))
    }

    @Test
    fun `enables DOM storage`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("domStorageEnabled = true"))
    }

    @Test
    fun `sets MainWebViewClient`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("MainWebViewClient"))
    }

    @Test
    fun `sets MainWebChromeClient`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("MainWebChromeClient"))
    }

    @Test
    fun `adds JavaScript interface with name Android`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("\"Android\""))
    }

    @Test
    fun `uses MainJsBridge`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("MainJsBridge"))
    }

    @Test
    fun `has webViewContainer field`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("webViewContainer"))
    }

    @Test
    fun `has fullScreenVideoContainer field`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("fullScreenVideoContainer"))
    }
}
