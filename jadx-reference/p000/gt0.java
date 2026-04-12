package p000;

/* loaded from: classes2.dex */
public class gt0 extends ft0 {
    private final byte[] keyData;

    public gt0(byte[] bArr) {
        super(true, null);
        this.keyData = C0133bg.clone(bArr);
    }

    public byte[] getKeyData() {
        return C0133bg.clone(this.keyData);
    }

    public gt0(byte[] bArr, String str) {
        super(true, str);
        this.keyData = C0133bg.clone(bArr);
    }
}
