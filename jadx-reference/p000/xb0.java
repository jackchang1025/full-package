package p000;

import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class xb0 {
    public static void byteArray(byte[] bArr, int i, int i2, InterfaceC1236sv interfaceC1236sv) {
        interfaceC1236sv.update(bArr, i, i2);
    }

    public static int calculateStrength(o90 o90Var) {
        if (o90Var == null) {
            throw new NullPointerException("lmsParameters cannot be null");
        }
        LMSigParameters lMSigParam = o90Var.getLMSigParam();
        return lMSigParam.getM() * (1 << lMSigParam.getH());
    }

    public static void u16str(short s, InterfaceC1236sv interfaceC1236sv) {
        interfaceC1236sv.update((byte) (s >>> 8));
        interfaceC1236sv.update((byte) s);
    }

    public static void u32str(int i, InterfaceC1236sv interfaceC1236sv) {
        interfaceC1236sv.update((byte) (i >>> 24));
        interfaceC1236sv.update((byte) (i >>> 16));
        interfaceC1236sv.update((byte) (i >>> 8));
        interfaceC1236sv.update((byte) i);
    }

    public static void byteArray(byte[] bArr, InterfaceC1236sv interfaceC1236sv) {
        interfaceC1236sv.update(bArr, 0, bArr.length);
    }
}
