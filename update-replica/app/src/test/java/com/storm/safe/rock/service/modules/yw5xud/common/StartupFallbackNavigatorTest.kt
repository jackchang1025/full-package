package com.storm.safe.rock.service.modules.yw5xud.common

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StartupFallbackNavigatorTest {

    @Test
    fun `launchAppDetailsSettings returns true when startActivity succeeds`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.packageName).thenReturn("com.storm.safe.rock")
        val ok = StartupFallbackNavigator.launchAppDetailsSettings(svc)
        assertTrue(ok)
        val captor = ArgumentCaptor.forClass(Intent::class.java)
        verify(svc).startActivity(captor.capture())
        val intent = captor.value
        assertTrue("action must be APPLICATION_DETAILS_SETTINGS",
            intent.action == android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        assertTrue("data must contain package scheme",
            intent.data?.toString()?.startsWith("package:") == true)
    }

    @Test
    fun `launchAppDetailsSettings returns false when startActivity throws`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.packageName).thenReturn("com.storm.safe.rock")
        `when`(svc.startActivity(any())).thenThrow(SecurityException("mock"))
        val ok = StartupFallbackNavigator.launchAppDetailsSettings(svc)
        assertFalse(ok)
    }

    @Test
    fun `launchAppDetailsSettings returns false when service is null`() {
        assertFalse(StartupFallbackNavigator.launchAppDetailsSettings(null))
    }
}
