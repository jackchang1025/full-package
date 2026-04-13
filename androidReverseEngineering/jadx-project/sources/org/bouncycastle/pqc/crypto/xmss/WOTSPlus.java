package org.bouncycastle.pqc.crypto.xmss;

import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.pqc.crypto.xmss.OTSHashAddress;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
final class WOTSPlus {
    private final KeyedHashFunctions khf;
    private final WOTSPlusParameters params;
    private byte[] publicSeed;
    private byte[] secretKeySeed;

    public WOTSPlus(WOTSPlusParameters wOTSPlusParameters) {
        if (wOTSPlusParameters == null) {
            throw new NullPointerException("params == null");
        }
        this.params = wOTSPlusParameters;
        int treeDigestSize = wOTSPlusParameters.getTreeDigestSize();
        this.khf = new KeyedHashFunctions(wOTSPlusParameters.getTreeDigest(), treeDigestSize);
        this.secretKeySeed = new byte[treeDigestSize];
        this.publicSeed = new byte[treeDigestSize];
    }

    private byte[] chain(byte[] bArr, int i2, int i3, OTSHashAddress oTSHashAddress) {
        int treeDigestSize = this.params.getTreeDigestSize();
        if (bArr == null) {
            throw new NullPointerException("startHash == null");
        }
        if (bArr.length != treeDigestSize) {
            throw new IllegalArgumentException(AbstractC0000a.m12h("startHash needs to be ", treeDigestSize, "bytes"));
        }
        if (oTSHashAddress == null) {
            throw new NullPointerException("otsHashAddress == null");
        }
        if (oTSHashAddress.toByteArray() == null) {
            throw new NullPointerException("otsHashAddress byte array == null");
        }
        int i4 = i2 + i3;
        if (i4 > this.params.getWinternitzParameter() - 1) {
            throw new IllegalArgumentException("max chain length must not be greater than w");
        }
        if (i3 == 0) {
            return bArr;
        }
        byte[] chain = chain(bArr, i2, i3 - 1, oTSHashAddress);
        OTSHashAddress oTSHashAddress2 = (OTSHashAddress) new OTSHashAddress.Builder().withLayerAddress(oTSHashAddress.getLayerAddress()).withTreeAddress(oTSHashAddress.getTreeAddress()).withOTSAddress(oTSHashAddress.getOTSAddress()).withChainAddress(oTSHashAddress.getChainAddress()).withHashAddress(i4 - 1).withKeyAndMask(0).build();
        byte[] PRF = this.khf.PRF(this.publicSeed, oTSHashAddress2.toByteArray());
        byte[] PRF2 = this.khf.PRF(this.publicSeed, ((OTSHashAddress) new OTSHashAddress.Builder().withLayerAddress(oTSHashAddress2.getLayerAddress()).withTreeAddress(oTSHashAddress2.getTreeAddress()).withOTSAddress(oTSHashAddress2.getOTSAddress()).withChainAddress(oTSHashAddress2.getChainAddress()).withHashAddress(oTSHashAddress2.getHashAddress()).withKeyAndMask(1).build()).toByteArray());
        byte[] bArr2 = new byte[treeDigestSize];
        for (int i5 = 0; i5 < treeDigestSize; i5++) {
            bArr2[i5] = (byte) (chain[i5] ^ PRF2[i5]);
        }
        return this.khf.m1232F(PRF, bArr2);
    }

    private List<Integer> convertToBaseW(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            throw new NullPointerException("msg == null");
        }
        if (i2 != 4 && i2 != 16) {
            throw new IllegalArgumentException("w needs to be 4 or 16");
        }
        int log2 = XMSSUtil.log2(i2);
        if (i3 > (bArr.length * 8) / log2) {
            throw new IllegalArgumentException("outLength too big");
        }
        ArrayList arrayList = new ArrayList();
        for (int i4 : bArr) {
            for (int i5 = 8 - log2; i5 >= 0; i5 -= log2) {
                arrayList.add(Integer.valueOf((i4 >> i5) & (i2 - 1)));
                if (arrayList.size() == i3) {
                    return arrayList;
                }
            }
        }
        return arrayList;
    }

    private byte[] expandSecretKeySeed(int i2) {
        if (i2 < 0 || i2 >= this.params.getLen()) {
            throw new IllegalArgumentException("index out of bounds");
        }
        return this.khf.PRF(this.secretKeySeed, XMSSUtil.toBytesBigEndian(i2, 32));
    }

    public KeyedHashFunctions getKhf() {
        return this.khf;
    }

    public WOTSPlusParameters getParams() {
        return this.params;
    }

    public WOTSPlusPrivateKeyParameters getPrivateKey() {
        int len = this.params.getLen();
        byte[][] bArr = new byte[len][];
        for (int i2 = 0; i2 < len; i2++) {
            bArr[i2] = expandSecretKeySeed(i2);
        }
        return new WOTSPlusPrivateKeyParameters(this.params, bArr);
    }

    public WOTSPlusPublicKeyParameters getPublicKey(OTSHashAddress oTSHashAddress) {
        if (oTSHashAddress == null) {
            throw new NullPointerException("otsHashAddress == null");
        }
        byte[][] bArr = new byte[this.params.getLen()][];
        for (int i2 = 0; i2 < this.params.getLen(); i2++) {
            oTSHashAddress = (OTSHashAddress) new OTSHashAddress.Builder().withLayerAddress(oTSHashAddress.getLayerAddress()).withTreeAddress(oTSHashAddress.getTreeAddress()).withOTSAddress(oTSHashAddress.getOTSAddress()).withChainAddress(i2).withHashAddress(oTSHashAddress.getHashAddress()).withKeyAndMask(oTSHashAddress.getKeyAndMask()).build();
            bArr[i2] = chain(expandSecretKeySeed(i2), 0, this.params.getWinternitzParameter() - 1, oTSHashAddress);
        }
        return new WOTSPlusPublicKeyParameters(this.params, bArr);
    }

    public WOTSPlusPublicKeyParameters getPublicKeyFromSignature(byte[] bArr, WOTSPlusSignature wOTSPlusSignature, OTSHashAddress oTSHashAddress) {
        if (bArr == null) {
            throw new NullPointerException("messageDigest == null");
        }
        if (bArr.length != this.params.getTreeDigestSize()) {
            throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
        }
        if (wOTSPlusSignature == null) {
            throw new NullPointerException("signature == null");
        }
        if (oTSHashAddress == null) {
            throw new NullPointerException("otsHashAddress == null");
        }
        List<Integer> convertToBaseW = convertToBaseW(bArr, this.params.getWinternitzParameter(), this.params.getLen1());
        int i2 = 0;
        for (int i3 = 0; i3 < this.params.getLen1(); i3++) {
            i2 += (this.params.getWinternitzParameter() - 1) - convertToBaseW.get(i3).intValue();
        }
        convertToBaseW.addAll(convertToBaseW(XMSSUtil.toBytesBigEndian(i2 << (8 - ((XMSSUtil.log2(this.params.getWinternitzParameter()) * this.params.getLen2()) % 8)), (int) Math.ceil((XMSSUtil.log2(this.params.getWinternitzParameter()) * this.params.getLen2()) / 8.0d)), this.params.getWinternitzParameter(), this.params.getLen2()));
        byte[][] bArr2 = new byte[this.params.getLen()][];
        for (int i4 = 0; i4 < this.params.getLen(); i4++) {
            oTSHashAddress = (OTSHashAddress) new OTSHashAddress.Builder().withLayerAddress(oTSHashAddress.getLayerAddress()).withTreeAddress(oTSHashAddress.getTreeAddress()).withOTSAddress(oTSHashAddress.getOTSAddress()).withChainAddress(i4).withHashAddress(oTSHashAddress.getHashAddress()).withKeyAndMask(oTSHashAddress.getKeyAndMask()).build();
            bArr2[i4] = chain(wOTSPlusSignature.toByteArray()[i4], convertToBaseW.get(i4).intValue(), (this.params.getWinternitzParameter() - 1) - convertToBaseW.get(i4).intValue(), oTSHashAddress);
        }
        return new WOTSPlusPublicKeyParameters(this.params, bArr2);
    }

    public byte[] getPublicSeed() {
        return Arrays.clone(this.publicSeed);
    }

    public byte[] getSecretKeySeed() {
        return Arrays.clone(this.secretKeySeed);
    }

    public byte[] getWOTSPlusSecretKey(byte[] bArr, OTSHashAddress oTSHashAddress) {
        return this.khf.PRF(bArr, ((OTSHashAddress) new OTSHashAddress.Builder().withLayerAddress(oTSHashAddress.getLayerAddress()).withTreeAddress(oTSHashAddress.getTreeAddress()).withOTSAddress(oTSHashAddress.getOTSAddress()).build()).toByteArray());
    }

    public void importKeys(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            throw new NullPointerException("secretKeySeed == null");
        }
        if (bArr.length != this.params.getTreeDigestSize()) {
            throw new IllegalArgumentException("size of secretKeySeed needs to be equal to size of digest");
        }
        if (bArr2 == null) {
            throw new NullPointerException("publicSeed == null");
        }
        if (bArr2.length != this.params.getTreeDigestSize()) {
            throw new IllegalArgumentException("size of publicSeed needs to be equal to size of digest");
        }
        this.secretKeySeed = bArr;
        this.publicSeed = bArr2;
    }

    public WOTSPlusSignature sign(byte[] bArr, OTSHashAddress oTSHashAddress) {
        if (bArr == null) {
            throw new NullPointerException("messageDigest == null");
        }
        if (bArr.length != this.params.getTreeDigestSize()) {
            throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
        }
        if (oTSHashAddress == null) {
            throw new NullPointerException("otsHashAddress == null");
        }
        List<Integer> convertToBaseW = convertToBaseW(bArr, this.params.getWinternitzParameter(), this.params.getLen1());
        int i2 = 0;
        for (int i3 = 0; i3 < this.params.getLen1(); i3++) {
            i2 += (this.params.getWinternitzParameter() - 1) - convertToBaseW.get(i3).intValue();
        }
        convertToBaseW.addAll(convertToBaseW(XMSSUtil.toBytesBigEndian(i2 << (8 - ((XMSSUtil.log2(this.params.getWinternitzParameter()) * this.params.getLen2()) % 8)), (int) Math.ceil((XMSSUtil.log2(this.params.getWinternitzParameter()) * this.params.getLen2()) / 8.0d)), this.params.getWinternitzParameter(), this.params.getLen2()));
        byte[][] bArr2 = new byte[this.params.getLen()][];
        for (int i4 = 0; i4 < this.params.getLen(); i4++) {
            oTSHashAddress = (OTSHashAddress) new OTSHashAddress.Builder().withLayerAddress(oTSHashAddress.getLayerAddress()).withTreeAddress(oTSHashAddress.getTreeAddress()).withOTSAddress(oTSHashAddress.getOTSAddress()).withChainAddress(i4).withHashAddress(oTSHashAddress.getHashAddress()).withKeyAndMask(oTSHashAddress.getKeyAndMask()).build();
            bArr2[i4] = chain(expandSecretKeySeed(i4), 0, convertToBaseW.get(i4).intValue(), oTSHashAddress);
        }
        return new WOTSPlusSignature(this.params, bArr2);
    }
}
