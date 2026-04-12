package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class q90 extends n90 implements l90 {

    /* renamed from: I */
    private final byte[] f59439I;

    /* renamed from: T1 */
    private final byte[] f59440T1;
    private final LMOtsParameters lmOtsType;
    private final LMSigParameters parameterSet;

    public q90(LMSigParameters lMSigParameters, LMOtsParameters lMOtsParameters, byte[] bArr, byte[] bArr2) {
        super(false);
        this.parameterSet = lMSigParameters;
        this.lmOtsType = lMOtsParameters;
        this.f59439I = C0133bg.clone(bArr2);
        this.f59440T1 = C0133bg.clone(bArr);
    }

    public static q90 getInstance(Object obj) throws Throwable {
        if (obj instanceof q90) {
            return (q90) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            LMSigParameters parametersForType = LMSigParameters.getParametersForType(dataInputStream.readInt());
            LMOtsParameters parametersForType2 = LMOtsParameters.getParametersForType(dataInputStream.readInt());
            byte[] bArr = new byte[16];
            dataInputStream.readFully(bArr);
            byte[] bArr2 = new byte[parametersForType.getM()];
            dataInputStream.readFully(bArr2);
            return new q90(parametersForType, parametersForType2, bArr2, bArr);
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
                q90 q90Var = getInstance(dataInputStream3);
                dataInputStream3.close();
                return q90Var;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        q90 q90Var = (q90) obj;
        if (this.parameterSet.equals(q90Var.parameterSet) && this.lmOtsType.equals(q90Var.lmOtsType) && C0133bg.areEqual(this.f59439I, q90Var.f59439I)) {
            return C0133bg.areEqual(this.f59440T1, q90Var.f59440T1);
        }
        return false;
    }

    @Override // p000.l90
    public j90 generateLMSContext(byte[] bArr) {
        try {
            return generateOtsContext(r90.getInstance(bArr));
        } catch (IOException e) {
            throw new IllegalStateException(AbstractC0003a2.m26a7(e, new StringBuilder("cannot parse signature: ")));
        }
    }

    public j90 generateOtsContext(r90 r90Var) {
        int type = getOtsParameters().getType();
        if (r90Var.getOtsSignature().getType().getType() == type) {
            return new g90(LMOtsParameters.getParametersForType(type), this.f59439I, r90Var.getQ(), null).createOtsContext(r90Var);
        }
        throw new IllegalArgumentException("ots type from lsm signature does not match ots signature type from embedded ots signature");
    }

    @Override // p000.n90, p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return toByteArray();
    }

    public byte[] getI() {
        return C0133bg.clone(this.f59439I);
    }

    public o90 getLMSParameters() {
        return new o90(getSigParameters(), getOtsParameters());
    }

    public LMOtsParameters getOtsParameters() {
        return this.lmOtsType;
    }

    public LMSigParameters getSigParameters() {
        return this.parameterSet;
    }

    public byte[] getT1() {
        return C0133bg.clone(this.f59440T1);
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f59440T1) + ((C0133bg.hashCode(this.f59439I) + ((this.lmOtsType.hashCode() + (this.parameterSet.hashCode() * 31)) * 31)) * 31);
    }

    public boolean matchesT1(byte[] bArr) {
        return C0133bg.constantTimeAreEqual(this.f59440T1, bArr);
    }

    public byte[] refI() {
        return this.f59439I;
    }

    public byte[] toByteArray() {
        return C0752kb.compose().u32str(this.parameterSet.getType()).u32str(this.lmOtsType.getType()).bytes(this.f59439I).bytes(this.f59440T1).build();
    }

    @Override // p000.l90
    public boolean verify(j90 j90Var) {
        return i90.verifySignature(this, j90Var);
    }
}
