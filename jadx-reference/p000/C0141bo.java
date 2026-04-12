package p000;

/* renamed from: bo */
/* loaded from: classes2.dex */
public class C0141bo extends AbstractC0158c3 {
    C0143bq acinfo;
    C1168r5 signatureAlgorithm;
    AbstractC0007a6 signatureValue;

    private C0141bo(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        this.acinfo = C0143bq.getInstance(abstractC0400d2.getObjectAt(0));
        this.signatureAlgorithm = C1168r5.getInstance(abstractC0400d2.getObjectAt(1));
        this.signatureValue = C0991oo.getInstance((Object) abstractC0400d2.getObjectAt(2));
    }

    public static C0141bo getInstance(Object obj) {
        if (obj instanceof C0141bo) {
            return (C0141bo) obj;
        }
        if (obj != null) {
            return new C0141bo(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C0143bq getAcinfo() {
        return this.acinfo;
    }

    public C1168r5 getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public AbstractC0007a6 getSignatureValue() {
        return this.signatureValue;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        c0118b1.add(this.acinfo);
        c0118b1.add(this.signatureAlgorithm);
        c0118b1.add(this.signatureValue);
        return new C1064pc(c0118b1);
    }

    public C0141bo(C0143bq c0143bq, C1168r5 c1168r5, C0991oo c0991oo) {
        this.acinfo = c0143bq;
        this.signatureAlgorithm = c1168r5;
        this.signatureValue = c0991oo;
    }
}
