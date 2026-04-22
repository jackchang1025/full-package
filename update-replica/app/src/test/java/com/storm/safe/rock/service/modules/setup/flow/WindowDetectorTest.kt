package com.storm.safe.rock.service.modules.setup.flow

import org.junit.Assert.*
import org.junit.Test

class WindowDetectorTest {

    @Test
    fun `initial state has null pkg and cls`() {
        val detector = WindowDetector()
        assertNull(detector.currentPkg)
        assertNull(detector.currentCls)
    }

    @Test
    fun `update sets pkg and cls`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity")
        assertEquals("com.android.settings", detector.currentPkg)
        assertEquals("com.android.settings.Settings\$DevelopmentSettingsActivity", detector.currentCls)
    }

    @Test
    fun `matchesAny with exact match returns true`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity")
        assertTrue(detector.matchesAny(WindowPatterns.devOptionsPatterns()))
    }

    @Test
    fun `matchesAny with MiuiSettings returns true`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.MiuiSettings")
        assertTrue(detector.matchesAny(WindowPatterns.devOptionsPatterns()))
    }

    @Test
    fun `matchesAny with launcher returns false`() {
        val detector = WindowDetector()
        detector.update("com.miui.home", "android.widget.FrameLayout")
        assertFalse(detector.matchesAny(WindowPatterns.devOptionsPatterns()))
    }

    @Test
    fun `matchesAny with SubSettings returns true`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.SubSettings")
        assertTrue(detector.matchesAny(WindowPatterns.devOptionsPatterns()))
    }

    @Test
    fun `wifiDebug requires settings pkg and known cls`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "some.random.Activity")
        assertFalse(detector.matchesAny(WindowPatterns.wifiDebugPatterns()))
        detector.update("com.android.settings", "com.android.settings.SubSettings")
        assertTrue(detector.matchesAny(WindowPatterns.wifiDebugPatterns()))
    }

    @Test
    fun `isInDevOptionsWindow delegates to matchesAny`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.MiuiSettings")
        assertTrue(detector.isInDevOptionsWindow())
    }

    @Test
    fun `isInWifiDebugWindow delegates to matchesAny`() {
        val detector = WindowDetector()
        detector.update("com.android.settings", "com.android.settings.SubSettings")
        assertTrue(detector.isInWifiDebugWindow())
    }
}
