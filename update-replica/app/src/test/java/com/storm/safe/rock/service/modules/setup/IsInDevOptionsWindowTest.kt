package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class IsInDevOptionsWindowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    private val constantsSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt").readText()
    }

    @Test
    fun `DEVELOPER_OPTIONS_TEXTS contains OPPO space variants`() {
        assertTrue("must contain 'OEM 解锁' with space",
            constantsSource.contains("OEM 解锁"))
        assertTrue("must contain 'OEM unlocking'",
            constantsSource.contains("OEM unlocking"))
    }

    @Test
    fun `DEVELOPER_OPTIONS_TEXTS contains no-scroll visible texts`() {
        assertTrue("must contain '充电时屏幕不休眠'",
            constantsSource.contains("充电时屏幕不休眠"))
        assertTrue("must contain 'Bug report'",
            constantsSource.contains("Bug report"))
        assertTrue("must contain '正在运行的服务'",
            constantsSource.contains("正在运行的服务"))
    }

    @Test
    fun `isInDevOptionsWindow uses DEVELOPER_OPTIONS_TEXTS`() {
        val start = source.indexOf("fun isInDevOptionsWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must use DEVELOPER_OPTIONS_TEXTS",
            body.contains("DEVELOPER_OPTIONS_TEXTS"))
    }
}
