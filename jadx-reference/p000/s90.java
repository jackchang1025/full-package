package p000;

import java.io.IOException;

/* loaded from: classes2.dex */
public class s90 implements InterfaceC1394wy {
    private final q90 publicKey;
    private final r90 signature;

    public s90(r90 r90Var, q90 q90Var) {
        this.signature = r90Var;
        this.publicKey = q90Var;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            s90 s90Var = (s90) obj;
            r90 r90Var = this.signature;
            if (r90Var == null ? s90Var.signature != null : !r90Var.equals(s90Var.signature)) {
                return false;
            }
            q90 q90Var = this.publicKey;
            q90 q90Var2 = s90Var.publicKey;
            if (q90Var != null) {
                return q90Var.equals(q90Var2);
            }
            if (q90Var2 == null) {
                return true;
            }
        }
        return false;
    }

    @Override // p000.InterfaceC1394wy
    public byte[] getEncoded() throws IOException {
        return C0752kb.compose().bytes(this.signature.getEncoded()).bytes(this.publicKey.getEncoded()).build();
    }

    public q90 getPublicKey() {
        return this.publicKey;
    }

    public r90 getSignature() {
        return this.signature;
    }

    public int hashCode() {
        r90 r90Var = this.signature;
        int iHashCode = (r90Var != null ? r90Var.hashCode() : 0) * 31;
        q90 q90Var = this.publicKey;
        return iHashCode + (q90Var != null ? q90Var.hashCode() : 0);
    }
}
