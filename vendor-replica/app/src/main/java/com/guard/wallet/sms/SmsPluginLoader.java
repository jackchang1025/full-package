package com.guard.wallet.sms;

/**
 * 短信识别插件加载器 -- SmsMessageListener 状态持有者。
 * a 持有已加载的识别插件, b 追踪加载状态: 0 空闲, 1 加载中, 2 就绪。
 *
 * vendor 原始路径: u/b.java
 */

import com.guard.wallet.core.AppUtils;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.guard.wallet.resp.SmsRecognizePlug;
import java.util.LinkedList;
import java.util.List;

public class SmsPluginLoader {
    private static final String TAG = "SmsMessageListener";

    public final LinkedList<SmsRecognizePlug> a = new LinkedList<>();
    public Integer b = 0;

    /**
     * vendor a() -- load SMS recognize plugs.
     * Returns true if plugs were loaded from cache (state=2), meaning l.y() should be called.
     * Returns false if loading is needed (state=1) or error (state=0).
     */
    public synchronized boolean a() {
        try {
            List<SmsRecognizePlug> cached = b();
            this.a.clear();
            if (cached != null && !cached.isEmpty()) {
                this.a.addAll(cached);
                this.b = 2;
                Log.d(TAG, "已加载短信识别插件:" + this.a.size());
                return true;
            }

            this.b = 1;
            return false;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            this.b = 0;
            return false;
        }
    }

    private List<SmsRecognizePlug> b() {
        try {
            String base = com.guard.wallet.utils.SystemHelper.i0();
            if (AppUtils.B(base)) {
                return null;
            }
            String json = AppUtils.K(base.concat("/smsRecognizePlugs.json"));
            if (AppUtils.B(json)) {
                return null;
            }
            return (List<SmsRecognizePlug>) com.guard.wallet.utils.SharedPrefsManager.c(
                    json,
                    new TypeToken<List<SmsRecognizePlug>>() {});
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }
}
