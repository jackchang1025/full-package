package org.bouncycastle.cert.crmf.jcajce;

import java.io.InputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.crmf.CRMFException;
import org.bouncycastle.cert.crmf.ValueDecryptorGenerator;
import org.bouncycastle.jcajce.io.CipherInputStream;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.InputDecryptor;
import org.bouncycastle.operator.OperatorException;
import org.bouncycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;

/* loaded from: classes.dex */
public class JceAsymmetricValueDecryptorGenerator implements ValueDecryptorGenerator {
    private CRMFHelper helper = new CRMFHelper(new DefaultJcaJceHelper());
    private Provider provider = null;
    private String providerName = null;
    private PrivateKey recipientKey;

    public JceAsymmetricValueDecryptorGenerator(PrivateKey privateKey) {
        this.recipientKey = privateKey;
    }

    private Key extractSecretKey(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) {
        try {
            JceAsymmetricKeyUnwrapper jceAsymmetricKeyUnwrapper = new JceAsymmetricKeyUnwrapper(algorithmIdentifier, this.recipientKey);
            Provider provider = this.provider;
            if (provider != null) {
                jceAsymmetricKeyUnwrapper.setProvider(provider);
            }
            String str = this.providerName;
            if (str != null) {
                jceAsymmetricKeyUnwrapper.setProvider(str);
            }
            return new SecretKeySpec((byte[]) jceAsymmetricKeyUnwrapper.generateUnwrappedKey(algorithmIdentifier2, bArr).getRepresentation(), algorithmIdentifier2.getAlgorithm().getId());
        } catch (OperatorException e2) {
            throw new CRMFException("key invalid in message: " + e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.cert.crmf.ValueDecryptorGenerator
    public InputDecryptor getValueDecryptor(AlgorithmIdentifier algorithmIdentifier, final AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) {
        final Cipher createContentCipher = this.helper.createContentCipher(extractSecretKey(algorithmIdentifier, algorithmIdentifier2, bArr), algorithmIdentifier2);
        return new InputDecryptor() { // from class: org.bouncycastle.cert.crmf.jcajce.JceAsymmetricValueDecryptorGenerator.1
            @Override // org.bouncycastle.operator.InputDecryptor
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return algorithmIdentifier2;
            }

            @Override // org.bouncycastle.operator.InputDecryptor
            public InputStream getInputStream(InputStream inputStream) {
                return new CipherInputStream(inputStream, createContentCipher);
            }
        };
    }

    public JceAsymmetricValueDecryptorGenerator setProvider(String str) {
        this.helper = new CRMFHelper(new NamedJcaJceHelper(str));
        this.provider = null;
        this.providerName = str;
        return this;
    }

    public JceAsymmetricValueDecryptorGenerator setProvider(Provider provider) {
        this.helper = new CRMFHelper(new ProviderJcaJceHelper(provider));
        this.provider = provider;
        this.providerName = null;
        return this;
    }
}
