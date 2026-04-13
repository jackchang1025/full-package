package org.bouncycastle.tsp.ers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.tsp.ArchiveTimeStamp;
import org.bouncycastle.asn1.tsp.PartialHashtree;
import org.bouncycastle.asn1.tsp.TSTInfo;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class ERSArchiveTimeStampGenerator {
    private final DigestCalculator digCalc;
    private List<ERSData> dataObjects = new ArrayList();
    private ERSRootNodeCalculator rootNodeCalculator = new BinaryTreeRootCalculator();

    public ERSArchiveTimeStampGenerator(DigestCalculator digestCalculator) {
        this.digCalc = digestCalculator;
    }

    private PartialHashtree[] getPartialHashtrees() {
        ERSDataGroup eRSDataGroup;
        List<byte[]> buildHashList = ERSUtil.buildHashList(this.digCalc, this.dataObjects);
        PartialHashtree[] partialHashtreeArr = new PartialHashtree[buildHashList.size()];
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 != this.dataObjects.size(); i2++) {
            if (this.dataObjects.get(i2) instanceof ERSDataGroup) {
                hashSet.add((ERSDataGroup) this.dataObjects.get(i2));
            }
        }
        for (int i3 = 0; i3 != buildHashList.size(); i3++) {
            byte[] bArr = buildHashList.get(i3);
            Iterator it = hashSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    eRSDataGroup = null;
                    break;
                }
                eRSDataGroup = (ERSDataGroup) it.next();
                if (Arrays.areEqual(eRSDataGroup.getHash(this.digCalc), bArr)) {
                    List<byte[]> hashes = eRSDataGroup.getHashes(this.digCalc);
                    partialHashtreeArr[i3] = new PartialHashtree((byte[][]) hashes.toArray(new byte[hashes.size()][]));
                    break;
                }
            }
            if (eRSDataGroup == null) {
                partialHashtreeArr[i3] = new PartialHashtree(bArr);
            } else {
                hashSet.remove(eRSDataGroup);
            }
        }
        return partialHashtreeArr;
    }

    public void addAllData(List<ERSData> list) {
        this.dataObjects.addAll(list);
    }

    public void addData(ERSData eRSData) {
        this.dataObjects.add(eRSData);
    }

    public ERSArchiveTimeStamp generateArchiveTimeStamp(TimeStampResponse timeStampResponse) {
        PartialHashtree[] partialHashtrees = getPartialHashtrees();
        byte[] computeRootHash = this.rootNodeCalculator.computeRootHash(this.digCalc, partialHashtrees);
        TSTInfo aSN1Structure = timeStampResponse.getTimeStampToken().getTimeStampInfo().toASN1Structure();
        if (!aSN1Structure.getMessageImprint().getHashAlgorithm().equals(this.digCalc.getAlgorithmIdentifier())) {
            throw new ERSException("time stamp imprint for wrong algorithm");
        }
        if (Arrays.areEqual(aSN1Structure.getMessageImprint().getHashedMessage(), computeRootHash)) {
            return new ERSArchiveTimeStamp(partialHashtrees.length == 1 ? new ArchiveTimeStamp(null, null, timeStampResponse.getTimeStampToken().toCMSSignedData().toASN1Structure()) : new ArchiveTimeStamp(this.digCalc.getAlgorithmIdentifier(), partialHashtrees, timeStampResponse.getTimeStampToken().toCMSSignedData().toASN1Structure()), this.digCalc, this.rootNodeCalculator);
        }
        throw new ERSException("time stamp imprint for wrong root hash");
    }

    public TimeStampRequest generateTimeStampRequest(TimeStampRequestGenerator timeStampRequestGenerator) {
        return timeStampRequestGenerator.generate(this.digCalc.getAlgorithmIdentifier(), this.rootNodeCalculator.computeRootHash(this.digCalc, getPartialHashtrees()));
    }

    public TimeStampRequest generateTimeStampRequest(TimeStampRequestGenerator timeStampRequestGenerator, BigInteger bigInteger) {
        return timeStampRequestGenerator.generate(this.digCalc.getAlgorithmIdentifier(), this.rootNodeCalculator.computeRootHash(this.digCalc, getPartialHashtrees()), bigInteger);
    }
}
