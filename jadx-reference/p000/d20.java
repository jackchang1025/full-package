package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class d20 implements b20 {
    protected final e20 parameters;
    protected final InterfaceC1342vm pointMap;

    public d20(AbstractC1316ux abstractC1316ux, e20 e20Var) {
        this.parameters = e20Var;
        this.pointMap = new cu0(abstractC1316ux.fromBigInteger(e20Var.getBeta()));
    }

    @Override // p000.b20
    public BigInteger[] decomposeScalar(BigInteger bigInteger) {
        return AbstractC1418xi.decomposeScalar(this.parameters.getSplitParams(), bigInteger);
    }

    @Override // p000.b20, p000.InterfaceC1318uz
    public InterfaceC1342vm getPointMap() {
        return this.pointMap;
    }

    @Override // p000.b20, p000.InterfaceC1318uz
    public boolean hasEfficientPointMap() {
        return true;
    }
}
