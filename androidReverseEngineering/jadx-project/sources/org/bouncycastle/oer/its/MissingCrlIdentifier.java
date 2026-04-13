package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.HashedId;

/* loaded from: classes.dex */
public class MissingCrlIdentifier extends ASN1Object {
    private final HashedId.HashedId3 cracaId;
    private final CrlSeries crlSeries;

    public MissingCrlIdentifier(HashedId.HashedId3 hashedId3, CrlSeries crlSeries) {
        this.cracaId = hashedId3;
        this.crlSeries = crlSeries;
    }

    public static MissingCrlIdentifier getInstance(Object obj) {
        if (obj instanceof MissingCrlIdentifier) {
            return (MissingCrlIdentifier) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new MissingCrlIdentifier((HashedId.HashedId3) HashedId.getInstance(aSN1Sequence.getObjectAt(0)), CrlSeries.getInstance((Object) aSN1Sequence.getObjectAt(1)));
    }

    public HashedId.HashedId3 getCracaId() {
        return this.cracaId;
    }

    public CrlSeries getCrlSeries() {
        return this.crlSeries;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.cracaId, this.crlSeries);
    }
}
