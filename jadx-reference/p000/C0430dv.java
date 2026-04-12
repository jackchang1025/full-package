package p000;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dv */
/* loaded from: classes2.dex */
public final class C0430dv {

    /* renamed from: a0 */
    public static final ArrayList f55884a0 = new ArrayList();

    /* renamed from: a1 */
    public static final Object f55885a1 = new Object();

    /* renamed from: a2 */
    public static long f55886a2;

    /* renamed from: a0 */
    public static Bitmap m212643a0(int i, int i2) {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        t60.m214695b6(config, "config");
        synchronized (f55885a1) {
            Iterator it = f55884a0.iterator();
            while (it.hasNext()) {
                Bitmap bitmap = (Bitmap) it.next();
                if (bitmap.getWidth() == i && bitmap.getHeight() == i2 && bitmap.getConfig() == config && !bitmap.isRecycled()) {
                    it.remove();
                    f55886a2 -= bitmap.getByteCount();
                    bitmap.eraseColor(0);
                    return bitmap;
                }
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, config);
            t60.m214694b5(bitmapCreateBitmap, "createBitmap(width, height, config)");
            return bitmapCreateBitmap;
        }
    }

    /* renamed from: a1 */
    public static void m212644a1() {
        synchronized (f55885a1) {
            try {
                ArrayList arrayList = f55884a0;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    Bitmap bitmap = (Bitmap) obj;
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
                f55884a0.clear();
                f55886a2 = 0L;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a2 */
    public static void m212645a2(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        synchronized (f55885a1) {
            try {
                ArrayList arrayList = f55884a0;
                if (arrayList.contains(bitmap)) {
                    return;
                }
                long byteCount = bitmap.getByteCount();
                if (arrayList.size() >= 15 || f55886a2 + byteCount > 20971520) {
                    bitmap.recycle();
                } else {
                    arrayList.add(bitmap);
                    f55886a2 += byteCount;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
