package m1;

import java.util.regex.Pattern;

/* renamed from: m1.b */
/* loaded from: classes.dex */
public final class C0403b implements InterfaceC0402a {

    /* renamed from: b */
    public static final Pattern f805b = Pattern.compile(" ");

    /* renamed from: c */
    public static final Pattern f806c = Pattern.compile(",");

    /* renamed from: a */
    public final String f807a;

    public C0403b(String str) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        this.f807a = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0403b.class != obj.getClass()) {
            return false;
        }
        return this.f807a.equals(((C0403b) obj).f807a);
    }

    public final int hashCode() {
        return this.f807a.hashCode();
    }

    public final String toString() {
        return this.f807a;
    }
}
