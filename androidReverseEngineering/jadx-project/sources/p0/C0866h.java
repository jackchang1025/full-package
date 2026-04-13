package p0;

import java.util.Comparator;

/* renamed from: p0.h */
/* loaded from: classes.dex */
public final /* synthetic */ class C0866h implements Comparator {

    /* renamed from: a */
    public final /* synthetic */ int f1790a;

    /* JADX WARN: Removed duplicated region for block: B:10:0x0037 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0035 A[RETURN, SYNTHETIC] */
    @Override // java.util.Comparator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int compare(Object obj, Object obj2) {
        switch (this.f1790a) {
            case 0:
                String str = (String) obj;
                String str2 = (String) obj2;
                int min = Math.min(str.length(), str2.length());
                for (int i2 = 4; i2 < min; i2++) {
                    char charAt = str.charAt(i2);
                    char charAt2 = str2.charAt(i2);
                    if (charAt != charAt2) {
                        return charAt < charAt2 ? -1 : 1;
                    }
                }
                int length = str.length();
                int length2 = str2.length();
                if (length == length2) {
                    return 0;
                }
                if (length < length2) {
                }
            default:
                return ((String) obj).compareTo((String) obj2);
        }
    }
}
