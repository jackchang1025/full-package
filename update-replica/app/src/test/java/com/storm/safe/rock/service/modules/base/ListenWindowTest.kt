package com.storm.safe.rock.service.modules.base

import org.junit.Assert.*
import org.junit.Test

class ListenWindowTest {

    @Test
    fun `matches exact package and class`() {
        val w = ListenWindow("com.android.settings", "SettingsActivity")
        assertTrue(w.matches("com.android.settings", "SettingsActivity"))
    }

    @Test
    fun `matches case-insensitive`() {
        val w = ListenWindow("com.android.settings", "SettingsActivity")
        assertTrue(w.matches("COM.ANDROID.SETTINGS", "settingsactivity"))
    }

    @Test
    fun `matches package-only when className is empty`() {
        val w = ListenWindow("com.android.settings")
        assertTrue(w.matches("com.android.settings", "AnyClass"))
    }

    @Test
    fun `rejects wrong package`() {
        val w = ListenWindow("com.android.settings", "SettingsActivity")
        assertFalse(w.matches("com.other.app", "SettingsActivity"))
    }

    @Test
    fun `partial match works via contains`() {
        val w = ListenWindow("settings")
        assertTrue(w.matches("com.android.settings", "Whatever"))
    }

    @Test
    fun `rejects wrong class when both specified`() {
        val w = ListenWindow("com.android.settings", "SettingsActivity")
        assertFalse(w.matches("com.android.settings", "OtherActivity"))
    }

    @Test
    fun `data class equals for identical values`() {
        assertEquals(ListenWindow("a", "b"), ListenWindow("a", "b"))
    }

    @Test
    fun `data class not equals for different className`() {
        assertNotEquals(ListenWindow("a", "b"), ListenWindow("a", "c"))
    }

    @Test
    fun `data class not equals for different packageName`() {
        assertNotEquals(ListenWindow("a", "b"), ListenWindow("x", "b"))
    }

    @Test
    fun `empty packageName matches any package`() {
        val w = ListenWindow("", "SettingsActivity")
        assertTrue(w.matches("com.any.app", "SettingsActivity"))
    }

    @Test
    fun `both empty matches anything`() {
        val w = ListenWindow("", "")
        assertTrue(w.matches("com.any.app", "AnyActivity"))
    }
}
