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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$tryClickByCoordinatesWithPageCheck$success$1$onCompleted$1", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {2080, 2081}, m214405m = "invokeSuspend")
/* renamed from: com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$tryClickByCoordinatesWithPageCheck$success$1$onCompleted$1 */
/* loaded from: classes2.dex */
final class C0314xa79daf25 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f53004a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0327b2 f53005a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f53006a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f53007a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0314xa79daf25(C0327b2 c0327b2, String str, String str2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53005a2 = c0327b2;
        this.f53006a3 = str;
        this.f53007a4 = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new C0314xa79daf25(this.f53005a2, this.f53006a3, this.f53007a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((C0314xa79daf25) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0036, code lost:
    
        if (com.storm.safe.rock.service.modules.C0327b2.m211694a1(r5.f53005a2, r5.f53006a3, r5.f53007a4, r5) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f53004a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f53004a1 = 1;
            if (b81.m210571b1(1500L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
        kg1.m213544f4(obj);
        this.f53004a1 = 2;
    }
}
