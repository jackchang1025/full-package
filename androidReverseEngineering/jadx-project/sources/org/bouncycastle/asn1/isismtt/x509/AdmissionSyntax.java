package org.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.GeneralName;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class AdmissionSyntax extends ASN1Object {
    private GeneralName admissionAuthority;
    private ASN1Sequence contentsOfAdmissions;

    private AdmissionSyntax(ASN1Sequence aSN1Sequence) {
        ASN1Encodable objectAt;
        int size = aSN1Sequence.size();
        if (size == 1) {
            objectAt = aSN1Sequence.getObjectAt(0);
        } else {
            if (size != 2) {
                throw new IllegalArgumentException(AbstractC0413b.m1012f(aSN1Sequence, new StringBuilder("Bad sequence size: ")));
            }
            this.admissionAuthority = GeneralName.getInstance(aSN1Sequence.getObjectAt(0));
            objectAt = aSN1Sequence.getObjectAt(1);
        }
        this.contentsOfAdmissions = ASN1Sequence.getInstance(objectAt);
    }

    public static AdmissionSyntax getInstance(Object obj) {
        if (obj == null || (obj instanceof AdmissionSyntax)) {
            return (AdmissionSyntax) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new AdmissionSyntax((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
    }

    public GeneralName getAdmissionAuthority() {
        return this.admissionAuthority;
    }

    public Admissions[] getContentsOfAdmissions() {
        Admissions[] admissionsArr = new Admissions[this.contentsOfAdmissions.size()];
        Enumeration objects = this.contentsOfAdmissions.getObjects();
        int i2 = 0;
        while (objects.hasMoreElements()) {
            admissionsArr[i2] = Admissions.getInstance(objects.nextElement());
            i2++;
        }
        return admissionsArr;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        GeneralName generalName = this.admissionAuthority;
        if (generalName != null) {
            aSN1EncodableVector.add(generalName);
        }
        aSN1EncodableVector.add(this.contentsOfAdmissions);
        return new DERSequence(aSN1EncodableVector);
    }

    public AdmissionSyntax(GeneralName generalName, ASN1Sequence aSN1Sequence) {
        this.admissionAuthority = generalName;
        this.contentsOfAdmissions = aSN1Sequence;
    }
}
