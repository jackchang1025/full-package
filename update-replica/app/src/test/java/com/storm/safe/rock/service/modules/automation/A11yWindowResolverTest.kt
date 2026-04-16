package com.storm.safe.rock.service.modules.automation

import org.junit.Assert.*
import org.junit.Test

class A11yWindowResolverTest {

    private val vendorRequired = listOf(
        "com.android.settings",
        "com.android.systemui",
        "com.android.permissioncontroller",
        "com.miui.securitycenter",
        "com.miui.permcenter",
        "com.coloros.safecenter",
        "com.vivo.permissionmanager",
        "com.huawei.systemmanager",
        "com.samsung.android.lool",
        "com.oneplus.security",
        "com.honor.systemmanager",
        "com.transsion.permissionmanager",
        "com.meizu.safe",
        "com.smartisanos.security",
        "com.lenovo.safecenter",
        "com.xiaomi.misettings"
    )

    @Test
    fun `SETTINGS_PACKAGES contains all vendor required entries`() {
        for (pkg in vendorRequired) {
            assertTrue(
                "SETTINGS_PACKAGES missing '$pkg'",
                A11yWindowResolver.SETTINGS_PACKAGES.contains(pkg)
            )
        }
    }

    @Test
    fun `SETTINGS_PACKAGES size at least 19`() {
        assertTrue(
            "size=${A11yWindowResolver.SETTINGS_PACKAGES.size}, expected >=19",
            A11yWindowResolver.SETTINGS_PACKAGES.size >= 19
        )
    }

    @Test
    fun `startsWith matching works for sub-packages`() {
        val pkg = "com.android.settings.SubActivity"
        val matched = A11yWindowResolver.SETTINGS_PACKAGES.any {
            it == pkg || pkg.startsWith("$it.")
        }
        assertTrue("Sub-package should match via startsWith", matched)
    }

    @Test
    fun `unrelated packages do not match`() {
        val unrelated = listOf("com.miui.home", "com.android.launcher3", "com.whatsapp")
        for (pkg in unrelated) {
            val matched = A11yWindowResolver.SETTINGS_PACKAGES.any {
                it == pkg || pkg.startsWith("$it.")
            }
            assertFalse("'$pkg' should NOT match", matched)
        }
    }
}
