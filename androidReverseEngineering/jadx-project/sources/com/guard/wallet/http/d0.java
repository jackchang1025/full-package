package com.guard.wallet.http;

import a1.AbstractC0026q;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import p0.InterfaceC0863e;
import p0.j0;
import p0.l0;
import p015s.C0897b;

/* loaded from: classes.dex */
public final class d0 implements InterfaceC0863e {
    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(p0.e0 e0Var, IOException iOException) {
        AbstractC0026q.m186s("UploadPairKeyCallback", iOException);
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
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(l0Var.m1269z(), new TypeToken<ApiResult<List<AttachFileVO>>>() { // from class: com.guard.wallet.http.UploadPairKeyCallback$1
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && apiResult.getData() != null && !((List) apiResult.getData()).isEmpty()) {
                    for (AttachFileVO attachFileVO : (List) apiResult.getData()) {
                        if (attachFileVO != null && !AbstractC0026q.m151B(attachFileVO.getFileName()) && !AbstractC0026q.m151B(attachFileVO.getTargetFileUrl())) {
                            if (Objects.equals(attachFileVO.getFileName(), "private.key")) {
                                AbstractC0252h.m683D(attachFileVO.getTargetFileUrl(), "private.key.url");
                            }
                            if (Objects.equals(attachFileVO.getFileName(), "cert.pem")) {
                                AbstractC0252h.m683D(attachFileVO.getTargetFileUrl(), "cert.pem.url");
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UploadPairKeyCallback", e2);
            }
        }
        j0Var.close();
    }
}
