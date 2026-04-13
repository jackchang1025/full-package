package com.guard.wallet.receiver;

import com.guard.wallet.core.AppUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;

public class ShutDownBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "ShutDownBroadcastReceiver";
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

            String action = intent.getAction();

            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                Log.d(TAG, "手机关机了 ACTION_SHUTDOWN");
            } else if ("android.intent.action.QUICKBOOT_POWEROFF".equals(action)) {
                Log.d(TAG, "手机关机了 QUICKBOOT_POWEROFF");
            }

            MessageRecordVO record = new MessageRecordVO();
            MessageBodyVO body = new MessageBodyVO();
            record.setIntentCode(intent.getAction());
            record.setExtraBody(body);
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }
}
