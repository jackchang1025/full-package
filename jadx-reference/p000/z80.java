package p000;

/* loaded from: classes2.dex */
public final class z80 {
    private final InterfaceC1236sv digest;
    private final int digestSize;

    public z80(C0160c5 c0160c5, int i) {
        if (c0160c5 == null) {
            throw new NullPointerException("digest == null");
        }
        this.digest = C1254tc.getDigest(c0160c5);
        this.digestSize = i;
    }

    private byte[] coreDigest(int i, byte[] bArr, byte[] bArr2) {
        byte[] bytesBigEndian = fj1.toBytesBigEndian(i, this.digestSize);
        this.digest.update(bytesBigEndian, 0, bytesBigEndian.length);
        this.digest.update(bArr, 0, bArr.length);
        this.digest.update(bArr2, 0, bArr2.length);
        int i2 = this.digestSize;
        byte[] bArr3 = new byte[i2];
        InterfaceC1236sv interfaceC1236sv = this.digest;
        if (interfaceC1236sv instanceof gj1) {
            ((gj1) interfaceC1236sv).doFinal(bArr3, 0, i2);
            return bArr3;
        }
        interfaceC1236sv.doFinal(bArr3, 0);
        return bArr3;
    }

    /* renamed from: F */
    public byte[] m215378F(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int i = this.digestSize;
        if (length != i) {
            throw new IllegalArgumentException("wrong key length");
        }
        if (bArr2.length == i) {
            return coreDigest(0, bArr, bArr2);
        }
        throw new IllegalArgumentException("wrong in length");
    }

    /* renamed from: H */
    public byte[] m215379H(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int i = this.digestSize;
        if (length != i) {
            throw new IllegalArgumentException("wrong key length");
        }
        if (bArr2.length == i * 2) {
            return coreDigest(1, bArr, bArr2);
        }
        throw new IllegalArgumentException("wrong in length");
    }

    public byte[] HMsg(byte[] bArr, byte[] bArr2) {
        if (bArr.length == this.digestSize * 3) {
            return coreDigest(2, bArr, bArr2);
        }
        throw new IllegalArgumentException("wrong key length");
    }

    public byte[] PRF(byte[] bArr, byte[] bArr2) {
        if (bArr.length != this.digestSize) {
            throw new IllegalArgumentException("wrong key length");
        }
        if (bArr2.length == 32) {
            return coreDigest(3, bArr, bArr2);
        }
        throw new IllegalArgumentException("wrong address length");
    }
}
