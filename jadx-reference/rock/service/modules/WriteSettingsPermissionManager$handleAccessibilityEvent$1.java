package com.storm.safe.rock.service.modules;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$handleAccessibilityEvent$1", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {488, 495, 495, 497}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$handleAccessibilityEvent$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52966a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52967a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0327b2 f52968a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$handleAccessibilityEvent$1(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52968a3 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        WriteSettingsPermissionManager$handleAccessibilityEvent$1 writeSettingsPermissionManager$handleAccessibilityEvent$1 = new WriteSettingsPermissionManager$handleAccessibilityEvent$1(this.f52968a3, interfaceC0876mv);
        writeSettingsPermissionManager$handleAccessibilityEvent$1.f52967a2 = obj;
        return writeSettingsPermissionManager$handleAccessibilityEvent$1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((WriteSettingsPermissionManager$handleAccessibilityEvent$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a0, code lost:
    
        if (r10 == r1) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0094  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52966a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f52967a2;
            this.f52967a2 = interfaceC0920no;
            this.f52966a1 = 1;
            if (b81.m210571b1(1000L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i == 2) {
                kg1.m213544f4(obj);
                if (((Boolean) obj).booleanValue()) {
                    C0327b2 c0327b2 = this.f52968a3;
                    this.f52966a1 = 3;
                    int i2 = C0327b2.f53165c0;
                    obj = c0327b2.m211755g2(10, 1000L, this);
                }
                return c1351vv;
            }
            if (i == 3) {
                kg1.m213544f4(obj);
                ((Boolean) obj).booleanValue();
                return c1351vv;
            }
            if (i != 4) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return c1351vv;
        }
        interfaceC0920no = (InterfaceC0920no) this.f52967a2;
        kg1.m213544f4(obj);
        if (AbstractC0780a0.m213691a2(interfaceC0920no.mo210226a1()) && this.f52968a3.f53169a3) {
            if (this.f52968a3.m211734d5()) {
                this.f52968a3.m211741e6();
                return c1351vv;
            }
            int iOrdinal = this.f52968a3.f53176b0.ordinal();
            if (iOrdinal == 0) {
                C0327b2 c0327b22 = this.f52968a3;
                this.f52967a2 = null;
                this.f52966a1 = 2;
                obj = c0327b22.m211714a3(this);
                if (obj != coroutineSingletons) {
                    if (((Boolean) obj).booleanValue()) {
                    }
                }
            } else if (iOrdinal == 1) {
                C0327b2 c0327b23 = this.f52968a3;
                this.f52967a2 = null;
                this.f52966a1 = 4;
                if (C0327b2.m211693a0(c0327b23, this) == coroutineSingletons) {
                }
            }
            return coroutineSingletons;
        }
        return c1351vv;
    }
}
