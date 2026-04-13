package org.bouncycastle.cert.cmp;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cmp.PKIBody;
import org.bouncycastle.asn1.cmp.PKIHeader;
import org.bouncycastle.asn1.cmp.PKIMessage;
import org.bouncycastle.cert.CertIOException;

/* loaded from: classes.dex */
public class GeneralPKIMessage {
    private final PKIMessage pkiMessage;

    public GeneralPKIMessage(PKIMessage pKIMessage) {
        this.pkiMessage = pKIMessage;
    }

    private static PKIMessage parseBytes(byte[] bArr) {
        try {
            return PKIMessage.getInstance(ASN1Primitive.fromByteArray(bArr));
        } catch (ClassCastException e2) {
            throw new CertIOException("malformed data: " + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new CertIOException("malformed data: " + e3.getMessage(), e3);
        }
    }

    public PKIBody getBody() {
        return this.pkiMessage.getBody();
    }

    public PKIHeader getHeader() {
        return this.pkiMessage.getHeader();
    }

    public boolean hasProtection() {
        return this.pkiMessage.getHeader().getProtectionAlg() != null;
    }

    public PKIMessage toASN1Structure() {
        return this.pkiMessage;
    }

    public GeneralPKIMessage(byte[] bArr) {
        this(parseBytes(bArr));
    }
}
