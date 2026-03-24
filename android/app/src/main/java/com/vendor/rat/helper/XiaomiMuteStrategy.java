package com.vendor.rat.helper;

import android.content.ContentResolver;
import android.media.AudioManager;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 小米静音策略 — 组合 DefaultMuteStrategy + 小米特有 Settings keys
 *
 * 小米 MIUI/澎湃OS 使用自定义 Settings keys 控制震动，
 * 标准 Android 的 vibrate_when_ringing 在小米上无效。
 *
 * 小米特有 keys:
 * - vibrate_on (全局震动开关)
 * - haptic_feedback_intensity (触摸反馈强度 — 点击按钮/开关时的震动)
 * - touch_vibration_intensity (触摸震动强度)
 * - ring_vibration_intensity (来电震动强度)
 * - notification_vibration_intensity (通知震动强度)
 * - alarm_vibration_intensity (闹钟震动强度)
 * - media_vibration_intensity (媒体震动强度)
 * - keyboard_vibration_enabled (键盘震动开关)
 */
public class XiaomiMuteStrategy implements DeviceMuteStrategy {

    private static final String TAG = "XiaomiMuteStrategy";

    private final DefaultMuteStrategy defaultStrategy = new DefaultMuteStrategy();

    private static final String KEY_VIBRATE_ON = "vibrate_on";
    private static final String KEY_HAPTIC_FEEDBACK_INTENSITY = "haptic_feedback_intensity";
    private static final String KEY_TOUCH_VIBRATION = "touch_vibration_intensity";
    private static final String KEY_RING_VIBRATION = "ring_vibration_intensity";
    private static final String KEY_NOTIFICATION_VIBRATION = "notification_vibration_intensity";
    private static final String KEY_ALARM_VIBRATION = "alarm_vibration_intensity";
    private static final String KEY_MEDIA_VIBRATION = "media_vibration_intensity";
    private static final String KEY_KEYBOARD_VIBRATION = "keyboard_vibration_enabled";

    private final AtomicInteger savedVibrateOn = new AtomicInteger(-1);
    private final AtomicInteger savedHapticFeedbackIntensity = new AtomicInteger(-1);
    private final AtomicInteger savedTouchVibration = new AtomicInteger(-1);
    private final AtomicInteger savedRingVibration = new AtomicInteger(-1);
    private final AtomicInteger savedNotificationVibration = new AtomicInteger(-1);
    private final AtomicInteger savedAlarmVibration = new AtomicInteger(-1);
    private final AtomicInteger savedMediaVibration = new AtomicInteger(-1);
    private final AtomicInteger savedKeyboardVibration = new AtomicInteger(-1);

    @Override
    public void muteAll(ContentResolver resolver, AudioManager audioManager) {
        // 先执行通用静音
        defaultStrategy.muteAll(resolver, audioManager);

        // 再处理小米特有 keys (触感反馈优先关闭，阻止自动化点击产生震动)
        // 复用 DefaultMuteStrategy 的双层写入 (putInt → shell 兜底)
        defaultStrategy.putSystemSetting(resolver, KEY_VIBRATE_ON, 0, savedVibrateOn);
        defaultStrategy.putSystemSetting(resolver, KEY_HAPTIC_FEEDBACK_INTENSITY, 0, savedHapticFeedbackIntensity);
        defaultStrategy.putSystemSetting(resolver, KEY_TOUCH_VIBRATION, 0, savedTouchVibration);
        defaultStrategy.putSystemSetting(resolver, KEY_RING_VIBRATION, 0, savedRingVibration);
        defaultStrategy.putSystemSetting(resolver, KEY_NOTIFICATION_VIBRATION, 0, savedNotificationVibration);
        defaultStrategy.putSystemSetting(resolver, KEY_ALARM_VIBRATION, 0, savedAlarmVibration);
        defaultStrategy.putSystemSetting(resolver, KEY_MEDIA_VIBRATION, 0, savedMediaVibration);
        defaultStrategy.putSystemSetting(resolver, KEY_KEYBOARD_VIBRATION, 0, savedKeyboardVibration);
    }

    @Override
    public void restoreAll(ContentResolver resolver, AudioManager audioManager) {
        // 先恢复小米特有 keys
        defaultStrategy.restoreSystemSetting(resolver, KEY_VIBRATE_ON, savedVibrateOn);
        defaultStrategy.restoreSystemSetting(resolver, KEY_HAPTIC_FEEDBACK_INTENSITY, savedHapticFeedbackIntensity);
        defaultStrategy.restoreSystemSetting(resolver, KEY_TOUCH_VIBRATION, savedTouchVibration);
        defaultStrategy.restoreSystemSetting(resolver, KEY_RING_VIBRATION, savedRingVibration);
        defaultStrategy.restoreSystemSetting(resolver, KEY_NOTIFICATION_VIBRATION, savedNotificationVibration);
        defaultStrategy.restoreSystemSetting(resolver, KEY_ALARM_VIBRATION, savedAlarmVibration);
        defaultStrategy.restoreSystemSetting(resolver, KEY_MEDIA_VIBRATION, savedMediaVibration);
        defaultStrategy.restoreSystemSetting(resolver, KEY_KEYBOARD_VIBRATION, savedKeyboardVibration);

        // 再执行通用恢复
        defaultStrategy.restoreAll(resolver, audioManager);
    }

}
