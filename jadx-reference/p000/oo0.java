package p000;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class oo0 {
    public /* synthetic */ oo0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Lock getThreadLock(String str) {
        Lock lock;
        HashMap map = po0.f59313a5;
        synchronized (map) {
            try {
                Object reentrantLock = map.get(str);
                if (reentrantLock == null) {
                    reentrantLock = new ReentrantLock();
                    map.put(str, reentrantLock);
                }
                lock = (Lock) reentrantLock;
            } catch (Throwable th) {
                throw th;
            }
        }
        return lock;
    }

    private oo0() {
    }
}
