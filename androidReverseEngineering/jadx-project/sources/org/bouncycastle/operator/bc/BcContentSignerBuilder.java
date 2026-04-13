package org.bouncycastle.operator.bc;

import java.io.OutputStream;
import java.security.SecureRandom;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.RuntimeOperatorException;

/* loaded from: classes.dex */
public abstract class BcContentSignerBuilder {
    private AlgorithmIdentifier digAlgId;
    protected BcDigestProvider digestProvider = BcDefaultDigestProvider.INSTANCE;
    private SecureRandom random;
    private AlgorithmIdentifier sigAlgId;

    public BcContentSignerBuilder(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        this.sigAlgId = algorithmIdentifier;
        this.digAlgId = algorithmIdentifier2;
    }

    public ContentSigner build(AsymmetricKeyParameter asymmetricKeyParameter) {
        Signer createSigner = createSigner(this.sigAlgId, this.digAlgId);
        SecureRandom secureRandom = this.random;
        if (secureRandom != null) {
            createSigner.init(true, new ParametersWithRandom(asymmetricKeyParameter, secureRandom));
        } else {
            createSigner.init(true, asymmetricKeyParameter);
        }
        return new ContentSigner(createSigner) { // from class: org.bouncycastle.operator.bc.BcContentSignerBuilder.1
            private BcSignerOutputStream stream;
            final /* synthetic */ Signer val$sig;

            {
                this.val$sig = createSigner;
                this.stream = new BcSignerOutputStream(createSigner);
            }

            @Override // org.bouncycastle.operator.ContentSigner
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return BcContentSignerBuilder.this.sigAlgId;
            }

            @Override // org.bouncycastle.operator.ContentSigner
            public OutputStream getOutputStream() {
                return this.stream;
            }

            @Override // org.bouncycastle.operator.ContentSigner
            public byte[] getSignature() {
                try {
                    return this.stream.getSignature();
                } catch (CryptoException e2) {
                    throw new RuntimeOperatorException("exception obtaining signature: " + e2.getMessage(), e2);
                }
            }
        };
    }

    public abstract Signer createSigner(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2);

    public BcContentSignerBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }
}
