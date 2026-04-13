package f0;

import java.util.Comparator;

/* renamed from: f0.i */
/* loaded from: classes.dex */
public final class C0288i implements Comparator {

    /* renamed from: a */
    public static final C0288i f522a = new C0288i();

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        long j2 = ((RunnableC0287h) obj).f520c;
        long j3 = ((RunnableC0287h) obj2).f520c;
        if (j2 == j3) {
            return 0;
        }
        return j2 > j3 ? 1 : -1;
    }
}
