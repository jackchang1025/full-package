package android.support.v4.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pair;
import android.view.View;

/* loaded from: classes.dex */
public class ActivityOptionsCompat {
    public static final String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";

    @RequiresApi(16)
    public static class ActivityOptionsCompatImpl extends ActivityOptionsCompat {
        private final ActivityOptions mActivityOptions;

        public ActivityOptionsCompatImpl(ActivityOptions activityOptions) {
            this.mActivityOptions = activityOptions;
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public Rect getLaunchBounds() {
            return this.mActivityOptions.getLaunchBounds();
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public void requestUsageTimeReport(PendingIntent pendingIntent) {
            this.mActivityOptions.requestUsageTimeReport(pendingIntent);
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
            return new ActivityOptionsCompatImpl(this.mActivityOptions.setLaunchBounds(rect));
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public Bundle toBundle() {
            return this.mActivityOptions.toBundle();
        }

        @Override // android.support.v4.app.ActivityOptionsCompat
        public void update(ActivityOptionsCompat activityOptionsCompat) {
            if (activityOptionsCompat instanceof ActivityOptionsCompatImpl) {
                this.mActivityOptions.update(((ActivityOptionsCompatImpl) activityOptionsCompat).mActivityOptions);
            }
        }
    }

    @NonNull
    public static ActivityOptionsCompat makeBasic() {
        return new ActivityOptionsCompatImpl(ActivityOptions.makeBasic());
    }

    @NonNull
    public static ActivityOptionsCompat makeClipRevealAnimation(@NonNull View view, int i2, int i3, int i4, int i5) {
        return new ActivityOptionsCompatImpl(ActivityOptions.makeClipRevealAnimation(view, i2, i3, i4, i5));
    }

    @NonNull
    public static ActivityOptionsCompat makeCustomAnimation(@NonNull Context context, int i2, int i3) {
        return new ActivityOptionsCompatImpl(ActivityOptions.makeCustomAnimation(context, i2, i3));
    }

    @NonNull
    public static ActivityOptionsCompat makeScaleUpAnimation(@NonNull View view, int i2, int i3, int i4, int i5) {
        return new ActivityOptionsCompatImpl(ActivityOptions.makeScaleUpAnimation(view, i2, i3, i4, i5));
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity, @NonNull View view, @NonNull String str) {
        return new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(activity, view, str));
    }

    @NonNull
    public static ActivityOptionsCompat makeTaskLaunchBehind() {
        return new ActivityOptionsCompatImpl(ActivityOptions.makeTaskLaunchBehind());
    }

    @NonNull
    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(@NonNull View view, @NonNull Bitmap bitmap, int i2, int i3) {
        return new ActivityOptionsCompatImpl(ActivityOptions.makeThumbnailScaleUpAnimation(view, bitmap, i2, i3));
    }

    @Nullable
    public Rect getLaunchBounds() {
        return null;
    }

    public void requestUsageTimeReport(@NonNull PendingIntent pendingIntent) {
    }

    @NonNull
    public ActivityOptionsCompat setLaunchBounds(@Nullable Rect rect) {
        return this;
    }

    @Nullable
    public Bundle toBundle() {
        return null;
    }

    public void update(@NonNull ActivityOptionsCompat activityOptionsCompat) {
    }

    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(@NonNull Activity activity, Pair<View, String>... pairArr) {
        android.util.Pair[] pairArr2;
        if (pairArr != null) {
            pairArr2 = new android.util.Pair[pairArr.length];
            for (int i2 = 0; i2 < pairArr.length; i2++) {
                Pair<View, String> pair = pairArr[i2];
                pairArr2[i2] = android.util.Pair.create(pair.first, pair.second);
            }
        } else {
            pairArr2 = null;
        }
        return new ActivityOptionsCompatImpl(ActivityOptions.makeSceneTransitionAnimation(activity, pairArr2));
    }
}
