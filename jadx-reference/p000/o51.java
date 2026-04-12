package p000;

/* loaded from: classes2.dex */
public interface o51 {
    public static final C0160c5 brainpoolP160r1;
    public static final C0160c5 brainpoolP160t1;
    public static final C0160c5 brainpoolP192r1;
    public static final C0160c5 brainpoolP192t1;
    public static final C0160c5 brainpoolP224r1;
    public static final C0160c5 brainpoolP224t1;
    public static final C0160c5 brainpoolP256r1;
    public static final C0160c5 brainpoolP256t1;
    public static final C0160c5 brainpoolP320r1;
    public static final C0160c5 brainpoolP320t1;
    public static final C0160c5 brainpoolP384r1;
    public static final C0160c5 brainpoolP384t1;
    public static final C0160c5 brainpoolP512r1;
    public static final C0160c5 brainpoolP512t1;
    public static final C0160c5 ecSign;
    public static final C0160c5 ecSignWithRipemd160;
    public static final C0160c5 ecSignWithSha1;
    public static final C0160c5 ecc_brainpool;
    public static final C0160c5 ellipticCurve;
    public static final C0160c5 ripemd128;
    public static final C0160c5 ripemd160;
    public static final C0160c5 ripemd256;
    public static final C0160c5 rsaSignatureWithripemd128;
    public static final C0160c5 rsaSignatureWithripemd160;
    public static final C0160c5 rsaSignatureWithripemd256;
    public static final C0160c5 teleTrusTAlgorithm;
    public static final C0160c5 teleTrusTRSAsignatureAlgorithm;
    public static final C0160c5 versionOne;

    static {
        C0160c5 c0160c5 = new C0160c5("1.3.36.3");
        teleTrusTAlgorithm = c0160c5;
        ripemd160 = c0160c5.branch("2.1");
        ripemd128 = c0160c5.branch("2.2");
        ripemd256 = c0160c5.branch("2.3");
        C0160c5 c0160c5Branch = c0160c5.branch("3.1");
        teleTrusTRSAsignatureAlgorithm = c0160c5Branch;
        rsaSignatureWithripemd160 = c0160c5Branch.branch("2");
        rsaSignatureWithripemd128 = c0160c5Branch.branch("3");
        rsaSignatureWithripemd256 = c0160c5Branch.branch("4");
        C0160c5 c0160c5Branch2 = c0160c5.branch("3.2");
        ecSign = c0160c5Branch2;
        ecSignWithSha1 = c0160c5Branch2.branch("1");
        ecSignWithRipemd160 = c0160c5Branch2.branch("2");
        C0160c5 c0160c5Branch3 = c0160c5.branch("3.2.8");
        ecc_brainpool = c0160c5Branch3;
        C0160c5 c0160c5Branch4 = c0160c5Branch3.branch("1");
        ellipticCurve = c0160c5Branch4;
        C0160c5 c0160c5Branch5 = c0160c5Branch4.branch("1");
        versionOne = c0160c5Branch5;
        brainpoolP160r1 = c0160c5Branch5.branch("1");
        brainpoolP160t1 = c0160c5Branch5.branch("2");
        brainpoolP192r1 = c0160c5Branch5.branch("3");
        brainpoolP192t1 = c0160c5Branch5.branch("4");
        brainpoolP224r1 = c0160c5Branch5.branch("5");
        brainpoolP224t1 = c0160c5Branch5.branch("6");
        brainpoolP256r1 = c0160c5Branch5.branch("7");
        brainpoolP256t1 = c0160c5Branch5.branch("8");
        brainpoolP320r1 = c0160c5Branch5.branch("9");
        brainpoolP320t1 = c0160c5Branch5.branch("10");
        brainpoolP384r1 = c0160c5Branch5.branch("11");
        brainpoolP384t1 = c0160c5Branch5.branch("12");
        brainpoolP512r1 = c0160c5Branch5.branch("13");
        brainpoolP512t1 = c0160c5Branch5.branch("14");
    }
}
