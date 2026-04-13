package k1;

import android.support.v4.view.PointerIconCompat;
import i1.C0340c;
import o1.AbstractC0447a;

/* renamed from: k1.a */
/* loaded from: classes.dex */
public final class C0364a extends AbstractC0366c {

    /* renamed from: i */
    public final /* synthetic */ int f720i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0364a(int i2) {
        super(3, 1);
        this.f720i = i2;
        if (i2 == 1) {
            super(1, 1);
        } else if (i2 != 2) {
        } else {
            super(2, 1);
        }
    }

    @Override // k1.AbstractC0366c, k1.AbstractC0367d
    /* renamed from: b */
    public final void mo941b() {
        switch (this.f720i) {
            case 2:
                super.mo941b();
                if (!AbstractC0447a.m1182a(this.f726c)) {
                    throw new C0340c(PointerIconCompat.TYPE_CROSSHAIR, "Received text is no valid utf8 string!");
                }
                return;
            default:
                super.mo941b();
                return;
        }
    }
}
