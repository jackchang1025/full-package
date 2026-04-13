package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class Ieee1609Dot2Data extends ASN1Object {
    private final Ieee1609Dot2Content content;
    private final Uint8 protocolVersion;

    public static class Builder {
        private Ieee1609Dot2Content content;
        private Uint8 protocolVersion;

        public Ieee1609Dot2Data build() {
            return new Ieee1609Dot2Data(this.protocolVersion, this.content);
        }

        public Builder setContent(Ieee1609Dot2Content ieee1609Dot2Content) {
            this.content = ieee1609Dot2Content;
            return this;
        }

        public Builder setProtocolVersion(Uint8 uint8) {
            this.protocolVersion = uint8;
            return this;
        }
    }

    public Ieee1609Dot2Data(Uint8 uint8, Ieee1609Dot2Content ieee1609Dot2Content) {
        this.protocolVersion = uint8;
        this.content = ieee1609Dot2Content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Ieee1609Dot2Data getInstance(Object obj) {
        if (obj instanceof Ieee1609Dot2Data) {
            return (Ieee1609Dot2Data) obj;
        }
        Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(obj).iterator();
        return new Ieee1609Dot2Data(Uint8.getInstance(it.next()), Ieee1609Dot2Content.getInstance(it.next()));
    }

    public Ieee1609Dot2Content getContent() {
        return this.content;
    }

    public Uint8 getProtocolVersion() {
        return this.protocolVersion;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.protocolVersion, this.content);
    }
}
