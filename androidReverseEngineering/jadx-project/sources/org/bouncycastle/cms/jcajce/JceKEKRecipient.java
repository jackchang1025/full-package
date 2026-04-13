package org.bouncycastle.cms.jcajce;

import java.security.Key;
import java.security.Provider;
import javax.crypto.SecretKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.KEKRecipient;
import org.bouncycastle.operator.OperatorException;

/* loaded from: classes.dex */
public abstract class JceKEKRecipient implements KEKRecipient {
    protected EnvelopedDataHelper contentHelper;
    protected EnvelopedDataHelper helper;
    private SecretKey recipientKey;
    protected boolean validateKeySize;

    public JceKEKRecipient(SecretKey secretKey) {
        EnvelopedDataHelper envelopedDataHelper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
        this.helper = envelopedDataHelper;
        this.contentHelper = envelopedDataHelper;
        this.validateKeySize = false;
        this.recipientKey = secretKey;
    }

    public Key extractSecretKey(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) {
        try {
            Key jceKey = this.helper.getJceKey(algorithmIdentifier2.getAlgorithm(), this.helper.createSymmetricUnwrapper(algorithmIdentifier, this.recipientKey).generateUnwrappedKey(algorithmIdentifier2, bArr));
            if (this.validateKeySize) {
                this.helper.keySizeCheck(algorithmIdentifier2, jceKey);
            }
            return jceKey;
        } catch (OperatorException e2) {
            throw new CMSException("exception unwrapping key: " + e2.getMessage(), e2);
        }
    }

    public JceKEKRecipient setContentProvider(String str) {
        this.contentHelper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(str));
        return this;
    }

    public JceKEKRecipient setKeySizeValidation(boolean z2) {
        this.validateKeySize = z2;
        return this;
    }

    public JceKEKRecipient setProvider(String str) {
        EnvelopedDataHelper envelopedDataHelper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(str));
        this.helper = envelopedDataHelper;
        this.contentHelper = envelopedDataHelper;
        return this;
    }

    public JceKEKRecipient setContentProvider(Provider provider) {
        this.contentHelper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(provider));
        return this;
    }

    public JceKEKRecipient setProvider(Provider provider) {
        EnvelopedDataHelper envelopedDataHelper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(provider));
        this.helper = envelopedDataHelper;
        this.contentHelper = envelopedDataHelper;
        return this;
    }
}
