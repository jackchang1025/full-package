package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class r90 implements InterfaceC1394wy {
    private final h90 otsSignature;
    private final LMSigParameters parameter;

    /* renamed from: q */
    private final int f59648q;

    /* renamed from: y */
    private final byte[][] f59649y;

    public r90(int i, h90 h90Var, LMSigParameters lMSigParameters, byte[][] bArr) {
        this.f59648q = i;
        this.otsSignature = h90Var;
        this.parameter = lMSigParameters;
        this.f59649y = bArr;
    }

    public static r90 getInstance(Object obj) throws Throwable {
        if (obj instanceof r90) {
            return (r90) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            int i = dataInputStream.readInt();
            h90 h90Var = h90.getInstance(obj);
            LMSigParameters parametersForType = LMSigParameters.getParametersForType(dataInputStream.readInt());
            int h = parametersForType.getH();
            byte[][] bArr = new byte[h][];
            for (int i2 = 0; i2 < h; i2++) {
                byte[] bArr2 = new byte[parametersForType.getM()];
                bArr[i2] = bArr2;
                dataInputStream.readFully(bArr2);
            }
            return new r90(i, h90Var, parametersForType, bArr);
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
                r90 r90Var = getInstance(dataInputStream3);
                dataInputStream3.close();
                return r90Var;
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
        r90 r90Var = (r90) obj;
        if (this.f59648q != r90Var.f59648q) {
            return false;
        }
        h90 h90Var = this.otsSignature;
        if (h90Var == null ? r90Var.otsSignature != null : !h90Var.equals(r90Var.otsSignature)) {
            return false;
        }
        LMSigParameters lMSigParameters = this.parameter;
        if (lMSigParameters == null ? r90Var.parameter == null : lMSigParameters.equals(r90Var.parameter)) {
            return Arrays.deepEquals(this.f59649y, r90Var.f59649y);
        }
        return false;
    }

    @Override // p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return C0752kb.compose().u32str(this.f59648q).bytes(this.otsSignature.getEncoded()).u32str(this.parameter.getType()).bytes(this.f59649y).build();
    }

    public h90 getOtsSignature() {
        return this.otsSignature;
    }

    public LMSigParameters getParameter() {
        return this.parameter;
    }

    public int getQ() {
        return this.f59648q;
    }

    public byte[][] getY() {
        return this.f59649y;
    }

    public int hashCode() {
        int i = this.f59648q * 31;
        h90 h90Var = this.otsSignature;
        int iHashCode = (i + (h90Var != null ? h90Var.hashCode() : 0)) * 31;
        LMSigParameters lMSigParameters = this.parameter;
        return Arrays.deepHashCode(this.f59649y) + ((iHashCode + (lMSigParameters != null ? lMSigParameters.hashCode() : 0)) * 31);
    }
}
