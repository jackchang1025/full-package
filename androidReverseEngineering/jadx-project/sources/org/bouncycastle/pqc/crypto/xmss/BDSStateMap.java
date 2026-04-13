package org.bouncycastle.pqc.crypto.xmss;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.pqc.crypto.xmss.OTSHashAddress;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
public class BDSStateMap implements Serializable {
    private static final long serialVersionUID = -3464451825208522308L;
    private final Map<Integer, BDS> bdsState = new TreeMap();
    private transient long maxIndex;

    public BDSStateMap(long j2) {
        this.maxIndex = j2;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.maxIndex = objectInputStream.available() != 0 ? objectInputStream.readLong() : 0L;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(this.maxIndex);
    }

    public BDS get(int i2) {
        return this.bdsState.get(Integers.valueOf(i2));
    }

    public long getMaxIndex() {
        return this.maxIndex;
    }

    public boolean isEmpty() {
        return this.bdsState.isEmpty();
    }

    public void put(int i2, BDS bds) {
        this.bdsState.put(Integers.valueOf(i2), bds);
    }

    public BDS update(int i2, byte[] bArr, byte[] bArr2, OTSHashAddress oTSHashAddress) {
        return this.bdsState.put(Integers.valueOf(i2), this.bdsState.get(Integers.valueOf(i2)).getNextState(bArr, bArr2, oTSHashAddress));
    }

    public void updateState(XMSSMTParameters xMSSMTParameters, long j2, byte[] bArr, byte[] bArr2) {
        XMSSParameters xMSSParameters = xMSSMTParameters.getXMSSParameters();
        int height = xMSSParameters.getHeight();
        long treeIndex = XMSSUtil.getTreeIndex(j2, height);
        int leafIndex = XMSSUtil.getLeafIndex(j2, height);
        OTSHashAddress oTSHashAddress = (OTSHashAddress) new OTSHashAddress.Builder().withTreeAddress(treeIndex).withOTSAddress(leafIndex).build();
        int i2 = (1 << height) - 1;
        if (leafIndex < i2) {
            if (get(0) == null || leafIndex == 0) {
                put(0, new BDS(xMSSParameters, bArr, bArr2, oTSHashAddress));
            }
            update(0, bArr, bArr2, oTSHashAddress);
        }
        for (int i3 = 1; i3 < xMSSMTParameters.getLayers(); i3++) {
            int leafIndex2 = XMSSUtil.getLeafIndex(treeIndex, height);
            treeIndex = XMSSUtil.getTreeIndex(treeIndex, height);
            OTSHashAddress oTSHashAddress2 = (OTSHashAddress) new OTSHashAddress.Builder().withLayerAddress(i3).withTreeAddress(treeIndex).withOTSAddress(leafIndex2).build();
            if (this.bdsState.get(Integer.valueOf(i3)) == null || XMSSUtil.isNewBDSInitNeeded(j2, height, i3)) {
                this.bdsState.put(Integer.valueOf(i3), new BDS(xMSSParameters, bArr, bArr2, oTSHashAddress2));
            }
            if (leafIndex2 < i2 && XMSSUtil.isNewAuthenticationPathNeeded(j2, height, i3)) {
                update(i3, bArr, bArr2, oTSHashAddress2);
            }
        }
    }

    public BDSStateMap withWOTSDigest(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        BDSStateMap bDSStateMap = new BDSStateMap(this.maxIndex);
        for (Integer num : this.bdsState.keySet()) {
            bDSStateMap.bdsState.put(num, this.bdsState.get(num).withWOTSDigest(aSN1ObjectIdentifier));
        }
        return bDSStateMap;
    }

    public BDSStateMap(BDSStateMap bDSStateMap, long j2) {
        for (Integer num : bDSStateMap.bdsState.keySet()) {
            this.bdsState.put(num, new BDS(bDSStateMap.bdsState.get(num)));
        }
        this.maxIndex = j2;
    }

    public BDSStateMap(XMSSMTParameters xMSSMTParameters, long j2, byte[] bArr, byte[] bArr2) {
        this.maxIndex = (1 << xMSSMTParameters.getHeight()) - 1;
        for (long j3 = 0; j3 < j2; j3++) {
            updateState(xMSSMTParameters, j3, bArr, bArr2);
        }
    }
}
