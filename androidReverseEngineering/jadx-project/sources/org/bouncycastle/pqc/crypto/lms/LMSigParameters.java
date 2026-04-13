package org.bouncycastle.pqc.crypto.lms;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;

/* loaded from: classes.dex */
public class LMSigParameters {
    public static final LMSigParameters lms_sha256_n32_h10;
    public static final LMSigParameters lms_sha256_n32_h15;
    public static final LMSigParameters lms_sha256_n32_h20;
    public static final LMSigParameters lms_sha256_n32_h25;
    public static final LMSigParameters lms_sha256_n32_h5;
    private static Map<Object, LMSigParameters> paramBuilders;
    private final ASN1ObjectIdentifier digestOid;

    /* renamed from: h */
    private final int f1566h;

    /* renamed from: m */
    private final int f1567m;
    private final int type;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_sha256;
        lms_sha256_n32_h5 = new LMSigParameters(5, 32, 5, aSN1ObjectIdentifier);
        lms_sha256_n32_h10 = new LMSigParameters(6, 32, 10, aSN1ObjectIdentifier);
        lms_sha256_n32_h15 = new LMSigParameters(7, 32, 15, aSN1ObjectIdentifier);
        lms_sha256_n32_h20 = new LMSigParameters(8, 32, 20, aSN1ObjectIdentifier);
        lms_sha256_n32_h25 = new LMSigParameters(9, 32, 25, aSN1ObjectIdentifier);
        paramBuilders = new HashMap<Object, LMSigParameters>() { // from class: org.bouncycastle.pqc.crypto.lms.LMSigParameters.1
            {
                LMSigParameters lMSigParameters = LMSigParameters.lms_sha256_n32_h5;
                put(Integer.valueOf(lMSigParameters.type), lMSigParameters);
                LMSigParameters lMSigParameters2 = LMSigParameters.lms_sha256_n32_h10;
                put(Integer.valueOf(lMSigParameters2.type), lMSigParameters2);
                LMSigParameters lMSigParameters3 = LMSigParameters.lms_sha256_n32_h15;
                put(Integer.valueOf(lMSigParameters3.type), lMSigParameters3);
                LMSigParameters lMSigParameters4 = LMSigParameters.lms_sha256_n32_h20;
                put(Integer.valueOf(lMSigParameters4.type), lMSigParameters4);
                LMSigParameters lMSigParameters5 = LMSigParameters.lms_sha256_n32_h25;
                put(Integer.valueOf(lMSigParameters5.type), lMSigParameters5);
            }
        };
    }

    public LMSigParameters(int i2, int i3, int i4, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.type = i2;
        this.f1567m = i3;
        this.f1566h = i4;
        this.digestOid = aSN1ObjectIdentifier;
    }

    public static LMSigParameters getParametersForType(int i2) {
        return paramBuilders.get(Integer.valueOf(i2));
    }

    public ASN1ObjectIdentifier getDigestOID() {
        return this.digestOid;
    }

    public int getH() {
        return this.f1566h;
    }

    public int getM() {
        return this.f1567m;
    }

    public int getType() {
        return this.type;
    }
}
