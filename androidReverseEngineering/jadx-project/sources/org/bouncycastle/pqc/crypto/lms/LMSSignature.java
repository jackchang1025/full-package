package org.bouncycastle.pqc.crypto.lms;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Arrays;
import org.bouncycastle.util.Encodable;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
class LMSSignature implements Encodable {
    private final LMOtsSignature otsSignature;
    private final LMSigParameters parameter;

    /* renamed from: q */
    private final int f1564q;

    /* renamed from: y */
    private final byte[][] f1565y;

    public LMSSignature(int i2, LMOtsSignature lMOtsSignature, LMSigParameters lMSigParameters, byte[][] bArr) {
        this.f1564q = i2;
        this.otsSignature = lMOtsSignature;
        this.parameter = lMSigParameters;
        this.f1565y = bArr;
    }

    public static LMSSignature getInstance(Object obj) {
        if (obj instanceof LMSSignature) {
            return (LMSSignature) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            int readInt = dataInputStream.readInt();
            LMOtsSignature lMOtsSignature = LMOtsSignature.getInstance(obj);
            LMSigParameters parametersForType = LMSigParameters.getParametersForType(dataInputStream.readInt());
            int h2 = parametersForType.getH();
            byte[][] bArr = new byte[h2][];
            for (int i2 = 0; i2 < h2; i2++) {
                byte[] bArr2 = new byte[parametersForType.getM()];
                bArr[i2] = bArr2;
                dataInputStream.readFully(bArr2);
            }
            return new LMSSignature(readInt, lMOtsSignature, parametersForType, bArr);
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
                LMSSignature lMSSignature = getInstance(dataInputStream3);
                dataInputStream3.close();
                return lMSSignature;
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
        LMSSignature lMSSignature = (LMSSignature) obj;
        if (this.f1564q != lMSSignature.f1564q) {
            return false;
        }
        LMOtsSignature lMOtsSignature = this.otsSignature;
        if (lMOtsSignature == null ? lMSSignature.otsSignature != null : !lMOtsSignature.equals(lMSSignature.otsSignature)) {
            return false;
        }
        LMSigParameters lMSigParameters = this.parameter;
        if (lMSigParameters == null ? lMSSignature.parameter == null : lMSigParameters.equals(lMSSignature.parameter)) {
            return Arrays.deepEquals(this.f1565y, lMSSignature.f1565y);
        }
        return false;
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return Composer.compose().u32str(this.f1564q).bytes(this.otsSignature.getEncoded()).u32str(this.parameter.getType()).bytes(this.f1565y).build();
    }

    public LMOtsSignature getOtsSignature() {
        return this.otsSignature;
    }

    public LMSigParameters getParameter() {
        return this.parameter;
    }

    public int getQ() {
        return this.f1564q;
    }

    public byte[][] getY() {
        return this.f1565y;
    }

    public int hashCode() {
        int i2 = this.f1564q * 31;
        LMOtsSignature lMOtsSignature = this.otsSignature;
        int hashCode = (i2 + (lMOtsSignature != null ? lMOtsSignature.hashCode() : 0)) * 31;
        LMSigParameters lMSigParameters = this.parameter;
        return Arrays.deepHashCode(this.f1565y) + ((hashCode + (lMSigParameters != null ? lMSigParameters.hashCode() : 0)) * 31);
    }
}
