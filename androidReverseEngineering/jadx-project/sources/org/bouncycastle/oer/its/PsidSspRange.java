package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.OEROptional;

/* loaded from: classes.dex */
public class PsidSspRange extends ASN1Object {
    private final ASN1Integer psid;
    private final OEROptional sspRange;

    public static class Builder {
        private ASN1Integer psid;
        private OEROptional sspRange = OEROptional.ABSENT;

        public PsidSspRange createPsidSspRange() {
            return new PsidSspRange(this.psid, this.sspRange);
        }

        public Builder setPsid(long j2) {
            this.psid = new ASN1Integer(j2);
            return this;
        }

        public Builder setSspRange(SspRange sspRange) {
            this.sspRange = OEROptional.getInstance(sspRange);
            return this;
        }

        public Builder setPsid(ASN1Integer aSN1Integer) {
            this.psid = aSN1Integer;
            return this;
        }
    }

    public PsidSspRange(ASN1Integer aSN1Integer, OEROptional oEROptional) {
        this.psid = aSN1Integer;
        this.sspRange = oEROptional;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static PsidSspRange getInstance(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PsidSspRange) {
            return (PsidSspRange) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new PsidSspRange(ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)), OEROptional.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public ASN1Integer getPsid() {
        return this.psid;
    }

    public OEROptional getSspRange() {
        return this.sspRange;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.psid);
        OEROptional oEROptional = this.sspRange;
        if (oEROptional != null) {
            aSN1EncodableVector.add(oEROptional);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public PsidSspRange(ASN1Integer aSN1Integer, SspRange sspRange) {
        this.psid = aSN1Integer;
        this.sspRange = OEROptional.getInstance(sspRange);
    }
}
