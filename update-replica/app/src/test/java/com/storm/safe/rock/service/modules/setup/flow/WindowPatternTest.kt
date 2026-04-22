package com.storm.safe.rock.service.modules.setup.flow

import org.junit.Assert.*
import org.junit.Test

class WindowPatternTest {

    @Test
    fun `pattern with exact pkg and cls matches`() {
        val pattern = WindowPattern(
            pkg = "com.android.settings",
            cls = "com.android.settings.Settings\$DevelopmentSettingsActivity"
        )
        assertTrue(pattern.matches("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity"))
        assertFalse(pattern.matches("com.miui.home", "android.widget.FrameLayout"))
    }

    @Test
    fun `pattern with null pkg matches any pkg`() {
        val pattern = WindowPattern(pkg = null, cls = null)
        assertTrue(pattern.matches("com.android.settings", "any.Class"))
        assertTrue(pattern.matches("com.miui.home", "any.Class"))
    }

    @Test
    fun `pattern with null cls matches any cls`() {
        val pattern = WindowPattern(pkg = "com.android.settings", cls = null)
        assertTrue(pattern.matches("com.android.settings", "any.Activity"))
        assertFalse(pattern.matches("com.miui.home", "any.Activity"))
    }

    @Test
    fun `devOptionsPatterns returns 6 patterns`() {
        val patterns = WindowPatterns.devOptionsPatterns()
        assertEquals(6, patterns.size)
        assertEquals("com.android.settings.Settings\$DevelopmentSettingsDashboardActivity", patterns[0].cls)
        assertEquals("com.android.settings.Settings\$DevelopmentSettingsActivity", patterns[1].cls)
        assertTrue(patterns.any { it.cls == "com.android.settings.MiuiSettings" })
        assertTrue(patterns.any { it.cls == "com.hihonor.settingslib.SubSettings" })
        assertTrue(patterns.all { it.pkg == "com.android.settings" })
    }

    @Test
    fun `wifiDebugPatterns returns 3 patterns without null catch-all`() {
        val patterns = WindowPatterns.wifiDebugPatterns()
        assertEquals(3, patterns.size)
        assertTrue(patterns.all { it.pkg != null })
    }
}
