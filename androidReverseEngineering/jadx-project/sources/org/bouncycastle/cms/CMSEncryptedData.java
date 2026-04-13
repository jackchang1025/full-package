package org.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.cms.EncryptedData;
import org.bouncycastle.operator.InputDecryptorProvider;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSEncryptedData {
    private ContentInfo contentInfo;
    private EncryptedData encryptedData;

    public CMSEncryptedData(ContentInfo contentInfo) {
        this.contentInfo = contentInfo;
        this.encryptedData = EncryptedData.getInstance(contentInfo.getContent());
    }

    public byte[] getContent(InputDecryptorProvider inputDecryptorProvider) {
        try {
            return CMSUtils.streamToByteArray(getContentStream(inputDecryptorProvider).getContentStream());
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to parse internal stream: ")), e2);
        }
    }

    public CMSTypedStream getContentStream(InputDecryptorProvider inputDecryptorProvider) {
        try {
            EncryptedContentInfo encryptedContentInfo = this.encryptedData.getEncryptedContentInfo();
            return new CMSTypedStream(encryptedContentInfo.getContentType(), inputDecryptorProvider.get(encryptedContentInfo.getContentEncryptionAlgorithm()).getInputStream(new ByteArrayInputStream(encryptedContentInfo.getEncryptedContent().getOctets())));
        } catch (Exception e2) {
            throw new CMSException(AbstractC0000a.m9e(e2, new StringBuilder("unable to create stream: ")), e2);
        }
    }

    public ContentInfo toASN1Structure() {
        return this.contentInfo;
    }
}
