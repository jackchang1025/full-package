package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class ImageViewCompat {
    private ImageViewCompat() {
    }

    @Nullable
    public static ColorStateList getImageTintList(@NonNull ImageView imageView) {
        return imageView.getImageTintList();
    }

    @Nullable
    public static PorterDuff.Mode getImageTintMode(@NonNull ImageView imageView) {
        return imageView.getImageTintMode();
    }

    public static void setImageTintList(@NonNull ImageView imageView, @Nullable ColorStateList colorStateList) {
        imageView.setImageTintList(colorStateList);
    }

    public static void setImageTintMode(@NonNull ImageView imageView, @Nullable PorterDuff.Mode mode) {
        imageView.setImageTintMode(mode);
    }
}
