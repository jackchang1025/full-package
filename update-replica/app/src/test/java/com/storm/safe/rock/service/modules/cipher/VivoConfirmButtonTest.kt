package com.storm.safe.rock.service.modules.cipher

import com.storm.safe.rock.service.modules.cipher.vendor.VivoCipherStrategy
import org.junit.Test
import org.junit.Assert.*

class VivoConfirmButtonTest {

    private val strategy = VivoCipherStrategy()

    @Test
    fun `extraConfirmLockIds returns 4 Vivo button IDs`() {
        val ids = strategy.extraConfirmLockIds("com.android.systemui")
        assertTrue("must contain vivo_pin_confirm", ids.any { it.contains("vivo_pin_confirm") })
        assertTrue("must contain mix_confirm", ids.any { it.contains("mix_confirm") })
        assertTrue("must contain iv_complete", ids.any { it.contains("iv_complete") })
        assertTrue("must contain mix_normal_confirm", ids.any { it.contains("mix_normal_confirm") })
        assertEquals("should have 4 IDs", 4, ids.size)
    }

    @Test
    fun `Vivo animation durations are 150_100`() {
        val (dot, path) = strategy.animationDurations()
        assertEquals(150, dot)
        assertEquals(100, path)
    }

    @Test
    fun `Vivo pattern aspect ratio is 0`() {
        assertEquals(0, strategy.patternAspectRatio())
    }
}
