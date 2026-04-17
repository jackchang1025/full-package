package com.storm.safe.rock.service.modules.yw5xud

import android.os.Build
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class Yw5xudHandlerTest {

    private var savedBrand: String = ""
    private var savedManufacturer: String = ""
    private var savedModel: String = ""

    @Before
    fun saveBuildFields() {
        savedBrand = Build.BRAND
        savedManufacturer = Build.MANUFACTURER
        savedModel = Build.MODEL
    }

    @After
    fun restoreBuildFields() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", savedBrand)
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", savedManufacturer)
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", savedModel)
    }

    private fun createHandler(): Yw5xudHandler {
        return Yw5xudHandler(RuntimeEnvironment.getApplication())
    }

    @Test
    fun `constructor sets tag`() {
        val handler = createHandler()
        assertEquals("Yw5xudHandler", handler.tag)
        handler.dispose()
    }

    @Test
    fun `initially not authorizing`() {
        val handler = createHandler()
        assertFalse(handler.isAuthorizing)
        handler.dispose()
    }

    @Test
    fun `getListenWindows returns permission controller windows`() {
        val handler = createHandler()
        val windows = handler.getListenWindows()
        assertTrue(windows.any { w -> w.packageName == "com.android.permissioncontroller" })
        assertTrue(windows.any { w -> w.packageName == "com.android.settings" })
        handler.dispose()
    }

    @Test
    fun `PERMISSION_ALLOW_BUTTON_IDS contains expected IDs`() {
        assertTrue(Yw5xudHandler.PERMISSION_ALLOW_BUTTON_IDS.any { s -> s.contains("permission_allow_button") })
        assertTrue(Yw5xudHandler.PERMISSION_ALLOW_BUTTON_IDS.any { s -> s.contains("button1") })
    }

    @Test
    fun `dispose sets isAuthorizing to false`() {
        val handler = createHandler()
        handler.dispose()
        assertFalse(handler.isAuthorizing)
    }

    // --- collectVisibleTexts tests ---

    @Test
    fun `collectVisibleTexts collects text from visible nodes`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isVisibleToUser).thenReturn(true)
        `when`(root.text).thenReturn("Hello")
        `when`(root.contentDescription).thenReturn("World")
        `when`(root.childCount).thenReturn(0)

        val texts = Yw5xudHandler.collectVisibleTexts(root)
        assertTrue(texts.contains("Hello"))
        assertTrue(texts.contains("World"))
    }

    @Test
    fun `collectVisibleTexts skips invisible nodes`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isVisibleToUser).thenReturn(false)
        `when`(root.text).thenReturn("Hidden")
        `when`(root.childCount).thenReturn(0)

        val texts = Yw5xudHandler.collectVisibleTexts(root)
        assertFalse(texts.contains("Hidden"))
    }

    @Test
    fun `collectVisibleTexts traverses children`() {
        val child = mock(AccessibilityNodeInfo::class.java)
        `when`(child.isVisibleToUser).thenReturn(true)
        `when`(child.text).thenReturn("ChildText")
        `when`(child.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isVisibleToUser).thenReturn(true)
        `when`(root.text).thenReturn("RootText")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(child)

        val texts = Yw5xudHandler.collectVisibleTexts(root)
        assertTrue(texts.contains("RootText"))
        assertTrue(texts.contains("ChildText"))
    }

    @Test
    fun `collectVisibleTexts trims whitespace`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isVisibleToUser).thenReturn(true)
        `when`(root.text).thenReturn("  trimmed  ")
        `when`(root.childCount).thenReturn(0)

        val texts = Yw5xudHandler.collectVisibleTexts(root)
        assertTrue(texts.contains("trimmed"))
    }

    @Test
    fun `collectVisibleTexts skips empty strings`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isVisibleToUser).thenReturn(true)
        `when`(root.text).thenReturn("   ")
        `when`(root.childCount).thenReturn(0)

        val texts = Yw5xudHandler.collectVisibleTexts(root)
        assertTrue(texts.isEmpty())
    }

    // --- findCheckBoxOrClickableParent tests ---

    @Test
    fun `findCheckBoxOrClickableParent returns CheckBox node directly`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.widget.CheckBox")

        val result = Yw5xudHandler.findCheckBoxOrClickableParent(node)
        assertSame(node, result)
    }

    @Test
    fun `findCheckBoxOrClickableParent returns CompoundButton node directly`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.widget.CompoundButton")

        val result = Yw5xudHandler.findCheckBoxOrClickableParent(node)
        assertSame(node, result)
    }

    @Test
    fun `findCheckBoxOrClickableParent finds sibling CheckBox`() {
        val sibling = mock(AccessibilityNodeInfo::class.java)
        `when`(sibling.className).thenReturn("android.widget.CheckBox")

        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.childCount).thenReturn(2)
        val textChild = mock(AccessibilityNodeInfo::class.java)
        `when`(textChild.className).thenReturn("android.widget.TextView")
        `when`(parent.getChild(0)).thenReturn(textChild)
        `when`(parent.getChild(1)).thenReturn(sibling)

        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.widget.TextView")
        `when`(node.parent).thenReturn(parent)

        val result = Yw5xudHandler.findCheckBoxOrClickableParent(node)
        assertSame(sibling, result)
    }

    @Test
    fun `findCheckBoxOrClickableParent returns clickable parent when no checkbox`() {
        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.childCount).thenReturn(1)
        val textChild2 = mock(AccessibilityNodeInfo::class.java)
        `when`(textChild2.className).thenReturn("android.widget.TextView")
        `when`(parent.getChild(0)).thenReturn(textChild2)
        `when`(parent.isClickable).thenReturn(true)

        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.widget.TextView")
        `when`(node.parent).thenReturn(parent)

        val result = Yw5xudHandler.findCheckBoxOrClickableParent(node)
        assertSame(parent, result)
    }

    @Test
    fun `findCheckBoxOrClickableParent returns null when no parent`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.className).thenReturn("android.widget.TextView")
        `when`(node.parent).thenReturn(null)

        assertNull(Yw5xudHandler.findCheckBoxOrClickableParent(node))
    }

    // --- executeAuthorization tests ---

    @Test
    fun `executeAuthorization dispatches by brand and always runs generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "unknown_test_brand")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "unknown_test_mfr")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "test_model")

        val handler = createHandler()
        val result = kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        // Should have logs from dispatch
        assertTrue(result.logs.any { log -> log.contains("GenericSteps") || log.contains("通用步骤") || log.contains("未识别") })
        handler.dispose()
    }

    @Test
    fun `stepDelay matches vendor constant`() {
        val handler = createHandler()
        assertEquals(300L, handler.stepDelay)
        handler.dispose()
    }

    // --- Keyword constants tests (review fix #2-#4) ---

    @Test
    fun `VIRUS_KEYWORDS contains key virus detection strings`() {
        assertTrue(Yw5xudHandler.VIRUS_KEYWORDS.any { it.contains("病毒") })
        assertTrue(Yw5xudHandler.VIRUS_KEYWORDS.any { it.contains("高风险") })
        assertTrue(Yw5xudHandler.VIRUS_KEYWORDS.any { it.contains("恶意应用") })
        assertEquals(8, Yw5xudHandler.VIRUS_KEYWORDS.size)
    }

    @Test
    fun `VIRUS_DISMISS_TEXTS contains dismiss buttons`() {
        assertTrue(Yw5xudHandler.VIRUS_DISMISS_TEXTS.contains("继续使用"))
        assertTrue(Yw5xudHandler.VIRUS_DISMISS_TEXTS.contains("恢复开启"))
        assertTrue(Yw5xudHandler.VIRUS_DISMISS_TEXTS.contains("取消"))
        assertTrue(Yw5xudHandler.VIRUS_DISMISS_TEXTS.contains("Continue"))
    }

    @Test
    fun `APP_LIST_KEYWORDS contains installed apps keywords`() {
        assertTrue(Yw5xudHandler.APP_LIST_KEYWORDS.any { it.contains("已安装应用") })
        assertTrue(Yw5xudHandler.APP_LIST_KEYWORDS.contains("应用列表"))
    }

    @Test
    fun `RISK_CONTROL_KEYWORDS contains control keywords`() {
        assertTrue(Yw5xudHandler.RISK_CONTROL_KEYWORDS.any { it.contains("管控") })
        assertTrue(Yw5xudHandler.RISK_CONTROL_KEYWORDS.contains("移入隔离箱"))
    }

    @Test
    fun `PERMISSION_ALLOW_TEXTS contains bilingual allow texts`() {
        assertTrue(Yw5xudHandler.PERMISSION_ALLOW_TEXTS.contains("允许"))
        assertTrue(Yw5xudHandler.PERMISSION_ALLOW_TEXTS.contains("Allow"))
        assertTrue(Yw5xudHandler.PERMISSION_ALLOW_TEXTS.contains("While using the app"))
        assertTrue(Yw5xudHandler.PERMISSION_ALLOW_TEXTS.contains("Accept"))
        assertEquals(9, Yw5xudHandler.PERMISSION_ALLOW_TEXTS.size)
    }

    // --- clickWithParentFallback tests ---

    @Test
    fun `clickWithParentFallback clicks directly when clickable`() {
        val handler = createHandler()
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(true)
        `when`(node.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        assertTrue(handler.clickWithParentFallback(node))
        handler.dispose()
    }

    @Test
    fun `clickWithParentFallback walks up parent chain`() {
        val handler = createHandler()
        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.isClickable).thenReturn(true)
        `when`(parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(false)
        `when`(node.parent).thenReturn(parent)

        assertTrue(handler.clickWithParentFallback(node))
        handler.dispose()
    }

    @Test
    fun `clickWithParentFallback returns false when no clickable in chain`() {
        val handler = createHandler()
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(false)
        `when`(node.parent).thenReturn(null)
        // No parent, no gesture (empty bounds), should return false
        assertFalse(handler.clickWithParentFallback(node))
        handler.dispose()
    }

    // --- executeAuthorization dispatches to real Steps ---

    @Test
    fun `executeAuthorization runs GenericSteps and records logs`() {
        val handler = createHandler()
        val result = kotlinx.coroutines.runBlocking { handler.executeAuthorization() }
        // GenericSteps.execute() adds "GenericSteps: 开始通用权限配置"
        assertTrue(result.logs.any { it.contains("GenericSteps") })
        handler.dispose()
    }

    // --- brand dispatch: per-brand branch must NOT append generic ---
    // Reference: jadx-reference/.../C0372a9.java m212450a8 (generic) is called only
    // in the default case (no brand matched + unknown OS), not unconditionally.

    /**
     * Test-only subclass that counts calls to each executeXxxSteps stub so we can
     * verify dispatch behaviour without running the real Steps classes (which
     * require a live MyAccessibilityService).
     */
    private class CountingYw5xudHandler(
        ctx: android.content.Context
    ) : Yw5xudHandler(ctx) {
        var miuiCalls = 0
        var huaweiCalls = 0
        var oppoCalls = 0
        var vivoCalls = 0
        var samsungCalls = 0
        var meizuCalls = 0
        var genericCalls = 0

        override suspend fun executeMiuiSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
            miuiCalls++
        }
        override suspend fun executeHuaweiSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
            huaweiCalls++
        }
        override suspend fun executeOppoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
            oppoCalls++
        }
        override suspend fun executeVivoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
            vivoCalls++
        }
        override suspend fun executeSamsungSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
            samsungCalls++
        }
        override suspend fun executeMeizuSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
            meizuCalls++
        }
        override suspend fun executeGenericSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
            genericCalls++
        }
    }

    @Test
    fun `huawei branch does not call generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "HUAWEI")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "HUAWEI")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "P40")

        val handler = CountingYw5xudHandler(RuntimeEnvironment.getApplication())
        kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        assertEquals("huawei branch should invoke HuaweiSteps once", 1, handler.huaweiCalls)
        assertEquals("huawei branch must NOT append generic", 0, handler.genericCalls)
        assertEquals(0, handler.miuiCalls)
        assertEquals(0, handler.oppoCalls)
        assertEquals(0, handler.vivoCalls)
        assertEquals(0, handler.samsungCalls)
        assertEquals(0, handler.meizuCalls)
        handler.dispose()
    }

    @Test
    fun `samsung branch does not call generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "samsung")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "samsung")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "SM-G998B")

        val handler = CountingYw5xudHandler(RuntimeEnvironment.getApplication())
        kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        assertEquals(1, handler.samsungCalls)
        assertEquals("samsung branch must NOT append generic", 0, handler.genericCalls)
        handler.dispose()
    }

    @Test
    fun `xiaomi branch does not call generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "Xiaomi")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "Mi 13")

        val handler = CountingYw5xudHandler(RuntimeEnvironment.getApplication())
        kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        assertEquals(1, handler.miuiCalls)
        assertEquals("xiaomi branch must NOT append generic", 0, handler.genericCalls)
        handler.dispose()
    }

    @Test
    fun `oppo branch does not call generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "OPPO")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "OPPO")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "Find X6")

        val handler = CountingYw5xudHandler(RuntimeEnvironment.getApplication())
        kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        assertEquals(1, handler.oppoCalls)
        assertEquals("oppo branch must NOT append generic", 0, handler.genericCalls)
        handler.dispose()
    }

    @Test
    fun `vivo branch does not call generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "vivo")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "vivo")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "X100")

        val handler = CountingYw5xudHandler(RuntimeEnvironment.getApplication())
        kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        assertEquals(1, handler.vivoCalls)
        assertEquals("vivo branch must NOT append generic", 0, handler.genericCalls)
        handler.dispose()
    }

    @Test
    fun `meizu branch does not call generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "meizu")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Meizu")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "21")

        val handler = CountingYw5xudHandler(RuntimeEnvironment.getApplication())
        kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        assertEquals(1, handler.meizuCalls)
        assertEquals("meizu branch must NOT append generic", 0, handler.genericCalls)
        handler.dispose()
    }

    @Test
    fun `unknown brand with unknown OS falls back to generic`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "unknown_test_brand")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "unknown_test_mfr")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "test_model")

        val handler = CountingYw5xudHandler(RuntimeEnvironment.getApplication())
        kotlinx.coroutines.runBlocking { handler.executeAuthorization() }

        // No brand matched and OS detection will return UNKNOWN in Robolectric (no ro.* sysprops set)
        assertEquals(0, handler.miuiCalls)
        assertEquals(0, handler.huaweiCalls)
        assertEquals(0, handler.oppoCalls)
        assertEquals(0, handler.vivoCalls)
        assertEquals(0, handler.samsungCalls)
        assertEquals(0, handler.meizuCalls)
        assertEquals("unknown brand + unknown OS must fall back to generic exactly once", 1, handler.genericCalls)
        handler.dispose()
    }
}
