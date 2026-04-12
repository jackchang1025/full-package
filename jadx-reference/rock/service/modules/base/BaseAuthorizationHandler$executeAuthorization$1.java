package com.storm.safe.rock.service.modules.base;

import java.util.ArrayList;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.base.BaseAuthorizationHandler", m214403f = "BaseAuthorizationHandler.kt", m214404l = {113}, m214405m = "executeAuthorization")
/* loaded from: classes2.dex */
final class BaseAuthorizationHandler$executeAuthorization$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public AbstractC0330a0 f53201a0;

    /* renamed from: a1 */
    public ArrayList f53202a1;

    /* renamed from: a2 */
    public ArrayList f53203a2;

    /* renamed from: a3 */
    public ArrayList f53204a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f53205a4;

    /* renamed from: a5 */
    public final /* synthetic */ AbstractC0330a0 f53206a5;

    /* renamed from: a6 */
    public int f53207a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseAuthorizationHandler$executeAuthorization$1(AbstractC0330a0 abstractC0330a0, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53206a5 = abstractC0330a0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53205a4 = obj;
        this.f53207a6 |= Integer.MIN_VALUE;
        return this.f53206a5.m211770a1(this);
    }
}
