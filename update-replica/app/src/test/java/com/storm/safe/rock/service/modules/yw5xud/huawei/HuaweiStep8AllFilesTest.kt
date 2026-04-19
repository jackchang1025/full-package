package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.Context
import android.content.Intent
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * HuaweiStep8AllFilesTest — TDD coverage for [HuaweiSteps.executeStep8AllFilesAccess].
 *
 * Vendor: C0365a2.java m212163b0 (L1623-2049) — "所有文件访问权限"
 * Action: android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION (Android 11+, SDK 30+)
 *
 * Vendor flow:
 *  - L1661: log "[所有文件] 开始"
 *  - L1663: m212193f0(f55075b3) SP short-circuit → skip if done
 *  - L1667: SDK_INT < 30 → mark done ("Android 10及以下不需要此权限") + return
 *  - L1672: Environment.isExternalStorageManager() → mark done + return
 *  - Outer retry loop i=1; i < 3 (max 2 iterations):
 *    - L1681: intent ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION + pkg uri + flags 276824064
 *    - L1685: service.startActivity(intent)
 *    - delay(300L)
 *    - L1702: m212188e2() = page-verify (true = NOT on page) → retry loop
 *    - L1715: strArr = {"允许管理所有文件","允许访问所有文件","所有文件访问权限","允许"}
 *    - Try each via m212208g6(str, true) = toggleSwitchByText; first match sets i8=1
 *    - delay(100L)
 *    - If i8==0 && !isExternalStorageManager(): coordinate tap loop (up to 10 × 300ms)
 *    - delay(100L) + verify loop i2=1..5: if not granted → clickFirstSwitchOnDetailPage
 *    - If granted → log success
 *    - m212195f2(f55075b3) mark done → log "[所有文件] 完成"
 *    - return
 *
 * Test approach: open-helper spy overrides (same pattern as Steps 4, 5, 6, 7).
 */
@Ignore("TODO: adapt to HuaweiSteps split — executeStep8AllFilesAccess moved to HuaweiStep8AllFiles delegate")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [31])
class HuaweiStep8AllFilesTest {

    private lateinit var context: Context
    private lateinit var service: MyAccessibilityService

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        service = mock(MyAccessibilityService::class.java)
    }

    // -------------------------------------------------------------------------
    // Test 1 — SDK < 30: step is skipped entirely
    // vendor L1667: if SDK_INT < 30 → log "Android 10及以下不需要此权限" + mark done + return
    // -------------------------------------------------------------------------

    @Test
    @Config(sdk = [29])
    fun `executeStep8AllFilesAccess skips on SDK below 30`() = runBlocking {
        val steps = spy(HuaweiSteps(service, context))
        doReturn(false).`when`(steps).isStep8Completed()
        doReturn(false).`when`(steps).isExternalStorageManagerGranted()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep8AllFilesAccess(successes, failures, logs)

        assertTrue(
            "Logs should mention SDK skip or Android 10",
            logs.any { it.contains("SDK") || it.contains("30") || it.contains("Android 10") || it.contains("跳过") }
        )
        // No startActivity should have been called (no intent launched)
        org.mockito.Mockito.verify(service, org.mockito.Mockito.never())
            .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))
    }

    // -------------------------------------------------------------------------
    // Test 2 — Permission already granted: early return without UI interaction
    // vendor L1672: Environment.isExternalStorageManager() → mark done + return
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep8AllFilesAccess skips when permission already granted`() = runBlocking {
        val steps = spy(HuaweiSteps(service, context))
        doReturn(false).`when`(steps).isStep8Completed()
        doReturn(true).`when`(steps).isExternalStorageManagerGranted()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep8AllFilesAccess(successes, failures, logs)

        assertTrue(
            "Logs should mention permission already granted",
            logs.any { it.contains("已有") || it.contains("已授权") || it.contains("已开启") || it.contains("所有文件") }
        )
        org.mockito.Mockito.verify(service, org.mockito.Mockito.never())
            .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))
    }

    // -------------------------------------------------------------------------
    // Test 3 — SP short-circuit: isStep8Completed() == true → skips immediately
    // vendor L1663: m212193f0(f55075b3) → if already done, return
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep8AllFilesAccess skips when step already completed`() = runBlocking {
        val steps = spy(HuaweiSteps(service, context))
        doReturn(true).`when`(steps).isStep8Completed()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep8AllFilesAccess(successes, failures, logs)

        assertTrue(
            "Logs should mention step skip/completed",
            logs.any { it.contains("跳过") || it.contains("完成") || it.contains("Step8") || it.contains("所有文件") }
        )
        org.mockito.Mockito.verify(service, org.mockito.Mockito.never())
            .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))
    }

    // -------------------------------------------------------------------------
    // Test 4 — Intent launched with correct action, package uri, and flags
    // vendor L1681-1685: ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION + pkg uri + flags 276824064
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep8AllFilesAccess launches correct intent with flags and package uri`() =
        runBlocking {
            val capturedIntents = mutableListOf<Intent>()
            doAnswer { inv ->
                capturedIntents.add(inv.arguments[0] as Intent)
                null
            }.`when`(service).startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep8Completed()
            // Use sequential returns: first call (early-exit check) → false, subsequent → true
            // This avoids the early return before startActivity is called.
            var grantedSeqCall = 0
            doAnswer {
                grantedSeqCall++
                // First call at L1672 (early-exit) returns false (not yet granted)
                // subsequent calls return true (permission "granted" after toggle)
                grantedSeqCall > 1
            }.`when`(steps).isExternalStorageManagerGranted()
            // Make page verify succeed (on page) so we proceed to switch step
            doReturn(true).`when`(steps).isOnAllFilesPageNow()
            // Make toggle succeed on first text
            doReturn(true).`when`(steps).toggleAllFilesSwitch(
                org.mockito.ArgumentMatchers.anyString()
            )
            doNothing().`when`(steps).markStep8Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep8AllFilesAccess(successes, failures, logs)

            assertTrue("At least one intent should be launched", capturedIntents.isNotEmpty())
            val intent = capturedIntents.first()
            assertEquals(
                "Intent action must be ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION",
                "android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION",
                intent.action
            )
            assertTrue(
                "Intent data must contain package name",
                intent.dataString?.contains(context.packageName) == true
            )
            assertEquals(
                "Intent flags must be 276824064 (vendor L1684)",
                276824064,
                intent.flags
            )
        }

    // -------------------------------------------------------------------------
    // Test 5 — Page not entered: retry up to limit, then mark done
    // vendor L1702: if m212188e2() == true (NOT on page) → i++ → retry; after 3 fails mark done
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep8AllFilesAccess retries on page not found and marks done after exhaustion`() =
        runBlocking {
            doAnswer { null }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep8Completed()
            doReturn(false).`when`(steps).isExternalStorageManagerGranted()
            // Page never found → isOnAllFilesPageNow always returns false
            doReturn(false).`when`(steps).isOnAllFilesPageNow()
            doNothing().`when`(steps).markStep8Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep8AllFilesAccess(successes, failures, logs)

            // Should log that page was not entered
            assertTrue(
                "Should log page-not-entered or retry",
                logs.any { it.contains("未进入") || it.contains("重新") || it.contains("Step8") || it.contains("所有文件") }
            )
            // markStep8Completed should be called (vendor marks done on exhaustion)
            verify(steps).markStep8Completed()
        }

    // -------------------------------------------------------------------------
    // Test 6 — Toggle switch text path: found text → switch clicked → permission granted
    // vendor L1715-1728: iterate strArr {"允许管理所有文件",...}, call m212208g6(text, true)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep8AllFilesAccess clicks text switch and succeeds`() = runBlocking {
        doAnswer { null }.`when`(service)
            .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

        val steps = spy(HuaweiSteps(service, context))
        doReturn(false).`when`(steps).isStep8Completed()
        // First call: not granted; after toggle: granted
        var grantedCallCount = 0
        doAnswer {
            grantedCallCount++
            grantedCallCount > 1 // false on first check, true thereafter
        }.`when`(steps).isExternalStorageManagerGranted()
        doReturn(true).`when`(steps).isOnAllFilesPageNow()
        // toggleAllFilesSwitch succeeds on first text try
        doReturn(true).`when`(steps).toggleAllFilesSwitch(org.mockito.ArgumentMatchers.anyString())
        doNothing().`when`(steps).markStep8Completed()

        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        steps.executeStep8AllFilesAccess(successes, failures, logs)

        verify(steps).markStep8Completed()
        assertTrue(
            "Logs should mention completion",
            logs.any { it.contains("完成") || it.contains("开启") || it.contains("所有文件") }
        )
    }

    // -------------------------------------------------------------------------
    // Test 7 — Text switch fails → fallback to clickFirstSwitchOnDetailPage (verify loop)
    // vendor L1789-1799: if !isExternalStorageManager → m212158a1() per iteration
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep8AllFilesAccess falls back to clickFirstSwitch when text toggle fails`() =
        runBlocking {
            doAnswer { null }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep8Completed()
            doReturn(false).`when`(steps).isExternalStorageManagerGranted()
            doReturn(true).`when`(steps).isOnAllFilesPageNow()
            // All text toggles fail
            doReturn(false).`when`(steps).toggleAllFilesSwitch(org.mockito.ArgumentMatchers.anyString())
            doReturn(false).`when`(steps).clickFirstSwitchOnDetailPage()
            doNothing().`when`(steps).markStep8Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            steps.executeStep8AllFilesAccess(successes, failures, logs)

            // clickFirstSwitchOnDetailPage should be invoked in the verify loop
            verify(steps, org.mockito.Mockito.atLeastOnce()).clickFirstSwitchOnDetailPage()
            verify(steps).markStep8Completed()
        }

    // -------------------------------------------------------------------------
    // Test 8 — isStep8Completed returns false by default (SP not yet wired)
    // vendor: T16 implements SP; until then always false
    // -------------------------------------------------------------------------

    @Test
    fun `isStep8Completed returns false by default`() {
        val steps = HuaweiSteps(service, context)
        assertFalse("isStep8Completed() must return false until T16 implements SP", steps.isStep8Completed())
    }

    // -------------------------------------------------------------------------
    // Test 9 — isExternalStorageManagerGranted is overridable (open method)
    // Used in tests to avoid real Environment.isExternalStorageManager() call
    // -------------------------------------------------------------------------

    @Test
    fun `isExternalStorageManagerGranted is open and returns default false in tests`() {
        val steps = spy(HuaweiSteps(service, context))
        doReturn(false).`when`(steps).isExternalStorageManagerGranted()
        assertFalse(steps.isExternalStorageManagerGranted())
    }

    // -------------------------------------------------------------------------
    // Test 10 — startActivity exception is caught and execution continues
    // vendor: outer loop wrapped in try/catch (L1692-1700)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStep8AllFilesAccess handles startActivity exception gracefully`() =
        runBlocking {
            doAnswer { throw RuntimeException("startActivity failed") }.`when`(service)
                .startActivity(org.mockito.ArgumentMatchers.any(Intent::class.java))

            val steps = spy(HuaweiSteps(service, context))
            doReturn(false).`when`(steps).isStep8Completed()
            doReturn(false).`when`(steps).isExternalStorageManagerGranted()
            doNothing().`when`(steps).markStep8Completed()

            val successes = mutableListOf<String>()
            val failures = mutableListOf<String>()
            val logs = mutableListOf<String>()

            // Should not throw
            steps.executeStep8AllFilesAccess(successes, failures, logs)

            assertTrue(
                "Exception should be logged or step should complete gracefully",
                logs.any { it.contains("异常") || it.contains("所有文件") || it.contains("Step8") }
            )
        }
}
