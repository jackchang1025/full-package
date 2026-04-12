package p000;

/* loaded from: classes2.dex */
public interface t40 {
    public static final C0160c5 hash_algorithms;
    public static final C0160c5 id_ac_generic_hybrid;
    public static final C0160c5 id_kem_rsa;
    public static final C0160c5 is18033_2;
    public static final C0160c5 iso_encryption_algorithms;
    public static final C0160c5 ripemd128;
    public static final C0160c5 ripemd160;
    public static final C0160c5 whirlpool;

    static {
        C0160c5 c0160c5 = new C0160c5("1.0.10118");
        iso_encryption_algorithms = c0160c5;
        C0160c5 c0160c5Branch = c0160c5.branch("3.0");
        hash_algorithms = c0160c5Branch;
        ripemd160 = c0160c5Branch.branch("49");
        ripemd128 = c0160c5Branch.branch("50");
        whirlpool = c0160c5Branch.branch("55");
        C0160c5 c0160c52 = new C0160c5("1.0.18033.2");
        is18033_2 = c0160c52;
        id_ac_generic_hybrid = c0160c52.branch("1.2");
        id_kem_rsa = c0160c52.branch("2.4");
    }
}
