package com.storm.safe.rock.network

import org.json.JSONObject

data class CommandRequest(
    val command: String,
    val params: Map<String, Any>
) {
    companion object {
        fun fromJson(json: JSONObject): CommandRequest {
            val command = json.optString("command", "")

            val params = LinkedHashMap<String, Any>()

            // 1. Extract params object
            val paramsJson = json.optJSONObject("params")
            if (paramsJson != null) {
                for (key in paramsJson.keys()) {
                    params[key as String] = paramsJson.get(key)
                }
            }

            // 2. Merge top-level fields (excluding "command" and "params")
            for (key in json.keys()) {
                val k = key as String
                if (k != "command" && k != "params") {
                    params[k] = json.get(k)
                }
            }

            return CommandRequest(command, params)
        }
    }

    fun getStringParam(key: String, default: String = ""): String {
        return params[key]?.toString() ?: default
    }

    fun getIntParam(key: String, default: Int = 0): Int {
        return when (val v = params[key]) {
            is Number -> v.toInt()
            is String -> v.toIntOrNull() ?: default
            else -> default
        }
    }
}
