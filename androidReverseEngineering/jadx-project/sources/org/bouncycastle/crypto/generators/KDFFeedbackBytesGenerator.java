package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.MacDerivationFunction;
import org.bouncycastle.crypto.params.KDFFeedbackParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class KDFFeedbackBytesGenerator implements MacDerivationFunction {
    private static final BigInteger INTEGER_MAX = BigInteger.valueOf(2147483647L);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private byte[] fixedInputData;
    private int generatedBytes;

    /* renamed from: h */
    private final int f1246h;
    private byte[] ios;
    private byte[] iv;

    /* renamed from: k */
    private byte[] f1247k;
    private int maxSizeExcl;
    private final Mac prf;
    private boolean useCounter;

    public KDFFeedbackBytesGenerator(Mac mac) {
        this.prf = mac;
        int macSize = mac.getMacSize();
        this.f1246h = macSize;
        this.f1247k = new byte[macSize];
    }

    private void generateNext() {
        if (this.generatedBytes == 0) {
            Mac mac = this.prf;
            byte[] bArr = this.iv;
            mac.update(bArr, 0, bArr.length);
        } else {
            Mac mac2 = this.prf;
            byte[] bArr2 = this.f1247k;
            mac2.update(bArr2, 0, bArr2.length);
        }
        if (this.useCounter) {
            int i2 = (this.generatedBytes / this.f1246h) + 1;
            byte[] bArr3 = this.ios;
            int length = bArr3.length;
            if (length != 1) {
                if (length != 2) {
                    if (length != 3) {
                        if (length != 4) {
                            throw new IllegalStateException("Unsupported size of counter i");
                        }
                        bArr3[0] = (byte) (i2 >>> 24);
                    }
                    bArr3[bArr3.length - 3] = (byte) (i2 >>> 16);
                }
                bArr3[bArr3.length - 2] = (byte) (i2 >>> 8);
            }
            bArr3[bArr3.length - 1] = (byte) i2;
            this.prf.update(bArr3, 0, bArr3.length);
        }
        Mac mac3 = this.prf;
        byte[] bArr4 = this.fixedInputData;
        mac3.update(bArr4, 0, bArr4.length);
        this.prf.doFinal(this.f1247k, 0);
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public int generateBytes(byte[] bArr, int i2, int i3) {
        int i4 = this.generatedBytes;
        int i5 = i4 + i3;
        if (i5 < 0 || i5 >= this.maxSizeExcl) {
            throw new DataLengthException(AbstractC0000a.m17m(new StringBuilder("Current KDFCTR may only be used for "), this.maxSizeExcl, " bytes"));
        }
        if (i4 % this.f1246h == 0) {
            generateNext();
        }
        int i6 = this.generatedBytes;
        int i7 = this.f1246h;
        int i8 = i6 % i7;
        int min = Math.min(i7 - (i6 % i7), i3);
        System.arraycopy(this.f1247k, i8, bArr, i2, min);
        this.generatedBytes += min;
        int i9 = i3 - min;
        while (true) {
            i2 += min;
            if (i9 <= 0) {
                return i3;
            }
            generateNext();
            min = Math.min(this.f1246h, i9);
            System.arraycopy(this.f1247k, 0, bArr, i2, min);
            this.generatedBytes += min;
            i9 -= min;
        }
    }

    @Override // org.bouncycastle.crypto.MacDerivationFunction
    public Mac getMac() {
        return this.prf;
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public void init(DerivationParameters derivationParameters) {
        if (!(derivationParameters instanceof KDFFeedbackParameters)) {
            throw new IllegalArgumentException("Wrong type of arguments given");
        }
        KDFFeedbackParameters kDFFeedbackParameters = (KDFFeedbackParameters) derivationParameters;
        this.prf.init(new KeyParameter(kDFFeedbackParameters.getKI()));
        this.fixedInputData = kDFFeedbackParameters.getFixedInputData();
        int r2 = kDFFeedbackParameters.getR();
        this.ios = new byte[r2 / 8];
        int i2 = Integer.MAX_VALUE;
        if (kDFFeedbackParameters.useCounter()) {
            BigInteger multiply = TWO.pow(r2).multiply(BigInteger.valueOf(this.f1246h));
            if (multiply.compareTo(INTEGER_MAX) != 1) {
                i2 = multiply.intValue();
            }
        }
        this.maxSizeExcl = i2;
        this.iv = kDFFeedbackParameters.getIV();
        this.useCounter = kDFFeedbackParameters.useCounter();
        this.generatedBytes = 0;
    }
}
