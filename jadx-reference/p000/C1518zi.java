package p000;

import org.conscrypt.PSKKeyManager;

/* renamed from: zi */
/* loaded from: classes2.dex */
public class C1518zi {
    private static char[] encodingTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final byte[] fingerprint;

    public C1518zi(byte[] bArr) {
        this(bArr, 160);
    }

    public static byte[] calculateFingerprint(byte[] bArr) {
        return calculateFingerprint(bArr, 160);
    }

    public static byte[] calculateFingerprintSHA512_160(byte[] bArr) {
        ys0 ys0Var = new ys0(160);
        ys0Var.update(bArr, 0, bArr.length);
        byte[] bArr2 = new byte[ys0Var.getDigestSize()];
        ys0Var.doFinal(bArr2, 0);
        return bArr2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1518zi) {
            return C0133bg.areEqual(((C1518zi) obj).fingerprint, this.fingerprint);
        }
        return false;
    }

    public byte[] getFingerprint() {
        return C0133bg.clone(this.fingerprint);
    }

    public int hashCode() {
        return C0133bg.hashCode(this.fingerprint);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i != this.fingerprint.length; i++) {
            if (i > 0) {
                stringBuffer.append(":");
            }
            stringBuffer.append(encodingTable[(this.fingerprint[i] >>> 4) & 15]);
            stringBuffer.append(encodingTable[this.fingerprint[i] & 15]);
        }
        return stringBuffer.toString();
    }

    public C1518zi(byte[] bArr, int i) {
        this.fingerprint = calculateFingerprint(bArr, i);
    }

    public static byte[] calculateFingerprint(byte[] bArr, int i) {
        if (i % 8 != 0) {
            throw new IllegalArgumentException("bitLength must be a multiple of 8");
        }
        zs0 zs0Var = new zs0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
        zs0Var.update(bArr, 0, bArr.length);
        int i2 = i / 8;
        byte[] bArr2 = new byte[i2];
        zs0Var.doFinal(bArr2, 0, i2);
        return bArr2;
    }

    public C1518zi(byte[] bArr, boolean z) {
        if (z) {
            this.fingerprint = calculateFingerprintSHA512_160(bArr);
        } else {
            this.fingerprint = calculateFingerprint(bArr);
        }
    }
}
