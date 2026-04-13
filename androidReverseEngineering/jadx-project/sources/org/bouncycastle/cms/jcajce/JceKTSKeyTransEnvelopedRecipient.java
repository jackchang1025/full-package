package org.bouncycastle.cms.jcajce;

import java.io.InputStream;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.KeyTransRecipientId;
import org.bouncycastle.cms.RecipientOperator;
import org.bouncycastle.jcajce.io.CipherInputStream;
import org.bouncycastle.operator.InputDecryptor;

/* loaded from: classes.dex */
public class JceKTSKeyTransEnvelopedRecipient extends JceKTSKeyTransRecipient {
    public JceKTSKeyTransEnvelopedRecipient(PrivateKey privateKey, KeyTransRecipientId keyTransRecipientId) {
        super(privateKey, JceKTSKeyTransRecipient.getPartyVInfoFromRID(keyTransRecipientId));
    }

    @Override // org.bouncycastle.cms.KeyTransRecipient
    public RecipientOperator getRecipientOperator(AlgorithmIdentifier algorithmIdentifier, final AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) {
        final Cipher createContentCipher = this.contentHelper.createContentCipher(extractSecretKey(algorithmIdentifier, algorithmIdentifier2, bArr), algorithmIdentifier2);
        return new RecipientOperator(new InputDecryptor() { // from class: org.bouncycastle.cms.jcajce.JceKTSKeyTransEnvelopedRecipient.1
            @Override // org.bouncycastle.operator.InputDecryptor
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return algorithmIdentifier2;
            }

            @Override // org.bouncycastle.operator.InputDecryptor
            public InputStream getInputStream(InputStream inputStream) {
                return new CipherInputStream(inputStream, createContentCipher);
            }
        });
    }
}
