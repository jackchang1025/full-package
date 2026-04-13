package org.bouncycastle.pqc.crypto.lms;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Arrays;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Encodable;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
class LMOtsPublicKey implements Encodable {

    /* renamed from: I */
    private final byte[] f1555I;

    /* renamed from: K */
    private final byte[] f1556K;
    private final LMOtsParameters parameter;

    /* renamed from: q */
    private final int f1557q;

    public LMOtsPublicKey(LMOtsParameters lMOtsParameters, byte[] bArr, int i2, byte[] bArr2) {
        this.parameter = lMOtsParameters;
        this.f1555I = bArr;
        this.f1557q = i2;
        this.f1556K = bArr2;
    }

    public static LMOtsPublicKey getInstance(Object obj) {
        if (obj instanceof LMOtsPublicKey) {
            return (LMOtsPublicKey) obj;
        }
        if (obj instanceof DataInputStream) {
            DataInputStream dataInputStream = (DataInputStream) obj;
            LMOtsParameters parametersForType = LMOtsParameters.getParametersForType(dataInputStream.readInt());
            byte[] bArr = new byte[16];
            dataInputStream.readFully(bArr);
            int readInt = dataInputStream.readInt();
            byte[] bArr2 = new byte[parametersForType.getN()];
            dataInputStream.readFully(bArr2);
            return new LMOtsPublicKey(parametersForType, bArr, readInt, bArr2);
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
                LMOtsPublicKey lMOtsPublicKey = getInstance(dataInputStream3);
                dataInputStream3.close();
                return lMOtsPublicKey;
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

    public LMSContext createOtsContext(LMOtsSignature lMOtsSignature) {
        Digest digest = DigestUtil.getDigest(this.parameter.getDigestOID());
        LmsUtils.byteArray(this.f1555I, digest);
        LmsUtils.u32str(this.f1557q, digest);
        LmsUtils.u16str((short) -32383, digest);
        LmsUtils.byteArray(lMOtsSignature.getC(), digest);
        return new LMSContext(this, lMOtsSignature, digest);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LMOtsPublicKey lMOtsPublicKey = (LMOtsPublicKey) obj;
        if (this.f1557q != lMOtsPublicKey.f1557q) {
            return false;
        }
        LMOtsParameters lMOtsParameters = this.parameter;
        if (lMOtsParameters == null ? lMOtsPublicKey.parameter != null : !lMOtsParameters.equals(lMOtsPublicKey.parameter)) {
            return false;
        }
        if (Arrays.equals(this.f1555I, lMOtsPublicKey.f1555I)) {
            return Arrays.equals(this.f1556K, lMOtsPublicKey.f1556K);
        }
        return false;
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return Composer.compose().u32str(this.parameter.getType()).bytes(this.f1555I).u32str(this.f1557q).bytes(this.f1556K).build();
    }

    public byte[] getI() {
        return this.f1555I;
    }

    public byte[] getK() {
        return this.f1556K;
    }

    public LMOtsParameters getParameter() {
        return this.parameter;
    }

    public int getQ() {
        return this.f1557q;
    }

    public int hashCode() {
        LMOtsParameters lMOtsParameters = this.parameter;
        return Arrays.hashCode(this.f1556K) + ((((Arrays.hashCode(this.f1555I) + ((lMOtsParameters != null ? lMOtsParameters.hashCode() : 0) * 31)) * 31) + this.f1557q) * 31);
    }

    public LMSContext createOtsContext(LMSSignature lMSSignature) {
        Digest digest = DigestUtil.getDigest(this.parameter.getDigestOID());
        LmsUtils.byteArray(this.f1555I, digest);
        LmsUtils.u32str(this.f1557q, digest);
        LmsUtils.u16str((short) -32383, digest);
        LmsUtils.byteArray(lMSSignature.getOtsSignature().getC(), digest);
        return new LMSContext(this, lMSSignature, digest);
    }
}
