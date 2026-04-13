package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: o.p */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0427p implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f943a;

    /* renamed from: b */
    public final /* synthetic */ C0428q f944b;

    public /* synthetic */ RunnableC0427p(C0428q c0428q, int i2) {
        this.f943a = i2;
        this.f944b = c0428q;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f943a;
        C0428q c0428q = this.f944b;
        switch (i2) {
            case 0:
                c0428q.mo1051Z();
                break;
            case 1:
                c0428q.getClass();
                try {
                    AtomicInteger atomicInteger = new AtomicInteger(0);
                    while (!c0428q.f0() && atomicInteger.incrementAndGet() < 20) {
                        AbstractC0251g.T0(1);
                    }
                    c0428q.c0();
                    c0428q.j0();
                    break;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.q", e2);
                }
            default:
                c0428q.getClass();
                try {
                    if (c0428q.h0()) {
                        Log.d("o.q", "keepAliveInAutoStartManage 窗口匹配");
                        AbstractC0184g.m354h(80);
                        String x02 = AbstractC0251g.x0();
                        if (!AbstractC0026q.m151B(x02)) {
                            Log.d("o.q", "mainAppLabel:" + x02);
                            if (c0428q.i0(x02)) {
                                c0428q.f947s.set(true);
                                Log.d("o.q", x02.concat(" 已开启自启动"));
                                AbstractC0184g.m354h(90);
                            } else {
                                Log.e("o.q", x02.concat(" 未开启自启动"));
                                x02.concat(" 未开启自启动");
                            }
                        }
                        String m658e = AbstractC0251g.m658e();
                        if (AbstractC0251g.d0("com.google.guard") != null) {
                            Log.d("o.q", "backupAppLabel:" + m658e);
                            if (c0428q.i0(m658e)) {
                                c0428q.f948t.set(true);
                                Log.d("o.q", m658e.concat(" 已开启自启动"));
                                AbstractC0184g.m354h(90);
                            } else {
                                Log.e("o.q", m658e.concat(" 未开启自启动"));
                                m658e.concat(" 未开启自启动");
                            }
                        }
                        c0428q.j0();
                        break;
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.q", e3);
                    return;
                }
                break;
        }
    }
}
