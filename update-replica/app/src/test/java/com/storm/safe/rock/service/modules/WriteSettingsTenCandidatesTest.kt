package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class WriteSettingsTenCandidatesTest {

    /**
     * vendor C0327b2.m211716a5 (lines 1720-1956) — 10 candidate coords given rect.top.
     * Order per vendor source.
     */
    @Test
    fun `buildCandidates generates 10 points in vendor order`() {
        val W = 1080
        val rectTop = 500
        val candidates = MainOrchestrator.buildWriteSettingsCandidates(screenWidthPx = W, rectTop = rectTop)

        assertEquals(10, candidates.size)
        assertEquals(Pair(W - 150f, rectTop - 110f), candidates[0])
        assertEquals(Pair(W - 160f, rectTop - 120f), candidates[1])
        assertEquals(Pair(W - 140f, rectTop - 100f), candidates[2])
        assertEquals(Pair(W - 130f, rectTop - 90f), candidates[3])
        assertEquals(Pair(W - 110f, rectTop - 70f), candidates[4])
        assertEquals(Pair(W - 120f, rectTop - 80f), candidates[5])
        assertEquals(Pair(W - 170f, rectTop - 130f), candidates[6])
        assertEquals(Pair(W - 70f,  rectTop - 180f), candidates[7])
        assertEquals(Pair(W - 70f,  rectTop - 200f), candidates[8])
        assertEquals(Pair(W - 70f,  rectTop - 210f), candidates[9])
    }

    @Test
    fun `CANCEL_RETRY_MAX_ATTEMPTS is 3 matching real-device evidence`() {
        assertEquals(3, MainOrchestrator.WRITE_SETTINGS_CANCEL_RETRY_MAX_ATTEMPTS)
    }

    @Test
    fun `CANCEL_RETRY_DELAY_MS is 5L or less (tight retry)`() {
        assertTrue(MainOrchestrator.WRITE_SETTINGS_CANCEL_RETRY_DELAY_MS <= 5L)
    }

    @Test
    fun `WRITE_SETTINGS_TAP_DURATION_MS is 100L matching vendor C0327b2`() {
        assertEquals(100L, MainOrchestrator.WRITE_SETTINGS_TAP_DURATION_MS)
    }
}
