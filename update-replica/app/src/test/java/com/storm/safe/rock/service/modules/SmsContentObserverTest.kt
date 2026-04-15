package com.storm.safe.rock.service.modules

import android.os.Handler
import android.os.Looper
import com.storm.safe.rock.service.MyAccessibilityService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for SmsContentObserver — C0931ny replica.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class SmsContentObserverTest {

    private lateinit var mockService: MyAccessibilityService

    @Before
    fun setup() {
        mockService = mock(MyAccessibilityService::class.java)
    }

    // ════════════════════════════════════════════════════════════════
    // Construction
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `constructor creates instance with handler and service`() {
        val handler = Handler(Looper.getMainLooper())
        val observer = SmsContentObserver(handler, mockService)
        assertNotNull(observer)
    }

    @Test
    fun `SMS_URI is content sms`() {
        val uri = SmsContentObserver.SMS_URI
        assertNotNull(uri)
        assert(uri.toString() == "content://sms")
    }

    // ════════════════════════════════════════════════════════════════
    // onChange
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `onChange with lastNetworkEventTime 0 initializes baseline`() {
        val handler = Handler(Looper.getMainLooper())
        val observer = SmsContentObserver(handler, mockService)
        `when`(mockService.lastNetworkEventTime).thenReturn(0L)

        // Should not throw even though ContentResolver is null (mocked)
        // The method gracefully catches exceptions
        observer.onChange(false)
    }

    @Test
    fun `onChange handles exception gracefully`() {
        val handler = Handler(Looper.getMainLooper())
        val observer = SmsContentObserver(handler, mockService)
        `when`(mockService.lastNetworkEventTime).thenReturn(100L)
        `when`(mockService.contentResolver).thenReturn(null)

        // Should not throw
        observer.onChange(false)
    }
}
