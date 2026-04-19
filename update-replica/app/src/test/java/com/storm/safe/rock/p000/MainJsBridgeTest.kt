package com.storm.safe.rock.p000

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class MainJsBridgeTest {
    private val sourceFile = File("src/main/java/com/storm/safe/rock/p000/MainJsBridge.kt")

    @Test
    fun `source file exists`() {
        assertTrue("MainJsBridge.kt must exist", sourceFile.exists())
    }

    @Test
    fun `has JavascriptInterface annotation on processWebClick`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("@JavascriptInterface"))
        assertTrue(src.contains("fun processWebClick"))
    }

    @Test
    fun `processWebClick takes String parameter`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("fun processWebClick(url: String)"))
    }

    @Test
    fun `class references WebViewManager`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("WebViewManager"))
    }
}
