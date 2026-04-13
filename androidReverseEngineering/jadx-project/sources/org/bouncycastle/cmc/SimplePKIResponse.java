package org.bouncycastle.cmc;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.util.Encodable;
import org.bouncycastle.util.Store;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SimplePKIResponse implements Encodable {
    private final CMSSignedData certificateResponse;

    public SimplePKIResponse(ContentInfo contentInfo) {
        try {
            CMSSignedData cMSSignedData = new CMSSignedData(contentInfo);
            this.certificateResponse = cMSSignedData;
            if (cMSSignedData.getSignerInfos().size() != 0) {
                throw new CMCException("malformed response: SignerInfo structures found");
            }
            if (cMSSignedData.getSignedContent() != null) {
                throw new CMCException("malformed response: Signed Content found");
            }
        } catch (CMSException e2) {
            throw new CMCException("malformed response: " + e2.getMessage(), e2);
        }
    }

    private static ContentInfo parseBytes(byte[] bArr) {
        try {
            return ContentInfo.getInstance(ASN1Primitive.fromByteArray(bArr));
        } catch (Exception e2) {
            throw new CMCException(AbstractC0000a.m9e(e2, new StringBuilder("malformed data: ")), e2);
        }
    }

    public Store<X509CRLHolder> getCRLs() {
        return this.certificateResponse.getCRLs();
    }

    public Store<X509CertificateHolder> getCertificates() {
        return this.certificateResponse.getCertificates();
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return this.certificateResponse.getEncoded();
    }

    public SimplePKIResponse(byte[] bArr) {
        this(parseBytes(bArr));
    }
}
