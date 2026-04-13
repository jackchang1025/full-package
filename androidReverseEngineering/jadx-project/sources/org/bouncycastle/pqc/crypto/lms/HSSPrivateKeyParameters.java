package org.bouncycastle.pqc.crypto.lms;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class HSSPrivateKeyParameters extends LMSKeyParameters implements LMSContextBasedSigner {
    private long index;
    private final long indexLimit;
    private final boolean isShard;
    private List<LMSPrivateKeyParameters> keys;

    /* renamed from: l */
    private final int f1548l;
    private HSSPublicKeyParameters publicKey;
    private List<LMSSignature> sig;

    public HSSPrivateKeyParameters(int i2, List<LMSPrivateKeyParameters> list, List<LMSSignature> list2, long j2, long j3) {
        super(true);
        this.index = 0L;
        this.f1548l = i2;
        this.keys = Collections.unmodifiableList(list);
        this.sig = Collections.unmodifiableList(list2);
        this.index = j2;
        this.indexLimit = j3;
        this.isShard = false;
        resetKeyToIndex();
    }

    public static HSSPrivateKeyParameters getInstance(Object obj) {
        if (obj instanceof HSSPrivateKeyParameters) {
            return (HSSPrivateKeyParameters) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            if (dataInputStream.readInt() != 0) {
                throw new IllegalStateException("unknown version for hss private key");
            }
            int readInt = dataInputStream.readInt();
            long readLong = dataInputStream.readLong();
            long readLong2 = dataInputStream.readLong();
            boolean readBoolean = dataInputStream.readBoolean();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < readInt; i2++) {
                arrayList.add(LMSPrivateKeyParameters.getInstance(obj));
            }
            for (int i3 = 0; i3 < readInt - 1; i3++) {
                arrayList2.add(LMSSignature.getInstance(obj));
            }
            return new HSSPrivateKeyParameters(readInt, arrayList, arrayList2, readLong, readLong2, readBoolean);
        }
        if (!(obj instanceof byte[])) {
            if (obj instanceof InputStream) {
                return getInstance(Streams.readAll((InputStream) obj));
            }
            throw new IllegalArgumentException("cannot parse " + obj);
        }
        DataInputStream dataInputStream2 = null;
        try {
            DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream((byte[]) obj));
            try {
                HSSPrivateKeyParameters hSSPrivateKeyParameters = getInstance(dataInputStream3);
                dataInputStream3.close();
                return hSSPrivateKeyParameters;
            } catch (Throwable th) {
                th = th;
                dataInputStream2 = dataInputStream3;
                if (dataInputStream2 != null) {
                    dataInputStream2.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static HSSPrivateKeyParameters makeCopy(HSSPrivateKeyParameters hSSPrivateKeyParameters) {
        try {
            return getInstance(hSSPrivateKeyParameters.getEncoded());
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    public Object clone() {
        return makeCopy(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HSSPrivateKeyParameters hSSPrivateKeyParameters = (HSSPrivateKeyParameters) obj;
        if (this.f1548l == hSSPrivateKeyParameters.f1548l && this.isShard == hSSPrivateKeyParameters.isShard && this.indexLimit == hSSPrivateKeyParameters.indexLimit && this.index == hSSPrivateKeyParameters.index && this.keys.equals(hSSPrivateKeyParameters.keys)) {
            return this.sig.equals(hSSPrivateKeyParameters.sig);
        }
        return false;
    }

    public HSSPrivateKeyParameters extractKeyShard(int i2) {
        HSSPrivateKeyParameters makeCopy;
        synchronized (this) {
            long j2 = i2;
            if (getUsagesRemaining() < j2) {
                throw new IllegalArgumentException("usageCount exceeds usages remaining in current leaf");
            }
            long j3 = this.index;
            this.index = j2 + j3;
            makeCopy = makeCopy(new HSSPrivateKeyParameters(this.f1548l, new ArrayList(getKeys()), new ArrayList(getSig()), j3, j3 + j2, true));
            resetKeyToIndex();
        }
        return makeCopy;
    }

    @Override // org.bouncycastle.pqc.crypto.lms.LMSContextBasedSigner
    public LMSContext generateLMSContext() {
        LMSPrivateKeyParameters lMSPrivateKeyParameters;
        LMSSignedPubKey[] lMSSignedPubKeyArr;
        int l2 = getL();
        synchronized (this) {
            HSS.rangeTestKeys(this);
            List<LMSPrivateKeyParameters> keys = getKeys();
            List<LMSSignature> sig = getSig();
            int i2 = l2 - 1;
            lMSPrivateKeyParameters = getKeys().get(i2);
            lMSSignedPubKeyArr = new LMSSignedPubKey[i2];
            int i3 = 0;
            while (i3 < i2) {
                int i4 = i3 + 1;
                lMSSignedPubKeyArr[i3] = new LMSSignedPubKey(sig.get(i3), keys.get(i4).getPublicKey());
                i3 = i4;
            }
            incIndex();
        }
        return lMSPrivateKeyParameters.generateLMSContext().withSignedPublicKeys(lMSSignedPubKeyArr);
    }

    @Override // org.bouncycastle.pqc.crypto.lms.LMSContextBasedSigner
    public byte[] generateSignature(LMSContext lMSContext) {
        try {
            return HSS.generateSignature(getL(), lMSContext).getEncoded();
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("unable to encode signature: ")), e2);
        }
    }

    @Override // org.bouncycastle.pqc.crypto.lms.LMSKeyParameters, org.bouncycastle.util.Encodable
    public synchronized byte[] getEncoded() {
        Composer bool;
        bool = Composer.compose().u32str(0).u32str(this.f1548l).u64str(this.index).u64str(this.indexLimit).bool(this.isShard);
        Iterator<LMSPrivateKeyParameters> it = this.keys.iterator();
        while (it.hasNext()) {
            bool.bytes(it.next());
        }
        Iterator<LMSSignature> it2 = this.sig.iterator();
        while (it2.hasNext()) {
            bool.bytes(it2.next());
        }
        return bool.build();
    }

    public synchronized long getIndex() {
        return this.index;
    }

    public long getIndexLimit() {
        return this.indexLimit;
    }

    public synchronized List<LMSPrivateKeyParameters> getKeys() {
        return this.keys;
    }

    public int getL() {
        return this.f1548l;
    }

    public synchronized LMSParameters[] getLMSParameters() {
        LMSParameters[] lMSParametersArr;
        int size = this.keys.size();
        lMSParametersArr = new LMSParameters[size];
        for (int i2 = 0; i2 < size; i2++) {
            LMSPrivateKeyParameters lMSPrivateKeyParameters = this.keys.get(i2);
            lMSParametersArr[i2] = new LMSParameters(lMSPrivateKeyParameters.getSigParameters(), lMSPrivateKeyParameters.getOtsParameters());
        }
        return lMSParametersArr;
    }

    public synchronized HSSPublicKeyParameters getPublicKey() {
        return new HSSPublicKeyParameters(this.f1548l, getRootKey().getPublicKey());
    }

    public LMSPrivateKeyParameters getRootKey() {
        return this.keys.get(0);
    }

    public synchronized List<LMSSignature> getSig() {
        return this.sig;
    }

    @Override // org.bouncycastle.pqc.crypto.lms.LMSContextBasedSigner
    public long getUsagesRemaining() {
        return this.indexLimit - this.index;
    }

    public int hashCode() {
        int hashCode = (this.sig.hashCode() + ((this.keys.hashCode() + (((this.f1548l * 31) + (this.isShard ? 1 : 0)) * 31)) * 31)) * 31;
        long j2 = this.indexLimit;
        int i2 = (hashCode + ((int) (j2 ^ (j2 >>> 32)))) * 31;
        long j3 = this.index;
        return i2 + ((int) (j3 ^ (j3 >>> 32)));
    }

    public synchronized void incIndex() {
        this.index++;
    }

    public boolean isShard() {
        return this.isShard;
    }

    public void replaceConsumedKey(int i2) {
        int i3 = i2 - 1;
        SeedDerive derivationFunction = this.keys.get(i3).getCurrentOTSKey().getDerivationFunction();
        derivationFunction.setJ(-2);
        byte[] bArr = new byte[32];
        derivationFunction.deriveSeed(bArr, true);
        byte[] bArr2 = new byte[32];
        derivationFunction.deriveSeed(bArr2, false);
        byte[] bArr3 = new byte[16];
        System.arraycopy(bArr2, 0, bArr3, 0, 16);
        ArrayList arrayList = new ArrayList(this.keys);
        LMSPrivateKeyParameters lMSPrivateKeyParameters = this.keys.get(i2);
        arrayList.set(i2, LMS.generateKeys(lMSPrivateKeyParameters.getSigParameters(), lMSPrivateKeyParameters.getOtsParameters(), 0, bArr3, bArr));
        ArrayList arrayList2 = new ArrayList(this.sig);
        arrayList2.set(i3, LMS.generateSign((LMSPrivateKeyParameters) arrayList.get(i3), ((LMSPrivateKeyParameters) arrayList.get(i2)).getPublicKey().toByteArray()));
        this.keys = Collections.unmodifiableList(arrayList);
        this.sig = Collections.unmodifiableList(arrayList2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x00d1, code lost:
    
        if (r3[r9] == (r4[r9].getIndex() - 1)) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void resetKeyToIndex() {
        boolean z2;
        List<LMSPrivateKeyParameters> keys = getKeys();
        int size = keys.size();
        long[] jArr = new long[size];
        long index = getIndex();
        for (int size2 = keys.size() - 1; size2 >= 0; size2--) {
            LMSigParameters sigParameters = keys.get(size2).getSigParameters();
            jArr[size2] = ((1 << sigParameters.getH()) - 1) & index;
            index >>>= sigParameters.getH();
        }
        LMSPrivateKeyParameters[] lMSPrivateKeyParametersArr = (LMSPrivateKeyParameters[]) keys.toArray(new LMSPrivateKeyParameters[keys.size()]);
        List<LMSSignature> list = this.sig;
        LMSSignature[] lMSSignatureArr = (LMSSignature[]) list.toArray(new LMSSignature[list.size()]);
        LMSPrivateKeyParameters rootKey = getRootKey();
        if (lMSPrivateKeyParametersArr[0].getIndex() - 1 != jArr[0]) {
            lMSPrivateKeyParametersArr[0] = LMS.generateKeys(rootKey.getSigParameters(), rootKey.getOtsParameters(), (int) jArr[0], rootKey.getI(), rootKey.getMasterSecret());
            z2 = true;
        } else {
            z2 = false;
        }
        int i2 = 1;
        while (i2 < size) {
            int i3 = i2 - 1;
            LMSPrivateKeyParameters lMSPrivateKeyParameters = lMSPrivateKeyParametersArr[i3];
            byte[] bArr = new byte[16];
            byte[] bArr2 = new byte[32];
            SeedDerive seedDerive = new SeedDerive(lMSPrivateKeyParameters.getI(), lMSPrivateKeyParameters.getMasterSecret(), DigestUtil.getDigest(lMSPrivateKeyParameters.getOtsParameters().getDigestOID()));
            seedDerive.setQ((int) jArr[i3]);
            seedDerive.setJ(-2);
            seedDerive.deriveSeed(bArr2, true);
            byte[] bArr3 = new byte[32];
            boolean z3 = false;
            seedDerive.deriveSeed(bArr3, false);
            System.arraycopy(bArr3, 0, bArr, 0, 16);
            if (i2 >= size - 1) {
                if (jArr[i2] != lMSPrivateKeyParametersArr[i2].getIndex()) {
                    z3 = false;
                }
                z3 = true;
            }
            if (!(Arrays.areEqual(bArr, lMSPrivateKeyParametersArr[i2].getI()) && Arrays.areEqual(bArr2, lMSPrivateKeyParametersArr[i2].getMasterSecret()))) {
                LMSPrivateKeyParameters generateKeys = LMS.generateKeys(keys.get(i2).getSigParameters(), keys.get(i2).getOtsParameters(), (int) jArr[i2], bArr, bArr2);
                lMSPrivateKeyParametersArr[i2] = generateKeys;
                lMSSignatureArr[i3] = LMS.generateSign(lMSPrivateKeyParametersArr[i3], generateKeys.getPublicKey().toByteArray());
            } else if (z3) {
                i2++;
            } else {
                lMSPrivateKeyParametersArr[i2] = LMS.generateKeys(keys.get(i2).getSigParameters(), keys.get(i2).getOtsParameters(), (int) jArr[i2], bArr, bArr2);
            }
            z2 = true;
            i2++;
        }
        if (z2) {
            updateHierarchy(lMSPrivateKeyParametersArr, lMSSignatureArr);
        }
    }

    public void updateHierarchy(LMSPrivateKeyParameters[] lMSPrivateKeyParametersArr, LMSSignature[] lMSSignatureArr) {
        synchronized (this) {
            this.keys = Collections.unmodifiableList(java.util.Arrays.asList(lMSPrivateKeyParametersArr));
            this.sig = Collections.unmodifiableList(java.util.Arrays.asList(lMSSignatureArr));
        }
    }

    private HSSPrivateKeyParameters(int i2, List<LMSPrivateKeyParameters> list, List<LMSSignature> list2, long j2, long j3, boolean z2) {
        super(true);
        this.index = 0L;
        this.f1548l = i2;
        this.keys = Collections.unmodifiableList(list);
        this.sig = Collections.unmodifiableList(list2);
        this.index = j2;
        this.indexLimit = j3;
        this.isShard = z2;
    }

    public static HSSPrivateKeyParameters getInstance(byte[] bArr, byte[] bArr2) {
        HSSPrivateKeyParameters hSSPrivateKeyParameters = getInstance(bArr);
        hSSPrivateKeyParameters.publicKey = HSSPublicKeyParameters.getInstance(bArr2);
        return hSSPrivateKeyParameters;
    }
}
