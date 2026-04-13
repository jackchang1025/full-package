package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public final class KDFDoublePipelineIterationParameters implements DerivationParameters {
    private static final int UNUSED_R = 32;
    private final byte[] fixedInputData;
    private final byte[] ki;

    /* renamed from: r */
    private final int f1327r;
    private final boolean useCounter;

    private KDFDoublePipelineIterationParameters(byte[] bArr, byte[] bArr2, int i2, boolean z2) {
        if (bArr == null) {
            throw new IllegalArgumentException("A KDF requires Ki (a seed) as input");
        }
        this.ki = Arrays.clone(bArr);
        if (bArr2 == null) {
            this.fixedInputData = new byte[0];
        } else {
            this.fixedInputData = Arrays.clone(bArr2);
        }
        if (i2 != 8 && i2 != 16 && i2 != 24 && i2 != 32) {
            throw new IllegalArgumentException("Length of counter should be 8, 16, 24 or 32");
        }
        this.f1327r = i2;
        this.useCounter = z2;
    }

    public static KDFDoublePipelineIterationParameters createWithCounter(byte[] bArr, byte[] bArr2, int i2) {
        return new KDFDoublePipelineIterationParameters(bArr, bArr2, i2, true);
    }

    public static KDFDoublePipelineIterationParameters createWithoutCounter(byte[] bArr, byte[] bArr2) {
        return new KDFDoublePipelineIterationParameters(bArr, bArr2, 32, false);
    }

    public byte[] getFixedInputData() {
        return Arrays.clone(this.fixedInputData);
    }

    public byte[] getKI() {
        return this.ki;
    }

    public int getR() {
        return this.f1327r;
    }

    public boolean useCounter() {
        return this.useCounter;
    }
}
