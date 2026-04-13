package org.bouncycastle.tsp.ers;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import org.bouncycastle.asn1.tsp.ArchiveTimeStamp;
import org.bouncycastle.asn1.tsp.PartialHashtree;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Store;

/* loaded from: classes.dex */
public class ERSArchiveTimeStamp {
    private final ArchiveTimeStamp archiveTimeStamp;
    private final DigestCalculator digCalc;
    private ERSRootNodeCalculator rootNodeCalculator;
    private final TimeStampToken timeStampToken;

    public ERSArchiveTimeStamp(ArchiveTimeStamp archiveTimeStamp, DigestCalculator digestCalculator, ERSRootNodeCalculator eRSRootNodeCalculator) {
        this.rootNodeCalculator = new BinaryTreeRootCalculator();
        try {
            this.archiveTimeStamp = archiveTimeStamp;
            this.timeStampToken = new TimeStampToken(archiveTimeStamp.getTimeStamp());
            this.digCalc = digestCalculator;
            this.rootNodeCalculator = eRSRootNodeCalculator;
        } catch (IOException e2) {
            throw new ERSException(e2.getMessage(), e2);
        }
    }

    public void checkContainsHashValue(byte[] bArr, DigestCalculator digestCalculator) {
        PartialHashtree[] reducedHashTree = this.archiveTimeStamp.getReducedHashTree();
        if (reducedHashTree == null) {
            if (!Arrays.areEqual(bArr, this.timeStampToken.getTimeStampInfo().getMessageImprintDigest())) {
                throw new ArchiveTimeStampValidationException("object hash not found in wrapped timestamp");
            }
            return;
        }
        for (int i2 = 0; i2 != reducedHashTree.length; i2++) {
            PartialHashtree partialHashtree = reducedHashTree[i2];
            if (partialHashtree.containsHash(bArr)) {
                return;
            }
            if (partialHashtree.getValueCount() > 1 && Arrays.areEqual(bArr, ERSUtil.calculateBranchHash(digestCalculator, partialHashtree.getValues()))) {
                return;
            }
        }
        throw new ArchiveTimeStampValidationException("object hash not found");
    }

    public void checkTimeStampValid(TimeStampToken timeStampToken, byte[] bArr) {
        if (bArr != null && !Arrays.areEqual(bArr, timeStampToken.getTimeStampInfo().getMessageImprintDigest())) {
            throw new ArchiveTimeStampValidationException("timestamp hash does not match root");
        }
    }

    public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
        return this.archiveTimeStamp.getDigestAlgorithmIdentifier();
    }

    public byte[] getEncoded() {
        return this.archiveTimeStamp.getEncoded();
    }

    public Date getExpiryTime() {
        X509CertificateHolder signingCertificate = getSigningCertificate();
        if (signingCertificate != null) {
            return signingCertificate.getNotAfter();
        }
        return null;
    }

    public Date getGenTime() {
        return this.timeStampToken.getTimeStampInfo().getGenTime();
    }

    public X509CertificateHolder getSigningCertificate() {
        Store<X509CertificateHolder> certificates = this.timeStampToken.getCertificates();
        if (certificates == null) {
            return null;
        }
        Collection<X509CertificateHolder> matches = certificates.getMatches(this.timeStampToken.getSID());
        if (matches.isEmpty()) {
            return null;
        }
        return matches.iterator().next();
    }

    public TimeStampToken getTimeStampToken() {
        return this.timeStampToken;
    }

    public ArchiveTimeStamp toASN1Structure() {
        return this.archiveTimeStamp;
    }

    public void validate(SignerInformationVerifier signerInformationVerifier) {
        this.timeStampToken.validate(signerInformationVerifier);
    }

    public void validatePresent(ERSData eRSData, Date date) {
        validatePresent(eRSData.getHash(this.digCalc), date);
    }

    public ERSArchiveTimeStamp(ArchiveTimeStamp archiveTimeStamp, DigestCalculatorProvider digestCalculatorProvider) {
        this.rootNodeCalculator = new BinaryTreeRootCalculator();
        try {
            this.archiveTimeStamp = archiveTimeStamp;
            this.timeStampToken = new TimeStampToken(archiveTimeStamp.getTimeStamp());
            this.digCalc = digestCalculatorProvider.get(archiveTimeStamp.getDigestAlgorithmIdentifier());
        } catch (IOException e2) {
            throw new ERSException(e2.getMessage(), e2);
        } catch (OperatorCreationException e3) {
            throw new ERSException(e3.getMessage(), e3);
        }
    }

    public void validatePresent(byte[] bArr, Date date) {
        if (this.timeStampToken.getTimeStampInfo().getGenTime().after(date)) {
            throw new ArchiveTimeStampValidationException("timestamp generation time is in the future");
        }
        checkContainsHashValue(bArr, this.digCalc);
        if (this.archiveTimeStamp.getReducedHashTree() != null) {
            bArr = this.rootNodeCalculator.computeRootHash(this.digCalc, this.archiveTimeStamp.getReducedHashTree());
        }
        checkTimeStampValid(this.timeStampToken, bArr);
    }

    public ERSArchiveTimeStamp(byte[] bArr, DigestCalculatorProvider digestCalculatorProvider) {
        this(ArchiveTimeStamp.getInstance(bArr), digestCalculatorProvider);
    }
}
