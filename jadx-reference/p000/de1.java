package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;
import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class de1 extends AbstractC0485f7 {
    static final String PRECOMP_NAME = "bc_wtnaf";

    /* renamed from: de1$a0 */
    public static class C0413a0 implements zn0 {
        final /* synthetic */ byte val$a;
        final /* synthetic */ AbstractC1341vl.a1 val$p;

        public C0413a0(AbstractC1341vl.a1 a1Var, byte b) {
            this.val$p = a1Var;
            this.val$a = b;
        }

        @Override // p000.zn0
        public ao0 precompute(ao0 ao0Var) {
            if (ao0Var instanceof ee1) {
                return ao0Var;
            }
            ee1 ee1Var = new ee1();
            ee1Var.setPreComp(w61.getPreComp(this.val$p, this.val$a));
            return ee1Var;
        }
    }

    private static AbstractC1341vl.a1 multiplyFromWTnaf(AbstractC1341vl.a1 a1Var, byte[] bArr) {
        AbstractC1316ux.a1 a1Var2 = (AbstractC1316ux.a1) a1Var.getCurve();
        AbstractC1341vl.a1[] preComp = ((ee1) a1Var2.precompute(a1Var, PRECOMP_NAME, new C0413a0(a1Var, a1Var2.getA().toBigInteger().byteValue()))).getPreComp();
        AbstractC1341vl.a1[] a1VarArr = new AbstractC1341vl.a1[preComp.length];
        for (int i = 0; i < preComp.length; i++) {
            a1VarArr[i] = (AbstractC1341vl.a1) preComp[i].negate();
        }
        AbstractC1341vl.a1 a1Var3 = (AbstractC1341vl.a1) a1Var.getCurve().getInfinity();
        int i2 = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i2++;
            byte b = bArr[length];
            if (b != 0) {
                a1Var3 = (AbstractC1341vl.a1) a1Var3.tauPow(i2).add(b > 0 ? preComp[b >>> 1] : a1VarArr[(-b) >>> 1]);
                i2 = 0;
            }
        }
        return i2 > 0 ? a1Var3.tauPow(i2) : a1Var3;
    }

    private AbstractC1341vl.a1 multiplyWTnaf(AbstractC1341vl.a1 a1Var, jj1 jj1Var, byte b, byte b2) {
        return multiplyFromWTnaf(a1Var, w61.tauAdicWNaf(b2, jj1Var, (byte) 4, BigInteger.valueOf(16L), w61.getTw(b2, 4), b == 0 ? w61.alpha0 : w61.alpha1));
    }

    @Override // p000.AbstractC0485f7
    public AbstractC1341vl multiplyPositive(AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        if (!(abstractC1341vl instanceof AbstractC1341vl.a1)) {
            throw new IllegalArgumentException("Only ECPoint.AbstractF2m can be used in WTauNafMultiplier");
        }
        AbstractC1341vl.a1 a1Var = (AbstractC1341vl.a1) abstractC1341vl;
        AbstractC1316ux.a1 a1Var2 = (AbstractC1316ux.a1) a1Var.getCurve();
        int fieldSize = a1Var2.getFieldSize();
        byte bByteValue = a1Var2.getA().toBigInteger().byteValue();
        byte mu = w61.getMu(bByteValue);
        return multiplyWTnaf(a1Var, w61.partModReduction(bigInteger, fieldSize, bByteValue, a1Var2.getSi(), mu, (byte) 10), bByteValue, mu);
    }
}
