package org.bouncycastle.pqc.crypto.xmss;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
public final class XMSSParameters {
    private static final Map<Integer, XMSSParameters> paramsLookupTable;
    private final int height;

    /* renamed from: k */
    private final int f1619k;
    private final XMSSOid oid;
    private final String treeDigest;
    private final ASN1ObjectIdentifier treeDigestOID;
    private final int treeDigestSize;
    private final int winternitzParameter;
    private final WOTSPlusParameters wotsPlusParams;

    static {
        HashMap hashMap = new HashMap();
        Integer valueOf = Integers.valueOf(1);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_sha256;
        hashMap.put(valueOf, new XMSSParameters(10, aSN1ObjectIdentifier));
        hashMap.put(Integers.valueOf(2), new XMSSParameters(16, aSN1ObjectIdentifier));
        hashMap.put(Integers.valueOf(3), new XMSSParameters(20, aSN1ObjectIdentifier));
        Integer valueOf2 = Integers.valueOf(4);
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.id_sha512;
        hashMap.put(valueOf2, new XMSSParameters(10, aSN1ObjectIdentifier2));
        hashMap.put(Integers.valueOf(5), new XMSSParameters(16, aSN1ObjectIdentifier2));
        hashMap.put(Integers.valueOf(6), new XMSSParameters(20, aSN1ObjectIdentifier2));
        Integer valueOf3 = Integers.valueOf(7);
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_shake128;
        hashMap.put(valueOf3, new XMSSParameters(10, aSN1ObjectIdentifier3));
        hashMap.put(Integers.valueOf(8), new XMSSParameters(16, aSN1ObjectIdentifier3));
        hashMap.put(Integers.valueOf(9), new XMSSParameters(20, aSN1ObjectIdentifier3));
        Integer valueOf4 = Integers.valueOf(10);
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.id_shake256;
        hashMap.put(valueOf4, new XMSSParameters(10, aSN1ObjectIdentifier4));
        hashMap.put(Integers.valueOf(11), new XMSSParameters(16, aSN1ObjectIdentifier4));
        hashMap.put(Integers.valueOf(12), new XMSSParameters(20, aSN1ObjectIdentifier4));
        paramsLookupTable = Collections.unmodifiableMap(hashMap);
    }

    public XMSSParameters(int i2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (i2 < 2) {
            throw new IllegalArgumentException("height must be >= 2");
        }
        if (aSN1ObjectIdentifier == null) {
            throw new NullPointerException("digest == null");
        }
        this.height = i2;
        this.f1619k = determineMinK();
        String digestName = DigestUtil.getDigestName(aSN1ObjectIdentifier);
        this.treeDigest = digestName;
        this.treeDigestOID = aSN1ObjectIdentifier;
        WOTSPlusParameters wOTSPlusParameters = new WOTSPlusParameters(aSN1ObjectIdentifier);
        this.wotsPlusParams = wOTSPlusParameters;
        int treeDigestSize = wOTSPlusParameters.getTreeDigestSize();
        this.treeDigestSize = treeDigestSize;
        int winternitzParameter = wOTSPlusParameters.getWinternitzParameter();
        this.winternitzParameter = winternitzParameter;
        this.oid = DefaultXMSSOid.lookup(digestName, treeDigestSize, winternitzParameter, wOTSPlusParameters.getLen(), i2);
    }

    private int determineMinK() {
        int i2 = 2;
        while (true) {
            int i3 = this.height;
            if (i2 > i3) {
                throw new IllegalStateException("should never happen...");
            }
            if ((i3 - i2) % 2 == 0) {
                return i2;
            }
            i2++;
        }
    }

    public static XMSSParameters lookupByOID(int i2) {
        return paramsLookupTable.get(Integers.valueOf(i2));
    }

    public int getHeight() {
        return this.height;
    }

    public int getK() {
        return this.f1619k;
    }

    public int getLen() {
        return this.wotsPlusParams.getLen();
    }

    public XMSSOid getOid() {
        return this.oid;
    }

    public String getTreeDigest() {
        return this.treeDigest;
    }

    public ASN1ObjectIdentifier getTreeDigestOID() {
        return this.treeDigestOID;
    }

    public int getTreeDigestSize() {
        return this.treeDigestSize;
    }

    public WOTSPlus getWOTSPlus() {
        return new WOTSPlus(this.wotsPlusParams);
    }

    public int getWinternitzParameter() {
        return this.winternitzParameter;
    }

    public XMSSParameters(int i2, Digest digest) {
        this(i2, DigestUtil.getDigestOID(digest.getAlgorithmName()));
    }
}
