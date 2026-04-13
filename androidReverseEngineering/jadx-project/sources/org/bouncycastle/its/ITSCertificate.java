package org.bouncycastle.its;

import java.io.OutputStream;
import org.bouncycastle.its.operator.ECDSAEncoder;
import org.bouncycastle.its.operator.ITSContentVerifierProvider;
import org.bouncycastle.oer.OEREncoder;
import org.bouncycastle.oer.its.Certificate;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.template.IEEE1609dot2;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.util.Encodable;

/* loaded from: classes.dex */
public class ITSCertificate implements Encodable {
    private final Certificate certificate;

    public ITSCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return OEREncoder.toByteArray(this.certificate.getCertificateBase(), IEEE1609dot2.certificate);
    }

    public IssuerIdentifier getIssuer() {
        return this.certificate.getCertificateBase().getIssuer();
    }

    public ITSPublicEncryptionKey getPublicEncryptionKey() {
        PublicEncryptionKey encryptionKey = this.certificate.getCertificateBase().getToBeSignedCertificate().getEncryptionKey();
        if (encryptionKey != null) {
            return new ITSPublicEncryptionKey(encryptionKey);
        }
        return null;
    }

    public ITSValidityPeriod getValidityPeriod() {
        return new ITSValidityPeriod(this.certificate.getCertificateBase().getToBeSignedCertificate().getValidityPeriod());
    }

    public boolean isSignatureValid(ITSContentVerifierProvider iTSContentVerifierProvider) {
        ContentVerifier contentVerifier = iTSContentVerifierProvider.get(this.certificate.getCertificateBase().getSignature().getChoice());
        OutputStream outputStream = contentVerifier.getOutputStream();
        outputStream.write(OEREncoder.toByteArray(this.certificate.getCertificateBase().getToBeSignedCertificate(), IEEE1609dot2.tbsCertificate));
        outputStream.close();
        return contentVerifier.verify(ECDSAEncoder.toX962(this.certificate.getCertificateBase().getSignature()));
    }

    public Certificate toASN1Structure() {
        return this.certificate;
    }
}
