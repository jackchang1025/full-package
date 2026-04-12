package p000;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class u30 implements InterfaceC1394wy {
    private final int lMinus1;
    private final r90 signature;
    private final s90[] signedPubKey;

    public u30(int i, s90[] s90VarArr, r90 r90Var) {
        this.lMinus1 = i;
        this.signedPubKey = s90VarArr;
        this.signature = r90Var;
    }

    public static u30 getInstance(Object obj, int i) throws Throwable {
        if (obj instanceof u30) {
            return (u30) obj;
        }
        if (obj instanceof DataInputStream) {
            int i2 = ((DataInputStream) obj).readInt();
            if (i2 != i - 1) {
                throw new IllegalStateException("nspk exceeded maxNspk");
            }
            s90[] s90VarArr = new s90[i2];
            if (i2 != 0) {
                for (int i3 = 0; i3 < i2; i3++) {
                    s90VarArr[i3] = new s90(r90.getInstance(obj), q90.getInstance(obj));
                }
            }
            return new u30(i2, s90VarArr, r90.getInstance(obj));
        }
        if (!(obj instanceof byte[])) {
            if (obj instanceof InputStream) {
                return getInstance(i21.readAll((InputStream) obj), i);
            }
            throw new IllegalArgumentException("cannot parse " + obj);
        }
        DataInputStream dataInputStream = null;
        try {
            DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream((byte[]) obj));
            try {
                u30 u30Var = getInstance(dataInputStream2, i);
                dataInputStream2.close();
                return u30Var;
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
        if (obj != null && getClass() == obj.getClass()) {
            u30 u30Var = (u30) obj;
            if (this.lMinus1 != u30Var.lMinus1 || this.signedPubKey.length != u30Var.signedPubKey.length) {
                return false;
            }
            int i = 0;
            while (true) {
                s90[] s90VarArr = this.signedPubKey;
                if (i >= s90VarArr.length) {
                    r90 r90Var = this.signature;
                    r90 r90Var2 = u30Var.signature;
                    if (r90Var != null) {
                        return r90Var.equals(r90Var2);
                    }
                    if (r90Var2 == null) {
                        return true;
                    }
                } else {
                    if (!s90VarArr[i].equals(u30Var.signedPubKey[i])) {
                        return false;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    @Override // p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        C0752kb c0752kbCompose = C0752kb.compose();
        c0752kbCompose.u32str(this.lMinus1);
        s90[] s90VarArr = this.signedPubKey;
        if (s90VarArr != null) {
            for (s90 s90Var : s90VarArr) {
                c0752kbCompose.bytes(s90Var);
            }
        }
        c0752kbCompose.bytes(this.signature);
        return c0752kbCompose.build();
    }

    public r90 getSignature() {
        return this.signature;
    }

    public s90[] getSignedPubKey() {
        return this.signedPubKey;
    }

    public int getlMinus1() {
        return this.lMinus1;
    }

    public int hashCode() {
        int iHashCode = ((this.lMinus1 * 31) + Arrays.hashCode(this.signedPubKey)) * 31;
        r90 r90Var = this.signature;
        return iHashCode + (r90Var != null ? r90Var.hashCode() : 0);
    }
}
