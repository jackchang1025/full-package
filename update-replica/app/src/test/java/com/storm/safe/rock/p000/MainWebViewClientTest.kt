package com.storm.safe.rock.p000

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class MainWebViewClientTest {
    private val sourceFile = File("src/main/java/com/storm/safe/rock/p000/MainWebViewClient.kt")

    @Test
    fun `source file exists`() {
        assertTrue(sourceFile.exists())
    }

    @Test
    fun `extends WebViewClient`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("WebViewClient"))
    }

    @Test
    fun `overrides onPageFinished`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("override fun onPageFinished"))
    }

    @Test
    fun `references WebViewManager`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("WebViewManager"))
    }
}
