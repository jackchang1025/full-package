package org.bouncycastle.cert.ocsp;

import java.io.OutputStream;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ocsp.ResponderID;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.operator.DigestCalculator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class RespID {
    public static final AlgorithmIdentifier HASH_SHA1 = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    ResponderID id;

    public RespID(ResponderID responderID) {
        this.id = responderID;
    }

    public boolean equals(Object obj) {
        if (obj instanceof RespID) {
            return this.id.equals(((RespID) obj).id);
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public ResponderID toASN1Primitive() {
        return this.id;
    }

    public RespID(X500Name x500Name) {
        this.id = new ResponderID(x500Name);
    }

    public RespID(SubjectPublicKeyInfo subjectPublicKeyInfo, DigestCalculator digestCalculator) {
        try {
            if (!digestCalculator.getAlgorithmIdentifier().equals(HASH_SHA1)) {
                throw new IllegalArgumentException("only SHA-1 can be used with RespID - found: " + digestCalculator.getAlgorithmIdentifier().getAlgorithm());
            }
            OutputStream outputStream = digestCalculator.getOutputStream();
            outputStream.write(subjectPublicKeyInfo.getPublicKeyData().getBytes());
            outputStream.close();
            this.id = new ResponderID(new DEROctetString(digestCalculator.getDigest()));
        } catch (Exception e2) {
            throw new OCSPException(AbstractC0000a.m14j("problem creating ID: ", e2), e2);
        }
    }
}
