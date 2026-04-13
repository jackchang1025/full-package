package b1;

import java.util.Arrays;

/* renamed from: b1.o */
/* loaded from: classes.dex */
public final class C0093o {

    /* renamed from: a */
    public final byte f158a;

    /* renamed from: b */
    public final byte[] f159b;

    public C0093o(byte b, byte[] bArr) {
        byte[] bArr2 = new byte[8191];
        this.f159b = bArr2;
        this.f158a = b;
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, 8191));
    }

    public final String toString() {
        return "PeerInfo{type=" + ((int) this.f158a) + ", data=" + Arrays.toString(this.f159b) + '}';
    }
}
