package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class CMSAlgorithmProtection extends ASN1Object {
    public static final int MAC = 2;
    public static final int SIGNATURE = 1;
    private final AlgorithmIdentifier digestAlgorithm;
    private final AlgorithmIdentifier macAlgorithm;
    private final AlgorithmIdentifier signatureAlgorithm;

    private CMSAlgorithmProtection(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("Sequence wrong size: One of signatureAlgorithm or macAlgorithm must be present");
        }
        this.digestAlgorithm = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1TaggedObject.getTagNo() == 1) {
            this.signatureAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
            this.macAlgorithm = null;
        } else {
            if (aSN1TaggedObject.getTagNo() != 2) {
                throw new IllegalArgumentException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("Unknown tag found: ")));
            }
            this.signatureAlgorithm = null;
            this.macAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
        }
    }

    public static CMSAlgorithmProtection getInstance(Object obj) {
        if (obj instanceof CMSAlgorithmProtection) {
            return (CMSAlgorithmProtection) obj;
        }
        if (obj != null) {
            return new CMSAlgorithmProtection(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public AlgorithmIdentifier getMacAlgorithm() {
        return this.macAlgorithm;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.digestAlgorithm);
        AlgorithmIdentifier algorithmIdentifier = this.signatureAlgorithm;
        if (algorithmIdentifier != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable) algorithmIdentifier));
        }
        AlgorithmIdentifier algorithmIdentifier2 = this.macAlgorithm;
        if (algorithmIdentifier2 != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, (ASN1Encodable) algorithmIdentifier2));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public CMSAlgorithmProtection(AlgorithmIdentifier algorithmIdentifier, int i2, AlgorithmIdentifier algorithmIdentifier2) {
        if (algorithmIdentifier == null || algorithmIdentifier2 == null) {
            throw new NullPointerException("AlgorithmIdentifiers cannot be null");
        }
        this.digestAlgorithm = algorithmIdentifier;
        if (i2 == 1) {
            this.signatureAlgorithm = algorithmIdentifier2;
            this.macAlgorithm = null;
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException(AbstractC0000a.m11g("Unknown type: ", i2));
            }
            this.signatureAlgorithm = null;
            this.macAlgorithm = algorithmIdentifier2;
        }
    }
}
