package com.storm.safe.rock.service.modules.command;

import android.content.ContentResolver;
import android.media.AudioManager;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AppCommandHandler", m214403f = "AppCommandHandler.kt", m214404l = {295, 305}, m214405m = "handleMute")
/* loaded from: classes2.dex */
final class AppCommandHandler$handleMute$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public AudioManager f53431a0;

    /* renamed from: a1 */
    public ContentResolver f53432a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f53433a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0344a1 f53434a3;

    /* renamed from: a4 */
    public int f53435a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCommandHandler$handleMute$1(C0344a1 c0344a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53434a3 = c0344a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53433a2 = obj;
        this.f53435a4 |= Integer.MIN_VALUE;
        return this.f53434a3.m211876a3(null, null, this);
    }
}
