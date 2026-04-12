package p000;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.constraintlayout.utils.widget.MotionLabel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class og0 {

    /* renamed from: a1 */
    public final View f58800a1;

    /* renamed from: a2 */
    public final int f58801a2;

    /* renamed from: a9 */
    public b81[] f58808a9;

    /* renamed from: b0 */
    public C0110au f58809b0;

    /* renamed from: b4 */
    public int[] f58813b4;

    /* renamed from: b5 */
    public double[] f58814b5;

    /* renamed from: b6 */
    public double[] f58815b6;

    /* renamed from: b7 */
    public String[] f58816b7;

    /* renamed from: b8 */
    public int[] f58817b8;

    /* renamed from: c3 */
    public HashMap f58822c3;

    /* renamed from: c4 */
    public HashMap f58823c4;

    /* renamed from: c5 */
    public HashMap f58824c5;

    /* renamed from: a0 */
    public final Rect f58799a0 = new Rect();

    /* renamed from: a3 */
    public boolean f58802a3 = false;

    /* renamed from: a4 */
    public int f58803a4 = -1;

    /* renamed from: a5 */
    public final vg0 f58804a5 = new vg0();

    /* renamed from: a6 */
    public final vg0 f58805a6 = new vg0();

    /* renamed from: a7 */
    public final mg0 f58806a7 = new mg0();

    /* renamed from: a8 */
    public final mg0 f58807a8 = new mg0();

    /* renamed from: b1 */
    public float f58810b1 = Float.NaN;

    /* renamed from: b2 */
    public float f58811b2 = 0.0f;

    /* renamed from: b3 */
    public float f58812b3 = 1.0f;

    /* renamed from: b9 */
    public final float[] f58818b9 = new float[4];

    /* renamed from: c0 */
    public final ArrayList f58819c0 = new ArrayList();

    /* renamed from: c1 */
    public final float[] f58820c1 = new float[1];

    /* renamed from: c2 */
    public final ArrayList f58821c2 = new ArrayList();

    /* renamed from: c6 */
    public int f58825c6 = -1;

    /* renamed from: c7 */
    public int f58826c7 = -1;

    /* renamed from: c8 */
    public View f58827c8 = null;

    /* renamed from: c9 */
    public int f58828c9 = -1;

    /* renamed from: d0 */
    public float f58829d0 = Float.NaN;

    /* renamed from: d1 */
    public Interpolator f58830d1 = null;

    /* renamed from: d2 */
    public boolean f58831d2 = false;

    public og0(View view) {
        this.f58800a1 = view;
        this.f58801a2 = view.getId();
        view.getLayoutParams();
    }

    /* renamed from: a6 */
    public static void m214191a6(Rect rect, Rect rect2, int i, int i2, int i3) {
        if (i == 1) {
            int i4 = rect.left + rect.right;
            rect2.left = ((rect.top + rect.bottom) - rect.width()) / 2;
            rect2.top = i3 - ((rect.height() + i4) / 2);
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
            return;
        }
        if (i == 2) {
            int i5 = rect.left + rect.right;
            rect2.left = i2 - ((rect.width() + (rect.top + rect.bottom)) / 2);
            rect2.top = (i5 - rect.height()) / 2;
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
            return;
        }
        if (i == 3) {
            int i6 = rect.left + rect.right;
            rect2.left = ((rect.height() / 2) + rect.top) - (i6 / 2);
            rect2.top = i3 - ((rect.height() + i6) / 2);
            rect2.right = rect.width() + rect2.left;
            rect2.bottom = rect.height() + rect2.top;
            return;
        }
        if (i != 4) {
            return;
        }
        int i7 = rect.left + rect.right;
        rect2.left = i2 - ((rect.width() + (rect.bottom + rect.top)) / 2);
        rect2.top = (i7 - rect.height()) / 2;
        rect2.right = rect.width() + rect2.left;
        rect2.bottom = rect.height() + rect2.top;
    }

    /* renamed from: a0 */
    public final void m214192a0(k80 k80Var) {
        this.f58821c2.add(k80Var);
    }

    /* renamed from: a1 */
    public final float m214193a1(float f, float[] fArr) {
        float f2 = 0.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else {
            float f3 = this.f58812b3;
            if (f3 != 1.0d) {
                float f4 = this.f58811b2;
                if (f < f4) {
                    f = 0.0f;
                }
                if (f > f4 && f < 1.0d) {
                    f = Math.min((f - f4) * f3, 1.0f);
                }
            }
        }
        C1347vr c1347vr = this.f58804a5.f60626a0;
        ArrayList arrayList = this.f58819c0;
        int size = arrayList.size();
        float f5 = Float.NaN;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            vg0 vg0Var = (vg0) obj;
            C1347vr c1347vr2 = vg0Var.f60626a0;
            if (c1347vr2 != null) {
                float f6 = vg0Var.f60628a2;
                if (f6 < f) {
                    c1347vr = c1347vr2;
                    f2 = f6;
                } else if (Float.isNaN(f5)) {
                    f5 = vg0Var.f60628a2;
                }
            }
        }
        if (c1347vr != null) {
            float f7 = (Float.isNaN(f5) ? 1.0f : f5) - f2;
            double d = (f - f2) / f7;
            f = (((float) c1347vr.mo210531a0(d)) * f7) + f2;
            if (fArr != null) {
                fArr[0] = (float) c1347vr.mo210532a1(d);
            }
        }
        return f;
    }

    /* renamed from: a2 */
    public final void m214194a2(double d, float[] fArr, float[] fArr2) {
        float f;
        double[] dArr = new double[4];
        double[] dArr2 = new double[4];
        this.f58808a9[0].mo210517c1(d, dArr);
        this.f58808a9[0].mo210520c4(d, dArr2);
        float f2 = 0.0f;
        Arrays.fill(fArr2, 0.0f);
        int[] iArr = this.f58813b4;
        vg0 vg0Var = this.f58804a5;
        float f3 = vg0Var.f60630a4;
        float f4 = vg0Var.f60631a5;
        float f5 = vg0Var.f60632a6;
        float f6 = vg0Var.f60633a7;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f10 = (float) dArr[i];
            float f11 = (float) dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                f3 = f10;
                f2 = f11;
            } else if (i2 == 2) {
                f4 = f10;
                f9 = f11;
            } else if (i2 == 3) {
                f5 = f10;
                f7 = f11;
            } else if (i2 == 4) {
                f6 = f10;
                f8 = f11;
            }
        }
        float fCos = (f7 / 2.0f) + f2;
        float fSin = (f8 / 2.0f) + f9;
        og0 og0Var = vg0Var.f60638b2;
        if (og0Var != null) {
            float[] fArr3 = new float[2];
            float[] fArr4 = new float[2];
            og0Var.m214194a2(d, fArr3, fArr4);
            float f12 = fArr3[0];
            float f13 = fArr3[1];
            float f14 = fArr4[0];
            float f15 = fArr4[1];
            double d2 = f3;
            double d3 = f4;
            float fSin2 = (float) (((Math.sin(d3) * d2) + f12) - (f5 / 2.0f));
            float fCos2 = (float) ((f13 - (Math.cos(d3) * d2)) - (f6 / 2.0f));
            double d4 = f2;
            double d5 = f9;
            f = 2.0f;
            f4 = fCos2;
            fCos = (float) ((Math.cos(d3) * d5) + (Math.sin(d3) * d4) + f14);
            fSin = (float) ((Math.sin(d3) * d5) + (f15 - (Math.cos(d3) * d4)));
            f3 = fSin2;
        } else {
            f = 2.0f;
        }
        fArr[0] = (f5 / f) + f3 + 0.0f;
        fArr[1] = (f6 / f) + f4 + 0.0f;
        fArr2[0] = fCos;
        fArr2[1] = fSin;
    }

    /* renamed from: a3 */
    public final void m214195a3(float f, float f2, float f3, float[] fArr) {
        double[] dArr;
        float[] fArr2 = this.f58820c1;
        float fM214193a1 = m214193a1(f, fArr2);
        b81[] b81VarArr = this.f58808a9;
        vg0 vg0Var = this.f58804a5;
        int i = 0;
        if (b81VarArr == null) {
            vg0 vg0Var2 = this.f58805a6;
            float f4 = vg0Var2.f60630a4 - vg0Var.f60630a4;
            float f5 = vg0Var2.f60631a5 - vg0Var.f60631a5;
            float f6 = vg0Var2.f60632a6 - vg0Var.f60632a6;
            float f7 = (vg0Var2.f60633a7 - vg0Var.f60633a7) + f5;
            fArr[0] = ((f6 + f4) * f2) + ((1.0f - f2) * f4);
            fArr[1] = (f7 * f3) + ((1.0f - f3) * f5);
            return;
        }
        double d = fM214193a1;
        b81VarArr[0].mo210520c4(d, this.f58815b6);
        this.f58808a9[0].mo210517c1(d, this.f58814b5);
        float f8 = fArr2[0];
        while (true) {
            dArr = this.f58815b6;
            if (i >= dArr.length) {
                break;
            }
            dArr[i] = dArr[i] * f8;
            i++;
        }
        C0110au c0110au = this.f58809b0;
        if (c0110au == null) {
            int[] iArr = this.f58813b4;
            double[] dArr2 = this.f58814b5;
            vg0Var.getClass();
            vg0.m214924a4(f2, f3, fArr, iArr, dArr, dArr2);
            return;
        }
        double[] dArr3 = this.f58814b5;
        if (dArr3.length > 0) {
            c0110au.mo210517c1(d, dArr3);
            this.f58809b0.mo210520c4(d, this.f58815b6);
            int[] iArr2 = this.f58813b4;
            double[] dArr4 = this.f58815b6;
            double[] dArr5 = this.f58814b5;
            vg0Var.getClass();
            vg0.m214924a4(f2, f3, fArr, iArr2, dArr4, dArr5);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a4 */
    public final boolean m214196a4(float f, long j, C1105qc c1105qc, View view) {
        boolean zMo214921a1;
        boolean z;
        float f2;
        xc1 xc1Var;
        boolean z2;
        double d;
        float f3;
        float f4;
        float f5;
        float fSin;
        float f6;
        xc1 xc1Var2 = null;
        float fM214193a1 = m214193a1(f, null);
        int i = this.f58828c9;
        if (i != -1) {
            float f7 = 1.0f / i;
            float fFloor = ((float) Math.floor(fM214193a1 / f7)) * f7;
            float f8 = (fM214193a1 % f7) / f7;
            if (!Float.isNaN(this.f58829d0)) {
                f8 = (f8 + this.f58829d0) % 1.0f;
            }
            Interpolator interpolator = this.f58830d1;
            fM214193a1 = ((interpolator != null ? interpolator.getInterpolation(f8) : ((double) f8) > 0.5d ? 1.0f : 0.0f) * f7) + fFloor;
        }
        HashMap map = this.f58823c4;
        if (map != null) {
            Iterator it = map.values().iterator();
            while (it.hasNext()) {
                ((tc1) it.next()).mo214245a2(view, fM214193a1);
            }
        }
        HashMap map2 = this.f58822c3;
        if (map2 != null) {
            xc1 xc1Var3 = null;
            zMo214921a1 = false;
            for (zc1 zc1Var : map2.values()) {
                if (zc1Var instanceof xc1) {
                    xc1Var3 = (xc1) zc1Var;
                } else {
                    zMo214921a1 |= zc1Var.mo214921a1(fM214193a1, j, c1105qc, view);
                }
            }
            xc1Var2 = xc1Var3;
        } else {
            zMo214921a1 = false;
        }
        b81[] b81VarArr = this.f58808a9;
        vg0 vg0Var = this.f58804a5;
        if (b81VarArr != null) {
            double d2 = fM214193a1;
            b81VarArr[0].mo210517c1(d2, this.f58814b5);
            this.f58808a9[0].mo210520c4(d2, this.f58815b6);
            C0110au c0110au = this.f58809b0;
            if (c0110au != null) {
                double[] dArr = this.f58814b5;
                f2 = 1.0f;
                if (dArr.length > 0) {
                    c0110au.mo210517c1(d2, dArr);
                    this.f58809b0.mo210520c4(d2, this.f58815b6);
                }
            } else {
                f2 = 1.0f;
            }
            if (this.f58831d2) {
                xc1Var = xc1Var2;
                z2 = zMo214921a1;
                d = d2;
                f3 = 2.0f;
            } else {
                int[] iArr = this.f58813b4;
                double[] dArr2 = this.f58814b5;
                f3 = 2.0f;
                double[] dArr3 = this.f58815b6;
                boolean z3 = this.f58802a3;
                float f9 = vg0Var.f60630a4;
                float f10 = vg0Var.f60631a5;
                float f11 = vg0Var.f60632a6;
                int i2 = 1;
                float f12 = vg0Var.f60633a7;
                xc1Var = xc1Var2;
                if (iArr.length != 0) {
                    f4 = f10;
                    if (vg0Var.f60641b5.length <= iArr[iArr.length - 1]) {
                        int i3 = iArr[iArr.length - 1] + 1;
                        vg0Var.f60641b5 = new double[i3];
                        vg0Var.f60642b6 = new double[i3];
                    }
                } else {
                    f4 = f10;
                }
                Arrays.fill(vg0Var.f60641b5, Double.NaN);
                for (int i4 = 0; i4 < iArr.length; i4++) {
                    double[] dArr4 = vg0Var.f60641b5;
                    int i5 = iArr[i4];
                    dArr4[i5] = dArr2[i4];
                    vg0Var.f60642b6[i5] = dArr3[i4];
                }
                float f13 = Float.NaN;
                int i6 = 0;
                float fCos = f4;
                float f14 = f11;
                float f15 = 0.0f;
                float f16 = 0.0f;
                float f17 = 0.0f;
                float f18 = 0.0f;
                while (true) {
                    double[] dArr5 = vg0Var.f60641b5;
                    f5 = f12;
                    if (i6 >= dArr5.length) {
                        break;
                    }
                    if (Double.isNaN(dArr5[i6])) {
                        f6 = f9;
                    } else {
                        f6 = f9;
                        float f19 = (float) (Double.isNaN(vg0Var.f60641b5[i6]) ? 0.0d : vg0Var.f60641b5[i6] + 0.0d);
                        float f20 = (float) vg0Var.f60642b6[i6];
                        if (i6 == i2) {
                            f16 = f20;
                            f12 = f5;
                            f9 = f19;
                        } else if (i6 == 2) {
                            f15 = f20;
                            f9 = f6;
                            f12 = f5;
                            fCos = f19;
                        } else if (i6 == 3) {
                            f17 = f20;
                            f9 = f6;
                            f12 = f5;
                            f14 = f19;
                        } else if (i6 == 4) {
                            f18 = f20;
                            f9 = f6;
                            f12 = f19;
                        } else if (i6 == 5) {
                            f9 = f6;
                            f12 = f5;
                            f13 = f19;
                        }
                        i6++;
                        i2 = 1;
                    }
                    f9 = f6;
                    f12 = f5;
                    i6++;
                    i2 = 1;
                }
                float f21 = f9;
                og0 og0Var = vg0Var.f60638b2;
                if (og0Var != null) {
                    float[] fArr = new float[2];
                    float[] fArr2 = new float[2];
                    og0Var.m214194a2(d2, fArr, fArr2);
                    float f22 = fArr[0];
                    float f23 = fArr[1];
                    float f24 = fArr2[0];
                    float f25 = fArr2[1];
                    z2 = zMo214921a1;
                    d = d2;
                    double d3 = f21;
                    double d4 = fCos;
                    fSin = (float) (((Math.sin(d4) * d3) + f22) - (f14 / 2.0f));
                    fCos = (float) ((f23 - (Math.cos(d4) * d3)) - (f5 / 2.0f));
                    double d5 = f16;
                    double d6 = f15;
                    float fCos2 = (float) ((Math.cos(d4) * d3 * d6) + (Math.sin(d4) * d5) + f24);
                    float fSin2 = (float) ((Math.sin(d4) * d3 * d6) + (f25 - (Math.cos(d4) * d5)));
                    if (dArr3.length >= 2) {
                        dArr3[0] = fCos2;
                        dArr3[1] = fSin2;
                    }
                    if (!Float.isNaN(f13)) {
                        view.setRotation((float) (Math.toDegrees(Math.atan2(fSin2, fCos2)) + f13));
                    }
                } else {
                    fSin = f21;
                    z2 = zMo214921a1;
                    d = d2;
                    if (!Float.isNaN(f13)) {
                        view.setRotation((float) (Math.toDegrees(Math.atan2((f18 / 2.0f) + f15, (f17 / 2.0f) + f16)) + f13 + 0.0f));
                    }
                }
                float f26 = fSin;
                if (view instanceof InterfaceC1525zp) {
                    ((MotionLabel) ((InterfaceC1525zp) view)).m210033a2(f26, fCos, f26 + f14, fCos + f5);
                } else {
                    float f27 = f26 + 0.5f;
                    int i7 = (int) f27;
                    float f28 = fCos + 0.5f;
                    int i8 = (int) f28;
                    int i9 = (int) (f27 + f14);
                    int i10 = (int) (f28 + f5);
                    int i11 = i9 - i7;
                    int i12 = i10 - i8;
                    if (i11 != view.getMeasuredWidth() || i12 != view.getMeasuredHeight() || z3) {
                        view.measure(View.MeasureSpec.makeMeasureSpec(i11, 1073741824), View.MeasureSpec.makeMeasureSpec(i12, 1073741824));
                    }
                    view.layout(i7, i8, i9, i10);
                }
                this.f58802a3 = false;
            }
            if (this.f58826c7 != -1) {
                if (this.f58827c8 == null) {
                    this.f58827c8 = ((View) view.getParent()).findViewById(this.f58826c7);
                }
                if (this.f58827c8 != null) {
                    float bottom = (this.f58827c8.getBottom() + r1.getTop()) / f3;
                    float right = (this.f58827c8.getRight() + this.f58827c8.getLeft()) / f3;
                    if (view.getRight() - view.getLeft() > 0 && view.getBottom() - view.getTop() > 0) {
                        view.setPivotX(right - view.getLeft());
                        view.setPivotY(bottom - view.getTop());
                    }
                }
            }
            HashMap map3 = this.f58823c4;
            if (map3 != null) {
                for (tc1 tc1Var : map3.values()) {
                    if (tc1Var instanceof rc1) {
                        double[] dArr6 = this.f58815b6;
                        if (dArr6.length > 1) {
                            view.setRotation(((rc1) tc1Var).m214736a0(fM214193a1) + ((float) Math.toDegrees(Math.atan2(dArr6[1], dArr6[0]))));
                        }
                    }
                }
            }
            if (xc1Var != null) {
                double[] dArr7 = this.f58815b6;
                double d7 = dArr7[0];
                double d8 = dArr7[1];
                xc1 xc1Var4 = xc1Var;
                view.setRotation(xc1Var4.m215392a0(fM214193a1, j, c1105qc, view) + ((float) Math.toDegrees(Math.atan2(d8, d7))));
                z = z2 | xc1Var4.f61504a3;
            } else {
                z = z2;
            }
            int i13 = 1;
            while (true) {
                b81[] b81VarArr2 = this.f58808a9;
                if (i13 >= b81VarArr2.length) {
                    break;
                }
                b81 b81Var = b81VarArr2[i13];
                float[] fArr3 = this.f58818b9;
                b81Var.mo210518c2(d, fArr3);
                kg1.m213540e9((C0798kw) vg0Var.f60639b3.get(this.f58816b7[i13 - 1]), view, fArr3);
                i13++;
            }
            mg0 mg0Var = this.f58806a7;
            if (mg0Var.f58356a1 == 0) {
                if (fM214193a1 <= 0.0f) {
                    view.setVisibility(mg0Var.f58357a2);
                } else {
                    mg0 mg0Var2 = this.f58807a8;
                    if (fM214193a1 >= f2) {
                        view.setVisibility(mg0Var2.f58357a2);
                    } else if (mg0Var2.f58357a2 != mg0Var.f58357a2) {
                        view.setVisibility(0);
                    }
                }
            }
        } else {
            boolean z4 = zMo214921a1;
            float f29 = vg0Var.f60630a4;
            vg0 vg0Var2 = this.f58805a6;
            float fM19a0 = AbstractC0003a2.m19a0(vg0Var2.f60630a4, f29, fM214193a1, f29);
            float f30 = vg0Var.f60631a5;
            float fM19a02 = AbstractC0003a2.m19a0(vg0Var2.f60631a5, f30, fM214193a1, f30);
            float f31 = vg0Var.f60632a6;
            float f32 = vg0Var2.f60632a6;
            float fM19a03 = AbstractC0003a2.m19a0(f32, f31, fM214193a1, f31);
            float f33 = vg0Var.f60633a7;
            float f34 = vg0Var2.f60633a7;
            float f35 = fM19a0 + 0.5f;
            int i14 = (int) f35;
            float f36 = fM19a02 + 0.5f;
            int i15 = (int) f36;
            int i16 = (int) (f35 + fM19a03);
            int iM19a0 = (int) (f36 + AbstractC0003a2.m19a0(f34, f33, fM214193a1, f33));
            int i17 = i16 - i14;
            int i18 = iM19a0 - i15;
            if (f32 != f31 || f34 != f33 || this.f58802a3) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i17, 1073741824), View.MeasureSpec.makeMeasureSpec(i18, 1073741824));
                this.f58802a3 = false;
            }
            view.layout(i14, i15, i16, iM19a0);
            z = z4;
        }
        HashMap map4 = this.f58824c5;
        if (map4 != null) {
            for (zb1 zb1Var : map4.values()) {
                if (zb1Var instanceof xb1) {
                    double[] dArr8 = this.f58815b6;
                    view.setRotation(((xb1) zb1Var).m215389a0(fM214193a1) + ((float) Math.toDegrees(Math.atan2(dArr8[1], dArr8[0]))));
                } else {
                    zb1Var.mo214920a3(view, fM214193a1);
                }
            }
        }
        return z;
    }

    /* renamed from: a5 */
    public final void m214197a5(vg0 vg0Var) {
        vg0Var.m214927a3((int) this.f58800a1.getX(), (int) this.f58800a1.getY(), this.f58800a1.getWidth(), this.f58800a1.getHeight());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:508:0x0d1a. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:314:0x09d7  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x0d8c  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0d94  */
    /* JADX WARN: Type inference failed for: r0v79, types: [yc1, zc1] */
    /* JADX WARN: Type inference failed for: r0v84, types: [zc1] */
    /* JADX WARN: Type inference failed for: r0v96, types: [wc1, zc1] */
    /* JADX WARN: Type inference failed for: r3v89, types: [qc1, tc1] */
    /* JADX WARN: Type inference failed for: r4v53, types: [sc1, tc1] */
    /* JADX WARN: Type inference failed for: r4v61, types: [tc1] */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214198a7(long j, int i, int i2) {
        Object obj;
        HashMap map;
        ArrayList arrayList;
        String str;
        String str2;
        String str3;
        String str4;
        HashSet hashSet;
        String str5;
        vg0 vg0Var;
        Object obj2;
        vg0 vg0Var2;
        Object obj3;
        ArrayList arrayList2;
        Object obj4;
        String str6;
        Object obj5;
        String str7;
        og0 og0Var;
        ArrayList arrayList3;
        int i3;
        String str8;
        int i4;
        C0798kw c0798kw;
        String str9;
        HashSet hashSet2;
        Iterator it;
        HashMap map2;
        char c;
        vc1 vc1Var;
        vc1 vc1Var2;
        vc1 vc1Var3;
        C0798kw c0798kw2;
        Integer num;
        int i5;
        char c2;
        Iterator it2;
        String str10;
        String str11;
        String str12;
        String str13;
        HashSet hashSet3;
        String str14;
        vg0 vg0Var3;
        Object obj6;
        vg0 vg0Var4;
        Object obj7;
        ArrayList arrayList4;
        Object obj8;
        String str15;
        Object obj9;
        char c3;
        char c4;
        char c5;
        HashMap map3;
        String str16;
        pc1 pc1Var;
        pc1 pc1Var2;
        C0798kw c0798kw3;
        String str17;
        String str18;
        String str19;
        String str20;
        String str21;
        float fMin;
        float fM19a0;
        og0 og0Var2 = this;
        new HashSet();
        HashSet hashSet4 = new HashSet();
        HashSet hashSet5 = new HashSet();
        HashSet hashSet6 = new HashSet();
        HashMap map4 = new HashMap();
        int i6 = og0Var2.f58825c6;
        vg0 vg0Var5 = og0Var2.f58804a5;
        if (i6 != -1) {
            vg0Var5.f60635a9 = i6;
        }
        mg0 mg0Var = og0Var2.f58806a7;
        float f = mg0Var.f58355a0;
        mg0 mg0Var2 = og0Var2.f58807a8;
        if (mg0.m213999a1(f, mg0Var2.f58355a0)) {
            hashSet5.add("alpha");
        }
        String str22 = "elevation";
        if (mg0.m213999a1(mg0Var.f58358a3, mg0Var2.f58358a3)) {
            hashSet5.add("elevation");
        }
        int i7 = mg0Var.f58357a2;
        int i8 = mg0Var2.f58357a2;
        if (i7 != i8 && mg0Var.f58356a1 == 0 && (i7 == 0 || i8 == 0)) {
            hashSet5.add("alpha");
        }
        String str23 = "rotation";
        if (mg0.m213999a1(mg0Var.f58359a4, mg0Var2.f58359a4)) {
            hashSet5.add("rotation");
        }
        if (!Float.isNaN(mg0Var.f58369b4) || !Float.isNaN(mg0Var2.f58369b4)) {
            hashSet5.add("transitionPathRotate");
        }
        String str24 = "progress";
        if (!Float.isNaN(mg0Var.f58370b5) || !Float.isNaN(mg0Var2.f58370b5)) {
            hashSet5.add("progress");
        }
        if (mg0.m213999a1(mg0Var.f58360a5, mg0Var2.f58360a5)) {
            hashSet5.add("rotationX");
        }
        if (mg0.m213999a1(mg0Var.f58361a6, mg0Var2.f58361a6)) {
            hashSet5.add("rotationY");
        }
        if (mg0.m213999a1(mg0Var.f58364a9, mg0Var2.f58364a9)) {
            hashSet5.add("transformPivotX");
        }
        if (mg0.m213999a1(mg0Var.f58365b0, mg0Var2.f58365b0)) {
            hashSet5.add("transformPivotY");
        }
        String str25 = "scaleX";
        if (mg0.m213999a1(mg0Var.f58362a7, mg0Var2.f58362a7)) {
            hashSet5.add("scaleX");
        }
        String str26 = "scaleY";
        if (mg0.m213999a1(mg0Var.f58363a8, mg0Var2.f58363a8)) {
            hashSet5.add("scaleY");
        }
        Object obj10 = "rotationX";
        if (mg0.m213999a1(mg0Var.f58366b1, mg0Var2.f58366b1)) {
            hashSet5.add("translationX");
        }
        Object obj11 = "rotationY";
        if (mg0.m213999a1(mg0Var.f58367b2, mg0Var2.f58367b2)) {
            hashSet5.add("translationY");
        }
        if (mg0.m213999a1(mg0Var.f58368b3, mg0Var2.f58368b3)) {
            hashSet5.add("translationZ");
        }
        vg0 vg0Var6 = og0Var2.f58805a6;
        ArrayList arrayList5 = og0Var2.f58819c0;
        ArrayList arrayList6 = og0Var2.f58821c2;
        Object obj12 = "translationX";
        if (arrayList6 != null) {
            int size = arrayList6.size();
            obj = "translationY";
            int i9 = 0;
            while (i9 < size) {
                Object obj13 = arrayList6.get(i9);
                int i10 = i9 + 1;
                k80 k80Var = (k80) obj13;
                int i11 = size;
                if (k80Var instanceof v80) {
                    v80 v80Var = (v80) k80Var;
                    vg0 vg0Var7 = new vg0();
                    str21 = str24;
                    vg0Var7.f60627a1 = 0;
                    vg0Var7.f60634a8 = Float.NaN;
                    vg0Var7.f60635a9 = -1;
                    vg0Var7.f60636b0 = -1;
                    vg0Var7.f60637b1 = Float.NaN;
                    vg0Var7.f60638b2 = null;
                    vg0Var7.f60639b3 = new LinkedHashMap();
                    vg0Var7.f60640b4 = 0;
                    str18 = str25;
                    vg0Var7.f60641b5 = new double[18];
                    vg0Var7.f60642b6 = new double[18];
                    if (vg0Var5.f60636b0 != -1) {
                        float f2 = v80Var.f57482a0 / 100.0f;
                        vg0Var7.f60628a2 = f2;
                        vg0Var7.f60627a1 = v80Var.f60602a7;
                        vg0Var7.f60640b4 = v80Var.f60607b2;
                        float f3 = Float.isNaN(v80Var.f60603a8) ? f2 : v80Var.f60603a8;
                        str17 = str26;
                        float f4 = Float.isNaN(v80Var.f60604a9) ? f2 : v80Var.f60604a9;
                        str20 = str23;
                        float f5 = vg0Var6.f60632a6 - vg0Var5.f60632a6;
                        float f6 = vg0Var6.f60633a7;
                        float f7 = vg0Var5.f60633a7;
                        vg0Var7.f60629a3 = vg0Var7.f60628a2;
                        vg0Var7.f60632a6 = (int) ((f5 * f3) + r12);
                        vg0Var7.f60633a7 = (int) (((f6 - f7) * f4) + f7);
                        int i12 = v80Var.f60607b2;
                        str19 = str22;
                        if (i12 == 1) {
                            float f8 = Float.isNaN(v80Var.f60605b0) ? f2 : v80Var.f60605b0;
                            float f9 = vg0Var6.f60630a4;
                            float f10 = vg0Var5.f60630a4;
                            vg0Var7.f60630a4 = AbstractC0003a2.m19a0(f9, f10, f8, f10);
                            if (!Float.isNaN(v80Var.f60606b1)) {
                                f2 = v80Var.f60606b1;
                            }
                            float f11 = vg0Var6.f60631a5;
                            float f12 = vg0Var5.f60631a5;
                            vg0Var7.f60631a5 = AbstractC0003a2.m19a0(f11, f12, f2, f12);
                        } else if (i12 != 2) {
                            float f13 = Float.isNaN(v80Var.f60605b0) ? f2 : v80Var.f60605b0;
                            float f14 = vg0Var6.f60630a4;
                            float f15 = vg0Var5.f60630a4;
                            vg0Var7.f60630a4 = AbstractC0003a2.m19a0(f14, f15, f13, f15);
                            if (!Float.isNaN(v80Var.f60606b1)) {
                                f2 = v80Var.f60606b1;
                            }
                            float f16 = vg0Var6.f60631a5;
                            float f17 = vg0Var5.f60631a5;
                            vg0Var7.f60631a5 = AbstractC0003a2.m19a0(f16, f17, f2, f17);
                        } else {
                            if (Float.isNaN(v80Var.f60605b0)) {
                                float f18 = vg0Var6.f60630a4;
                                float f19 = vg0Var5.f60630a4;
                                fMin = AbstractC0003a2.m19a0(f18, f19, f2, f19);
                            } else {
                                fMin = Math.min(f4, f3) * v80Var.f60605b0;
                            }
                            vg0Var7.f60630a4 = fMin;
                            if (Float.isNaN(v80Var.f60606b1)) {
                                float f20 = vg0Var6.f60631a5;
                                float f21 = vg0Var5.f60631a5;
                                fM19a0 = AbstractC0003a2.m19a0(f20, f21, f2, f21);
                            } else {
                                fM19a0 = v80Var.f60606b1;
                            }
                            vg0Var7.f60631a5 = fM19a0;
                        }
                        vg0Var7.f60636b0 = vg0Var5.f60636b0;
                        vg0Var7.f60626a0 = C1347vr.m214949a2(v80Var.f60600a5);
                        vg0Var7.f60635a9 = v80Var.f60601a6;
                    } else {
                        str17 = str26;
                        str19 = str22;
                        str20 = str23;
                        int i13 = v80Var.f60607b2;
                        if (i13 == 1) {
                            float f22 = v80Var.f57482a0 / 100.0f;
                            vg0Var7.f60628a2 = f22;
                            vg0Var7.f60627a1 = v80Var.f60602a7;
                            float f23 = Float.isNaN(v80Var.f60603a8) ? f22 : v80Var.f60603a8;
                            float f24 = Float.isNaN(v80Var.f60604a9) ? f22 : v80Var.f60604a9;
                            float f25 = vg0Var6.f60632a6 - vg0Var5.f60632a6;
                            float f26 = vg0Var6.f60633a7 - vg0Var5.f60633a7;
                            vg0Var7.f60629a3 = vg0Var7.f60628a2;
                            if (!Float.isNaN(v80Var.f60605b0)) {
                                f22 = v80Var.f60605b0;
                            }
                            float f27 = (vg0Var5.f60632a6 / 2.0f) + vg0Var5.f60630a4;
                            float f28 = vg0Var5.f60631a5;
                            float f29 = vg0Var5.f60633a7;
                            float f30 = ((vg0Var6.f60632a6 / 2.0f) + vg0Var6.f60630a4) - f27;
                            float f31 = ((vg0Var6.f60633a7 / 2.0f) + vg0Var6.f60631a5) - ((f29 / 2.0f) + f28);
                            float f32 = f30 * f22;
                            float f33 = (f23 * f25) / 2.0f;
                            vg0Var7.f60630a4 = (int) ((r3 + f32) - f33);
                            float f34 = f22 * f31;
                            float f35 = (f24 * f26) / 2.0f;
                            vg0Var7.f60631a5 = (int) ((f28 + f34) - f35);
                            vg0Var7.f60632a6 = (int) (r3 + r12);
                            vg0Var7.f60633a7 = (int) (f29 + r14);
                            float f36 = Float.isNaN(v80Var.f60606b1) ? 0.0f : v80Var.f60606b1;
                            vg0Var7.f60640b4 = 1;
                            float f37 = (int) ((vg0Var5.f60630a4 + f32) - f33);
                            float f38 = (int) ((vg0Var5.f60631a5 + f34) - f35);
                            vg0Var7.f60630a4 = f37 + ((-f31) * f36);
                            vg0Var7.f60631a5 = f38 + (f30 * f36);
                            vg0Var7.f60636b0 = vg0Var7.f60636b0;
                            vg0Var7.f60626a0 = C1347vr.m214949a2(v80Var.f60600a5);
                            vg0Var7.f60635a9 = v80Var.f60601a6;
                        } else if (i13 != 2) {
                            float f39 = v80Var.f57482a0 / 100.0f;
                            vg0Var7.f60628a2 = f39;
                            vg0Var7.f60627a1 = v80Var.f60602a7;
                            float f40 = Float.isNaN(v80Var.f60603a8) ? f39 : v80Var.f60603a8;
                            float f41 = Float.isNaN(v80Var.f60604a9) ? f39 : v80Var.f60604a9;
                            float f42 = vg0Var6.f60632a6;
                            float f43 = vg0Var5.f60632a6;
                            float f44 = f42 - f43;
                            float f45 = vg0Var6.f60633a7;
                            float f46 = vg0Var5.f60633a7;
                            float f47 = f45 - f46;
                            vg0Var7.f60629a3 = vg0Var7.f60628a2;
                            float f48 = (f43 / 2.0f) + vg0Var5.f60630a4;
                            float f49 = vg0Var5.f60631a5;
                            float f50 = ((f42 / 2.0f) + vg0Var6.f60630a4) - f48;
                            float f51 = ((f45 / 2.0f) + vg0Var6.f60631a5) - ((f46 / 2.0f) + f49);
                            float f52 = (f44 * f40) / 2.0f;
                            vg0Var7.f60630a4 = (int) (((f50 * f39) + r3) - f52);
                            float f53 = (f47 * f41) / 2.0f;
                            vg0Var7.f60631a5 = (int) (((f51 * f39) + f49) - f53);
                            vg0Var7.f60632a6 = (int) (f43 + r33);
                            vg0Var7.f60633a7 = (int) (f46 + r41);
                            float f54 = Float.isNaN(v80Var.f60605b0) ? f39 : v80Var.f60605b0;
                            float f55 = Float.isNaN(Float.NaN) ? 0.0f : Float.NaN;
                            float f56 = f54;
                            float f57 = Float.isNaN(v80Var.f60606b1) ? f39 : v80Var.f60606b1;
                            float f58 = Float.isNaN(Float.NaN) ? 0.0f : Float.NaN;
                            vg0Var7.f60640b4 = 0;
                            vg0Var7.f60630a4 = (int) (((f58 * f51) + ((f56 * f50) + vg0Var5.f60630a4)) - f52);
                            vg0Var7.f60631a5 = (int) (((f51 * f57) + ((f50 * f55) + vg0Var5.f60631a5)) - f53);
                            vg0Var7.f60626a0 = C1347vr.m214949a2(v80Var.f60600a5);
                            vg0Var7.f60635a9 = v80Var.f60601a6;
                        } else {
                            float f59 = v80Var.f57482a0 / 100.0f;
                            vg0Var7.f60628a2 = f59;
                            vg0Var7.f60627a1 = v80Var.f60602a7;
                            float f60 = Float.isNaN(v80Var.f60603a8) ? f59 : v80Var.f60603a8;
                            float f61 = Float.isNaN(v80Var.f60604a9) ? f59 : v80Var.f60604a9;
                            float f62 = vg0Var6.f60632a6;
                            float f63 = vg0Var5.f60632a6;
                            float f64 = f62 - f63;
                            float f65 = vg0Var6.f60633a7;
                            float f66 = vg0Var5.f60633a7;
                            float f67 = f65 - f66;
                            vg0Var7.f60629a3 = vg0Var7.f60628a2;
                            float f68 = (f63 / 2.0f) + vg0Var5.f60630a4;
                            float f69 = vg0Var5.f60631a5;
                            float f70 = (f62 / 2.0f) + vg0Var6.f60630a4;
                            float f71 = ((f65 / 2.0f) + vg0Var6.f60631a5) - ((f66 / 2.0f) + f69);
                            float f72 = f64 * f60;
                            vg0Var7.f60630a4 = (int) ((((f70 - f68) * f59) + r3) - (f72 / 2.0f));
                            float f73 = f67 * f61;
                            vg0Var7.f60631a5 = (int) (((f71 * f59) + f69) - (f73 / 2.0f));
                            vg0Var7.f60632a6 = (int) (f63 + f72);
                            vg0Var7.f60633a7 = (int) (f66 + f73);
                            vg0Var7.f60640b4 = 2;
                            if (!Float.isNaN(v80Var.f60605b0)) {
                                vg0Var7.f60630a4 = (int) (v80Var.f60605b0 * ((int) (i - vg0Var7.f60632a6)));
                            }
                            if (!Float.isNaN(v80Var.f60606b1)) {
                                vg0Var7.f60631a5 = (int) (v80Var.f60606b1 * ((int) (i2 - vg0Var7.f60633a7)));
                            }
                            vg0Var7.f60636b0 = vg0Var7.f60636b0;
                            vg0Var7.f60626a0 = C1347vr.m214949a2(v80Var.f60600a5);
                            vg0Var7.f60635a9 = v80Var.f60601a6;
                        }
                    }
                    arrayList5.add((-Collections.binarySearch(arrayList5, vg0Var7)) - 1, vg0Var7);
                    int i14 = v80Var.f60599a4;
                    if (i14 != -1) {
                        og0Var2.f58803a4 = i14;
                    }
                } else {
                    str17 = str26;
                    str18 = str25;
                    str19 = str22;
                    str20 = str23;
                    str21 = str24;
                    k80Var.mo213475a3(map4);
                    k80Var.mo213473a1(hashSet5);
                }
                size = i11;
                i9 = i10;
                str24 = str21;
                str25 = str18;
                str26 = str17;
                str23 = str20;
                str22 = str19;
            }
        } else {
            obj = "translationY";
        }
        String str27 = str26;
        String str28 = str25;
        String str29 = str22;
        String str30 = str23;
        String str31 = str24;
        String str32 = "CUSTOM,";
        String str33 = ",";
        if (hashSet5.isEmpty()) {
            map = map4;
            arrayList = arrayList6;
            str = str28;
            str2 = str27;
            str3 = str30;
            str4 = str29;
            hashSet = hashSet5;
            str5 = str31;
            vg0Var = vg0Var5;
            obj2 = obj;
            vg0Var2 = vg0Var6;
            obj3 = obj12;
            arrayList2 = arrayList5;
            obj4 = obj11;
            str6 = ",";
            obj5 = obj10;
            str7 = "CUSTOM,";
        } else {
            og0Var2.f58823c4 = new HashMap();
            Iterator it3 = hashSet5.iterator();
            while (it3.hasNext()) {
                String str34 = (String) it3.next();
                if (!str34.startsWith(str32)) {
                    it2 = it3;
                    switch (str34.hashCode()) {
                        case -1249320806:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            if (str34.equals(obj9)) {
                                c3 = 0;
                                break;
                            } else {
                                c3 = 65535;
                                break;
                            }
                        case -1249320805:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            if (str34.equals(obj8)) {
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 1;
                                break;
                            }
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -1225497657:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            if (str34.equals(obj7)) {
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 2;
                                break;
                            }
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -1225497656:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            if (str34.equals(obj6)) {
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 3;
                                break;
                            }
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -1225497655:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            if (str34.equals("translationZ")) {
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 4;
                                break;
                            }
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -1001078227:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            if (str34.equals(str14)) {
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 5;
                                break;
                            }
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -908189618:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            if (str34.equals(str10)) {
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 6;
                                break;
                            }
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -908189617:
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            if (str34.equals(str11)) {
                                str10 = str28;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 7;
                                break;
                            } else {
                                str10 = str28;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 65535;
                                break;
                            }
                        case -797520672:
                            str12 = str30;
                            str13 = str29;
                            if (str34.equals("waveVariesBy")) {
                                str10 = str28;
                                str11 = str27;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = '\b';
                                break;
                            }
                            str10 = str28;
                            str11 = str27;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -760884510:
                            str12 = str30;
                            str13 = str29;
                            if (str34.equals("transformPivotX")) {
                                str10 = str28;
                                str11 = str27;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = '\t';
                                break;
                            }
                            str10 = str28;
                            str11 = str27;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -760884509:
                            str12 = str30;
                            str13 = str29;
                            if (str34.equals("transformPivotY")) {
                                c4 = '\n';
                                str10 = str28;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = c4;
                                str11 = str27;
                                break;
                            }
                            str10 = str28;
                            str11 = str27;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -40300674:
                            str12 = str30;
                            str13 = str29;
                            if (str34.equals(str12)) {
                                c4 = 11;
                                str10 = str28;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = c4;
                                str11 = str27;
                                break;
                            }
                            str10 = str28;
                            str11 = str27;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case -4379043:
                            str13 = str29;
                            if (str34.equals(str13)) {
                                str10 = str28;
                                str11 = str27;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = '\f';
                                str12 = str30;
                                break;
                            } else {
                                str10 = str28;
                                str11 = str27;
                                str12 = str30;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = 65535;
                                break;
                            }
                        case 37232917:
                            if (str34.equals("transitionPathRotate")) {
                                c5 = '\r';
                                str10 = str28;
                                str11 = str27;
                                str12 = str30;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = c5;
                                str13 = str29;
                                break;
                            }
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case 92909918:
                            if (str34.equals("alpha")) {
                                c5 = 14;
                                str10 = str28;
                                str11 = str27;
                                str12 = str30;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = c5;
                                str13 = str29;
                                break;
                            }
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        case 156108012:
                            if (str34.equals("waveOffset")) {
                                c5 = 15;
                                str10 = str28;
                                str11 = str27;
                                str12 = str30;
                                hashSet3 = hashSet5;
                                str14 = str31;
                                vg0Var3 = vg0Var5;
                                obj6 = obj;
                                vg0Var4 = vg0Var6;
                                obj7 = obj12;
                                arrayList4 = arrayList5;
                                obj8 = obj11;
                                str15 = str33;
                                obj9 = obj10;
                                c3 = c5;
                                str13 = str29;
                                break;
                            }
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                        default:
                            str10 = str28;
                            str11 = str27;
                            str12 = str30;
                            str13 = str29;
                            hashSet3 = hashSet5;
                            str14 = str31;
                            vg0Var3 = vg0Var5;
                            obj6 = obj;
                            vg0Var4 = vg0Var6;
                            obj7 = obj12;
                            arrayList4 = arrayList5;
                            obj8 = obj11;
                            str15 = str33;
                            obj9 = obj10;
                            c3 = 65535;
                            break;
                    }
                    switch (c3) {
                        case 0:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(5);
                            break;
                        case 1:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(6);
                            break;
                        case 2:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(9);
                            break;
                        case 3:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(10);
                            break;
                        case 4:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(11);
                            break;
                        case 5:
                            map3 = map4;
                            str16 = str32;
                            ?? sc1Var = new sc1();
                            sc1Var.f59954a5 = false;
                            pc1Var2 = sc1Var;
                            break;
                        case 6:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(7);
                            break;
                        case 7:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(8);
                            break;
                        case '\b':
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(0);
                            break;
                        case '\t':
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(2);
                            break;
                        case '\n':
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(3);
                            break;
                        case oe0.DEFAULT_M /* 11 */:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(4);
                            break;
                        case FileClientSessionCache.MAX_SIZE /* 12 */:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new pc1(1);
                            break;
                        case '\r':
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = new rc1();
                            break;
                        case 14:
                            map3 = map4;
                            str16 = str32;
                            pc1Var = new pc1(0);
                            pc1Var2 = pc1Var;
                            break;
                        case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                            str16 = str32;
                            map3 = map4;
                            pc1Var = new pc1(0);
                            pc1Var2 = pc1Var;
                            break;
                        default:
                            map3 = map4;
                            str16 = str32;
                            pc1Var2 = null;
                            break;
                    }
                } else {
                    SparseArray sparseArray = new SparseArray();
                    String str35 = str34.split(str33)[1];
                    int size2 = arrayList6.size();
                    it2 = it3;
                    int i15 = 0;
                    while (i15 < size2) {
                        Object obj14 = arrayList6.get(i15);
                        int i16 = i15 + 1;
                        k80 k80Var2 = (k80) obj14;
                        int i17 = size2;
                        HashMap map5 = k80Var2.f57485a3;
                        if (map5 != null && (c0798kw3 = (C0798kw) map5.get(str35)) != null) {
                            sparseArray.append(k80Var2.f57482a0, c0798kw3);
                        }
                        size2 = i17;
                        i15 = i16;
                    }
                    ?? qc1Var = new qc1();
                    String str36 = str34.split(str33)[1];
                    qc1Var.f59465a5 = sparseArray;
                    str10 = str28;
                    str11 = str27;
                    str12 = str30;
                    hashSet3 = hashSet5;
                    map3 = map4;
                    str14 = str31;
                    pc1Var2 = qc1Var;
                    vg0Var3 = vg0Var5;
                    obj6 = obj;
                    str13 = str29;
                    vg0Var4 = vg0Var6;
                    obj7 = obj12;
                    arrayList4 = arrayList5;
                    obj8 = obj11;
                    str15 = str33;
                    obj9 = obj10;
                    str16 = str32;
                }
                if (pc1Var2 != null) {
                    pc1Var2.f60204a4 = str34;
                    og0Var2.f58823c4.put(str34, pc1Var2);
                }
                str29 = str13;
                str30 = str12;
                str32 = str16;
                map4 = map3;
                it3 = it2;
                obj10 = obj9;
                str27 = str11;
                str33 = str15;
                obj11 = obj8;
                arrayList5 = arrayList4;
                obj12 = obj7;
                vg0Var6 = vg0Var4;
                obj = obj6;
                vg0Var5 = vg0Var3;
                str31 = str14;
                hashSet5 = hashSet3;
                str28 = str10;
            }
            str = str28;
            str2 = str27;
            str3 = str30;
            str4 = str29;
            hashSet = hashSet5;
            HashMap map6 = map4;
            str5 = str31;
            vg0Var = vg0Var5;
            obj2 = obj;
            vg0Var2 = vg0Var6;
            obj3 = obj12;
            arrayList2 = arrayList5;
            obj4 = obj11;
            str6 = str33;
            obj5 = obj10;
            str7 = str32;
            if (arrayList6 != null) {
                int size3 = arrayList6.size();
                for (int i18 = 0; i18 < size3; i18 = i5) {
                    Object obj15 = arrayList6.get(i18);
                    i5 = i18 + 1;
                    k80 k80Var3 = (k80) obj15;
                    int i19 = size3;
                    if (k80Var3 instanceof m80) {
                        HashMap map7 = og0Var2.f58823c4;
                        m80 m80Var = (m80) k80Var3;
                        for (String str37 : map7.keySet()) {
                            int i20 = i5;
                            HashMap map8 = map7;
                            tc1 tc1Var = (tc1) map7.get(str37);
                            if (tc1Var != null) {
                                ArrayList arrayList7 = arrayList6;
                                if (!str37.startsWith("CUSTOM")) {
                                    switch (str37.hashCode()) {
                                        case -1249320806:
                                            if (str37.equals(obj5)) {
                                                c2 = 0;
                                                break;
                                            } else {
                                                c2 = 65535;
                                                break;
                                            }
                                        case -1249320805:
                                            if (str37.equals(obj4)) {
                                                c2 = 1;
                                                break;
                                            }
                                            break;
                                        case -1225497657:
                                            if (str37.equals(obj3)) {
                                                c2 = 2;
                                                break;
                                            }
                                            break;
                                        case -1225497656:
                                            if (str37.equals(obj2)) {
                                                c2 = 3;
                                                break;
                                            }
                                            break;
                                        case -1225497655:
                                            if (str37.equals("translationZ")) {
                                                c2 = 4;
                                                break;
                                            }
                                            break;
                                        case -1001078227:
                                            if (str37.equals(str5)) {
                                                c2 = 5;
                                                break;
                                            }
                                            break;
                                        case -908189618:
                                            if (str37.equals(str)) {
                                                c2 = 6;
                                                break;
                                            }
                                            break;
                                        case -908189617:
                                            if (str37.equals(str2)) {
                                                c2 = 7;
                                                break;
                                            }
                                            break;
                                        case -760884510:
                                            if (str37.equals("transformPivotX")) {
                                                c2 = '\b';
                                                break;
                                            }
                                            break;
                                        case -760884509:
                                            if (str37.equals("transformPivotY")) {
                                                c2 = '\t';
                                                break;
                                            }
                                            break;
                                        case -40300674:
                                            if (str37.equals(str3)) {
                                                c2 = '\n';
                                                break;
                                            }
                                            break;
                                        case -4379043:
                                            if (str37.equals(str4)) {
                                                c2 = 11;
                                                break;
                                            }
                                            break;
                                        case 37232917:
                                            if (str37.equals("transitionPathRotate")) {
                                                c2 = '\f';
                                                break;
                                            }
                                            break;
                                        case 92909918:
                                            if (str37.equals("alpha")) {
                                                c2 = '\r';
                                                break;
                                            }
                                            break;
                                    }
                                    switch (c2) {
                                        case 0:
                                            if (!Float.isNaN(m80Var.f58295a8)) {
                                                tc1Var.mo214378a1(m80Var.f58295a8, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case 1:
                                            if (!Float.isNaN(m80Var.f58296a9)) {
                                                tc1Var.mo214378a1(m80Var.f58296a9, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case 2:
                                            if (!Float.isNaN(m80Var.f58302b5)) {
                                                tc1Var.mo214378a1(m80Var.f58302b5, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case 3:
                                            if (!Float.isNaN(m80Var.f58303b6)) {
                                                tc1Var.mo214378a1(m80Var.f58303b6, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case 4:
                                            if (!Float.isNaN(m80Var.f58304b7)) {
                                                tc1Var.mo214378a1(m80Var.f58304b7, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case 5:
                                            if (!Float.isNaN(m80Var.f58305b8)) {
                                                tc1Var.mo214378a1(m80Var.f58305b8, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case 6:
                                            if (!Float.isNaN(m80Var.f58300b3)) {
                                                tc1Var.mo214378a1(m80Var.f58300b3, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case 7:
                                            if (!Float.isNaN(m80Var.f58301b4)) {
                                                tc1Var.mo214378a1(m80Var.f58301b4, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case '\b':
                                            if (!Float.isNaN(m80Var.f58295a8)) {
                                                tc1Var.mo214378a1(m80Var.f58297b0, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case '\t':
                                            if (!Float.isNaN(m80Var.f58296a9)) {
                                                tc1Var.mo214378a1(m80Var.f58298b1, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case '\n':
                                            if (!Float.isNaN(m80Var.f58294a7)) {
                                                tc1Var.mo214378a1(m80Var.f58294a7, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case oe0.DEFAULT_M /* 11 */:
                                            if (!Float.isNaN(m80Var.f58293a6)) {
                                                tc1Var.mo214378a1(m80Var.f58293a6, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case FileClientSessionCache.MAX_SIZE /* 12 */:
                                            if (!Float.isNaN(m80Var.f58299b2)) {
                                                tc1Var.mo214378a1(m80Var.f58299b2, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                        case '\r':
                                            if (!Float.isNaN(m80Var.f58292a5)) {
                                                tc1Var.mo214378a1(m80Var.f58292a5, m80Var.f57482a0);
                                                break;
                                            }
                                            break;
                                    }
                                } else {
                                    C0798kw c0798kw4 = (C0798kw) m80Var.f57485a3.get(str37.substring(7));
                                    if (c0798kw4 != null) {
                                        ((qc1) tc1Var).f59465a5.append(m80Var.f57482a0, c0798kw4);
                                    }
                                }
                                arrayList6 = arrayList7;
                            }
                            i5 = i20;
                            map7 = map8;
                        }
                    }
                    size3 = i19;
                    arrayList6 = arrayList6;
                }
            }
            arrayList = arrayList6;
            mg0Var.m214000a0(og0Var2.f58823c4, 0);
            mg0Var2.m214000a0(og0Var2.f58823c4, 100);
            Iterator it4 = og0Var2.f58823c4.keySet().iterator();
            while (it4.hasNext()) {
                String str38 = (String) it4.next();
                HashMap map9 = map6;
                int iIntValue = (!map9.containsKey(str38) || (num = (Integer) map9.get(str38)) == null) ? 0 : num.intValue();
                Iterator it5 = it4;
                tc1 tc1Var2 = (tc1) og0Var2.f58823c4.get(str38);
                if (tc1Var2 != null) {
                    tc1Var2.mo214379a3(iIntValue);
                }
                map6 = map9;
                it4 = it5;
            }
            map = map6;
        }
        if (hashSet4.isEmpty()) {
            og0Var = og0Var2;
            arrayList3 = arrayList;
        } else {
            if (og0Var2.f58822c3 == null) {
                og0Var2.f58822c3 = new HashMap();
            }
            Iterator it6 = hashSet4.iterator();
            while (it6.hasNext()) {
                String str39 = (String) it6.next();
                if (!og0Var2.f58822c3.containsKey(str39)) {
                    String str40 = str7;
                    if (str39.startsWith(str40)) {
                        it = it6;
                        SparseArray sparseArray2 = new SparseArray();
                        str7 = str40;
                        String str41 = str6;
                        map2 = map;
                        String str42 = str39.split(str41)[1];
                        int size4 = arrayList.size();
                        Object obj16 = obj5;
                        int i21 = 0;
                        while (i21 < size4) {
                            int i22 = size4;
                            Object obj17 = arrayList.get(i21);
                            int i23 = i21 + 1;
                            k80 k80Var4 = (k80) obj17;
                            HashMap map10 = k80Var4.f57485a3;
                            if (map10 != null && (c0798kw2 = (C0798kw) map10.get(str42)) != null) {
                                sparseArray2.append(k80Var4.f57482a0, c0798kw2);
                            }
                            size4 = i22;
                            i21 = i23;
                        }
                        ?? wc1Var = new wc1();
                        wc1Var.f60893a8 = new SparseArray();
                        wc1Var.f60891a6 = str39.split(str41)[1];
                        wc1Var.f60892a7 = sparseArray2;
                        obj5 = obj16;
                        str6 = str41;
                        vc1Var2 = wc1Var;
                    } else {
                        it = it6;
                        map2 = map;
                        str7 = str40;
                        String str43 = str6;
                        Object obj18 = obj5;
                        switch (str39.hashCode()) {
                            case -1249320806:
                                obj5 = obj18;
                                if (str39.equals(obj5)) {
                                    c = 0;
                                    break;
                                } else {
                                    c = 65535;
                                    break;
                                }
                            case -1249320805:
                                if (str39.equals(obj4)) {
                                    obj5 = obj18;
                                    c = 1;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -1225497657:
                                if (str39.equals(obj3)) {
                                    obj5 = obj18;
                                    c = 2;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -1225497656:
                                if (str39.equals(obj2)) {
                                    obj5 = obj18;
                                    c = 3;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -1225497655:
                                if (str39.equals("translationZ")) {
                                    obj5 = obj18;
                                    c = 4;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -1001078227:
                                if (str39.equals(str5)) {
                                    obj5 = obj18;
                                    c = 5;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -908189618:
                                if (str39.equals(str)) {
                                    obj5 = obj18;
                                    c = 6;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -908189617:
                                if (str39.equals(str2)) {
                                    obj5 = obj18;
                                    c = 7;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -40300674:
                                if (str39.equals(str3)) {
                                    obj5 = obj18;
                                    c = '\b';
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case -4379043:
                                if (str39.equals(str4)) {
                                    obj5 = obj18;
                                    c = '\t';
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case 37232917:
                                if (str39.equals("transitionPathRotate")) {
                                    c = '\n';
                                    obj5 = obj18;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            case 92909918:
                                if (str39.equals("alpha")) {
                                    c = 11;
                                    obj5 = obj18;
                                    break;
                                }
                                obj5 = obj18;
                                c = 65535;
                                break;
                            default:
                                obj5 = obj18;
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                vc1Var = new vc1(3);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case 1:
                                vc1Var = new vc1(4);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case 2:
                                vc1Var = new vc1(7);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case 3:
                                vc1Var = new vc1(8);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case 4:
                                vc1Var = new vc1(9);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case 5:
                                ?? yc1Var = new yc1();
                                yc1Var.f61292a6 = false;
                                vc1Var = yc1Var;
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case 6:
                                vc1Var = new vc1(5);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case 7:
                                vc1Var = new vc1(6);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case '\b':
                                vc1Var = new vc1(2);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case '\t':
                                vc1Var = new vc1(1);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case '\n':
                                vc1Var = new xc1();
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            case oe0.DEFAULT_M /* 11 */:
                                vc1Var = new vc1(0);
                                str6 = str43;
                                vc1Var.f61505a4 = j;
                                vc1Var3 = vc1Var;
                                break;
                            default:
                                str6 = str43;
                                vc1Var2 = null;
                                break;
                        }
                        if (vc1Var3 != null) {
                            og0Var2 = this;
                            it6 = it;
                        } else {
                            vc1Var3.f61502a1 = str39;
                            this.f58822c3.put(str39, vc1Var3);
                            og0Var2 = this;
                            it6 = it;
                            obj4 = obj4;
                        }
                        map = map2;
                    }
                    vc1Var3 = vc1Var2;
                    if (vc1Var3 != null) {
                    }
                    map = map2;
                }
            }
            og0Var = og0Var2;
            HashMap map11 = map;
            if (arrayList != null) {
                int size5 = arrayList.size();
                int i24 = 0;
                while (i24 < size5) {
                    Object obj19 = arrayList.get(i24);
                    i24++;
                }
            }
            arrayList3 = arrayList;
            for (String str44 : og0Var.f58822c3.keySet()) {
                HashMap map12 = map11;
                ((zc1) og0Var.f58822c3.get(str44)).mo215046a2(map12.containsKey(str44) ? ((Integer) map12.get(str44)).intValue() : 0);
                map11 = map12;
            }
        }
        int size6 = arrayList2.size();
        int i25 = size6 + 2;
        vg0[] vg0VarArr = new vg0[i25];
        vg0VarArr[0] = vg0Var;
        vg0VarArr[size6 + 1] = vg0Var2;
        if (arrayList2.size() > 0 && og0Var.f58803a4 == -1) {
            og0Var.f58803a4 = 0;
        }
        int size7 = arrayList2.size();
        int i26 = 0;
        int i27 = 1;
        while (i26 < size7) {
            Object obj20 = arrayList2.get(i26);
            i26++;
            vg0VarArr[i27] = (vg0) obj20;
            i27++;
        }
        HashSet hashSet7 = new HashSet();
        for (String str45 : vg0Var2.f60639b3.keySet()) {
            vg0 vg0Var8 = vg0Var;
            if (vg0Var8.f60639b3.containsKey(str45)) {
                str9 = str7;
                hashSet2 = hashSet;
                if (!hashSet2.contains(str9 + str45)) {
                    hashSet7.add(str45);
                }
            } else {
                str9 = str7;
                hashSet2 = hashSet;
            }
            vg0Var = vg0Var8;
            hashSet = hashSet2;
            str7 = str9;
        }
        String[] strArr = (String[]) hashSet7.toArray(new String[0]);
        og0Var.f58816b7 = strArr;
        og0Var.f58817b8 = new int[strArr.length];
        int i28 = 0;
        while (true) {
            String[] strArr2 = og0Var.f58816b7;
            if (i28 < strArr2.length) {
                String str46 = strArr2[i28];
                og0Var.f58817b8[i28] = 0;
                int i29 = 0;
                while (true) {
                    if (i29 >= i25) {
                        break;
                    }
                    if (!vg0VarArr[i29].f60639b3.containsKey(str46) || (c0798kw = (C0798kw) vg0VarArr[i29].f60639b3.get(str46)) == null) {
                        i29++;
                    } else {
                        int[] iArr = og0Var.f58817b8;
                        iArr[i28] = c0798kw.m213762a2() + iArr[i28];
                    }
                }
                i28++;
            } else {
                boolean z = vg0VarArr[0].f60635a9 != -1;
                int length = 18 + strArr2.length;
                boolean[] zArr = new boolean[length];
                for (int i30 = 1; i30 < i25; i30++) {
                    vg0 vg0Var9 = vg0VarArr[i30];
                    vg0 vg0Var10 = vg0VarArr[i30 - 1];
                    boolean zM214923a1 = vg0.m214923a1(vg0Var9.f60630a4, vg0Var10.f60630a4);
                    boolean zM214923a12 = vg0.m214923a1(vg0Var9.f60631a5, vg0Var10.f60631a5);
                    zArr[0] = zArr[0] | vg0.m214923a1(vg0Var9.f60629a3, vg0Var10.f60629a3);
                    boolean z2 = zM214923a1 | zM214923a12 | z;
                    zArr[1] = zArr[1] | z2;
                    zArr[2] = z2 | zArr[2];
                    zArr[3] = zArr[3] | vg0.m214923a1(vg0Var9.f60632a6, vg0Var10.f60632a6);
                    zArr[4] = vg0.m214923a1(vg0Var9.f60633a7, vg0Var10.f60633a7) | zArr[4];
                }
                int i31 = 0;
                for (int i32 = 1; i32 < length; i32++) {
                    if (zArr[i32]) {
                        i31++;
                    }
                }
                og0Var.f58813b4 = new int[i31];
                int iMax = Math.max(2, i31);
                og0Var.f58814b5 = new double[iMax];
                og0Var.f58815b6 = new double[iMax];
                int i33 = 0;
                for (int i34 = 1; i34 < length; i34++) {
                    if (zArr[i34]) {
                        og0Var.f58813b4[i33] = i34;
                        i33++;
                    }
                }
                int[] iArr2 = {i25, og0Var.f58813b4.length};
                Class cls = Double.TYPE;
                double[][] dArr = (double[][]) Array.newInstance((Class<?>) cls, iArr2);
                double[] dArr2 = new double[i25];
                int i35 = 0;
                while (i35 < i25) {
                    vg0 vg0Var11 = vg0VarArr[i35];
                    double[] dArr3 = dArr[i35];
                    int[] iArr3 = og0Var.f58813b4;
                    vg0[] vg0VarArr2 = vg0VarArr;
                    int i36 = i35;
                    int i37 = 6;
                    float[] fArr = {vg0Var11.f60629a3, vg0Var11.f60630a4, vg0Var11.f60631a5, vg0Var11.f60632a6, vg0Var11.f60633a7, vg0Var11.f60634a8};
                    int i38 = 0;
                    int i39 = 0;
                    while (i38 < iArr3.length) {
                        if (iArr3[i38] < i37) {
                            dArr3[i39] = fArr[r13];
                            i39++;
                        }
                        i38++;
                        i37 = 6;
                    }
                    dArr2[i36] = vg0VarArr2[i36].f60628a2;
                    i35 = i36 + 1;
                    vg0VarArr = vg0VarArr2;
                }
                vg0[] vg0VarArr3 = vg0VarArr;
                int i40 = 0;
                while (true) {
                    int[] iArr4 = og0Var.f58813b4;
                    if (i40 < iArr4.length) {
                        if (iArr4[i40] < 6) {
                            String strM35b6 = AbstractC0003a2.m35b6(new StringBuilder(), vg0.f60625b7[og0Var.f58813b4[i40]], " [");
                            for (int i41 = 0; i41 < i25; i41++) {
                                StringBuilder sbM37b8 = AbstractC0003a2.m37b8(strM35b6);
                                sbM37b8.append(dArr[i41][i40]);
                                strM35b6 = sbM37b8.toString();
                            }
                        }
                        i40++;
                    } else {
                        og0Var.f58808a9 = new b81[og0Var.f58816b7.length + 1];
                        int i42 = 0;
                        while (true) {
                            String[] strArr3 = og0Var.f58816b7;
                            if (i42 >= strArr3.length) {
                                int i43 = 0;
                                og0Var.f58808a9[0] = b81.m210573b3(og0Var.f58803a4, dArr2, dArr);
                                if (vg0VarArr3[0].f60635a9 != -1) {
                                    int[] iArr5 = new int[i25];
                                    double[] dArr4 = new double[i25];
                                    double[][] dArr5 = (double[][]) Array.newInstance((Class<?>) cls, i25, 2);
                                    for (int i44 = 0; i44 < i25; i44++) {
                                        iArr5[i44] = vg0VarArr3[i44].f60635a9;
                                        dArr4[i44] = r6.f60628a2;
                                        double[] dArr6 = dArr5[i44];
                                        dArr6[0] = r6.f60630a4;
                                        dArr6[1] = r6.f60631a5;
                                    }
                                    i43 = 0;
                                    og0Var.f58809b0 = new C0110au(iArr5, dArr4, dArr5);
                                }
                                og0Var.f58824c5 = new HashMap();
                                if (arrayList3 != null) {
                                    Iterator it7 = hashSet6.iterator();
                                    while (it7.hasNext()) {
                                        String str47 = (String) it7.next();
                                        zb1 zb1VarM215388a2 = zb1.m215388a2(str47);
                                        if (zb1VarM215388a2 != null) {
                                            zb1VarM215388a2.f61494a1 = str47;
                                            og0Var.f58824c5.put(str47, zb1VarM215388a2);
                                        }
                                    }
                                    int size8 = arrayList3.size();
                                    int i45 = i43;
                                    while (i45 < size8) {
                                        Object obj21 = arrayList3.get(i45);
                                        i45++;
                                    }
                                    Iterator it8 = og0Var.f58824c5.values().iterator();
                                    while (it8.hasNext()) {
                                        ((zb1) it8.next()).m215391a4();
                                    }
                                    return;
                                }
                                return;
                            }
                            String str48 = strArr3[i42];
                            int i46 = 0;
                            int i47 = 0;
                            double[] dArr7 = null;
                            double[][] dArr8 = null;
                            while (i46 < i25) {
                                if (vg0VarArr3[i46].f60639b3.containsKey(str48)) {
                                    if (dArr8 == null) {
                                        dArr7 = new double[i25];
                                        C0798kw c0798kw5 = (C0798kw) vg0VarArr3[i46].f60639b3.get(str48);
                                        dArr8 = (double[][]) Array.newInstance((Class<?>) cls, i25, c0798kw5 == null ? 0 : c0798kw5.m213762a2());
                                    }
                                    vg0 vg0Var12 = vg0VarArr3[i46];
                                    dArr7[i47] = vg0Var12.f60628a2;
                                    double[] dArr9 = dArr8[i47];
                                    C0798kw c0798kw6 = (C0798kw) vg0Var12.f60639b3.get(str48);
                                    if (c0798kw6 != null) {
                                        if (c0798kw6.m213762a2() == 1) {
                                            dArr9[0] = c0798kw6.m213760a0();
                                        } else {
                                            int iM213762a2 = c0798kw6.m213762a2();
                                            c0798kw6.m213761a1(new float[iM213762a2]);
                                            int i48 = 0;
                                            int i49 = 0;
                                            while (i48 < iM213762a2) {
                                                dArr9[i49] = r15[i48];
                                                i48++;
                                                str48 = str48;
                                                i49++;
                                                i42 = i42;
                                                i46 = i46;
                                            }
                                        }
                                    }
                                    i3 = i42;
                                    str8 = str48;
                                    i4 = i46;
                                    i47++;
                                } else {
                                    i3 = i42;
                                    str8 = str48;
                                    i4 = i46;
                                }
                                i46 = i4 + 1;
                                str48 = str8;
                                i42 = i3;
                            }
                            int i50 = i42;
                            double[] dArrCopyOf = Arrays.copyOf(dArr7, i47);
                            double[][] dArr10 = (double[][]) Arrays.copyOf(dArr8, i47);
                            int i51 = i50 + 1;
                            og0Var.f58808a9[i51] = b81.m210573b3(og0Var.f58803a4, dArrCopyOf, dArr10);
                            i42 = i51;
                        }
                    }
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" start: x: ");
        vg0 vg0Var = this.f58804a5;
        sb.append(vg0Var.f60630a4);
        sb.append(" y: ");
        sb.append(vg0Var.f60631a5);
        sb.append(" end: x: ");
        vg0 vg0Var2 = this.f58805a6;
        sb.append(vg0Var2.f60630a4);
        sb.append(" y: ");
        sb.append(vg0Var2.f60631a5);
        return sb.toString();
    }
}
