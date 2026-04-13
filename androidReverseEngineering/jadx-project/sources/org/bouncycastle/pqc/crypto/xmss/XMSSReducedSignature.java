package org.bouncycastle.pqc.crypto.xmss;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class XMSSReducedSignature implements XMSSStoreableObjectInterface {
    private final List<XMSSNode> authPath;
    private final XMSSParameters params;
    private final WOTSPlusSignature wotsPlusSignature;

    public static class Builder {
        private final XMSSParameters params;
        private WOTSPlusSignature wotsPlusSignature = null;
        private List<XMSSNode> authPath = null;
        private byte[] reducedSignature = null;

        public Builder(XMSSParameters xMSSParameters) {
            this.params = xMSSParameters;
        }

        public XMSSReducedSignature build() {
            return new XMSSReducedSignature(this);
        }

        public Builder withAuthPath(List<XMSSNode> list) {
            this.authPath = list;
            return this;
        }

        public Builder withReducedSignature(byte[] bArr) {
            this.reducedSignature = XMSSUtil.cloneArray(bArr);
            return this;
        }

        public Builder withWOTSPlusSignature(WOTSPlusSignature wOTSPlusSignature) {
            this.wotsPlusSignature = wOTSPlusSignature;
            return this;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v10, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r9v3, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.util.List<org.bouncycastle.pqc.crypto.xmss.XMSSNode>] */
    public XMSSReducedSignature(Builder builder) {
        ?? r9;
        XMSSParameters xMSSParameters = builder.params;
        this.params = xMSSParameters;
        if (xMSSParameters == null) {
            throw new NullPointerException("params == null");
        }
        int treeDigestSize = xMSSParameters.getTreeDigestSize();
        int len = xMSSParameters.getWOTSPlus().getParams().getLen();
        int height = xMSSParameters.getHeight();
        byte[] bArr = builder.reducedSignature;
        if (bArr == null) {
            WOTSPlusSignature wOTSPlusSignature = builder.wotsPlusSignature;
            this.wotsPlusSignature = wOTSPlusSignature == null ? new WOTSPlusSignature(xMSSParameters.getWOTSPlus().getParams(), (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, len, treeDigestSize)) : wOTSPlusSignature;
            r9 = builder.authPath;
            if (r9 == 0) {
                r9 = new ArrayList();
            } else if (r9.size() != height) {
                throw new IllegalArgumentException("size of authPath needs to be equal to height of tree");
            }
        } else {
            if (bArr.length != (height * treeDigestSize) + (len * treeDigestSize)) {
                throw new IllegalArgumentException("signature has wrong size");
            }
            byte[][] bArr2 = new byte[len][];
            int i2 = 0;
            for (int i3 = 0; i3 < len; i3++) {
                bArr2[i3] = XMSSUtil.extractBytesAtOffset(bArr, i2, treeDigestSize);
                i2 += treeDigestSize;
            }
            this.wotsPlusSignature = new WOTSPlusSignature(this.params.getWOTSPlus().getParams(), bArr2);
            r9 = new ArrayList();
            for (int i4 = 0; i4 < height; i4++) {
                r9.add(new XMSSNode(i4, XMSSUtil.extractBytesAtOffset(bArr, i2, treeDigestSize)));
                i2 += treeDigestSize;
            }
        }
        this.authPath = r9;
    }

    public List<XMSSNode> getAuthPath() {
        return this.authPath;
    }

    public XMSSParameters getParams() {
        return this.params;
    }

    public WOTSPlusSignature getWOTSPlusSignature() {
        return this.wotsPlusSignature;
    }

    @Override // org.bouncycastle.pqc.crypto.xmss.XMSSStoreableObjectInterface
    public byte[] toByteArray() {
        int treeDigestSize = this.params.getTreeDigestSize();
        byte[] bArr = new byte[(this.params.getHeight() * treeDigestSize) + (this.params.getWOTSPlus().getParams().getLen() * treeDigestSize)];
        int i2 = 0;
        for (byte[] bArr2 : this.wotsPlusSignature.toByteArray()) {
            XMSSUtil.copyBytesAtOffset(bArr, bArr2, i2);
            i2 += treeDigestSize;
        }
        for (int i3 = 0; i3 < this.authPath.size(); i3++) {
            XMSSUtil.copyBytesAtOffset(bArr, this.authPath.get(i3).getValue(), i2);
            i2 += treeDigestSize;
        }
        return bArr;
    }
}
