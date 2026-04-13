package com.storm.safe.rock.service.modules.setup

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SetupConstantsTest {

    // --- ALLOW_TEXTS ---
    @Test
    fun `ALLOW_TEXTS contains Chinese allow`() {
        assertTrue(SetupConstants.ALLOW_TEXTS.contains("允许"))
    }

    @Test
    fun `ALLOW_TEXTS contains English Allow`() {
        assertTrue(SetupConstants.ALLOW_TEXTS.contains("Allow"))
    }

    // --- CONFIRM_TEXTS ---
    @Test
    fun `CONFIRM_TEXTS contains Chinese confirm`() {
        assertTrue(SetupConstants.CONFIRM_TEXTS.contains("确定"))
    }

    @Test
    fun `CONFIRM_TEXTS contains OK`() {
        assertTrue(SetupConstants.CONFIRM_TEXTS.contains("OK"))
    }

    @Test
    fun `CONFIRM_TEXTS contains Yes`() {
        assertTrue(SetupConstants.CONFIRM_TEXTS.contains("Yes"))
    }

    // --- CANCEL_TEXTS ---
    @Test
    fun `CANCEL_TEXTS contains Chinese cancel`() {
        assertTrue(SetupConstants.CANCEL_TEXTS.contains("取消"))
    }

    @Test
    fun `CANCEL_TEXTS contains Cancel`() {
        assertTrue(SetupConstants.CANCEL_TEXTS.contains("Cancel"))
    }

    // --- ABOUT_PHONE_TEXTS ---
    @Test
    fun `ABOUT_PHONE_TEXTS contains Chinese about phone`() {
        assertTrue(SetupConstants.ABOUT_PHONE_TEXTS.any { it.contains("关于手机") })
    }

    @Test
    fun `ABOUT_PHONE_TEXTS contains English About phone`() {
        assertTrue(SetupConstants.ABOUT_PHONE_TEXTS.contains("About phone"))
    }

    @Test
    fun `ABOUT_PHONE_TEXTS contains About device`() {
        assertTrue(SetupConstants.ABOUT_PHONE_TEXTS.contains("About device"))
    }

    // --- BUILD_NUMBER_TEXTS ---
    @Test
    fun `BUILD_NUMBER_TEXTS contains Chinese build number`() {
        assertTrue(SetupConstants.BUILD_NUMBER_TEXTS.any { it.contains("版本号") })
    }

    @Test
    fun `BUILD_NUMBER_TEXTS contains English Build number`() {
        assertTrue(SetupConstants.BUILD_NUMBER_TEXTS.contains("Build number"))
    }

    @Test
    fun `BUILD_NUMBER_TEXTS contains MIUI version`() {
        assertTrue(SetupConstants.BUILD_NUMBER_TEXTS.any { it.contains("MIUI") })
    }

    @Test
    fun `BUILD_NUMBER_TEXTS contains ColorOS`() {
        assertTrue(SetupConstants.BUILD_NUMBER_TEXTS.any { it.contains("ColorOS") })
    }

    @Test
    fun `BUILD_NUMBER_TEXTS contains HarmonyOS`() {
        assertTrue(SetupConstants.BUILD_NUMBER_TEXTS.any { it.contains("HarmonyOS") })
    }

    // --- VERSION_INFO_TEXTS ---
    @Test
    fun `VERSION_INFO_TEXTS contains Chinese version info`() {
        assertTrue(SetupConstants.VERSION_INFO_TEXTS.any { it.contains("版本信息") })
    }

    @Test
    fun `VERSION_INFO_TEXTS contains English Version info`() {
        assertTrue(SetupConstants.VERSION_INFO_TEXTS.contains("Version info"))
    }

    // --- SOFTWARE_INFO_TEXTS ---
    @Test
    fun `SOFTWARE_INFO_TEXTS contains Chinese software info`() {
        assertTrue(SetupConstants.SOFTWARE_INFO_TEXTS.any { it.contains("软件信息") })
    }

    @Test
    fun `SOFTWARE_INFO_TEXTS contains English Software information`() {
        assertTrue(SetupConstants.SOFTWARE_INFO_TEXTS.contains("Software information"))
    }

    // --- DEVELOPER_OPTIONS_TEXTS ---
    @Test
    fun `DEVELOPER_OPTIONS_TEXTS contains Chinese dev options`() {
        assertTrue(SetupConstants.DEVELOPER_OPTIONS_TEXTS.any { it.contains("USB调试") || it.contains("USB 调试") })
    }

    @Test
    fun `DEVELOPER_OPTIONS_TEXTS contains English USB debugging`() {
        assertTrue(SetupConstants.DEVELOPER_OPTIONS_TEXTS.contains("USB debugging"))
    }

    @Test
    fun `DEVELOPER_OPTIONS_TEXTS contains Wireless debugging`() {
        assertTrue(SetupConstants.DEVELOPER_OPTIONS_TEXTS.contains("Wireless debugging"))
    }

    // --- ALL_BUILD_NUMBER_TEXTS ---
    @Test
    fun `ALL_BUILD_NUMBER_TEXTS is superset of BUILD_NUMBER_TEXTS`() {
        for (text in SetupConstants.BUILD_NUMBER_TEXTS) {
            assertTrue(
                "BUILD_NUMBER_TEXTS '$text' should be in ALL_BUILD_NUMBER_TEXTS",
                SetupConstants.ALL_BUILD_NUMBER_TEXTS.contains(text)
            )
        }
    }

    @Test
    fun `ALL_BUILD_NUMBER_TEXTS includes vendor-specific versions`() {
        // MIUI, OS, ColorOS, 软件版本号, 版本号, HarmonyOS
        assertTrue(SetupConstants.ALL_BUILD_NUMBER_TEXTS.any { it.contains("MIUI") })
        assertTrue(SetupConstants.ALL_BUILD_NUMBER_TEXTS.any { it.contains("OS") })
    }

    // --- SOFTWARE_VERSION_TEXTS ---
    @Test
    fun `SOFTWARE_VERSION_TEXTS contains Chinese`() {
        assertTrue(SetupConstants.SOFTWARE_VERSION_TEXTS.any { it.contains("软件版本") })
    }

    @Test
    fun `SOFTWARE_VERSION_TEXTS contains English`() {
        assertTrue(SetupConstants.SOFTWARE_VERSION_TEXTS.contains("Software version"))
    }
}
