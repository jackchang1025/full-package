package p000;

/* loaded from: classes2.dex */
public class zi1 extends AbstractC0158c3 {
    private final byte[] bdsState;
    private final int index;
    private final int maxIndex;
    private final byte[] publicSeed;
    private final byte[] root;
    private final byte[] secretKeyPRF;
    private final byte[] secretKeySeed;
    private final int version;

    public zi1(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) {
        this.version = 0;
        this.index = i;
        this.secretKeySeed = C0133bg.clone(bArr);
        this.secretKeyPRF = C0133bg.clone(bArr2);
        this.publicSeed = C0133bg.clone(bArr3);
        this.root = C0133bg.clone(bArr4);
        this.bdsState = C0133bg.clone(bArr5);
        this.maxIndex = -1;
    }

    public static zi1 getInstance(Object obj) {
        if (obj instanceof zi1) {
            return (zi1) obj;
        }
        if (obj != null) {
            return new zi1(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public byte[] getBdsState() {
        return C0133bg.clone(this.bdsState);
    }

    public int getIndex() {
        return this.index;
    }

    public int getMaxIndex() {
        return this.maxIndex;
    }

    public byte[] getPublicSeed() {
        return C0133bg.clone(this.publicSeed);
    }

    public byte[] getRoot() {
        return C0133bg.clone(this.root);
    }

    public byte[] getSecretKeyPRF() {
        return C0133bg.clone(this.secretKeyPRF);
    }

    public byte[] getSecretKeySeed() {
        return C0133bg.clone(this.secretKeySeed);
    }

    public int getVersion() {
        return this.version;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(this.maxIndex >= 0 ? new C0155c0(1L) : new C0155c0(0L));
        C0118b1 c0118b12 = new C0118b1();
        c0118b12.add(new C0155c0(this.index));
        c0118b12.add(new C1048oy(this.secretKeySeed));
        c0118b12.add(new C1048oy(this.secretKeyPRF));
        c0118b12.add(new C1048oy(this.publicSeed));
        c0118b12.add(new C1048oy(this.root));
        if (this.maxIndex >= 0) {
            c0118b12.add(new C1067pf(false, 0, (InterfaceC0117b0) new C0155c0(this.maxIndex)));
        }
        c0118b1.add(new C1064pc(c0118b12));
        c0118b1.add(new C1067pf(true, 0, (InterfaceC0117b0) new C1048oy(this.bdsState)));
        return new C1064pc(c0118b1);
    }

    public zi1(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, int i2) {
        this.version = 1;
        this.index = i;
        this.secretKeySeed = C0133bg.clone(bArr);
        this.secretKeyPRF = C0133bg.clone(bArr2);
        this.publicSeed = C0133bg.clone(bArr3);
        this.root = C0133bg.clone(bArr4);
        this.bdsState = C0133bg.clone(bArr5);
        this.maxIndex = i2;
    }

    private zi1(AbstractC0400d2 abstractC0400d2) {
        int iIntValueExact;
        C0155c0 c0155c0 = C0155c0.getInstance(abstractC0400d2.getObjectAt(0));
        if (!c0155c0.hasValue(0) && !c0155c0.hasValue(1)) {
            throw new IllegalArgumentException("unknown version of sequence");
        }
        this.version = c0155c0.intValueExact();
        if (abstractC0400d2.size() != 2 && abstractC0400d2.size() != 3) {
            throw new IllegalArgumentException("key sequence wrong size");
        }
        AbstractC0400d2 abstractC0400d22 = AbstractC0400d2.getInstance(abstractC0400d2.getObjectAt(1));
        this.index = C0155c0.getInstance(abstractC0400d22.getObjectAt(0)).intValueExact();
        this.secretKeySeed = C0133bg.clone(AbstractC0161c6.getInstance(abstractC0400d22.getObjectAt(1)).getOctets());
        this.secretKeyPRF = C0133bg.clone(AbstractC0161c6.getInstance(abstractC0400d22.getObjectAt(2)).getOctets());
        this.publicSeed = C0133bg.clone(AbstractC0161c6.getInstance(abstractC0400d22.getObjectAt(3)).getOctets());
        this.root = C0133bg.clone(AbstractC0161c6.getInstance(abstractC0400d22.getObjectAt(4)).getOctets());
        if (abstractC0400d22.size() == 6) {
            AbstractC0439e0 abstractC0439e0 = AbstractC0439e0.getInstance(abstractC0400d22.getObjectAt(5));
            if (abstractC0439e0.getTagNo() != 0) {
                throw new IllegalArgumentException("unknown tag in XMSSPrivateKey");
            }
            iIntValueExact = C0155c0.getInstance(abstractC0439e0, false).intValueExact();
        } else {
            if (abstractC0400d22.size() != 5) {
                throw new IllegalArgumentException("keySeq should be 5 or 6 in length");
            }
            iIntValueExact = -1;
        }
        this.maxIndex = iIntValueExact;
        if (abstractC0400d2.size() == 3) {
            this.bdsState = C0133bg.clone(AbstractC0161c6.getInstance(AbstractC0439e0.getInstance(abstractC0400d2.getObjectAt(2)), true).getOctets());
        } else {
            this.bdsState = null;
        }
    }
}
