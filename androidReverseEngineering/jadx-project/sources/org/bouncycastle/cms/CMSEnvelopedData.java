package org.bouncycastle.cms;

import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSEnvelopedHelper;
import org.bouncycastle.util.Encodable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSEnvelopedData implements Encodable {
    ContentInfo contentInfo;
    private AlgorithmIdentifier encAlg;
    private OriginatorInformation originatorInfo;
    RecipientInformationStore recipientInfoStore;
    private ASN1Set unprotectedAttributes;

    public CMSEnvelopedData(InputStream inputStream) {
        this(CMSUtils.readContentInfo(inputStream));
    }

    private byte[] encodeObj(ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            return aSN1Encodable.toASN1Primitive().getEncoded();
        }
        return null;
    }

    public AlgorithmIdentifier getContentEncryptionAlgorithm() {
        return this.encAlg;
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return this.contentInfo.getEncoded();
    }

    public String getEncryptionAlgOID() {
        return this.encAlg.getAlgorithm().getId();
    }

    public byte[] getEncryptionAlgParams() {
        try {
            return encodeObj(this.encAlg.getParameters());
        } catch (Exception e2) {
            throw new RuntimeException(AbstractC0000a.m14j("exception getting encryption parameters ", e2));
        }
    }

    public OriginatorInformation getOriginatorInfo() {
        return this.originatorInfo;
    }

    public RecipientInformationStore getRecipientInfos() {
        return this.recipientInfoStore;
    }

    public AttributeTable getUnprotectedAttributes() {
        ASN1Set aSN1Set = this.unprotectedAttributes;
        if (aSN1Set == null) {
            return null;
        }
        return new AttributeTable(aSN1Set);
    }

    public ContentInfo toASN1Structure() {
        return this.contentInfo;
    }

    public CMSEnvelopedData(ContentInfo contentInfo) {
        this.contentInfo = contentInfo;
        try {
            EnvelopedData envelopedData = EnvelopedData.getInstance(contentInfo.getContent());
            if (envelopedData.getOriginatorInfo() != null) {
                this.originatorInfo = new OriginatorInformation(envelopedData.getOriginatorInfo());
            }
            ASN1Set recipientInfos = envelopedData.getRecipientInfos();
            EncryptedContentInfo encryptedContentInfo = envelopedData.getEncryptedContentInfo();
            this.encAlg = encryptedContentInfo.getContentEncryptionAlgorithm();
            this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(recipientInfos, this.encAlg, new CMSEnvelopedHelper.CMSEnvelopedSecureReadable(this.encAlg, encryptedContentInfo.getContentType(), new CMSProcessableByteArray(encryptedContentInfo.getEncryptedContent().getOctets())));
            this.unprotectedAttributes = envelopedData.getUnprotectedAttrs();
        } catch (ClassCastException e2) {
            throw new CMSException("Malformed content.", e2);
        } catch (IllegalArgumentException e3) {
            throw new CMSException("Malformed content.", e3);
        }
    }

    public CMSEnvelopedData(byte[] bArr) {
        this(CMSUtils.readContentInfo(bArr));
    }
}
