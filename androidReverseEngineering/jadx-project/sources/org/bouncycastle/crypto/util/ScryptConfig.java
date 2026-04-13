package org.bouncycastle.crypto.util;

import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;

/* loaded from: classes.dex */
public class ScryptConfig extends PBKDFConfig {
    private final int blockSize;
    private final int costParameter;
    private final int parallelizationParameter;
    private final int saltLength;

    public static class Builder {
        private final int blockSize;
        private final int costParameter;
        private final int parallelizationParameter;
        private int saltLength = 16;

        public Builder(int i2, int i3, int i4) {
            if (i2 <= 1 || !isPowerOf2(i2)) {
                throw new IllegalArgumentException("Cost parameter N must be > 1 and a power of 2");
            }
            this.costParameter = i2;
            this.blockSize = i3;
            this.parallelizationParameter = i4;
        }

        private static boolean isPowerOf2(int i2) {
            return (i2 & (i2 + (-1))) == 0;
        }

        public ScryptConfig build() {
            return new ScryptConfig(this);
        }

        public Builder withSaltLength(int i2) {
            this.saltLength = i2;
            return this;
        }
    }

    private ScryptConfig(Builder builder) {
        super(MiscObjectIdentifiers.id_scrypt);
        this.costParameter = builder.costParameter;
        this.blockSize = builder.blockSize;
        this.parallelizationParameter = builder.parallelizationParameter;
        this.saltLength = builder.saltLength;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public int getCostParameter() {
        return this.costParameter;
    }

    public int getParallelizationParameter() {
        return this.parallelizationParameter;
    }

    public int getSaltLength() {
        return this.saltLength;
    }
}
