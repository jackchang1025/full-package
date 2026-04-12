package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class bi1 extends AbstractC0158c3 implements hi1 {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private AbstractC1316ux curve;
    private fi1 fieldID;

    /* renamed from: g */
    private di1 f45898g;

    /* renamed from: h */
    private BigInteger f45899h;

    /* renamed from: n */
    private BigInteger f45900n;
    private byte[] seed;

    private bi1(AbstractC0400d2 abstractC0400d2) {
        if (!(abstractC0400d2.getObjectAt(0) instanceof C0155c0) || !((C0155c0) abstractC0400d2.getObjectAt(0)).hasValue(1)) {
            throw new IllegalArgumentException("bad version in X9ECParameters");
        }
        this.f45900n = ((C0155c0) abstractC0400d2.getObjectAt(4)).getValue();
        if (abstractC0400d2.size() == 6) {
            this.f45899h = ((C0155c0) abstractC0400d2.getObjectAt(5)).getValue();
        }
        ai1 ai1Var = new ai1(fi1.getInstance(abstractC0400d2.getObjectAt(1)), this.f45900n, this.f45899h, AbstractC0400d2.getInstance(abstractC0400d2.getObjectAt(2)));
        this.curve = ai1Var.getCurve();
        InterfaceC0117b0 objectAt = abstractC0400d2.getObjectAt(3);
        if (objectAt instanceof di1) {
            this.f45898g = (di1) objectAt;
        } else {
            this.f45898g = new di1(this.curve, (AbstractC0161c6) objectAt);
        }
        this.seed = ai1Var.getSeed();
    }

    public static bi1 getInstance(Object obj) {
        if (obj instanceof bi1) {
            return (bi1) obj;
        }
        if (obj != null) {
            return new bi1(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public di1 getBaseEntry() {
        return this.f45898g;
    }

    public AbstractC1316ux getCurve() {
        return this.curve;
    }

    public ai1 getCurveEntry() {
        return new ai1(this.curve, this.seed);
    }

    public fi1 getFieldIDEntry() {
        return this.fieldID;
    }

    public AbstractC1341vl getG() {
        return this.f45898g.getPoint();
    }

    public BigInteger getH() {
        return this.f45899h;
    }

    public BigInteger getN() {
        return this.f45900n;
    }

    public byte[] getSeed() {
        return C0133bg.clone(this.seed);
    }

    public boolean hasSeed() {
        return this.seed != null;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(6);
        c0118b1.add(new C0155c0(ONE));
        c0118b1.add(this.fieldID);
        c0118b1.add(new ai1(this.curve, this.seed));
        c0118b1.add(this.f45898g);
        c0118b1.add(new C0155c0(this.f45900n));
        if (this.f45899h != null) {
            c0118b1.add(new C0155c0(this.f45899h));
        }
        return new C1064pc(c0118b1);
    }

    public bi1(AbstractC1316ux abstractC1316ux, di1 di1Var, BigInteger bigInteger) {
        this(abstractC1316ux, di1Var, bigInteger, null, null);
    }

    public bi1(AbstractC1316ux abstractC1316ux, di1 di1Var, BigInteger bigInteger, BigInteger bigInteger2) {
        this(abstractC1316ux, di1Var, bigInteger, bigInteger2, null);
    }

    public bi1(AbstractC1316ux abstractC1316ux, di1 di1Var, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        fi1 fi1Var;
        this.curve = abstractC1316ux;
        this.f45898g = di1Var;
        this.f45900n = bigInteger;
        this.f45899h = bigInteger2;
        this.seed = C0133bg.clone(bArr);
        if (C1314uv.isFpCurve(abstractC1316ux)) {
            fi1Var = new fi1(abstractC1316ux.getField().getCharacteristic());
        } else {
            if (!C1314uv.isF2mCurve(abstractC1316ux)) {
                throw new IllegalArgumentException("'curve' is of an unsupported type");
            }
            int[] exponentsPresent = ((rn0) abstractC1316ux.getField()).getMinimalPolynomial().getExponentsPresent();
            if (exponentsPresent.length == 3) {
                fi1Var = new fi1(exponentsPresent[2], exponentsPresent[1]);
            } else {
                if (exponentsPresent.length != 5) {
                    throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
                }
                fi1Var = new fi1(exponentsPresent[4], exponentsPresent[1], exponentsPresent[2], exponentsPresent[3]);
            }
        }
        this.fieldID = fi1Var;
    }
}
