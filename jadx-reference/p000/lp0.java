package p000;

/* loaded from: classes2.dex */
public final class lp0 extends C0136bj {
    private byte[] publicKey;
    private int securityCategory;

    public lp0(int i, byte[] bArr) {
        super(false);
        if (bArr.length != mp0.getPublicSize(i)) {
            throw new IllegalArgumentException("invalid key size for security category");
        }
        this.securityCategory = i;
        this.publicKey = C0133bg.clone(bArr);
    }

    public byte[] getPublicData() {
        return C0133bg.clone(this.publicKey);
    }

    public int getSecurityCategory() {
        return this.securityCategory;
    }
}
