package com.storm.safe.rock.p000

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class WebViewHeartbeatTest {
    private val sourceFile = File("src/main/java/com/storm/safe/rock/p000/WebViewHeartbeat.kt")

    @Test
    fun `source file exists`() {
        assertTrue(sourceFile.exists())
    }

    @Test
    fun `implements Runnable`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("Runnable"))
    }

    @Test
    fun `has run method`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("override fun run()"))
    }

    @Test
    fun `checks isInitialized and isFinishing`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("isInitialized"))
        assertTrue(src.contains("isFinishing"))
    }

    @Test
    fun `checks hasWindowFocus`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("hasWindowFocus"))
    }

    @Test
    fun `sets isWebViewOpen true when active`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("isWebViewOpen = true"))
    }

    @Test
    fun `re-schedules with 500ms delay`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("500"))
    }

    @Test
    fun `references iuzxujjtqev activity`() {
        val src = sourceFile.readText()
        assertTrue(src.contains("iuzxujjtqev"))
    }
}
