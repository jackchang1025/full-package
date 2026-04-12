package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class ai1 extends AbstractC0158c3 implements hi1 {
    private AbstractC1316ux curve;
    private C0160c5 fieldIdentifier;
    private byte[] seed;

    public ai1(AbstractC1316ux abstractC1316ux) {
        this(abstractC1316ux, null);
    }

    private void setFieldIdentifier() {
        C0160c5 c0160c5;
        if (C1314uv.isFpCurve(this.curve)) {
            c0160c5 = hi1.prime_field;
        } else {
            if (!C1314uv.isF2mCurve(this.curve)) {
                throw new IllegalArgumentException("This type of ECCurve is not implemented");
            }
            c0160c5 = hi1.characteristic_two_field;
        }
        this.fieldIdentifier = c0160c5;
    }

    public AbstractC1316ux getCurve() {
        return this.curve;
    }

    public byte[] getSeed() {
        return C0133bg.clone(this.seed);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        if (this.fieldIdentifier.equals((AbstractC0164c9) hi1.prime_field) || this.fieldIdentifier.equals((AbstractC0164c9) hi1.characteristic_two_field)) {
            c0118b1.add(new ei1(this.curve.getA()).toASN1Primitive());
            ei1 ei1Var = new ei1(this.curve.getB());
            c0118b1.add(ei1Var.toASN1Primitive());
        }
        if (this.seed != null) {
            c0118b1.add(new C0991oo(this.seed));
        }
        return new C1064pc(c0118b1);
    }

    public ai1(AbstractC1316ux abstractC1316ux, byte[] bArr) {
        this.fieldIdentifier = null;
        this.curve = abstractC1316ux;
        this.seed = C0133bg.clone(bArr);
        setFieldIdentifier();
    }

    public ai1(fi1 fi1Var, BigInteger bigInteger, BigInteger bigInteger2, AbstractC0400d2 abstractC0400d2) {
        int iIntValueExact;
        int iIntValueExact2;
        int i;
        this.fieldIdentifier = null;
        C0160c5 identifier = fi1Var.getIdentifier();
        this.fieldIdentifier = identifier;
        if (identifier.equals((AbstractC0164c9) hi1.prime_field)) {
            this.curve = new AbstractC1316ux.a5(((C0155c0) fi1Var.getParameters()).getValue(), new BigInteger(1, AbstractC0161c6.getInstance(abstractC0400d2.getObjectAt(0)).getOctets()), new BigInteger(1, AbstractC0161c6.getInstance(abstractC0400d2.getObjectAt(1)).getOctets()), bigInteger, bigInteger2);
        } else {
            if (!this.fieldIdentifier.equals((AbstractC0164c9) hi1.characteristic_two_field)) {
                throw new IllegalArgumentException("This type of ECCurve is not implemented");
            }
            AbstractC0400d2 abstractC0400d22 = AbstractC0400d2.getInstance(fi1Var.getParameters());
            int iIntValueExact3 = ((C0155c0) abstractC0400d22.getObjectAt(0)).intValueExact();
            C0160c5 c0160c5 = (C0160c5) abstractC0400d22.getObjectAt(1);
            if (c0160c5.equals((AbstractC0164c9) hi1.tpBasis)) {
                iIntValueExact2 = C0155c0.getInstance(abstractC0400d22.getObjectAt(2)).intValueExact();
                i = 0;
                iIntValueExact = 0;
            } else {
                if (!c0160c5.equals((AbstractC0164c9) hi1.ppBasis)) {
                    throw new IllegalArgumentException("This type of EC basis is not implemented");
                }
                AbstractC0400d2 abstractC0400d23 = AbstractC0400d2.getInstance(abstractC0400d22.getObjectAt(2));
                int iIntValueExact4 = C0155c0.getInstance(abstractC0400d23.getObjectAt(0)).intValueExact();
                int iIntValueExact5 = C0155c0.getInstance(abstractC0400d23.getObjectAt(1)).intValueExact();
                iIntValueExact = C0155c0.getInstance(abstractC0400d23.getObjectAt(2)).intValueExact();
                iIntValueExact2 = iIntValueExact4;
                i = iIntValueExact5;
            }
            this.curve = new AbstractC1316ux.a4(iIntValueExact3, iIntValueExact2, i, iIntValueExact, new BigInteger(1, AbstractC0161c6.getInstance(abstractC0400d2.getObjectAt(0)).getOctets()), new BigInteger(1, AbstractC0161c6.getInstance(abstractC0400d2.getObjectAt(1)).getOctets()), bigInteger, bigInteger2);
        }
        if (abstractC0400d2.size() == 3) {
            this.seed = ((C0991oo) abstractC0400d2.getObjectAt(2)).getBytes();
        }
    }
}
