package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class u20 implements rn0 {
    protected final qn0 minimalPolynomial;
    protected final InterfaceC1519zj subfield;

    public u20(InterfaceC1519zj interfaceC1519zj, qn0 qn0Var) {
        this.subfield = interfaceC1519zj;
        this.minimalPolynomial = qn0Var;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof u20)) {
            return false;
        }
        u20 u20Var = (u20) obj;
        return this.subfield.equals(u20Var.subfield) && this.minimalPolynomial.equals(u20Var.minimalPolynomial);
    }

    @Override // p000.rn0, p000.InterfaceC1453yd, p000.InterfaceC1519zj
    public BigInteger getCharacteristic() {
        return this.subfield.getCharacteristic();
    }

    @Override // p000.rn0, p000.InterfaceC1453yd
    public int getDegree() {
        return this.minimalPolynomial.getDegree();
    }

    @Override // p000.rn0, p000.InterfaceC1453yd, p000.InterfaceC1519zj
    public int getDimension() {
        return this.minimalPolynomial.getDegree() * this.subfield.getDimension();
    }

    @Override // p000.rn0
    public qn0 getMinimalPolynomial() {
        return this.minimalPolynomial;
    }

    @Override // p000.rn0, p000.InterfaceC1453yd
    public InterfaceC1519zj getSubfield() {
        return this.subfield;
    }

    public int hashCode() {
        return this.subfield.hashCode() ^ q60.rotateLeft(this.minimalPolynomial.hashCode(), 16);
    }
}
