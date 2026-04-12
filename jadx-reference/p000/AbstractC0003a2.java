package p000;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.modules.C0322a7;
import com.storm.safe.rock.service.modules.yw5xud.C0367a4;
import com.storm.safe.rock.service.modules.yw5xud.C0368a5;
import java.io.IOException;
import java.util.Iterator;
import okio.Path;
import org.conscrypt.OpenSSLProvider;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: a2 */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC0003a2 {
    /* renamed from: a0 */
    public static float m19a0(float f, float f2, float f3, float f4) {
        return ((f - f2) * f3) + f4;
    }

    /* renamed from: a1 */
    public static int m20a1(int i, int i2, int i3, int i4) {
        return i + i2 + i3 + i4;
    }

    /* renamed from: a2 */
    public static long m21a2(long j, long j2, long j3, long j4) {
        return (j * j2) + j3 + j4;
    }

    /* renamed from: a3 */
    public static C0160c5 m22a3(String str) {
        return new C0160c5(str).intern();
    }

    /* renamed from: a4 */
    public static AbstractC1330va m23a4(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return abstractC1330va.square().add(abstractC1330va2).add(abstractC1330va3);
    }

    /* renamed from: a5 */
    public static Rect m24a5(AccessibilityNodeInfo accessibilityNodeInfo) {
        Rect rect = new Rect();
        accessibilityNodeInfo.getBoundsInScreen(rect);
        return rect;
    }

    /* renamed from: a6 */
    public static ClassCastException m25a6(Iterator it) {
        it.next().getClass();
        return new ClassCastException();
    }

    /* renamed from: a7 */
    public static String m26a7(IOException iOException, StringBuilder sb) {
        sb.append(iOException.getMessage());
        return sb.toString();
    }

    /* renamed from: a8 */
    public static String m27a8(Exception exc, StringBuilder sb) {
        sb.append(exc.toString());
        return sb.toString();
    }

    /* renamed from: a9 */
    public static String m28a9(Object obj, String str) {
        return str.concat(obj.getClass().getName());
    }

    /* renamed from: b0 */
    public static String m29b0(String str, float f, String str2, float f2, String str3) {
        return str + f + str2 + f2 + str3;
    }

    /* renamed from: b1 */
    public static String m30b1(String str, int i, String str2) {
        return str + i + str2;
    }

    /* renamed from: b2 */
    public static String m31b2(String str, int i, String str2, int i2, String str3) {
        return str + i + str2 + i2 + str3;
    }

    /* renamed from: b3 */
    public static String m32b3(String str, String str2) {
        return str + str2;
    }

    /* renamed from: b4 */
    public static String m33b4(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    /* renamed from: b5 */
    public static String m34b5(String str, String str2, String str3, String str4, String str5) {
        return str + str2 + str3 + str4 + str5;
    }

    /* renamed from: b6 */
    public static String m35b6(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    /* renamed from: b7 */
    public static String m36b7(Path path, String str) {
        return str + path;
    }

    /* renamed from: b8 */
    public static StringBuilder m37b8(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    /* renamed from: b9 */
    public static StringBuilder m38b9(String str, int i, String str2, int i2, String str3) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i);
        sb.append(str2);
        sb.append(i2);
        sb.append(str3);
        return sb;
    }

    /* renamed from: c0 */
    public static StringBuilder m39c0(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    /* renamed from: c1 */
    public static StringBuilder m40c1(String str, String str2, String str3, int i, String str4) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(i);
        sb.append(str4);
        return sb;
    }

    /* renamed from: c2 */
    public static StringBuilder m41c2(String str, String str2, String str3, String str4, String str5) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        sb.append(str5);
        return sb;
    }

    /* renamed from: c3 */
    public static StringBuilder m42c3(OpenSSLProvider openSSLProvider, String str, String str2, String str3, String str4) {
        openSSLProvider.put(str, str2);
        openSSLProvider.put(str3, str4);
        return new StringBuilder();
    }

    /* renamed from: c4 */
    public static JSONObject m43c4(String str, String str2) {
        return C0322a7.m211585a1(str + str2);
    }

    /* renamed from: c5 */
    public static void m44c5(String str, int i, String str2, String str3) {
        t60.m214714d6(str3, str + i + str2);
    }

    /* renamed from: c6 */
    public static void m45c6(String str, String str2, C0367a4 c0367a4) {
        c0367a4.m212274d8(str + str2);
    }

    /* renamed from: c7 */
    public static void m46c7(String str, String str2, String str3) {
        C0368a5.m212303e0(str + str2 + str3);
    }

    /* renamed from: c8 */
    public static void m47c8(StringBuilder sb, String str, String str2, OpenSSLProvider openSSLProvider, String str3) {
        sb.append(str);
        sb.append(str2);
        openSSLProvider.put(str3, sb.toString());
    }

    /* renamed from: c9 */
    public static String m48c9(String str, String str2) {
        return str + str2;
    }
}
