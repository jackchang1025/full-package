package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.cms.EncryptedData;
import org.bouncycastle.operator.OutputEncryptor;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
public class CMSEncryptedDataGenerator extends CMSEncryptedGenerator {
    private CMSEncryptedData doGenerate(CMSTypedData cMSTypedData, OutputEncryptor outputEncryptor) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            OutputStream outputStream = outputEncryptor.getOutputStream(byteArrayOutputStream);
            cMSTypedData.write(outputStream);
            outputStream.close();
            EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(cMSTypedData.getContentType(), outputEncryptor.getAlgorithmIdentifier(), new BEROctetString(byteArrayOutputStream.toByteArray()));
            CMSAttributeTableGenerator cMSAttributeTableGenerator = this.unprotectedAttributeGenerator;
            return new CMSEncryptedData(new ContentInfo(CMSObjectIdentifiers.encryptedData, new EncryptedData(encryptedContentInfo, cMSAttributeTableGenerator != null ? new BERSet(cMSAttributeTableGenerator.getAttributes(Collections.EMPTY_MAP).toASN1EncodableVector()) : null)));
        } catch (IOException unused) {
            throw new CMSException(BuildConfig.FLAVOR);
        }
    }

    public CMSEncryptedData generate(CMSTypedData cMSTypedData, OutputEncryptor outputEncryptor) {
        return doGenerate(cMSTypedData, outputEncryptor);
    }
}
