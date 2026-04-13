package org.bouncycastle.dvcs;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.dvcs.DVCSObjectIdentifiers;
import org.bouncycastle.asn1.dvcs.ServiceType;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.cms.CMSSignedData;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DVCSRequest extends DVCSMessage {
    private org.bouncycastle.asn1.dvcs.DVCSRequest asn1;
    private DVCSRequestData data;
    private DVCSRequestInfo reqInfo;

    public DVCSRequest(ContentInfo contentInfo) {
        super(contentInfo);
        DVCSRequestData cCPDRequestData;
        if (!DVCSObjectIdentifiers.id_ct_DVCSRequestData.equals((ASN1Primitive) contentInfo.getContentType())) {
            throw new DVCSConstructionException("ContentInfo not a DVCS Request");
        }
        try {
            this.asn1 = contentInfo.getContent().toASN1Primitive() instanceof ASN1Sequence ? org.bouncycastle.asn1.dvcs.DVCSRequest.getInstance(contentInfo.getContent()) : org.bouncycastle.asn1.dvcs.DVCSRequest.getInstance(ASN1OctetString.getInstance(contentInfo.getContent()).getOctets());
            DVCSRequestInfo dVCSRequestInfo = new DVCSRequestInfo(this.asn1.getRequestInformation());
            this.reqInfo = dVCSRequestInfo;
            int serviceType = dVCSRequestInfo.getServiceType();
            if (serviceType == ServiceType.CPD.getValue().intValue()) {
                cCPDRequestData = new CPDRequestData(this.asn1.getData());
            } else if (serviceType == ServiceType.VSD.getValue().intValue()) {
                cCPDRequestData = new VSDRequestData(this.asn1.getData());
            } else if (serviceType == ServiceType.VPKC.getValue().intValue()) {
                cCPDRequestData = new VPKCRequestData(this.asn1.getData());
            } else {
                if (serviceType != ServiceType.CCPD.getValue().intValue()) {
                    throw new DVCSConstructionException(AbstractC0000a.m11g("Unknown service type: ", serviceType));
                }
                cCPDRequestData = new CCPDRequestData(this.asn1.getData());
            }
            this.data = cCPDRequestData;
        } catch (Exception e2) {
            throw new DVCSConstructionException(AbstractC0000a.m9e(e2, new StringBuilder("Unable to parse content: ")), e2);
        }
    }

    @Override // org.bouncycastle.dvcs.DVCSMessage
    public ASN1Encodable getContent() {
        return this.asn1;
    }

    public DVCSRequestData getData() {
        return this.data;
    }

    public DVCSRequestInfo getRequestInfo() {
        return this.reqInfo;
    }

    public GeneralName getTransactionIdentifier() {
        return this.asn1.getTransactionIdentifier();
    }

    public DVCSRequest(CMSSignedData cMSSignedData) {
        this(SignedData.getInstance(cMSSignedData.toASN1Structure().getContent()).getEncapContentInfo());
    }
}
