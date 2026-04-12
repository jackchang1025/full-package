package p000;

/* renamed from: vo */
/* loaded from: classes2.dex */
public class C1344vo extends C1333vd {

    /* renamed from: q */
    private final AbstractC1341vl f60662q;

    public C1344vo(AbstractC1341vl abstractC1341vl, C1317uy c1317uy) {
        super(false, c1317uy);
        this.f60662q = c1317uy.validatePublicPoint(abstractC1341vl);
    }

    public AbstractC1341vl getQ() {
        return this.f60662q;
    }
}
