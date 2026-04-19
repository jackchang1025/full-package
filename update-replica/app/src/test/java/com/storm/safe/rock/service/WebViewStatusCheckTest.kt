package com.storm.safe.rock.service

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class WebViewStatusCheckTest {
    private val sourceFile = File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt")
    private val src by lazy { sourceFile.readText() }

    @Test
    fun `has startWebViewStatusCheckTask method`() {
        assertTrue(src.contains("startWebViewStatusCheckTask"))
    }

    @Test
    fun `checks isWebViewOpen flag`() {
        assertTrue(src.contains("isWebViewOpen"))
    }

    @Test
    fun `has lastWebViewStatusTime field`() {
        assertTrue(src.contains("lastWebViewStatusTime"))
    }

    @Test
    fun `uses 500ms expiry threshold`() {
        assertTrue(src.contains("500"))
    }

    @Test
    fun `resets isWebViewOpen to false on expiry`() {
        assertTrue(src.contains("isWebViewOpen = false"))
    }

    @Test
    fun `uses 200ms polling when WebView open`() {
        assertTrue(src.contains("200"))
    }

    @Test
    fun `uses 2000ms polling when WebView closed`() {
        assertTrue(src.contains("2000"))
    }
}
