package org.bouncycastle.pkcs;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.pkcs.AuthenticatedSafe;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.cms.CMSEncryptedDataGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.operator.OutputEncryptor;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS12PfxPduBuilder {
    private ASN1EncodableVector dataVector = new ASN1EncodableVector();

    private PKCS12PfxPduBuilder addEncryptedData(OutputEncryptor outputEncryptor, ASN1Sequence aSN1Sequence) {
        try {
            this.dataVector.add(new CMSEncryptedDataGenerator().generate(new CMSProcessableByteArray(aSN1Sequence.getEncoded()), outputEncryptor).toASN1Structure());
            return this;
        } catch (CMSException e2) {
            throw new PKCSIOException(e2.getMessage(), e2.getCause());
        }
    }

    public PKCS12PfxPduBuilder addData(PKCS12SafeBag pKCS12SafeBag) {
        this.dataVector.add(new ContentInfo(PKCSObjectIdentifiers.data, new DEROctetString(new DLSequence(pKCS12SafeBag.toASN1Structure()).getEncoded())));
        return this;
    }

    public PKCS12PfxPdu build(PKCS12MacCalculatorBuilder pKCS12MacCalculatorBuilder, char[] cArr) {
        try {
            byte[] encoded = AuthenticatedSafe.getInstance(new DLSequence(this.dataVector)).getEncoded();
            return new PKCS12PfxPdu(new Pfx(new ContentInfo(PKCSObjectIdentifiers.data, new DEROctetString(encoded)), pKCS12MacCalculatorBuilder != null ? new MacDataGenerator(pKCS12MacCalculatorBuilder).build(cArr, encoded) : null));
        } catch (IOException e2) {
            throw new PKCSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to encode AuthenticatedSafe: ")), e2);
        }
    }

    public PKCS12PfxPduBuilder addEncryptedData(OutputEncryptor outputEncryptor, PKCS12SafeBag pKCS12SafeBag) {
        return addEncryptedData(outputEncryptor, new DERSequence(pKCS12SafeBag.toASN1Structure()));
    }

    public PKCS12PfxPduBuilder addEncryptedData(OutputEncryptor outputEncryptor, PKCS12SafeBag[] pKCS12SafeBagArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i2 = 0; i2 != pKCS12SafeBagArr.length; i2++) {
            aSN1EncodableVector.add(pKCS12SafeBagArr[i2].toASN1Structure());
        }
        return addEncryptedData(outputEncryptor, new DLSequence(aSN1EncodableVector));
    }
}
