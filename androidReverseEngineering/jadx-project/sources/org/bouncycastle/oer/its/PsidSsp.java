package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.OEROptional;

/* loaded from: classes.dex */
public class PsidSsp extends ASN1Object {
    private final Psid psid;
    private final ServiceSpecificPermissions ssp;

    public static class Builder {
        private Psid psid;
        private ServiceSpecificPermissions ssp;

        public PsidSsp createPsidSsp() {
            return new PsidSsp(this.psid, this.ssp);
        }

        public Builder setPsid(Psid psid) {
            this.psid = psid;
            return this;
        }

        public Builder setSsp(ServiceSpecificPermissions serviceSpecificPermissions) {
            this.ssp = serviceSpecificPermissions;
            return this;
        }
    }

    public PsidSsp(Psid psid, ServiceSpecificPermissions serviceSpecificPermissions) {
        this.psid = psid;
        this.ssp = serviceSpecificPermissions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static PsidSsp getInstance(Object obj) {
        if (obj instanceof PsidSsp) {
            return (PsidSsp) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new PsidSsp(Psid.getInstance((Object) aSN1Sequence.getObjectAt(0)), (ServiceSpecificPermissions) OEROptional.getValue(ServiceSpecificPermissions.class, aSN1Sequence.getObjectAt(1)));
    }

    public Psid getPsid() {
        return this.psid;
    }

    public ServiceSpecificPermissions getSsp() {
        return this.ssp;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.psid, OEROptional.getInstance(this.ssp));
    }
}
