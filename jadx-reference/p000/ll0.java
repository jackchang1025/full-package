package p000;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import com.storm.safe.rock.service.modules.setup.C0358a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ll0 {
    public /* synthetic */ ll0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0358a0 getInstance() {
        return C0358a0.f53791b7;
    }

    public final void initInstance(AccessibilityService accessibilityService, Context context) {
        t60.m214695b6(accessibilityService, "service");
        t60.m214695b6(context, "context");
        if (C0358a0.f53791b7 == null) {
            synchronized (C0358a0.class) {
                if (C0358a0.f53791b7 == null) {
                    C0358a0.f53791b7 = new C0358a0(accessibilityService, context);
                }
            }
        }
    }

    private ll0() {
    }
}
