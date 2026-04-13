package org.bouncycastle.pqc.crypto.xmss;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Integers;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public final class XMSSMTParameters {
    private static final Map<Integer, XMSSMTParameters> paramsLookupTable;
    private final int height;
    private final int layers;
    private final XMSSOid oid;
    private final XMSSParameters xmssParams;

    static {
        HashMap hashMap = new HashMap();
        Integer valueOf = Integers.valueOf(1);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_sha256;
        hashMap.put(valueOf, new XMSSMTParameters(20, 2, aSN1ObjectIdentifier));
        AbstractC0413b.m1027u(20, 4, aSN1ObjectIdentifier, hashMap, Integers.valueOf(2));
        AbstractC0413b.m1027u(40, 2, aSN1ObjectIdentifier, hashMap, Integers.valueOf(3));
        AbstractC0413b.m1027u(40, 4, aSN1ObjectIdentifier, hashMap, Integers.valueOf(4));
        AbstractC0413b.m1027u(40, 8, aSN1ObjectIdentifier, hashMap, Integers.valueOf(5));
        AbstractC0413b.m1027u(60, 3, aSN1ObjectIdentifier, hashMap, Integers.valueOf(6));
        AbstractC0413b.m1027u(60, 6, aSN1ObjectIdentifier, hashMap, Integers.valueOf(7));
        AbstractC0413b.m1027u(60, 12, aSN1ObjectIdentifier, hashMap, Integers.valueOf(8));
        Integer valueOf2 = Integers.valueOf(9);
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.id_sha512;
        hashMap.put(valueOf2, new XMSSMTParameters(20, 2, aSN1ObjectIdentifier2));
        AbstractC0413b.m1027u(20, 4, aSN1ObjectIdentifier2, hashMap, Integers.valueOf(10));
        AbstractC0413b.m1027u(40, 2, aSN1ObjectIdentifier2, hashMap, Integers.valueOf(11));
        AbstractC0413b.m1027u(40, 4, aSN1ObjectIdentifier2, hashMap, Integers.valueOf(12));
        AbstractC0413b.m1027u(40, 8, aSN1ObjectIdentifier2, hashMap, Integers.valueOf(13));
        AbstractC0413b.m1027u(60, 3, aSN1ObjectIdentifier2, hashMap, Integers.valueOf(14));
        AbstractC0413b.m1027u(60, 6, aSN1ObjectIdentifier2, hashMap, Integers.valueOf(15));
        AbstractC0413b.m1027u(60, 12, aSN1ObjectIdentifier2, hashMap, Integers.valueOf(16));
        Integer valueOf3 = Integers.valueOf(17);
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_shake128;
        hashMap.put(valueOf3, new XMSSMTParameters(20, 2, aSN1ObjectIdentifier3));
        AbstractC0413b.m1027u(20, 4, aSN1ObjectIdentifier3, hashMap, Integers.valueOf(18));
        AbstractC0413b.m1027u(40, 2, aSN1ObjectIdentifier3, hashMap, Integers.valueOf(19));
        AbstractC0413b.m1027u(40, 4, aSN1ObjectIdentifier3, hashMap, Integers.valueOf(20));
        AbstractC0413b.m1027u(40, 8, aSN1ObjectIdentifier3, hashMap, Integers.valueOf(21));
        AbstractC0413b.m1027u(60, 3, aSN1ObjectIdentifier3, hashMap, Integers.valueOf(22));
        AbstractC0413b.m1027u(60, 6, aSN1ObjectIdentifier3, hashMap, Integers.valueOf(23));
        AbstractC0413b.m1027u(60, 12, aSN1ObjectIdentifier3, hashMap, Integers.valueOf(24));
        Integer valueOf4 = Integers.valueOf(25);
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.id_shake256;
        hashMap.put(valueOf4, new XMSSMTParameters(20, 2, aSN1ObjectIdentifier4));
        AbstractC0413b.m1027u(20, 4, aSN1ObjectIdentifier4, hashMap, Integers.valueOf(26));
        AbstractC0413b.m1027u(40, 2, aSN1ObjectIdentifier4, hashMap, Integers.valueOf(27));
        AbstractC0413b.m1027u(40, 4, aSN1ObjectIdentifier4, hashMap, Integers.valueOf(28));
        AbstractC0413b.m1027u(40, 8, aSN1ObjectIdentifier4, hashMap, Integers.valueOf(29));
        AbstractC0413b.m1027u(60, 3, aSN1ObjectIdentifier4, hashMap, Integers.valueOf(30));
        AbstractC0413b.m1027u(60, 6, aSN1ObjectIdentifier4, hashMap, Integers.valueOf(31));
        AbstractC0413b.m1027u(60, 12, aSN1ObjectIdentifier4, hashMap, Integers.valueOf(32));
        paramsLookupTable = Collections.unmodifiableMap(hashMap);
    }

    public XMSSMTParameters(int i2, int i3, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.height = i2;
        this.layers = i3;
        this.xmssParams = new XMSSParameters(xmssTreeHeight(i2, i3), aSN1ObjectIdentifier);
        this.oid = DefaultXMSSMTOid.lookup(getTreeDigest(), getTreeDigestSize(), getWinternitzParameter(), getLen(), getHeight(), i3);
    }

    public static XMSSMTParameters lookupByOID(int i2) {
        return paramsLookupTable.get(Integers.valueOf(i2));
    }

    private static int xmssTreeHeight(int i2, int i3) {
        if (i2 < 2) {
            throw new IllegalArgumentException("totalHeight must be > 1");
        }
        if (i2 % i3 != 0) {
            throw new IllegalArgumentException("layers must divide totalHeight without remainder");
        }
        int i4 = i2 / i3;
        if (i4 != 1) {
            return i4;
        }
        throw new IllegalArgumentException("height / layers must be greater than 1");
    }

    public int getHeight() {
        return this.height;
    }

    public int getLayers() {
        return this.layers;
    }

    public int getLen() {
        return this.xmssParams.getLen();
    }

    public XMSSOid getOid() {
        return this.oid;
    }

    public String getTreeDigest() {
        return this.xmssParams.getTreeDigest();
    }

    public ASN1ObjectIdentifier getTreeDigestOID() {
        return this.xmssParams.getTreeDigestOID();
    }

    public int getTreeDigestSize() {
        return this.xmssParams.getTreeDigestSize();
    }

    public WOTSPlus getWOTSPlus() {
        return this.xmssParams.getWOTSPlus();
    }

    public int getWinternitzParameter() {
        return this.xmssParams.getWinternitzParameter();
    }

    public XMSSParameters getXMSSParameters() {
        return this.xmssParams;
    }

    public XMSSMTParameters(int i2, int i3, Digest digest) {
        this(i2, i3, DigestUtil.getDigestOID(digest.getAlgorithmName()));
    }
}
