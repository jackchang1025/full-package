package p000;

import java.util.AbstractList;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: h0 */
/* loaded from: classes2.dex */
public abstract class AbstractC0564h0 extends AbstractList implements List, f80 {
    /* renamed from: a0 */
    public abstract int mo210617a0();

    /* renamed from: a1 */
    public abstract Object mo210618a1(int i);

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ Object remove(int i) {
        return mo210618a1(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return mo210617a0();
    }
}
