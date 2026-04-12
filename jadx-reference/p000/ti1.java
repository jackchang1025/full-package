package p000;

import java.io.IOException;
import org.bouncycastle.pqc.crypto.xmss.BDSStateMap;

/* loaded from: classes2.dex */
public final class ti1 extends oi1 implements ej1, InterfaceC1394wy {
    private volatile BDSStateMap bdsState;
    private volatile long index;
    private final qi1 params;
    private final byte[] publicSeed;
    private final byte[] root;
    private final byte[] secretKeyPRF;
    private final byte[] secretKeySeed;
    private volatile boolean used;

    /* renamed from: ti1$a0 */
    public static class C1261a0 {
        private final qi1 params;
        private long index = 0;
        private long maxIndex = -1;
        private byte[] secretKeySeed = null;
        private byte[] secretKeyPRF = null;
        private byte[] publicSeed = null;
        private byte[] root = null;
        private BDSStateMap bdsState = null;
        private byte[] privateKey = null;
        private yi1 xmss = null;

        public C1261a0(qi1 qi1Var) {
            this.params = qi1Var;
        }

        public ti1 build() {
            return new ti1(this);
        }

        public C1261a0 withBDSState(BDSStateMap bDSStateMap) {
            if (bDSStateMap.getMaxIndex() == 0) {
                this.bdsState = new BDSStateMap(bDSStateMap, (1 << this.params.getHeight()) - 1);
                return this;
            }
            this.bdsState = bDSStateMap;
            return this;
        }

        public C1261a0 withIndex(long j) {
            this.index = j;
            return this;
        }

        public C1261a0 withMaxIndex(long j) {
            this.maxIndex = j;
            return this;
        }

        public C1261a0 withPrivateKey(byte[] bArr) {
            this.privateKey = fj1.cloneArray(bArr);
            this.xmss = this.params.getXMSSParameters();
            return this;
        }

        public C1261a0 withPublicSeed(byte[] bArr) {
            this.publicSeed = fj1.cloneArray(bArr);
            return this;
        }

        public C1261a0 withRoot(byte[] bArr) {
            this.root = fj1.cloneArray(bArr);
            return this;
        }

        public C1261a0 withSecretKeyPRF(byte[] bArr) {
            this.secretKeyPRF = fj1.cloneArray(bArr);
            return this;
        }

        public C1261a0 withSecretKeySeed(byte[] bArr) {
            this.secretKeySeed = fj1.cloneArray(bArr);
            return this;
        }
    }

    private ti1(C1261a0 c1261a0) {
        super(true, c1261a0.params.getTreeDigest());
        qi1 qi1Var = c1261a0.params;
        this.params = qi1Var;
        if (qi1Var == null) {
            throw new NullPointerException("params == null");
        }
        int treeDigestSize = qi1Var.getTreeDigestSize();
        byte[] bArr = c1261a0.privateKey;
        if (bArr != null) {
            if (c1261a0.xmss == null) {
                throw new NullPointerException("xmss == null");
            }
            int height = qi1Var.getHeight();
            int i = (height + 7) / 8;
            this.index = fj1.bytesToXBigEndian(bArr, 0, i);
            if (!fj1.isIndexValid(height, this.index)) {
                throw new IllegalArgumentException("index out of bounds");
            }
            this.secretKeySeed = fj1.extractBytesAtOffset(bArr, i, treeDigestSize);
            int i2 = i + treeDigestSize;
            this.secretKeyPRF = fj1.extractBytesAtOffset(bArr, i2, treeDigestSize);
            int i3 = i2 + treeDigestSize;
            this.publicSeed = fj1.extractBytesAtOffset(bArr, i3, treeDigestSize);
            int i4 = i3 + treeDigestSize;
            this.root = fj1.extractBytesAtOffset(bArr, i4, treeDigestSize);
            int i5 = i4 + treeDigestSize;
            try {
                this.bdsState = ((BDSStateMap) fj1.deserialize(fj1.extractBytesAtOffset(bArr, i5, bArr.length - i5), BDSStateMap.class)).withWOTSDigest(c1261a0.xmss.getTreeDigestOID());
                return;
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            } catch (ClassNotFoundException e2) {
                throw new IllegalArgumentException(e2.getMessage(), e2);
            }
        }
        this.index = c1261a0.index;
        byte[] bArr2 = c1261a0.secretKeySeed;
        if (bArr2 == null) {
            this.secretKeySeed = new byte[treeDigestSize];
        } else {
            if (bArr2.length != treeDigestSize) {
                throw new IllegalArgumentException("size of secretKeySeed needs to be equal size of digest");
            }
            this.secretKeySeed = bArr2;
        }
        byte[] bArr3 = c1261a0.secretKeyPRF;
        if (bArr3 == null) {
            this.secretKeyPRF = new byte[treeDigestSize];
        } else {
            if (bArr3.length != treeDigestSize) {
                throw new IllegalArgumentException("size of secretKeyPRF needs to be equal size of digest");
            }
            this.secretKeyPRF = bArr3;
        }
        byte[] bArr4 = c1261a0.publicSeed;
        if (bArr4 == null) {
            this.publicSeed = new byte[treeDigestSize];
        } else {
            if (bArr4.length != treeDigestSize) {
                throw new IllegalArgumentException("size of publicSeed needs to be equal size of digest");
            }
            this.publicSeed = bArr4;
        }
        byte[] bArr5 = c1261a0.root;
        if (bArr5 == null) {
            this.root = new byte[treeDigestSize];
        } else {
            if (bArr5.length != treeDigestSize) {
                throw new IllegalArgumentException("size of root needs to be equal size of digest");
            }
            this.root = bArr5;
        }
        BDSStateMap bDSStateMap = c1261a0.bdsState;
        if (bDSStateMap != null) {
            this.bdsState = bDSStateMap;
        } else {
            if (!fj1.isIndexValid(qi1Var.getHeight(), c1261a0.index) || bArr4 == null || bArr2 == null) {
                bDSStateMap = new BDSStateMap(c1261a0.maxIndex + 1);
                this.bdsState = bDSStateMap;
            } else {
                this.bdsState = new BDSStateMap(qi1Var, c1261a0.index, bArr4, bArr2);
            }
        }
        if (c1261a0.maxIndex >= 0 && c1261a0.maxIndex != this.bdsState.getMaxIndex()) {
            throw new IllegalArgumentException("maxIndex set but not reflected in state");
        }
    }

    public ti1 extractKeyShard(int i) {
        ti1 ti1VarBuild;
        if (i < 1) {
            throw new IllegalArgumentException("cannot ask for a shard with 0 keys");
        }
        synchronized (this) {
            long j = i;
            try {
                if (j > getUsagesRemaining()) {
                    throw new IllegalArgumentException("usageCount exceeds usages remaining");
                }
                ti1VarBuild = new C1261a0(this.params).withSecretKeySeed(this.secretKeySeed).withSecretKeyPRF(this.secretKeyPRF).withPublicSeed(this.publicSeed).withRoot(this.root).withIndex(getIndex()).withBDSState(new BDSStateMap(this.bdsState, (getIndex() + j) - 1)).build();
                for (int i2 = 0; i2 != i; i2++) {
                    rollKey();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return ti1VarBuild;
    }

    public BDSStateMap getBDSState() {
        return this.bdsState;
    }

    @Override // p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        byte[] byteArray;
        synchronized (this) {
            byteArray = toByteArray();
        }
        return byteArray;
    }

    public long getIndex() {
        return this.index;
    }

    public ti1 getNextKey() {
        ti1 ti1VarExtractKeyShard;
        synchronized (this) {
            ti1VarExtractKeyShard = extractKeyShard(1);
        }
        return ti1VarExtractKeyShard;
    }

    public qi1 getParameters() {
        return this.params;
    }

    public byte[] getPublicSeed() {
        return fj1.cloneArray(this.publicSeed);
    }

    public byte[] getRoot() {
        return fj1.cloneArray(this.root);
    }

    public byte[] getSecretKeyPRF() {
        return fj1.cloneArray(this.secretKeyPRF);
    }

    public byte[] getSecretKeySeed() {
        return fj1.cloneArray(this.secretKeySeed);
    }

    public long getUsagesRemaining() {
        long maxIndex;
        synchronized (this) {
            maxIndex = (this.bdsState.getMaxIndex() - getIndex()) + 1;
        }
        return maxIndex;
    }

    public ti1 rollKey() {
        synchronized (this) {
            try {
                if (getIndex() < this.bdsState.getMaxIndex()) {
                    this.bdsState.updateState(this.params, this.index, this.publicSeed, this.secretKeySeed);
                    this.index++;
                } else {
                    this.index = this.bdsState.getMaxIndex() + 1;
                    this.bdsState = new BDSStateMap(this.bdsState.getMaxIndex());
                }
                this.used = false;
            } catch (Throwable th) {
                throw th;
            }
        }
        return this;
    }

    @Override // p000.ej1
    public byte[] toByteArray() {
        byte[] bArrConcatenate;
        synchronized (this) {
            try {
                int treeDigestSize = this.params.getTreeDigestSize();
                int height = (this.params.getHeight() + 7) / 8;
                byte[] bArr = new byte[height + treeDigestSize + treeDigestSize + treeDigestSize + treeDigestSize];
                fj1.copyBytesAtOffset(bArr, fj1.toBytesBigEndian(this.index, height), 0);
                fj1.copyBytesAtOffset(bArr, this.secretKeySeed, height);
                int i = height + treeDigestSize;
                fj1.copyBytesAtOffset(bArr, this.secretKeyPRF, i);
                int i2 = i + treeDigestSize;
                fj1.copyBytesAtOffset(bArr, this.publicSeed, i2);
                fj1.copyBytesAtOffset(bArr, this.root, i2 + treeDigestSize);
                try {
                    bArrConcatenate = C0133bg.concatenate(bArr, fj1.serialize(this.bdsState));
                } catch (IOException e) {
                    throw new IllegalStateException("error serializing bds state: " + e.getMessage(), e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bArrConcatenate;
    }
}
