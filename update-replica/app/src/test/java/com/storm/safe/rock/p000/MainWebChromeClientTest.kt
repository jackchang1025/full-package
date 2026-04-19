package com.storm.safe.rock.p000

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class MainWebChromeClientTest {
    private val sourceFile = File("src/main/java/com/storm/safe/rock/p000/MainWebChromeClient.kt")

    @Test
    fun `source file exists`() {
        assertTrue(sourceFile.exists())
    }

    @Test
    fun `extends WebChromeClient`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("WebChromeClient"))
    }

    @Test
    fun `overrides onShowCustomView`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("override fun onShowCustomView"))
    }

    @Test
    fun `overrides onHideCustomView`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("override fun onHideCustomView"))
    }

    @Test
    fun `overrides onShowFileChooser`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("override fun onShowFileChooser"))
    }

    @Test
    fun `has customViewCallback field`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("CustomViewCallback"))
    }

    @Test
    fun `handles fullscreen video container visibility`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("fullScreenVideoContainer"))
    }

    @Test
    fun `sets system UI visibility for immersive mode`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("systemUiVisibility") || src.contains("setSystemUiVisibility"))
    }
}
