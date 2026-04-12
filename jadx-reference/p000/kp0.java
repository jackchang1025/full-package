package p000;

/* loaded from: classes2.dex */
public final class kp0 extends C0136bj {
    private byte[] privateKey;
    private int securityCategory;

    public kp0(int i, byte[] bArr) {
        super(true);
        if (bArr.length != mp0.getPrivateSize(i)) {
            throw new IllegalArgumentException("invalid key size for security category");
        }
        this.securityCategory = i;
        this.privateKey = C0133bg.clone(bArr);
    }

    public byte[] getSecret() {
        return C0133bg.clone(this.privateKey);
    }

    public int getSecurityCategory() {
        return this.securityCategory;
    }
}
