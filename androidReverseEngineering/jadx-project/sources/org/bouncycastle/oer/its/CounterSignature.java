package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class CounterSignature extends ASN1Object {
    private final Uint8 protocolVersion;
    private final SignedData signedData;

    public CounterSignature(Uint8 uint8, SignedData signedData) {
        this.protocolVersion = uint8;
        this.signedData = signedData;
    }

    public CounterSignature getInstance(Object obj) {
        if (obj instanceof CounterSignature) {
            return (CounterSignature) obj;
        }
        Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(obj).iterator();
        return new CounterSignature(Uint8.getInstance(it.next()), SignedData.getInstance(it.next()));
    }

    public Uint8 getProtocolVersion() {
        return this.protocolVersion;
    }

    public SignedData getSignedData() {
        return this.signedData;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.protocolVersion, this.signedData);
    }
}
