package p000;

import java.util.HashMap;
import java.util.Map;
import org.conscrypt.PSKKeyManager;

/* renamed from: sz */
/* loaded from: classes2.dex */
public final class C1240sz {
    private static final Map cloneMap;

    /* renamed from: sz$a0 */
    public static class a0 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new ws0((ws0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a1 */
    public static class a1 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new zs0((zs0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a2 */
    public static class a2 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new zs0((zs0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a3 */
    public static class a3 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new qc0((qc0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a4 */
    public static class a4 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new qc0((qc0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a5 */
    public static class a5 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new ts0((ts0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a6 */
    public static class a6 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new us0((us0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a7 */
    public static class a7 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new vs0((vs0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a8 */
    public static class a8 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new xs0((xs0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$a9 */
    public static class a9 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new ws0((ws0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$b0 */
    public static class b0 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new ws0((ws0) interfaceC1236sv);
        }
    }

    /* renamed from: sz$b1 */
    public static class b1 implements InterfaceC1252ta {
        @Override // p000.InterfaceC1252ta
        public InterfaceC1236sv createClone(InterfaceC1236sv interfaceC1236sv) {
            return new ws0((ws0) interfaceC1236sv);
        }
    }

    static {
        HashMap map = new HashMap();
        cloneMap = map;
        map.put(createMD5().getAlgorithmName(), new a3());
        map.put(createSHA1().getAlgorithmName(), new a4());
        map.put(createSHA224().getAlgorithmName(), new a5());
        map.put(createSHA256().getAlgorithmName(), new a6());
        map.put(createSHA384().getAlgorithmName(), new a7());
        map.put(createSHA512().getAlgorithmName(), new a8());
        map.put(createSHA3_224().getAlgorithmName(), new a9());
        map.put(createSHA3_256().getAlgorithmName(), new b0());
        map.put(createSHA3_384().getAlgorithmName(), new b1());
        map.put(createSHA3_512().getAlgorithmName(), new a0());
        map.put(createSHAKE128().getAlgorithmName(), new a1());
        map.put(createSHAKE256().getAlgorithmName(), new a2());
    }

    public static InterfaceC1236sv cloneDigest(InterfaceC1236sv interfaceC1236sv) {
        return ((InterfaceC1252ta) cloneMap.get(interfaceC1236sv.getAlgorithmName())).createClone(interfaceC1236sv);
    }

    public static InterfaceC1236sv createMD5() {
        return new qc0();
    }

    public static InterfaceC1236sv createSHA1() {
        return new ss0();
    }

    public static InterfaceC1236sv createSHA224() {
        return new ts0();
    }

    public static InterfaceC1236sv createSHA256() {
        return new us0();
    }

    public static InterfaceC1236sv createSHA384() {
        return new vs0();
    }

    public static InterfaceC1236sv createSHA3_224() {
        return new ws0(224);
    }

    public static InterfaceC1236sv createSHA3_256() {
        return new ws0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
    }

    public static InterfaceC1236sv createSHA3_384() {
        return new ws0(384);
    }

    public static InterfaceC1236sv createSHA3_512() {
        return new ws0(512);
    }

    public static InterfaceC1236sv createSHA512() {
        return new xs0();
    }

    public static InterfaceC1236sv createSHA512_224() {
        return new ys0(224);
    }

    public static InterfaceC1236sv createSHA512_256() {
        return new ys0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
    }

    public static InterfaceC1236sv createSHAKE128() {
        return new zs0(128);
    }

    public static InterfaceC1236sv createSHAKE256() {
        return new zs0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
    }
}
