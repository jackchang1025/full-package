package com.storm.safe.rock.service.modules.yw5xud

import android.content.pm.PackageManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowSettings
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class GenericStepsTest {

    private lateinit var steps: GenericSteps

    @Before
    fun setUp() {
        steps = GenericSteps(null, RuntimeEnvironment.getApplication())
        // Reset Build fields to known defaults
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "generic")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "unknown")
    }

    // ── FlowType enum ────────────────────────────────────────────────

    @Test
    fun `FlowType has all expected values`() {
        val expected = setOf(
            "BATTERY_OPTIMIZATION",
            "OVERLAY_PERMISSION",
            "NOTIFICATION_CHANNEL",
            "ALL_FILES_ACCESS",
            "PLAY_STORE_DISABLE",
            "BASIC_PERMISSIONS",
            "XIAOMI_AUTOSTART",
            "XIAOMI_BG_MANAGEMENT"
        )
        val actual = GenericSteps.FlowType.values().map { it.name }.toSet()
        assertEquals(expected, actual)
    }

    @Test
    fun `FlowType count is 8`() {
        assertEquals(8, GenericSteps.FlowType.values().size)
    }

    // ── PERMISSION_ALLOW_IDS ─────────────────────────────────────────

    @Test
    fun `PERMISSION_ALLOW_IDS contains AOSP IDs`() {
        assertTrue(GenericSteps.PERMISSION_ALLOW_IDS.contains(
            "com.android.permissioncontroller:id/permission_allow_button"
        ))
        assertTrue(GenericSteps.PERMISSION_ALLOW_IDS.contains(
            "com.android.packageinstaller:id/permission_allow_button"
        ))
    }

    @Test
    fun `PERMISSION_ALLOW_IDS contains Samsung IDs`() {
        assertTrue(GenericSteps.PERMISSION_ALLOW_IDS.any { it.contains("samsung") })
    }

    @Test
    fun `PERMISSION_ALLOW_IDS contains Huawei IDs`() {
        assertTrue(GenericSteps.PERMISSION_ALLOW_IDS.any { it.contains("huawei") })
    }

    @Test
    fun `PERMISSION_ALLOW_IDS contains MIUI IDs`() {
        assertTrue(GenericSteps.PERMISSION_ALLOW_IDS.any { it.contains("miui") })
    }

    @Test
    fun `PERMISSION_ALLOW_IDS contains android button1`() {
        assertTrue(GenericSteps.PERMISSION_ALLOW_IDS.contains("android:id/button1"))
    }

    @Test
    fun `PERMISSION_ALLOW_IDS has 21 entries matching vendor`() {
        assertEquals(21, GenericSteps.PERMISSION_ALLOW_IDS.size)
    }

    // ── OVERLAY_SWITCH_IDS ───────────────────────────────────────────

    @Test
    fun `OVERLAY_SWITCH_IDS contains expected IDs`() {
        assertTrue(GenericSteps.OVERLAY_SWITCH_IDS.contains("com.android.settings:id/switch_widget"))
        assertTrue(GenericSteps.OVERLAY_SWITCH_IDS.contains("android:id/switch_widget"))
        assertTrue(GenericSteps.OVERLAY_SWITCH_IDS.any { it.contains("samsung") })
    }

    // ── executeBatteryOptimization ───────────────────────────────────

    @Test
    fun `executeBatteryOptimization already exempt adds success`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        // Robolectric's PowerManager: battery optimization whitelist
        val pm = RuntimeEnvironment.getApplication()
            .getSystemService(android.content.Context.POWER_SERVICE) as PowerManager
        shadowOf(pm).setIgnoringBatteryOptimizations(
            RuntimeEnvironment.getApplication().packageName, true
        )

        steps.executeBatteryOptimization(successes, failures, logs)

        assertTrue(successes.any { it.contains("电池优化已豁免") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeBatteryOptimization not exempt launches intent`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        val pm = RuntimeEnvironment.getApplication()
            .getSystemService(android.content.Context.POWER_SERVICE) as PowerManager
        shadowOf(pm).setIgnoringBatteryOptimizations(
            RuntimeEnvironment.getApplication().packageName, false
        )

        steps.executeBatteryOptimization(successes, failures, logs)

        // Should not add to successes (not yet exempt), should log intent launch
        assertTrue(successes.isEmpty())
        assertTrue(logs.any { it.contains("电池优化豁免请求") })
    }

    // ── executeOverlayPermission ─────────────────────────────────────

    @Test
    fun `executeOverlayPermission already granted adds success`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        // Robolectric: grant overlay permission
        ShadowSettings.setCanDrawOverlays(true)

        steps.executeOverlayPermission(successes, failures, logs)

        assertTrue(successes.any { it.contains("悬浮窗权限已开启") })
        assertTrue(failures.isEmpty())
    }

    @Test
    fun `executeOverlayPermission not granted launches intent`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        ShadowSettings.setCanDrawOverlays(false)

        steps.executeOverlayPermission(successes, failures, logs)

        assertTrue(successes.isEmpty())
        assertTrue(logs.any { it.contains("悬浮窗") })
    }

    // ── executeNotificationChannel ───────────────────────────────────

    @Test
    fun `executeNotificationChannel skips on API below 33`() {
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", 32)
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeNotificationChannel(successes, failures, logs)

        assertTrue(logs.any { it.contains("API < 33") })
        assertTrue(successes.isEmpty())
    }

    @Test
    fun `executeNotificationChannel checks on API 33 plus`() {
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", 33)
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeNotificationChannel(successes, failures, logs)

        // Should either add success or log — no crash
        assertTrue(successes.isNotEmpty() || logs.isNotEmpty())
        assertTrue(failures.isEmpty())
    }

    // ── executeAllFilesAccess ────────────────────────────────────────

    @Test
    fun `executeAllFilesAccess skips on API below 30`() = runBlocking {
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", 29)
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeAllFilesAccess(successes, failures, logs)

        assertTrue(logs.any { it.contains("API < 30") })
        assertTrue(successes.isEmpty())
    }

    @Test
    fun `executeAllFilesAccess checks on API 30 plus`() = runBlocking {
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", 30)
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeAllFilesAccess(successes, failures, logs)

        // On Robolectric, isExternalStorageManager returns false by default
        // So it should try to launch intent or log
        assertTrue(successes.isNotEmpty() || logs.isNotEmpty() || failures.isNotEmpty())
    }

    // ── executePlayStoreDisable ──────────────────────────────────────

    @Test
    fun `executePlayStoreDisable logs skip when not installed`() {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        // On Robolectric, Play Store is not installed by default
        steps.executePlayStoreDisable(successes, failures, logs)

        assertTrue(logs.any { it.contains("Play Store 未安装") })
    }

    // ── executeBasicPermissions ──────────────────────────────────────

    @Test
    fun `executeBasicPermissions adds log and queues request`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeBasicPermissions(successes, failures, logs)

        assertTrue(logs.any { it.contains("基础权限") })
        assertTrue(successes.any { it.contains("基础权限") })
    }

    // ── executeXiaomiAutostart ───────────────────────────────────────

    @Test
    fun `executeXiaomiAutostart skips on non-Xiaomi device`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "samsung")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "samsung")
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeXiaomiAutostart(successes, failures, logs)

        assertTrue(logs.any { it.contains("非小米设备") })
        assertTrue(successes.isEmpty())
    }

    @Test
    fun `executeXiaomiAutostart runs on Xiaomi device`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "xiaomi")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeXiaomiAutostart(successes, failures, logs)

        // Should attempt launch (may fail on Robolectric, but should not crash)
        assertTrue(successes.isNotEmpty() || failures.isNotEmpty() || logs.isNotEmpty())
    }

    @Test
    fun `executeXiaomiAutostart detects Redmi as Xiaomi`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "redmi")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeXiaomiAutostart(successes, failures, logs)

        // Should not skip (not "非小米设备")
        assertFalse(logs.any { it.contains("非小米设备") })
    }

    @Test
    fun `executeXiaomiAutostart detects POCO as Xiaomi`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "poco")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeXiaomiAutostart(successes, failures, logs)

        assertFalse(logs.any { it.contains("非小米设备") })
    }

    // ── executeXiaomiBgManagement ────────────────────────────────────

    @Test
    fun `executeXiaomiBgManagement skips on non-Xiaomi device`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "oppo")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "OPPO")
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeXiaomiBgManagement(successes, failures, logs)

        assertTrue(logs.any { it.contains("非小米设备") })
    }

    @Test
    fun `executeXiaomiBgManagement queues on Xiaomi device`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "xiaomi")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeXiaomiBgManagement(successes, failures, logs)

        assertTrue(successes.any { it.contains("小米后台管理已排队") })
    }

    // ── execute (orchestrator) ───────────────────────────────────────

    @Test
    fun `execute runs all sub-flows and adds start and end logs`() = runBlocking {
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        // Should have start and end markers
        assertTrue(logs.any { it.contains("开始通用权限配置") })
        assertTrue(logs.any { it.contains("通用权限配置完成") })
        // Should have run basic permissions (always runs)
        assertTrue(successes.any { it.contains("基础权限") })
    }

    @Test
    fun `execute populates successes from all sub-flows`() = runBlocking {
        // Set up battery exempt so we get a success from that flow
        val pm = RuntimeEnvironment.getApplication()
            .getSystemService(android.content.Context.POWER_SERVICE) as PowerManager
        shadowOf(pm).setIgnoringBatteryOptimizations(
            RuntimeEnvironment.getApplication().packageName, true
        )
        ShadowSettings.setCanDrawOverlays(true)

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.execute(successes, failures, logs)

        assertTrue(successes.any { it.contains("电池优化已豁免") })
        assertTrue(successes.any { it.contains("悬浮窗权限已开启") })
        assertTrue(successes.any { it.contains("基础权限") })
    }

    // ── collectAllTexts ──────────────────────────────────────────────

    @Test
    fun `collectAllTexts collects text from single node`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("Hello")
        `when`(root.childCount).thenReturn(0)

        val texts = steps.collectAllTexts(root)

        assertEquals(listOf("Hello"), texts)
    }

    @Test
    fun `collectAllTexts traverses children DFS`() {
        val child1 = mock(AccessibilityNodeInfo::class.java)
        `when`(child1.text).thenReturn("Child1")
        `when`(child1.childCount).thenReturn(0)

        val child2 = mock(AccessibilityNodeInfo::class.java)
        `when`(child2.text).thenReturn("Child2")
        `when`(child2.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("Root")
        `when`(root.childCount).thenReturn(2)
        `when`(root.getChild(0)).thenReturn(child1)
        `when`(root.getChild(1)).thenReturn(child2)

        val texts = steps.collectAllTexts(root)

        assertEquals(3, texts.size)
        assertEquals("Root", texts[0])
        assertEquals("Child1", texts[1])
        assertEquals("Child2", texts[2])
    }

    @Test
    fun `collectAllTexts skips blank text`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("   ")
        `when`(root.childCount).thenReturn(0)

        val texts = steps.collectAllTexts(root)

        assertTrue(texts.isEmpty())
    }

    @Test
    fun `collectAllTexts skips null text`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn(null)
        `when`(root.childCount).thenReturn(0)

        val texts = steps.collectAllTexts(root)

        assertTrue(texts.isEmpty())
    }

    @Test
    fun `collectAllTexts handles null child gracefully`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.text).thenReturn("Root")
        `when`(root.childCount).thenReturn(2)
        `when`(root.getChild(0)).thenReturn(null)
        `when`(root.getChild(1)).thenReturn(null)

        val texts = steps.collectAllTexts(root)

        assertEquals(listOf("Root"), texts)
    }

    // ── findAllToggles ───────────────────────────────────────────────

    @Test
    fun `findAllToggles finds Switch nodes`() {
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isVisibleToUser).thenReturn(true)
        `when`(switchNode.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.isVisibleToUser).thenReturn(true)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(switchNode)

        val toggles = steps.findAllToggles(root)

        assertEquals(1, toggles.size)
        assertSame(switchNode, toggles[0])
    }

    @Test
    fun `findAllToggles finds Toggle nodes`() {
        val toggleNode = mock(AccessibilityNodeInfo::class.java)
        `when`(toggleNode.className).thenReturn("android.widget.ToggleButton")
        `when`(toggleNode.isVisibleToUser).thenReturn(true)
        `when`(toggleNode.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.FrameLayout")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(toggleNode)

        val toggles = steps.findAllToggles(root)

        assertEquals(1, toggles.size)
    }

    @Test
    fun `findAllToggles finds CheckBox nodes`() {
        val checkBox = mock(AccessibilityNodeInfo::class.java)
        `when`(checkBox.className).thenReturn("android.widget.CheckBox")
        `when`(checkBox.isVisibleToUser).thenReturn(true)
        `when`(checkBox.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(checkBox)

        val toggles = steps.findAllToggles(root)

        assertEquals(1, toggles.size)
    }

    @Test
    fun `findAllToggles finds CompoundButton nodes`() {
        val compound = mock(AccessibilityNodeInfo::class.java)
        `when`(compound.className).thenReturn("android.widget.CompoundButton")
        `when`(compound.isVisibleToUser).thenReturn(true)
        `when`(compound.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(compound)

        val toggles = steps.findAllToggles(root)

        assertEquals(1, toggles.size)
    }

    @Test
    fun `findAllToggles skips invisible toggles`() {
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isVisibleToUser).thenReturn(false)
        `when`(switchNode.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(switchNode)

        val toggles = steps.findAllToggles(root)

        assertTrue(toggles.isEmpty())
    }

    @Test
    fun `findAllToggles respects maxDepth`() {
        // Create a deep chain: root -> level1 -> level2 (Switch)
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isVisibleToUser).thenReturn(true)
        `when`(switchNode.childCount).thenReturn(0)

        val level1 = mock(AccessibilityNodeInfo::class.java)
        `when`(level1.className).thenReturn("android.widget.LinearLayout")
        `when`(level1.childCount).thenReturn(1)
        `when`(level1.getChild(0)).thenReturn(switchNode)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(level1)

        // maxDepth=1 means depth 0 (root) and depth 1 (level1) are processed,
        // but depth 2 (switchNode) exceeds maxDepth
        val toggles = steps.findAllToggles(root, maxDepth = 1)

        assertTrue(toggles.isEmpty())
    }

    @Test
    fun `findAllToggles finds multiple toggles`() {
        val switch1 = mock(AccessibilityNodeInfo::class.java)
        `when`(switch1.className).thenReturn("android.widget.Switch")
        `when`(switch1.isVisibleToUser).thenReturn(true)
        `when`(switch1.childCount).thenReturn(0)

        val switch2 = mock(AccessibilityNodeInfo::class.java)
        `when`(switch2.className).thenReturn("android.widget.CheckBox")
        `when`(switch2.isVisibleToUser).thenReturn(true)
        `when`(switch2.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.childCount).thenReturn(2)
        `when`(root.getChild(0)).thenReturn(switch1)
        `when`(root.getChild(1)).thenReturn(switch2)

        val toggles = steps.findAllToggles(root)

        assertEquals(2, toggles.size)
    }

    // ── clickNodeOrParent ────────────────────────────────────────────

    @Test
    fun `clickNodeOrParent clicks clickable node directly`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(true)
        `when`(node.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        assertTrue(steps.clickNodeOrParent(node))
    }

    @Test
    fun `clickNodeOrParent walks up to clickable parent`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(false)

        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.isClickable).thenReturn(true)
        `when`(parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        `when`(parent.parent).thenReturn(null)

        `when`(node.parent).thenReturn(parent)

        assertTrue(steps.clickNodeOrParent(node))
    }

    @Test
    fun `clickNodeOrParent walks up to grandparent`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(false)

        val parent = mock(AccessibilityNodeInfo::class.java)
        `when`(parent.isClickable).thenReturn(false)

        val grandparent = mock(AccessibilityNodeInfo::class.java)
        `when`(grandparent.isClickable).thenReturn(true)
        `when`(grandparent.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        `when`(grandparent.parent).thenReturn(null)

        `when`(node.parent).thenReturn(parent)
        `when`(parent.parent).thenReturn(grandparent)

        assertTrue(steps.clickNodeOrParent(node))
    }

    @Test
    fun `clickNodeOrParent stops after 3 levels`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(false)

        val p1 = mock(AccessibilityNodeInfo::class.java)
        `when`(p1.isClickable).thenReturn(false)
        val p2 = mock(AccessibilityNodeInfo::class.java)
        `when`(p2.isClickable).thenReturn(false)
        val p3 = mock(AccessibilityNodeInfo::class.java)
        `when`(p3.isClickable).thenReturn(false)

        // Clickable ancestor beyond depth 3 — should NOT be reached
        val p4 = mock(AccessibilityNodeInfo::class.java)
        `when`(p4.isClickable).thenReturn(true)
        `when`(p4.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        `when`(node.parent).thenReturn(p1)
        `when`(p1.parent).thenReturn(p2)
        `when`(p2.parent).thenReturn(p3)
        `when`(p3.parent).thenReturn(p4)

        assertFalse(steps.clickNodeOrParent(node))
    }

    @Test
    fun `clickNodeOrParent returns false when no clickable ancestor`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(false)
        `when`(node.parent).thenReturn(null)

        assertFalse(steps.clickNodeOrParent(node))
    }

    @Test
    fun `clickNodeOrParent returns false when click action fails`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        `when`(node.isClickable).thenReturn(true)
        `when`(node.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(false)
        `when`(node.parent).thenReturn(null)

        assertFalse(steps.clickNodeOrParent(node))
    }

    // ── findFirstToggle ──────────────────────────────────────────────

    @Test
    fun `findFirstToggle finds checkable Switch`() {
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isCheckable).thenReturn(true)
        `when`(switchNode.isVisibleToUser).thenReturn(true)
        `when`(switchNode.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.isCheckable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(switchNode)

        val result = steps.findFirstToggle(root)

        assertSame(switchNode, result)
    }

    @Test
    fun `findFirstToggle requires isCheckable per vendor b8`() {
        // Switch that is NOT checkable should be skipped
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isCheckable).thenReturn(false)
        `when`(switchNode.isVisibleToUser).thenReturn(true)
        `when`(switchNode.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.isCheckable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(switchNode)

        val result = steps.findFirstToggle(root)

        assertNull(result)
    }

    @Test
    fun `findFirstToggle requires isVisibleToUser`() {
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isCheckable).thenReturn(true)
        `when`(switchNode.isVisibleToUser).thenReturn(false)
        `when`(switchNode.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isCheckable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(switchNode)

        val result = steps.findFirstToggle(root)

        assertNull(result)
    }

    @Test
    fun `findFirstToggle finds CheckBox`() {
        val checkBox = mock(AccessibilityNodeInfo::class.java)
        `when`(checkBox.className).thenReturn("android.widget.CheckBox")
        `when`(checkBox.isCheckable).thenReturn(true)
        `when`(checkBox.isVisibleToUser).thenReturn(true)
        `when`(checkBox.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isCheckable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(checkBox)

        assertNotNull(steps.findFirstToggle(root))
    }

    @Test
    fun `findFirstToggle finds CompoundButton variant`() {
        val compound = mock(AccessibilityNodeInfo::class.java)
        `when`(compound.className).thenReturn("android.widget.CompoundButton")
        `when`(compound.isCheckable).thenReturn(true)
        `when`(compound.isVisibleToUser).thenReturn(true)
        `when`(compound.childCount).thenReturn(0)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isCheckable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(compound)

        assertNotNull(steps.findFirstToggle(root))
    }

    @Test
    fun `findFirstToggle returns null when no toggles`() {
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.className).thenReturn("android.widget.LinearLayout")
        `when`(root.isCheckable).thenReturn(false)
        `when`(root.childCount).thenReturn(0)

        assertNull(steps.findFirstToggle(root))
    }

    @Test
    fun `findFirstToggle respects maxDepth`() {
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isCheckable).thenReturn(true)
        `when`(switchNode.isVisibleToUser).thenReturn(true)
        `when`(switchNode.childCount).thenReturn(0)

        val level1 = mock(AccessibilityNodeInfo::class.java)
        `when`(level1.isCheckable).thenReturn(false)
        `when`(level1.childCount).thenReturn(1)
        `when`(level1.getChild(0)).thenReturn(switchNode)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.isCheckable).thenReturn(false)
        `when`(root.childCount).thenReturn(1)
        `when`(root.getChild(0)).thenReturn(level1)

        // maxDepth=1: root(0) -> level1(1) -> switch(2, exceeds)
        assertNull(steps.findFirstToggle(root, maxDepth = 1))
    }

    // ── isXiaomiBrand ────────────────────────────────────────────────

    @Test
    fun `isXiaomiBrand detects xiaomi brand`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "Xiaomi")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        assertTrue(steps.isXiaomiBrand())
    }

    @Test
    fun `isXiaomiBrand detects redmi brand`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "Redmi")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        assertTrue(steps.isXiaomiBrand())
    }

    @Test
    fun `isXiaomiBrand detects poco brand`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "POCO")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "Xiaomi")
        assertTrue(steps.isXiaomiBrand())
    }

    @Test
    fun `isXiaomiBrand detects xiaomi manufacturer only`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "other_brand")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "xiaomi")
        assertTrue(steps.isXiaomiBrand())
    }

    @Test
    fun `isXiaomiBrand returns false for samsung`() {
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "samsung")
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "samsung")
        assertFalse(steps.isXiaomiBrand())
    }

    // ── getAppLabel ──────────────────────────────────────────────────

    @Test
    fun `getAppLabel returns app label or package name`() {
        val label = steps.getAppLabel()
        // On Robolectric, returns the package label or package name
        assertNotNull(label)
        assertTrue(label.isNotBlank())
    }

    // ═══ Overlay permission fixes (vendor m212133b3 + m212126a6) ═══

    @Test
    fun `OVERLAY_SWITCH_IDS has 7 entries matching vendor`() {
        assertEquals(7, GenericSteps.OVERLAY_SWITCH_IDS.size)
        assertTrue(GenericSteps.OVERLAY_SWITCH_IDS.contains("com.android.settings:id/switch_widget"))
        assertTrue(GenericSteps.OVERLAY_SWITCH_IDS.contains("com.samsung.android.settings:id/switch_widget"))
    }

    @Test
    fun `dispatchGestureClick does not crash with null service`() {
        // steps has null service — calling coordinate-based overload should not throw
        val method = GenericSteps::class.java.getDeclaredMethod("dispatchGestureClick", Float::class.java, Float::class.java)
        method.isAccessible = true
        method.invoke(steps, 100f, 200f)
    }

    @Test
    fun `scrollForward returns false for non-scrollable node`() {
        val mockNode = mock(AccessibilityNodeInfo::class.java)
        `when`(mockNode.isScrollable).thenReturn(false)
        `when`(mockNode.childCount).thenReturn(0)
        val result = steps.scrollForward(mockNode)
        assertFalse(result)
    }
}

class GenericStepsAllFilesToggleTest {

    @Test
    fun `ALL_FILES_KEYWORDS contains MIUI-specific labels`() {
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.contains("允许管理所有文件"))
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.contains("允许访问全部"))
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.contains("Allow access to manage all files"))
    }

    @Test
    fun `ALL_FILES_TOGGLE_MAX_ITERATIONS is 10`() {
        assertEquals(10, GenericSteps.ALL_FILES_TOGGLE_MAX_ITERATIONS)
    }

    @Test
    fun `ALL_FILES_TOGGLE_INTERVAL_MS is 1000`() {
        assertEquals(1000L, GenericSteps.ALL_FILES_TOGGLE_INTERVAL_MS)
    }

    @Test
    fun `ALL_FILES_FLAGS matches vendor 276824064`() {
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_FLAGS")
        field.isAccessible = true
        assertEquals(276824064, field.get(null))
    }

    @Test
    fun `MIUI_PREDWARM_FLAGS matches vendor 1350631424`() {
        val field = GenericSteps::class.java.getDeclaredField("MIUI_PREDWARM_FLAGS")
        field.isAccessible = true
        assertEquals(1350631424, field.get(null))
    }

    @Test
    fun `MIUI_ALL_FILES_PACKAGES includes settings and misettings`() {
        val field = GenericSteps::class.java.getDeclaredField("MIUI_ALL_FILES_PACKAGES")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val packages = field.get(null) as Set<String>
        assertTrue(packages.contains("com.android.settings"))
        assertTrue(packages.contains("com.xiaomi.misettings"))
    }

    @Test
    fun `ALL_FILES_COORD_X_RATIO is 0_875`() {
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_COORD_X_RATIO")
        field.isAccessible = true
        assertEquals(0.875f, field.get(null) as Float, 0.001f)
    }

    @Test
    fun `ALL_FILES_COORD_Y_RATIO is 0_225`() {
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_COORD_Y_RATIO")
        field.isAccessible = true
        assertEquals(0.225f, field.get(null) as Float, 0.001f)
    }

    @Test
    fun `ALL_FILES_VERIFY_DELAY_MS is 150`() {
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_VERIFY_DELAY_MS")
        field.isAccessible = true
        assertEquals(150L, field.get(null))
    }

    @Test
    fun `ALL_FILES_VERIFY_ROUNDS is 3`() {
        val field = GenericSteps::class.java.getDeclaredField("ALL_FILES_VERIFY_ROUNDS")
        field.isAccessible = true
        assertEquals(3, field.get(null))
    }

    @Test
    fun `PARENT_CLIMB_DEPTH is 15`() {
        val field = GenericSteps::class.java.getDeclaredField("PARENT_CLIMB_DEPTH")
        field.isAccessible = true
        assertEquals(15, field.get(null))
    }

    @Test
    fun `ALL_FILES_ALLOW_KEYWORDS contains vendor MIUI texts`() {
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.any { it.contains("授予管理") })
        assertTrue(GenericSteps.ALL_FILES_ALLOW_KEYWORDS.any { it.contains("管理所有文件") })
    }
}
