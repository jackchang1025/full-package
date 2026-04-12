package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ld0 extends AbstractC0528g9 {

    /* renamed from: a0 */
    public final /* synthetic */ md0 f57885a0;

    public ld0(md0 md0Var) {
        this.f57885a0 = md0Var;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof String) {
            return super.contains((String) obj);
        }
        return false;
    }

    @Override // java.util.List
    public final Object get(int i) {
        String strGroup = this.f57885a0.f58332a0.group(i);
        return strGroup == null ? "" : strGroup;
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.f57885a0.f58332a0.groupCount() + 1;
    }

    @Override // p000.AbstractC0528g9, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof String) {
            return super.indexOf((String) obj);
        }
        return -1;
    }

    @Override // p000.AbstractC0528g9, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof String) {
            return super.lastIndexOf((String) obj);
        }
        return -1;
    }
}
