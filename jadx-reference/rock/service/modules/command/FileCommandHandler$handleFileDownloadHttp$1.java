package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.FileCommandHandler", m214403f = "FileCommandHandler.kt", m214404l = {127, 134}, m214405m = "handleFileDownloadHttp")
/* loaded from: classes2.dex */
final class FileCommandHandler$handleFileDownloadHttp$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public uz0 f53467a0;

    /* renamed from: a1 */
    public String f53468a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f53469a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0347a4 f53470a3;

    /* renamed from: a4 */
    public int f53471a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileCommandHandler$handleFileDownloadHttp$1(C0347a4 c0347a4, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53470a3 = c0347a4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53469a2 = obj;
        this.f53471a4 |= Integer.MIN_VALUE;
        return this.f53470a3.m211878a3(null, null, this);
    }
}
