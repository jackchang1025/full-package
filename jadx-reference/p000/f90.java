package p000;

import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class f90 {

    /* renamed from: I */
    private final byte[] f56189I;
    private final byte[] masterSecret;
    private final LMOtsParameters parameter;

    /* renamed from: q */
    private final int f56190q;

    public f90(LMOtsParameters lMOtsParameters, byte[] bArr, int i, byte[] bArr2) {
        this.parameter = lMOtsParameters;
        this.f56189I = bArr;
        this.f56190q = i;
        this.masterSecret = bArr2;
    }

    public iz0 getDerivationFunction() {
        iz0 iz0Var = new iz0(this.f56189I, this.masterSecret, C1256te.getDigest(this.parameter.getDigestOID()));
        iz0Var.setQ(this.f56190q);
        return iz0Var;
    }

    public byte[] getI() {
        return this.f56189I;
    }

    public byte[] getMasterSecret() {
        return this.masterSecret;
    }

    public LMOtsParameters getParameter() {
        return this.parameter;
    }

    public int getQ() {
        return this.f56190q;
    }

    public j90 getSignatureContext(LMSigParameters lMSigParameters, byte[][] bArr) {
        byte[] bArr2 = new byte[32];
        iz0 derivationFunction = getDerivationFunction();
        derivationFunction.setJ(-3);
        derivationFunction.deriveSeed(bArr2, false);
        InterfaceC1236sv digest = C1256te.getDigest(this.parameter.getDigestOID());
        xb0.byteArray(getI(), digest);
        xb0.u32str(getQ(), digest);
        xb0.u16str((short) -32383, digest);
        xb0.byteArray(bArr2, digest);
        return new j90(this, lMSigParameters, digest, bArr2, bArr);
    }
}
