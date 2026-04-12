package com.storm.safe.rock.manager;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.MicrophoneManager$startRecording$1", m214403f = "MicrophoneManager.kt", m214404l = {202}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class MicrophoneManager$startRecording$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52009a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0259a1 f52010a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MicrophoneManager$startRecording$1(C0259a1 c0259a1, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52010a2 = c0259a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new MicrophoneManager$startRecording$1(this.f52010a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((MicrophoneManager$startRecording$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52009a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52009a1 = 1;
            if (C0259a1.m211251a0(this.f52010a2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        return C1351vv.f60710b1;
    }
}
