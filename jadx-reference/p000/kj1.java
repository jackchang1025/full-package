package p000;

import android.R;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.util.Xml;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.core.R$styleable;
import com.google.android.material.R$attr;
import com.google.android.material.R$integer;
import com.google.android.material.appbar.AppBarLayout;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import okio.Segment;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class kj1 implements oc1 {

    /* renamed from: a0 */
    public static final Object[] f57532a0 = new Object[0];

    /* renamed from: a1 */
    public static final C1347vr f57533a1 = new C1347vr("REMOVED_TASK");

    /* renamed from: a2 */
    public static final C1347vr f57534a2 = new C1347vr("CLOSED_EMPTY");

    /* renamed from: a3 */
    public static final boolean[] f57535a3 = new boolean[3];

    /* renamed from: a4 */
    public static final int[] f57536a4 = {R.attr.stateListAnimator};

    /* renamed from: a5 */
    public static final /* synthetic */ int f57537a5 = 0;

    /* renamed from: a3 */
    public static void m213556a3(Throwable th, Throwable th2) {
        t60.m214695b6(th, "<this>");
        t60.m214695b6(th2, "exception");
        if (th != th2) {
            Integer num = e70.f55936a0;
            if (num == null || num.intValue() >= 19) {
                th.addSuppressed(th2);
                return;
            }
            Method method = nn0.f58675a0;
            if (method != null) {
                method.invoke(th, th2);
            }
        }
    }

    /* renamed from: a4 */
    public static final String m213557a4(Number number, Number number2) {
        return "Random range is empty: [" + number + ", " + number2 + ").";
    }

    /* renamed from: a5 */
    public static void m213558a5(C0830lr c0830lr, ab0 ab0Var, C0829lq c0829lq) {
        c0829lq.f58075b4 = -1;
        C0797kv c0797kv = c0829lq.f58100d9;
        C0797kv c0797kv2 = c0829lq.f58099d8;
        C0797kv c0797kv3 = c0829lq.f58097d6;
        C0797kv c0797kv4 = c0829lq.f58098d7;
        C0797kv c0797kv5 = c0829lq.f58096d5;
        c0829lq.f58076b5 = -1;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = c0830lr.f58107e6[0];
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = ConstraintWidget$DimensionBehaviour.f44427a3;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = ConstraintWidget$DimensionBehaviour.f44425a1;
        if (constraintWidget$DimensionBehaviour != constraintWidget$DimensionBehaviour3 && c0829lq.f58107e6[0] == constraintWidget$DimensionBehaviour2) {
            int i = c0797kv5.f57727a6;
            int iM213891b7 = c0830lr.m213891b7() - c0797kv4.f57727a6;
            c0797kv5.f57729a8 = ab0Var.m209769b0(c0797kv5);
            c0797kv4.f57729a8 = ab0Var.m209769b0(c0797kv4);
            ab0Var.m209762a3(c0797kv5.f57729a8, i);
            ab0Var.m209762a3(c0797kv4.f57729a8, iM213891b7);
            c0829lq.f58075b4 = 2;
            c0829lq.f58113f2 = i;
            int i2 = iM213891b7 - i;
            c0829lq.f58109e8 = i2;
            int i3 = c0829lq.f58116f5;
            if (i2 < i3) {
                c0829lq.f58109e8 = i3;
            }
        }
        if (c0830lr.f58107e6[1] == constraintWidget$DimensionBehaviour3 || c0829lq.f58107e6[1] != constraintWidget$DimensionBehaviour2) {
            return;
        }
        int i4 = c0797kv3.f57727a6;
        int iM213887b1 = c0830lr.m213887b1() - c0797kv2.f57727a6;
        c0797kv3.f57729a8 = ab0Var.m209769b0(c0797kv3);
        c0797kv2.f57729a8 = ab0Var.m209769b0(c0797kv2);
        ab0Var.m209762a3(c0797kv3.f57729a8, i4);
        ab0Var.m209762a3(c0797kv2.f57729a8, iM213887b1);
        if (c0829lq.f58115f4 > 0 || c0829lq.f58121g0 == 8) {
            e11 e11VarM209769b0 = ab0Var.m209769b0(c0797kv);
            c0797kv.f57729a8 = e11VarM209769b0;
            ab0Var.m209762a3(e11VarM209769b0, c0829lq.f58115f4 + i4);
        }
        c0829lq.f58076b5 = 2;
        c0829lq.f58114f3 = i4;
        int i5 = iM213887b1 - i4;
        c0829lq.f58110e9 = i5;
        int i6 = c0829lq.f58117f6;
        if (i5 < i6) {
            c0829lq.f58110e9 = i6;
        }
    }

    /* renamed from: a6 */
    public static final void m213559a6(Closeable closeable, Throwable th) {
        if (closeable != null) {
            if (th == null) {
                closeable.close();
                return;
            }
            try {
                closeable.close();
            } catch (Throwable th2) {
                m213556a3(th, th2);
            }
        }
    }

    /* renamed from: a7 */
    public static void m213560a7(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    /* renamed from: a8 */
    public static int m213561a8(int i, int i2) {
        return AbstractC0724jn.m213334a4(i, (Color.alpha(i) * i2) / v10.MASK);
    }

    /* renamed from: a9 */
    public static final void m213562a9(int i, int i2) {
        if (i > i2) {
            throw new IndexOutOfBoundsException(AbstractC0003a2.m31b2("toIndex (", i, ") is greater than size (", i2, ")."));
        }
    }

    /* renamed from: b0 */
    public static boolean m213563b0(File file, Resources resources, int i) throws Throwable {
        InputStream inputStreamOpenRawResource;
        try {
            inputStreamOpenRawResource = resources.openRawResource(i);
            try {
                boolean zM213564b1 = m213564b1(file, inputStreamOpenRawResource);
                m213560a7(inputStreamOpenRawResource);
                return zM213564b1;
            } catch (Throwable th) {
                th = th;
                m213560a7(inputStreamOpenRawResource);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpenRawResource = null;
        }
    }

    /* renamed from: b1 */
    public static boolean m213564b1(File file, InputStream inputStream) throws Throwable {
        FileOutputStream fileOutputStream;
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file, false);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[Segment.SHARE_MINIMUM];
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    m213560a7(fileOutputStream);
                    StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites);
                    return true;
                }
                fileOutputStream.write(bArr, 0, i);
            }
        } catch (IOException e2) {
            e = e2;
            fileOutputStream2 = fileOutputStream;
            e.getMessage();
            m213560a7(fileOutputStream2);
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites);
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            m213560a7(fileOutputStream2);
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites);
            throw th;
        }
    }

    /* renamed from: b2 */
    public static final boolean m213565b2(int i, int i2) {
        return (i & i2) == i2;
    }

    /* renamed from: b3 */
    public static void m213566b3() {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
    }

    /* renamed from: b4 */
    public static int m213567b4(Context context, int i, int i2) {
        TypedValue typedValueM213534e1 = kg1.m213534e1(context, i);
        if (typedValueM213534e1 == null) {
            return i2;
        }
        int i3 = typedValueM213534e1.resourceId;
        return i3 != 0 ? AbstractC0871mq.m214015a0(context, i3) : typedValueM213534e1.data;
    }

    /* renamed from: b5 */
    public static int m213568b5(View view, int i) {
        Context context = view.getContext();
        TypedValue typedValueM213538e5 = kg1.m213538e5(view.getContext(), i, view.getClass().getCanonicalName());
        int i2 = typedValueM213538e5.resourceId;
        return i2 != 0 ? AbstractC0871mq.m214015a0(context, i2) : typedValueM213538e5.data;
    }

    /* renamed from: b6 */
    public static Intent m213569b6(AppCompatActivity appCompatActivity) {
        Intent intentM209801a0 = ai0.m209801a0(appCompatActivity);
        if (intentM209801a0 != null) {
            return intentM209801a0;
        }
        try {
            String strM213571b8 = m213571b8(appCompatActivity, appCompatActivity.getComponentName());
            if (strM213571b8 == null) {
                return null;
            }
            ComponentName componentName = new ComponentName(appCompatActivity, strM213571b8);
            try {
                return m213571b8(appCompatActivity, componentName) == null ? Intent.makeMainActivity(componentName) : new Intent().setComponent(componentName);
            } catch (PackageManager.NameNotFoundException unused) {
                return null;
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /* renamed from: b7 */
    public static Intent m213570b7(AppCompatActivity appCompatActivity, ComponentName componentName) {
        String strM213571b8 = m213571b8(appCompatActivity, componentName);
        if (strM213571b8 == null) {
            return null;
        }
        ComponentName componentName2 = new ComponentName(componentName.getPackageName(), strM213571b8);
        return m213571b8(appCompatActivity, componentName2) == null ? Intent.makeMainActivity(componentName2) : new Intent().setComponent(componentName2);
    }

    /* renamed from: b8 */
    public static String m213571b8(Context context, ComponentName componentName) {
        String string;
        ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(componentName, Build.VERSION.SDK_INT >= 29 ? 269222528 : 787072);
        String str = activityInfo.parentActivityName;
        if (str != null) {
            return str;
        }
        Bundle bundle = activityInfo.metaData;
        if (bundle == null || (string = bundle.getString("android.support.PARENT_ACTIVITY")) == null) {
            return null;
        }
        if (string.charAt(0) != '.') {
            return string;
        }
        return context.getPackageName() + string;
    }

    /* renamed from: b9 */
    public static Pair m213572b9(Context context) {
        t60.m214695b6(context, "context");
        try {
            Object systemService = context.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            if (point.x > 0 && point.y > 0) {
                if (point.y >= context.getResources().getDisplayMetrics().heightPixels) {
                    return new Pair(Integer.valueOf(point.x), Integer.valueOf(point.y));
                }
            }
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            t60.m214726f4("ScreenUtils", "回退使用displayMetrics: " + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels);
            return new Pair(Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels));
        } catch (Exception e) {
            t60.m214705c6("ScreenUtils", "获取屏幕尺寸失败", e);
            DisplayMetrics displayMetrics2 = context.getResources().getDisplayMetrics();
            return new Pair(Integer.valueOf(displayMetrics2.widthPixels), Integer.valueOf(displayMetrics2.heightPixels));
        }
    }

    /* renamed from: c0 */
    public static File m213573c0(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String str = ".font" + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; i++) {
            File file = new File(cacheDir, str + i);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }

    /* renamed from: c1 */
    public static final void m213574c1(InterfaceC0912ng interfaceC0912ng, Throwable th) {
        try {
            InterfaceC0914ni interfaceC0914ni = (InterfaceC0914ni) interfaceC0912ng.mo212745b4(C1351vv.f60701a2);
            if (interfaceC0914ni != null) {
                interfaceC0914ni.mo214107c5(th);
            } else {
                kg1.m213516c1(interfaceC0912ng, th);
            }
        } catch (Throwable th2) {
            if (th != th2) {
                RuntimeException runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                m213556a3(runtimeException, th);
                th = runtimeException;
            }
            kg1.m213516c1(interfaceC0912ng, th);
        }
    }

    /* renamed from: c2 */
    public static InterfaceC0876mv m213575c2(InterfaceC0876mv interfaceC0876mv) {
        InterfaceC0876mv interfaceC0876mvIntercepted;
        t60.m214695b6(interfaceC0876mv, "<this>");
        ContinuationImpl continuationImpl = interfaceC0876mv instanceof ContinuationImpl ? (ContinuationImpl) interfaceC0876mv : null;
        return (continuationImpl == null || (interfaceC0876mvIntercepted = continuationImpl.intercepted()) == null) ? interfaceC0876mv : interfaceC0876mvIntercepted;
    }

    /* renamed from: c3 */
    public static boolean m213576c3(int i) {
        if (i == 0) {
            return false;
        }
        ThreadLocal threadLocal = AbstractC0724jn.f57347a0;
        double[] dArr = (double[]) threadLocal.get();
        if (dArr == null) {
            dArr = new double[3];
            threadLocal.set(dArr);
        }
        int iRed = Color.red(i);
        int iGreen = Color.green(i);
        int iBlue = Color.blue(i);
        if (dArr.length != 3) {
            throw new IllegalArgumentException("outXyz must have a length of 3.");
        }
        double d = iRed / 255.0d;
        double dPow = d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d);
        double d2 = iGreen / 255.0d;
        double dPow2 = d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 + 0.055d) / 1.055d, 2.4d);
        double d3 = iBlue / 255.0d;
        double dPow3 = d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 + 0.055d) / 1.055d, 2.4d);
        dArr[0] = ((0.1805d * dPow3) + (0.3576d * dPow2) + (0.4124d * dPow)) * 100.0d;
        double d4 = ((0.0722d * dPow3) + (0.7152d * dPow2) + (0.2126d * dPow)) * 100.0d;
        dArr[1] = d4;
        dArr[2] = ((dPow3 * 0.9505d) + (dPow2 * 0.1192d) + (dPow * 0.0193d)) * 100.0d;
        return d4 / 100.0d > 0.5d;
    }

    /* renamed from: c4 */
    public static int m213577c4(int i, float f, int i2) {
        return AbstractC0724jn.m213332a2(AbstractC0724jn.m213334a4(i2, Math.round(Color.alpha(i2) * f)), i);
    }

    /* renamed from: c5 */
    public static MappedByteBuffer m213578c5(Context context, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptorM213110a0;
        try {
            parcelFileDescriptorM213110a0 = i81.m213110a0(context.getContentResolver(), uri, "r", null);
        } catch (IOException unused) {
        }
        if (parcelFileDescriptorM213110a0 == null) {
            if (parcelFileDescriptorM213110a0 != null) {
                parcelFileDescriptorM213110a0.close();
                return null;
            }
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptorM213110a0.getFileDescriptor());
            try {
                FileChannel channel = fileInputStream.getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                fileInputStream.close();
                parcelFileDescriptorM213110a0.close();
                return map;
            } finally {
            }
        } finally {
        }
    }

    /* renamed from: c6 */
    public static InterfaceC0881n m213579c6(XmlResourceParser xmlResourceParser, Resources resources) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        xmlResourceParser.require(2, null, "font-family");
        if (!xmlResourceParser.getName().equals("font-family")) {
            m213588d5(xmlResourceParser);
            return null;
        }
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.FontFamily);
        String string = typedArrayObtainAttributes.getString(R$styleable.FontFamily_fontProviderAuthority);
        String string2 = typedArrayObtainAttributes.getString(R$styleable.FontFamily_fontProviderPackage);
        String string3 = typedArrayObtainAttributes.getString(R$styleable.FontFamily_fontProviderQuery);
        int resourceId = typedArrayObtainAttributes.getResourceId(R$styleable.FontFamily_fontProviderCerts, 0);
        int integer = typedArrayObtainAttributes.getInteger(R$styleable.FontFamily_fontProviderFetchStrategy, 1);
        int integer2 = typedArrayObtainAttributes.getInteger(R$styleable.FontFamily_fontProviderFetchTimeout, 500);
        String string4 = typedArrayObtainAttributes.getString(R$styleable.FontFamily_fontProviderSystemFontFamily);
        typedArrayObtainAttributes.recycle();
        if (string != null && string2 != null && string3 != null) {
            while (xmlResourceParser.next() != 3) {
                m213588d5(xmlResourceParser);
            }
            return new C1091q(new C1094q2(string, string2, string3, m213581c8(resources, resourceId)), integer, integer2, string4);
        }
        ArrayList arrayList = new ArrayList();
        while (xmlResourceParser.next() != 3) {
            if (xmlResourceParser.getEventType() == 2) {
                if (xmlResourceParser.getName().equals("font")) {
                    TypedArray typedArrayObtainAttributes2 = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.FontFamilyFont);
                    int i = typedArrayObtainAttributes2.getInt(typedArrayObtainAttributes2.hasValue(R$styleable.FontFamilyFont_fontWeight) ? R$styleable.FontFamilyFont_fontWeight : R$styleable.FontFamilyFont_android_fontWeight, 400);
                    boolean z = 1 == typedArrayObtainAttributes2.getInt(typedArrayObtainAttributes2.hasValue(R$styleable.FontFamilyFont_fontStyle) ? R$styleable.FontFamilyFont_fontStyle : R$styleable.FontFamilyFont_android_fontStyle, 0);
                    int i2 = typedArrayObtainAttributes2.hasValue(R$styleable.FontFamilyFont_ttcIndex) ? R$styleable.FontFamilyFont_ttcIndex : R$styleable.FontFamilyFont_android_ttcIndex;
                    String string5 = typedArrayObtainAttributes2.getString(typedArrayObtainAttributes2.hasValue(R$styleable.FontFamilyFont_fontVariationSettings) ? R$styleable.FontFamilyFont_fontVariationSettings : R$styleable.FontFamilyFont_android_fontVariationSettings);
                    int i3 = typedArrayObtainAttributes2.getInt(i2, 0);
                    int i4 = typedArrayObtainAttributes2.hasValue(R$styleable.FontFamilyFont_font) ? R$styleable.FontFamilyFont_font : R$styleable.FontFamilyFont_android_font;
                    int resourceId2 = typedArrayObtainAttributes2.getResourceId(i4, 0);
                    String string6 = typedArrayObtainAttributes2.getString(i4);
                    typedArrayObtainAttributes2.recycle();
                    while (xmlResourceParser.next() != 3) {
                        m213588d5(xmlResourceParser);
                    }
                    arrayList.add(new C1050p(i, i3, resourceId2, string6, string5, z));
                } else {
                    m213588d5(xmlResourceParser);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new C0934o((C1050p[]) arrayList.toArray(new C1050p[0]));
    }

    /* renamed from: c7 */
    public static final Cursor m213580c7(fs0 fs0Var, js0 js0Var) {
        t60.m214695b6(fs0Var, "db");
        t60.m214695b6(js0Var, "sqLiteQuery");
        return fs0Var.m212861b0(js0Var);
    }

    /* renamed from: c8 */
    public static List m213581c8(Resources resources, int i) throws Resources.NotFoundException {
        if (i == 0) {
            return Collections.EMPTY_LIST;
        }
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(i);
        try {
            if (typedArrayObtainTypedArray.length() == 0) {
                return Collections.EMPTY_LIST;
            }
            ArrayList arrayList = new ArrayList();
            if (AbstractC0843m.m213929a0(typedArrayObtainTypedArray, 0) == 1) {
                for (int i2 = 0; i2 < typedArrayObtainTypedArray.length(); i2++) {
                    int resourceId = typedArrayObtainTypedArray.getResourceId(i2, 0);
                    if (resourceId != 0) {
                        String[] stringArray = resources.getStringArray(resourceId);
                        ArrayList arrayList2 = new ArrayList();
                        for (String str : stringArray) {
                            arrayList2.add(Base64.decode(str, 0));
                        }
                        arrayList.add(arrayList2);
                    }
                }
            } else {
                String[] stringArray2 = resources.getStringArray(i);
                ArrayList arrayList3 = new ArrayList();
                for (String str2 : stringArray2) {
                    arrayList3.add(Base64.decode(str2, 0));
                }
                arrayList.add(arrayList3);
            }
            return arrayList;
        } finally {
            typedArrayObtainTypedArray.recycle();
        }
    }

    /* renamed from: c9 */
    public static final void m213582c9(Object[] objArr, int i, int i2) {
        t60.m214695b6(objArr, "<this>");
        while (i < i2) {
            objArr[i] = null;
            i++;
        }
    }

    /* renamed from: d0 */
    public static void m213583d0(AppBarLayout appBarLayout, float f) throws Resources.NotFoundException {
        int integer = appBarLayout.getResources().getInteger(R$integer.app_bar_elevation_anim_duration);
        StateListAnimator stateListAnimator = new StateListAnimator();
        long j = integer;
        stateListAnimator.addState(new int[]{R.attr.state_enabled, R$attr.state_liftable, -R$attr.state_lifted}, ObjectAnimator.ofFloat(appBarLayout, "elevation", 0.0f).setDuration(j));
        stateListAnimator.addState(new int[]{R.attr.state_enabled}, ObjectAnimator.ofFloat(appBarLayout, "elevation", f).setDuration(j));
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(appBarLayout, "elevation", 0.0f).setDuration(0L));
        appBarLayout.setStateListAnimator(stateListAnimator);
    }

    /* renamed from: d1 */
    public static void m213584d1(Drawable drawable, int i) {
        AbstractC1270tr.m214773a6(drawable, i);
    }

    /* renamed from: d2 */
    public static void m213585d2(Drawable drawable, ColorStateList colorStateList) {
        AbstractC1270tr.m214774a7(drawable, colorStateList);
    }

    /* renamed from: d3 */
    public static void m213586d3(Drawable drawable, PorterDuff.Mode mode) {
        AbstractC1270tr.m214775a8(drawable, mode);
    }

    /* renamed from: d4 */
    public static void m213587d4(View view, CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            g71.m212898a0(view, charSequence);
            return;
        }
        i71 i71Var = i71.f56806b0;
        if (i71Var != null && i71Var.f56808a0 == view) {
            i71.m213107a1(null);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            new i71(view, charSequence);
            return;
        }
        i71 i71Var2 = i71.f56807b1;
        if (i71Var2 != null && i71Var2.f56808a0 == view) {
            i71Var2.m213108a0();
        }
        view.setOnLongClickListener(null);
        view.setLongClickable(false);
        view.setOnHoverListener(null);
    }

    /* renamed from: d5 */
    public static void m213588d5(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int i = 1;
        while (i > 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }

    /* renamed from: d6 */
    public static String m213589d6(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        String string = stringWriter.toString();
        t60.m214694b5(string, "sw.toString()");
        return string;
    }

    /* renamed from: d7 */
    public static final long m213590d7(String str, long j, long j2, long j3) {
        String property;
        int i = q41.f59384a0;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            property = null;
        }
        if (property == null) {
            return j;
        }
        Long lM213686d9 = AbstractC0779a1.m213686d9(property);
        if (lM213686d9 == null) {
            throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + property + '\'').toString());
        }
        long jLongValue = lM213686d9.longValue();
        if (j2 <= jLongValue && jLongValue <= j3) {
            return jLongValue;
        }
        throw new IllegalStateException(("System property '" + str + "' should be in range " + j2 + ".." + j3 + ", but is '" + jLongValue + '\'').toString());
    }

    /* renamed from: d8 */
    public static int m213591d8(int i, int i2, String str) {
        return (int) m213590d7(str, i, 1, (i2 & 8) != 0 ? Integer.MAX_VALUE : 2097150);
    }

    /* renamed from: d9 */
    public static final Object[] m213592d9(Collection collection) {
        int size = collection.size();
        if (size != 0) {
            Iterator it = collection.iterator();
            if (it.hasNext()) {
                Object[] objArrCopyOf = new Object[size];
                int i = 0;
                while (true) {
                    int i2 = i + 1;
                    objArrCopyOf[i] = it.next();
                    if (i2 >= objArrCopyOf.length) {
                        if (!it.hasNext()) {
                            return objArrCopyOf;
                        }
                        int i3 = ((i2 * 3) + 1) >>> 1;
                        if (i3 <= i2) {
                            i3 = 2147483645;
                            if (i2 >= 2147483645) {
                                throw new OutOfMemoryError();
                            }
                        }
                        objArrCopyOf = Arrays.copyOf(objArrCopyOf, i3);
                        t60.m214694b5(objArrCopyOf, "copyOf(result, newSize)");
                    } else if (!it.hasNext()) {
                        Object[] objArrCopyOf2 = Arrays.copyOf(objArrCopyOf, i2);
                        t60.m214694b5(objArrCopyOf2, "copyOf(result, size)");
                        return objArrCopyOf2;
                    }
                    i = i2;
                }
            }
        }
        return f57532a0;
    }

    /* renamed from: e0 */
    public static final Object[] m213593e0(Collection collection, Object[] objArr) throws NegativeArraySizeException {
        Object[] objArrCopyOf;
        int size = collection.size();
        int i = 0;
        if (size != 0) {
            Iterator it = collection.iterator();
            if (it.hasNext()) {
                if (size <= objArr.length) {
                    objArrCopyOf = objArr;
                } else {
                    Object objNewInstance = Array.newInstance(objArr.getClass().getComponentType(), size);
                    t60.m214693b4(objNewInstance, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                    objArrCopyOf = (Object[]) objNewInstance;
                }
                while (true) {
                    int i2 = i + 1;
                    objArrCopyOf[i] = it.next();
                    if (i2 >= objArrCopyOf.length) {
                        if (!it.hasNext()) {
                            return objArrCopyOf;
                        }
                        int i3 = ((i2 * 3) + 1) >>> 1;
                        if (i3 <= i2) {
                            i3 = 2147483645;
                            if (i2 >= 2147483645) {
                                throw new OutOfMemoryError();
                            }
                        }
                        objArrCopyOf = Arrays.copyOf(objArrCopyOf, i3);
                        t60.m214694b5(objArrCopyOf, "copyOf(result, newSize)");
                    } else if (!it.hasNext()) {
                        if (objArrCopyOf == objArr) {
                            objArr[i2] = null;
                            return objArr;
                        }
                        Object[] objArrCopyOf2 = Arrays.copyOf(objArrCopyOf, i2);
                        t60.m214694b5(objArrCopyOf2, "copyOf(result, size)");
                        return objArrCopyOf2;
                    }
                    i = i2;
                }
            } else if (objArr.length > 0) {
                objArr[0] = null;
            }
        } else if (objArr.length > 0) {
            objArr[0] = null;
            return objArr;
        }
        return objArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: e1 */
    public static Drawable m213594e1(Drawable drawable) {
        if (!(drawable instanceof gh1)) {
            return drawable;
        }
        ((hh1) ((gh1) drawable)).getClass();
        return null;
    }

    @Override // p000.oc1
    /* renamed from: a2 */
    public void mo212660a2() {
    }

    @Override // p000.oc1
    /* renamed from: a1 */
    public void mo212659a1(View view) {
    }
}
