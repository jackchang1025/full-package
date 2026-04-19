package com.storm.safe.rock.service.modules.yw5xud.common

import org.junit.Assert.*
import org.junit.Test

class AutoStartNavigatorTest {
    @Test fun `ENTRY_TEXTS covers 应用和服务 and variants`() {
        assertTrue(AutoStartNavigator.ENTRY_TEXTS.contains("应用和服务"))
        assertTrue(AutoStartNavigator.ENTRY_TEXTS.contains("应用管理"))
    }
    @Test fun `MANAGER_TEXTS covers 应用启动管理`() {
        assertTrue(AutoStartNavigator.MANAGER_TEXTS.contains("应用启动管理"))
        assertTrue(AutoStartNavigator.MANAGER_TEXTS.contains("启动管理"))
    }
    @Test fun `SWITCH_TEXTS covers 3 switches in simplified and traditional`() {
        assertTrue(AutoStartNavigator.SWITCH_TEXTS.contains("允许自启动"))
        assertTrue(AutoStartNavigator.SWITCH_TEXTS.contains("允許自啟動"))
        assertEquals(9, AutoStartNavigator.SWITCH_TEXTS.size)
    }
    @Test fun `navigationPath returns 3 levels`() {
        assertEquals(3, AutoStartNavigator.navigationPath().size)
    }
}
