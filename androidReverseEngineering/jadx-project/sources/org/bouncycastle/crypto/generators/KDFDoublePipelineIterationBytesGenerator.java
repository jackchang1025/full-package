package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.MacDerivationFunction;
import org.bouncycastle.crypto.params.KDFDoublePipelineIterationParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class KDFDoublePipelineIterationBytesGenerator implements MacDerivationFunction {
    private static final BigInteger INTEGER_MAX = BigInteger.valueOf(2147483647L);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    /* renamed from: a */
    private byte[] f1243a;
    private byte[] fixedInputData;
    private int generatedBytes;

    /* renamed from: h */
    private final int f1244h;
    private byte[] ios;

    /* renamed from: k */
    private byte[] f1245k;
    private int maxSizeExcl;
    private final Mac prf;
    private boolean useCounter;

    public KDFDoublePipelineIterationBytesGenerator(Mac mac) {
        this.prf = mac;
        int macSize = mac.getMacSize();
        this.f1244h = macSize;
        this.f1243a = new byte[macSize];
        this.f1245k = new byte[macSize];
    }

    private void generateNext() {
        if (this.generatedBytes == 0) {
            Mac mac = this.prf;
            byte[] bArr = this.fixedInputData;
            mac.update(bArr, 0, bArr.length);
            this.prf.doFinal(this.f1243a, 0);
        } else {
            Mac mac2 = this.prf;
            byte[] bArr2 = this.f1243a;
            mac2.update(bArr2, 0, bArr2.length);
            this.prf.doFinal(this.f1243a, 0);
        }
        Mac mac3 = this.prf;
        byte[] bArr3 = this.f1243a;
        mac3.update(bArr3, 0, bArr3.length);
        if (this.useCounter) {
            int i2 = (this.generatedBytes / this.f1244h) + 1;
            byte[] bArr4 = this.ios;
            int length = bArr4.length;
            if (length != 1) {
                if (length != 2) {
                    if (length != 3) {
                        if (length != 4) {
                            throw new IllegalStateException("Unsupported size of counter i");
                        }
                        bArr4[0] = (byte) (i2 >>> 24);
                    }
                    bArr4[bArr4.length - 3] = (byte) (i2 >>> 16);
                }
                bArr4[bArr4.length - 2] = (byte) (i2 >>> 8);
            }
            bArr4[bArr4.length - 1] = (byte) i2;
            this.prf.update(bArr4, 0, bArr4.length);
        }
        Mac mac4 = this.prf;
        byte[] bArr5 = this.fixedInputData;
        mac4.update(bArr5, 0, bArr5.length);
        this.prf.doFinal(this.f1245k, 0);
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public int generateBytes(byte[] bArr, int i2, int i3) {
        int i4 = this.generatedBytes;
        int i5 = i4 + i3;
        if (i5 < 0 || i5 >= this.maxSizeExcl) {
            throw new DataLengthException(AbstractC0000a.m17m(new StringBuilder("Current KDFCTR may only be used for "), this.maxSizeExcl, " bytes"));
        }
        if (i4 % this.f1244h == 0) {
            generateNext();
        }
        int i6 = this.generatedBytes;
        int i7 = this.f1244h;
        int i8 = i6 % i7;
        int min = Math.min(i7 - (i6 % i7), i3);
        System.arraycopy(this.f1245k, i8, bArr, i2, min);
        this.generatedBytes += min;
        int i9 = i3 - min;
        while (true) {
            i2 += min;
            if (i9 <= 0) {
                return i3;
            }
            generateNext();
            min = Math.min(this.f1244h, i9);
            System.arraycopy(this.f1245k, 0, bArr, i2, min);
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
        if (!(derivationParameters instanceof KDFDoublePipelineIterationParameters)) {
            throw new IllegalArgumentException("Wrong type of arguments given");
        }
        KDFDoublePipelineIterationParameters kDFDoublePipelineIterationParameters = (KDFDoublePipelineIterationParameters) derivationParameters;
        this.prf.init(new KeyParameter(kDFDoublePipelineIterationParameters.getKI()));
        this.fixedInputData = kDFDoublePipelineIterationParameters.getFixedInputData();
        int r2 = kDFDoublePipelineIterationParameters.getR();
        this.ios = new byte[r2 / 8];
        int i2 = Integer.MAX_VALUE;
        if (kDFDoublePipelineIterationParameters.useCounter()) {
            BigInteger multiply = TWO.pow(r2).multiply(BigInteger.valueOf(this.f1244h));
            if (multiply.compareTo(INTEGER_MAX) != 1) {
                i2 = multiply.intValue();
            }
        }
        this.maxSizeExcl = i2;
        this.useCounter = kDFDoublePipelineIterationParameters.useCounter();
        this.generatedBytes = 0;
    }
}
