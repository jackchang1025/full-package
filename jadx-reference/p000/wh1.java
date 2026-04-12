package p000;

/* loaded from: classes2.dex */
public interface wh1 {
    public static final C0160c5 crlAccessMethod;
    public static final C0160c5 id_ad;
    public static final C0160c5 id_ad_caIssuers;
    public static final C0160c5 id_ad_ocsp;
    public static final C0160c5 id_ce;
    public static final C0160c5 id_ecdsa_with_shake128;
    public static final C0160c5 id_ecdsa_with_shake256;
    public static final C0160c5 id_pe;
    public static final C0160c5 id_pkix;
    public static final C0160c5 id_rsassa_pss_shake128;
    public static final C0160c5 id_rsassa_pss_shake256;
    public static final C0160c5 ocspAccessMethod;
    public static final C0160c5 commonName = AbstractC0003a2.m22a3("2.5.4.3");
    public static final C0160c5 countryName = AbstractC0003a2.m22a3("2.5.4.6");
    public static final C0160c5 localityName = AbstractC0003a2.m22a3("2.5.4.7");
    public static final C0160c5 stateOrProvinceName = AbstractC0003a2.m22a3("2.5.4.8");
    public static final C0160c5 organization = AbstractC0003a2.m22a3("2.5.4.10");
    public static final C0160c5 organizationalUnitName = AbstractC0003a2.m22a3("2.5.4.11");
    public static final C0160c5 id_at_telephoneNumber = AbstractC0003a2.m22a3("2.5.4.20");
    public static final C0160c5 id_at_name = AbstractC0003a2.m22a3("2.5.4.41");
    public static final C0160c5 id_at_organizationIdentifier = AbstractC0003a2.m22a3("2.5.4.97");
    public static final C0160c5 id_SHA1 = AbstractC0003a2.m22a3("1.3.14.3.2.26");
    public static final C0160c5 ripemd160 = AbstractC0003a2.m22a3("1.3.36.3.2.1");
    public static final C0160c5 ripemd160WithRSAEncryption = AbstractC0003a2.m22a3("1.3.36.3.3.1.2");
    public static final C0160c5 id_ea_rsa = AbstractC0003a2.m22a3("2.5.8.1.1");

    static {
        C0160c5 c0160c5 = new C0160c5("1.3.6.1.5.5.7");
        id_pkix = c0160c5;
        id_rsassa_pss_shake128 = c0160c5.branch("6.30");
        id_rsassa_pss_shake256 = c0160c5.branch("6.31");
        id_ecdsa_with_shake128 = c0160c5.branch("6.32");
        id_ecdsa_with_shake256 = c0160c5.branch("6.33");
        id_pe = c0160c5.branch("1");
        id_ce = new C0160c5("2.5.29");
        C0160c5 c0160c5Branch = c0160c5.branch("48");
        id_ad = c0160c5Branch;
        C0160c5 c0160c5Intern = c0160c5Branch.branch("2").intern();
        id_ad_caIssuers = c0160c5Intern;
        C0160c5 c0160c5Intern2 = c0160c5Branch.branch("1").intern();
        id_ad_ocsp = c0160c5Intern2;
        ocspAccessMethod = c0160c5Intern2;
        crlAccessMethod = c0160c5Intern;
    }
}
