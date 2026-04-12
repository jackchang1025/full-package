package p000;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class e81 extends kg1 {

    /* renamed from: a4 */
    public static final Class f55941a4;

    /* renamed from: a5 */
    public static final Constructor f55942a5;

    /* renamed from: a6 */
    public static final Method f55943a6;

    /* renamed from: a7 */
    public static final Method f55944a7;

    static {
        Class<?> cls;
        Method method;
        Method method2;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(null);
            Class cls2 = Integer.TYPE;
            method2 = cls.getMethod("addFontWeightStyle", ByteBuffer.class, cls2, List.class, cls2, Boolean.TYPE);
            method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            cls = null;
            method = null;
            method2 = null;
        }
        f55942a5 = constructor;
        f55941a4 = cls;
        f55943a6 = method2;
        f55944a7 = method;
    }

    /* renamed from: f9 */
    public static boolean m212661f9(Object obj, ByteBuffer byteBuffer, int i, int i2, boolean z) {
        try {
            return ((Boolean) f55943a6.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    /* renamed from: g0 */
    public static Typeface m212662g0(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) f55941a4, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) f55944a7.invoke(null, objNewInstance);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    @Override // p000.kg1
    /* renamed from: a8 */
    public final Typeface mo212560a8(Context context, C0934o c0934o, Resources resources, int i) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        MappedByteBuffer map;
        FileInputStream fileInputStream;
        try {
            objNewInstance = f55942a5.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            objNewInstance = null;
        }
        if (objNewInstance != null) {
            for (C1050p c1050p : c0934o.f58705a0) {
                int i2 = c1050p.f59132a5;
                File fileM213573c0 = kj1.m213573c0(context);
                if (fileM213573c0 != null) {
                    try {
                        if (kj1.m213563b0(fileM213573c0, resources, i2)) {
                            try {
                                fileInputStream = new FileInputStream(fileM213573c0);
                            } catch (IOException unused2) {
                                map = null;
                            }
                            try {
                                FileChannel channel = fileInputStream.getChannel();
                                map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                                fileInputStream.close();
                                if (map != null && m212661f9(objNewInstance, map, c1050p.f59131a4, c1050p.f59128a1, c1050p.f59129a2)) {
                                }
                            } finally {
                            }
                        }
                    } finally {
                        fileM213573c0.delete();
                    }
                }
                map = null;
                if (map != null) {
                }
            }
            return m212662g0(objNewInstance);
        }
        return null;
    }

    @Override // p000.kg1
    /* renamed from: a9 */
    public final Typeface mo212561a9(Context context, C1162r[] c1162rArr, int i) throws IllegalAccessException, InstantiationException, IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException, NegativeArraySizeException {
        Object objNewInstance;
        try {
            objNewInstance = f55942a5.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            objNewInstance = null;
        }
        if (objNewInstance != null) {
            t01 t01Var = new t01();
            int length = c1162rArr.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    C1162r c1162r = c1162rArr[i2];
                    Uri uri = c1162r.f59573a0;
                    ByteBuffer byteBufferM213578c5 = (ByteBuffer) t01Var.getOrDefault(uri, null);
                    if (byteBufferM213578c5 == null) {
                        byteBufferM213578c5 = kj1.m213578c5(context, uri);
                        t01Var.put(uri, byteBufferM213578c5);
                    }
                    if (byteBufferM213578c5 == null || !m212661f9(objNewInstance, byteBufferM213578c5, c1162r.f59574a1, c1162r.f59575a2, c1162r.f59576a3)) {
                        break;
                    }
                    i2++;
                } else {
                    Typeface typefaceM212662g0 = m212662g0(objNewInstance);
                    if (typefaceM212662g0 != null) {
                        return Typeface.create(typefaceM212662g0, i);
                    }
                }
            }
        }
        return null;
    }
}
