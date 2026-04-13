package org.bouncycastle.its;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.its.operator.ECDSAEncoder;
import org.bouncycastle.its.operator.ITSContentSigner;
import org.bouncycastle.oer.OEREncoder;
import org.bouncycastle.oer.its.Certificate;
import org.bouncycastle.oer.its.CertificateBase;
import org.bouncycastle.oer.its.CertificateId;
import org.bouncycastle.oer.its.CertificateType;
import org.bouncycastle.oer.its.HashAlgorithm;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.oer.its.ToBeSignedCertificate;
import org.bouncycastle.oer.its.VerificationKeyIndicator;
import org.bouncycastle.oer.its.template.IEEE1609dot2;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class ITSExplicitCertificateBuilder extends ITSCertificateBuilder {
    private final ITSContentSigner signer;

    public ITSExplicitCertificateBuilder(ITSContentSigner iTSContentSigner, ToBeSignedCertificate.Builder builder) {
        super(builder);
        this.signer = iTSContentSigner;
    }

    public ITSCertificate build(CertificateId certificateId, ITSPublicVerificationKey iTSPublicVerificationKey) {
        return build(certificateId, iTSPublicVerificationKey, null);
    }

    public ITSCertificate build(CertificateId certificateId, ITSPublicVerificationKey iTSPublicVerificationKey, ITSPublicEncryptionKey iTSPublicEncryptionKey) {
        Signature its;
        HashAlgorithm hashAlgorithm;
        ToBeSignedCertificate.Builder builder = new ToBeSignedCertificate.Builder(this.tbsCertificateBuilder);
        builder.setCertificateId(certificateId);
        if (iTSPublicEncryptionKey != null) {
            builder.setEncryptionKey(iTSPublicEncryptionKey.toASN1Structure());
        }
        builder.setVerificationKeyIndicator(VerificationKeyIndicator.builder().publicVerificationKey(iTSPublicVerificationKey.toASN1Structure()).createVerificationKeyIndicator());
        ToBeSignedCertificate createToBeSignedCertificate = builder.createToBeSignedCertificate();
        VerificationKeyIndicator verificationKeyIndicator = this.signer.isForSelfSigning() ? createToBeSignedCertificate.getVerificationKeyIndicator() : this.signer.getAssociatedCertificate().toASN1Structure().getCertificateBase().getToBeSignedCertificate().getVerificationKeyIndicator();
        OutputStream outputStream = this.signer.getOutputStream();
        try {
            outputStream.write(OEREncoder.toByteArray(createToBeSignedCertificate, IEEE1609dot2.tbsCertificate));
            outputStream.close();
            int choice = verificationKeyIndicator.getChoice();
            if (choice == 0) {
                its = ECDSAEncoder.toITS(SECObjectIdentifiers.secp256r1, this.signer.getSignature());
            } else if (choice == 1) {
                its = ECDSAEncoder.toITS(TeleTrusTObjectIdentifiers.brainpoolP256r1, this.signer.getSignature());
            } else {
                if (choice != 3) {
                    throw new IllegalStateException("unknown key type");
                }
                its = ECDSAEncoder.toITS(TeleTrusTObjectIdentifiers.brainpoolP384r1, this.signer.getSignature());
            }
            CertificateBase.Builder builder2 = new CertificateBase.Builder();
            IssuerIdentifier.Builder builder3 = IssuerIdentifier.builder();
            ASN1ObjectIdentifier algorithm = this.signer.getDigestAlgorithm().getAlgorithm();
            if (this.signer.isForSelfSigning()) {
                if (algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_sha256)) {
                    hashAlgorithm = HashAlgorithm.sha256;
                } else {
                    if (!algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_sha384)) {
                        throw new IllegalStateException("unknown digest");
                    }
                    hashAlgorithm = HashAlgorithm.sha384;
                }
                builder3.self(hashAlgorithm);
            } else {
                byte[] associatedCertificateDigest = this.signer.getAssociatedCertificateDigest();
                HashedId.HashedId8 hashedId8 = new HashedId.HashedId8(Arrays.copyOfRange(associatedCertificateDigest, associatedCertificateDigest.length - 8, associatedCertificateDigest.length));
                if (algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_sha256)) {
                    builder3.sha256AndDigest(hashedId8);
                } else {
                    if (!algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_sha384)) {
                        throw new IllegalStateException("unknown digest");
                    }
                    builder3.sha384AndDigest(hashedId8);
                }
            }
            builder2.setVersion(this.version);
            builder2.setType(CertificateType.Explicit);
            builder2.setIssuer(builder3.createIssuerIdentifier());
            builder2.setToBeSignedCertificate(createToBeSignedCertificate);
            builder2.setSignature(its);
            Certificate.Builder builder4 = new Certificate.Builder();
            builder4.setCertificateBase(builder2.createCertificateBase());
            return new ITSCertificate(builder4.createCertificate());
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot produce certificate signature");
        }
    }
}
