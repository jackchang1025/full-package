package org.bouncycastle.its;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.oer.its.Certificate;
import org.bouncycastle.oer.its.CertificateBase;
import org.bouncycastle.oer.its.CertificateId;
import org.bouncycastle.oer.its.CertificateType;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.ToBeSignedCertificate;
import org.bouncycastle.oer.its.VerificationKeyIndicator;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class ITSImplicitCertificateBuilder extends ITSCertificateBuilder {
    private final IssuerIdentifier issuerIdentifier;

    public ITSImplicitCertificateBuilder(ITSCertificate iTSCertificate, DigestCalculatorProvider digestCalculatorProvider, ToBeSignedCertificate.Builder builder) {
        super(iTSCertificate, builder);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_sha256;
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(aSN1ObjectIdentifier);
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        try {
            DigestCalculator digestCalculator = digestCalculatorProvider.get(algorithmIdentifier);
            try {
                OutputStream outputStream = digestCalculator.getOutputStream();
                outputStream.write(iTSCertificate.getEncoded());
                outputStream.close();
                byte[] digest = digestCalculator.getDigest();
                IssuerIdentifier.Builder builder2 = IssuerIdentifier.builder();
                HashedId.HashedId8 hashedId8 = new HashedId.HashedId8(Arrays.copyOfRange(digest, digest.length - 8, digest.length));
                if (algorithm.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
                    builder2.sha256AndDigest(hashedId8);
                } else {
                    if (!algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_sha384)) {
                        throw new IllegalStateException("unknown digest");
                    }
                    builder2.sha384AndDigest(hashedId8);
                }
                this.issuerIdentifier = builder2.createIssuerIdentifier();
            } catch (IOException e2) {
                throw new IllegalStateException(e2.getMessage(), e2);
            }
        } catch (OperatorCreationException e3) {
            throw new IllegalStateException(e3.getMessage(), e3);
        }
    }

    public ITSCertificate build(CertificateId certificateId, BigInteger bigInteger, BigInteger bigInteger2) {
        return build(certificateId, bigInteger, bigInteger2, null);
    }

    public ITSCertificate build(CertificateId certificateId, BigInteger bigInteger, BigInteger bigInteger2, PublicEncryptionKey publicEncryptionKey) {
        EccP256CurvePoint createUncompressedP256 = EccP256CurvePoint.builder().createUncompressedP256(bigInteger, bigInteger2);
        ToBeSignedCertificate.Builder builder = new ToBeSignedCertificate.Builder(this.tbsCertificateBuilder);
        builder.setCertificateId(certificateId);
        if (publicEncryptionKey != null) {
            builder.setEncryptionKey(publicEncryptionKey);
        }
        builder.setVerificationKeyIndicator(VerificationKeyIndicator.builder().reconstructionValue(createUncompressedP256).createVerificationKeyIndicator());
        CertificateBase.Builder builder2 = new CertificateBase.Builder();
        builder2.setVersion(this.version);
        builder2.setType(CertificateType.Implicit);
        builder2.setIssuer(this.issuerIdentifier);
        builder2.setToBeSignedCertificate(builder.createToBeSignedCertificate());
        Certificate.Builder builder3 = new Certificate.Builder();
        builder3.setCertificateBase(builder2.createCertificateBase());
        return new ITSCertificate(builder3.createCertificate());
    }
}
