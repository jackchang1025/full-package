package com.storm.safe.rock.security

import android.content.Context
import android.os.Build
import android.os.Debug
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.Socket

object SecurityChecker {

    var policy: SecurityPolicy = SecurityPolicy.NORMAL

    data class CheckResult(
        val issues: List<String>,
        val isClean: Boolean = issues.isEmpty()
    )

    fun runAllChecks(context: Context): CheckResult {
        val issues = mutableListOf<String>()
        if (checkDebugger()) issues.add("检测到调试器")
        if (checkRootFiles()) issues.add("检测到 Root")
        if (checkRootPackages()) issues.add("检测到 Root 包")
        if (policy != SecurityPolicy.RELAXED && checkEmulator()) issues.add("检测到模拟器")
        if (checkXposed()) issues.add("检测到 Xposed")
        if (checkFrida()) issues.add("检测到 Frida")
        if (checkDebuggable(context)) issues.add("APK 可调试")
        return CheckResult(issues)
    }

    fun checkDebugger(): Boolean =
        try {
            Debug.isDebuggerConnected() || Debug.waitingForDebugger()
        } catch (_: Exception) {
            false
        }

    fun checkRootFiles(): Boolean {
        val paths = listOf(
            "/system/app/Superuser.apk", "/system/app/su", "/system/bin/su",
            "/sbin/su", "/system/xbin/su", "/system/xbin/daemonsu",
            "/system/xbin/busybox", "/system/app/daemonsu",
            "/system/bin/failsafe/su", "/su", "/su/bin",
            "/data/local/su", "/data/local/tmp/su", "/system/xbin/mu"
        )
        return paths.any { File(it).exists() }
    }

    fun checkRootPackages(): Boolean {
        val packages = listOf(
            "com.noshufou.android.su", "com.thirdparty.superuser",
            "eu.chainfire.supersu", "com.koushikdutta.superuser",
            "com.zachspong.temprootremovejb", "com.ramdroid.appquarantine",
            "com.topjohnwu.magisk", "me.phh.superuser",
            "com.kingroot.kinguser"
        )
        return packages.any { File("/data/data/$it").exists() }
    }

    fun checkEmulator(): Boolean {
        val checks = listOf(
            Build.FINGERPRINT.contains("generic", ignoreCase = true),
            Build.FINGERPRINT.contains("unknown", ignoreCase = true),
            Build.MODEL.contains("google_sdk", ignoreCase = true),
            Build.MODEL.contains("Emulator", ignoreCase = true),
            Build.MODEL.contains("Android SDK built for x86", ignoreCase = true),
            Build.MANUFACTURER.contains("Genymotion", ignoreCase = true),
            Build.BRAND.startsWith("generic", ignoreCase = true) &&
                Build.DEVICE.startsWith("generic", ignoreCase = true),
            Build.PRODUCT in listOf(
                "google_sdk", "sdk", "sdk_x86", "vbox86p", "emulator", "simulator"
            ),
            Build.HARDWARE.contains("goldfish", ignoreCase = true),
            Build.HARDWARE.contains("ranchu", ignoreCase = true),
            Build.BOARD == "unknown"
        )
        if (checks.any { it }) return true

        val emuFiles = listOf(
            "/dev/socket/qemud", "/dev/qemu_pipe",
            "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace",
            "/system/bin/qemu-props", "/dev/socket/genyd", "/dev/socket/baseband_genyd"
        )
        return emuFiles.any { File(it).exists() }
    }

    fun checkXposed(): Boolean {
        try {
            val stackTrace = Exception("stack check").stackTrace
            for (element in stackTrace) {
                if (element.className.contains("xposed", ignoreCase = true) ||
                    element.className.contains("de.robv.android.xposed", ignoreCase = true)
                ) {
                    return true
                }
            }
        } catch (_: Exception) {
        }
        val packages = listOf(
            "de.robv.android.xposed.installer", "io.va.exposed",
            "org.meowcat.edxposed.manager", "org.lsposed.manager"
        )
        return packages.any { File("/data/data/$it").exists() }
    }

    fun checkFrida(): Boolean {
        try {
            Socket("127.0.0.1", 27042).close()
            return true
        } catch (_: Exception) {
        }
        try {
            val process = Runtime.getRuntime().exec("ps")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.contains("frida", ignoreCase = true)) {
                    process.destroy()
                    return true
                }
            }
            process.destroy()
        } catch (_: Exception) {
        }
        try {
            val maps = File("/proc/self/maps")
            if (maps.exists()) {
                val content = maps.readText()
                if (content.contains("frida-agent", ignoreCase = true) ||
                    content.contains("frida-gadget", ignoreCase = true)
                ) {
                    return true
                }
            }
        } catch (_: Exception) {
        }
        return false
    }

    fun checkDebuggable(context: Context): Boolean =
        try {
            (context.applicationInfo.flags and 2) != 0
        } catch (_: Exception) {
            false
        }
}
