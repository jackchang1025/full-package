package p022z;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import java.util.ArrayList;

/* renamed from: z.d */
/* loaded from: classes.dex */
public final class C0981d implements InterfaceC0978a, InterfaceC0979b {

    /* renamed from: a */
    public final /* synthetic */ int f2325a;

    /* renamed from: b */
    public int f2326b;

    /* renamed from: c */
    public final Object f2327c;

    public C0981d() {
        this.f2325a = 3;
        this.f2327c = new int[10];
    }

    @Override // p022z.InterfaceC0978a, p022z.InterfaceC0979b
    /* renamed from: a */
    public final int mo1470a() {
        switch (this.f2325a) {
        }
        return this.f2326b;
    }

    @Override // p022z.InterfaceC0979b
    /* renamed from: b */
    public final UiObjectCollection mo1472b(UiObject uiObject) {
        int i2 = this.f2325a;
        Object obj = this.f2327c;
        switch (i2) {
            case 0:
                return uiObject.findByCombine((CombineFilter) obj);
            default:
                return uiObject.findByOperateOr((CombineFiltersWithOr) obj);
        }
    }

    @Override // p022z.InterfaceC0978a
    /* renamed from: c */
    public final UiObject mo1471c(UiObject uiObject) {
        int i2 = this.f2325a;
        Object obj = this.f2327c;
        switch (i2) {
            case 0:
                return uiObject.findOneByCombine((CombineFilter) obj);
            default:
                return uiObject.findOneByOperateOr((CombineFiltersWithOr) obj);
        }
    }

    /* renamed from: d */
    public final int m1473d() {
        if ((this.f2326b & 128) != 0) {
            return ((int[]) this.f2327c)[7];
        }
        return 65535;
    }

    /* renamed from: e */
    public final void m1474e(int i2, int i3) {
        if (i2 >= 0) {
            int[] iArr = (int[]) this.f2327c;
            if (i2 >= iArr.length) {
                return;
            }
            this.f2326b = (1 << i2) | this.f2326b;
            iArr[i2] = i3;
        }
    }

    public /* synthetic */ C0981d(Object obj, int i2) {
        this.f2325a = i2;
        this.f2326b = 20;
        this.f2327c = obj;
    }

    public /* synthetic */ C0981d(Object obj, int i2, int i3) {
        this.f2325a = i3;
        this.f2326b = i2;
        this.f2327c = obj;
    }

    public C0981d(ArrayList arrayList) {
        this.f2325a = 2;
        this.f2326b = 0;
        this.f2327c = arrayList;
    }
}
