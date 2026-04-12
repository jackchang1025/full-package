package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;

/* loaded from: classes2.dex */
public class h90 implements InterfaceC1394wy {

    /* renamed from: C */
    private final byte[] f56635C;
    private final LMOtsParameters type;

    /* renamed from: y */
    private final byte[] f56636y;

    public h90(LMOtsParameters lMOtsParameters, byte[] bArr, byte[] bArr2) {
        this.type = lMOtsParameters;
        this.f56635C = bArr;
        this.f56636y = bArr2;
    }

    public static h90 getInstance(Object obj) throws Throwable {
        if (obj instanceof h90) {
            return (h90) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            LMOtsParameters parametersForType = LMOtsParameters.getParametersForType(dataInputStream.readInt());
            byte[] bArr = new byte[parametersForType.getN()];
            dataInputStream.readFully(bArr);
            byte[] bArr2 = new byte[parametersForType.getN() * parametersForType.getP()];
            dataInputStream.readFully(bArr2);
            return new h90(parametersForType, bArr, bArr2);
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
                h90 h90Var = getInstance(dataInputStream3);
                dataInputStream3.close();
                return h90Var;
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
        h90 h90Var = (h90) obj;
        LMOtsParameters lMOtsParameters = this.type;
        if (lMOtsParameters == null ? h90Var.type != null : !lMOtsParameters.equals(h90Var.type)) {
            return false;
        }
        if (Arrays.equals(this.f56635C, h90Var.f56635C)) {
            return Arrays.equals(this.f56636y, h90Var.f56636y);
        }
        return false;
    }

    public byte[] getC() {
        return this.f56635C;
    }

    @Override // p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return C0752kb.compose().u32str(this.type.getType()).bytes(this.f56635C).bytes(this.f56636y).build();
    }

    public LMOtsParameters getType() {
        return this.type;
    }

    public byte[] getY() {
        return this.f56636y;
    }

    public int hashCode() {
        LMOtsParameters lMOtsParameters = this.type;
        return Arrays.hashCode(this.f56636y) + ((Arrays.hashCode(this.f56635C) + ((lMOtsParameters != null ? lMOtsParameters.hashCode() : 0) * 31)) * 31);
    }
}
