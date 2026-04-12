package p000;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EdgeEffect;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.storm.safe.rock.activity.yojggfhv;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import kotlin.Pair;
import kotlin.UninitializedPropertyAccessException;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.text.AbstractC0779a1;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class t60 {

    /* renamed from: a0 */
    public static final int[] f60148a0 = {R.attr.name, R.attr.tint, R.attr.height, R.attr.width, R.attr.alpha, R.attr.autoMirrored, R.attr.tintMode, R.attr.viewportWidth, R.attr.viewportHeight};

    /* renamed from: a1 */
    public static final int[] f60149a1 = {R.attr.name, R.attr.pivotX, R.attr.pivotY, R.attr.scaleX, R.attr.scaleY, R.attr.rotation, R.attr.translateX, R.attr.translateY};

    /* renamed from: a2 */
    public static final int[] f60150a2 = {R.attr.name, R.attr.fillColor, R.attr.pathData, R.attr.strokeColor, R.attr.strokeWidth, R.attr.trimPathStart, R.attr.trimPathEnd, R.attr.trimPathOffset, R.attr.strokeLineCap, R.attr.strokeLineJoin, R.attr.strokeMiterLimit, R.attr.strokeAlpha, R.attr.fillAlpha, R.attr.fillType};

    /* renamed from: a3 */
    public static final int[] f60151a3 = {R.attr.name, R.attr.pathData, R.attr.fillType};

    /* renamed from: a4 */
    public static final int[] f60152a4 = {R.attr.drawable};

    /* renamed from: a5 */
    public static final int[] f60153a5 = {R.attr.name, R.attr.animation};

    /* renamed from: a6 */
    public static final int[] f60154a6 = new int[0];

    /* renamed from: a7 */
    public static final Object[] f60155a7 = new Object[0];

    /* renamed from: a8 */
    public static final C1347vr f60156a8 = new C1347vr("COMPLETING_ALREADY");

    /* renamed from: a9 */
    public static final C1347vr f60157a9 = new C1347vr("COMPLETING_WAITING_CHILDREN");

    /* renamed from: b0 */
    public static final C1347vr f60158b0 = new C1347vr("COMPLETING_RETRY");

    /* renamed from: b1 */
    public static final C1347vr f60159b1 = new C1347vr("TOO_LATE_TO_CANCEL");

    /* renamed from: b2 */
    public static final C1347vr f60160b2 = new C1347vr("SEALED");

    /* renamed from: b3 */
    public static final C1391wv f60161b3 = new C1391wv(false);

    /* renamed from: b4 */
    public static final C1391wv f60162b4 = new C1391wv(true);

    /* renamed from: b5 */
    public static final byte[] f60163b5 = {48, 49, 53, 0};

    /* renamed from: b6 */
    public static final byte[] f60164b6 = {48, 49, 48, 0};

    /* renamed from: b7 */
    public static final byte[] f60165b7 = {48, 48, 57, 0};

    /* renamed from: b8 */
    public static final byte[] f60166b8 = {48, 48, 53, 0};

    /* renamed from: b9 */
    public static final byte[] f60167b9 = {48, 48, 49, 0};

    /* renamed from: c0 */
    public static final byte[] f60168c0 = {48, 48, 49, 0};

    /* renamed from: c1 */
    public static final byte[] f60169c1 = {48, 48, 50, 0};

    /* renamed from: c2 */
    public static JSONObject f60170c2 = null;

    /* renamed from: c3 */
    public static Field f60171c3 = null;

    /* renamed from: c4 */
    public static boolean f60172c4 = false;

    /* renamed from: c5 */
    public static boolean f60173c5 = true;

    /* renamed from: a2 */
    public static boolean m214686a2(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    /* renamed from: a3 */
    public static int m214687a3(int i, int i2, int[] iArr) {
        int i3 = i - 1;
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            int i6 = iArr[i5];
            if (i6 < i2) {
                i4 = i5 + 1;
            } else {
                if (i6 <= i2) {
                    return i5;
                }
                i3 = i5 - 1;
            }
        }
        return ~i4;
    }

    /* renamed from: a6 */
    public static int m214688a6(long j, long[] jArr, int i) {
        int i2 = i - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            long j2 = jArr[i4];
            if (j2 < j) {
                i3 = i4 + 1;
            } else {
                if (j2 <= j) {
                    return i4;
                }
                i2 = i4 - 1;
            }
        }
        return ~i3;
    }

    /* renamed from: a7 */
    public static final Boolean m214689a7(boolean z) {
        return Boolean.valueOf(z);
    }

    /* renamed from: a8 */
    public static boolean m214690a8(InterfaceC0726jp interfaceC0726jp, String str) {
        m214695b6(str, "command");
        return interfaceC0726jp.mo210873a1().contains(str);
    }

    /* renamed from: a9 */
    public static boolean m214691a9(qm0[] qm0VarArr, qm0[] qm0VarArr2) {
        if (qm0VarArr == null || qm0VarArr2 == null || qm0VarArr.length != qm0VarArr2.length) {
            return false;
        }
        for (int i = 0; i < qm0VarArr.length; i++) {
            qm0 qm0Var = qm0VarArr[i];
            char c = qm0Var.f59534a0;
            qm0 qm0Var2 = qm0VarArr2[i];
            if (c != qm0Var2.f59534a0 || qm0Var.f59535a1.length != qm0Var2.f59535a1.length) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: b3 */
    public static void m214692b3(Object obj) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException();
        m214719e5(nullPointerException, t60.class.getName());
        throw nullPointerException;
    }

    /* renamed from: b4 */
    public static void m214693b4(Object obj, String str) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException(str);
        m214719e5(nullPointerException, t60.class.getName());
        throw nullPointerException;
    }

    /* renamed from: b5 */
    public static void m214694b5(Object obj, String str) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException(str.concat(" must not be null"));
        m214719e5(nullPointerException, t60.class.getName());
        throw nullPointerException;
    }

    /* renamed from: b6 */
    public static void m214695b6(Object obj, String str) {
        if (obj == null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String name = t60.class.getName();
            int i = 0;
            while (!stackTrace[i].getClassName().equals(name)) {
                i++;
            }
            while (stackTrace[i].getClassName().equals(name)) {
                i++;
            }
            StackTraceElement stackTraceElement = stackTrace[i];
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("Parameter specified as non-null is null: method ", stackTraceElement.getClassName(), ".", stackTraceElement.getMethodName(), ", parameter ");
            sbM41c2.append(str);
            NullPointerException nullPointerException = new NullPointerException(sbM41c2.toString());
            m214719e5(nullPointerException, t60.class.getName());
            throw nullPointerException;
        }
    }

    /* renamed from: b7 */
    public static boolean m214696b7() {
        Object next;
        String str;
        String string;
        Integer numM213685d8;
        try {
            File file = new File("/proc/self/status");
            if (file.exists()) {
                Iterator it = AbstractC0779a1.m213666b9(AbstractC1517zh.m215420f8(file)).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (AbstractC0779a1.m213679d2((String) next, false, k21.m213444a0("YyVwUIN1WEE5dQ=="))) {
                        break;
                    }
                }
                String str2 = (String) next;
                if (((str2 == null || (str = (String) AbstractC0779a1.m213677d0(str2, new String[]{":"}, 6).get(1)) == null || (string = AbstractC0779a1.m213687e0(str).toString()) == null || (numM213685d8 = AbstractC0779a1.m213685d8(string)) == null) ? 0 : numM213685d8.intValue()) != 0) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* renamed from: b8 */
    public static int m214697b8(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    /* renamed from: b9 */
    public static float[] m214698b9(float[] fArr, int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int iMin = Math.min(i, length);
        float[] fArr2 = new float[i];
        System.arraycopy(fArr, 0, fArr2, 0, iMin);
        return fArr2;
    }

    /* renamed from: c0 */
    public static b81 m214699c0(int i) {
        return i != 0 ? i != 1 ? new ns0() : new C0954oj() : new ns0();
    }

    /* renamed from: c1 */
    public static ProgressBar m214700c1(yojggfhv yojggfhvVar) {
        int i = (int) (19.0f * yojggfhvVar.getResources().getDisplayMetrics().density);
        ProgressBar progressBar = new ProgressBar(yojggfhvVar, null, 0, R.style.Widget.ProgressBar.Horizontal);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(0);
        progressBar.setMinimumHeight(i);
        try {
            try {
                int iM214706c7 = m214706c7(yojggfhvVar, "progress_bar_horizontal_custom", 0);
                if (iM214706c7 != 0) {
                    progressBar.setProgressDrawable(yojggfhvVar.getResources().getDrawable(iM214706c7, null));
                } else {
                    Drawable progressDrawable = progressBar.getProgressDrawable();
                    if (progressDrawable != null) {
                        progressDrawable.setTint(Color.parseColor("#FF9800"));
                    }
                    progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(0));
                }
            } catch (Exception e) {
                m214695b6("设置进度条drawable失败: " + e.getMessage(), "msg");
                Drawable progressDrawable2 = progressBar.getProgressDrawable();
                if (progressDrawable2 != null) {
                    progressDrawable2.setTint(Color.parseColor("#FF9800"));
                }
            }
        } catch (Exception unused) {
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, i);
        layoutParams.setMargins(64, 24, 64, 16);
        progressBar.setLayoutParams(layoutParams);
        progressBar.setAlpha(1.0f);
        return progressBar;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009a A[Catch: NumberFormatException -> 0x00ae, LOOP:3: B:29:0x006c->B:48:0x009a, LOOP_END, TryCatch #0 {NumberFormatException -> 0x00ae, blocks: (B:26:0x0058, B:29:0x006c, B:31:0x0072, B:35:0x007e, B:48:0x009a, B:50:0x00a0, B:56:0x00b5, B:57:0x00b8), top: B:71:0x0058 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a0 A[Catch: NumberFormatException -> 0x00ae, TryCatch #0 {NumberFormatException -> 0x00ae, blocks: (B:26:0x0058, B:29:0x006c, B:31:0x0072, B:35:0x007e, B:48:0x009a, B:50:0x00a0, B:56:0x00b5, B:57:0x00b8), top: B:71:0x0058 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00b5 A[Catch: NumberFormatException -> 0x00ae, TryCatch #0 {NumberFormatException -> 0x00ae, blocks: (B:26:0x0058, B:29:0x006c, B:31:0x0072, B:35:0x007e, B:48:0x009a, B:50:0x00a0, B:56:0x00b5, B:57:0x00b8), top: B:71:0x0058 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00df A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0099 A[SYNTHETIC] */
    /* renamed from: c2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static qm0[] m214701c2(String str) {
        String strTrim;
        float[] fArrM214698b9;
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        while (i3 < str.length()) {
            while (i3 < str.length()) {
                char cCharAt = str.charAt(i3);
                if ((cCharAt - 'Z') * (cCharAt - 'A') > 0) {
                    if ((cCharAt - 'z') * (cCharAt - 'a') > 0) {
                        continue;
                    }
                    i3++;
                }
                if (cCharAt == 'e' || cCharAt == 'E') {
                    i3++;
                } else {
                    strTrim = str.substring(i2, i3).trim();
                    if (strTrim.length() > 0) {
                        if (strTrim.charAt(i) == 'z' || strTrim.charAt(i) == 'Z') {
                            fArrM214698b9 = new float[i];
                        } else {
                            try {
                                float[] fArr = new float[strTrim.length()];
                                int length = strTrim.length();
                                int i4 = i;
                                int i5 = 1;
                                while (i5 < length) {
                                    int i6 = i;
                                    int i7 = i6;
                                    int i8 = i7;
                                    int i9 = i8;
                                    for (int i10 = i5; i10 < strTrim.length(); i10++) {
                                        char cCharAt2 = strTrim.charAt(i10);
                                        if (cCharAt2 == ' ') {
                                            i6 = 0;
                                            i8 = 1;
                                            if (i8 != 0) {
                                            }
                                        } else {
                                            if (cCharAt2 != 'E' && cCharAt2 != 'e') {
                                                switch (cCharAt2) {
                                                    case ',':
                                                        break;
                                                    case '-':
                                                        if (i10 != i5 && i6 == 0) {
                                                            i6 = 0;
                                                            i8 = 1;
                                                            i9 = 1;
                                                            break;
                                                        }
                                                        i6 = 0;
                                                        break;
                                                    case '.':
                                                        if (i7 == 0) {
                                                            i6 = 0;
                                                            i7 = 1;
                                                            break;
                                                        } else {
                                                            i6 = 0;
                                                            i8 = 1;
                                                            i9 = 1;
                                                            break;
                                                        }
                                                    default:
                                                        i6 = 0;
                                                        break;
                                                }
                                            } else {
                                                i6 = 1;
                                            }
                                            if (i8 != 0) {
                                            }
                                        }
                                        if (i5 < i10) {
                                            fArr[i4] = Float.parseFloat(strTrim.substring(i5, i10));
                                            i4++;
                                        }
                                        i5 = i9 == 0 ? i10 : i10 + 1;
                                        i = 0;
                                    }
                                    if (i5 < i10) {
                                    }
                                    if (i9 == 0) {
                                    }
                                    i = 0;
                                }
                                fArrM214698b9 = m214698b9(fArr, i4);
                                i = 0;
                            } catch (NumberFormatException e) {
                                throw new RuntimeException(AbstractC0003a2.m33b4("error in parsing \"", strTrim, "\""), e);
                            }
                        }
                        char cCharAt3 = strTrim.charAt(i);
                        qm0 qm0Var = new qm0();
                        qm0Var.f59534a0 = cCharAt3;
                        qm0Var.f59535a1 = fArrM214698b9;
                        arrayList.add(qm0Var);
                    }
                    i2 = i3;
                    i3++;
                    i = 0;
                }
            }
            strTrim = str.substring(i2, i3).trim();
            if (strTrim.length() > 0) {
            }
            i2 = i3;
            i3++;
            i = 0;
        }
        if (i3 - i2 == 1 && i2 < str.length()) {
            char cCharAt4 = str.charAt(i2);
            qm0 qm0Var2 = new qm0();
            qm0Var2.f59534a0 = cCharAt4;
            qm0Var2.f59535a1 = new float[0];
            arrayList.add(qm0Var2);
        }
        return (qm0[]) arrayList.toArray(new qm0[arrayList.size()]);
    }

    /* renamed from: c3 */
    public static final void m214702c3(String str, String str2) {
        m214695b6(str, "tag");
        m214695b6(str2, "msg");
    }

    /* renamed from: c4 */
    public static qm0[] m214703c4(qm0[] qm0VarArr) {
        if (qm0VarArr == null) {
            return null;
        }
        qm0[] qm0VarArr2 = new qm0[qm0VarArr.length];
        for (int i = 0; i < qm0VarArr.length; i++) {
            qm0 qm0Var = qm0VarArr[i];
            qm0 qm0Var2 = new qm0();
            qm0Var2.f59534a0 = qm0Var.f59534a0;
            float[] fArr = qm0Var.f59535a1;
            qm0Var2.f59535a1 = m214698b9(fArr, fArr.length);
            qm0VarArr2[i] = qm0Var2;
        }
        return qm0VarArr2;
    }

    /* renamed from: c5 */
    public static final void m214704c5(String str, String str2) {
        m214695b6(str, "tag");
        m214695b6(str2, "msg");
    }

    /* renamed from: c6 */
    public static final void m214705c6(String str, String str2, Throwable th) {
        m214695b6(str, "tag");
        m214695b6(str2, "msg");
        m214695b6(th, "tr");
    }

    /* renamed from: c7 */
    public static int m214706c7(Activity activity, String str, int i) {
        int identifier;
        Resources resources = activity.getResources();
        String packageName = activity.getPackageName();
        try {
            int identifier2 = resources.getIdentifier(str, "drawable", packageName);
            if (identifier2 != 0) {
                try {
                    if (resources.getDrawable(identifier2, null) != null) {
                        return identifier2;
                    }
                } catch (Exception e) {
                    m214726f4("ResourceUtils", "资源ID存在但无法获取drawable: " + str + " id=" + identifier2 + ": " + e.getMessage());
                }
            }
            String str2 = activity.getApplicationInfo().packageName;
            if (!m214686a2(str2, packageName) && (identifier = resources.getIdentifier(str, "drawable", str2)) != 0) {
                try {
                    if (resources.getDrawable(identifier, null) != null) {
                        return identifier;
                    }
                } catch (Exception unused) {
                    m214726f4("ResourceUtils", "资源ID存在但无法获取drawable: " + str + " id=" + identifier);
                }
            }
            int identifier3 = resources.getIdentifier(str, "drawable", null);
            if (identifier3 != 0) {
                try {
                    if (resources.getDrawable(identifier3, null) != null) {
                        return identifier3;
                    }
                } catch (Exception unused2) {
                    m214726f4("ResourceUtils", "资源ID存在但无法获取drawable: " + str + " id=" + identifier3);
                }
            }
            if (i != 0) {
                try {
                    if (resources.getDrawable(i, null) != null) {
                        return i;
                    }
                } catch (Exception e2) {
                    m214726f4("ResourceUtils", "备用资源ID验证失败: " + str + " id=" + i + ": " + e2.getMessage());
                }
            }
            m214726f4("ResourceUtils", "未找到资源: ".concat(str));
            return 0;
        } catch (Exception e3) {
            m214705c6("ResourceUtils", "查找资源异常: ".concat(str), e3);
            return 0;
        }
    }

    /* renamed from: c8 */
    public static float m214707c8(EdgeEffect edgeEffect) {
        if (Build.VERSION.SDK_INT >= 31) {
            return AbstractC1350vu.m214956a1(edgeEffect);
        }
        return 0.0f;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* renamed from: c9 */
    public static final Class m214708c9(a80 a80Var) {
        m214695b6(a80Var, "<this>");
        Class clsMo213174a0 = ((InterfaceC0625il) a80Var).mo213174a0();
        if (clsMo213174a0.isPrimitive()) {
            String name = clsMo213174a0.getName();
            switch (name.hashCode()) {
                case -1325958191:
                    if (name.equals("double")) {
                        return Double.class;
                    }
                    break;
                case 104431:
                    if (name.equals("int")) {
                        return Integer.class;
                    }
                    break;
                case 3039496:
                    if (name.equals("byte")) {
                        return Byte.class;
                    }
                    break;
                case 3052374:
                    if (name.equals("char")) {
                        return Character.class;
                    }
                    break;
                case 3327612:
                    if (name.equals("long")) {
                        return Long.class;
                    }
                    break;
                case 3625364:
                    if (name.equals("void")) {
                        return Void.class;
                    }
                    break;
                case 64711720:
                    if (name.equals("boolean")) {
                        return Boolean.class;
                    }
                    break;
                case 97526364:
                    if (name.equals("float")) {
                        return Float.class;
                    }
                    break;
                case 109413500:
                    if (name.equals("short")) {
                        return Short.class;
                    }
                    break;
            }
        }
        return clsMo213174a0;
    }

    /* renamed from: d0 */
    public static void m214709d0() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        stackTraceElement.getFileName();
        stackTraceElement.getLineNumber();
        stackTraceElement.getMethodName();
    }

    /* renamed from: d1 */
    public static void m214710d1() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        stackTraceElement.getFileName();
        stackTraceElement.getLineNumber();
    }

    /* renamed from: d2 */
    public static String m214711d2(Context context, int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        try {
            return context.getResources().getResourceEntryName(i);
        } catch (Exception unused) {
            return tz0.m214802a2(i, "?");
        }
    }

    /* renamed from: d3 */
    public static String m214712d3(View view) {
        try {
            return view.getContext().getResources().getResourceEntryName(view.getId());
        } catch (Exception unused) {
            return "UNKNOWN";
        }
    }

    /* renamed from: d4 */
    public static String m214713d4(String str, String str2) {
        JSONObject jSONObjectOptJSONObject;
        String strOptString;
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String str3 = language + "-" + locale.getCountry();
        JSONObject jSONObject = f60170c2;
        if (jSONObject == null || !jSONObject.has(str3)) {
            JSONObject jSONObject2 = f60170c2;
            if (jSONObject2 == null || !jSONObject2.has(language)) {
                language = "en";
            } else {
                m214694b5(language, "language");
            }
        } else {
            language = str3;
        }
        JSONObject jSONObject3 = f60170c2;
        if (jSONObject3 == null || (jSONObjectOptJSONObject = jSONObject3.optJSONObject(language)) == null) {
            JSONObject jSONObject4 = f60170c2;
            jSONObjectOptJSONObject = jSONObject4 != null ? jSONObject4.optJSONObject("en") : null;
        }
        if (jSONObjectOptJSONObject != null && (strOptString = jSONObjectOptJSONObject.optString(str)) != null) {
            String str4 = strOptString.length() > 0 ? strOptString : null;
            if (str4 != null) {
                return str4;
            }
        }
        return str2;
    }

    /* renamed from: d6 */
    public static final void m214714d6(String str, String str2) {
        m214695b6(str, "tag");
        m214695b6(str2, "msg");
    }

    /* renamed from: d7 */
    public static void m214715d7(Context context) {
        Iterator<String> itKeys;
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(AbstractC1408xb.m215154a0(context, "app_config.json")).optJSONObject("langMap");
            f60170c2 = jSONObjectOptJSONObject;
            m214702c3("CredentialConfigHelper", "配置加载成功，支持语言: " + ((jSONObjectOptJSONObject == null || (itKeys = jSONObjectOptJSONObject.keys()) == null) ? null : qz0.m214468f7(qz0.m214467f6(itKeys))));
        } catch (Exception e) {
            tz0.m214807a7("加载配置失败: ", e.getMessage(), "CredentialConfigHelper");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v8, types: [java.util.Map] */
    /* renamed from: d8 */
    public static final void m214716d8(Context context) {
        LinkedHashMap linkedHashMapM213613f8;
        m214695b6(context, "context");
        File databasePath = context.getDatabasePath("androidx.work.workdb");
        m214694b5(databasePath, "context.getDatabasePath(WORK_DATABASE_NAME)");
        if (databasePath.exists()) {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            String[] strArr = fg1.f56253a0;
            c1351vvM214963a5.getClass();
            File databasePath2 = context.getDatabasePath("androidx.work.workdb");
            m214694b5(databasePath2, "context.getDatabasePath(WORK_DATABASE_NAME)");
            File file = new File(C1283u0.f60313a0.m214811a0(context), "androidx.work.workdb");
            String[] strArr2 = fg1.f56253a0;
            int iM213612f7 = AbstractC0770a1.m213612f7(strArr2.length);
            if (iM213612f7 < 16) {
                iM213612f7 = 16;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(iM213612f7);
            for (String str : strArr2) {
                linkedHashMap.put(new File(databasePath2.getPath() + str), new File(file.getPath() + str));
            }
            Pair pair = new Pair(databasePath2, file);
            if (linkedHashMap.isEmpty()) {
                linkedHashMapM213613f8 = AbstractC0770a1.m213613f8(pair);
            } else {
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
                linkedHashMap2.put(databasePath2, file);
                linkedHashMapM213613f8 = linkedHashMap2;
            }
            for (Map.Entry entry : linkedHashMapM213613f8.entrySet()) {
                File file2 = (File) entry.getKey();
                File file3 = (File) entry.getValue();
                if (file2.exists()) {
                    if (file3.exists()) {
                        C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                        String[] strArr3 = fg1.f56253a0;
                        file3.toString();
                        c1351vvM214963a52.getClass();
                    }
                    if (file2.renameTo(file3)) {
                        file2.toString();
                        file3.toString();
                    } else {
                        file2.toString();
                        file3.toString();
                    }
                    C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
                    String[] strArr4 = fg1.f56253a0;
                    c1351vvM214963a53.getClass();
                }
            }
        }
    }

    /* renamed from: e1 */
    public static float m214717e1(EdgeEffect edgeEffect, float f, float f2) {
        if (Build.VERSION.SDK_INT >= 31) {
            return AbstractC1350vu.m214957a2(edgeEffect, f, f2);
        }
        AbstractC1349vt.m214950a0(edgeEffect, f, f2);
        return f;
    }

    /* renamed from: e2 */
    public static void m214718e2(AnimatorSet animatorSet, ArrayList arrayList) {
        int size = arrayList.size();
        long jMax = 0;
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) arrayList.get(i);
            jMax = Math.max(jMax, animator.getDuration() + animator.getStartDelay());
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 0);
        valueAnimatorOfInt.setDuration(jMax);
        arrayList.add(0, valueAnimatorOfInt);
        animatorSet.playTogether(arrayList);
    }

    /* renamed from: e5 */
    public static void m214719e5(RuntimeException runtimeException, String str) {
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        int length = stackTrace.length;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.equals(stackTrace[i2].getClassName())) {
                i = i2;
            }
        }
        runtimeException.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i + 1, length));
    }

    /* renamed from: e6 */
    public static void m214720e6(ViewGroup viewGroup, float f) {
        Drawable background = viewGroup.getBackground();
        if (background instanceof ce0) {
            ((ce0) background).m210839b1(f);
        }
    }

    /* renamed from: e7 */
    public static void m214721e7(View view, ce0 ce0Var) {
        C1357vz c1357vz = ce0Var.f46107a0.f45838a1;
        if (c1357vz == null || !c1357vz.f60719a0) {
            return;
        }
        float fM213809a8 = 0.0f;
        for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            fM213809a8 += la1.m213809a8((View) parent);
        }
        be0 be0Var = ce0Var.f46107a0;
        if (be0Var.f45848b1 != fM213809a8) {
            be0Var.f45848b1 = fM213809a8;
            ce0Var.m210849c1();
        }
    }

    /* renamed from: e8 */
    public static void m214722e8(ViewGroup viewGroup) {
        Drawable background = viewGroup.getBackground();
        if (background instanceof ce0) {
            m214721e7(viewGroup, (ce0) background);
        }
    }

    /* renamed from: f1 */
    public static final Object m214723f1(hu0 hu0Var, hu0 hu0Var2, l10 l10Var) throws Throwable {
        Object c0730jt;
        Object objM215265d1;
        try {
            b81.m210564a4(l10Var);
            c0730jt = l10Var.invoke(hu0Var2, hu0Var);
        } catch (Throwable th) {
            c0730jt = new C0730jt(th, false);
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        if (c0730jt == coroutineSingletons || (objM215265d1 = hu0Var.m215265d1(c0730jt)) == f60157a9) {
            return coroutineSingletons;
        }
        if (objM215265d1 instanceof C0730jt) {
            throw ((C0730jt) objM215265d1).f57378a0;
        }
        return m214725f3(objM215265d1);
    }

    /* renamed from: f2 */
    public static void m214724f2(String str) {
        UninitializedPropertyAccessException uninitializedPropertyAccessException = new UninitializedPropertyAccessException(AbstractC0003a2.m33b4("lateinit property ", str, " has not been initialized"));
        m214719e5(uninitializedPropertyAccessException, t60.class.getName());
        throw uninitializedPropertyAccessException;
    }

    /* renamed from: f3 */
    public static final Object m214725f3(Object obj) {
        k50 k50Var;
        l50 l50Var = obj instanceof l50 ? (l50) obj : null;
        return (l50Var == null || (k50Var = l50Var.f57829a0) == null) ? obj : k50Var;
    }

    /* renamed from: f4 */
    public static final void m214726f4(String str, String str2) {
        m214695b6(str, "tag");
        m214695b6(str2, "msg");
    }

    /* renamed from: b0 */
    public abstract boolean mo212999b0(AbstractC0573h9 abstractC0573h9, C0569h5 c0569h5, C0569h5 c0569h52);

    /* renamed from: b1 */
    public abstract boolean mo213000b1(AbstractC0573h9 abstractC0573h9, Object obj, Object obj2);

    /* renamed from: b2 */
    public abstract boolean mo213001b2(AbstractC0573h9 abstractC0573h9, C0572h8 c0572h8, C0572h8 c0572h82);

    /* renamed from: d5 */
    public float mo213494d5(View view) {
        if (f60173c5) {
            try {
                return view.getTransitionAlpha();
            } catch (NoSuchMethodError unused) {
                f60173c5 = false;
            }
        }
        return view.getAlpha();
    }

    /* renamed from: d9 */
    public abstract View mo214668d9(int i);

    /* renamed from: e0 */
    public abstract boolean mo214669e0();

    /* renamed from: e3 */
    public abstract void mo213002e3(C0572h8 c0572h8, C0572h8 c0572h82);

    /* renamed from: e4 */
    public abstract void mo213003e4(C0572h8 c0572h8, Thread thread);

    /* renamed from: e9 */
    public void mo213495e9(View view, float f) {
        if (f60173c5) {
            try {
                view.setTransitionAlpha(f);
                return;
            } catch (NoSuchMethodError unused) {
                f60173c5 = false;
            }
        }
        view.setAlpha(f);
    }

    /* renamed from: f0 */
    public void mo213284f0(View view, int i) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (!f60172c4) {
            try {
                Field declaredField = View.class.getDeclaredField("mViewFlags");
                f60171c3 = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
            }
            f60172c4 = true;
        }
        Field field = f60171c3;
        if (field != null) {
            try {
                f60171c3.setInt(view, i | (field.getInt(view) & (-13)));
            } catch (IllegalAccessException unused2) {
            }
        }
    }
}
