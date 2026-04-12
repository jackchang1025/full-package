package p000;

import java.io.IOException;

/* loaded from: classes2.dex */
public final class dj1 extends ki1 implements ej1, InterfaceC1394wy {
    private final int oid;
    private final yi1 params;
    private final byte[] publicSeed;
    private final byte[] root;

    /* renamed from: dj1$a0 */
    public static class C0419a0 {
        private final yi1 params;
        private byte[] root = null;
        private byte[] publicSeed = null;
        private byte[] publicKey = null;

        public C0419a0(yi1 yi1Var) {
            this.params = yi1Var;
        }

        public dj1 build() {
            return new dj1(this);
        }

        public C0419a0 withPublicKey(byte[] bArr) {
            this.publicKey = fj1.cloneArray(bArr);
            return this;
        }

        public C0419a0 withPublicSeed(byte[] bArr) {
            this.publicSeed = fj1.cloneArray(bArr);
            return this;
        }

        public C0419a0 withRoot(byte[] bArr) {
            this.root = fj1.cloneArray(bArr);
            return this;
        }
    }

    private dj1(C0419a0 c0419a0) {
        super(false, c0419a0.params.getTreeDigest());
        yi1 yi1Var = c0419a0.params;
        this.params = yi1Var;
        if (yi1Var == null) {
            throw new NullPointerException("params == null");
        }
        int treeDigestSize = yi1Var.getTreeDigestSize();
        byte[] bArr = c0419a0.publicKey;
        if (bArr != null) {
            if (bArr.length == treeDigestSize + treeDigestSize) {
                this.oid = 0;
                this.root = fj1.extractBytesAtOffset(bArr, 0, treeDigestSize);
                this.publicSeed = fj1.extractBytesAtOffset(bArr, treeDigestSize, treeDigestSize);
                return;
            } else {
                if (bArr.length != treeDigestSize + 4 + treeDigestSize) {
                    throw new IllegalArgumentException("public key has wrong size");
                }
                this.oid = wl0.bigEndianToInt(bArr, 0);
                this.root = fj1.extractBytesAtOffset(bArr, 4, treeDigestSize);
                this.publicSeed = fj1.extractBytesAtOffset(bArr, 4 + treeDigestSize, treeDigestSize);
                return;
            }
        }
        if (yi1Var.getOid() != null) {
            this.oid = yi1Var.getOid().getOid();
        } else {
            this.oid = 0;
        }
        byte[] bArr2 = c0419a0.root;
        if (bArr2 == null) {
            this.root = new byte[treeDigestSize];
        } else {
            if (bArr2.length != treeDigestSize) {
                throw new IllegalArgumentException("length of root must be equal to length of digest");
            }
            this.root = bArr2;
        }
        byte[] bArr3 = c0419a0.publicSeed;
        if (bArr3 == null) {
            this.publicSeed = new byte[treeDigestSize];
        } else {
            if (bArr3.length != treeDigestSize) {
                throw new IllegalArgumentException("length of publicSeed must be equal to length of digest");
            }
            this.publicSeed = bArr3;
        }
    }

    @Override // p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return toByteArray();
    }

    public yi1 getParameters() {
        return this.params;
    }

    public byte[] getPublicSeed() {
        return fj1.cloneArray(this.publicSeed);
    }

    public byte[] getRoot() {
        return fj1.cloneArray(this.root);
    }

    @Override // p000.ej1
    public byte[] toByteArray() {
        byte[] bArr;
        int treeDigestSize = this.params.getTreeDigestSize();
        int i = this.oid;
        int i2 = 0;
        if (i != 0) {
            bArr = new byte[treeDigestSize + 4 + treeDigestSize];
            wl0.intToBigEndian(i, bArr, 0);
            i2 = 4;
        } else {
            bArr = new byte[treeDigestSize + treeDigestSize];
        }
        fj1.copyBytesAtOffset(bArr, this.root, i2);
        fj1.copyBytesAtOffset(bArr, this.publicSeed, i2 + treeDigestSize);
        return bArr;
    }
}
