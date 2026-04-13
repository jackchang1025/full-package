package org.bouncycastle.mime.smime;

import java.io.InputStream;
import java.util.HashMap;
import org.bouncycastle.cms.CMSEnvelopedDataParser;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.OriginatorInformation;
import org.bouncycastle.cms.RecipientInformationStore;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.mime.ConstantMimeContext;
import org.bouncycastle.mime.Headers;
import org.bouncycastle.mime.MimeContext;
import org.bouncycastle.mime.MimeIOException;
import org.bouncycastle.mime.MimeParserContext;
import org.bouncycastle.mime.MimeParserListener;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public abstract class SMimeParserListener implements MimeParserListener {
    private DigestCalculator[] digestCalculators;
    private SMimeMultipartContext parent;

    public void content(MimeParserContext mimeParserContext, Headers headers, InputStream inputStream) {
        throw new IllegalStateException("content handling not implemented");
    }

    @Override // org.bouncycastle.mime.MimeParserListener
    public MimeContext createContext(MimeParserContext mimeParserContext, Headers headers) {
        if (!headers.isMultipart()) {
            return new ConstantMimeContext();
        }
        SMimeMultipartContext sMimeMultipartContext = new SMimeMultipartContext(mimeParserContext, headers);
        this.parent = sMimeMultipartContext;
        this.digestCalculators = sMimeMultipartContext.getDigestCalculators();
        return this.parent;
    }

    public void envelopedData(MimeParserContext mimeParserContext, Headers headers, OriginatorInformation originatorInformation, RecipientInformationStore recipientInformationStore) {
        throw new IllegalStateException("envelopedData handling not implemented");
    }

    @Override // org.bouncycastle.mime.MimeParserListener
    public void object(MimeParserContext mimeParserContext, Headers headers, InputStream inputStream) {
        try {
            if (!headers.getContentType().equals("application/pkcs7-signature") && !headers.getContentType().equals("application/x-pkcs7-signature")) {
                if (!headers.getContentType().equals("application/pkcs7-mime") && !headers.getContentType().equals("application/x-pkcs7-mime")) {
                    content(mimeParserContext, headers, inputStream);
                    return;
                }
                CMSEnvelopedDataParser cMSEnvelopedDataParser = new CMSEnvelopedDataParser(inputStream);
                envelopedData(mimeParserContext, headers, cMSEnvelopedDataParser.getOriginatorInfo(), cMSEnvelopedDataParser.getRecipientInfos());
                cMSEnvelopedDataParser.close();
                return;
            }
            HashMap hashMap = new HashMap();
            int i2 = 0;
            while (true) {
                DigestCalculator[] digestCalculatorArr = this.digestCalculators;
                if (i2 == digestCalculatorArr.length) {
                    CMSSignedData cMSSignedData = new CMSSignedData(hashMap, Streams.readAll(inputStream));
                    signedData(mimeParserContext, headers, cMSSignedData.getCertificates(), cMSSignedData.getCRLs(), cMSSignedData.getAttributeCertificates(), cMSSignedData.getSignerInfos());
                    return;
                } else {
                    digestCalculatorArr[i2].getOutputStream().close();
                    hashMap.put(this.digestCalculators[i2].getAlgorithmIdentifier().getAlgorithm(), this.digestCalculators[i2].getDigest());
                    i2++;
                }
            }
        } catch (CMSException e2) {
            throw new MimeIOException("CMS failure: " + e2.getMessage(), e2);
        }
    }

    public void signedData(MimeParserContext mimeParserContext, Headers headers, Store store, Store store2, Store store3, SignerInformationStore signerInformationStore) {
        throw new IllegalStateException("signedData handling not implemented");
    }
}
