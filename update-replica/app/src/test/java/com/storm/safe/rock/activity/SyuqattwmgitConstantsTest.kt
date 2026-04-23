package com.storm.safe.rock.activity

import org.junit.Test
import org.junit.Assert.*

/**
 * Vendor constant alignment verification — source-level scan of syuqattwmgit.kt
 * per Plan 2026-04-16-biometric-credential-verification-alignment Task 1.
 */
class SyuqattwmgitConstantsTest {

    private val source: String by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/activity/syuqattwmgit.kt").readText()
    }

    @Test
    fun `gravity is END or TOP matching vendor 8388661`() {
        // vendor L239: attributes.gravity = 8388661 = Gravity.END | Gravity.TOP
        val hasEndTop = source.contains("Gravity.END") && source.contains("Gravity.TOP") &&
            !source.contains("CENTER_VERTICAL")
        assertTrue(
            "gravity must be Gravity.END | Gravity.TOP (vendor 8388661), no CENTER_VERTICAL",
            hasEndTop
        )
    }

    @Test
    fun `addFlags includes NOT_TOUCH_MODAL not NOT_FOCUSABLE`() {
        // vendor L243: addFlags(32) = FLAG_NOT_TOUCH_MODAL
        val hasTouchModal = source.contains("FLAG_NOT_TOUCH_MODAL")
        assertTrue("addFlags(FLAG_NOT_TOUCH_MODAL) must be present", hasTouchModal)
    }

    @Test
    fun `addFlags includes IGNORE_CHEEK_PRESSES not LAYOUT_IN_SCREEN`() {
        // vendor L247: addFlags(262144) = FLAG_IGNORE_CHEEK_PRESSES
        val hasIgnoreCheek = source.contains("FLAG_IGNORE_CHEEK_PRESSES") ||
            source.contains("0x40000")
        assertTrue(
            "addFlags must use FLAG_IGNORE_CHEEK_PRESSES (262144 / 0x40000)",
            hasIgnoreCheek
        )
    }

    @Test
    fun `addFlags keeps TRANSLUCENT_STATUS and TRANSLUCENT_NAVIGATION`() {
        assertTrue(
            "addFlags(FLAG_TRANSLUCENT_STATUS) 必须保留",
            source.contains("FLAG_TRANSLUCENT_STATUS") || source.contains("0x4000000")
        )
        assertTrue(
            "addFlags(FLAG_TRANSLUCENT_NAVIGATION) 必须保留",
            source.contains("FLAG_TRANSLUCENT_NAVIGATION") || source.contains("0x8000000")
        )
    }
}
