package org.bouncycastle.cms;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.PasswordRecipientInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Integers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PasswordRecipientInformation extends RecipientInformation {
    static Map BLOCKSIZES;
    static Map KEYSIZES = new HashMap();
    private PasswordRecipientInfo info;

    static {
        HashMap hashMap = new HashMap();
        BLOCKSIZES = hashMap;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = CMSAlgorithm.DES_EDE3_CBC;
        hashMap.put(aSN1ObjectIdentifier, Integers.valueOf(8));
        Map map = BLOCKSIZES;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = CMSAlgorithm.AES128_CBC;
        map.put(aSN1ObjectIdentifier2, Integers.valueOf(16));
        Map map2 = BLOCKSIZES;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = CMSAlgorithm.AES192_CBC;
        map2.put(aSN1ObjectIdentifier3, Integers.valueOf(16));
        Map map3 = BLOCKSIZES;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = CMSAlgorithm.AES256_CBC;
        map3.put(aSN1ObjectIdentifier4, Integers.valueOf(16));
        KEYSIZES.put(aSN1ObjectIdentifier, Integers.valueOf(192));
        KEYSIZES.put(aSN1ObjectIdentifier2, Integers.valueOf(128));
        KEYSIZES.put(aSN1ObjectIdentifier3, Integers.valueOf(192));
        KEYSIZES.put(aSN1ObjectIdentifier4, Integers.valueOf(256));
    }

    public PasswordRecipientInformation(PasswordRecipientInfo passwordRecipientInfo, AlgorithmIdentifier algorithmIdentifier, CMSSecureReadable cMSSecureReadable, AuthAttributesProvider authAttributesProvider) {
        super(passwordRecipientInfo.getKeyEncryptionAlgorithm(), algorithmIdentifier, cMSSecureReadable, authAttributesProvider);
        this.info = passwordRecipientInfo;
        this.rid = new PasswordRecipientId();
    }

    public String getKeyDerivationAlgOID() {
        if (this.info.getKeyDerivationAlgorithm() != null) {
            return this.info.getKeyDerivationAlgorithm().getAlgorithm().getId();
        }
        return null;
    }

    public byte[] getKeyDerivationAlgParams() {
        ASN1Encodable parameters;
        try {
            if (this.info.getKeyDerivationAlgorithm() == null || (parameters = this.info.getKeyDerivationAlgorithm().getParameters()) == null) {
                return null;
            }
            return parameters.toASN1Primitive().getEncoded();
        } catch (Exception e2) {
            throw new RuntimeException(AbstractC0000a.m14j("exception getting encryption parameters ", e2));
        }
    }

    public AlgorithmIdentifier getKeyDerivationAlgorithm() {
        return this.info.getKeyDerivationAlgorithm();
    }

    @Override // org.bouncycastle.cms.RecipientInformation
    public RecipientOperator getRecipientOperator(Recipient recipient) {
        PasswordRecipient passwordRecipient = (PasswordRecipient) recipient;
        AlgorithmIdentifier algorithmIdentifier = AlgorithmIdentifier.getInstance(AlgorithmIdentifier.getInstance(this.info.getKeyEncryptionAlgorithm()).getParameters());
        return passwordRecipient.getRecipientOperator(algorithmIdentifier, this.messageAlgorithm, passwordRecipient.calculateDerivedKey(passwordRecipient.getPasswordConversionScheme(), getKeyDerivationAlgorithm(), ((Integer) KEYSIZES.get(algorithmIdentifier.getAlgorithm())).intValue()), this.info.getEncryptedKey().getOctets());
    }
}
