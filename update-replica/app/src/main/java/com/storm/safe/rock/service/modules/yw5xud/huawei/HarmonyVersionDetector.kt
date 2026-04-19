package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.os.Build
import android.util.Log

/**
 * HarmonyVersionDetector — 对齐 vendor C0365a2.java L265-322。
 *
 * 双路径检测 HarmonyOS 版本：
 *  Path 1: 反射 com.huawei.system.BuildEx.getOsName() → "Harmony"（仅能确认是 HarmonyOS,不含版本）
 *  Path 2: Build.DISPLAY.toLowerCase() 匹配 "harmonyos X" → 提取版本号 2/3/4
 *
 * vendor 加密字符串：
 *   "KFYcdEUtDTlSOGVKCClZPQFgdSQiVRUfVQ==" → "com.huawei.system.BuildEx"
 *   "LFwFFV4aHi9ZNQ==" → "getOsName"
 *
 * ADAPT: replica 直接使用明文类名 + 方法名,不走 StringUtil.m212470a0 解密路径,
 *        因 replica 已有独立字符串混淆机制,不需叠加 vendor XOR。
 *
 * ADAPT: replica 只用反射 getOsName() 确认 isHarmony，版本号依靠 Build.DISPLAY 提取。
 *        vendor 还有一个反射方法 (加密 "LFwFFV4OCTxEOCRX") 提取版本号，replica 不实现
 *        因为 Build.DISPLAY 已覆盖 HarmonyOS 2/3/4 主流版本；极少数 OEM ROM 可能剥离
 *        DISPLAY 中的 "harmonyos" 字样，届时 detect() 会返回 HARMONY_OS_UNKNOWN 而非
 *        具体版本号 — 对下游分支（只用 isHarmony + 版本 2/3/4 区分）影响可接受。
 */
object HarmonyVersionDetector {
    private const val TAG = "HarmonyVer"

    enum class Version(val isHarmony: Boolean, val displayName: String) {
        HARMONY_OS_4(true, "HarmonyOS 4"),
        HARMONY_OS_3(true, "HarmonyOS 3"),
        HARMONY_OS_2(true, "HarmonyOS 2"),
        /** 反射路径确认是 HarmonyOS 但 Build.DISPLAY 不含版本号 */
        HARMONY_OS_UNKNOWN(true, "HarmonyOS ?"),
        NOT_HARMONY(false, "non-Harmony");
    }

    /** 组合双路径检测(真机入口) */
    fun detect(): Version {
        val displayVer = parseDisplayVersion(Build.DISPLAY)
        if (displayVer != Version.NOT_HARMONY) {
            Log.d(TAG, "detect via Build.DISPLAY='${Build.DISPLAY}' → $displayVer")
            return displayVer
        }
        val reflectVer = detectViaReflection()
        Log.d(TAG, "detect via reflection → $reflectVer (Build.DISPLAY='${Build.DISPLAY}')")
        return reflectVer
    }

    /** 解析 Build.DISPLAY 字符串,返回 HarmonyOS 版本。纯函数,便于单元测试。 */
    fun parseDisplayVersion(display: String?): Version {
        if (display.isNullOrEmpty()) return Version.NOT_HARMONY
        val lower = display.lowercase()
        return when {
            lower.contains("harmonyos 4") -> Version.HARMONY_OS_4
            lower.contains("harmonyos 3") -> Version.HARMONY_OS_3
            lower.contains("harmonyos 2") -> Version.HARMONY_OS_2
            // ADAPT: vendor 分出 HarmonyOS 1.0；replica 收敛为 UNKNOWN 因 HOS 1 已 EOL，下游分支只用到 2/3/4
            lower.contains("harmonyos") -> Version.HARMONY_OS_UNKNOWN
            else -> Version.NOT_HARMONY
        }
    }

    /** 解析反射返回的 osName。纯函数,便于单元测试。 */
    fun parseOsName(osName: String?): Version {
        if (osName == null) return Version.NOT_HARMONY
        return if (osName.equals("Harmony", ignoreCase = true))
            Version.HARMONY_OS_UNKNOWN
        else
            Version.NOT_HARMONY
    }

    /** 反射检测 com.huawei.system.BuildEx.getOsName()。 */
    private fun detectViaReflection(): Version {
        return try {
            val cls = Class.forName("com.huawei.system.BuildEx")
            val method = cls.getMethod("getOsName")
            val osName = method.invoke(null) as? String
            parseOsName(osName)
        } catch (e: ClassNotFoundException) {
            Version.NOT_HARMONY
        } catch (e: Exception) {
            Log.w(TAG, "reflection 异常: ${e.message}")
            Version.NOT_HARMONY
        }
    }
}
