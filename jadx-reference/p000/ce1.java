package p000;

/* loaded from: classes2.dex */
public final class ce1 {
    private byte[][] signature;

    public ce1(zd1 zd1Var, byte[][] bArr) {
        if (zd1Var == null) {
            throw new NullPointerException("params == null");
        }
        if (bArr == null) {
            throw new NullPointerException("signature == null");
        }
        if (fj1.hasNullPointer(bArr)) {
            throw new NullPointerException("signature byte array == null");
        }
        if (bArr.length != zd1Var.getLen()) {
            throw new IllegalArgumentException("wrong signature size");
        }
        for (byte[] bArr2 : bArr) {
            if (bArr2.length != zd1Var.getTreeDigestSize()) {
                throw new IllegalArgumentException("wrong signature format");
            }
        }
        this.signature = fj1.cloneArray(bArr);
    }

    public byte[][] toByteArray() {
        return fj1.cloneArray(this.signature);
    }
}
