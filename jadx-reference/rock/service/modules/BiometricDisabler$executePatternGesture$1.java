package com.storm.safe.rock.service.modules;

import android.graphics.Path;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.BiometricDisabler", m214403f = "BiometricDisabler.kt", m214404l = {353, 356}, m214405m = "executePatternGesture")
/* loaded from: classes2.dex */
final class BiometricDisabler$executePatternGesture$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0317a2 f52745a0;

    /* renamed from: a1 */
    public Path f52746a1;

    /* renamed from: a2 */
    public int f52747a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f52748a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0317a2 f52749a4;

    /* renamed from: a5 */
    public int f52750a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricDisabler$executePatternGesture$1(C0317a2 c0317a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52749a4 = c0317a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52748a3 = obj;
        this.f52750a5 |= Integer.MIN_VALUE;
        return this.f52749a4.m211562a8(0.0f, 0.0f, 0.0f, 0.0f, this);
    }
}
