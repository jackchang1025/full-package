package com.storm.safe.rock.service.modules.yw5xud.huawei

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

/**
 * HarmonyVersionDetector TDD — 双路径 HarmonyOS 版本检测。
 * 对齐 vendor C0365a2.java L265-322。
 */
@RunWith(RobolectricTestRunner::class)
class HarmonyVersionDetectorTest {

    @Test
    fun `parseDisplayVersion returns HARMONY_OS_4 for 'harmonyos 4' display`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_4,
            HarmonyVersionDetector.parseDisplayVersion("HarmonyOS 4.2.0.123")
        )
    }

    @Test
    fun `parseDisplayVersion returns HARMONY_OS_3 for 'harmonyos 3'`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_3,
            HarmonyVersionDetector.parseDisplayVersion("harmonyos 3.0.0")
        )
    }

    @Test
    fun `parseDisplayVersion returns HARMONY_OS_2 for 'harmonyos 2'`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_2,
            HarmonyVersionDetector.parseDisplayVersion("HarmonyOS 2.0.1")
        )
    }

    @Test
    fun `parseDisplayVersion returns NOT_HARMONY for EMUI display`() {
        assertEquals(
            HarmonyVersionDetector.Version.NOT_HARMONY,
            HarmonyVersionDetector.parseDisplayVersion("EMUI 12.0.0")
        )
    }

    @Test
    fun `parseDisplayVersion returns NOT_HARMONY for empty string`() {
        assertEquals(
            HarmonyVersionDetector.Version.NOT_HARMONY,
            HarmonyVersionDetector.parseDisplayVersion("")
        )
    }

    @Test
    fun `parseOsName returns HARMONY_OS_UNKNOWN for reflective 'Harmony'`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_UNKNOWN,
            HarmonyVersionDetector.parseOsName("Harmony")
        )
    }

    @Test
    fun `parseOsName returns NOT_HARMONY for non-Harmony name`() {
        assertEquals(
            HarmonyVersionDetector.Version.NOT_HARMONY,
            HarmonyVersionDetector.parseOsName("Android")
        )
    }

    @Test
    fun `isHarmonyOS returns true for any HARMONY_OS version`() {
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_4.isHarmony)
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_3.isHarmony)
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_2.isHarmony)
        assertTrue(HarmonyVersionDetector.Version.HARMONY_OS_UNKNOWN.isHarmony)
        assertFalse(HarmonyVersionDetector.Version.NOT_HARMONY.isHarmony)
    }

    @Test
    fun `parseDisplayVersion returns HARMONY_OS_UNKNOWN for plain 'harmonyos' without version`() {
        assertEquals(
            HarmonyVersionDetector.Version.HARMONY_OS_UNKNOWN,
            HarmonyVersionDetector.parseDisplayVersion("HarmonyOS 1.0")
        )
    }

    @Test
    fun `detect returns HARMONY_OS_4 when Build_DISPLAY is HarmonyOS 4`() {
        val originalDisplay = android.os.Build.DISPLAY
        try {
            ReflectionHelpers.setStaticField(android.os.Build::class.java, "DISPLAY", "HarmonyOS 4.2.0")
            assertEquals(HarmonyVersionDetector.Version.HARMONY_OS_4, HarmonyVersionDetector.detect())
        } finally {
            ReflectionHelpers.setStaticField(android.os.Build::class.java, "DISPLAY", originalDisplay)
        }
    }

    @Test
    fun `detect returns NOT_HARMONY when Build_DISPLAY is EMUI and reflection fails`() {
        val originalDisplay = android.os.Build.DISPLAY
        try {
            ReflectionHelpers.setStaticField(android.os.Build::class.java, "DISPLAY", "EMUI 12.0.0")
            // On JVM test env, com.huawei.system.BuildEx is NOT on classpath → reflection returns NOT_HARMONY
            assertEquals(HarmonyVersionDetector.Version.NOT_HARMONY, HarmonyVersionDetector.detect())
        } finally {
            ReflectionHelpers.setStaticField(android.os.Build::class.java, "DISPLAY", originalDisplay)
        }
    }

    @Test
    fun `detect returns HARMONY_OS_UNKNOWN for plain harmonyos display`() {
        val originalDisplay = android.os.Build.DISPLAY
        try {
            ReflectionHelpers.setStaticField(android.os.Build::class.java, "DISPLAY", "HarmonyOS 1.0.1")
            assertEquals(HarmonyVersionDetector.Version.HARMONY_OS_UNKNOWN, HarmonyVersionDetector.detect())
        } finally {
            ReflectionHelpers.setStaticField(android.os.Build::class.java, "DISPLAY", originalDisplay)
        }
    }
}
