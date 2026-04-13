package p012o;

import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.utils.AbstractC0250f;
import java.util.HashMap;
import java.util.HashSet;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.util.Arrays;
import org.conscrypt.OpenSSLProvider;

/* renamed from: o.b */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0413b {
    /* renamed from: A */
    public static void m1003A(StringBuilder sb, ASN1ObjectIdentifier aSN1ObjectIdentifier, ConfigurableProvider configurableProvider, String str) {
        sb.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(sb.toString(), str);
    }

    /* renamed from: B */
    public static byte[] m1004B(ASN1Sequence aSN1Sequence, int i2) {
        return Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i2)).getOctets());
    }

    /* renamed from: C */
    public static StringBuilder m1005C(StringBuilder sb, ASN1ObjectIdentifier aSN1ObjectIdentifier, ConfigurableProvider configurableProvider, String str, String str2) {
        sb.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(sb.toString(), str);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        return sb2;
    }

    /* renamed from: D */
    public static byte[] m1006D(ASN1Sequence aSN1Sequence, int i2) {
        return ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i2)).getOctets();
    }

    /* renamed from: a */
    public static long m1007a(long j2, long j3, long j4, long j5) {
        return (j2 * j3) + j4 + j5;
    }

    /* renamed from: b */
    public static StringCondition m1008b(CombineFilter combineFilter, StringCondition stringCondition, String str) {
        combineFilter.getStringConditions().add(stringCondition);
        StringCondition stringCondition2 = new StringCondition();
        stringCondition2.setProperty(str);
        return stringCondition2;
    }

    /* renamed from: c */
    public static String m1009c(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return str + aSN1ObjectIdentifier;
    }

    /* renamed from: d */
    public static String m1010d(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str2) {
        return str + aSN1ObjectIdentifier + str2;
    }

    /* renamed from: e */
    public static String m1011e(String str, SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        return str + signatureAndHashAlgorithm;
    }

    /* renamed from: f */
    public static String m1012f(ASN1Sequence aSN1Sequence, StringBuilder sb) {
        sb.append(aSN1Sequence.size());
        return sb.toString();
    }

    /* renamed from: g */
    public static String m1013g(ASN1TaggedObject aSN1TaggedObject, StringBuilder sb) {
        sb.append(aSN1TaggedObject.getTagNo());
        return sb.toString();
    }

    /* renamed from: h */
    public static String m1014h(CipherParameters cipherParameters, String str) {
        return str.concat(cipherParameters.getClass().getName());
    }

    /* renamed from: i */
    public static String m1015i(ConfigurableProvider configurableProvider, String str, String str2, String str3, String str4) {
        configurableProvider.addAlgorithm(str, str2);
        return str3 + str4;
    }

    /* renamed from: j */
    public static StringBuilder m1016j(StringBuilder sb, String str, String str2, ConfigurableProvider configurableProvider, String str3) {
        sb.append(str);
        sb.append(str2);
        configurableProvider.addAlgorithm(str3, sb.toString());
        return new StringBuilder();
    }

    /* renamed from: k */
    public static StringBuilder m1017k(StringBuilder sb, String str, ConfigurableProvider configurableProvider, String str2, String str3) {
        sb.append(str);
        configurableProvider.addAlgorithm(str2, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str3);
        return sb2;
    }

    /* renamed from: l */
    public static StringBuilder m1018l(StringBuilder sb, String str, ConfigurableProvider configurableProvider, String str2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        sb.append(str);
        configurableProvider.addAlgorithm(str2, aSN1ObjectIdentifier, sb.toString());
        return new StringBuilder();
    }

    /* renamed from: m */
    public static StringBuilder m1019m(StringBuilder sb, ASN1ObjectIdentifier aSN1ObjectIdentifier, ConfigurableProvider configurableProvider, String str, String str2) {
        sb.append(aSN1ObjectIdentifier);
        configurableProvider.addAlgorithm(sb.toString(), str);
        return new StringBuilder(str2);
    }

    /* renamed from: n */
    public static StringBuilder m1020n(ConfigurableProvider configurableProvider, String str, String str2, String str3) {
        configurableProvider.addAlgorithm(str, str2);
        return new StringBuilder(str3);
    }

    /* renamed from: o */
    public static StringBuilder m1021o(ConfigurableProvider configurableProvider, String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str2, String str3) {
        configurableProvider.addAlgorithm(str, aSN1ObjectIdentifier, str2);
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        return sb;
    }

    /* renamed from: p */
    public static StringBuilder m1022p(OpenSSLProvider openSSLProvider, String str, String str2, String str3, String str4) {
        openSSLProvider.put(str, str2);
        openSSLProvider.put(str3, str4);
        return new StringBuilder();
    }

    /* renamed from: q */
    public static HashSet m1023q(int i2, HashSet hashSet, ListenWindow listenWindow) {
        hashSet.add(Integer.valueOf(i2));
        return listenWindow.getEventTypes();
    }

    /* renamed from: r */
    public static HashSet m1024r(ListenWindow listenWindow) {
        listenWindow.setEventTypes(new HashSet<>());
        return listenWindow.getEventTypes();
    }

    /* renamed from: s */
    public static ASN1ObjectIdentifier m1025s(String str) {
        return new ASN1ObjectIdentifier(str).intern();
    }

    /* renamed from: t */
    public static ECFieldElement m1026t(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return eCFieldElement.square().add(eCFieldElement2).add(eCFieldElement3);
    }

    /* renamed from: u */
    public static void m1027u(int i2, int i3, ASN1ObjectIdentifier aSN1ObjectIdentifier, HashMap hashMap, Integer num) {
        hashMap.put(num, new XMSSMTParameters(i2, i3, aSN1ObjectIdentifier));
    }

    /* renamed from: v */
    public static void m1028v(String str, StringCondition stringCondition, CombineFilter combineFilter, StringCondition stringCondition2) {
        stringCondition.setEquals(AbstractC0250f.m627b(str));
        combineFilter.getStringConditions().add(stringCondition2);
    }

    /* renamed from: w */
    public static void m1029w(String str, String str2, ConfigurableProvider configurableProvider, String str3, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        configurableProvider.addAlgorithm(str3, aSN1ObjectIdentifier, str + str2);
    }

    /* renamed from: x */
    public static void m1030x(StringBuilder sb, String str, String str2, ConfigurableProvider configurableProvider, String str3) {
        sb.append(str);
        sb.append(str2);
        configurableProvider.addAlgorithm(str3, sb.toString());
    }

    /* renamed from: y */
    public static void m1031y(StringBuilder sb, String str, String str2, OpenSSLProvider openSSLProvider, String str3) {
        sb.append(str);
        sb.append(str2);
        openSSLProvider.put(str3, sb.toString());
    }

    /* renamed from: z */
    public static void m1032z(StringBuilder sb, String str, ConfigurableProvider configurableProvider, String str2) {
        sb.append(str);
        configurableProvider.addAlgorithm(str2, sb.toString());
    }
}
