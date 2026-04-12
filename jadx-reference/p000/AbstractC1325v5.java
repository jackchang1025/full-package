package p000;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppLocalesMetadataHolderService;
import java.lang.ref.WeakReference;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v5 */
/* loaded from: classes.dex */
public abstract class AbstractC1325v5 {

    /* renamed from: a0 */
    public static final ExecutorC0034an f60575a0 = new ExecutorC0034an(new ExecutorC0101ao(0));

    /* renamed from: a1 */
    public static final int f60576a1 = -100;

    /* renamed from: a2 */
    public static dc0 f60577a2 = null;

    /* renamed from: a3 */
    public static dc0 f60578a3 = null;

    /* renamed from: a4 */
    public static Boolean f60579a4 = null;

    /* renamed from: a5 */
    public static boolean f60580a5 = false;

    /* renamed from: a6 */
    public static final C0132bf f60581a6 = new C0132bf(0);

    /* renamed from: a7 */
    public static final Object f60582a7 = new Object();

    /* renamed from: a8 */
    public static final Object f60583a8 = new Object();

    /* renamed from: a1 */
    public static boolean m214896a1(Context context) {
        if (f60579a4 == null) {
            try {
                int i = AppLocalesMetadataHolderService.f43775a0;
                Bundle bundle = context.getPackageManager().getServiceInfo(new ComponentName(context, (Class<?>) AppLocalesMetadataHolderService.class), AbstractC0033am.m209822a0() | 128).metaData;
                if (bundle != null) {
                    f60579a4 = Boolean.valueOf(bundle.getBoolean("autoStoreLocales"));
                }
            } catch (PackageManager.NameNotFoundException unused) {
                f60579a4 = Boolean.FALSE;
            }
        }
        return f60579a4.booleanValue();
    }

    /* renamed from: a5 */
    public static void m214897a5(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8) {
        synchronized (f60582a7) {
            try {
                Iterator it = f60581a6.iterator();
                while (true) {
                    xc0 xc0Var = (xc0) it;
                    if (xc0Var.hasNext()) {
                        AbstractC1325v5 abstractC1325v5 = (AbstractC1325v5) ((WeakReference) xc0Var.next()).get();
                        if (abstractC1325v5 == layoutInflaterFactory2C1367w8 || abstractC1325v5 == null) {
                            xc0Var.remove();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a0 */
    public abstract void mo214898a0();

    /* renamed from: a2 */
    public abstract void mo214899a2();

    /* renamed from: a3 */
    public abstract void mo214900a3();

    /* renamed from: a6 */
    public abstract boolean mo214901a6(int i);

    /* renamed from: a7 */
    public abstract void mo214902a7(int i);

    /* renamed from: a8 */
    public abstract void mo214903a8(View view);

    /* renamed from: a9 */
    public abstract void mo214904a9(View view, ViewGroup.LayoutParams layoutParams);

    /* renamed from: b1 */
    public abstract void mo214905b1(CharSequence charSequence);
}
