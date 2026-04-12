package com.storm.safe.rock.service.modules;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC0003a2;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$startPermissionMonitoring$1", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {448}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$startPermissionMonitoring$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f53001a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f53002a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0327b2 f53003a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$startPermissionMonitoring$1(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53003a3 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        WriteSettingsPermissionManager$startPermissionMonitoring$1 writeSettingsPermissionManager$startPermissionMonitoring$1 = new WriteSettingsPermissionManager$startPermissionMonitoring$1(this.f53003a3, interfaceC0876mv);
        writeSettingsPermissionManager$startPermissionMonitoring$1.f53002a2 = obj;
        return writeSettingsPermissionManager$startPermissionMonitoring$1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((WriteSettingsPermissionManager$startPermissionMonitoring$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f53001a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f53002a2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            interfaceC0920no = (InterfaceC0920no) this.f53002a2;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e) {
                t60.m214705c6("WriteSettingsPerm", "❌ 权限监听异常", e);
                C0327b2 c0327b2 = this.f53003a3;
                String strM48c9 = AbstractC0003a2.m48c9("权限监听异常: ", e.getMessage());
                int i2 = C0327b2.f53165c0;
                c0327b2.m211740e5(strM48c9);
            }
        }
        do {
            if (this.f53003a3.f53169a3 && AbstractC0780a0.m213691a2(interfaceC0920no.mo210226a1())) {
                if (this.f53003a3.m211734d5()) {
                    this.f53003a3.m211741e6();
                } else if (System.currentTimeMillis() - this.f53003a3.f53172a6 > 10000) {
                    C0327b2 c0327b22 = this.f53003a3;
                    t60.m214726f4("WriteSettingsPerm", "⏰ WRITE_SETTINGS权限申请超时");
                    try {
                        c0327b22.m211740e5("权限申请超时");
                    } catch (Exception e2) {
                        t60.m214705c6("WriteSettingsPerm", "❌ 超时处理失败，强制发送广播", e2);
                        c0327b22.m211749f5("权限申请超时", false);
                    }
                } else {
                    this.f53002a2 = interfaceC0920no;
                    this.f53001a1 = 1;
                }
            }
            return C1351vv.f60710b1;
        } while (b81.m210571b1(500L, this) != coroutineSingletons);
        return coroutineSingletons;
    }
}
