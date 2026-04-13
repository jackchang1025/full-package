package org.bouncycastle.cms.jcajce;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.PasswordRecipientInfoGenerator;
import org.bouncycastle.operator.GenericKey;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JcePasswordRecipientInfoGenerator extends PasswordRecipientInfoGenerator {
    private EnvelopedDataHelper helper;

    public JcePasswordRecipientInfoGenerator(ASN1ObjectIdentifier aSN1ObjectIdentifier, char[] cArr) {
        super(aSN1ObjectIdentifier, cArr);
        this.helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
    }

    @Override // org.bouncycastle.cms.PasswordRecipientInfoGenerator
    public byte[] calculateDerivedKey(int i2, AlgorithmIdentifier algorithmIdentifier, int i3) {
        return this.helper.calculateDerivedKey(i2, this.password, algorithmIdentifier, i3);
    }

    @Override // org.bouncycastle.cms.PasswordRecipientInfoGenerator
    public byte[] generateEncryptedBytes(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, GenericKey genericKey) {
        Key jceKey = this.helper.getJceKey(genericKey);
        Cipher createRFC3211Wrapper = this.helper.createRFC3211Wrapper(algorithmIdentifier.getAlgorithm());
        try {
            createRFC3211Wrapper.init(3, new SecretKeySpec(bArr, createRFC3211Wrapper.getAlgorithm()), new IvParameterSpec(ASN1OctetString.getInstance(algorithmIdentifier.getParameters()).getOctets()));
            return createRFC3211Wrapper.wrap(jceKey);
        } catch (GeneralSecurityException e2) {
            throw new CMSException(AbstractC0000a.m19o(e2, new StringBuilder("cannot process content encryption key: ")), e2);
        }
    }

    public JcePasswordRecipientInfoGenerator setProvider(String str) {
        this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(str));
        return this;
    }

    public JcePasswordRecipientInfoGenerator setProvider(Provider provider) {
        this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(provider));
        return this;
    }
}
