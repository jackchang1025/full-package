package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.WeakHashMap;
import org.bouncycastle.pqc.crypto.ExhaustedPrivateKeyException;
import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class p90 extends n90 implements k90 {

    /* renamed from: T1 */
    private static C1061a0 f59167T1;
    private static C1061a0[] internedKeys;

    /* renamed from: I */
    private final byte[] f59168I;
    private final byte[] masterSecret;
    private final int maxCacheR;
    private final int maxQ;
    private final LMOtsParameters otsParameters;
    private final LMSigParameters parameters;
    private q90 publicKey;

    /* renamed from: q */
    private int f59169q;
    private final Map<C1061a0, byte[]> tCache;
    private final InterfaceC1236sv tDigest;

    /* renamed from: p90$a0 */
    public static class C1061a0 {
        private final int index;

        public C1061a0(int i) {
            this.index = i;
        }

        public boolean equals(Object obj) {
            return (obj instanceof C1061a0) && ((C1061a0) obj).index == this.index;
        }

        public int hashCode() {
            return this.index;
        }
    }

    static {
        C1061a0 c1061a0 = new C1061a0(1);
        f59167T1 = c1061a0;
        C1061a0[] c1061a0Arr = new C1061a0[129];
        internedKeys = c1061a0Arr;
        c1061a0Arr[1] = c1061a0;
        int i = 2;
        while (true) {
            C1061a0[] c1061a0Arr2 = internedKeys;
            if (i >= c1061a0Arr2.length) {
                return;
            }
            c1061a0Arr2[i] = new C1061a0(i);
            i++;
        }
    }

    private p90(p90 p90Var, int i, int i2) {
        super(true);
        LMSigParameters lMSigParameters = p90Var.parameters;
        this.parameters = lMSigParameters;
        this.otsParameters = p90Var.otsParameters;
        this.f59169q = i;
        this.f59168I = p90Var.f59168I;
        this.maxQ = i2;
        this.masterSecret = p90Var.masterSecret;
        this.maxCacheR = 1 << lMSigParameters.getH();
        this.tCache = p90Var.tCache;
        this.tDigest = C1256te.getDigest(lMSigParameters.getDigestOID());
        this.publicKey = p90Var.publicKey;
    }

    private byte[] calcT(int i) {
        int h = 1 << getSigParameters().getH();
        if (i >= h) {
            xb0.byteArray(getI(), this.tDigest);
            xb0.u32str(i, this.tDigest);
            xb0.u16str((short) -32126, this.tDigest);
            xb0.byteArray(t90.lms_ots_generatePublicKey(getOtsParameters(), getI(), i - h, getMasterSecret()), this.tDigest);
            byte[] bArr = new byte[this.tDigest.getDigestSize()];
            this.tDigest.doFinal(bArr, 0);
            return bArr;
        }
        int i2 = i * 2;
        byte[] bArrFindT = findT(i2);
        byte[] bArrFindT2 = findT(i2 + 1);
        xb0.byteArray(getI(), this.tDigest);
        xb0.u32str(i, this.tDigest);
        xb0.u16str((short) -31869, this.tDigest);
        xb0.byteArray(bArrFindT, this.tDigest);
        xb0.byteArray(bArrFindT2, this.tDigest);
        byte[] bArr2 = new byte[this.tDigest.getDigestSize()];
        this.tDigest.doFinal(bArr2, 0);
        return bArr2;
    }

    public static p90 getInstance(Object obj) throws Throwable {
        Throwable th;
        if (obj instanceof p90) {
            return (p90) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            if (dataInputStream.readInt() != 0) {
                throw new IllegalStateException("expected version 0 lms private key");
            }
            LMSigParameters parametersForType = LMSigParameters.getParametersForType(dataInputStream.readInt());
            LMOtsParameters parametersForType2 = LMOtsParameters.getParametersForType(dataInputStream.readInt());
            byte[] bArr = new byte[16];
            dataInputStream.readFully(bArr);
            int i = dataInputStream.readInt();
            int i2 = dataInputStream.readInt();
            int i3 = dataInputStream.readInt();
            if (i3 < 0) {
                throw new IllegalStateException("secret length less than zero");
            }
            if (i3 <= dataInputStream.available()) {
                byte[] bArr2 = new byte[i3];
                dataInputStream.readFully(bArr2);
                return new p90(parametersForType, parametersForType2, i, bArr, i2, bArr2);
            }
            throw new IOException("secret length exceeded " + dataInputStream.available());
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
                p90 p90Var = getInstance(dataInputStream3);
                dataInputStream3.close();
                return p90Var;
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

    public boolean equals(Object obj) {
        q90 q90Var;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        p90 p90Var = (p90) obj;
        if (this.f59169q != p90Var.f59169q || this.maxQ != p90Var.maxQ || !C0133bg.areEqual(this.f59168I, p90Var.f59168I)) {
            return false;
        }
        LMSigParameters lMSigParameters = this.parameters;
        if (lMSigParameters == null ? p90Var.parameters != null : !lMSigParameters.equals(p90Var.parameters)) {
            return false;
        }
        LMOtsParameters lMOtsParameters = this.otsParameters;
        if (lMOtsParameters == null ? p90Var.otsParameters != null : !lMOtsParameters.equals(p90Var.otsParameters)) {
            return false;
        }
        if (!C0133bg.areEqual(this.masterSecret, p90Var.masterSecret)) {
            return false;
        }
        q90 q90Var2 = this.publicKey;
        if (q90Var2 == null || (q90Var = p90Var.publicKey) == null) {
            return true;
        }
        return q90Var2.equals(q90Var);
    }

    public p90 extractKeyShard(int i) {
        p90 p90Var;
        synchronized (this) {
            try {
                int i2 = this.f59169q;
                if (i2 + i >= this.maxQ) {
                    throw new IllegalArgumentException("usageCount exceeds usages remaining");
                }
                p90Var = new p90(this, i2, i2 + i);
                this.f59169q += i;
            } catch (Throwable th) {
                throw th;
            }
        }
        return p90Var;
    }

    public byte[] findT(int i) {
        if (i >= this.maxCacheR) {
            return calcT(i);
        }
        C1061a0[] c1061a0Arr = internedKeys;
        return findT(i < c1061a0Arr.length ? c1061a0Arr[i] : new C1061a0(i));
    }

    @Override // p000.k90
    public j90 generateLMSContext() {
        int h = getSigParameters().getH();
        int index = getIndex();
        f90 nextOtsPrivateKey = getNextOtsPrivateKey();
        int i = (1 << h) + index;
        byte[][] bArr = new byte[h][];
        for (int i2 = 0; i2 < h; i2++) {
            bArr[i2] = findT((i / (1 << i2)) ^ 1);
        }
        return nextOtsPrivateKey.getSignatureContext(getSigParameters(), bArr);
    }

    @Override // p000.k90
    public byte[] generateSignature(j90 j90Var) {
        try {
            return i90.generateSign(j90Var).getEncoded();
        } catch (IOException e) {
            throw new IllegalStateException(AbstractC0003a2.m26a7(e, new StringBuilder("unable to encode signature: ")), e);
        }
    }

    public f90 getCurrentOTSKey() {
        f90 f90Var;
        synchronized (this) {
            try {
                int i = this.f59169q;
                if (i >= this.maxQ) {
                    throw new ExhaustedPrivateKeyException("ots private keys expired");
                }
                f90Var = new f90(this.otsParameters, this.f59168I, i, this.masterSecret);
            } catch (Throwable th) {
                throw th;
            }
        }
        return f90Var;
    }

    @Override // p000.n90, p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return C0752kb.compose().u32str(0).u32str(this.parameters.getType()).u32str(this.otsParameters.getType()).bytes(this.f59168I).u32str(this.f59169q).u32str(this.maxQ).u32str(this.masterSecret.length).bytes(this.masterSecret).build();
    }

    public byte[] getI() {
        return C0133bg.clone(this.f59168I);
    }

    public synchronized int getIndex() {
        return this.f59169q;
    }

    public byte[] getMasterSecret() {
        return C0133bg.clone(this.masterSecret);
    }

    public f90 getNextOtsPrivateKey() {
        f90 f90Var;
        synchronized (this) {
            try {
                int i = this.f59169q;
                if (i >= this.maxQ) {
                    throw new ExhaustedPrivateKeyException("ots private key exhausted");
                }
                f90Var = new f90(this.otsParameters, this.f59168I, i, this.masterSecret);
                incIndex();
            } catch (Throwable th) {
                throw th;
            }
        }
        return f90Var;
    }

    public LMOtsParameters getOtsParameters() {
        return this.otsParameters;
    }

    public q90 getPublicKey() {
        q90 q90Var;
        synchronized (this) {
            try {
                if (this.publicKey == null) {
                    this.publicKey = new q90(this.parameters, this.otsParameters, findT(f59167T1), this.f59168I);
                }
                q90Var = this.publicKey;
            } catch (Throwable th) {
                throw th;
            }
        }
        return q90Var;
    }

    public LMSigParameters getSigParameters() {
        return this.parameters;
    }

    @Override // p000.k90
    public long getUsagesRemaining() {
        return this.maxQ - this.f59169q;
    }

    public int hashCode() {
        int iHashCode = (C0133bg.hashCode(this.f59168I) + (this.f59169q * 31)) * 31;
        LMSigParameters lMSigParameters = this.parameters;
        int iHashCode2 = (iHashCode + (lMSigParameters != null ? lMSigParameters.hashCode() : 0)) * 31;
        LMOtsParameters lMOtsParameters = this.otsParameters;
        int iHashCode3 = (C0133bg.hashCode(this.masterSecret) + ((((iHashCode2 + (lMOtsParameters != null ? lMOtsParameters.hashCode() : 0)) * 31) + this.maxQ) * 31)) * 31;
        q90 q90Var = this.publicKey;
        return iHashCode3 + (q90Var != null ? q90Var.hashCode() : 0);
    }

    public synchronized void incIndex() {
        this.f59169q++;
    }

    public p90(LMSigParameters lMSigParameters, LMOtsParameters lMOtsParameters, int i, byte[] bArr, int i2, byte[] bArr2) {
        super(true);
        this.parameters = lMSigParameters;
        this.otsParameters = lMOtsParameters;
        this.f59169q = i;
        this.f59168I = C0133bg.clone(bArr);
        this.maxQ = i2;
        this.masterSecret = C0133bg.clone(bArr2);
        this.maxCacheR = 1 << (lMSigParameters.getH() + 1);
        this.tCache = new WeakHashMap();
        this.tDigest = C1256te.getDigest(lMSigParameters.getDigestOID());
    }

    private byte[] findT(C1061a0 c1061a0) {
        byte[] bArrCalcT;
        synchronized (this.tCache) {
            try {
                bArrCalcT = this.tCache.get(c1061a0);
                if (bArrCalcT == null) {
                    bArrCalcT = calcT(c1061a0.index);
                    this.tCache.put(c1061a0, bArrCalcT);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bArrCalcT;
    }

    public static p90 getInstance(byte[] bArr, byte[] bArr2) throws Throwable {
        p90 p90Var = getInstance(bArr);
        p90Var.publicKey = q90.getInstance(bArr2);
        return p90Var;
    }
}
