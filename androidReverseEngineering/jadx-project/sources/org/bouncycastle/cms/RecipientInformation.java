package org.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSEnvelopedHelper;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class RecipientInformation {
    private AuthAttributesProvider additionalData;
    protected AlgorithmIdentifier keyEncAlg;
    protected AlgorithmIdentifier messageAlgorithm;
    private RecipientOperator operator;
    private byte[] resultMac;
    protected RecipientId rid;
    protected CMSSecureReadable secureReadable;

    public RecipientInformation(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, CMSSecureReadable cMSSecureReadable, AuthAttributesProvider authAttributesProvider) {
        this.keyEncAlg = algorithmIdentifier;
        this.messageAlgorithm = algorithmIdentifier2;
        this.secureReadable = cMSSecureReadable;
        this.additionalData = authAttributesProvider;
    }

    private byte[] encodeObj(ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            return aSN1Encodable.toASN1Primitive().getEncoded();
        }
        return null;
    }

    public byte[] getContent(Recipient recipient) {
        try {
            return CMSUtils.streamToByteArray(getContentStream(recipient).getContentStream());
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to parse internal stream: ")), e2);
        }
    }

    public byte[] getContentDigest() {
        CMSSecureReadable cMSSecureReadable = this.secureReadable;
        if (cMSSecureReadable instanceof CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable) {
            return ((CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable) cMSSecureReadable).getDigest();
        }
        return null;
    }

    public CMSTypedStream getContentStream(Recipient recipient) {
        this.operator = getRecipientOperator(recipient);
        AuthAttributesProvider authAttributesProvider = this.additionalData;
        if (authAttributesProvider == null) {
            return new CMSTypedStream(this.secureReadable.getContentType(), this.operator.getInputStream(this.secureReadable.getInputStream()));
        }
        if (!authAttributesProvider.isAead()) {
            return new CMSTypedStream(this.secureReadable.getContentType(), this.secureReadable.getInputStream());
        }
        this.operator.getAADStream().write(this.additionalData.getAuthAttributes().getEncoded(ASN1Encoding.DER));
        return new CMSTypedStream(this.secureReadable.getContentType(), this.operator.getInputStream(this.secureReadable.getInputStream()));
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.secureReadable.getContentType();
    }

    public String getKeyEncryptionAlgOID() {
        return this.keyEncAlg.getAlgorithm().getId();
    }

    public byte[] getKeyEncryptionAlgParams() {
        try {
            return encodeObj(this.keyEncAlg.getParameters());
        } catch (Exception e2) {
            throw new RuntimeException(AbstractC0000a.m14j("exception getting encryption parameters ", e2));
        }
    }

    public AlgorithmIdentifier getKeyEncryptionAlgorithm() {
        return this.keyEncAlg;
    }

    public byte[] getMac() {
        if (this.resultMac == null && this.operator.isMacBased()) {
            if (this.additionalData != null) {
                try {
                    Streams.drain(this.operator.getInputStream(new ByteArrayInputStream(this.additionalData.getAuthAttributes().getEncoded(ASN1Encoding.DER))));
                } catch (IOException e2) {
                    throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("unable to drain input: ")));
                }
            }
            this.resultMac = this.operator.getMac();
        }
        return this.resultMac;
    }

    public RecipientId getRID() {
        return this.rid;
    }

    public abstract RecipientOperator getRecipientOperator(Recipient recipient);
}
