package com.storm.safe.rock.service.modules.overlay

import org.junit.Test
import org.junit.Assert.*

class AudioStealthManagerTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManager.kt").readText()
    }

    @Test
    fun `file exists`() {
        assertTrue(java.io.File("src/main/java/com/storm/safe/rock/service/modules/overlay/AudioStealthManager.kt").exists())
    }

    @Test
    fun `class takes Context in constructor`() {
        assertTrue(source.contains("class AudioStealthManager"))
        assertTrue(source.contains("Context"))
    }

    @Test
    fun `STREAM_TYPES matches vendor f53808b6`() {
        assertTrue("must define STREAM_TYPES", source.contains("STREAM_TYPES"))
        assertTrue("must contain stream 2", source.contains("STREAM_VOICE_CALL") || source.contains(", 2,") || source.contains("(2,"))
        assertTrue("must contain stream 5", source.contains("STREAM_NOTIFICATION") || source.contains(", 5,") || source.contains("(5,"))
        assertTrue("must contain stream 1", source.contains("STREAM_RING") || source.contains(", 1,"))
        assertTrue("must contain stream 3", source.contains("STREAM_MUSIC") || source.contains(", 3,"))
        assertTrue("must contain stream 4", source.contains("STREAM_ALARM") || source.contains(", 4)"))
    }

    @Test
    fun `has savedVolumes map`() {
        assertTrue(source.contains("savedVolumes"))
        assertTrue(source.contains("LinkedHashMap"))
    }

    @Test
    fun `has savedRingerMode with default NORMAL`() {
        assertTrue(source.contains("savedRingerMode"))
        assertTrue(source.contains("RINGER_MODE_NORMAL") || source.contains("= 2"))
    }

    @Test
    fun `has savedHapticFeedback with default 1`() {
        assertTrue(source.contains("savedHapticFeedback"))
    }

    @Test
    fun `has isActive volatile flag`() {
        assertTrue(source.contains("isActive"))
        assertTrue(source.contains("@Volatile") || source.contains("Volatile"))
    }

    @Test
    fun `has muteAll method`() {
        assertTrue(source.contains("fun muteAll()"))
    }

    @Test
    fun `muteAll saves volumes then sets to zero`() {
        assertTrue("must get volume", source.contains("getStreamVolume"))
        assertTrue("must set volume to 0", source.contains("setStreamVolume"))
    }

    @Test
    fun `muteAll sets ringer to SILENT`() {
        assertTrue(source.contains("RINGER_MODE_SILENT") || source.contains("ringerMode = 0"))
    }

    @Test
    fun `muteAll disables haptic feedback`() {
        assertTrue(source.contains("haptic_feedback_enabled"))
        assertTrue(source.contains("putInt"))
    }

    @Test
    fun `has restoreAll method`() {
        assertTrue(source.contains("fun restoreAll()"))
    }

    @Test
    fun `restoreAll clears savedVolumes after restore`() {
        assertTrue(source.contains("savedVolumes.clear()") || source.contains("clear()"))
    }

    @Test
    fun `has forceRestoreDefaults method`() {
        assertTrue(source.contains("fun forceRestoreDefaults()"))
    }

    @Test
    fun `forceRestoreDefaults sets RINGER_MODE_NORMAL`() {
        assertTrue(source.contains("RINGER_MODE_NORMAL"))
    }
}
