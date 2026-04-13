package org.bouncycastle.tls;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public final class SessionID implements Comparable {
    private final byte[] id;

    public SessionID(byte[] bArr) {
        this.id = Arrays.clone(bArr);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return Arrays.compareUnsigned(this.id, ((SessionID) obj).id);
    }

    public boolean equals(Object obj) {
        if (obj instanceof SessionID) {
            return Arrays.areEqual(this.id, ((SessionID) obj).id);
        }
        return false;
    }

    public byte[] getBytes() {
        return Arrays.clone(this.id);
    }

    public int hashCode() {
        return Arrays.hashCode(this.id);
    }

    public String toString() {
        return Hex.toHexString(this.id);
    }
}
