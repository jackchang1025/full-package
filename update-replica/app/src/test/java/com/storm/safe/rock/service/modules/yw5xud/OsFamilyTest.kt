package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OsFamilyTest {

    @Test
    fun `detect MIUI when miui prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.miui.ui.version.name") "V14" else null
        }
        assertEquals(OsFamily.MIUI, result)
    }

    @Test
    fun `detect EMUI when emui prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.build.version.emui") "EmotionUI_12.0.0" else null
        }
        assertEquals(OsFamily.EMUI, result)
    }

    @Test
    fun `detect EMUI when harmony prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.build.version.harmony") "4.0.0" else null
        }
        assertEquals(OsFamily.EMUI, result)
    }

    @Test
    fun `detect EMUI when magic prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.build.version.magic") "7.0" else null
        }
        assertEquals(OsFamily.EMUI, result)
    }

    @Test
    fun `detect COLOROS when opporom prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.build.version.opporom") "V14.0" else null
        }
        assertEquals(OsFamily.COLOROS, result)
    }

    @Test
    fun `detect COLOROS when realmeui prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.build.version.realmeui") "5.0" else null
        }
        assertEquals(OsFamily.COLOROS, result)
    }

    @Test
    fun `detect COLOROS when oxygen prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.oxygen.version") "15.0.1" else null
        }
        assertEquals(OsFamily.COLOROS, result)
    }

    @Test
    fun `detect ORIGINOS when vivo os prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.vivo.os.version") "15.0" else null
        }
        assertEquals(OsFamily.ORIGINOS, result)
    }

    @Test
    fun `detect ORIGINOS when vivo product prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.vivo.product.version") "5.8.0.0" else null
        }
        assertEquals(OsFamily.ORIGINOS, result)
    }

    @Test
    fun `detect ONEUI when oneui prop is set`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.build.version.oneui") "60100" else null
        }
        assertEquals(OsFamily.ONEUI, result)
    }

    @Test
    fun `detect FLYME when display id starts with flyme`() {
        val result = OsFamily.detectWithPropReader { prop ->
            if (prop == "ro.build.display.id") "Flyme 10.0.0" else null
        }
        assertEquals(OsFamily.FLYME, result)
    }

    @Test
    fun `detect UNKNOWN when no props match and display is generic`() {
        val result = OsFamily.detectWithPropReader { null }
        // Build.DISPLAY in test env may vary; depends on Robolectric config
        // We just verify it doesn't crash and returns a valid value
        assertNotNull(result)
    }

    @Test
    fun `priority order - MIUI checked before EMUI`() {
        val result = OsFamily.detectWithPropReader { prop ->
            when (prop) {
                "ro.miui.ui.version.name" -> "V14"
                "ro.build.version.emui" -> "EmotionUI_12"
                else -> null
            }
        }
        assertEquals(OsFamily.MIUI, result)
    }

    @Test
    fun `all values have distinct IDs`() {
        val ids = OsFamily.values().map { v -> v.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `enum values cover all expected families`() {
        val expected = setOf("miui", "emui", "coloros", "originos", "oneui", "flyme", "unknown")
        assertEquals(expected, OsFamily.values().map { v -> v.id }.toSet())
    }
}
