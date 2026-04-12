package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class s30 extends n90 implements k90 {
    private long index;
    private final long indexLimit;
    private final boolean isShard;
    private List<p90> keys;

    /* renamed from: l */
    private final int f59857l;
    private t30 publicKey;
    private List<r90> sig;

    public s30(int i, List<p90> list, List<r90> list2, long j, long j2) throws IllegalArgumentException {
        super(true);
        this.index = 0L;
        this.f59857l = i;
        this.keys = Collections.unmodifiableList(list);
        this.sig = Collections.unmodifiableList(list2);
        this.index = j;
        this.indexLimit = j2;
        this.isShard = false;
        resetKeyToIndex();
    }

    public static s30 getInstance(Object obj) throws Throwable {
        Throwable th;
        if (obj instanceof s30) {
            return (s30) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            if (dataInputStream.readInt() != 0) {
                throw new IllegalStateException("unknown version for hss private key");
            }
            int i = dataInputStream.readInt();
            long j = dataInputStream.readLong();
            long j2 = dataInputStream.readLong();
            boolean z = dataInputStream.readBoolean();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(p90.getInstance(obj));
            }
            for (int i3 = 0; i3 < i - 1; i3++) {
                arrayList2.add(r90.getInstance(obj));
            }
            return new s30(i, arrayList, arrayList2, j, j2, z);
        }
        if (!(obj instanceof byte[])) {
            if (obj instanceof InputStream) {
                return getInstance(i21.readAll((InputStream) obj));
            }
            throw new IllegalArgumentException("cannot parse " + obj);
        }
        DataInputStream dataInputStream2 = null;
        try {
            DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream((byte[]) obj));
            try {
                s30 s30Var = getInstance(dataInputStream3);
                dataInputStream3.close();
                return s30Var;
            } catch (Throwable th2) {
                th = th2;
                dataInputStream2 = dataInputStream3;
                if (dataInputStream2 == null) {
                    throw th;
                }
                dataInputStream2.close();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static s30 makeCopy(s30 s30Var) {
        try {
            return getInstance(s30Var.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return makeCopy(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        s30 s30Var = (s30) obj;
        if (this.f59857l == s30Var.f59857l && this.isShard == s30Var.isShard && this.indexLimit == s30Var.indexLimit && this.index == s30Var.index && this.keys.equals(s30Var.keys)) {
            return this.sig.equals(s30Var.sig);
        }
        return false;
    }

    public s30 extractKeyShard(int i) {
        s30 s30VarMakeCopy;
        synchronized (this) {
            try {
                long j = i;
                if (getUsagesRemaining() < j) {
                    throw new IllegalArgumentException("usageCount exceeds usages remaining in current leaf");
                }
                long j2 = this.index;
                this.index = j + j2;
                s30VarMakeCopy = makeCopy(new s30(this.f59857l, new ArrayList(getKeys()), new ArrayList(getSig()), j2, j2 + j, true));
                resetKeyToIndex();
            } catch (Throwable th) {
                throw th;
            }
        }
        return s30VarMakeCopy;
    }

    @Override // p000.k90
    public j90 generateLMSContext() {
        p90 p90Var;
        s90[] s90VarArr;
        int l = getL();
        synchronized (this) {
            try {
                q30.rangeTestKeys(this);
                List<p90> keys = getKeys();
                List<r90> sig = getSig();
                int i = l - 1;
                p90Var = getKeys().get(i);
                s90VarArr = new s90[i];
                int i2 = 0;
                while (i2 < i) {
                    int i3 = i2 + 1;
                    s90VarArr[i2] = new s90(sig.get(i2), keys.get(i3).getPublicKey());
                    i2 = i3;
                }
                incIndex();
            } catch (Throwable th) {
                throw th;
            }
        }
        return p90Var.generateLMSContext().withSignedPublicKeys(s90VarArr);
    }

    @Override // p000.k90
    public byte[] generateSignature(j90 j90Var) {
        try {
            return q30.generateSignature(getL(), j90Var).getEncoded();
        } catch (IOException e) {
            throw new IllegalStateException(AbstractC0003a2.m26a7(e, new StringBuilder("unable to encode signature: ")), e);
        }
    }

    @Override // p000.n90, p000.InterfaceC1394wy
    public synchronized byte[] getEncoded() throws IOException {
        C0752kb c0752kbBool;
        try {
            c0752kbBool = C0752kb.compose().u32str(0).u32str(this.f59857l).u64str(this.index).u64str(this.indexLimit).bool(this.isShard);
            Iterator<p90> it = this.keys.iterator();
            while (it.hasNext()) {
                c0752kbBool.bytes(it.next());
            }
            Iterator<r90> it2 = this.sig.iterator();
            while (it2.hasNext()) {
                c0752kbBool.bytes(it2.next());
            }
        } catch (Throwable th) {
            throw th;
        }
        return c0752kbBool.build();
    }

    public synchronized long getIndex() {
        return this.index;
    }

    public long getIndexLimit() {
        return this.indexLimit;
    }

    public synchronized List<p90> getKeys() {
        return this.keys;
    }

    public int getL() {
        return this.f59857l;
    }

    public synchronized o90[] getLMSParameters() {
        o90[] o90VarArr;
        int size = this.keys.size();
        o90VarArr = new o90[size];
        for (int i = 0; i < size; i++) {
            p90 p90Var = this.keys.get(i);
            o90VarArr[i] = new o90(p90Var.getSigParameters(), p90Var.getOtsParameters());
        }
        return o90VarArr;
    }

    public synchronized t30 getPublicKey() {
        return new t30(this.f59857l, getRootKey().getPublicKey());
    }

    public p90 getRootKey() {
        return this.keys.get(0);
    }

    public synchronized List<r90> getSig() {
        return this.sig;
    }

    @Override // p000.k90
    public long getUsagesRemaining() {
        return this.indexLimit - this.index;
    }

    public int hashCode() {
        int iHashCode = (this.sig.hashCode() + ((this.keys.hashCode() + (((this.f59857l * 31) + (this.isShard ? 1 : 0)) * 31)) * 31)) * 31;
        long j = this.indexLimit;
        int i = (iHashCode + ((int) (j ^ (j >>> 32)))) * 31;
        long j2 = this.index;
        return i + ((int) (j2 ^ (j2 >>> 32)));
    }

    public synchronized void incIndex() {
        this.index++;
    }

    public boolean isShard() {
        return this.isShard;
    }

    public void replaceConsumedKey(int i) {
        int i2 = i - 1;
        iz0 derivationFunction = this.keys.get(i2).getCurrentOTSKey().getDerivationFunction();
        derivationFunction.setJ(-2);
        byte[] bArr = new byte[32];
        derivationFunction.deriveSeed(bArr, true);
        byte[] bArr2 = new byte[32];
        derivationFunction.deriveSeed(bArr2, false);
        byte[] bArr3 = new byte[16];
        System.arraycopy(bArr2, 0, bArr3, 0, 16);
        ArrayList arrayList = new ArrayList(this.keys);
        p90 p90Var = this.keys.get(i);
        arrayList.set(i, i90.generateKeys(p90Var.getSigParameters(), p90Var.getOtsParameters(), 0, bArr3, bArr));
        ArrayList arrayList2 = new ArrayList(this.sig);
        arrayList2.set(i2, i90.generateSign((p90) arrayList.get(i2), ((p90) arrayList.get(i)).getPublicKey().toByteArray()));
        this.keys = Collections.unmodifiableList(arrayList);
        this.sig = Collections.unmodifiableList(arrayList2);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00d5 A[PHI: r16
      0x00d5: PHI (r16v2 int) = (r16v0 int), (r16v4 int) binds: [B:17:0x00e4, B:14:0x00d3] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void resetKeyToIndex() throws IllegalArgumentException {
        boolean z;
        int i;
        int i2;
        List<p90> keys = getKeys();
        int size = keys.size();
        long[] jArr = new long[size];
        long index = getIndex();
        for (int size2 = keys.size() - 1; size2 >= 0; size2--) {
            LMSigParameters sigParameters = keys.get(size2).getSigParameters();
            jArr[size2] = ((1 << sigParameters.getH()) - 1) & index;
            index >>>= sigParameters.getH();
        }
        p90[] p90VarArr = (p90[]) keys.toArray(new p90[keys.size()]);
        List<r90> list = this.sig;
        r90[] r90VarArr = (r90[]) list.toArray(new r90[list.size()]);
        p90 rootKey = getRootKey();
        if (p90VarArr[0].getIndex() - 1 != jArr[0]) {
            p90VarArr[0] = i90.generateKeys(rootKey.getSigParameters(), rootKey.getOtsParameters(), (int) jArr[0], rootKey.getI(), rootKey.getMasterSecret());
            z = true;
        } else {
            z = false;
        }
        int i3 = 1;
        while (i3 < size) {
            int i4 = i3 - 1;
            p90 p90Var = p90VarArr[i4];
            byte[] bArr = new byte[16];
            byte[] bArr2 = new byte[32];
            iz0 iz0Var = new iz0(p90Var.getI(), p90Var.getMasterSecret(), C1256te.getDigest(p90Var.getOtsParameters().getDigestOID()));
            iz0Var.setQ((int) jArr[i4]);
            iz0Var.setJ(-2);
            iz0Var.deriveSeed(bArr2, true);
            byte[] bArr3 = new byte[32];
            boolean z2 = false;
            iz0Var.deriveSeed(bArr3, false);
            System.arraycopy(bArr3, 0, bArr, 0, 16);
            if (i3 < size - 1) {
                i = i3;
                if (jArr[i3] == p90VarArr[i3].getIndex() - 1) {
                    z2 = true;
                }
            } else {
                i = i3;
                if (jArr[i3] != p90VarArr[i3].getIndex()) {
                    z2 = false;
                }
            }
            if (C0133bg.areEqual(bArr, p90VarArr[i].getI()) && C0133bg.areEqual(bArr2, p90VarArr[i].getMasterSecret())) {
                i2 = i;
                if (z2) {
                    i3 = i2 + 1;
                } else {
                    p90VarArr[i2] = i90.generateKeys(keys.get(i2).getSigParameters(), keys.get(i2).getOtsParameters(), (int) jArr[i2], bArr, bArr2);
                }
            } else {
                i2 = i;
                p90 p90VarGenerateKeys = i90.generateKeys(keys.get(i2).getSigParameters(), keys.get(i2).getOtsParameters(), (int) jArr[i2], bArr, bArr2);
                p90VarArr[i2] = p90VarGenerateKeys;
                r90VarArr[i4] = i90.generateSign(p90VarArr[i4], p90VarGenerateKeys.getPublicKey().toByteArray());
            }
            z = true;
            i3 = i2 + 1;
        }
        if (z) {
            updateHierarchy(p90VarArr, r90VarArr);
        }
    }

    public void updateHierarchy(p90[] p90VarArr, r90[] r90VarArr) {
        synchronized (this) {
            this.keys = Collections.unmodifiableList(Arrays.asList(p90VarArr));
            this.sig = Collections.unmodifiableList(Arrays.asList(r90VarArr));
        }
    }

    private s30(int i, List<p90> list, List<r90> list2, long j, long j2, boolean z) {
        super(true);
        this.index = 0L;
        this.f59857l = i;
        this.keys = Collections.unmodifiableList(list);
        this.sig = Collections.unmodifiableList(list2);
        this.index = j;
        this.indexLimit = j2;
        this.isShard = z;
    }

    public static s30 getInstance(byte[] bArr, byte[] bArr2) throws Throwable {
        s30 s30Var = getInstance(bArr);
        s30Var.publicKey = t30.getInstance(bArr2);
        return s30Var;
    }
}
