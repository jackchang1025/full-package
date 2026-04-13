package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class ContributedExtensionBlock extends ASN1Object {
    private final HeaderInfoContributorId contributorId;
    private final List<EtsiOriginatingHeaderInfoExtension> extns;

    public ContributedExtensionBlock(HeaderInfoContributorId headerInfoContributorId, List<EtsiOriginatingHeaderInfoExtension> list) {
        this.contributorId = headerInfoContributorId;
        this.extns = list;
    }

    public static ContributedExtensionBlock getInstance(Object obj) {
        if (obj instanceof ContributedExtensionBlock) {
            return (ContributedExtensionBlock) obj;
        }
        Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(obj).iterator();
        HeaderInfoContributorId headerInfoContributorId = HeaderInfoContributorId.getInstance((Object) it.next());
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            arrayList.add(EtsiOriginatingHeaderInfoExtension.getInstance(it.next()));
        }
        return new ContributedExtensionBlock(headerInfoContributorId, arrayList);
    }

    public HeaderInfoContributorId getContributorId() {
        return this.contributorId;
    }

    public List<EtsiOriginatingHeaderInfoExtension> getExtns() {
        return this.extns;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.contributorId, Utils.toSequence(this.extns));
    }
}
