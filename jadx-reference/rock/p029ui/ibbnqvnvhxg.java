package com.storm.safe.rock.p029ui;

import android.app.Activity;
import android.os.Bundle;
import com.storm.safe.rock.service.dqtvuisjd;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ibbnqvnvhxg extends Activity {

    /* renamed from: a0 */
    public static final C0383a0 f55194a0 = new C0383a0(null);

    /* renamed from: a1 */
    public static volatile ibbnqvnvhxg f55195a1;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.ui.ibbnqvnvhxg$a0 */
    public static final class C0383a0 {
        public /* synthetic */ C0383a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final void finishIfRunning() {
            ibbnqvnvhxg ibbnqvnvhxgVar = ibbnqvnvhxg.f55195a1;
            if (ibbnqvnvhxgVar != null) {
                ibbnqvnvhxgVar.finish();
            }
        }

        public final boolean isRunning() {
            return ibbnqvnvhxg.f55195a1 != null;
        }

        private C0383a0() {
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setSoftInputMode(3);
        getWindow().addFlags(4719120);
        getWindow().setLayout(1, 1);
        f55195a1 = this;
        dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
        if (c0290a0 != null) {
            c0290a0.f52479l0 = false;
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        if (t60.m214686a2(f55195a1, this)) {
            f55195a1 = null;
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 == null) {
                return;
            }
            c0290a0.f52479l0 = false;
        }
    }
}
