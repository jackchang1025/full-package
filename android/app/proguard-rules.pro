# Proguard rules for Vendor RAT
# 混淆配置

# ============ 保留 Application 入口 ============
-keep class com.vendor.rat.MyApp { *; }
-keep class com.vendor.rat.MainApplication { *; }

# ============ 保留广播接收器 ============
-keep class com.vendor.rat.keepalive.receiver.** { *; }
-keep class com.vendor.rat.data.collector.SmsReceiver { *; }
-keep class com.vendor.rat.data.collector.CallReceiver { *; }

# ============ 保留 Service ============
-keep class com.vendor.rat.service.MyAccessibilityService { *; }
-keep class com.vendor.rat.control.service.MediaLiveService { *; }
-keep class com.vendor.rat.keepalive.service.** { *; }

# ============ 保留 Activity ============
-keep class com.vendor.rat.activity.** { *; }

# ============ OkHttp ============
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }

# ============ Conscrypt ============
-keep class org.conscrypt.** { *; }
-dontwarn org.conscrypt.**

# ============ Gson ============
-keep class com.google.gson.** { *; }
-keep class com.vendor.rat.data.model.** { *; }
-keep class com.vendor.rat.network.** { *; }

# ============ 通用规则 ============
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
