package org.bouncycastle.its.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Provider;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.operator.ITSContentVerifierProvider;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.oer.OEREncoder;
import org.bouncycastle.oer.its.PublicVerificationKey;
import org.bouncycastle.oer.its.VerificationKeyIndicator;
import org.bouncycastle.oer.its.template.IEEE1609dot2;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JcaITSContentVerifierProvider implements ITSContentVerifierProvider {
    private final AlgorithmIdentifier digestAlgo;
    private JcaJceHelper helper;
    private final ITSCertificate issuer;
    private final byte[] parentData;
    private final ECPublicKey pubParams;
    private final int sigChoice;

    public static class Builder {
        private JcaJceHelper helper = new DefaultJcaJceHelper();

        public JcaITSContentVerifierProvider build(ITSCertificate iTSCertificate) {
            return new JcaITSContentVerifierProvider(iTSCertificate, this.helper);
        }

        public Builder setProvider(String str) {
            this.helper = new NamedJcaJceHelper(str);
            return this;
        }

        public Builder setProvider(Provider provider) {
            this.helper = new ProviderJcaJceHelper(provider);
            return this;
        }
    }

    private JcaITSContentVerifierProvider(ITSCertificate iTSCertificate, JcaJceHelper jcaJceHelper) {
        AlgorithmIdentifier algorithmIdentifier;
        this.issuer = iTSCertificate;
        this.helper = jcaJceHelper;
        try {
            this.parentData = iTSCertificate.getEncoded();
            VerificationKeyIndicator verificationKeyIndicator = iTSCertificate.toASN1Structure().getCertificateBase().getToBeSignedCertificate().getVerificationKeyIndicator();
            if (!(verificationKeyIndicator.getObject() instanceof PublicVerificationKey)) {
                throw new IllegalArgumentException("not public verification key");
            }
            PublicVerificationKey publicVerificationKey = PublicVerificationKey.getInstance(verificationKeyIndicator.getObject());
            this.sigChoice = publicVerificationKey.getChoice();
            int choice = publicVerificationKey.getChoice();
            if (choice == 0) {
                algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
            } else if (choice == 1) {
                algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
            } else {
                if (choice != 3) {
                    throw new IllegalArgumentException("unknown key type");
                }
                algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384);
            }
            this.digestAlgo = algorithmIdentifier;
            this.pubParams = (ECPublicKey) new JcaITSPublicVerificationKey(publicVerificationKey, jcaJceHelper).getKey();
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("unable to extract parent data: ")));
        }
    }

    @Override // org.bouncycastle.its.operator.ITSContentVerifierProvider
    public ContentVerifier get(int i2) {
        byte[] bArr;
        JcaJceHelper jcaJceHelper;
        String str;
        if (this.sigChoice != i2) {
            throw new OperatorCreationException(AbstractC0000a.m11g("wrong verifier for algorithm: ", i2));
        }
        try {
            final DigestCalculator digestCalculator = new JcaDigestCalculatorProviderBuilder().setHelper(this.helper).build().get(this.digestAlgo);
            try {
                final OutputStream outputStream = digestCalculator.getOutputStream();
                byte[] bArr2 = this.parentData;
                outputStream.write(bArr2, 0, bArr2.length);
                final byte[] digest = digestCalculator.getDigest();
                if (this.issuer.getIssuer().isSelf()) {
                    byte[] byteArray = OEREncoder.toByteArray(this.issuer.toASN1Structure().getCertificateBase().getToBeSignedCertificate(), IEEE1609dot2.tbsCertificate);
                    outputStream.write(byteArray, 0, byteArray.length);
                    bArr = digestCalculator.getDigest();
                } else {
                    bArr = null;
                }
                final byte[] bArr3 = bArr;
                int i3 = this.sigChoice;
                if (i3 == 0 || i3 == 1) {
                    jcaJceHelper = this.helper;
                    str = "SHA256withECDSA";
                } else {
                    if (i3 != 3) {
                        throw new IllegalArgumentException("choice " + this.sigChoice + " not supported");
                    }
                    jcaJceHelper = this.helper;
                    str = "SHA384withECDSA";
                }
                final Signature createSignature = jcaJceHelper.createSignature(str);
                return new ContentVerifier() { // from class: org.bouncycastle.its.jcajce.JcaITSContentVerifierProvider.1
                    @Override // org.bouncycastle.operator.ContentVerifier
                    public AlgorithmIdentifier getAlgorithmIdentifier() {
                        return null;
                    }

                    @Override // org.bouncycastle.operator.ContentVerifier
                    public OutputStream getOutputStream() {
                        return outputStream;
                    }

                    @Override // org.bouncycastle.operator.ContentVerifier
                    public boolean verify(byte[] bArr4) {
                        byte[] digest2 = digestCalculator.getDigest();
                        try {
                            createSignature.initVerify(JcaITSContentVerifierProvider.this.pubParams);
                            createSignature.update(digest2);
                            byte[] bArr5 = bArr3;
                            if (bArr5 == null || !Arrays.areEqual(digest2, bArr5)) {
                                createSignature.update(digest);
                            } else {
                                createSignature.update(digestCalculator.getDigest());
                            }
                            return createSignature.verify(bArr4);
                        } catch (Exception e2) {
                            throw new RuntimeException(e2.getMessage(), e2);
                        }
                    }
                };
            } catch (Exception e2) {
                throw new IllegalStateException(e2.getMessage(), e2);
            }
        } catch (Exception e3) {
            throw new IllegalStateException(e3.getMessage(), e3);
        }
    }

    @Override // org.bouncycastle.its.operator.ITSContentVerifierProvider
    public ITSCertificate getAssociatedCertificate() {
        return this.issuer;
    }

    @Override // org.bouncycastle.its.operator.ITSContentVerifierProvider
    public boolean hasAssociatedCertificate() {
        return this.issuer != null;
    }
}
