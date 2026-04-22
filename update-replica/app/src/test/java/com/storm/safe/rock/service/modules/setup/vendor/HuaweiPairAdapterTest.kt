package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HuaweiPairAdapterTest {

    private lateinit var service: AccessibilityService
    private lateinit var context: Context
    private lateinit var adapter: HuaweiPairAdapter

    @Before
    fun setUp() {
        service = mock(AccessibilityService::class.java)
        context = mock(Context::class.java)
        adapter = HuaweiPairAdapter(service, context)
    }

    @Test
    fun `vendorName is Huawei`() {
        assertEquals("Huawei", adapter.vendorName)
    }

    @Test
    fun `needsVersionInfoPage returns false`() {
        // Huawei does not need version info page (default interface impl)
        assertFalse(adapter.needsVersionInfoPage())
    }

    @Test
    fun `DEV_COMPONENTS has 4 entries`() {
        assertEquals(4, HuaweiPairAdapter.DEV_COMPONENTS.size)
    }

    @Test
    fun `first component is DevelopmentSettingsDashboardActivity`() {
        val first = HuaweiPairAdapter.DEV_COMPONENTS[0]
        assertEquals("com.android.settings", first.packageName)
        assertEquals(
            "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity",
            first.className
        )
    }

    @Test
    fun `second component is DevelopmentSettingsActivity`() {
        val second = HuaweiPairAdapter.DEV_COMPONENTS[1]
        assertEquals("com.android.settings", second.packageName)
        assertEquals(
            "com.android.settings.Settings\$DevelopmentSettingsActivity",
            second.className
        )
    }

    @Test
    fun `third component is HWSettings`() {
        val third = HuaweiPairAdapter.DEV_COMPONENTS[2]
        assertEquals("com.android.settings", third.packageName)
        assertEquals("com.android.settings.HWSettings", third.className)
    }

    @Test
    fun `fourth component is hihonor SubSettings`() {
        val fourth = HuaweiPairAdapter.DEV_COMPONENTS[3]
        assertEquals("com.android.settings", fourth.packageName)
        assertEquals("com.hihonor.settingslib.SubSettings", fourth.className)
    }

    @Test
    fun `DEV_COMPONENTS matches OpenDevelopmentDelegate HUAWEI_DEV_COMPONENTS`() {
        // Cross-check: HuaweiPairAdapter.DEV_COMPONENTS must match
        // OpenDevelopmentDelegate.HUAWEI_DEV_COMPONENTS in both size and content
        val odComponents = com.storm.safe.rock.service.modules.setup.OpenDevelopmentDelegate.HUAWEI_DEV_COMPONENTS
        assertEquals(odComponents.size, HuaweiPairAdapter.DEV_COMPONENTS.size)
        for (i in odComponents.indices) {
            assertEquals(odComponents[i].packageName, HuaweiPairAdapter.DEV_COMPONENTS[i].packageName)
            assertEquals(odComponents[i].className, HuaweiPairAdapter.DEV_COMPONENTS[i].className)
        }
    }

    @Test
    fun `implements VendorPairAdapter`() {
        assertTrue(adapter is VendorPairAdapter)
    }

    @Test
    fun `enableWirelessDebug returns false by default`() {
        // Huawei uses generic wireless debug toggle (default interface impl)
        assertFalse(adapter.enableWirelessDebug(service))
    }

    @Test
    fun `isVendorLockDialog returns false by default`() {
        assertFalse(adapter.isVendorLockDialog("com.example.SomeDialog"))
    }
}
