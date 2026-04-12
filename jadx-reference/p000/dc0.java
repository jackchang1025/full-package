package p000;

import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class dc0 {

    /* renamed from: a1 */
    public static final dc0 f55690a1 = new dc0(new ec0(cc0.m210824a0(new Locale[0])));

    /* renamed from: a0 */
    public final ec0 f55691a0;

    public dc0(ec0 ec0Var) {
        this.f55691a0 = ec0Var;
    }

    /* renamed from: a0 */
    public static dc0 m212582a0(String str) {
        if (str == null || str.isEmpty()) {
            return f55690a1;
        }
        String[] strArrSplit = str.split(",", -1);
        int length = strArrSplit.length;
        Locale[] localeArr = new Locale[length];
        for (int i = 0; i < length; i++) {
            localeArr[i] = bc0.m210659a0(strArrSplit[i]);
        }
        return new dc0(new ec0(cc0.m210824a0(localeArr)));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof dc0) {
            return this.f55691a0.equals(((dc0) obj).f55691a0);
        }
        return false;
    }

    public final int hashCode() {
        return this.f55691a0.f55969a0.hashCode();
    }

    public final String toString() {
        return this.f55691a0.f55969a0.toString();
    }
}
