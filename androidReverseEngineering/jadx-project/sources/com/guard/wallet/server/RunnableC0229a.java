package com.guard.wallet.server;

import a1.AbstractC0026q;
import android.graphics.Bitmap;
import android.os.Message;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.b0;
import com.guard.wallet.req.UploadAppIconVO;
import com.guard.wallet.utils.AbstractC0252h;
import e0.C0265c;
import java.io.Serializable;
import p013p.AbstractC0857b;

/* renamed from: com.guard.wallet.server.a */
/* loaded from: classes.dex */
public final class RunnableC0229a implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f287a;

    /* renamed from: b */
    public final /* synthetic */ String f288b;

    /* renamed from: c */
    public final /* synthetic */ Serializable f289c;

    /* renamed from: d */
    public final /* synthetic */ Object f290d;

    public /* synthetic */ RunnableC0229a(Object obj, String str, Serializable serializable, int i2) {
        this.f287a = i2;
        this.f290d = obj;
        this.f288b = str;
        this.f289c = serializable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Bitmap m159J;
        int i2 = this.f287a;
        String str = this.f288b;
        Object obj = this.f289c;
        switch (i2) {
            case 0:
                byte[] bArr = (byte[]) obj;
                String str2 = AbstractC0207l.f252a;
                String m708l = AbstractC0252h.m708l("deviceId");
                if (!AbstractC0026q.m151B(m708l) && !AbstractC0026q.m151B(str) && bArr != null && bArr.length > 0) {
                    new C0204i().m411k(new UploadAppIconVO(m708l, str, "100018"), "/api/package/uploadAppIcon.json", str.concat("_ic_launcher").concat(".webp"), bArr, new b0());
                    break;
                }
                break;
            default:
                try {
                    if (AbstractC0857b.m1240a(str, (String) obj) && (m159J = AbstractC0026q.m159J((String) obj)) != null) {
                        Message obtain = Message.obtain();
                        obtain.obj = m159J;
                        ((C0265c) this.f290d).f439a.sendMessage(obtain);
                        break;
                    }
                } catch (Exception e2) {
                    int i3 = C0265c.f438b;
                    AbstractC0026q.m186s("e0.c", e2);
                    return;
                }
                break;
        }
    }
}
