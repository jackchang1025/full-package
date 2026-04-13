package com.guard.wallet.http;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.DeviceInfoVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.IOException;
import p0.C0879u;
import p0.InterfaceC0863e;
import p0.f0;
import p0.j0;
import p0.l0;
import p015s.C0897b;

/* renamed from: com.guard.wallet.http.e */
/* loaded from: classes.dex */
public final class C0200e implements InterfaceC0863e {
    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(p0.e0 e0Var, IOException iOException) {
        AbstractC0026q.m186s("DeviceIdCallback", iOException);
        if ((iOException instanceof C0897b) || AbstractC0026q.m151B(e0Var.f1773c.f1777a.f1914h)) {
            return;
        }
        f0 f0Var = e0Var.f1773c;
        C0204i.m400c(f0Var.f1777a.f1914h);
        C0879u c0879u = f0Var.f1777a;
        Log.d("DeviceIdCallback", c0879u.f1914h);
        if (c0879u.f1914h.contains("127.0.0.1:7911")) {
            AbstractC0207l.m424g("http://127.0.0.1:7912");
        }
        if (c0879u.f1914h.contains("127.0.0.1:7912")) {
            String m708l = AbstractC0252h.m708l("deviceId");
            DeviceInfoVO of = DeviceInfoVO.of();
            of.setDeviceId(m708l);
            new C0204i().m408h(of, "/api/device/register.json", new C0218w());
        }
    }

    @Override // p0.InterfaceC0863e
    /* renamed from: d */
    public final void mo390d(p0.e0 e0Var, j0 j0Var) {
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        l0 l0Var = j0Var.f1835g;
        if (l0Var != null) {
            try {
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(l0Var.m1269z(), new TypeToken<ApiResult<String>>() { // from class: com.guard.wallet.http.DeviceIdCallback$1
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && !AbstractC0026q.m151B(apiResult.getData())) {
                    AbstractC0252h.m683D((String) apiResult.getData(), "deviceId");
                    if (!AbstractC0026q.m189v(AbstractC0251g.i0())) {
                        AbstractC0207l.m438u();
                    }
                    AbstractC0207l.m443z();
                    AbstractC0207l.m420c();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("DeviceIdCallback", e2);
            }
        }
        j0Var.close();
    }
}
