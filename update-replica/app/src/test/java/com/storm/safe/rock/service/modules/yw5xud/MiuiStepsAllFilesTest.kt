package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Test
import org.junit.Assert.*

class MiuiStepsAllFilesTest {

    @Test
    fun `ALL_FILES_KEYWORDS matches vendor C0367a4 string constants`() {
        assertTrue(MiuiSteps.ALL_FILES_KEYWORDS.contains("授予管理"))
        assertTrue(MiuiSteps.ALL_FILES_KEYWORDS.contains("管理所有文件"))
        assertTrue(MiuiSteps.ALL_FILES_KEYWORDS.contains("授予管理所有文件的权限"))
    }

    @Test
    fun `ALL_FILES_COORD_X_RATIO is 0_875 matching vendor C0367a4_1915`() {
        assertEquals(0.875f, MiuiSteps.ALL_FILES_COORD_X_RATIO, 0.0001f)
    }

    @Test
    fun `ALL_FILES_COORD_Y_RATIO is 0_225 matching vendor C0367a4_1916`() {
        assertEquals(0.225f, MiuiSteps.ALL_FILES_COORD_Y_RATIO, 0.0001f)
    }

    @Test
    fun `ALL_FILES_COORD_DURATION_MS is 100L matching vendor C0367a4 level3`() {
        assertEquals(100L, MiuiSteps.ALL_FILES_COORD_DURATION_MS)
    }

    @Test
    fun `ALL_FILES_VERIFY_ROUNDS is 3 matching vendor C0367a4_1960`() {
        assertEquals(3, MiuiSteps.ALL_FILES_VERIFY_ROUNDS)
    }

    @Test
    fun `ALL_FILES_VERIFY_DELAY_MS is 150L matching vendor C0367a4_1907`() {
        assertEquals(150L, MiuiSteps.ALL_FILES_VERIFY_DELAY_MS)
    }

    @Test
    fun `ALL_FILES_OUTER_RETRIES is 3 matching vendor C0367a4_1798`() {
        assertEquals(3, MiuiSteps.ALL_FILES_OUTER_RETRIES)
    }

    @Test
    fun `ALL_FILES_MAIN_FLAGS is 0x10800000 NEW_TASK EXCLUDE_FROM_RECENTS`() {
        assertEquals(0x10800000, MiuiSteps.ALL_FILES_MAIN_FLAGS)
    }

    @Test
    fun `ALL_FILES_PREDWARM_FLAGS is 0x50810000 NEW_TASK NO_HISTORY EXCLUDE_FROM_RECENTS NO_ANIMATION`() {
        assertEquals(0x50810000, MiuiSteps.ALL_FILES_PREDWARM_FLAGS)
    }
}
