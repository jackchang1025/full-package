package p000;

import com.storm.safe.rock.service.modules.cipher.C0337a3;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class rm0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59794a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0337a3 f59795a1;

    public /* synthetic */ rm0(C0337a3 c0337a3, int i) {
        this.f59794a0 = i;
        this.f59795a1 = c0337a3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f59794a0) {
            case 0:
                C0337a3 c0337a3 = this.f59795a1;
                t60.m214695b6(c0337a3, "this$0");
                ReentrantLock reentrantLock = c0337a3.f53350a4;
                if (reentrantLock.tryLock()) {
                    try {
                        c0337a3.m211847b0();
                        return;
                    } finally {
                        reentrantLock.unlock();
                    }
                }
                return;
            default:
                C0337a3 c0337a32 = this.f59795a1;
                t60.m214695b6(c0337a32, "this$0");
                c0337a32.m211841a4();
                return;
        }
    }
}
