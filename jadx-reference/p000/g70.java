package p000;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.List;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.RuntimeOperatorException;
import org.bouncycastle.operator.jcajce.OperatorHelper;

/* loaded from: classes2.dex */
public class g70 {
    private OperatorHelper helper;
    private SecureRandom random;
    private C1168r5 sigAlgId;
    private AlgorithmParameterSpec sigAlgSpec;
    private String signatureAlgorithm;

    /* renamed from: g70$a0 */
    public class C0525a0 implements InterfaceC0863mj {
        private OutputStream stream;
        final /* synthetic */ Signature val$sig;
        final /* synthetic */ C1168r5 val$signatureAlgId;

        public C0525a0(Signature signature, C1168r5 c1168r5) {
            this.val$sig = signature;
            this.val$signatureAlgId = c1168r5;
            this.stream = tl0.createStream(signature);
        }

        @Override // p000.InterfaceC0863mj
        public C1168r5 getAlgorithmIdentifier() {
            return this.val$signatureAlgId;
        }

        @Override // p000.InterfaceC0863mj
        public OutputStream getOutputStream() {
            return this.stream;
        }

        @Override // p000.InterfaceC0863mj
        public byte[] getSignature() {
            try {
                return this.val$sig.sign();
            } catch (SignatureException e) {
                throw new RuntimeOperatorException("exception obtaining signature: " + e.getMessage(), e);
            }
        }
    }

    /* renamed from: g70$a1 */
    public class C0526a1 implements InterfaceC0863mj {
        OutputStream stream;
        final /* synthetic */ OutputStream val$sigStream;
        final /* synthetic */ Signature[] val$sigs;

        public C0526a1(OutputStream outputStream, Signature[] signatureArr) {
            this.val$sigStream = outputStream;
            this.val$sigs = signatureArr;
            this.stream = outputStream;
        }

        @Override // p000.InterfaceC0863mj
        public C1168r5 getAlgorithmIdentifier() {
            return g70.this.sigAlgId;
        }

        @Override // p000.InterfaceC0863mj
        public OutputStream getOutputStream() {
            return this.stream;
        }

        @Override // p000.InterfaceC0863mj
        public byte[] getSignature() {
            try {
                C0118b1 c0118b1 = new C0118b1();
                for (int i = 0; i != this.val$sigs.length; i++) {
                    c0118b1.add(new C0991oo(this.val$sigs[i].sign()));
                }
                return new C1064pc(c0118b1).getEncoded("DER");
            } catch (IOException e) {
                throw new RuntimeOperatorException(AbstractC0003a2.m26a7(e, new StringBuilder("exception encoding signature: ")), e);
            } catch (SignatureException e2) {
                throw new RuntimeOperatorException("exception obtaining signature: " + e2.getMessage(), e2);
            }
        }
    }

    public g70(String str) {
        this.helper = new OperatorHelper(new C1177re());
        this.signatureAlgorithm = str;
        this.sigAlgId = new C1181ri().find(str);
        this.sigAlgSpec = null;
    }

    private InterfaceC0863mj buildComposite(CompositePrivateKey compositePrivateKey) throws OperatorCreationException, InvalidKeyException {
        try {
            List<PrivateKey> privateKeys = compositePrivateKey.getPrivateKeys();
            AbstractC0400d2 abstractC0400d2 = AbstractC0400d2.getInstance(this.sigAlgId.getParameters());
            int size = abstractC0400d2.size();
            Signature[] signatureArr = new Signature[size];
            for (int i = 0; i != abstractC0400d2.size(); i++) {
                Signature signatureCreateSignature = this.helper.createSignature(C1168r5.getInstance(abstractC0400d2.getObjectAt(i)));
                signatureArr[i] = signatureCreateSignature;
                if (this.random != null) {
                    signatureCreateSignature.initSign(privateKeys.get(i), this.random);
                } else {
                    signatureCreateSignature.initSign(privateKeys.get(i));
                }
            }
            OutputStream outputStreamCreateStream = tl0.createStream(signatureArr[0]);
            int i2 = 1;
            while (i2 != size) {
                m51 m51Var = new m51(outputStreamCreateStream, tl0.createStream(signatureArr[i2]));
                i2++;
                outputStreamCreateStream = m51Var;
            }
            return new C0526a1(outputStreamCreateStream, signatureArr);
        } catch (GeneralSecurityException e) {
            throw new OperatorCreationException("cannot create signer: " + e.getMessage(), e);
        }
    }

    private static AbstractC0400d2 createCompParams(C0753kc c0753kc) {
        InterfaceC0117b0 interfaceC0117b0CreatePSSParams;
        C1181ri c1181ri = new C1181ri();
        C0118b1 c0118b1 = new C0118b1();
        List<String> algorithmNames = c0753kc.getAlgorithmNames();
        List<AlgorithmParameterSpec> parameterSpecs = c0753kc.getParameterSpecs();
        for (int i = 0; i != algorithmNames.size(); i++) {
            AlgorithmParameterSpec algorithmParameterSpec = parameterSpecs.get(i);
            if (algorithmParameterSpec == null) {
                interfaceC0117b0CreatePSSParams = c1181ri.find(algorithmNames.get(i));
            } else {
                if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
                    throw new IllegalArgumentException("unrecognized parameterSpec");
                }
                interfaceC0117b0CreatePSSParams = createPSSParams((PSSParameterSpec) algorithmParameterSpec);
            }
            c0118b1.add(interfaceC0117b0CreatePSSParams);
        }
        return new C1064pc(c0118b1);
    }

    private static op0 createPSSParams(PSSParameterSpec pSSParameterSpec) {
        C1121qs c1121qs = new C1121qs();
        C1168r5 c1168r5Find = c1121qs.find(pSSParameterSpec.getDigestAlgorithm());
        if (c1168r5Find.getParameters() == null) {
            c1168r5Find = new C1168r5(c1168r5Find.getAlgorithm(), C1046ow.INSTANCE);
        }
        C1168r5 c1168r5Find2 = c1121qs.find(((MGF1ParameterSpec) pSSParameterSpec.getMGFParameters()).getDigestAlgorithm());
        if (c1168r5Find2.getParameters() == null) {
            c1168r5Find2 = new C1168r5(c1168r5Find2.getAlgorithm(), C1046ow.INSTANCE);
        }
        return new op0(c1168r5Find, new C1168r5(ul0.id_mgf1, c1168r5Find2), new C0155c0(pSSParameterSpec.getSaltLength()), new C0155c0(pSSParameterSpec.getTrailerField()));
    }

    public InterfaceC0863mj build(PrivateKey privateKey) throws OperatorCreationException, InvalidKeyException {
        if (privateKey instanceof CompositePrivateKey) {
            return buildComposite((CompositePrivateKey) privateKey);
        }
        try {
            Signature signatureCreateSignature = this.helper.createSignature(this.sigAlgId);
            C1168r5 c1168r5 = this.sigAlgId;
            SecureRandom secureRandom = this.random;
            if (secureRandom != null) {
                signatureCreateSignature.initSign(privateKey, secureRandom);
            } else {
                signatureCreateSignature.initSign(privateKey);
            }
            return new C0525a0(signatureCreateSignature, c1168r5);
        } catch (GeneralSecurityException e) {
            throw new OperatorCreationException("cannot create signer: " + e.getMessage(), e);
        }
    }

    public g70 setProvider(String str) {
        this.helper = new OperatorHelper(new nh0(str));
        return this;
    }

    public g70 setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public g70(String str, AlgorithmParameterSpec algorithmParameterSpec) {
        C1168r5 c1168r5;
        this.helper = new OperatorHelper(new C1177re());
        this.signatureAlgorithm = str;
        if (algorithmParameterSpec instanceof PSSParameterSpec) {
            PSSParameterSpec pSSParameterSpec = (PSSParameterSpec) algorithmParameterSpec;
            this.sigAlgSpec = pSSParameterSpec;
            c1168r5 = new C1168r5(ul0.id_RSASSA_PSS, createPSSParams(pSSParameterSpec));
        } else {
            if (!(algorithmParameterSpec instanceof C0753kc)) {
                throw new IllegalArgumentException("unknown sigParamSpec: ".concat(algorithmParameterSpec == null ? "null" : algorithmParameterSpec.getClass().getName()));
            }
            C0753kc c0753kc = (C0753kc) algorithmParameterSpec;
            this.sigAlgSpec = c0753kc;
            c1168r5 = new C1168r5(eg0.id_alg_composite, createCompParams(c0753kc));
        }
        this.sigAlgId = c1168r5;
    }

    public g70 setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ep0(provider));
        return this;
    }
}
