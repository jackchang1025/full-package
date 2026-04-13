package org.bouncycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class NullDigest implements Digest {
    private OpenByteArrayOutputStream bOut = new OpenByteArrayOutputStream();

    public static class OpenByteArrayOutputStream extends ByteArrayOutputStream {
        private OpenByteArrayOutputStream() {
        }

        public void copy(byte[] bArr, int i2) {
            System.arraycopy(((ByteArrayOutputStream) this).buf, 0, bArr, i2, size());
        }

        @Override // java.io.ByteArrayOutputStream
        public void reset() {
            super.reset();
            Arrays.clear(((ByteArrayOutputStream) this).buf);
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        int size = this.bOut.size();
        this.bOut.copy(bArr, i2);
        reset();
        return size;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "NULL";
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.bOut.size();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.bOut.reset();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        this.bOut.write(b);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        this.bOut.write(bArr, i2, i3);
    }
}
