package org.bouncycastle.tsp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.asn1.cmp.PKIFreeText;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.tsp.TimeStampResp;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class TimeStampResponse {
    TimeStampResp resp;
    TimeStampToken timeStampToken;

    public TimeStampResponse(InputStream inputStream) {
        this(readTimeStampResp(inputStream));
    }

    private static TimeStampResp readTimeStampResp(InputStream inputStream) {
        try {
            return TimeStampResp.getInstance(new ASN1InputStream(inputStream).readObject());
        } catch (ClassCastException e2) {
            throw new TSPException("malformed timestamp response: " + e2, e2);
        } catch (IllegalArgumentException e3) {
            throw new TSPException("malformed timestamp response: " + e3, e3);
        }
    }

    public byte[] getEncoded() {
        return this.resp.getEncoded();
    }

    public PKIFailureInfo getFailInfo() {
        if (this.resp.getStatus().getFailInfo() != null) {
            return new PKIFailureInfo(this.resp.getStatus().getFailInfo());
        }
        return null;
    }

    public int getStatus() {
        return this.resp.getStatus().getStatus().intValue();
    }

    public String getStatusString() {
        if (this.resp.getStatus().getStatusString() == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        PKIFreeText statusString = this.resp.getStatus().getStatusString();
        for (int i2 = 0; i2 != statusString.size(); i2++) {
            stringBuffer.append(statusString.getStringAtUTF8(i2).getString());
        }
        return stringBuffer.toString();
    }

    public TimeStampToken getTimeStampToken() {
        return this.timeStampToken;
    }

    public void validate(TimeStampRequest timeStampRequest) {
        TimeStampToken timeStampToken = getTimeStampToken();
        if (timeStampToken == null) {
            if (getStatus() == 0 || getStatus() == 1) {
                throw new TSPValidationException("no time stamp token found and one expected.");
            }
            return;
        }
        TimeStampTokenInfo timeStampInfo = timeStampToken.getTimeStampInfo();
        if (timeStampRequest.getNonce() != null && !timeStampRequest.getNonce().equals(timeStampInfo.getNonce())) {
            throw new TSPValidationException("response contains wrong nonce value.");
        }
        if (getStatus() != 0 && getStatus() != 1) {
            throw new TSPValidationException("time stamp token found in failed request.");
        }
        if (!Arrays.constantTimeAreEqual(timeStampRequest.getMessageImprintDigest(), timeStampInfo.getMessageImprintDigest())) {
            throw new TSPValidationException("response for different message imprint digest.");
        }
        if (!timeStampInfo.getMessageImprintAlgOID().equals((ASN1Primitive) timeStampRequest.getMessageImprintAlgOID())) {
            throw new TSPValidationException("response for different message imprint algorithm.");
        }
        Attribute attribute = timeStampToken.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
        Attribute attribute2 = timeStampToken.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
        if (attribute == null && attribute2 == null) {
            throw new TSPValidationException("no signing certificate attribute present.");
        }
        if (timeStampRequest.getReqPolicy() != null && !timeStampRequest.getReqPolicy().equals((ASN1Primitive) timeStampInfo.getPolicy())) {
            throw new TSPValidationException("TSA policy wrong for request.");
        }
    }

    public TimeStampResponse(DLSequence dLSequence) {
        try {
            this.resp = TimeStampResp.getInstance(dLSequence);
            this.timeStampToken = new TimeStampToken(ContentInfo.getInstance(dLSequence.getObjectAt(1)));
        } catch (ClassCastException e2) {
            throw new TSPException("malformed timestamp response: " + e2, e2);
        } catch (IllegalArgumentException e3) {
            throw new TSPException("malformed timestamp response: " + e3, e3);
        }
    }

    public byte[] getEncoded(String str) {
        return (ASN1Encoding.DL.equals(str) ? this.timeStampToken == null ? new DLSequence(this.resp.getStatus()) : new DLSequence(new ASN1Encodable[]{this.resp.getStatus(), this.timeStampToken.toCMSSignedData().toASN1Structure()}) : this.resp).getEncoded(str);
    }

    public TimeStampResponse(TimeStampResp timeStampResp) {
        this.resp = timeStampResp;
        if (timeStampResp.getTimeStampToken() != null) {
            this.timeStampToken = new TimeStampToken(timeStampResp.getTimeStampToken());
        }
    }

    public TimeStampResponse(byte[] bArr) {
        this(new ByteArrayInputStream(bArr));
    }
}
