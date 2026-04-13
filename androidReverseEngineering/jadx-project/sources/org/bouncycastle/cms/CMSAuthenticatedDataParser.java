package org.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OctetStringParser;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.AuthenticatedDataParser;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.ContentInfoParser;
import org.bouncycastle.asn1.cms.OriginatorInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSEnvelopedHelper;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSAuthenticatedDataParser extends CMSContentInfoParser {
    private boolean authAttrNotRead;
    private ASN1Set authAttrSet;
    private AttributeTable authAttrs;
    AuthenticatedDataParser authData;
    private byte[] mac;
    private AlgorithmIdentifier macAlg;
    private OriginatorInformation originatorInfo;
    RecipientInformationStore recipientInfoStore;
    private boolean unauthAttrNotRead;
    private AttributeTable unauthAttrs;

    public CMSAuthenticatedDataParser(InputStream inputStream) {
        this(inputStream, (DigestCalculatorProvider) null);
    }

    private byte[] encodeObj(ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            return aSN1Encodable.toASN1Primitive().getEncoded();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ASN1Set getAuthAttrSet() {
        if (this.authAttrs == null && this.authAttrNotRead) {
            ASN1SetParser authAttrs = this.authData.getAuthAttrs();
            if (authAttrs != null) {
                this.authAttrSet = (ASN1Set) authAttrs.toASN1Primitive();
            }
            this.authAttrNotRead = false;
        }
        return this.authAttrSet;
    }

    public AttributeTable getAuthAttrs() {
        ASN1Set authAttrSet;
        if (this.authAttrs == null && this.authAttrNotRead && (authAttrSet = getAuthAttrSet()) != null) {
            this.authAttrs = new AttributeTable(authAttrSet);
        }
        return this.authAttrs;
    }

    public byte[] getContentDigest() {
        AttributeTable attributeTable = this.authAttrs;
        if (attributeTable != null) {
            return ASN1OctetString.getInstance(attributeTable.get(CMSAttributes.messageDigest).getAttrValues().getObjectAt(0)).getOctets();
        }
        return null;
    }

    public byte[] getMac() {
        if (this.mac == null) {
            getAuthAttrs();
            this.mac = this.authData.getMac().getOctets();
        }
        return Arrays.clone(this.mac);
    }

    public String getMacAlgOID() {
        return this.macAlg.getAlgorithm().toString();
    }

    public byte[] getMacAlgParams() {
        try {
            return encodeObj(this.macAlg.getParameters());
        } catch (Exception e2) {
            throw new RuntimeException(AbstractC0000a.m14j("exception getting encryption parameters ", e2));
        }
    }

    public AlgorithmIdentifier getMacAlgorithm() {
        return this.macAlg;
    }

    public OriginatorInformation getOriginatorInfo() {
        return this.originatorInfo;
    }

    public RecipientInformationStore getRecipientInfos() {
        return this.recipientInfoStore;
    }

    public AttributeTable getUnauthAttrs() {
        if (this.unauthAttrs == null && this.unauthAttrNotRead) {
            ASN1SetParser unauthAttrs = this.authData.getUnauthAttrs();
            this.unauthAttrNotRead = false;
            if (unauthAttrs != null) {
                ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                while (true) {
                    ASN1Encodable readObject = unauthAttrs.readObject();
                    if (readObject == null) {
                        break;
                    }
                    aSN1EncodableVector.add(((ASN1SequenceParser) readObject).toASN1Primitive());
                }
                this.unauthAttrs = new AttributeTable(new DERSet(aSN1EncodableVector));
            }
        }
        return this.unauthAttrs;
    }

    public CMSAuthenticatedDataParser(InputStream inputStream, DigestCalculatorProvider digestCalculatorProvider) {
        super(inputStream);
        this.authAttrNotRead = true;
        AuthenticatedDataParser authenticatedDataParser = new AuthenticatedDataParser((ASN1SequenceParser) this._contentInfo.getContent(16));
        this.authData = authenticatedDataParser;
        OriginatorInfo originatorInfo = authenticatedDataParser.getOriginatorInfo();
        if (originatorInfo != null) {
            this.originatorInfo = new OriginatorInformation(originatorInfo);
        }
        ASN1Set aSN1Set = ASN1Set.getInstance(this.authData.getRecipientInfos().toASN1Primitive());
        this.macAlg = this.authData.getMacAlgorithm();
        AlgorithmIdentifier digestAlgorithm = this.authData.getDigestAlgorithm();
        if (digestAlgorithm == null) {
            ContentInfoParser encapsulatedContentInfo = this.authData.getEncapsulatedContentInfo();
            this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(aSN1Set, this.macAlg, new CMSEnvelopedHelper.CMSAuthenticatedSecureReadable(this.macAlg, encapsulatedContentInfo.getContentType(), new CMSProcessableInputStream(((ASN1OctetStringParser) encapsulatedContentInfo.getContent(4)).getOctetStream())));
            return;
        }
        if (digestCalculatorProvider == null) {
            throw new CMSException("a digest calculator provider is required if authenticated attributes are present");
        }
        ContentInfoParser encapsulatedContentInfo2 = this.authData.getEncapsulatedContentInfo();
        try {
            this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(aSN1Set, this.macAlg, new CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable(digestCalculatorProvider.get(digestAlgorithm), encapsulatedContentInfo2.getContentType(), new CMSProcessableInputStream(((ASN1OctetStringParser) encapsulatedContentInfo2.getContent(4)).getOctetStream())), new AuthAttributesProvider() { // from class: org.bouncycastle.cms.CMSAuthenticatedDataParser.1
                @Override // org.bouncycastle.cms.AuthAttributesProvider
                public ASN1Set getAuthAttributes() {
                    try {
                        return CMSAuthenticatedDataParser.this.getAuthAttrSet();
                    } catch (IOException unused) {
                        throw new IllegalStateException("can't parse authenticated attributes!");
                    }
                }

                @Override // org.bouncycastle.cms.AuthAttributesProvider
                public boolean isAead() {
                    return false;
                }
            });
        } catch (OperatorCreationException e2) {
            throw new CMSException("unable to create digest calculator: " + e2.getMessage(), e2);
        }
    }

    public CMSAuthenticatedDataParser(byte[] bArr) {
        this(new ByteArrayInputStream(bArr));
    }

    public CMSAuthenticatedDataParser(byte[] bArr, DigestCalculatorProvider digestCalculatorProvider) {
        this(new ByteArrayInputStream(bArr), digestCalculatorProvider);
    }
}
