package p000;

import org.conscrypt.PSKKeyManager;

/* renamed from: td */
/* loaded from: classes2.dex */
public class C1255td {
    public static InterfaceC1236sv getDigest(C0160c5 c0160c5) {
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha256)) {
            return new us0();
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha512)) {
            return new xs0();
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake128)) {
            return new zs0(128);
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake256)) {
            return new zs0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
        }
        throw new IllegalArgumentException("unrecognized digest OID: " + c0160c5);
    }

    public static C0160c5 getDigestOID(String str) {
        if (str.equals(ki1.SHA_256)) {
            return lh0.id_sha256;
        }
        if (str.equals(ki1.SHA_512)) {
            return lh0.id_sha512;
        }
        if (str.equals(ki1.SHAKE128)) {
            return lh0.id_shake128;
        }
        if (str.equals(ki1.SHAKE256)) {
            return lh0.id_shake256;
        }
        throw new IllegalArgumentException("unrecognized digest: ".concat(str));
    }

    public static byte[] getDigestResult(InterfaceC1236sv interfaceC1236sv) {
        int digestSize = getDigestSize(interfaceC1236sv);
        byte[] bArr = new byte[digestSize];
        if (interfaceC1236sv instanceof gj1) {
            ((gj1) interfaceC1236sv).doFinal(bArr, 0, digestSize);
            return bArr;
        }
        interfaceC1236sv.doFinal(bArr, 0);
        return bArr;
    }

    public static int getDigestSize(InterfaceC1236sv interfaceC1236sv) {
        boolean z = interfaceC1236sv instanceof gj1;
        int digestSize = interfaceC1236sv.getDigestSize();
        return z ? digestSize * 2 : digestSize;
    }

    public static String getXMSSDigestName(C0160c5 c0160c5) {
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha256)) {
            return "SHA256";
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha512)) {
            return "SHA512";
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake128)) {
            return ki1.SHAKE128;
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake256)) {
            return ki1.SHAKE256;
        }
        throw new IllegalArgumentException("unrecognized digest OID: " + c0160c5);
    }
}
