package com.storm.safe.rock.service.modules.yw5xud

/**
 * OS family detection matching vendor C0372a9.m212439a7()
 * Detects via system properties (getprop) first, then Build.DISPLAY fallback.
 */
enum class OsFamily(val id: String) {
    MIUI("miui"),
    EMUI("emui"),
    COLOROS("coloros"),
    ORIGINOS("originos"),
    ONEUI("oneui"),
    FLYME("flyme"),
    UNKNOWN("unknown");

    companion object {
        /**
         * Detect OS family. Matches vendor a7() exactly:
         * 1. getprop ro.miui.ui.version.name → MIUI
         * 2. getprop ro.build.version.emui/harmony/magic → EMUI
         * 3. getprop ro.build.version.opporom/realmeui + ro.oxygen.version → COLOROS
         * 4. getprop ro.vivo.os.version/product.version → ORIGINOS
         * 5. getprop ro.build.version.oneui → ONEUI
         * 6. getprop ro.build.display.id contains "flyme" → FLYME
         * 7. Build.DISPLAY fallback (miui/hyperos → MIUI, emui/harmonyos → EMUI, etc.)
         * 8. UNKNOWN
         */
        fun detect(): OsFamily = detectWithPropReader { prop -> getProp(prop) }

        /** Testable version with injectable prop reader */
        internal fun detectWithPropReader(getProp: (String) -> String?): OsFamily {
            // 1. MIUI
            if (getProp("ro.miui.ui.version.name").isNotNullOrEmpty()) return MIUI

            // 2. EMUI (Huawei/Honor/Magic)
            if (getProp("ro.build.version.emui").isNotNullOrEmpty() ||
                getProp("ro.build.version.harmony").isNotNullOrEmpty() ||
                getProp("ro.build.version.magic").isNotNullOrEmpty()) return EMUI

            // 3. ColorOS (OPPO/Realme/OnePlus)
            if (getProp("ro.build.version.opporom").isNotNullOrEmpty() ||
                getProp("ro.build.version.realmeui").isNotNullOrEmpty() ||
                getProp("ro.oxygen.version").isNotNullOrEmpty()) return COLOROS

            // 4. OriginOS (Vivo/iQOO)
            if (getProp("ro.vivo.os.version").isNotNullOrEmpty() ||
                getProp("ro.vivo.product.version").isNotNullOrEmpty()) return ORIGINOS

            // 5. OneUI (Samsung)
            if (getProp("ro.build.version.oneui").isNotNullOrEmpty()) return ONEUI

            // 6. Flyme check via display ID
            val displayId = getProp("ro.build.display.id")
            if (displayId != null && displayId.lowercase().startsWith("flyme")) return FLYME

            // 7. Build.DISPLAY fallback
            val display = android.os.Build.DISPLAY.lowercase()
            return when {
                display.startsWith("miui") || display.startsWith("hyperos") -> MIUI
                display.startsWith("emui") || display.startsWith("harmonyos") -> EMUI
                display.startsWith("coloros") || display.startsWith("realme") -> COLOROS
                display.startsWith("originos") || display.startsWith("funtouch") -> ORIGINOS
                display.startsWith("oneui") -> ONEUI
                display.startsWith("flyme") -> FLYME
                else -> UNKNOWN
            }
        }

        private fun String?.isNotNullOrEmpty(): Boolean = this != null && this.isNotEmpty()

        /** Read system property via `getprop`. Returns null on failure. */
        private fun getProp(name: String): String? {
            return try {
                val process = Runtime.getRuntime().exec("getprop $name")
                val result = process.inputStream.bufferedReader().readLine()?.trim()
                process.destroy()
                if (result.isNullOrEmpty()) null else result
            } catch (_: Exception) { null }
        }
    }
}
