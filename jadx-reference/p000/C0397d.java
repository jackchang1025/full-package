package p000;

import android.graphics.Rect;
import java.util.Comparator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: d */
/* loaded from: classes.dex */
public final class C0397d implements Comparator {

    /* renamed from: a0 */
    public final Rect f55544a0 = new Rect();

    /* renamed from: a1 */
    public final Rect f55545a1 = new Rect();

    /* renamed from: a2 */
    public final boolean f55546a2;

    /* renamed from: a3 */
    public final C1351vv f55547a3;

    public C0397d(C1351vv c1351vv, boolean z) {
        this.f55546a2 = z;
        this.f55547a3 = c1351vv;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        this.f55547a3.getClass();
        Rect rect = this.f55544a0;
        ((C0748k7) obj).m213461a4(rect);
        Rect rect2 = this.f55545a1;
        ((C0748k7) obj2).m213461a4(rect2);
        int i = rect.top;
        int i2 = rect2.top;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        int i3 = rect.left;
        int i4 = rect2.left;
        boolean z = this.f55546a2;
        if (i3 < i4) {
            return z ? 1 : -1;
        }
        if (i3 > i4) {
            return z ? -1 : 1;
        }
        int i5 = rect.bottom;
        int i6 = rect2.bottom;
        if (i5 < i6) {
            return -1;
        }
        if (i5 > i6) {
            return 1;
        }
        int i7 = rect.right;
        int i8 = rect2.right;
        if (i7 < i8) {
            return z ? 1 : -1;
        }
        if (i7 > i8) {
            return z ? -1 : 1;
        }
        return 0;
    }
}
