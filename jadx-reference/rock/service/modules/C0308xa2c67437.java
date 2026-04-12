package com.storm.safe.rock.service.modules;

import java.util.Iterator;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {3520, 3526, 3530}, m214405m = "attemptVivoAndroid15SwitchToggle")
/* renamed from: com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptVivoAndroid15SwitchToggle$1 */
/* loaded from: classes2.dex */
final class C0308xa2c67437 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0327b2 f52931a0;

    /* renamed from: a1 */
    public Iterator f52932a1;

    /* renamed from: a2 */
    public int f52933a2;

    /* renamed from: a3 */
    public int f52934a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f52935a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0327b2 f52936a5;

    /* renamed from: a6 */
    public int f52937a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0308xa2c67437(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52936a5 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52935a4 = obj;
        this.f52937a6 |= Integer.MIN_VALUE;
        return this.f52936a5.m211717a6(null, this);
    }
}
