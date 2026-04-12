package p000;

import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class j90 implements InterfaceC1236sv {

    /* renamed from: C */
    private final byte[] f57308C;
    private volatile InterfaceC1236sv digest;
    private final f90 key;
    private final byte[][] path;
    private final g90 publicKey;
    private final LMSigParameters sigParams;
    private final Object signature;
    private s90[] signedPubKeys;

    public j90(f90 f90Var, LMSigParameters lMSigParameters, InterfaceC1236sv interfaceC1236sv, byte[] bArr, byte[][] bArr2) {
        this.key = f90Var;
        this.sigParams = lMSigParameters;
        this.digest = interfaceC1236sv;
        this.f57308C = bArr;
        this.path = bArr2;
        this.publicKey = null;
        this.signature = null;
    }

    @Override // p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        return this.digest.doFinal(bArr, i);
    }

    @Override // p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return this.digest.getAlgorithmName();
    }

    public byte[] getC() {
        return this.f57308C;
    }

    @Override // p000.InterfaceC1236sv
    public int getDigestSize() {
        return this.digest.getDigestSize();
    }

    public byte[][] getPath() {
        return this.path;
    }

    public f90 getPrivateKey() {
        return this.key;
    }

    public g90 getPublicKey() {
        return this.publicKey;
    }

    public byte[] getQ() {
        byte[] bArr = new byte[34];
        this.digest.doFinal(bArr, 0);
        this.digest = null;
        return bArr;
    }

    public LMSigParameters getSigParams() {
        return this.sigParams;
    }

    public Object getSignature() {
        return this.signature;
    }

    public s90[] getSignedPubKeys() {
        return this.signedPubKeys;
    }

    @Override // p000.InterfaceC1236sv
    public void reset() {
        this.digest.reset();
    }

    @Override // p000.InterfaceC1236sv
    public void update(byte b) {
        this.digest.update(b);
    }

    public j90 withSignedPublicKeys(s90[] s90VarArr) {
        this.signedPubKeys = s90VarArr;
        return this;
    }

    public j90(g90 g90Var, Object obj, InterfaceC1236sv interfaceC1236sv) {
        this.publicKey = g90Var;
        this.signature = obj;
        this.digest = interfaceC1236sv;
        this.f57308C = null;
        this.key = null;
        this.sigParams = null;
        this.path = null;
    }

    @Override // p000.InterfaceC1236sv
    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }
}
