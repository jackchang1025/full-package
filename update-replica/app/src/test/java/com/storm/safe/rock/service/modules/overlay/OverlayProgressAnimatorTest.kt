package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class OverlayProgressAnimatorTest {

    private val source by lazy {
        java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimator.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue(java.io.File("app/src/main/java/com/storm/safe/rock/service/modules/overlay/OverlayProgressAnimator.kt").exists())
    }

    @Test
    fun `has start stop forceProgress methods`() {
        assertTrue(source.contains("fun start()"))
        assertTrue(source.contains("fun stop()"))
        assertTrue(source.contains("fun forceProgress("))
    }

    @Test
    fun `calcProgress mode B before 30s returns 0 to 80`() {
        assertEquals(0, OverlayProgressAnimator.calcProgress(0L, false))
        assertEquals(40, OverlayProgressAnimator.calcProgress(15000L, false))
        assertEquals(80, OverlayProgressAnimator.calcProgress(30000L, false))
    }

    @Test
    fun `calcProgress mode B after 30s returns 80 to 95`() {
        assertEquals(80, OverlayProgressAnimator.calcProgress(30000L, false))
        assertEquals(81, OverlayProgressAnimator.calcProgress(33000L, false))
        assertEquals(85, OverlayProgressAnimator.calcProgress(45000L, false))
        assertEquals(95, OverlayProgressAnimator.calcProgress(75000L, false))
        assertEquals(95, OverlayProgressAnimator.calcProgress(120000L, false))
    }

    @Test
    fun `calcProgress mode A returns 80 to 100 over 60s`() {
        assertEquals(80, OverlayProgressAnimator.calcProgress(0L, true))
        assertEquals(90, OverlayProgressAnimator.calcProgress(30000L, true))
        assertEquals(100, OverlayProgressAnimator.calcProgress(60000L, true))
        assertEquals(100, OverlayProgressAnimator.calcProgress(90000L, true))
    }

    @Test
    fun `calcInterval mode B before 30s returns 1000ms`() {
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(0L, false))
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(29000L, false))
    }

    @Test
    fun `calcInterval mode B after 30s returns 3000ms`() {
        assertEquals(3000L, OverlayProgressAnimator.calcInterval(30000L, false))
        assertEquals(3000L, OverlayProgressAnimator.calcInterval(60000L, false))
    }

    @Test
    fun `calcInterval mode A always returns 1000ms`() {
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(0L, true))
        assertEquals(1000L, OverlayProgressAnimator.calcInterval(30000L, true))
    }

    @Test
    fun `calcTipIndex maps progress to tip index`() {
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(0, 5))
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(19, 5))
        assertEquals(1, OverlayProgressAnimator.calcTipIndex(20, 5))
        assertEquals(2, OverlayProgressAnimator.calcTipIndex(40, 5))
        assertEquals(3, OverlayProgressAnimator.calcTipIndex(60, 5))
        assertEquals(4, OverlayProgressAnimator.calcTipIndex(80, 5))
        assertEquals(4, OverlayProgressAnimator.calcTipIndex(100, 5))
    }

    @Test
    fun `calcTipIndex with 0 tips returns 0`() {
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(50, 0))
    }

    @Test
    fun `calcTipIndex with 1 tip always returns 0`() {
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(0, 1))
        assertEquals(0, OverlayProgressAnimator.calcTipIndex(99, 1))
    }

    @Test
    fun `uses Handler for scheduling`() {
        assertTrue(source.contains("Handler"))
        assertTrue(source.contains("Looper.getMainLooper()"))
    }

    @Test
    fun `stop removes callbacks`() {
        assertTrue(source.contains("removeCallbacks"))
    }
}
