package p000;

import java.util.Enumeration;

/* renamed from: vb */
/* loaded from: classes2.dex */
public class C1331vb {
    public static Enumeration getNames() {
        return C1332vc.getNames();
    }

    public static C1336vg getParameterSpec(String str) {
        bi1 byNameX9 = C1332vc.getByNameX9(str);
        if (byNameX9 == null) {
            try {
                byNameX9 = C1332vc.getByOIDX9(new C0160c5(str));
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
        if (byNameX9 == null) {
            return null;
        }
        return new C1336vg(str, byNameX9.getCurve(), byNameX9.getG(), byNameX9.getN(), byNameX9.getH(), byNameX9.getSeed());
    }
}
