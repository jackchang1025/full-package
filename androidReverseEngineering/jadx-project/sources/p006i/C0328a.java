package p006i;

import a1.AbstractC0026q;

/* renamed from: i.a */
/* loaded from: classes.dex */
public final class C0328a implements InterfaceC0329b {

    /* renamed from: a */
    public final /* synthetic */ int f642a;

    /* renamed from: b */
    public final String f643b;

    /* renamed from: c */
    public final boolean f644c;

    public /* synthetic */ C0328a(String str, boolean z2, int i2) {
        this.f642a = i2;
        this.f643b = str;
        this.f644c = z2;
    }

    /* renamed from: a */
    public final int m872a(String str) {
        int i2 = this.f642a;
        boolean z2 = this.f644c;
        String str2 = this.f643b;
        switch (i2) {
            case 0:
                if (!AbstractC0026q.m151B(str)) {
                    if (str.contains(str2)) {
                        break;
                    }
                } else {
                    break;
                }
                break;
            default:
                if (!AbstractC0026q.m151B(str)) {
                    if (str.endsWith(str2)) {
                        break;
                    }
                } else {
                    break;
                }
                break;
        }
        return -1;
    }
}
