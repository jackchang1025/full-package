package org.bouncycastle.cms;

import org.bouncycastle.asn1.cms.KEKRecipientInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public class KEKRecipientInformation extends RecipientInformation {
    private KEKRecipientInfo info;

    public KEKRecipientInformation(KEKRecipientInfo kEKRecipientInfo, AlgorithmIdentifier algorithmIdentifier, CMSSecureReadable cMSSecureReadable, AuthAttributesProvider authAttributesProvider) {
        super(kEKRecipientInfo.getKeyEncryptionAlgorithm(), algorithmIdentifier, cMSSecureReadable, authAttributesProvider);
        this.info = kEKRecipientInfo;
        this.rid = new KEKRecipientId(kEKRecipientInfo.getKekid().getKeyIdentifier().getOctets());
    }

    @Override // org.bouncycastle.cms.RecipientInformation
    public RecipientOperator getRecipientOperator(Recipient recipient) {
        return ((KEKRecipient) recipient).getRecipientOperator(this.keyEncAlg, this.messageAlgorithm, this.info.getEncryptedKey().getOctets());
    }
}
