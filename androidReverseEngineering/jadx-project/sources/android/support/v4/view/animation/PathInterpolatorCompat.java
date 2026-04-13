package android.support.v4.view.animation;

import android.graphics.Path;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* loaded from: classes.dex */
public final class PathInterpolatorCompat {
    private PathInterpolatorCompat() {
    }

    public static Interpolator create(float f2, float f3) {
        return new PathInterpolator(f2, f3);
    }

    public static Interpolator create(float f2, float f3, float f4, float f5) {
        return new PathInterpolator(f2, f3, f4, f5);
    }

    public static Interpolator create(Path path) {
        return new PathInterpolator(path);
    }
}
