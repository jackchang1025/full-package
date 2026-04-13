package p002e;

import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.entity.RootInActiveWindowResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import p012o.C0416e;
import p012o.C0422k;

/* renamed from: e.a */
/* loaded from: classes.dex */
public final class RunnableC0261a implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f431a;

    /* renamed from: b */
    public final /* synthetic */ Object f432b;

    public /* synthetic */ RunnableC0261a(Object obj, int i2) {
        this.f431a = i2;
        this.f432b = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean m701e;
        int i2 = this.f431a;
        Object obj = this.f432b;
        switch (i2) {
            case 0:
                if (AbstractC0251g.m663j()) {
                    return;
                }
                synchronized (AbstractC0252h.class) {
                    m701e = AbstractC0252h.m701e("adbCanWriteSecure");
                }
                if (m701e) {
                    return;
                }
                AbstractC0246b.m600e();
                return;
            case 1:
                C0416e c0416e = (C0416e) obj;
                c0416e.getClass();
                MyAccessibilityService m554P = MyAccessibilityService.m554P();
                RootInActiveWindowResult m561R = m554P.m561R();
                AtomicInteger atomicInteger = new AtomicInteger(10);
                while (!m561R.isComplete() && atomicInteger.decrementAndGet() > 0) {
                    AbstractC0251g.T0(1);
                    m561R = m554P.m561R();
                }
                AccessibilityNodeInfo curRoot = m561R.getCurRoot();
                UiObject createRoot = curRoot != null ? UiObject.createRoot(curRoot) : null;
                if (createRoot != null) {
                    if (Objects.equals(c0416e.f871j.get(), createRoot.packageName())) {
                        c0416e.f869h.set(createRoot);
                    }
                }
                c0416e.f870i.set(true);
                return;
            case 2:
                C0422k c0422k = (C0422k) obj;
                c0422k.m1127I(c0422k.f925q && c0422k.f926r);
                return;
            default:
                AbstractC0184g.m348b((BlockViewVO) obj);
                return;
        }
    }
}
