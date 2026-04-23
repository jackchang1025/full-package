package com.storm.safe.rock.service.delegates

import com.storm.safe.rock.service.modules.EventFilterManager
import com.storm.safe.rock.service.modules.NetworkManager
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.invocation.InvocationOnMock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for DetectionController — extracted detection enable/disable
 * methods from MyAccessibilityService.
 *
 * RecordingNetworkAnswer is used as the defaultAnswer for the NetworkManager
 * mock, capturing JSONObject arguments keyed by method name. This avoids the
 * Kotlin non-null / Mockito matcher NPE that occurs when using any() or
 * ArgumentCaptor.capture() with non-null Kotlin parameters.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class DetectionControllerTest {

    /** Captures the JSONObject passed to each send method by its method name. */
    private class RecordingNetworkAnswer : org.mockito.stubbing.Answer<Any?> {
        val calls = mutableMapOf<String, JSONObject>()

        override fun answer(invocation: InvocationOnMock): Any? {
            val arg = invocation.arguments.firstOrNull()
            if (arg is JSONObject) {
                calls[invocation.method.name] = arg
            }
            return null
        }
    }

    private lateinit var mockEventFilterManager: EventFilterManager
    private lateinit var mockNetworkManager: NetworkManager
    private lateinit var networkRecorder: RecordingNetworkAnswer
    private lateinit var controller: DetectionController

    @Before
    fun setup() {
        mockEventFilterManager = mock(EventFilterManager::class.java)
        networkRecorder = RecordingNetworkAnswer()
        mockNetworkManager = mock(NetworkManager::class.java, networkRecorder)
        controller = DetectionController(
            eventFilterManagerProvider = { mockEventFilterManager },
            networkManagerProvider = { mockNetworkManager }
        )
    }

    // ════════════════════════════════════════════════════════════════
    // enableAlipayDetection
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableAlipayDetection delegates to eventFilterManager`() {
        controller.enableAlipayDetection(3000L)
        verify(mockEventFilterManager).enableAlipayDetection(3000L)
    }

    @Test
    fun `enableAlipayDetection sends status via networkManager`() {
        controller.enableAlipayDetection(3000L)
        val json = networkRecorder.calls["sendAlipayDetectionStatus"]
        assertNotNull(json)
        assertTrue(json!!.getBoolean("enabled"))
        assertEquals(3000L, json.getLong("delayMs"))
    }

    // ════════════════════════════════════════════════════════════════
    // enableWechatDetection
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableWechatDetection delegates to eventFilterManager`() {
        controller.enableWechatDetection(5000L)
        verify(mockEventFilterManager).enableWechatDetection(5000L)
    }

    @Test
    fun `enableWechatDetection sends status via networkManager`() {
        controller.enableWechatDetection(5000L)
        val json = networkRecorder.calls["sendWechatDetectionStatus"]
        assertNotNull(json)
        assertTrue(json!!.getBoolean("enabled"))
        assertEquals(5000L, json.getLong("delayMs"))
    }

    // ════════════════════════════════════════════════════════════════
    // enableAutoPassword
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `enableAutoPassword delegates to eventFilterManager`() {
        controller.enableAutoPassword(2000L)
        verify(mockEventFilterManager).enableAutoPassword(2000L)
    }

    @Test
    fun `enableAutoPassword sends status via networkManager`() {
        controller.enableAutoPassword(2000L)
        val json = networkRecorder.calls["sendAutoPasswordDetectionStatus"]
        assertNotNull(json)
        assertTrue(json!!.getBoolean("enabled"))
        assertEquals(2000L, json.getLong("delayMs"))
    }

    // ════════════════════════════════════════════════════════════════
    // disableAutoPassword
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `disableAutoPassword delegates to eventFilterManager`() {
        controller.disableAutoPassword()
        verify(mockEventFilterManager).disableAutoPassword()
    }

    @Test
    fun `disableAutoPassword sends status via networkManager`() {
        controller.disableAutoPassword()
        val json = networkRecorder.calls["sendAutoPasswordDetectionStatus"]
        assertNotNull(json)
        assertFalse(json!!.getBoolean("enabled"))
        assertEquals(0L, json.getLong("delayMs"))
    }

    // ════════════════════════════════════════════════════════════════
    // disableWechatDetection
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `disableWechatDetection delegates to eventFilterManager`() {
        controller.disableWechatDetection()
        verify(mockEventFilterManager).disableWechatDetection()
    }

    @Test
    fun `disableWechatDetection sends status via networkManager`() {
        controller.disableWechatDetection()
        val json = networkRecorder.calls["sendWechatDetectionStatus"]
        assertNotNull(json)
        assertFalse(json!!.getBoolean("enabled"))
    }

    // ════════════════════════════════════════════════════════════════
    // disableAlipayDetection
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `disableAlipayDetection delegates to eventFilterManager`() {
        controller.disableAlipayDetection()
        verify(mockEventFilterManager).disableAlipayDetection()
    }

    @Test
    fun `disableAlipayDetection sends status via networkManager`() {
        controller.disableAlipayDetection()
        val json = networkRecorder.calls["sendAlipayDetectionStatus"]
        assertNotNull(json)
        assertFalse(json!!.getBoolean("enabled"))
    }

    // ════════════════════════════════════════════════════════════════
    // Null safety — null providers don't throw
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `null eventFilterManager does not throw on enableAlipayDetection`() {
        val ctrl = DetectionController(
            eventFilterManagerProvider = { null },
            networkManagerProvider = { mockNetworkManager }
        )
        ctrl.enableAlipayDetection(1000L) // should not throw
    }

    @Test
    fun `null networkManager does not throw on enableAlipayDetection`() {
        val ctrl = DetectionController(
            eventFilterManagerProvider = { mockEventFilterManager },
            networkManagerProvider = { null }
        )
        ctrl.enableAlipayDetection(1000L) // should not throw
    }

    @Test
    fun `both providers null does not throw on any method`() {
        val ctrl = DetectionController(
            eventFilterManagerProvider = { null },
            networkManagerProvider = { null }
        )
        // None of these should throw
        ctrl.enableAlipayDetection(1000L)
        ctrl.enableWechatDetection(1000L)
        ctrl.enableAutoPassword(1000L)
        ctrl.disableAutoPassword()
        ctrl.disableWechatDetection()
        ctrl.disableAlipayDetection()
    }

    @Test
    fun `null eventFilterManager on disableWechatDetection skips network status`() {
        // JADX: original disableWechatDetection returns early if eventFilterManager == null
        val ctrl = DetectionController(
            eventFilterManagerProvider = { null },
            networkManagerProvider = { mockNetworkManager }
        )
        ctrl.disableWechatDetection()
        assertTrue(
            "sendWechatDetectionStatus must not be called when eventFilterManager is null",
            !networkRecorder.calls.containsKey("sendWechatDetectionStatus")
        )
    }

    @Test
    fun `null eventFilterManager on disableAlipayDetection skips network status`() {
        // JADX: original disableAlipayDetection returns early if eventFilterManager == null
        val ctrl = DetectionController(
            eventFilterManagerProvider = { null },
            networkManagerProvider = { mockNetworkManager }
        )
        ctrl.disableAlipayDetection()
        assertTrue(
            "sendAlipayDetectionStatus must not be called when eventFilterManager is null",
            !networkRecorder.calls.containsKey("sendAlipayDetectionStatus")
        )
    }
}
