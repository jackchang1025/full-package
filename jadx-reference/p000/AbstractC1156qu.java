package p000;

import kotlinx.coroutines.RunnableC0782a2;
import kotlinx.coroutines.android.C0785a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qu */
/* loaded from: classes2.dex */
public abstract class AbstractC1156qu {

    /* renamed from: a0 */
    public static final InterfaceC1191rs f59549a0;

    static {
        String property;
        InterfaceC1191rs interfaceC1191rs;
        int i = q41.f59384a0;
        try {
            property = System.getProperty("kotlinx.coroutines.main.delay");
        } catch (SecurityException unused) {
            property = null;
        }
        if (property != null ? Boolean.parseBoolean(property) : false) {
            C1180rh c1180rh = AbstractC1262tj.f60233a0;
            C0785a0 c0785a0 = sc0.f59953a0;
            C0785a0 c0785a02 = c0785a0.f57666a4;
            interfaceC1191rs = c0785a0;
            if (c0785a0 == null) {
                interfaceC1191rs = RunnableC0782a2.f57657a9;
            }
        } else {
            interfaceC1191rs = RunnableC0782a2.f57657a9;
        }
        f59549a0 = interfaceC1191rs;
    }
}
