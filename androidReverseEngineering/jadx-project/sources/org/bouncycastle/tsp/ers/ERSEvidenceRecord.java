package org.bouncycastle.tsp.ers;

import java.util.Date;
import org.bouncycastle.asn1.tsp.EvidenceRecord;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.bouncycastle.operator.DigestCalculatorProvider;

/* loaded from: classes.dex */
public class ERSEvidenceRecord {
    private final DigestCalculatorProvider digestCalculatorProvider;
    private final EvidenceRecord evidenceRecord;
    private final ERSArchiveTimeStamp lastArchiveTimeStamp;

    public ERSEvidenceRecord(EvidenceRecord evidenceRecord, DigestCalculatorProvider digestCalculatorProvider) {
        this.evidenceRecord = evidenceRecord;
        this.digestCalculatorProvider = digestCalculatorProvider;
        this.lastArchiveTimeStamp = new ERSArchiveTimeStamp(evidenceRecord.getArchiveTimeStampSequence().getArchiveTimeStampChains()[r3.length - 1].getArchiveTimestamps()[r3.length - 1], digestCalculatorProvider);
    }

    public byte[] getEncoded() {
        return this.evidenceRecord.getEncoded();
    }

    public ERSArchiveTimeStamp getLastArchiveTimeStamp() {
        return this.lastArchiveTimeStamp;
    }

    public X509CertificateHolder getSigningCertificate() {
        return this.lastArchiveTimeStamp.getSigningCertificate();
    }

    public void validate(SignerInformationVerifier signerInformationVerifier) {
        this.lastArchiveTimeStamp.validate(signerInformationVerifier);
    }

    public void validatePresent(ERSData eRSData, Date date) {
        this.lastArchiveTimeStamp.validatePresent(eRSData, date);
    }

    public ERSEvidenceRecord(byte[] bArr, DigestCalculatorProvider digestCalculatorProvider) {
        this(EvidenceRecord.getInstance(bArr), digestCalculatorProvider);
    }

    public void validatePresent(byte[] bArr, Date date) {
        this.lastArchiveTimeStamp.validatePresent(bArr, date);
    }
}
