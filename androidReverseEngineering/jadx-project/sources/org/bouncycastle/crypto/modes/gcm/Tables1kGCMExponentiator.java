package org.bouncycastle.crypto.modes.gcm;

import java.util.Vector;

/* loaded from: classes.dex */
public class Tables1kGCMExponentiator implements GCMExponentiator {
    private Vector lookupPowX2;

    private void ensureAvailable(int i2) {
        int size = this.lookupPowX2.size() - 1;
        if (size >= i2) {
            return;
        }
        long[] jArr = (long[]) this.lookupPowX2.elementAt(size);
        while (true) {
            long[] jArr2 = new long[2];
            GCMUtil.square(jArr, jArr2);
            this.lookupPowX2.addElement(jArr2);
            size++;
            if (size >= i2) {
                return;
            } else {
                jArr = jArr2;
            }
        }
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void exponentiateX(long j2, byte[] bArr) {
        long[] oneAsLongs = GCMUtil.oneAsLongs();
        int i2 = 0;
        while (j2 > 0) {
            if ((1 & j2) != 0) {
                ensureAvailable(i2);
                GCMUtil.multiply(oneAsLongs, (long[]) this.lookupPowX2.elementAt(i2));
            }
            i2++;
            j2 >>>= 1;
        }
        GCMUtil.asBytes(oneAsLongs, bArr);
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void init(byte[] bArr) {
        long[] asLongs = GCMUtil.asLongs(bArr);
        Vector vector = this.lookupPowX2;
        if (vector == null || 0 == GCMUtil.areEqual(asLongs, (long[]) vector.elementAt(0))) {
            Vector vector2 = new Vector(8);
            this.lookupPowX2 = vector2;
            vector2.addElement(asLongs);
        }
    }
}
