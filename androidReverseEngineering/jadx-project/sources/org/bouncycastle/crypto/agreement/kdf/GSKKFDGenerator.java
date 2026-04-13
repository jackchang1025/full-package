package org.bouncycastle.crypto.agreement.kdf;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.DigestDerivationFunction;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class GSKKFDGenerator implements DigestDerivationFunction {
    private byte[] buf;
    private int counter;
    private final Digest digest;

    /* renamed from: r */
    private byte[] f1136r;

    /* renamed from: z */
    private byte[] f1137z;

    public GSKKFDGenerator(Digest digest) {
        this.digest = digest;
        this.buf = new byte[digest.getDigestSize()];
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public int generateBytes(byte[] bArr, int i2, int i3) {
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("output buffer too small");
        }
        Digest digest = this.digest;
        byte[] bArr2 = this.f1137z;
        digest.update(bArr2, 0, bArr2.length);
        int i4 = this.counter;
        this.counter = i4 + 1;
        byte[] intToBigEndian = Pack.intToBigEndian(i4);
        this.digest.update(intToBigEndian, 0, intToBigEndian.length);
        byte[] bArr3 = this.f1136r;
        if (bArr3 != null) {
            this.digest.update(bArr3, 0, bArr3.length);
        }
        this.digest.doFinal(this.buf, 0);
        System.arraycopy(this.buf, 0, bArr, i2, i3);
        Arrays.clear(this.buf);
        return i3;
    }

    @Override // org.bouncycastle.crypto.DigestDerivationFunction
    public Digest getDigest() {
        return this.digest;
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public void init(DerivationParameters derivationParameters) {
        if (!(derivationParameters instanceof GSKKDFParameters)) {
            throw new IllegalArgumentException("unkown parameters type");
        }
        GSKKDFParameters gSKKDFParameters = (GSKKDFParameters) derivationParameters;
        this.f1137z = gSKKDFParameters.getZ();
        this.counter = gSKKDFParameters.getStartCounter();
        this.f1136r = gSKKDFParameters.getNonce();
    }
}
