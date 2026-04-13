package org.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.DigestedData;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Encodable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSDigestedData implements Encodable {
    private ContentInfo contentInfo;
    private DigestedData digestedData;

    public CMSDigestedData(InputStream inputStream) {
        this(CMSUtils.readContentInfo(inputStream));
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.contentInfo.getContentType();
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestedData.getDigestAlgorithm();
    }

    public CMSProcessable getDigestedContent() {
        ContentInfo encapContentInfo = this.digestedData.getEncapContentInfo();
        try {
            return new CMSProcessableByteArray(encapContentInfo.getContentType(), ((ASN1OctetString) encapContentInfo.getContent()).getOctets());
        } catch (Exception e2) {
            throw new CMSException("exception reading digested stream.", e2);
        }
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return this.contentInfo.getEncoded();
    }

    public ContentInfo toASN1Structure() {
        return this.contentInfo;
    }

    public boolean verify(DigestCalculatorProvider digestCalculatorProvider) {
        try {
            ContentInfo encapContentInfo = this.digestedData.getEncapContentInfo();
            DigestCalculator digestCalculator = digestCalculatorProvider.get(this.digestedData.getDigestAlgorithm());
            digestCalculator.getOutputStream().write(((ASN1OctetString) encapContentInfo.getContent()).getOctets());
            return Arrays.areEqual(this.digestedData.getDigest(), digestCalculator.getDigest());
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("unable process content: ")), e2);
        } catch (OperatorCreationException e3) {
            throw new CMSException("unable to create digest calculator: " + e3.getMessage(), e3);
        }
    }

    public CMSDigestedData(ContentInfo contentInfo) {
        this.contentInfo = contentInfo;
        try {
            this.digestedData = DigestedData.getInstance(contentInfo.getContent());
        } catch (ClassCastException e2) {
            throw new CMSException("Malformed content.", e2);
        } catch (IllegalArgumentException e3) {
            throw new CMSException("Malformed content.", e3);
        }
    }

    public CMSDigestedData(byte[] bArr) {
        this(CMSUtils.readContentInfo(bArr));
    }
}
