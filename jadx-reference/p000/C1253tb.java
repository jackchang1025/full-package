package p000;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

/* renamed from: tb */
/* loaded from: classes2.dex */
public class C1253tb extends OutputStream {
    private MessageDigest digest;

    public C1253tb(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        this.digest.update((byte) i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        this.digest.update(bArr);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.digest.update(bArr, i, i2);
    }
}
