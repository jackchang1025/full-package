package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject

class ProtectionCommandHandler : CommandHandler {
    companion object {
        private const val TAG = "ProtectionCmd"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "ENABLE_UNINSTALL_PROTECTION",
        "DISABLE_UNINSTALL_PROTECTION",
        "DISABLE_BIOMETRIC",
        "UNINSTALL_SELF"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val service = context.service ?: return
        Log.i(TAG, "Protection command: $command")

        when (command) {
            "ENABLE_UNINSTALL_PROTECTION" -> {
                // Delegate to RecentsGuardManager or protection module
                context.sendEvent("protection_status", JSONObject().apply {
                    put("uninstall_protection", true)
                })
            }
            "DISABLE_UNINSTALL_PROTECTION" -> {
                context.sendEvent("protection_status", JSONObject().apply {
                    put("uninstall_protection", false)
                })
            }
            "DISABLE_BIOMETRIC" -> {
                context.sendEvent("protection_status", JSONObject().apply {
                    put("biometric_disabled", true)
                })
            }
            "UNINSTALL_SELF" -> {
                Log.w(TAG, "UNINSTALL_SELF command received")
                context.sendEvent("protection_status", JSONObject().apply {
                    put("self_destruct", true)
                })
            }
        }
    }
}
