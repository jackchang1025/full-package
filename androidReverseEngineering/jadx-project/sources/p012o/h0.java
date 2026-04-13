package p012o;

import a1.AbstractC0026q;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p014r.EnumC0892e;
import p022z.C0981d;

/* loaded from: classes.dex */
public final /* synthetic */ class h0 implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f898a;

    /* renamed from: b */
    public final /* synthetic */ i0 f899b;

    public /* synthetic */ h0(i0 i0Var, int i2) {
        this.f898a = i2;
        this.f899b = i0Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        String str2;
        UiObject findParentUtilCombine;
        UiObject m1047Q;
        String m658e;
        UiObject uiObject;
        String concat;
        String str3;
        String str4;
        EnumC0892e enumC0892e = EnumC0892e.KEEP_ALIVE_MAIN_APP;
        int i2 = this.f898a;
        i0 i0Var = this.f899b;
        switch (i2) {
            case 0:
                i0Var.mo1051Z();
                break;
            case 1:
                i0Var.getClass();
                try {
                    if (i0Var.p0()) {
                        Log.d("o.i0", "keepAliveInPowerRank 窗口匹配");
                        AbstractC0184g.m354h(10);
                        i0Var.m1062G();
                        Log.d("o.i0", "active root complete");
                        UiObject findOneByCombine = i0Var.m1072k().findOneByCombine(i0.E0());
                        if (findOneByCombine == null && (m1047Q = i0Var.m1047Q()) != null) {
                            m1047Q.scrollBackwardEnd();
                            AbstractC0251g.T0(5);
                        }
                        if (findOneByCombine == null) {
                            findOneByCombine = i0Var.m1072k().findOneByCombine(i0.E0());
                        }
                        if (findOneByCombine != null) {
                            Log.d("o.i0", "后台耗电管理栏目查找成功");
                            int i3 = Build.VERSION.SDK_INT;
                            AtomicReference atomicReference = i0Var.f905s;
                            if (i3 >= 35 && (findParentUtilCombine = findOneByCombine.findParentUtilCombine(AbstractC0414c.m1037L())) != null && findParentUtilCombine.click()) {
                                str2 = "后台耗电父节点点击成功";
                            } else if (findOneByCombine.click()) {
                                str2 = "后台耗电管理栏目点击成功";
                            } else {
                                str = "后台耗电管理栏目点击失败";
                            }
                            Log.d("o.i0", str2);
                            AbstractC0184g.m354h(15);
                            atomicReference.set("prepareInExcessivePowerManager");
                            break;
                        } else {
                            str = "后台耗电管理栏目查找失败";
                        }
                        Log.e("o.i0", str);
                        break;
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.i0", e2);
                    return;
                }
                break;
            case 2:
                i0Var.getClass();
                try {
                    if (i0Var.n0()) {
                        Log.d("o.i0", "keepAliveInExcessivePowerManager 窗口匹配");
                        AbstractC0184g.m354h(20);
                        i0Var.m1062G();
                        Log.d("o.i0", "active root complete");
                        AtomicReference atomicReference2 = i0Var.f904r;
                        Object obj = atomicReference2.get();
                        EnumC0892e enumC0892e2 = EnumC0892e.KEEP_ALIVE_UNKNOWN;
                        if (Objects.equals(obj, enumC0892e2)) {
                            atomicReference2.set(enumC0892e);
                            m658e = AbstractC0251g.x0();
                        } else if (!Objects.equals(atomicReference2.get(), enumC0892e) || AbstractC0251g.d0("com.google.guard") == null) {
                            atomicReference2.set(enumC0892e2);
                            i0Var.z0();
                            break;
                        } else {
                            atomicReference2.set(EnumC0892e.KEEP_ALIVE_BACKUP_APP);
                            m658e = AbstractC0251g.m658e();
                        }
                        UiObject m1047Q2 = i0Var.m1047Q();
                        AtomicInteger atomicInteger = new AtomicInteger(0);
                        while (m1047Q2 == null && atomicInteger.incrementAndGet() <= 5) {
                            AbstractC0251g.T0(5);
                            m1047Q2 = i0Var.m1047Q();
                        }
                        if (m1047Q2 != null) {
                            if (m1047Q2.canScrollBackward()) {
                                m1047Q2.scrollBackwardEnd();
                                AbstractC0251g.T0(5);
                            }
                            Log.d("o.i0", "应用耗电管理窗口滚动视图查找成功");
                            AbstractC0184g.m354h(25);
                            C0981d c0981d = new C0981d(AbstractC0414c.m1033H(m658e), 0);
                            uiObject = m1047Q2.scrollForwardUtil(c0981d);
                            if (uiObject == null) {
                                uiObject = m1047Q2.scrollBackwardUtil(c0981d);
                            }
                        } else {
                            Log.e("o.i0", "应用耗电管理窗口滚动视图查找失败");
                            uiObject = null;
                        }
                        if (uiObject == null) {
                            uiObject = i0Var.m1072k().findOneByCombine(AbstractC0414c.m1033H(m658e));
                        }
                        if (uiObject == null) {
                            Objects.requireNonNull(m658e);
                            Log.e("o.i0", m658e.concat(" App栏目查找失败"));
                            m658e.concat(" App栏目查找失败");
                            break;
                        } else {
                            Objects.requireNonNull(m658e);
                            Log.d("o.i0", m658e.concat(" App栏目查找成功"));
                            AbstractC0184g.m354h(30);
                            m658e.concat(" App栏目查找成功");
                            UiObject findParentUtilCombine2 = uiObject.findParentUtilCombine(AbstractC0414c.m1037L());
                            AtomicReference atomicReference3 = i0Var.f905s;
                            if (findParentUtilCombine2 != null && findParentUtilCombine2.click()) {
                                concat = m658e.concat(" App栏目点击成功");
                            } else if (!uiObject.click()) {
                                Log.e("o.i0", m658e.concat(" App栏目点击失败"));
                                m658e.concat(" App栏目点击失败");
                                break;
                            } else {
                                concat = m658e.concat(" App栏目点击成功");
                            }
                            Log.d("o.i0", concat);
                            AbstractC0184g.m354h(35);
                            m658e.concat(" App栏目点击成功");
                            atomicReference3.set("prepareInExcessivePowerDescription");
                            break;
                        }
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.i0", e3);
                    return;
                }
                break;
            case 3:
                i0Var.getClass();
                try {
                    if (i0Var.m0()) {
                        Log.d("o.i0", "keepAliveInExcessivePowerDescription 窗口匹配");
                        AbstractC0184g.m354h(40);
                        i0Var.m1062G();
                        Log.d("o.i0", "active root complete");
                        if (i0Var.m1072k() != null) {
                            UiObject findOneByCombine2 = i0Var.m1072k().findOneByCombine(i0.C0());
                            if (findOneByCombine2 != null) {
                                Log.d("o.i0", "允许后台高耗电查找成功");
                                if (findOneByCombine2.click()) {
                                    Log.d("o.i0", "允许后台高耗电点击成功");
                                    AbstractC0184g.m354h(40);
                                    (Objects.equals(i0Var.f904r.get(), enumC0892e) ? i0Var.f910x : i0Var.f911y).set(true);
                                    i0Var.f905s.set("prepareInExcessivePowerManager");
                                    AbstractC0251g.F0(1);
                                    break;
                                } else {
                                    str3 = "允许后台高耗电点击失败";
                                }
                            } else {
                                str3 = "允许后台高耗电查找失败";
                            }
                            Log.d("o.i0", str3);
                            break;
                        }
                    }
                } catch (Exception e4) {
                    AbstractC0026q.m186s("o.i0", e4);
                    return;
                }
                break;
            case 4:
                i0Var.getClass();
                try {
                    if (i0Var.j0()) {
                        Log.d("o.i0", "keepAliveInAppDetail 窗口匹配");
                        AbstractC0184g.m354h(50);
                        i0Var.m1062G();
                        Log.d("o.i0", "active root complete");
                        UiObject findOneByCombine3 = i0Var.m1072k().findOneByCombine(i0.H0());
                        if (findOneByCombine3 != null) {
                            Log.d("o.i0", "权限栏目查找完成");
                            UiObject findParentUtilCombine3 = findOneByCombine3.findParentUtilCombine(AbstractC0414c.m1037L());
                            if (findParentUtilCombine3 == null || !findParentUtilCombine3.click()) {
                                str4 = "查找并点击权限栏目失败";
                            } else {
                                Log.d("o.i0", "查找并点击权限栏目完成");
                                AbstractC0184g.m354h(55);
                                AbstractC0251g.T0(10);
                                i0Var.f905s.set("prepareInAppPermissionManage");
                                i0Var.t0();
                            }
                        } else {
                            str4 = "权限栏目查找失败";
                        }
                        Log.e("o.i0", str4);
                    }
                    break;
                } catch (Exception e5) {
                    AbstractC0026q.m186s("o.i0", e5);
                    return;
                }
            case 5:
                i0Var.t0();
                break;
            case 6:
                i0Var.getClass();
                try {
                    if (i0Var.k0()) {
                        Log.d("o.i0", "keepAliveInAppPermissionDetail 窗口匹配");
                        AbstractC0184g.m354h(60);
                        i0Var.m1062G();
                        Log.d("o.i0", "active root complete");
                        UiObject m1047Q3 = i0Var.m1047Q();
                        AtomicInteger atomicInteger2 = new AtomicInteger(0);
                        while (m1047Q3 == null && atomicInteger2.incrementAndGet() <= 5) {
                            AbstractC0251g.T0(5);
                            m1047Q3 = i0Var.m1047Q();
                        }
                        if (m1047Q3 != null && m1047Q3.canScrollBackward()) {
                            Log.d("o.i0", "App所有权限窗口滚动至顶部成功");
                            m1047Q3.scrollForwardEnd();
                            i0Var.m1072k().refresh();
                        }
                        UiObject findOneByCombine4 = i0Var.m1072k().findOneByCombine(i0.i0());
                        AtomicReference atomicReference4 = i0Var.f904r;
                        if (findOneByCombine4 != null) {
                            AbstractC0251g.T0(5);
                            Log.d("o.i0", "自启动栏目查找成功");
                            CheckedResult m1041S = AbstractC0414c.m1041S(findOneByCombine4.parent());
                            if (!m1041S.isClicked() && !m1041S.isChecked()) {
                                Log.d("o.i0", "未勾选App自启动");
                            }
                            Log.d("o.i0", "自启动栏目点击成功");
                            AbstractC0184g.m354h(65);
                            (Objects.equals(atomicReference4.get(), enumC0892e) ? i0Var.f906t : i0Var.f907u).set(true);
                        }
                        UiObject findOneByCombine5 = i0Var.m1072k().findOneByCombine(i0.w0());
                        if (findOneByCombine5 != null) {
                            AbstractC0251g.T0(5);
                            Log.d("o.i0", "后台弹出界面栏目查找成功");
                            CheckedResult m1041S2 = AbstractC0414c.m1041S(findOneByCombine5.parent());
                            if (!m1041S2.isClicked() && !m1041S2.isChecked()) {
                                Log.e("o.i0", "未勾选后台弹出界面");
                            }
                            Log.d("o.i0", "后台弹出界面栏目点击成功");
                            AbstractC0184g.m354h(70);
                            (Objects.equals(atomicReference4.get(), enumC0892e) ? i0Var.f912z : i0Var.f903A).set(true);
                        }
                        i0Var.z0();
                        break;
                    }
                } catch (Exception e6) {
                    AbstractC0026q.m186s("o.i0", e6);
                }
                break;
            default:
                i0Var.getClass();
                try {
                    if (i0Var.o0()) {
                        Log.d("o.i0", "keepAliveInPermissionAllowDialog 窗口匹配");
                        i0Var.m1062G();
                        Log.d("o.i0", "active root complete");
                        UiObject findOneByCombine6 = i0Var.m1072k().findOneByCombine(i0.b0());
                        if (findOneByCombine6 == null || !findOneByCombine6.click()) {
                            Log.e("o.i0", "查找并点击允许按钮失败");
                        } else {
                            Log.d("o.i0", "查找并点击允许按钮完成");
                            AbstractC0184g.m354h(80);
                            (Objects.equals(i0Var.f904r.get(), enumC0892e) ? i0Var.f908v : i0Var.f909w).set(true);
                        }
                        i0Var.z0();
                        break;
                    }
                } catch (Exception e7) {
                    AbstractC0026q.m186s("o.i0", e7);
                    return;
                }
                break;
        }
    }
}
