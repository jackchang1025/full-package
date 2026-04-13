package org.bouncycastle.dvcs;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.dvcs.DVCSObjectIdentifiers;
import org.bouncycastle.cms.CMSSignedData;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DVCSResponse extends DVCSMessage {
    private org.bouncycastle.asn1.dvcs.DVCSResponse asn1;

    public DVCSResponse(ContentInfo contentInfo) {
        super(contentInfo);
        if (!DVCSObjectIdentifiers.id_ct_DVCSResponseData.equals((ASN1Primitive) contentInfo.getContentType())) {
            throw new DVCSConstructionException("ContentInfo not a DVCS Response");
        }
        try {
            this.asn1 = contentInfo.getContent().toASN1Primitive() instanceof ASN1Sequence ? org.bouncycastle.asn1.dvcs.DVCSResponse.getInstance(contentInfo.getContent()) : org.bouncycastle.asn1.dvcs.DVCSResponse.getInstance(ASN1OctetString.getInstance(contentInfo.getContent()).getOctets());
        } catch (Exception e2) {
            throw new DVCSConstructionException(AbstractC0000a.m9e(e2, new StringBuilder("Unable to parse content: ")), e2);
        }
    }

    @Override // org.bouncycastle.dvcs.DVCSMessage
    public ASN1Encodable getContent() {
        return this.asn1;
    }

    public DVCSResponse(CMSSignedData cMSSignedData) {
        this(SignedData.getInstance(cMSSignedData.toASN1Structure().getContent()).getEncapContentInfo());
    }
}
