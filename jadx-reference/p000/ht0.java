package p000;

/* loaded from: classes2.dex */
public class ht0 extends ft0 {
    private final byte[] keyData;

    public ht0(byte[] bArr) {
        super(false, null);
        this.keyData = C0133bg.clone(bArr);
    }

    public byte[] getKeyData() {
        return C0133bg.clone(this.keyData);
    }

    public ht0(byte[] bArr, String str) {
        super(false, str);
        this.keyData = C0133bg.clone(bArr);
    }
}
