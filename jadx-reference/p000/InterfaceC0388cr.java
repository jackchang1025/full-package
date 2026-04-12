package p000;

/* renamed from: cr */
/* loaded from: classes2.dex */
public interface InterfaceC0388cr {
    public static final C0160c5 algorithm;
    public static final C0160c5 bsi_de;
    public static final C0160c5 ecdsa_plain_RIPEMD160;
    public static final C0160c5 ecdsa_plain_SHA1;
    public static final C0160c5 ecdsa_plain_SHA224;
    public static final C0160c5 ecdsa_plain_SHA256;
    public static final C0160c5 ecdsa_plain_SHA384;
    public static final C0160c5 ecdsa_plain_SHA3_224;
    public static final C0160c5 ecdsa_plain_SHA3_256;
    public static final C0160c5 ecdsa_plain_SHA3_384;
    public static final C0160c5 ecdsa_plain_SHA3_512;
    public static final C0160c5 ecdsa_plain_SHA512;
    public static final C0160c5 ecdsa_plain_signatures;
    public static final C0160c5 ecka_eg;
    public static final C0160c5 ecka_eg_SessionKDF;
    public static final C0160c5 ecka_eg_SessionKDF_3DES;
    public static final C0160c5 ecka_eg_SessionKDF_AES128;
    public static final C0160c5 ecka_eg_SessionKDF_AES192;
    public static final C0160c5 ecka_eg_SessionKDF_AES256;
    public static final C0160c5 ecka_eg_X963kdf;
    public static final C0160c5 ecka_eg_X963kdf_RIPEMD160;
    public static final C0160c5 ecka_eg_X963kdf_SHA1;
    public static final C0160c5 ecka_eg_X963kdf_SHA224;
    public static final C0160c5 ecka_eg_X963kdf_SHA256;
    public static final C0160c5 ecka_eg_X963kdf_SHA384;
    public static final C0160c5 ecka_eg_X963kdf_SHA512;
    public static final C0160c5 id_ecc;

    static {
        C0160c5 c0160c5 = new C0160c5("0.4.0.127.0.7");
        bsi_de = c0160c5;
        C0160c5 c0160c5Branch = c0160c5.branch("1.1");
        id_ecc = c0160c5Branch;
        C0160c5 c0160c5Branch2 = c0160c5Branch.branch("4.1");
        ecdsa_plain_signatures = c0160c5Branch2;
        ecdsa_plain_SHA1 = c0160c5Branch2.branch("1");
        ecdsa_plain_SHA224 = c0160c5Branch2.branch("2");
        ecdsa_plain_SHA256 = c0160c5Branch2.branch("3");
        ecdsa_plain_SHA384 = c0160c5Branch2.branch("4");
        ecdsa_plain_SHA512 = c0160c5Branch2.branch("5");
        ecdsa_plain_RIPEMD160 = c0160c5Branch2.branch("6");
        ecdsa_plain_SHA3_224 = c0160c5Branch2.branch("8");
        ecdsa_plain_SHA3_256 = c0160c5Branch2.branch("9");
        ecdsa_plain_SHA3_384 = c0160c5Branch2.branch("10");
        ecdsa_plain_SHA3_512 = c0160c5Branch2.branch("11");
        algorithm = c0160c5.branch("1");
        C0160c5 c0160c5Branch3 = c0160c5Branch.branch("5.1");
        ecka_eg = c0160c5Branch3;
        C0160c5 c0160c5Branch4 = c0160c5Branch3.branch("1");
        ecka_eg_X963kdf = c0160c5Branch4;
        ecka_eg_X963kdf_SHA1 = c0160c5Branch4.branch("1");
        ecka_eg_X963kdf_SHA224 = c0160c5Branch4.branch("2");
        ecka_eg_X963kdf_SHA256 = c0160c5Branch4.branch("3");
        ecka_eg_X963kdf_SHA384 = c0160c5Branch4.branch("4");
        ecka_eg_X963kdf_SHA512 = c0160c5Branch4.branch("5");
        ecka_eg_X963kdf_RIPEMD160 = c0160c5Branch4.branch("6");
        C0160c5 c0160c5Branch5 = c0160c5Branch3.branch("2");
        ecka_eg_SessionKDF = c0160c5Branch5;
        ecka_eg_SessionKDF_3DES = c0160c5Branch5.branch("1");
        ecka_eg_SessionKDF_AES128 = c0160c5Branch5.branch("2");
        ecka_eg_SessionKDF_AES192 = c0160c5Branch5.branch("3");
        ecka_eg_SessionKDF_AES256 = c0160c5Branch5.branch("4");
    }
}
