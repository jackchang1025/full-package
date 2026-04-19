package com.storm.safe.rock.service.modules.yw5xud.common

import org.junit.Test
import org.junit.Assert.*

/**
 * Tests for SwitchClassNames — vendor f55054c0 Switch 类名数组。
 * 8 个 vendor 定义类名 + isSwitch() 正例/反例。
 */
class SwitchClassNamesTest {

    @Test
    fun `ALL contains all 8 vendor class names`() {
        val names = SwitchClassNames.ALL
        assertTrue("缺 HwSwitch", names.contains("com.huawei.hwswitchwidget.HwSwitch"))
        assertTrue("缺 hihonor Switch", names.contains("com.hihonor.widget.Switch"))
        assertTrue("缺 hihonor android Switch", names.contains("com.hihonor.android.widget.Switch"))
        assertTrue("缺 SwitchCompat", names.contains("androidx.appcompat.widget.SwitchCompat"))
        assertTrue("缺 android Switch", names.contains("android.widget.Switch"))
        assertTrue("缺 CheckBox", names.contains("android.widget.CheckBox"))
        assertTrue("缺 ToggleButton", names.contains("android.widget.ToggleButton"))
        assertTrue("缺 CompoundButton", names.contains("android.widget.CompoundButton"))
        assertEquals("vendor 定义恰好 8 个类名", 8, names.size)
    }

    // ── isSwitch 正例 ──────────────────────────────────────────────

    @Test
    fun `isSwitch matches exact full class name`() {
        assertTrue(SwitchClassNames.isSwitch("com.huawei.hwswitchwidget.HwSwitch"))
        assertTrue(SwitchClassNames.isSwitch("com.hihonor.widget.Switch"))
        assertTrue(SwitchClassNames.isSwitch("com.hihonor.android.widget.Switch"))
        assertTrue(SwitchClassNames.isSwitch("androidx.appcompat.widget.SwitchCompat"))
        assertTrue(SwitchClassNames.isSwitch("android.widget.Switch"))
        assertTrue(SwitchClassNames.isSwitch("android.widget.CheckBox"))
        assertTrue(SwitchClassNames.isSwitch("android.widget.ToggleButton"))
        assertTrue(SwitchClassNames.isSwitch("android.widget.CompoundButton"))
    }

    @Test
    fun `isSwitch matches simple class name suffix`() {
        // ROM 变体可能报告不同包名但 simple name 相同
        assertTrue(SwitchClassNames.isSwitch("com.custom.rom.HwSwitch"))
        assertTrue(SwitchClassNames.isSwitch("miui.widget.Switch"))
        assertTrue(SwitchClassNames.isSwitch("some.vendor.CheckBox"))
        assertTrue(SwitchClassNames.isSwitch("some.vendor.SwitchCompat"))
    }

    // ── isSwitch 反例 ──────────────────────────────────────────────

    @Test
    fun `isSwitch rejects null`() {
        assertFalse(SwitchClassNames.isSwitch(null))
    }

    @Test
    fun `isSwitch rejects Button and TextView`() {
        assertFalse(SwitchClassNames.isSwitch("android.widget.Button"))
        assertFalse(SwitchClassNames.isSwitch("android.widget.TextView"))
    }

    @Test
    fun `isSwitch rejects partial substring that is not suffix`() {
        // "Switch" 出现在中间，但 simple name 不是列表中的任何一个
        assertFalse(SwitchClassNames.isSwitch("android.widget.SwitchPreference"))
    }

    @Test
    fun `isSwitch rejects empty string`() {
        assertFalse(SwitchClassNames.isSwitch(""))
    }
}
