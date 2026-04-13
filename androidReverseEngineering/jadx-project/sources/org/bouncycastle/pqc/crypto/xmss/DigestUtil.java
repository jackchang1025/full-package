package org.bouncycastle.pqc.crypto.xmss;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
class DigestUtil {
    private static Map<String, ASN1ObjectIdentifier> nameToOid = new HashMap();
    private static Map<ASN1ObjectIdentifier, String> oidToName = new HashMap();

    static {
        Map<String, ASN1ObjectIdentifier> map = nameToOid;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_sha256;
        map.put("SHA-256", aSN1ObjectIdentifier);
        Map<String, ASN1ObjectIdentifier> map2 = nameToOid;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.id_sha512;
        map2.put("SHA-512", aSN1ObjectIdentifier2);
        Map<String, ASN1ObjectIdentifier> map3 = nameToOid;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_shake128;
        map3.put("SHAKE128", aSN1ObjectIdentifier3);
        Map<String, ASN1ObjectIdentifier> map4 = nameToOid;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.id_shake256;
        map4.put("SHAKE256", aSN1ObjectIdentifier4);
        oidToName.put(aSN1ObjectIdentifier, "SHA-256");
        oidToName.put(aSN1ObjectIdentifier2, "SHA-512");
        oidToName.put(aSN1ObjectIdentifier3, "SHAKE128");
        oidToName.put(aSN1ObjectIdentifier4, "SHAKE256");
    }

    public static Digest getDigest(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) NISTObjectIdentifiers.id_sha256)) {
            return new SHA256Digest();
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) NISTObjectIdentifiers.id_sha512)) {
            return new SHA512Digest();
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) NISTObjectIdentifiers.id_shake128)) {
            return new SHAKEDigest(128);
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) NISTObjectIdentifiers.id_shake256)) {
            return new SHAKEDigest(256);
        }
        throw new IllegalArgumentException(AbstractC0413b.m1009c("unrecognized digest OID: ", aSN1ObjectIdentifier));
    }

    public static String getDigestName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = oidToName.get(aSN1ObjectIdentifier);
        if (str != null) {
            return str;
        }
        throw new IllegalArgumentException(AbstractC0413b.m1009c("unrecognized digest oid: ", aSN1ObjectIdentifier));
    }

    public static ASN1ObjectIdentifier getDigestOID(String str) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = nameToOid.get(str);
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        throw new IllegalArgumentException(AbstractC0000a.m15k("unrecognized digest name: ", str));
    }

    public static int getDigestSize(Digest digest) {
        boolean z2 = digest instanceof Xof;
        int digestSize = digest.getDigestSize();
        return z2 ? digestSize * 2 : digestSize;
    }
}
