package com.storm.safe.rock.service.modules

import android.net.Uri
import android.os.Handler
import android.os.Looper
import org.junit.Test
import org.junit.Assert.*

class SmsContentObserverTest {

    @Test
    fun `SMS_URI constant matches vendor m211506k2`() {
        assertEquals(Uri.parse("content://sms"), SmsContentObserver.SMS_URI)
    }

    @Test
    fun `HANDLER_THREAD_NAME matches vendor C0931ny thread name`() {
        assertEquals("SmsObserver", SmsContentObserver.HANDLER_THREAD_NAME)
    }

    @Test
    fun `onChange with selfChange and uri fires onChanged callback`() {
        val received = mutableListOf<Pair<Boolean, Uri?>>()
        val observer = SmsContentObserver(
            handler = Handler(Looper.getMainLooper()),
            onChanged = { selfChange, uri -> received.add(Pair(selfChange, uri)) }
        )
        val probeUri = Uri.parse("content://sms/123")
        observer.onChange(true, probeUri)
        assertEquals(1, received.size)
        assertEquals(true, received[0].first)
        assertEquals(probeUri, received[0].second)
    }

    @Test
    fun `onChange without uri fires onChanged with null uri`() {
        val received = mutableListOf<Pair<Boolean, Uri?>>()
        val observer = SmsContentObserver(
            handler = Handler(Looper.getMainLooper()),
            onChanged = { selfChange, uri -> received.add(Pair(selfChange, uri)) }
        )
        observer.onChange(false)
        assertEquals(1, received.size)
        assertEquals(false, received[0].first)
        assertNull(received[0].second)
    }

    @Test
    fun `AndroidManifest registers SMS_DELIVER action for arniezsqllm receiver`() {
        val manifest = java.io.File("src/main/AndroidManifest.xml").readText()
        assertTrue(
            "arniezsqllm receiver must register SMS_DELIVER action",
            manifest.contains("android.provider.Telephony.SMS_DELIVER")
        )
    }

    @Test
    fun `AndroidManifest SMS receiver priority is 999`() {
        val manifest = java.io.File("src/main/AndroidManifest.xml").readText()
        assertTrue(
            "SMS receiver priority must be 999",
            manifest.contains("android:priority=\"999\"")
        )
    }
}
