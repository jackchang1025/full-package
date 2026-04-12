package p000;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class d81 extends kg1 {

    /* renamed from: a4 */
    public static Class f55575a4 = null;

    /* renamed from: a5 */
    public static Constructor f55576a5 = null;

    /* renamed from: a6 */
    public static Method f55577a6 = null;

    /* renamed from: a7 */
    public static Method f55578a7 = null;

    /* renamed from: a8 */
    public static boolean f55579a8 = false;

    /* renamed from: f9 */
    public static boolean m212558f9(Object obj, String str, int i, boolean z) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        m212559g0();
        try {
            return ((Boolean) f55577a6.invoke(obj, str, Integer.valueOf(i), Boolean.valueOf(z))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /* renamed from: g0 */
    public static void m212559g0() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        Method method;
        Class<?> cls;
        Method method2;
        if (f55579a8) {
            return;
        }
        f55579a8 = true;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(null);
            method2 = cls.getMethod("addFontWeightStyle", String.class, Integer.TYPE, Boolean.TYPE);
            method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            method = null;
            cls = null;
            method2 = null;
        }
        f55576a5 = constructor;
        f55575a4 = cls;
        f55577a6 = method2;
        f55578a7 = method;
    }

    @Override // p000.kg1
    /* renamed from: a8 */
    public Typeface mo212560a8(Context context, C0934o c0934o, Resources resources, int i) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException, NegativeArraySizeException {
        m212559g0();
        try {
            Object objNewInstance = f55576a5.newInstance(null);
            for (C1050p c1050p : c0934o.f58705a0) {
                File fileM213573c0 = kj1.m213573c0(context);
                if (fileM213573c0 == null) {
                    return null;
                }
                try {
                    if (!kj1.m213563b0(fileM213573c0, resources, c1050p.f59132a5)) {
                        return null;
                    }
                    if (!m212558f9(objNewInstance, fileM213573c0.getPath(), c1050p.f59128a1, c1050p.f59129a2)) {
                        return null;
                    }
                    fileM213573c0.delete();
                } catch (RuntimeException unused) {
                    return null;
                } finally {
                    fileM213573c0.delete();
                }
            }
            m212559g0();
            try {
                Object objNewInstance2 = Array.newInstance((Class<?>) f55575a4, 1);
                Array.set(objNewInstance2, 0, objNewInstance);
                return (Typeface) f55578a7.invoke(null, objNewInstance2);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // p000.kg1
    /* renamed from: a9 */
    public Typeface mo212561a9(Context context, C1162r[] c1162rArr, int i) throws IOException {
        String str;
        if (c1162rArr.length >= 1) {
            try {
                ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(mo213008b4(i, c1162rArr).f59573a0, "r", null);
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    try {
                        try {
                            str = Os.readlink("/proc/self/fd/" + parcelFileDescriptorOpenFileDescriptor.getFd());
                        } finally {
                        }
                    } catch (ErrnoException unused) {
                    }
                    File file = OsConstants.S_ISREG(Os.stat(str).st_mode) ? new File(str) : null;
                    if (file != null && file.canRead()) {
                        Typeface typefaceCreateFromFile = Typeface.createFromFile(file);
                        parcelFileDescriptorOpenFileDescriptor.close();
                        return typefaceCreateFromFile;
                    }
                    FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor());
                    try {
                        Typeface typefaceMo213007b0 = mo213007b0(context, fileInputStream);
                        fileInputStream.close();
                        parcelFileDescriptorOpenFileDescriptor.close();
                        return typefaceMo213007b0;
                    } finally {
                    }
                }
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                    return null;
                }
            } catch (IOException unused2) {
            }
        }
        return null;
    }
}
