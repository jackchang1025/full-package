package org.bouncycastle.pqc.crypto.lms;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
public class LMOtsParameters {
    public static final int reserved = 0;
    public static final LMOtsParameters sha256_n32_w1;
    public static final LMOtsParameters sha256_n32_w2;
    public static final LMOtsParameters sha256_n32_w4;
    public static final LMOtsParameters sha256_n32_w8;
    private static final Map<Object, LMOtsParameters> suppliers;
    private final ASN1ObjectIdentifier digestOID;
    private final int ls;

    /* renamed from: n */
    private final int f1550n;

    /* renamed from: p */
    private final int f1551p;
    private final int sigLen;
    private final int type;

    /* renamed from: w */
    private final int f1552w;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_sha256;
        sha256_n32_w1 = new LMOtsParameters(1, 32, 1, 265, 7, 8516, aSN1ObjectIdentifier);
        sha256_n32_w2 = new LMOtsParameters(2, 32, 2, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, 6, 4292, aSN1ObjectIdentifier);
        sha256_n32_w4 = new LMOtsParameters(3, 32, 4, 67, 4, 2180, aSN1ObjectIdentifier);
        sha256_n32_w8 = new LMOtsParameters(4, 32, 8, 34, 0, 1124, aSN1ObjectIdentifier);
        suppliers = new HashMap<Object, LMOtsParameters>() { // from class: org.bouncycastle.pqc.crypto.lms.LMOtsParameters.1
            {
                LMOtsParameters lMOtsParameters = LMOtsParameters.sha256_n32_w1;
                put(Integer.valueOf(lMOtsParameters.type), lMOtsParameters);
                LMOtsParameters lMOtsParameters2 = LMOtsParameters.sha256_n32_w2;
                put(Integer.valueOf(lMOtsParameters2.type), lMOtsParameters2);
                LMOtsParameters lMOtsParameters3 = LMOtsParameters.sha256_n32_w4;
                put(Integer.valueOf(lMOtsParameters3.type), lMOtsParameters3);
                LMOtsParameters lMOtsParameters4 = LMOtsParameters.sha256_n32_w8;
                put(Integer.valueOf(lMOtsParameters4.type), lMOtsParameters4);
            }
        };
    }

    public LMOtsParameters(int i2, int i3, int i4, int i5, int i6, int i7, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.type = i2;
        this.f1550n = i3;
        this.f1552w = i4;
        this.f1551p = i5;
        this.ls = i6;
        this.sigLen = i7;
        this.digestOID = aSN1ObjectIdentifier;
    }

    public static LMOtsParameters getParametersForType(int i2) {
        return suppliers.get(Integer.valueOf(i2));
    }

    public ASN1ObjectIdentifier getDigestOID() {
        return this.digestOID;
    }

    public int getLs() {
        return this.ls;
    }

    public int getN() {
        return this.f1550n;
    }

    public int getP() {
        return this.f1551p;
    }

    public int getSigLen() {
        return this.sigLen;
    }

    public int getType() {
        return this.type;
    }

    public int getW() {
        return this.f1552w;
    }
}
