package p000;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: rx */
/* loaded from: classes2.dex */
public final class C1197rx implements Iterator, d80 {

    /* renamed from: a0 */
    public int f59828a0 = -1;

    /* renamed from: a1 */
    public int f59829a1;

    /* renamed from: a2 */
    public int f59830a2;

    /* renamed from: a3 */
    public n60 f59831a3;

    /* renamed from: a4 */
    public int f59832a4;

    /* renamed from: a5 */
    public final /* synthetic */ C1198ry f59833a5;

    public C1197rx(C1198ry c1198ry) {
        this.f59833a5 = c1198ry;
        int iM214413a9 = AbstractC1117qo.m214413a9(0, 0, c1198ry.f59834a0.length());
        this.f59829a1 = iM214413a9;
        this.f59830a2 = iM214413a9;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001c  */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.internal.Lambda, l10] */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214551a0() {
        Pair pair;
        C1198ry c1198ry = this.f59833a5;
        CharSequence charSequence = c1198ry.f59834a0;
        int i = this.f59830a2;
        if (i < 0) {
            this.f59828a0 = 0;
            this.f59831a3 = null;
            return;
        }
        int i2 = c1198ry.f59835a1;
        if (i2 > 0) {
            int i3 = this.f59832a4 + 1;
            this.f59832a4 = i3;
            if (i3 >= i2) {
                this.f59831a3 = new n60(this.f59829a1, AbstractC0779a1.m213657b0(charSequence), 1);
                this.f59830a2 = -1;
            } else if (i <= charSequence.length() && (pair = (Pair) c1198ry.f59836a2.invoke(charSequence, Integer.valueOf(this.f59830a2))) != null) {
                int iIntValue = ((Number) pair.f57556a0).intValue();
                int iIntValue2 = ((Number) pair.f57557a1).intValue();
                this.f59831a3 = AbstractC1117qo.m214463g2(this.f59829a1, iIntValue);
                int i4 = iIntValue + iIntValue2;
                this.f59829a1 = i4;
                this.f59830a2 = i4 + (iIntValue2 == 0 ? 1 : 0);
            } else {
                this.f59831a3 = new n60(this.f59829a1, AbstractC0779a1.m213657b0(charSequence), 1);
                this.f59830a2 = -1;
            }
        }
        this.f59828a0 = 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f59828a0 == -1) {
            m214551a0();
        }
        return this.f59828a0 == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f59828a0 == -1) {
            m214551a0();
        }
        if (this.f59828a0 == 0) {
            throw new NoSuchElementException();
        }
        n60 n60Var = this.f59831a3;
        t60.m214693b4(n60Var, "null cannot be cast to non-null type kotlin.ranges.IntRange");
        this.f59831a3 = null;
        this.f59828a0 = -1;
        return n60Var;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
