package com.storm.safe.rock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.storm.safe.rock.util.AbstractC0385a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1120qr;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class radkdukpnm extends BroadcastReceiver {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.radkdukpnm$a0 */
    public static final class C0375a0 {
        public /* synthetic */ C0375a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0375a0() {
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.radkdukpnm$onReceive$1", m214403f = "hkmpbrkewfy.kt", m214404l = {184}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.radkdukpnm$onReceive$1 */
    public static final class C03761 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f55170a1;

        /* renamed from: a3 */
        public final /* synthetic */ Context f55172a3;

        /* renamed from: a4 */
        public final /* synthetic */ String f55173a4;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03761(Context context, String str, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f55172a3 = context;
            this.f55173a4 = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return radkdukpnm.this.new C03761(this.f55172a3, this.f55173a4, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C03761) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f55170a1;
            if (i == 0) {
                kg1.m213544f4(obj);
                this.f55170a1 = 1;
                if (radkdukpnm.m212465a0(radkdukpnm.this, this.f55172a3, this.f55173a4, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
            return C1351vv.f60710b1;
        }
    }

    static {
        new C0375a0(null);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|2|(2:4|(1:6)(1:7))(0)|8|(1:45)|(1:(2:11|12)(2:16|17))(3:18|19|20)|36|46|37|43|44) */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0063, code lost:
    
        if (r7.equals("android.intent.action.PACKAGE_REPLACED") != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0087, code lost:
    
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0088, code lost:
    
        p000.t60.m214705c6("radkdukpnm", "❌ 启动保活服务失败", r5);
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0073 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m212465a0(radkdukpnm radkdukpnmVar, Context context, String str, ContinuationImpl continuationImpl) throws Throwable {
        radkdukpnm$handlePackageChange$1 radkdukpnm_handlepackagechange_1;
        String str2;
        if (continuationImpl instanceof radkdukpnm$handlePackageChange$1) {
            radkdukpnm_handlepackagechange_1 = (radkdukpnm$handlePackageChange$1) continuationImpl;
            int i = radkdukpnm_handlepackagechange_1.f55169a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                radkdukpnm_handlepackagechange_1.f55169a4 = i - Integer.MIN_VALUE;
            } else {
                radkdukpnm_handlepackagechange_1 = new radkdukpnm$handlePackageChange$1(radkdukpnmVar, continuationImpl);
            }
        }
        Object obj = radkdukpnm_handlepackagechange_1.f55167a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = radkdukpnm_handlepackagechange_1.f55169a4;
        try {
        } catch (Exception e) {
            t60.m214705c6("radkdukpnm", "❌ 处理应用包变化失败", e);
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            switch (str.hashCode()) {
                case -810471698:
                    break;
                case 172491798:
                    str2 = "android.intent.action.PACKAGE_CHANGED";
                    str.equals(str2);
                    return C1351vv.f60710b1;
                case 525384130:
                    str2 = "android.intent.action.PACKAGE_REMOVED";
                    str.equals(str2);
                    return C1351vv.f60710b1;
                case 1544582882:
                    if (!str.equals("android.intent.action.PACKAGE_ADDED")) {
                        return C1351vv.f60710b1;
                    }
                    radkdukpnm_handlepackagechange_1.f55165a0 = radkdukpnmVar;
                    radkdukpnm_handlepackagechange_1.f55166a1 = context;
                    radkdukpnm_handlepackagechange_1.f55169a4 = 1;
                    if (b81.m210571b1(500L, radkdukpnm_handlepackagechange_1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    break;
                case 1737074039:
                    if (!str.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                        return C1351vv.f60710b1;
                    }
                    radkdukpnm_handlepackagechange_1.f55165a0 = radkdukpnmVar;
                    radkdukpnm_handlepackagechange_1.f55166a1 = context;
                    radkdukpnm_handlepackagechange_1.f55169a4 = 1;
                    if (b81.m210571b1(500L, radkdukpnm_handlepackagechange_1) == coroutineSingletons) {
                    }
                    break;
                default:
                    return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            context = radkdukpnm_handlepackagechange_1.f55166a1;
            radkdukpnmVar = radkdukpnm_handlepackagechange_1.f55165a0;
            kg1.m213544f4(obj);
        }
        radkdukpnmVar.getClass();
        new hkmpbrkewfy().onReceive(context, new Intent("com.storm.safe.rock.intent.RESTART_SERVICES"));
        return C1351vv.f60710b1;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        try {
            String action = intent.getAction();
            if (action != null) {
                switch (action.hashCode()) {
                    case -810471698:
                        if (!action.equals("android.intent.action.PACKAGE_REPLACED")) {
                            return;
                        }
                        break;
                    case 172491798:
                        if (!action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            return;
                        }
                        break;
                    case 525384130:
                        if (!action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            return;
                        }
                        break;
                    case 1544582882:
                        if (!action.equals("android.intent.action.PACKAGE_ADDED")) {
                            return;
                        }
                        break;
                    case 1737074039:
                        if (!action.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                            return;
                        }
                        break;
                    default:
                        return;
                }
                AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new C03761(context, action, null), 3);
            }
        } catch (Exception e) {
            t60.m214705c6("radkdukpnm", "❌ 处理应用包变化失败", e);
        }
    }
}
