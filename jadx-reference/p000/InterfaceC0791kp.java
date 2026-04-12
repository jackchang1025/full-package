package p000;

import java.util.Map;

/* renamed from: kp */
/* loaded from: classes2.dex */
public interface InterfaceC0791kp {
    public static final String ACCEPTABLE_EC_CURVES = "acceptableEcCurves";
    public static final String ADDITIONAL_EC_PARAMETERS = "additionalEcParameters";
    public static final String DH_DEFAULT_PARAMS = "DhDefaultParams";
    public static final String EC_IMPLICITLY_CA = "ecImplicitlyCa";
    public static final String THREAD_LOCAL_DH_DEFAULT_PARAMS = "threadLocalDhDefaultParams";
    public static final String THREAD_LOCAL_EC_IMPLICITLY_CA = "threadLocalEcImplicitlyCa";

    void addAlgorithm(String str, C0160c5 c0160c5, String str2);

    void addAlgorithm(String str, String str2);

    void addAttributes(String str, Map<String, String> map);

    void addKeyInfoConverter(C0160c5 c0160c5, InterfaceC0135bi interfaceC0135bi);

    InterfaceC0135bi getKeyInfoConverter(C0160c5 c0160c5);

    boolean hasAlgorithm(String str, String str2);

    void setParameter(String str, Object obj);
}
