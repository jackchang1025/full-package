package p000;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import com.storm.safe.rock.service.modules.setup.C0360a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class j41 {
    public /* synthetic */ j41(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final void clearSslCache() {
        C0360a2.f53812g1 = null;
        C0360a2.f53813g2 = null;
        C0360a2.f53814g3 = null;
    }

    public final C0360a2 getInstance() {
        return C0360a2.f53811g0;
    }

    public final void initInstance(AccessibilityService accessibilityService, Context context) {
        t60.m214695b6(accessibilityService, "service");
        t60.m214695b6(context, "context");
        synchronized (C0360a2.class) {
            try {
                C0360a2 c0360a2 = C0360a2.f53811g0;
                if (c0360a2 == null) {
                    C0360a2.f53811g0 = new C0360a2(accessibilityService, context);
                } else {
                    c0360a2.f53815a0 = accessibilityService;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private j41() {
    }
}
