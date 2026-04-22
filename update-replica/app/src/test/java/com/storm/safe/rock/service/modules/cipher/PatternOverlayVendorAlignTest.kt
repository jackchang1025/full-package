package com.storm.safe.rock.service.modules.cipher

import com.storm.safe.rock.service.modules.cipher.vendor.*
import org.junit.Test
import org.junit.Assert.*

class PatternOverlayVendorAlignTest {

    // --- Animation Durations ---

    @Test
    fun `Samsung animation durations are 100_200`() {
        val (dot, path) = SamsungCipherStrategy().animationDurations()
        assertEquals(100, dot)
        assertEquals(200, path)
    }

    @Test
    fun `MIUI animation durations are 50_50`() {
        val (dot, path) = MiuiCipherStrategy().animationDurations()
        assertEquals(50, dot)
        assertEquals(50, path)
    }

    @Test
    fun `Generic animation durations are 150_100`() {
        val (dot, path) = GenericCipherStrategy().animationDurations()
        assertEquals(150, dot)
        assertEquals(100, path)
    }

    @Test
    fun `Huawei animation durations are 150_100`() {
        val (dot, path) = HuaweiCipherStrategy().animationDurations()
        assertEquals(150, dot)
        assertEquals(100, path)
    }

    @Test
    fun `Tecno animation durations are 150_100`() {
        val (dot, path) = TecnoCipherStrategy().animationDurations()
        assertEquals(150, dot)
        assertEquals(100, path)
    }

    // --- Aspect Ratio ---

    @Test
    fun `OPPO pattern aspect ratio is 1 (square)`() {
        assertEquals(1, OppoCipherStrategy().patternAspectRatio())
    }

    @Test
    fun `Samsung pattern aspect ratio is 0`() {
        assertEquals(0, SamsungCipherStrategy().patternAspectRatio())
    }

    @Test
    fun `Generic pattern aspect ratio is 0`() {
        assertEquals(0, GenericCipherStrategy().patternAspectRatio())
    }

    // --- Outer Circle Alpha ---

    @Test
    fun `OPPO has outerCircleAlpha resource name`() {
        assertEquals("coui_lock_pattern_outer_circle_max_alpha", OppoCipherStrategy().outerCircleAlphaResourceName())
    }

    @Test
    fun `Samsung has no outerCircleAlpha resource`() {
        assertNull(SamsungCipherStrategy().outerCircleAlphaResourceName())
    }

    @Test
    fun `Generic has no outerCircleAlpha resource`() {
        assertNull(GenericCipherStrategy().outerCircleAlphaResourceName())
    }

    // --- Extra Confirm Lock IDs ---

    @Test
    fun `Vivo has 4 extra confirm lock IDs`() {
        val ids = VivoCipherStrategy().extraConfirmLockIds("com.android.systemui")
        assertEquals(4, ids.size)
    }

    @Test
    fun `Generic has 0 extra confirm lock IDs`() {
        val ids = GenericCipherStrategy().extraConfirmLockIds("com.android.systemui")
        assertEquals(0, ids.size)
    }

    @Test
    fun `OPPO has 0 extra confirm lock IDs`() {
        val ids = OppoCipherStrategy().extraConfirmLockIds("com.android.systemui")
        assertEquals(0, ids.size)
    }
}
