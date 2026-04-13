package k1;

import i1.C0341d;

/* renamed from: k1.c */
/* loaded from: classes.dex */
public abstract class AbstractC0366c extends AbstractC0367d {

    /* renamed from: h */
    public final /* synthetic */ int f723h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ AbstractC0366c(int i2, int i3) {
        super(i2);
        this.f723h = i3;
    }

    @Override // k1.AbstractC0367d
    /* renamed from: b */
    public void mo941b() {
        switch (this.f723h) {
            case 0:
                if (!this.f724a) {
                    throw new C0341d("Control frame can't have fin==false set");
                }
                if (this.f728e) {
                    throw new C0341d("Control frame can't have rsv1==true set");
                }
                if (this.f729f) {
                    throw new C0341d("Control frame can't have rsv2==true set");
                }
                if (this.f730g) {
                    throw new C0341d("Control frame can't have rsv3==true set");
                }
                return;
            default:
                return;
        }
    }
}
