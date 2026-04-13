package android.support.v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
public abstract class AutoScrollHelper implements View.OnTouchListener {
    private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    private static final int DEFAULT_EDGE_TYPE = 1;
    private static final float DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE;
    private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
    private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
    private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
    private static final int DEFAULT_RAMP_UP_DURATION = 500;
    private static final float DEFAULT_RELATIVE_EDGE = 0.2f;
    private static final float DEFAULT_RELATIVE_VELOCITY = 1.0f;
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    private static final int HORIZONTAL = 0;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int VERTICAL = 1;
    private int mActivationDelay;
    private boolean mAlreadyDelayed;
    boolean mAnimating;
    private int mEdgeType;
    private boolean mEnabled;
    private boolean mExclusive;
    boolean mNeedsCancel;
    boolean mNeedsReset;
    private Runnable mRunnable;
    final View mTarget;
    final ClampedScroller mScroller = new ClampedScroller();
    private final Interpolator mEdgeInterpolator = new AccelerateInterpolator();
    private float[] mRelativeEdges = {0.0f, 0.0f};
    private float[] mMaximumEdges = {Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mRelativeVelocity = {0.0f, 0.0f};
    private float[] mMinimumVelocity = {0.0f, 0.0f};
    private float[] mMaximumVelocity = {Float.MAX_VALUE, Float.MAX_VALUE};

    public static class ClampedScroller {
        private int mEffectiveRampDown;
        private int mRampDownDuration;
        private int mRampUpDuration;
        private float mStopValue;
        private float mTargetVelocityX;
        private float mTargetVelocityY;
        private long mStartTime = Long.MIN_VALUE;
        private long mStopTime = -1;
        private long mDeltaTime = 0;
        private int mDeltaX = 0;
        private int mDeltaY = 0;

        private float getValueAt(long j2) {
            if (j2 < this.mStartTime) {
                return 0.0f;
            }
            long j3 = this.mStopTime;
            if (j3 < 0 || j2 < j3) {
                return AutoScrollHelper.constrain((j2 - r0) / this.mRampUpDuration, 0.0f, AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY) * 0.5f;
            }
            float f2 = this.mStopValue;
            return (AutoScrollHelper.constrain((j2 - j3) / this.mEffectiveRampDown, 0.0f, AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY) * f2) + (AutoScrollHelper.DEFAULT_RELATIVE_VELOCITY - f2);
        }

        private float interpolateValue(float f2) {
            return (f2 * 4.0f) + ((-4.0f) * f2 * f2);
        }

        public void computeScrollDelta() {
            if (this.mDeltaTime == 0) {
                throw new RuntimeException("Cannot compute scroll delta before calling start()");
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float interpolateValue = interpolateValue(getValueAt(currentAnimationTimeMillis));
            long j2 = currentAnimationTimeMillis - this.mDeltaTime;
            this.mDeltaTime = currentAnimationTimeMillis;
            float f2 = j2 * interpolateValue;
            this.mDeltaX = (int) (this.mTargetVelocityX * f2);
            this.mDeltaY = (int) (f2 * this.mTargetVelocityY);
        }

        public int getDeltaX() {
            return this.mDeltaX;
        }

        public int getDeltaY() {
            return this.mDeltaY;
        }

        public int getHorizontalDirection() {
            float f2 = this.mTargetVelocityX;
            return (int) (f2 / Math.abs(f2));
        }

        public int getVerticalDirection() {
            float f2 = this.mTargetVelocityY;
            return (int) (f2 / Math.abs(f2));
        }

        public boolean isFinished() {
            return this.mStopTime > 0 && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + ((long) this.mEffectiveRampDown);
        }

        public void requestStop() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mEffectiveRampDown = AutoScrollHelper.constrain((int) (currentAnimationTimeMillis - this.mStartTime), 0, this.mRampDownDuration);
            this.mStopValue = getValueAt(currentAnimationTimeMillis);
            this.mStopTime = currentAnimationTimeMillis;
        }

        public void setRampDownDuration(int i2) {
            this.mRampDownDuration = i2;
        }

        public void setRampUpDuration(int i2) {
            this.mRampUpDuration = i2;
        }

        public void setTargetVelocity(float f2, float f3) {
            this.mTargetVelocityX = f2;
            this.mTargetVelocityY = f3;
        }

        public void start() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = currentAnimationTimeMillis;
            this.mStopTime = -1L;
            this.mDeltaTime = currentAnimationTimeMillis;
            this.mStopValue = 0.5f;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }
    }

    public class ScrollAnimationRunnable implements Runnable {
        public ScrollAnimationRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            AutoScrollHelper autoScrollHelper = AutoScrollHelper.this;
            if (autoScrollHelper.mAnimating) {
                if (autoScrollHelper.mNeedsReset) {
                    autoScrollHelper.mNeedsReset = false;
                    autoScrollHelper.mScroller.start();
                }
                ClampedScroller clampedScroller = AutoScrollHelper.this.mScroller;
                if (clampedScroller.isFinished() || !AutoScrollHelper.this.shouldAnimate()) {
                    AutoScrollHelper.this.mAnimating = false;
                    return;
                }
                AutoScrollHelper autoScrollHelper2 = AutoScrollHelper.this;
                if (autoScrollHelper2.mNeedsCancel) {
                    autoScrollHelper2.mNeedsCancel = false;
                    autoScrollHelper2.cancelTargetTouch();
                }
                clampedScroller.computeScrollDelta();
                AutoScrollHelper.this.scrollTargetBy(clampedScroller.getDeltaX(), clampedScroller.getDeltaY());
                ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this);
            }
        }
    }

    public AutoScrollHelper(@NonNull View view) {
        this.mTarget = view;
        float f2 = Resources.getSystem().getDisplayMetrics().density;
        float f3 = (int) ((1575.0f * f2) + 0.5f);
        setMaximumVelocity(f3, f3);
        float f4 = (int) ((f2 * 315.0f) + 0.5f);
        setMinimumVelocity(f4, f4);
        setEdgeType(1);
        setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        setRelativeEdges(DEFAULT_RELATIVE_EDGE, DEFAULT_RELATIVE_EDGE);
        setRelativeVelocity(DEFAULT_RELATIVE_VELOCITY, DEFAULT_RELATIVE_VELOCITY);
        setActivationDelay(DEFAULT_ACTIVATION_DELAY);
        setRampUpDuration(500);
        setRampDownDuration(500);
    }

    private float computeTargetVelocity(int i2, float f2, float f3, float f4) {
        float edgeValue = getEdgeValue(this.mRelativeEdges[i2], f3, this.mMaximumEdges[i2], f2);
        if (edgeValue == 0.0f) {
            return 0.0f;
        }
        float f5 = this.mRelativeVelocity[i2];
        float f6 = this.mMinimumVelocity[i2];
        float f7 = this.mMaximumVelocity[i2];
        float f8 = f5 * f4;
        return edgeValue > 0.0f ? constrain(edgeValue * f8, f6, f7) : -constrain((-edgeValue) * f8, f6, f7);
    }

    public static float constrain(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    private float constrainEdgeValue(float f2, float f3) {
        if (f3 == 0.0f) {
            return 0.0f;
        }
        int i2 = this.mEdgeType;
        if (i2 == 0 || i2 == 1) {
            if (f2 < f3) {
                if (f2 >= 0.0f) {
                    return DEFAULT_RELATIVE_VELOCITY - (f2 / f3);
                }
                if (this.mAnimating && i2 == 1) {
                    return DEFAULT_RELATIVE_VELOCITY;
                }
            }
        } else if (i2 == 2 && f2 < 0.0f) {
            return f2 / (-f3);
        }
        return 0.0f;
    }

    private float getEdgeValue(float f2, float f3, float f4, float f5) {
        float interpolation;
        float constrain = constrain(f2 * f3, 0.0f, f4);
        float constrainEdgeValue = constrainEdgeValue(f3 - f5, constrain) - constrainEdgeValue(f5, constrain);
        if (constrainEdgeValue < 0.0f) {
            interpolation = -this.mEdgeInterpolator.getInterpolation(-constrainEdgeValue);
        } else {
            if (constrainEdgeValue <= 0.0f) {
                return 0.0f;
            }
            interpolation = this.mEdgeInterpolator.getInterpolation(constrainEdgeValue);
        }
        return constrain(interpolation, -1.0f, DEFAULT_RELATIVE_VELOCITY);
    }

    private void requestStop() {
        if (this.mNeedsReset) {
            this.mAnimating = false;
        } else {
            this.mScroller.requestStop();
        }
    }

    private void startAnimating() {
        int i2;
        if (this.mRunnable == null) {
            this.mRunnable = new ScrollAnimationRunnable();
        }
        this.mAnimating = true;
        this.mNeedsReset = true;
        if (this.mAlreadyDelayed || (i2 = this.mActivationDelay) <= 0) {
            this.mRunnable.run();
        } else {
            ViewCompat.postOnAnimationDelayed(this.mTarget, this.mRunnable, i2);
        }
        this.mAlreadyDelayed = true;
    }

    public abstract boolean canTargetScrollHorizontally(int i2);

    public abstract boolean canTargetScrollVertically(int i2);

    public void cancelTargetTouch() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        this.mTarget.onTouchEvent(obtain);
        obtain.recycle();
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isExclusive() {
        return this.mExclusive;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0013, code lost:
    
        if (r0 != 3) goto L20;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.mEnabled) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                }
            }
            requestStop();
            return this.mExclusive && this.mAnimating;
        }
        this.mNeedsCancel = true;
        this.mAlreadyDelayed = false;
        this.mScroller.setTargetVelocity(computeTargetVelocity(0, motionEvent.getX(), view.getWidth(), this.mTarget.getWidth()), computeTargetVelocity(1, motionEvent.getY(), view.getHeight(), this.mTarget.getHeight()));
        if (!this.mAnimating && shouldAnimate()) {
            startAnimating();
        }
        if (this.mExclusive) {
            return false;
        }
    }

    public abstract void scrollTargetBy(int i2, int i3);

    @NonNull
    public AutoScrollHelper setActivationDelay(int i2) {
        this.mActivationDelay = i2;
        return this;
    }

    @NonNull
    public AutoScrollHelper setEdgeType(int i2) {
        this.mEdgeType = i2;
        return this;
    }

    public AutoScrollHelper setEnabled(boolean z2) {
        if (this.mEnabled && !z2) {
            requestStop();
        }
        this.mEnabled = z2;
        return this;
    }

    public AutoScrollHelper setExclusive(boolean z2) {
        this.mExclusive = z2;
        return this;
    }

    @NonNull
    public AutoScrollHelper setMaximumEdges(float f2, float f3) {
        float[] fArr = this.mMaximumEdges;
        fArr[0] = f2;
        fArr[1] = f3;
        return this;
    }

    @NonNull
    public AutoScrollHelper setMaximumVelocity(float f2, float f3) {
        float[] fArr = this.mMaximumVelocity;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setMinimumVelocity(float f2, float f3) {
        float[] fArr = this.mMinimumVelocity;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRampDownDuration(int i2) {
        this.mScroller.setRampDownDuration(i2);
        return this;
    }

    @NonNull
    public AutoScrollHelper setRampUpDuration(int i2) {
        this.mScroller.setRampUpDuration(i2);
        return this;
    }

    @NonNull
    public AutoScrollHelper setRelativeEdges(float f2, float f3) {
        float[] fArr = this.mRelativeEdges;
        fArr[0] = f2;
        fArr[1] = f3;
        return this;
    }

    @NonNull
    public AutoScrollHelper setRelativeVelocity(float f2, float f3) {
        float[] fArr = this.mRelativeVelocity;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    public boolean shouldAnimate() {
        ClampedScroller clampedScroller = this.mScroller;
        int verticalDirection = clampedScroller.getVerticalDirection();
        int horizontalDirection = clampedScroller.getHorizontalDirection();
        return (verticalDirection != 0 && canTargetScrollVertically(verticalDirection)) || (horizontalDirection != 0 && canTargetScrollHorizontally(horizontalDirection));
    }

    public static int constrain(int i2, int i3, int i4) {
        return i2 > i4 ? i4 : i2 < i3 ? i3 : i2;
    }
}
