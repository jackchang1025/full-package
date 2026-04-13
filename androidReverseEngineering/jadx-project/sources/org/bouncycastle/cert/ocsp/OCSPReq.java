package org.bouncycastle.cert.ocsp;

import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Exception;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ocsp.OCSPRequest;
import org.bouncycastle.asn1.ocsp.Request;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.ContentVerifierProvider;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class OCSPReq {
    private static final X509CertificateHolder[] EMPTY_CERTS = new X509CertificateHolder[0];
    private Extensions extensions;
    private OCSPRequest req;

    private OCSPReq(ASN1InputStream aSN1InputStream) {
        try {
            OCSPRequest oCSPRequest = OCSPRequest.getInstance(aSN1InputStream.readObject());
            this.req = oCSPRequest;
            if (oCSPRequest == null) {
                throw new CertIOException("malformed request: no request data found");
            }
            this.extensions = oCSPRequest.getTbsRequest().getRequestExtensions();
        } catch (ClassCastException e2) {
            throw new CertIOException("malformed request: " + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new CertIOException("malformed request: " + e3.getMessage(), e3);
        } catch (ASN1Exception e4) {
            throw new CertIOException("malformed request: " + e4.getMessage(), e4);
        }
    }

    public X509CertificateHolder[] getCerts() {
        ASN1Sequence certs;
        if (this.req.getOptionalSignature() != null && (certs = this.req.getOptionalSignature().getCerts()) != null) {
            int size = certs.size();
            X509CertificateHolder[] x509CertificateHolderArr = new X509CertificateHolder[size];
            for (int i2 = 0; i2 != size; i2++) {
                x509CertificateHolderArr[i2] = new X509CertificateHolder(Certificate.getInstance(certs.getObjectAt(i2)));
            }
            return x509CertificateHolderArr;
        }
        return EMPTY_CERTS;
    }

    public Set getCriticalExtensionOIDs() {
        return OCSPUtils.getCriticalExtensionOIDs(this.extensions);
    }

    public byte[] getEncoded() {
        return this.req.getEncoded();
    }

    public Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extensions extensions = this.extensions;
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    public List getExtensionOIDs() {
        return OCSPUtils.getExtensionOIDs(this.extensions);
    }

    public Set getNonCriticalExtensionOIDs() {
        return OCSPUtils.getNonCriticalExtensionOIDs(this.extensions);
    }

    public Req[] getRequestList() {
        ASN1Sequence requestList = this.req.getTbsRequest().getRequestList();
        int size = requestList.size();
        Req[] reqArr = new Req[size];
        for (int i2 = 0; i2 != size; i2++) {
            reqArr[i2] = new Req(Request.getInstance(requestList.getObjectAt(i2)));
        }
        return reqArr;
    }

    public GeneralName getRequestorName() {
        return GeneralName.getInstance(this.req.getTbsRequest().getRequestorName());
    }

    public byte[] getSignature() {
        if (isSigned()) {
            return this.req.getOptionalSignature().getSignature().getOctets();
        }
        return null;
    }

    public ASN1ObjectIdentifier getSignatureAlgOID() {
        if (isSigned()) {
            return this.req.getOptionalSignature().getSignatureAlgorithm().getAlgorithm();
        }
        return null;
    }

    public int getVersionNumber() {
        return this.req.getTbsRequest().getVersion().intValueExact() + 1;
    }

    public boolean hasExtensions() {
        return this.extensions != null;
    }

    public boolean isSignatureValid(ContentVerifierProvider contentVerifierProvider) {
        if (!isSigned()) {
            throw new OCSPException("attempt to verify signature on unsigned object");
        }
        try {
            ContentVerifier contentVerifier = contentVerifierProvider.get(this.req.getOptionalSignature().getSignatureAlgorithm());
            contentVerifier.getOutputStream().write(this.req.getTbsRequest().getEncoded(ASN1Encoding.DER));
            return contentVerifier.verify(getSignature());
        } catch (Exception e2) {
            throw new OCSPException(AbstractC0000a.m14j("exception processing signature: ", e2), e2);
        }
    }

    public boolean isSigned() {
        return this.req.getOptionalSignature() != null;
    }

    public OCSPReq(OCSPRequest oCSPRequest) {
        this.req = oCSPRequest;
        this.extensions = oCSPRequest.getTbsRequest().getRequestExtensions();
    }

    public OCSPReq(byte[] bArr) {
        this(new ASN1InputStream(bArr));
    }
}
