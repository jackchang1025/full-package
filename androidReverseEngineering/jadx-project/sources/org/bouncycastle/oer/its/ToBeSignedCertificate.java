package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.OEROptional;

/* loaded from: classes.dex */
public class ToBeSignedCertificate extends ASN1Object {
    private final SequenceOfPsidSsp appPermissions;
    private final SubjectAssurance assuranceLevel;
    private final ASN1Null canRequestRollover;
    private final SequenceOfPsidGroupPermissions certIssuePermissions;
    private final SequenceOfPsidGroupPermissions certRequestPermissions;
    private final CertificateId certificateId;
    private final HashedId cracaId;
    private final CrlSeries crlSeries;
    private final PublicEncryptionKey encryptionKey;
    private final GeographicRegion geographicRegion;
    private final ValidityPeriod validityPeriod;
    private final VerificationKeyIndicator verificationKeyIndicator;

    public static class Builder {
        private SequenceOfPsidSsp appPermissions;
        private SubjectAssurance assuranceLevel;
        private ASN1Null canRequestRollover;
        private SequenceOfPsidGroupPermissions certIssuePermissions;
        private SequenceOfPsidGroupPermissions certRequestPermissions;
        private CertificateId certificateId;
        private HashedId cracaId;
        private CrlSeries crlSeries;
        private PublicEncryptionKey encryptionKey;
        private GeographicRegion geographicRegion;
        private ValidityPeriod validityPeriod;
        private VerificationKeyIndicator verificationKeyIndicator;

        public Builder() {
        }

        public Builder(Builder builder) {
            this.certificateId = builder.certificateId;
            this.cracaId = builder.cracaId;
            this.crlSeries = builder.crlSeries;
            this.validityPeriod = builder.validityPeriod;
            this.geographicRegion = builder.geographicRegion;
            this.assuranceLevel = builder.assuranceLevel;
            this.appPermissions = builder.appPermissions;
            this.certIssuePermissions = builder.certIssuePermissions;
            this.certRequestPermissions = builder.certRequestPermissions;
            this.canRequestRollover = builder.canRequestRollover;
            this.encryptionKey = builder.encryptionKey;
            this.verificationKeyIndicator = builder.verificationKeyIndicator;
        }

        public ToBeSignedCertificate createToBeSignedCertificate() {
            return new ToBeSignedCertificate(this.certificateId, this.cracaId, this.crlSeries, this.validityPeriod, this.geographicRegion, this.assuranceLevel, this.appPermissions, this.certIssuePermissions, this.certRequestPermissions, this.canRequestRollover, this.encryptionKey, this.verificationKeyIndicator);
        }

        public Builder setAppPermissions(SequenceOfPsidSsp sequenceOfPsidSsp) {
            this.appPermissions = sequenceOfPsidSsp;
            return this;
        }

        public Builder setAssuranceLevel(SubjectAssurance subjectAssurance) {
            this.assuranceLevel = subjectAssurance;
            return this;
        }

        public Builder setCanRequestRollover(ASN1Null aSN1Null) {
            this.canRequestRollover = aSN1Null;
            return this;
        }

        public Builder setCertIssuePermissions(SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions) {
            this.certIssuePermissions = sequenceOfPsidGroupPermissions;
            return this;
        }

        public Builder setCertRequestPermissions(SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions) {
            this.certRequestPermissions = sequenceOfPsidGroupPermissions;
            return this;
        }

        public Builder setCertificateId(CertificateId certificateId) {
            this.certificateId = certificateId;
            return this;
        }

        public Builder setCracaId(HashedId hashedId) {
            this.cracaId = hashedId;
            return this;
        }

        public Builder setCrlSeries(CrlSeries crlSeries) {
            this.crlSeries = crlSeries;
            return this;
        }

        public Builder setEncryptionKey(PublicEncryptionKey publicEncryptionKey) {
            this.encryptionKey = publicEncryptionKey;
            return this;
        }

        public Builder setGeographicRegion(GeographicRegion geographicRegion) {
            this.geographicRegion = geographicRegion;
            return this;
        }

        public Builder setValidityPeriod(ValidityPeriod validityPeriod) {
            this.validityPeriod = validityPeriod;
            return this;
        }

        public Builder setVerificationKeyIndicator(VerificationKeyIndicator verificationKeyIndicator) {
            this.verificationKeyIndicator = verificationKeyIndicator;
            return this;
        }

        public Builder(ToBeSignedCertificate toBeSignedCertificate) {
            this.certificateId = toBeSignedCertificate.certificateId;
            this.cracaId = toBeSignedCertificate.cracaId;
            this.crlSeries = toBeSignedCertificate.crlSeries;
            this.validityPeriod = toBeSignedCertificate.validityPeriod;
            this.geographicRegion = toBeSignedCertificate.geographicRegion;
            this.assuranceLevel = toBeSignedCertificate.assuranceLevel;
            this.appPermissions = toBeSignedCertificate.appPermissions;
            this.certIssuePermissions = toBeSignedCertificate.certIssuePermissions;
            this.certRequestPermissions = toBeSignedCertificate.certRequestPermissions;
            this.canRequestRollover = toBeSignedCertificate.canRequestRollover;
            this.encryptionKey = toBeSignedCertificate.encryptionKey;
            this.verificationKeyIndicator = toBeSignedCertificate.verificationKeyIndicator;
        }
    }

    public ToBeSignedCertificate(CertificateId certificateId, HashedId hashedId, CrlSeries crlSeries, ValidityPeriod validityPeriod, GeographicRegion geographicRegion, SubjectAssurance subjectAssurance, SequenceOfPsidSsp sequenceOfPsidSsp, SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions, SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions2, ASN1Null aSN1Null, PublicEncryptionKey publicEncryptionKey, VerificationKeyIndicator verificationKeyIndicator) {
        this.certificateId = certificateId;
        this.cracaId = hashedId;
        this.crlSeries = crlSeries;
        this.validityPeriod = validityPeriod;
        this.geographicRegion = geographicRegion;
        this.assuranceLevel = subjectAssurance;
        this.appPermissions = sequenceOfPsidSsp;
        this.certIssuePermissions = sequenceOfPsidGroupPermissions;
        this.certRequestPermissions = sequenceOfPsidGroupPermissions2;
        this.canRequestRollover = aSN1Null;
        this.encryptionKey = publicEncryptionKey;
        this.verificationKeyIndicator = verificationKeyIndicator;
    }

    public static ToBeSignedCertificate getInstance(Object obj) {
        if (obj == null || (obj instanceof ToBeSignedCertificate)) {
            return (ToBeSignedCertificate) obj;
        }
        Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(obj).iterator();
        return new Builder().setCertificateId(CertificateId.getInstance(it.next())).setCracaId(HashedId.getInstance(it.next())).setCrlSeries(CrlSeries.getInstance((Object) it.next())).setValidityPeriod(ValidityPeriod.getInstance(it.next())).setGeographicRegion((GeographicRegion) OEROptional.getValue(GeographicRegion.class, it.next())).setAssuranceLevel((SubjectAssurance) OEROptional.getValue(SubjectAssurance.class, it.next())).setAppPermissions((SequenceOfPsidSsp) OEROptional.getValue(SequenceOfPsidSsp.class, it.next())).setCertIssuePermissions((SequenceOfPsidGroupPermissions) OEROptional.getValue(SequenceOfPsidGroupPermissions.class, it.next())).setCertRequestPermissions((SequenceOfPsidGroupPermissions) OEROptional.getValue(SequenceOfPsidGroupPermissions.class, it.next())).setCanRequestRollover((ASN1Null) OEROptional.getValue(ASN1Null.class, it.next())).setEncryptionKey((PublicEncryptionKey) OEROptional.getValue(PublicEncryptionKey.class, it.next())).setVerificationKeyIndicator(VerificationKeyIndicator.getInstance(it.next())).createToBeSignedCertificate();
    }

    public SequenceOfPsidSsp getAppPermissions() {
        return this.appPermissions;
    }

    public SubjectAssurance getAssuranceLevel() {
        return this.assuranceLevel;
    }

    public ASN1Null getCanRequestRollover() {
        return this.canRequestRollover;
    }

    public SequenceOfPsidGroupPermissions getCertIssuePermissions() {
        return this.certIssuePermissions;
    }

    public SequenceOfPsidGroupPermissions getCertRequestPermissions() {
        return this.certRequestPermissions;
    }

    public CertificateId getCertificateId() {
        return this.certificateId;
    }

    public HashedId getCracaId() {
        return this.cracaId;
    }

    public CrlSeries getCrlSeries() {
        return this.crlSeries;
    }

    public PublicEncryptionKey getEncryptionKey() {
        return this.encryptionKey;
    }

    public GeographicRegion getGeographicRegion() {
        return this.geographicRegion;
    }

    public ValidityPeriod getValidityPeriod() {
        return this.validityPeriod;
    }

    public VerificationKeyIndicator getVerificationKeyIndicator() {
        return this.verificationKeyIndicator;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.certificateId, this.cracaId, this.crlSeries, this.validityPeriod, OEROptional.getInstance(this.geographicRegion), OEROptional.getInstance(this.assuranceLevel), OEROptional.getInstance(this.appPermissions), OEROptional.getInstance(this.certIssuePermissions), OEROptional.getInstance(this.certRequestPermissions), OEROptional.getInstance(this.canRequestRollover), OEROptional.getInstance(this.encryptionKey), this.verificationKeyIndicator);
    }
}
