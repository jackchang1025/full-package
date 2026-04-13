package p000a;

import android.sun.security.x509.AttributeNameEnumeration;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.security.GeneralSecurityException;
import java.util.Enumeration;
import java.util.LinkedList;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;

/* renamed from: a.a */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0000a {
    /* renamed from: A */
    public static /* synthetic */ void m0A() {
    }

    /* renamed from: B */
    public static /* synthetic */ void m1B() {
    }

    /* renamed from: C */
    public static /* synthetic */ void m2C() {
    }

    /* renamed from: D */
    public static /* synthetic */ String m3D(int i2) {
        if (i2 == 1) {
            return "CONTINUOUS";
        }
        if (i2 == 2) {
            return "TEXT";
        }
        if (i2 == 3) {
            return "BINARY";
        }
        if (i2 == 4) {
            return "PING";
        }
        if (i2 == 5) {
            return "PONG";
        }
        if (i2 == 6) {
            return "CLOSING";
        }
        throw null;
    }

    /* renamed from: E */
    public static /* synthetic */ String m4E(int i2) {
        return i2 == 1 ? "CONTINUOUS" : i2 == 2 ? "TEXT" : i2 == 3 ? "BINARY" : i2 == 4 ? "PING" : i2 == 5 ? "PONG" : i2 == 6 ? "CLOSING" : "null";
    }

    /* renamed from: a */
    public static int m5a(int i2, int i3, int i4, int i5) {
        return i2 + i3 + i4 + i5;
    }

    /* renamed from: b */
    public static StringCondition m6b(CombineFilter combineFilter, StringCondition stringCondition, String str, String str2) {
        combineFilter.getStringConditions().add(stringCondition);
        StringCondition stringCondition2 = new StringCondition();
        stringCondition2.setProperty(str);
        stringCondition2.setEquals(str2);
        return stringCondition2;
    }

    /* renamed from: c */
    public static StringCondition m7c(CombineFilter combineFilter, String str, String str2) {
        combineFilter.setStringConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(str);
        stringCondition.setEquals(str2);
        return stringCondition;
    }

    /* renamed from: d */
    public static String m8d(IOException iOException, StringBuilder sb) {
        sb.append(iOException.getMessage());
        return sb.toString();
    }

    /* renamed from: e */
    public static String m9e(Exception exc, StringBuilder sb) {
        sb.append(exc.getMessage());
        return sb.toString();
    }

    /* renamed from: f */
    public static String m10f(Object obj, String str) {
        return str.concat(obj.getClass().getName());
    }

    /* renamed from: g */
    public static String m11g(String str, int i2) {
        return str + i2;
    }

    /* renamed from: h */
    public static String m12h(String str, int i2, String str2) {
        return str + i2 + str2;
    }

    /* renamed from: i */
    public static String m13i(String str, IOException iOException) {
        return str + iOException;
    }

    /* renamed from: j */
    public static String m14j(String str, Exception exc) {
        return str + exc;
    }

    /* renamed from: k */
    public static String m15k(String str, String str2) {
        return str + str2;
    }

    /* renamed from: l */
    public static String m16l(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    /* renamed from: m */
    public static String m17m(StringBuilder sb, int i2, String str) {
        sb.append(i2);
        sb.append(str);
        return sb.toString();
    }

    /* renamed from: n */
    public static String m18n(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    /* renamed from: o */
    public static String m19o(GeneralSecurityException generalSecurityException, StringBuilder sb) {
        sb.append(generalSecurityException.getMessage());
        return sb.toString();
    }

    /* renamed from: p */
    public static StringBuilder m20p(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    /* renamed from: q */
    public static StringBuilder m21q(String str, int i2, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i2);
        sb.append(str2);
        return sb;
    }

    /* renamed from: r */
    public static StringBuilder m22r(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    /* renamed from: s */
    public static StringBuilder m23s(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str2);
        sb.append(str3);
        return sb;
    }

    /* renamed from: t */
    public static Enumeration m24t(String str) {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(str);
        return attributeNameEnumeration.elements();
    }

    /* renamed from: u */
    public static ASN1EncodableVector m25u(ASN1EncodableVector aSN1EncodableVector, ASN1EncodableVector aSN1EncodableVector2) {
        aSN1EncodableVector2.add(new DERSequence(aSN1EncodableVector));
        return new ASN1EncodableVector();
    }

    /* renamed from: v */
    public static /* synthetic */ void m26v() {
    }

    /* renamed from: w */
    public static /* synthetic */ void m27w(Object obj) {
        if (obj != null) {
            throw new ClassCastException();
        }
    }

    /* renamed from: x */
    public static /* synthetic */ void m28x(ByteChannel byteChannel) {
        if (byteChannel != null) {
            throw new ClassCastException();
        }
    }

    /* renamed from: y */
    public static String m29y(Exception exc, StringBuilder sb) {
        sb.append(exc.toString());
        return sb.toString();
    }

    /* renamed from: z */
    public static String m30z(String str, String str2) {
        return str + str2;
    }
}
