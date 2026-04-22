package com.storm.safe.rock.service.modules.cipher

import org.junit.Test
import org.junit.Assert.*

class VivoConfirmButtonTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/cipher/CipherCaptureManager.kt").readText()
    }

    @Test
    fun `isInConfirmLockScreen contains vivo_pin_confirm`() {
        val methodIdx = source.indexOf("fun isInConfirmLockScreen(")
        assertTrue("isInConfirmLockScreen must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 1200).coerceAtMost(source.length))
        assertTrue("must contain vivo_pin_confirm", body.contains("vivo_pin_confirm"))
    }

    @Test
    fun `isInConfirmLockScreen contains mix_confirm (vendor L838)`() {
        val methodIdx = source.indexOf("fun isInConfirmLockScreen(")
        assertTrue("isInConfirmLockScreen must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 1200).coerceAtMost(source.length))
        assertTrue("must contain mix_confirm", body.contains("mix_confirm"))
    }

    @Test
    fun `isInConfirmLockScreen contains iv_complete (vendor L839)`() {
        val methodIdx = source.indexOf("fun isInConfirmLockScreen(")
        assertTrue("isInConfirmLockScreen must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 1200).coerceAtMost(source.length))
        assertTrue("must contain iv_complete", body.contains("iv_complete"))
    }

    @Test
    fun `isInConfirmLockScreen contains mix_normal_confirm (vendor L841)`() {
        val methodIdx = source.indexOf("fun isInConfirmLockScreen(")
        assertTrue("isInConfirmLockScreen must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 1200).coerceAtMost(source.length))
        assertTrue("must contain mix_normal_confirm", body.contains("mix_normal_confirm"))
    }
}
