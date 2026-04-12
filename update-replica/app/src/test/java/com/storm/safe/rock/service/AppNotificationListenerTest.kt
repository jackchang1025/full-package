package com.storm.safe.rock.service

import org.junit.Assert.*
import org.junit.Test

class AppNotificationListenerTest {

    @Test
    fun `instance is null initially`() {
        assertNull(AppNotificationListener.instance)
    }

    @Test
    fun `can create instance`() {
        val listener = AppNotificationListener()
        assertNotNull(listener)
    }

    @Test
    fun `onNotificationPosted with null does not crash`() {
        val listener = AppNotificationListener()
        listener.onNotificationPosted(null) // should not throw
    }

    @Test
    fun `onNotificationRemoved with null does not crash`() {
        val listener = AppNotificationListener()
        listener.onNotificationRemoved(null) // should not throw
    }
}
