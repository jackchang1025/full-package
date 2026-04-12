package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class op0 extends AbstractC0158c3 {
    public static final C1168r5 DEFAULT_HASH_ALGORITHM;
    public static final C1168r5 DEFAULT_MASK_GEN_FUNCTION;
    public static final C0155c0 DEFAULT_SALT_LENGTH;
    public static final C0155c0 DEFAULT_TRAILER_FIELD;
    private C1168r5 hashAlgorithm;
    private C1168r5 maskGenAlgorithm;
    private C0155c0 saltLength;
    private C0155c0 trailerField;

    static {
        C1168r5 c1168r5 = new C1168r5(pk0.idSHA1, C1046ow.INSTANCE);
        DEFAULT_HASH_ALGORITHM = c1168r5;
        DEFAULT_MASK_GEN_FUNCTION = new C1168r5(ul0.id_mgf1, c1168r5);
        DEFAULT_SALT_LENGTH = new C0155c0(20L);
        DEFAULT_TRAILER_FIELD = new C0155c0(1L);
    }

    public op0() {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.saltLength = DEFAULT_SALT_LENGTH;
        this.trailerField = DEFAULT_TRAILER_FIELD;
    }

    public static op0 getInstance(Object obj) {
        if (obj instanceof op0) {
            return (op0) obj;
        }
        if (obj != null) {
            return new op0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C1168r5 getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public C1168r5 getMaskGenAlgorithm() {
        return this.maskGenAlgorithm;
    }

    public BigInteger getSaltLength() {
        return this.saltLength.getValue();
    }

    public BigInteger getTrailerField() {
        return this.trailerField.getValue();
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(4);
        if (!this.hashAlgorithm.equals(DEFAULT_HASH_ALGORITHM)) {
            c0118b1.add(new C1067pf(true, 0, (InterfaceC0117b0) this.hashAlgorithm));
        }
        if (!this.maskGenAlgorithm.equals(DEFAULT_MASK_GEN_FUNCTION)) {
            c0118b1.add(new C1067pf(true, 1, (InterfaceC0117b0) this.maskGenAlgorithm));
        }
        if (!this.saltLength.equals((AbstractC0164c9) DEFAULT_SALT_LENGTH)) {
            c0118b1.add(new C1067pf(true, 2, (InterfaceC0117b0) this.saltLength));
        }
        if (!this.trailerField.equals((AbstractC0164c9) DEFAULT_TRAILER_FIELD)) {
            c0118b1.add(new C1067pf(true, 3, (InterfaceC0117b0) this.trailerField));
        }
        return new C1064pc(c0118b1);
    }

    private op0(AbstractC0400d2 abstractC0400d2) {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.saltLength = DEFAULT_SALT_LENGTH;
        this.trailerField = DEFAULT_TRAILER_FIELD;
        for (int i = 0; i != abstractC0400d2.size(); i++) {
            AbstractC0439e0 abstractC0439e0 = (AbstractC0439e0) abstractC0400d2.getObjectAt(i);
            int tagNo = abstractC0439e0.getTagNo();
            if (tagNo == 0) {
                this.hashAlgorithm = C1168r5.getInstance(abstractC0439e0, true);
            } else if (tagNo == 1) {
                this.maskGenAlgorithm = C1168r5.getInstance(abstractC0439e0, true);
            } else if (tagNo == 2) {
                this.saltLength = C0155c0.getInstance(abstractC0439e0, true);
            } else {
                if (tagNo != 3) {
                    throw new IllegalArgumentException("unknown tag");
                }
                this.trailerField = C0155c0.getInstance(abstractC0439e0, true);
            }
        }
    }

    public op0(C1168r5 c1168r5, C1168r5 c1168r52, C0155c0 c0155c0, C0155c0 c0155c02) {
        this.hashAlgorithm = c1168r5;
        this.maskGenAlgorithm = c1168r52;
        this.saltLength = c0155c0;
        this.trailerField = c0155c02;
    }
}
