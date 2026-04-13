package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.Objects;
import p014r.EnumC0892e;
import p022z.C0980c;
import p022z.C0981d;

/* renamed from: o.u */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0432u implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f962a;

    /* renamed from: b */
    public final /* synthetic */ C0433v f963b;

    public /* synthetic */ RunnableC0432u(C0433v c0433v, int i2) {
        this.f962a = i2;
        this.f963b = c0433v;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x00cc -> B:41:0x00d4). Please report as a decompilation issue!!! */
    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f962a;
        C0433v c0433v = this.f963b;
        switch (i2) {
            case 0:
                c0433v.getClass();
                try {
                    if (c0433v.k0()) {
                        Log.d("o.v", "keepAliveInAppDetail 窗口匹配");
                        AbstractC0184g.m354h(10);
                        c0433v.m1062G();
                        Log.d("o.v", "active root complete");
                        UiObject m1047Q = c0433v.m1047Q();
                        CombineFilter B0 = C0433v.B0();
                        CombineFilter C0 = C0433v.C0();
                        UiObject uiObject = null;
                        if (m1047Q != null) {
                            Log.d("o.v", "应用详情窗口滚动视图查找成功");
                            AbstractC0184g.m354h(15);
                            if (B0 != null) {
                                C0981d c0981d = new C0981d(B0, 0);
                                UiObject scrollForwardUtil = m1047Q.scrollForwardUtil(c0981d);
                                uiObject = scrollForwardUtil == null ? m1047Q.scrollBackwardUtil(c0981d) : scrollForwardUtil;
                            }
                            if (uiObject == null && C0 != null) {
                                C0981d c0981d2 = new C0981d(C0, 0);
                                UiObject scrollBackwardUtil = m1047Q.scrollBackwardUtil(c0981d2);
                                uiObject = scrollBackwardUtil == null ? m1047Q.scrollForwardUtil(c0981d2) : scrollBackwardUtil;
                            }
                        }
                        if (uiObject == null && c0433v.m1072k() != null) {
                            Log.e("o.v", "应用详情窗口滚动视图查找失败");
                            if (B0 != null) {
                                uiObject = c0433v.m1072k().findOneByCombine(B0);
                            }
                            if (uiObject == null && C0 != null) {
                                uiObject = c0433v.m1072k().findOneByCombine(C0);
                            }
                        }
                        if (uiObject != null && uiObject.click()) {
                            Log.d("o.v", "查找并点击耗电管理栏目成功");
                            AbstractC0184g.m354h(30);
                            break;
                        } else {
                            Log.e("o.v", "查找并点击耗电管理栏目失败");
                            break;
                        }
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("o.v", e2);
                    return;
                }
                break;
            case 1:
                c0433v.getClass();
                try {
                    if (c0433v.l0()) {
                        Log.d("o.v", "keepAliveInPowerControl 窗口匹配");
                        AbstractC0184g.m354h(40);
                        c0433v.m1062G();
                        Log.d("o.v", "active root complete");
                        if (!c0433v.s0()) {
                            Log.e("o.v", "允许自启动行为失败");
                        }
                        AbstractC0184g.m354h(50);
                        if (!c0433v.t0()) {
                            Log.e("o.v", "允许关联启动行为失败");
                        }
                        AbstractC0184g.m354h(60);
                        if (!c0433v.r0()) {
                            Log.e("o.v", "允许完全后台行为失败");
                            break;
                        } else {
                            AbstractC0184g.m354h(70);
                            c0433v.u0();
                            break;
                        }
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.v", e3);
                    return;
                }
                break;
            case 2:
                c0433v.getClass();
                try {
                    if (c0433v.j0()) {
                        Log.d("o.v", "checkInAndroidXDialog 窗口匹配");
                        AbstractC0184g.m354h(80);
                        c0433v.m1062G();
                        Log.d("o.v", "active root complete");
                        try {
                            UiObject findOneByCombineLoop = c0433v.m1072k().findOneByCombineLoop(C0433v.d0());
                            if (findOneByCombineLoop == null || !findOneByCombineLoop.click()) {
                                Log.e("o.v", "查找并点击允许确认按钮失败");
                            } else {
                                Log.d("o.v", "查找并点击允许确认按钮完成");
                                AbstractC0184g.m354h(90);
                            }
                        } catch (Exception e4) {
                            AbstractC0026q.m186s("o.v", e4);
                        }
                    }
                    break;
                } catch (Exception e5) {
                    AbstractC0026q.m186s("o.v", e5);
                    return;
                }
                break;
            case 3:
                c0433v.getClass();
                try {
                    if (c0433v.m0()) {
                        c0433v.m1062G();
                        Log.d("o.v", "active root complete");
                        String x02 = Objects.equals(c0433v.f965r.get(), EnumC0892e.KEEP_ALIVE_MAIN_APP) ? AbstractC0251g.x0() : AbstractC0251g.m658e();
                        Log.d("o.v", "keepAliveInStartup 窗口匹配");
                        UiObject m1047Q2 = c0433v.m1047Q();
                        CombineFilterWithChild combineFilterWithChild = new CombineFilterWithChild(AbstractC0414c.m1036K(), AbstractC0414c.m1033H(x02));
                        UiObject scrollForwardUtil2 = m1047Q2 != null ? m1047Q2.scrollForwardUtil(new C0980c(combineFilterWithChild, 0)) : c0433v.m1072k().findOneByCombineWithChild(combineFilterWithChild);
                        if (scrollForwardUtil2 != null) {
                            CheckedResult m1048R = c0433v.m1048R(scrollForwardUtil2, 5);
                            if (m1048R.isClicked()) {
                                Log.d("o.v", "已点击自启动");
                            }
                            if (!m1048R.isChecked()) {
                                Log.e("o.v", "未勾选自启动");
                                break;
                            } else {
                                Log.d("o.v", "已勾选自启动");
                                c0433v.f967t.set(true);
                                break;
                            }
                        }
                    }
                } catch (Exception e6) {
                    AbstractC0026q.m186s("o.v", e6);
                }
                break;
            default:
                c0433v.mo1051Z();
                break;
        }
    }
}
