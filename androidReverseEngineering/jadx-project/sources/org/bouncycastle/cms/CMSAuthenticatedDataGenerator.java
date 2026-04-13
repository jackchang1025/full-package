package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AuthenticatedData;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.util.io.TeeOutputStream;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSAuthenticatedDataGenerator extends CMSAuthenticatedGenerator {
    public CMSAuthenticatedData generate(CMSTypedData cMSTypedData, MacCalculator macCalculator) {
        return generate(cMSTypedData, macCalculator, null);
    }

    public CMSAuthenticatedData generate(CMSTypedData cMSTypedData, MacCalculator macCalculator, final DigestCalculator digestCalculator) {
        AuthenticatedData authenticatedData;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator it = this.recipientInfoGenerators.iterator();
        while (it.hasNext()) {
            aSN1EncodableVector.add(((RecipientInfoGenerator) it.next()).generate(macCalculator.getKey()));
        }
        if (digestCalculator != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TeeOutputStream teeOutputStream = new TeeOutputStream(digestCalculator.getOutputStream(), byteArrayOutputStream);
                cMSTypedData.write(teeOutputStream);
                teeOutputStream.close();
                BEROctetString bEROctetString = new BEROctetString(byteArrayOutputStream.toByteArray());
                Map unmodifiableMap = Collections.unmodifiableMap(getBaseParameters(cMSTypedData.getContentType(), digestCalculator.getAlgorithmIdentifier(), macCalculator.getAlgorithmIdentifier(), digestCalculator.getDigest()));
                if (this.authGen == null) {
                    this.authGen = new DefaultAuthenticatedAttributeTableGenerator();
                }
                DERSet dERSet = new DERSet(this.authGen.getAttributes(unmodifiableMap).toASN1EncodableVector());
                try {
                    OutputStream outputStream = macCalculator.getOutputStream();
                    outputStream.write(dERSet.getEncoded(ASN1Encoding.DER));
                    outputStream.close();
                    authenticatedData = new AuthenticatedData(this.originatorInfo, new DERSet(aSN1EncodableVector), macCalculator.getAlgorithmIdentifier(), digestCalculator.getAlgorithmIdentifier(), new ContentInfo(cMSTypedData.getContentType(), bEROctetString), dERSet, new DEROctetString(macCalculator.getMac()), this.unauthGen != null ? new BERSet(this.unauthGen.getAttributes(unmodifiableMap).toASN1EncodableVector()) : null);
                } catch (IOException e2) {
                    throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to perform MAC calculation: ")), e2);
                }
            } catch (IOException e3) {
                throw new CMSException(AbstractC0000a.m8d(e3, new StringBuilder("unable to perform digest calculation: ")), e3);
            }
        } else {
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                TeeOutputStream teeOutputStream2 = new TeeOutputStream(byteArrayOutputStream2, macCalculator.getOutputStream());
                cMSTypedData.write(teeOutputStream2);
                teeOutputStream2.close();
                BEROctetString bEROctetString2 = new BEROctetString(byteArrayOutputStream2.toByteArray());
                authenticatedData = new AuthenticatedData(this.originatorInfo, new DERSet(aSN1EncodableVector), macCalculator.getAlgorithmIdentifier(), null, new ContentInfo(cMSTypedData.getContentType(), bEROctetString2), null, new DEROctetString(macCalculator.getMac()), this.unauthGen != null ? new BERSet(this.unauthGen.getAttributes(Collections.EMPTY_MAP).toASN1EncodableVector()) : null);
            } catch (IOException e4) {
                throw new CMSException(AbstractC0000a.m8d(e4, new StringBuilder("unable to perform MAC calculation: ")), e4);
            }
        }
        return new CMSAuthenticatedData(new ContentInfo(CMSObjectIdentifiers.authenticatedData, authenticatedData), new DigestCalculatorProvider() { // from class: org.bouncycastle.cms.CMSAuthenticatedDataGenerator.1
            @Override // org.bouncycastle.operator.DigestCalculatorProvider
            public DigestCalculator get(AlgorithmIdentifier algorithmIdentifier) {
                return digestCalculator;
            }
        });
    }
}
