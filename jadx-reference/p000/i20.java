package p000;

/* loaded from: classes2.dex */
public class i20 extends AbstractC0158c3 {
    private C0160c5 digestParamSet;
    private C0160c5 encryptionParamSet;
    private C0160c5 publicKeyParamSet;

    public i20(C0160c5 c0160c5, C0160c5 c0160c52) {
        this.publicKeyParamSet = c0160c5;
        this.digestParamSet = c0160c52;
        this.encryptionParamSet = null;
    }

    public static i20 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public C0160c5 getDigestParamSet() {
        return this.digestParamSet;
    }

    public C0160c5 getEncryptionParamSet() {
        return this.encryptionParamSet;
    }

    public C0160c5 getPublicKeyParamSet() {
        return this.publicKeyParamSet;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        c0118b1.add(this.publicKeyParamSet);
        c0118b1.add(this.digestParamSet);
        C0160c5 c0160c5 = this.encryptionParamSet;
        if (c0160c5 != null) {
            c0118b1.add(c0160c5);
        }
        return new C1064pc(c0118b1);
    }

    public i20(C0160c5 c0160c5, C0160c5 c0160c52, C0160c5 c0160c53) {
        this.publicKeyParamSet = c0160c5;
        this.digestParamSet = c0160c52;
        this.encryptionParamSet = c0160c53;
    }

    public static i20 getInstance(Object obj) {
        if (obj instanceof i20) {
            return (i20) obj;
        }
        if (obj != null) {
            return new i20(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    private i20(AbstractC0400d2 abstractC0400d2) {
        this.publicKeyParamSet = (C0160c5) abstractC0400d2.getObjectAt(0);
        this.digestParamSet = (C0160c5) abstractC0400d2.getObjectAt(1);
        if (abstractC0400d2.size() > 2) {
            this.encryptionParamSet = (C0160c5) abstractC0400d2.getObjectAt(2);
        }
    }
}
