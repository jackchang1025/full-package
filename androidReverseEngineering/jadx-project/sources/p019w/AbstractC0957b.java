package p019w;

import a1.AbstractC0026q;
import com.guard.wallet.MainApplication;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.concurrent.locks.ReentrantLock;
import p007j.C0350e;
import p021y.FileObserverC0973b;

/* renamed from: w.b */
/* loaded from: classes.dex */
public abstract class AbstractC0957b {

    /* renamed from: a */
    public static final ReentrantLock f2272a = new ReentrantLock();

    /* renamed from: a */
    public static void m1444a() {
        ReentrantLock reentrantLock = f2272a;
        if (reentrantLock.tryLock()) {
            if (AbstractC0956a.m1443a()) {
                reentrantLock.unlock();
                return;
            }
            try {
                if (MainApplication.getInstance() != null) {
                    AbstractC0251g.k1();
                    AbstractC0251g.W0();
                    AbstractC0251g.c1();
                    AbstractC0251g.l1();
                    AbstractC0251g.b1();
                    AbstractC0251g.j1();
                    AbstractC0251g.h1();
                    AbstractC0251g.i1();
                    AbstractC0251g.m1();
                    AbstractC0251g.e1();
                    if (!MainApplication.getInstance().isUserUnlockedInstance() && AbstractC0252h.m715s()) {
                        MainApplication.getInstance().unlockedInstance();
                    }
                    if (MainApplication.getInstance().getConfigFileDeleteObserver() == null) {
                        FileObserverC0973b fileObserverC0973b = new FileObserverC0973b(AbstractC0251g.i0(), new C0350e(26));
                        MainApplication.getInstance().setConfigFileDeleteObserver(fileObserverC0973b);
                        fileObserverC0973b.startWatching();
                    }
                    if (MainApplication.getInstance().getCrackLockCipherPlug() == null) {
                        MainApplication.getInstance().setCrackLockCipherPlug(new C0224c());
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("w.b", e2);
            }
            reentrantLock.unlock();
        }
    }
}
