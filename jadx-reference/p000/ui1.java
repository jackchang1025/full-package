package p000;

/* loaded from: classes2.dex */
public class ui1 extends AbstractC0158c3 {
    private final byte[] publicSeed;
    private final byte[] root;

    private ui1(AbstractC0400d2 abstractC0400d2) {
        if (!C0155c0.getInstance(abstractC0400d2.getObjectAt(0)).hasValue(0)) {
            throw new IllegalArgumentException("unknown version of sequence");
        }
        this.publicSeed = C0133bg.clone(AbstractC0161c6.getInstance(abstractC0400d2.getObjectAt(1)).getOctets());
        this.root = C0133bg.clone(AbstractC0161c6.getInstance(abstractC0400d2.getObjectAt(2)).getOctets());
    }

    public static ui1 getInstance(Object obj) {
        if (obj instanceof ui1) {
            return (ui1) obj;
        }
        if (obj != null) {
            return new ui1(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public byte[] getPublicSeed() {
        return C0133bg.clone(this.publicSeed);
    }

    public byte[] getRoot() {
        return C0133bg.clone(this.root);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(new C0155c0(0L));
        c0118b1.add(new C1048oy(this.publicSeed));
        c0118b1.add(new C1048oy(this.root));
        return new C1064pc(c0118b1);
    }

    public ui1(byte[] bArr, byte[] bArr2) {
        this.publicSeed = C0133bg.clone(bArr);
        this.root = C0133bg.clone(bArr2);
    }
}
