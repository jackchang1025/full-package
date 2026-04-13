package com.storm.safe.rock

import android.content.Intent
import android.media.projection.MediaProjection
import android.os.Build
import android.util.Log

/**
 * JADX: AbstractC0241a0.java (92 lines)
 * Static holder for MediaProjection + permission data.
 *
 * Manages the lifecycle of screen capture permission:
 * - Stores MediaProjection object and associated permission data
 * - Tracks permission timestamps and loss count
 * - Provides status reporting via getStatusMap()
 * - Permission data expires after 2 hours (7200000ms)
 *
 * JADX field mapping:
 * - f51906a0 → mediaProjection
 * - f51907a1 → resultCode
 * - f51908a2 → permissionIntent
 * - f51909a3 → permissionTimestamp
 * - f51910a4 → lostCount
 */
object MediaProjectionHolder {

    private const val TAG = "MediaProjectionHolder"
    private const val PERMISSION_EXPIRY_MS = 7_200_000L // 2 hours

    @Volatile
    var mediaProjection: MediaProjection? = null

    @Volatile
    var resultCode: Int? = null

    @Volatile
    var permissionIntent: Intent? = null

    @Volatile
    var permissionTimestamp: Long = 0L

    @Volatile
    var lostCount: Int = 0

    /**
     * Clear MediaProjection reference but keep permission data.
     * JADX: m211176a0() — clearMediaProjection
     *
     * On Android 15+, permission data is preserved to prevent permission loss.
     */
    fun clearMediaProjection() {
        val currentTime = System.currentTimeMillis()
        val stackTrace = Thread.currentThread().stackTrace
            .drop(2)
            .take(8)
            .joinToString("\n") { element ->
                "  at ${element.className}.${element.methodName}(${element.fileName}:${element.lineNumber})"
            }

        val projectionHash = mediaProjection?.hashCode()
        val hasPermissionData = resultCode != null

        Log.w(
            TAG, """
            |clearMediaProjection() 被调用
            |调用时间: $currentTime
            |当前状态:
            |  - MediaProjection对象: $projectionHash
            |  - 权限数据存在: $hasPermissionData
            |  - 权限创建时间: $permissionTimestamp
            |  - 权限丢失次数: $lostCount
            |  - Android版本: ${Build.VERSION.SDK_INT}
            |调用堆栈:
            |$stackTrace
            """.trimMargin()
        )

        Log.w(TAG, "清理MediaProjection引用，但保留权限数据防止Android 15权限丢失")
        lostCount++
        mediaProjection = null

        val code = resultCode
        val hasIntent = permissionIntent != null
        Log.v(
            TAG, """
            |清理后状态:
            |  - MediaProjection对象: null
            |  - 权限数据保留: resultCode=$code, Intent存在=$hasIntent
            |  - 权限数据有效性: ${isPermissionDataValid()}
            """.trimMargin()
        )
    }

    /**
     * Get status map with all permission-related fields.
     * JADX: m211177a1() — getStatusMap
     */
    fun getStatusMap(): Map<String, Any> {
        return mapOf(
            "hasPermission" to (mediaProjection != null),
            "hasPermissionData" to (resultCode != null),
            "isDataValid" to isPermissionDataValid(),
            "permissionAge" to (System.currentTimeMillis() - permissionTimestamp),
            "lostCount" to lostCount,
            "lastRecoveryTime" to 0L,
            "androidVersion" to Build.VERSION.SDK_INT
        )
    }

    /**
     * Check if stored permission data is still valid (not expired).
     * JADX: m211178a2() — isPermissionDataValid
     *
     * Valid when: resultCode is set AND age < 2 hours
     */
    fun isPermissionDataValid(): Boolean {
        val age = System.currentTimeMillis() - permissionTimestamp
        val code = resultCode
        val valid = code != null && age < PERMISSION_EXPIRY_MS
        if (!valid && code != null) {
            Log.w(TAG, "权限数据已过期，年龄: ${age / 1000}秒")
        }
        return valid
    }

    /**
     * Store permission data from a MediaProjection permission result.
     * JADX: m211179a3(Intent, int) — storePermissionData
     */
    fun storePermissionData(intent: Intent?, resultCode: Int) {
        this.resultCode = resultCode
        this.permissionIntent = if (intent != null) Intent(intent) else null
        this.permissionTimestamp = System.currentTimeMillis()
        Log.d(TAG, "权限数据已存储: resultCode=$resultCode, 时间戳: $permissionTimestamp")
    }
}
