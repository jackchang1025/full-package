package p022z;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilterWithChild;

/* renamed from: z.c */
/* loaded from: classes.dex */
public final class C0980c implements InterfaceC0978a, InterfaceC0979b {

    /* renamed from: a */
    public final /* synthetic */ int f2323a;

    /* renamed from: b */
    public final CombineFilterWithChild f2324b;

    public /* synthetic */ C0980c(CombineFilterWithChild combineFilterWithChild, int i2) {
        this.f2323a = i2;
        this.f2324b = combineFilterWithChild;
    }

    @Override // p022z.InterfaceC0978a, p022z.InterfaceC0979b
    /* renamed from: a */
    public final int mo1470a() {
        return 20;
    }

    @Override // p022z.InterfaceC0979b
    /* renamed from: b */
    public final UiObjectCollection mo1472b(UiObject uiObject) {
        int i2 = this.f2323a;
        CombineFilterWithChild combineFilterWithChild = this.f2324b;
        switch (i2) {
            case 0:
                return uiObject.findByCombineWithChild(combineFilterWithChild);
            default:
                return uiObject.findByCombineWithoutChild(combineFilterWithChild);
        }
    }

    @Override // p022z.InterfaceC0978a
    /* renamed from: c */
    public final UiObject mo1471c(UiObject uiObject) {
        int i2 = this.f2323a;
        CombineFilterWithChild combineFilterWithChild = this.f2324b;
        switch (i2) {
            case 0:
                return uiObject.findOneByCombineWithChild(combineFilterWithChild);
            default:
                return uiObject.findOneByCombineWithoutChild(combineFilterWithChild);
        }
    }
}
