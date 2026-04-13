package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class SequenceOfPsidGroupPermissions extends ASN1Object {
    private final List<PsidGroupPermissions> groupPermissions;

    public static class Builder {
        private List<PsidGroupPermissions> groupPermissions = new ArrayList();

        public Builder addGroupPermission(PsidGroupPermissions... psidGroupPermissionsArr) {
            this.groupPermissions.addAll(Arrays.asList(psidGroupPermissionsArr));
            return this;
        }

        public SequenceOfPsidGroupPermissions createSequenceOfPsidGroupPermissions() {
            return new SequenceOfPsidGroupPermissions(this.groupPermissions);
        }

        public Builder setGroupPermissions(List<PsidGroupPermissions> list) {
            this.groupPermissions.addAll(list);
            return this;
        }
    }

    public SequenceOfPsidGroupPermissions(List<PsidGroupPermissions> list) {
        this.groupPermissions = Collections.unmodifiableList(list);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SequenceOfPsidGroupPermissions getInstance(Object obj) {
        if (obj instanceof SequenceOfPsidGroupPermissions) {
            return (SequenceOfPsidGroupPermissions) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        ArrayList arrayList = new ArrayList();
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            arrayList.add(PsidGroupPermissions.getInstance(objects.nextElement()));
        }
        return new Builder().setGroupPermissions(arrayList).createSequenceOfPsidGroupPermissions();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.groupPermissions.toArray(new PsidGroupPermissions[0]));
    }
}
