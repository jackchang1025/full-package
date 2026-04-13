package android.support.v4.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.util.Preconditions;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ResourcesCompat {
    private static final String TAG = "ResourcesCompat";

    public static abstract class FontCallback {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final void callbackFailAsync(final int i2, @Nullable Handler handler) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler.post(new Runnable() { // from class: android.support.v4.content.res.ResourcesCompat.FontCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    FontCallback.this.onFontRetrievalFailed(i2);
                }
            });
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public final void callbackSuccessAsync(final Typeface typeface, @Nullable Handler handler) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler.post(new Runnable() { // from class: android.support.v4.content.res.ResourcesCompat.FontCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    FontCallback.this.onFontRetrieved(typeface);
                }
            });
        }

        public abstract void onFontRetrievalFailed(int i2);

        public abstract void onFontRetrieved(@NonNull Typeface typeface);
    }

    private ResourcesCompat() {
    }

    @ColorInt
    public static int getColor(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) {
        return resources.getColor(i2, theme);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources resources, @ColorRes int i2, @Nullable Resources.Theme theme) {
        return resources.getColorStateList(i2, theme);
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Resources resources, @DrawableRes int i2, @Nullable Resources.Theme theme) {
        return resources.getDrawable(i2, theme);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources resources, @DrawableRes int i2, int i3, @Nullable Resources.Theme theme) {
        return resources.getDrawableForDensity(i2, i3, theme);
    }

    @Nullable
    public static Typeface getFont(@NonNull Context context, @FontRes int i2) {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, new TypedValue(), 0, null, null, false);
    }

    private static Typeface loadFont(@NonNull Context context, int i2, TypedValue typedValue, int i3, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z2) {
        Resources resources = context.getResources();
        resources.getValue(i2, typedValue, true);
        Typeface loadFont = loadFont(context, resources, typedValue, i2, i3, fontCallback, handler, z2);
        if (loadFont != null || fontCallback != null) {
            return loadFont;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i2) + " could not be retrieved.");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Typeface getFont(@NonNull Context context, @FontRes int i2, TypedValue typedValue, int i3, @Nullable FontCallback fontCallback) {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i2, typedValue, i3, fontCallback, null, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Typeface loadFont(@NonNull Context context, Resources resources, TypedValue typedValue, int i2, int i3, @Nullable FontCallback fontCallback, @Nullable Handler handler, boolean z2) {
        String str;
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i2) + "\" (" + Integer.toHexString(i2) + ") is not a Font: " + typedValue);
        }
        String charSequence2 = charSequence.toString();
        if (!charSequence2.startsWith("res/")) {
            if (fontCallback != null) {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        }
        Typeface findFromCache = TypefaceCompat.findFromCache(resources, i2, i3);
        if (findFromCache != null) {
            if (fontCallback != null) {
                fontCallback.callbackSuccessAsync(findFromCache, handler);
            }
            return findFromCache;
        }
        try {
            if (!charSequence2.toLowerCase().endsWith(".xml")) {
                Typeface createFromResourcesFontFile = TypefaceCompat.createFromResourcesFontFile(context, resources, i2, charSequence2, i3);
                if (fontCallback != null) {
                    if (createFromResourcesFontFile != null) {
                        fontCallback.callbackSuccessAsync(createFromResourcesFontFile, handler);
                    } else {
                        fontCallback.callbackFailAsync(-3, handler);
                    }
                }
                return createFromResourcesFontFile;
            }
            FontResourcesParserCompat.FamilyResourceEntry parse = FontResourcesParserCompat.parse(resources.getXml(i2), resources);
            if (parse != null) {
                return TypefaceCompat.createFromResourcesFamilyXml(context, parse, resources, i2, i3, fontCallback, handler, z2);
            }
            Log.e(TAG, "Failed to find font-family tag");
            if (fontCallback != null) {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        } catch (IOException e2) {
            e = e2;
            str = "Failed to read xml resource ";
            Log.e(TAG, str.concat(charSequence2), e);
            if (fontCallback != null) {
                fontCallback.callbackFailAsync(-3, handler);
            }
            return null;
        } catch (XmlPullParserException e3) {
            e = e3;
            str = "Failed to parse xml resource ";
            Log.e(TAG, str.concat(charSequence2), e);
            if (fontCallback != null) {
            }
            return null;
        }
    }

    public static void getFont(@NonNull Context context, @FontRes int i2, @NonNull FontCallback fontCallback, @Nullable Handler handler) {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
        } else {
            loadFont(context, i2, new TypedValue(), 0, fontCallback, handler, false);
        }
    }
}
