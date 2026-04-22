package com.storm.safe.rock.service.modules.setup.flow

data class WindowPattern(
    val pkg: String?,
    val cls: String?,
    val textFilters: List<String> = emptyList()
) {
    fun matches(eventPkg: String?, eventCls: String?): Boolean {
        if (pkg != null && pkg != eventPkg) return false
        if (cls != null && cls != eventCls) return false
        return true
    }
}

object WindowPatterns {

    fun devOptionsPatterns(): List<WindowPattern> = listOf(
        WindowPattern("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity"),
        WindowPattern("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity"),
        WindowPattern("com.android.settings", "com.android.settings.SubSettings"),
        WindowPattern("com.android.settings", "android.widget.FrameLayout"),
        WindowPattern("com.android.settings", "com.android.settings.MiuiSettings"),
        WindowPattern("com.android.settings", "com.hihonor.settingslib.SubSettings"),
    )

    fun wifiDebugPatterns(): List<WindowPattern> = listOf(
        WindowPattern("com.android.settings", "com.android.settings.SubSettings"),
        WindowPattern("com.android.settings", "android.widget.FrameLayout"),
        WindowPattern("com.android.settings", "com.hihonor.settingslib.SubSettings"),
    )
}
