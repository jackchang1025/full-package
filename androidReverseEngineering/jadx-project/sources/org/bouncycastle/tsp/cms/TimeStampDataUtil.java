package org.bouncycastle.tsp.cms;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.TimeStampAndCRL;
import org.bouncycastle.asn1.cms.TimeStampedData;
import org.bouncycastle.asn1.cms.TimeStampedDataParser;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class TimeStampDataUtil {
    private final MetaDataUtil metaDataUtil;
    private final TimeStampAndCRL[] timeStamps;

    public TimeStampDataUtil(TimeStampedData timeStampedData) {
        this.metaDataUtil = new MetaDataUtil(timeStampedData.getMetaData());
        this.timeStamps = timeStampedData.getTemporalEvidence().getTstEvidence().toTimeStampAndCRLArray();
    }

    private void compareDigest(TimeStampToken timeStampToken, byte[] bArr) {
        if (!Arrays.areEqual(bArr, timeStampToken.getTimeStampInfo().getMessageImprintDigest())) {
            throw new ImprintDigestInvalidException("hash calculated is different from MessageImprintDigest found in TimeStampToken", timeStampToken);
        }
    }

    public byte[] calculateNextHash(DigestCalculator digestCalculator) {
        TimeStampAndCRL timeStampAndCRL = this.timeStamps[r0.length - 1];
        OutputStream outputStream = digestCalculator.getOutputStream();
        try {
            outputStream.write(timeStampAndCRL.getEncoded(ASN1Encoding.DER));
            outputStream.close();
            return digestCalculator.getDigest();
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("exception calculating hash: ")), e2);
        }
    }

    public String getFileName() {
        return this.metaDataUtil.getFileName();
    }

    public String getMediaType() {
        return this.metaDataUtil.getMediaType();
    }

    public DigestCalculator getMessageImprintDigestCalculator(DigestCalculatorProvider digestCalculatorProvider) {
        try {
            DigestCalculator digestCalculator = digestCalculatorProvider.get(new AlgorithmIdentifier(getTimeStampToken(this.timeStamps[0]).getTimeStampInfo().getMessageImprintAlgOID()));
            initialiseMessageImprintDigestCalculator(digestCalculator);
            return digestCalculator;
        } catch (CMSException e2) {
            throw new OperatorCreationException("unable to extract algorithm ID: " + e2.getMessage(), e2);
        }
    }

    public AttributeTable getOtherMetaData() {
        return new AttributeTable(this.metaDataUtil.getOtherMetaData());
    }

    public TimeStampToken getTimeStampToken(TimeStampAndCRL timeStampAndCRL) {
        try {
            return new TimeStampToken(timeStampAndCRL.getTimeStampToken());
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to parse token data: ")), e2);
        } catch (IllegalArgumentException e3) {
            throw new CMSException("token data invalid: " + e3.getMessage(), e3);
        } catch (TSPException e4) {
            if (e4.getCause() instanceof CMSException) {
                throw ((CMSException) e4.getCause());
            }
            throw new CMSException("token data invalid: " + e4.getMessage(), e4);
        }
    }

    public TimeStampToken[] getTimeStampTokens() {
        TimeStampToken[] timeStampTokenArr = new TimeStampToken[this.timeStamps.length];
        int i2 = 0;
        while (true) {
            TimeStampAndCRL[] timeStampAndCRLArr = this.timeStamps;
            if (i2 >= timeStampAndCRLArr.length) {
                return timeStampTokenArr;
            }
            timeStampTokenArr[i2] = getTimeStampToken(timeStampAndCRLArr[i2]);
            i2++;
        }
    }

    public TimeStampAndCRL[] getTimeStamps() {
        return this.timeStamps;
    }

    public void initialiseMessageImprintDigestCalculator(DigestCalculator digestCalculator) {
        this.metaDataUtil.initialiseMessageImprintDigestCalculator(digestCalculator);
    }

    public void validate(DigestCalculatorProvider digestCalculatorProvider, byte[] bArr) {
        int i2 = 0;
        while (true) {
            TimeStampAndCRL[] timeStampAndCRLArr = this.timeStamps;
            if (i2 >= timeStampAndCRLArr.length) {
                return;
            }
            try {
                TimeStampToken timeStampToken = getTimeStampToken(timeStampAndCRLArr[i2]);
                if (i2 > 0) {
                    DigestCalculator digestCalculator = digestCalculatorProvider.get(timeStampToken.getTimeStampInfo().getHashAlgorithm());
                    digestCalculator.getOutputStream().write(this.timeStamps[i2 - 1].getEncoded(ASN1Encoding.DER));
                    bArr = digestCalculator.getDigest();
                }
                compareDigest(timeStampToken, bArr);
                i2++;
            } catch (IOException e2) {
                throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("exception calculating hash: ")), e2);
            } catch (OperatorCreationException e3) {
                throw new CMSException("cannot create digest: " + e3.getMessage(), e3);
            }
        }
    }

    public TimeStampDataUtil(TimeStampedDataParser timeStampedDataParser) {
        this.metaDataUtil = new MetaDataUtil(timeStampedDataParser.getMetaData());
        this.timeStamps = timeStampedDataParser.getTemporalEvidence().getTstEvidence().toTimeStampAndCRLArray();
    }

    public void validate(DigestCalculatorProvider digestCalculatorProvider, byte[] bArr, TimeStampToken timeStampToken) {
        try {
            byte[] encoded = timeStampToken.getEncoded();
            int i2 = 0;
            while (true) {
                TimeStampAndCRL[] timeStampAndCRLArr = this.timeStamps;
                if (i2 >= timeStampAndCRLArr.length) {
                    throw new ImprintDigestInvalidException("passed in token not associated with timestamps present", timeStampToken);
                }
                try {
                    TimeStampToken timeStampToken2 = getTimeStampToken(timeStampAndCRLArr[i2]);
                    if (i2 > 0) {
                        DigestCalculator digestCalculator = digestCalculatorProvider.get(timeStampToken2.getTimeStampInfo().getHashAlgorithm());
                        digestCalculator.getOutputStream().write(this.timeStamps[i2 - 1].getEncoded(ASN1Encoding.DER));
                        bArr = digestCalculator.getDigest();
                    }
                    compareDigest(timeStampToken2, bArr);
                    if (Arrays.areEqual(timeStampToken2.getEncoded(), encoded)) {
                        return;
                    } else {
                        i2++;
                    }
                } catch (IOException e2) {
                    throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("exception calculating hash: ")), e2);
                } catch (OperatorCreationException e3) {
                    throw new CMSException("cannot create digest: " + e3.getMessage(), e3);
                }
            }
        } catch (IOException e4) {
            throw new CMSException(AbstractC0000a.m8d(e4, new StringBuilder("exception encoding timeStampToken: ")), e4);
        }
    }
}
