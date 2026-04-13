package org.bouncycastle.tsp.ers;

import org.bouncycastle.asn1.tsp.CryptoInfos;
import org.bouncycastle.asn1.tsp.EncryptionInfo;
import org.bouncycastle.asn1.tsp.EvidenceRecord;
import org.bouncycastle.operator.DigestCalculatorProvider;

/* loaded from: classes.dex */
public class ERSEvidenceRecordGenerator {
    private final DigestCalculatorProvider digCalcProv;

    public ERSEvidenceRecordGenerator(DigestCalculatorProvider digestCalculatorProvider) {
        this.digCalcProv = digestCalculatorProvider;
    }

    public ERSEvidenceRecord generate(ERSArchiveTimeStamp eRSArchiveTimeStamp) {
        return new ERSEvidenceRecord(new EvidenceRecord((CryptoInfos) null, (EncryptionInfo) null, eRSArchiveTimeStamp.toASN1Structure()), this.digCalcProv);
    }
}
