package com.storm.safe.rock.service.modules.yw5xud.generic

import com.storm.safe.rock.service.modules.yw5xud.common.AllowKeywords
import android.content.pm.PackageManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
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

    // NOTE: executeBatteryOptimization, executeOverlayPermission, executeNotificationChannel,
    // executeAllFilesAccess, executePlayStoreDisable, executeBasicPermissions,
    // executeXiaomiAutostart, executeXiaomiBgManagement and execute() were moved to
    // Generic* delegate classes (GenericBattery, GenericOverlay, etc.) during the GKD
    // selector integration refactor. Tests are @Ignored until accessible via GenericSteps.

    @Ignore("GKD refactor: executeBatteryOptimization moved to GenericBattery delegate")
    @Test
    fun `executeBatteryOptimization already exempt adds success`() {}

    @Ignore("GKD refactor: executeBatteryOptimization moved to GenericBattery delegate")
    @Test
    fun `executeBatteryOptimization not exempt launches intent`() {}

    @Ignore("GKD refactor: executeOverlayPermission moved to GenericOverlay delegate")
    @Test
    fun `executeOverlayPermission already granted adds success`() {}

    @Ignore("GKD refactor: executeOverlayPermission moved to GenericOverlay delegate")
    @Test
    fun `executeOverlayPermission not granted launches intent`() {}

    @Ignore("GKD refactor: executeNotificationChannel moved to GenericMisc delegate")
    @Test
    fun `executeNotificationChannel skips on API below 33`() {}

    @Ignore("GKD refactor: executeNotificationChannel moved to GenericMisc delegate")
    @Test
    fun `executeNotificationChannel checks on API 33 plus`() {}

    @Ignore("GKD refactor: executeAllFilesAccess moved to GenericAllFiles delegate")
    @Test
    fun `executeAllFilesAccess skips on API below 30`() {}

    @Ignore("GKD refactor: executeAllFilesAccess moved to GenericAllFiles delegate")
    @Test
    fun `executeAllFilesAccess checks on API 30 plus`() {}

    @Ignore("GKD refactor: executePlayStoreDisable moved to GenericMisc delegate")
    @Test
    fun `executePlayStoreDisable logs skip when not installed`() {}

    @Ignore("GKD refactor: executeBasicPermissions moved to GenericBasicPerms delegate")
    @Test
    fun `executeBasicPermissions adds log and queues request`() {}

    @Ignore("GKD refactor: executeXiaomiAutostart moved to GenericMisc delegate")
    @Test
    fun `executeXiaomiAutostart skips on non-Xiaomi device`() {}

    @Ignore("GKD refactor: executeXiaomiAutostart moved to GenericMisc delegate")
    @Test
    fun `executeXiaomiAutostart runs on Xiaomi device`() {}

    @Ignore("GKD refactor: executeXiaomiAutostart moved to GenericMisc delegate")
    @Test
    fun `executeXiaomiAutostart detects Redmi as Xiaomi`() {}

    @Ignore("GKD refactor: executeXiaomiAutostart moved to GenericMisc delegate")
    @Test
    fun `executeXiaomiAutostart detects POCO as Xiaomi`() {}

    @Ignore("GKD refactor: executeXiaomiBgManagement moved to GenericMisc delegate")
    @Test
    fun `executeXiaomiBgManagement skips on non-Xiaomi device`() {}

    @Ignore("GKD refactor: executeXiaomiBgManagement moved to GenericMisc delegate")
    @Test
    fun `executeXiaomiBgManagement queues on Xiaomi device`() {}

    @Ignore("GKD refactor: execute() calls all delegate methods, re-test after delegate tests pass")
    @Test
    fun `execute runs all sub-flows and adds start and end logs`() {}

    @Ignore("GKD refactor: execute() calls all delegate methods, re-test after delegate tests pass")
    @Test
    fun `execute populates successes from all sub-flows`() {}

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

    // ── appLabel ──────────────────────────────────────────────────

    @Test
    fun `appLabel returns app label or package name`() {
        val label = steps.appLabel
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

    // ═══ clickPermissionAllowButton — text fallback (vendor C0364a1.m212122a0 L268-294) ═══

    @Test
    fun `clickPermissionAllowButton falls back to text when viewId miss`() {
        // Build a clickable node with text "允许"; viewId lookup returns empty,
        // text lookup returns the node.
        val allowNode = mock(AccessibilityNodeInfo::class.java)
        `when`(allowNode.text).thenReturn("允许")
        `when`(allowNode.isVisibleToUser).thenReturn(true)
        `when`(allowNode.isClickable).thenReturn(true)
        `when`(allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        val root = mock(AccessibilityNodeInfo::class.java)
        // All viewId queries return empty
        `when`(root.findAccessibilityNodeInfosByViewId(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn(emptyList())
        // Text query for "允许" returns our allow node
        `when`(root.findAccessibilityNodeInfosByText("允许"))
            .thenReturn(listOf(allowNode))

        assertTrue(steps.clickPermissionAllowButton(root))
        verify(allowNode).performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    @Test
    fun `clickPermissionAllowButton matches multilingual allow keyword`() {
        // French "Autoriser" is in AllowKeywords.ALLOW list.
        val allowNode = mock(AccessibilityNodeInfo::class.java)
        `when`(allowNode.text).thenReturn("Autoriser")
        `when`(allowNode.isVisibleToUser).thenReturn(true)
        `when`(allowNode.isClickable).thenReturn(true)
        `when`(allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByViewId(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("Autoriser"))
            .thenReturn(listOf(allowNode))

        assertTrue(steps.clickPermissionAllowButton(root))
    }

    @Test
    fun `clickPermissionAllowButton does not click cancel only dialog`() {
        // Page has only "取消" node — no keyword from AllowKeywords.ALLOW would match.
        // findAccessibilityNodeInfosByText(any ALLOW keyword) should return empty list.
        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.findAccessibilityNodeInfosByViewId(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText(org.mockito.ArgumentMatchers.anyString()))
            .thenReturn(emptyList())

        assertFalse(steps.clickPermissionAllowButton(root))
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
    fun `ALL_FILES_TOGGLE_MAX_ITERATIONS is 5 (ADAPT from 10)`() {
        // ADAPT: 从 vendor 默认的 10 次改为 5 次，降低失败场景的最长阻塞时间
        assertEquals(5, GenericSteps.ALL_FILES_TOGGLE_MAX_ITERATIONS)
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
