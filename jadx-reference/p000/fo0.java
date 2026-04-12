package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class fo0 implements InterfaceC1519zj {
    protected final BigInteger characteristic;

    public fo0(BigInteger bigInteger) {
        this.characteristic = bigInteger;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof fo0) {
            return this.characteristic.equals(((fo0) obj).characteristic);
        }
        return false;
    }

    @Override // p000.InterfaceC1519zj
    public BigInteger getCharacteristic() {
        return this.characteristic;
    }

    @Override // p000.InterfaceC1519zj
    public int getDimension() {
        return 1;
    }

    public int hashCode() {
        return this.characteristic.hashCode();
    }
}
