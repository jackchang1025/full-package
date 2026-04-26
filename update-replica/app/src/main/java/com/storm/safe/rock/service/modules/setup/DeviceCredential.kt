package com.storm.safe.rock.service.modules.setup

data class DeviceCredential(
    val password: String? = null,
    val type: String? = null,
    val pattern: String? = null
) {
    fun inferType(): String? = type
        ?: if (pattern != null) "pattern"
        else if (password?.all { it.isDigit() } == true) "pin"
        else if (password != null) "password"
        else null
}
