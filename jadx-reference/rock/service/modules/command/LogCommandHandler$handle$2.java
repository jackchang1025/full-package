package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.LogCommandHandler$handle$2", m214403f = "LogCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class LogCommandHandler$handle$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0348a5 f53508a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53509a2;

    /* renamed from: a3 */
    public final /* synthetic */ JSONObject f53510a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f53511a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0323a8 f53512a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LogCommandHandler$handle$2(C0348a5 c0348a5, String str, JSONObject jSONObject, String str2, C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53508a1 = c0348a5;
        this.f53509a2 = str;
        this.f53510a3 = jSONObject;
        this.f53511a4 = str2;
        this.f53512a5 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new LogCommandHandler$handle$2(this.f53508a1, this.f53509a2, this.f53510a3, this.f53511a4, this.f53512a5, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((LogCommandHandler$handle$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8 = this.f53512a5;
        String str = this.f53511a4;
        String str2 = this.f53509a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            JSONObject jSONObjectM211879a3 = C0348a5.m211879a3(this.f53508a1, str2, this.f53510a3);
            jSONObjectM211879a3.put("requestId", str);
            if (c0323a8 != null) {
                c0323a8.m211658c4(StringUtil.m212470a0("J1YWBV89Hz5YPzhc"), jSONObjectM211879a3);
            }
            t60.m214714d6("LogCommandHandler", "日志响应已发送: " + str2 + ", requestId=" + str);
            return new Integer(0);
        } catch (Exception e) {
            t60.m214705c6("LogCommandHandler", "处理日志命令失败: ".concat(str2), e);
            if (c0323a8 == null) {
                return null;
            }
            String strM212470a0 = StringUtil.m212470a0("J1YWBV89Hz5YPzhc");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("requestId", str);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, false);
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
