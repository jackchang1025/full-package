package p000;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.os.Trace;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.fragment.R$animator;
import androidx.fragment.R$id;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.profileinstaller.AbstractC0080a0;
import com.storm.safe.rock.hkdrkgzsfs;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Result;
import kotlin.collections.builders.ListBuilder;
import kotlin.text.AbstractC0779a1;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qo */
/* loaded from: classes2.dex */
public abstract class AbstractC1117qo {

    /* renamed from: a0 */
    public static final Object f59536a0 = null;

    /* renamed from: a1 */
    public static final C1347vr f59537a1 = new C1347vr("CLOSED");

    /* renamed from: a2 */
    public static final C0438e f59538a2 = new C0438e();

    /* renamed from: a3 */
    public static final fh0 f59539a3 = new fh0(4);

    /* renamed from: a4 */
    public static JSONObject f59540a4;

    /* renamed from: a5 */
    public static JSONObject f59541a5;

    /* renamed from: a6 */
    public static long f59542a6;

    /* renamed from: a7 */
    public static Method f59543a7;

    /* renamed from: a0 */
    public static final C0873ms m214407a0(InterfaceC0912ng interfaceC0912ng) {
        if (interfaceC0912ng.mo212745b4(C1351vv.f60702a3) == null) {
            interfaceC0912ng = interfaceC0912ng.mo212744b2(new m70());
        }
        return new C0873ms(interfaceC0912ng);
    }

    /* renamed from: a1 */
    public static ListBuilder m214408a1(ListBuilder listBuilder) {
        if (listBuilder.f57583a4 != null) {
            throw new IllegalStateException();
        }
        listBuilder.m213621a4();
        listBuilder.f57582a3 = true;
        return listBuilder.f57581a2 > 0 ? listBuilder : ListBuilder.f57578a6;
    }

    /* renamed from: a2 */
    public static void m214409a2(float f, float[] fArr) {
        if (f <= 0.5f) {
            fArr[0] = 1.0f - (f * 2.0f);
            fArr[1] = 0.0f;
        } else {
            fArr[0] = 0.0f;
            fArr[1] = (f * 2.0f) - 1.0f;
        }
    }

    /* renamed from: a3 */
    public static void m214410a3(InterfaceC0920no interfaceC0920no) {
        k70 k70Var = (k70) interfaceC0920no.mo210226a1().mo212745b4(C1351vv.f60702a3);
        if (k70Var != null) {
            ((y70) k70Var).m215253a7(null);
        } else {
            throw new IllegalStateException(("Scope cannot be cancelled because it does not have a job: " + interfaceC0920no).toString());
        }
    }

    /* renamed from: a7 */
    public static int m214411a7(Context context, String str) {
        if (str != null) {
            return (AbstractC0496fi.m212821a0() || !TextUtils.equals("android.permission.POST_NOTIFICATIONS", str)) ? context.checkPermission(str, Process.myPid(), Process.myUid()) : mk0.m214005a0(new nk0(context).f58645a0) ? 0 : -1;
        }
        throw new NullPointerException("permission must be non-null");
    }

    /* renamed from: a8 */
    public static float m214412a8(float f, float f2, float f3) {
        if (f2 <= f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + f3 + " is less than minimum " + f2 + '.');
    }

    /* renamed from: a9 */
    public static int m214413a9(int i, int i2, int i3) {
        if (i2 <= i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + i3 + " is less than minimum " + i2 + '.');
    }

    /* renamed from: b0 */
    public static long m214414b0(long j, long j2, long j3) {
        if (j2 <= j3) {
            return j < j2 ? j2 : j > j3 ? j3 : j;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + j3 + " is less than minimum " + j2 + '.');
    }

    /* renamed from: b1 */
    public static Drawable m214415b1(Drawable drawable, Drawable drawable2) {
        int intrinsicHeight;
        int intrinsicWidth;
        if (drawable == null) {
            return drawable2;
        }
        if (drawable2 == null) {
            return drawable;
        }
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable, drawable2});
        if (drawable2.getIntrinsicWidth() == -1 || drawable2.getIntrinsicHeight() == -1) {
            int intrinsicWidth2 = drawable.getIntrinsicWidth();
            intrinsicHeight = drawable.getIntrinsicHeight();
            intrinsicWidth = intrinsicWidth2;
        } else if (drawable2.getIntrinsicWidth() > drawable.getIntrinsicWidth() || drawable2.getIntrinsicHeight() > drawable.getIntrinsicHeight()) {
            float intrinsicWidth3 = drawable2.getIntrinsicWidth() / drawable2.getIntrinsicHeight();
            if (intrinsicWidth3 >= drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) {
                intrinsicWidth = drawable.getIntrinsicWidth();
                intrinsicHeight = (int) (intrinsicWidth / intrinsicWidth3);
            } else {
                intrinsicHeight = drawable.getIntrinsicHeight();
                intrinsicWidth = (int) (intrinsicWidth3 * intrinsicHeight);
            }
        } else {
            intrinsicWidth = drawable2.getIntrinsicWidth();
            intrinsicHeight = drawable2.getIntrinsicHeight();
        }
        layerDrawable.setLayerSize(1, intrinsicWidth, intrinsicHeight);
        layerDrawable.setLayerGravity(1, 17);
        return layerDrawable;
    }

    /* renamed from: b2 */
    public static int m214416b2(ar0 ar0Var, AbstractC1371wc abstractC1371wc, View view, View view2, pq0 pq0Var, boolean z) {
        if (pq0Var.m214311c1() == 0 || ar0Var.m210500a1() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(pq0.m214304d0(view) - pq0.m214304d0(view2)) + 1;
        }
        return Math.min(abstractC1371wc.mo214631b1(), abstractC1371wc.mo214621a1(view2) - abstractC1371wc.mo214624a4(view));
    }

    /* renamed from: b3 */
    public static int m214417b3(ar0 ar0Var, AbstractC1371wc abstractC1371wc, View view, View view2, pq0 pq0Var, boolean z, boolean z2) {
        if (pq0Var.m214311c1() == 0 || ar0Var.m210500a1() == 0 || view == null || view2 == null) {
            return 0;
        }
        int iMax = z2 ? Math.max(0, (ar0Var.m210500a1() - Math.max(pq0.m214304d0(view), pq0.m214304d0(view2))) - 1) : Math.max(0, Math.min(pq0.m214304d0(view), pq0.m214304d0(view2)));
        if (z) {
            return Math.round((iMax * (Math.abs(abstractC1371wc.mo214621a1(view2) - abstractC1371wc.mo214624a4(view)) / (Math.abs(pq0.m214304d0(view) - pq0.m214304d0(view2)) + 1))) + (abstractC1371wc.mo214630b0() - abstractC1371wc.mo214624a4(view)));
        }
        return iMax;
    }

    /* renamed from: b4 */
    public static int m214418b4(ar0 ar0Var, AbstractC1371wc abstractC1371wc, View view, View view2, pq0 pq0Var, boolean z) {
        if (pq0Var.m214311c1() == 0 || ar0Var.m210500a1() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return ar0Var.m210500a1();
        }
        return (int) (((abstractC1371wc.mo214621a1(view2) - abstractC1371wc.mo214624a4(view)) / (Math.abs(pq0.m214304d0(view) - pq0.m214304d0(view2)) + 1)) * ar0Var.m210500a1());
    }

    /* renamed from: b5 */
    public static Drawable m214419b5(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (drawable == null) {
            return null;
        }
        if (colorStateList != null) {
            drawable = drawable.mutate();
            if (mode != null) {
                AbstractC1270tr.m214775a8(drawable, mode);
            }
        }
        return drawable;
    }

    /* renamed from: b6 */
    public static boolean m214420b6(File file) {
        if (!file.isDirectory()) {
            file.delete();
            return true;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return false;
        }
        boolean z = true;
        for (File file2 : fileArrListFiles) {
            z = m214420b6(file2) && z;
        }
        return z;
    }

    /* renamed from: b7 */
    public static void m214421b7(View view, fd1 fd1Var) {
        WeakHashMap weakHashMap = xa1.f61054a0;
        int iM212906a5 = ga1.m212906a5(view);
        int paddingTop = view.getPaddingTop();
        int iM212905a4 = ga1.m212905a4(view);
        int paddingBottom = view.getPaddingBottom();
        gd1 gd1Var = new gd1();
        gd1Var.f56445a0 = iM212906a5;
        gd1Var.f56446a1 = paddingTop;
        gd1Var.f56447a2 = iM212905a4;
        gd1Var.f56448a3 = paddingBottom;
        la1.m213821c0(view, new og1(fd1Var, gd1Var));
        if (ia1.m213141a1(view)) {
            ja1.m213282a2(view);
        } else {
            view.addOnAttachStateChangeListener(new ed1());
        }
    }

    /* renamed from: b8 */
    public static float m214422b8(Context context, int i) {
        return TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }

    /* renamed from: b9 */
    public static final Object m214423b9(jz0 jz0Var, long j, l10 l10Var) {
        while (true) {
            if (jz0Var.f57401a2 >= j && !jz0Var.mo213361a2()) {
                return jz0Var;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = AbstractC0759ki.f57528a0;
            Object obj = atomicReferenceFieldUpdater.get(jz0Var);
            C1347vr c1347vr = f59537a1;
            if (obj == c1347vr) {
                return c1347vr;
            }
            jz0 jz0Var2 = (jz0) ((AbstractC0759ki) obj);
            if (jz0Var2 == null) {
                jz0Var2 = (jz0) l10Var.invoke(Long.valueOf(jz0Var.f57401a2 + 1), jz0Var);
                while (!atomicReferenceFieldUpdater.compareAndSet(jz0Var, null, jz0Var2)) {
                    if (atomicReferenceFieldUpdater.get(jz0Var) != null) {
                        break;
                    }
                }
                if (jz0Var.mo213361a2()) {
                    jz0Var.m213555a3();
                }
            }
            jz0Var = jz0Var2;
        }
    }

    /* renamed from: c0 */
    public static String m214424c0() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase;
    }

    /* renamed from: c1 */
    public static int[] m214425c1(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            if (i2 == 16842912) {
                return iArr;
            }
            if (i2 == 0) {
                int[] iArr2 = (int[]) iArr.clone();
                iArr2[i] = 16842912;
                return iArr2;
            }
        }
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length + 1);
        iArrCopyOf[iArr.length] = 16842912;
        return iArrCopyOf;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0047, code lost:
    
        if (r5.f60681a2 == r8.hashCode()) goto L21;
     */
    /* renamed from: c2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ColorStateList m214426c2(Context context, int i) throws Resources.NotFoundException {
        ColorStateList colorStateListM213328a0;
        ColorStateList colorStateList;
        vr0 vr0Var;
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();
        wr0 wr0Var = new wr0(resources, theme);
        synchronized (yr0.f61366a2) {
            try {
                SparseArray sparseArray = (SparseArray) yr0.f61365a1.get(wr0Var);
                colorStateListM213328a0 = null;
                if (sparseArray == null || sparseArray.size() <= 0 || (vr0Var = (vr0) sparseArray.get(i)) == null) {
                    colorStateList = null;
                } else {
                    if (vr0Var.f60680a1.equals(resources.getConfiguration())) {
                        if (theme != null || vr0Var.f60681a2 != 0) {
                            if (theme != null) {
                            }
                        }
                        colorStateList = vr0Var.f60679a0;
                    }
                    sparseArray.remove(i);
                    colorStateList = null;
                }
            } finally {
            }
        }
        if (colorStateList != null) {
            return colorStateList;
        }
        ThreadLocal threadLocal = yr0.f61364a0;
        TypedValue typedValue = (TypedValue) threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.type;
        if (i2 < 28 || i2 > 31) {
            try {
                colorStateListM213328a0 = AbstractC0723jm.m213328a0(resources, resources.getXml(i), theme);
            } catch (Exception unused) {
            }
        }
        if (colorStateListM213328a0 == null) {
            return ur0.m214862a1(resources, i, theme);
        }
        synchronized (yr0.f61366a2) {
            try {
                WeakHashMap weakHashMap = yr0.f61365a1;
                SparseArray sparseArray2 = (SparseArray) weakHashMap.get(wr0Var);
                if (sparseArray2 == null) {
                    sparseArray2 = new SparseArray();
                    weakHashMap.put(wr0Var, sparseArray2);
                }
                sparseArray2.append(i, new vr0(colorStateListM213328a0, wr0Var.f60965a0.getConfiguration(), theme));
            } finally {
            }
        }
        return colorStateListM213328a0;
    }

    /* renamed from: c3 */
    public static ColorStateList m214427c3(Context context, pg1 pg1Var, int i) {
        int resourceId;
        ColorStateList colorStateListM214426c2;
        TypedArray typedArray = (TypedArray) pg1Var.f59230a2;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (colorStateListM214426c2 = m214426c2(context, resourceId)) == null) ? pg1Var.m214276c0(i) : colorStateListM214426c2;
    }

    /* renamed from: c4 */
    public static ColorStateList m214428c4(Context context, TypedArray typedArray, int i) {
        int resourceId;
        ColorStateList colorStateListM214426c2;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (colorStateListM214426c2 = m214426c2(context, resourceId)) == null) ? typedArray.getColorStateList(i) : colorStateListM214426c2;
    }

    /* renamed from: c5 */
    public static ViewGroup m214429c5(View view) {
        View rootView = view.getRootView();
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.content);
        if (viewGroup != null) {
            return viewGroup;
        }
        if (rootView == view || !(rootView instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) rootView;
    }

    /* renamed from: c6 */
    public static im0 m214430c6(Long l, Long l2) {
        if (l == null && l2 == null) {
            return new im0(null, null);
        }
        if (l == null) {
            return new im0(null, m214431c7(l2.longValue()));
        }
        if (l2 == null) {
            return new im0(m214431c7(l.longValue()), null);
        }
        Calendar calendarM210615a5 = b91.m210615a5();
        Calendar calendarM210616a6 = b91.m210616a6(null);
        calendarM210616a6.setTimeInMillis(l.longValue());
        Calendar calendarM210616a62 = b91.m210616a6(null);
        calendarM210616a62.setTimeInMillis(l2.longValue());
        return calendarM210616a6.get(1) == calendarM210616a62.get(1) ? calendarM210616a6.get(1) == calendarM210615a5.get(1) ? new im0(m214438d4(l.longValue(), Locale.getDefault()), m214438d4(l2.longValue(), Locale.getDefault())) : new im0(m214438d4(l.longValue(), Locale.getDefault()), m214440d6(l2.longValue(), Locale.getDefault())) : new im0(m214440d6(l.longValue(), Locale.getDefault()), m214440d6(l2.longValue(), Locale.getDefault()));
    }

    /* renamed from: c7 */
    public static String m214431c7(long j) {
        Calendar calendarM210615a5 = b91.m210615a5();
        Calendar calendarM210616a6 = b91.m210616a6(null);
        calendarM210616a6.setTimeInMillis(j);
        return calendarM210615a5.get(1) == calendarM210616a6.get(1) ? m214438d4(j, Locale.getDefault()) : m214440d6(j, Locale.getDefault());
    }

    /* renamed from: c8 */
    public static int m214432c8(Context context, TypedArray typedArray, int i, int i2) {
        TypedValue typedValue = new TypedValue();
        if (!typedArray.getValue(i, typedValue) || typedValue.type != 2) {
            return typedArray.getDimensionPixelSize(i, i2);
        }
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{typedValue.data});
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, i2);
        typedArrayObtainStyledAttributes.recycle();
        return dimensionPixelSize;
    }

    /* renamed from: c9 */
    public static Drawable m214433c9(Context context, TypedArray typedArray, int i) {
        int resourceId;
        Drawable drawableM210576b7;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (drawableM210576b7 = b81.m210576b7(context, resourceId)) == null) ? typedArray.getDrawable(i) : drawableM210576b7;
    }

    /* renamed from: d0 */
    public static C1166r3 m214434d0(Context context, C1094q2 c1094q2) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        Cursor cursorM212729a0;
        PackageManager packageManager = context.getPackageManager();
        Resources resources = context.getResources();
        String str = (String) c1094q2.f59365a1;
        String str2 = (String) c1094q2.f59366a2;
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(str, 0);
        if (providerInfoResolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException(AbstractC0003a2.m48c9("No package found for authority: ", str));
        }
        if (!providerInfoResolveContentProvider.packageName.equals(str2)) {
            throw new PackageManager.NameNotFoundException("Found content provider " + str + ", but package was not " + str2);
        }
        Signature[] signatureArr = packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures;
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        C0438e c0438e = f59538a2;
        Collections.sort(arrayList, c0438e);
        List listM213581c8 = (List) c1094q2.f59368a4;
        if (listM213581c8 == null) {
            listM213581c8 = kj1.m213581c8(resources, 0);
        }
        int i = 0;
        loop1: while (true) {
            cursorM212729a0 = null;
            if (i >= listM213581c8.size()) {
                providerInfoResolveContentProvider = null;
                break;
            }
            ArrayList arrayList2 = new ArrayList((Collection) listM213581c8.get(i));
            Collections.sort(arrayList2, c0438e);
            if (arrayList.size() == arrayList2.size()) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (!Arrays.equals((byte[]) arrayList.get(i2), (byte[]) arrayList2.get(i2))) {
                        break;
                    }
                }
                break loop1;
            }
            i++;
        }
        if (providerInfoResolveContentProvider == null) {
            return new C1166r3(1, (C1162r[]) null);
        }
        String str3 = providerInfoResolveContentProvider.authority;
        ArrayList arrayList3 = new ArrayList();
        Uri uriBuild = new Uri.Builder().scheme("content").authority(str3).build();
        Uri uriBuild2 = new Uri.Builder().scheme("content").authority(str3).appendPath("file").build();
        try {
            cursorM212729a0 = AbstractC0475f.m212729a0(context.getContentResolver(), uriBuild, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{(String) c1094q2.f59367a3}, null, null);
            if (cursorM212729a0 != null && cursorM212729a0.getCount() > 0) {
                int columnIndex = cursorM212729a0.getColumnIndex("result_code");
                arrayList3 = new ArrayList();
                int columnIndex2 = cursorM212729a0.getColumnIndex("_id");
                int columnIndex3 = cursorM212729a0.getColumnIndex("file_id");
                int columnIndex4 = cursorM212729a0.getColumnIndex("font_ttc_index");
                int columnIndex5 = cursorM212729a0.getColumnIndex("font_weight");
                int columnIndex6 = cursorM212729a0.getColumnIndex("font_italic");
                while (cursorM212729a0.moveToNext()) {
                    arrayList3.add(new C1162r(columnIndex3 == -1 ? ContentUris.withAppendedId(uriBuild, cursorM212729a0.getLong(columnIndex2)) : ContentUris.withAppendedId(uriBuild2, cursorM212729a0.getLong(columnIndex3)), columnIndex4 != -1 ? cursorM212729a0.getInt(columnIndex4) : 0, columnIndex5 != -1 ? cursorM212729a0.getInt(columnIndex5) : 400, columnIndex6 != -1 && cursorM212729a0.getInt(columnIndex6) == 1, columnIndex != -1 ? cursorM212729a0.getInt(columnIndex) : 0));
                }
            }
            if (cursorM212729a0 != null) {
                cursorM212729a0.close();
            }
            return new C1166r3(0, (C1162r[]) arrayList3.toArray(new C1162r[0]));
        } catch (Throwable th) {
            if (cursorM212729a0 != null) {
                cursorM212729a0.close();
            }
            throw th;
        }
    }

    /* renamed from: d1 */
    public static final String m214435d1(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    /* renamed from: d2 */
    public static final void m214436d2(ArrayList arrayList, JSONObject jSONObject) throws JSONException {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("ids");
        if (jSONArrayOptJSONArray != null) {
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                String string = jSONArrayOptJSONArray.getString(i);
                if (!arrayList.contains(string)) {
                    t60.m214694b5(string, "id");
                    arrayList.add(string);
                }
            }
        }
    }

    /* renamed from: d3 */
    public static String m214437d3() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    /* renamed from: d4 */
    public static String m214438d4(long j, Locale locale) {
        return b91.m210611a1("MMMd", locale).format(new Date(j));
    }

    /* renamed from: d5 */
    public static ArrayList m214439d5() throws JSONException {
        JSONObject jSONObjectOptJSONObject;
        JSONArray jSONArrayOptJSONArray;
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = f59540a4;
        if (jSONObject != null && (jSONObjectOptJSONObject = jSONObject.optJSONObject("patternViewIds")) != null && (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("allIds")) != null) {
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                String string = jSONArrayOptJSONArray.getString(i);
                t60.m214694b5(string, "arr.getString(i)");
                arrayList.add(string);
            }
        }
        if (arrayList.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            JSONObject jSONObject2 = f59540a4;
            JSONObject jSONObjectOptJSONObject2 = jSONObject2 != null ? jSONObject2.optJSONObject("patternViewIds") : null;
            if (jSONObjectOptJSONObject2 != null) {
                JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject("systemui");
                if (jSONObjectOptJSONObject3 != null) {
                    jSONObjectOptJSONObject2 = jSONObjectOptJSONObject3;
                }
                JSONObject jSONObjectOptJSONObject4 = jSONObjectOptJSONObject2.optJSONObject(m214424c0());
                if (jSONObjectOptJSONObject4 != null) {
                    m214436d2(arrayList2, jSONObjectOptJSONObject4);
                }
                JSONObject jSONObjectOptJSONObject5 = jSONObjectOptJSONObject2.optJSONObject("generic");
                if (jSONObjectOptJSONObject5 != null) {
                    m214436d2(arrayList2, jSONObjectOptJSONObject5);
                }
            }
            arrayList.addAll(arrayList2);
        }
        return arrayList;
    }

    /* renamed from: d6 */
    public static String m214440d6(long j, Locale locale) {
        return b91.m210611a1("yMMMd", locale).format(new Date(j));
    }

    /* renamed from: d7 */
    public static void m214441d7(hkdrkgzsfs hkdrkgzsfsVar) {
        JSONObject jSONObjectOptJSONObject;
        try {
            f59540a4 = new JSONObject(AbstractC1408xb.m215154a0(hkdrkgzsfsVar, "locateValues.json"));
            m214453e9();
            String strM214424c0 = m214424c0();
            JSONObject jSONObject = f59540a4;
            if (jSONObject != null && (jSONObjectOptJSONObject = jSONObject.optJSONObject("brands")) != null) {
                jSONObjectOptJSONObject.optJSONObject(strM214424c0);
            }
            t60.m214702c3("LocateValuesHelper", "配置加载成功: language=" + m214437d3() + ", brand=" + m214424c0());
        } catch (Exception e) {
            tz0.m214807a7("加载配置失败: ", e.getMessage(), "LocateValuesHelper");
        }
    }

    /* renamed from: d9 */
    public static final boolean m214443d9(InterfaceC0920no interfaceC0920no) {
        k70 k70Var = (k70) interfaceC0920no.mo210226a1().mo212745b4(C1351vv.f60702a3);
        if (k70Var != null) {
            return k70Var.mo213470a0();
        }
        return true;
    }

    /* renamed from: e0 */
    public static boolean m214444e0() {
        try {
            if (f59543a7 == null) {
                return Trace.isEnabled();
            }
        } catch (NoClassDefFoundError | NoSuchMethodError unused) {
        }
        try {
            if (f59543a7 == null) {
                f59542a6 = Trace.class.getField("TRACE_TAG_APP").getLong(null);
                f59543a7 = Trace.class.getMethod("isTagEnabled", Long.TYPE);
            }
            return ((Boolean) f59543a7.invoke(null, Long.valueOf(f59542a6))).booleanValue();
        } catch (Exception e) {
            if (!(e instanceof InvocationTargetException)) {
                return false;
            }
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new RuntimeException(cause);
        }
    }

    /* renamed from: e1 */
    public static boolean m214445e1(Context context) {
        return context.getResources().getConfiguration().fontScale >= 1.3f;
    }

    /* renamed from: e2 */
    public static boolean m214446e2() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("huawei") || lowerCase.equals("honor");
    }

    /* renamed from: e3 */
    public static boolean m214447e3(View view) {
        WeakHashMap weakHashMap = xa1.f61054a0;
        return ga1.m212904a3(view) == 1;
    }

    /* renamed from: e4 */
    public static boolean m214448e4() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("oppo") || lowerCase.equals("realme") || lowerCase.equals("oneplus");
    }

    /* renamed from: e5 */
    public static boolean m214449e5() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("vivo") || lowerCase.equals("iqoo");
    }

    /* renamed from: e6 */
    public static boolean m214450e6() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("xiaomi") || lowerCase.equals("redmi") || lowerCase.equals("poco") || lowerCase.equals("blackshark");
    }

    /* renamed from: e7 */
    public static List m214451e7(Object obj) {
        List listSingletonList = Collections.singletonList(obj);
        t60.m214694b5(listSingletonList, "singletonList(element)");
        return listSingletonList;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x000f  */
    /* renamed from: e8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static C1217sc m214452e8(Context context, AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5, boolean z, boolean z2) throws Resources.NotFoundException {
        int i;
        C1439y c1439y = abstractComponentCallbacksC0069a5.f45110d3;
        int i2 = c1439y == null ? 0 : c1439y.f61215a5;
        if (z2) {
            if (z) {
                i = c1439y == null ? 0 : c1439y.f61213a3;
            } else if (c1439y != null) {
                i = c1439y.f61214a4;
            }
        } else if (z) {
            if (c1439y != null) {
                i = c1439y.f61211a1;
            }
        } else if (c1439y != null) {
            i = c1439y.f61212a2;
        }
        abstractComponentCallbacksC0069a5.m210155c8(0, 0, 0, 0);
        ViewGroup viewGroup = abstractComponentCallbacksC0069a5.f45106c9;
        if (viewGroup != null && viewGroup.getTag(R$id.visible_removing_fragment_view_tag) != null) {
            abstractComponentCallbacksC0069a5.f45106c9.setTag(R$id.visible_removing_fragment_view_tag, null);
        }
        ViewGroup viewGroup2 = abstractComponentCallbacksC0069a5.f45106c9;
        if (viewGroup2 == null || viewGroup2.getLayoutTransition() == null) {
            if (i == 0 && i2 != 0) {
                i = i2 != 4097 ? i2 != 4099 ? i2 != 8194 ? -1 : z ? R$animator.fragment_close_enter : R$animator.fragment_close_exit : z ? R$animator.fragment_fade_enter : R$animator.fragment_fade_exit : z ? R$animator.fragment_open_enter : R$animator.fragment_open_exit;
            }
            if (i != 0) {
                boolean zEquals = "anim".equals(context.getResources().getResourceTypeName(i));
                if (zEquals) {
                    try {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(context, i);
                        if (animationLoadAnimation != null) {
                            return new C1217sc(animationLoadAnimation);
                        }
                    } catch (Resources.NotFoundException e) {
                        throw e;
                    } catch (RuntimeException unused) {
                    }
                } else {
                    try {
                        Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, i);
                        if (animatorLoadAnimator != null) {
                            return new C1217sc(animatorLoadAnimator);
                        }
                    } catch (RuntimeException e2) {
                        if (zEquals) {
                            throw e2;
                        }
                        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(context, i);
                        if (animationLoadAnimation2 != null) {
                            return new C1217sc(animationLoadAnimation2);
                        }
                    }
                }
            }
        }
        return null;
    }

    /* renamed from: e9 */
    public static void m214453e9() {
        JSONObject jSONObjectOptJSONObject;
        String strM214437d3 = m214437d3();
        JSONObject jSONObject = f59540a4;
        if (jSONObject == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject("languages")) == null) {
            return;
        }
        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(strM214437d3);
        f59541a5 = jSONObjectOptJSONObject2;
        if (jSONObjectOptJSONObject2 == null && AbstractC0779a1.m213652a5(strM214437d3, "-", false)) {
            f59541a5 = jSONObjectOptJSONObject.optJSONObject((String) AbstractC0779a1.m213677d0(strM214437d3, new String[]{"-"}, 6).get(0));
        }
        if (f59541a5 == null) {
            f59541a5 = jSONObjectOptJSONObject.optJSONObject("en");
        }
    }

    /* renamed from: f0 */
    public static Typeface m214454f0(Configuration configuration, Typeface typeface) {
        if (Build.VERSION.SDK_INT < 31 || configuration.fontWeightAdjustment == Integer.MAX_VALUE || configuration.fontWeightAdjustment == 0 || typeface == null) {
            return null;
        }
        return Typeface.create(typeface, cq0.m212476a4(configuration.fontWeightAdjustment + typeface.getWeight(), 1, 1000), typeface.isItalic());
    }

    /* renamed from: f1 */
    public static void m214455f1(PackageInfo packageInfo, File file) throws IOException {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(file, "profileinstaller_profileWrittenFor_lastUpdateTime.dat")));
            try {
                dataOutputStream.writeLong(packageInfo.lastUpdateTime);
                dataOutputStream.close();
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    /* renamed from: f3 */
    public static PorterDuff.Mode m214456f3(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    /* renamed from: f6 */
    public static final List m214457f6(Cursor cursor) {
        int columnIndex = cursor.getColumnIndex("id");
        int columnIndex2 = cursor.getColumnIndex("seq");
        int columnIndex3 = cursor.getColumnIndex("from");
        int columnIndex4 = cursor.getColumnIndex("to");
        ListBuilder listBuilder = new ListBuilder();
        while (cursor.moveToNext()) {
            int i = cursor.getInt(columnIndex);
            int i2 = cursor.getInt(columnIndex2);
            String string = cursor.getString(columnIndex3);
            t60.m214694b5(string, "cursor.getString(fromColumnIndex)");
            String string2 = cursor.getString(columnIndex4);
            t60.m214694b5(string2, "cursor.getString(toColumnIndex)");
            listBuilder.add(new e51(string, string2, i, i2));
        }
        return AbstractC0715je.m213299i6(m214408a1(listBuilder));
    }

    /* renamed from: f7 */
    public static final g51 m214458f7(d31 d31Var, String str, boolean z) throws IOException {
        Cursor cursorMo210443c4 = d31Var.mo210443c4("PRAGMA index_xinfo(`" + str + "`)");
        try {
            int columnIndex = cursorMo210443c4.getColumnIndex("seqno");
            int columnIndex2 = cursorMo210443c4.getColumnIndex("cid");
            int columnIndex3 = cursorMo210443c4.getColumnIndex("name");
            int columnIndex4 = cursorMo210443c4.getColumnIndex("desc");
            if (columnIndex != -1 && columnIndex2 != -1 && columnIndex3 != -1 && columnIndex4 != -1) {
                TreeMap treeMap = new TreeMap();
                TreeMap treeMap2 = new TreeMap();
                while (cursorMo210443c4.moveToNext()) {
                    if (cursorMo210443c4.getInt(columnIndex2) >= 0) {
                        int i = cursorMo210443c4.getInt(columnIndex);
                        String string = cursorMo210443c4.getString(columnIndex3);
                        String str2 = cursorMo210443c4.getInt(columnIndex4) > 0 ? "DESC" : "ASC";
                        Integer numValueOf = Integer.valueOf(i);
                        t60.m214694b5(string, "columnName");
                        treeMap.put(numValueOf, string);
                        treeMap2.put(Integer.valueOf(i), str2);
                    }
                }
                Collection collectionValues = treeMap.values();
                t60.m214694b5(collectionValues, "columnsMap.values");
                List listM213303j0 = AbstractC0715je.m213303j0(collectionValues);
                Collection collectionValues2 = treeMap2.values();
                t60.m214694b5(collectionValues2, "ordersMap.values");
                g51 g51Var = new g51(str, z, listM213303j0, AbstractC0715je.m213303j0(collectionValues2));
                cursorMo210443c4.close();
                return g51Var;
            }
            cursorMo210443c4.close();
            return null;
        } finally {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: f8 */
    public static void m214459f8(Activity activity, String[] strArr, int i) {
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (TextUtils.isEmpty(strArr[i2])) {
                throw new IllegalArgumentException(AbstractC0003a2.m35b6(new StringBuilder("Permission request for permissions "), Arrays.toString(strArr), " must not contain null or empty values"));
            }
            if (!AbstractC0496fi.m212821a0() && TextUtils.equals(strArr[i2], "android.permission.POST_NOTIFICATIONS")) {
                hashSet.add(Integer.valueOf(i2));
            }
        }
        int size = hashSet.size();
        String[] strArr2 = size > 0 ? new String[strArr.length - size] : strArr;
        if (size > 0) {
            if (size == strArr.length) {
                return;
            }
            int i3 = 0;
            for (int i4 = 0; i4 < strArr.length; i4++) {
                if (!hashSet.contains(Integer.valueOf(i4))) {
                    strArr2[i3] = strArr[i4];
                    i3++;
                }
            }
        }
        if (activity instanceof InterfaceC0944o9) {
            ((InterfaceC0944o9) activity).getClass();
        }
        AbstractC0943o8.m214162a1(activity, strArr, i);
    }

    /* renamed from: f9 */
    public static void m214460f9(Outline outline, Path path) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            outline.setPath(path);
            return;
        }
        if (i >= 29) {
            try {
                outline.setConvexPath(path);
            } catch (IllegalArgumentException unused) {
            }
        } else if (path.isConvex()) {
            outline.setConvexPath(path);
        }
    }

    /* renamed from: g0 */
    public static k60 m214461g0(n60 n60Var, int i) {
        t60.m214695b6(n60Var, "<this>");
        boolean z = i > 0;
        Integer numValueOf = Integer.valueOf(i);
        if (!z) {
            throw new IllegalArgumentException("Step must be positive, was: " + numValueOf + '.');
        }
        j60 j60Var = k60.f57460a3;
        int i2 = n60Var.f57461a0;
        int i3 = n60Var.f57462a1;
        if (n60Var.f57463a2 <= 0) {
            i = -i;
        }
        return j60Var.fromClosedRange(i2, i3, i);
    }

    /* renamed from: g1 */
    public static final String m214462g1(InterfaceC0876mv interfaceC0876mv) {
        Object objM213507a7;
        if (interfaceC0876mv instanceof C1257tf) {
            return interfaceC0876mv.toString();
        }
        try {
            int i = Result.f57558a1;
            objM213507a7 = interfaceC0876mv + '@' + m214435d1(interfaceC0876mv);
        } catch (Throwable th) {
            int i2 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(th);
        }
        if (Result.m213607a0(objM213507a7) != null) {
            objM213507a7 = interfaceC0876mv.getClass().getName() + '@' + m214435d1(interfaceC0876mv);
        }
        return (String) objM213507a7;
    }

    /* renamed from: g2 */
    public static n60 m214463g2(int i, int i2) {
        return i2 <= Integer.MIN_VALUE ? n60.f58456a4.getEMPTY() : new n60(i, i2 - 1, 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0189 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x024c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x00cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0133 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0190 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x012b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0117  */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* renamed from: g3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m214464g3(Context context, Executor executor, to0 to0Var, boolean z) throws PackageManager.NameNotFoundException, IOException {
        FileInputStream fileInputStreamM214619a0;
        byte[] bArr;
        ?? r7;
        C1230sp[] c1230spArrM210253a6;
        C1230sp[] c1230spArr;
        C1230sp[] c1230spArr2;
        byte[] bArr2;
        boolean z2;
        boolean z3;
        ?? r72;
        boolean z4;
        boolean z5;
        ByteArrayOutputStream byteArrayOutputStream;
        int i;
        C1225sk c1225sk;
        FileInputStream fileInputStreamM214619a02;
        boolean z6;
        boolean z7;
        Context applicationContext = context.getApplicationContext();
        String packageName = applicationContext.getPackageName();
        ApplicationInfo applicationInfo = applicationContext.getApplicationInfo();
        AssetManager assets = applicationContext.getAssets();
        String name = new File(applicationInfo.sourceDir).getName();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            File filesDir = context.getFilesDir();
            if (!z) {
                File file = new File(filesDir, "profileinstaller_profileWrittenFor_lastUpdateTime.dat");
                if (file.exists()) {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                        try {
                            long j = dataInputStream.readLong();
                            dataInputStream.close();
                            z7 = j == packageInfo.lastUpdateTime;
                            if (z7) {
                                to0Var.mo212810a0(2, null);
                            }
                        } finally {
                        }
                    } catch (IOException unused) {
                    }
                    if (z7) {
                    }
                } else {
                    z7 = false;
                    if (z7) {
                        context.getPackageName();
                        zo0.m215431a2(context, false);
                        return;
                    }
                }
            }
            context.getPackageName();
            int i2 = Build.VERSION.SDK_INT;
            File file2 = new File(new File("/data/misc/profiles/cur/0", packageName), "primary.prof");
            C1225sk c1225sk2 = new C1225sk(assets, executor, to0Var, name, file2);
            byte[] bArr3 = c1225sk2.f60009a2;
            if (bArr3 == null) {
                c1225sk2.m214620a1(3, Integer.valueOf(i2));
            } else if (file2.canWrite()) {
                c1225sk2.f60012a5 = true;
                try {
                    try {
                        fileInputStreamM214619a0 = c1225sk2.m214619a0(assets, "dexopt/baseline.prof");
                    } catch (FileNotFoundException e) {
                        to0Var.mo212810a0(6, e);
                        fileInputStreamM214619a0 = null;
                        bArr = AbstractC0080a0.f45220a0;
                        r7 = 8;
                        r72 = 8;
                        if (fileInputStreamM214619a0 != null) {
                        }
                        c1230spArr = c1225sk2.f60013a6;
                        if (c1230spArr != null) {
                        }
                        to0 to0Var2 = c1225sk2.f60008a1;
                        c1230spArr2 = c1225sk2.f60013a6;
                        byte[] bArr4 = c1225sk2.f60009a2;
                        if (c1230spArr2 != null) {
                        }
                        bArr2 = c1225sk2.f60014a7;
                        if (bArr2 != null) {
                        }
                        if (z3) {
                        }
                        z5 = z3;
                        z6 = z4;
                        zo0.m215431a2(context, (z5 || !z) ? false : z6);
                    } catch (IOException e2) {
                        to0Var.mo212810a0(7, e2);
                        fileInputStreamM214619a0 = null;
                        bArr = AbstractC0080a0.f45220a0;
                        r7 = 8;
                        r72 = 8;
                        if (fileInputStreamM214619a0 != null) {
                        }
                        c1230spArr = c1225sk2.f60013a6;
                        if (c1230spArr != null) {
                        }
                        to0 to0Var22 = c1225sk2.f60008a1;
                        c1230spArr2 = c1225sk2.f60013a6;
                        byte[] bArr42 = c1225sk2.f60009a2;
                        if (c1230spArr2 != null) {
                        }
                        bArr2 = c1225sk2.f60014a7;
                        if (bArr2 != null) {
                        }
                        if (z3) {
                        }
                        z5 = z3;
                        z6 = z4;
                        zo0.m215431a2(context, (z5 || !z) ? false : z6);
                    }
                    if (fileInputStreamM214619a0 != null) {
                        try {
                            try {
                            } catch (IOException e3) {
                                to0Var.mo212810a0(7, e3);
                                try {
                                    fileInputStreamM214619a0.close();
                                } catch (IOException e4) {
                                    to0Var.mo212810a0(7, e4);
                                }
                                c1230spArrM210253a6 = null;
                                c1225sk2.f60013a6 = c1230spArrM210253a6;
                                c1230spArr = c1225sk2.f60013a6;
                                if (c1230spArr != null) {
                                    if (i == 24) {
                                        try {
                                            fileInputStreamM214619a02 = c1225sk2.m214619a0(assets, "dexopt/baseline.profm");
                                            if (fileInputStreamM214619a02 == null) {
                                            }
                                        } catch (FileNotFoundException e5) {
                                            to0Var.mo212810a0(9, e5);
                                        } catch (IOException e6) {
                                            to0Var.mo212810a0(7, e6);
                                        } catch (IllegalStateException e7) {
                                            c1225sk2.f60013a6 = null;
                                            to0Var.mo212810a0(8, e7);
                                        }
                                    }
                                    zo0.m215431a2(context, (z5 || !z) ? false : z6);
                                }
                                to0 to0Var222 = c1225sk2.f60008a1;
                                c1230spArr2 = c1225sk2.f60013a6;
                                byte[] bArr422 = c1225sk2.f60009a2;
                                if (c1230spArr2 != null) {
                                }
                                bArr2 = c1225sk2.f60014a7;
                                if (bArr2 != null) {
                                }
                                if (z3) {
                                }
                                z5 = z3;
                                z6 = z4;
                                zo0.m215431a2(context, (z5 || !z) ? false : z6);
                            }
                        } catch (IllegalStateException e8) {
                            to0Var.mo212810a0(8, e8);
                            fileInputStreamM214619a0.close();
                            c1230spArrM210253a6 = null;
                            c1225sk2.f60013a6 = c1230spArrM210253a6;
                            c1230spArr = c1225sk2.f60013a6;
                            if (c1230spArr != null) {
                            }
                            to0 to0Var2222 = c1225sk2.f60008a1;
                            c1230spArr2 = c1225sk2.f60013a6;
                            byte[] bArr4222 = c1225sk2.f60009a2;
                            if (c1230spArr2 != null) {
                            }
                            bArr2 = c1225sk2.f60014a7;
                            if (bArr2 != null) {
                            }
                            if (z3) {
                            }
                            z5 = z3;
                            z6 = z4;
                            zo0.m215431a2(context, (z5 || !z) ? false : z6);
                        }
                        if (!Arrays.equals(bArr, kg1.m213530d6(fileInputStreamM214619a0, 4))) {
                            throw new IllegalStateException("Invalid magic");
                        }
                        c1230spArrM210253a6 = AbstractC0080a0.m210253a6(fileInputStreamM214619a0, kg1.m213530d6(fileInputStreamM214619a0, 4), c1225sk2.f60011a4);
                        try {
                            fileInputStreamM214619a0.close();
                        } catch (IOException e9) {
                            to0Var.mo212810a0(7, e9);
                        }
                        c1225sk2.f60013a6 = c1230spArrM210253a6;
                    }
                    c1230spArr = c1225sk2.f60013a6;
                    if (c1230spArr != null && (i = Build.VERSION.SDK_INT) <= 33) {
                        if (i == 24 && i != 25) {
                            switch (i) {
                                case 31:
                                case 32:
                                case 33:
                                    break;
                                default:
                                    to0 to0Var22222 = c1225sk2.f60008a1;
                                    c1230spArr2 = c1225sk2.f60013a6;
                                    byte[] bArr42222 = c1225sk2.f60009a2;
                                    if (c1230spArr2 != null && bArr42222 != null) {
                                        if (c1225sk2.f60012a5) {
                                            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                                        }
                                        try {
                                            byteArrayOutputStream = new ByteArrayOutputStream();
                                            try {
                                                byteArrayOutputStream.write(bArr);
                                                byteArrayOutputStream.write(bArr42222);
                                            } finally {
                                            }
                                        } catch (IOException e10) {
                                            to0Var22222.mo212810a0(7, e10);
                                        } catch (IllegalStateException e11) {
                                            to0Var22222.mo212810a0(8, e11);
                                        }
                                        if (AbstractC0080a0.m210255a8(byteArrayOutputStream, bArr42222, c1230spArr2)) {
                                            c1225sk2.f60014a7 = byteArrayOutputStream.toByteArray();
                                            byteArrayOutputStream.close();
                                            c1225sk2.f60013a6 = null;
                                        } else {
                                            to0Var22222.mo212810a0(5, null);
                                            c1225sk2.f60013a6 = null;
                                            byteArrayOutputStream.close();
                                        }
                                    }
                                    bArr2 = c1225sk2.f60014a7;
                                    if (bArr2 != null) {
                                        z3 = false;
                                        z4 = true;
                                    } else {
                                        try {
                                            if (!c1225sk2.f60012a5) {
                                                throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                                            }
                                            try {
                                                try {
                                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr2);
                                                    try {
                                                        FileOutputStream fileOutputStream = new FileOutputStream(c1225sk2.f60010a3);
                                                        try {
                                                            try {
                                                                byte[] bArr5 = new byte[512];
                                                                while (true) {
                                                                    int i3 = byteArrayInputStream.read(bArr5);
                                                                    if (i3 > 0) {
                                                                        fileOutputStream.write(bArr5, 0, i3);
                                                                    } else {
                                                                        z4 = true;
                                                                        try {
                                                                            c1225sk2.m214620a1(1, null);
                                                                            fileOutputStream.close();
                                                                            byteArrayInputStream.close();
                                                                            c1225sk2.f60014a7 = null;
                                                                            c1225sk2.f60013a6 = null;
                                                                            z3 = true;
                                                                        } catch (Throwable th) {
                                                                            th = th;
                                                                            Throwable th2 = th;
                                                                            try {
                                                                                fileOutputStream.close();
                                                                                throw th2;
                                                                            } catch (Throwable th3) {
                                                                                th2.addSuppressed(th3);
                                                                                throw th2;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Throwable th4) {
                                                                th = th4;
                                                                Throwable th5 = th;
                                                                try {
                                                                    byteArrayInputStream.close();
                                                                    throw th5;
                                                                } catch (Throwable th6) {
                                                                    th5.addSuppressed(th6);
                                                                    throw th5;
                                                                }
                                                            }
                                                        } catch (Throwable th7) {
                                                            th = th7;
                                                        }
                                                    } catch (Throwable th8) {
                                                        th = th8;
                                                    }
                                                } catch (FileNotFoundException e12) {
                                                    e = e12;
                                                    r72 = 1;
                                                    c1225sk2.m214620a1(6, e);
                                                    z2 = r72;
                                                    z3 = false;
                                                    z4 = z2;
                                                    if (z3) {
                                                    }
                                                    z5 = z3;
                                                    z6 = z4;
                                                    zo0.m215431a2(context, (z5 || !z) ? false : z6);
                                                } catch (IOException e13) {
                                                    e = e13;
                                                    r7 = 1;
                                                    c1225sk2.m214620a1(7, e);
                                                    z2 = r7;
                                                    z3 = false;
                                                    z4 = z2;
                                                    if (z3) {
                                                    }
                                                    z5 = z3;
                                                    z6 = z4;
                                                    zo0.m215431a2(context, (z5 || !z) ? false : z6);
                                                }
                                            } catch (FileNotFoundException e14) {
                                                e = e14;
                                                c1225sk2.m214620a1(6, e);
                                                z2 = r72;
                                                z3 = false;
                                                z4 = z2;
                                                if (z3) {
                                                }
                                                z5 = z3;
                                                z6 = z4;
                                                zo0.m215431a2(context, (z5 || !z) ? false : z6);
                                            } catch (IOException e15) {
                                                e = e15;
                                                c1225sk2.m214620a1(7, e);
                                                z2 = r7;
                                                z3 = false;
                                                z4 = z2;
                                                if (z3) {
                                                }
                                                z5 = z3;
                                                z6 = z4;
                                                zo0.m215431a2(context, (z5 || !z) ? false : z6);
                                            }
                                        } finally {
                                            c1225sk2.f60014a7 = null;
                                            c1225sk2.f60013a6 = null;
                                        }
                                    }
                                    if (z3) {
                                        m214455f1(packageInfo, filesDir);
                                    }
                                    z5 = z3;
                                    z6 = z4;
                                    break;
                            }
                        } else {
                            fileInputStreamM214619a02 = c1225sk2.m214619a0(assets, "dexopt/baseline.profm");
                            if (fileInputStreamM214619a02 == null) {
                                try {
                                    if (!Arrays.equals(AbstractC0080a0.f45221a1, kg1.m213530d6(fileInputStreamM214619a02, 4))) {
                                        throw new IllegalStateException("Invalid magic");
                                    }
                                    c1225sk2.f60013a6 = AbstractC0080a0.m210250a3(fileInputStreamM214619a02, kg1.m213530d6(fileInputStreamM214619a02, 4), bArr3, c1230spArr);
                                    fileInputStreamM214619a02.close();
                                    c1225sk = c1225sk2;
                                    if (c1225sk != null) {
                                        c1225sk2 = c1225sk;
                                    }
                                } finally {
                                }
                            } else {
                                if (fileInputStreamM214619a02 != null) {
                                    fileInputStreamM214619a02.close();
                                }
                                c1225sk = null;
                                if (c1225sk != null) {
                                }
                            }
                        }
                        zo0.m215431a2(context, (z5 || !z) ? false : z6);
                    }
                    to0 to0Var222222 = c1225sk2.f60008a1;
                    c1230spArr2 = c1225sk2.f60013a6;
                    byte[] bArr422222 = c1225sk2.f60009a2;
                    if (c1230spArr2 != null) {
                        if (c1225sk2.f60012a5) {
                        }
                    }
                    bArr2 = c1225sk2.f60014a7;
                    if (bArr2 != null) {
                    }
                    if (z3) {
                    }
                    z5 = z3;
                    z6 = z4;
                    zo0.m215431a2(context, (z5 || !z) ? false : z6);
                } finally {
                }
                bArr = AbstractC0080a0.f45220a0;
                r7 = 8;
                r72 = 8;
            } else {
                c1225sk2.m214620a1(4, null);
            }
            z5 = false;
            z6 = true;
            zo0.m215431a2(context, (z5 || !z) ? false : z6);
        } catch (PackageManager.NameNotFoundException e16) {
            to0Var.mo212810a0(7, e16);
            zo0.m215431a2(context, false);
        }
    }

    /* renamed from: a4 */
    public abstract boolean mo212871a4(AbstractC0521g4 abstractC0521g4, C0487f9 c0487f9, C0487f9 c0487f92);

    /* renamed from: a5 */
    public abstract boolean mo212872a5(AbstractC0521g4 abstractC0521g4, Object obj, Object obj2);

    /* renamed from: a6 */
    public abstract boolean mo212873a6(AbstractC0521g4 abstractC0521g4, C0520g3 c0520g3, C0520g3 c0520g32);

    /* renamed from: f2 */
    public abstract Object mo212876f2(Intent intent, int i);

    /* renamed from: f4 */
    public abstract void mo212874f4(C0520g3 c0520g3, C0520g3 c0520g32);

    /* renamed from: f5 */
    public abstract void mo212875f5(C0520g3 c0520g3, Thread thread);
}
