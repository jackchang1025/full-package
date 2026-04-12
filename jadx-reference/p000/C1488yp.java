package p000;

import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yp */
/* loaded from: classes.dex */
public final class C1488yp extends nt0 {

    /* renamed from: a4 */
    public final HashMap f61353a4 = new HashMap();

    @Override // p000.nt0
    /* renamed from: a0 */
    public final kt0 mo214143a0(Object obj) {
        return (kt0) this.f61353a4.get(obj);
    }

    @Override // p000.nt0
    /* renamed from: a1 */
    public final Object mo214144a1(Object obj) {
        Object objMo214144a1 = super.mo214144a1(obj);
        this.f61353a4.remove(obj);
        return objMo214144a1;
    }
}
