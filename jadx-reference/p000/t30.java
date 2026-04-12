package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class t30 extends n90 implements l90 {

    /* renamed from: l */
    private final int f60134l;
    private final q90 lmsPublicKey;

    public t30(int i, q90 q90Var) {
        super(false);
        this.f60134l = i;
        this.lmsPublicKey = q90Var;
    }

    public static t30 getInstance(Object obj) throws Throwable {
        if (obj instanceof t30) {
            return (t30) obj;
        }
        if (obj instanceof DataInputStream) {
            return new t30(((DataInputStream) obj).readInt(), q90.getInstance(obj));
        }
        if (!(obj instanceof byte[])) {
            if (obj instanceof InputStream) {
                return getInstance(i21.readAll((InputStream) obj));
            }
            throw new IllegalArgumentException("cannot parse " + obj);
        }
        DataInputStream dataInputStream = null;
        try {
            DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream((byte[]) obj));
            try {
                t30 t30Var = getInstance(dataInputStream2);
                dataInputStream2.close();
                return t30Var;
            } catch (Throwable th) {
                th = th;
                dataInputStream = dataInputStream2;
                if (dataInputStream != null) {
                    dataInputStream.close();
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
        t30 t30Var = (t30) obj;
        if (this.f60134l != t30Var.f60134l) {
            return false;
        }
        return this.lmsPublicKey.equals(t30Var.lmsPublicKey);
    }

    @Override // p000.l90
    public j90 generateLMSContext(byte[] bArr) throws Throwable {
        try {
            u30 u30Var = u30.getInstance(bArr, getL());
            s90[] signedPubKey = u30Var.getSignedPubKey();
            return signedPubKey[signedPubKey.length - 1].getPublicKey().generateOtsContext(u30Var.getSignature()).withSignedPublicKeys(signedPubKey);
        } catch (IOException e) {
            throw new IllegalStateException(AbstractC0003a2.m26a7(e, new StringBuilder("cannot parse signature: ")));
        }
    }

    @Override // p000.n90, p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return C0752kb.compose().u32str(this.f60134l).bytes(this.lmsPublicKey.getEncoded()).build();
    }

    public int getL() {
        return this.f60134l;
    }

    public q90 getLMSPublicKey() {
        return this.lmsPublicKey;
    }

    public int hashCode() {
        return this.lmsPublicKey.hashCode() + (this.f60134l * 31);
    }

    @Override // p000.l90
    public boolean verify(j90 j90Var) {
        s90[] signedPubKeys = j90Var.getSignedPubKeys();
        if (signedPubKeys.length != getL() - 1) {
            return false;
        }
        q90 lMSPublicKey = getLMSPublicKey();
        boolean z = false;
        for (int i = 0; i < signedPubKeys.length; i++) {
            if (!i90.verifySignature(lMSPublicKey, signedPubKeys[i].getSignature(), signedPubKeys[i].getPublicKey().toByteArray())) {
                z = true;
            }
            lMSPublicKey = signedPubKeys[i].getPublicKey();
        }
        return lMSPublicKey.verify(j90Var) & (!z);
    }
}
