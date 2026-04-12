package p000;

import java.util.Comparator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zh0 implements Comparator {

    /* renamed from: a1 */
    public static final zh0 f61554a1 = new zh0(0);

    /* renamed from: a2 */
    public static final zh0 f61555a2 = new zh0(1);

    /* renamed from: a0 */
    public final /* synthetic */ int f61556a0;

    public /* synthetic */ zh0(int i) {
        this.f61556a0 = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f61556a0) {
            case 0:
                Comparable comparable = (Comparable) obj;
                Comparable comparable2 = (Comparable) obj2;
                t60.m214695b6(comparable, "a");
                t60.m214695b6(comparable2, "b");
                return comparable.compareTo(comparable2);
            default:
                Comparable comparable3 = (Comparable) obj;
                Comparable comparable4 = (Comparable) obj2;
                t60.m214695b6(comparable3, "a");
                t60.m214695b6(comparable4, "b");
                return comparable4.compareTo(comparable3);
        }
    }

    @Override // java.util.Comparator
    public final Comparator reversed() {
        switch (this.f61556a0) {
            case 0:
                return f61555a2;
            default:
                return f61554a1;
        }
    }
}
