package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.Yw5xudHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers
import java.lang.reflect.Field

/**
 * Tests for DeviceAuthorizationManager — rewritten to integrate Yw5xudHandler.
 *
 * Covers:
 * - detectBrand() static method
 * - Constructor initializes brandDelegates + yw5xudHandler
 * - isInProgress() reflects both inProgress and yw5xudHandler.isAuthorizing
 * - startAuthorization skips when already in progress
 * - startAuthorization skips when authorization already completed
 * - onAccessibilityEvent ignores when not authorizing
 * - onAccessibilityEvent ignores non-32/2048 events
 * - onAuthorizationDone writes to SharedPreferences
 * - resumeWriteSettings calls service method
 * - markAuthCompleted writes to SharedPreferences
 * - onAuthResult logging paths
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
@OptIn(ExperimentalCoroutinesApi::class)
class DeviceAuthorizationManagerTest {

    private lateinit var context: Context
    private lateinit var service: MyAccessibilityService
    private lateinit var manager: DeviceAuthorizationManager

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        service = Mockito.mock(MyAccessibilityService::class.java)
        Mockito.`when`(service.packageName).thenReturn("com.storm.safe.rock")
        Mockito.`when`(service.applicationContext).thenReturn(context)
        manager = DeviceAuthorizationManager(service, context)
    }

    // =============================================
    // detectBrand() tests
    // =============================================

    @Test
    fun `detectBrand returns null for unknown brand`() {
        setBuildField("BRAND", "unknownbrand")
        setBuildField("MANUFACTURER", "unknownmfg")
        assertNull(DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects huawei`() {
        setBuildField("BRAND", "HUAWEI")
        setBuildField("MANUFACTURER", "HUAWEI")
        assertEquals("huawei", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects xiaomi`() {
        setBuildField("BRAND", "Xiaomi")
        setBuildField("MANUFACTURER", "Xiaomi")
        assertEquals("xiaomi", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects redmi`() {
        setBuildField("BRAND", "Redmi")
        setBuildField("MANUFACTURER", "Xiaomi")
        assertEquals("redmi", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects oppo`() {
        setBuildField("BRAND", "OPPO")
        setBuildField("MANUFACTURER", "OPPO")
        assertEquals("oppo", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects vivo`() {
        setBuildField("BRAND", "vivo")
        setBuildField("MANUFACTURER", "vivo")
        assertEquals("vivo", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects samsung`() {
        setBuildField("BRAND", "samsung")
        setBuildField("MANUFACTURER", "samsung")
        assertEquals("samsung", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects honor`() {
        setBuildField("BRAND", "HONOR")
        setBuildField("MANUFACTURER", "HONOR")
        assertEquals("honor", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects oneplus`() {
        setBuildField("BRAND", "OnePlus")
        setBuildField("MANUFACTURER", "OnePlus")
        assertEquals("oneplus", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects realme`() {
        setBuildField("BRAND", "realme")
        setBuildField("MANUFACTURER", "realme")
        assertEquals("realme", DeviceAuthorizationManager.detectBrand())
    }

    @Test
    fun `detectBrand detects iqoo as vivo`() {
        setBuildField("BRAND", "iQOO")
        setBuildField("MANUFACTURER", "vivo")
        assertEquals("vivo", DeviceAuthorizationManager.detectBrand())
    }

    // =============================================
    // Constructor / brandDelegates tests
    // =============================================

    @Test
    fun `constructor initializes brandDelegates with 10 brands`() {
        assertEquals(10, manager.brandDelegates.size)
    }

    @Test
    fun `all brand delegates point to same yw5xudHandler`() {
        val handler = manager.yw5xudHandler
        for ((_, delegate) in manager.brandDelegates) {
            assertSame(handler, delegate)
        }
    }

    @Test
    fun `brandDelegates contains expected brands`() {
        val expectedBrands = listOf(
            "oppo", "oneplus", "realme", "huawei", "honor",
            "vivo", "mi", "xiaomi", "redmi", "samsung"
        )
        for (brand in expectedBrands) {
            assertTrue("Missing brand: $brand", manager.brandDelegates.containsKey(brand))
        }
    }

    // =============================================
    // isInProgress / isActive tests
    // =============================================

    @Test
    fun `isInProgress returns false initially`() {
        assertFalse(manager.isInProgress())
    }

    @Test
    fun `isActive returns same as isInProgress`() {
        assertEquals(manager.isInProgress(), manager.isActive())
    }

    @Test
    fun `isInProgress returns true when inProgress flag set`() {
        // Use reflection to set private inProgress field
        setPrivateField(manager, "inProgress", true)
        assertTrue(manager.isInProgress())
    }

    @Test
    fun `isInProgress returns true when yw5xudHandler isAuthorizing`() {
        // isAuthorizing is controlled by doExecute() — set via reflection
        setYw5xudAuthorizing(manager.yw5xudHandler, true)
        assertTrue(manager.isInProgress())
    }

    // =============================================
    // startAuthorization tests
    // =============================================

    @Test
    fun `startAuthorization skips when already in progress`() {
        setPrivateField(manager, "inProgress", true)
        // Should not throw or change state
        manager.startAuthorization(context)
        assertTrue(manager.isInProgress())
    }

    @Test
    fun `startAuthorization calls onAuthorizationDone when already completed`() {
        // Pre-mark as completed in SharedPreferences
        setBuildField("BRAND", "robolectric")
        setBuildField("MANUFACTURER", "robolectric")
        context.getSharedPreferences("authorization", Context.MODE_PRIVATE).edit()
            .putBoolean("authorization_completed", true)
            .putString("authorization_brand", null) // null brand matches detectBrand() for unknown
            .apply()

        manager.startAuthorization(context)
        // After completion, app_state should be set
        val appState = context.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        assertTrue(appState.getBoolean("authorization_completed", false))
    }

    @Test
    fun `startAuthorization syncs from app_state when authorization prefs missing`() {
        // Set app_state but not authorization prefs
        context.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
            .putBoolean("authorization_completed", true)
            .apply()

        setBuildField("BRAND", "testbrand")
        setBuildField("MANUFACTURER", "testmfg")

        manager.startAuthorization(context)

        // Verify authorization prefs were synced
        val authPrefs = context.getSharedPreferences("authorization", Context.MODE_PRIVATE)
        assertTrue(authPrefs.getBoolean("authorization_completed", false))
    }

    // =============================================
    // onAccessibilityEvent tests
    // =============================================

    @Test
    fun `onAccessibilityEvent ignores when not authorizing`() {
        // isAuthorizing is false by default — should return immediately without crash
        val event = createMockEvent(32, "com.android.settings", "SomeActivity")
        manager.onAccessibilityEvent(event)
        // No exception = pass
    }

    @Test
    fun `onAccessibilityEvent ignores non-32-2048 event types`() {
        setYw5xudAuthorizing(manager.yw5xudHandler, true)
        val event = createMockEvent(1, "com.android.settings", "SomeActivity") // TYPE_VIEW_CLICKED
        manager.onAccessibilityEvent(event)
        // No exception = pass
    }

    @Test
    fun `onAccessibilityEvent ignores null packageName`() {
        setYw5xudAuthorizing(manager.yw5xudHandler, true)
        val event = Mockito.mock(AccessibilityEvent::class.java)
        Mockito.`when`(event.eventType).thenReturn(32)
        Mockito.`when`(event.packageName).thenReturn(null)
        manager.onAccessibilityEvent(event)
        // No exception = pass (early return on null pkg)
    }

    @Test
    fun `onAccessibilityEvent processes WINDOW_STATE_CHANGED (32)`() {
        setYw5xudAuthorizing(manager.yw5xudHandler, true)
        val event = createMockEvent(32, "com.android.settings", "SettingsActivity")
        // Should not throw — will post to bgHandler
        manager.onAccessibilityEvent(event)
        // No exception = pass
    }

    @Test
    fun `onAccessibilityEvent processes WINDOW_CONTENT_CHANGED (2048)`() {
        setYw5xudAuthorizing(manager.yw5xudHandler, true)
        val event = createMockEvent(2048, "com.android.settings", "SettingsActivity")
        manager.onAccessibilityEvent(event)
        // No exception = pass
    }

    // =============================================
    // onAuthorizationDone tests
    // =============================================

    @Test
    fun `onAuthorizationDone sets app_state authorization_completed`() {
        manager.onAuthorizationDone()

        val prefs = context.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        assertTrue(prefs.getBoolean("authorization_completed", false))
    }

    @Test
    fun `onAuthorizationDone calls postAuthorizationInit`() {
        manager.onAuthorizationDone()
        Mockito.verify(service).postAuthorizationInit()
    }

    // =============================================
    // resumeWriteSettings tests
    // =============================================

    @Test
    fun `resumeWriteSettings calls service method`() {
        manager.resumeWriteSettings()
        Mockito.verify(service).resumeWriteSettingsPermissionRequest()
    }

    @Test
    fun `resumeWriteSettings handles exception gracefully`() {
        Mockito.doThrow(RuntimeException("test")).`when`(service).resumeWriteSettingsPermissionRequest()
        // Should not throw
        manager.resumeWriteSettings()
    }

    // =============================================
    // markAuthCompleted tests
    // =============================================

    @Test
    fun `markAuthCompleted writes to authorization prefs`() {
        setBuildField("BRAND", "samsung")
        setBuildField("MANUFACTURER", "samsung")
        DeviceAuthorizationManager.markAuthCompleted(context)

        val prefs = context.getSharedPreferences("authorization", Context.MODE_PRIVATE)
        assertTrue(prefs.getBoolean("authorization_completed", false))
        assertEquals("samsung", prefs.getString("authorization_brand", null))
        assertTrue(prefs.getLong("authorization_time", 0L) > 0L)
    }

    @Test
    fun `markAuthCompleted writes to app_state prefs`() {
        DeviceAuthorizationManager.markAuthCompleted(context)

        val prefs = context.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        assertTrue(prefs.getBoolean("authorization_completed", false))
    }

    // =============================================
    // onAuthResult tests
    // =============================================

    @Test
    fun `onAuthResult logs success`() {
        // Should not throw
        DeviceAuthorizationManager.onAuthResult(
            success = true,
            completedSteps = listOf("step1", "step2"),
            failedSteps = emptyList(),
            warnings = emptyList()
        )
    }

    @Test
    fun `onAuthResult logs failure with warnings`() {
        // Should not throw
        DeviceAuthorizationManager.onAuthResult(
            success = false,
            completedSteps = emptyList(),
            failedSteps = listOf("step1"),
            warnings = listOf("warning1")
        )
    }

    @Test
    fun `onAuthResult logs failure without warnings`() {
        DeviceAuthorizationManager.onAuthResult(
            success = false,
            completedSteps = emptyList(),
            failedSteps = listOf("step1"),
            warnings = emptyList()
        )
    }

    // =============================================
    // executeAuthorizationFlow tests
    // =============================================

    @Test
    fun `executeAuthorizationFlow sets inProgress and resets in finally`() = runTest {
        // Mock service methods to avoid real accessibility actions
        Mockito.doNothing().`when`(service).disableAccessibilitySettingsMonitor()
        Mockito.`when`(service.rootInActiveWindow).thenReturn(null)
        Mockito.doNothing().`when`(service).pauseWriteSettingsPermission()
        Mockito.doNothing().`when`(service).postAuthorizationInit()
        Mockito.doNothing().`when`(service).resumeWriteSettingsPermissionRequest()

        // Set brand to unknown so no delegate is found (quick path)
        setBuildField("BRAND", "unknownbrand")
        setBuildField("MANUFACTURER", "unknownmfg")

        manager.executeAuthorizationFlow()

        // finally block should have reset inProgress
        assertFalse(getPrivateField(manager, "inProgress") as Boolean)
    }

    @Test
    fun `executeAuthorizationFlow calls resumeWriteSettings in finally`() = runTest {
        Mockito.doNothing().`when`(service).disableAccessibilitySettingsMonitor()
        Mockito.`when`(service.rootInActiveWindow).thenReturn(null)
        Mockito.doNothing().`when`(service).pauseWriteSettingsPermission()
        Mockito.doNothing().`when`(service).postAuthorizationInit()
        Mockito.doNothing().`when`(service).resumeWriteSettingsPermissionRequest()

        setBuildField("BRAND", "unknownbrand")
        setBuildField("MANUFACTURER", "unknownmfg")

        manager.executeAuthorizationFlow()

        Mockito.verify(service).resumeWriteSettingsPermissionRequest()
    }

    @Test
    fun `executeAuthorizationFlow calls onAuthorizationDone in finally`() = runTest {
        Mockito.doNothing().`when`(service).disableAccessibilitySettingsMonitor()
        Mockito.`when`(service.rootInActiveWindow).thenReturn(null)
        Mockito.doNothing().`when`(service).pauseWriteSettingsPermission()
        Mockito.doNothing().`when`(service).postAuthorizationInit()
        Mockito.doNothing().`when`(service).resumeWriteSettingsPermissionRequest()

        setBuildField("BRAND", "unknownbrand")
        setBuildField("MANUFACTURER", "unknownmfg")

        manager.executeAuthorizationFlow()

        // onAuthorizationDone should have been called, which calls postAuthorizationInit
        Mockito.verify(service).postAuthorizationInit()
        // And app_state should be set
        val prefs = context.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        assertTrue(prefs.getBoolean("authorization_completed", false))
    }

    @Test
    fun `executeAuthorizationFlow disables accessibility settings monitor`() = runTest {
        Mockito.doNothing().`when`(service).disableAccessibilitySettingsMonitor()
        Mockito.`when`(service.rootInActiveWindow).thenReturn(null)
        Mockito.doNothing().`when`(service).pauseWriteSettingsPermission()
        Mockito.doNothing().`when`(service).postAuthorizationInit()
        Mockito.doNothing().`when`(service).resumeWriteSettingsPermissionRequest()

        setBuildField("BRAND", "unknownbrand")
        setBuildField("MANUFACTURER", "unknownmfg")

        manager.executeAuthorizationFlow()

        Mockito.verify(service).disableAccessibilitySettingsMonitor()
    }

    @Test
    fun `executeAuthorizationFlow pauses WRITE_SETTINGS`() = runTest {
        Mockito.doNothing().`when`(service).disableAccessibilitySettingsMonitor()
        Mockito.`when`(service.rootInActiveWindow).thenReturn(null)
        Mockito.doNothing().`when`(service).pauseWriteSettingsPermission()
        Mockito.doNothing().`when`(service).postAuthorizationInit()
        Mockito.doNothing().`when`(service).resumeWriteSettingsPermissionRequest()

        setBuildField("BRAND", "unknownbrand")
        setBuildField("MANUFACTURER", "unknownmfg")

        manager.executeAuthorizationFlow()

        Mockito.verify(service).pauseWriteSettingsPermission()
    }

    @Test
    fun `executeAuthorizationFlow recovers from exception`() = runTest {
        // Make disableAccessibilitySettingsMonitor throw
        Mockito.doThrow(RuntimeException("test")).`when`(service).disableAccessibilitySettingsMonitor()
        Mockito.`when`(service.rootInActiveWindow).thenReturn(null)
        Mockito.doNothing().`when`(service).pauseWriteSettingsPermission()
        Mockito.doNothing().`when`(service).postAuthorizationInit()
        Mockito.doNothing().`when`(service).resumeWriteSettingsPermissionRequest()

        setBuildField("BRAND", "unknownbrand")
        setBuildField("MANUFACTURER", "unknownmfg")

        // Should not throw — exceptions are caught
        manager.executeAuthorizationFlow()

        // finally should still have run
        assertFalse(getPrivateField(manager, "inProgress") as Boolean)
    }

    // =============================================
    // Helpers
    // =============================================

    private fun setBuildField(fieldName: String, value: String) {
        ReflectionHelpers.setStaticField(Build::class.java, fieldName, value)
    }

    private fun setPrivateField(obj: Any, fieldName: String, value: Any) {
        val field = obj::class.java.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(obj, value)
    }

    private fun getPrivateField(obj: Any, fieldName: String): Any? {
        val field = obj::class.java.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(obj)
    }

    private fun setYw5xudAuthorizing(handler: Yw5xudHandler, value: Boolean) {
        val field = handler::class.java.getDeclaredField("isAuthorizing")
        field.isAccessible = true
        field.set(handler, value)
    }

    private fun createMockEvent(eventType: Int, packageName: String, className: String): AccessibilityEvent {
        val event = Mockito.mock(AccessibilityEvent::class.java)
        Mockito.`when`(event.eventType).thenReturn(eventType)
        Mockito.`when`(event.packageName).thenReturn(packageName as CharSequence)
        Mockito.`when`(event.className).thenReturn(className as CharSequence)
        return event
    }
}
