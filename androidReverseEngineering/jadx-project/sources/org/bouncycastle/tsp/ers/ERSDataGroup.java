package org.bouncycastle.tsp.ers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.operator.DigestCalculator;

/* loaded from: classes.dex */
public class ERSDataGroup extends ERSCachingData {
    protected List<ERSData> dataObjects;

    public ERSDataGroup(List<ERSData> list) {
        ArrayList arrayList = new ArrayList(list.size());
        this.dataObjects = arrayList;
        arrayList.addAll(list);
    }

    @Override // org.bouncycastle.tsp.ers.ERSCachingData
    public byte[] calculateHash(DigestCalculator digestCalculator) {
        List<byte[]> hashes = getHashes(digestCalculator);
        return hashes.size() > 1 ? ERSUtil.calculateDigest(digestCalculator, hashes.iterator()) : hashes.get(0);
    }

    public List<byte[]> getHashes(DigestCalculator digestCalculator) {
        return ERSUtil.buildHashList(digestCalculator, this.dataObjects);
    }

    public int size() {
        return this.dataObjects.size();
    }

    public ERSDataGroup(ERSData eRSData) {
        this.dataObjects = Collections.singletonList(eRSData);
    }

    public ERSDataGroup(ERSData... eRSDataArr) {
        ArrayList arrayList = new ArrayList(eRSDataArr.length);
        this.dataObjects = arrayList;
        arrayList.addAll(Arrays.asList(eRSDataArr));
    }
}
