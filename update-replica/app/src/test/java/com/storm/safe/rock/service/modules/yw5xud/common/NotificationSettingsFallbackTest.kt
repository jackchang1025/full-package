package com.storm.safe.rock.service.modules.yw5xud.common

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NotificationSettingsFallbackTest {

    @Test
    fun `launchAppNotificationSettings fires correct intent`() {
        val svc = mock(AccessibilityService::class.java)
        `when`(svc.packageName).thenReturn("com.storm.safe.rock")
        val ok = NotificationSettingsFallback.launchAppNotificationSettings(svc)
        assertTrue(ok)
        val captor = ArgumentCaptor.forClass(Intent::class.java)
        verify(svc).startActivity(captor.capture())
        val intent = captor.value
        assertTrue("action should be APP_NOTIFICATION_SETTINGS",
            intent.action == "android.settings.APP_NOTIFICATION_SETTINGS")
        assertTrue("extra APP_PACKAGE should be set",
            intent.getStringExtra("android.provider.extra.APP_PACKAGE") == "com.storm.safe.rock")
    }

    @Test
    fun `CHANNEL_KEYWORDS covers common variants`() {
        val list = NotificationSettingsFallback.CHANNEL_KEYWORDS
        assertTrue(list.contains("允许通知"))
        assertTrue(list.contains("显示通知"))
        assertTrue(list.contains("通知管理"))
    }
}
