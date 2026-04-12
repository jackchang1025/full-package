package p000;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.InputFilter;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.LegacySavedStateHandleController$tryToAddRecreator$1;
import androidx.lifecycle.Lifecycle$State;
import androidx.lifecycle.SavedStateHandleController;
import androidx.work.BackoffPolicy;
import androidx.work.NetworkType;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo$State;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CancellationException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.AbstractC0781a1;
import kotlinx.coroutines.internal.AbstractC0788a1;
import okio.Segment;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class b81 {

    /* renamed from: a7 */
    public static jg0 f45736a7 = null;

    /* renamed from: b3 */
    public static Field f45742b3 = null;

    /* renamed from: b4 */
    public static boolean f45743b4 = false;

    /* renamed from: b5 */
    public static Class f45744b5 = null;

    /* renamed from: b6 */
    public static boolean f45745b6 = false;

    /* renamed from: b7 */
    public static Field f45746b7 = null;

    /* renamed from: b8 */
    public static boolean f45747b8 = false;

    /* renamed from: b9 */
    public static Field f45748b9 = null;

    /* renamed from: c0 */
    public static boolean f45749c0 = false;

    /* renamed from: c1 */
    public static boolean f45750c1 = true;

    /* renamed from: a0 */
    public static final float[][] f45729a0 = {new float[]{0.401288f, 0.650173f, -0.051461f}, new float[]{-0.250268f, 1.204414f, 0.045854f}, new float[]{-0.002079f, 0.048952f, 0.953127f}};

    /* renamed from: a1 */
    public static final float[][] f45730a1 = {new float[]{1.8620678f, -1.0112547f, 0.14918678f}, new float[]{0.38752654f, 0.62144744f, -0.00897398f}, new float[]{-0.0158415f, -0.03412294f, 1.0499644f}};

    /* renamed from: a2 */
    public static final float[] f45731a2 = {95.047f, 100.0f, 108.883f};

    /* renamed from: a3 */
    public static final float[][] f45732a3 = {new float[]{0.41233894f, 0.35762063f, 0.18051042f}, new float[]{0.2126f, 0.7152f, 0.0722f}, new float[]{0.01932141f, 0.11916382f, 0.9503448f}};

    /* renamed from: a4 */
    public static final C1347vr f45733a4 = new C1347vr("UNDEFINED");

    /* renamed from: a5 */
    public static final C1347vr f45734a5 = new C1347vr("REUSABLE_CLAIMED");

    /* renamed from: a6 */
    public static final jg0 f45735a6 = new jg0(null, null, null);

    /* renamed from: a8 */
    public static final int[] f45737a8 = {R.attr.state_pressed};

    /* renamed from: a9 */
    public static final int[] f45738a9 = {R.attr.state_focused};

    /* renamed from: b0 */
    public static final int[] f45739b0 = {R.attr.state_selected, R.attr.state_pressed};

    /* renamed from: b1 */
    public static final int[] f45740b1 = {R.attr.state_selected};

    /* renamed from: b2 */
    public static final int[] f45741b2 = {R.attr.state_enabled, R.attr.state_pressed};

    /* JADX WARN: Removed duplicated region for block: B:189:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x06ac  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x06ba  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x06c0  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x06c3  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x06c7  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x06d7  */
    /* JADX WARN: Removed duplicated region for block: B:425:0x06db A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:435:0x06f7 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0117  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m210560a0(C0830lr c0830lr, ab0 ab0Var, ArrayList arrayList, int i) {
        int i2;
        C0554gr[] c0554grArr;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        C0829lq c0829lq;
        float f;
        int i5;
        C0829lq c0829lq2;
        ab0 ab0Var2;
        C0829lq c0829lq3;
        e11 e11Var;
        C0797kv c0797kv;
        e11 e11Var2;
        C0829lq c0829lq4;
        int i6;
        C0797kv c0797kv2;
        e11 e11Var3;
        C0829lq c0829lq5;
        C0797kv[] c0797kvArr;
        C0829lq c0829lq6;
        int i7;
        e11 e11Var4;
        int size;
        ArrayList arrayList2;
        int i8;
        int i9;
        C0829lq c0829lq7;
        float f2;
        float f3;
        float f4;
        int i10;
        C0829lq c0829lq8;
        int i11;
        int i12;
        int i13;
        C0829lq c0829lq9;
        float f5;
        C0830lr c0830lr2 = c0830lr;
        ab0 ab0Var3 = ab0Var;
        ArrayList arrayList3 = arrayList;
        if (i == 0) {
            i2 = c0830lr2.f58148i1;
            c0554grArr = c0830lr2.f58151i4;
            i3 = 0;
        } else {
            i2 = c0830lr2.f58149i2;
            c0554grArr = c0830lr2.f58150i3;
            i3 = 2;
        }
        int i14 = i2;
        C0554gr[] c0554grArr2 = c0554grArr;
        int i15 = 0;
        while (i15 < i14) {
            C0554gr c0554gr = c0554grArr2[i15];
            boolean z5 = c0554gr.f56567b6;
            C0829lq c0829lq10 = c0554gr.f56551a0;
            C0797kv[] c0797kvArr2 = c0829lq10.f58104e3;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44426a2;
            int i16 = 8;
            float f6 = 0.0f;
            if (z5) {
                i4 = i15;
            } else {
                int i17 = c0554gr.f56562b1;
                int i18 = i17 * 2;
                C0829lq c0829lq11 = c0829lq10;
                C0829lq c0829lq12 = c0829lq11;
                boolean z6 = false;
                while (!z6) {
                    c0554gr.f56559a8++;
                    C0829lq[] c0829lqArr = c0829lq11.f58128g7;
                    C0797kv[] c0797kvArr3 = c0829lq11.f58104e3;
                    c0829lqArr[i17] = null;
                    c0829lq11.f58127g6[i17] = null;
                    if (c0829lq11.f58121g0 != i16) {
                        c0829lq11.m213886b0(i17);
                        c0797kvArr3[i18].m213750a4();
                        int i19 = i18 + 1;
                        c0797kvArr3[i19].m213750a4();
                        c0797kvArr3[i18].m213750a4();
                        c0797kvArr3[i19].m213750a4();
                        if (c0554gr.f56552a1 == null) {
                            c0554gr.f56552a1 = c0829lq11;
                        }
                        c0554gr.f56554a3 = c0829lq11;
                        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = c0829lq11.f58107e6[i17];
                        if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour) {
                            int i20 = c0829lq11.f58080b9[i17];
                            i12 = i15;
                            if (i20 == 0 || i20 == 3 || i20 == 2) {
                                c0554gr.f56560a9++;
                                float f7 = c0829lq11.f58126g5[i17];
                                if (f7 > 0.0f) {
                                    f5 = f7;
                                    c0554gr.f56561b0 += f5;
                                } else {
                                    f5 = f7;
                                }
                                i13 = i17;
                                if (c0829lq11.f58121g0 != 8 && constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour && (i20 == 0 || i20 == 3)) {
                                    if (f5 < 0.0f) {
                                        c0554gr.f56564b3 = true;
                                    } else {
                                        c0554gr.f56565b4 = true;
                                    }
                                    if (c0554gr.f56558a7 == null) {
                                        c0554gr.f56558a7 = new ArrayList();
                                    }
                                    c0554gr.f56558a7.add(c0829lq11);
                                }
                                if (c0554gr.f56556a5 == null) {
                                    c0554gr.f56556a5 = c0829lq11;
                                }
                                C0829lq c0829lq13 = c0554gr.f56557a6;
                                if (c0829lq13 != null) {
                                    c0829lq13.f58127g6[i13] = c0829lq11;
                                }
                                c0554gr.f56557a6 = c0829lq11;
                            } else {
                                i13 = i17;
                            }
                            if (i13 == 0) {
                                if (c0829lq11.f58078b7 == 0 && c0829lq11.f58081c0 == 0) {
                                    int i21 = c0829lq11.f58082c1;
                                }
                            } else if (c0829lq11.f58079b8 == 0 && c0829lq11.f58084c3 == 0) {
                                int i22 = c0829lq11.f58085c4;
                            }
                        } else {
                            i12 = i15;
                            i13 = i17;
                        }
                    }
                    C0829lq c0829lq14 = c0829lq12;
                    if (c0829lq14 != c0829lq11) {
                        c0829lq14.f58128g7[i13] = c0829lq11;
                    }
                    C0797kv c0797kv3 = c0797kvArr3[i18 + 1].f57726a5;
                    if (c0797kv3 != null) {
                        c0829lq9 = c0797kv3.f57724a3;
                        C0797kv c0797kv4 = c0829lq9.f58104e3[i18].f57726a5;
                        if (c0797kv4 == null || c0797kv4.f57724a3 != c0829lq11) {
                            c0829lq9 = null;
                        }
                    }
                    if (c0829lq9 == null) {
                        c0829lq9 = c0829lq11;
                        z6 = true;
                    }
                    c0829lq12 = c0829lq11;
                    i17 = i13;
                    i16 = 8;
                    c0829lq11 = c0829lq9;
                    i15 = i12;
                }
                i4 = i15;
                int i23 = i17;
                C0829lq c0829lq15 = c0554gr.f56552a1;
                if (c0829lq15 != null) {
                    c0829lq15.f58104e3[i18].m213750a4();
                }
                C0829lq c0829lq16 = c0554gr.f56554a3;
                if (c0829lq16 != null) {
                    c0829lq16.f58104e3[i18 + 1].m213750a4();
                }
                c0554gr.f56553a2 = c0829lq11;
                if (i23 == 0 && c0554gr.f56563b2) {
                    c0554gr.f56555a4 = c0829lq11;
                } else {
                    c0554gr.f56555a4 = c0829lq10;
                }
                c0554gr.f56566b5 = c0554gr.f56565b4 && c0554gr.f56564b3;
            }
            c0554gr.f56567b6 = true;
            if (arrayList3 == null || arrayList3.contains(c0829lq10)) {
                C0829lq c0829lq17 = c0554gr.f56553a2;
                C0829lq c0829lq18 = c0554gr.f56552a1;
                C0829lq c0829lq19 = c0554gr.f56554a3;
                C0829lq c0829lq20 = c0554gr.f56555a4;
                float f8 = c0554gr.f56561b0;
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0830lr2.f58107e6;
                C0797kv[] c0797kvArr4 = c0797kvArr2;
                C0797kv[] c0797kvArr5 = c0830lr2.f58104e3;
                boolean z7 = constraintWidget$DimensionBehaviourArr[i] == ConstraintWidget$DimensionBehaviour.f44425a1;
                if (i == 0) {
                    int i24 = c0829lq20.f58124g3;
                    boolean z8 = i24 == 0;
                    z = z7;
                    z3 = i24 == 1;
                    z4 = i24 == 2;
                    c0829lq = c0829lq10;
                    f = f8;
                    z2 = z8;
                } else {
                    z = z7;
                    int i25 = c0829lq20.f58125g4;
                    z2 = i25 == 0;
                    z3 = i25 == 1;
                    z4 = i25 == 2;
                    c0829lq = c0829lq10;
                    f = f8;
                }
                boolean z9 = z3;
                boolean z10 = false;
                while (!z10) {
                    C0797kv[] c0797kvArr6 = c0829lq.f58104e3;
                    C0797kv c0797kv5 = c0797kvArr6[i3];
                    int i26 = z4 ? 1 : 4;
                    int iM213750a4 = c0797kv5.m213750a4();
                    boolean z11 = z4;
                    boolean z12 = c0829lq.f58107e6[i] == constraintWidget$DimensionBehaviour && c0829lq.f58080b9[i] == 0;
                    C0797kv c0797kv6 = c0797kv5.f57726a5;
                    if (c0797kv6 != null && c0829lq != c0829lq10) {
                        iM213750a4 = c0797kv6.m213750a4() + iM213750a4;
                    }
                    int i27 = iM213750a4;
                    if (z11 && c0829lq != c0829lq10 && c0829lq != c0829lq18) {
                        i26 = 8;
                    }
                    C0829lq c0829lq21 = c0829lq10;
                    C0797kv c0797kv7 = c0797kv5.f57726a5;
                    if (c0797kv7 != null) {
                        if (c0829lq == c0829lq18) {
                            i10 = i14;
                            ab0Var3.m209764a5(c0797kv5.f57729a8, c0797kv7.f57729a8, i27, 6);
                        } else {
                            i10 = i14;
                            ab0Var3.m209764a5(c0797kv5.f57729a8, c0797kv7.f57729a8, i27, 8);
                        }
                        if (z12 && !z11) {
                            i26 = 5;
                        }
                        ab0Var3.m209763a4(c0797kv5.f57729a8, c0797kv5.f57726a5.f57729a8, i27, (c0829lq == c0829lq18 && z11 && c0829lq.f58106e5[i]) ? 5 : i26);
                    } else {
                        i10 = i14;
                    }
                    if (z) {
                        if (c0829lq.f58121g0 == 8 || c0829lq.f58107e6[i] != constraintWidget$DimensionBehaviour) {
                            i11 = 0;
                        } else {
                            i11 = 0;
                            ab0Var3.m209764a5(c0797kvArr6[i3 + 1].f57729a8, c0797kvArr6[i3].f57729a8, 0, 5);
                        }
                        ab0Var3.m209764a5(c0797kvArr6[i3].f57729a8, c0797kvArr5[i3].f57729a8, i11, 8);
                    }
                    C0797kv c0797kv8 = c0797kvArr6[i3 + 1].f57726a5;
                    if (c0797kv8 != null) {
                        c0829lq8 = c0797kv8.f57724a3;
                        C0797kv c0797kv9 = c0829lq8.f58104e3[i3].f57726a5;
                        if (c0797kv9 == null || c0797kv9.f57724a3 != c0829lq) {
                            c0829lq8 = null;
                        }
                    }
                    if (c0829lq8 != null) {
                        c0829lq = c0829lq8;
                    } else {
                        z10 = true;
                    }
                    c0829lq10 = c0829lq21;
                    z4 = z11;
                    i14 = i10;
                }
                boolean z13 = z4;
                i5 = i14;
                if (c0829lq19 != null) {
                    int i28 = i3 + 1;
                    if (c0829lq17.f58104e3[i28].f57726a5 != null) {
                        C0797kv c0797kv10 = c0829lq19.f58104e3[i28];
                        if (c0829lq19.f58107e6[i] == constraintWidget$DimensionBehaviour && c0829lq19.f58080b9[i] == 0 && !z13) {
                            C0797kv c0797kv11 = c0797kv10.f57726a5;
                            if (c0797kv11.f57724a3 == c0830lr2) {
                                ab0Var3.m209763a4(c0797kv10.f57729a8, c0797kv11.f57729a8, -c0797kv10.m213750a4(), 5);
                            }
                            ab0Var3.m209765a6(c0797kv10.f57729a8, c0829lq17.f58104e3[i28].f57726a5.f57729a8, -c0797kv10.m213750a4(), 6);
                        } else {
                            if (z13) {
                                C0797kv c0797kv12 = c0797kv10.f57726a5;
                                if (c0797kv12.f57724a3 == c0830lr2) {
                                    ab0Var3.m209763a4(c0797kv10.f57729a8, c0797kv12.f57729a8, -c0797kv10.m213750a4(), 4);
                                }
                            }
                            ab0Var3.m209765a6(c0797kv10.f57729a8, c0829lq17.f58104e3[i28].f57726a5.f57729a8, -c0797kv10.m213750a4(), 6);
                        }
                    }
                    if (z) {
                        int i29 = i3 + 1;
                        e11 e11Var5 = c0797kvArr5[i29].f57729a8;
                        C0797kv c0797kv13 = c0829lq17.f58104e3[i29];
                        ab0Var3.m209764a5(e11Var5, c0797kv13.f57729a8, c0797kv13.m213750a4(), 8);
                    }
                    ArrayList arrayList4 = c0554gr.f56558a7;
                    if (arrayList4 != null && (size = arrayList4.size()) > 1) {
                        float f9 = (!c0554gr.f56564b3 || c0554gr.f56566b5) ? f : c0554gr.f56560a9;
                        C0829lq c0829lq22 = null;
                        float f10 = 0.0f;
                        int i30 = 0;
                        while (i30 < size) {
                            C0829lq c0829lq23 = (C0829lq) arrayList4.get(i30);
                            float[] fArr = c0829lq23.f58126g5;
                            C0797kv[] c0797kvArr7 = c0829lq23.f58104e3;
                            float f11 = fArr[i];
                            if (f11 >= f6) {
                                arrayList2 = arrayList4;
                                if (f11 != f6) {
                                    i8 = i30;
                                    ab0Var3.m209763a4(c0797kvArr7[i3 + 1].f57729a8, c0797kvArr7[i3].f57729a8, 0, 8);
                                    i9 = size;
                                    f2 = f9;
                                    f3 = f6;
                                } else {
                                    i8 = i30;
                                    if (c0829lq22 != null) {
                                        C0797kv[] c0797kvArr8 = c0829lq22.f58104e3;
                                        e11 e11Var6 = c0797kvArr8[i3].f57729a8;
                                        int i31 = i3 + 1;
                                        e11 e11Var7 = c0797kvArr8[i31].f57729a8;
                                        e11 e11Var8 = c0797kvArr7[i3].f57729a8;
                                        e11 e11Var9 = c0797kvArr7[i31].f57729a8;
                                        i9 = size;
                                        C0131be c0131beM209770b1 = ab0Var3.m209770b1();
                                        c0829lq7 = c0829lq23;
                                        float f12 = f6;
                                        c0131beM209770b1.f45833a1 = f12;
                                        f3 = f12;
                                        if (f9 == f12 || f10 == f11) {
                                            f2 = f9;
                                            f4 = f11;
                                            c0131beM209770b1.f45835a3.m210629a6(e11Var6, 1.0f);
                                            c0131beM209770b1.f45835a3.m210629a6(e11Var7, -1.0f);
                                            c0131beM209770b1.f45835a3.m210629a6(e11Var9, 1.0f);
                                            c0131beM209770b1.f45835a3.m210629a6(e11Var8, -1.0f);
                                        } else {
                                            if (f10 == f3) {
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var6, 1.0f);
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var7, -1.0f);
                                            } else if (f11 == f6) {
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var8, 1.0f);
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var9, -1.0f);
                                            } else {
                                                f2 = f9;
                                                float f13 = (f10 / f9) / (f11 / f9);
                                                f4 = f11;
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var6, 1.0f);
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var7, -1.0f);
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var9, f13);
                                                c0131beM209770b1.f45835a3.m210629a6(e11Var8, -f13);
                                            }
                                            f2 = f9;
                                            f4 = f11;
                                        }
                                        ab0Var3.m209761a2(c0131beM209770b1);
                                    } else {
                                        i9 = size;
                                        c0829lq7 = c0829lq23;
                                        f2 = f9;
                                        f3 = f6;
                                        f4 = f11;
                                    }
                                    f10 = f4;
                                    c0829lq22 = c0829lq7;
                                }
                            } else if (c0554gr.f56566b5) {
                                arrayList2 = arrayList4;
                                ab0Var3.m209763a4(c0797kvArr7[i3 + 1].f57729a8, c0797kvArr7[i3].f57729a8, 0, 4);
                                i8 = i30;
                                i9 = size;
                                f2 = f9;
                                f3 = f6;
                            } else {
                                f11 = 1.0f;
                                arrayList2 = arrayList4;
                                if (f11 != f6) {
                                }
                            }
                            i30 = i8 + 1;
                            arrayList4 = arrayList2;
                            size = i9;
                            f9 = f2;
                            f6 = f3;
                        }
                    }
                    if (c0829lq18 == null || !(c0829lq18 == c0829lq19 || z13)) {
                        c0829lq2 = c0829lq19;
                        if (!z2 || c0829lq18 == null) {
                            int i32 = 8;
                            if (z9 && c0829lq18 != null) {
                                int i33 = c0554gr.f56560a9;
                                boolean z14 = i33 > 0 && c0554gr.f56559a8 == i33;
                                C0829lq c0829lq24 = c0829lq18;
                                C0829lq c0829lq25 = c0829lq24;
                                while (c0829lq25 != null) {
                                    C0797kv[] c0797kvArr9 = c0829lq25.f58104e3;
                                    C0829lq c0829lq26 = c0829lq25.f58128g7[i];
                                    while (c0829lq26 != null && c0829lq26.f58121g0 == i32) {
                                        c0829lq26 = c0829lq26.f58128g7[i];
                                    }
                                    if (c0829lq25 == c0829lq18 || c0829lq25 == c0829lq2 || c0829lq26 == null) {
                                        c0829lq3 = c0829lq24;
                                    } else {
                                        if (c0829lq26 == c0829lq2) {
                                            c0829lq26 = null;
                                        }
                                        C0797kv c0797kv14 = c0797kvArr9[i3];
                                        e11 e11Var10 = c0797kv14.f57729a8;
                                        int i34 = i3 + 1;
                                        e11 e11Var11 = c0829lq24.f58104e3[i34].f57729a8;
                                        int iM213750a42 = c0797kv14.m213750a4();
                                        int iM213750a43 = c0797kvArr9[i34].m213750a4();
                                        if (c0829lq26 != null) {
                                            c0797kv = c0829lq26.f58104e3[i3];
                                            e11Var2 = c0797kv.f57729a8;
                                            C0797kv c0797kv15 = c0797kv.f57726a5;
                                            e11Var = c0797kv15 != null ? c0797kv15.f57729a8 : null;
                                        } else {
                                            C0797kv c0797kv16 = c0829lq2.f58104e3[i3];
                                            e11 e11Var12 = c0797kv16 != null ? c0797kv16.f57729a8 : null;
                                            e11Var = c0797kvArr9[i34].f57729a8;
                                            c0797kv = c0797kv16;
                                            e11Var2 = e11Var12;
                                        }
                                        if (c0797kv != null) {
                                            iM213750a43 += c0797kv.m213750a4();
                                        }
                                        int iM213750a44 = iM213750a42 + c0829lq24.f58104e3[i34].m213750a4();
                                        C0829lq c0829lq27 = c0829lq26;
                                        e11 e11Var13 = e11Var2;
                                        int i35 = z14 ? 8 : 4;
                                        if (e11Var10 == null || e11Var11 == null || e11Var13 == null || e11Var == null) {
                                            c0829lq3 = c0829lq24;
                                            c0829lq4 = c0829lq27;
                                        } else {
                                            c0829lq4 = c0829lq27;
                                            e11 e11Var14 = e11Var;
                                            c0829lq3 = c0829lq24;
                                            ab0Var.m209760a1(e11Var10, e11Var11, iM213750a44, 0.5f, e11Var13, e11Var14, iM213750a43, i35);
                                        }
                                        c0829lq26 = c0829lq4;
                                    }
                                    if (c0829lq25.f58121g0 != 8) {
                                        c0829lq3 = c0829lq25;
                                    }
                                    c0829lq25 = c0829lq26;
                                    c0829lq24 = c0829lq3;
                                    i32 = 8;
                                }
                                ab0Var2 = ab0Var;
                                C0797kv c0797kv17 = c0829lq18.f58104e3[i3];
                                C0797kv c0797kv18 = c0797kvArr4[i3].f57726a5;
                                int i36 = i3 + 1;
                                C0797kv c0797kv19 = c0829lq2.f58104e3[i36];
                                C0797kv c0797kv20 = c0829lq17.f58104e3[i36].f57726a5;
                                if (c0797kv18 != null) {
                                    if (c0829lq18 != c0829lq2) {
                                        ab0Var2.m209763a4(c0797kv17.f57729a8, c0797kv18.f57729a8, c0797kv17.m213750a4(), 5);
                                    } else if (c0797kv20 != null) {
                                        ab0Var2.m209760a1(c0797kv17.f57729a8, c0797kv18.f57729a8, c0797kv17.m213750a4(), 0.5f, c0797kv19.f57729a8, c0797kv20.f57729a8, c0797kv19.m213750a4(), 5);
                                    }
                                }
                                if (c0797kv20 != null && c0829lq18 != c0829lq2) {
                                    ab0Var2.m209763a4(c0797kv19.f57729a8, c0797kv20.f57729a8, -c0797kv19.m213750a4(), 5);
                                }
                            }
                            if ((z2 || z9) && c0829lq18 != null && c0829lq18 != c0829lq2) {
                                C0797kv[] c0797kvArr10 = c0829lq18.f58104e3;
                                C0797kv c0797kv21 = c0797kvArr10[i3];
                                if (c0829lq2 == null) {
                                    c0829lq2 = c0829lq18;
                                }
                                C0797kv[] c0797kvArr11 = c0829lq2.f58104e3;
                                int i37 = i3 + 1;
                                C0797kv c0797kv22 = c0797kvArr11[i37];
                                C0797kv c0797kv23 = c0797kv21.f57726a5;
                                e11Var4 = c0797kv23 != null ? c0797kv23.f57729a8 : null;
                                C0797kv c0797kv24 = c0797kv22.f57726a5;
                                e11 e11Var15 = c0797kv24 != null ? c0797kv24.f57729a8 : null;
                                if (c0829lq17 != c0829lq2) {
                                    C0797kv c0797kv25 = c0829lq17.f58104e3[i37].f57726a5;
                                    e11Var15 = c0797kv25 != null ? c0797kv25.f57729a8 : null;
                                }
                                if (c0829lq18 == c0829lq2) {
                                    c0797kv22 = c0797kvArr10[i37];
                                }
                                if (e11Var4 != null && e11Var15 != null) {
                                    ab0Var2.m209760a1(c0797kv21.f57729a8, e11Var4, c0797kv21.m213750a4(), 0.5f, e11Var15, c0797kv22.f57729a8, c0797kvArr11[i37].m213750a4(), 5);
                                }
                            }
                        } else {
                            int i38 = c0554gr.f56560a9;
                            boolean z15 = i38 > 0 && c0554gr.f56559a8 == i38;
                            C0829lq c0829lq28 = c0829lq18;
                            C0829lq c0829lq29 = c0829lq28;
                            while (c0829lq28 != null) {
                                C0797kv[] c0797kvArr12 = c0829lq28.f58104e3;
                                C0829lq c0829lq30 = c0829lq28.f58128g7[i];
                                while (true) {
                                    if (c0829lq30 == null) {
                                        i6 = 8;
                                        break;
                                    }
                                    i6 = 8;
                                    if (c0829lq30.f58121g0 != 8) {
                                        break;
                                    } else {
                                        c0829lq30 = c0829lq30.f58128g7[i];
                                    }
                                }
                                if (c0829lq30 != null || c0829lq28 == c0829lq2) {
                                    C0797kv c0797kv26 = c0797kvArr12[i3];
                                    e11 e11Var16 = c0797kv26.f57729a8;
                                    C0797kv c0797kv27 = c0797kv26.f57726a5;
                                    e11 e11Var17 = c0797kv27 != null ? c0797kv27.f57729a8 : null;
                                    if (c0829lq29 != c0829lq28) {
                                        e11Var17 = c0829lq29.f58104e3[i3 + 1].f57729a8;
                                    } else if (c0829lq28 == c0829lq18) {
                                        C0797kv c0797kv28 = c0797kvArr4[i3].f57726a5;
                                        e11Var17 = c0797kv28 != null ? c0797kv28.f57729a8 : null;
                                    }
                                    int iM213750a45 = c0797kv26.m213750a4();
                                    int i39 = i3 + 1;
                                    int iM213750a46 = c0797kvArr12[i39].m213750a4();
                                    if (c0829lq30 != null) {
                                        c0797kv2 = c0829lq30.f58104e3[i3];
                                        e11Var3 = c0797kv2.f57729a8;
                                    } else {
                                        c0797kv2 = c0829lq17.f58104e3[i39].f57726a5;
                                        e11Var3 = c0797kv2 != null ? c0797kv2.f57729a8 : null;
                                    }
                                    e11 e11Var18 = c0797kvArr12[i39].f57729a8;
                                    if (c0797kv2 != null) {
                                        iM213750a46 += c0797kv2.m213750a4();
                                    }
                                    int iM213750a47 = c0829lq29.f58104e3[i39].m213750a4() + iM213750a45;
                                    if (e11Var16 == null || e11Var17 == null || e11Var3 == null || e11Var18 == null) {
                                        c0829lq5 = c0829lq30;
                                        c0797kvArr = c0797kvArr4;
                                        c0829lq6 = c0829lq29;
                                        i7 = 8;
                                    } else {
                                        if (c0829lq28 == c0829lq18) {
                                            iM213750a47 = c0829lq18.f58104e3[i3].m213750a4();
                                        }
                                        if (c0829lq28 == c0829lq2) {
                                            iM213750a46 = c0829lq2.f58104e3[i39].m213750a4();
                                        }
                                        c0829lq5 = c0829lq30;
                                        c0797kvArr = c0797kvArr4;
                                        c0829lq6 = c0829lq29;
                                        i7 = 8;
                                        ab0Var.m209760a1(e11Var16, e11Var17, iM213750a47, 0.5f, e11Var3, e11Var18, iM213750a46, z15 ? 8 : 5);
                                    }
                                } else {
                                    c0829lq5 = c0829lq30;
                                    c0797kvArr = c0797kvArr4;
                                    c0829lq6 = c0829lq29;
                                    i7 = i6;
                                }
                                if (c0829lq28.f58121g0 != i7) {
                                    c0829lq6 = c0829lq28;
                                }
                                c0829lq28 = c0829lq5;
                                c0829lq29 = c0829lq6;
                                c0797kvArr4 = c0797kvArr;
                            }
                        }
                    } else {
                        C0797kv c0797kv29 = c0797kvArr4[i3];
                        int i40 = i3 + 1;
                        C0797kv c0797kv30 = c0829lq17.f58104e3[i40];
                        C0797kv c0797kv31 = c0797kv29.f57726a5;
                        e11 e11Var19 = c0797kv31 != null ? c0797kv31.f57729a8 : null;
                        C0797kv c0797kv32 = c0797kv30.f57726a5;
                        e11 e11Var20 = c0797kv32 != null ? c0797kv32.f57729a8 : null;
                        C0797kv c0797kv33 = c0829lq18.f58104e3[i3];
                        if (c0829lq19 != null) {
                            c0797kv30 = c0829lq19.f58104e3[i40];
                        }
                        if (e11Var19 == null || e11Var20 == null) {
                            c0829lq2 = c0829lq19;
                        } else {
                            float f14 = i == 0 ? c0829lq20.f58118f7 : c0829lq20.f58119f8;
                            int iM213750a48 = c0797kv33.m213750a4();
                            int iM213750a49 = c0797kv30.m213750a4();
                            e11 e11Var21 = c0797kv33.f57729a8;
                            e11 e11Var22 = c0797kv30.f57729a8;
                            e11 e11Var23 = e11Var19;
                            c0829lq2 = c0829lq19;
                            ab0Var3.m209760a1(e11Var21, e11Var23, iM213750a48, f14, e11Var20, e11Var22, iM213750a49, 7);
                        }
                    }
                    ab0Var2 = ab0Var;
                    if (z2) {
                        C0797kv[] c0797kvArr102 = c0829lq18.f58104e3;
                        C0797kv c0797kv212 = c0797kvArr102[i3];
                        if (c0829lq2 == null) {
                        }
                        C0797kv[] c0797kvArr112 = c0829lq2.f58104e3;
                        int i372 = i3 + 1;
                        C0797kv c0797kv222 = c0797kvArr112[i372];
                        C0797kv c0797kv232 = c0797kv212.f57726a5;
                        if (c0797kv232 != null) {
                        }
                        C0797kv c0797kv242 = c0797kv222.f57726a5;
                        if (c0797kv242 != null) {
                        }
                        if (c0829lq17 != c0829lq2) {
                        }
                        if (c0829lq18 == c0829lq2) {
                        }
                        if (e11Var4 != null) {
                        }
                    } else {
                        C0797kv[] c0797kvArr1022 = c0829lq18.f58104e3;
                        C0797kv c0797kv2122 = c0797kvArr1022[i3];
                        if (c0829lq2 == null) {
                        }
                        C0797kv[] c0797kvArr1122 = c0829lq2.f58104e3;
                        int i3722 = i3 + 1;
                        C0797kv c0797kv2222 = c0797kvArr1122[i3722];
                        C0797kv c0797kv2322 = c0797kv2122.f57726a5;
                        if (c0797kv2322 != null) {
                        }
                        C0797kv c0797kv2422 = c0797kv2222.f57726a5;
                        if (c0797kv2422 != null) {
                        }
                        if (c0829lq17 != c0829lq2) {
                        }
                        if (c0829lq18 == c0829lq2) {
                        }
                        if (e11Var4 != null) {
                        }
                    }
                }
            } else {
                i5 = i14;
            }
            i15 = i4 + 1;
            c0830lr2 = c0830lr;
            ab0Var3 = ab0Var;
            arrayList3 = arrayList;
            i14 = i5;
        }
    }

    /* renamed from: a1 */
    public static void m210561a1(TextInputLayout textInputLayout, CheckableImageButton checkableImageButton, ColorStateList colorStateList, PorterDuff.Mode mode) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (drawable != null) {
            drawable = drawable.mutate();
            if (colorStateList == null || !colorStateList.isStateful()) {
                AbstractC1270tr.m214774a7(drawable, colorStateList);
            } else {
                int[] drawableState = textInputLayout.getDrawableState();
                int[] drawableState2 = checkableImageButton.getDrawableState();
                int length = drawableState.length;
                int[] iArrCopyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
                System.arraycopy(drawableState2, 0, iArrCopyOf, length, drawableState2.length);
                AbstractC1270tr.m214774a7(drawable, ColorStateList.valueOf(colorStateList.getColorForState(iArrCopyOf, colorStateList.getDefaultColor())));
            }
            if (mode != null) {
                AbstractC1270tr.m214775a8(drawable, mode);
            }
        }
        if (checkableImageButton.getDrawable() != drawable) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    /* renamed from: a2 */
    public static List m210562a2(Object obj) {
        if ((obj instanceof d80) && !(obj instanceof f80)) {
            m210600f3(obj, "kotlin.collections.MutableList");
            throw null;
        }
        try {
            return (List) obj;
        } catch (ClassCastException e) {
            t60.m214719e5(e, b81.class.getName());
            throw e;
        }
    }

    /* renamed from: a3 */
    public static final void m210563a3(ib1 ib1Var, vt0 vt0Var, kg1 kg1Var) {
        Object obj;
        t60.m214695b6(vt0Var, "registry");
        t60.m214695b6(kg1Var, "lifecycle");
        HashMap map = ib1Var.f56853a0;
        if (map == null) {
            obj = null;
        } else {
            synchronized (map) {
                obj = ib1Var.f56853a0.get("androidx.lifecycle.savedstate.vm.tag");
            }
        }
        SavedStateHandleController savedStateHandleController = (SavedStateHandleController) obj;
        if (savedStateHandleController == null || savedStateHandleController.f45185a2) {
            return;
        }
        savedStateHandleController.m210229a1(vt0Var, kg1Var);
        Lifecycle$State lifecycle$State = ((C0076a0) kg1Var).f45191a6;
        if (lifecycle$State == Lifecycle$State.f45174a1 || lifecycle$State.compareTo(Lifecycle$State.f45176a3) >= 0) {
            vt0Var.m214954a3();
        } else {
            kg1Var.mo210230a0(new LegacySavedStateHandleController$tryToAddRecreator$1(vt0Var, kg1Var));
        }
    }

    /* renamed from: a4 */
    public static void m210564a4(l10 l10Var) {
        if (l10Var == null || m210585d4(2, l10Var)) {
            return;
        }
        m210600f3(l10Var, "kotlin.jvm.functions.Function2");
        throw null;
    }

    /* renamed from: a5 */
    public static final LinkedHashSet m210565a5(byte[] bArr) throws IOException {
        t60.m214695b6(bArr, "bytes");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (bArr.length == 0) {
            return linkedHashSet;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            try {
                int i = objectInputStream.readInt();
                for (int i2 = 0; i2 < i; i2++) {
                    Uri uri = Uri.parse(objectInputStream.readUTF());
                    boolean z = objectInputStream.readBoolean();
                    t60.m214694b5(uri, "uri");
                    linkedHashSet.add(new C0834lt(z, uri));
                }
                objectInputStream.close();
            } finally {
            }
        } catch (IOException unused) {
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                kj1.m213559a6(byteArrayInputStream, th);
                throw th2;
            }
        }
        byteArrayInputStream.close();
        return linkedHashSet;
    }

    /* renamed from: a6 */
    public static void m210566a6(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(str);
        }
    }

    /* renamed from: a7 */
    public static void m210567a7(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
    }

    /* renamed from: a8 */
    public static void m210568a8(Object obj, String str) {
        if (obj == null) {
            throw new NullPointerException(str);
        }
    }

    /* renamed from: a9 */
    public static ImageView.ScaleType m210569a9(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 5 ? i != 6 ? ImageView.ScaleType.CENTER : ImageView.ScaleType.CENTER_INSIDE : ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.FIT_END : ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.FIT_START : ImageView.ScaleType.FIT_XY;
    }

    /* renamed from: b0 */
    public static ColorStateList m210570b0(ColorStateList colorStateList) {
        int[] iArr = f45738a9;
        return new ColorStateList(new int[][]{f45740b1, iArr, StateSet.NOTHING}, new int[]{m210574b4(colorStateList, f45739b0), m210574b4(colorStateList, iArr), m210574b4(colorStateList, f45737a8)});
    }

    /* renamed from: b1 */
    public static final Object m210571b1(long j, InterfaceC0876mv interfaceC0876mv) {
        C1351vv c1351vv = C1351vv.f60710b1;
        if (j > 0) {
            C0530gb c0530gb = new C0530gb(1, kj1.m213575c2(interfaceC0876mv));
            c0530gb.m212926b6();
            if (j < Long.MAX_VALUE) {
                m210575b6(c0530gb.f56434a4).mo213703a7(j, c0530gb);
            }
            Object objM212925b5 = c0530gb.m212925b5();
            if (objM212925b5 == CoroutineSingletons.f57606a0) {
                return objM212925b5;
            }
        }
        return c1351vv;
    }

    /* renamed from: b2 */
    public static AccessibilityNodeInfo m210572b2(AccessibilityNodeInfo accessibilityNodeInfo, String str, String str2) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String string;
        String string2;
        if (str.length() != 0 && (listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
            int size = listFindAccessibilityNodeInfosByText.size();
            for (int i = 0; i < size; i++) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = listFindAccessibilityNodeInfosByText.get(i);
                CharSequence text = accessibilityNodeInfo2.getText();
                if (text != null && (string = text.toString()) != null && string.equals(str)) {
                    CharSequence className = accessibilityNodeInfo2.getClassName();
                    if (className == null || (string2 = className.toString()) == null) {
                        string2 = "";
                    }
                    if (!AbstractC0779a1.m213655a8(string2, false, ".AutoCompleteTextView") && !AbstractC0779a1.m213655a8(string2, false, ".EditText") && !AbstractC0779a1.m213652a5(string2, "EditText", false) && (!AbstractC0779a1.m213656a9(Build.BRAND, "vivo") || accessibilityNodeInfo2.isVisibleToUser())) {
                        return accessibilityNodeInfo2;
                    }
                }
            }
            t60.m214702c3(str2, "exact match not found for \"" + str + "\"");
        }
        return null;
    }

    /* renamed from: b3 */
    public static b81 m210573b3(int i, double[] dArr, double[][] dArr2) {
        if (dArr.length == 1) {
            i = 2;
        }
        if (i == 0) {
            return new kg0(dArr, dArr2);
        }
        if (i == 2) {
            double d = dArr[0];
            double[] dArr3 = dArr2[0];
            C0951og c0951og = new C0951og();
            c0951og.f58797c2 = d;
            c0951og.f58798c3 = dArr3;
            return c0951og;
        }
        qa0 qa0Var = new qa0();
        int length = dArr2[0].length;
        qa0Var.f59458c4 = new double[length];
        qa0Var.f59456c2 = dArr;
        qa0Var.f59457c3 = dArr2;
        if (length > 2) {
            double d2 = 0.0d;
            int i2 = 0;
            while (true) {
                double d3 = d2;
                if (i2 >= dArr.length) {
                    break;
                }
                double d4 = dArr2[i2][0];
                if (i2 > 0) {
                    Math.hypot(d4 - d2, d4 - d3);
                }
                i2++;
                d2 = d4;
            }
        }
        return qa0Var;
    }

    /* renamed from: b4 */
    public static int m210574b4(ColorStateList colorStateList, int[] iArr) {
        int colorForState = colorStateList != null ? colorStateList.getColorForState(iArr, colorStateList.getDefaultColor()) : 0;
        return AbstractC0724jn.m213334a4(colorForState, Math.min(Color.alpha(colorForState) * 2, v10.MASK));
    }

    /* renamed from: b6 */
    public static final InterfaceC1191rs m210575b6(InterfaceC0912ng interfaceC0912ng) {
        InterfaceC0910ne interfaceC0910neMo212745b4 = interfaceC0912ng.mo212745b4(C1351vv.f60700a1);
        InterfaceC1191rs interfaceC1191rs = interfaceC0910neMo212745b4 instanceof InterfaceC1191rs ? (InterfaceC1191rs) interfaceC0910neMo212745b4 : null;
        return interfaceC1191rs == null ? AbstractC1156qu.f59549a0 : interfaceC1191rs;
    }

    /* renamed from: b7 */
    public static Drawable m210576b7(Context context, int i) {
        return sr0.m214658a1().m214661a2(context, i);
    }

    /* renamed from: b9 */
    public static C1401x4 m210577b9(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme, String str, int i) {
        C1401x4 c1401x4M215115a1;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", str) != null) {
            TypedValue typedValue = new TypedValue();
            typedArray.getValue(i, typedValue);
            int i2 = typedValue.type;
            if (i2 >= 28 && i2 <= 31) {
                return new C1401x4(null, null, typedValue.data);
            }
            try {
                c1401x4M215115a1 = C1401x4.m215115a1(typedArray.getResources(), typedArray.getResourceId(i, 0), theme);
            } catch (Exception unused) {
                c1401x4M215115a1 = null;
            }
            if (c1401x4M215115a1 != null) {
                return c1401x4M215115a1;
            }
        }
        return new C1401x4(null, null, 0);
    }

    /* renamed from: c6 */
    public static boolean m210578c6(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", str) != null;
    }

    /* renamed from: c7 */
    public static int m210579c7(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 4) {
            return 2;
        }
        if (i == 8) {
            return 3;
        }
        if (i == 16) {
            return 4;
        }
        if (i == 32) {
            return 5;
        }
        if (i == 64) {
            return 6;
        }
        if (i == 128) {
            return 7;
        }
        if (i == 256) {
            return 8;
        }
        throw new IllegalArgumentException(tz0.m214802a2(i, "type needs to be >= FIRST and <= LAST, type="));
    }

    /* renamed from: c8 */
    public static int m210580c8(float f) {
        if (f < 1.0f) {
            return -16777216;
        }
        if (f > 99.0f) {
            return -1;
        }
        float f2 = (f + 16.0f) / 116.0f;
        float f3 = f > 8.0f ? f2 * f2 * f2 : f / 903.2963f;
        float f4 = f2 * f2 * f2;
        boolean z = f4 > 0.008856452f;
        float f5 = z ? f4 : ((f2 * 116.0f) - 16.0f) / 903.2963f;
        if (!z) {
            f4 = ((f2 * 116.0f) - 16.0f) / 903.2963f;
        }
        float[] fArr = f45731a2;
        return AbstractC0724jn.m213330a0(f5 * fArr[0], f3 * fArr[1], f4 * fArr[2]);
    }

    /* renamed from: c9 */
    public static final BackoffPolicy m210581c9(int i) {
        if (i == 0) {
            return BackoffPolicy.f45495a0;
        }
        if (i == 1) {
            return BackoffPolicy.f45496a1;
        }
        throw new IllegalArgumentException(AbstractC0003a2.m30b1("Could not convert ", i, " to BackoffPolicy"));
    }

    /* renamed from: d0 */
    public static final NetworkType m210582d0(int i) {
        if (i == 0) {
            return NetworkType.f45516a0;
        }
        if (i == 1) {
            return NetworkType.f45517a1;
        }
        if (i == 2) {
            return NetworkType.f45518a2;
        }
        if (i == 3) {
            return NetworkType.f45519a3;
        }
        if (i == 4) {
            return NetworkType.f45520a4;
        }
        if (Build.VERSION.SDK_INT < 30 || i != 5) {
            throw new IllegalArgumentException(AbstractC0003a2.m30b1("Could not convert ", i, " to NetworkType"));
        }
        return NetworkType.f45521a5;
    }

    /* renamed from: d1 */
    public static final OutOfQuotaPolicy m210583d1(int i) {
        if (i == 0) {
            return OutOfQuotaPolicy.f45523a0;
        }
        if (i == 1) {
            return OutOfQuotaPolicy.f45524a1;
        }
        throw new IllegalArgumentException(AbstractC0003a2.m30b1("Could not convert ", i, " to OutOfQuotaPolicy"));
    }

    /* renamed from: d2 */
    public static final WorkInfo$State m210584d2(int i) {
        if (i == 0) {
            return WorkInfo$State.f45526a0;
        }
        if (i == 1) {
            return WorkInfo$State.f45527a1;
        }
        if (i == 2) {
            return WorkInfo$State.f45528a2;
        }
        if (i == 3) {
            return WorkInfo$State.f45529a3;
        }
        if (i == 4) {
            return WorkInfo$State.f45530a4;
        }
        if (i == 5) {
            return WorkInfo$State.f45531a5;
        }
        throw new IllegalArgumentException(AbstractC0003a2.m30b1("Could not convert ", i, " to State"));
    }

    /* renamed from: d4 */
    public static boolean m210585d4(int i, Object obj) {
        if (obj instanceof t10) {
            if ((obj instanceof u10 ? ((u10) obj).getArity() : obj instanceof w00 ? 0 : obj instanceof h10 ? 1 : obj instanceof l10 ? 2 : obj instanceof m10 ? 3 : obj instanceof n10 ? 4 : -1) == i) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: d5 */
    public static float m210586d5(int i) {
        float f = i / 255.0f;
        return (f <= 0.04045f ? f / 12.92f : (float) Math.pow((f + 0.055f) / 1.055f, 2.4000000953674316d)) * 100.0f;
    }

    /* renamed from: d6 */
    public static final int m210587d6(NetworkType networkType) {
        t60.m214695b6(networkType, "networkType");
        int iOrdinal = networkType.ordinal();
        if (iOrdinal == 0) {
            return 0;
        }
        int i = 1;
        if (iOrdinal != 1) {
            i = 2;
            if (iOrdinal != 2) {
                i = 3;
                if (iOrdinal != 3) {
                    i = 4;
                    if (iOrdinal != 4) {
                        if (Build.VERSION.SDK_INT >= 30 && networkType == NetworkType.f45521a5) {
                            return 5;
                        }
                        throw new IllegalArgumentException("Could not convert " + networkType + " to int");
                    }
                }
            }
        }
        return i;
    }

    /* renamed from: d7 */
    public static TypedArray m210588d7(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    /* renamed from: e0 */
    public static zf0 m210589e0(MappedByteBuffer mappedByteBuffer) throws IOException {
        long j;
        ByteBuffer byteBufferDuplicate = mappedByteBuffer.duplicate();
        byteBufferDuplicate.order(ByteOrder.BIG_ENDIAN);
        byteBufferDuplicate.position(byteBufferDuplicate.position() + 4);
        int i = byteBufferDuplicate.getShort() & 65535;
        if (i > 100) {
            throw new IOException("Cannot read metadata.");
        }
        byteBufferDuplicate.position(byteBufferDuplicate.position() + 6);
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                j = -1;
                break;
            }
            int i3 = byteBufferDuplicate.getInt();
            byteBufferDuplicate.position(byteBufferDuplicate.position() + 4);
            j = byteBufferDuplicate.getInt() & 4294967295L;
            byteBufferDuplicate.position(byteBufferDuplicate.position() + 4);
            if (1835365473 == i3) {
                break;
            }
            i2++;
        }
        if (j != -1) {
            byteBufferDuplicate.position(byteBufferDuplicate.position() + ((int) (j - byteBufferDuplicate.position())));
            byteBufferDuplicate.position(byteBufferDuplicate.position() + 12);
            long j2 = byteBufferDuplicate.getInt() & 4294967295L;
            for (int i4 = 0; i4 < j2; i4++) {
                int i5 = byteBufferDuplicate.getInt();
                long j3 = byteBufferDuplicate.getInt() & 4294967295L;
                byteBufferDuplicate.getInt();
                if (1164798569 == i5 || 1701669481 == i5) {
                    byteBufferDuplicate.position((int) (j3 + j));
                    zf0 zf0Var = new zf0();
                    byteBufferDuplicate.order(ByteOrder.LITTLE_ENDIAN);
                    int iPosition = byteBufferDuplicate.position() + byteBufferDuplicate.getInt(byteBufferDuplicate.position());
                    zf0Var.f61458a3 = byteBufferDuplicate;
                    zf0Var.f61455a0 = iPosition;
                    int i6 = iPosition - byteBufferDuplicate.getInt(iPosition);
                    zf0Var.f61456a1 = i6;
                    zf0Var.f61457a2 = ((ByteBuffer) zf0Var.f61458a3).getShort(i6);
                    return zf0Var;
                }
            }
        }
        throw new IOException("Cannot read metadata.");
    }

    /* renamed from: e1 */
    public static final String m210590e1(Reader reader) throws IOException {
        StringWriter stringWriter = new StringWriter();
        char[] cArr = new char[Segment.SIZE];
        int i = reader.read(cArr);
        while (i >= 0) {
            stringWriter.write(cArr, 0, i);
            i = reader.read(cArr);
        }
        String string = stringWriter.toString();
        t60.m214694b5(string, "buffer.toString()");
        return string;
    }

    /* renamed from: e2 */
    public static void m210591e2(TextInputLayout textInputLayout, CheckableImageButton checkableImageButton, ColorStateList colorStateList) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() == null || colorStateList == null || !colorStateList.isStateful()) {
            return;
        }
        int[] drawableState = textInputLayout.getDrawableState();
        int[] drawableState2 = checkableImageButton.getDrawableState();
        int length = drawableState.length;
        int[] iArrCopyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
        System.arraycopy(drawableState2, 0, iArrCopyOf, length, drawableState2.length);
        int colorForState = colorStateList.getColorForState(iArrCopyOf, colorStateList.getDefaultColor());
        Drawable drawableMutate = drawable.mutate();
        AbstractC1270tr.m214774a7(drawableMutate, ColorStateList.valueOf(colorForState));
        checkableImageButton.setImageDrawable(drawableMutate);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x009a A[Catch: all -> 0x0079, DONT_GENERATE, TryCatch #2 {all -> 0x0079, blocks: (B:19:0x0054, B:21:0x0062, B:23:0x0068, B:36:0x009d, B:26:0x007b, B:28:0x0089, B:33:0x0094, B:35:0x009a, B:41:0x00aa, B:44:0x00b3, B:43:0x00b0, B:31:0x008f), top: B:57:0x0054, inners: #0 }] */
    /* renamed from: e3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m210592e3(Object obj, InterfaceC0876mv interfaceC0876mv) {
        k70 k70Var;
        if (!(interfaceC0876mv instanceof C1257tf)) {
            interfaceC0876mv.resumeWith(obj);
            return;
        }
        C1257tf c1257tf = (C1257tf) interfaceC0876mv;
        AbstractC0781a1 abstractC0781a1 = c1257tf.f60208a3;
        ContinuationImpl continuationImpl = c1257tf.f60209a4;
        Throwable thM213607a0 = Result.m213607a0(obj);
        Object c0730jt = thM213607a0 == null ? obj : new C0730jt(thM213607a0, false);
        continuationImpl.getContext();
        if (abstractC0781a1.mo213698c7()) {
            c1257tf.f60210a5 = c0730jt;
            c1257tf.f60222a2 = 1;
            abstractC0781a1.mo212723c6(continuationImpl.getContext(), c1257tf);
            return;
        }
        AbstractC1424xo abstractC1424xoM213943a0 = m61.m213943a0();
        if (abstractC1424xoM213943a0.f61166a2 >= 4294967296L) {
            c1257tf.f60210a5 = c0730jt;
            c1257tf.f60222a2 = 1;
            C0127ba c0127ba = abstractC1424xoM213943a0.f61168a4;
            if (c0127ba == null) {
                c0127ba = new C0127ba();
                abstractC1424xoM213943a0.f61168a4 = c0127ba;
            }
            c0127ba.addLast(c1257tf);
            return;
        }
        abstractC1424xoM213943a0.m215201d0(true);
        try {
            k70Var = (k70) continuationImpl.getContext().mo212745b4(C1351vv.f60702a3);
        } finally {
            try {
            } finally {
            }
        }
        if (k70Var == null || k70Var.mo213470a0()) {
            Object obj2 = c1257tf.f60211a6;
            InterfaceC0912ng context = continuationImpl.getContext();
            Object objM213735a1 = AbstractC0788a1.m213735a1(context, obj2);
            o81 o81VarM213695a6 = objM213735a1 != AbstractC0788a1.f57688a0 ? AbstractC0780a0.m213695a6(continuationImpl, context, objM213735a1) : null;
            try {
                continuationImpl.resumeWith(obj);
            } finally {
                if (o81VarM213695a6 == null || o81VarM213695a6.m214164e1()) {
                    AbstractC0788a1.m213734a0(context, objM213735a1);
                }
            }
        }
        CancellationException cancellationExceptionM215259b8 = ((y70) k70Var).m215259b8();
        c1257tf.mo212914a1(c0730jt, cancellationExceptionM215259b8);
        c1257tf.resumeWith(kg1.m213507a7(cancellationExceptionM215259b8));
        while (abstractC1424xoM213943a0.m215202d2()) {
        }
    }

    /* renamed from: e5 */
    public static ColorStateList m210594e5(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return ColorStateList.valueOf(0);
        }
        if (Build.VERSION.SDK_INT <= 27 && Color.alpha(colorStateList.getDefaultColor()) == 0) {
            Color.alpha(colorStateList.getColorForState(f45741b2, 0));
        }
        return colorStateList;
    }

    /* renamed from: e8 */
    public static void m210595e8(CheckableImageButton checkableImageButton, View.OnLongClickListener onLongClickListener) {
        WeakHashMap weakHashMap = xa1.f61054a0;
        boolean zM212663a0 = ea1.m212663a0(checkableImageButton);
        boolean z = onLongClickListener != null;
        boolean z2 = zM212663a0 || z;
        checkableImageButton.setFocusable(z2);
        checkableImageButton.setClickable(zM212663a0);
        checkableImageButton.setPressable(zM212663a0);
        checkableImageButton.setLongClickable(z);
        fa1.m212781b8(checkableImageButton, z2 ? 1 : 2);
    }

    /* renamed from: e9 */
    public static final byte[] m210596e9(Set set) throws IOException {
        t60.m214695b6(set, "triggers");
        if (set.isEmpty()) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeInt(set.size());
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    C0834lt c0834lt = (C0834lt) it.next();
                    objectOutputStream.writeUTF(c0834lt.f58174a0.toString());
                    objectOutputStream.writeBoolean(c0834lt.f58175a1);
                }
                objectOutputStream.close();
                byteArrayOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                t60.m214694b5(byteArray, "outputStream.toByteArray()");
                return byteArray;
            } finally {
            }
        } finally {
        }
    }

    /* renamed from: f0 */
    public static boolean m210597f0(int[] iArr) {
        boolean z = false;
        boolean z2 = false;
        for (int i : iArr) {
            if (i == 16842910) {
                z = true;
            } else if (i == 16842908 || i == 16842919 || i == 16843623) {
                z2 = true;
            }
        }
        return z && z2;
    }

    /* renamed from: f1 */
    public static final int m210598f1(WorkInfo$State workInfo$State) {
        t60.m214695b6(workInfo$State, "state");
        int iOrdinal = workInfo$State.ordinal();
        if (iOrdinal == 0) {
            return 0;
        }
        int i = 1;
        if (iOrdinal != 1) {
            i = 2;
            if (iOrdinal != 2) {
                i = 3;
                if (iOrdinal != 3) {
                    i = 4;
                    if (iOrdinal != 4) {
                        if (iOrdinal == 5) {
                            return 5;
                        }
                        throw new NoWhenBranchMatchedException();
                    }
                }
            }
        }
        return i;
    }

    /* renamed from: f2 */
    public static void m210599f2(ViewGroup viewGroup, boolean z) {
        if (Build.VERSION.SDK_INT >= 29) {
            viewGroup.suppressLayout(z);
        } else if (f45750c1) {
            try {
                viewGroup.suppressLayout(z);
            } catch (NoSuchMethodError unused) {
                f45750c1 = false;
            }
        }
    }

    /* renamed from: f3 */
    public static void m210600f3(Object obj, String str) {
        ClassCastException classCastException = new ClassCastException((obj == null ? "null" : obj.getClass().getName()) + " cannot be cast to " + str);
        t60.m214719e5(classCastException, b81.class.getName());
        throw classCastException;
    }

    /* renamed from: f5 */
    public static float m210601f5() {
        return ((float) Math.pow((50.0f + 16.0d) / 116.0d, 3.0d)) * 100.0f;
    }

    /* renamed from: b5 */
    public abstract void mo210602b5(k01 k01Var, float f, float f2);

    /* renamed from: b8 */
    public abstract InputFilter[] mo210603b8(InputFilter[] inputFilterArr);

    /* renamed from: c0 */
    public abstract double mo210516c0(double d);

    /* renamed from: c1 */
    public abstract void mo210517c1(double d, double[] dArr);

    /* renamed from: c2 */
    public abstract void mo210518c2(double d, float[] fArr);

    /* renamed from: c3 */
    public abstract double mo210519c3(double d);

    /* renamed from: c4 */
    public abstract void mo210520c4(double d, double[] dArr);

    /* renamed from: c5 */
    public abstract double[] mo210521c5();

    /* renamed from: d3 */
    public abstract boolean mo210604d3();

    /* renamed from: e6 */
    public abstract void mo210607e6(boolean z);

    /* renamed from: e7 */
    public abstract void mo210608e7(boolean z);

    /* renamed from: f4 */
    public abstract TransformationMethod mo210609f4(TransformationMethod transformationMethod);

    /* renamed from: d9 */
    public void mo210606d9() {
    }

    /* renamed from: d8 */
    public void mo210605d8(FloatingActionButton floatingActionButton) {
    }
}
