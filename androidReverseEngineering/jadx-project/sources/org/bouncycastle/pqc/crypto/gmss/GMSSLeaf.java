package org.bouncycastle.pqc.crypto.gmss;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class GMSSLeaf {
    private byte[] concHashs;
    private GMSSRandom gmssRandom;

    /* renamed from: i */
    private int f1537i;

    /* renamed from: j */
    private int f1538j;
    private int keysize;
    private byte[] leaf;
    private int mdsize;
    private Digest messDigestOTS;
    byte[] privateKeyOTS;
    private byte[] seed;
    private int steps;
    private int two_power_w;

    /* renamed from: w */
    private int f1539w;

    public GMSSLeaf(Digest digest, int i2, int i3) {
        this.f1539w = i2;
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        this.mdsize = this.messDigestOTS.getDigestSize();
        double d2 = i2;
        this.keysize = ((int) Math.ceil((r7 << 3) / d2)) + ((int) Math.ceil(getLog((r7 << i2) + 1) / d2));
        this.two_power_w = 1 << i2;
        this.steps = (int) Math.ceil(((((r8 - 1) * r7) + 1) + r7) / i3);
        int i4 = this.mdsize;
        this.seed = new byte[i4];
        this.leaf = new byte[i4];
        this.privateKeyOTS = new byte[i4];
        this.concHashs = new byte[i4 * this.keysize];
    }

    private int getLog(int i2) {
        int i3 = 1;
        int i4 = 2;
        while (i4 < i2) {
            i4 <<= 1;
            i3++;
        }
        return i3;
    }

    private void updateLeafCalc() {
        byte[] bArr = new byte[this.messDigestOTS.getDigestSize()];
        for (int i2 = 0; i2 < this.steps + 10000; i2++) {
            int i3 = this.f1537i;
            if (i3 == this.keysize && this.f1538j == this.two_power_w - 1) {
                Digest digest = this.messDigestOTS;
                byte[] bArr2 = this.concHashs;
                digest.update(bArr2, 0, bArr2.length);
                byte[] bArr3 = new byte[this.messDigestOTS.getDigestSize()];
                this.leaf = bArr3;
                this.messDigestOTS.doFinal(bArr3, 0);
                return;
            }
            if (i3 == 0 || this.f1538j == this.two_power_w - 1) {
                this.f1537i = i3 + 1;
                this.f1538j = 0;
                this.privateKeyOTS = this.gmssRandom.nextSeed(this.seed);
            } else {
                Digest digest2 = this.messDigestOTS;
                byte[] bArr4 = this.privateKeyOTS;
                digest2.update(bArr4, 0, bArr4.length);
                this.privateKeyOTS = bArr;
                this.messDigestOTS.doFinal(bArr, 0);
                int i4 = this.f1538j + 1;
                this.f1538j = i4;
                if (i4 == this.two_power_w - 1) {
                    byte[] bArr5 = this.privateKeyOTS;
                    byte[] bArr6 = this.concHashs;
                    int i5 = this.mdsize;
                    System.arraycopy(bArr5, 0, bArr6, (this.f1537i - 1) * i5, i5);
                }
            }
        }
        throw new IllegalStateException("unable to updateLeaf in steps: " + this.steps + " " + this.f1537i + " " + this.f1538j);
    }

    public byte[] getLeaf() {
        return Arrays.clone(this.leaf);
    }

    public byte[][] getStatByte() {
        return new byte[][]{this.privateKeyOTS, this.seed, this.concHashs, this.leaf};
    }

    public int[] getStatInt() {
        return new int[]{this.f1537i, this.f1538j, this.steps, this.f1539w};
    }

    public void initLeafCalc(byte[] bArr) {
        this.f1537i = 0;
        this.f1538j = 0;
        byte[] bArr2 = new byte[this.mdsize];
        System.arraycopy(bArr, 0, bArr2, 0, this.seed.length);
        this.seed = this.gmssRandom.nextSeed(bArr2);
    }

    public GMSSLeaf nextLeaf() {
        GMSSLeaf gMSSLeaf = new GMSSLeaf(this);
        gMSSLeaf.updateLeafCalc();
        return gMSSLeaf;
    }

    public String toString() {
        StringBuilder m22r;
        String str = BuildConfig.FLAVOR;
        for (int i2 = 0; i2 < 4; i2++) {
            str = AbstractC0000a.m17m(AbstractC0000a.m20p(str), getStatInt()[i2], " ");
        }
        StringBuilder m22r2 = AbstractC0000a.m22r(str, " ");
        m22r2.append(this.mdsize);
        m22r2.append(" ");
        m22r2.append(this.keysize);
        m22r2.append(" ");
        String m17m = AbstractC0000a.m17m(m22r2, this.two_power_w, " ");
        byte[][] statByte = getStatByte();
        for (int i3 = 0; i3 < 4; i3++) {
            if (statByte[i3] != null) {
                m22r = AbstractC0000a.m20p(m17m);
                m22r.append(new String(Hex.encode(statByte[i3])));
                m22r.append(" ");
            } else {
                m22r = AbstractC0000a.m22r(m17m, "null ");
            }
            m17m = m22r.toString();
        }
        return m17m;
    }

    public GMSSLeaf(Digest digest, int i2, int i3, byte[] bArr) {
        this.f1539w = i2;
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        this.mdsize = this.messDigestOTS.getDigestSize();
        double d2 = i2;
        this.keysize = ((int) Math.ceil((r7 << 3) / d2)) + ((int) Math.ceil(getLog((r7 << i2) + 1) / d2));
        this.two_power_w = 1 << i2;
        this.steps = (int) Math.ceil(((((r8 - 1) * r7) + 1) + r7) / i3);
        int i4 = this.mdsize;
        this.seed = new byte[i4];
        this.leaf = new byte[i4];
        this.privateKeyOTS = new byte[i4];
        this.concHashs = new byte[i4 * this.keysize];
        initLeafCalc(bArr);
    }

    public GMSSLeaf(Digest digest, byte[][] bArr, int[] iArr) {
        this.f1537i = iArr[0];
        this.f1538j = iArr[1];
        this.steps = iArr[2];
        this.f1539w = iArr[3];
        this.messDigestOTS = digest;
        this.gmssRandom = new GMSSRandom(digest);
        this.mdsize = this.messDigestOTS.getDigestSize();
        this.keysize = ((int) Math.ceil((r9 << 3) / this.f1539w)) + ((int) Math.ceil(getLog((r9 << this.f1539w) + 1) / this.f1539w));
        this.two_power_w = 1 << this.f1539w;
        this.privateKeyOTS = bArr[0];
        this.seed = bArr[1];
        this.concHashs = bArr[2];
        this.leaf = bArr[3];
    }

    private GMSSLeaf(GMSSLeaf gMSSLeaf) {
        this.messDigestOTS = gMSSLeaf.messDigestOTS;
        this.mdsize = gMSSLeaf.mdsize;
        this.keysize = gMSSLeaf.keysize;
        this.gmssRandom = gMSSLeaf.gmssRandom;
        this.leaf = Arrays.clone(gMSSLeaf.leaf);
        this.concHashs = Arrays.clone(gMSSLeaf.concHashs);
        this.f1537i = gMSSLeaf.f1537i;
        this.f1538j = gMSSLeaf.f1538j;
        this.two_power_w = gMSSLeaf.two_power_w;
        this.f1539w = gMSSLeaf.f1539w;
        this.steps = gMSSLeaf.steps;
        this.seed = Arrays.clone(gMSSLeaf.seed);
        this.privateKeyOTS = Arrays.clone(gMSSLeaf.privateKeyOTS);
    }
}
