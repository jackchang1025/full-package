package com.storm.safe.rock.service.modules;

import android.view.accessibility.AccessibilityNodeInfo;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.BiometricDisabler", m214403f = "BiometricDisabler.kt", m214404l = {586}, m214405m = "inputWrongPin")
/* loaded from: classes2.dex */
final class BiometricDisabler$inputWrongPin$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0317a2 f52756a0;

    /* renamed from: a1 */
    public AccessibilityNodeInfo f52757a1;

    /* renamed from: a2 */
    public String[] f52758a2;

    /* renamed from: a3 */
    public int f52759a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f52760a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0317a2 f52761a5;

    /* renamed from: a6 */
    public int f52762a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricDisabler$inputWrongPin$1(C0317a2 c0317a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52761a5 = c0317a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52760a4 = obj;
        this.f52762a6 |= Integer.MIN_VALUE;
        return this.f52761a5.m211564b5(this);
    }
}
