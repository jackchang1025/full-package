package p000;

import java.math.BigInteger;

/* renamed from: zo */
/* loaded from: classes2.dex */
public class C1524zo {
    public static final String PRECOMP_NAME = "bc_fixed_point";

    /* renamed from: zo$a0 */
    public static class a0 implements zn0 {
        final /* synthetic */ AbstractC1316ux val$c;
        final /* synthetic */ AbstractC1341vl val$p;

        public a0(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl) {
            this.val$c = abstractC1316ux;
            this.val$p = abstractC1341vl;
        }

        private boolean checkExisting(C1523zn c1523zn, int i) {
            return c1523zn != null && checkTable(c1523zn.getLookupTable(), i);
        }

        private boolean checkTable(InterfaceC1334ve interfaceC1334ve, int i) {
            return interfaceC1334ve != null && interfaceC1334ve.getSize() >= i;
        }

        @Override // p000.zn0
        public ao0 precompute(ao0 ao0Var) {
            C1523zn c1523zn = ao0Var instanceof C1523zn ? (C1523zn) ao0Var : null;
            int combSize = C1524zo.getCombSize(this.val$c);
            int i = combSize > 250 ? 6 : 5;
            int i2 = 1 << i;
            if (checkExisting(c1523zn, i2)) {
                return c1523zn;
            }
            int i3 = ((combSize + i) - 1) / i;
            AbstractC1341vl[] abstractC1341vlArr = new AbstractC1341vl[i + 1];
            abstractC1341vlArr[0] = this.val$p;
            for (int i4 = 1; i4 < i; i4++) {
                abstractC1341vlArr[i4] = abstractC1341vlArr[i4 - 1].timesPow2(i3);
            }
            abstractC1341vlArr[i] = abstractC1341vlArr[0].subtract(abstractC1341vlArr[1]);
            this.val$c.normalizeAll(abstractC1341vlArr);
            AbstractC1341vl[] abstractC1341vlArr2 = new AbstractC1341vl[i2];
            abstractC1341vlArr2[0] = abstractC1341vlArr[0];
            for (int i5 = i - 1; i5 >= 0; i5--) {
                AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i5];
                int i6 = 1 << i5;
                for (int i7 = i6; i7 < i2; i7 += i6 << 1) {
                    abstractC1341vlArr2[i7] = abstractC1341vlArr2[i7 - i6].add(abstractC1341vl);
                }
            }
            this.val$c.normalizeAll(abstractC1341vlArr2);
            C1523zn c1523zn2 = new C1523zn();
            c1523zn2.setLookupTable(this.val$c.createCacheSafeLookupTable(abstractC1341vlArr2, 0, i2));
            c1523zn2.setOffset(abstractC1341vlArr[i]);
            c1523zn2.setWidth(i);
            return c1523zn2;
        }
    }

    public static int getCombSize(AbstractC1316ux abstractC1316ux) {
        BigInteger order = abstractC1316ux.getOrder();
        return order == null ? abstractC1316ux.getFieldSize() + 1 : order.bitLength();
    }

    public static C1523zn getFixedPointPreCompInfo(ao0 ao0Var) {
        if (ao0Var instanceof C1523zn) {
            return (C1523zn) ao0Var;
        }
        return null;
    }

    public static C1523zn precompute(AbstractC1341vl abstractC1341vl) {
        AbstractC1316ux curve = abstractC1341vl.getCurve();
        return (C1523zn) curve.precompute(abstractC1341vl, PRECOMP_NAME, new a0(curve, abstractC1341vl));
    }
}
