package p000;

/* renamed from: ut */
/* loaded from: classes2.dex */
public interface InterfaceC1312ut {
    public static final C0160c5 bsi_de;
    public static final C0160c5 id_CA;
    public static final C0160c5 id_CA_DH;
    public static final C0160c5 id_CA_DH_3DES_CBC_CBC;
    public static final C0160c5 id_CA_ECDH;
    public static final C0160c5 id_CA_ECDH_3DES_CBC_CBC;
    public static final C0160c5 id_EAC_ePassport;
    public static final C0160c5 id_PK;
    public static final C0160c5 id_PK_DH;
    public static final C0160c5 id_PK_ECDH;
    public static final C0160c5 id_TA;
    public static final C0160c5 id_TA_ECDSA;
    public static final C0160c5 id_TA_ECDSA_SHA_1;
    public static final C0160c5 id_TA_ECDSA_SHA_224;
    public static final C0160c5 id_TA_ECDSA_SHA_256;
    public static final C0160c5 id_TA_ECDSA_SHA_384;
    public static final C0160c5 id_TA_ECDSA_SHA_512;
    public static final C0160c5 id_TA_RSA;
    public static final C0160c5 id_TA_RSA_PSS_SHA_1;
    public static final C0160c5 id_TA_RSA_PSS_SHA_256;
    public static final C0160c5 id_TA_RSA_PSS_SHA_512;
    public static final C0160c5 id_TA_RSA_v1_5_SHA_1;
    public static final C0160c5 id_TA_RSA_v1_5_SHA_256;
    public static final C0160c5 id_TA_RSA_v1_5_SHA_512;

    static {
        C0160c5 c0160c5 = new C0160c5("0.4.0.127.0.7");
        bsi_de = c0160c5;
        C0160c5 c0160c5Branch = c0160c5.branch("2.2.1");
        id_PK = c0160c5Branch;
        id_PK_DH = c0160c5Branch.branch("1");
        id_PK_ECDH = c0160c5Branch.branch("2");
        C0160c5 c0160c5Branch2 = c0160c5.branch("2.2.3");
        id_CA = c0160c5Branch2;
        C0160c5 c0160c5Branch3 = c0160c5Branch2.branch("1");
        id_CA_DH = c0160c5Branch3;
        id_CA_DH_3DES_CBC_CBC = c0160c5Branch3.branch("1");
        C0160c5 c0160c5Branch4 = c0160c5Branch2.branch("2");
        id_CA_ECDH = c0160c5Branch4;
        id_CA_ECDH_3DES_CBC_CBC = c0160c5Branch4.branch("1");
        C0160c5 c0160c5Branch5 = c0160c5.branch("2.2.2");
        id_TA = c0160c5Branch5;
        C0160c5 c0160c5Branch6 = c0160c5Branch5.branch("1");
        id_TA_RSA = c0160c5Branch6;
        id_TA_RSA_v1_5_SHA_1 = c0160c5Branch6.branch("1");
        id_TA_RSA_v1_5_SHA_256 = c0160c5Branch6.branch("2");
        id_TA_RSA_PSS_SHA_1 = c0160c5Branch6.branch("3");
        id_TA_RSA_PSS_SHA_256 = c0160c5Branch6.branch("4");
        id_TA_RSA_v1_5_SHA_512 = c0160c5Branch6.branch("5");
        id_TA_RSA_PSS_SHA_512 = c0160c5Branch6.branch("6");
        C0160c5 c0160c5Branch7 = c0160c5Branch5.branch("2");
        id_TA_ECDSA = c0160c5Branch7;
        id_TA_ECDSA_SHA_1 = c0160c5Branch7.branch("1");
        id_TA_ECDSA_SHA_224 = c0160c5Branch7.branch("2");
        id_TA_ECDSA_SHA_256 = c0160c5Branch7.branch("3");
        id_TA_ECDSA_SHA_384 = c0160c5Branch7.branch("4");
        id_TA_ECDSA_SHA_512 = c0160c5Branch7.branch("5");
        id_EAC_ePassport = c0160c5.branch("3.1.2.1");
    }
}
