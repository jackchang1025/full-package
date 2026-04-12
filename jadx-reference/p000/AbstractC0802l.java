package p000;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: l */
/* loaded from: classes.dex */
public abstract class AbstractC0802l {

    /* renamed from: a0 */
    public static final pc0 f57815a0 = new pc0(16);

    /* renamed from: a1 */
    public static final ThreadPoolExecutor f57816a1;

    /* renamed from: a2 */
    public static final Object f57817a2;

    /* renamed from: a3 */
    public static final t01 f57818a3;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new nr0());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        f57816a1 = threadPoolExecutor;
        f57817a2 = new Object();
        f57818a3 = new t01();
    }

    /* renamed from: a0 */
    public static C0739k m213767a0(String str, Context context, C1094q2 c1094q2, int i) throws Resources.NotFoundException {
        pc0 pc0Var = f57815a0;
        Typeface typeface = (Typeface) pc0Var.m214243a0(str);
        if (typeface != null) {
            return new C0739k(typeface);
        }
        try {
            C1166r3 c1166r3M214434d0 = AbstractC1117qo.m214434d0(context, c1094q2);
            C1162r[] c1162rArr = (C1162r[]) c1166r3M214434d0.f59608a1;
            int i2 = c1166r3M214434d0.f59607a0;
            int i3 = 1;
            if (i2 != 0) {
                i3 = i2 != 1 ? -3 : -2;
            } else if (c1162rArr != null && c1162rArr.length != 0) {
                int length = c1162rArr.length;
                i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        break;
                    }
                    int i5 = c1162rArr[i4].f59577a4;
                    if (i5 == 0) {
                        i4++;
                    } else if (i5 >= 0) {
                        i3 = i5;
                    }
                }
            }
            if (i3 != 0) {
                return new C0739k(i3);
            }
            Typeface typefaceMo212561a9 = c81.f46076a0.mo212561a9(context, c1162rArr, i);
            if (typefaceMo212561a9 == null) {
                return new C0739k(-3);
            }
            pc0Var.m214244a1(str, typefaceMo212561a9);
            return new C0739k(typefaceMo212561a9);
        } catch (PackageManager.NameNotFoundException unused) {
            return new C0739k(-1);
        }
    }
}
