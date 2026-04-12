package com.storm.safe.rock.service.modules;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {3462}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52946a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0327b2 f52947a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52947a2 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2(this.f52947a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((WriteSettingsPermissionManager$attemptVivoRightSwitchToggle$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52946a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52946a1 = 1;
            if (b81.m210571b1(200L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        this.f52947a2.f53185b9 = false;
        return C1351vv.f60710b1;
    }
}
