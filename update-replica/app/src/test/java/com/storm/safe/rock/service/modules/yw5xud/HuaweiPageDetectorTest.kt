package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

/**
 * HuaweiPageDetector tests — 对齐 vendor C0365a2 L6572-6842 的 8 个布尔判定方法。
 *
 * 每个方法至少 2 个测试：null root → false；keyword 匹配 → true。
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiPageDetectorTest {

    private lateinit var detector: HuaweiPageDetector

    @Before
    fun setUp() {
        detector = HuaweiPageDetector()
    }

    // --- isAutoStartDialogOpened (vendor m212185d9 L6572-6618) ---

    @Test
    fun `isAutoStartDialogOpened returns false for null root`() {
        assertFalse(detector.isAutoStartDialogOpened(null))
    }

    @Test
    fun `isAutoStartDialogOpened returns true when both 手动管理 and 允许自启动 present`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("手动管理\n允许自启动")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isAutoStartDialogOpened(root))
    }

    @Test
    fun `isAutoStartDialogOpened returns false when only 手动管理 without switch keyword`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("手动管理")
        `when`(root.childCount).thenReturn(0)
        assertFalse(detector.isAutoStartDialogOpened(root))
    }

    // --- isOnSettingsPackage (vendor m212186e0 L6621-6627) ---

    @Test
    fun `isOnSettingsPackage returns false for null root`() {
        assertFalse(detector.isOnSettingsPackage(null))
    }

    @Test
    fun `isOnSettingsPackage returns true when root packageName matches settings`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.settings")
        assertTrue(detector.isOnSettingsPackage(root))
    }

    @Test
    fun `isOnSettingsPackage returns false for non-settings package`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.launcher")
        assertFalse(detector.isOnSettingsPackage(root))
    }

    // --- isNotificationPermissionDialog (vendor m212187e1 L6631-6648) ---

    @Test
    fun `isNotificationPermissionDialog returns false for null root`() {
        assertFalse(detector.isNotificationPermissionDialog(null))
    }

    @Test
    fun `isNotificationPermissionDialog returns true when keyword present and visible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isVisibleToUser).thenReturn(true)
        `when`(root.findAccessibilityNodeInfosByText("发送通知")).thenReturn(listOf(node))
        assertTrue(detector.isNotificationPermissionDialog(root))
    }

    @Test
    fun `isNotificationPermissionDialog returns false when keyword present but invisible`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isVisibleToUser).thenReturn(false)
        `when`(root.findAccessibilityNodeInfosByText("发送通知")).thenReturn(listOf(node))
        assertFalse(detector.isNotificationPermissionDialog(root))
    }

    // --- isOnAllFilesPage (vendor m212188e2 L6652-6674) ---

    @Test
    fun `isOnAllFilesPage returns false for null root`() {
        assertFalse(detector.isOnAllFilesPage(null, appLabel = "MyApp"))
    }

    @Test
    fun `isOnAllFilesPage returns true when 所有文件访问权限 keyword present`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("所有文件访问权限")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isOnAllFilesPage(root, appLabel = "MyApp"))
    }

    @Test
    fun `isOnAllFilesPage returns true when appLabel in collected texts`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("MyAppCustomName")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isOnAllFilesPage(root, appLabel = "MyAppCustomName"))
    }

    // --- isOnBatteryPage (vendor m212189e3 L6678-6701) ---

    @Test
    fun `isOnBatteryPage returns false for null root`() {
        assertFalse(detector.isOnBatteryPage(null))
    }

    @Test
    fun `isOnBatteryPage returns true for 电池 keyword`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("电池")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isOnBatteryPage(root))
    }

    @Test
    fun `isOnBatteryPage returns true for traditional chinese 電池`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("電池")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isOnBatteryPage(root))
    }

    // --- isOnMoreBatterySettingsPage (vendor m212190e5 L6705-6728) ---

    @Test
    fun `isOnMoreBatterySettingsPage returns false for null root`() {
        assertFalse(detector.isOnMoreBatterySettingsPage(null))
    }

    @Test
    fun `isOnMoreBatterySettingsPage returns true for 更多电池设置 keyword`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("更多电池设置")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isOnMoreBatterySettingsPage(root))
    }

    // --- isOnOverlayDetailPage (vendor m212191e7 L6732-6754) ---

    @Test
    fun `isOnOverlayDetailPage returns false for null root`() {
        assertFalse(detector.isOnOverlayDetailPage(null))
    }

    @Test
    fun `isOnOverlayDetailPage returns true for 悬浮 keyword`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("悬浮窗权限")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isOnOverlayDetailPage(root))
    }

    @Test
    fun `isOnOverlayDetailPage returns true for 显示在其他应用 keyword`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("显示在其他应用的上层")
        `when`(root.childCount).thenReturn(0)
        assertTrue(detector.isOnOverlayDetailPage(root))
    }

    // --- isOnSettingsHomePage (vendor m212192e8 L6758-6835) ---

    @Test
    fun `isOnSettingsHomePage returns false for null root`() {
        assertFalse(detector.isOnSettingsHomePage(null))
    }

    @Test
    fun `isOnSettingsHomePage returns false for non-settings package`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.launcher")
        assertFalse(detector.isOnSettingsHomePage(root))
    }

    @Test
    fun `isOnSettingsHomePage returns true when 设置 TextView visible and no nav-up and no cancel`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.settings")
        val titleNode = mock(AccessibilityNodeInfo::class.java)
        `when`(titleNode.isVisibleToUser).thenReturn(true)
        `when`(titleNode.text).thenReturn("设置")
        `when`(titleNode.className).thenReturn("android.widget.TextView")
        `when`(root.findAccessibilityNodeInfosByText("设置")).thenReturn(listOf(titleNode))
        `when`(root.findAccessibilityNodeInfosByText("向上导航")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("Navigate up")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("取消")).thenReturn(emptyList())
        assertTrue(detector.isOnSettingsHomePage(root))
    }

    @Test
    fun `isOnSettingsHomePage returns false when 向上导航 visible (sub-page indicator)`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.settings")
        val titleNode = mock(AccessibilityNodeInfo::class.java)
        `when`(titleNode.isVisibleToUser).thenReturn(true)
        `when`(titleNode.text).thenReturn("设置")
        `when`(titleNode.className).thenReturn("android.widget.TextView")
        val navUp = mock(AccessibilityNodeInfo::class.java)
        `when`(navUp.isVisibleToUser).thenReturn(true)
        `when`(root.findAccessibilityNodeInfosByText("设置")).thenReturn(listOf(titleNode))
        `when`(root.findAccessibilityNodeInfosByText("向上导航")).thenReturn(listOf(navUp))
        `when`(root.findAccessibilityNodeInfosByText("Navigate up")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("取消")).thenReturn(emptyList())
        assertFalse(detector.isOnSettingsHomePage(root))
    }

    // --- collectTexts (vendor m212145a7 / m212153e4 / m212154e6 统一为一个) ---

    @Test
    fun `collectTexts on null returns empty list`() {
        assertEquals(emptyList<String>(), HuaweiPageDetector.collectTexts(null))
    }

    @Test
    fun `collectTexts returns trimmed non-empty text of root only when no children`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("  hello  ")
        `when`(root.childCount).thenReturn(0)
        assertEquals(listOf("hello"), HuaweiPageDetector.collectTexts(root))
    }

    @Test
    fun `collectTexts recurses into children`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val child1 = mock(AccessibilityNodeInfo::class.java)
        val child2 = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("root")
        `when`(root.childCount).thenReturn(2)
        `when`(root.getChild(0)).thenReturn(child1)
        `when`(root.getChild(1)).thenReturn(child2)
        `when`(child1.text).thenReturn("c1")
        `when`(child1.childCount).thenReturn(0)
        `when`(child2.text).thenReturn("c2")
        `when`(child2.childCount).thenReturn(0)

        val texts = HuaweiPageDetector.collectTexts(root)
        assertEquals(listOf("root", "c1", "c2"), texts)
    }

    @Test
    fun `collectTexts skips empty strings`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)
        `when`(child.text).thenReturn("only-child")
        `when`(child.childCount).thenReturn(0)
        assertEquals(listOf("only-child"), HuaweiPageDetector.collectTexts(root))
    }

    // --- instance-exists smoke test ---

    @Test
    fun `HuaweiPageDetector instance can be constructed without arguments`() {
        assertNotNull(HuaweiPageDetector())
    }
}
