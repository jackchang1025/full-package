package org.bouncycastle.pqc.crypto.xmss;

import java.io.IOException;
import org.bouncycastle.pqc.crypto.xmss.OTSHashAddress;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Encodable;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public final class XMSSPrivateKeyParameters extends XMSSKeyParameters implements XMSSStoreableObjectInterface, Encodable {
    private volatile BDS bdsState;
    private final XMSSParameters params;
    private final byte[] publicSeed;
    private final byte[] root;
    private final byte[] secretKeyPRF;
    private final byte[] secretKeySeed;

    public static class Builder {
        private final XMSSParameters params;
        private int index = 0;
        private int maxIndex = -1;
        private byte[] secretKeySeed = null;
        private byte[] secretKeyPRF = null;
        private byte[] publicSeed = null;
        private byte[] root = null;
        private BDS bdsState = null;
        private byte[] privateKey = null;

        public Builder(XMSSParameters xMSSParameters) {
            this.params = xMSSParameters;
        }

        public XMSSPrivateKeyParameters build() {
            return new XMSSPrivateKeyParameters(this);
        }

        public Builder withBDSState(BDS bds) {
            this.bdsState = bds;
            return this;
        }

        public Builder withIndex(int i2) {
            this.index = i2;
            return this;
        }

        public Builder withMaxIndex(int i2) {
            this.maxIndex = i2;
            return this;
        }

        public Builder withPrivateKey(byte[] bArr) {
            this.privateKey = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withPublicSeed(byte[] bArr) {
            this.publicSeed = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withRoot(byte[] bArr) {
            this.root = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withSecretKeyPRF(byte[] bArr) {
            this.secretKeyPRF = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withSecretKeySeed(byte[] bArr) {
            this.secretKeySeed = XMSSUtil.cloneArray(bArr);
            return this;
        }
    }

    private XMSSPrivateKeyParameters(Builder builder) {
        super(true, builder.params.getTreeDigest());
        XMSSParameters xMSSParameters = builder.params;
        this.params = xMSSParameters;
        if (xMSSParameters == null) {
            throw new NullPointerException("params == null");
        }
        int treeDigestSize = xMSSParameters.getTreeDigestSize();
        byte[] bArr = builder.privateKey;
        if (bArr != null) {
            int height = xMSSParameters.getHeight();
            int bigEndianToInt = Pack.bigEndianToInt(bArr, 0);
            if (!XMSSUtil.isIndexValid(height, bigEndianToInt)) {
                throw new IllegalArgumentException("index out of bounds");
            }
            this.secretKeySeed = XMSSUtil.extractBytesAtOffset(bArr, 4, treeDigestSize);
            int i2 = 4 + treeDigestSize;
            this.secretKeyPRF = XMSSUtil.extractBytesAtOffset(bArr, i2, treeDigestSize);
            int i3 = i2 + treeDigestSize;
            this.publicSeed = XMSSUtil.extractBytesAtOffset(bArr, i3, treeDigestSize);
            int i4 = i3 + treeDigestSize;
            this.root = XMSSUtil.extractBytesAtOffset(bArr, i4, treeDigestSize);
            int i5 = i4 + treeDigestSize;
            try {
                BDS bds = (BDS) XMSSUtil.deserialize(XMSSUtil.extractBytesAtOffset(bArr, i5, bArr.length - i5), BDS.class);
                if (bds.getIndex() != bigEndianToInt) {
                    throw new IllegalStateException("serialized BDS has wrong index");
                }
                this.bdsState = bds.withWOTSDigest(builder.params.getTreeDigestOID());
                return;
            } catch (IOException e2) {
                throw new IllegalArgumentException(e2.getMessage(), e2);
            } catch (ClassNotFoundException e3) {
                throw new IllegalArgumentException(e3.getMessage(), e3);
            }
        }
        byte[] bArr2 = builder.secretKeySeed;
        if (bArr2 == null) {
            this.secretKeySeed = new byte[treeDigestSize];
        } else {
            if (bArr2.length != treeDigestSize) {
                throw new IllegalArgumentException("size of secretKeySeed needs to be equal size of digest");
            }
            this.secretKeySeed = bArr2;
        }
        byte[] bArr3 = builder.secretKeyPRF;
        if (bArr3 == null) {
            this.secretKeyPRF = new byte[treeDigestSize];
        } else {
            if (bArr3.length != treeDigestSize) {
                throw new IllegalArgumentException("size of secretKeyPRF needs to be equal size of digest");
            }
            this.secretKeyPRF = bArr3;
        }
        byte[] bArr4 = builder.publicSeed;
        if (bArr4 == null) {
            this.publicSeed = new byte[treeDigestSize];
        } else {
            if (bArr4.length != treeDigestSize) {
                throw new IllegalArgumentException("size of publicSeed needs to be equal size of digest");
            }
            this.publicSeed = bArr4;
        }
        byte[] bArr5 = builder.root;
        if (bArr5 == null) {
            this.root = new byte[treeDigestSize];
        } else {
            if (bArr5.length != treeDigestSize) {
                throw new IllegalArgumentException("size of root needs to be equal size of digest");
            }
            this.root = bArr5;
        }
        BDS bds2 = builder.bdsState;
        this.bdsState = bds2 == null ? (builder.index >= (1 << xMSSParameters.getHeight()) + (-2) || bArr4 == null || bArr2 == null) ? new BDS(xMSSParameters, (1 << xMSSParameters.getHeight()) - 1, builder.index) : new BDS(xMSSParameters, bArr4, bArr2, (OTSHashAddress) new OTSHashAddress.Builder().build(), builder.index) : bds2;
        if (builder.maxIndex >= 0 && builder.maxIndex != this.bdsState.getMaxIndex()) {
            throw new IllegalArgumentException("maxIndex set but not reflected in state");
        }
    }

    public XMSSPrivateKeyParameters extractKeyShard(int i2) {
        XMSSPrivateKeyParameters build;
        if (i2 < 1) {
            throw new IllegalArgumentException("cannot ask for a shard with 0 keys");
        }
        synchronized (this) {
            long j2 = i2;
            if (j2 > getUsagesRemaining()) {
                throw new IllegalArgumentException("usageCount exceeds usages remaining");
            }
            build = new Builder(this.params).withSecretKeySeed(this.secretKeySeed).withSecretKeyPRF(this.secretKeyPRF).withPublicSeed(this.publicSeed).withRoot(this.root).withIndex(getIndex()).withBDSState(this.bdsState.withMaxIndex((this.bdsState.getIndex() + i2) - 1, this.params.getTreeDigestOID())).build();
            if (j2 == getUsagesRemaining()) {
                this.bdsState = new BDS(this.params, this.bdsState.getMaxIndex(), getIndex() + i2);
            } else {
                OTSHashAddress oTSHashAddress = (OTSHashAddress) new OTSHashAddress.Builder().build();
                for (int i3 = 0; i3 != i2; i3++) {
                    this.bdsState = this.bdsState.getNextState(this.publicSeed, this.secretKeySeed, oTSHashAddress);
                }
            }
        }
        return build;
    }

    public BDS getBDSState() {
        return this.bdsState;
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        byte[] byteArray;
        synchronized (this) {
            byteArray = toByteArray();
        }
        return byteArray;
    }

    public int getIndex() {
        return this.bdsState.getIndex();
    }

    public XMSSPrivateKeyParameters getNextKey() {
        XMSSPrivateKeyParameters extractKeyShard;
        synchronized (this) {
            extractKeyShard = extractKeyShard(1);
        }
        return extractKeyShard;
    }

    public XMSSParameters getParameters() {
        return this.params;
    }

    public byte[] getPublicSeed() {
        return XMSSUtil.cloneArray(this.publicSeed);
    }

    public byte[] getRoot() {
        return XMSSUtil.cloneArray(this.root);
    }

    public byte[] getSecretKeyPRF() {
        return XMSSUtil.cloneArray(this.secretKeyPRF);
    }

    public byte[] getSecretKeySeed() {
        return XMSSUtil.cloneArray(this.secretKeySeed);
    }

    public long getUsagesRemaining() {
        long maxIndex;
        synchronized (this) {
            maxIndex = (this.bdsState.getMaxIndex() - getIndex()) + 1;
        }
        return maxIndex;
    }

    public XMSSPrivateKeyParameters rollKey() {
        synchronized (this) {
            this.bdsState = this.bdsState.getIndex() < this.bdsState.getMaxIndex() ? this.bdsState.getNextState(this.publicSeed, this.secretKeySeed, (OTSHashAddress) new OTSHashAddress.Builder().build()) : new BDS(this.params, this.bdsState.getMaxIndex(), this.bdsState.getMaxIndex() + 1);
        }
        return this;
    }

    @Override // org.bouncycastle.pqc.crypto.xmss.XMSSStoreableObjectInterface
    public byte[] toByteArray() {
        byte[] concatenate;
        synchronized (this) {
            int treeDigestSize = this.params.getTreeDigestSize();
            byte[] bArr = new byte[treeDigestSize + 4 + treeDigestSize + treeDigestSize + treeDigestSize];
            Pack.intToBigEndian(this.bdsState.getIndex(), bArr, 0);
            XMSSUtil.copyBytesAtOffset(bArr, this.secretKeySeed, 4);
            int i2 = 4 + treeDigestSize;
            XMSSUtil.copyBytesAtOffset(bArr, this.secretKeyPRF, i2);
            int i3 = i2 + treeDigestSize;
            XMSSUtil.copyBytesAtOffset(bArr, this.publicSeed, i3);
            XMSSUtil.copyBytesAtOffset(bArr, this.root, i3 + treeDigestSize);
            try {
                concatenate = Arrays.concatenate(bArr, XMSSUtil.serialize(this.bdsState));
            } catch (IOException e2) {
                throw new RuntimeException("error serializing bds state: " + e2.getMessage());
            }
        }
        return concatenate;
    }
}
