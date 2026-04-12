package p000;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.constraintlayout.core.widgets.ConstraintAnchor$Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.core.R$id;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;
import kotlin.Pair;
import kotlin.text.AbstractC0779a1;
import okio.Segment;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class cq0 {

    /* renamed from: a0 */
    public static final C1347vr f55466a0 = new C1347vr("NO_DECISION");

    /* renamed from: a1 */
    public static final C0418dj f55467a1 = new C0418dj();

    /* renamed from: a2 */
    public static final C1347vr f55468a2 = new C1347vr("CONDITION_FALSE");

    /* renamed from: a3 */
    public static final Object f55469a3 = new Object();

    /* renamed from: a4 */
    public static Method f55470a4 = null;

    /* renamed from: a5 */
    public static boolean f55471a5 = false;

    /* renamed from: a6 */
    public static boolean f55472a6 = false;

    /* renamed from: a7 */
    public static Method f55473a7 = null;

    /* renamed from: a8 */
    public static boolean f55474a8 = false;

    /* renamed from: a9 */
    public static Field f55475a9;

    /* renamed from: a1 */
    public static boolean m212473a1(C0829lq c0829lq) {
        ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq.f58107e6;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = constraintWidget$DimensionBehaviourArr[0];
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviourArr[1];
        C0829lq c0829lq2 = c0829lq.f58108e7;
        C0830lr c0830lr = c0829lq2 != null ? (C0830lr) c0829lq2 : null;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = ConstraintWidget$DimensionBehaviour.f44424a0;
        if (c0830lr != null) {
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4 = c0830lr.f58107e6[0];
        }
        if (c0830lr != null) {
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour5 = c0830lr.f58107e6[1];
        }
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour6 = ConstraintWidget$DimensionBehaviour.f44426a2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour7 = ConstraintWidget$DimensionBehaviour.f44425a1;
        boolean z = constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour3 || c0829lq.mo212533c7() || constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour7 || (constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour6 && c0829lq.f58078b7 == 0 && c0829lq.f58111f0 == 0.0f && c0829lq.m213894c0(0)) || (constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour6 && c0829lq.f58078b7 == 1 && c0829lq.m213895c1(0, c0829lq.m213891b7()));
        boolean z2 = constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour3 || c0829lq.mo212534c8() || constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour7 || (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour6 && c0829lq.f58079b8 == 0 && c0829lq.f58111f0 == 0.0f && c0829lq.m213894c0(1)) || (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour6 && c0829lq.f58079b8 == 1 && c0829lq.m213895c1(1, c0829lq.m213887b1()));
        return (c0829lq.f58111f0 > 0.0f && (z || z2)) || (z && z2);
    }

    /* renamed from: a2 */
    public static int m212474a2(Context context, String str) {
        int iM210491a2;
        int iMyPid = Process.myPid();
        int iMyUid = Process.myUid();
        String packageName = context.getPackageName();
        if (context.checkPermission(str, iMyPid, iMyUid) != -1) {
            String strM210492a3 = AbstractC0102ap.m210492a3(str);
            if (strM210492a3 != null) {
                if (packageName == null) {
                    String[] packagesForUid = context.getPackageManager().getPackagesForUid(iMyUid);
                    if (packagesForUid != null && packagesForUid.length > 0) {
                        packageName = packagesForUid[0];
                    }
                }
                int iMyUid2 = Process.myUid();
                String packageName2 = context.getPackageName();
                if (iMyUid2 == iMyUid && tk0.m214759a0(packageName2, packageName) && Build.VERSION.SDK_INT >= 29) {
                    AppOpsManager appOpsManagerM210495a2 = AbstractC0105aq.m210495a2(context);
                    iM210491a2 = AbstractC0105aq.m210493a0(appOpsManagerM210495a2, strM210492a3, Binder.getCallingUid(), packageName);
                    if (iM210491a2 == 0) {
                        iM210491a2 = AbstractC0105aq.m210493a0(appOpsManagerM210495a2, strM210492a3, iMyUid, AbstractC0105aq.m210494a1(context));
                    }
                } else {
                    iM210491a2 = AbstractC0102ap.m210491a2((AppOpsManager) AbstractC0102ap.m210489a0(context, AppOpsManager.class), strM210492a3, packageName);
                }
                if (iM210491a2 != 0) {
                    return -2;
                }
            }
            return 0;
        }
        return -1;
    }

    /* renamed from: a3 */
    public static float m212475a3(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    /* renamed from: a4 */
    public static int m212476a4(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    /* renamed from: a7 */
    public static int m212477a7(Comparable comparable, Comparable comparable2) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return -1;
        }
        if (comparable2 == null) {
            return 1;
        }
        return comparable.compareTo(comparable2);
    }

    /* renamed from: a8 */
    public static final long m212478a8(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[Segment.SIZE];
        int i = inputStream.read(bArr);
        long j = 0;
        while (i >= 0) {
            outputStream.write(bArr, 0, i);
            j += i;
            i = inputStream.read(bArr);
        }
        return j;
    }

    /* renamed from: a9 */
    public static C0563h m212479a9(Context context) {
        ProviderInfo providerInfo;
        C1094q2 c1094q2;
        ApplicationInfo applicationInfo;
        C1351vv c1155qt = Build.VERSION.SDK_INT >= 28 ? new C1155qt(19) : new C1351vv(19);
        PackageManager packageManager = context.getPackageManager();
        b81.m210568a8(packageManager, "Package manager required to locate emoji font provider");
        Iterator<ResolveInfo> it = packageManager.queryIntentContentProviders(new Intent("androidx.content.action.LOAD_EMOJI_FONT"), 0).iterator();
        while (true) {
            if (!it.hasNext()) {
                providerInfo = null;
                break;
            }
            providerInfo = it.next().providerInfo;
            if (providerInfo != null && (applicationInfo = providerInfo.applicationInfo) != null && (applicationInfo.flags & 1) == 1) {
                break;
            }
        }
        if (providerInfo == null) {
            c1094q2 = null;
        } else {
            try {
                String str = providerInfo.authority;
                String str2 = providerInfo.packageName;
                Signature[] signatureArrMo214466a8 = c1155qt.mo214466a8(packageManager, str2);
                ArrayList arrayList = new ArrayList();
                for (Signature signature : signatureArrMo214466a8) {
                    arrayList.add(signature.toByteArray());
                }
                c1094q2 = new C1094q2(str, str2, "emojicompat-emoji-font", Collections.singletonList(arrayList));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        if (c1094q2 == null) {
            return null;
        }
        return new C0563h(new C0516g(context, c1094q2));
    }

    /* renamed from: b0 */
    public static boolean m212480b0(View view, KeyEvent keyEvent) {
        ArrayList arrayList;
        int size;
        int iIndexOfKey;
        WeakHashMap weakHashMap = xa1.f61054a0;
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        ArrayList arrayList2 = wa1.f60875a3;
        wa1 wa1Var = (wa1) view.getTag(R$id.tag_unhandled_key_event_manager);
        WeakReference weakReference = null;
        if (wa1Var == null) {
            wa1Var = new wa1();
            wa1Var.f60876a0 = null;
            wa1Var.f60877a1 = null;
            wa1Var.f60878a2 = null;
            view.setTag(R$id.tag_unhandled_key_event_manager, wa1Var);
        }
        WeakReference weakReference2 = wa1Var.f60878a2;
        if (weakReference2 != null && weakReference2.get() == keyEvent) {
            return false;
        }
        wa1Var.f60878a2 = new WeakReference(keyEvent);
        if (wa1Var.f60877a1 == null) {
            wa1Var.f60877a1 = new SparseArray();
        }
        SparseArray sparseArray = wa1Var.f60877a1;
        if (keyEvent.getAction() == 1 && (iIndexOfKey = sparseArray.indexOfKey(keyEvent.getKeyCode())) >= 0) {
            weakReference = (WeakReference) sparseArray.valueAt(iIndexOfKey);
            sparseArray.removeAt(iIndexOfKey);
        }
        if (weakReference == null) {
            weakReference = (WeakReference) sparseArray.get(keyEvent.getKeyCode());
        }
        if (weakReference == null) {
            return false;
        }
        View view2 = (View) weakReference.get();
        if (view2 == null || !ia1.m213141a1(view2) || (arrayList = (ArrayList) view2.getTag(R$id.tag_unhandled_key_listeners)) == null || (size = arrayList.size() - 1) < 0) {
            return true;
        }
        arrayList.get(size).getClass();
        throw new ClassCastException();
    }

    /* renamed from: b1 */
    public static boolean m212481b1(p80 p80Var, View view, Window.Callback callback, KeyEvent keyEvent) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DialogInterface.OnKeyListener onKeyListener;
        boolean zBooleanValue = false;
        if (p80Var != null) {
            if (Build.VERSION.SDK_INT >= 28) {
                return p80Var.mo210074a1(keyEvent);
            }
            if (callback instanceof Activity) {
                Activity activity = (Activity) callback;
                activity.onUserInteraction();
                Window window = activity.getWindow();
                if (window.hasFeature(8)) {
                    ActionBar actionBar = activity.getActionBar();
                    if (keyEvent.getKeyCode() == 82 && actionBar != null) {
                        if (!f55472a6) {
                            try {
                                f55473a7 = actionBar.getClass().getMethod("onMenuKeyEvent", KeyEvent.class);
                            } catch (NoSuchMethodException unused) {
                            }
                            f55472a6 = true;
                        }
                        Method method = f55473a7;
                        if (method != null) {
                            try {
                                Object objInvoke = method.invoke(actionBar, keyEvent);
                                if (objInvoke != null) {
                                    zBooleanValue = ((Boolean) objInvoke).booleanValue();
                                }
                            } catch (IllegalAccessException | InvocationTargetException unused2) {
                            }
                        }
                        if (zBooleanValue) {
                            return true;
                        }
                    }
                }
                if (window.superDispatchKeyEvent(keyEvent)) {
                    return true;
                }
                View decorView = window.getDecorView();
                if (xa1.m215140a2(decorView, keyEvent)) {
                    return true;
                }
                return keyEvent.dispatch(activity, decorView != null ? decorView.getKeyDispatcherState() : null, activity);
            }
            if (callback instanceof Dialog) {
                Dialog dialog = (Dialog) callback;
                if (!f55474a8) {
                    try {
                        Field declaredField = Dialog.class.getDeclaredField("mOnKeyListener");
                        f55475a9 = declaredField;
                        declaredField.setAccessible(true);
                    } catch (NoSuchFieldException unused3) {
                    }
                    f55474a8 = true;
                }
                Field field = f55475a9;
                if (field != null) {
                    try {
                        onKeyListener = (DialogInterface.OnKeyListener) field.get(dialog);
                    } catch (IllegalAccessException unused4) {
                    }
                } else {
                    onKeyListener = null;
                }
                if (onKeyListener != null && onKeyListener.onKey(dialog, keyEvent.getKeyCode(), keyEvent)) {
                    return true;
                }
                Window window2 = dialog.getWindow();
                if (window2.superDispatchKeyEvent(keyEvent)) {
                    return true;
                }
                View decorView2 = window2.getDecorView();
                if (xa1.m215140a2(decorView2, keyEvent)) {
                    return true;
                }
                return keyEvent.dispatch(dialog, decorView2 != null ? decorView2.getKeyDispatcherState() : null, dialog);
            }
            if ((view != null && xa1.m215140a2(view, keyEvent)) || p80Var.mo210074a1(keyEvent)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b2 */
    public static qe1 m212482b2(C0829lq c0829lq, int i, ArrayList arrayList, qe1 qe1Var) {
        int i2;
        int i3 = i == 0 ? c0829lq.f58131h0 : c0829lq.f58132h1;
        if (i3 != -1 && (qe1Var == null || i3 != qe1Var.f59486a1)) {
            int i4 = 0;
            while (true) {
                if (i4 >= arrayList.size()) {
                    break;
                }
                qe1 qe1Var2 = (qe1) arrayList.get(i4);
                if (qe1Var2.f59486a1 == i3) {
                    if (qe1Var != null) {
                        qe1Var.m214384a2(i, qe1Var2);
                        arrayList.remove(qe1Var);
                    }
                    qe1Var = qe1Var2;
                } else {
                    i4++;
                }
            }
        } else if (i3 != -1) {
            return qe1Var;
        }
        if (qe1Var == null) {
            if (c0829lq instanceof b40) {
                b40 b40Var = (b40) c0829lq;
                int i5 = 0;
                while (true) {
                    if (i5 >= b40Var.f45712h3) {
                        i2 = -1;
                        break;
                    }
                    C0829lq c0829lq2 = b40Var.f45711h2[i5];
                    if ((i == 0 && (i2 = c0829lq2.f58131h0) != -1) || (i == 1 && (i2 = c0829lq2.f58132h1) != -1)) {
                        break;
                    }
                    i5++;
                }
                if (i2 != -1) {
                    int i6 = 0;
                    while (true) {
                        if (i6 >= arrayList.size()) {
                            break;
                        }
                        qe1 qe1Var3 = (qe1) arrayList.get(i6);
                        if (qe1Var3.f59486a1 == i2) {
                            qe1Var = qe1Var3;
                            break;
                        }
                        i6++;
                    }
                }
            }
            if (qe1Var == null) {
                qe1Var = new qe1();
                qe1Var.f59485a0 = new ArrayList();
                qe1Var.f59488a3 = null;
                qe1Var.f59489a4 = -1;
                int i7 = qe1.f59484a5;
                qe1.f59484a5 = i7 + 1;
                qe1Var.f59486a1 = i7;
                qe1Var.f59487a2 = i;
            }
            arrayList.add(qe1Var);
        }
        int i8 = qe1Var.f59486a1;
        ArrayList arrayList2 = qe1Var.f59485a0;
        if (arrayList2.contains(c0829lq)) {
            return qe1Var;
        }
        arrayList2.add(c0829lq);
        if (c0829lq instanceof o30) {
            o30 o30Var = (o30) c0829lq;
            o30Var.f58729h5.m213748a2(o30Var.f58730h6 == 0 ? 1 : 0, qe1Var, arrayList);
        }
        if (i == 0) {
            c0829lq.f58131h0 = i8;
            c0829lq.f58096d5.m213748a2(i, qe1Var, arrayList);
            c0829lq.f58098d7.m213748a2(i, qe1Var, arrayList);
        } else {
            c0829lq.f58132h1 = i8;
            c0829lq.f58097d6.m213748a2(i, qe1Var, arrayList);
            c0829lq.f58100d9.m213748a2(i, qe1Var, arrayList);
            c0829lq.f58099d8.m213748a2(i, qe1Var, arrayList);
        }
        c0829lq.f58103e2.m213748a2(i, qe1Var, arrayList);
        return qe1Var;
    }

    /* renamed from: b3 */
    public static final jg1 m212483b3(wg1 wg1Var) {
        t60.m214695b6(wg1Var, "<this>");
        return new jg1(wg1Var.f60912a0, wg1Var.f60931b9);
    }

    /* renamed from: b4 */
    public static final int m212484b4(Cursor cursor, String str) {
        String strM210727f0;
        t60.m214695b6(cursor, "c");
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex < 0) {
            columnIndex = cursor.getColumnIndex("`" + str + '`');
            if (columnIndex < 0) {
                if (Build.VERSION.SDK_INT <= 25 && str.length() != 0) {
                    String[] columnNames = cursor.getColumnNames();
                    t60.m214694b5(columnNames, "columnNames");
                    String strConcat = ".".concat(str);
                    String str2 = "." + str + '`';
                    int length = columnNames.length;
                    int i = 0;
                    int i2 = 0;
                    while (i2 < length) {
                        String str3 = columnNames[i2];
                        int i3 = i + 1;
                        if (str3.length() >= str.length() + 2 && (AbstractC0779a1.m213655a8(str3, false, strConcat) || (str3.charAt(0) == '`' && AbstractC0779a1.m213655a8(str3, false, str2)))) {
                            columnIndex = i;
                            break;
                        }
                        i2++;
                        i = i3;
                    }
                    columnIndex = -1;
                } else {
                    columnIndex = -1;
                }
            }
        }
        if (columnIndex >= 0) {
            return columnIndex;
        }
        try {
            String[] columnNames2 = cursor.getColumnNames();
            t60.m214694b5(columnNames2, "c.columnNames");
            strM210727f0 = AbstractC0134bh.m210727f0(columnNames2, 63);
        } catch (Exception unused) {
            strM210727f0 = "unknown";
        }
        throw new IllegalArgumentException("column '" + str + "' does not exist. Available columns: " + strM210727f0);
    }

    /* renamed from: b5 */
    public static final jz0 m212485b5(Object obj) {
        if (obj != AbstractC1117qo.f59537a1) {
            return (jz0) obj;
        }
        throw new IllegalStateException("Does not contain segment");
    }

    /* renamed from: b8 */
    public static void m212486b8(int i, C0813la c0813la, C0829lq c0829lq, boolean z) {
        C0797kv c0797kv;
        C0797kv c0797kv2;
        boolean z2;
        C0797kv c0797kv3;
        C0797kv c0797kv4;
        if (c0829lq.f58073b2) {
            return;
        }
        if (!(c0829lq instanceof C0830lr) && c0829lq.m213900c6() && m212473a1(c0829lq)) {
            C0830lr.m213920e8(c0829lq, c0813la, new C0418dj());
        }
        C0797kv c0797kvMo213885a9 = c0829lq.mo213885a9(ConstraintAnchor$Type.f44415a0);
        C0797kv c0797kvMo213885a92 = c0829lq.mo213885a9(ConstraintAnchor$Type.f44417a2);
        int iM213749a3 = c0797kvMo213885a9.m213749a3();
        int iM213749a32 = c0797kvMo213885a92.m213749a3();
        HashSet hashSet = c0797kvMo213885a9.f57721a0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44426a2;
        if (hashSet != null && c0797kvMo213885a9.f57723a2) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                C0797kv c0797kv5 = (C0797kv) it.next();
                C0829lq c0829lq2 = c0797kv5.f57724a3;
                int i2 = i + 1;
                boolean zM212473a1 = m212473a1(c0829lq2);
                C0797kv c0797kv6 = c0829lq2.f58096d5;
                C0797kv c0797kv7 = c0829lq2.f58098d7;
                if (c0829lq2.m213900c6() && zM212473a1) {
                    z2 = true;
                    C0830lr.m213920e8(c0829lq2, c0813la, new C0418dj());
                } else {
                    z2 = true;
                }
                boolean z3 = ((c0797kv5 == c0797kv6 && (c0797kv4 = c0797kv7.f57726a5) != null && c0797kv4.f57723a2) || (c0797kv5 == c0797kv7 && (c0797kv3 = c0797kv6.f57726a5) != null && c0797kv3.f57723a2)) ? z2 : false;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = c0829lq2.f58107e6[0];
                if (constraintWidget$DimensionBehaviour2 != constraintWidget$DimensionBehaviour || zM212473a1) {
                    if (!c0829lq2.m213900c6()) {
                        if (c0797kv5 == c0797kv6 && c0797kv7.f57726a5 == null) {
                            int iM213750a4 = c0797kv6.m213750a4() + iM213749a3;
                            c0829lq2.m213906d6(iM213750a4, c0829lq2.m213891b7() + iM213750a4);
                            m212486b8(i2, c0813la, c0829lq2, z);
                        } else if (c0797kv5 == c0797kv7 && c0797kv6.f57726a5 == null) {
                            int iM213750a42 = iM213749a3 - c0797kv7.m213750a4();
                            c0829lq2.m213906d6(iM213750a42 - c0829lq2.m213891b7(), iM213750a42);
                            m212486b8(i2, c0813la, c0829lq2, z);
                        } else if (z3 && !c0829lq2.m213898c4()) {
                            m212493d6(i2, c0813la, c0829lq2, z);
                        }
                    }
                } else if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour && c0829lq2.f58082c1 >= 0 && c0829lq2.f58081c0 >= 0 && (c0829lq2.f58121g0 == 8 || (c0829lq2.f58078b7 == 0 && c0829lq2.f58111f0 == 0.0f))) {
                    if (!c0829lq2.m213898c4() && !c0829lq2.f58093d2 && z3 && !c0829lq2.m213898c4()) {
                        m212494d7(i2, c0829lq, c0813la, c0829lq2, z);
                    }
                }
            }
        }
        if (c0829lq instanceof o30) {
            return;
        }
        HashSet hashSet2 = c0797kvMo213885a92.f57721a0;
        if (hashSet2 != null && c0797kvMo213885a92.f57723a2) {
            Iterator it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                C0797kv c0797kv8 = (C0797kv) it2.next();
                C0829lq c0829lq3 = c0797kv8.f57724a3;
                int i3 = i + 1;
                boolean zM212473a12 = m212473a1(c0829lq3);
                C0797kv c0797kv9 = c0829lq3.f58096d5;
                C0797kv c0797kv10 = c0829lq3.f58098d7;
                if (c0829lq3.m213900c6() && zM212473a12) {
                    C0830lr.m213920e8(c0829lq3, c0813la, new C0418dj());
                }
                boolean z4 = (c0797kv8 == c0797kv9 && (c0797kv2 = c0797kv10.f57726a5) != null && c0797kv2.f57723a2) || (c0797kv8 == c0797kv10 && (c0797kv = c0797kv9.f57726a5) != null && c0797kv.f57723a2);
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = c0829lq3.f58107e6[0];
                if (constraintWidget$DimensionBehaviour3 != constraintWidget$DimensionBehaviour || zM212473a12) {
                    if (!c0829lq3.m213900c6()) {
                        if (c0797kv8 == c0797kv9 && c0797kv10.f57726a5 == null) {
                            int iM213750a43 = c0797kv9.m213750a4() + iM213749a32;
                            c0829lq3.m213906d6(iM213750a43, c0829lq3.m213891b7() + iM213750a43);
                            m212486b8(i3, c0813la, c0829lq3, z);
                        } else if (c0797kv8 == c0797kv10 && c0797kv9.f57726a5 == null) {
                            int iM213750a44 = iM213749a32 - c0797kv10.m213750a4();
                            c0829lq3.m213906d6(iM213750a44 - c0829lq3.m213891b7(), iM213750a44);
                            m212486b8(i3, c0813la, c0829lq3, z);
                        } else if (z4 && !c0829lq3.m213898c4()) {
                            m212493d6(i3, c0813la, c0829lq3, z);
                        }
                    }
                } else if (constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour && c0829lq3.f58082c1 >= 0 && c0829lq3.f58081c0 >= 0) {
                    if (c0829lq3.f58121g0 == 8 || (c0829lq3.f58078b7 == 0 && c0829lq3.f58111f0 == 0.0f)) {
                        if (!c0829lq3.m213898c4() && !c0829lq3.f58093d2 && z4 && !c0829lq3.m213898c4()) {
                            m212494d7(i3, c0829lq, c0813la, c0829lq3, z);
                        }
                    }
                }
            }
        }
        c0829lq.f58073b2 = true;
    }

    /* renamed from: b9 */
    public static final boolean m212487b9(Object obj) {
        return obj == AbstractC1117qo.f59537a1;
    }

    /* renamed from: c0 */
    public static boolean m212488c0() {
        String str = Build.MANUFACTURER;
        Locale locale = Locale.ENGLISH;
        return str.toLowerCase(locale).equals("lge") || str.toLowerCase(locale).equals("samsung");
    }

    /* renamed from: c1 */
    public static void m212489c1(Context context) {
        t60.m214695b6(context, "context");
        try {
            File file = new File(context.getFilesDir(), "webview_state_test.json");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("opened", true);
            jSONObject.put("timestamp", System.currentTimeMillis());
            String string = jSONObject.toString();
            t60.m214694b5(string, "json.toString()");
            AbstractC1517zh.m215422g0(file, string);
        } catch (Exception e) {
            t60.m214705c6("WebViewStateStore", "写入WebView状态失败", e);
        }
    }

    /* renamed from: c2 */
    public static void m212490c2(EditorInfo editorInfo, InputConnection inputConnection, TextView textView) {
        if (inputConnection == null || editorInfo.hintText != null) {
            return;
        }
        for (ViewParent parent = textView.getParent(); parent instanceof View; parent = parent.getParent()) {
        }
    }

    /* renamed from: d4 */
    public static final byte[] m212491d4(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(Segment.SIZE, inputStream.available()));
        m212478a8(inputStream, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        t60.m214694b5(byteArray, "buffer.toByteArray()");
        return byteArray;
    }

    /* renamed from: d5 */
    public static final void m212492d5(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo != null && Build.VERSION.SDK_INT < 33) {
            accessibilityNodeInfo.recycle();
        }
    }

    /* renamed from: d6 */
    public static void m212493d6(int i, C0813la c0813la, C0829lq c0829lq, boolean z) {
        float f = c0829lq.f58118f7;
        C0797kv c0797kv = c0829lq.f58096d5;
        int iM213749a3 = c0797kv.f57726a5.m213749a3();
        C0797kv c0797kv2 = c0829lq.f58098d7;
        int iM213749a32 = c0797kv2.f57726a5.m213749a3();
        int iM213750a4 = c0797kv.m213750a4() + iM213749a3;
        int iM213750a42 = iM213749a32 - c0797kv2.m213750a4();
        if (iM213749a3 == iM213749a32) {
            f = 0.5f;
        } else {
            iM213749a3 = iM213750a4;
            iM213749a32 = iM213750a42;
        }
        int iM213891b7 = c0829lq.m213891b7();
        int i2 = (iM213749a32 - iM213749a3) - iM213891b7;
        if (iM213749a3 > iM213749a32) {
            i2 = (iM213749a3 - iM213749a32) - iM213891b7;
        }
        int i3 = ((int) (i2 > 0 ? (f * i2) + 0.5f : f * i2)) + iM213749a3;
        int i4 = i3 + iM213891b7;
        if (iM213749a3 > iM213749a32) {
            i4 = i3 - iM213891b7;
        }
        c0829lq.m213906d6(i3, i4);
        m212486b8(i + 1, c0813la, c0829lq, z);
    }

    /* renamed from: d7 */
    public static void m212494d7(int i, C0829lq c0829lq, C0813la c0813la, C0829lq c0829lq2, boolean z) {
        float f = c0829lq2.f58118f7;
        C0797kv c0797kv = c0829lq2.f58096d5;
        int iM213750a4 = c0797kv.m213750a4() + c0797kv.f57726a5.m213749a3();
        C0797kv c0797kv2 = c0829lq2.f58098d7;
        int iM213749a3 = c0797kv2.f57726a5.m213749a3() - c0797kv2.m213750a4();
        if (iM213749a3 >= iM213750a4) {
            int iM213891b7 = c0829lq2.m213891b7();
            if (c0829lq2.f58121g0 != 8) {
                int i2 = c0829lq2.f58078b7;
                if (i2 == 2) {
                    iM213891b7 = (int) (c0829lq2.f58118f7 * 0.5f * (c0829lq instanceof C0830lr ? c0829lq.m213891b7() : c0829lq.f58108e7.m213891b7()));
                } else if (i2 == 0) {
                    iM213891b7 = iM213749a3 - iM213750a4;
                }
                iM213891b7 = Math.max(c0829lq2.f58081c0, iM213891b7);
                int i3 = c0829lq2.f58082c1;
                if (i3 > 0) {
                    iM213891b7 = Math.min(i3, iM213891b7);
                }
            }
            int i4 = iM213750a4 + ((int) ((f * ((iM213749a3 - iM213750a4) - iM213891b7)) + 0.5f));
            c0829lq2.m213906d6(i4, iM213891b7 + i4);
            m212486b8(i + 1, c0813la, c0829lq2, z);
        }
    }

    /* renamed from: d8 */
    public static void m212495d8(int i, C0813la c0813la, C0829lq c0829lq) {
        float f = c0829lq.f58119f8;
        C0797kv c0797kv = c0829lq.f58097d6;
        int iM213749a3 = c0797kv.f57726a5.m213749a3();
        C0797kv c0797kv2 = c0829lq.f58099d8;
        int iM213749a32 = c0797kv2.f57726a5.m213749a3();
        int iM213750a4 = c0797kv.m213750a4() + iM213749a3;
        int iM213750a42 = iM213749a32 - c0797kv2.m213750a4();
        if (iM213749a3 == iM213749a32) {
            f = 0.5f;
        } else {
            iM213749a3 = iM213750a4;
            iM213749a32 = iM213750a42;
        }
        int iM213887b1 = c0829lq.m213887b1();
        int i2 = (iM213749a32 - iM213749a3) - iM213887b1;
        if (iM213749a3 > iM213749a32) {
            i2 = (iM213749a3 - iM213749a32) - iM213887b1;
        }
        int i3 = (int) (i2 > 0 ? (f * i2) + 0.5f : f * i2);
        int i4 = iM213749a3 + i3;
        int i5 = i4 + iM213887b1;
        if (iM213749a3 > iM213749a32) {
            i4 = iM213749a3 - i3;
            i5 = i4 - iM213887b1;
        }
        c0829lq.m213907d7(i4, i5);
        m212499e3(i + 1, c0813la, c0829lq);
    }

    /* renamed from: d9 */
    public static void m212496d9(int i, C0829lq c0829lq, C0813la c0813la, C0829lq c0829lq2) {
        float f = c0829lq2.f58119f8;
        C0797kv c0797kv = c0829lq2.f58097d6;
        int iM213750a4 = c0797kv.m213750a4() + c0797kv.f57726a5.m213749a3();
        C0797kv c0797kv2 = c0829lq2.f58099d8;
        int iM213749a3 = c0797kv2.f57726a5.m213749a3() - c0797kv2.m213750a4();
        if (iM213749a3 >= iM213750a4) {
            int iM213887b1 = c0829lq2.m213887b1();
            if (c0829lq2.f58121g0 != 8) {
                int i2 = c0829lq2.f58079b8;
                if (i2 == 2) {
                    iM213887b1 = (int) (f * 0.5f * (c0829lq instanceof C0830lr ? c0829lq.m213887b1() : c0829lq.f58108e7.m213887b1()));
                } else if (i2 == 0) {
                    iM213887b1 = iM213749a3 - iM213750a4;
                }
                iM213887b1 = Math.max(c0829lq2.f58084c3, iM213887b1);
                int i3 = c0829lq2.f58085c4;
                if (i3 > 0) {
                    iM213887b1 = Math.min(i3, iM213887b1);
                }
            }
            int i4 = iM213750a4 + ((int) ((f * ((iM213749a3 - iM213750a4) - iM213887b1)) + 0.5f));
            c0829lq2.m213907d7(i4, iM213887b1 + i4);
            m212499e3(i + 1, c0813la, c0829lq2);
        }
    }

    /* renamed from: e0 */
    public static final Pair m212497e0(String str, List list) {
        return new Pair(str, list);
    }

    /* renamed from: e2 */
    public static boolean m212498e2(ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour, ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2, ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3, ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4) {
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour5 = ConstraintWidget$DimensionBehaviour.f44427a3;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour6 = ConstraintWidget$DimensionBehaviour.f44425a1;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour7 = ConstraintWidget$DimensionBehaviour.f44424a0;
        return (constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour7 || constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour6 || (constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour5 && constraintWidget$DimensionBehaviour != constraintWidget$DimensionBehaviour6)) || (constraintWidget$DimensionBehaviour4 == constraintWidget$DimensionBehaviour7 || constraintWidget$DimensionBehaviour4 == constraintWidget$DimensionBehaviour6 || (constraintWidget$DimensionBehaviour4 == constraintWidget$DimensionBehaviour5 && constraintWidget$DimensionBehaviour2 != constraintWidget$DimensionBehaviour6));
    }

    /* renamed from: e3 */
    public static void m212499e3(int i, C0813la c0813la, C0829lq c0829lq) {
        boolean z;
        C0797kv c0797kv;
        C0797kv c0797kv2;
        C0797kv c0797kv3;
        C0797kv c0797kv4;
        if (c0829lq.f58074b3) {
            return;
        }
        if (!(c0829lq instanceof C0830lr) && c0829lq.m213900c6() && m212473a1(c0829lq)) {
            C0830lr.m213920e8(c0829lq, c0813la, new C0418dj());
        }
        C0797kv c0797kvMo213885a9 = c0829lq.mo213885a9(ConstraintAnchor$Type.f44416a1);
        C0797kv c0797kvMo213885a92 = c0829lq.mo213885a9(ConstraintAnchor$Type.f44418a3);
        int iM213749a3 = c0797kvMo213885a9.m213749a3();
        int iM213749a32 = c0797kvMo213885a92.m213749a3();
        HashSet hashSet = c0797kvMo213885a9.f57721a0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44426a2;
        if (hashSet != null && c0797kvMo213885a9.f57723a2) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                C0797kv c0797kv5 = (C0797kv) it.next();
                C0829lq c0829lq2 = c0797kv5.f57724a3;
                int i2 = i + 1;
                boolean zM212473a1 = m212473a1(c0829lq2);
                C0797kv c0797kv6 = c0829lq2.f58097d6;
                C0797kv c0797kv7 = c0829lq2.f58099d8;
                if (c0829lq2.m213900c6() && zM212473a1) {
                    C0830lr.m213920e8(c0829lq2, c0813la, new C0418dj());
                }
                boolean z2 = (c0797kv5 == c0797kv6 && (c0797kv4 = c0797kv7.f57726a5) != null && c0797kv4.f57723a2) || (c0797kv5 == c0797kv7 && (c0797kv3 = c0797kv6.f57726a5) != null && c0797kv3.f57723a2);
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = c0829lq2.f58107e6[1];
                if (constraintWidget$DimensionBehaviour2 != constraintWidget$DimensionBehaviour || zM212473a1) {
                    if (!c0829lq2.m213900c6()) {
                        if (c0797kv5 == c0797kv6 && c0797kv7.f57726a5 == null) {
                            int iM213750a4 = c0797kv6.m213750a4() + iM213749a3;
                            c0829lq2.m213907d7(iM213750a4, c0829lq2.m213887b1() + iM213750a4);
                            m212499e3(i2, c0813la, c0829lq2);
                        } else if (c0797kv5 == c0797kv7 && c0797kv6.f57726a5 == null) {
                            int iM213750a42 = iM213749a3 - c0797kv7.m213750a4();
                            c0829lq2.m213907d7(iM213750a42 - c0829lq2.m213887b1(), iM213750a42);
                            m212499e3(i2, c0813la, c0829lq2);
                        } else if (z2 && !c0829lq2.m213899c5()) {
                            m212495d8(i2, c0813la, c0829lq2);
                        }
                    }
                } else if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour && c0829lq2.f58085c4 >= 0 && c0829lq2.f58084c3 >= 0 && (c0829lq2.f58121g0 == 8 || (c0829lq2.f58079b8 == 0 && c0829lq2.f58111f0 == 0.0f))) {
                    if (!c0829lq2.m213899c5() && !c0829lq2.f58093d2 && z2 && !c0829lq2.m213899c5()) {
                        m212496d9(i2, c0829lq, c0813la, c0829lq2);
                    }
                }
            }
        }
        boolean z3 = true;
        z3 = true;
        z3 = true;
        if (c0829lq instanceof o30) {
            return;
        }
        HashSet hashSet2 = c0797kvMo213885a92.f57721a0;
        if (hashSet2 != null && c0797kvMo213885a92.f57723a2) {
            Iterator it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                C0797kv c0797kv8 = (C0797kv) it2.next();
                C0829lq c0829lq3 = c0797kv8.f57724a3;
                int i3 = i + 1;
                boolean zM212473a12 = m212473a1(c0829lq3);
                C0797kv c0797kv9 = c0829lq3.f58097d6;
                C0797kv c0797kv10 = c0829lq3.f58099d8;
                if (c0829lq3.m213900c6() && zM212473a12) {
                    C0830lr.m213920e8(c0829lq3, c0813la, new C0418dj());
                }
                boolean z4 = (c0797kv8 == c0797kv9 && (c0797kv2 = c0797kv10.f57726a5) != null && c0797kv2.f57723a2) || (c0797kv8 == c0797kv10 && (c0797kv = c0797kv9.f57726a5) != null && c0797kv.f57723a2);
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = c0829lq3.f58107e6[1];
                if (constraintWidget$DimensionBehaviour3 != constraintWidget$DimensionBehaviour || zM212473a12) {
                    if (!c0829lq3.m213900c6()) {
                        if (c0797kv8 == c0797kv9 && c0797kv10.f57726a5 == null) {
                            int iM213750a43 = c0797kv9.m213750a4() + iM213749a32;
                            c0829lq3.m213907d7(iM213750a43, c0829lq3.m213887b1() + iM213750a43);
                            m212499e3(i3, c0813la, c0829lq3);
                        } else if (c0797kv8 == c0797kv10 && c0797kv9.f57726a5 == null) {
                            int iM213750a44 = iM213749a32 - c0797kv10.m213750a4();
                            c0829lq3.m213907d7(iM213750a44 - c0829lq3.m213887b1(), iM213750a44);
                            m212499e3(i3, c0813la, c0829lq3);
                        } else if (z4 && !c0829lq3.m213899c5()) {
                            m212495d8(i3, c0813la, c0829lq3);
                        }
                    }
                } else if (constraintWidget$DimensionBehaviour3 == constraintWidget$DimensionBehaviour && c0829lq3.f58085c4 >= 0 && c0829lq3.f58084c3 >= 0 && (c0829lq3.f58121g0 == 8 || (c0829lq3.f58079b8 == 0 && c0829lq3.f58111f0 == 0.0f))) {
                    if (!c0829lq3.m213899c5() && !c0829lq3.f58093d2 && z4 && !c0829lq3.m213899c5()) {
                        m212496d9(i3, c0829lq, c0813la, c0829lq3);
                    }
                }
            }
        }
        C0797kv c0797kvMo213885a93 = c0829lq.mo213885a9(ConstraintAnchor$Type.f44419a4);
        if (c0797kvMo213885a93.f57721a0 != null && c0797kvMo213885a93.f57723a2) {
            int iM213749a33 = c0797kvMo213885a93.m213749a3();
            Iterator it3 = c0797kvMo213885a93.f57721a0.iterator();
            while (it3.hasNext()) {
                C0797kv c0797kv11 = (C0797kv) it3.next();
                C0829lq c0829lq4 = c0797kv11.f57724a3;
                int i4 = i + 1;
                boolean zM212473a13 = m212473a1(c0829lq4);
                C0797kv c0797kv12 = c0829lq4.f58100d9;
                if (c0829lq4.m213900c6() && zM212473a13) {
                    C0830lr.m213920e8(c0829lq4, c0813la, new C0418dj());
                }
                if (c0829lq4.f58107e6[z3 ? 1 : 0] != constraintWidget$DimensionBehaviour || zM212473a13) {
                    if (!c0829lq4.m213900c6()) {
                        if (c0797kv11 == c0797kv12) {
                            int iM213750a45 = c0797kv11.m213750a4() + iM213749a33;
                            if (c0829lq4.f58091d0) {
                                int i5 = iM213750a45 - c0829lq4.f58115f4;
                                int i6 = c0829lq4.f58110e9 + i5;
                                c0829lq4.f58114f3 = i5;
                                c0829lq4.f58097d6.m213757b1(i5);
                                c0829lq4.f58099d8.m213757b1(i6);
                                c0797kv12.m213757b1(iM213750a45);
                                z = z3 ? 1 : 0;
                                c0829lq4.f58072b1 = z;
                            } else {
                                z = z3 ? 1 : 0;
                            }
                            m212499e3(i4, c0813la, c0829lq4);
                        }
                        z3 = z;
                    }
                }
                z = z3 ? 1 : 0;
                z3 = z;
            }
        }
        c0829lq.f58074b3 = z3;
    }

    /* renamed from: a0 */
    public void m212500a0(int i) {
        new Handler(Looper.getMainLooper()).post(new RunnableC0027ag(this, i, 3));
    }

    /* renamed from: a5 */
    public abstract int mo212501a5(View view, int i);

    /* renamed from: a6 */
    public abstract int mo212502a6(View view, int i);

    /* renamed from: b6 */
    public int mo212503b6(View view) {
        return 0;
    }

    /* renamed from: b7 */
    public int mo212504b7() {
        return 0;
    }

    /* renamed from: c5 */
    public abstract void mo212507c5(Throwable th);

    /* renamed from: c6 */
    public abstract void mo212508c6(int i);

    /* renamed from: c7 */
    public abstract void mo212509c7(Typeface typeface);

    /* renamed from: c8 */
    public abstract void mo212510c8(Typeface typeface, boolean z);

    /* renamed from: c9 */
    public abstract void mo212511c9(x31 x31Var);

    /* renamed from: d1 */
    public abstract void mo212513d1(int i);

    /* renamed from: d2 */
    public abstract void mo212514d2(View view, int i, int i2);

    /* renamed from: d3 */
    public abstract void mo212515d3(View view, float f, float f2);

    /* renamed from: e1 */
    public abstract boolean mo212516e1(View view, int i);

    /* renamed from: c4 */
    public void mo212506c4() {
    }

    /* renamed from: c3 */
    public void mo212505c3(int i, int i2) {
    }

    /* renamed from: d0 */
    public void mo212512d0(View view, int i) {
    }
}
