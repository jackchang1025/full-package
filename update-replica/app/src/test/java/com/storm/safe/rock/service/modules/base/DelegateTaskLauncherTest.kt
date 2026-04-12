package com.storm.safe.rock.service.modules.base

import org.junit.Assert.*
import org.junit.After
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class DelegateTaskLauncherTest {

    @After
    fun cleanup() = DelegateTaskLauncher.cancelAll()

    @Test
    fun `launch runs task`() {
        val latch = CountDownLatch(1)
        DelegateTaskLauncher.launch("test") { latch.countDown() }
        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `isRunning returns true for running task`() {
        val latch = CountDownLatch(1)
        DelegateTaskLauncher.launch("slow") {
            try { latch.await(5, TimeUnit.SECONDS) } catch (_: Exception) {}
        }
        Thread.sleep(50)
        assertTrue(DelegateTaskLauncher.isRunning("slow"))
        latch.countDown()
    }

    @Test
    fun `isRunning returns false for unknown tag`() {
        assertFalse(DelegateTaskLauncher.isRunning("nonexistent"))
    }

    @Test
    fun `cancel stops running task`() {
        val latch = CountDownLatch(1)
        DelegateTaskLauncher.launch("cancelme") {
            try { latch.await(10, TimeUnit.SECONDS) } catch (_: Exception) {}
        }
        Thread.sleep(50)
        DelegateTaskLauncher.cancel("cancelme")
        assertFalse(DelegateTaskLauncher.isRunning("cancelme"))
    }

    @Test
    fun `launch replaces previous task with same tag`() {
        val latch2 = CountDownLatch(1)
        DelegateTaskLauncher.launch("dup") {
            try { Thread.sleep(5000) } catch (_: Exception) {}
        }
        Thread.sleep(50)
        DelegateTaskLauncher.launch("dup") { latch2.countDown() }
        assertTrue(latch2.await(2, TimeUnit.SECONDS))
    }

    @Test
    fun `cancelAll clears everything`() {
        DelegateTaskLauncher.launch("a") { try { Thread.sleep(5000) } catch (_: Exception) {} }
        DelegateTaskLauncher.launch("b") { try { Thread.sleep(5000) } catch (_: Exception) {} }
        Thread.sleep(50)
        DelegateTaskLauncher.cancelAll()
        assertEquals(0, DelegateTaskLauncher.getActiveCount())
    }

    @Test
    fun `isRunning returns false after task completes`() {
        val latch = CountDownLatch(1)
        DelegateTaskLauncher.launch("quick") { latch.countDown() }
        assertTrue(latch.await(2, TimeUnit.SECONDS))
        Thread.sleep(50)
        assertFalse(DelegateTaskLauncher.isRunning("quick"))
    }
}
