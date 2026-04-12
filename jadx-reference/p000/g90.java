package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;

/* loaded from: classes2.dex */
public class g90 implements InterfaceC1394wy {

    /* renamed from: I */
    private final byte[] f56427I;

    /* renamed from: K */
    private final byte[] f56428K;
    private final LMOtsParameters parameter;

    /* renamed from: q */
    private final int f56429q;

    public g90(LMOtsParameters lMOtsParameters, byte[] bArr, int i, byte[] bArr2) {
        this.parameter = lMOtsParameters;
        this.f56427I = bArr;
        this.f56429q = i;
        this.f56428K = bArr2;
    }

    public static g90 getInstance(Object obj) throws Exception {
        if (obj instanceof g90) {
            return (g90) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            LMOtsParameters parametersForType = LMOtsParameters.getParametersForType(dataInputStream.readInt());
            byte[] bArr = new byte[16];
            dataInputStream.readFully(bArr);
            int i = dataInputStream.readInt();
            byte[] bArr2 = new byte[parametersForType.getN()];
            dataInputStream.readFully(bArr2);
            return new g90(parametersForType, bArr, i, bArr2);
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
                g90 g90Var = getInstance(dataInputStream3);
                dataInputStream3.close();
                return g90Var;
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

    public j90 createOtsContext(h90 h90Var) {
        InterfaceC1236sv digest = C1256te.getDigest(this.parameter.getDigestOID());
        xb0.byteArray(this.f56427I, digest);
        xb0.u32str(this.f56429q, digest);
        xb0.u16str((short) -32383, digest);
        xb0.byteArray(h90Var.getC(), digest);
        return new j90(this, h90Var, digest);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        g90 g90Var = (g90) obj;
        if (this.f56429q != g90Var.f56429q) {
            return false;
        }
        LMOtsParameters lMOtsParameters = this.parameter;
        if (lMOtsParameters == null ? g90Var.parameter != null : !lMOtsParameters.equals(g90Var.parameter)) {
            return false;
        }
        if (Arrays.equals(this.f56427I, g90Var.f56427I)) {
            return Arrays.equals(this.f56428K, g90Var.f56428K);
        }
        return false;
    }

    @Override // p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return C0752kb.compose().u32str(this.parameter.getType()).bytes(this.f56427I).u32str(this.f56429q).bytes(this.f56428K).build();
    }

    public byte[] getI() {
        return this.f56427I;
    }

    public byte[] getK() {
        return this.f56428K;
    }

    public LMOtsParameters getParameter() {
        return this.parameter;
    }

    public int getQ() {
        return this.f56429q;
    }

    public int hashCode() {
        LMOtsParameters lMOtsParameters = this.parameter;
        return Arrays.hashCode(this.f56428K) + ((((Arrays.hashCode(this.f56427I) + ((lMOtsParameters != null ? lMOtsParameters.hashCode() : 0) * 31)) * 31) + this.f56429q) * 31);
    }

    public j90 createOtsContext(r90 r90Var) {
        InterfaceC1236sv digest = C1256te.getDigest(this.parameter.getDigestOID());
        xb0.byteArray(this.f56427I, digest);
        xb0.u32str(this.f56429q, digest);
        xb0.u16str((short) -32383, digest);
        xb0.byteArray(r90Var.getOtsSignature().getC(), digest);
        return new j90(this, r90Var, digest);
    }
}
