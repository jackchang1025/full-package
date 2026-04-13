package org.bouncycastle.tsp.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.Evidence;
import org.bouncycastle.asn1.cms.TimeStampAndCRL;
import org.bouncycastle.asn1.cms.TimeStampTokenEvidence;
import org.bouncycastle.asn1.cms.TimeStampedData;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.tsp.TimeStampToken;

/* loaded from: classes.dex */
public class CMSTimeStampedData {
    private ContentInfo contentInfo;
    private TimeStampedData timeStampedData;
    private TimeStampDataUtil util;

    public CMSTimeStampedData(InputStream inputStream) {
        try {
            initialize(ContentInfo.getInstance(new ASN1InputStream(inputStream).readObject()));
        } catch (ClassCastException e2) {
            throw new IOException("Malformed content: " + e2);
        } catch (IllegalArgumentException e3) {
            throw new IOException("Malformed content: " + e3);
        }
    }

    private void initialize(ContentInfo contentInfo) {
        this.contentInfo = contentInfo;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = CMSObjectIdentifiers.timestampedData;
        if (!aSN1ObjectIdentifier.equals((ASN1Primitive) contentInfo.getContentType())) {
            throw new IllegalArgumentException("Malformed content - type must be " + aSN1ObjectIdentifier.getId());
        }
        TimeStampedData timeStampedData = TimeStampedData.getInstance(contentInfo.getContent());
        this.timeStampedData = timeStampedData;
        this.util = new TimeStampDataUtil(timeStampedData);
    }

    public CMSTimeStampedData addTimeStamp(TimeStampToken timeStampToken) {
        TimeStampAndCRL[] timeStamps = this.util.getTimeStamps();
        TimeStampAndCRL[] timeStampAndCRLArr = new TimeStampAndCRL[timeStamps.length + 1];
        System.arraycopy(timeStamps, 0, timeStampAndCRLArr, 0, timeStamps.length);
        timeStampAndCRLArr[timeStamps.length] = new TimeStampAndCRL(timeStampToken.toCMSSignedData().toASN1Structure());
        return new CMSTimeStampedData(new ContentInfo(CMSObjectIdentifiers.timestampedData, new TimeStampedData(this.timeStampedData.getDataUri(), this.timeStampedData.getMetaData(), this.timeStampedData.getContent(), new Evidence(new TimeStampTokenEvidence(timeStampAndCRLArr)))));
    }

    public byte[] calculateNextHash(DigestCalculator digestCalculator) {
        return this.util.calculateNextHash(digestCalculator);
    }

    public byte[] getContent() {
        if (this.timeStampedData.getContent() != null) {
            return this.timeStampedData.getContent().getOctets();
        }
        return null;
    }

    public URI getDataUri() {
        DERIA5String dataUri = this.timeStampedData.getDataUri();
        if (dataUri != null) {
            return new URI(dataUri.getString());
        }
        return null;
    }

    public byte[] getEncoded() {
        return this.contentInfo.getEncoded();
    }

    public String getFileName() {
        return this.util.getFileName();
    }

    public String getMediaType() {
        return this.util.getMediaType();
    }

    public DigestCalculator getMessageImprintDigestCalculator(DigestCalculatorProvider digestCalculatorProvider) {
        return this.util.getMessageImprintDigestCalculator(digestCalculatorProvider);
    }

    public AttributeTable getOtherMetaData() {
        return this.util.getOtherMetaData();
    }

    public TimeStampToken[] getTimeStampTokens() {
        return this.util.getTimeStampTokens();
    }

    public void initialiseMessageImprintDigestCalculator(DigestCalculator digestCalculator) {
        this.util.initialiseMessageImprintDigestCalculator(digestCalculator);
    }

    public void validate(DigestCalculatorProvider digestCalculatorProvider, byte[] bArr) {
        this.util.validate(digestCalculatorProvider, bArr);
    }

    public CMSTimeStampedData(ContentInfo contentInfo) {
        initialize(contentInfo);
    }

    public void validate(DigestCalculatorProvider digestCalculatorProvider, byte[] bArr, TimeStampToken timeStampToken) {
        this.util.validate(digestCalculatorProvider, bArr, timeStampToken);
    }

    public CMSTimeStampedData(byte[] bArr) {
        this(new ByteArrayInputStream(bArr));
    }
}
