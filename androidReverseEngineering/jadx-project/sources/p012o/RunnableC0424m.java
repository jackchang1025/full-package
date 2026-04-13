package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.helper.AbstractC0184g;
import java.util.Objects;
import p014r.EnumC0892e;
import p022z.C0981d;

/* renamed from: o.m */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0424m implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f931a;

    /* renamed from: b */
    public final /* synthetic */ C0425n f932b;

    public /* synthetic */ RunnableC0424m(C0425n c0425n, int i2) {
        this.f931a = i2;
        this.f932b = c0425n;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0086 A[Catch: Exception -> 0x016e, TryCatch #0 {Exception -> 0x016e, blocks: (B:5:0x0011, B:7:0x0017, B:9:0x0037, B:11:0x0046, B:13:0x0055, B:14:0x005a, B:16:0x0060, B:17:0x0078, B:19:0x0086, B:21:0x0095, B:23:0x00a4, B:24:0x00a9, B:26:0x00af, B:27:0x00c7, B:29:0x00d5, B:31:0x00e4, B:33:0x00f3, B:34:0x00f8, B:36:0x00fe, B:37:0x0115, B:39:0x0123, B:41:0x0129, B:43:0x0141, B:44:0x0164, B:47:0x0153, B:48:0x0168, B:51:0x0112, B:55:0x00c3, B:59:0x0074), top: B:4:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d5 A[Catch: Exception -> 0x016e, TryCatch #0 {Exception -> 0x016e, blocks: (B:5:0x0011, B:7:0x0017, B:9:0x0037, B:11:0x0046, B:13:0x0055, B:14:0x005a, B:16:0x0060, B:17:0x0078, B:19:0x0086, B:21:0x0095, B:23:0x00a4, B:24:0x00a9, B:26:0x00af, B:27:0x00c7, B:29:0x00d5, B:31:0x00e4, B:33:0x00f3, B:34:0x00f8, B:36:0x00fe, B:37:0x0115, B:39:0x0123, B:41:0x0129, B:43:0x0141, B:44:0x0164, B:47:0x0153, B:48:0x0168, B:51:0x0112, B:55:0x00c3, B:59:0x0074), top: B:4:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0123 A[Catch: Exception -> 0x016e, TryCatch #0 {Exception -> 0x016e, blocks: (B:5:0x0011, B:7:0x0017, B:9:0x0037, B:11:0x0046, B:13:0x0055, B:14:0x005a, B:16:0x0060, B:17:0x0078, B:19:0x0086, B:21:0x0095, B:23:0x00a4, B:24:0x00a9, B:26:0x00af, B:27:0x00c7, B:29:0x00d5, B:31:0x00e4, B:33:0x00f3, B:34:0x00f8, B:36:0x00fe, B:37:0x0115, B:39:0x0123, B:41:0x0129, B:43:0x0141, B:44:0x0164, B:47:0x0153, B:48:0x0168, B:51:0x0112, B:55:0x00c3, B:59:0x0074), top: B:4:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c1  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        String str;
        UiObject findOneByCombine;
        String str2;
        String str3;
        boolean z2;
        UiObject findOneByCombine2;
        String str4;
        boolean z3;
        UiObject findOneByCombine3;
        String str5;
        UiObject findOneByCombine4;
        String str6;
        int i2 = this.f931a;
        boolean z4 = false;
        C0425n c0425n = this.f932b;
        switch (i2) {
            case 0:
                c0425n.getClass();
                try {
                    if (c0425n.j0()) {
                        Log.d("o.n", "keepAliveInHwSettings 窗口匹配");
                        AbstractC0184g.m354h(10);
                        c0425n.m1062G();
                        Log.d("o.n", "active root complete");
                        UiObject m1047Q = c0425n.m1047Q();
                        if (m1047Q != null) {
                            Log.d("o.n", "查找华为系统设置滚动视图成功");
                            AbstractC0184g.m354h(15);
                            C0981d c0981d = new C0981d(C0425n.e0(), 0);
                            UiObject scrollForwardUtil = m1047Q.scrollForwardUtil(c0981d);
                            if (scrollForwardUtil == null) {
                                scrollForwardUtil = m1047Q.scrollBackwardUtil(c0981d);
                            }
                            if (scrollForwardUtil == null) {
                                scrollForwardUtil = m1047Q.scrollForwardUtil(c0981d);
                            }
                            if (scrollForwardUtil != null) {
                                AbstractC0184g.m354h(20);
                                UiObject findParentUtilCombine = scrollForwardUtil.findParentUtilCombine(AbstractC0414c.m1036K());
                                if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                                    Log.d("o.n", "已点击进入应用和服务栏目");
                                    AbstractC0184g.m354h(25);
                                    break;
                                } else {
                                    str = "点击进入应用和服务栏目失败";
                                }
                            } else {
                                str = "查找应用和服务栏目栏目失败";
                            }
                        } else {
                            str = "查找华为系统设置滚动视图失败";
                        }
                        Log.e("o.n", str);
                        break;
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.n", e2);
                    return;
                }
                break;
            case 1:
                c0425n.getClass();
                try {
                    if (c0425n.i0()) {
                        Log.d("o.n", "keepAliveInAppAndNotification 窗口匹配");
                        AbstractC0184g.m354h(30);
                        c0425n.m1062G();
                        Log.d("o.n", "active root complete");
                        UiObject m1047Q2 = c0425n.m1047Q();
                        if (m1047Q2 != null) {
                            Log.d("o.n", "应用和服务窗口滚动视图查找成功");
                            AbstractC0184g.m354h(35);
                            C0981d c0981d2 = new C0981d(C0425n.g0(), 0);
                            findOneByCombine = m1047Q2.scrollForwardUtil(c0981d2);
                            if (findOneByCombine == null) {
                                findOneByCombine = m1047Q2.scrollBackwardUtil(c0981d2);
                            }
                        } else {
                            Log.e("o.n", "应用和服务窗口滚动视图查找失败");
                            findOneByCombine = c0425n.m1072k().findOneByCombine(C0425n.g0());
                        }
                        if (findOneByCombine != null) {
                            Log.d("o.n", "应用启动管理栏目查找成功");
                            AbstractC0184g.m354h(40);
                            UiObject findParentUtilCombine2 = findOneByCombine.findParentUtilCombine(AbstractC0414c.m1036K());
                            if (findParentUtilCombine2 != null && findParentUtilCombine2.click()) {
                                Log.d("o.n", "点击应用启动管理栏目完成");
                                AbstractC0184g.m354h(45);
                                break;
                            } else {
                                str2 = "点击应用启动管理栏目失败";
                            }
                        } else {
                            str2 = "应用启动管理栏目查找失败";
                        }
                        Log.e("o.n", str2);
                        break;
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.n", e3);
                    return;
                }
                break;
            case 2:
                c0425n.r0();
                break;
            case 3:
                c0425n.getClass();
                try {
                    if (c0425n.h0()) {
                        Log.d("o.n", "keepAliveInAlertDialog 窗口匹配");
                        AbstractC0184g.m354h(70);
                        c0425n.m1062G();
                        Log.d("o.n", "active root complete");
                        UiObject findOneByCombine5 = c0425n.m1072k().findOneByCombine(C0425n.b0());
                        if (findOneByCombine5 != null) {
                            Log.d("o.n", "自启动节点查找成功");
                            UiObject findParentUtilCombine3 = findOneByCombine5.findParentUtilCombine(AbstractC0414c.m1042U());
                            if (findParentUtilCombine3 != null) {
                                Log.d("o.n", "自启动栏目查找成功");
                                CheckedResult m1048R = c0425n.m1048R(findParentUtilCombine3, 5);
                                if (m1048R.isClicked()) {
                                    Log.d("o.n", "已点击允许自启动");
                                }
                                if (m1048R.isChecked()) {
                                    Log.d("o.n", "已勾选允许自启动");
                                    AbstractC0184g.m354h(75);
                                    z2 = true;
                                    findOneByCombine2 = c0425n.m1072k().findOneByCombine(C0425n.d0());
                                    if (findOneByCombine2 == null) {
                                        Log.d("o.n", "关联启动节点查找成功");
                                        UiObject findParentUtilCombine4 = findOneByCombine2.findParentUtilCombine(AbstractC0414c.m1042U());
                                        if (findParentUtilCombine4 != null) {
                                            Log.d("o.n", "关联启动栏目查找成功");
                                            CheckedResult m1048R2 = c0425n.m1048R(findParentUtilCombine4, 5);
                                            if (m1048R2.isClicked()) {
                                                Log.d("o.n", "已点击允许关联启动");
                                            }
                                            if (m1048R2.isChecked()) {
                                                Log.d("o.n", "已勾选允许关联启动");
                                                AbstractC0184g.m354h(80);
                                                z3 = true;
                                                findOneByCombine3 = c0425n.m1072k().findOneByCombine(C0425n.c0());
                                                if (findOneByCombine3 != null) {
                                                    Log.d("o.n", "允许后台活动节点查找成功");
                                                    UiObject findParentUtilCombine5 = findOneByCombine3.findParentUtilCombine(AbstractC0414c.m1042U());
                                                    if (findParentUtilCombine5 != null) {
                                                        Log.d("o.n", "允许后台活动栏目查找成功");
                                                        CheckedResult m1048R3 = c0425n.m1048R(findParentUtilCombine5, 5);
                                                        if (m1048R3.isClicked()) {
                                                            Log.d("o.n", "已点击允许后台活动");
                                                        }
                                                        if (m1048R3.isChecked()) {
                                                            Log.d("o.n", "已勾选允许后台活动");
                                                            AbstractC0184g.m354h(85);
                                                            z4 = true;
                                                            findOneByCombine4 = c0425n.m1072k().findOneByCombine(C0425n.l0());
                                                            if (findOneByCombine4 != null || !findOneByCombine4.click()) {
                                                                Log.e("o.n", "查找并点击确认按钮失败");
                                                                break;
                                                            } else {
                                                                Log.d("o.n", "查找并点击确认按钮完成");
                                                                AbstractC0184g.m354h(90);
                                                                if (Objects.equals(c0425n.f934r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP)) {
                                                                    c0425n.f935s.set(z2);
                                                                    c0425n.f937u.set(z3);
                                                                    c0425n.f939w.set(z4);
                                                                    str6 = "更新主进程保活策略";
                                                                } else {
                                                                    c0425n.f936t.set(z2);
                                                                    c0425n.f938v.set(z3);
                                                                    c0425n.f940x.set(z4);
                                                                    str6 = "更新备用进程保活策略";
                                                                }
                                                                Log.d("o.n", str6);
                                                                break;
                                                            }
                                                        } else {
                                                            str5 = "未勾选允许后台活动";
                                                        }
                                                    } else {
                                                        str5 = "允许后台活动栏目查找失败";
                                                    }
                                                } else {
                                                    str5 = "允许后台活动节点查找失败";
                                                }
                                                Log.e("o.n", str5);
                                                findOneByCombine4 = c0425n.m1072k().findOneByCombine(C0425n.l0());
                                                if (findOneByCombine4 != null) {
                                                }
                                                Log.e("o.n", "查找并点击确认按钮失败");
                                            } else {
                                                str4 = "未勾选允许关联启动";
                                            }
                                        } else {
                                            str4 = "关联启动栏目查找失败";
                                        }
                                    } else {
                                        str4 = "关联启动节点查找失败";
                                    }
                                    Log.e("o.n", str4);
                                    z3 = false;
                                    findOneByCombine3 = c0425n.m1072k().findOneByCombine(C0425n.c0());
                                    if (findOneByCombine3 != null) {
                                    }
                                    Log.e("o.n", str5);
                                    findOneByCombine4 = c0425n.m1072k().findOneByCombine(C0425n.l0());
                                    if (findOneByCombine4 != null) {
                                    }
                                    Log.e("o.n", "查找并点击确认按钮失败");
                                } else {
                                    str3 = "未勾选允许自启动";
                                }
                            } else {
                                str3 = "自启动栏目查找失败";
                            }
                        } else {
                            str3 = "自启动节点查找失败";
                        }
                        Log.e("o.n", str3);
                        z2 = false;
                        findOneByCombine2 = c0425n.m1072k().findOneByCombine(C0425n.d0());
                        if (findOneByCombine2 == null) {
                        }
                        Log.e("o.n", str4);
                        z3 = false;
                        findOneByCombine3 = c0425n.m1072k().findOneByCombine(C0425n.c0());
                        if (findOneByCombine3 != null) {
                        }
                        Log.e("o.n", str5);
                        findOneByCombine4 = c0425n.m1072k().findOneByCombine(C0425n.l0());
                        if (findOneByCombine4 != null) {
                        }
                        Log.e("o.n", "查找并点击确认按钮失败");
                    }
                } catch (Exception e4) {
                    AbstractC0026q.m186s("o.n", e4);
                }
                break;
            default:
                c0425n.mo1051Z();
                break;
        }
    }
}
