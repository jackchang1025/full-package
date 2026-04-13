package org.bouncycastle.cert.crmf.bc;

import java.security.SecureRandom;
import org.bouncycastle.cert.crmf.EncryptedValuePadder;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.generators.MGF1BytesGenerator;
import org.bouncycastle.crypto.params.MGFParameters;

/* loaded from: classes.dex */
public class BcFixedLengthMGF1Padder implements EncryptedValuePadder {
    private Digest dig;
    private int length;
    private SecureRandom random;

    public BcFixedLengthMGF1Padder(int i2) {
        this(i2, null);
    }

    @Override // org.bouncycastle.cert.crmf.EncryptedValuePadder
    public byte[] getPaddedData(byte[] bArr) {
        int i2 = this.length;
        byte[] bArr2 = new byte[i2];
        int digestSize = this.dig.getDigestSize();
        byte[] bArr3 = new byte[digestSize];
        int digestSize2 = this.length - this.dig.getDigestSize();
        byte[] bArr4 = new byte[digestSize2];
        if (this.random == null) {
            this.random = new SecureRandom();
        }
        this.random.nextBytes(bArr3);
        MGF1BytesGenerator mGF1BytesGenerator = new MGF1BytesGenerator(this.dig);
        mGF1BytesGenerator.init(new MGFParameters(bArr3));
        mGF1BytesGenerator.generateBytes(bArr4, 0, digestSize2);
        System.arraycopy(bArr3, 0, bArr2, 0, digestSize);
        System.arraycopy(bArr, 0, bArr2, digestSize, bArr.length);
        int length = bArr.length + digestSize;
        while (true) {
            length++;
            if (length == i2) {
                break;
            }
            bArr2[length] = (byte) (this.random.nextInt(255) + 1);
        }
        for (int i3 = 0; i3 != digestSize2; i3++) {
            int i4 = i3 + digestSize;
            bArr2[i4] = (byte) (bArr2[i4] ^ bArr4[i3]);
        }
        return bArr2;
    }

    @Override // org.bouncycastle.cert.crmf.EncryptedValuePadder
    public byte[] getUnpaddedData(byte[] bArr) {
        int digestSize = this.dig.getDigestSize();
        byte[] bArr2 = new byte[digestSize];
        int digestSize2 = this.length - this.dig.getDigestSize();
        byte[] bArr3 = new byte[digestSize2];
        System.arraycopy(bArr, 0, bArr2, 0, digestSize);
        MGF1BytesGenerator mGF1BytesGenerator = new MGF1BytesGenerator(this.dig);
        mGF1BytesGenerator.init(new MGFParameters(bArr2));
        mGF1BytesGenerator.generateBytes(bArr3, 0, digestSize2);
        for (int i2 = 0; i2 != digestSize2; i2++) {
            int i3 = i2 + digestSize;
            bArr[i3] = (byte) (bArr[i3] ^ bArr3[i2]);
        }
        int length = bArr.length - 1;
        while (true) {
            if (length == digestSize) {
                length = 0;
                break;
            }
            if (bArr[length] == 0) {
                break;
            }
            length--;
        }
        if (length == 0) {
            throw new IllegalStateException("bad padding in encoding");
        }
        int i4 = length - digestSize;
        byte[] bArr4 = new byte[i4];
        System.arraycopy(bArr, digestSize, bArr4, 0, i4);
        return bArr4;
    }

    public BcFixedLengthMGF1Padder(int i2, SecureRandom secureRandom) {
        this.dig = new SHA1Digest();
        this.length = i2;
        this.random = secureRandom;
    }
}
