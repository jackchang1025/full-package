package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class ValidityPeriod extends ASN1Object {
    private final Duration duration;
    private final ASN1Integer time32;

    public static class Builder {
        private Duration duration;
        private ASN1Integer time32;

        public ValidityPeriod createValidityPeriod() {
            return new ValidityPeriod(this.time32, this.duration);
        }

        public Builder setDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder setTime32(ASN1Integer aSN1Integer) {
            this.time32 = aSN1Integer;
            return this;
        }
    }

    public ValidityPeriod(ASN1Integer aSN1Integer, Duration duration) {
        this.time32 = aSN1Integer;
        this.duration = duration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ValidityPeriod getInstance(Object obj) {
        if (obj instanceof ValidityPeriod) {
            return (ValidityPeriod) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new Builder().setTime32(ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0))).setDuration(Duration.getInstance(aSN1Sequence.getObjectAt(1))).createValidityPeriod();
    }

    public Duration getDuration() {
        return this.duration;
    }

    public ASN1Integer getTime32() {
        return this.time32;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1Encodable[]{this.time32, this.duration});
    }
}
