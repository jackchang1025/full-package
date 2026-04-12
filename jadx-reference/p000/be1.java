package p000;

/* loaded from: classes2.dex */
public final class be1 {
    private final byte[][] publicKey;

    public be1(zd1 zd1Var, byte[][] bArr) {
        if (zd1Var == null) {
            throw new NullPointerException("params == null");
        }
        if (bArr == null) {
            throw new NullPointerException("publicKey == null");
        }
        if (fj1.hasNullPointer(bArr)) {
            throw new NullPointerException("publicKey byte array == null");
        }
        if (bArr.length != zd1Var.getLen()) {
            throw new IllegalArgumentException("wrong publicKey size");
        }
        for (byte[] bArr2 : bArr) {
            if (bArr2.length != zd1Var.getTreeDigestSize()) {
                throw new IllegalArgumentException("wrong publicKey format");
            }
        }
        this.publicKey = fj1.cloneArray(bArr);
    }

    public byte[][] toByteArray() {
        return fj1.cloneArray(this.publicKey);
    }
}
