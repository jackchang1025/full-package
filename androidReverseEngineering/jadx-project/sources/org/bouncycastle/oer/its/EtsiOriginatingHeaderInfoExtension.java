package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class EtsiOriginatingHeaderInfoExtension extends ASN1Object {
    private final HeaderInfoContributorId etsiHeaderInfoContributorId;
    private final ASN1OctetString extension;

    public EtsiOriginatingHeaderInfoExtension(HeaderInfoContributorId headerInfoContributorId, ASN1OctetString aSN1OctetString) {
        this.etsiHeaderInfoContributorId = headerInfoContributorId;
        this.extension = aSN1OctetString;
    }

    public static EtsiOriginatingHeaderInfoExtension getInstance(Object obj) {
        if (obj instanceof EtsiOriginatingHeaderInfoExtension) {
            return (EtsiOriginatingHeaderInfoExtension) obj;
        }
        Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(obj).iterator();
        HeaderInfoContributorId headerInfoContributorId = HeaderInfoContributorId.getInstance((Object) it.next());
        return it.hasNext() ? new EtsiOriginatingHeaderInfoExtension(headerInfoContributorId, ASN1OctetString.getInstance(it.next())) : new EtsiOriginatingHeaderInfoExtension(headerInfoContributorId, null);
    }

    public HeaderInfoContributorId getEtsiHeaderInfoContributorId() {
        return this.etsiHeaderInfoContributorId;
    }

    public ASN1OctetString getExtension() {
        return this.extension;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.etsiHeaderInfoContributorId, this.extension);
    }
}
