package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Iterator;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DLSet;
import org.bouncycastle.asn1.cms.AuthEnvelopedData;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OutputAEADEncryptor;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSAuthEnvelopedDataGenerator extends CMSAuthEnvelopedGenerator {
    private CMSAuthEnvelopedData doGenerate(CMSTypedData cMSTypedData, OutputAEADEncryptor outputAEADEncryptor) {
        DERSet dERSet;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            OutputStream outputStream = outputAEADEncryptor.getOutputStream(byteArrayOutputStream);
            cMSTypedData.write(outputStream);
            CMSAttributeTableGenerator cMSAttributeTableGenerator = this.authAttrsGenerator;
            if (cMSAttributeTableGenerator != null) {
                dERSet = new DERSet(cMSAttributeTableGenerator.getAttributes(Collections.EMPTY_MAP).toASN1EncodableVector());
                outputAEADEncryptor.getAADStream().write(dERSet.getEncoded(ASN1Encoding.DER));
            } else {
                dERSet = null;
            }
            outputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byte[] mac = outputAEADEncryptor.getMAC();
            AlgorithmIdentifier algorithmIdentifier = outputAEADEncryptor.getAlgorithmIdentifier();
            BEROctetString bEROctetString = new BEROctetString(byteArray);
            GenericKey key = outputAEADEncryptor.getKey();
            Iterator it = ((CMSAuthEnvelopedGenerator) this).recipientInfoGenerators.iterator();
            while (it.hasNext()) {
                aSN1EncodableVector.add(((RecipientInfoGenerator) it.next()).generate(key));
            }
            EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(cMSTypedData.getContentType(), algorithmIdentifier, bEROctetString);
            CMSAttributeTableGenerator cMSAttributeTableGenerator2 = this.unauthAttrsGenerator;
            return new CMSAuthEnvelopedData(new ContentInfo(CMSObjectIdentifiers.authEnvelopedData, new AuthEnvelopedData(((CMSAuthEnvelopedGenerator) this).originatorInfo, new DERSet(aSN1EncodableVector), encryptedContentInfo, dERSet, new DEROctetString(mac), cMSAttributeTableGenerator2 != null ? new DLSet(cMSAttributeTableGenerator2.getAttributes(Collections.EMPTY_MAP).toASN1EncodableVector()) : null)));
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to process authenticated content: ")), e2);
        }
    }

    public CMSAuthEnvelopedData generate(CMSTypedData cMSTypedData, OutputAEADEncryptor outputAEADEncryptor) {
        return doGenerate(cMSTypedData, outputAEADEncryptor);
    }
}
