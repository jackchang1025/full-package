package com.storm.safe.rock.service.modules.yw5xud.common

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FoldableDeviceDetectorTest {

    @Test
    fun `isAspectRatioFoldable returns true for wide aspect ratio 0_7`() {
        assertTrue(FoldableDeviceDetector.isAspectRatioFoldable(1400, 2000))
    }

    @Test
    fun `isAspectRatioFoldable returns true for ratio exactly 0_6`() {
        assertTrue(FoldableDeviceDetector.isAspectRatioFoldable(1200, 2000))
    }

    @Test
    fun `isAspectRatioFoldable returns false for ratio 0_5 (phone)`() {
        assertFalse(FoldableDeviceDetector.isAspectRatioFoldable(1080, 2160))
    }

    @Test
    fun `isModelFoldable matches Mate X keyword`() {
        assertTrue(FoldableDeviceDetector.isModelFoldable("HUAWEI Mate X3"))
        assertTrue(FoldableDeviceDetector.isModelFoldable("MATE X5"))
    }

    @Test
    fun `isModelFoldable matches Magic V keyword`() {
        assertTrue(FoldableDeviceDetector.isModelFoldable("Honor Magic V2"))
    }

    @Test
    fun `isModelFoldable does not match regular Mate`() {
        assertFalse(FoldableDeviceDetector.isModelFoldable("HUAWEI Mate 60 Pro"))
        assertFalse(FoldableDeviceDetector.isModelFoldable("FIN-AL60"))
    }

    @Test
    fun `activateLeftPanel returns false when service is null`() {
        assertFalse(FoldableDeviceDetector.activateLeftPanel(null))
    }

    @Test
    fun `isFoldable returns true when only model matches (ratio alone would fail)`() {
        // Pure OR-logic test: ratio < 0.6 but model matches → true
        // Covers the short-circuit OR wiring between the two predicates
        // (previously only implicit via sum of individual predicate tests)
        // Requires the internal extracted overload — see additional MINOR fix below
        assertTrue(FoldableDeviceDetector.isFoldable(widthPx = 1080, heightPx = 2400, model = "HUAWEI Mate X3"))
    }

    @Test
    fun `isFoldable returns true when only ratio matches (model alone would fail)`() {
        assertTrue(FoldableDeviceDetector.isFoldable(widthPx = 1400, heightPx = 2000, model = "FIN-AL60"))
    }

    @Test
    fun `isFoldable returns false when neither ratio nor model matches`() {
        assertFalse(FoldableDeviceDetector.isFoldable(widthPx = 1080, heightPx = 2400, model = "FIN-AL60"))
    }
}
