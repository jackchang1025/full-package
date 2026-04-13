package i0;

import android.text.TextUtils;

/* renamed from: i0.a */
/* loaded from: classes.dex */
public final class C0330a implements Cloneable {

    /* renamed from: a */
    public final String f645a;

    /* renamed from: b */
    public final String f646b;

    public C0330a(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.f645a = str;
        this.f646b = str2;
    }

    public final Object clone() {
        return super.clone();
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0330a)) {
            return false;
        }
        C0330a c0330a = (C0330a) obj;
        return this.f645a.equals(c0330a.f645a) && TextUtils.equals(this.f646b, c0330a.f646b);
    }

    public final int hashCode() {
        return this.f645a.hashCode() ^ this.f646b.hashCode();
    }

    public final String toString() {
        return this.f645a + "=" + this.f646b;
    }
}
