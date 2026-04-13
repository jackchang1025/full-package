package org.bouncycastle.operator.jcajce;

import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jcajce.CompositePublicKey;
import org.bouncycastle.jcajce.io.OutputStreamFactory;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.RawContentVerifier;
import org.bouncycastle.operator.RuntimeOperatorException;
import org.bouncycastle.util.io.TeeOutputStream;

/* loaded from: classes.dex */
public class JcaContentVerifierProviderBuilder {
    private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());

    public class CompositeVerifier implements ContentVerifier {
        private Signature[] sigs;
        private OutputStream stream;

        public CompositeVerifier(Signature[] signatureArr) {
            this.sigs = signatureArr;
            int i2 = 0;
            while (i2 < signatureArr.length && signatureArr[i2] == null) {
                i2++;
            }
            if (i2 == signatureArr.length) {
                throw new OperatorCreationException("no matching signature found in composite");
            }
            OutputStream createStream = OutputStreamFactory.createStream(signatureArr[i2]);
            while (true) {
                this.stream = createStream;
                do {
                    i2++;
                    if (i2 == signatureArr.length) {
                        return;
                    }
                } while (signatureArr[i2] == null);
                createStream = new TeeOutputStream(this.stream, OutputStreamFactory.createStream(signatureArr[i2]));
            }
        }

        @Override // org.bouncycastle.operator.ContentVerifier
        public AlgorithmIdentifier getAlgorithmIdentifier() {
            return new AlgorithmIdentifier(MiscObjectIdentifiers.id_alg_composite);
        }

        @Override // org.bouncycastle.operator.ContentVerifier
        public OutputStream getOutputStream() {
            return this.stream;
        }

        @Override // org.bouncycastle.operator.ContentVerifier
        public boolean verify(byte[] bArr) {
            try {
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(bArr);
                boolean z2 = false;
                for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
                    Signature signature = this.sigs[i2];
                    if (signature != null && !signature.verify(DERBitString.getInstance((Object) aSN1Sequence.getObjectAt(i2)).getBytes())) {
                        z2 = true;
                    }
                }
                return !z2;
            } catch (SignatureException e2) {
                throw new RuntimeOperatorException("exception obtaining signature: " + e2.getMessage(), e2);
            }
        }
    }

    public class RawSigVerifier extends SigVerifier implements RawContentVerifier {
        private Signature rawSignature;

        public RawSigVerifier(AlgorithmIdentifier algorithmIdentifier, Signature signature, Signature signature2) {
            super(algorithmIdentifier, signature);
            this.rawSignature = signature2;
        }

        @Override // org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.SigVerifier, org.bouncycastle.operator.ContentVerifier
        public boolean verify(byte[] bArr) {
            try {
                return super.verify(bArr);
            } finally {
                try {
                    this.rawSignature.verify(bArr);
                } catch (Exception unused) {
                }
            }
        }

        @Override // org.bouncycastle.operator.RawContentVerifier
        public boolean verify(byte[] bArr, byte[] bArr2) {
            try {
                try {
                    this.rawSignature.update(bArr);
                    return this.rawSignature.verify(bArr2);
                } catch (SignatureException e2) {
                    throw new RuntimeOperatorException("exception obtaining raw signature: " + e2.getMessage(), e2);
                }
            } finally {
                try {
                    this.rawSignature.verify(bArr2);
                } catch (Exception unused) {
                }
            }
        }
    }

    public class SigVerifier implements ContentVerifier {
        private final AlgorithmIdentifier algorithm;
        private final Signature signature;
        protected final OutputStream stream;

        public SigVerifier(AlgorithmIdentifier algorithmIdentifier, Signature signature) {
            this.algorithm = algorithmIdentifier;
            this.signature = signature;
            this.stream = OutputStreamFactory.createStream(signature);
        }

        @Override // org.bouncycastle.operator.ContentVerifier
        public AlgorithmIdentifier getAlgorithmIdentifier() {
            return this.algorithm;
        }

        @Override // org.bouncycastle.operator.ContentVerifier
        public OutputStream getOutputStream() {
            OutputStream outputStream = this.stream;
            if (outputStream != null) {
                return outputStream;
            }
            throw new IllegalStateException("verifier not initialised");
        }

        @Override // org.bouncycastle.operator.ContentVerifier
        public boolean verify(byte[] bArr) {
            try {
                return this.signature.verify(bArr);
            } catch (SignatureException e2) {
                throw new RuntimeOperatorException("exception obtaining signature: " + e2.getMessage(), e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ContentVerifier createCompositeVerifier(AlgorithmIdentifier algorithmIdentifier, PublicKey publicKey) {
        int i2 = 0;
        if (!(publicKey instanceof CompositePublicKey)) {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(algorithmIdentifier.getParameters());
            Signature[] signatureArr = new Signature[aSN1Sequence.size()];
            while (i2 != aSN1Sequence.size()) {
                try {
                    signatureArr[i2] = createSignature(AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i2)), publicKey);
                } catch (Exception unused) {
                    signatureArr[i2] = null;
                }
                i2++;
            }
            return new CompositeVerifier(signatureArr);
        }
        List<PublicKey> publicKeys = ((CompositePublicKey) publicKey).getPublicKeys();
        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(algorithmIdentifier.getParameters());
        Signature[] signatureArr2 = new Signature[aSN1Sequence2.size()];
        while (i2 != aSN1Sequence2.size()) {
            AlgorithmIdentifier algorithmIdentifier2 = AlgorithmIdentifier.getInstance(aSN1Sequence2.getObjectAt(i2));
            if (publicKeys.get(i2) != null) {
                signatureArr2[i2] = createSignature(algorithmIdentifier2, publicKeys.get(i2));
            } else {
                signatureArr2[i2] = null;
            }
            i2++;
        }
        return new CompositeVerifier(signatureArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Signature createRawSig(AlgorithmIdentifier algorithmIdentifier, PublicKey publicKey) {
        try {
            Signature createRawSignature = this.helper.createRawSignature(algorithmIdentifier);
            if (createRawSignature == null) {
                return createRawSignature;
            }
            createRawSignature.initVerify(publicKey);
            return createRawSignature;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Signature createSignature(AlgorithmIdentifier algorithmIdentifier, PublicKey publicKey) {
        try {
            Signature createSignature = this.helper.createSignature(algorithmIdentifier);
            createSignature.initVerify(publicKey);
            return createSignature;
        } catch (GeneralSecurityException e2) {
            throw new OperatorCreationException("exception on setup: " + e2, e2);
        }
    }

    public ContentVerifierProvider build(final PublicKey publicKey) {
        return new ContentVerifierProvider() { // from class: org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.2
            @Override // org.bouncycastle.operator.ContentVerifierProvider
            public ContentVerifier get(AlgorithmIdentifier algorithmIdentifier) {
                if (algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) MiscObjectIdentifiers.id_alg_composite)) {
                    return JcaContentVerifierProviderBuilder.this.createCompositeVerifier(algorithmIdentifier, publicKey);
                }
                PublicKey publicKey2 = publicKey;
                if (!(publicKey2 instanceof CompositePublicKey)) {
                    Signature createSignature = JcaContentVerifierProviderBuilder.this.createSignature(algorithmIdentifier, publicKey2);
                    Signature createRawSig = JcaContentVerifierProviderBuilder.this.createRawSig(algorithmIdentifier, publicKey);
                    return createRawSig != null ? JcaContentVerifierProviderBuilder.this.new RawSigVerifier(algorithmIdentifier, createSignature, createRawSig) : JcaContentVerifierProviderBuilder.this.new SigVerifier(algorithmIdentifier, createSignature);
                }
                List<PublicKey> publicKeys = ((CompositePublicKey) publicKey2).getPublicKeys();
                for (int i2 = 0; i2 != publicKeys.size(); i2++) {
                    try {
                        Signature createSignature2 = JcaContentVerifierProviderBuilder.this.createSignature(algorithmIdentifier, publicKeys.get(i2));
                        Signature createRawSig2 = JcaContentVerifierProviderBuilder.this.createRawSig(algorithmIdentifier, publicKeys.get(i2));
                        return createRawSig2 != null ? JcaContentVerifierProviderBuilder.this.new RawSigVerifier(algorithmIdentifier, createSignature2, createRawSig2) : JcaContentVerifierProviderBuilder.this.new SigVerifier(algorithmIdentifier, createSignature2);
                    } catch (OperatorCreationException unused) {
                    }
                }
                throw new OperatorCreationException("no matching algorithm found for key");
            }

            @Override // org.bouncycastle.operator.ContentVerifierProvider
            public X509CertificateHolder getAssociatedCertificate() {
                return null;
            }

            @Override // org.bouncycastle.operator.ContentVerifierProvider
            public boolean hasAssociatedCertificate() {
                return false;
            }
        };
    }

    public JcaContentVerifierProviderBuilder setProvider(String str) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(str));
        return this;
    }

    public ContentVerifierProvider build(final X509Certificate x509Certificate) {
        try {
            final JcaX509CertificateHolder jcaX509CertificateHolder = new JcaX509CertificateHolder(x509Certificate);
            return new ContentVerifierProvider() { // from class: org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder.1
                @Override // org.bouncycastle.operator.ContentVerifierProvider
                public ContentVerifier get(AlgorithmIdentifier algorithmIdentifier) {
                    if (algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) MiscObjectIdentifiers.id_alg_composite)) {
                        return JcaContentVerifierProviderBuilder.this.createCompositeVerifier(algorithmIdentifier, x509Certificate.getPublicKey());
                    }
                    try {
                        Signature createSignature = JcaContentVerifierProviderBuilder.this.helper.createSignature(algorithmIdentifier);
                        createSignature.initVerify(x509Certificate.getPublicKey());
                        Signature createRawSig = JcaContentVerifierProviderBuilder.this.createRawSig(algorithmIdentifier, x509Certificate.getPublicKey());
                        return createRawSig != null ? JcaContentVerifierProviderBuilder.this.new RawSigVerifier(algorithmIdentifier, createSignature, createRawSig) : JcaContentVerifierProviderBuilder.this.new SigVerifier(algorithmIdentifier, createSignature);
                    } catch (GeneralSecurityException e2) {
                        throw new OperatorCreationException("exception on setup: " + e2, e2);
                    }
                }

                @Override // org.bouncycastle.operator.ContentVerifierProvider
                public X509CertificateHolder getAssociatedCertificate() {
                    return jcaX509CertificateHolder;
                }

                @Override // org.bouncycastle.operator.ContentVerifierProvider
                public boolean hasAssociatedCertificate() {
                    return true;
                }
            };
        } catch (CertificateEncodingException e2) {
            throw new OperatorCreationException("cannot process certificate: " + e2.getMessage(), e2);
        }
    }

    public JcaContentVerifierProviderBuilder setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }

    public ContentVerifierProvider build(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        return build(this.helper.convertPublicKey(subjectPublicKeyInfo));
    }

    public ContentVerifierProvider build(X509CertificateHolder x509CertificateHolder) {
        return build(this.helper.convertCertificate(x509CertificateHolder));
    }
}
