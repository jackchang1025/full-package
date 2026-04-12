package p000;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class f81 extends d81 {

    /* renamed from: a9 */
    public final Class f56178a9;

    /* renamed from: b0 */
    public final Constructor f56179b0;

    /* renamed from: b1 */
    public final Method f56180b1;

    /* renamed from: b2 */
    public final Method f56181b2;

    /* renamed from: b3 */
    public final Method f56182b3;

    /* renamed from: b4 */
    public final Method f56183b4;

    /* renamed from: b5 */
    public final Method f56184b5;

    public f81() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        Method methodMo212762g5;
        Constructor<?> constructor;
        Method methodM212757g4;
        Method method;
        Method method2;
        Method method3;
        Class<?> cls = null;
        try {
            Class<?> cls2 = Class.forName("android.graphics.FontFamily");
            constructor = cls2.getConstructor(null);
            methodM212757g4 = m212757g4(cls2);
            Class cls3 = Integer.TYPE;
            method = cls2.getMethod("addFontFromBuffer", ByteBuffer.class, cls3, FontVariationAxis[].class, cls3, cls3);
            method2 = cls2.getMethod("freeze", null);
            method3 = cls2.getMethod("abortCreation", null);
            methodMo212762g5 = mo212762g5(cls2);
            cls = cls2;
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            methodMo212762g5 = null;
            constructor = null;
            methodM212757g4 = null;
            method = null;
            method2 = null;
            method3 = null;
        }
        this.f56178a9 = cls;
        this.f56179b0 = constructor;
        this.f56180b1 = methodM212757g4;
        this.f56181b2 = method;
        this.f56182b3 = method2;
        this.f56183b4 = method3;
        this.f56184b5 = methodMo212762g5;
    }

    /* renamed from: g4 */
    public static Method m212757g4(Class cls) {
        Class cls2 = Boolean.TYPE;
        Class cls3 = Integer.TYPE;
        return cls.getMethod("addFontFromAssetManager", AssetManager.class, String.class, cls3, cls2, cls3, cls3, cls3, FontVariationAxis[].class);
    }

    @Override // p000.d81, p000.kg1
    /* renamed from: a8 */
    public final Typeface mo212560a8(Context context, C0934o c0934o, Resources resources, int i) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        if (this.f56180b1 == null) {
            return super.mo212560a8(context, c0934o, resources, i);
        }
        try {
            objNewInstance = this.f56179b0.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            objNewInstance = null;
        }
        if (objNewInstance != null) {
            C1050p[] c1050pArr = c0934o.f58705a0;
            int length = c1050pArr.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    C1050p c1050p = c1050pArr[i2];
                    Context context2 = context;
                    if (m212759g1(context2, objNewInstance, c1050p.f59127a0, c1050p.f59131a4, c1050p.f59128a1, c1050p.f59129a2 ? 1 : 0, FontVariationAxis.fromFontVariationSettings(c1050p.f59130a3))) {
                        i2++;
                        context = context2;
                    } else {
                        try {
                            this.f56183b4.invoke(objNewInstance, null);
                            break;
                        } catch (IllegalAccessException | InvocationTargetException unused2) {
                        }
                    }
                } else if (m212761g3(objNewInstance)) {
                    return mo212760g2(objNewInstance);
                }
            }
        }
        return null;
    }

    @Override // p000.d81, p000.kg1
    /* renamed from: a9 */
    public final Typeface mo212561a9(Context context, C1162r[] c1162rArr, int i) throws IllegalAccessException, InstantiationException, IOException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        Typeface typefaceMo212760g2;
        boolean zBooleanValue;
        if (c1162rArr.length >= 1) {
            try {
                if (this.f56180b1 != null) {
                    HashMap map = new HashMap();
                    for (C1162r c1162r : c1162rArr) {
                        if (c1162r.f59577a4 == 0) {
                            Uri uri = c1162r.f59573a0;
                            if (!map.containsKey(uri)) {
                                map.put(uri, kj1.m213578c5(context, uri));
                            }
                        }
                    }
                    Map mapUnmodifiableMap = Collections.unmodifiableMap(map);
                    try {
                        objNewInstance = this.f56179b0.newInstance(null);
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
                        objNewInstance = null;
                    }
                    if (objNewInstance != null) {
                        int length = c1162rArr.length;
                        int i2 = 0;
                        boolean z = false;
                        while (true) {
                            Method method = this.f56183b4;
                            if (i2 < length) {
                                C1162r c1162r2 = c1162rArr[i2];
                                ByteBuffer byteBuffer = (ByteBuffer) mapUnmodifiableMap.get(c1162r2.f59573a0);
                                if (byteBuffer != null) {
                                    try {
                                        zBooleanValue = ((Boolean) this.f56181b2.invoke(objNewInstance, byteBuffer, Integer.valueOf(c1162r2.f59574a1), null, Integer.valueOf(c1162r2.f59575a2), Integer.valueOf(c1162r2.f59576a3 ? 1 : 0))).booleanValue();
                                    } catch (IllegalAccessException | InvocationTargetException unused2) {
                                        zBooleanValue = false;
                                    }
                                    if (!zBooleanValue) {
                                        method.invoke(objNewInstance, null);
                                        break;
                                    }
                                    z = true;
                                }
                                i2++;
                                z = z;
                            } else if (!z) {
                                method.invoke(objNewInstance, null);
                            } else if (m212761g3(objNewInstance) && (typefaceMo212760g2 = mo212760g2(objNewInstance)) != null) {
                                return Typeface.create(typefaceMo212760g2, i);
                            }
                        }
                    }
                } else {
                    C1162r c1162rMo213008b4 = mo213008b4(i, c1162rArr);
                    ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(c1162rMo213008b4.f59573a0, "r", null);
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        try {
                            Typeface typefaceBuild = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).setWeight(c1162rMo213008b4.f59575a2).setItalic(c1162rMo213008b4.f59576a3).build();
                            parcelFileDescriptorOpenFileDescriptor.close();
                            return typefaceBuild;
                        } finally {
                        }
                    }
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                        return null;
                    }
                }
            } catch (IOException | IllegalAccessException | InvocationTargetException unused3) {
            }
        }
        return null;
    }

    @Override // p000.kg1
    /* renamed from: b1 */
    public final Typeface mo212758b1(Context context, Resources resources, int i, String str, int i2) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        if (this.f56180b1 == null) {
            return super.mo212758b1(context, resources, i, str, i2);
        }
        try {
            objNewInstance = this.f56179b0.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            objNewInstance = null;
        }
        if (objNewInstance != null) {
            if (!m212759g1(context, objNewInstance, str, 0, -1, -1, null)) {
                try {
                    this.f56183b4.invoke(objNewInstance, null);
                } catch (IllegalAccessException | InvocationTargetException unused2) {
                }
            } else if (m212761g3(objNewInstance)) {
                return mo212760g2(objNewInstance);
            }
        }
        return null;
    }

    /* renamed from: g1 */
    public final boolean m212759g1(Context context, Object obj, String str, int i, int i2, int i3, FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.f56180b1.invoke(obj, context.getAssets(), str, 0, Boolean.FALSE, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    /* renamed from: g2 */
    public Typeface mo212760g2(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) this.f56178a9, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) this.f56184b5.invoke(null, objNewInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    /* renamed from: g3 */
    public final boolean m212761g3(Object obj) {
        try {
            return ((Boolean) this.f56182b3.invoke(obj, null)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    /* renamed from: g5 */
    public Method mo212762g5(Class cls) throws NoSuchMethodException, SecurityException {
        Class<?> cls2 = Array.newInstance((Class<?>) cls, 1).getClass();
        Class cls3 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", cls2, cls3, cls3);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}
