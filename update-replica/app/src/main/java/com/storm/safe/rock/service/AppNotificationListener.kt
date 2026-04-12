package com.storm.safe.rock.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

/**
 * NotificationListenerService that intercepts notifications.
 *
 * Reverse-engineered from JADX reference `sqlszawlrvc.java`.
 * Key behaviors:
 * - Singleton instance exposed via companion object
 * - onListenerConnected/Disconnected manage instance lifecycle
 * - onNotificationPosted extracts package name for forwarding
 * - Null-safe handling for all notification callbacks
 */
class AppNotificationListener : NotificationListenerService() {

    companion object {
        private const val TAG = "AppNotificationListener"

        @Volatile
        var instance: AppNotificationListener? = null
            private set
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        instance = this
        android.util.Log.i(TAG, "通知监听已连接")
    }

    override fun onListenerDisconnected() {
        instance = null
        super.onListenerDisconnected()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) return
        try {
            val pkg = sbn.packageName
            // TODO Phase 8: Forward to NetworkManager for SMS/notification interception
            android.util.Log.d(TAG, "通知: $pkg")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "处理通知失败", e)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Optional: track notification removal
    }
}
