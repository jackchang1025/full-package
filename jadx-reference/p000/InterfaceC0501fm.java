package p000;

/* renamed from: fm */
/* loaded from: classes2.dex */
public interface InterfaceC0501fm {
    public static final C0160c5 id_RSASSA_PSS_SHAKE128;
    public static final C0160c5 id_RSASSA_PSS_SHAKE256;
    public static final C0160c5 id_alg;
    public static final C0160c5 id_ecdsa_with_shake128;
    public static final C0160c5 id_ecdsa_with_shake256;
    public static final C0160c5 id_ri;
    public static final C0160c5 id_ri_ocsp_response;
    public static final C0160c5 id_ri_scvp;
    public static final C0160c5 data = ul0.data;
    public static final C0160c5 signedData = ul0.signedData;
    public static final C0160c5 envelopedData = ul0.envelopedData;
    public static final C0160c5 signedAndEnvelopedData = ul0.signedAndEnvelopedData;
    public static final C0160c5 digestedData = ul0.digestedData;
    public static final C0160c5 encryptedData = ul0.encryptedData;
    public static final C0160c5 authenticatedData = ul0.id_ct_authData;
    public static final C0160c5 compressedData = ul0.id_ct_compressedData;
    public static final C0160c5 authEnvelopedData = ul0.id_ct_authEnvelopedData;
    public static final C0160c5 timestampedData = ul0.id_ct_timestampedData;

    static {
        C0160c5 c0160c5 = new C0160c5("1.3.6.1.5.5.7.16");
        id_ri = c0160c5;
        id_ri_ocsp_response = c0160c5.branch("2");
        id_ri_scvp = c0160c5.branch("4");
        C0160c5 c0160c52 = new C0160c5("1.3.6.1.5.5.7.6");
        id_alg = c0160c52;
        id_RSASSA_PSS_SHAKE128 = c0160c52.branch("30");
        id_RSASSA_PSS_SHAKE256 = c0160c52.branch("31");
        id_ecdsa_with_shake128 = c0160c52.branch("32");
        id_ecdsa_with_shake256 = c0160c52.branch("33");
    }
}
