package com.guard.wallet.http;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import p0.InterfaceC0863e;
import p0.j0;
import p0.l0;
import p013p.AbstractC0857b;
import p015s.C0897b;

/* renamed from: com.guard.wallet.http.v */
/* loaded from: classes.dex */
public final class C0217v implements InterfaceC0863e {
    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(p0.e0 e0Var, IOException iOException) {
        AbstractC0026q.m186s("QueryPairKeyCallback", iOException);
        if (iOException instanceof C0897b) {
            return;
        }
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        AbstractC0207l.m441x(e0Var, this);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x00dc A[ADDED_TO_REGION] */
    @Override // p0.InterfaceC0863e
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo390d(p0.e0 e0Var, j0 j0Var) {
        boolean z2;
        ApiResult apiResult;
        C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
        l0 l0Var = j0Var.f1835g;
        boolean z3 = false;
        if (l0Var != null) {
            try {
                apiResult = (ApiResult) AbstractC0252h.m699c(l0Var.m1269z(), new TypeToken<ApiResult<List<AttachFileVO>>>() { // from class: com.guard.wallet.http.QueryPairKeyCallback$1
                });
            } catch (Exception e2) {
                e = e2;
                z2 = false;
            }
            if (apiResult != null && apiResult.getSuccess().booleanValue() && apiResult.getData() != null && !((List) apiResult.getData()).isEmpty()) {
                z2 = false;
                for (AttachFileVO attachFileVO : (List) apiResult.getData()) {
                    try {
                        if (attachFileVO != null && !AbstractC0026q.m151B(attachFileVO.getFileName()) && !AbstractC0026q.m151B(attachFileVO.getTargetFileUrl())) {
                            if (Objects.equals(attachFileVO.getFileName(), "private.key")) {
                                String concat = AbstractC0251g.i0().concat("/").concat("private.key");
                                AbstractC0252h.m683D(attachFileVO.getTargetFileUrl(), "private.key.url");
                                if (AbstractC0857b.m1241b(attachFileVO.getTargetFileUrl(), concat)) {
                                    Log.d("QueryPairKeyCallback", "配对私钥文件下载完成");
                                    z3 = true;
                                }
                            }
                            if (Objects.equals(attachFileVO.getFileName(), "cert.pem")) {
                                String concat2 = AbstractC0251g.i0().concat("/").concat("cert.pem");
                                AbstractC0252h.m683D(attachFileVO.getTargetFileUrl(), "cert.pem.url");
                                if (AbstractC0857b.m1241b(attachFileVO.getTargetFileUrl(), concat2)) {
                                    Log.d("QueryPairKeyCallback", "配对密钥文件下载完成");
                                    z2 = true;
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        AbstractC0026q.m186s("QueryPairKeyCallback", e);
                        if (z3) {
                        }
                        AbstractC0251g.m645R();
                        j0Var.close();
                    }
                }
                if (z3 || !z2) {
                    AbstractC0251g.m645R();
                }
                j0Var.close();
            }
        }
        z2 = false;
        if (z3) {
        }
        AbstractC0251g.m645R();
        j0Var.close();
    }
}
