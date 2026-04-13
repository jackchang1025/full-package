package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.OEROptional;

/* loaded from: classes.dex */
public class CertificateBase extends ASN1Object {
    private final IssuerIdentifier issuer;
    private final Signature signature;
    private final ToBeSignedCertificate toBeSignedCertificate;
    private final CertificateType type;
    private final ASN1Integer version;

    public static class Builder {
        private IssuerIdentifier issuer;
        private Signature signature;
        private ToBeSignedCertificate toBeSignedCertificate;
        private CertificateType type;
        private ASN1Integer version;

        public CertificateBase createCertificateBase() {
            return new CertificateBase(this.version, this.type, this.issuer, this.toBeSignedCertificate, this.signature);
        }

        public Builder setIssuer(IssuerIdentifier issuerIdentifier) {
            this.issuer = issuerIdentifier;
            return this;
        }

        public Builder setSignature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public Builder setToBeSignedCertificate(ToBeSignedCertificate toBeSignedCertificate) {
            this.toBeSignedCertificate = toBeSignedCertificate;
            return this;
        }

        public Builder setType(CertificateType certificateType) {
            this.type = certificateType;
            return this;
        }

        public Builder setVersion(ASN1Integer aSN1Integer) {
            this.version = aSN1Integer;
            return this;
        }
    }

    public CertificateBase(ASN1Integer aSN1Integer, CertificateType certificateType, IssuerIdentifier issuerIdentifier, ToBeSignedCertificate toBeSignedCertificate, Signature signature) {
        this.version = aSN1Integer;
        this.type = certificateType;
        this.issuer = issuerIdentifier;
        this.toBeSignedCertificate = toBeSignedCertificate;
        this.signature = signature;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static CertificateBase getInstance(Object obj) {
        if (obj instanceof CertificateBase) {
            return (CertificateBase) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        ASN1Integer aSN1Integer = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        CertificateType certificateType = CertificateType.getInstance((Object) aSN1Sequence.getObjectAt(1));
        IssuerIdentifier issuerIdentifier = IssuerIdentifier.getInstance(aSN1Sequence.getObjectAt(2));
        ToBeSignedCertificate toBeSignedCertificate = ToBeSignedCertificate.getInstance(aSN1Sequence.getObjectAt(3));
        return new Builder().setVersion(aSN1Integer).setType(certificateType).setIssuer(issuerIdentifier).setToBeSignedCertificate(toBeSignedCertificate).setSignature((Signature) OEROptional.getValue(Signature.class, aSN1Sequence.getObjectAt(4))).createCertificateBase();
    }

    public IssuerIdentifier getIssuer() {
        return this.issuer;
    }

    public Signature getSignature() {
        return this.signature;
    }

    public ToBeSignedCertificate getToBeSignedCertificate() {
        return this.toBeSignedCertificate;
    }

    public CertificateType getType() {
        return this.type;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.version, this.type, this.issuer, this.toBeSignedCertificate, OEROptional.getInstance(this.signature));
    }
}
