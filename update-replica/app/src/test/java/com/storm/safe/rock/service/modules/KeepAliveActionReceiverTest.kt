package com.storm.safe.rock.service.modules

import android.content.Context
import android.content.Intent
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class KeepAliveActionReceiverTest {

    @Test
    fun `ACTION_SUFFIX matches vendor m211418b7`() {
        assertEquals(".ACTION_KEEP_ALIVE", KeepAliveActionReceiver.ACTION_SUFFIX)
    }

    @Test
    fun `buildAction returns packageName plus ACTION_KEEP_ALIVE`() {
        assertEquals(
            "dev.deltalab2964.swift.ACTION_KEEP_ALIVE",
            KeepAliveActionReceiver.buildAction("dev.deltalab2964.swift")
        )
    }

    @Test
    fun `onReceive fires onKeepAlive when intent action matches exact`() {
        var fired = false
        val receiver = KeepAliveActionReceiver(packageName = "com.test.app") { fired = true }
        val intent = Intent("com.test.app.ACTION_KEEP_ALIVE")
        receiver.onReceive(mock(Context::class.java), intent)
        assertTrue(fired)
    }

    @Test
    fun `onReceive fires onKeepAlive when intent action ends with suffix`() {
        var fired = false
        val receiver = KeepAliveActionReceiver(packageName = "com.test.app") { fired = true }
        val intent = Intent("com.other.pkg.ACTION_KEEP_ALIVE")
        receiver.onReceive(mock(Context::class.java), intent)
        assertTrue(fired)
    }

    @Test
    fun `onReceive ignores unrelated action`() {
        var fired = false
        val receiver = KeepAliveActionReceiver(packageName = "com.test.app") { fired = true }
        val intent = Intent("android.intent.action.BOOT_COMPLETED")
        receiver.onReceive(mock(Context::class.java), intent)
        assertFalse(fired)
    }

    @Test
    fun `onReceive ignores null action`() {
        var fired = false
        val receiver = KeepAliveActionReceiver(packageName = "com.test.app") { fired = true }
        receiver.onReceive(mock(Context::class.java), Intent())
        assertFalse(fired)
    }
}
