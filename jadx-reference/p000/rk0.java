package p000;

/* loaded from: classes2.dex */
public class rk0 extends AbstractC0158c3 {
    public static final int otherObjectDigest = 2;
    public static final int publicKey = 0;
    public static final int publicKeyCert = 1;
    C1168r5 digestAlgorithm;
    C0119b2 digestedObjectType;
    AbstractC0007a6 objectDigest;
    C0160c5 otherObjectTypeID;

    public rk0(int i, C0160c5 c0160c5, C1168r5 c1168r5, byte[] bArr) {
        this.digestedObjectType = new C0119b2(i);
        if (i == 2) {
            this.otherObjectTypeID = c0160c5;
        }
        this.digestAlgorithm = c1168r5;
        this.objectDigest = new C0991oo(bArr);
    }

    public static rk0 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public C1168r5 getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public C0119b2 getDigestedObjectType() {
        return this.digestedObjectType;
    }

    public AbstractC0007a6 getObjectDigest() {
        return this.objectDigest;
    }

    public C0160c5 getOtherObjectTypeID() {
        return this.otherObjectTypeID;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(4);
        c0118b1.add(this.digestedObjectType);
        C0160c5 c0160c5 = this.otherObjectTypeID;
        if (c0160c5 != null) {
            c0118b1.add(c0160c5);
        }
        c0118b1.add(this.digestAlgorithm);
        c0118b1.add(this.objectDigest);
        return new C1064pc(c0118b1);
    }

    private rk0(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() > 4 || abstractC0400d2.size() < 3) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        int i = 0;
        this.digestedObjectType = C0119b2.getInstance(abstractC0400d2.getObjectAt(0));
        if (abstractC0400d2.size() == 4) {
            i = 1;
            this.otherObjectTypeID = C0160c5.getInstance(abstractC0400d2.getObjectAt(1));
        }
        this.digestAlgorithm = C1168r5.getInstance(abstractC0400d2.getObjectAt(i + 1));
        this.objectDigest = C0991oo.getInstance((Object) abstractC0400d2.getObjectAt(i + 2));
    }

    public static rk0 getInstance(Object obj) {
        if (obj instanceof rk0) {
            return (rk0) obj;
        }
        if (obj != null) {
            return new rk0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }
}
