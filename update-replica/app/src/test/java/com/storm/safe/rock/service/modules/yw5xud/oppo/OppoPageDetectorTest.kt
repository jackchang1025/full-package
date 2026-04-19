package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class OppoPageDetectorTest {
    @Test fun `isOnBatteryPage true when 电池 in texts`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnBatteryPage(texts = listOf("电池", "剩余 60%")))
    }

    @Test fun `isOnBatteryPage false when no battery keyword`() {
        val d = OppoPageDetector()
        assertFalse(d.isOnBatteryPage(texts = listOf("设置", "网络")))
    }

    @Test fun `isOnOverlayDetailPage true when 在其他应用上层显示 present`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnOverlayDetailPage(texts = listOf("允许在其他应用上层显示", "开关")))
    }

    @Test fun `isOnAutoStartListPage true when safecenter package and 自启动 in texts`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnAutoStartListPage(pkg = "com.coloros.safecenter", texts = listOf("自启动管理", "应用列表")))
    }

    @Test fun `isOnAutoStartListPage false on non-safecenter package`() {
        val d = OppoPageDetector()
        assertFalse(d.isOnAutoStartListPage(pkg = "com.android.settings", texts = listOf("自启动管理")))
    }

    @Test fun `isOnFileAccessPage true when 所有文件访问权限 present`() {
        val d = OppoPageDetector()
        assertTrue(d.isOnFileAccessPage(texts = listOf("所有文件访问权限", "允许")))
    }

    // --- Production root overload smoke test ---
    @Test fun `isOnBatteryPage root overload integrates with collectTexts`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.settings")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(root.text).thenReturn(null)
        `when`(root.contentDescription).thenReturn(null)
        `when`(child.childCount).thenReturn(0)
        `when`(child.text).thenReturn("电池")
        `when`(child.contentDescription).thenReturn(null)

        val d = OppoPageDetector()
        assertTrue("root overload → collectTexts(DFS) → texts overload 整链",
            d.isOnBatteryPage(root))
    }

    @Test fun `collectTexts handles null root without throwing`() {
        val out = OppoPageDetector.collectTexts(null)
        assertTrue(out.isEmpty())
    }
}
