package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.provider.FontsContractCompat;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

@RequiresApi(26)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String DEFAULT_FAMILY = "sans-serif";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class mFontFamily;
    protected final Constructor mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Class cls;
        Constructor constructor;
        Method method;
        Method method2;
        Method method3;
        Method method4;
        Method method5;
        try {
            cls = obtainFontFamily();
            constructor = obtainFontFamilyCtor(cls);
            method = obtainAddFontFromAssetManagerMethod(cls);
            method2 = obtainAddFontFromBufferMethod(cls);
            method3 = obtainFreezeMethod(cls);
            method4 = obtainAbortCreationMethod(cls);
            method5 = obtainCreateFromFamiliesWithDefaultMethod(cls);
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            Log.e(TAG, "Unable to collect necessary methods for class ".concat(e2.getClass().getName()), e2);
            cls = null;
            constructor = null;
            method = null;
            method2 = null;
            method3 = null;
            method4 = null;
            method5 = null;
        }
        this.mFontFamily = cls;
        this.mFontFamilyCtor = constructor;
        this.mAddFontFromAssetManager = method;
        this.mAddFontFromBuffer = method2;
        this.mFreeze = method3;
        this.mAbortCreation = method4;
        this.mCreateFromFamiliesWithDefault = method5;
    }

    private void abortCreation(Object obj) {
        try {
            this.mAbortCreation.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private boolean addFontFromAssetManager(Context context, Object obj, String str, int i2, int i3, int i4, @Nullable FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.mAddFontFromAssetManager.invoke(obj, context.getAssets(), str, 0, Boolean.FALSE, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private boolean addFontFromBuffer(Object obj, ByteBuffer byteBuffer, int i2, int i3, int i4) {
        try {
            return ((Boolean) this.mAddFontFromBuffer.invoke(obj, byteBuffer, Integer.valueOf(i2), null, Integer.valueOf(i3), Integer.valueOf(i4))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private boolean freeze(Object obj) {
        try {
            return ((Boolean) this.mFreeze.invoke(obj, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.mAddFontFromAssetManager != null;
    }

    private Object newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    public Typeface createFromFamiliesWithDefault(Object obj) {
        try {
            Object newInstance = Array.newInstance((Class<?>) this.mFontFamily, 1);
            Array.set(newInstance, 0, obj);
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke(null, newInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i2) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, fontFamilyFilesResourceEntry, resources, i2);
        }
        Object newFamily = newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
            if (!addFontFromAssetManager(context, newFamily, fontFileResourceEntry.getFileName(), fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(fontFileResourceEntry.getVariationSettings()))) {
                abortCreation(newFamily);
                return null;
            }
        }
        if (freeze(newFamily)) {
            return createFromFamiliesWithDefault(newFamily);
        }
        return null;
    }

    @Override // android.support.v4.graphics.TypefaceCompatApi21Impl, android.support.v4.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i2) {
        if (fontInfoArr.length < 1) {
            return null;
        }
        if (isFontFamilyPrivateAPIAvailable()) {
            Map<Uri, ByteBuffer> prepareFontData = FontsContractCompat.prepareFontData(context, fontInfoArr, cancellationSignal);
            Object newFamily = newFamily();
            boolean z2 = false;
            for (FontsContractCompat.FontInfo fontInfo : fontInfoArr) {
                ByteBuffer byteBuffer = prepareFontData.get(fontInfo.getUri());
                if (byteBuffer != null) {
                    if (!addFontFromBuffer(newFamily, byteBuffer, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic() ? 1 : 0)) {
                        abortCreation(newFamily);
                        return null;
                    }
                    z2 = true;
                }
            }
            if (!z2) {
                abortCreation(newFamily);
                return null;
            }
            if (freeze(newFamily)) {
                return Typeface.create(createFromFamiliesWithDefault(newFamily), i2);
            }
            return null;
        }
        FontsContractCompat.FontInfo findBestInfo = findBestInfo(fontInfoArr, i2);
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(findBestInfo.getUri(), "r", cancellationSignal);
            if (openFileDescriptor == null) {
                if (openFileDescriptor != null) {
                    openFileDescriptor.close();
                }
                return null;
            }
            try {
                Typeface build = new Typeface.Builder(openFileDescriptor.getFileDescriptor()).setWeight(findBestInfo.getWeight()).setItalic(findBestInfo.isItalic()).build();
                openFileDescriptor.close();
                return build;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl
    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i2, String str, int i3) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, i2, str, i3);
        }
        Object newFamily = newFamily();
        if (!addFontFromAssetManager(context, newFamily, str, 0, -1, -1, null)) {
            abortCreation(newFamily);
            return null;
        }
        if (freeze(newFamily)) {
            return createFromFamiliesWithDefault(newFamily);
        }
        return null;
    }

    public Method obtainAbortCreationMethod(Class cls) {
        return cls.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    public Method obtainAddFontFromAssetManagerMethod(Class cls) {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, cls2, Boolean.TYPE, cls2, cls2, cls2, FontVariationAxis[].class);
    }

    public Method obtainAddFontFromBufferMethod(Class cls) {
        Class<?> cls2 = Integer.TYPE;
        return cls.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, cls2, FontVariationAxis[].class, cls2, cls2);
    }

    public Method obtainCreateFromFamiliesWithDefaultMethod(Class cls) {
        Class cls2 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance((Class<?>) cls, 1).getClass(), cls2, cls2);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    public Class obtainFontFamily() {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    public Constructor obtainFontFamilyCtor(Class cls) {
        return cls.getConstructor(new Class[0]);
    }

    public Method obtainFreezeMethod(Class cls) {
        return cls.getMethod(FREEZE_METHOD, new Class[0]);
    }
}
