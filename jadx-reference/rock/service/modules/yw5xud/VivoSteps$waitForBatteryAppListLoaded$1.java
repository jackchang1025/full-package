package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import org.conscrypt.PSKKeyManager;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.VivoSteps", m214403f = "VivoSteps.kt", m214404l = {251, PSKKeyManager.MAX_KEY_LENGTH_BYTES}, m214405m = "waitForBatteryAppListLoaded")
/* loaded from: classes2.dex */
final class VivoSteps$waitForBatteryAppListLoaded$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0371a8 f54974a0;

    /* renamed from: a1 */
    public int f54975a1;

    /* renamed from: a2 */
    public int f54976a2;

    /* renamed from: a3 */
    public long f54977a3;

    /* renamed from: a4 */
    public long f54978a4;

    /* renamed from: a5 */
    public /* synthetic */ Object f54979a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0371a8 f54980a6;

    /* renamed from: a7 */
    public int f54981a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VivoSteps$waitForBatteryAppListLoaded$1(C0371a8 c0371a8, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54980a6 = c0371a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54979a5 = obj;
        this.f54981a7 |= Integer.MIN_VALUE;
        return this.f54980a6.m212436g4(0, 0L, this);
    }
}
