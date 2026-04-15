package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Test
import org.junit.Assert.*

class UiDebuggerTest {

    @Test
    fun `nodeToString formats node info correctly`() {
        val result = UiDebugger.nodeToString(
            className = "android.widget.Switch",
            viewId = "com.miui.securitycenter:id/title",
            text = "自启动",
            bounds = "[77,276][301,439]",
            isCheckable = true,
            isChecked = false,
            isClickable = true,
            isVisible = true,
            contentDesc = ""
        )
        assertTrue(result.contains("Switch"))
        assertTrue(result.contains("自启动"))
        assertTrue(result.contains("checkable=true"))
    }

    @Test
    fun `buildNodeTree creates indented tree string`() {
        val nodes = listOf(
            UiDebugger.NodeInfo(0, "FrameLayout", "", "", "[0,0][1080,2400]", false, false, false, true, ""),
            UiDebugger.NodeInfo(1, "TextView", "title", "系统服务", "[414,156][666,240]", false, false, false, true, ""),
            UiDebugger.NodeInfo(1, "Switch", "switch1", "", "[900,276][1000,439]", true, false, true, true, "")
        )
        val tree = UiDebugger.buildNodeTree(nodes)
        assertTrue(tree.contains("系统服务"))
        assertTrue(tree.contains("Switch"))
        assertTrue(tree.lines().size >= 3)
    }

    @Test
    fun `generateFileName creates timestamped filename`() {
        val name = UiDebugger.generateFileName("miui_phase1_autostart")
        assertTrue(name.startsWith("miui_phase1_autostart_"))
        assertTrue(name.endsWith(".txt"))
    }
}
