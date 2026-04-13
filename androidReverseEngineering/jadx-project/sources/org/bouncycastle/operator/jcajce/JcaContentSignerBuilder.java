package org.bouncycastle.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.io.OutputStreamFactory;
import org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.RuntimeOperatorException;
import org.bouncycastle.util.io.TeeOutputStream;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JcaContentSignerBuilder {
    private OperatorHelper helper;
    private SecureRandom random;
    private AlgorithmIdentifier sigAlgId;
    private AlgorithmParameterSpec sigAlgSpec;
    private String signatureAlgorithm;

    public JcaContentSignerBuilder(String str) {
        this.helper = new OperatorHelper(new DefaultJcaJceHelper());
        this.signatureAlgorithm = str;
        this.sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find(str);
        this.sigAlgSpec = null;
    }

    private ContentSigner buildComposite(CompositePrivateKey compositePrivateKey) {
        try {
            List<PrivateKey> privateKeys = compositePrivateKey.getPrivateKeys();
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(this.sigAlgId.getParameters());
            int size = aSN1Sequence.size();
            Signature[] signatureArr = new Signature[size];
            for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
                Signature createSignature = this.helper.createSignature(AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i2)));
                signatureArr[i2] = createSignature;
                if (this.random != null) {
                    createSignature.initSign(privateKeys.get(i2), this.random);
                } else {
                    createSignature.initSign(privateKeys.get(i2));
                }
            }
            OutputStream createStream = OutputStreamFactory.createStream(signatureArr[0]);
            int i3 = 1;
            while (i3 != size) {
                TeeOutputStream teeOutputStream = new TeeOutputStream(createStream, OutputStreamFactory.createStream(signatureArr[i3]));
                i3++;
                createStream = teeOutputStream;
            }
            return new ContentSigner(createStream, signatureArr) { // from class: org.bouncycastle.operator.jcajce.JcaContentSignerBuilder.2
                OutputStream stream;
                final /* synthetic */ OutputStream val$sigStream;
                final /* synthetic */ Signature[] val$sigs;

                {
                    this.val$sigStream = createStream;
                    this.val$sigs = signatureArr;
                    this.stream = createStream;
                }

                @Override // org.bouncycastle.operator.ContentSigner
                public AlgorithmIdentifier getAlgorithmIdentifier() {
                    return JcaContentSignerBuilder.this.sigAlgId;
                }

                @Override // org.bouncycastle.operator.ContentSigner
                public OutputStream getOutputStream() {
                    return this.stream;
                }

                @Override // org.bouncycastle.operator.ContentSigner
                public byte[] getSignature() {
                    try {
                        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                        for (int i4 = 0; i4 != this.val$sigs.length; i4++) {
                            aSN1EncodableVector.add(new DERBitString(this.val$sigs[i4].sign()));
                        }
                        return new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER);
                    } catch (IOException e2) {
                        throw new RuntimeOperatorException(AbstractC0000a.m8d(e2, new StringBuilder("exception encoding signature: ")), e2);
                    } catch (SignatureException e3) {
                        throw new RuntimeOperatorException("exception obtaining signature: " + e3.getMessage(), e3);
                    }
                }
            };
        } catch (GeneralSecurityException e2) {
            throw new OperatorCreationException(AbstractC0000a.m19o(e2, new StringBuilder("cannot create signer: ")), e2);
        }
    }

    private static ASN1Sequence createCompParams(CompositeAlgorithmSpec compositeAlgorithmSpec) {
        ASN1Encodable createPSSParams;
        DefaultSignatureAlgorithmIdentifierFinder defaultSignatureAlgorithmIdentifierFinder = new DefaultSignatureAlgorithmIdentifierFinder();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        List<String> algorithmNames = compositeAlgorithmSpec.getAlgorithmNames();
        List<AlgorithmParameterSpec> parameterSpecs = compositeAlgorithmSpec.getParameterSpecs();
        for (int i2 = 0; i2 != algorithmNames.size(); i2++) {
            AlgorithmParameterSpec algorithmParameterSpec = parameterSpecs.get(i2);
            if (algorithmParameterSpec == null) {
                createPSSParams = defaultSignatureAlgorithmIdentifierFinder.find(algorithmNames.get(i2));
            } else {
                if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
                    throw new IllegalArgumentException("unrecognized parameterSpec");
                }
                createPSSParams = createPSSParams((PSSParameterSpec) algorithmParameterSpec);
            }
            aSN1EncodableVector.add(createPSSParams);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    private static RSASSAPSSparams createPSSParams(PSSParameterSpec pSSParameterSpec) {
        DefaultDigestAlgorithmIdentifierFinder defaultDigestAlgorithmIdentifierFinder = new DefaultDigestAlgorithmIdentifierFinder();
        AlgorithmIdentifier find = defaultDigestAlgorithmIdentifierFinder.find(pSSParameterSpec.getDigestAlgorithm());
        if (find.getParameters() == null) {
            find = new AlgorithmIdentifier(find.getAlgorithm(), DERNull.INSTANCE);
        }
        AlgorithmIdentifier find2 = defaultDigestAlgorithmIdentifierFinder.find(((MGF1ParameterSpec) pSSParameterSpec.getMGFParameters()).getDigestAlgorithm());
        if (find2.getParameters() == null) {
            find2 = new AlgorithmIdentifier(find2.getAlgorithm(), DERNull.INSTANCE);
        }
        return new RSASSAPSSparams(find, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, find2), new ASN1Integer(pSSParameterSpec.getSaltLength()), new ASN1Integer(pSSParameterSpec.getTrailerField()));
    }

    public ContentSigner build(PrivateKey privateKey) {
        if (privateKey instanceof CompositePrivateKey) {
            return buildComposite((CompositePrivateKey) privateKey);
        }
        try {
            Signature createSignature = this.helper.createSignature(this.sigAlgId);
            AlgorithmIdentifier algorithmIdentifier = this.sigAlgId;
            SecureRandom secureRandom = this.random;
            if (secureRandom != null) {
                createSignature.initSign(privateKey, secureRandom);
            } else {
                createSignature.initSign(privateKey);
            }
            return new ContentSigner(createSignature, algorithmIdentifier) { // from class: org.bouncycastle.operator.jcajce.JcaContentSignerBuilder.1
                private OutputStream stream;
                final /* synthetic */ Signature val$sig;
                final /* synthetic */ AlgorithmIdentifier val$signatureAlgId;

                {
                    this.val$sig = createSignature;
                    this.val$signatureAlgId = algorithmIdentifier;
                    this.stream = OutputStreamFactory.createStream(createSignature);
                }

                @Override // org.bouncycastle.operator.ContentSigner
                public AlgorithmIdentifier getAlgorithmIdentifier() {
                    return this.val$signatureAlgId;
                }

                @Override // org.bouncycastle.operator.ContentSigner
                public OutputStream getOutputStream() {
                    return this.stream;
                }

                @Override // org.bouncycastle.operator.ContentSigner
                public byte[] getSignature() {
                    try {
                        return this.val$sig.sign();
                    } catch (SignatureException e2) {
                        throw new RuntimeOperatorException("exception obtaining signature: " + e2.getMessage(), e2);
                    }
                }
            };
        } catch (GeneralSecurityException e2) {
            throw new OperatorCreationException(AbstractC0000a.m19o(e2, new StringBuilder("cannot create signer: ")), e2);
        }
    }

    public JcaContentSignerBuilder setProvider(String str) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(str));
        return this;
    }

    public JcaContentSignerBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public JcaContentSignerBuilder(String str, AlgorithmParameterSpec algorithmParameterSpec) {
        AlgorithmIdentifier algorithmIdentifier;
        this.helper = new OperatorHelper(new DefaultJcaJceHelper());
        this.signatureAlgorithm = str;
        if (algorithmParameterSpec instanceof PSSParameterSpec) {
            PSSParameterSpec pSSParameterSpec = (PSSParameterSpec) algorithmParameterSpec;
            this.sigAlgSpec = pSSParameterSpec;
            algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_RSASSA_PSS, createPSSParams(pSSParameterSpec));
        } else {
            if (!(algorithmParameterSpec instanceof CompositeAlgorithmSpec)) {
                throw new IllegalArgumentException("unknown sigParamSpec: ".concat(algorithmParameterSpec == null ? "null" : algorithmParameterSpec.getClass().getName()));
            }
            CompositeAlgorithmSpec compositeAlgorithmSpec = (CompositeAlgorithmSpec) algorithmParameterSpec;
            this.sigAlgSpec = compositeAlgorithmSpec;
            algorithmIdentifier = new AlgorithmIdentifier(MiscObjectIdentifiers.id_alg_composite, createCompParams(compositeAlgorithmSpec));
        }
        this.sigAlgId = algorithmIdentifier;
    }

    public JcaContentSignerBuilder setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }
}
