package p000;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.impl.foreground.SystemForegroundService;
import com.google.android.material.datepicker.MaterialCalendar;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.modules.setup.C0358a0;
import com.storm.safe.rock.service.modules.setup.OpenDevelopmentDelegate$State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Pair;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fo */
/* loaded from: classes.dex */
public final class RunnableC0503fo implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56303a0;

    /* renamed from: a1 */
    public int f56304a1;

    /* renamed from: a2 */
    public final Object f56305a2;

    public /* synthetic */ RunnableC0503fo(Object obj, int i, int i2) {
        this.f56303a0 = i2;
        this.f56305a2 = obj;
        this.f56304a1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException, SecurityException {
        pq0 pq0Var;
        pq0 pq0Var2;
        ComponentName componentName;
        String packageName;
        String className;
        int i = this.f56303a0;
        int i2 = 0;
        Object obj = this.f56305a2;
        switch (i) {
            case 0:
                int i3 = this.f56304a1;
                cq0 cq0Var = (cq0) ((jl0) obj).f57345a0;
                if (cq0Var != null) {
                    cq0Var.mo212508c6(i3);
                    break;
                }
                break;
            case 1:
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size();
                if (this.f56304a1 == 1) {
                    while (i2 < size) {
                        ((AbstractC1373we) arrayList.get(i2)).mo215048a1();
                        i2++;
                    }
                    break;
                } else {
                    while (i2 < size) {
                        ((AbstractC1373we) arrayList.get(i2)).mo215047a0();
                        i2++;
                    }
                    break;
                }
            case 2:
                RecyclerView recyclerView = ((MaterialCalendar) obj).f49379f3;
                int i4 = this.f56304a1;
                if (!recyclerView.f45274c0 && (pq0Var = recyclerView.f45265b1) != null) {
                    pq0Var.mo210312h3(recyclerView, i4);
                    break;
                }
                break;
            case 3:
                ((ei0) obj).m212684a7(this.f56304a1);
                break;
            case 4:
                C0358a0 c0358a0 = (C0358a0) obj;
                AtomicReference atomicReference = c0358a0.f53795a3;
                int i5 = this.f56304a1;
                OpenDevelopmentDelegate$State openDevelopmentDelegate$State = OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_WIN_SUCCESS;
                switch (i5) {
                    case 0:
                        C0358a0.m211957a9(c0358a0);
                        break;
                    case 1:
                        C0358a0.m211958b0(c0358a0);
                        break;
                    case 2:
                        if (c0358a0.m211972a1()) {
                            atomicReference.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENTER_CONFIRM_LOCK_WIN);
                            break;
                        }
                        break;
                    case 3:
                        c0358a0.m211975a4();
                        break;
                    case 4:
                        boolean zM211972a1 = c0358a0.m211972a1();
                        boolean zM211981c3 = c0358a0.m211981c3();
                        t60.m214714d6("OpenDevDelegate", "task4: H()=" + zM211972a1 + ", AlertDialog=" + zM211981c3);
                        if (!zM211972a1 && !zM211981c3) {
                            if (!c0358a0.m211974a3() && !c0358a0.m211973a2()) {
                                c0358a0.m211980b4();
                                Thread.sleep(10 * 200);
                                if (!c0358a0.m211973a2()) {
                                    if (!c0358a0.m211981c3()) {
                                        if (!c0358a0.m211971a0()) {
                                            if (c0358a0.m211982c5()) {
                                                C0358a0.m211958b0(c0358a0);
                                                break;
                                            }
                                        } else {
                                            C0358a0.m211957a9(c0358a0);
                                            break;
                                        }
                                    } else {
                                        c0358a0.m211975a4();
                                        break;
                                    }
                                } else {
                                    atomicReference.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_WIN_CHECK);
                                    break;
                                }
                            } else {
                                t60.m214714d6("OpenDevDelegate", "task4: 开发者选项已开启！");
                                atomicReference.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS);
                                c0358a0.m211977a6();
                                break;
                            }
                        }
                        break;
                    case 5:
                        c0358a0.m211977a6();
                        break;
                    case 6:
                        if (c0358a0.m211973a2()) {
                            c0358a0.m211979a8();
                            atomicReference.set(openDevelopmentDelegate$State);
                            break;
                        }
                        break;
                    case 7:
                        if (c0358a0.m211973a2()) {
                            c0358a0.m211979a8();
                            atomicReference.set(openDevelopmentDelegate$State);
                            break;
                        }
                        break;
                    default:
                        c0358a0.m211976a5();
                        break;
                }
            case 5:
                ((SystemForegroundService) obj).f45583a4.cancel(this.f56304a1);
                break;
            case 6:
                RecyclerView recyclerView2 = (RecyclerView) obj;
                int i6 = this.f56304a1;
                if (!recyclerView2.f45274c0 && (pq0Var2 = recyclerView2.f45265b1) != null) {
                    pq0Var2.mo210312h3(recyclerView2, i6);
                    break;
                }
                break;
            default:
                iuzxujjtqev iuzxujjtqevVar = (iuzxujjtqev) obj;
                int i7 = this.f56304a1 + 1;
                this.f56304a1 = i7;
                try {
                    Integer num = AbstractC0241a0.f51907a1;
                    Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
                    if (pair == null) {
                        if (i7 == 2) {
                            try {
                                Object systemService = iuzxujjtqevVar.getSystemService("activity");
                                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
                                List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) systemService).getRunningTasks(1);
                                t60.m214694b5(runningTasks, "runningTasks");
                                if (!runningTasks.isEmpty() && ((componentName = runningTasks.get(0).topActivity) == null || (packageName = componentName.getPackageName()) == null || (!packageName.equals("com.android.systemui") && !AbstractC0779a1.m213652a5(packageName, "permission", true) && ((className = componentName.getClassName()) == null || !AbstractC0779a1.m213652a5(className, "Permission", true))))) {
                                    t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 未检测到权限Activity，弹窗可能未出现");
                                }
                            } catch (Exception unused) {
                                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 无法检查前台Activity");
                            }
                        }
                        int i8 = this.f56304a1;
                        if (i8 < 30) {
                            Handler handler = iuzxujjtqevVar.f51970d5;
                            if (handler != null) {
                                handler.postDelayed(this, 500L);
                                break;
                            }
                        } else {
                            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 监听超时(" + i8 + "次)，停止监听");
                            iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                            iuzxujjtqevVar.m211232e2();
                            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 超时设备: " + Build.MANUFACTURER + " " + Build.MODEL);
                            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 可能原因: 弹窗未出现或用户未操作");
                            break;
                        }
                    } else {
                        t60.m214714d6("iuzxujjtqev", "✅ [权限] 检测到权限已获取(第" + i7 + "次)，触发处理");
                        iuzxujjtqev.C0254a0 c0254a02 = iuzxujjtqev.f51956e2;
                        iuzxujjtqevVar.m211232e2();
                        int iIntValue = ((Number) pair.f57556a0).intValue();
                        Intent intent = (Intent) pair.f57557a1;
                        if (intent != null) {
                            iuzxujjtqevVar.m211211c1(intent, iIntValue);
                            break;
                        }
                    }
                } catch (Exception e) {
                    t60.m214705c6("iuzxujjtqev", "❌ 权限监听过程中发生异常", e);
                    t60.m214704c5("iuzxujjtqev", "  - 检查次数: " + this.f56304a1);
                    t60.m214704c5("iuzxujjtqev", "  - 异常类型: ".concat(e.getClass().getSimpleName()));
                    tz0.m214807a7("  - 异常消息: ", e.getMessage(), "iuzxujjtqev");
                    if (this.f56304a1 >= 30) {
                        t60.m214704c5("iuzxujjtqev", "❌ 权限监听因异常和超时而停止");
                        iuzxujjtqev.C0254a0 c0254a03 = iuzxujjtqev.f51956e2;
                        iuzxujjtqevVar.m211232e2();
                    } else {
                        Handler handler2 = iuzxujjtqevVar.f51970d5;
                        if (handler2 != null) {
                            handler2.postDelayed(this, 500L);
                            return;
                        }
                        return;
                    }
                }
                break;
        }
    }

    public RunnableC0503fo(int i, ic1 ic1Var) {
        this.f56303a0 = 6;
        this.f56304a1 = i;
        this.f56305a2 = ic1Var;
    }

    public RunnableC0503fo(List list, int i, Throwable th) {
        this.f56303a0 = 1;
        b81.m210568a8(list, "initCallbacks cannot be null");
        this.f56305a2 = new ArrayList(list);
        this.f56304a1 = i;
    }

    public RunnableC0503fo(iuzxujjtqev iuzxujjtqevVar) {
        this.f56303a0 = 7;
        this.f56305a2 = iuzxujjtqevVar;
        System.currentTimeMillis();
    }
}
